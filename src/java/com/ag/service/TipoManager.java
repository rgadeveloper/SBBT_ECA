/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service;

import com.ag.model.Grupo;
import com.ag.model.Tbltipo;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface TipoManager {   
    
    public List<Grupo> getGruposAll();
    
    public List<Tbltipo> getTiposAll();
    
    public List<Tbltipo> getTiposByGrupo(String codgrupo);  
    
    public List<Tbltipo> getTiposDescripciones();
    
    public Tbltipo save(String usuario, String programa, String tipo, String nombre, String codgrupo);
        
    public boolean existeTipo (String tipo);    
    
    public void update(Tbltipo tipo);
    
    public Tbltipo saveTipoRangoClasificacion(String usuario, String programa, String tipo, String nombre, String codgrupo);
}
