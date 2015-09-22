/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

/**
 *
 * @author 85154220
 */
public interface SCalculaBalanceManager {
    
    public String calcularBalance (String periodo, String idSimulacion);
    
    public String copiarDatosSimulacion(String periodo);
    
}
