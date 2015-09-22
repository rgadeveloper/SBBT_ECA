/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.SBalances;
import com.ag.model.SComponente;
import com.ag.model.ZonaGeografica;
//import com.ag.model.*;
import com.ag.service.SReporteManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Larry
 */
@Service("SReporteManager")
public class SReporteManagerImpl implements SReporteManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    
    @Override
    public List<ZonaGeografica> getZonasByTipo(String tipo) {

        String hql ="Select z from ZonaGeografica z "
                  + "where z.estado='AC001' and "
                  + "z.tipoComponente.idTipoComponente=" + tipo;
        return dao.find(hql);
    }

    @Override
    public List<SComponente> getTrafosByEmpresa(String id) {
        String hql ="SELECT r.sComponente FROM SRelComponenteUbicacion r "
                  + "WHERE r.sRelComponenteUbicacionPK.idZona in (SELECT z.idZona"
                                                             + " FROM ZonaGeografica z"
                                                             + " WHERE z.idPadre in (SELECT z.idZona"
                                                                                 + " FROM ZonaGeografica z"
                                                                                 + " WHERE z.idPadre in (SELECT zh.idZona"
                                                                                                     + " FROM ZonaGeografica zp, ZonaGeografica zh"
                                                                                                     + " WHERE zp.idZona=zh.idPadre and zp.idZona="+id+")))";
        return dao.find(hql);
    }

    @Override
    public List<SComponente> getTrafosByZona(String id) {
        String hql ="SELECT r.sComponente FROM SRelComponenteUbicacion r "
                  + "WHERE r.sRelComponenteUbicacionPK.idZona in (SELECT z.idZona"
                                                             + " FROM ZonaGeografica z"
                                                             + " WHERE z.idPadre in (SELECT zh.idZona"
                                                                                 + " FROM ZonaGeografica zp, ZonaGeografica zh"
                                                                                 + " WHERE zp.idZona=zh.idPadre and zp.idZona="+id+"))";
        return dao.find(hql);
    }

    @Override
    public List<SComponente> getTrafosByCircuitoOrBarrio(String id) {
        String hql ="SELECT r.sComponente FROM SRelComponenteUbicacion r "
                   +"WHERE r.sRelComponenteUbicacionPK.idZona="+id;
        return dao.find(hql);
    }

    @Override
    public List<SComponente> getTrafosBySubestacion(String id) {
        String hql ="SELECT r.sComponente FROM SRelComponenteUbicacion r "
                  + "WHERE r.sRelComponenteUbicacionPK.idZona in (SELECT z.idZona"
                                                             + " FROM ZonaGeografica z"
                                                             + " WHERE z.idZona in (SELECT zh.idZona"
                                                                                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                                                                                + " WHERE zp.idZona=zh.idPadre and zp.idZona="+id+"))";
        return dao.find(hql);
    }    
    
    @Override
    public SBalances getBalanceByPeriodo(String idComponente, int periodo) {
        String hql = "select max(b.sBalancesPK.periodo)"
                                              + " from SBalances b"
                                              + " where b.sBalancesPK.idTipoComponente=8 and"
                                                    + " b.sBalancesPK.idComponente="+idComponente;
        String periodoObjetivo = dao.findObject(hql).toString();
        int tam = periodoObjetivo.length();        
        String mes = periodoObjetivo.substring(tam-2);
        String ano = periodoObjetivo.substring(0, tam-2);
        String[] periodos = new String[12];
        for (int i = 0; i < 12; i++) {            
            if (mes.equals("00")) {
                mes="12";
                ano=String.valueOf(Integer.parseInt(ano)-1);
            }
            periodos[i]=ano+mes;
            int m=Integer.parseInt(mes)-1;
            mes=(m>9)?String.valueOf(m):
                      "0"+String.valueOf(m);
        }
               
        
        hql = "SELECT b FROM SBalances b "
                   + "WHERE b.sBalancesPK.idTipoComponente=8 and b.sBalancesPK.idComponente="+idComponente
                   + " and b.sBalancesPK.periodo ="+periodos[periodo];
        
        return dao.findObject(hql);
    }

    @Override
    public String getRutaReportes() {
        String hql ="SELECT p.valor FROM Parametro p WHERE p.idParametro ='RUTA_REPORTE'";
        return dao.findObject(hql).toString();
    }
    
}
