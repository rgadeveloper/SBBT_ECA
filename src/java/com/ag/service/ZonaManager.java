package com.ag.service;
import com.ag.model.TipoComponente;
import com.ag.model.ZonaGeografica;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface ZonaManager { 
    
    public void save(String usuario, String programa, BigDecimal idZona, String nombre, BigInteger idPadre, TipoComponente tipoComponente, String coordX, String coordY);
            
    public List<ZonaGeografica> getZonasAll(); 
    
    public List<TipoComponente> getTiposComponentes(); 
       
    public ZonaGeografica getZonaGeografica(String id);
    
    public TipoComponente getTipoComponente(String id);
    
    public void update(ZonaGeografica zona);
    
    public String getIdPadre(String nombre, String id_tipo_componente)throws SQLException;
    
    public String getNombreZona(String idPadre)throws SQLException;

}
