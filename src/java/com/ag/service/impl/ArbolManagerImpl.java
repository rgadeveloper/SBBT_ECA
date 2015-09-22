package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Balances;

import com.ag.model.RangoClasificacion;
import com.ag.model.view.Delegacion;
import com.ag.model.view.Nodo;
import com.ag.model.view.Perdidas;
import com.ag.model.view.TipoComponenteVer;
import com.ag.model.view.UbicacionMacroV;
import com.ag.service.ArbolManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("ArbolManager")
public class ArbolManagerImpl implements ArbolManager {

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

    String hql = "SELECT t.idZona, t.nombre,t.tipoComponente.idTipoComponente,t.coordX,t.coordY,t.tipoComponente.descripcion "
            + " FROM ZonaGeografica t ,  Parametro p"
            + " WHERE t.tipoComponente.idTipoComponente = p.valor "
            + " AND  t.tipoComponente.tbltipo.tipo = '" + nivelGrupo + "' "
            + " AND t.estado = 'AC001' "
            + " and p.idParametro ='" + parametro + "' ";
    hql += " order by t.nombre";
    return obtenerArbol(hql, periodo, null);

  }

  @Override
  public List cargaArbolHijos(String codigoPadre, String tipoPadre, String periodo, HashMap gestionFiltros) {
    int contZona = 0, contUbicacion = 0, contComponente = 0;

        //LOB.20141128.INI 
    // SE COMENTA PARA TRABAJAR CON EL TIPO DE PADRE
        /*
     String hql = "SELECT COUNT(*) "
     + " FROM ZonaGeografica p, ZonaGeografica h "
     + " WHERE h.idPadre = " + codigoPadre
     + " AND p.idZona = h.idPadre"
     + " AND p.tipoComponente.idTipoComponente = " + tipoPadre;
     contZona = Integer.parseInt(String.valueOf(dao.findObject(hql)));
     hql = "SELECT COUNT(*) "
     + " FROM RelComponenteUbicacion r "
     + " WHERE  r.zonaGeografica.idZona =  " + codigoPadre
     + " AND   r.zonaGeografica.tipoComponente.idTipoComponente = " + tipoPadre
     + " AND   r.relComponenteUbicacionPK.periodoIni <=" + periodo
     + " AND   r.periodoFin       > " + periodo;
     contUbicacion = Integer.parseInt(String.valueOf(dao.findObject(hql)));

     hql = "SELECT COUNT(*) "
     + "FROM RelComponente r "
     + " WHERE r.relComponentePK.idComponente =" + codigoPadre
     + " AND r.componente1.tipoComponente.idTipoComponente = " + tipoPadre
     + " AND  r.relComponentePK.periodoIni <= " + periodo
     + " AND r.periodoFin> " + periodo;
     contComponente = Integer.parseInt(String.valueOf(dao.findObject(hql)));
     */
    //es una zona -- 0, 1, 11, 2, 4, 5, 12, 6, 13
    if (tipoPadre.equals("0") || tipoPadre.equals("1") || tipoPadre.equals("11") || tipoPadre.equals("2")
            || tipoPadre.equals("4") || tipoPadre.equals("5") || tipoPadre.equals("12") || tipoPadre.equals("6")
            || tipoPadre.equals("13")) {
      contZona = 1;
    }//Tabla Rel_componente_ubicacion
    else if (tipoPadre.equals("3") || tipoPadre.equals("7")) {
      contUbicacion = 1;
    }//Tabla Rel_componente_
    else {
      contComponente = 1;
    }
    //LOB.20141128.FIN
    String hqlArbol = null;
    if (contZona > 0) {
      hqlArbol = "SELECT  z.idZona, z.nombre, z.tipoComponente.idTipoComponente, z.coordX,z.coordY, z.tipoComponente.descripcion "
              + " FROM ZonaGeografica z "
              + " WHERE z.idPadre =" + codigoPadre + " ";
      hqlArbol += " order by z.nombre";
    } else if (contUbicacion > 0) {

            //LOB.20140802.INI
            /*
       hqlArbol = "SELECT ru.componente.idComponente, ru.componente.nombre,"
       + " ru.componente.tipoComponente.idTipoComponente, "
       + " ru.componente.coordX,ru.componente.coordY,"
       + " ru.componente.tipoComponente.descripcion,"
       + " ru.componente.tbltipo1.nombre,"
       + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=ru.componente.idComponente) as tipoUso," 
       + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=ru.componente.idComponente) as tipoRed "+
       " FROM RelComponenteUbicacion ru "+                        
       " WHERE  ru.zonaGeografica.idZona = " + codigoPadre +
       "  AND  ru.zonaGeografica.tipoComponente.idTipoComponente = " +tipoPadre +
       "  AND  ru.relComponenteUbicacionPK.periodoIni <=" + periodo +
       "  AND  ru.periodoFin                          > " + periodo
       +" order by ru.componente.nombre"; */
      // se agrega el nombre del componente de medida.
      hqlArbol = "SELECT ru.componente.idComponente, "
              + "  ru.componente.nombre,"
              + " ru.componente.tipoComponente.idTipoComponente, "
              + " ru.componente.coordX,ru.componente.coordY,"
              + " ru.componente.tipoComponente.descripcion,"
              + " ru.componente.tbltipo1.nombre,"
              + " tu.tbltipo1.nombre  as tipoUso,"
              + " tr.tbltipo5.tipo as tipoRed, "
              + " rm.componenteMedida.nombre "
              + " FROM RelComponenteUbicacion ru, AtrComponente tu, AtrComponente tr,RelComponenteMedida  rm "
              + " WHERE  ru.zonaGeografica.idZona = " + codigoPadre
              + "  and tu.componente.idComponente=ru.componente.idComponente "
              + "  and tr.componente.idComponente=ru.componente.idComponente "
              + "  AND  ru.zonaGeografica.tipoComponente.idTipoComponente = " + tipoPadre
              + "  AND  ru.relComponenteUbicacionPK.periodoIni <=" + periodo
              + "  AND  ru.periodoFin                          > " + periodo
              + " and rm.componente.idComponente= ru.componente.idComponente "
              + " and    rm.relComponenteMedidaPK.periodoIni <= " + periodo
              + " and   rm.periodoFin > " + periodo
              + " order by ru.componente.nombre";
      /* hqlArbol = "SELECT ru.componente.idComponente, "
       + "  ru.componente.nombre,"
       + " ru.componente.tipoComponente.idTipoComponente, "
       + " ru.componente.coordX,ru.componente.coordY,"
       + " ru.componente.tipoComponente.descripcion,"
       + " ru.componente.tbltipo1.nombre,"
       + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=ru.componente.idComponente) as tipoUso,"
       + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=ru.componente.idComponente) as tipoRed, "
       + "(select  t.componenteMedida.nombre from RelComponenteMedida t "
       + " where t.componente.idComponente= ru.componente.idComponente "
       + " and    t.relComponenteMedidaPK.periodoIni <= " + periodo
       + " and   t.periodoFin > " + periodo + " )"
       + " FROM RelComponenteUbicacion ru "
       + " WHERE  ru.zonaGeografica.idZona = " + codigoPadre
       + "  AND  ru.zonaGeografica.tipoComponente.idTipoComponente = " + tipoPadre
       + "  AND  ru.relComponenteUbicacionPK.periodoIni <=" + periodo
       + "  AND  ru.periodoFin                          > " + periodo
       + " order by ru.componente.nombre";*/
      //LOB.20140802.FIN

    } else if (contComponente > 0) {
      hqlArbol = "SELECT r.relComponentePK.idComponenteHijo, r.componente.idCliente,"
              + " r.componente.tipoComponente.idTipoComponente,"
              + " nvl(r.componente.coordX, 0),nvl(r.componente.coordY, 0),"//Se agrega nvl a la consulta para que pueda agregar el componente al mapa
              + " r.componente.tipoComponente.descripcion,"
              + " r.componente.tbltipo1.nombre,"
              + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
              + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
              + " FROM RelComponente r "
              + " WHERE r.relComponentePK.idComponente =" + codigoPadre
              + "  AND  r.relComponentePK.periodoIni <= " + periodo
              + " AND r.periodoFin> " + periodo
              + " AND r.componente1.tipoComponente.idTipoComponente = " + tipoPadre
              + " order by r.componente.idCliente";
      /*hqlArbol = "SELECT r.relComponentePK.idComponenteHijo, r.componente.idCliente,"
       + " r.componente.tipoComponente.idTipoComponente,"
       + " r.componente.coordX,r.componente.coordY,"
       + " r.componente.tipoComponente.descripcion,"
       + " r.componente.tbltipo1.nombre,"
       + " tu.tbltipo1.nombre ) as tipoUso,"
       + " tr.tbltipo5.tipo  as tipoRed "
       + " FROM RelComponente r , AtrComponente  tu,AtrComponente  tr "
       + " WHERE r.relComponentePK.idComponente =" + codigoPadre
       + " and tu.componente.idComponente=r.componente.idComponente "
       + " and tr.componente.idComponente=r.componente.idComponente"
       + "  AND  r.relComponentePK.periodoIni <= " + periodo
       + " AND r.periodoFin> " + periodo
       + " AND r.componente1.tipoComponente.idTipoComponente = " + tipoPadre
       + " order by r.componente.idCliente";*/

    }
    System.out.println("hqlArbol:" + hqlArbol + "\n" + codigoPadre + " " + tipoPadre);
    if (hqlArbol == null) {
      return null;
    }
    return obtenerArbol(hqlArbol, periodo, gestionFiltros);
  }

  @Override
  public List cargaArbolHijosTrafos(String codigoPadre, String tipoPadre, String periodo, HashMap gestionFiltros) {
    int contTrafosByEmpresa = 0, contTrafosByZona = 0, contTrafosBySubMun = 0, contTrafosByDelegacion = 0;

    String hql = "";
    if (tipoPadre.equals("4") || tipoPadre.equals("0")) { //empresa
      if (tipoPadre.equals("4")) {
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
                + " and z6.idZona = " + codigoPadre + ")";
      } else if (tipoPadre.equals("0")) {
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
                + " and z5.idZona = " + codigoPadre + ")";
      }
      contTrafosByEmpresa = Integer.parseInt(String.valueOf(dao.findObject(hql)));
    } else if (tipoPadre.equals("5") || tipoPadre.equals("1")) { //zona
      if (tipoPadre.equals("5")) {
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
                + " and z5.idZona = " + codigoPadre + ")";
      } else if (tipoPadre.equals("1")) {
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
                + " and z4.idZona = " + codigoPadre + ")";
      }
      contTrafosByZona = Integer.parseInt(String.valueOf(dao.findObject(hql)));
    } else if (tipoPadre.equals("12") || tipoPadre.equals("11")) { //municipio o subestacion
      if (tipoPadre.equals("12")) {
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
                + " and z4.idZona = " + codigoPadre + ")";
      } else if (tipoPadre.equals("11")) {
        hql = " SELECT r.componente FROM RelComponenteUbicacion r "
                + " WHERE r.periodoFin> " + periodo + " and "
                + " r.relComponenteUbicacionPK.idZona in (SELECT z1.idZona "
                + " FROM ZonaGeografica z1, "
                + " ZonaGeografica z2, "
                + " ZonaGeografica z3 "
                + " WHERE z3.idZona = z2.idPadre "
                + " and z2.idZona = z1.idPadre "
                + " and z3.idZona = " + codigoPadre + ")";
      }
      contTrafosByDelegacion = Integer.parseInt(String.valueOf(dao.findObject(hql)));
    } else if (tipoPadre.equals("6") || tipoPadre.equals("2")) { //zona
      if (tipoPadre.equals("6")) {
        hql = " SELECT r.componente FROM RelComponenteUbicacion r "
                + " WHERE r.periodoFin> " + periodo + " and "
                + " r.relComponenteUbicacionPK.idZona in (SELECT z1.idZona "
                + " FROM ZonaGeografica z1, "
                + " ZonaGeografica z2, "
                + " ZonaGeografica z3 "
                + " WHERE z3.idZona = z2.idPadre "
                + " and z2.idZona = z1.idPadre "
                + " and z3.idZona = " + codigoPadre + ")";
      } else if (tipoPadre.equals("2")) {
        hql = " SELECT r.componente FROM RelComponenteUbicacion r "
                + " WHERE r.periodoFin> " + periodo + " and "
                + " r.relComponenteUbicacionPK.idZona in (SELECT z1.idZona "
                + " FROM ZonaGeografica z1, "
                + " ZonaGeografica z2 "
                + " WHERE z2.idZona = z1.idPadre "
                + " and z2.idZona = " + codigoPadre + ")";
      }
      contTrafosBySubMun = Integer.parseInt(String.valueOf(dao.findObject(hql)));
    }

    String hqlArbol = null;
    if (contTrafosByEmpresa > 0) {
      if (tipoPadre.equals("4")) {
        hqlArbol = "SELECT r.componente.idComponente, r.componente.nombre,"
                + " r.componente.tipoComponente.idTipoComponente, "
                + " r.componente.coordX,r.componente.coordY,"
                + " r.componente.tipoComponente.descripcion,"
                + " r.componente.tbltipo1.nombre,"
                + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
                + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
                + " FROM RelComponenteUbicacion r , ZonaGeografica z1,ZonaGeografica z2,ZonaGeografica z3,ZonaGeografica z4, ZonaGeografica z5, ZonaGeografica z6 "
                + " WHERE r.relComponenteUbicacionPK.periodoIni <=" + periodo + " and "
                + " r.periodoFin>" + periodo
                + " and z1.idZona = " + codigoPadre
                + " and z2.idPadre = z1.idZona"
                + " and z3.idPadre = z2.idZona"
                + " and z4.idPadre = z3.idZona"
                + " and z5.idPadre = z4.idZona"
                + " and z6.idPadre = z5.idZona"
                + " and r.relComponenteUbicacionPK.idZona = z6.idZona ";
      } else if (tipoPadre.equals("0")) {
        hqlArbol = "SELECT r.componente.idComponente, r.componente.nombre,"
                + " r.componente.tipoComponente.idTipoComponente, "
                + " r.componente.coordX,r.componente.coordY,"
                + " r.componente.tipoComponente.descripcion,"
                + " r.componente.tbltipo1.nombre,"
                + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
                + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
                + " FROM RelComponenteUbicacion r , ZonaGeografica z1,ZonaGeografica z2,ZonaGeografica z3,ZonaGeografica z4, ZonaGeografica z5 "
                + " WHERE r.relComponenteUbicacionPK.periodoIni <=" + periodo + " and "
                + " r.periodoFin>" + periodo
                + " and z1.idZona = " + codigoPadre
                + " and z2.idPadre = z1.idZona"
                + " and z3.idPadre = z2.idZona"
                + " and z4.idPadre = z3.idZona"
                + " and z5.idPadre = z4.idZona"
                + " and r.relComponenteUbicacionPK.idZona = z5.idZona ";
      }
    } else if (contTrafosByZona > 0) {
      if (tipoPadre.equals("5")) {
        hqlArbol = "SELECT r.componente.idComponente, r.componente.nombre,"
                + " r.componente.tipoComponente.idTipoComponente, "
                + " r.componente.coordX,r.componente.coordY,"
                + " r.componente.tipoComponente.descripcion,"
                + " r.componente.tbltipo1.nombre,"
                + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
                + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
                + " FROM RelComponenteUbicacion r , ZonaGeografica z1,ZonaGeografica z2,ZonaGeografica z3,ZonaGeografica z4, ZonaGeografica z5"
                + " WHERE r.relComponenteUbicacionPK.periodoIni <=" + periodo + " and "
                + " r.periodoFin>" + periodo
                + " and z1.idZona = " + codigoPadre
                + " and z2.idPadre = z1.idZona"
                + " and z3.idPadre = z2.idZona"
                + " and z4.idPadre = z3.idZona"
                + " and z5.idPadre = z4.idZona"
                + " and r.relComponenteUbicacionPK.idZona = z5.idZona ";
      } else if (tipoPadre.equals("1")) {
        hqlArbol = "SELECT r.componente.idComponente, r.componente.nombre,"
                + " r.componente.tipoComponente.idTipoComponente, "
                + " r.componente.coordX,r.componente.coordY,"
                + " r.componente.tipoComponente.descripcion,"
                + " r.componente.tbltipo1.nombre,"
                + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
                + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
                + " FROM RelComponenteUbicacion r , ZonaGeografica z1,ZonaGeografica z2,ZonaGeografica z3,ZonaGeografica z4 "
                + " WHERE r.relComponenteUbicacionPK.periodoIni <=" + periodo + " and "
                + " r.periodoFin>" + periodo
                + " and z1.idZona = " + codigoPadre
                + " and z2.idPadre = z1.idZona"
                + " and z3.idPadre = z2.idZona"
                + " and z4.idPadre = z3.idZona"
                + " and r.relComponenteUbicacionPK.idZona = z4.idZona ";
      }
    } else if (contTrafosByDelegacion > 0) {
      if (tipoPadre.equals("12")) {
        hqlArbol = "SELECT r.componente.idComponente, r.componente.nombre,"
                + " r.componente.tipoComponente.idTipoComponente, "
                + " r.componente.coordX,r.componente.coordY,"
                + " r.componente.tipoComponente.descripcion,"
                + " r.componente.tbltipo1.nombre,"
                + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
                + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
                + " FROM RelComponenteUbicacion r , ZonaGeografica z1,ZonaGeografica z2,ZonaGeografica z3,ZonaGeografica z4"
                + " WHERE r.relComponenteUbicacionPK.periodoIni <=" + periodo + " and "
                + " r.periodoFin>" + periodo
                + " and z1.idZona = " + codigoPadre
                + " and z2.idPadre = z1.idZona"
                + " and z3.idPadre = z2.idZona"
                + " and z4.idPadre = z3.idZona"
                + " and r.relComponenteUbicacionPK.idZona = z4.idZona ";
      } else if (tipoPadre.equals("11")) {
        hqlArbol = "SELECT r.componente.idComponente, r.componente.nombre,"
                + " r.componente.tipoComponente.idTipoComponente, "
                + " r.componente.coordX,r.componente.coordY,"
                + " r.componente.tipoComponente.descripcion,"
                + " r.componente.tbltipo1.nombre,"
                + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
                + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
                + " FROM RelComponenteUbicacion r , ZonaGeografica z1,ZonaGeografica z2,ZonaGeografica z3 "
                + " WHERE r.relComponenteUbicacionPK.periodoIni <=" + periodo + " and "
                + " r.periodoFin>" + periodo
                + " and z1.idZona = " + codigoPadre
                + " and z2.idPadre = z1.idZona"
                + " and z3.idPadre = z2.idZona"
                + " and r.relComponenteUbicacionPK.idZona = z3.idZona ";
      }
    } else if (contTrafosBySubMun > 0) {
      if (tipoPadre.equals("6")) {
        hqlArbol = "SELECT r.componente.idComponente, r.componente.nombre,"
                + " r.componente.tipoComponente.idTipoComponente, "
                + " r.componente.coordX,r.componente.coordY,"
                + " r.componente.tipoComponente.descripcion,"
                + " r.componente.tbltipo1.nombre,"
                + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
                + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
                + " FROM RelComponenteUbicacion r , ZonaGeografica z1,ZonaGeografica z2,ZonaGeografica z3"
                + " WHERE r.relComponenteUbicacionPK.periodoIni <=" + periodo + " and "
                + " r.periodoFin>" + periodo
                + " and z1.idZona = " + codigoPadre
                + " and z2.idPadre = z1.idZona"
                + " and z3.idPadre = z2.idZona"
                + " and r.relComponenteUbicacionPK.idZona = z3.idZona ";
      } else if (tipoPadre.equals("2")) {
        hqlArbol = "SELECT r.componente.idComponente, r.componente.nombre,"
                + " r.componente.tipoComponente.idTipoComponente, "
                + " r.componente.coordX,r.componente.coordY,"
                + " r.componente.tipoComponente.descripcion,"
                + " r.componente.tbltipo1.nombre,"
                + " (SELECT a.tbltipo1.nombre FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoUso,"
                + " (SELECT a.tbltipo5.tipo FROM AtrComponente a WHERE a.componente.idComponente=r.componente.idComponente) as tipoRed "
                + " FROM RelComponenteUbicacion r , ZonaGeografica z1,ZonaGeografica z2 "
                + " WHERE r.relComponenteUbicacionPK.periodoIni <=" + periodo + " and "
                + " r.periodoFin>" + periodo
                + " and z1.idZona = " + codigoPadre
                + " and z2.idPadre = z1.idZona"
                + " and r.relComponenteUbicacionPK.idZona = z2.idZona ";
      }
    }

    if (hqlArbol == null) {
      return null;
    }
    return obtenerArbol(hqlArbol, periodo, gestionFiltros);

    //MOLLEJA
  }

  @Override
  public boolean soloTrafoInGmap(String tipo) {
    if (tipo.equals("4") || tipo.equals("0")
            || tipo.equals("5") || tipo.equals("1")
            || tipo.equals("12") || tipo.equals("11")
            || tipo.equals("6") || tipo.equals("2")) {
      String hql = "SELECT p.valor FROM Parametro p WHERE p.idParametro ='SOLO_COMPONENTES'";
      String valor = dao.findObject(hql).toString();
      return valor.equalsIgnoreCase("si") ? true : false;
    }
    return false;
  }

  public List obtenerArbol(String hql, String periodo, HashMap gestionFiltros) {
    try {
      List elemento = dao.find(hql);
      List hijos = new ArrayList();
      boolean sw_nodo = getMostrarNodo().equalsIgnoreCase("si")
              ? false : true;
      if (elemento != null) {
        for (Iterator it = elemento.iterator(); it.hasNext();) {
          Object[] valores = (Object[]) it.next();
          String codigo = String.valueOf(valores[0]);
          String tipo = ((Short) valores[2]).toString();
          Balances b = getBalances(codigo, tipo, periodo);
          String perdidas = getPerdidas(b);
          String color = getColor(tipo, perdidas);
          if (sw_nodo && !tipo.equals("9") && !tipo.equals("10")) {
            if (perdidas.equals("null")) {
              continue;
            }
            Double porcperd = Double.parseDouble(perdidas);
            if (porcperd > 70 || porcperd < -5) {
              continue;
            }
          }

          //LOB.20140802.INI
          //String nombre = (String) valores[1] ;
          String nombre;
          if (tipo.equals("8")) {
            nombre = (String) valores[1] + " - " + (String) valores[9];
          } else {
            nombre = (String) valores[1];
          }
          //LOB.20140803.FIN  

          String coordX = (String) valores[3];
          String coordY = (String) valores[4];
          //PTORRES.20140514.INI

          if (perdidas.equals("null") && tipo.equals("9")) {
            if (perdidas.equals("null")) {
              perdidas = "";
            }
            Nodo hijo = new Nodo(codigo, nombre, tipo, color, perdidas, coordX, coordY);
            if (b != null) {
              hijo.setNumMacroTot(String.valueOf(b.getTotalMacros()));
              hijo.setNumMacrosFuncionando(String.valueOf(b.getTotalMacrosFunc()));
              hijo.setNumSuministrosFact(String.valueOf(b.getCantSumFact()));
            }
            hijo.setNombreTipo((String) valores[5]);
            hijo.setTipoUso((String) valores[7]);
            hijos.add(hijo);
          } else if (!perdidas.equals("null")) {
            Nodo hijo = new Nodo(codigo, nombre, tipo, color, perdidas, coordX, coordY);
            if (b != null) {
              hijo.setNumMacroTot(String.valueOf(b.getTotalMacros()));
              hijo.setNumMacrosFuncionando(String.valueOf(b.getTotalMacrosFunc()));
              hijo.setNumSuministrosFact(String.valueOf(b.getCantSumFact()));
            }
            hijo.setNombreTipo((String) valores[5]);
            if (tipo.equals("8")) {
              //Gestionar filtros para Trafos
              String usoTrafo = (String) valores[7];
              String redTrafo = (String) valores[8];
              String rangoTrafo = getRango(b);
              if (gestionFiltros != null && !gestionFiltros.isEmpty()) {
                String tipo_uso = (String) gestionFiltros.get("TIPO_USO");
                String tipo_red = (String) gestionFiltros.get("TIPO_RED");
                String rango = (String) gestionFiltros.get("RANGO");
                if (!tipo_uso.equals("NO") && !usoTrafo.equals(tipo_uso)) {
                  continue;
                }
                if (!tipo_red.equals("NO") && !redTrafo.equals(tipo_red)) {
                  continue;
                }
                if (!rango.equals("NO") && !rangoTrafo.equals(rango)) {
                  continue;
                }
              }
              hijo.setLocalizacion((String) valores[6]);
              hijo.setTipoUso(usoTrafo);
              hijo.setNombreColor(getNombreColor(perdidas));
            }
            hijos.add(hijo);
          }
          //PTORRES.20140514.FIN
        }
      }
      return hijos;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  public String getColor(String tipoComponente, String perdidas) {

    String hql = !perdidas.equals("null") ? "select  r "
            + " from RangoClasificacion r  "
            + " where r.tipoComponente.idTipoComponente = " + tipoComponente
            + " and porcMinimo <= " + perdidas
            + " and porcMaximo > " + perdidas
            : //si no tiene balances
            "select  r "
            + " from RangoClasificacion r  "
            + " where r.tipoComponente.idTipoComponente = " + tipoComponente
            + " and porcMinimo is null "
            + " and porcMaximo is null ";
    RangoClasificacion objColor = (RangoClasificacion) dao.findObject(hql);
    String color = "color:rgb(212,208,200)"; //no ha sido asignado ningun rango
    if (objColor != null) {
      color = "color:rgb(" + objColor.getColor().getRojo() + "," + objColor.getColor().getVerde() + "," + objColor.getColor().getAzul() + ")";
    }
    return color;
  }

  public String getNombreColor(String perdidas) {
    String hql = !perdidas.equals("null") ? "select  r "
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

  public Balances getBalances(String codigo, String tipo, String periodo) {
    String hql = " SELECT b FROM Balances b "
            + " WHERE b.balancesPK.idComponente =  " + codigo
            + " and  b.balancesPK.periodo = " + periodo
            + " and  b.balancesPK.idTipoComponente=" + tipo;
    Balances bal = (Balances) dao.findObject(hql);
    return bal;
  }

  public String getPerdidas(Balances bal) {
    String perdidas = "null";
    if (bal != null && bal.getPorcPerdidaMes() != null) {
      perdidas = String.valueOf(bal.getPorcPerdidaMes());
    }
    return perdidas;
  }

  public String getRango(Balances bal) {
    String rango = "null";
    if (bal != null && bal.getRangoClasificacion() != null) {
      rango = bal.getRangoClasificacion().getDescripcion();
    }
    return rango;
  }

  @Override
  public List getZoomMapa() {
    String hql = " SELECT t FROM TipoComponente t ";
    List tip = dao.find(hql);
    return tip;
  }

  @Override
  public List getPeriodo() {
    String hql = " SELECT distinct  b.balancesPK.periodo  from Balances  b "
            + " order by  b.balancesPK.periodo desc";
    List periodo = dao.find(hql);
    List listCombo = new ArrayList();
    for (int i = 0; i < periodo.size(); i++) {
      String per = String.valueOf(periodo.get(i));
      listCombo.add(per);
    }
    return listCombo;
  }

  public static void main(String[] agr) {
    try {
      ArbolManagerImpl app = new ArbolManagerImpl();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String rutaTrafo(String idCliente, String tipoArbol, String periodo) throws SQLException {
    String ruta = idCliente + " no encontrado.";
    String sql = "select emp.nombre, zona.nombre, mun_sub.nombre, bar_cir.nombre, trafo.NOMBRE "
            + "from componente trafo, rel_componente_ubicacion rcu, tipo_componente tipo, "
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
            + "from rel_componente r, "
            + "componente trafo, "
            + "componente sumi, "
            + "rel_componente_ubicacion rcu, "
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
    UbicacionMacroV result = new UbicacionMacroV();
    String cantTrafos = "(select count(*) "
            + "from rel_componente rc "
            + "where rc.id_componente=" + idComponente + " and "
            + "rc.periodo_ini<=" + periodo + " and "
            + "rc.periodo_fin>" + periodo + ")";

    String sql = "select zona.nombre,munsub.nombre,barcir.nombre," + cantTrafos + " "
            + "from rel_componente_ubicacion rcu,"
            + "     zona_geografica barcir,"
            + "     zona_geografica munsub,"
            + "     zona_geografica zona "
            + "where rcu.id_componente=" + idComponente + " and"
            + "      rcu.periodo_ini<=" + periodo + " and "
            + "      rcu.periodo_fin>" + periodo + " and"
            + "      rcu.id_zona=barcir.id_zona and"
            + "      barcir.id_padre=munsub.id_zona and"
            + "      munsub.id_padre=zona.id_zona";
    Statement statement = dao.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(sql);
    if (rs.next()) {
      result.setZona(rs.getString(1));
      result.setMunSub(rs.getString(2));
      result.setBarCir(rs.getString(3));
      result.setCantTrafosAso(rs.getString(4));
    }
    rs.close();
    statement.close();
    return result;
  }

  private String getMostrarNodo() {
    String hql = "SELECT p.valor FROM Parametro p WHERE p.idParametro ='MOSTRAR_INCONSISTENTES'";
    return dao.findObject(hql).toString();
  }

  /* Código  agregado por jose mejia -- 26/07/2014 
   Estós métodos sirven para buscar la ruta del arbol y buscar el historial de un componente
   */
  //select emp.nombre, zona.nombre, mun_sub.nombre, bar_cir.nombre, trafo.nombre, sumi.nombre from rel_componente r, componente trafo, componente sumi, rel_componente_ubicacion rcu, tipo_componente tipo, zona_geografica bar_cir, zona_geografica mun_sub, zona_geografica zona, zona_geografica emp , Atr_Componente atrComp where r.id_componente_hijo=sumi.id_componente  and sumi.id_cliente='587940'  and r.periodo_fin>201403 and r.periodo_ini <=201403 and r.id_componente=trafo.id_componente and sumi.id_componente=r.id_componente_hijo and rcu.id_componente=trafo.id_componente and tipo.id_tipo_componente=bar_cir.id_tipo_componente  and tipo.tipo_arbol='NIV100' and bar_cir.id_zona=rcu.id_zona and mun_sub.id_zona=bar_cir.id_padre and zona.id_zona=mun_sub.id_padre and emp.id_zona=zona.id_padre
    /* INI AAMC 20140807 */
  @Override
  public String rutaView(String tipoComponente, String tipoArbol, String periodo, String idNis, String idPlaca) throws SQLException {

    String ruta = " NO ENCONTRADO.";
    String sql = "";
    String idContrato = null;

    if (tipoComponente.equals("Suministro")) {

      //Se busca inicialmente si el suministro pertenece a un trafo sin balance
      BigDecimal contrato = idContrato(Integer.parseInt(idNis));
      idContrato = contrato.equals(BigDecimal.ZERO) ? "-1" : contrato == null ? "-2" : String.valueOf(contrato);

      //Tipo 8
      sql = "select z5.nombre as n1,emp.nombre as n2, zona.nombre as n3, mun_sub.nombre as n4, bar_cir.nombre as n5, trafo.nombre as n6, sumi.nombre as n7,"
              + " z5.id_zona||z5.id_tipo_componente||';'||emp.id_zona||emp.id_tipo_componente||';'|| zona.id_zona||zona.id_tipo_componente||';'|| mun_sub.id_Zona||mun_sub.id_tipo_componente||';'|| bar_cir.id_zona||bar_cir.id_tipo_componente||';'||trafo.id_componente|| trafo.id_tipo_componente||';'||sumi.id_componente|| sumi.id_tipo_componente as claves \n"
              + "from rel_componente r, componente trafo, componente sumi, rel_componente_ubicacion rcu, \n"
              + "tipo_componente tipo, zona_geografica bar_cir, zona_geografica mun_sub, zona_geografica zona, \n"
              + "zona_geografica emp,zona_geografica z5 , atr_componente atr\n"
              + "where r.id_componente_hijo=sumi.id_componente  \n";

      sql += "and r.periodo_fin >" + periodo + " and r.periodo_ini <=" + periodo + " \n"
              + "and r.id_componente=trafo.id_componente \n"
              + "and rcu.id_componente=trafo.id_componente \n"
              + "and tipo.id_tipo_componente=bar_cir.id_tipo_componente  \n"
              + "and tipo.tipo_arbol='" + tipoArbol + "' \n"
              + "and bar_cir.id_zona=rcu.id_zona \n"
              + "and mun_sub.id_zona=bar_cir.id_padre \n"
              + "and zona.id_zona=mun_sub.id_padre \n"
              + "and emp.id_zona=zona.id_padre\n"
              + "and z5.id_zona=emp.id_padre\n"
              + "and atr.id_componente = sumi.id_componente\n"
              + "and atr.contrato = " + idContrato + "";

    } else if (tipoComponente.equals("Trafo")) {
      //Tipo 8
      sql = "select z5.nombre as n1 ,emp.nombre as n2, zona.nombre as n3 , mun_sub.nombre as n4, bar_cir.nombre as n5, "
              + "trafo.nombre as n6,' ' as n7,  \n"
              + " z5.id_zona||z5.id_tipo_componente||';'||emp.id_zona||emp.id_tipo_componente||';'"
              + "|| zona.id_zona||zona.id_tipo_componente||';'|| mun_sub.id_Zona||mun_sub.id_tipo_componente||';'"
              + "|| bar_cir.id_zona||bar_cir.id_tipo_componente||';'||trafo.id_componente||trafo.id_tipo_componente claves\n"
              + "from  componente trafo, rel_componente_ubicacion rcu, \n"
              + "tipo_componente tipo, zona_geografica bar_cir, zona_geografica mun_sub, zona_geografica zona, \n"
              + "zona_geografica emp,zona_geografica z5 \n"
              + "where bar_cir.id_zona = rcu.id_zona \n";

      sql += "and rcu.periodo_fin>" + periodo + " and rcu.periodo_ini <=" + periodo + " \n"
              + "and rcu.id_componente=trafo.id_componente \n"
              + "and tipo.id_tipo_componente=bar_cir.id_tipo_componente  \n"
              + "and tipo.tipo_arbol='" + tipoArbol + "' \n"
              + "and mun_sub.id_zona=bar_cir.id_padre \n"
              + "and zona.id_zona=mun_sub.id_padre \n"
              + "and emp.id_zona=zona.id_padre\n"
              + "and z5.id_zona=emp.id_padre\n"
              + "and trafo.id_cliente = '" + idPlaca + "'\n";

    }

    try {
      //  int c=0;
      //Modo lectura, optimiza la base de datos
      System.out.println("Lanza el querie");
      List l = dao.executeQuerie(sql);
      System.out.println("Lanzó el querie");
      if (l.size() > 0) {
        System.out.println("Lanzó el querie + size " + l.size());
        String rutaReturn = "";
        for (int i = 0; i < l.size(); i++) {
          Object[] row = (Object[]) l.get(i);
          try {
            String n1 = getString(row[0]);
            String n2 = getString(row[1]);
            String n3 = getString(row[2]);
            String n4 = getString(row[3]);
            String n5 = getString(row[4]);
            String n6 = getString(row[5]);
            String n7 = getString(row[6]);
            String claves = getString(row[7]);
            rutaReturn = n1 + "/" + n2 + "/" + n3 + "/" + n4 + "/" + n5 + "/" + n6 + "/" + n7;
            ruta = rutaReturn + ";" + claves;

            break;
          } catch (Exception ex) {
            Logger.getLogger(ArbolManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
          }

        }
        System.out.println("Se termino correctamente sin errores " + ruta);
      }
    } catch (Exception ex) {
      Logger.getLogger(ArbolManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (idContrato == "-1") {
      ruta = " SIN BALANCE.";
    }

    return ruta;
  }

  //Retorna el NIC asociado al NIS pasado como parámetro
  /*Cuando se buscaba un suministro con trafo sin balance no mostraba ningún msj, si el NIC encontrado es 0
   el suministro no tiene balance.*/
  private BigDecimal idContrato(int idNis) {
    String sql = "select s.nic from sumi_sgc s where s.nis_rad = " + idNis;
    List lista = null;
    Session session = null;
    BigDecimal idContrato = BigDecimal.ZERO;
    try {
      session = dao.getSessionHibernate();
      lista = dao.executeQuerie(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }

    try {
      idContrato = (BigDecimal) lista.get(0);
    } catch (Exception ex) {
      idContrato = null;
    }

    return idContrato;
  }

  @Override
  public String rutaViewGeografica(String tipoComponente, String tipoArbol, String periodo, String idNis, String idPlaca) throws SQLException {
    String ruta = " NO ENCONTRADO.";
    String sql = "";
    String idContrato = null;
    if (tipoComponente.equals("Suministro")) {

      //Se busca inicialmente si el suministro pertenece a un trafo sin balance
      BigDecimal contrato = idContrato(Integer.parseInt(idNis));
      idContrato = contrato.equals(BigDecimal.ZERO) ? "-1" : contrato == null ? "-2" : String.valueOf(contrato);

      //Tipo 8
      sql = "select z6.nombre as n1,z5.nombre as n2,emp.nombre as n3, zona.nombre as n4, mun_sub.nombre as n5, bar_cir.nombre as n6, trafo.nombre as n7, sumi.nombre as n8,"
              + " z6.id_zona||z6.id_tipo_componente||';'||z5.id_zona||z5.id_tipo_componente||';'||emp.id_zona||emp.id_tipo_componente||';'|| zona.id_zona||zona.id_tipo_componente||';'|| mun_sub.id_Zona||mun_sub.id_tipo_componente||';'|| bar_cir.id_zona||bar_cir.id_tipo_componente||';'||trafo.id_componente|| trafo.id_tipo_componente||';'||sumi.id_componente|| sumi.id_tipo_componente as claves \n"
              + "from rel_componente r, componente trafo, componente sumi, rel_componente_ubicacion rcu, \n"
              + "tipo_componente tipo, zona_geografica bar_cir, zona_geografica mun_sub, zona_geografica zona, \n"
              + "zona_geografica emp,zona_geografica z5,zona_geografica z6, atr_componente atr \n"
              + "where r.id_componente_hijo=sumi.id_componente  \n";

      sql += "and r.periodo_fin>" + periodo + " and r.periodo_ini <=" + periodo + " \n"
              + "and r.id_componente=trafo.id_componente \n"
              + "and rcu.id_componente=trafo.id_componente \n"
              + "and tipo.id_tipo_componente=bar_cir.id_tipo_componente  \n"
              + "and tipo.tipo_arbol='" + tipoArbol + "' \n"
              + "and bar_cir.id_zona=rcu.id_zona \n"
              + "and mun_sub.id_zona=bar_cir.id_padre \n"
              + "and zona.id_zona=mun_sub.id_padre \n"
              + "and emp.id_zona=zona.id_padre\n"
              + "and z5.id_zona=emp.id_padre\n"
              + "and z6.id_zona=z5.id_padre\n"
              + "and atr.id_componente = sumi.id_componente\n"
              + "and atr.contrato = " + idContrato + "";

    } else if (tipoComponente.equals("Trafo")) {
      //Tipo 8
      sql = "select z6.nombre as n1,z5.nombre as n2,emp.nombre as n3, zona.nombre as n4, mun_sub.nombre as n5, bar_cir.nombre as n6, trafo.nombre as n7,' ' as  n8,  \n"
              + " z6.id_zona||z6.id_tipo_componente||';'||z5.id_zona||z5.id_tipo_componente||';'"
              + "||emp.id_zona||emp.id_tipo_componente||';'|| zona.id_zona||zona.id_tipo_componente||';'"
              + "|| mun_sub.id_Zona||mun_sub.id_tipo_componente||';'|| bar_cir.id_zona||bar_cir.id_tipo_componente||';'"
              + "||trafo.id_componente||trafo.id_tipo_componente claves\n"
              + "from  componente trafo, rel_componente_ubicacion rcu, \n"
              + "tipo_componente tipo, zona_geografica bar_cir, zona_geografica mun_sub, zona_geografica zona, \n"
              + "zona_geografica emp,zona_geografica z5,zona_geografica z6 \n"
              + "where bar_cir.id_zona = rcu.id_zona \n";

      sql += "and rcu.periodo_fin>" + periodo + " and rcu.periodo_ini <=" + periodo + " \n"
              + "and rcu.id_componente=trafo.id_componente \n"
              + "and tipo.id_tipo_componente=bar_cir.id_tipo_componente  \n"
              + "and tipo.tipo_arbol='" + tipoArbol + "' \n"
              + "and mun_sub.id_zona=bar_cir.id_padre \n"
              + "and zona.id_zona=mun_sub.id_padre \n"
              + "and emp.id_zona=zona.id_padre\n"
              + "and z5.id_zona=emp.id_padre\n"
              + "and z6.id_zona=z5.id_padre\n"
              + "and trafo.id_cliente = '" + idPlaca + "'\n";
    }

    try {
      //Modo lectura, optimiza la base de datos
      System.out.println("Lanza el querie");
      List l = dao.executeQuerie(sql);
      System.out.println("Lanzó el querie");
      if (l.size() > 0) {
        System.out.println("Lanzó el querie + size " + l.size());
        String rutaReturn = "";
        for (int i = 0; i < l.size(); i++) {
          Object[] row = (Object[]) l.get(i);
          try {
            String n1 = getString(row[0]);
            String n2 = getString(row[1]);
            String n3 = getString(row[2]);
            String n4 = getString(row[3]);
            String n5 = getString(row[4]);
            String n6 = getString(row[5]);
            String n7 = getString(row[6]);
            String n8 = getString(row[7]);
            String claves = getString(row[8]);
            rutaReturn = n1 + "/" + n2 + "/" + n3 + "/" + n4 + "/" + n5 + "/" + n6 + "/" + n7 + "/" + n8;
            ruta = rutaReturn + ";" + claves;

            break;
          } catch (Exception ex) {
            Logger.getLogger(ArbolManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
          }

        }
        System.out.println("Se termino correctamente sin errores " + ruta);
      }
    } catch (Exception ex) {
      Logger.getLogger(ArbolManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (idContrato == "-1") {
      ruta = " SIN BALANCE.";
    }

    return ruta;
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
  public List<Perdidas> buscarPerdida(BigDecimal periodo) {
    try {
      String sql = "select row_number() over (order by periodo) as rank,periodo, rango_balance, r.descripcion RANGOS, t.nombre TIPOPCI,\n"
              + "decode(rango_balance, 31,0, sum(nvl(consumo_macros,0)) - sum(nvl(consumo_suministro,0))- sum(nvl(consumo_adicional,0))) perdidas_kw_mes,\n"
              + "count(b.id_componente) cantidad\n"
              + "from balances b, rango_clasificacion r, rel_componente_medida r, componente_medida c, tbltipo t\n"
              + "where b.id_tipo_componente = 3\n"
              + "and rango_balance = id_rango(+)\n"
              + "and b.id_componente = r.id_componente\n"
              + "and r.id_componente_medida = c.id_componente_medida\n"
              + "and r.periodo_fin = 999912\n"
              + "and c.tipo_medidor = t.tipo\n"
              + "and periodo <= " + periodo + "\n"
              + "and periodo >= " + periodo + " -12\n"
              + "group by periodo, rango_balance, r.descripcion, t.nombre\n"
              + "order by periodo, rango_balance";

      Statement statement = dao.getConnection().createStatement();
      ResultSet rs = statement.executeQuery(sql);
      List<Perdidas> perdida = new ArrayList();
      Perdidas p;
      while (rs.next()) {
        p = new Perdidas();
        p.setRANK(rs.getBigDecimal(1));
        p.setPERIODO(rs.getBigDecimal(2));
        p.setRANGO_BALANCE(rs.getBigDecimal(3));
        p.setRANGOS(rs.getString(4));
        p.setTIPOPCI(rs.getString(5));
        p.setPERDIDAS_KW_MES(rs.getBigDecimal(6));
        p.setCANTIDAD(rs.getBigDecimal(7));
        perdida.add(p);
      }
      rs.close();
      statement.close();
      return perdida;
    } catch (SQLException ex) {

      Logger.getLogger(ArbolManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }

  @Override
  public List<Delegacion> buscarDelegaciones() {
    try {
      List<Delegacion> delegacion = new ArrayList();
      String sql = "select delega.id_zona,delega.nombre,zona.nombre Zona\n"
              + "                       from zona_geografica delega, zona_geografica zona\n"
              + "                       where delega.id_tipo_componente=11\n"
              + "                       and   delega.id_padre = zona.id_zona\n"
              + "                       and delega.estado = 'AC001'\n"
              + "                       order by Zona";

      Statement statement = dao.getConnection().createStatement();
      ResultSet rs = statement.executeQuery(sql);
      Delegacion p;
      while (rs.next()) {
        delegacion.add(new Delegacion(rs.getBigDecimal(1), rs.getString(2), rs.getString(3)));
      }
      rs.close();
      statement.close();
      return delegacion;
    } catch (SQLException ex) {
      Logger.getLogger(ArbolManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }

  }

  /*@Override
   public List<String> tipoComponenteList() {
   boolean error=false;
   try{
   List<String> string = new ArrayList();
   string =(List<String>) dao.findObject("Select Distinct t.tbltipo.nombre From TipoComponente t");
   return string;
   }catch (Exception e){
   e.printStackTrace();
   }
         
   //  int c=0;
   //Modo lectura, optimiza la base de datos
   //List<String> string = new ArrayList();
   List l = dao.executeQuerie("Select Distinct Tipo_Arbol From Tipo_Componente");
   return (List<String>)l;
            
   /*if (l.size() > 0) {
   for (int i = 0; i < l.size(); i++) {
   Object[] row = (Object[]) l.get(i);
   try {
   String n1 = getString(row[0]);
                       
   } catch (Exception ex) {
   Logger.getLogger(ArbolManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
   }
   }
   }    
   }*/
}
