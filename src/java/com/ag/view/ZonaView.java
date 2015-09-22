/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.TipoComponente;
import com.ag.model.ZonaGeografica;
import com.ag.service.ConsecutivoManager;
import com.ag.service.SpringContext;
import com.ag.service.ZonaManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author depazan
 */
@ManagedBean
@SessionScoped
public class ZonaView implements Serializable{
    
    private SpringContext context;
    private ZonaManager zonaManager;
    
    private List<ZonaGeografica> zonas;
    private List<TipoComponente> tipos;
    private ZonaGeografica zonaSelected;    
    private String nombre;
    private BigInteger idPadre;
    private String tipoComponente;
    private String coordX;
    private String coordY;  
        
    private String idPadreComercialSelected;
    private Map<String,String> idPadresComerciales = new HashMap<String, String>();
    
    private String idTipoSelected;
    private Map<String,String> idTiposComerciales = new HashMap<String, String>();
        
    public ZonaView() {
        context = SpringContext.getInstance();
        zonaManager=(ZonaManager) context.getBean("ZonaManager");       
       
        
        setZonas(zonaManager.getZonasAll());  
        setTipos(zonaManager.getTiposComponentes());  
        
        List<ZonaGeografica> idComercialList=zonaManager.getZonasAll();
        Iterator iter = idComercialList.iterator();        
        idPadresComerciales.put("SIN PADRE", "");
        while (iter.hasNext()){
             ZonaGeografica z = (ZonaGeografica) iter.next();
             String nombre=z.getIdComercial()!=null && z.getIdComercial().equals("")?
                            z.getIdComercial():
                            z.getIdZona().toString()+"-"+z.getNombre();
             String id=z.getIdZona().toString();
             idPadresComerciales.put(nombre, id);
        }           
        idComercialList.clear();
        
        List<TipoComponente> listTipos=zonaManager.getTiposComponentes();
        Iterator iter2 = listTipos.iterator();        
        while (iter2.hasNext()){
             TipoComponente t = (TipoComponente) iter2.next();
             String nombre=t.getIdTipoComponente().toString()+"-"+t.getDescripcion();
             String id=t.getIdTipoComponente().toString();
             idTiposComerciales.put(nombre, id);
        }           
        listTipos.clear();
    }
    
    public String nombrePadre(String id){
        String np="SIN PADRE";
        if (id != null && !id.equals("")) {
            ZonaGeografica z = zonaManager.getZonaGeografica(id);
            np=z.getIdComercial()!=null && z.getIdComercial().equals("")?
                            z.getIdComercial():
                            z.getIdZona().toString()+"-"+z.getNombre();            
        }          
        return np;
    }
    
    public String nombreTipo(String id){
        String np="-";
        if (id != null && !id.equals("")) {
             TipoComponente t = zonaManager.getTipoComponente(id);
             np=t.getIdTipoComponente().toString()+"-"+t.getDescripcion();
        }          
        return np;
    }
    
    public String nombreEstado(String est){
        String nomEst;               
        if (est.equals("AC001")){
            nomEst="ACTIVO";
        }else{
            nomEst="INACTIVO";
        }
        return nomEst;
    }
    
    public void editZona(String usuario, String programa){
        try {
          zonaSelected.setUsuario(usuario);
          zonaSelected.setPrograma(programa);

          if (idPadreComercialSelected!=null && !idPadreComercialSelected.equals("no") && !idPadreComercialSelected.equals(zonaSelected.getIdPadre().toString())){
           zonaSelected.setIdPadre(new BigInteger(idPadreComercialSelected));
          }

          if (idTipoSelected!=null && !idTipoSelected.equals("no") && !idTipoSelected.equals(zonaSelected.getTipoComponente().getIdTipoComponente().toString())){
           TipoComponente t = zonaManager.getTipoComponente(idTipoSelected);
           zonaSelected.setTipoComponente(t);
          }

          zonaManager.update(zonaSelected);
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                 "Editado con exito.", null));
          
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                 "No pudo ser editado correctamente.", "Error en el sistema."));
        }finally{
           ClearCampos();
        }
        
    }
       
    public void saveZona(String usuario, String programa){ 
      try {
        TipoComponente t = zonaManager.getTipoComponente(tipoComponente);  
        ConsecutivoManager app = (ConsecutivoManager) context.getBean("ConsecutivoManager");
        String consecutivo = app.getConsecutivo("ZONA_GEOGRAFICA","MIGRACION","AUTOMATICO","A","0");
        BigDecimal idZona= new BigDecimal(consecutivo);
            
        zonaManager.save(usuario, programa, idZona, nombre, idPadre, t, coordX, coordY);
        zonas.clear();
        setZonas(zonaManager.getZonasAll());  
        FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                 "Agregado con exito.", null));
      } catch (Exception e) {
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                 "No pudo ser agregada la zona.", "Error en el sistema."));
      }finally{
        ClearCampos();
      }
              
    }
    
    public void ClearCampos(){
        idPadreComercialSelected=null;
        idTipoSelected=null;       
        nombre=null;
        idPadre=null;
        tipoComponente=null;
        coordX=null;
        coordY=null;  
    }
    
    public ZonaGeografica getZonaSelected() {
        return zonaSelected;
    }

    public void setZonaSelected(ZonaGeografica zonaSelected) {
        this.zonaSelected = zonaSelected;
    }

    public List<ZonaGeografica> getZonas() {
        return zonas;
    }

    public void setZonas(List<ZonaGeografica> zonas) {
        this.zonas = zonas;
    }

    public List<TipoComponente> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoComponente> tipos) {
        this.tipos = tipos;
    }

    public String getIdTipoSelected() {
        return idTipoSelected;
    }

    public void setIdTipoSelected(String idTipoSelected) {
        this.idTipoSelected = idTipoSelected;
    }
   
    public Map<String, String> getIdPadresComerciales() {
        return idPadresComerciales;
    }

    public void setIdPadresComerciales(Map<String, String> idPadresComerciales) {
        this.idPadresComerciales = idPadresComerciales;
    }

    public Map<String, String> getIdTiposComerciales() {
        return idTiposComerciales;
    }

    public void setIdTiposComerciales(Map<String, String> idTiposComerciales) {
        this.idTiposComerciales = idTiposComerciales;
    }

    public String getCoordX() {
        return coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCoordY() {
        return coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }

    public BigInteger getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(BigInteger idPadre) {
        this.idPadre = idPadre;
    }
   
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getIdPadreComercialSelected() {
        return idPadreComercialSelected;
    }

    public void setIdPadreComercialSelected(String idPadreComercialSelected) {
        this.idPadreComercialSelected = idPadreComercialSelected;
    }   
       
}