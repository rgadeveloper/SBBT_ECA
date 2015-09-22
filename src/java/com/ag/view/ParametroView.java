/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.Parametro;
import com.ag.service.ParametroManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author arodriguezr
 */
@ManagedBean
@SessionScoped
public class ParametroView implements Serializable{
    
    private SpringContext context; 
    private ParametroManager parametroManager;
    private List<Parametro> parametros; 
    private Parametro parametroSelected;
    private String idParametro;
    private String nombre;
    private String valor;
   
    public ParametroView() {
        context = SpringContext.getInstance();
        parametroManager=(ParametroManager) context.getBean("ParametroManager");        
        setParametros(parametroManager.getParametrosAll());        
    }
    
    public void editParametro(String usuario, String programa){
        idParametro=parametroSelected.getIdParametro();
        nombre=parametroSelected.getNombre();
        valor=parametroSelected.getValor();
        parametroManager.save(usuario, programa, idParametro, nombre, valor);        
        setParametros(parametroManager.getParametrosAll());
        clearCampos();
    }
    
    public void clearCampos(){
        idParametro=null;
        nombre=null;
        valor=null;
    }
    
    public void saveParametro(String usuario, String programa){
        try {
            if (!parametroManager.existeParametro(idParametro)) {
               parametroManager.save(usuario, programa, idParametro, nombre, valor);        
               setParametros(parametroManager.getParametrosAll());
               
               FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Parámetro guardado exitosamente.", ""));
            }else
                FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "El parámetro "+idParametro+" ya existe.", ""));
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", "")); 
        }finally{
           clearCampos(); 
        }
    }
    
    public void msgEditado(){
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Parámetro Editado exitosamente", ""));   
    }
    
    public void msgGuardado(){
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Parámetro Guardado exitosamente", ""));   
    }

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Parametro getParametroSelected() {
        return parametroSelected;
    }

    public void setParametroSelected(Parametro parametroSelected) {
        this.parametroSelected = parametroSelected;
    }

    
}