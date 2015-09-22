/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

import java.io.InputStream;

/**
 *
 * @author 85154220
 */
public interface SArchivoManager {
    
    public String[] cargaAmarreMasivo(String usuario, String programa, InputStream inputStream);

    public String[] cargaConsumoMasivo(String usuario, String programa, InputStream inputStream);    
    
}
