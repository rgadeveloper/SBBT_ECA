/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service.impl;
 
import com.ag.dao.Dao;
import com.ag.model.AtrComponente;
import com.ag.model.Balances;
import com.ag.model.Campania;
import com.ag.model.Componente;
import com.ag.model.OrdenTrabajo;
import com.ag.model.Tbltipo;
import com.ag.model.ZonaGeografica;
import com.ag.model.view.ResultCampania;
import com.ag.model.view.ResultOrdenTrabajo;
import com.ag.model.view.Trafo;
import com.ag.model.view.ordenesDeTrabajo;
import com.ag.service.ReporteManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Larry
 */
@Service("ReporteManager")
public class ReporteManagerImpl implements ReporteManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;

    @Override
    public List<ZonaGeografica> getZonasByTipo(String tipo) {

        String hql = "Select z from ZonaGeografica z "
                + "where z.estado='AC001' and "
                + "z.tipoComponente.idTipoComponente=" + tipo
                + " order by z.idComercial";
        return dao.find(hql);
    }

    @Override
    public List<Componente> getTrafosByEmpresa(String id) {
        String hql = "SELECT r.componente FROM RelComponenteUbicacion r "
                + "WHERE r.periodoFin=999912 and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + ")))";
        return dao.find(hql);
    }

    @Override
    public List<Componente> getTrafosByZona(String id) {
        String hql = "SELECT r.componente FROM RelComponenteUbicacion r "
                + "WHERE r.periodoFin=999912 and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + "))";
        return dao.find(hql);
    }

    @Override
    public List<Componente> getTrafosByCircuitoOrBarrio(String id) {
        String hql = "SELECT r.componente FROM RelComponenteUbicacion r "
                + "WHERE r.periodoFin=999912 and r.relComponenteUbicacionPK.idZona=" + id;
        return dao.find(hql);
    }

    @Override
    public List<Componente> getTrafosBySubestacion(String id) {
        String hql = "SELECT r.componente FROM RelComponenteUbicacion r "
                + "WHERE r.periodoFin=999912 and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idZona in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + "))";
        return dao.find(hql);
    }

    @Override
    public List<Componente> getTrafosByVista(String vista) {
        String hql = "SELECT rcu.componente "
                + "FROM RelComponenteUbicacion rcu "
                + "WHERE rcu.periodoFin=999912 and "
                + "rcu.componente.tipoComponente.idTipoComponente=8 and "
                + "rcu.zonaGeografica.tipoComponente.tbltipo.tipo='" + vista + "'";
        return dao.find(hql);
    }

    @Override
    public List<Componente> getTrafo(String id) {
        String hql = "SELECT c FROM Componente c WHERE c.idComponente=" + id;
        return dao.find(hql);
    }
    
    @Override
    public List<Componente> getTrafoByNombre(String nombre) {
        String hql = "SELECT c FROM Componente c WHERE c.nombre='" + nombre + "'";
        return dao.find(hql);
    }

    @Override
    public Balances getBalanceByPeriodo(String idComponente, int periodo) {
        String hql = "select max(b.balancesPK.periodo)"
                + " from Balances b"
                + " where b.balancesPK.idTipoComponente=8 and"
                + " b.balancesPK.idComponente=" + idComponente;
        String periodoObjetivo = dao.findObject(hql).toString();
        int tam = periodoObjetivo.length();
        String mes = periodoObjetivo.substring(tam - 2);
        String ano = periodoObjetivo.substring(0, tam - 2);
        String[] periodos = new String[12];
        for (int i = 0; i < 12; i++) {
            if (mes.equals("00")) {
                mes = "12";
                ano = String.valueOf(Integer.parseInt(ano) - 1);
            }
            periodos[i] = ano + mes;
            int m = Integer.parseInt(mes) - 1;
            mes = (m > 9) ? String.valueOf(m)
                    : "0" + String.valueOf(m);
        }

        hql = "SELECT b FROM Balances b "
                + "WHERE b.balancesPK.idTipoComponente=8 and b.balancesPK.idComponente=" + idComponente
                + " and b.balancesPK.periodo =" + periodos[periodo];

        return dao.findObject(hql);
    }

    @Override
    public Balances getBalanceByPeriodo(String idComponente, String periodo, String tipo) {
        String hql = "SELECT b FROM Balances b "
                + "WHERE b.balancesPK.idTipoComponente=" + tipo + " and b.balancesPK.idComponente=" + idComponente
                + " and b.balancesPK.periodo =" + periodo;

        return dao.findObject(hql);
    }

    @Override
    public String getRutaReportes() {
        String hql = "SELECT p.valor FROM Parametro p WHERE p.idParametro ='RUTA_REPORTE'";
        return dao.findObject(hql).toString();
    }

    @Override
    public String getRutaLogoForReportes() {
        String hql = "SELECT p.valor FROM Parametro p WHERE p.idParametro ='RUTA_LOGO_REPORTES'";
        return dao.findObject(hql).toString();
    }

    /*@Override
     public String[] getPeriodosByTrafos(List<Componente> trafos) {
     String periodoObjetivo="000000";
     Iterator iter = trafos.iterator();        
     while (iter.hasNext()){
     Componente c = (Componente) iter.next();
     String hql = "select max(b.balancesPK.periodo)"
     +" from Balances b"
     +" where b.balancesPK.idTipoComponente=8 and"
     + " b.balancesPK.idComponente = "+c.getIdComponente();
     periodoObjetivo=dao.findObject(hql).toString();
     /*if (periodoAct.compareTo(periodoObjetivo)>0)
     periodoObjetivo=periodoAct;
     } 
        
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
        
     return periodos;
     }*/
    @Override
    public String[] getPeriodoMax() {
        String hql = "select max(b.balancesPK.periodo)"
                + " from Balances b"
                + " where b.balancesPK.idTipoComponente=8";

        String periodoObjetivo = dao.findObject(hql).toString();
        int tam = periodoObjetivo.length();
        String mes = periodoObjetivo.substring(tam - 2);
        String ano = periodoObjetivo.substring(0, tam - 2);
        String[] periodos = new String[12];
        for (int i = 0; i < 12; i++) {
            if (mes.equals("00")) {
                mes = "12";
                ano = String.valueOf(Integer.parseInt(ano) - 1);
            }
            periodos[i] = ano + mes;
            int m = Integer.parseInt(mes) - 1;
            mes = (m > 9) ? String.valueOf(m)
                    : "0" + String.valueOf(m);
        }

        return periodos;
    }

    //OPERATIVO
    @Override
    public List<Tbltipo> getActividades() {
        String hql = "select t "
                + " from Tbltipo t"
                + " where t.grupo.codigo='TAA000'";
        return dao.find(hql);
    }

    @Override
    public List<Campania> getCampanias(String fechaIni, String fechaFin, String tipoActividad) {
        String hql = "SELECT c FROM Campania c "
                + "where trunc(c.fechaInicio)>='" + fechaIni + "' "
                + "and trunc(c.fechaInicio)<='" + fechaFin + "' "
                + "and c.tbltipo.tipo=" + tipoActividad;
        return dao.find(hql);
    }

    @Override
    public List<ResultCampania> getResultCampanias(List<Campania> campanias) {
        List<ResultCampania> resultCampanias = new ArrayList<ResultCampania>();
        if (!campanias.isEmpty()) {
            for (Iterator it = campanias.iterator(); it.hasNext();) {
                Campania c = (Campania) it.next();
                String idCampania = c.getIdCampania().toString();
                String nombre = c.getDescripcion();
                String periodo = String.valueOf(c.getPeriodo());
                Tbltipo tipo = c.getTbltipo();
                ResultCampania rc = new ResultCampania(idCampania, nombre, periodo, tipo);
                //demas valores de los query de Larry 
                if (tipo.getTipo().equals("7526")) {//Revisión/Normalización Macro
                    rc.setCantMacrosTotal(cantidadDeMacros("Total", idCampania));
                    rc.setCantMacrosNormales(cantidadDeMacros("Normales", idCampania));
                    rc.setCantMacrosRevisados(cantidadDeMacros("Revisados", idCampania));
                    rc.setCantMacrosNormalizados(cantidadDeMacros("Normalizados", idCampania));
                    rc.setCantMacrosIrregularidad(cantidadDeMacros("Irregularidad", idCampania));
                    rc.setPorcEfectividadMacros(rc.getCantMacrosTotal() != 0
                            ? (rc.getCantMacrosRevisados() / rc.getCantMacrosTotal()) * 100
                            : 0);
                } else {//Revisión del usuario               
                    rc.setCantClientes(cantidadDeClientes("Total", idCampania));
                    rc.setCantClientesNormales(cantidadDeClientes("Normales", idCampania));
                    rc.setCantClientesRevisados(cantidadDeClientes("Revisados", idCampania));
                    rc.setCantClientesNormalizados(cantidadDeClientes("Normalizados", idCampania));
                    rc.setCantClientesIrregularidad(cantidadDeClientes("Irregularidad", idCampania));
                    /*double porcMacro=rc.getCantMacrosTotal()!=0?
                     (rc.getCantMacrosRevisados()/rc.getCantMacrosTotal())*100:
                     0;*/
                    rc.setPorcEfectividadClientes(rc.getCantMacrosTotal() != 0
                            ? (rc.getCantMacrosRevisados() / rc.getCantMacrosTotal()) * 100
                            : 0);
                }
                resultCampanias.add(rc);
            }
        }
        return resultCampanias;
    }

    @Override
    public List<ResultCampania> getResultCampanias(String idCampania) {
        List<ResultCampania> resultCampanias = new ArrayList<ResultCampania>();
        String hql = "SELECT c FROM Campania c WHERE c.idCampania = " + idCampania;
        Campania c = dao.findObject(hql);
        if (c != null) {
            String nombre = c.getDescripcion();
            String periodo = String.valueOf(c.getPeriodo());
            Tbltipo tipo = c.getTbltipo();
            ResultCampania rc = new ResultCampania(idCampania, nombre, periodo, tipo);
            //demas valores de los query de Larry 
            if (tipo.getTipo().equals("7526")) {//Revisión/Normalización Macro
                rc.setCantMacrosTotal(cantidadDeMacros("Total", idCampania));
                rc.setCantMacrosNormales(cantidadDeMacros("Normales", idCampania));
                rc.setCantMacrosRevisados(cantidadDeMacros("Revisados", idCampania));
                rc.setCantMacrosNormalizados(cantidadDeMacros("Normalizados", idCampania));
                rc.setCantMacrosIrregularidad(cantidadDeMacros("Irregularidad", idCampania));
                rc.setPorcEfectividadMacros(rc.getCantMacrosTotal() != 0
                        ? (rc.getCantMacrosRevisados() / rc.getCantMacrosTotal()) * 100
                        : 0);
                rc.setMostrarValoresMacros(true);
            } else {//Revisión del usuario               
                rc.setCantClientes(cantidadDeClientes("Total", idCampania));
                rc.setCantClientesNormales(cantidadDeClientes("Normales", idCampania));
                rc.setCantClientesRevisados(cantidadDeClientes("Revisados", idCampania));
                rc.setCantClientesNormalizados(cantidadDeClientes("Normalizados", idCampania));
                rc.setCantClientesIrregularidad(cantidadDeClientes("Irregularidad", idCampania));
                rc.setPorcEfectividadClientes(rc.getCantMacrosTotal() != 0
                        ? (rc.getCantMacrosRevisados() / rc.getCantMacrosTotal()) * 100
                        : 0);
                rc.setMostrarValoresClientes(true);
            }
            resultCampanias.add(rc);
        }
        return resultCampanias;
    }

    @Override
    public List<ResultOrdenTrabajo> getResultOrdenesTrabajo(List<Campania> campanias, String filtro, List<ordenesDeTrabajo> resultOrdenesTrabajoForJasper) {
        List<ResultOrdenTrabajo> resultOrdenTrabajo = new ArrayList<ResultOrdenTrabajo>();
        if (!campanias.isEmpty()) {
            for (Iterator it = campanias.iterator(); it.hasNext();) {
                Campania c = (Campania) it.next();
                String idCampania = c.getIdCampania().toString();
                String nombre = c.getDescripcion();
                String periodo = String.valueOf(c.getPeriodo());
                Tbltipo tipo = c.getTbltipo();
                //buscamos todos las ordenes de trabajos y creamos los objetos
                List<OrdenTrabajo> ordenesTrabajo = ordenesTrabajoByCampania(idCampania, filtro);
                if (!ordenesTrabajo.isEmpty()) {
                    resultOrdenesTrabajoForJasper.add(new ordenesDeTrabajo(idCampania, nombre, periodo, tipo, ordenesTrabajo));
                    for (Iterator itO = ordenesTrabajo.iterator(); itO.hasNext();) {
                        OrdenTrabajo o = (OrdenTrabajo) itO.next();
                        ResultOrdenTrabajo rot = new ResultOrdenTrabajo(idCampania, nombre, periodo, tipo, o);
                        resultOrdenTrabajo.add(rot);
                    }
                }
            }
        }
        return resultOrdenTrabajo;
    }

    @Override
    public List<ResultOrdenTrabajo> getResultOrdenesTrabajo(String idCampania, String filtro, List<ordenesDeTrabajo> resultOrdenesTrabajoForJasper) {
        List<ResultOrdenTrabajo> resultOrdenTrabajo = new ArrayList<ResultOrdenTrabajo>();
        String hql = "SELECT c FROM Campania c WHERE c.idCampania = " + idCampania;
        Campania c = dao.findObject(hql);
        if (c != null) {
            String nombre = c.getDescripcion();
            String periodo = String.valueOf(c.getPeriodo());
            Tbltipo tipo = c.getTbltipo();
            List<OrdenTrabajo> ordenesTrabajo = ordenesTrabajoByCampania(idCampania, filtro);
            if (!ordenesTrabajo.isEmpty()) {
                resultOrdenesTrabajoForJasper.add(new ordenesDeTrabajo(idCampania, nombre, periodo, tipo, ordenesTrabajo));
                for (Iterator itO = ordenesTrabajo.iterator(); itO.hasNext();) {
                    OrdenTrabajo o = (OrdenTrabajo) itO.next();
                    ResultOrdenTrabajo rot = new ResultOrdenTrabajo(idCampania, nombre, periodo, tipo, o);
                    resultOrdenTrabajo.add(rot);
                }
            }
        }
        return resultOrdenTrabajo;
    }

    public double cantidadDeMacros(String con, String idCampania) {
        double result;
        String hql;

        if (con.equals("Total")) {
            hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                    + " and o.tipoComponente=8";
            result = Double.valueOf(dao.findObject(hql).toString());
        } else {
            if (con.equals("Irregularidad")) {
                hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                        + " AND o.irregularidad='S' and o.tipoComponente=8";
                result = Double.valueOf(dao.findObject(hql).toString());
            } else {
                if (con.equals("Normales")) {
                    hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                            + " AND o.irregularidad='N' and o.tipoComponente=8";
                    result = Double.valueOf(dao.findObject(hql).toString());
                } else {
                    if (con.equals("Normalizados")) {
                        hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                                + " AND o.normalizado='S' and o.tipoComponente=8";
                        result = Double.valueOf(dao.findObject(hql).toString());
                    } else {
                        result = 0;
                    }
                }
            }
        }

        return result;
    }

    public List<OrdenTrabajo> ordenesTrabajoByCampania(String idCampania, String filtro) {
        String hql = "SELECT o FROM OrdenTrabajo o "
                + "WHERE o.campania.idCampania = " + idCampania;

        if (filtro.equals("Irregularidad")) {
            hql = hql + " And o.irregularidad='S'";
        }

        if (filtro.equals("SinResolver")) {
            hql += " And o.estado='ESO001'";
        }

        return dao.find(hql);
    }

    public double cantidadDeClientes(String con, String idCampania) {
        double result;
        String hql;

        if (con.equals("Total")) {
            hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                    + " and o.tipoComponente=9";
            result = Double.valueOf(dao.findObject(hql).toString());
        } else {
            if (con.equals("Irregularidad")) {
                hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                        + " AND o.irregularidad='S' and o.tipoComponente=9";
                result = Double.valueOf(dao.findObject(hql).toString());
            } else {
                if (con.equals("Normales")) {
                    hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                            + " AND o.irregularidad='N' and o.tipoComponente=9";
                    result = Double.valueOf(dao.findObject(hql).toString());
                } else {
                    if (con.equals("Normalizados")) {
                        hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                                + " AND o.normalizado='S' and o.tipoComponente=9";
                        result = Double.valueOf(dao.findObject(hql).toString());
                    } else {
                        result = 0;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<Trafo> getTrafosByPeriodo(String periodo) {
        List<Trafo> trafosInconsistentes = new ArrayList<Trafo>();

        String hql = "SELECT c, b "
                + "FROM Componente c, Balances b "
                + "WHERE c.idComponente=b.balancesPK.idComponente and"
                + "      b.balancesPK.idTipoComponente=8 and"
                + "      b.balancesPK.periodo=" + periodo;

        List data = dao.find(hql);
        if (data != null) {
            for (Iterator it = data.iterator(); it.hasNext();) {
                Object[] valores = (Object[]) it.next();
                Componente c = (Componente) valores[0];
                Balances b = (Balances) valores[1];
                trafosInconsistentes.add(new Trafo(c, b));
            }
        }

        return trafosInconsistentes;
    }

    @Override
    public List<Trafo> getTrafosInconsistentes(String periodo) {
        List<Trafo> trafosInconsistentes = new ArrayList<Trafo>();

        /*String hql = "select c from Componente c "
         + "where c.idComponente in (select b.balancesPK.idComponente "
         + "from Balances b, RangoClasificacion r "
         + "where b.balancesPK.idTipoComponente=8 and b.rangoClasificacion.idRango=r.idRango and "
         + "(r.descripcion='NEGATIVO' or r.descripcion='SIN BALANCE' or r.descripcion='INCONSISTENTE') "
         + "and b.balancesPK.periodo=(select max(b.balancesPK.periodo) "
         + "from Balances b "
         + "where b.balancesPK.idTipoComponente=8) ))";*/
        String hql = "SELECT c, b "
                + "FROM Componente c, Balances b "
                + "WHERE c.idComponente=b.balancesPK.idComponente and"
                + "      b.balancesPK.idTipoComponente=8 and"
                + "      b.rangoClasificacion.descripcion in ('NEGATIVO','SIN BALANCE', 'INCONSISTENTE') and"
                + "      b.balancesPK.periodo=" + periodo;

        List data = dao.find(hql);
        if (data != null) {
            for (Iterator it = data.iterator(); it.hasNext();) {
                Object[] valores = (Object[]) it.next();
                Componente c = (Componente) valores[0];
                Balances b = (Balances) valores[1];
                trafosInconsistentes.add(new Trafo(c, b));
            }
        }

        /*if (!dao.find(hql).isEmpty()){
         List<Componente> trafos = dao.find(hql);
         for (Iterator it = trafos.iterator(); it.hasNext();) {
         Componente t = (Componente) it.next();  
         List<Componente> suministros = getSuministros(t.getIdComponente().toString());
         Trafo i = new Trafo(t, suministros);
         trafosInconsistentes.add(i);
         }
         }*/
        return trafosInconsistentes;
    }

    public List<Componente> getSuministros(String idComponente) {
        String hql = "select rc.componente from RelComponente rc "
                + "where rc.periodoFin=999912 and "
                + "rc.componente1.idComponente= " + idComponente;
        return dao.find(hql);
    }

    @Override
    public Componente getComponente(String idComponente) {
        String hql = "SELECT c FROM Componente c WHERE c.idComponente =" + idComponente;
        return dao.findObject(hql);
    }

    @Override
    public ZonaGeografica getZona(String idZona, String tipo) {
        String hql = "SELECT z FROM ZonaGeografica z WHERE z.idZona = " + idZona
                + " and z.tipoComponente.idTipoComponente = " + tipo;
        return dao.findObject(hql);
    }

    @Override
    public ZonaGeografica getZona(String idZona) {
        String hql = "SELECT z FROM ZonaGeografica z WHERE z.idZona = " + idZona;
        return dao.findObject(hql);
    }

    @Override
    public List<String> getPeriodos() {
        String hql = "SELECT distinct(b.balancesPK.periodo) FROM Balances b"
                + " order by b.balancesPK.periodo desc";
        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getTrafosAtrByEmpresa(String id) {
        String hql = "SELECT atr FROM RelComponenteUbicacion r, AtrComponente atr "
                + "WHERE r.periodoFin=999912 and "
                + "atr.componente.idComponente=r.relComponenteUbicacionPK.idComponente and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + ")))";
        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getTrafosAtrByZona(String id) {
        String hql = "SELECT atr FROM RelComponenteUbicacion r, AtrComponente atr "
                + "WHERE r.periodoFin=999912 and "
                + "atr.componente.idComponente=r.relComponenteUbicacionPK.idComponente and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + "))";
        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getTrafosAtrByCircuitoOrBarrio(String id) {
        String hql = "SELECT atr FROM RelComponenteUbicacion r, AtrComponente atr "
                + "WHERE r.periodoFin=999912 and "
                + "atr.componente.idComponente=r.relComponenteUbicacionPK.idComponente and "
                + "r.relComponenteUbicacionPK.idZona=" + id;
        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getTrafosAtrBySubestacion(String id) {
        String hql = "SELECT atr FROM RelComponenteUbicacion r, AtrComponente atr "
                + "WHERE r.periodoFin=999912 and "
                + "atr.componente.idComponente=r.relComponenteUbicacionPK.idComponente and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idZona in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + "))";
        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getTrafosAtrByVista(String vista) {
        String hql = "SELECT atr "
                + "FROM RelComponenteUbicacion rcu, AtrComponente atr  "
                + "WHERE rcu.periodoFin=999912 and "
                + "atr.componente.idComponente=rcu.relComponenteUbicacionPK.idComponente and "
                + "rcu.componente.tipoComponente.idTipoComponente=8 and "
                + "rcu.zonaGeografica.tipoComponente.tbltipo.tipo='" + vista + "'";
        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getTrafoAtr(String id) {
        String hql = "SELECT atr FROM AtrComponente atr WHERE atr.componente.idComponente=" + id;
        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getClientesAtrByEmpresa(String id) {
        String subhql = "(SELECT r.componente.idComponente FROM RelComponenteUbicacion r "
                + "WHERE r.periodoFin=999912 and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + "))))";
        String hql = "SELECT atr FROM RelComponente r, AtrComponente atr "
                + "WHERE atr.componente.idComponente=r.relComponentePK.idComponenteHijo and "
                + "r.relComponentePK.idComponente in " + subhql;

        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getClientesAtrByZona(String id) {
        String subhql = "(SELECT r.componente.idComponente FROM RelComponenteUbicacion r "
                + "WHERE r.periodoFin=999912 and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idPadre in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + ")))";
        String hql = "SELECT atr FROM RelComponente r, AtrComponente atr "
                + "WHERE atr.componente.idComponente=r.relComponentePK.idComponenteHijo and "
                + "r.relComponentePK.idComponente in " + subhql;

        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getClientesAtrByCircuitoOrBarrio(String id) {
        String subhql = "(SELECT r.componente.idComponente FROM RelComponenteUbicacion r "
                + "WHERE r.periodoFin=999912 and r.relComponenteUbicacionPK.idZona=" + id + ")";

        String hql = "SELECT atr FROM RelComponente r, AtrComponente atr "
                + "WHERE atr.componente.idComponente=r.relComponentePK.idComponenteHijo and "
                + "r.relComponentePK.idComponente in " + subhql;

        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getClientesAtrBySubestacion(String id) {
        String subhql = "(SELECT r.componente.idComponente FROM RelComponenteUbicacion r "
                + "WHERE r.periodoFin=999912 and "
                + "r.relComponenteUbicacionPK.idZona in (SELECT z.idZona"
                + " FROM ZonaGeografica z"
                + " WHERE z.idZona in (SELECT zh.idZona"
                + " FROM ZonaGeografica zp, ZonaGeografica zh"
                + " WHERE zp.idZona=zh.idPadre and zp.idZona=" + id + ")))";
        String hql = "SELECT atr FROM RelComponente r, AtrComponente atr "
                + "WHERE atr.componente.idComponente=r.relComponentePK.idComponenteHijo and "
                + "r.relComponentePK.idComponente in " + subhql;

        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getClientesAtrByVista(String vista) {
        String subhql = "(SELECT rcu.componente.idComponente "
                + "FROM RelComponenteUbicacion rcu "
                + "WHERE rcu.periodoFin=999912 and "
                + "rcu.componente.tipoComponente.idTipoComponente=8 and "
                + "rcu.zonaGeografica.tipoComponente.tbltipo.tipo='" + vista + "')";

        String hql = "SELECT atr FROM RelComponente r, AtrComponente atr "
                + "WHERE atr.componente.idComponente=r.relComponentePK.idComponenteHijo and "
                + "r.relComponentePK.idComponente in " + subhql;

        return dao.find(hql);
    }

    @Override
    public List<AtrComponente> getClienteAtrByTrafo(String id) {
        String hql = "SELECT atr FROM RelComponente r, AtrComponente atr "
                + "WHERE atr.componente.idComponente=r.relComponentePK.idComponenteHijo and "
                + "r.relComponentePK.idComponente = " + id;
        return dao.find(hql);
    }

    @Override
    public void reporteTrafos(String periodo, ServletOutputStream out) {
        try {
            String select = "select b.periodo, nvl(z4.nombre, 'Sin delegacion') as Delegacion, nvl(z3.nombre, 'Sin municipio') as Municipio, nvl(z2.nombre, 'Sin poblacion') Poblacion, "
                    + "nvl(z1.nombre, '') Barrio, nvl(b.id_componente, '') Id_componente, nvl(cm.nombre, '') as placa_amarilla, nvl(ac.codigo_trafo, '') as CODIGO_BDI_TRAFO,\n"
                    + " a.medidor,  nvl(t3.nombre, '') as Tipo_Totalizador,nvl(tm.nombre, '') marca,\n"
                    + "nvl(t2.nombre, '') as Tipo_red , nvl(z1e.nombre, '') Circuito, nvl(z2e.nombre, '') as Area_Mt, a.carga_instalada,  nvl(r.descripcion,'') as Rango,NVL(t.nombre,'Con balance') as Motivo_No_Balance, nvl(b.consumo_macros,0), nvl(b.consumo_suministro,0),\n"
                    + "nvl(b.consumo_adicional,0), nvl(b.cant_sum_fact,0), nvl(b.cant_suministros_total,0) as cant_amarres, nvl(b.bal_energia_sum,0), nvl(b.porc_perdida_mes,0), nvl(b.porc_perdida_movil,0)\n"
                    + "from balances b, rango_clasificacion r, tbltipo t, componente c, rel_componente_medida re, componente_medida cm,\n"
                    + "rel_componente_ubicacion ru, atr_componente a, TBLTIPO Tm, zona_geografica z1, zona_geografica z2, zona_geografica z3,\n"
                    + "zona_geografica z4, rel_componente_ubicacion rue,\n"
                    + "zona_geografica z1e, zona_geografica z2e, tbltipo t2, tbltipo t3,\n"
                    + "atr_componente_medida ac\n"
                    + "where b.periodo = " + periodo
                    + "\n"
                    + "and b.id_componente = a.id_componente\n"
                    + "and a.tipo_red = t2.tipo(+)\n"
                    + "and cm.tipo_medidor = t3.tipo(+)\n"
                    + "and a.tipo_marca = tm.tipo(+)\n"
                    + "and b.id_tipo_componente=8\n"
                    + "and b.rango_balance = r.id_rango\n"
                    + "and b.mot_no_balance = t.tipo(+)\n"
                    + "and b.id_componente = c.id_componente\n"
                    + "and c.id_tipo_componente = 8\n"
                    + "and b.id_componente = re.id_componente\n"
                    + "and re.periodo_fin = 999912\n"
                    + "and b.id_componente = ru.id_componente\n"
                    + "and b.id_componente = rue.id_componente\n"
                    + "and re.id_componente_medida = cm.id_componente_medida\n"
                    + "and cm.id_componente_medida = ac.id_componente_medida\n"
                    + "and ru.id_zona = z1.id_zona\n"
                    + "and z1.id_padre = z2.id_zona\n"
                    + "and z2.id_padre = z3.id_zona\n"
                    + "and z3.id_padre = z4.id_zona\n"
                    + "and z4.id_tipo_componente = 12\n"
                    + "and rue.id_zona = z1e.id_zona\n"
                    + "and z1e.id_padre = z2e.id_zona\n"
                    + "and z2e.id_tipo_componente = 2\n"
                    + "and ru.periodo_fin = 999912\n"
                    + "and rue.periodo_fin = 999912";
            String primeraLinea = "periodo;Delegacion;Municipio;Poblacion;Barrio;id_componente;placa_amarilla;CODIGO_BDI_TRAFO;medidor;Tipo_Totalizador;marca;Tipo_red;Circuito;Area_Mt;carga_instalada;Rango;Motivo_No_Balance;consumo_macros;consumo_suministro;b.consumo_adicional;cant_sum_fact;cant_amarres;bal_energia_sum;porc_perdida_mes;porc_perdida_movil;";
            out.println(primeraLinea);
            escribirArchivo(out, select);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static boolean isNumeric(String inString) {
        try {
            Double.parseDouble(inString);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public void reporteTrafosDelegacion(String periodo, String delegacion, HttpServletResponse response) {
        try {
            String select = "select b.periodo, nvl(z4.nombre, 'Sin delegacion') as Delegacion, nvl(z3.nombre, 'Sin municipio') as Municipio, "
                    + "nvl(z2.nombre, 'Sin poblacion') Poblacion, nvl(z1.nombre, 'Sin barrio') Barrio, nvl(b.id_componente, '') Id_componente , nvl(cm.nombre, '') as placa_amarilla, nvl(ac.codigo_trafo, '') as CODIGO_BDI_TRAFO,\n"
                    + " a.medidor,  nvl(t3.nombre, '') as Tipo_Totalizador, nvl(tm.nombre, '') marca,\n"
                    + "nvl(t2.nombre, '') as Tipo_red , nvl(z1e.nombre, '') Circuito, nvl(z2e.nombre, '') as Area_Mt,a.carga_instalada,  nvl(r.descripcion,'') as Rango,NVL(t.nombre,'Con balance') as Motivo_No_Balance, nvl(b.consumo_macros,0), nvl(b.consumo_suministro,0),\n"
                    + "nvl(b.consumo_adicional,0), nvl(b.cant_sum_fact,0), nvl(b.cant_suministros_total,0) as cant_amarres, nvl(b.bal_energia_sum,0), nvl(b.porc_perdida_mes,0), nvl(b.porc_perdida_movil,0)\n"
                    + "from balances b, rango_clasificacion r, tbltipo t, componente c, rel_componente_medida re, componente_medida cm,\n"
                    + "rel_componente_ubicacion ru, atr_componente a, TBLTIPO Tm, zona_geografica z1, zona_geografica z2, zona_geografica z3,\n"
                    + "zona_geografica z4, rel_componente_ubicacion rue,\n"
                    + "zona_geografica z1e, zona_geografica z2e, tbltipo t2, tbltipo t3,\n"
                    + "atr_componente_medida ac\n"
                    + "where b.periodo = " + periodo
                    + "\n"
                    + "and b.id_componente = a.id_componente\n"
                    + "and a.tipo_red = t2.tipo(+)\n"
                    + "and cm.tipo_medidor = t3.tipo(+)\n"
                    + "and a.tipo_marca = tm.tipo(+)\n"
                    + "and b.id_tipo_componente=8\n"
                    + "and b.rango_balance = r.id_rango\n"
                    + "and b.mot_no_balance = t.tipo(+)\n"
                    + "and b.id_componente = c.id_componente\n"
                    + "and c.id_tipo_componente = 8\n"
                    + "and b.id_componente = re.id_componente\n"
                    + "and re.periodo_fin = 999912\n"
                    + "and b.id_componente = ru.id_componente\n"
                    + "and b.id_componente = rue.id_componente\n"
                    + "and re.id_componente_medida = cm.id_componente_medida\n"
                    + "and cm.id_componente_medida = ac.id_componente_medida\n"
                    + "and ru.id_zona = z1.id_zona\n"
                    + "and z1.id_padre = z2.id_zona\n"
                    + "and z2.id_padre = z3.id_zona\n"
                    + "and z3.id_padre = z4.id_zona\n"
                    + "and z4.id_tipo_componente = 12\n"
                    + "and rue.id_zona = z1e.id_zona\n"
                    + "and z1e.id_padre = z2e.id_zona\n"
                    + "and z2e.id_tipo_componente = 2\n"
                    + "and z4.nombre = '" + delegacion + "'\n"
                    + "and ru.periodo_fin = 999912\n"
                    + "and rue.periodo_fin = 999912";
            String primeraLinea = "periodo;Delegacion;Municipio;Poblacion;Barrio;id_componente;placa_amarilla;CODIGO_BDI_TRAFO;medidor;Tipo_Totalizador;marca;Tipo_red;Circuito;Area_Mt;carga_instalada;Rango;Motivo_No_Balance;consumo_macros;consumo_suministro;b.consumo_adicional;cant_sum_fact;cant_amarres;bal_energia_sum;porc_perdida_mes;porc_perdida_movil;";
            //response.getOutputStream().println(primeraLinea);
            escribirArchivoXLS(select, "Trafos_Delegacion", response, primeraLinea);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
     /**
     * Crea y escribe el archivo en formato excel o xls.
     * Procesa los resultados de la consulta.
     *
     * @autor <b>JMM</b>
     * @see ReporteManagerImpl
     * @since 04/03/2015
     * @param sql
     * @param response
     * @param primeraLinea
     * @return
     */
    
    public void escribirArchivoXLS(String sql, String title, HttpServletResponse response, String primeraLinea) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
          /* Get access to HSSFCellStyle */   
       HSSFCellStyle my_style_0 = workbook.createCellStyle();
        my_style_0.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        my_style_0.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
                
        int filauno=0;
        List l = dao.executeQuerie(sql);
        for (int i = 0; i < l.size(); i++) {
            Object[] row = (Object[]) l.get(i);
            
            try {
                if (filauno == 0) {
                    Row rows = sheet.createRow(0);
                    for (int k = 0; k < primeraLinea.split(";").length; k++) {
                        String s = primeraLinea.split(";")[k];
                        Cell cell = rows.createCell(k);
                        cell.setCellValue(s);
                        cell.setCellStyle(my_style_0);
                    }
                    filauno = 999;
                }
                Row rows = sheet.createRow(i + 1);
                for (int j = 0; j < row.length; j++) {
                    String valor = getString(row[j]);
                    Cell cell = rows.createCell(j);
                    if (isNumeric(valor)) {
                        try {
                            cell.setCellValue(Double.parseDouble(valor));
                        } catch (Exception e) {
                            Logger logger = Logger.getLogger(ReporteManagerImpl.class.getName());
                            logger.log(Level.INFO, "No se pudo convertir el dato a number.");
                            Logger.getLogger(ReporteManagerImpl.class.getName()).log(Level.SEVERE, null, e);
                            
                            
                            System.out.println("Conivirtiendo String... ERROR. <<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>");
                            System.out.println("<<<<<<>>>>>" + e.getLocalizedMessage());

                            valor = valor.replaceAll("\\.", ",");
                            cell.setCellValue(Double.valueOf(valor));
                        }
                    } else {
                        cell.setCellValue((String) valor);
                    }
                    
                    cell.setCellStyle(my_style_0);
                }
            
            } catch (Exception ex) {
                Logger logger = Logger.getLogger(ReporteManagerImpl.class.getName());
                logger.log(Level.INFO, "No se pudo procesar los registros.");
                Logger.getLogger(ReporteManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "inline; filename=" + title + ".csv");

            workbook.write(response.getOutputStream());
            response.getOutputStream().close();

            Logger logger = Logger.getLogger(ReporteManagerImpl.class.getName());
            logger.log(Level.INFO, "Se generó correctamente el archivo en formato excel.");
            
        } catch (FileNotFoundException e) {
           Logger logger = Logger.getLogger(ReporteManagerImpl.class.getName());
            logger.log(Level.INFO, "Archivo no encontrado.");
            Logger.getLogger(ReporteManagerImpl.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(ReporteManagerImpl.class.getName());
            logger.log(Level.INFO, "Error desconocido.");
            Logger.getLogger(ReporteManagerImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void escribirArchivo(ServletOutputStream out, String sql) {
        List l = dao.executeQuerie(sql);
        for (int i = 0; i < l.size(); i++) {
            Object[] row = (Object[]) l.get(i);
            try {
                String linea = "";
                for (int j = 0; j < row.length; j++) {
                    String valor = getString(row[j]);
                    if (isNumeric(valor)) {
                        linea += valor.replaceAll("\\.", ",") + ";";
                    } else {
                        linea += valor + ";";
                    }
                }
                out.println(linea);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Castea el objeto a string
     *
     * @autor <b>Jose Mejia</b>
     * @see MapBean
     * @since 27/11/2014
     * @param value
     * @return
     */
    public String getString(Object value) {
        String ret = "";
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = ((BigDecimal) value).toString();
            } else if (value instanceof String) {
                ret = (String) value;
            } else if (value instanceof BigInteger) {
                ret = ((BigInteger) value).toString();
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue()).toString();
            } else if (value instanceof Long) {
                ret = ((Long) value).toString();
            } else if (value instanceof Integer) {
                ret = ((Integer) value).toString();
            } else if (value instanceof Character) {
                ret = ((Character) value).toString();
            } else {
                throw new ClassCastException("No es posible castear el object [" + value + "] a String, porque es de tipo " + value.getClass());
            }
        }
        return ret;
    }

    @Override
    public void reporteSuministros(String periodo, String delegacion, ServletOutputStream out) {
        try {
            String select = "select a.contrato nic, C.ID_CLIENTE NiS, c.nombre direccion, c.direccion nombre, nvl(a.serie, 'Sin datos') num_medidor, m.CONSUMO_FACTURADO ,m.consumo_calculado, c2.nombre codigo_trafo, cm.nombre placa_mt\n"
                    + "from componente c, atr_componente a, medida m, rel_componente r, componente c2, rel_componente_medida rm, componente_medida cm,\n"
                    + "     rel_componente_ubicacion ru, zona_geografica circu, zona_geografica areamt, zona_geografica delega\n"
                    + "where c.id_tipo_componente = 9\n"
                    + "and   c.id_componente = a.id_componente\n"
                    + "and   c.id_componente = m.id_componente\n"
                    + "and   c2.id_componente = rm.id_componente\n"
                    + "and   rm.id_componente_medida = cm.id_componente_medida\n"
                    + "and   m.periodo = " + periodo + "\n"
                    + "and   m.id_tipo_componente = c.id_tipo_componente\n"
                    + "and   c.id_componente = r.ID_COMPONENTE_HIJO\n"
                    + "and   c2.id_componente = r.id_Componente\n"
                    + "and   c2.id_tipo_componente = 8\n"
                    + "and   r.periodo_ini <= " + periodo + "\n"
                    + "and   r.periodo_fin > " + periodo + "\n"
                    + "and   rm.periodo_ini <= " + periodo + "\n"
                    + "and   rm.periodo_fin > " + periodo + "\n"
                    + "and   ru.periodo_ini <= " + periodo + "\n"
                    + "and   ru.periodo_fin > " + periodo + "\n"
                    + "and   ru.id_componente = c2.id_componente\n"
                    + "and   ru.id_zona = circu.id_zona\n"
                    + "and   circu.id_padre = areamt.id_zona\n"
                    + "and   areamt.id_padre = delega.id_zona\n"
                    + "and   delega.nombre = '" + delegacion + "'\n"
                    + "ORDER BY cm.nombre";
            String primeraLinea = "Nic;Nis;Dirección;Nombre;Medidor;Consumo_Fact;Consumo_Prorrateado;Código Trafo;Placa Mt;";
            out.println(primeraLinea);
            List l = dao.executeQuerie(select);
            for (int i = 0; i < l.size(); i++) {
                Object[] row = (Object[]) l.get(i);
                try {
                    String linea = "";
                    for (int j = 0; j < row.length; j++) {
                        String valor = getString(row[j]) != null ? getString(row[j]) : "Sin datos";
                        if (isNumeric(valor)) {
                            linea += valor.replaceAll("\\.", ",") + ";";
                        } else {
                            linea += valor + ";";
                        }
                    }
                    out.println(linea);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    //public void reporteTodosSuministros(String delegacion,String periodo, HttpServletResponse out) {
    public void reporteTodosSuministros(String delegacion,String periodo, ServletOutputStream out) {
        try {
            System.out.println("Delegacion: "+delegacion);
            System.out.println("periodo: "+periodo);
            String select = "Select atr.contrato as NIC, sumi.id_cliente as NIS, Sumi.Nombre as Direccion, \n" +
                            "sumi.direccion as Nombre, nvl(atr.serie, 'Sin datos') as Medidor, Trafo.Nombre As Codigo_Trafo from\n" +
                            "Zona_Geografica Dele, \n" +
                            "Zona_Geografica SubE, \n" +
                            "Zona_Geografica cir, \n" +
                            "Rel_Componente_Ubicacion Rcu, \n" +
                            "Componente Trafo, \n" +
                            "Rel_Componente Rc, \n" +
                            "Componente Sumi, \n" +
                            "Atr_Componente atr\n" +
                            "Where Dele.NOMBRE='"+delegacion+"' \n" +
                            "And Dele.Id_Tipo_Componente='11' \n" +
                            "And Dele.Id_Zona=SubE.Id_Padre \n" +
                            "And SubE.Id_Zona=cir.Id_Padre \n" +
                            "And cir.Id_Zona=Rcu.Id_Zona \n" +
                            "And Trafo.Id_Componente=Rcu.Id_Componente \n" +
                            "And Rc.Id_Componente=Trafo.Id_Componente \n" +
                            "And rc.periodo_ini<="+periodo+"\n" +
                            "and rc.periodo_fin > "+periodo+"\n" +
                            "And rcu.periodo_ini<="+periodo+"\n" +
                            "and rcu.periodo_fin > "+periodo+"\n" +
                            "And sumi.Id_Componente=Rc.Id_Componente_Hijo \n" +
                            "And Atr.Id_Componente=Sumi.Id_Componente";
            System.out.println(select);
            
            String primeraLinea = "NIC;NIS;Direccion;Nombre;Medidor;Codigo_Trafo;";
            out.println(primeraLinea);
            List l = dao.executeQuerie(select);
            for (int i = 0; i < l.size(); i++) {
                Object[] row = (Object[]) l.get(i);
                try {
                    String linea = "";
                    for (int j = 0; j < row.length; j++) {
                        String valor = getString(row[j]) != null ? getString(row[j]) : "Sin datos";
                        if (isNumeric(valor)) {
                            linea += valor.replaceAll("\\.", ",") + ";";
                        } else {
                            linea += valor + ";";
                        }
                    }
                    out.println(linea);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            //escribirArchivoXLS(select, "Suministros_Totales", out, primeraLinea);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
