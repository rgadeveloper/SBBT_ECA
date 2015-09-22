/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Estado;
import com.ag.model.SComponente;
import com.ag.model.SMedida;
import com.ag.model.SRelComponente;
import com.ag.model.SRelComponentePK;
import com.ag.model.SRelComponenteUbicacion;
import com.ag.model.SRelComponenteUbicacionPK;
import com.ag.model.ZonaGeografica;
//import com.ag.model.*;
import com.ag.model.view.Fecha;
import com.ag.service.SArchivoManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.*;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("SArchivoManager")
public class SArchivoManagerImpl implements SArchivoManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    
    private String mensaje,estado;
    private int procesados,totalRegistros;
    
    @Override
    public String[] cargaConsumoMasivo(String usuario, String programa, InputStream inputStream) {
        mensaje = "";
        estado = "exitoso";
        procesados = 0;
        totalRegistros = 0;
        String [] respuesta = new String[3];
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));         
            String line = in.readLine();
            String idCliente, consumoFacturado, diasFacturado;
            String[] columna;
            while (line != null) {
                columna=line.split(";");                
                idCliente=columna[1];
                consumoFacturado=columna[2];//
                diasFacturado=columna[3];
                if (columna[0].equalsIgnoreCase("C")) {
                    if (consumoFacturado.contains("-")) {
                        mensaje += "*** No se permite consumo negativo: "+consumoFacturado+" para el Suministro: "+idCliente+"<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue; 
                    }
                    SComponente sumin = getComponenteByIdCliente(idCliente);
                    if (sumin == null) {
                        mensaje += "*** El Suministro: " + idCliente + " No existe en la BD Simulacion<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }else if(!sumin.getEstado().getIdEstado().equals("AC001")){
                        mensaje += "*** El Suministro: " + idCliente + " se encuentra inactivo<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }
                    if (diasFacturado.isEmpty()) {
                        mensaje += "*** No se permite dias facturados nulo o vacio para el Cliente: "+idCliente+"<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue; 
                    }
                    if (!validarConsumo(sumin, consumoFacturado)) {
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }
                    actConsumoSumin(usuario, programa, sumin, consumoFacturado, diasFacturado);
                    procesados++;
                } else if(columna[0].equalsIgnoreCase("M")){
                    String cargaExtra = columna[4];
                    if (cargaExtra.contains("-")) {
                        mensaje += "*** No se permite carga extra negativa: "+cargaExtra+" para el Trafo: "+idCliente+"<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue; 
                    }
                    if (consumoFacturado.contains("-")) {
                        mensaje += "*** No se permite consumo negativo: "+consumoFacturado+" para el Trafo: "+idCliente+"<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue; 
                    }
                    if (diasFacturado.isEmpty()) {
                        mensaje += "*** No se permite dias facturados nulo o vacio para el Trafo: "+idCliente+"<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue; 
                    }
                    SComponente trafo = getComponenteByIdCliente(idCliente);                    
                    if (trafo == null) {
                        mensaje += "*** El Trafo: " + idCliente + " No existe en la BD Simulacion<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }else if(!trafo.getEstado().getIdEstado().equals("AC001")){
                        mensaje += "*** El Trafo: " + idCliente + " se encuentra inactivo<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }
                    actConsumoTrafo(usuario, programa, trafo, consumoFacturado, diasFacturado, cargaExtra);
                    procesados++;
                }else{
                   mensaje += "*** El Tipo: " + columna[0] + " no está permitido<br>";
                   estado = "warning";                   
                } 
                line = in.readLine();                 
            }         
                in.close();
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al cargar el archivo, " + e.toString();
            estado = "errado";
        }
        respuesta[0] = mensaje;
        respuesta[1] = estado;
        respuesta[2] = "Total Registros:"+totalRegistros+" Procesados: " + procesados + " revise el Log";
        return respuesta;
    }

    @Override
    public String[] cargaAmarreMasivo(String usuario, String programa, InputStream inputStream) {
        mensaje = "";
        estado = "exitoso";
        procesados = 0;
        totalRegistros = 0;
        String [] respuesta = new String[3];
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));         
            String line = in.readLine();
            String tipoAmarre, idClientePadre, idClienteHijo, periodo;
            String[] columna;
            while (line != null) {
                columna=line.split(";");
                tipoAmarre=columna[0];
                idClientePadre=columna[1];
                idClienteHijo=columna[2];
                periodo=getPeriodoActualSimulacion();
                //idPadre=getIdComponenteByIdCliente(columna[1]);
                //idHijo=getIdComponenteByIdCliente(columna[2]);
                //periodo=columna[3];//debe ser el periodo que se esta simulando

                if (tipoAmarre.equals("1") || tipoAmarre.equals("3")){
                    //mover trafo a otro circuito o barrio                    
                    SComponente trafo = getComponenteByIdCliente(idClienteHijo);
                    if (trafo == null) {
                        mensaje += "*** El Transformador: " + idClienteHijo + " No existe en la BD Simulacion<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }else if(!trafo.getEstado().getIdEstado().equals("AC001")){
                        mensaje += "*** El Transformador: " + idClienteHijo + " se encuentra inactivo<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }
                    
                    String tipoZona=tipoAmarre.equals("1")?"Circuito":"Barrio";
                    ZonaGeografica barCir = getZona(idClientePadre,tipoZona); 
                    if (barCir == null) {
                        mensaje += "*** El "+tipoZona+" con id: " + idClientePadre + " No existe en la BD Simulacion<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }else if(!barCir.getEstado().equals("AC001")){
                        mensaje += "*** El "+tipoZona+" con id: " + idClientePadre + " se encuentra inactivo<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }   
                    
                    crearRelTrafoBarCir(usuario, programa, tipoZona, barCir, trafo, periodo);
                    procesados++;                                 
                }else if (tipoAmarre.equals("2") || tipoAmarre.equals("4")){
                    //mover suministro a otro trafo
                    SComponente trafo = getComponenteByIdCliente(idClientePadre);
                    if (trafo == null) {
                        mensaje += "*** El Transformador: " + idClientePadre + " No existe en la BD Simulacion<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }else if(!trafo.getEstado().getIdEstado().equals("AC001")){
                        mensaje += "*** El Transformador: " + idClientePadre + " se encuentra inactivo<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }
                    
                    SComponente sumin = getComponenteByIdCliente(idClienteHijo);
                    if (sumin == null) {
                        mensaje += "*** El Suministro: " + idClienteHijo + " No existe en la BD Simulacion<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }else if(!sumin.getEstado().getIdEstado().equals("AC001")){
                        mensaje += "*** El Suministro: " + idClienteHijo + " se encuentra inactivo<br>";
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    } 
                    if (!validarMunicipio(sumin, trafo)) {
                        line = in.readLine();
                        estado = "warning";
                        continue;
                    }
                    crearRelSuminTrafo(usuario, programa, trafo, sumin, periodo);
                    procesados++;
                }else{
                   mensaje += "*** El Tipo: " + columna[0] + " no está permitido<br>";
                   estado = "warning";   
                }
                line = in.readLine();
            }         
                in.close();
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al cargar el archivo, " + e.toString();
            estado = "errado";
        }
        respuesta[0] = mensaje;
        respuesta[1] = estado;
        respuesta[2] = "Total Registros:"+totalRegistros+" Procesados: " + procesados + " revise el Log";
        return respuesta;
    }
    
    public void actConsumoTrafo(String usuario, String programa, SComponente trafo, String consumoFacturado, String diasFacturado, String consumoAdicional) {
        String periodo=getPeriodoActualSimulacion();
        SMedida m = getMedida(trafo.getIdComponente().toString(), periodo, "8");
        if (m!=null) {
            m.setUsuario(usuario);
            m.setPrograma(programa);
            m.setConsumoFacturado(new BigDecimal(consumoFacturado));
            m.setDiasFacturados(new Short(diasFacturado));
            m.setConsumoAdicional(new BigDecimal(consumoAdicional));
            Fecha fechaModif = new Fecha();
            m.setFechaModif(fechaModif.getFecha());
            dao.persist(m);
            mensaje += "- Actualizado consumo para Trafo: " + trafo.getIdCliente() +"<br>";
        }
    }
    
    public void actConsumoSumin(String usuario, String programa, SComponente sumin, String consumoFacturado, String diasFacturado) {
        String periodo=getPeriodoActualSimulacion();
        SMedida m = getMedida(sumin.getIdComponente().toString(), periodo, "9");
        if (m!=null) {
            m.setUsuario(usuario);
            m.setPrograma(programa);
            m.setConsumoFacturado(new BigDecimal(consumoFacturado));
            m.setDiasFacturados(new Short(diasFacturado));           
            Fecha fechaModif = new Fecha();
            m.setFechaModif(fechaModif.getFecha());
            dao.persist(m);
            mensaje += "- Actualizado consumo para Suministro: " + sumin.getIdCliente() +"<br>";
        }
    }
    
    public boolean validarConsumo(SComponente sumin, String consumoFacturado){
        SRelComponente rel=getRelSuminTrafoActual(sumin.getIdComponente().toString());
        if (rel!=null) {            
            double cargaInstalada=getCargaInstalada(rel.getSRelComponentePK().getIdComponente().toString());
            double consumoCliente=getConsumoClientes(rel.getSRelComponentePK().getIdComponente().toString(), sumin.getIdComponente().toString());
            consumoCliente=consumoCliente+new Double(consumoFacturado);
            if (consumoCliente>cargaInstalada) {
                mensaje += "*** El consumo: "+consumoFacturado+" para el Suministro: "+ sumin.getIdCliente() +"<br> &nbsp;&nbsp;&nbsp; supera la carga instalada del Trafo: "+rel.getSComponente1().getIdCliente() +"<br>";
                return false;
            } else {
                return true;
            }            
        } else {
            mensaje += "*** El Suministro: " + sumin.getIdCliente() +" no tiene relacion con algun Trafo<br>";
            return false;
        }
    }
    
    public boolean validarMunicipio(SComponente sumin, SComponente trafo){
        SRelComponente rel=getRelSuminTrafoActual(sumin.getIdComponente().toString());
        if (rel!=null) {
            ZonaGeografica municipioSumin=getMunicipioByTrafo(rel.getSComponente1().getIdComponente().toString());
            ZonaGeografica municipioTrafo=getMunicipioByTrafo(trafo.getIdComponente().toString());
            if (!municipioSumin.equals(municipioTrafo)) {
               mensaje += "*** El Trafo: " + trafo.getIdCliente() +" no pertenece al Municipio: "+municipioSumin.getNombre()+"<br> &nbsp;&nbsp;&nbsp; que es donde se encuentra el Suministro: "+sumin.getIdCliente()+"<br>";
               return false; 
            } else {
                return true;
            }
        } else {
            mensaje += "*** El Suministro: " + sumin.getIdCliente() +" no tiene relacion con algun Trafo<br>";
            return false;
        }                
    }
    
    public SMedida getMedida(String idComponente, String periodo, String idTipoCompo){
        String hql = "select m from SMedida m"
                   + " where m.sMedidaPK.idComponente = " + idComponente 
                   + " and m.sMedidaPK.periodo = " + periodo
                   + " and m.sMedidaPK.idTipoComponente = "+idTipoCompo;
        return dao.findObject(hql);
    }
    
    public double getCargaInstalada(String idTrafo){
        double cargaInstalada=0.0;
        String hql = "SELECT s.cargaInstalada FROM SAtrComponente s "
                   + "WHERE s.periodoFin=999912 "
                         + "and s.sComponente.idComponente="+idTrafo;
        Object carga= dao.findObject(hql);       
        if (carga!=null) {
            cargaInstalada=new Double(carga.toString()); //medida en Kwh/mes
            cargaInstalada=cargaInstalada*24.0*30.0;
        }
        return cargaInstalada;
    }
    
    public double getConsumoClientes(String idTrafo, String idSumin){        
        String hql = "SELECT sum(m.consumoFacturado) FROM SMedida m "
                   + "WHERE m.sMedidaPK.idComponente in "
                           + "(SELECT s.sRelComponentePK.idComponenteHijo"
                           + " FROM SRelComponente s"
                           + " WHERE s.sRelComponentePK.idComponente="+idTrafo 
                                + " and s.sRelComponentePK.idComponenteHijo<>"+idSumin
                                + " and s.periodoFin=999912)";
        Object total= dao.findObject(hql);       
        return total!=null?new Double(total.toString()):0.0;        
    }  
    
    public ZonaGeografica getMunicipioByTrafo(String idTrafo){
        String hql="SELECT mun FROM SRelComponenteUbicacion rcu, ZonaGeografica mun "
                 + "WHERE rcu.sRelComponenteUbicacionPK.idComponente="+idTrafo
                       + "and rcu.periodoFin=999912 "
                       + "and rcu.zonaGeografica.tipoComponente.idTipoComponente=7 "
                       + "and rcu.zonaGeografica.idPadre=mun.idZona";  
        return dao.findObject(hql);
    }
    
    public SComponente getComponenteByIdCliente(String idCliente) {
        String hql="SELECT s FROM SComponente s "
                 + "WHERE s.idCliente ='"+idCliente+"'";  
        return dao.findObject(hql);
    }
    
    public ZonaGeografica getZona(String idComercialZona, String tipoZona){
        tipoZona=tipoZona.equals("Circuito")?"3":"7";
        String hql="SELECT z FROM ZonaGeografica z WHERE z.idComercial ='"+idComercialZona+
                   "' and z.tipoComponente.idTipoComponente="+tipoZona;
        return dao.findObject(hql);
    }  
    
    public SRelComponenteUbicacion getRelTrafoBarCirActual(String idTrafo, String tipo) {
        String hql = "SELECT s FROM SRelComponenteUbicacion s "+ 
                     "WHERE s.periodoFin = 999912 "+
                     "and s.sRelComponenteUbicacionPK.idComponente ="+ idTrafo +
                     " and s.zonaGeografica.tipoComponente =" + tipo;              
        return dao.findObject(hql);
    }   

    public SRelComponente getRelSuminTrafoActual(String idSuministro){        
        String hql = "SELECT s FROM SRelComponente s "+ 
                     "WHERE s.periodoFin = 999912 "+
                     "and s.sRelComponentePK.idComponenteHijo ="+ idSuministro;              
        return dao.findObject(hql);   
    }
    
    public Estado getEstado(String idEstado){
        String hql="SELECT e FROM Estado e WHERE e.idEstado ='"+idEstado+"'";
        return dao.findObject(hql);
    }  
    
    public void crearRelTrafoBarCir(String usuario,String programa, String tipoZona, ZonaGeografica barCir, SComponente trafo, String periodo){
        SRelComponenteUbicacion relacionActual= tipoZona.equals("Circuito")?
                                    getRelTrafoBarCirActual(trafo.getIdComponente().toString(),"3"):
                                    getRelTrafoBarCirActual(trafo.getIdComponente().toString(),"7");
               
        if (relacionActual!=null) {
            String padreActual=relacionActual.getZonaGeografica().getIdZona().toString();
            String padreNuevo=barCir.getIdZona().toString();
            if (!padreActual.equals(padreNuevo)) {
                relacionActual.setPeriodoFin(Integer.parseInt(periodo));
                relacionActual.setUsuario(usuario);
                relacionActual.setPrograma(programa);
                relacionActual.setFechaModif(new Fecha().getFecha());
                dao.persist(relacionActual);
                mensaje += "- Terminada relación entre Trafo: " + relacionActual.getSComponente().getIdCliente() + 
                            " y el "+tipoZona+": "+ relacionActual.getZonaGeografica().getNombre()+"<br>";
                nuevaRelTrafoBarCir(usuario, programa, tipoZona, barCir, trafo, periodo);
            } else {
                mensaje += "- Ya existe la relación entre Trafo: " + trafo.getIdCliente() + 
                            " y el "+tipoZona+": "+ barCir.getNombre()+"<br>";
            }  
        }else{
            nuevaRelTrafoBarCir(usuario, programa, tipoZona, barCir, trafo, periodo);
        }   
    }
    
    public void nuevaRelTrafoBarCir(String usuario,String programa, String tipoZona, ZonaGeografica barCir, SComponente trafo, String periodo){
         //ahora creamos la nueva relacion        
        SRelComponenteUbicacion rcu=new SRelComponenteUbicacion();
        rcu.setUsuario(usuario);
        rcu.setPrograma(programa);
        rcu.setZonaGeografica(barCir);
        rcu.setSComponente(trafo);
        rcu.setPeriodoFin(999912);
        SRelComponenteUbicacionPK clavePrimaria=new SRelComponenteUbicacionPK(barCir.getIdZona().toBigInteger(), trafo.getIdComponente().toBigInteger(), Integer.parseInt(periodo));
        rcu.setSRelComponenteUbicacionPK(clavePrimaria);
        rcu.setFechaModif(new Fecha().getFecha());
        dao.persist(rcu);
        mensaje += "- Creada relación entre Trafo: " + trafo.getIdCliente() + " y el "+tipoZona+": "+ barCir.getNombre()+"<br>";
    }
    
    public void crearRelSuminTrafo(String usuario,String programa, SComponente trafo, SComponente sumin, String periodo){
        SRelComponente relacionActual= getRelSuminTrafoActual(sumin.getIdComponente().toString());
               
        if (relacionActual!=null) {
            String padreActual=relacionActual.getSRelComponentePK().getIdComponente().toString();
            String padreNuevo=trafo.getIdComponente().toString();
            if (!padreActual.equals(padreNuevo)) {
                relacionActual.setPeriodoFin(Integer.parseInt(periodo));
                relacionActual.setUsuario(usuario);
                relacionActual.setPrograma(programa);
                relacionActual.setEstado(getEstado("AC002"));
                relacionActual.setFechaModif(new Fecha().getFecha());
                dao.persist(relacionActual);
                mensaje += "- Terminada relación entre Trafo: " + relacionActual.getSComponente1().getIdCliente() + 
                            " y el Suministro: "+ sumin.getIdCliente()+"<br>";
                nuevaRelSuminTrafo(usuario, programa, trafo, sumin, periodo);
            } else {
                mensaje += "- Ya existe la relación entre Trafo: " + trafo.getIdCliente() + 
                            " y el Suministro: "+ sumin.getIdCliente()+"<br>";
            }  
        }else{
            nuevaRelSuminTrafo(usuario, programa, trafo, sumin, periodo);
        }   
    }
    
    public void nuevaRelSuminTrafo(String usuario,String programa, SComponente trafo, SComponente sumin, String periodo){
        SRelComponente rc = new SRelComponente();
        rc.setUsuario(usuario);
        rc.setPrograma(programa);
        rc.setSComponente1(trafo); //padre
        rc.setSComponente(sumin); //hijo
        rc.setEstado2(trafo.getEstado());
        rc.setEstado1(sumin.getEstado());
        Estado activo=getEstado("AC001");       
        Estado estRelacion=(trafo.getEstado().equals(sumin.getEstado()) && trafo.getEstado().equals(activo))?
                activo:getEstado("AC002");
        rc.setEstado(estRelacion);

        SRelComponentePK clavePrimaria = new SRelComponentePK(trafo.getIdComponente().toBigInteger(), sumin.getIdComponente().toBigInteger(), Integer.parseInt(periodo));
        rc.setSRelComponentePK(clavePrimaria);
        rc.setPeriodoFin(999912);
        Fecha fechaModif=new Fecha();
        rc.setFechaModif(fechaModif.getFecha());        
        dao.persist(rc);
        mensaje += "- Creada relación entre Trafo: " + trafo.getIdCliente() + " y el Suministro: "+ sumin.getIdCliente()+"<br>";
    }
    
    public String getPeriodoActualSimulacion(){
        String hql="SELECT max(b.sBalancesPK.periodo) FROM SBalances b"
                + " where b.sBalancesPK.idSimulacion=(SELECT max(s.sBalancesPK.idSimulacion) FROM SBalances s)";
        return dao.findObject(hql).toString();
    }
   
}
    
    
    
  