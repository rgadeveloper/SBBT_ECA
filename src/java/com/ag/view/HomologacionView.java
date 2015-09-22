/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.Homologacion;
import com.ag.model.TipoComponente;
import com.ag.service.HomologacionManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ptorres
 */
@ManagedBean
@SessionScoped
public class HomologacionView  implements Serializable{
    private SpringContext context;
    private HomologacionManager homologacionManager;
    private List<Homologacion> homologaciones;
    
    private String tipo_ceo;
    private String tipo_simbal;
    private String grupo;
    private Homologacion homologacionSelected;
    private List<TipoComponente> tipos;
    private String tipoComponente;
    
    public HomologacionView() {
        context = SpringContext.getInstance();
        homologacionManager = (HomologacionManager) context.getBean("HomologacionManager");
        setHomologaciones(homologacionManager.getHomologacionAll());
    }
    
    /**
     * @return the tipo_ceo
     */
    public String getTipo_ceo() {
        return tipo_ceo;
    }

    /**
     * @param tipo_ceo the tipo_ceo to set
     */
    public void setTipo_ceo(String tipo_ceo) {
        this.tipo_ceo = tipo_ceo;
    }

    /**
     * @return the tipo_simbal
     */
    public String getTipo_simbal() {
        return tipo_simbal;
    }

    /**
     * @param tipo_simbal the tipo_simbal to set
     */
    public void setTipo_simbal(String tipo_simbal) {
        this.tipo_simbal = tipo_simbal;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the homologacionSelected
     */
    public Homologacion getHomologacionSelected() {
        return homologacionSelected;
    }

    /**
     * @param homologacionSelected the homologacionSelected to set
     */
    public void setHomologacionSelected(Homologacion homologacionSelected) {
        this.homologacionSelected = homologacionSelected;
    }
    
    public void editHomologacion(String usuario, String programa){
        try {
          homologacionSelected.setUsuario(usuario);
          homologacionSelected.setPrograma(programa);
          homologacionManager.update(homologacionSelected);
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                 "Editado con exito.", null));
          
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                 "No pudo ser editado correctamente.", "Error en el sistema."));
        }finally{
           ClearCampos();
        }
        
    }
    
    public void saveHomologacion(String usuario, String programa){ 
      try {
        homologacionManager.save(usuario, programa, tipo_ceo, tipo_simbal, grupo);
        getHomologaciones().clear();
        setHomologaciones(homologacionManager.getHomologacionAll());  
        FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                 "Agregado con exito.", null));
      } catch (Exception e) {
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                 "No pudo ser agregada la zona.", "Error en el sistema."));
      }finally{
        ClearCampos();
      }
              
    }
    
    public void ClearCampos(){
        tipo_ceo=null;
        tipo_simbal=null;       
        grupo=null;
        tipoComponente=null;
    }

    public List<TipoComponente> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoComponente> tipos) {
        this.tipos = tipos;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public List<Homologacion> getHomologaciones() {
        return homologaciones;
    }

    public void setHomologaciones(List<Homologacion> homologaciones) {
        this.homologaciones = homologaciones;
    }
}
