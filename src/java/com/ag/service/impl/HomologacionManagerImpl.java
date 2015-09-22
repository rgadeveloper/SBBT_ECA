/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;


import com.ag.dao.Dao;
import com.ag.model.TipoComponente;
import com.ag.model.Homologacion;
import com.ag.model.view.Fecha;
import com.ag.service.HomologacionManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author ptorres
 */
@Service("HomologacionManager")
public class HomologacionManagerImpl implements HomologacionManager {
    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;
    
        @Override
    public TipoComponente getTipoComponente(String id) {
        String hql = "SELECT t FROM TipoComponente t WHERE t.idTipoComponente ="+id;
        return dao.findObject(hql);
    }
        
            @Override
    public List<Homologacion> getHomologacionAll() {        
        String hql = "SELECT h FROM Homologacion h";
        return dao.find(hql);  
    }
            
            @Override
    public void save(String usuario, String programa, String tipo_ceo, String tipo_simbal, String grupo) {
        Homologacion homologacion = new Homologacion();
        homologacion.setUsuario(usuario);
        homologacion.setPrograma(programa);
        Fecha date = new Fecha();
        homologacion.setFechaModif(date.getFechaSistema());
        homologacion.setTipo_ceo(tipo_ceo);
        homologacion.setTipo_simbal(tipo_simbal);
        homologacion.setGrupo(grupo);
        homologacion.setEstado("AC001");
        dao.persist(homologacion);
    }
            
            @Override
    public void update(Homologacion homologacion) {
        Fecha date = new Fecha();
        homologacion.setFechaModif(date.getFechaSistema());
        dao.persist(homologacion);
    }
            
            @Override
    public List<TipoComponente> getTiposComponentes() {
        String hql ="SELECT t FROM TipoComponente t";
        return dao.find(hql); 
    }
    
            @Override
    public Homologacion getHomologacion(String id) {
        String hql ="SELECT t FROM Homologacion h WHERE h.tipo_simbal ="+id;
        return dao.findObject(hql); 
    }
    
}
