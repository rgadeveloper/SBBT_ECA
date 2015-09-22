/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Estado;
import com.ag.model.Perfil;
//import com.ag.model.*;
import com.ag.model.view.Fecha;
import com.ag.service.PerfilManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */

@Service("PerfilManager")
public class PerfilManagerImpl implements PerfilManager{

    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;
    
    @Override
    public List<Perfil> getPerfilesAll() {
        String hql ="Select p from Perfil p order by p.nombre";
        return dao.find(hql);
    }
    
    @Override
    public List<Perfil> getPerfilesActivos() {
        String hql ="Select p from Perfil p where p.estado='AC001' order by p.nombre";
        return dao.find(hql);
    }

    @Override
    public void save(String usuario, String programa, String nombre, String codigo, String estado) {
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        perfil.setPrograma(programa);        
        Fecha fechaModif = new Fecha();         
        perfil.setFechaModif(fechaModif.getFechaSistema());
        perfil.setNombre(nombre);
        perfil.setCodigo(codigo);
        perfil.setEstado(getEstado(estado));
        dao.persist(perfil);
    }

    @Override
    public Perfil getPerfil(String codperfil) {
        String hql = "select p from Perfil p where p.codigo='" + codperfil +"'";              
        return dao.findObject(hql);
    }

    @Override
    public boolean existePerfil(String codigo) {
        String hql="SELECT p FROM Perfil p WHERE p.codigo = '" + codigo+"'";
        boolean existe=dao.findObject(hql)!=null?true:false;
        return existe;
    }
    
    public Estado getEstado(String idEstado) {
        String hql = "SELECT e FROM Estado e WHERE e.idEstado='" + idEstado +"'";              
        return dao.findObject(hql);
    }
    
}
