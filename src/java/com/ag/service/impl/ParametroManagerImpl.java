/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Parametro;
import com.ag.model.view.Fecha;
import com.ag.service.ParametroManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("ParametroManager")
public class ParametroManagerImpl implements ParametroManager{
    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;

    @Override
    public List<Parametro> getParametrosAll() {
        String hql ="Select p from Parametro p order by p.nombre";
        return dao.find(hql);
    }

    @Override
    public void save(String usuario, String programa, String idParametro, String nombre, String valor) {
        Parametro parametro = new Parametro();
        parametro.setUsuario(usuario);
        parametro.setPrograma(programa);        
        Fecha date=new Fecha();         
        parametro.setFechaModif(date.getFechaSistema());
        parametro.setNombre(nombre);
        parametro.setIdParametro(idParametro);
        parametro.setValor(valor);
        dao.persist(parametro);
    }

    /*@Override
    public void edit(String idParametro, String nombre, String valor) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Parametro p = (Parametro) session.get(Parametro.class, 1);
    }*/

    @Override
    public boolean existeParametro(String idParametro) {
        String hql="SELECT p FROM Parametro p WHERE p.idParametro ='"+idParametro+"'";
        boolean existe=dao.findObject(hql)!=null?true:false;
        return existe;        
    }
    
}
    

    