/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

import com.ag.model.view.Delegacion;
import com.ag.model.view.Perdidas;
import com.ag.model.view.TipoComponenteVer;
import com.ag.model.view.UbicacionMacroV;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface ArbolManager {
    
    public List cargaArbolInicial(String nivelGrupo,String periodo);
    
    public List cargaArbolHijos(String codigoPadre,String tipoPadre,String periodo, HashMap gestionFiltros);
    
    public List cargaArbolHijosTrafos(String codigoPadre,String tipoPadre,String periodo, HashMap gestionFiltros);
    
    public boolean soloTrafoInGmap(String tipo);
            
    public List getZoomMapa();

    public List getPeriodo();
    
    public String rutaTrafo(String idCliente, String tipoArbol, String periodo) throws SQLException;
    
    public String rutaSuministro(String idCliente, String tipoArbol, String periodo) throws SQLException;
    
    public UbicacionMacroV getUbicacionMacroV(String idComponente, String periodo) throws SQLException;
    
     public String rutaView(String tipoComponente,String tipoArbol, String periodo,String idNic, String idPlaca) throws SQLException;
    
   
    public List<TipoComponenteVer> buscarComponente(String tipoComp);
    /* INI AAMC 20140807 */
    public String rutaViewGeografica(String tipoComponente,String tipoArbol, String periodo,String idNic, String idPlaca) throws SQLException ;
    /* FIN AAMC 20140807 */
	
    public List<Perdidas> buscarPerdida(BigDecimal periodo);
    
    public List<Delegacion> buscarDelegaciones();
    
}
