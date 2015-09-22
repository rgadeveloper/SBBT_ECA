/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.reportes;

//import com.ag.model.*;
import com.ag.model.SBalances;
import com.ag.model.SComponente;
import com.ag.model.ZonaGeografica;
import com.ag.service.SReporteManager;
import com.ag.service.SpringContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


/**
 *
 * @author arodriguezr
 */
@ManagedBean(name="sreporte")  
@SessionScoped
public class SReporte {

    private SpringContext context;
    private SReporteManager reporteManager;
    
    private String tipo;
    private String componente;    
    private Map<String,String> tipos = new HashMap<String, String>();
    private Map<String,Map<String,String>> componentesByTipos = new HashMap<String, Map<String,String>>();
    private Map<String,String> componentes = new HashMap<String, String>();
    
    private String vista;
    private Map<String,String> vistas = new HashMap<String, String>();
    private Map<String,Map<String,String>> tiposByVistas = new HashMap<String, Map<String,String>>();
    
    private List<SComponente> trafos;
    private List<TrafoPeriodos> trafoPeriodos;
    
    private String opcion;
    private Map<String,String> opciones = new HashMap<String, String>();
    
    public SReporte() {
        context = SpringContext.getInstance();        
        reporteManager = (SReporteManager) context.getBean("SReporteManager");
        
        opciones.put("MES", "MES");
        opciones.put("MOVIL", "MOVIL");
        
        vistas.put("ELECTRICA", "ELECTRICA");
        vistas.put("GEOGRAFICA", "GEOGRAFICA");
        Map<String,String> electrica = new HashMap<String, String>();
        Map<String,String> geografica = new HashMap<String, String>();
        
        geografica.put("BARRIO", "7"); 
        geografica.put("EMPRESA", "0");  
        geografica.put("SUBESTACION", "2");  
        geografica.put("ZONA", "5");
                
        electrica.put("CIRCUITO", "3");  
        electrica.put("EMPRESA", "4");  
        electrica.put("SUBESTACION", "2");  
        electrica.put("ZONA", "1");        
        
        tiposByVistas.put("ELECTRICA", electrica);
        tiposByVistas.put("GEOGRAFICA", geografica);
                     
        List<ZonaGeografica> list;
        
        list = reporteManager.getZonasByTipo("7");        
        Map<String,String> barrios = llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("3");        
        Map<String,String> circuitos = llenarMapZona(list);      
        
        list = reporteManager.getZonasByTipo("0");        
        Map<String,String> empresasVE = llenarMapZona(list);     
        
        list = reporteManager.getZonasByTipo("4");        
        Map<String,String> empresasVG = llenarMapZona(list); 
        
        list = reporteManager.getZonasByTipo("2");
        Map<String,String> subestaciones=llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("5");
        Map<String,String> zonasVG=llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("1");
        Map<String,String> zonasVE=llenarMapZona(list);        
               
        componentesByTipos.put("7", barrios);
        componentesByTipos.put("3", circuitos);
        componentesByTipos.put("0", empresasVE);
        componentesByTipos.put("4", empresasVG);
        componentesByTipos.put("2", subestaciones);
        componentesByTipos.put("1", zonasVE);
        componentesByTipos.put("5", zonasVG);
    }
    
    public Map<String, String> llenarMapZona (List list){  
        Iterator iter = list.iterator();
        Map<String,String> map = new HashMap<String, String>();
        while (iter.hasNext()){
             ZonaGeografica z = (ZonaGeografica) iter.next();
             String nombre=z.getNombre();
             String id=z.getIdZona().toString();
             map.put(nombre, id);
        }        
        list.clear();
        return map;
    }
    
        public void actualizarComboTipos() { 
         tipo=null;
         componente=null;
         actualizarComboComponentes();
        if(vista !=null && !vista.equals(""))             
            tipos = tiposByVistas.get(vista);     
        else  
            tipos = new HashMap<String, String>();  
    }  
    
    public void actualizarComboComponentes() {  
        if(tipo !=null && !tipo.equals(""))  
            componentes = componentesByTipos.get(tipo);            
        else  
            componentes = new HashMap<String, String>();  
    }  
    
    public void llenarDataTable(){
        if ((opcion!=null && !opcion.equals("")) && (tipo !=null && !tipo.equals("")) && (vista !=null && !vista.equals("")) && (componente !=null && !componente.equals(""))) {
            if (tipo.equals("3") || tipo.equals("7")) {
             setTrafos(reporteManager.getTrafosByCircuitoOrBarrio(componente)); 
            }else{
                if (tipo.equals("0") || tipo.equals("4")) {
                    setTrafos(reporteManager.getTrafosByEmpresa(componente));
                } else {
                    if (tipo.equals("2")) {
                        setTrafos(reporteManager.getTrafosBySubestacion(componente));                    
                    } else {
                        if (tipo.equals("1") || tipo.equals("5")) {
                            setTrafos(reporteManager.getTrafosByZona(componente));                        
                        } else {
                            trafos = new ArrayList<SComponente>(); 
                        }
                    }
                }
            }
            
            //Llenar trafosPerioos
            llenarTrafosPeriodos();
            
        }else{
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Tiene que escoger todas las opciones.", "Inténtalo de nuevo."));  
        } 
    }
    
