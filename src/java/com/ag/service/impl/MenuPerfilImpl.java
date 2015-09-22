/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Menu;
//import com.ag.model.*;
import com.ag.service.MenuPerfilManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 *
 * @author Larry
 */
  @Service("MenuPerfilManager")

public class MenuPerfilImpl implements MenuPerfilManager {
   @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;

    @Override
        public List<Menu> MenuPerfilManagerUsu(String usuario) {

        String hql =    "select p.menu   "+
                        " from  MenuPerfil p,UsuarioPerfil u  "  +
                        " where p.perfil.codigo= u.perfil.codigo"+
                        " and   p.estado = 'SY001'  " +
                        " and   u.usuario1.codigo = '"+usuario+"'"+
                        " and   u.fechaBaja is null " +
                        " order by p.menu.orden";

        return dao.find(hql);
        //return ;
    }

}
