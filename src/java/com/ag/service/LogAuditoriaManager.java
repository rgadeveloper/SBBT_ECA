/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service;
import com.ag.model.LogAuditoria;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface LogAuditoriaManager {    
    
    public List<String> getPrograma(); 
    
    public List<String> getProgramaEjecucion();    
    
    public List<LogAuditoria> getLogs(String programa, String programaEjecucion, String fechaIni, String fechaFin);
}
