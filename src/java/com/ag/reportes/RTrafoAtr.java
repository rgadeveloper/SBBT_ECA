/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.reportes;

/**
 *
 * @author arodriguezr
 */
import com.ag.model.AtrComponente;
import com.ag.model.Componente;
import com.ag.model.ZonaGeografica;
import com.ag.service.ReporteManager;
import com.ag.service.SpringContext;
import java.io.IOException;
import java.net.URISyntaxException;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author arodriguezr
 */
@ManagedBean
@SessionScoped
public class RTrafoAtr {
    
    private SpringContext context;
    private ReporteManager reporteManager;
    private String tipo;
    private String componente;
    private Map<String, String> tipos = new HashMap<String, String>();
    private Map<String, Map<String, String>> componentesByTipos = new HashMap<String, Map<String, String>>();
    private Map<String, String> componentes = new HashMap<String, String>();
    private String vista;
    private Map<String, String> vistas = new HashMap<String, String>();
    private Map<String, Map<String, String>> tiposByVistas = new HashMap<String, Map<String, String>>();
    private List<AtrComponente> trafosAtr;
    private boolean mostrarTabla;
    
    public RTrafoAtr() {
        context = SpringContext.getInstance();
        reporteManager = (ReporteManager) context.getBean("ReporteManager");
        
        vistas.put("ELECTRICA", "ELECTRICA");
        vistas.put("GEOGRAFICA", "GEOGRAFICA");
        Map<String, String> electrica = new HashMap<String, String>();
        Map<String, String> geografica = new HashMap<String, String>();
        
        geografica.put("BARRIO", "7");
        geografica.put("EMPRESA", "0");
        geografica.put("MUNICIPIO", "6");
        geografica.put("ZONA", "5");
        geografica.put("TRANSFORMADORES", "NIV200");
        
        electrica.put("CIRCUITO", "3");
        electrica.put("EMPRESA", "4");
        electrica.put("SUBESTACION", "2");
        electrica.put("ZONA", "1");
        electrica.put("TRANSFORMADORES", "NIV100");
        
        tiposByVistas.put("ELECTRICA", electrica);
        tiposByVistas.put("GEOGRAFICA", geografica);
        
        List<ZonaGeografica> list;
        List<Componente> listTrafos;
        
        list = reporteManager.getZonasByTipo("7");
        Map<String, String> barrios = llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("3");
        Map<String, String> circuitos = llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("0");
        Map<String, String> empresasVE = llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("4");
        Map<String, String> empresasVG = llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("2");
        Map<String, String> subestaciones = llenarMapSubestacion(list);
        
        list = reporteManager.getZonasByTipo("6");
        Map<String, String> municipios = llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("5");
        Map<String, String> zonasVG = llenarMapZona(list);
        
        list = reporteManager.getZonasByTipo("1");
        Map<String, String> zonasVE = llenarMapZona(list);
        
        listTrafos = reporteManager.getTrafosByVista("NIV200");
        Map<String, String> trafosVG = llenarMapTrafo(listTrafos);
        
        listTrafos = reporteManager.getTrafosByVista("NIV100");
        Map<String, String> trafosVE = llenarMapTrafo(listTrafos);
        
        componentesByTipos.put("7", barrios);
        componentesByTipos.put("3", circuitos);
        componentesByTipos.put("0", empresasVE);
        componentesByTipos.put("4", empresasVG);
        componentesByTipos.put("6", municipios);
        componentesByTipos.put("2", subestaciones);
        componentesByTipos.put("1", zonasVE);
        componentesByTipos.put("5", zonasVG);
        componentesByTipos.put("NIV200", trafosVG);
        componentesByTipos.put("NIV100", trafosVE);
    }
    
    public Map<String, String> llenarMapZona(List list) {
        Iterator iter = list.iterator();
        Map<String, String> map = new HashMap<String, String>();
        //map.put("TODO", "TODO");
        while (iter.hasNext()) {
            ZonaGeografica z = (ZonaGeografica) iter.next();
            String id = z.getIdZona().toString();
            String nombre = z.getIdComercial() != null ? z.getIdComercial()
                    : id + "-" + z.getNombre();
            map.put(nombre, id);
        }
        list.clear();
        return map;
    }
    
    public Map<String, String> llenarMapSubestacion(List list) {
        Iterator iter = list.iterator();
        Map<String, String> map = new HashMap<String, String>();
        //map.put("TODO", "TODO");
        while (iter.hasNext()) {
            ZonaGeografica z = (ZonaGeografica) iter.next();
            String id = z.getIdZona().toString();
            String nombre = z.getIdComercial() != null
                    ? z.getIdComercial() + " /" + reporteManager.getZona(z.getIdPadre().toString()).getIdComercial()
                    : id + "-" + z.getNombre();
            map.put(nombre, id);
        }
        list.clear();
        return map;
    }
    
