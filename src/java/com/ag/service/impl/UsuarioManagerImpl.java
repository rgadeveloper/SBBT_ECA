/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Perfil;
import com.ag.model.Usuario;
import com.ag.model.UsuarioPerfil;
import com.ag.model.UsuarioPerfilPK;
import com.ag.model.view.Fecha;
import com.ag.service.UsuarioManager;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("UsuarioManager")
public class UsuarioManagerImpl implements UsuarioManager{
    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;
    private short zero=0;

    @Override
    public boolean login(String usuario, String contrasena) {
        String hql = "SELECT u FROM Usuario u "
                + "WHERE u.codigo = '" + usuario + "' and "
                + "u.estado='SY001'";
        Usuario user = dao.findObject(hql);
        if (user == null) {
            return false;
        }
        if (user.getPass().equals(contrasena)) {           //borrar intentos en la tabal usuario
            user.setIntentos(zero);
            dao.persist(user);
            return true;
        } else {
            //aumentar intentos en la tabla usuario
            short userIntento = user.getIntentos()!=null?user.getIntentos():zero;
            short cont = (short) (userIntento+1);
            user.setIntentos(cont);
            dao.persist(user);
            return false;
        }
    }

    @Override
    public void save(String usuario, String contrasena) {
        Usuario user = new Usuario();
        user.setUsuario("MIGRACION");
        user.setPrograma("AUTOMATICO");        
        user.setPass(contrasena);
        user.setUsuario(usuario);
        dao.persist(user);
    }    
   
    @Override
    public void save(String usuario, String programa, String codigo, String contrasena, String nombre, String tel, String email, String estado, Date fecha_alta, Perfil perfil) {
        Usuario user = new Usuario();
        user.setUsuario(usuario);
        user.setPrograma(programa);
        Fecha date = new Fecha();
        user.setFechaModif(date.getFechaSistema());
        user.setCodigo(codigo);
        user.setPass(contrasena);
        user.setNombre(nombre);
        user.setTelefono(tel);
        user.setEstado(estado);
        user.setFechaAlta(fecha_alta);
        user.setEmail(email);        
        
        dao.persist(user);
        saveUsuarioPerfil(usuario, programa, user, perfil);
        
    }
    
    @Override
    public List<Usuario> getUsuariosAll() {    
        String hql ="Select u from Usuario u order by u.nombre";
        return dao.find(hql);    
    }

    @Override
    public void saveUsuarioPerfil(String usuarioLogueado, String programa, Usuario usuario, Perfil perfil) {
        darBajaUsuarioPerfil(usuario.getCodigo());
        UsuarioPerfil usuarioPerfil=new UsuarioPerfil();
        usuarioPerfil.setUsuario(usuarioLogueado);
        usuarioPerfil.setPrograma(programa);       
        Fecha date = new Fecha();
        usuarioPerfil.setFechaModif(date.getFechaSistema());
        usuarioPerfil.setUsuario1(usuario);
        usuarioPerfil.setPerfil(perfil);     
       
        UsuarioPerfilPK usuarioPerfilPK=new UsuarioPerfilPK(usuario.getCodigo(), perfil.getCodigo(), date.getFechaSistema());
        usuarioPerfil.setUsuarioPerfilPK(usuarioPerfilPK);
        
        dao.persist(usuarioPerfil);        
    }

    @Override
    public Perfil getPerfilDeUsuario(String coduser) {
        String hql = "select u.perfil from UsuarioPerfil u where u.usuario1.codigo='" + coduser + "' AND u.fechaBaja IS NULL order by u.fechaBaja desc";              
        return dao.findObject(hql);
    }

    @Override
    public Usuario getUsuario(String codigo) {
        String hql = "SELECT u FROM Usuario u WHERE u.codigo = '" + codigo+"'";
        return dao.findObject(hql);
    }

    @Override
    public void updateUsuario(Usuario usuario) {
        dao.persist(usuario);
    }
    
    public void darBajaUsuarioPerfil(String idUsuario){
        
        String hql="SELECT u FROM UsuarioPerfil u "
                + "WHERE u.usuarioPerfilPK.codigoUsuario ='"+idUsuario+"' "
                + "and u.fechaBaja IS NULL";
        
        if (!dao.find(hql).isEmpty()){
         List<UsuarioPerfil> data = dao.find(hql);
         for (Iterator it = data.iterator(); it.hasNext();) {
             UsuarioPerfil up = (UsuarioPerfil) it.next(); 
             Fecha fecha=new Fecha();
             up.setFechaBaja(fecha.getFecha());
             dao.persist(up);
         }
        }
             
    }
    
    /*public void logout() {
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
    ctx.redirect(ctxPath + "/faces/index.xhtml");
  } catch (IOException ex) {
    ex.printStackTrace();
  }
}*/

    @Override
    public boolean existeUsuario(String coduser) {
        String hql="SELECT u FROM Usuario u WHERE u.codigo = '" + coduser+"'";
        boolean existe=dao.findObject(hql)!=null?true:false;
        return existe; 
    }

    @Override
    public boolean usuarioBloqueado(String usuario) {        
        String hql ="SELECT p.valor FROM Parametro p WHERE p.idParametro ='LOGIN_MAXIMO_INTENTO'";
        String valor = dao.findObject(hql).toString();
        short maxIntento = Short.valueOf(valor);
        Usuario user = getUsuario(usuario); 
        if (user == null) {
            return false;
        }
        short userIntento = user.getIntentos()!=null?user.getIntentos():zero;
        boolean bloqueado = userIntento >= maxIntento?
                true:false;
        return bloqueado;       
    }

}
