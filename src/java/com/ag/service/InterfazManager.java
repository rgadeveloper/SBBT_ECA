package com.ag.service;

import java.sql.Connection;

/**
 *
 * @author 85154220
 */
public interface InterfazManager {
    
    public String calcularBalance(int periodo);
    
    public String lecturas(int periodo, int borrar);
    
    public int getBorrarLecturas();
    
    public String ejecutarTodo(int periodo);
    
    public String ceoAsimbal(int periodo);
    
    public String crearArbol(int periodo);
    
    public Object executeProcedimientoAlmacenado(final Integer periodo,final String situacion);
    
    public Connection generarConexion();
    
    public Object validarPeriodo(String periodo);
    
    public boolean existsPeriodo(String periodo);
    
}
