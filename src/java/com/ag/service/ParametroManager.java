/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service;

import com.ag.model.Parametro;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface ParametroManager {   
    
    public void save(String usuario, String programa, String idParametro ,String nombre, String valor);
        
    public List<Parametro> getParametrosAll();
    
    public boolean existeParametro(String idParametro);
    
    //public void edit(String idParametro ,String nombre, String valor);
}
