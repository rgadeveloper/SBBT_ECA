package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.view.Nodo;
import com.ag.model.RangoClasificacion;
import com.ag.model.SBalances;
import com.ag.model.view.ComboLista;
import com.ag.model.view.Delegacion;
import com.ag.model.view.TipoComponenteVer;
import com.ag.model.view.UbicacionMacroV;
import com.ag.service.ArbolManager;
import java.math.BigDecimal;
import java.sql.ResultSet;
//import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author David
 */
@Service("SArbolManager")
public class SArbolManagerImpl implements ArbolManager {

  @Autowired
  @Qualifier("DaoHibernate")
  private Dao dao;

  @Override
  public List cargaArbolInicial(String nivelGrupo, String periodo) {
    String parametro = "";
    if (nivelGrupo != null) {
      if (nivelGrupo.equals("NIV100")) {
        parametro = "NIVEL_ENERGETICO";
      } else {
        parametro = "NIVEL_GEOGRAFICO";
      }
    }

    String hql = "SELECT t.idZona, t.nombre,t.tipoComponente.idTipoComponente,"
            + " t.coordX,t.coordY,t.tipoComponente.descripcion "
            + " FROM ZonaGeografica t ,  Parametro p"
            + " WHERE t.tipoComponente.idTipoComponente = p.valor "
            + " AND  t.tipoComponente.tbltipo.tipo = '" + nivelGrupo + "' "
            + " AND t.estado = 'AC001' "
            + " and p.idParametro ='" + parametro + "'";
    return obtenerArbol(hql, periodo);
  }

  @Override
  public List cargaArbolHijos(String codigoPadre, String tipoPadre, String periodo, HashMap gestionFiltros) {
    int contZona, contUbicacion, contComponente;

    String hql = "SELECT COUNT(*) "
            + " FROM ZonaGeografica p, ZonaGeografica h "
            + " WHERE h.idPadre = " + codigoPadre
            + " AND p.idZona = h.idPadre"
            + " AND p.tipoComponente.idTipoComponente = " + tipoPadre;
    contZona = Integer.parseInt(String.valueOf(dao.findObject(hql)));

    hql = "SELECT COUNT(*) "
            + " FROM SRelComponenteUbicacion r "
            + " WHERE  r.zonaGeografica.idZona =  " + codigoPadre
            + " AND   r.zonaGeografica.tipoComponente.idTipoComponente = " + tipoPadre
            + " AND   r.sRelComponenteUbicacionPK.periodoIni <=" + periodo
            + " AND   r.periodoFin       > " + periodo;
    contUbicacion = Integer.parseInt(String.valueOf(dao.findObject(hql)));

    hql = "SELECT COUNT(*) "
            + "FROM SRelComponente r "
            + " WHERE r.sRelComponentePK.idComponente =" + codigoPadre
            + " AND r.sComponente1.tipoComponente.idTipoComponente = " + tipoPadre
            + " AND  r.sRelComponentePK.periodoIni <= " + periodo
            + " AND r.periodoFin> " + periodo;
    contComponente = Integer.parseInt(String.valueOf(dao.findObject(hql)));

    String hqlArbol = null;
    if (contZona > 0) {
      hqlArbol = "SELECT  z.idZona, z.nombre,  z.tipoComponente.idTipoComponente,z.coordX,z.coordY,z.tipoComponente.descripcion "
              + " FROM ZonaGeografica z "
              + " WHERE z.idPadre =" + codigoPadre;
    } else if (contUbicacion > 0) {
      hqlArbol = "SELECT ru.sComponente.idComponente, ru.sComponente.nombre,"
              + " ru.sComponente.tipoComponente.idTipoComponente,"
              + " ru.sComponente.coordX,ru.sComponente.coordY,"
              + " ru.sComponente.tipoComponente.descripcion,"
              + " ru.sComponente.tbltipo1.nombre,"
              + " (SELECT a.tbltipo5.nombre FROM SAtrComponente a WHERE a.sComponente.idComponente=ru.sComponente.idComponente) as tipoUso,"
              + " (SELECT a.tbltipo.tipo FROM SAtrComponente a WHERE a.sComponente.idComponente=ru.sComponente.idComponente) as tipoRed "
              + " FROM SRelComponenteUbicacion ru  "
              + " WHERE  ru.zonaGeografica.idZona = " + codigoPadre
              + "  AND  ru.zonaGeografica.tipoComponente.idTipoComponente = " + tipoPadre
              + "  AND  ru.sRelComponenteUbicacionPK.periodoIni <=" + periodo
              + "  AND  ru.periodoFin                          > " + periodo
              + " order by ru.sComponente.nombre";

    } else if (contComponente > 0) {
      hqlArbol = "SELECT r.sRelComponentePK.idComponenteHijo, r.sComponente.idCliente,"
              + "r.sComponente.tipoComponente.idTipoComponente,"
              + "r.sComponente.coordX,r.sComponente.coordY,"
              + "r.sComponente.tipoComponente.descripcion,"
              + "r.sComponente.tbltipo1.nombre,"
              + "(SELECT a.tbltipo5.nombre FROM SAtrComponente a WHERE a.sComponente.idComponente=r.sComponente.idComponente) as tipoUso,"
              + "(SELECT a.tbltipo.tipo FROM SAtrComponente a WHERE a.sComponente.idComponente=r.sComponente.idComponente) as tipoRed "
              + " FROM SRelComponente r "
              + " WHERE r.sRelComponentePK.idComponente =" + codigoPadre
              + "  AND  r.sRelComponentePK.periodoIni <= " + periodo
              + " AND r.periodoFin> " + periodo
              + " AND r.sComponente1.tipoComponente.idTipoComponente = " + tipoPadre
              + " order by r.sComponente.idCliente";

    }
    if (hqlArbol == null) {
      return null;
    }
    return obtenerArbol(hqlArbol, periodo);
  }

