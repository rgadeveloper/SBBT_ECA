/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

import com.ag.model.Perfil;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface PerfilManager {
    
    public List<Perfil> getPerfilesAll();
    
    public List<Perfil> getPerfilesActivos();
    
    public Perfil getPerfil(String codperfil);
    
    public void save(String usuario, String programa, String nombre, String codigo, String estado);  
    
    public boolean existePerfil (String codigo);
}
