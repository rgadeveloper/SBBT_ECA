package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.TipoComponente;
import com.ag.model.ZonaGeografica;
//import com.ag.model.*;
import com.ag.model.view.Fecha;
import com.ag.service.ZonaManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("ZonaManager")
public class ZonaManagerImpl implements ZonaManager {

  @Autowired
  @Qualifier("DaoHibernate")
  private Dao dao;

  /*@Override
   public void save(String usuario, String programa, String codigo, String nombre, String tel, String email, String estado, String pass, Date fecha_alta) {
   Usuario user = new Usuario();
   user.setUsuario(usuario);
   user.setPrograma(programa);
   Fecha date = new Fecha();
   user.setFechaModif(date.getFechaSistema());
   user.setFechaAlta(fecha_alta);
   user.setPass(pass);
   user.setCodigo(codigo);
   user.setNombre(nombre);
   user.setTelefono(tel);
   user.setEmail(email); 
   user.setEstado(estado);
   dao.persist(user);
   }    
    
   @Override
   public List<Usuario> getUsuariosAll() {    
   String hql ="Select u from Usuario u order by u.nombre";
   return dao.find(hql);    
   }

   @Override
   public Usuario getUsuario(String codigo) {
   String hql = "SELECT u FROM Usuario u WHERE u.codigo = '" + codigo+"'";
   return dao.findObject(hql);
   }

   @Override
   public void updateUsuario(Usuario usuario) {
   dao.persist(usuario);
   }*/
  @Override
  public List<ZonaGeografica> getZonasAll() {
    String hql = "SELECT z FROM ZonaGeografica z";
    return dao.find(hql);
  }

  @Override
  public ZonaGeografica getZonaGeografica(String id) {
    String hql = "SELECT z FROM ZonaGeografica z WHERE z.idZona =" + id;
    return dao.findObject(hql);
  }

  @Override
  public String getIdPadre(String nombre, String id_tipo_componente) {
    String resultado = null;
    String sql = "select z.id_padre "
            + "from zona_geografica z "
            + "where z.nombre = '" + nombre + "'"
            + "and z.id_tipo_componente = '" + id_tipo_componente + "'";
    Statement statement = null;
    ResultSet rs = null;
    Connection conn = null;
    try {
      conn = dao.getConnectionJDBC();
      statement = conn.createStatement();
      rs = statement.executeQuery(sql);
      while (rs.next()) {
        resultado = rs.getString(1);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        rs.close();
        statement.close();
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }

      return resultado;
    }

  }

  @Override
  public String getNombreZona(String idPadre) {
    String resultado = null;
    String sql = "select z.nombre "
            + "from zona_geografica z "
            + "where z.id_zona = '" + idPadre + "'";
    Statement statement = null;
    ResultSet rs = null;
    Connection conn = null;
    try {
      conn = dao.getConnectionJDBC();
      statement = conn.createStatement();
      rs = statement.executeQuery(sql);
      while (rs.next()) {
        resultado = rs.getString(1);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        rs.close();
        statement.close();
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }

      return resultado;
    }

  }

  @Override
  public void update(ZonaGeografica zona) {
    Fecha date = new Fecha();
    zona.setFechaModif(date.getFechaSistema());
    dao.persist(zona);
  }

  @Override
  public List<TipoComponente> getTiposComponentes() {
    String hql = "SELECT t FROM TipoComponente t";
    return dao.find(hql);
  }

  @Override
  public TipoComponente getTipoComponente(String id) {
    String hql = "SELECT t FROM TipoComponente t WHERE t.idTipoComponente =" + id;
    return dao.findObject(hql);
  }

  @Override
  public void save(String usuario, String programa, BigDecimal idZona, String nombre, BigInteger idPadre, TipoComponente tipoComponente, String coordX, String coordY) {
    ZonaGeografica zona = new ZonaGeografica();
    zona.setUsuario(usuario);
    zona.setPrograma(programa);
    Fecha date = new Fecha();
    zona.setFechaModif(date.getFechaSistema());
    zona.setIdZona(idZona);
    zona.setNombre(nombre);
    zona.setIdPadre(idPadre);
    zona.setTipoComponente(tipoComponente);
    zona.setEstado("AC001");
    zona.setCoordX(coordX);
    zona.setCoordY(coordY);
    dao.persist(zona);
  }

}
