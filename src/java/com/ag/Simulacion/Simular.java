/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.Simulacion;

import com.ag.service.ConsecutivoManager;
import com.ag.service.SCalculaBalanceManager;
import com.ag.service.SimularBalanceManager;
import com.ag.service.SpringContext;
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext; 

/**
 *
 * @author arodriguezr
 */
@ManagedBean(name="simular")  
@SessionScoped
public class Simular implements Serializable {

    private SpringContext context;
    private SimularBalanceManager simularBalance;
    private SCalculaBalanceManager calculaBalanceManager;
    private String periodo;
   
    public Simular() {
        context = SpringContext.getInstance();        
        simularBalance =   (SimularBalanceManager) context.getBean("SimularBalance");  
        calculaBalanceManager =(SCalculaBalanceManager) context.getBean("SCalcularBalance");
    }      
   
    
    public void calcularBalanceSim(String usuario, String programa) {
      try {
        ConsecutivoManager app = (ConsecutivoManager) context.getBean("ConsecutivoManager");
        String idSimulacion = app.getConsecutivo("ID_SIMULACION",usuario, programa,"A","0");
        String periodo = simularBalance.getMaxPeriodo();
        
        String error = calculaBalanceManager.calcularBalance(periodo, idSimulacion);
        if (error==null){
            int idSim=Integer.parseInt(idSimulacion)-1;
            FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                   "El Calculo de Balances para Simulacion finalizo correctamente.", "Id Simulacion:"+idSim + " - Periodo:"+simularBalance.getMaxPeriodo()));
        }else
            FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                   "El Calculo de Balances para Simulacion no se realizo con exito.", "Detalle:"+error)); 
            
      } catch (Exception e) {
          FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                   "Upss ha ocurrido un error en el sistema.", "Detalle:"+e.getMessage())); 
      }
 
        
    }
    
    public void copiarDatosSim() {
      try {        
        String error = calculaBalanceManager.copiarDatosSimulacion(periodo);
        if (error==null)
            FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                   "El Procceso de Copia de Datos a Simulacion se realizo con exito.","" ));
        else
            FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                   "El Procceso de Copia de Datos a Simulacion se realizo con exito.", "Detalle:"+error)); 
            
      } catch (Exception e) {
          FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                   "Ha ocurrido un error en el sistema.", "Detalle:"+e.getMessage())); 
      }        
    }
  
    public void displayLocation() {  
        FacesMessage msg = new FacesMessage("Selected", "Tipo:" );  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
}