  public List obtenerArbol(String hql, String periodo) {
    List elemento = dao.find(hql);
    List hijos = new ArrayList();
    if (elemento != null) {
      for (Iterator it = elemento.iterator(); it.hasNext();) {
        Object[] valores = (Object[]) it.next();
        String codigo = String.valueOf(valores[0]);
        String nombre = (String) valores[1];
        String tipo = ((Short) valores[2]).toString();
        SBalances b = getBalances(codigo, tipo, periodo);
        String perdidas = getPerdidas(b);
        String color = getColor(tipo, perdidas);
        String coordX = (String) valores[3];
        String coordY = (String) valores[4];
        if (!perdidas.equals("null")) {
          Nodo hijo = new Nodo(codigo, nombre, tipo, color, perdidas, coordX, coordY);
          if (b != null) {
            hijo.setNumMacroTot(String.valueOf(b.getTotalMacros()));
            hijo.setNumMacrosFuncionando(String.valueOf(b.getTotalMacrosFunc()));
            hijo.setNumSuministrosFact(String.valueOf(b.getCantSumFact()));
          }
          hijo.setNombreTipo((String) valores[5]);
          if (tipo.equals("8")) {
            hijo.setLocalizacion((String) valores[6]);
            hijo.setTipoUso((String) valores[7]);
            hijo.setNombreColor(getNombreColor(perdidas));
          }
          if (tipo.equals("9")) {
            hijo.setTipoUso((String) valores[7]);
          }
          hijos.add(hijo);
        }
      }
    }
    return hijos;
  }

  public String getColor(String tipoComponente, String perdidas) {
    String hql = !perdidas.equals("sb") ? "select  r "
            + " from RangoClasificacion r  "
            + " where r.tipoComponente.idTipoComponente = " + tipoComponente
            + " and porcMinimo <= " + perdidas
            + " and porcMaximo > " + perdidas
            : //si no tiene balances
            "select  r "
            + " from RangoClasificacion r  "
            + " where r.tipoComponente.idTipoComponente = " + tipoComponente
            + " and porcMinimo is null "
            + " and porcMaximo is null";
    RangoClasificacion objColor = (RangoClasificacion) dao.findObject(hql);
    String color = "color:rgb(212,208,200)";
    if (objColor != null) {
      color = "color:rgb(" + objColor.getColor().getRojo() + "," + objColor.getColor().getVerde() + "," + objColor.getColor().getAzul() + ")";
    }
    return color;
  }

