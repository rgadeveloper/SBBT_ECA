/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Menu;
import com.ag.model.MenuPerfil;
import com.ag.model.MenuPerfilPK;
import com.ag.model.Perfil;
import com.ag.model.view.Fecha;
import com.ag.service.MenuManager;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */

@Service("MenuManager")
public class MenuManagerImpl implements MenuManager{

    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;
    
    @Override
    public List<Menu> getMenusAll() {
        String hql ="Select m from Menu m order by m.codigo";
        return dao.find(hql);
    }

    @Override
    public List<Menu> getMenusSoloPadres() {
        String hql ="Select m from Menu m where m.codPadre is null order by m.codigo";
        return dao.find(hql);
    }

    @Override
    public List<Menu> getMenusHijos() {
       String hql ="Select m from Menu m where m.codPadre is not null order by m.codigo";
       return dao.find(hql);
    }

    @Override
    public Menu getMenu(String id) {
        String hql ="Select m from Menu m WHERE m.codigo='"+id+"'";
        return dao.findObject(hql);
    }
    
    @Override
    public void saveMenuPerfil(String usuario, String programa, Menu menu, Perfil perfil) {
        MenuPerfil menuPerfil=new MenuPerfil();
        menuPerfil.setUsuario(usuario);
        menuPerfil.setPrograma(programa);
        Fecha fechaModif = new Fecha();
        menuPerfil.setFechaModif(fechaModif.getFechaSistema());
        menuPerfil.setMenu(menu);
        menuPerfil.setPerfil(perfil);
        menuPerfil.setEstado("SY001");
       
        MenuPerfilPK menuPerfilPK=new MenuPerfilPK(menu.getCodigo(), perfil.getCodigo());
        menuPerfil.setMenuPerfilPK(menuPerfilPK);
        
        dao.persist(menuPerfil); 
    }

    @Override
    public void desactivarMenuPerfil(String idPerfil) {
        String hql ="SELECT m FROM MenuPerfil m WHERE m.menuPerfilPK.codigoPerfil='"+idPerfil+"'";
        List<MenuPerfil> menuPerfilList = dao.find(hql);
        if (!menuPerfilList.isEmpty()) {
           Iterator iter = menuPerfilList.iterator();
           while (iter.hasNext()){
               MenuPerfil mp = (MenuPerfil) iter.next();
               mp.setEstado("SY002");
               dao.persist(mp);
           } 
        }        
    }

    @Override
    public boolean menuAsociado(String idMenu, String idPerfil) {        
        String hql="SELECT m FROM MenuPerfil m WHERE m.menuPerfilPK.codigoMenu = '" +idMenu+ "'"
                + " and m.menuPerfilPK.codigoPerfil ='" +idPerfil+ "' and m.estado='SY001'";
        
        boolean isSelected = dao.findObject(hql)!=null?true:false;
        return isSelected;
    }
    
}
