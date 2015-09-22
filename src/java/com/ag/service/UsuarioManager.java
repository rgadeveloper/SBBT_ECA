/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service;

import com.ag.model.Perfil;
import com.ag.model.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface UsuarioManager {
    public boolean login(String usuario,String contrasena);

    public void save(String usuario,String contrasena);
    
    public void save(String usuario, String programa, String codigo ,String contrasena, String nombre, String tel, String email, String estado, Date fecha_alta, Perfil perfil);
      
    public List<Usuario> getUsuariosAll();
    
    public void saveUsuarioPerfil(String usuarioLogueado, String programa, Usuario usuario, Perfil perfil);
    
    public Perfil getPerfilDeUsuario(String coduser);
    
    public Usuario getUsuario(String codigo);
    
    public void updateUsuario(Usuario usuario);
    
    public boolean usuarioBloqueado(String usuario);
    
    public boolean existeUsuario (String coduser);

}
