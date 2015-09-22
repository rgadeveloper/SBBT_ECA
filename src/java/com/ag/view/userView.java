/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.Perfil;
import com.ag.model.Usuario;
import com.ag.service.PerfilManager;
import com.ag.service.SpringContext;
import com.ag.service.UsuarioManager;
import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
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
public class userView implements Serializable {
    
    private SpringContext context;
    private UsuarioManager usuarioManager;
    private PerfilManager perfilManager;
    private List<Usuario> usuarios;
    private Usuario usuarioSelected;
    private String codigo;
    private String contrasena;
    private String nombre;
    private String tel;
    private String email;
    private String estado;
    private Date fecha_alta;
    private List<Perfil> perfilesActivos;
    private String codigoPerfil;
    private Hashtable tablaPerfiles;
    private String viejaContrasena; 
    private String nuevaContrasena; 
    private String nuevaContrasenaConfirm;     
    
    public userView() {
        context = SpringContext.getInstance();
        usuarioManager=(UsuarioManager) context.getBean("UsuarioManager"); 
        perfilManager=(PerfilManager) context.getBean("PerfilManager");
        setUsuarios(usuarioManager.getUsuariosAll());
        //setPerfiles(perfilManager.getPerfilesActivos());    
        LlenaTablaPerfiles();
    }
    
    public void cambiarContrasena(String usuario){
      try { 
       if (nuevaContrasena.equals(nuevaContrasenaConfirm)) {       
        Usuario u = usuarioManager.getUsuario(usuario);
        String contrasenaActual=u.getPass();
        if (viejaContrasena.equals(contrasenaActual)) {
            if (!contrasenaActual.equals(nuevaContrasena)) {                
                  u.setPass(nuevaContrasena);
                  usuarioManager.updateUsuario(u);
                  FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                   "La contraseña ha sido modificada! La próxima vez que inice sesión. utiliza la contraseña nueva.", ""));                 
            }else
                FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "La contraseña que ha instroducido es igual a la antigua. Intente de nuevo.", ""));
        }else
            FacesContext.getCurrentInstance().addMessage
             (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
             "La contraseña antigua que ha instroducido no es válida. Intente de nuevo.", ""));
       }else
           FacesContext.getCurrentInstance().addMessage
           (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
           "Las contraseñas no coinciden. Intente de nuevo.", ""));
      } catch (Exception e) {
           FacesContext.getCurrentInstance().addMessage
           (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
           "Error al modificar contraseña. Error en el Sistema.", ""));
      }
    }      
    
    public String PerfilDelUsuario(String coduser){
        String nombrePerfil="-";
        if (coduser!=null && !coduser.equals("")) {
           Perfil p = usuarioManager.getPerfilDeUsuario(coduser);
           if (p!=null)
            nombrePerfil=(p.getNombre()==null)?"-":p.getNombre();
        }     
        
        return nombrePerfil;
    }
    
    public String getNombreEstado(String est){
        String nomEst;               
        if (est.equals("SY001")){
            nomEst="ACTIVO";
        }else{
            nomEst="INACTIVO";
        }
        return nomEst;
    }
    
    public void LlenaTablaPerfiles(){
        PerfilView pf=new PerfilView();
        setPerfiles(pf.getPerfilesOrderByNombre());
        tablaPerfiles=new Hashtable();
        Perfil p;
        String codigoPerfil;
        for(int i=0; i< perfilesActivos.size(); i++) {
            p =perfilesActivos.get(i);
            codigoPerfil=p.getCodigo();
            tablaPerfiles.put(codigoPerfil,p);
        }
    }
    
     public Perfil getPerfilOfTablaPerfiles(String codigoPerfil){
         Perfil p = (Perfil) tablaPerfiles.get(codigoPerfil);
         return p;
     }
    
    public void editUsuario(String usuario, String programa){
       try {
            /*codigo=usuarioSelected.getCodigo();
            nombre=usuarioSelected.getNombre();
            tel=usuarioSelected.getTelefono();
            email=usuarioSelected.getEmail();
            estado=usuarioSelected.getEstado();       
            contrasena=usuarioSelected.getPass();
            fecha_alta=usuarioSelected.getFechaAlta();
            String intentos=usuarioSelected.getIntentos()!=null && !usuarioSelected.getIntentos().toString().equals("")?
                            usuarioSelected.getIntentos().toString():"0";*/
           
            usuarioSelected.setUsuario(usuario);
            usuarioSelected.setPrograma(programa);

            if (codigoPerfil.equals("no")) {
                usuarioManager.updateUsuario(usuarioSelected);
                //usuarioManager.save(usuario, programa, codigo, nombre, tel, email, estado,contrasena,fecha_alta, intentos); 
            }else{
                Perfil p=perfilManager.getPerfil(codigoPerfil);
                usuarioManager.updateUsuario(usuarioSelected);
                usuarioManager.saveUsuarioPerfil(usuario, programa, usuarioSelected, p);
                //usuarioManager.save(usuario, programa, codigo, contrasena, nombre, tel, email, estado, fecha_alta,intentos, p);
            }     
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        }finally{
           ClearCampos(); 
        }
       //setUsuarios(usuarioManager.getUsuariosAll());
    }
    
    public void ClearCampos(){
       codigo=null;
       nombre=null;
       tel=null;
       email=null;
       estado=null;      
       contrasena=null;
       codigoPerfil=null;
       fecha_alta=null;
    } 
     
    public void saveUsuario(String usuario, String programa){   
       try {
          if (!usuarioManager.existeUsuario(codigo)) {
            Perfil perfil =perfilManager.getPerfil(codigoPerfil);
            usuarioManager.save(usuario, programa, codigo, contrasena, nombre, tel, email,"SY001", fecha_alta, perfil);
            setUsuarios(usuarioManager.getUsuariosAll()); 
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Usuario guardado exitosamente.", ""));
          }else
              FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "El usuario "+codigo+" ya existe.", ""));
       } catch (Exception e) {
           FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
       }finally{
          ClearCampos();  
       }        
    }
    
    public void msgEditado(){
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuario Editado exitosamente", ""));   
    }    
    
    public UsuarioManager getUsuarioManager() {
        return usuarioManager;
    }

    public void setUsuarioManager(UsuarioManager usuarioManager) {
        this.usuarioManager = usuarioManager;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }    
   
    public String getCodigoPerfil() {
        return codigoPerfil;
    }

    public void setCodigoPerfil(String codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    public List<Perfil> getPerfiles() {
        return perfilesActivos;
    }

    public void setPerfiles(List<Perfil> perfiles) {
        this.perfilesActivos = perfiles;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getViejaContrasena() {
        return viejaContrasena;
    }

    public void setViejaContrasena(String viejaContrasena) {
        this.viejaContrasena = viejaContrasena;
    }

    public String getNuevaContrasenaConfirm() {
        return nuevaContrasenaConfirm;
    }

    public void setNuevaContrasenaConfirm(String nuevaContrasenaConfirm) {
        this.nuevaContrasenaConfirm = nuevaContrasenaConfirm;
    }
    
}