/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Estado;
import com.ag.model.SAtrComponente;
import com.ag.model.SAtrComponenteMedida;
import com.ag.model.SBalances;
import com.ag.model.SComponente;
import com.ag.model.SComponenteMedida;
import com.ag.model.SMedida;
import com.ag.model.SRelComponente;
import com.ag.model.SRelComponenteMedida;
import com.ag.model.SRelComponentePK;
import com.ag.model.SRelComponenteUbicacion;
import com.ag.model.SRelComponenteUbicacionPK;
import com.ag.model.Tbltipo;
import com.ag.model.ZonaGeografica;
import com.ag.model.view.DataBalanceHijo;
import com.ag.model.view.DataRangosBalance;
import com.ag.model.view.DataValue;
import com.ag.model.view.Fecha;
import com.ag.model.view.PadreHijo;
//import com.ag.model.*;
//import com.ag.model.view.*;
import com.ag.service.SBalanceManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author David
 */
@Service("SBalanceManager")
public class SBalanceManagerImpl1 implements SBalanceManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;

    
    @Override
    public List<DataBalanceHijo> cuadroMando(String idComponente, String tipoComponente, String periodo) {
        String hql = "select h.idZona,h.nombre,h.tipoComponente.descripcion,b.porcPerdidaMes, ' ' "
                + " from SBalances b, ZonaGeografica p, ZonaGeografica h "
                + " where b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)"
                + "  and b.sBalancesPK.periodo= " + periodo
                + "  and b.sBalancesPK.idComponente = " + idComponente
                + "  and b.sBalancesPK.idTipoComponente = " + tipoComponente
                + "  and h.idPadre = b.sBalancesPK.idComponente "
                + "  and p.idZona = h.idPadre "
                + "  and p.tipoComponente.idTipoComponente =b.sBalancesPK.idTipoComponente";

        List data = dao.find(hql);
        if (data.isEmpty()) {
            hql = "SELECT r.sComponente.idComponente,  r.sComponente.nombre, r.sComponente.tipoComponente.descripcion,b.porcPerdidaMes,r.sComponente.idCliente "
                    + " FROM SBalances b , SRelComponenteUbicacion r "
                    + " WHERE b.sBalancesPK.periodo= " + periodo
                    + " AND r.zonaGeografica.idZona = " + idComponente
                    + " AND r.zonaGeografica.tipoComponente.idTipoComponente = " + tipoComponente
                    + " AND r.sRelComponenteUbicacionPK.periodoIni <= " + periodo
                    + " AND r.periodoFin       >" + periodo
                    + " AND b.sBalancesPK.idComponente =  r.sComponente.idComponente "
                    + " AND b.sBalancesPK.idTipoComponente =  r.sComponente.tipoComponente.idTipoComponente";
            data = dao.find(hql);
        } else if (data.isEmpty()) {
            hql = "SELECT r.componente1.idComponente,  r.componente1.nombre, r.componente1.tipoComponente.descripcion,b.porcPerdidaMes,r.componente1.idCliente "
                    + "  FROM SBalances b , SRelComponente r "
                    + " WHERE b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)"
                    + "   AND r.sRelComponentePK.idComponente = " + idComponente
                    + "   AND r.sComponente1.tipoComponente.idTipoComponente = " + tipoComponente
                    + "   AND  r.relComponentePK.periodoIni <= " + periodo
                    + "   AND r.periodoFin> " + periodo
                    + "   AND b.sBalancesPK.idComponente =  r.sComponente1.idComponente "
                    + "   AND b.sBalancesPK.idTipoComponente =  r.sComponente1.tipoComponente.idTipoComponente";
            data = dao.find(hql);
        }

        List<DataBalanceHijo> hijos = new ArrayList<DataBalanceHijo>();
        if (data != null) {
            for (Iterator it = data.iterator(); it.hasNext();) {
                Object[] valores = (Object[]) it.next();
                String idComponenteHijo = String.valueOf(valores[0]);
                String nombre = String.valueOf(valores[1]);
                String tipo = String.valueOf(valores[2]);
                String perdidas = String.valueOf(valores[3]);
                String idCliente = String.valueOf(valores[4]);
                DataBalanceHijo hijo = new DataBalanceHijo(idComponenteHijo, nombre, tipo, perdidas, idCliente);
                hijos.add(hijo);
            }
        }
        return hijos;

    }

    /**
     * Devuelve un objeto de tipo Balances para el IdComponente,tipoComponente y periodo pasado por parametro.
     * @param idComponente
     * @param tipoComponente
     * @param periodo
     * @return 
     */
    @Override
    public SBalances getBalances(String idComponente, String tipoComponente, String periodo) {
        String hql = "select b "
                + " from SBalances b "
                + " where b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)"
                + " and b.sBalancesPK.idComponente= " + idComponente
                + " and b.sBalancesPK.idTipoComponente= " + tipoComponente
                + " and b.sBalancesPK.periodo= " + periodo;
        return dao.findObject(hql);
    }

    /**
     * Este metodo devuelve un objeto de tipo DataRangosBalance, que contiene las cantidades de transformadores por cada rango
     * tomando como referencia la zona.
     * @param idZona
     * @param tipoComponente
     * @param tipoArbol
     * @param periodo
     * @return 
     */
    @Override
    public DataRangosBalance getRangosZonas(String idZona, String tipoComponente, String tipoArbol, String periodo) {
        DataRangosBalance rangoBalance = new DataRangosBalance();
        String hql = null;
        if (tipoComponente.equals("4")) {
            hql = " select  b.rangoClasificacion.descripcion, count(distinct b.sBalancesPK.idComponente) "
                    + "  from SBalances b,"
                    + " SRelComponenteUbicacion ru,"
                    + " ZonaGeografica z2,"
                    + " ZonaGeografica z3,"
                    + " ZonaGeografica z4, "
                    + " ZonaGeografica z5, "
                    + " ZonaGeografica z6, "
                    + " ZonaGeografica z7 "
                    + " where b.sBalancesPK.idComponente = ru.sComponente.idComponente "
                    + " and b.sBalancesPK.periodo = " + periodo
                    + " and b.sBalancesPK.idTipoComponente = 8" 
                    + " and ru.sRelComponenteUbicacionPK.idZona= z2.idZona"
                    + " and ru.sRelComponenteUbicacionPK.periodoIni <="+periodo
                    + " and ru.periodoFin > "+periodo
                    + " and z2.idPadre = z3.idZona "
                    + " and z3.idPadre = z4.idZona "
                    + " and z4.idPadre = z5.idZona "
                    + " and z5.idPadre = z6.idZona "
                    + " and z6.idPadre = z7.idZona "
                    + " and z7.idZona = " + idZona
                    + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";           
        } else if (tipoComponente.equals("5") || tipoComponente.equals("0")) {
            hql = " select  b.rangoClasificacion.descripcion, count(distinct b.sBalancesPK.idComponente) "
                    + "  from SBalances b,"
                    + " SRelComponenteUbicacion ru,"
                    + " ZonaGeografica z2,"
                    + " ZonaGeografica z3,"
                    + " ZonaGeografica z4, "
                    + " ZonaGeografica z5, "
                    + " ZonaGeografica z6 "
                    + " where b.sBalancesPK.idComponente = ru.sComponente.idComponente "
                    + " and b.sBalancesPK.periodo = " + periodo
                    + " and b.sBalancesPK.idTipoComponente = 8" 
                    + " and ru.sRelComponenteUbicacionPK.idZona= z2.idZona"
                    + " and ru.sRelComponenteUbicacionPK.periodoIni <="+periodo
                    + " and ru.periodoFin > "+periodo
                    + " and z2.idPadre = z3.idZona "
                    + " and z3.idPadre = z4.idZona "
                    + " and z4.idPadre = z5.idZona "
                    + " and z5.idPadre = z6.idZona "
                    + " and z6.idZona = " + idZona
                    + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";           
        } else if (tipoComponente.equals("12") || tipoComponente.equals("1")) {
            hql = " select  b.rangoClasificacion.descripcion, count(distinct b.sBalancesPK.idComponente) "
                    + "  from SBalances b,"
                    + " SRelComponenteUbicacion ru,"
                    + " ZonaGeografica z2,"
                    + " ZonaGeografica z3,"
                    + " ZonaGeografica z4, "
                    + " ZonaGeografica z5 "
                    + " where b.sBalancesPK.idComponente = ru.sComponente.idComponente "
                    + " and b.sBalancesPK.periodo = " + periodo
                    + " and b.sBalancesPK.idTipoComponente = 8" 
                    + " and ru.sRelComponenteUbicacionPK.idZona= z2.idZona"
                    + " and ru.sRelComponenteUbicacionPK.periodoIni <="+periodo
                    + " and ru.periodoFin > "+periodo
                    + " and z2.idPadre = z3.idZona "
                    + " and z3.idPadre = z4.idZona "
                    + " and z4.idPadre = z5.idZona "
                    + " and z5.idZona = " + idZona
                    + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";           
        } else if (tipoComponente.equals("11") || tipoComponente.equals("6")) {
            hql = " select  b.rangoClasificacion.descripcion, count(distinct b.sBalancesPK.idComponente) "
                    + "  from SBalances b,"
                    + " SRelComponenteUbicacion ru,"
                    + " ZonaGeografica z2,"
                    + " ZonaGeografica z3,"
                    + " ZonaGeografica z4 "
                    + " where b.sBalancesPK.idComponente = ru.sComponente.idComponente "
                    + " and b.sBalancesPK.periodo = " + periodo
                    + " and b.sBalancesPK.idTipoComponente = 8" 
                    + " and ru.sRelComponenteUbicacionPK.idZona= z2.idZona"
                    + " and ru.sRelComponenteUbicacionPK.periodoIni <="+periodo
                    + " and ru.periodoFin > "+periodo
                    + " and z2.idPadre = z3.idZona "
                    + " and z3.idPadre = z4.idZona "
                    + " and z4.idZona = " + idZona
                    + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";           
        } else if (tipoComponente.equals("13") || tipoComponente.equals("2")) {
            hql = " select  b.rangoClasificacion.descripcion, count(distinct b.sBalancesPK.idComponente) "
                    + "  from SBalances b,"
                    + " SRelComponenteUbicacion ru,"
                    + " ZonaGeografica z2,"
                    + " ZonaGeografica z3"
                    + " where b.sBalancesPK.idComponente = ru.sComponente.idComponente "
                    + " and b.sBalancesPK.periodo = " + periodo
                    + " and b.sBalancesPK.idTipoComponente = 8" 
                    + " and ru.sRelComponenteUbicacionPK.idZona= z2.idZona"
                    + " and ru.sRelComponenteUbicacionPK.periodoIni <="+periodo
                    + " and ru.periodoFin > "+periodo
                    + " and z2.idPadre = z3.idZona "
                    + " and z3.idZona = " + idZona
                    + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";           
        } else if (tipoComponente.equals("7") || tipoComponente.equals("3")) {
            hql = " select  b.rangoClasificacion.descripcion, count(*) "
                    + "  from SBalances b,"
                    + " SRelComponenteUbicacion ru"
                    + " where b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)"
                    + " and b.sBalancesPK.idComponente = ru.sComponente.idComponente "
                    + " and b.sBalancesPK.idTipoComponente = 8" 
                    + " and b.sBalancesPK.idTipoComponente = ru.sComponente.tipoComponente.idTipoComponente"
                    + " and b.sBalancesPK.periodo = " + periodo
                    + " and ru.sRelComponenteUbicacionPK.periodoIni <="+periodo
                    + " and ru.periodoFin > "+periodo
                    + " and ru.zonaGeografica.tipoComponente.idTipoComponente = " + tipoComponente
                    + " and ru.zonaGeografica.tipoComponente.tbltipo.tipo =  '" + tipoArbol + "' "
                    + " and ru.zonaGeografica.idZona = " + idZona
                    + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";
        }
        if (hql != null) {
            List data = dao.find(hql);
            if (data != null) {
                for (Iterator it = data.iterator(); it.hasNext();) {
                    Object[] valores = (Object[]) it.next();
                    if (String.valueOf(valores[0]).equalsIgnoreCase("CRITICO")) {
                        rangoBalance.setCritico(String.valueOf(valores[1]));
                    } else if (String.valueOf(valores[0]).equalsIgnoreCase("MEDIO")) {
                        rangoBalance.setMedio(String.valueOf(valores[1]));
                    } else if (String.valueOf(valores[0]).equalsIgnoreCase("BAJO")) {
                        rangoBalance.setBajo(String.valueOf(valores[1]));
                    } else if (String.valueOf(valores[0]).equalsIgnoreCase("NEGATIVO")) {
                        rangoBalance.setNegativos(String.valueOf(valores[1]));
                    } else if (String.valueOf(valores[0]).equalsIgnoreCase("INCONSISTENTE+")) {
                        rangoBalance.setInconsistentesPositivos(String.valueOf(valores[1]));
                    } else if (String.valueOf(valores[0]).equalsIgnoreCase("INCONSISTENTE-")) {
                        rangoBalance.setInconsistentesNegativos(String.valueOf(valores[1]));
                    }else if (String.valueOf(valores[0]).equalsIgnoreCase("SIN BALANCE")) {
                        rangoBalance.setSinBalance(String.valueOf(valores[1]));
                    }
                }
            }
        }
        return rangoBalance;

    }
    
    @Override
    public SMedida getMedida(String idComponente, String periodo, String idTipoCompo){
        String hql = "select m from SMedida m"
                   + " where m.sMedidaPK.idComponente = " + idComponente 
                   + " and m.sMedidaPK.periodo = " + periodo
                   + " and m.sMedidaPK.idTipoComponente = "+idTipoCompo;
        return dao.findObject(hql);
    }
    
    @Override
    public SAtrComponente getAtrComponente(String idComponente){
        String hql = "select a from SAtrComponente a"
                   + " where a.sComponente.idComponente = " + idComponente;
        //SAtrComponente resul=dao.findObject(hql);
        return dao.findObject(hql);
    }
    
    @Override
    public SRelComponenteMedida getRelComponenteMedida(String idComponente,String periodo){
        String hql = "select r from SRelComponenteMedida r "
                   + " where r.sComponente.idComponente = " + idComponente
                   + " and r.sRelComponenteMedidaPK.periodoIni <="+periodo
                   + " and r.periodoFin > " + periodo;
        return dao.findObject(hql);
    }
    
    @Override
    public SAtrComponenteMedida getAtrComponenteMedida(String idComponenteMedida,String periodo){
        SAtrComponenteMedida sacm=new SAtrComponenteMedida();
        String hql = "select a from SAtrComponenteMedida a"
                   + " where a.sComponenteMedida.idComponenteMedida = " + idComponenteMedida
                   + " and a.sAtrComponenteMedidaPK.periodoIni <="+periodo
                   + " and a.periodoFin > " + periodo;
        //SAtrComponenteMedida resul = dao.findObject(hql);       
        if (dao.findObject(hql)!=null) {
            sacm=dao.findObject(hql);
        }
        return sacm;
    }
    
    @Override
    public SAtrComponenteMedida getAtrComponenteMedidaTrafo(String nombre,String periodo){
        String hql = " select a from SAtrComponenteMedida a "
                    + " where a.sComponenteMedida.idComponenteMedida = (select c.idComponenteMedida from SComponenteMedida c"
                                                            + " where c.nombre = '" + nombre + "')" ;
        return dao.findObject(hql);
    }
    
	@Override
    public SComponenteMedida getComponenteMedida(String idComponente,String periodo){
        String hql = " select c from SRelComponenteMedida r, SComponenteMedida c "
                    + " where r.sComponente.idComponente = " + idComponente
                    + " and r.sRelComponenteMedidaPK.periodoIni <="+periodo
                    + " and r.periodoFin > " + periodo
                    + " and c.idComponenteMedida = r.sRelComponenteMedidaPK.idComponenteMedida ";
        //SComponenteMedida resul=dao.findObject(hql);
        return dao.findObject(hql);
    }


	@Override
    public SComponente getComponente(String idComponente){
        String hql =" select c from SComponente c "
                     + " where c.idComponente = " +idComponente;
        return dao.findObject(hql);
    }

    @Override
    public PadreHijo getSubestacionCircuito(String idComponente,String periodo){
        String hql = "select  rcu.zonaGeografica.idZona, rcu.zonaGeografica.nombre, z.idZona, z.nombre"
                   + " from SRelComponenteUbicacion rcu, ZonaGeografica z "
                   + " where rcu.sRelComponenteUbicacionPK.idComponente =  " + idComponente
                   + " and  rcu.sRelComponenteUbicacionPK.periodoIni <= "+periodo
                   + " and  rcu.periodoFin> " + periodo
                   + " and  rcu.zonaGeografica.idPadre = z.idZona " 
                   + " and  rcu.zonaGeografica.tipoComponente.tbltipo.tipo = 'NIV100'";
        Object [] data = dao.findObject(hql);
        PadreHijo ph;
        if(data != null)
            ph = new PadreHijo(String.valueOf(data[0]),String.valueOf(data[1]),String.valueOf(data[2]),String.valueOf(data[3]));
        else 
            ph = new PadreHijo("","","","");
        return ph;
    }

    @Override
    public PadreHijo getZonaMunicipio(String idComponente,String periodo){
        String hql = "select z.idZona, z.nombre, z1.idZona, z1.nombre"
                   + " from SRelComponenteUbicacion rcu, ZonaGeografica z, ZonaGeografica z1 "
                   + " where rcu.sRelComponenteUbicacionPK.idComponente =  " + idComponente
                   + " and  rcu.sRelComponenteUbicacionPK.periodoIni <= "+periodo
                   + " and  rcu.periodoFin> " + periodo
                   + " and  rcu.zonaGeografica.idPadre = z.idZona " 
                   + " and  rcu.zonaGeografica.tipoComponente.tbltipo.tipo = 'NIV200'"
                   + " and  z.idPadre = z1.idZona";
        Object [] data = dao.findObject(hql);
        PadreHijo ph;
        if(data != null)
            ph = new PadreHijo(String.valueOf(data[0]),String.valueOf(data[1]),String.valueOf(data[2]),String.valueOf(data[3]));
        else 
            ph = new PadreHijo("","","","");
        return ph;
    }
    
    @Override
    public List<DataValue> getCantSumXTipoUso(String idComponente,String periodo){
        String hql = " select atr.tbltipo4.tipo,atr.tbltipo4.nombre,count(*) "
                     + " from SRelComponente r, SAtrComponente atr "
                     + " where r.sComponente1.idComponente = " +idComponente
                     + " and atr.sComponente.idComponente =  r.sComponente.idComponente "
                     + " and r.sRelComponentePK.periodoIni <= " + periodo
                     + " and r.periodoFin > " +periodo
                     + " and atr.periodoIni <= " + periodo
                     + " and atr.periodoFin > " + periodo
                     + " group by atr.tbltipo4.tipo,atr.tbltipo4.nombre" ;
        List data =  dao.find(hql);
        List<DataValue> listCombo = new ArrayList();
        if(data!= null)
        for (int i = 0; i < data.size(); i++) {
            Object [] dTu = (Object[]) data.get(i);
            DataValue tu = new DataValue(String.valueOf(dTu[0]),String.valueOf(dTu[1]),String.valueOf(dTu[2]));
            listCombo.add(tu);
        }
        return listCombo;
    }

    
    @Override
    public String getCantSumNoMedidos(String idComponente,String periodo){
        String hql = " select count(*) "
                     + " from SRelComponente r, SAtrComponente atr " 
                     + " where r.sComponente1.idComponente = "  + idComponente
                     + " and atr.sComponente.idComponente =  r.sComponente.idComponente " 
                     + " and r.sRelComponentePK.periodoIni <= " + periodo
                     + " and r.periodoFin > " +periodo 
                     + " and atr.periodoIni <= " + periodo
                     + " and atr.periodoFin > " + periodo
                     + " and atr.tbltipo2.tipo = 'TSU002' "
                     + " group by r.sComponente1.idComponente" ;
        String cantidad =   String.valueOf(dao.findObject(hql)); 
        if(cantidad.equals("null"))
            cantidad = "0";
        return cantidad;
    }


    @Override
     public List<SMedida> getListMedida(String idComponente, int periodo){

         String hql = "select valor from Parametro where idParametro='HISTORICO_MEDIDA'";
         int periodoParam =  Integer.parseInt(String.valueOf(dao.findObject(hql)));
         int periodos = periodo-periodoParam;
         hql ="";
         
         hql = "select m from SMedida m" +
                  " where sMedidaPK.idComponente = " + idComponente
                 + " and  sMedidaPK.periodo  >= " + periodos;


          return dao.find(hql);

    }

    @Override
    public void editAtrComponenteMedida(SAtrComponenteMedida sacm) {
        Fecha fechaSistema=new Fecha();
        sacm.setFechaModif(fechaSistema.getFechaSistema());
        dao.persist(sacm);
    }

    @Override
    public void editMedida(SMedida m) {
        Fecha fechaSistema=new Fecha();
        m.setFechaModif(fechaSistema.getFechaSistema());
        dao.persist(m);
    }

    @Override
    public void editAtrComponente(SAtrComponente sac) {
        Fecha fechaSistema=new Fecha();
        sac.setFechaModif(fechaSistema.getFechaSistema());
        dao.persist(sac);
    }
    
    @Override
    public void editComponente(SComponente c) {
        Fecha fechaSistema=new Fecha();
        c.setFechaModif(fechaSistema.getFechaSistema());
        dao.persist(c);
    }

    @Override
    public List<Tbltipo> getTiposConexionesAll() {
        String hql ="Select t from Tbltipo t where t.tipo like 'TC%' order by t.nombre";
        return dao.find(hql);
    }

    @Override
    public Tbltipo getTipo(String tipo) {
        String hql = "select t from Tbltipo t where t.tipo='" + tipo +"'";              
        return dao.findObject(hql);
    }

    @Override
    public List<Tbltipo> getTiposUsosAll() {
        String hql ="Select t from Tbltipo t where t.tipo like 'TU%' order by t.nombre";
        return dao.find(hql);
    }
    
    @Override
    public SMedida getMedidaActual(String idComponente){
        String hql = "select m from SMedida m" +
                   " where m.sMedidaPK.idComponente=" + idComponente
                 + " and  m.sMedidaPK.periodo = ("
                 + " select max(p.sMedidaPK.periodo) from SMedida p"
                 + " where p.sMedidaPK.idComponente=" + idComponente+ ")";
        return dao.findObject(hql);
    }

    @Override
    public List<ZonaGeografica> getCircuitosOrBarrios(String tipo) {       
        String hql =" select z from ZonaGeografica z where z.tipoComponente.idTipoComponente=" + tipo;
        return dao.find(hql);
    }   
    
    @Override
    public List<SComponente> getTrafos(String tipo) {
        String hql = "select t.sComponente from SRelComponenteUbicacion t " 
                    +"where t.sComponente.tipoComponente.idTipoComponente=8 "
                    +"and t.zonaGeografica.tipoComponente=" + tipo;
        return dao.find(hql);
    }    

    @Override
    public void movTrafo(String usuario, String programa, SComponente sc, ZonaGeografica z, String idZonaAnt) {
        String hql="SELECT max(b.sBalancesPK.periodo) FROM SBalances b"
                + " where b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)";
        String periodo=dao.findObject(hql).toString();
        quitarAmarreTrafo(sc.getIdComponente().toString(), idZonaAnt, periodo);
        
        sc.setZonaGeografica(z);
        editComponente(sc);        
        
        SRelComponenteUbicacion newAmarre=new SRelComponenteUbicacion();
        newAmarre.setUsuario(usuario);
        newAmarre.setPrograma(programa);       
        Fecha fechaSistema=new Fecha();
        newAmarre.setFechaModif(fechaSistema.getFechaSistema());
        newAmarre.setSComponente(sc);
        newAmarre.setZonaGeografica(z);
        newAmarre.setPeriodoFin(999912);           
       // BigInteger idZona=z.getIdZona().toBigInteger();
        SRelComponenteUbicacionPK newAmarrePK=new SRelComponenteUbicacionPK(z.getIdZona().toBigInteger(),sc.getIdComponente().toBigInteger(), Integer.valueOf(periodo));
        newAmarre.setSRelComponenteUbicacionPK(newAmarrePK);
        
        dao.persist(newAmarre);
    }

    @Override
    public ZonaGeografica getZonaGeografica(String idZona) {
        String hql = "select z from ZonaGeografica z where z.idZona='" + idZona +"'";              
        return dao.findObject(hql);
    } 
    
        //11-07-14 ptorresINI
    @Override
    public ZonaGeografica getZonaGeografica(String idZona, String tipo) {
         String hql="SELECT z FROM RelComponenteUbicacion r, ZonaGeografica z "
                  + "WHERE r.relComponenteUbicacionPK.idZona=z.idZona "
                        + "and r.periodoFin=999912 and z.tipoComponente.idTipoComponente="+tipo
                        + " and r.relComponenteUbicacionPK.idZona="+idZona;
         return dao.findObject(hql);
    }
    
    @Override
    public ZonaGeografica getAreaMt(String idZona, String tipo) {
         String hql="SELECT z FROM ZonaGeografica z "
                  + "WHERE z.tipoComponente.idTipoComponente="+tipo
                        + " and z.idZona="+idZona;
         return dao.findObject(hql);
    }
    //11-07-14 ptorresFIN

    @Override
    public void movSuministro(String usuario, String programa, SComponente suministro, SComponente trafo, String idTrafoAnt) {
        String hql="SELECT max(b.sBalancesPK.periodo) FROM SBalances b"
                + " where b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)";        
        String periodo=dao.findObject(hql).toString();
        quitarAmarreSuministro(idTrafoAnt, suministro.getIdComponente().toString(), periodo);
        
        SRelComponente newAmarre=new SRelComponente();
        newAmarre.setUsuario(usuario);
        newAmarre.setPrograma(programa);       
        Fecha fechaSistema=new Fecha();
        newAmarre.setFechaModif(fechaSistema.getFechaSistema());
        newAmarre.setSComponente(suministro);
        newAmarre.setSComponente1(trafo);
        newAmarre.setPeriodoFin(999912); 
        newAmarre.setEstado(trafo.getEstado()); //estado del id_padre (trafo)
        newAmarre.setEstado1(suministro.getEstado()); //estado del id_Hijo (suministro)
        Estado est=getEstado("AC001");
        newAmarre.setEstado2(est);
       // BigInteger idZona=z.getIdZona().toBigInteger();
        SRelComponentePK newAmarrePK=new SRelComponentePK(trafo.getIdComponente().toBigInteger(), suministro.getIdComponente().toBigInteger(), Integer.valueOf(periodo));
        newAmarre.setSRelComponentePK(newAmarrePK);
        
        dao.persist(newAmarre);
    }
    
    public Estado getEstado(String id){       
        String hql = "SELECT e FROM Estado e WHERE e.idEstado='" + id + "'" ;              
        return dao.findObject(hql);
    }
    
    public void quitarAmarreSuministro(String idTrafo, String idSuministro, String periodoIni){
        String hql =  "SELECT s FROM SRelComponente s"
                    + " WHERE s.sRelComponentePK.idComponente =" + idTrafo
                    + " and s.sRelComponentePK.idComponenteHijo =" + idSuministro
                    + " and s.periodoFin =999912";
        SRelComponente amarre= dao.findObject(hql);       
        if (amarre!=null) {
            amarre.setPeriodoFin(Integer.parseInt(periodoIni));
            Fecha fechaSistema=new Fecha();
            amarre.setFechaModif(fechaSistema.getFechaSistema());
            amarre.setEstado(getEstado("AC002"));
            dao.persist(amarre);
        }
        
    }
    
    public void quitarAmarreTrafo(String idComponente, String idZona, String periodoIni){
        String hql =  "SELECT s FROM SRelComponenteUbicacion s"
                    + " WHERE s.sRelComponenteUbicacionPK.idComponente =" + idComponente
                    + " and s.sRelComponenteUbicacionPK.idZona =" + idZona
                    + " and s.sRelComponenteUbicacionPK.periodoIni =" + periodoIni;
        SRelComponenteUbicacion amarre= dao.findObject(hql);
        if (amarre!=null) {
            amarre.setPeriodoFin(Integer.parseInt(periodoIni));
            Fecha fechaSistema=new Fecha();
            amarre.setFechaModif(fechaSistema.getFechaSistema());
            dao.persist(amarre);  
        }
        
    }

    @Override
    public SComponente getTrafoBySuministro(String idSuministro) {
        String hql = "SELECT s.sComponente1 FROM SRelComponente s "+ 
                     "WHERE s.periodoFin = 999912 "+
                     "and s.sRelComponentePK.idComponenteHijo ="+ idSuministro;              
        return dao.findObject(hql);
    }

    @Override
    public ZonaGeografica getZonaByTrafo(String idTrafo, String tipo) {
        String hql = "SELECT s.zonaGeografica FROM SRelComponenteUbicacion s "+ 
                     "WHERE s.periodoFin = 999912 "+
                     "and s.sRelComponenteUbicacionPK.idComponente ="+ idTrafo +
                     " and s.zonaGeografica.tipoComponente =" + tipo;              
        return dao.findObject(hql);
    }

    @Override
    public String getIdComponenteByIdCliente(String idCliente) {
        String hql="SELECT s.idComponente FROM SComponente s "
                 + "WHERE s.idCliente ='"+idCliente+"'";  
        return dao.findObject(hql).toString();
    }

    @Override
    public void actConsumoTrafo(String usuario, String programa, String idCliente, String consumoFacturado, String diasFacturado, String consumoAdicional) {
        String idComponente=getIdComponenteByIdCliente(idCliente);
        String hql="SELECT max(b.sBalancesPK.periodo) FROM SBalances b"
                + " where b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)";        
        String periodo=dao.findObject(hql).toString();
        SMedida m = getMedida(idComponente, periodo, "8");
        if (m!=null) {
            m.setUsuario(usuario);
            m.setPrograma(programa);
            m.setConsumoFacturado(new BigDecimal(consumoFacturado));
            m.setDiasFacturados(new Short(diasFacturado));
            m.setConsumoAdicional(new BigDecimal(consumoAdicional));
            Fecha fechaModif = new Fecha();
            m.setFechaModif(fechaModif.getFecha());
            dao.persist(m);
        }
    }

    @Override
    public void actConsumoSumin(String usuario, String programa, String idCliente, String consumoFacturado, String diasFacturado) {
        String idComponente=getIdComponenteByIdCliente(idCliente);
        String hql="SELECT max(b.sBalancesPK.periodo) FROM SBalances b"
                + " where b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)";        
        String periodo=dao.findObject(hql).toString();
        SMedida m = getMedida(idComponente, periodo, "9");
        if (m!=null) {
            m.setUsuario(usuario);
            m.setPrograma(programa);
            m.setConsumoFacturado(new BigDecimal(consumoFacturado));
            m.setDiasFacturados(new Short(diasFacturado));           
            Fecha fechaModif = new Fecha();
            m.setFechaModif(fechaModif.getFecha());
            dao.persist(m);
        }
    }

    @Override
    public ZonaGeografica getPadreByTipo(String idComponente, String tipo) {
         String hql="SELECT z FROM SRelComponenteUbicacion r, ZonaGeografica z "
                  + "WHERE r.sRelComponenteUbicacionPK.idZona=z.idZona "
                        + "and r.periodoFin=999912 and z.tipoComponente.idTipoComponente="+tipo
                        + " and r.sRelComponenteUbicacionPK.idComponente="+idComponente;
         return dao.findObject(hql);
    }

    @Override
    public int getNumMesesAtras() {
        String hql ="SELECT p.valor FROM Parametro p WHERE p.idParametro ='MESES_ATRAS'";
        return Integer.valueOf(dao.findObject(hql).toString());
    }

    @Override
    public List<SComponente> getTrafosByRango(String id, String tipo, String periodo, String descripcion) {
        String hql="";        
        if (tipo.equals("4")) { //Empresa Geografica
            hql = " SELECT r.sComponente FROM SRelComponenteUbicacion r " 
                    + " WHERE r.periodoFin> "+ periodo +" and " 
                    + " r.sRelComponenteUbicacionPK.idZona in (SELECT z1.idZona " 
                    + " FROM ZonaGeografica z1, "
                    + " ZonaGeografica z2, "
                    + " ZonaGeografica z3, "
                    + " ZonaGeografica z4, "
                    + " ZonaGeografica z5, "
                    + " ZonaGeografica z6 " 
                    + " WHERE z6.idZona = z5.idPadre "
                    + " and z5.idZona = z4.idPadre "
                    + " and z4.idZona = z3.idPadre " 
                    + " and z3.idZona = z2.idPadre " 
                    + " and z2.idZona = z1.idPadre " 
                    + " and z6.idZona = "+id+")";             
        } else if (tipo.equals("5") || tipo.equals("0")) { //depto geografica o Empresa electrica
              hql = " SELECT r.sComponente FROM SRelComponenteUbicacion r " 
                    + " WHERE r.periodoFin> "+ periodo +" and " 
                    + " r.sRelComponenteUbicacionPK.idZona in (SELECT z1.idZona " 
                    + " FROM ZonaGeografica z1, "
                    + " ZonaGeografica z2, "
                    + " ZonaGeografica z3, "
                    + " ZonaGeografica z4, "
                    + " ZonaGeografica z5 "
                    + " WHERE z5.idZona = z4.idPadre "
                    + " and z4.idZona = z3.idPadre " 
                    + " and z3.idZona = z2.idPadre " 
                    + " and z2.idZona = z1.idPadre " 
                    + " and z5.idZona = "+id+")";
           } else if (tipo.equals("12") || tipo.equals("1")) { //Delegacion geografica o Zona electrica
              hql = " SELECT r.sComponente FROM SRelComponenteUbicacion r " 
                    + " WHERE r.periodoFin> "+ periodo +" and " 
                    + " r.sRelComponenteUbicacionPK.idZona in (SELECT z1.idZona " 
                    + " FROM ZonaGeografica z1, "
                    + " ZonaGeografica z2, "
                    + " ZonaGeografica z3, "
                    + " ZonaGeografica z4 "
                    + " WHERE z4.idZona = z3.idPadre " 
                    + " and z3.idZona = z2.idPadre " 
                    + " and z2.idZona = z1.idPadre " 
                    + " and z4.idZona = "+id+")";
           } else if (tipo.equals("11") || tipo.equals("6")) { //Delegacion electrica o Municipio geografica
                 hql = " SELECT r.sComponente FROM SRelComponenteUbicacion r " 
                    + " WHERE r.periodoFin> "+ periodo +" and " 
                    + " r.sRelComponenteUbicacionPK.idZona in (SELECT z1.idZona " 
                    + " FROM ZonaGeografica z1, "
                    + " ZonaGeografica z2, "
                    + " ZonaGeografica z3 "
                    + " WHERE z3.idZona = z2.idPadre " 
                    + " and z2.idZona = z1.idPadre " 
                    + " and z3.idZona = "+id+")";
           } else if (tipo.equals("13") || tipo.equals("2")) { //Poblacion geografica o Subestacion(Area MT) electrica
                 hql = " SELECT r.sComponente FROM SRelComponenteUbicacion r " 
                    + " WHERE r.periodoFin> "+ periodo +" and " 
                    + " r.sRelComponenteUbicacionPK.idZona in (SELECT z1.idZona " 
                    + " FROM ZonaGeografica z1, "
                    + " ZonaGeografica z2 "
                    + " WHERE z2.idZona = z1.idPadre " 
                    + " and z2.idZona = "+id+")";
           } else if (tipo.equals("7") || tipo.equals("3")) { // Barrio o Circuito             
                  hql ="SELECT r.sComponente FROM SRelComponenteUbicacion r "
                   +"WHERE r.periodoFin>"+periodo+" and "
                          + "r.sRelComponenteUbicacionPK.idZona="+id;
           }
        
         String hql2 ="select c from SComponente c "
                    + "where c.tipoComponente.idTipoComponente=8 and "
                          + "c.idComponente in (select b.sBalancesPK.idComponente "
                                             + "from SBalances b, RangoClasificacion r "
                                             + "where b.sBalancesPK.idTipoComponente=8 and b.rangoClasificacion.idRango=r.idRango "
                                             + "and r.descripcion='"+descripcion+"' "
                                             + "and b.sBalancesPK.periodo="+periodo+" "
                                             + "and b.sBalancesPK.idComponente in ("+hql+"))";
         return dao.find(hql2);
    }
}
