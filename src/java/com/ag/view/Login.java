/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.view;

import com.ag.service.SpringContext;
import com.ag.service.UsuarioManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import javax.faces.application.FacesMessage;

/**
 *
 * @author 85154220
 */

@ManagedBean(name = "login")
@SessionScoped

public class Login implements Serializable{
    private SpringContext context ;
    private String usuario ,contrasena;
    private UsuarioManager usuarioManager;
    private boolean validarSession;
    
    /*Para mantener filtros del Modulo de Gestion en el Arbol E|G 
     durante la sesion*/
    private HashMap gestionTrafo;
    private String tipoRed;
    private String tipoUso;
    private String rango;
    
    public Login(){
        validarSession=true;
        context = SpringContext.getInstance();
        usuarioManager = (UsuarioManager) context.getBean("UsuarioManager");
    }
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
/**
 * Validacion de usuarios de la aplicacion
 * @return
 */
    public String validarLogin(){
      try{  
        if (!usuario.equals("") && usuario != null && !contrasena.equals("") && contrasena != null) {
            if (!usuarioManager.usuarioBloqueado(usuario)) {
                boolean respuesta = usuarioManager.login(usuario, contrasena);
                if (respuesta) {
                    validarSession = false;
                    return "inicio";
                } else {
                    validarSession = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Usuario y/o Contraseña errada", "Intentelo otra vez!"));
                    return "login";
                }
            } else {
                validarSession = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Usuario bloqueado", "Pongase en contacto con el Administrador!"));
                    return "login";
            }

        } else {
            validarSession = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Debe ingresar su Usuario y Contraseña", ""));
            return "login";
        }
      }catch(Exception e){
            validarSession = true;
            usuario=null;
            contrasena=null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema", ""));
            return "login";
        }
    }

    public boolean isValidarSession() {
        return validarSession;
    }

    public void setValidarSession(boolean validarSession) {
        this.validarSession = validarSession;
    }    
    
    public void goInicio(){
        ExternalContext ctx = 
        FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        try{
            ctx.redirect(ctxPath + "/inicio.xhtml");  
        }catch(IOException e){       
        }
    }
    
    public void validateSession() {
        //FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");            
        ExternalContext ctx =
                FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath =
                ((ServletContext) ctx.getContext()).getContextPath();

        try {
            // Usar el contexto de JSF para invalidar la sesión,
            // NO EL DE SERVLETS (nada de HttpServletRequest)
            ((HttpSession) ctx.getSession(false)).invalidate();

            // Redirección de nuevo con el contexto de JSF,
            // si se usa una HttpServletResponse fallará.
            // Sin embargo, como ya está fuera del ciclo de vida 
            // de JSF se debe usar la ruta completa -_-U

            ctx.redirect(ctxPath + "/login.xhtml");
        } catch (IOException ex) {
        }
    }
    
     public void logout() {
        ExternalContext ctx =
                FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath =
                ((ServletContext) ctx.getContext()).getContextPath();

        try {
            // Usar el contexto de JSF para invalidar la sesión,
            // NO EL DE SERVLETS (nada de HttpServletRequest)
            ((HttpSession) ctx.getSession(false)).invalidate();

            // Redirección de nuevo con el contexto de JSF,
            // si se usa una HttpServletResponse fallará.
            // Sin embargo, como ya está fuera del ciclo de vida 
            // de JSF se debe usar la ruta completa -_-U
            validarSession = true;
            ctx.redirect(ctxPath + "/login.xhtml");
        } catch (IOException ex) {
        }
    }
     
    public void gestionarTrafos() {
        gestionTrafo = new HashMap();
        gestionTrafo.put("TIPO_USO", tipoUso);
        gestionTrafo.put("TIPO_RED", tipoRed);
        gestionTrafo.put("RANGO", rango);
    }
     
    public HashMap getGestionTrafo() {
        return gestionTrafo;
    }

    public void setGestionTrafo(HashMap gestionTrafo) {
        this.gestionTrafo = gestionTrafo;
    }

    public String getTipoRed() {
        return tipoRed;
    }

    public void setTipoRed(String tipoRed) {
        this.tipoRed = tipoRed;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

 /*public String salir(){         
  try {
    ExternalContext ctx = 
      FacesContext.getCurrentInstance().getExternalContext();
    ((HttpSession) ctx.getSession(false)).invalidate();
    validarSession=true;
    return "/login.xhtml";
  } catch (Exception ex) {
    return "/inicio.xhtml";
  }
  
 }
 
 public String salir(){
        validarSession = true;
        ExternalContext ctx =  FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) ctx.getSession(false)).invalidate();
        return null;
    }*/

}
