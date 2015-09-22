/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

import com.ag.model.Menu;
import com.ag.model.Perfil;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface MenuManager {
    
    public List<Menu> getMenusAll();
    
    public List<Menu> getMenusSoloPadres();
    
    public List<Menu> getMenusHijos();
    
    public Menu getMenu(String id);
    
    public void saveMenuPerfil(String usuario, String programa, Menu menu, Perfil perfil);
    
    public void desactivarMenuPerfil(String idPerfil);
    
    public boolean menuAsociado(String idMenu, String idPerfil);
}
