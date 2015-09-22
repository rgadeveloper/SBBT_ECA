/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Funcion;
import com.ag.service.FuncionManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */

@Service("FuncionManager")
public class FuncionManagerImpl implements FuncionManager{

    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;
    
    @Override
    public List<Funcion> getFuncionesAll() {
        String hql ="Select f from Funcion f order by f.nombre";
        return dao.find(hql);
    }
    
}
