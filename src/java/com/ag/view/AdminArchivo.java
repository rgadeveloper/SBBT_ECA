/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.Tbltipo;
import com.ag.service.ArchivoManager;
import com.ag.service.SpringContext;
import java.io.IOException;
import java.io.Serializable;
//import java.io.*;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;


import org.primefaces.model.UploadedFile;

/**
 *
 * @author Larry
 */
@ManagedBean
@SessionScoped
public class AdminArchivo implements Serializable {

    private String mensaje,estado;
    @ManagedProperty("#{login}")
    private Login login;
    private UploadedFile file;
    private List <Tbltipo> tipoMacromedidores;
    private String tipoMacroSel;
    private String transformador;
     private String macromedidor;


    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file; 
    }

    public AdminArchivo() {
        SpringContext context = SpringContext.getInstance();
        ArchivoManager archManager =  (ArchivoManager) context.getBean("ArchivoManager");
        mensaje = "";
        estado = "Carga";
        tipoMacromedidores = archManager.getTipoMacromedidores();
    }

    public void upload() throws IOException {
        if (file != null) {
            estado = "Cargado";
            SpringContext context = SpringContext.getInstance();
            ArchivoManager archivoManager = (ArchivoManager) context.getBean("ArchivoManager");
            String[] respuesta = archivoManager.cargaMacroMasivo(login.getUsuario(), "CrearMacroMas.xhtml", file.getInputstream());
            mensaje =  respuesta[0]; 
            String resultado =  respuesta[1];
            String procesados = respuesta[2];
            if (resultado.equals("exitoso")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", procesados));

            } else if(resultado.equals("warning")){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operación Con errores", procesados));

            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Errada", procesados));

            }
            
        }


    }

    public void guardar(){
            estado = "Cargado";
            SpringContext context = SpringContext.getInstance();
            ArchivoManager archivoManager = (ArchivoManager) context.getBean("ArchivoManager");
            String[] respuesta = archivoManager.cargaMacroIndividual(transformador, macromedidor, tipoMacroSel, login.getUsuario(), "CrearMacroInd");
            mensaje =  respuesta[0];
            String resultado =  respuesta[1];
            String procesados = respuesta[2];
            if (resultado.equals("exitoso")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", procesados));

            } else if(resultado.equals("error")){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Errada", procesados));

            }


    }
    
    public void volver(){
        setEstado("Carga");
        mensaje = "";
    }

    /**
     * @return the login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoMacromedidores
     */
    public List <Tbltipo> getTipoMacromedidores() {
        return tipoMacromedidores;
    }

    /**
     * @param tipoMacromedidores the tipoMacromedidores to set
     */
    public void setTipoMacromedidores(List tipoMacromedidores) {
        this.tipoMacromedidores = tipoMacromedidores;
    }

    /**
     * @return the tipoMacroSel
     */
    public String getTipoMacroSel() {
        return tipoMacroSel;
    }

    /**
     * @param tipoMacroSel the tipoMacroSel to set
     */
    public void setTipoMacroSel(String tipoMacroSel) {
        this.tipoMacroSel = tipoMacroSel;
    }

    /**
     * @return the transformador
     */
    public String getTransformador() {
        return transformador;
    }

    /**
     * @param transformador the transformador to set
     */
    public void setTransformador(String transformador) {
        this.transformador = transformador;
    }

    /**
     * @return the macromedidor
     */
    public String getMacromedidor() {
        return macromedidor;
    }

    /**
     * @param macromedidor the macromedidor to set
     */
    public void setMacromedidor(String macromedidor) {
        this.macromedidor = macromedidor;
    }
}
