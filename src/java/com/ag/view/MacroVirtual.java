/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.service.ArchivoManager;
import com.ag.service.SpringContext;
import java.io.IOException;
import java.io.Serializable;
//import java.io.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;


import org.primefaces.model.UploadedFile;

/**
 *
 * @author Depazan
 */
@ManagedBean
@SessionScoped
public class MacroVirtual implements Serializable{

    private String mensaje, estadoTrafoMacro, estadoMacroBarcir;
    @ManagedProperty("#{login}")
    private Login login;
    private UploadedFile file;
    private ArchivoManager archivoManager;
    private String nombre;
    private String direccion;
    private String idComercial;
    private String url;
    private String periodo;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public MacroVirtual() {
        SpringContext context = SpringContext.getInstance();
        archivoManager = (ArchivoManager) context.getBean("ArchivoManager");
        mensaje = "";
        estadoTrafoMacro = "Carga";
        estadoMacroBarcir = "Carga";
        url = "cargarTrafoMacro.xhtml";
    }

    public void crearMacroVirtual() {
        try {
            if (!archivoManager.existeMacroVirtual(idComercial)) {
                String dir=direccion!=null && !direccion.equals("") ?direccion:"-";
                archivoManager.crearMacroVirtual(login.getUsuario(), "MacroVirtual.xhtml", nombre, dir, idComercial);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado el Macro Virtual", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "El Macro Virtual " + idComercial + " ya existe.", ""));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ha ocurrido un Error en el Sistema", ""));
        } finally {
            nombre = null;
            direccion = null;
            idComercial = null;
        }
    }

    public void cargarArchivo(String tipoArchivo) throws IOException {
        if (file != null) {
            //SpringContext context = SpringContext.getInstance();
            //ArchivoManager archivoManager = (ArchivoManager) context.getBean("ArchivoManager");
            String[] respuesta;
            if (tipoArchivo.equals("macroTrafo")) {
                estadoTrafoMacro = "Cargado";
                respuesta = archivoManager.cargaAsociarTrafoMacro(login.getUsuario(), "MacroV.xhtml", file.getInputstream());
            } else {
                //tipoArchivo= BarCir
                estadoMacroBarcir = "Cargado";
                respuesta = archivoManager.cargaAsociarMacroCirBar(login.getUsuario(), "MacroV.xhtml", file.getInputstream());
            }

            mensaje = respuesta[0];
            String resultado = respuesta[1];
            String procesados = respuesta[2];
            if (resultado.equals("exitoso")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", procesados));

            } else if (resultado.equals("warning")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operación Con errores", procesados));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Errada", procesados));
            }
        }
    }

    public void ejecutarBalanceVirtual() {
        try {
            if (periodo != null && !periodo.equals("")) {

                String error = archivoManager.balanceVirtual(periodo, "10");

                if (error == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "La interfaz ha sido ejecutada.", ""));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "La interfaz no se ejecuto con exito.", "Detalle:" + error));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Debe seleccionar el periodo y tipo.", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Upss ha ocurrido un error en el sistema.", "Detalle:" + e.getMessage()));
        } finally {
            periodo = null;
        }

    }

    public void volverTrafoMacro() {
        setEstadoTrafoMacro("Carga");
        mensaje = "";
    }

    public void volverMacroBarcir() {
        setEstadoMacroBarcir("Carga");
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
     * @return the estadoTrafoMacro
     */
    public String getEstadoTrafoMacro() {
        return estadoTrafoMacro;
    }

    /**
     * @param estadoTrafoMacro the estadoTrafoMacro to set
     */
    public void setEstadoTrafoMacro(String estado) {
        this.estadoTrafoMacro = estado;
    }

    public String getEstadoMacroBarcir() {
        return estadoMacroBarcir;
    }

    public void setEstadoMacroBarcir(String estadoMacroBarcir) {
        this.estadoMacroBarcir = estadoMacroBarcir;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(String idComercial) {
        this.idComercial = idComercial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
