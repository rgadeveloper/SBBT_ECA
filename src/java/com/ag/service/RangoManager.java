/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service;

import com.ag.model.Color;
import com.ag.model.RangoClasificacion;
import com.ag.model.TipoComponente;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface RangoManager {   
    
    //public void save(String idParametro ,String nombre, String valor);
        
    public List<RangoClasificacion> getRangosAll();
    
    public List<Color> getColoresAll();
    
    public List<TipoComponente> getTiposComponentesAll(); 
    
    public void save(String usuario, String programa, BigDecimal idRango, BigDecimal porcMinimo, BigDecimal porcMaximo, String descripcion, short codTipoComponente, long codColor);
    
    public void edit(RangoClasificacion r, String codTipo, long codigoColor, String descripcion);
    
    public Color getColor(Long idColor);
    
    public void saveColor(String usuario, String programa, Long idColor, String descripcion, short rojo, short verde, short azul);
    
    public TipoComponente getTipoComponente(Short idTipoComponente);
    
    public boolean existeRango (String tipo, String descripcion);
    
    public boolean existeColor (String descripcion, short rojo, short verde, short azul);
    
    //public boolean existeDescripcionColor (String tipo, String descripcion);
    
}
