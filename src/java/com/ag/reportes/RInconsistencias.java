/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.reportes;

import com.ag.model.view.Trafo;
import com.ag.service.ReporteManager;
import com.ag.service.SpringContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
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
@ManagedBean(name="rinconsistencias")  
@SessionScoped
public class RInconsistencias {

    private SpringContext context;
    private ReporteManager reporteManager;
    
    private List<Trafo> TrafosInconsistentes;
    private List<String> periodos;
    private String periodo;
     private boolean mostrarTabla;
    
    public RInconsistencias() {
        mostrarTabla=false;
        context = SpringContext.getInstance();        
        reporteManager = (ReporteManager) context.getBean("ReporteManager");
        periodos=reporteManager.getPeriodos();
        TrafosInconsistentes = new ArrayList<Trafo>();
    }    
    
    JasperPrint jasperPrint;
    public void initj() throws JRException, URISyntaxException{
        String reporte="inconsistentes.jasper";        
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(TrafosInconsistentes);
        Map parameters = new HashMap();        
        parameters.put("logo", "com/ag/reportes/jaspers/CEOlogoReportes.jpg");        
        parameters.put("periodo", periodo); 
        jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/"+reporte), parameters, beanCollectionDataSource);
    }
    
    public void llenarDataTable(){
       TrafosInconsistentes = reporteManager.getTrafosInconsistentes(periodo);             
       mostrarTabla = true;
    }
    
    public void exportar() throws JRException, IOException {
         try {
            initj();
            FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                   "El reporte ha sido generado.", ""));
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=Reporte de Inconsistencias "+periodo+".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            
            servletOutputStream.flush();
            servletOutputStream.close();
            
         } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                 "Ha ocurrido un error al generar el reporte.", "")); 
        
         }
  
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public List<String> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<String> periodos) {
        this.periodos = periodos;
    }
    
    public boolean isMostrarTabla() {
        return mostrarTabla;
    }
    
    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }

    public List<Trafo> getTrafosInconsistentes() {
        return TrafosInconsistentes;
    }

    public void setTrafosInconsistentes(List<Trafo> TrafosInconsistentes) {
        this.TrafosInconsistentes = TrafosInconsistentes;
    }
            
}
