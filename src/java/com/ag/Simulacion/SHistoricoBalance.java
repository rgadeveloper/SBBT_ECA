/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.Simulacion;

import com.ag.model.SComponente;
import com.ag.model.view.Fecha;
import com.ag.service.SimularBalanceManager;
import com.ag.service.SpringContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext; 

/**
 *
 * @author arodriguezr
 */
@ManagedBean(name="sHistoricoBalance")  
@SessionScoped
public class SHistoricoBalance {
    
    private SpringContext context;
    private SimularBalanceManager simularBalance;

    private Date fechaIni;
    private Date fechaFin;    
    private List<Simulado> historico;
    private Simulado historicoSelected;       
    
    public SHistoricoBalance() {
        context = SpringContext.getInstance();        
        simularBalance =   (SimularBalanceManager) context.getBean("SimularBalance");
        fechaIni= new Date();
        fechaFin= new Date();
    }   
    
    public void exportar(){      
        String idSimulacion= historicoSelected.getId();
        try {
          String error=simularBalance.exportar(idSimulacion);
          
          if (error==null){
            FacesMessage msg = new FacesMessage("", "Se ha exportado con éxito");          
            FacesContext.getCurrentInstance().addMessage(null, msg);
          }else{
            FacesMessage msg = new FacesMessage("", "Ocurrio un error durante la exportación.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
          }             
        } catch (Exception e) {
          FacesMessage msg = new FacesMessage("", "Ocurrio un error durante la exportación.");  
          FacesContext.getCurrentInstance().addMessage(null, msg); 
        }      
    }
    
    public void visualizarHistorico(){ 
         historico = new ArrayList<Simulado>();
        if (fechaIni!=null && fechaFin!=null) {
            String fi= getFechaFormato(fechaIni, "dd/MM/yyyy");
            String ff= getFechaFormato(fechaFin, "dd/MM/yyyy");            
            if (rangoFechaValido(fechaIni, fechaFin)) {
                setHistorico(simularBalance.getHistoricoSim(fi, ff));
            } else {
                FacesMessage msg = new FacesMessage("Aviso", "La Fecha Inicial debe ser menor o igual a la Fecha Final");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
            fechaIni=null;
            fechaFin=null;
        } else {            
            FacesMessage msg = new FacesMessage("Aviso", "Seleccione criterio de busqueda.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
         
    }  
    
     protected boolean rangoFechaValido(Date fechaInicial, Date fechaFinal) {
        if (fechaInicial.before(fechaFinal)) {
            return true;
        } else {
            if (fechaFinal.before(fechaInicial)) {
                return false;
            } else {
                return true; //son iguales
            }
        }
    }
    
    public String getFechaFormato(Date fecha, String formato){
        Fecha formatearFecha=new Fecha(); 
        return formatearFecha.getFechaFormato(fecha, formato);
    }
    
    public String getDescripcionTC(Short id){        
        return simularBalance.getTipoComponente(id.toString())
                             .getDescripcion();        
    }
    
    public String getIDComercial(BigInteger id){           
         SComponente c= simularBalance.getComponente(id.toString());
         String idC = c.getIdComponente().toString();
         String nombre = c.getNombre();
         return idC + "-" + nombre;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public List<Simulado> getHistorico() {
        return historico;
    }

    public void setHistorico(List<Simulado> historico) {
        this.historico = historico;
    }

   
    public Simulado getHistoricoSelected() {
        return historicoSelected;
    }

    public void setHistoricoSelected(Simulado historicoSelected) {
        this.historicoSelected = historicoSelected;
    }    
}