    public Map<String, String> llenarMapTrafo(List list) {
        Iterator iter = list.iterator();
        Map<String, String> map = new HashMap<String, String>();
        map.put("TODO", "TODO");
        while (iter.hasNext()) {
            Componente c = (Componente) iter.next();
            String id = c.getIdComponente().toString();
            String nombre = c.getIdCliente() != null
                    ? c.getIdCliente()
                    : id + "-" + c.getNombre();
            map.put(nombre, id);
        }
        list.clear();
        return map;
    }
    
    public void actualizarComboTipos() {
        tipo = null;
        componente = null;
        actualizarComboComponentes();
        if (vista != null && !vista.equals("")) {
            tipos = tiposByVistas.get(vista);
        } else {
            tipos = new HashMap<String, String>();
        }
    }
    
    public void actualizarComboComponentes() {
        if (tipo != null && !tipo.equals("")) {
            componentes = componentesByTipos.get(tipo);
        } else {
            componentes = new HashMap<String, String>();
        }
    }
    
    public void llenarDataTable() {
        if ((tipo != null && !tipo.equals("")) && (vista != null && !vista.equals("")) && (componente != null && !componente.equals(""))) {
            if (tipo.equals("3") || tipo.equals("7")) {
                setTrafosAtr(reporteManager.getTrafosAtrByCircuitoOrBarrio(componente));
            } else {
                if (tipo.equals("0") || tipo.equals("4")) {
                    setTrafosAtr(reporteManager.getTrafosAtrByEmpresa(componente));
                } else {
                    if (tipo.equals("2") || tipo.equals("6")) {
                        setTrafosAtr(reporteManager.getTrafosAtrBySubestacion(componente));
                    } else {
                        if (tipo.equals("1") || tipo.equals("5")) {
                            setTrafosAtr(reporteManager.getTrafosAtrByZona(componente));
                        } else {
                            if (tipo.equals("NIV100") || tipo.equals("NIV200")) {
                                setTrafosAtr(componente.equals("TODO")
                                        ? reporteManager.getTrafosAtrByVista(tipo)
                                        : reporteManager.getTrafoAtr(componente));
                            } else {
                                trafosAtr = new ArrayList<AtrComponente>();
                            }
                        }
                    }
                }
            }
            
            mostrarTabla = true;
            tipo = null;
            tipos = new HashMap<String, String>();
            vista = null;
            componente = null;
            componentes = new HashMap<String, String>();
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Tiene que escoger todas las opciones.", "Inténtalo de nuevo."));
            mostrarTabla = false;
        }
    }
    
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
        
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFColor n = new HSSFColor();
        
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);        
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);            
            cell.setCellStyle(cellStyle);
        }
        
        //prueba
        
        List cellDataList = new ArrayList();
        Iterator rowIterator = sheet.rowIterator();
        HSSFPalette palette = wb.getCustomPalette();        
        while (rowIterator.hasNext()) {
            HSSFRow hssfRow = (HSSFRow) rowIterator.next();
            HSSFCell cellPorcPer = hssfRow.getCell(5);
            palette.setColorAtIndex(HSSFColor.GREEN.index,(byte)0, (byte)0, (byte)100);
            cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
            cellDataList.add(cellPorcPer); 
            cellPorcPer.setCellStyle(cellStyle);
        }
        
    }
    JasperPrint jasperPrint;
    
    public void initj(String formato) throws JRException, URISyntaxException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(trafosAtr);
        String reporte;
        Map parameters = new HashMap();
        if (formato.equals("pdf")) {
            parameters.put("logo", "com/ag/reportes/jaspers/CEOlogoReportes.jpg");
            reporte = "componenteAtr.jasper";
        } else {
            reporte = "componenteAtrXLS.jasper";
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);//para excel
        }

        jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/"+reporte), parameters, beanCollectionDataSource);
    }
    
    public void exportar(String formato) throws JRException, IOException {
        try {
            initj(formato);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=Historico de Perdidas." + formato + "");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            
            if (formato.equals("pdf")) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            } else {
                JRXlsExporter xlsxExporter = new JRXlsExporter();
                xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
                xlsxExporter.exportReport();
            }
            
            servletOutputStream.flush();
            servletOutputStream.close();
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "upss", e.getMessage().toString()));
        }
        
    }

    /*
     * public void jasperPrueba(){ FacesContext.getCurrentInstance().addMessage
     * (null, new FacesMessage(FacesMessage.SEVERITY_WARN, "upss",
     * "configurar")); }
     */
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
    
    public List<AtrComponente> getTrafosAtr() {
        return trafosAtr;
    }
    
    public void setTrafosAtr(List<AtrComponente> trafosAtr) {
        this.trafosAtr = trafosAtr;
    }
    
    public boolean isMostrarTabla() {
        return mostrarTabla;
    }
    
    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }
}
