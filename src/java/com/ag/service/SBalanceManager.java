/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

//import com.ag.model.*;
import com.ag.model.SAtrComponente;
import com.ag.model.SAtrComponenteMedida;
import com.ag.model.SBalances;
import com.ag.model.SComponente;
import com.ag.model.SComponenteMedida;
import com.ag.model.SMedida;
import com.ag.model.SRelComponenteMedida;
import com.ag.model.Tbltipo;
import com.ag.model.ZonaGeografica;
import com.ag.model.view.DataBalanceHijo;
import com.ag.model.view.DataRangosBalance;
import com.ag.model.view.DataValue;
import com.ag.model.view.PadreHijo;
//import com.ag.model.view.*;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface SBalanceManager {

    public List<DataBalanceHijo> cuadroMando(String idComponente,String tipoComponente, String periodo);

    public SBalances getBalances(String idComponente,String tipoComponente, String periodo);
    
    public DataRangosBalance getRangosZonas(String idZona, String tipoComponente,String tipoArbol, String periodo);
    
    public SMedida getMedida(String idComponente,String periodo, String idTipoCompo);
    
    public SAtrComponente getAtrComponente(String idComponente);
    
    public SRelComponenteMedida getRelComponenteMedida(String idComponente,String periodo);
    
    public SAtrComponenteMedida getAtrComponenteMedida(String idComponenteMedida,String periodo);
    
    public SAtrComponenteMedida getAtrComponenteMedidaTrafo(String nombre,String periodo);
    
    public PadreHijo getSubestacionCircuito(String idComponente,String periodo);

    public PadreHijo getZonaMunicipio(String idComponente,String periodo);
	
    public SComponenteMedida getComponenteMedida(String idComponenteMedida,String periodo);

    public SComponente getComponente(String idComponente);
    
    public List<DataValue> getCantSumXTipoUso(String idComponente,String periodo);
    
    public String getCantSumNoMedidos(String idComponente,String periodo);

    public List<SMedida> getListMedida(String idComponente, int periodo);
    
    public void editAtrComponenteMedida(SAtrComponenteMedida sacm);
    
    public void editMedida(SMedida m);
    
    public void editAtrComponente(SAtrComponente sac);
    
    public void editComponente(SComponente c);
    
    public List<Tbltipo> getTiposConexionesAll();
    
    public Tbltipo getTipo(String tipo);
    
    public List<Tbltipo> getTiposUsosAll();
    
    public SMedida getMedidaActual(String idComponente);
    
    public List<ZonaGeografica> getCircuitosOrBarrios(String tipo);  
    
    public List<SComponente> getTrafos(String tipo);
    
    public void movTrafo(String usuario, String programa, SComponente sc, ZonaGeografica z, String idZonaAnt);
    
    public void movSuministro(String usuario, String programa, SComponente suministro, SComponente trafo, String idTrafoAnt);
    
    public SComponente getTrafoBySuministro(String idSuministro);
    
    public ZonaGeografica getZonaByTrafo(String idTrafo, String tipo);
    
    public ZonaGeografica getZonaGeografica(String idZona);
    
    public ZonaGeografica getZonaGeografica(String idZona, String tipo);
    
    public ZonaGeografica getAreaMt(String idZona, String tipo);
    
    public String getIdComponenteByIdCliente(String idCliente);
    
    public void actConsumoTrafo(String usuario, String programa, String idCliente, String consumoFacturado, String diasFacturado, String consumoAdicional);
    
    public void actConsumoSumin(String usuario, String programa, String idCliente, String consumoFacturado, String diasFacturado);

    public ZonaGeografica getPadreByTipo (String idComponente, String tipo);
    
    public int getNumMesesAtras(); 
    
    public List<SComponente> getTrafosByRango (String id, String tipo, String periodo, String descripcion);
}
