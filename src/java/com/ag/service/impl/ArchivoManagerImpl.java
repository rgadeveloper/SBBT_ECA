/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.*;
import com.ag.model.view.Fecha;
import com.ag.service.ArchivoManager;
import com.ag.service.ConsecutivoManager;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("ArchivoManager")
public class ArchivoManagerImpl implements ArchivoManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    
    @Autowired
    @Qualifier("ConsecutivoManager")
    private ConsecutivoManager consecutivo;
    
    private String mensaje,estado;
    private int procesados,totalRegistros;

    @Override
    public String[] cargaMacroMasivo(String usuario, String programa, InputStream inputStream) {
        mensaje = "";
        estado = "exitoso";
        procesados = 0;
        totalRegistros = 0;
        String [] respuesta = new String[3];
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = in.readLine();
            String periodo = getPeriodoActual();
            while (line != null) {
                totalRegistros++;
                String[] array = line.split(";");
                String idComercialTrafo = array[0];
                String idComercialMacro = array[1];
                String tipoMedidor = array[2];
                Componente trafo = existeTrafo(idComercialTrafo);
                if (trafo == null) {
                    mensaje += "*** El Transformador: " + idComercialTrafo + " No existe en la BD<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }else if(!trafo.getEstado1().getIdEstado().equals("AC001")){
                    mensaje += "*** El Transformador: " + idComercialTrafo + " se encuentra inactivo<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }
                ComponenteMedida macro = existeMacro(idComercialMacro);
                if(macro == null){
                    macro = crearMacromedidor(usuario,programa, idComercialMacro,tipoMedidor);                    
                }else{
                    terminarRelacion(String.valueOf(macro.getIdComponenteMedida()),periodo);
                    
                }
                if(macro != null){
                    crearRelcomponenteMedida(usuario,programa,macro,trafo, periodo);
                    procesados++;
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

    public Componente existeTrafo(String idComercialTrafo) {
        String hql = " Select c from Componente c where c.idCliente ='" + idComercialTrafo +"' "
                     +" and c.tipoComponente.idTipoComponente = 8";
        return dao.findObject(hql);

    }
    
    public Componente getMacroVirtual(String idComercialMacro) {
        String hql = " Select c from Componente c where c.idCliente ='" + idComercialMacro +"' "
                     +" and c.tipoComponente.idTipoComponente =10";
        return dao.findObject(hql);
    }

    public ComponenteMedida existeMacro(String idComercialMacro) {
        String hql = "select c "
                    + " from ComponenteMedida c"
                    + " where c.idComercial = " + idComercialMacro
                    + " and   c.estado  = 'AC001'";
        return dao.findObject(hql);

    }

    public Tbltipo getTipo(String tipoMedidor) {
        String hql = "Select t from Tbltipo t where t.tipo ='" + tipoMedidor+"'";
        return dao.findObject(hql);
    }

    public ComponenteMedida crearMacromedidor(String usuario,String programa, String idComercialMacro,String tipoMedidor) {
        ComponenteMedida macro = new ComponenteMedida();
        macro.setUsuario(usuario);
        macro.setPrograma(programa);
        macro.setDireccion("Sin Direccion");
        macro.setNumeroRuedas(new Long(0));        
        Calendar hoy = Calendar.getInstance();
        macro.setFechaModif(hoy.getTime());
        String idComponenteMedida = consecutivo.getConsecutivo("COMPONENTE_MEDIDA", usuario, programa, "A", "0");
        BigDecimal id = new BigDecimal(idComponenteMedida);
        macro.setIdComponenteMedida(id);
        macro.setNombre(idComercialMacro);
        macro.setIdComercial(idComercialMacro);
       
        macro.setEstado("AC001");
        Tbltipo tipo = getTipo(tipoMedidor);
        if (tipo == null) {
            mensaje += "*** El tipoMedidor: " + tipoMedidor + "asociado al Macromedidor:" + idComercialMacro + " No existe en la tabla Tbltipo<br>";
            estado = "warning";
            return null;
        } else {
            macro.setTbltipo(tipo);
        }
        dao.persist(macro);
        mensaje += "- creado Macromedidor: " + idComercialMacro + " <br>";
        return macro;
    }
    
    public void crearRelcomponenteMedida(String usuario,String programa,ComponenteMedida macro, Componente trafo, String periodo){
        crearRelcomponenteMedida(usuario,programa,macro,trafo,periodo,"999912");
    }
    public void crearRelcomponenteMedida(String usuario,String programa,ComponenteMedida macro, Componente trafo, String periodo, String periodoFin){
        RelComponenteMedida relComponenteMedida = new RelComponenteMedida();
        RelComponenteMedidaPK pk = new RelComponenteMedidaPK();
        pk.setIdComponente(new BigInteger(String.valueOf(trafo.getIdComponente())));
        pk.setIdComponenteMedida(new BigInteger(String.valueOf(macro.getIdComponenteMedida())));
        pk.setPeriodoIni(Integer.parseInt(periodo));
        relComponenteMedida.setUsuario(usuario);
        relComponenteMedida.setPrograma(programa);
        Calendar hoy = Calendar.getInstance();
        relComponenteMedida.setFechaModif(hoy.getTime());
        relComponenteMedida.setRelComponenteMedidaPK(pk);
        relComponenteMedida.setComponente(trafo);
        relComponenteMedida.setComponenteMedida(macro);
        relComponenteMedida.setIdEstado("AC001");
        relComponenteMedida.setIdEstadoCompo(trafo.getEstado1().getIdEstado());
        relComponenteMedida.setIdEstadoCompoMed(macro.getEstado());
        relComponenteMedida.setFecha(hoy.getTime());
        dao.persist(relComponenteMedida);
        mensaje += "- Creada relación entre Macromedidor: " + relComponenteMedida.getComponenteMedida().getIdComercial() + " y el componente: "+ relComponenteMedida.getComponente().getIdCliente()+"<br>";
    }
    
    public void terminarRelacion(String idComponenteMedida,String periodo){
        String hql = " select r " +
                     " from RelComponenteMedida r " +
                     " where r.componenteMedida.idComponenteMedida= " + idComponenteMedida+
                     " and   r.periodoFin=999912";
        RelComponenteMedida relComponenteMedida = dao.findObject(hql);
        if(relComponenteMedida != null){
            relComponenteMedida.setPeriodoFin(Integer.parseInt(periodo));
            relComponenteMedida.setIdEstado("AC002");
            dao.persist(relComponenteMedida);
            mensaje += "- Terminada relación entre Macromedidor: " + relComponenteMedida.getComponenteMedida().getIdComercial() + " y el componente: "+ relComponenteMedida.getComponente().getIdCliente()+"<br>";
        }
    }
    
    public String getPeriodoActual(){
        String hql = " SELECT p.valor " + 
                     "  FROM Parametro p " +
                     " where p.idParametro='PERIODO_ACTUAL'";
        return dao.findObject(hql);
    }

 @Override
    public List <Tbltipo> getTipoMacromedidores(){
        String hql=" Select t from Tbltipo t"
                + " where t.grupo.codigo='TTM000'";
       return dao.find(hql);
    }


   @Override
    public String[] cargaMacroIndividual(String trafor, String macrom, String tipoMacro, String usuario,String programa) {
        mensaje = "";
        estado = "exitoso";
        String [] respuesta = new String[3];
        try {
                String periodo = getPeriodoActual();
                String idComercialTrafo = trafor;
                String idComercialMacro = macrom;
                String tipoMedidor = tipoMacro;
                Componente trafo = existeTrafo(idComercialTrafo);
                if (trafo == null) {
                    mensaje += "El Transformador: " + idComercialTrafo + " No existe en la BD";
                    estado = "error";
                    respuesta[0] = mensaje;
                    respuesta[1] = estado;
                    respuesta[2] = mensaje;
                    return respuesta;
                    
                }else if(!trafo.getEstado().getIdEstado().equals("AC001")){
                    mensaje += "El Transformador: " + idComercialTrafo + " se encuentra inactivo";
                    estado = "error";
                    respuesta[0] = mensaje;
                    respuesta[1] = estado;
                    respuesta[2] = mensaje;
                    return respuesta;
                }
                ComponenteMedida macro = existeMacro(idComercialMacro);
                if(macro == null){
                    macro = crearMacromedidor(usuario,programa, idComercialMacro,tipoMedidor);
                }else{
                    terminarRelacion(String.valueOf(macro.getIdComponenteMedida()),periodo);

                }
                if(macro != null){
                    crearRelcomponenteMedida(usuario,programa,macro,trafo, periodo);
                }

        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al guardar el Macromedidor, " + e.toString();
            estado = "error";
        }
        respuesta[0] = mensaje;
        respuesta[1] = estado;
        respuesta[2] = mensaje;
        return respuesta;
    }

    @Override
    public void crearMacroVirtual(String usuario, String programa, String nombre, String direccion, String idComercial) {        
        String idComponente = consecutivo.getConsecutivo("COMPONENTE", usuario, programa, "A", "0");
        Componente c = new Componente(new BigDecimal(idComponente));
        c.setUsuario(usuario);
        c.setPrograma(programa);
        Fecha fecha=new Fecha();
        c.setFechaModif(fecha.getFecha());
        c.setDireccion(direccion);
        c.setNombre(nombre);
        c.setIdCliente(idComercial);
        c.setTipoComponente(getTipoComponente("10"));//10 indica que es un MV 
        c.setEstado1(getEstado("AC001"));
        c.setTbltipo(getTipo("LOC999")); //LOCALIZACION
        c.setTbltipo1(getTipo("LOC999")); //TIPO_PROPIETARIO
        c.setEstado(getEstado("AC001")); //ESTADO_SERVICIO
        c.setTipoProvisional("LOC999");        
        dao.persist(c);
    }
    
    @Override
    public boolean existeMacroVirtual(String idComercial) {
        String hql="SELECT c FROM Componente c WHERE c.idCliente = '" + idComercial+"'"
                  +" and c.tipoComponente.idTipoComponente =10";
        boolean existe=dao.findObject(hql)!=null?true:false;
        return existe; 
    }
    
    @Override
    public String[] cargaAsociarMacroCirBar(String usuario, String programa, InputStream inputStream) {
        mensaje = "";
        estado = "exitoso";
        procesados = 0;
        totalRegistros = 0;
        String [] respuesta = new String[3];
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = in.readLine();            
            while (line != null) {
                totalRegistros++;
                String[] array = line.split(";");
                String idMacroVirtual = array[0];
                String idPadreAmarre = array[1];      //barrio o circuito     
                String periodo = array[2];
                String tipoZona;
                if (array[3].equals("C")) {
                    tipoZona="Circuito";
                } else if(array[3].equals("B")){
                    tipoZona="Barrio";
                }else{
                   mensaje += "*** El Tipo: " + array[3] + " no está permitido<br>";
                   line = in.readLine();
                   estado = "warning";
                   continue;
                }                 
                
                //aqui colocar una validacion que todas las lineas tengan valores                
                
                Componente macroVirtual = getMacroVirtual(idMacroVirtual);
                if (macroVirtual == null) {                    
                    mensaje += "*** El Macro Virtual: " + idMacroVirtual + " No existe en la BD<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }else if(!macroVirtual.getEstado1().getIdEstado().equals("AC001")){
                    mensaje += "*** El Macro Virtual: " + idMacroVirtual + " se encuentra inactivo<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }   
                
                ZonaGeografica barCir = getZona(idPadreAmarre,tipoZona); 
                if (barCir == null) {
                    mensaje += "*** El "+tipoZona+" con id: " + idPadreAmarre + " No existe en la BD<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }else if(!barCir.getEstado().equals("AC001")){
                    mensaje += "*** El "+tipoZona+" con id: " + idPadreAmarre + " se encuentra inactivo<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }   
                
                crearRelMacroBarCir(usuario, programa, tipoZona, barCir, macroVirtual, periodo);
                procesados++;
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
    public String[] cargaAsociarTrafoMacro(String usuario, String programa, InputStream inputStream) {
        mensaje = "";
        estado = "exitoso";
        procesados = 0;
        totalRegistros = 0;
        String [] respuesta = new String[3];
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = in.readLine();            
            while (line != null) {
                totalRegistros++;
                String[] array = line.split(";");
                String idMacroVirtual = array[0];
                String idComercialTrafo = array[1];
                String periodo = array[2];
                String tipo = array[3];
                
                //aqui colocar una validacion que todas las lineas tengan valores                
                
                Componente trafo = existeTrafo(idComercialTrafo);
                if (trafo == null) {
                    mensaje += "*** El Transformador: " + idComercialTrafo + " No existe en la BD<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }else if(!trafo.getEstado().getIdEstado().equals("AC001")){
                    mensaje += "*** El Transformador: " + idComercialTrafo + " se encuentra inactivo<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }
                
                Componente macroVirtual = getMacroVirtual(idMacroVirtual);
                if (macroVirtual == null) {
                    mensaje += "*** El Macro Virtual: " + idMacroVirtual + " No existe en la BD<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }else if(!macroVirtual.getEstado().getIdEstado().equals("AC001")){
                    mensaje += "*** El Macro Virtual: " + idMacroVirtual + " se encuentra inactivo<br>";
                    line = in.readLine();
                    estado = "warning";
                    continue;
                }
                
                if (tipo.equals("A")) {
                    //analizar si es necesario quitar la asociacios actual si tiene
                    crearRelTrafoMacro(usuario, programa, macroVirtual, trafo, periodo);
                    procesados++;
                }else if (tipo.equals("D")) {
                    quitarRelTrafoMacro(usuario, programa, macroVirtual, trafo, periodo);
                    procesados++;
                }else{
                    mensaje += "*** El Tipo: " + tipo + " no está permitido<br>";
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
    public String balanceVirtual(String periodo, String tipo) {
         try {
            Connection con = dao.getConnection();
            
            String sql = "{call P_BALANCE_VIRTUAL(?,?,?)}";
            CallableStatement  statement = con.prepareCall(sql);
            
            statement.setInt(1, Integer.parseInt(periodo));
            statement.setInt(2, Integer.parseInt(tipo));
            
            statement.registerOutParameter(3,Types.VARCHAR);            
                        
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
    
    public void crearRelTrafoMacro(String usuario,String programa,Componente macro, Componente trafo, String periodo){
        String hql ="SELECT r FROM RelComponente r "
                  + "WHERE r.relComponentePK.idComponente = "+ macro.getIdComponente()
                       + " and r.relComponentePK.idComponenteHijo = "+ trafo.getIdComponente()
                       + " and r.relComponentePK.periodoIni = "+periodo
                       + " and r.periodoFin=999912";
        RelComponente rcExiste = dao.findObject(hql);
        if(rcExiste != null){
            mensaje += "- Ya existe relación entre Macro Virtual: " + macro.getIdCliente() + " y el Trafo: "+ trafo.getIdCliente()+"<br>"; 
        }else{
            RelComponente rc = new RelComponente();
            rc.setUsuario(usuario);
            rc.setPrograma(programa);
            rc.setComponente1(macro); //padre
            rc.setComponente(trafo); //hijo
            rc.setEstado(macro.getEstado1());
            rc.setEstado1(trafo.getEstado1());
            Estado activo=getEstado("AC001");       
            Estado estRelacion=(macro.getEstado1().equals(trafo.getEstado1()) && macro.getEstado1().equals(activo))?
                    activo:getEstado("AC002");
            rc.setEstado2(estRelacion);

            RelComponentePK clavePrimaria = new RelComponentePK(macro.getIdComponente().toBigInteger(), trafo.getIdComponente().toBigInteger(), Integer.parseInt(periodo));
            rc.setRelComponentePK(clavePrimaria);
            rc.setPeriodoFin(999912);
            Fecha fechaModif=new Fecha();
            rc.setFechaModif(fechaModif.getFecha());        
            dao.persist(rc);
            mensaje += "- Creada relación entre Macro Virtual: " + macro.getIdCliente() + " y el Trafo: "+ trafo.getIdCliente()+"<br>";
          }        
    }
    
    public void quitarRelTrafoMacro(String usuario,String programa,Componente macro, Componente trafo, String periodo){
        String hql ="SELECT r FROM RelComponente r "
                  + "WHERE r.relComponentePK.idComponente = "+ macro.getIdComponente()
                       + " and r.relComponentePK.idComponenteHijo = "+ trafo.getIdComponente()
                       + " and r.relComponentePK.periodoIni = "+periodo
                       + " and r.periodoFin=999912";
        
        RelComponente rc = dao.findObject(hql);
        if(rc != null){            
            rc.setPeriodoFin(Integer.parseInt(getPeriodoActual()));
            rc.setEstado2(getEstado("AC002"));
            dao.persist(rc);
            mensaje += "- Terminada relación entre Macro Virtual: " + macro.getIdCliente() + " y el Trafo: "+ trafo.getIdCliente()+"<br>";
        }else{
            mensaje += "- No existe relación entre Macro Virtual: " + macro.getIdCliente() + " y el Trafo: "+ trafo.getIdCliente()+"<br>";
        }              
        
    }
    
    public void crearRelMacroBarCir(String usuario,String programa, String tipoZona, ZonaGeografica barCir, Componente macro, String periodo){
        RelComponenteUbicacion relacionActual= tipoZona.equals("Circuito")?
                                    getRelMacroBarCirActual(macro.getIdComponente().toString(),"3"):
                                    getRelMacroBarCirActual(macro.getIdComponente().toString(),"7");
               
        if (relacionActual!=null) {
            String padreActual=relacionActual.getZonaGeografica().getIdZona().toString();
            String padreNuevo=barCir.getIdZona().toString();
            if (!padreActual.equals(padreNuevo)) {
                relacionActual.setPeriodoFin(Integer.parseInt(periodo));
                relacionActual.setUsuario(usuario);
                relacionActual.setPrograma(programa);
                relacionActual.setFechaModif(new Fecha().getFecha());
                dao.persist(relacionActual);
                mensaje += "- Terminada relación entre Macro Virtual: " + macro.getIdCliente() + 
                            " y el "+tipoZona+": "+ relacionActual.getZonaGeografica().getNombre()+"<br>";
                nuevaRelMacroBarCir(usuario, programa, tipoZona, barCir, macro, periodo);
            } else {
                mensaje += "- Ya existe la relación entre Macro Virtual: " + macro.getIdCliente() + 
                            " y el "+tipoZona+": "+ barCir.getNombre()+"<br>";
            }  
        }else{
            nuevaRelMacroBarCir(usuario, programa, tipoZona, barCir, macro, periodo);
        }   
    }
    
    public void nuevaRelMacroBarCir(String usuario,String programa, String tipoZona, ZonaGeografica barCir, Componente macro, String periodo){
         //ahora creamos la nueva relacion        
        RelComponenteUbicacion rcu=new RelComponenteUbicacion();
        rcu.setUsuario(usuario);
        rcu.setPrograma(programa);
        rcu.setZonaGeografica(barCir);
        rcu.setComponente(macro);
        rcu.setPeriodoFin(999912);
        RelComponenteUbicacionPK clavePrimaria=new RelComponenteUbicacionPK(barCir.getIdZona().toBigInteger(), macro.getIdComponente().toBigInteger(), Integer.parseInt(periodo));
        rcu.setRelComponenteUbicacionPK(clavePrimaria);
        rcu.setFechaModif(new Fecha().getFecha());
        dao.persist(rcu);
        mensaje += "- Creada relación entre Macro Virtual: " + macro.getIdCliente() + " y el "+tipoZona+": "+ barCir.getNombre()+"<br>";
    }
    
    public TipoComponente getTipoComponente(String idTipo){
        String hql="SELECT t FROM TipoComponente t WHERE t.idTipoComponente ="+idTipo;
        return dao.findObject(hql);
    }
    
    public Estado getEstado(String idEstado){
        String hql="SELECT e FROM Estado e WHERE e.idEstado ='"+idEstado+"'";
        return dao.findObject(hql);
    }  
    
    public ZonaGeografica getZona(String idZona, String tipoZona){
        tipoZona=tipoZona.equals("Circuito")?"3":"7";
        String hql="SELECT z FROM ZonaGeografica z WHERE z.idZona ="+idZona+
                   " and z.tipoComponente.idTipoComponente="+tipoZona;
        return dao.findObject(hql);
    }  
    
    public RelComponenteUbicacion getRelMacroBarCirActual(String idMacro, String tipo) {
        String hql = "SELECT s FROM RelComponenteUbicacion s "+ 
                     "WHERE s.periodoFin = 999912 "+
                     "and s.relComponenteUbicacionPK.idComponente ="+ idMacro +
                     " and s.zonaGeografica.tipoComponente =" + tipo;              
        return dao.findObject(hql);
    }   
    
    
    @Override
    public Tbltipo TipoMacromedidores(String codigo) {
      String hql=" Select t from Tbltipo t"
                + " where t.tipo='"+codigo+"'";
       return dao.findObject(hql);
    }

    @Override
    public boolean cargaExtraSave(CargaExtra... cargaExtra) {
        try {
            dao.persist(cargaExtra);
            return true;
        } catch (Exception e) {
            Logger.getLogger(ArchivoManagerImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean balanceEnergiaSave(BalanceEnergia... balanceEnergia) {
         try {
            dao.persist(balanceEnergia);
            return true;
        } catch (Exception e) {
            Logger.getLogger(ArchivoManagerImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    @Override
    public boolean energiaTotalAreasSave(EnergiaTotalAreas... energiasTotalAreas){
      try {
            dao.persist(energiasTotalAreas);
            return true;
        } catch (Exception e) {
            Logger.getLogger(ArchivoManagerImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