  public String getPerdidas(String codigo, String tipo, String periodo) {
    String hql = " SELECT b FROM SBalances b "
            + " WHERE b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)"
            + " and  b.sBalancesPK.idComponente =  " + codigo
            + " and  b.sBalancesPK.periodo = " + periodo
            + " and  b.sBalancesPK.idTipoComponente=" + tipo;
    SBalances bal = (SBalances) dao.findObject(hql);
    String perdidas = "sb";
    if (bal != null) {
      perdidas = String.valueOf(bal.getPorcPerdidaMes());
    }
    return perdidas;
  }

  public String getNombreColor(String perdidas) {
    String hql = !perdidas.equals(" - ") ? "select  r "
            + " from RangoClasificacion r  "
            + " where r.tipoComponente.idTipoComponente = 8"
            + " and porcMinimo <= " + perdidas
            + " and porcMaximo > " + perdidas
            : //si no tiene balances
            "select  r "
            + " from RangoClasificacion r  "
            + " where r.tipoComponente.idTipoComponente = 8"
            + " and porcMinimo is null "
            + " and porcMaximo is null ";
    RangoClasificacion objColor = (RangoClasificacion) dao.findObject(hql);
    String colorName = "none"; //no ha sido asignado ningun rango
    if (objColor != null) {
      colorName = objColor.getColor().getDescripcion();
    }
    return colorName;
  }

  public SBalances getBalances(String codigo, String tipo, String periodo) {
    String hql = " SELECT b FROM SBalances b "
            + " WHERE b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s) "
            + " and b.sBalancesPK.idComponente =  " + codigo
            + " and  b.sBalancesPK.periodo = " + periodo
            + " and  b.sBalancesPK.idTipoComponente=" + tipo;
    SBalances bal = (SBalances) dao.findObject(hql);
    return bal;
  }

  public String getPerdidas(SBalances bal) {
    String perdidas = "null";
    if (bal != null && bal.getPorcPerdidaMes() != null) {
      perdidas = String.valueOf(bal.getPorcPerdidaMes());
    }
    return perdidas;
  }

  @Override
  public List getZoomMapa() {
    String hql = " SELECT t FROM TipoComponente t ";
    List tip = dao.find(hql);
    return tip;
  }

  @Override
  public List getPeriodo() {
    //no se usara
    String hql = " SELECT distinct  b.sBalancesPK.periodo  from SBalances  b "
            + " order by  b.sBalancesPK.periodo desc";
    List periodo = dao.find(hql);
    List listCombo = new ArrayList();
    for (int i = 0; i < periodo.size(); i++) {
      String per = String.valueOf(periodo.get(i));
      ComboLista cl = new ComboLista(per, per);
      listCombo.add(cl);
    }
    return listCombo;
  }

  @Override
  public String rutaTrafo(String idCliente, String tipoArbol, String periodo) throws SQLException {

    String ruta = idCliente + " no encontrado.";
    String sql = "select emp.nombre, zona.nombre, mun_sub.nombre, bar_cir.nombre, trafo.NOMBRE "
            + "from s_componente trafo, s_rel_componente_ubicacion rcu, tipo_componente tipo, "
            + "zona_geografica bar_cir, zona_geografica mun_sub, zona_geografica zona, "
            + "zona_geografica emp "
            + "where trafo.id_cliente='" + idCliente + "' and "
            + "rcu.periodo_fin>" + periodo + " and "
            + "rcu.periodo_ini <=" + periodo + " and "
            + "rcu.id_componente=trafo.id_componente and "
            + "tipo.id_tipo_componente=bar_cir.id_tipo_componente and "
            + "tipo.tipo_arbol='" + tipoArbol + "' and "
            + "bar_cir.id_zona=rcu.id_zona and "
            + "mun_sub.id_zona=bar_cir.id_padre and "
            + "zona.id_zona=mun_sub.id_padre and "
            + "emp.id_zona=zona.id_padre";
    Statement statement = dao.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(sql);
    while (rs.next()) {
      ruta = rs.getString(1) + "/" + rs.getString(2) + "/" + rs.getString(3) + "/" + rs.getString(4) + "/" + rs.getString(5);
    }
    rs.close();
    statement.close();
    return ruta;
  }

