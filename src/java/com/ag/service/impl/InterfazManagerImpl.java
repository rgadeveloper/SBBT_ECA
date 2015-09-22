package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.service.InterfazManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("InterfazManager")
public class InterfazManagerImpl implements InterfazManager {

  @Autowired
  @Qualifier("DaoHibernate")
  private Dao dao;

  @Override
  public String calcularBalance(int periodo) {
    try {
      Connection con = dao.getConnection();

      String sql = "{call P_CALCULO_BALANCES(?,?)}";
      CallableStatement statement = con.prepareCall(sql);

      statement.setInt(1, periodo);

      statement.registerOutParameter(2, Types.VARCHAR);

      statement.executeQuery();

      String error = statement.getString(2);
      con.close();
      return error;

    } catch (SQLException ex) {
      ex.printStackTrace();
      Logger.getLogger(InterfazManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Error al ejecutar en la BD";
    }
  }

  @Override
  public String lecturas(int periodo, int borrar) {
    try {
      Connection con = dao.getConnection();

      String sql = "{call P_INT_LECTURAS(?,?,?)}";
      CallableStatement statement = con.prepareCall(sql);

      statement.setInt(1, periodo);

      statement.setInt(2, borrar); //preguntar a javier

      statement.registerOutParameter(3, Types.VARCHAR);

      statement.executeQuery();

      String error = statement.getString(3);
      con.close();
      return error;

    } catch (SQLException ex) {
      ex.printStackTrace();
      Logger.getLogger(InterfazManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Error al ejecutar en la BD";
    }
  }

  @Override
  public String ejecutarTodo(int periodo) {
    try {
      Connection con = dao.getConnection();

      String sql = "{call P_INT_EJECUTAR(?,?)}";
      CallableStatement statement = con.prepareCall(sql);

      statement.setInt(1, periodo);

      statement.registerOutParameter(2, Types.VARCHAR);

      statement.executeQuery();

      String error = statement.getString(2);
      con.close();
      return error;

    } catch (SQLException ex) {
      ex.printStackTrace();
      Logger.getLogger(InterfazManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Error al ejecutar en la BD";
    }
  }

  @Override
  public String ceoAsimbal(int periodo) {
    try {
      Connection con = dao.getConnection();

      String sql = "{call P_INT_CEO_SIMBAL(?,?)}";
      CallableStatement statement = con.prepareCall(sql);

      statement.setInt(1, periodo);

      statement.registerOutParameter(2, Types.VARCHAR);

      statement.executeQuery();

      String error = statement.getString(2);
      con.close();
      return error;

    } catch (SQLException ex) {
      ex.printStackTrace();
      Logger.getLogger(InterfazManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Error al ejecutar en la BD";
    }
  }

  @Override
  public int getBorrarLecturas() {
    String Hql = "SELECT p.valor FROM Parametro p WHERE p.idParametro = 'BORRAR'";
    int valor = Integer.valueOf(dao.findObject(Hql).toString());
    return valor;
  }

  @Override
  public String crearArbol(int periodo) {
    try {
      Connection con = dao.getConnection();

      String sql = "{call P_CREAR_ARBOL(?)}";
      CallableStatement statement = con.prepareCall(sql);

      statement.setInt(1, periodo);

      statement.executeQuery();

      String error = statement.getString(2);
      con.close();
      return error;

    } catch (SQLException ex) {
      ex.printStackTrace();
      Logger.getLogger(InterfazManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Error al ejecutar en la BD";
    }
  }

  @Override
  public Object executeProcedimientoAlmacenado(Integer periodo, String situacion) {
    try {
      //   String sql="begin P_CALCULAR_INDICADOR(?,?); end;"; 
      // String sql = "{call P_CALCULAR_INDICADOR(?,?)}";
      Connection con = dao.getConnection();
      String sql = "";

      if (situacion.equals("Transformador")) {
        sql = "begin P_BALANCE_NIV0(?); end;";
      } else if (situacion.equals("Circuito")) {
        sql = "begin P_BALANCE_NIV1(?,3); end;";
      } else if (situacion.equals("Barrio")) {
        sql = "begin P_BALANCE_NIV1(?,7); end;";
      } else if (situacion.equals("Subestación")) {
        sql = "begin P_BALANCE_NIV2(?,3,2); end;";
      } else if (situacion.equalsIgnoreCase("Población")) {
        sql = "begin P_BALANCE_NIV2(?,7,13); end;";
      } else if (situacion.equalsIgnoreCase("Delegación")) {
        sql = "begin P_BALANCE_NIV3(?,3,11); end;";
      } else if (situacion.equals("Municipio")) {
        sql = "begin P_BALANCE_NIV3(?,7,6); end;";
      } else if (situacion.equals("Zona")) {
        sql = "begin P_BALANCE_NIV4(?,3,1); end;";
      } else if (situacion.equalsIgnoreCase("Delegación 2")) {
        sql = "begin P_BALANCE_NIV4(?,7,12); end;";
      } else if (situacion.equals("Empresa")) {
        sql = "begin P_BALANCE_NIV5(?,3,0); end;";
      } else if (situacion.equals("Departamento")) {
        sql = "begin P_BALANCE_NIV5(?,7,5); end;";
      } else {
        sql = "begin P_BALANCE_NIV6(?,7,4); end;";
      }

      CallableStatement statement = con.prepareCall(sql);

      statement.setInt(1, periodo);

      //statement.registerOutParameter(2,Types.VARCHAR);            
      statement.executeQuery();

      //String error = statement.getString(2);
      con.close();
      //if(error.equals("1"))                                       
      return true;
      //else
      //  return sql;

    } catch (SQLException e) {
      e.printStackTrace();
      Logger.getLogger(BalanceManagerImpl.class.getName()).log(Level.SEVERE, null, e);
      return e;
    }
  }

  @Override
  public Connection generarConexion() {
    return dao.getConnection();
  }

  @Override
  public boolean existsPeriodo(String periodo) {
    Session session = null;
    boolean model = false;
    String hql = "from Balances where periodo = :periodo ";
    try {
      session = dao.getSessionHibernate();
      Query query = session.createQuery(hql);
      query.setParameter("periodo", Integer.parseInt(periodo));
      query.setMaxResults(1);
      model = query.uniqueResult() != null;
    } catch (Exception ex) {
      model = false;
      ex.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return model;
  }

  @Override
  public Object validarPeriodo(String periodo) {
    Session session = null;
    Object model = null;
    if (existsPeriodo(periodo)) {
      String hql = "from Balances where cierre = 'A' "
              + " and balancesPK.periodo = :periodo ";
      try {
        session = dao.getSessionHibernate();
        Query query = session.createQuery(hql);
        query.setParameter("periodo", Integer.parseInt(periodo));
        query.setMaxResults(1);
        model = query.uniqueResult();
      } catch (Exception ex) {
        model = null;
        ex.printStackTrace();
      } finally {
        if (session != null) {
          session.close();
        }
      }

    } else {
      model = new String();
    }
    return model;
  }
}
