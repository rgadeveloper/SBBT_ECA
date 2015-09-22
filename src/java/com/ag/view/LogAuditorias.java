/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;


import com.ag.model.LogAuditoria;
import com.ag.model.view.Fecha;
import com.ag.service.LogAuditoriaManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
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
@ManagedBean
@SessionScoped
public class LogAuditorias implements Serializable {
    
    private SpringContext context;
    private LogAuditoriaManager logAuditoriaManager;

    private Date fechaIni, fechaFin;  
    private String programa, programaEjecucion; 
    private List<String> programas, programasEjecucion; 
    private List<LogAuditoria> auditorias;         
    
    public LogAuditorias() {
        context = SpringContext.getInstance();        
        logAuditoriaManager =  (LogAuditoriaManager) context.getBean("LogAuditoriaManager");
        programas=logAuditoriaManager.getPrograma();
        programasEjecucion=logAuditoriaManager.getProgramaEjecucion();
        auditorias=logAuditoriaManager.getLogs(null, null, null, null);
        fechaIni=new Date();
        fechaFin= new Date();
    }       
    
    public void visualizarAuditorias(){ 
        auditorias = new ArrayList<LogAuditoria>();
        String fi="";
        String ff="";
        if (fechaIni!=null) {
            fi= getFechaFormato(fechaIni, "dd/MM/yyyy");
            ff= fechaFin!=null?getFechaFormato(fechaFin, "dd/MM/yyyy"):"";         
            if (rangoFechaValido(fechaIni,fechaFin)){
            auditorias=logAuditoriaManager.getLogs(programa, programaEjecucion, fi, ff); 
            }           
            else{           
            FacesMessage msg = new FacesMessage("La Fecha Inicial debe ser menor o igual a la Fecha Final", "");  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
            }    
        } else {
            auditorias=logAuditoriaManager.getLogs(programa, programaEjecucion, fi, ff);
        }
        clearCampos();
    }  
    
    private void clearCampos(){
        fechaFin=null;
        fechaIni=null;
        programa=null;
        programaEjecucion=null;        
    }
    
    public String getFechaFormato(Date fecha, String formato){
        Fecha formatearFecha=new Fecha(); 
        return formatearFecha.getFechaFormato(fecha, formato);
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

    public List<LogAuditoria> getAuditorias() {
        return auditorias;
    }

    public void setAuditorias(List<LogAuditoria> auditorias) {
        this.auditorias = auditorias;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getProgramaEjecucion() {
        return programaEjecucion;
    }

    public void setProgramaEjecucion(String programaEjecucion) {
        this.programaEjecucion = programaEjecucion;
    }

    public List<String> getProgramas() {
        return programas;
    }

    public void setProgramas(List<String> programas) {
        this.programas = programas;
    }

    public List<String> getProgramasEjecucion() {
        return programasEjecucion;
    }

    public void setProgramasEjecucion(List<String> programasEjecucion) {
        this.programasEjecucion = programasEjecucion;
    }    
}