  @Override
  public String rutaSuministro(String idCliente, String tipoArbol, String periodo) throws SQLException {
    String ruta = idCliente + " no encontrado.";
    String sql = "select emp.nombre, zona.nombre, mun_sub.nombre, bar_cir.nombre, trafo.NOMBRE, sumi.nombre "
            + "from s_rel_componente r, "
            + "s_componente trafo, "
            + "s_componente sumi, "
            + "s_rel_componente_ubicacion rcu, "
            + "tipo_componente tipo, "
            + "zona_geografica bar_cir, "
            + "zona_geografica mun_sub, "
            + "zona_geografica zona, "
            + "zona_geografica emp "
            + "where r.id_componente_hijo=sumi.id_componente and "
            + "sumi.id_cliente='" + idCliente + "' and "
            + "r.periodo_fin>" + periodo + " and "
            + "r.periodo_ini <=" + periodo + " and "
            + "r.id_componente=trafo.id_componente and "
            + "sumi.id_componente=r.id_componente_hijo and "
            + "rcu.id_componente=trafo.id_componente and "
            + "tipo.id_tipo_componente=bar_cir.id_tipo_componente and "
            + "tipo.tipo_arbol='" + tipoArbol + "' and "
            + "bar_cir.id_zona=rcu.id_zona and "
            + "mun_sub.id_zona=bar_cir.id_padre and "
            + "zona.id_zona=mun_sub.id_padre and "
            + "emp.id_zona=zona.id_padre";
    Statement statement = dao.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(sql);
    if (rs.next()) {
      ruta = rs.getString(1) + "/" + rs.getString(2) + "/" + rs.getString(3) + "/" + rs.getString(4) + "/" + rs.getString(5) + "/" + rs.getString(6);
    }
    rs.close();
    statement.close();
    return ruta;
  }

  @Override
  public UbicacionMacroV getUbicacionMacroV(String idComponente, String periodo) throws SQLException {
    throw new UnsupportedOperationException("No soportado aun.");
  }

