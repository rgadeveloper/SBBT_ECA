/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.Simulacion.Empresa;
import com.ag.Simulacion.Simulado;
import com.ag.dao.Dao;
import com.ag.model.SBalances;
import com.ag.model.SComponente;
import com.ag.model.SHistRelComponente;
import com.ag.model.SHistRelComponenteUbica;
import com.ag.model.SVistaAmarres;
import com.ag.model.TipoComponente;
import com.ag.model.ZonaGeografica;
import com.ag.model.view.Fecha;
import com.ag.service.ConsecutivoManager;
import com.ag.service.SimularBalanceManager;
import com.ag.service.SpringContext;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
//import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */

@Service("SimularBalance")
public class SimularBalanceManagerImpl implements SimularBalanceManager{

    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;  

    @Override
    public List<ZonaGeografica> getZonasByTipo(String tipo) {
        String hql ="Select z from ZonaGeografica z "
                  + "where z.estado='AC001' and "
                  + "z.tipoComponente.idTipoComponente=" + tipo;
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
    public List<Simulado> getHistoricoSim(String fechaIni, String fechaFin) {
        List <Simulado> simulados = new ArrayList<Simulado>();
        String hql="select distinct s.sBalancesPK.idSimulacion from SBalances s "
                + "where trunc(s.fecha)>='"+fechaIni+"' "
                 + "and trunc(s.fecha)<='"+fechaFin+"'";
        
        //recorremos los id para sacar las empresas en esa simulacion
        List data = dao.find(hql);
        if (data != null) {
            for (Iterator it = data.iterator(); it.hasNext();) {
                 Object idSimulacion = (Object) it.next();
                 Simulado s=new Simulado();
                 s.setId(idSimulacion.toString());
                 hql="select distinct z.nombre from SBalances s, ZonaGeografica z "
                         + "where trunc(s.fecha)>='"+fechaIni+"' and "
                               + "trunc(s.fecha)<='"+fechaFin+"'"
                               + " and (s.sBalancesPK.idTipoComponente=0 or s.sBalancesPK.idTipoComponente=4)"
                               + " and s.sBalancesPK.idComponente=z.idZona and s.sBalancesPK.idSimulacion="+idSimulacion;
                 
                 List<Empresa> empresas = new ArrayList<Empresa>();
                 
                 List dataEmpresa = dao.find(hql);
                 if (!dataEmpresa.isEmpty()) {                     
                    
                    for (Iterator ite = dataEmpresa.iterator(); ite.hasNext();) {
                        Object empresa = (Object) ite.next();
                        hql = "select s from SBalances s, ZonaGeografica z "
                            + "where s.sBalancesPK.idComponente=z.idZona and "
                                + "(s.sBalancesPK.idTipoComponente=0 or s.sBalancesPK.idTipoComponente=4) and "
                                + "trunc(s.fecha)>='"+fechaIni+"'"
                                + " and trunc(s.fecha)<='"+fechaFin+"' and "
                                + "s.sBalancesPK.idSimulacion="+idSimulacion
                                + " and z.nombre='"+empresa+"'";                        
                        List<SBalances> balances = dao.find(hql);
                        Iterator iter = balances.iterator();
                        String idComercialElectrico="-";
                        String idComercialGeografico="-";
                        String perdidaElectrico="-";
                        String perdidaGeografico="-";
                        while (iter.hasNext()){
                            SBalances b = (SBalances) iter.next();
                            if(b.getSBalancesPK().getIdTipoComponente()==4){//geografica
                                idComercialGeografico=idComercialEmpresa(b.getSBalancesPK().getIdComponente().toString());
                                perdidaGeografico=b.getPorcPerdidaMes()==null?"-":b.getPorcPerdidaMes().toString();
                            }else{
                                idComercialElectrico=idComercialEmpresa(b.getSBalancesPK().getIdComponente().toString());
                                perdidaElectrico=b.getPorcPerdidaMes()==null?"-":b.getPorcPerdidaMes().toString();
                            } 
                        }
                         Empresa e = new Empresa(empresa.toString(), idComercialElectrico, idComercialGeografico, perdidaElectrico, perdidaGeografico);
                         empresas.add(e);
                    }
                    
                    s.setEmpresas(empresas);
                    simulados.add(s);
                 }                   
            }
                      
        }      
         
        return simulados;
    }
    
    public String idComercialEmpresa(String id){
        String hql = "SELECT z FROM ZonaGeografica z WHERE z.idZona =" + id;
        ZonaGeografica z = dao.findObject(hql);
        String idComercial="-";
        if (z!=null) {
          String codigo= z.getIdZona()==null?"-":z.getIdZona().toString();
          String nombre= z.getNombre()==null?"-":z.getNombre();  
          idComercial=codigo+"-"+nombre;
        }
        
        return idComercial;
    }

    @Override
    public TipoComponente getTipoComponente(String id) {
        String hql = "select t from TipoComponente t"
                   + " where t.idTipoComponente = " + id;
        return dao.findObject(hql);
    }

    @Override
    public SComponente getComponente(String id) {
        String hql =" select c from SComponente c "
                     + " where c.idComponente = " +id;
        return dao.findObject(hql);
    }

    @Override
    public String exportar(String idSimulacion) {
        try {
            Connection con = dao.getConnection();
            
            String sql = "{call P_COPIAR_AMARRES(?,?)}";
            CallableStatement  statement = con.prepareCall(sql);
            
            statement.setInt(1, Integer.parseInt(idSimulacion));           
            
            statement.registerOutParameter(2,Types.VARCHAR);            
                        
            statement.executeQuery();
            
            String error = statement.getString(2);
            con.close();
            return error;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<SHistRelComponente> getHistRelComponente(String idSimulacion) {
        String hql = "select s from SHistRelComponente s " 
                    +"where s.sHistRelComponentePK.idSimulacion = " + idSimulacion;
        return dao.find(hql);
    } 
    
    public List<SHistRelComponenteUbica> getHistRelComponenteUbica(String idSimulacion) {
        String hql = "select s from SHistRelComponenteUbica s " 
                    +"where s.sHistRelComponenteUbicaPK.idSimulacion = " + idSimulacion;
        return dao.find(hql);
    }
    
    public void saveSVistaAmarre(String usuario, String programa, String idPadre, String idHijo, Integer perIni,
                                 Integer perFin, String estPadre, String estHijo, Short idTipoPadre, Short idTipoHijo) {  
        SVistaAmarres sva = new SVistaAmarres();
        sva.setUsuario(usuario);
        sva.setPrograma(programa);
        Fecha fechaSistema=new Fecha();
        sva.setFechaModif(fechaSistema.getFechaSistema());
        sva.setIdPadre(idPadre);
        sva.setIdHijo(idHijo);
        sva.setPeriodoIni(perIni);
        sva.setPeriodoFin(perFin);
        sva.setEstadoPadre(estPadre);
        sva.setEstadoHijo(estHijo);
        sva.setIdTipoPadre(idTipoPadre);
        sva.setIdTipoHijo(idTipoHijo);
        
        SpringContext context = SpringContext.getInstance();
        ConsecutivoManager app = (ConsecutivoManager) context.getBean("ConsecutivoManager");
        String consecutivo = app.getConsecutivo("S_VISTA_AMARRES","MIGRACION","AUTOMATICO","A","0");
        sva.setIdVistaAmarre(new BigDecimal(consecutivo));//obtener id por geracons
        
        dao.persist(sva);
    }
    
    public boolean existeSVA(String idPadre, String idHijo){       
        String hql =" select s from SVistaAmarres s "
                     + " where s.idPadre = " +idPadre
                     + " and s.idHijo = " +idHijo;                     
        SVistaAmarres sva = dao.findObject(hql);
        boolean sw = (sva!=null)?true:false;
        return sw;
    }

    @Override
    public String getMaxPeriodo() {
        String hql ="SELECT max(s.sMedidaPK.periodo) FROM SMedida s";
        return dao.findObject(hql).toString();
    }
    
}