    public void llenarTrafosPeriodos(){
        trafoPeriodos = new ArrayList<TrafoPeriodos>();
        Iterator iter = trafos.iterator();        
        while (iter.hasNext()){
             SComponente c = (SComponente) iter.next();
             TrafoPeriodos h=new TrafoPeriodos();
             h.setIdTrafo(c.getIdComponente().toString());
             h.setNombre(c.getNombre());
             
             String[] mes = new String[12];
             String[] movil = new String[12];
             String[] periodo = new String[12];            
             for (int i = 0; i < 12; i++) {
                 String porcMes = "-";
                 String porcMovil = "-";
                 String periodoActual = "-";
                 SBalances b = reporteManager.getBalanceByPeriodo(c.getIdComponente().toString(), i);
                 if (b!=null) {    
                     periodoActual = String.valueOf(b.getSBalancesPK().getPeriodo());
                     porcMes = b.getPorcPerdidaMes().toString();
                     porcMovil = b.getPorcPerdidaMovil().toString();
                 }
                 mes[i]=porcMes;
                 movil[i]=porcMovil; 
                 periodo[i]=periodoActual;  
             }
             
                h.setMes1(mes[0]);
                h.setMovil1(movil[0]);
                
                h.setMes2(mes[1]);
                h.setMovil2(movil[1]);
                
                h.setMes3(mes[2]);
                h.setMovil3(movil[2]);
                
                h.setMes4(mes[3]);
                h.setMovil4(movil[3]);
                
                h.setMes5(mes[4]);
                h.setMovil5(movil[4]);
                
                h.setMes6(mes[5]);
                h.setMovil6(movil[5]);
                
                h.setMes7(mes[6]);
                h.setMovil7(movil[6]);
                
                h.setMes8(mes[7]);
                h.setMovil8(movil[7]);
                
                h.setMes9(mes[8]);
                h.setMovil9(movil[8]);
                
                h.setMes10(mes[9]);
                h.setMovil10(movil[9]);
                
                h.setMes11(mes[10]);
                h.setMovil11(movil[10]);
                
                h.setMes12(mes[11]);
                h.setMovil12(movil[11]); 
                 
                trafoPeriodos.add(h);
        }             
        
    }
    
    public String porcentajeByperiodo(String idComponente, int periodo){        
        String porcentaje="-";
        SBalances b = reporteManager.getBalanceByPeriodo(idComponente, periodo);
        if (b!=null){            
            porcentaje=(opcion.equals("MES"))?
                        b.getPorcPerdidaMes().toString():
                        b.getPorcPerdidaMovil().toString();             
        }
        return porcentaje;
    }
  
    public void displayLocation() {        
        FacesMessage msg = new FacesMessage("Selected", "Tipo:" + tipo + ", Componente: " + componente);  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }   
    
    JasperPrint jasperPrint;
    public void initj() throws JRException{
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(trafoPeriodos);
        String reporte=(opcion.equals("MES"))?
                        "historico.jasper":
                        "historicoMovil.jasper";
        jasperPrint = JasperFillManager.fillReport(reporteManager.getRutaReportes()+reporte, new HashMap(), beanCollectionDataSource);
    }
    
    public void jasperPrueba() throws JRException, IOException {
         try {
            initj();
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=historico.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            
            servletOutputStream.flush();
            servletOutputStream.close();

         } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "upss", e.getMessage())); 
        
         }
  
        } 
   
    
    /*public void jasperPrueba(){
        FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "upss", "configurar")); 
    }*/
    
    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public Map<String, String> getComponentes() {
        return componentes;
    }

    public void setComponentes(Map<String, String> componentes) {
        this.componentes = componentes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Map<String, String> getTipos() {
        return tipos;
    }

    public void setTipos(Map<String, String> tipos) {
        this.tipos = tipos;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

    public Map<String, String> getVistas() {
        return vistas;
    }

    public void setVistas(Map<String, String> vistas) {
        this.vistas = vistas;
    }

    public List<SComponente> getTrafos() {
        return trafos;
    }

    public void setTrafos(List<SComponente> trafos) {
        this.trafos = trafos;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Map<String, String> getOpciones() {
        return opciones;
    }

    public void setOpciones(Map<String, String> opciones) {
        this.opciones = opciones;
    }
        
}