  @Override
  public List cargaArbolHijosTrafos(String codigoPadre, String tipoPadre, String periodo, HashMap gestionFiltros) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean soloTrafoInGmap(String tipo) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public String rutaView(String tipoComponente, String tipoArbol, String periodo, String idNif, String idPlaca) throws SQLException {
    String ruta = " no encontrado.";
    String sql = "";
    if (tipoComponente.equals("Suministro")) {
      sql = "select emp.nombre, zona.nombre, mun_sub.nombre, bar_cir.nombre, trafo.nombre, sumi.nombre "
              + "from rel_componente r, "
              + "componente trafo, "
              + "componente sumi, "
              + "rel_componente_ubicacion rcu, "
              + "tipo_componente tipo, "
              + "zona_geografica bar_cir, "
              + "zona_geografica mun_sub, "
              + "zona_geografica zona, "
              + "zona_geografica emp ,"
              + " Atr_Componente atrComp "
              + "where r.id_componente_hijo=sumi.id_componente ";

      boolean ban = false;

      if (!periodo.equals("")) {
        sql += " and r.periodo_fin>=" + periodo + " and r.periodo_ini <=" + periodo;
      }

      sql += " and r.id_componente=trafo.id_componente and sumi.id_componente=r.id_componente_hijo and ";
      sql += "rcu.id_componente=trafo.id_componente and tipo.id_tipo_componente=bar_cir.id_tipo_componente ";

      if (!tipoArbol.equals("")) {
        sql += " and tipo.tipo_arbol='" + tipoArbol + "'";
      }
      if (!idPlaca.equals("")) {
        sql += " and atrComp.serie='" + idPlaca + "'";
      }
      sql += " and bar_cir.id_zona=rcu.id_zona and mun_sub.id_zona=bar_cir.id_padre and zona.id_zona=mun_sub.id_padre and ";
      sql += "emp.id_zona=zona.id_padre";

    } else if (tipoComponente.equals("Trafo")) {
      sql = "select emp.nombre, zona.nombre, mun_sub.nombre, bar_cir.nombre, trafo.nombre "
              + "from componente trafo, rel_componente_ubicacion rcu, tipo_componente tipo, "
              + "zona_geografica bar_cir, zona_geografica mun_sub, zona_geografica zona, "
              + "zona_geografica emp, Atr_Componente atrComp where ";

      sql += " rcu.periodo_fin>=" + periodo + " and "
              + "rcu.periodo_ini <=" + periodo + " and "
              + "rcu.id_componente=trafo.id_componente and "
              + "tipo.id_tipo_componente=bar_cir.id_tipo_componente and "
              + "tipo.tipo_arbol='" + tipoArbol + "' and "
              + "bar_cir.id_zona=rcu.id_zona and "
              + "mun_sub.id_zona=bar_cir.id_padre and "
              + "zona.id_zona=mun_sub.id_padre and "
              + "emp.id_zona=zona.id_padre";

      if (!tipoArbol.equals("")) {
        sql += " and tipo.tipo_arbol='" + tipoArbol + "'";
      }
      if (!idPlaca.equals("")) {
        sql += " and atrComp.serie='" + idPlaca + "'";
      }

    }
    System.out.println("---------------" + sql);
    Statement statement = dao.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(sql);

    if (!tipoArbol.equals("Trafo")) {
      if (rs.next()) {
        ruta = rs.getString(1) + "/" + rs.getString(2) + "/" + rs.getString(3) + "/" + rs.getString(4) + "/" + rs.getString(5);
        System.out.println("---------ruta------" + ruta);
      }
    } else {
      if (rs.next()) {
        ruta = rs.getString(1) + "/" + rs.getString(2) + "/" + rs.getString(3) + "/" + rs.getString(4) + "/" + rs.getString(5) + "/" + rs.getString(6);
        System.out.println("---------ruta------" + ruta);
      }
    }
    rs.close();
    statement.close();
    return ruta;
  }

  @Override
  public List<TipoComponenteVer> buscarComponente(String tipoComp) {
    try {
      List<TipoComponenteVer> buscarComponente = new ArrayList();
      TipoComponenteVer TipoComp;
      String sql = "select c.NOMBRE,c.DIRECCION,c.ID_TIPO_COMPONENTE,tc.DESCRIPCION,rc.PERIODO_INI,rc.PERIODO_FIN,e.DESCRIPCION from componente c\n"
              + "inner join rel_componente_medida rc on rc.ID_COMPONENTE=c.ID_COMPONENTE\n"
              + "inner join componente_medida cm on rc.ID_COMPONENTE_MEDIDA=cm.ID_COMPONENTE_MEDIDA\n"
              + "inner join estado e on e.ID_ESTADO=cm.ESTADO\n"
              + "inner join tipo_componente tc on tc.ID_TIPO_COMPONENTE=c.ID_TIPO_COMPONENTE\n"
              + "where c.ID_COMPONENTE=" + tipoComp;
      Statement statement = dao.getConnection().createStatement();
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        TipoComp = new TipoComponenteVer();
        TipoComp.setNombre(rs.getString(1));
        TipoComp.setDireccion(rs.getString(2));
        TipoComp.setTipoCompo(rs.getString(3));
        TipoComp.setNombreCompo(rs.getString(4));
        TipoComp.setPini(rs.getString(5));
        TipoComp.setPfin(rs.getString(6));
        TipoComp.setEstado(rs.getString(7));
        buscarComponente.add(TipoComp);
      }
      rs.close();
      statement.close();

      return buscarComponente;
    } catch (SQLException ex) {
      Logger.getLogger(ArbolManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }

  }

  @Override
  public String rutaViewGeografica(String tipoComponente, String tipoArbol, String periodo, String idNic, String idPlaca) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<com.ag.model.view.Perdidas> buscarPerdida(BigDecimal periodo) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<Delegacion> buscarDelegaciones() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
