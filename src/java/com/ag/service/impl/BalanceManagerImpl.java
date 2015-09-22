package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.AtrComponente;
import com.ag.model.AtrComponenteMedida;
import com.ag.model.BalanceEnergia;
import com.ag.model.Balances;
import com.ag.model.Componente;
import com.ag.model.ComponenteMedida;
import com.ag.model.Estado;
import com.ag.model.Medida;
import com.ag.model.Novedades;
import com.ag.model.NovedadesPK;
import com.ag.model.RelComponente;
import com.ag.model.RelComponenteMedida;
import com.ag.model.RelComponenteUbicacion;
import com.ag.model.Tbltipo;
import com.ag.model.ZonaGeografica;
import com.ag.model.view.DataBalanceHijo;
import com.ag.model.view.DataRangosBalance;
import com.ag.model.view.DataValue;
import com.ag.model.view.Fecha;
import com.ag.model.view.PadreHijo;
//import com.ag.model.*;
//import com.ag.model.view.*;
import com.ag.service.BalanceManager;
import com.ag.util.HibernateUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Larry
 */
@Service("BalanceManager")
public class BalanceManagerImpl implements BalanceManager {

  @Autowired
  @Qualifier("DaoHibernate")
  private Dao dao;

  @Override
  public List<DataBalanceHijo> cuadroMando(String idComponente, String tipoComponente, String periodo) {
    String hql = "select h.idZona,h.nombre,h.tipoComponente.descripcion,b.porcPerdidaMes, ' ' "
            + " from Balances b, ZonaGeografica p, ZonaGeografica h "
            + " where b.balancesPK.periodo= " + periodo
            + "  and b.balancesPK.idComponente = " + idComponente
            + "  and b.balancesPK.idTipoComponente = " + tipoComponente
            + "  and h.idPadre = b.balancesPK.idComponente "
            + "  and p.idZona = h.idPadre "
            + "  and p.tipoComponente.idTipoComponente =b.balancesPK.idTipoComponente";

    List data = dao.find(hql);
    if (data.isEmpty()) {
      hql = "SELECT r.componente.idComponente,  r.componente.nombre, r.componente.tipoComponente.descripcion,b.porcPerdidaMes,r.componente.idCliente "
              + " FROM Balances b , RelComponenteUbicacion r "
              + " WHERE b.balancesPK.periodo= " + periodo
              + " AND r.zonaGeografica.idZona = " + idComponente
              + " AND r.zonaGeografica.tipoComponente.idTipoComponente = " + tipoComponente
              + " AND r.relComponenteUbicacionPK.periodoIni <= " + periodo
              + " AND r.periodoFin       >" + periodo
              + " AND b.balancesPK.idComponente =  r.componente.idComponente "
              + " AND b.balancesPK.idTipoComponente =  r.componente.tipoComponente.idTipoComponente";
      data = dao.find(hql);
    } else if (data.isEmpty()) {
      hql = "SELECT r.componente1.idComponente,  r.componente1.nombre, r.componente1.tipoComponente.descripcion,b.porcPerdidaMes,r.componente1.idCliente "
              + "  FROM Balances b , RelComponenteUbicacion r "
              + " WHERE r.relComponentePK.idComponente = " + idComponente
              + "   AND r.componente.tipoComponente.idTipoComponente = " + tipoComponente
              + "   AND  r.relComponentePK.periodoIni <= " + periodo
              + "   AND r.periodoFin> " + periodo
              + "   AND b.balancesPK.idComponente =  r.componente1.idComponente "
              + "   AND b.balancesPK.idTipoComponente =  r.componente1.tipoComponente.idTipoComponente";
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
   * Devuelve un objeto de tipo Balances para el IdComponente,tipoComponente y
   * periodo pasado por parametro.
   *
   * @param idComponente
   * @param tipoComponente
   * @param periodo
   * @return
   */
  @Override
  public Balances getBalances(String idComponente, String tipoComponente, String periodo) {
    String hql = "select b "
            + " from Balances b "
            + " where b.balancesPK.idComponente= " + idComponente
            + " and   b.balancesPK.idTipoComponente= " + tipoComponente
            + " and    b.balancesPK.periodo= " + periodo;
    return dao.findObject(hql);
  }

  /**
   * Este metodo devuelve un objeto de tipo DataRangosBalance, que contiene las
   * cantidades de transformadores por cada rango tomando como referencia la
   * zona.
   *
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
      hql = " select  b.rangoClasificacion.descripcion, count(distinct b.balancesPK.idComponente) "
              + "  from Balances b,"
              + " RelComponenteUbicacion ru,"
              + " ZonaGeografica z2,"
              + " ZonaGeografica z3,"
              + " ZonaGeografica z4, "
              + " ZonaGeografica z5, "
              + " ZonaGeografica z6, "
              + " ZonaGeografica z7 "
              + " where b.balancesPK.idComponente = ru.componente.idComponente "
              + " and b.balancesPK.periodo = " + periodo
              + " and b.balancesPK.idTipoComponente = 8"
              + " and ru.relComponenteUbicacionPK.idZona= z2.idZona"
              + " and ru.relComponenteUbicacionPK.periodoIni <=" + periodo
              + " and ru.periodoFin > " + periodo
              + " and z2.idPadre = z3.idZona "
              + " and z3.idPadre = z4.idZona "
              + " and z4.idPadre = z5.idZona "
              + " and z5.idPadre = z6.idZona "
              + " and z6.idPadre = z7.idZona "
              + " and z7.idZona = " + idZona
              + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";
    } else if (tipoComponente.equals("5") || tipoComponente.equals("0")) {
      hql = " select  b.rangoClasificacion.descripcion, count(distinct b.balancesPK.idComponente) "
              + "  from Balances b,"
              + " RelComponenteUbicacion ru,"
              + " ZonaGeografica z2,"
              + " ZonaGeografica z3,"
              + " ZonaGeografica z4, "
              + " ZonaGeografica z5, "
              + " ZonaGeografica z6 "
              + " where b.balancesPK.idComponente = ru.componente.idComponente "
              + " and b.balancesPK.periodo = " + periodo
              + " and b.balancesPK.idTipoComponente = 8"
              + " and ru.relComponenteUbicacionPK.idZona= z2.idZona"
              + " and ru.relComponenteUbicacionPK.periodoIni <=" + periodo
              + " and ru.periodoFin > " + periodo
              + " and z2.idPadre = z3.idZona "
              + " and z3.idPadre = z4.idZona "
              + " and z4.idPadre = z5.idZona "
              + " and z5.idPadre = z6.idZona "
              + " and z6.idZona = " + idZona
              + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";
    } else if (tipoComponente.equals("12") || tipoComponente.equals("1")) {
      hql = " select  b.rangoClasificacion.descripcion, count(distinct b.balancesPK.idComponente) "
              + "  from Balances b,"
              + " RelComponenteUbicacion ru,"
              + " ZonaGeografica z2,"
              + " ZonaGeografica z3,"
              + " ZonaGeografica z4, "
              + " ZonaGeografica z5 "
              + " where b.balancesPK.idComponente = ru.componente.idComponente "
              + " and b.balancesPK.periodo = " + periodo
              + " and b.balancesPK.idTipoComponente = 8"
              + " and ru.relComponenteUbicacionPK.idZona= z2.idZona"
              + " and ru.relComponenteUbicacionPK.periodoIni <=" + periodo
              + " and ru.periodoFin > " + periodo
              + " and z2.idPadre = z3.idZona "
              + " and z3.idPadre = z4.idZona "
              + " and z4.idPadre = z5.idZona "
              + " and z5.idZona = " + idZona
              + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";
    } else if (tipoComponente.equals("11") || tipoComponente.equals("6")) {
      hql = " select  b.rangoClasificacion.descripcion, count(distinct b.balancesPK.idComponente) "
              + "  from Balances b,"
              + " RelComponenteUbicacion ru,"
              + " ZonaGeografica z2,"
              + " ZonaGeografica z3,"
              + " ZonaGeografica z4 "
              + " where b.balancesPK.idComponente = ru.componente.idComponente "
              + " and b.balancesPK.periodo = " + periodo
              + " and b.balancesPK.idTipoComponente = 8"
              + " and ru.relComponenteUbicacionPK.idZona= z2.idZona"
              + " and ru.relComponenteUbicacionPK.periodoIni <=" + periodo
              + " and ru.periodoFin > " + periodo
              + " and z2.idPadre = z3.idZona "
              + " and z3.idPadre = z4.idZona "
              + " and z4.idZona = " + idZona
              + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";
    } else if (tipoComponente.equals("13") || tipoComponente.equals("2")) {
      hql = " select  b.rangoClasificacion.descripcion, count(distinct b.balancesPK.idComponente) "
              + "  from Balances b,"
              + " RelComponenteUbicacion ru,"
              + " ZonaGeografica z2,"
              + " ZonaGeografica z3 "
              + " where b.balancesPK.idComponente = ru.componente.idComponente "
              + " and b.balancesPK.periodo = " + periodo
              + " and b.balancesPK.idTipoComponente = 8"
              + " and ru.relComponenteUbicacionPK.idZona= z2.idZona"
              + " and ru.relComponenteUbicacionPK.periodoIni <=" + periodo
              + " and ru.periodoFin > " + periodo
              + " and z2.idPadre = z3.idZona "
              + " and z3.idZona = " + idZona
              + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";
    } else if (tipoComponente.equals("7") || tipoComponente.equals("3")) {
      hql = " select  b.rangoClasificacion.descripcion, count(distinct b.balancesPK.idComponente) "
              + "  from Balances b,"
              + " RelComponenteUbicacion ru,"
              + " ZonaGeografica z2 "
              + " where b.balancesPK.idComponente = ru.componente.idComponente "
              + " and b.balancesPK.periodo = " + periodo
              + " and b.balancesPK.idTipoComponente = 8"
              + " and ru.relComponenteUbicacionPK.idZona= z2.idZona"
              + " and ru.relComponenteUbicacionPK.periodoIni <=" + periodo
              + " and ru.periodoFin > " + periodo
              + " and z2.idZona = " + idZona
              + " group by b.rangoClasificacion.idRango, b.rangoClasificacion.descripcion";
    }
    if (hql != null) {
      List data = dao.find(hql);
      if (data != null) {
        for (Iterator it = data.iterator(); it.hasNext();) {
          Object[] valores = (Object[]) it.next();
          if (String.valueOf(valores[0]).equalsIgnoreCase("CRITICO")) {
            rangoBalance.setCritico(String.valueOf(valores[1]));
          } else if (String.valueOf(valores[0]).equalsIgnoreCase("ALTO")) {
            rangoBalance.setAlto(String.valueOf(valores[1]));
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
          } else if (String.valueOf(valores[0]).equalsIgnoreCase("SIN BALANCE")) {
            rangoBalance.setSinBalance(String.valueOf(valores[1]));
          }
        }
      }
    }
    
    /*HQL para buscar la descripción, porc_minimo y porc_maximo del rango para el componente
    teniendo en cuenta el tipo de árbol y el id del componente*/
    
    return rangoBalance;

  }

  @Override
  public Medida getMedida(String idComponente, String periodo, String idTipoCompo) {
    String hql = "select m from Medida m"
            + " where m.medidaPK.idComponente = " + idComponente
            + " and m.medidaPK.periodo = " + periodo
            + " and m.medidaPK.idTipoComponente = " + idTipoCompo;
    return dao.findObject(hql);
  }

  @Override
  public AtrComponente getAtrComponente(String idComponente) {
    String hql = "select a from AtrComponente a"
            + " where a.componente.idComponente = " + idComponente;
    return dao.findObject(hql);
  }

  @Override
  public RelComponenteMedida getRelComponenteMedida(String idComponente, String periodo) {
    String hql = "select r from RelComponenteMedida r "
            + " where r.componente.idComponente = " + idComponente
            + " and r.relComponenteMedidaPK.periodoIni <=" + periodo
            + " and r.periodoFin > " + periodo;
    return dao.findObject(hql);
  }

  @Override
  public AtrComponenteMedida getAtrComponenteMedida(String idComponenteMedida, String periodo) {
    String hql = "select a from AtrComponenteMedida a"
            + " where a.componenteMedida.idComponenteMedida = " + idComponenteMedida
            + " and a.atrComponenteMedidaPK.periodoIni <=" + periodo
            + " and a.periodoFin > " + periodo;
    return dao.findObject(hql);
  }

  @Override
  public AtrComponenteMedida getAtrComponenteMedidaTrafo(String nombre, String periodo) {
    String hql = " select a from AtrComponenteMedida a "
            + " where a.componenteMedida.idComponenteMedida = (select c.idComponenteMedida from ComponenteMedida c"
            + " where c.nombre = '" + nombre + "')";
    return dao.findObject(hql);
  }

  @Override
  public ComponenteMedida getComponenteMedida(String idComponente, String periodo) {
    String hql = " select c from RelComponenteMedida r, ComponenteMedida c "
            + " where r.componente.idComponente = " + idComponente
            + " and r.relComponenteMedidaPK.periodoIni <=" + periodo
            + " and r.periodoFin > " + periodo
            + " and c.idComponenteMedida = r.relComponenteMedidaPK.idComponenteMedida ";
    return dao.findObject(hql);
  }

  @Override
  public Componente getComponente(String idComponente) {
    String hql = " select c from Componente c "
            + " where c.idComponente = " + idComponente;
    return dao.findObject(hql);
  }

  @Override
  public PadreHijo getSubestacionCircuito(String idComponente, String periodo) {
    String hql = "select  rcu.zonaGeografica.idZona, rcu.zonaGeografica.nombre, z.idZona, z.nombre"
            + " from RelComponenteUbicacion rcu, ZonaGeografica z "
            + " where rcu.relComponenteUbicacionPK.idComponente =  " + idComponente
            + " and  rcu.relComponenteUbicacionPK.periodoIni <= " + periodo
            + " and  rcu.periodoFin> " + periodo
            + " and  rcu.zonaGeografica.idPadre = z.idZona "
            + " and  rcu.zonaGeografica.tipoComponente.tbltipo.tipo = 'NIV100'";
    Object[] data = dao.findObject(hql);
    PadreHijo ph;
    if (data != null) {
      ph = new PadreHijo(String.valueOf(data[0]), String.valueOf(data[1]), String.valueOf(data[2]), String.valueOf(data[3]));
    } else {
      ph = new PadreHijo("", "", "", "");
    }
    return ph;
  }

  @Override
  public PadreHijo getZonaMunicipio(String idComponente, String periodo) {
    String hql = "select z.idZona, z.nombre, z1.idZona, z1.nombre"
            + " from RelComponenteUbicacion rcu, ZonaGeografica z, ZonaGeografica z1 "
            + " where rcu.relComponenteUbicacionPK.idComponente =  " + idComponente
            + " and  rcu.relComponenteUbicacionPK.periodoIni <= " + periodo
            + " and  rcu.periodoFin> " + periodo
            + " and  rcu.zonaGeografica.idPadre = z.idZona "
            + " and  rcu.zonaGeografica.tipoComponente.tbltipo.tipo = 'NIV200'"
            + " and  z.idPadre = z1.idZona";
    Object[] data = dao.findObject(hql);
    PadreHijo ph;
    if (data != null) {
      ph = new PadreHijo(String.valueOf(data[0]), String.valueOf(data[1]), String.valueOf(data[2]), String.valueOf(data[3]));
    } else {
      ph = new PadreHijo("", "", "", "");
    }
    return ph;
  }

  @Override
  public List<DataValue> getCantSumXTipoUso(String idComponente, String periodo) {
    String hql = " select atr.tbltipo4.tipo,atr.tbltipo4.nombre,count(*) "
            + " from RelComponente r, AtrComponente atr "
            + " where r.componente.idComponente = " + idComponente
            + " and atr.componente.idComponente =  r.componente1.idComponente "
            + " and r.relComponentePK.periodoIni <= " + periodo
            + " and r.periodoFin > " + periodo
            + " and atr.periodoIni <= " + periodo
            + " and atr.periodoFin > " + periodo
            + " group by atr.tbltipo4.tipo,atr.tbltipo4.nombre";
    List data = dao.find(hql);
    List<DataValue> listCombo = new ArrayList();
    if (data != null) {
      for (int i = 0; i < data.size(); i++) {
        Object[] dTu = (Object[]) data.get(i);
        DataValue tu = new DataValue(String.valueOf(dTu[0]), String.valueOf(dTu[1]), String.valueOf(dTu[2]));
        listCombo.add(tu);
      }
    }
    return listCombo;
  }

  @Override
  public String getCantSumNoMedidos(String idComponente, String periodo) {
    String hql = " select count(*) "
            + " from RelComponente r, AtrComponente atr "
            + " where r.componente.idComponente = " + idComponente
            + " and atr.componente.idComponente =  r.componente1.idComponente "
            + " and r.relComponentePK.periodoIni <= " + periodo
            + " and r.periodoFin > " + periodo
            + " and atr.periodoIni <= " + periodo
            + " and atr.periodoFin > " + periodo
            + " and atr.tbltipo2.tipo = 'TSU002' "
            + " group by r.componente.idComponente";
    String cantidad = String.valueOf(dao.findObject(hql));
    if (cantidad.equals("null")) {
      cantidad = "0";
    }
    return cantidad;
  }

  @Override
  public List<Medida> getListMedida(String idComponente, int periodo) {

    String hql = "select valor from Parametro where idParametro='HISTORICO_MEDIDA'";
    int periodoParam = Integer.parseInt(String.valueOf(dao.findObject(hql)));
    int periodos = periodo - periodoParam;
    hql = "";

    hql = "select m from Medida m"
            + " where medidaPK.idComponente = " + idComponente
            + " and  medidaPK.periodo      <= " + periodo;
    //+ " and  medidaPK.periodo      >= " + periodos;

    return dao.find(hql);

  }

  @Override
  public int getNumMesesAtras() {
    String hql = "SELECT p.valor FROM Parametro p WHERE p.idParametro ='MESES_ATRAS'";
    return Integer.valueOf(dao.findObject(hql).toString());
  }

  @Override
  public List<Componente> getTrafosByRango(String id, String tipo, String periodo, String descripcion) {
    String hql = "";
    if (tipo.equals("4")) { //Empresa geografica
      hql = " SELECT r.componente FROM RelComponenteUbicacion r "
              + " WHERE r.periodoFin> " + periodo + " and "
              + " r.relComponenteUbicacionPK.idZona in (SELECT z1.idZona "
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
              + " and z6.idZona = " + id + ")";
    } else if (tipo.equals("5") || tipo.equals("0")) { //depto geografica o Empresa electrica
      hql = " SELECT r.componente FROM RelComponenteUbicacion r "
              + " WHERE r.periodoFin> " + periodo + " and "
              + " r.relComponenteUbicacionPK.idZona in (SELECT z1.idZona "
              + " FROM ZonaGeografica z1, "
              + " ZonaGeografica z2, "
              + " ZonaGeografica z3, "
              + " ZonaGeografica z4, "
              + " ZonaGeografica z5 "
              + " WHERE z5.idZona = z4.idPadre "
              + " and z4.idZona = z3.idPadre "
              + " and z3.idZona = z2.idPadre "
              + " and z2.idZona = z1.idPadre "
              + " and z5.idZona = " + id + ")";
      //Lob.20140708.INI      
    } else if (tipo.equals("12") || tipo.equals("1")) { //Delegacion geografica o Zona electrica
      hql = " SELECT r.componente FROM RelComponenteUbicacion r "
              + " WHERE r.periodoFin> " + periodo + " and "
              + " r.relComponenteUbicacionPK.idZona in (SELECT z1.idZona "
              + " FROM ZonaGeografica z1, "
              + " ZonaGeografica z2, "
              + " ZonaGeografica z3, "
              + " ZonaGeografica z4 "
              + " WHERE z4.idZona = z3.idPadre "
              + " and z3.idZona = z2.idPadre "
              + " and z2.idZona = z1.idPadre "
              + " and z4.idZona = " + id + ")";
      //Lob.20140708.FIN         
    } else if (tipo.equals("11") || tipo.equals("6")) { //Delegacion electrica o Municipio geografica
      hql = " SELECT r.componente FROM RelComponenteUbicacion r "
              + " WHERE r.periodoFin> " + periodo + " and "
              + " r.relComponenteUbicacionPK.idZona in (SELECT z1.idZona "
              + " FROM ZonaGeografica z1, "
              + " ZonaGeografica z2, "
              + " ZonaGeografica z3 "
              + " WHERE z3.idZona = z2.idPadre "
              + " and z2.idZona = z1.idPadre "
              + " and z3.idZona = " + id + ")";
    } else if (tipo.equals("13") || tipo.equals("2")) { //Poblacion geografica o Subestacion(Area MT) electrica
      hql = " SELECT r.componente FROM RelComponenteUbicacion r "
              + " WHERE r.periodoFin> " + periodo + " and "
              + " r.relComponenteUbicacionPK.idZona in (SELECT z1.idZona "
              + " FROM ZonaGeografica z1, "
              + " ZonaGeografica z2 "
              + " WHERE z2.idZona = z1.idPadre "
              + " and z2.idZona = " + id + ")";
    } else if (tipo.equals("7") || tipo.equals("3")) { // Barrio o Circuito    
      hql = "SELECT r.componente FROM RelComponenteUbicacion r "
              + "WHERE r.periodoFin>" + periodo + " and "
              + "r.relComponenteUbicacionPK.idZona=" + id;
    }

    String hql2 = "select c from Componente c "
            + "where c.tipoComponente.idTipoComponente=8 and "
            + "c.idComponente in (select b.balancesPK.idComponente "
            + "from Balances b, RangoClasificacion r "
            + "where b.balancesPK.idTipoComponente=8 and b.rangoClasificacion.idRango=r.idRango "
            + "and r.descripcion='" + descripcion + "' "
            + "and b.balancesPK.periodo=" + periodo + " "
            + "and b.balancesPK.idComponente in (" + hql + "))";
    return dao.find(hql2);

  }

  @Override
  public ZonaGeografica getPadreByTipo(String idComponente, String tipo) {
    String hql = "SELECT z FROM RelComponenteUbicacion r, ZonaGeografica z "
            + "WHERE r.relComponenteUbicacionPK.idZona=z.idZona "
            + "and r.periodoFin=999912 and z.tipoComponente.idTipoComponente=" + tipo
            + " and r.relComponenteUbicacionPK.idComponente=" + idComponente;
    return dao.findObject(hql);
  }

  //11-07-14 ptorresINI
  @Override
  public ZonaGeografica getZonaGeografica(String idZona, String tipo) {
    String hql = "SELECT z FROM RelComponenteUbicacion r, ZonaGeografica z "
            + "WHERE r.relComponenteUbicacionPK.idZona=z.idZona "
            + "and r.periodoFin=999912 and z.tipoComponente.idTipoComponente=" + tipo
            + " and r.relComponenteUbicacionPK.idZona=" + idZona;
    return dao.findObject(hql);
  }

  @Override
  public ZonaGeografica getAreaMt(String idZona, String tipo) {
    String hql = "SELECT z FROM ZonaGeografica z "
            + "WHERE z.tipoComponente.idTipoComponente=" + tipo
            + " and z.idZona=" + idZona;
    return dao.findObject(hql);
  }
  //11-07-14 ptorresFIN

  @Override
  public List<Novedades> getNovedadesByTrafo(String idComponenteMedida) {
    String hql = "SELECT n FROM Novedades n WHERE n.novedadesPK.idComponenteMedida =" + idComponenteMedida;
    return dao.find(hql);
  }

  @Override
  public void saveNovedad(String usuario, String programa, int periodo, String idTipoNovedad, ComponenteMedida componenteMedida) {
    Novedades n = new Novedades();
    n.setUsuario(usuario);
    n.setPrograma(programa);
    Fecha fecha = new Fecha();
    n.setFechaModif(fecha.getFecha());
    n.setFecha(fecha.getFechaSistema());
    n.setPeriodo(periodo);
    n.setComponenteMedida(componenteMedida);
    n.setTbltipo(getTipo(idTipoNovedad));

    NovedadesPK novedadesPK = new NovedadesPK(componenteMedida.getIdComponenteMedida().toBigInteger(), idTipoNovedad);
    n.setNovedadesPK(novedadesPK);
    dao.persist(n);
  }

  @Override
  public void editNovedad(Novedades n) {
    Fecha fecha = new Fecha();
    n.setFechaModif(fecha.getFecha());
    dao.persist(n);
  }

  @Override
  public List<Tbltipo> getTiposNovedades() {
    String hql = "select t "
            + " from Tbltipo t"
            + " where t.grupo.codigo='TIN000'";
    return dao.find(hql);

  }

  @Override
  public Tbltipo getTipo(String tipo) {
    String hql = "Select t from Tbltipo t where t.tipo ='" + tipo + "'";
    return dao.findObject(hql);
  }

  @Override
  public void deleteNovedad(Novedades n) {
    dao.delete(n);
  }

  @Override
  public List<RelComponente> RelComponente(String relComponente) {
    String hql = "From RelComponente r where r.relComponentePK.idComponenteHijo=" + relComponente;
    return dao.find(hql);
  }

  @Override
  public Estado getEstado(String codigo) {
    String hql = "From Estado e where e.idEstado='" + codigo + "'";
    return dao.findObject(hql);
  }

  @Override
  public List<RelComponenteUbicacion> RelComponenteU(String relComponente) {
    String hql = "From RelComponenteUbicacion r where r.relComponenteUbicacionPK.idComponente=" + relComponente;
    return dao.find(hql);
  }

  @Override
  public boolean saveRelCompoente(RelComponente... rc) {
    try {
      dao.persist(rc);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean saveRelCompoenteUbi(RelComponenteUbicacion... rcu) {
    try {
      dao.persist(rcu);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public String getAreaMt(String idComercial) {
    String hql2 = "";
    String idComponente = "";
    String hql = "select z.idZona, z.idComercial "
            + " from ZonaGeografica z "
            + " where z.idComercial= '" + idComercial + "'";
    if (hql != null) {
      List data = dao.find(hql);
      if (data != null) {
        for (int i = 0; i < data.size(); i++) {
          Object[] valores = (Object[]) data.get(i);
          if (!String.valueOf(valores[0]).equals("")) {
            hql2 = "select b.balancesPK.idComponente "
                    + " from Balances b "
                    + " where b.balancesPK.idComponente = " + String.valueOf(valores[0])
                    + " and b.balancesPK.idTipoComponente = 2";
          }
          List validador = dao.find(hql2);
          if (!validador.isEmpty()) {
            idComponente = dao.findObject(hql2).toString();
          }
        }
      }
    }
    return idComponente;
  }

  @Override
  public BalanceEnergia getBalanceEnergia(String idComponente, String periodo) {
    String hql = "select b from BalanceEnergia b"
            + " where b.balanceEnergiaPK.idComponente = " + idComponente
            + " and b.balanceEnergiaPK.periodo = " + periodo;
    return dao.findObject(hql);
  }

  @Override
  public boolean cerrarBalances(String periodo) {
    Session session = null;
    boolean updated = false;
    String hql = "update Balances b set b.cierre = 'C' where b.balancesPK.periodo = :periodo and cierre = 'A'";
    try {
      session = dao.getSessionHibernate();
      Query query = session.createQuery(hql);
      query.setParameter("periodo", Integer.parseInt(periodo));
      query.executeUpdate();
      session.beginTransaction().commit();
      updated = true;
    } catch (Exception ex) {
      updated = false;
      session.beginTransaction().rollback();
      ex.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return updated;
  }

  @Override
  public BigDecimal getEnergiaTotalAreas(String periodo) {
    BigDecimal energiaTotalAreas = BigDecimal.ZERO;
    List l = null;
    String sql = "select e.energia_total "
            + "from energia_total_areas e "
            + "where e.periodo = " + periodo + " "
            + "order by e.fecha_modif desc";
    Session session = null;
    try {
      session = dao.getSessionHibernate();
      l = dao.executeQuerie(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }

    try{
      energiaTotalAreas = (BigDecimal) l.get(0);
    }catch(Exception ex){
      energiaTotalAreas = null;
    }
    
    return energiaTotalAreas;

  }

  @Override
  public BigDecimal getEnergiaEntradaAreas(String periodo) {
    BigDecimal energiaEntrada = BigDecimal.ZERO;
    List l = null;
    String sql = "select e.energia_entrada "
            + "from energia_total_areas e "
            + "where e.periodo = " + periodo + " "
            + "order by e.fecha_modif desc";
    Session session = null;
    try {
      session = dao.getSessionHibernate();
      l = dao.executeQuerie(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }

    try{
      energiaEntrada = (BigDecimal) l.get(0);
    }catch(Exception ex){
      energiaEntrada = null;
    }
    
    return energiaEntrada;
  }

  @Override
  public BigDecimal getEnergiaSalidaAreas(String periodo) {
    BigDecimal energiaSalida = BigDecimal.ZERO;
    List l = null;
    String sql = "select e.energia_salida "
            + "from energia_total_areas e "
            + "where e.periodo = " + periodo + " "
            + "order by e.fecha_modif desc";
    Session session = null;
    try {
      session = dao.getSessionHibernate();
      l = dao.executeQuerie(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }

    try{
      energiaSalida = (BigDecimal) l.get(0);
    }catch(Exception ex){
      energiaSalida = null;
    }
    
    return energiaSalida;
  }

  @Override
  public BigDecimal getPorcentajePerdidaAreas(String periodo) {
    BigDecimal porcentajePerdidas = BigDecimal.ZERO;
    List l = null;
    String sql = "select e.porcentaje_perdidas "
            + "from energia_total_areas e "
            + "where e.periodo = " + periodo + " "
            + "order by e.fecha_modif desc";
    Session session = null;
    try {
      session = dao.getSessionHibernate();
      l = dao.executeQuerie(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }

    try{
      porcentajePerdidas = (BigDecimal) l.get(0);
    }catch(Exception ex){
      porcentajePerdidas = null;
    }
    
    return porcentajePerdidas;
  }

}
