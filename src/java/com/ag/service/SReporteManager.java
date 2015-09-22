/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

//import com.ag.model.*;
import com.ag.model.SBalances;
import com.ag.model.SComponente;
import com.ag.model.ZonaGeografica;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface SReporteManager {
        
    public List<ZonaGeografica> getZonasByTipo (String tipo);
    
    public List<SComponente> getTrafosByEmpresa (String id);
    
    public List<SComponente> getTrafosByZona (String id);
    
    public List<SComponente> getTrafosByCircuitoOrBarrio (String id);
    
    public List<SComponente> getTrafosBySubestacion (String id);
    
    public SBalances getBalanceByPeriodo(String idComponente, int periodo);
    
    public String getRutaReportes();
    
}
