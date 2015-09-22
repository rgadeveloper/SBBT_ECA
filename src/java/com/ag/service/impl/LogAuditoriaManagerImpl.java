/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.LogAuditoria;
//import com.ag.model.*;
import com.ag.service.LogAuditoriaManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("LogAuditoriaManager")
public class LogAuditoriaManagerImpl implements LogAuditoriaManager{
    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;

    @Override
    public List<String> getPrograma() {
        String hql="SELECT distinct(l.programa) FROM LogAuditoria l order by l.programa";
        return dao.find(hql);
    }

    @Override
    public List<String> getProgramaEjecucion() {
        String hql="SELECT distinct(l.programaEjecucion) FROM LogAuditoria l order by l.programaEjecucion";
        return dao.find(hql);
    }

    @Override
    public List<LogAuditoria> getLogs(String programa, String programaEjecucion, String fechaIni, String fechaFin) {
        String hql="SELECT l FROM LogAuditoria l";
        boolean filtro = false;
        
        if (programa!=null && !programa.equals("")) {
            hql+=filtro?" and l.programa='"+programa+"'"
                       :" where l.programa='"+programa+"'";
            filtro=true;
        }
        
        if (programaEjecucion!=null && !programaEjecucion.equals("")) {
            hql+=filtro?" and l.programaEjecucion='"+programaEjecucion+"'"
                       :" where l.programaEjecucion='"+programaEjecucion+"'";
            filtro=true;
        }
        
        if (fechaFin!=null && fechaIni!=null && !fechaFin.equals("") && !fechaIni.equals("")) {
            hql+=filtro?" and trunc(l.fecha)>='"+fechaIni+"'":
                        " where trunc(l.fecha)>='"+fechaIni+"'";
            hql+=" and trunc(l.fecha)<='"+fechaFin+"'";            
        }
        
        hql+=" order by l.fecha";
                
        return dao.find(hql);
    }
}
