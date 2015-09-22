/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Grupo;
import com.ag.model.Tbltipo;
import com.ag.model.view.Fecha;
import com.ag.service.TipoManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("TipoManager")
public class TipoManagerImpl implements TipoManager{
    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;

    @Override
    public List<Grupo> getGruposAll() {
        String hql ="SELECT g FROM Grupo g ORDER BY g.nombre";
        return dao.find(hql);
    }
    
    @Override
    public List<Tbltipo> getTiposAll() {
        String hql ="SELECT t FROM Tbltipo t";
        return dao.find(hql);
    }

    @Override
    public List<Tbltipo> getTiposByGrupo(String codgrupo) {
        String hql ="SELECT t FROM Tbltipo t WHERE t.grupo.codigo = '"+codgrupo+"'";
        return dao.find(hql);
    }

    @Override
    public Tbltipo save(String usuario, String programa, String tipo, String nombre, String codgrupo) {
        Tbltipo tbltipo = new Tbltipo();
        tbltipo.setUsuario(usuario);
        tbltipo.setPrograma(programa);        
        Fecha date=new Fecha();        
        tbltipo.setFechaModif(date.getFechaSistema());
        tbltipo.setTipo(tipo);
        tbltipo.setNombre(nombre);
        tbltipo.setGrupo(getGrupo(codgrupo));
        dao.persist(tbltipo);
        return tbltipo;
    }

    @Override
    public boolean existeTipo(String tipo) {
        String hql="SELECT t FROM Tbltipo t WHERE t.tipo ='" + tipo+"'";
        boolean existe=dao.findObject(hql)!=null?true:false;
        return existe;    
    }
    
    private Grupo getGrupo(String codgrupo) {        
        String hql = "SELECT g FROM Grupo g WHERE g.codigo ='" + codgrupo+"'";              
        return dao.findObject(hql);
    }
    
    @Override
    public List<Tbltipo> getTiposDescripciones() {
        String hql ="SELECT t FROM Tbltipo t WHERE t.grupo.codigo = 'TRC000'";
        return dao.find(hql);
    }
    
    @Override
    public void update(Tbltipo tipo) {
        Fecha date = new Fecha();
        tipo.setFechaModif(date.getFechaSistema());
        dao.persist(tipo);
    }
    
    @Override
    public Tbltipo saveTipoRangoClasificacion(String usuario, String programa, String tipo, String nombre, String codgrupo) {
        Tbltipo tbltipo = new Tbltipo();
        tbltipo.setUsuario(usuario);
        tbltipo.setPrograma(programa);        
        Fecha date=new Fecha();        
        tbltipo.setFechaModif(date.getFechaSistema());
        tbltipo.setTipo(tipo);
        tbltipo.setNombre(nombre);
        tbltipo.setGrupo(getGrupo(codgrupo));
        dao.persist(tbltipo);
        return tbltipo;
    }
}
    

