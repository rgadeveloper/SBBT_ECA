/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.reportes;

//import com.ag.model.*;
import com.ag.model.Campania;
import com.ag.model.Tbltipo;
import com.ag.model.view.Fecha;
import com.ag.model.view.ResultCampania;
import com.ag.model.view.ResultOrdenTrabajo;
import com.ag.model.view.ordenesDeTrabajo;
//import com.ag.model.view.*;
import com.ag.service.ReporteManager;
import com.ag.service.SpringContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext; 
//import org.primefaces.event.DateSelectEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.primefaces.event.SelectEvent;


/**
 *
 * @author arodriguezr
 */
@ManagedBean
@SessionScoped
public class ReportesOperativo {

    private SpringContext context;
    private ReporteManager reporteManager; 
    private List<Tbltipo> actividades;
    private String actividadSelected;  
    private Date fechaIni;
    private Date fechaFin;     
    private String campaniaSelected;
    private List<Campania> campanias; 
    
    private List<ResultCampania> resultCampanias;
    private boolean mostrarTablaClientes;
    private boolean mostrarTablaMacros;  
    
    private List<ResultOrdenTrabajo> resultOrdenesTrabajo;
    private List<ordenesDeTrabajo> resultOrdenesTrabajoForPDF;
    private boolean mostrarTblYbtnOrdenesTrabajo;
    private boolean mostrarTblYbtnOrdenesIrregulares;
    private boolean mostrarTblYbtnOrdenesSinResolver;
    
    public ReportesOperativo() {
        context = SpringContext.getInstance();        
        reporteManager = (ReporteManager) context.getBean("ReporteManager");        
        setActividades(reporteManager.getActividades());
        campanias = new ArrayList<Campania>();        
        fechaIni= new Date();
        fechaFin= new Date();
    }      
    
    JasperPrint jasperPrint;
    public void initj(String report) throws JRException{
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(resultCampanias);
        String reporte=(report.equals("Operativo Informativo por Clientes"))?
                        "operativoInformativoCliente.jasper":
                        "operativoInformativoMacro.jasper";
        
        jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/"+reporte), new HashMap(), beanCollectionDataSource);
    }
    
   public void initjOT(String report, String formato) throws JRException, URISyntaxException{
        JRBeanCollectionDataSource beanCollectionDataSource;
        String reporte;
        String titulo="";
        Map parameters = new HashMap();
        
        if(formato.equals("pdf")){
           parameters.put("logo", "com/ag/reportes/jaspers/CEOlogoReportes.jpg");
           beanCollectionDataSource=new JRBeanCollectionDataSource(resultOrdenesTrabajoForPDF);
           reporte="ordenesTrabajo.jasper";
           
           if(report.equals("Ordenes de Trabajos con Irregularidad"))
            titulo="con Irregularidad";
           if(report.equals("Ordenes de Trabajos sin Resolver"))
            titulo="sin Resolver";           
            parameters.put("titulo", titulo);           
        }else{
           beanCollectionDataSource=new JRBeanCollectionDataSource(resultOrdenesTrabajo);           
           reporte="ordenesTrabajoXLS.jasper";     
           parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);//para excel
        }     
        
        jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/"+reporte), parameters, beanCollectionDataSource);
    }
    
    
    public void exportar(String report, String formato) throws JRException, IOException {
         try {
             if (report.equals("Operativo Informativo por Clientes") || report.equals("Operativo Informativo por Macros")) {
                initj(report); 
             }else{
                initjOT(report, formato); 
             }            
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename="+report+"."+formato);
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            
            if(formato.equals("pdf"))
             JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            else{
             JRXlsExporter xlsxExporter = new JRXlsExporter();
             xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
             xlsxExporter.exportReport();   
            }
            
            servletOutputStream.flush();
            servletOutputStream.close();
            
         } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "upss", e.getMessage().toString())); 
        
         }
  
    } 
    
    public void clearFechaFinal(SelectEvent event){
        fechaFin=null;
        campanias = new ArrayList<Campania>();
    }
    
    public void clearChangeActividad() {        
        fechaFin=null;
        fechaIni=null;
        campanias.clear();
    }  
    
    public void actualizarComboCampanias(SelectEvent event) {
      campanias = new ArrayList<Campania>(); 
        if (actividadSelected!=null && !actividadSelected.equals("")) {
          if(fechaFin !=null && fechaIni!=null){
            String fi= getFechaFormato(fechaIni, "dd/MM/yyyy");
            String ff= getFechaFormato(fechaFin, "dd/MM/yyyy");
            if (fi.compareTo(ff)==0 || fi.compareTo(ff)<0)
               setCampanias(reporteManager.getCampanias(fi, ff, actividadSelected));                
            else{           
               FacesMessage msg = new FacesMessage("Aviso", "La Fecha Inicial debe ser menor o igual a la Fecha Final");  
               FacesContext.getCurrentInstance().addMessage(null, msg);  
            }
          }     
        } else {
            FacesMessage msg = new FacesMessage("Aviso", "Debe seleccionar el tipo de actividad.");  
               FacesContext.getCurrentInstance().addMessage(null, msg); 
        }
  
        
    }  
    
    public void llenarTableResultadoCampanias(){
        ocultarTablaYBotones();
       if (fechaFin !=null && fechaIni!=null && actividadSelected!=null && !actividadSelected.equals("") && campaniaSelected!=null && !campaniaSelected.equals("")) {
           if (campaniaSelected.equals("todo")) {
               setResultCampanias(reporteManager.getResultCampanias(campanias));
               
           } else {
               setResultCampanias(reporteManager.getResultCampanias(campaniaSelected));
           }
                mostrarTablaYBotones();
        }else{
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Tiene que escoger todas las opciones.", "Inténtalo de nuevo."));  
        }
        
        clearCampos();
    }
    
    public void llenarTableOrdenesTrabajo(){ 
       mostrarTblYbtnOrdenesTrabajo=false;    
        resultOrdenesTrabajoForPDF=new ArrayList<ordenesDeTrabajo>();
       if (fechaFin !=null && fechaIni!=null && actividadSelected!=null && !actividadSelected.equals("") && campaniaSelected!=null && !campaniaSelected.equals("")) {
           if (campaniaSelected.equals("todo")) 
               setResultOrdenesTrabajo(reporteManager.getResultOrdenesTrabajo(campanias,"",resultOrdenesTrabajoForPDF));
           else 
               setResultOrdenesTrabajo(reporteManager.getResultOrdenesTrabajo(campaniaSelected,"",resultOrdenesTrabajoForPDF));
           
           mostrarTblYbtnOrdenesTrabajo=true;
        }else{
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Tiene que escoger todas las opciones.", "Inténtalo de nuevo."));  
        }
        
        clearCampos();
    }
    
    public void llenarTableOrdenesIrregulares(){ 
       mostrarTblYbtnOrdenesIrregulares=false;
       resultOrdenesTrabajoForPDF=new ArrayList<ordenesDeTrabajo>();
       if (fechaFin !=null && fechaIni!=null && actividadSelected!=null && !actividadSelected.equals("") && campaniaSelected!=null && !campaniaSelected.equals("")) {
           if (campaniaSelected.equals("todo")) 
               setResultOrdenesTrabajo(reporteManager.getResultOrdenesTrabajo(campanias, "Irregularidad", resultOrdenesTrabajoForPDF));
           else 
               setResultOrdenesTrabajo(reporteManager.getResultOrdenesTrabajo(campaniaSelected, "Irregularidad", resultOrdenesTrabajoForPDF));
           
           mostrarTblYbtnOrdenesIrregulares=true;
        }else{
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Tiene que escoger todas las opciones.", "Inténtalo de nuevo."));  
        }
        
        clearCampos();
    }
    
    public void llenarTableOrdenesSinResolver(){ 
       mostrarTblYbtnOrdenesSinResolver=false;
       resultOrdenesTrabajoForPDF=new ArrayList<ordenesDeTrabajo>();
       if (fechaFin !=null && fechaIni!=null && actividadSelected!=null && !actividadSelected.equals("") && campaniaSelected!=null && !campaniaSelected.equals("")) {
           if (campaniaSelected.equals("todo")) 
               setResultOrdenesTrabajo(reporteManager.getResultOrdenesTrabajo(campanias, "SinResolver", resultOrdenesTrabajoForPDF));
           else 
               setResultOrdenesTrabajo(reporteManager.getResultOrdenesTrabajo(campaniaSelected, "SinResolver", resultOrdenesTrabajoForPDF));
           
           mostrarTblYbtnOrdenesSinResolver=true;
        }else{
          FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Tiene que escoger todas las opciones.", "Inténtalo de nuevo."));  
        }
        
        clearCampos();
    }
    
    public void clearCampos(){
        fechaFin=null;
        fechaIni=null;
        actividadSelected=null;
        campaniaSelected=null; 
        campanias.clear();
    }
    
    public void mostrarTablaYBotones(){       
        if (actividadSelected.equals("7526")) { //macros
           mostrarTablaMacros=true;           
        } else { //clientes
           mostrarTablaClientes=true;
        }
    }
    
    public void ocultarTablaYBotones(){       
        mostrarTablaClientes=false;
        mostrarTablaMacros=false;
    }
    
    public String getFechaFormato(Date fecha, String formato){
        Fecha formatearFecha=new Fecha(); 
        return formatearFecha.getFechaFormato(fecha, formato);
    }

    public String getActividadSelected() {
        return actividadSelected;
    }

    public void setActividadSelected(String actividadSelected) {
        this.actividadSelected = actividadSelected;
    }

    public List<Tbltipo> getActividades() {
        return actividades;
    }

    public void setActividades(List<Tbltipo> actividades) {
        this.actividades = actividades;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getCampaniaSelected() {
        return campaniaSelected;
    }

    public void setCampaniaSelected(String campaniaSelected) {
        this.campaniaSelected = campaniaSelected;
    }

    public List<Campania> getCampanias() {
        return campanias;
    }

    public void setCampanias(List<Campania> campanias) {
        this.campanias = campanias;
    }

    public List<ResultCampania> getResultCampanias() {
        return resultCampanias;
    }

    public void setResultCampanias(List<ResultCampania> resultCampanias) {
        this.resultCampanias = resultCampanias;
    }   

    public boolean isMostrarTablaClientes() {
        return mostrarTablaClientes;
    }

    public void setMostrarTablaClientes(boolean mostrarTablaClientes) {
        this.mostrarTablaClientes = mostrarTablaClientes;
    }

    public boolean isMostrarTablaMacros() {
        return mostrarTablaMacros;
    }

    public void setMostrarTablaMacros(boolean mostrarTablaMacros) {
        this.mostrarTablaMacros = mostrarTablaMacros;
    }

    public List<ResultOrdenTrabajo> getResultOrdenesTrabajo() {
        return resultOrdenesTrabajo;
    }

    public void setResultOrdenesTrabajo(List<ResultOrdenTrabajo> resultOrdenesTrabajo) {
        this.resultOrdenesTrabajo = resultOrdenesTrabajo;
    }

    public boolean isMostrarTblYbtnOrdenesTrabajo() {
        return mostrarTblYbtnOrdenesTrabajo;
    }

    public void setMostrarTblYbtnOrdenesTrabajo(boolean mostrarTblYbtnOrdenesTrabajo) {
        this.mostrarTblYbtnOrdenesTrabajo = mostrarTblYbtnOrdenesTrabajo;
    }

    public boolean isMostrarTblYbtnOrdenesIrregulares() {
        return mostrarTblYbtnOrdenesIrregulares;
    }

    public void setMostrarTblYbtnOrdenesIrregulares(boolean mostrarTblYbtnOrdenesIrregulares) {
        this.mostrarTblYbtnOrdenesIrregulares = mostrarTblYbtnOrdenesIrregulares;
    }

    public boolean isMostrarTblYbtnOrdenesSinResolver() {
        return mostrarTblYbtnOrdenesSinResolver;
    }
    
}
