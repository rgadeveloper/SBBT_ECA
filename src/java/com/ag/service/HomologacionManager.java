/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

import com.ag.model.TipoComponente;
import com.ag.model.Homologacion;
import java.util.List;

/**
 *
 * @author ptorres
 */

public interface HomologacionManager {
    
        public TipoComponente getTipoComponente(String id);
        
        public List<TipoComponente> getTiposComponentes(); 
        
        public List<Homologacion> getHomologacionAll();
        
        public void save(String usuario, String programa, String tipo_ceo, String tipo_simbal, String grupo);
        
        public void update(Homologacion homologacion);
        
        public Homologacion getHomologacion(String id);
    
}
