/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package com.ag.service;

//import com.ag.model.SComponente;
import com.ag.Simulacion.Simulado;
import com.ag.model.SComponente;
import com.ag.model.TipoComponente;
import com.ag.model.ZonaGeografica;
import java.util.List;


/**
 *
 * @author 85154220
 */
public interface SimularBalanceManager {

    public List<ZonaGeografica> getZonasByTipo (String tipo);  
    
    public List<SComponente> getTrafos(String tipo);
    
    public List<Simulado> getHistoricoSim(String fechaIni, String fechaFin);
    
    public TipoComponente getTipoComponente(String id);
    
    public SComponente getComponente(String id);
    
    public String exportar(String idSimulacion);
    
    public String getMaxPeriodo ();    
    
}
