/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Color;
import com.ag.model.RangoClasificacion;
import com.ag.model.TipoComponente;
//import com.ag.model.*;
import com.ag.model.view.Fecha;
import com.ag.service.RangoManager;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("RangoManager")
public class RangoManagerImpl implements RangoManager{
    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;

    @Override
    public List<RangoClasificacion> getRangosAll() {
        String hql ="Select r from RangoClasificacion r order by r.descripcion";
        return dao.find(hql);
    }

    @Override
    public List<Color> getColoresAll() {
        String hql ="Select c from Color c order by c.descripcion";
        return dao.find(hql);
    }

    @Override
    public List<TipoComponente> getTiposComponentesAll() {
        String hql ="Select t from TipoComponente t order by t.descripcion";
        return dao.find(hql);
    }

    @Override
    public void save(String usuario, String programa, BigDecimal idRango, BigDecimal porcMinimo, BigDecimal porcMaximo, String descripcion, short codTipoComponente, long codColor) {
        RangoClasificacion rango = new RangoClasificacion();
        rango.setUsuario(usuario);
        rango.setPrograma(programa);        
        Fecha date=new Fecha();        
        rango.setFechaModif(date.getFechaSistema());
        rango.setIdRango(idRango);
        rango.setPorcMinimo(porcMinimo);
        rango.setPorcMaximo(porcMaximo);
        rango.setDescripcion(descripcion);
        Color color = getColor(codColor);        
        rango.setColor(color);
        TipoComponente tp=getTipoComponente(codTipoComponente);
        rango.setTipoComponente(tp);
        dao.persist(rango);
    }

    @Override
    public Color getColor(Long idColor) {        
        String hql = "select c from Color c where c.idColor=" + idColor;              
        return dao.findObject(hql);
    }

    @Override
    public TipoComponente getTipoComponente(Short idTipoComponente) {        
        String hql = "select c from TipoComponente c where c.idTipoComponente=" + idTipoComponente;              
        return dao.findObject(hql);
    }

    @Override
    public void saveColor(String usuario, String programa, Long idColor, String descripcion, short rojo, short verde, short azul) {
        Color color = new Color();
        color.setIdColor(idColor);
        color.setUsuario(usuario);
        color.setPrograma(programa);
        Fecha date=new Fecha();                
        color.setFechaModif(date.getFechaSistema());
        color.setDescripcion(descripcion);
        color.setRojo(rojo);
        color.setVerde(verde);
        color.setAzul(azul);
        dao.persist(color);
    }

    @Override
    public boolean existeRango(String idTipo, String descripcion) {       
        String hql="SELECT r FROM RangoClasificacion r "
                 + "WHERE r.tipoComponente.idTipoComponente = "+idTipo
                 + " and r.descripcion = '" + descripcion+"'";
        boolean existe=dao.findObject(hql)!=null?true:false;
        return existe;     
    }

    @Override
    public boolean existeColor(String descripcion, short rojo, short verde, short azul) {
        String hql = "SELECT c FROM Color c "
                + "WHERE c.descripcion ='" + descripcion + "' "
                + "OR (c.rojo="+rojo+" and c.verde="+verde+" and c.azul="+azul+")";
        boolean existe = dao.findObject(hql) != null ? true : false;
        return existe;
    }

    @Override
    public void edit(RangoClasificacion r, String codTipo, long codigoColor, String descripcion) {
        if (codigoColor != 0) 
            r.setColor(getColor(codigoColor));
        if (!codTipo.equals("no")) 
            r.setTipoComponente(getTipoComponente(Short.valueOf(codTipo)));
        if (!descripcion.equals("no"))
            r.setDescripcion(descripcion);
        Fecha date=new Fecha();        
        r.setFechaModif(date.getFechaSistema());
        dao.persist(r);
    }
    
}
    

    