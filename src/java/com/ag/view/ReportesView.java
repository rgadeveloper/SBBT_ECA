/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.view.Delegacion;
import com.ag.service.ArbolManager;
import com.ag.service.InterfazManager;
import com.ag.service.ReporteManager;
import com.ag.service.SpringContext;
import java.io.IOException;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Jose Mejia
 */
@ManagedBean
@SessionScoped
public class ReportesView implements Serializable {

    /**
     * Creates a new instance of ReportesView
     */
    @ManagedProperty("#{login}")
    private Login login;
    private String periodo;
    private List<String> reportTipo;
    private SpringContext context;
    private InterfazManager interfazManager;
    private String reportSeleccionado;
    private String tipoDocumento;
    private ArbolManager arbolManager;
    private ReporteManager reporteManager;
    private String delegacionSeleccionada, logoEca, logoGN, rutaReportes;
    private List<Delegacion> delegaList;
    private String nombreDelegacionSeleccionada;
    private List<Delegacion> tipoComponenteS;
    private BigDecimal tipoComponente;

    public ReportesView() {
        reportTipo = new ArrayList<String>();
        tipoComponenteS= new ArrayList<Delegacion>();
        delegaList = new ArrayList<Delegacion>();
        reportTipo.add("Suministro");
        reportTipo.add("Trafo");
        //reportTipo.add("Perdidas");
        reportTipo.add("Suministros totales");
        periodo = "";
        reportSeleccionado = "";
        delegacionSeleccionada = "";
        tipoComponente=BigDecimal.ZERO;
        tipoDocumento = "pdf";
        context = SpringContext.getInstance();
        arbolManager = (ArbolManager) context.getBean("ArbolManager");
        reporteManager = (ReporteManager) context.getBean("ReporteManager");
        delegaList = arbolManager.buscarDelegaciones();
        tipoComponenteS.add(new Delegacion(BigDecimal.valueOf(11), "NIV100", ""));
        tipoComponenteS.add(new Delegacion(BigDecimal.valueOf(12), "NIV200", ""));
        rutaReportes = "com/ag/reportes/jaspers/";
        logoEca = "com/ag/reportes/jaspers/logoEca.png";
        logoGN = "com/ag/reportes/jaspers/logoGN.png";

    }

    public void tipoDoc(String tipoDoc) {
        tipoDocumento = tipoDoc;
    }

    public boolean validarPeriodo() {
        if (periodo != null && !periodo.equals("")) {
            if (periodo.length() == 6) {
                try {
                    Integer.parseInt(periodo);
                    return true;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /*
      * -Siministros sin amarres
      *  componente o arbol
      *  delegación
      * -Trafo
      *   -Periodo
      *   -TipoDocumento (Pdf, Excel)
      *   -ReporteSeleccionado
      *   -DelegacionSeleccionada
      * -
      *  Si las delegación es null, se genera el reporte de trafo
      *  Si no, se genera el de trafos x delegación
      * -Suministro
     */
    public void ejecutarReporte() {
        if (reportSeleccionado != null && !reportSeleccionado.equals("")) {
            if (reportSeleccionado.equals("Suministros totales")) {
                //Cuando selecciona todos los suminostros, no se necesita periodo, ni tipo
//                if (tipoComponente != null && tipoComponente.compareTo(BigDecimal.ZERO) != 0) {
                    if (delegacionSeleccionada != null && !delegacionSeleccionada.equals("")) {
                        generarTodosSuministros();
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso - Informe", "Verifique la delegación. Datos obligatorios."));
                    }
//                } else {
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso - Informe", "Seleccione un tipo árbol para generar el reporte."));
//                }
            } else if (tipoDocumento != null && !tipoDocumento.equals("")) {
                if (validarPeriodo()) {
                    if (reportSeleccionado.equals("Trafo")) {
                        if (delegacionSeleccionada != null && !delegacionSeleccionada.equals("")) {
                            generarTrafoDelegacion();
                        } else {
                            generarTrafo();
                        }
                    } else if (delegacionSeleccionada != null && !delegacionSeleccionada.equals("")) {
                        if (reportSeleccionado.equals("Suministro")) {
                            generarSuministro();
                        } else if (reportSeleccionado.equals("Perdidas")) {
                            generarPerdida();
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso - Informe", "El tipo de reporte que seleccionó no existe."));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso - Informe", "Verifique el tipo de reporte y la delegación. Datos obligatorios."));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso - Informe", "Ingrese un periodo válido por favor"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso - Informe", "El tipo de documento no es válido"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Precaución", "Verifique el tipo de reporte. Dato obligatorio."));
        }
    }

    public void limpiar() {
        periodo = "";
        reportSeleccionado = "";
        tipoDocumento = "pdf";
        delegacionSeleccionada = "";
        tipoComponente =BigDecimal.ZERO;
    }

    public void generarTrafo() {

        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "ReporteTrafo.csv" + "\";");
        try {
            ServletOutputStream out = response.getOutputStream();

            try {
                reporteManager.reporteTrafos(periodo, out);
                out.flush();
                out.close();
                response.flushBuffer();
                ctx.responseComplete();
            } catch (Exception ex) {
                Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarTrafoDelegacion() {

        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "ReporteTrafo.csv" + "\";");
        try {
            ServletOutputStream out = response.getOutputStream();

            try {
                reporteManager.reporteTrafosDelegacion(periodo, delegacionSeleccionada.split("-")[0], response);
                out.flush();
                out.close();
                response.flushBuffer();
                ctx.responseComplete();
            } catch (Exception ex) {
                Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarSuministro() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte_" + reportSeleccionado + ".csv" + "\";");
        try {
            ServletOutputStream out = response.getOutputStream();
            try {
                reporteManager.reporteSuministros(periodo, delegacionSeleccionada.split("-")[0], out);
                out.flush();
                out.close();
                response.flushBuffer();
                ctx.responseComplete();
            } catch (Exception ex) {
                Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void generarTodosSuministros() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte_" + reportSeleccionado + ".csv" + "\";");
        try {
            ServletOutputStream out = response.getOutputStream();
            try {
                reporteManager.reporteTodosSuministros(delegacionSeleccionada.split("-")[0],periodo, out);
                out.flush();
                out.close();
                response.flushBuffer();
                ctx.responseComplete();
            } catch (Exception ex) {
                Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        

    public void generarPerdida() {
        //Genera el reporte de suministro
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("SUBREPORT_DIR", rutaReportes);
        parametros.put("periodo", Integer.parseInt(periodo));
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + reportSeleccionado + "_Reporte." + tipoDocumento + "\";");
        try {
            JasperReport reporte;
            JasperPrint jasperPrint = null;

            JasperReport jasperSubReport;
            JasperReport jasperSubReport2;
            JasperReport jasperSubReport3;
            ServletOutputStream out = response.getOutputStream();
            try {
                //Generamos la conexión
                interfazManager = (InterfazManager) context.getBean("InterfazManager");
                reporte = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/ECA.jasper"));
                jasperSubReport = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/report1.jasper"));
                jasperSubReport2 = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/report2.jasper"));
                jasperSubReport3 = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/report3.jasper"));
                parametros.put("subreportParameter", jasperSubReport);
                parametros.put("subreportParameter", jasperSubReport2);
                parametros.put("subreportParameter", jasperSubReport3);
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, interfazManager.generarConexion());
            } catch (JRException ex) {
                Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (tipoDocumento.equals("pdf")) {
                    JRExporter exporter;
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                    exporter.exportReport();
                } else if (tipoDocumento.equals("xlsx")) {
                    JRXlsxExporter exporter = new JRXlsxExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                    exporter.exportReport();
                }

                out.flush();
                out.close();
                ctx.responseComplete();

            } catch (JRException ex) {
                Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
            }


        } catch (IOException ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public List<String> getReportTipo() {
        return reportTipo;
    }

    public void setReportTipo(List<String> reportTipo) {
        this.reportTipo = reportTipo;
    }

    public String getReportSeleccionado() {
        return reportSeleccionado;
    }

    public void setReportSeleccionado(String reportSeleccionado) {
        this.reportSeleccionado = reportSeleccionado;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDelegacionSeleccionada() {
        return delegacionSeleccionada;
    }

    public void setDelegacionSeleccionada(String delegacionSeleccionada) {
        this.delegacionSeleccionada = delegacionSeleccionada;
    }

    public List<Delegacion> getDelegaList() {
        return delegaList;
    }

    public void setDelegaList(List<Delegacion> delegaList) {
        this.delegaList = delegaList;
    }

    /**
     * @return the nombreDelegacionSeleccionada
     */
    public String getNombreDelegacionSeleccionada() {
        return nombreDelegacionSeleccionada;
    }

    /**
     * @param nombreDelegacionSeleccionada the nombreDelegacionSeleccionada to
     * set
     */
    public void setNombreDelegacionSeleccionada(String nombreDelegacionSeleccionada) {
        this.nombreDelegacionSeleccionada = nombreDelegacionSeleccionada;
    }

    public List<Delegacion> getTipoComponenteS() {
        return tipoComponenteS;
    }

    public void setTipoComponenteS(List<Delegacion> tipoComponenteS) {
        this.tipoComponenteS = tipoComponenteS;
    }

    

    public BigDecimal getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(BigDecimal tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    
    public class export {

        private BigDecimal RANK;
        private BigDecimal PERIODO;
        private BigDecimal RANGO_BALANCE;
        private String RANGOS;
        private String TIPOPCI;
        private String PERDIDAS_KW_MES;
        private String CANTIDAD;

        public export() {
        }

        public BigDecimal getRANK() {
            return RANK;
        }

        public void setRANK(BigDecimal RANK) {
            this.RANK = RANK;
        }

        public BigDecimal getPERIODO() {
            return PERIODO;
        }

        public void setPERIODO(BigDecimal PERIODO) {
            this.PERIODO = PERIODO;
        }

        public BigDecimal getRANGO_BALANCE() {
            return RANGO_BALANCE;
        }

        public void setRANGO_BALANCE(BigDecimal RANGO_BALANCE) {
            this.RANGO_BALANCE = RANGO_BALANCE;
        }

        public String getRANGOS() {
            return RANGOS;
        }

        public void setRANGOS(String RANGOS) {
            this.RANGOS = RANGOS;
        }

        public String getTIPOPCI() {
            return TIPOPCI;
        }

        public void setTIPOPCI(String TIPOPCI) {
            this.TIPOPCI = TIPOPCI;
        }

        public String getPERDIDAS_KW_MES() {
            return PERDIDAS_KW_MES;
        }

        public void setPERDIDAS_KW_MES(String PERDIDAS_KW_MES) {
            this.PERDIDAS_KW_MES = PERDIDAS_KW_MES;
        }

        public String getCANTIDAD() {
            return CANTIDAD;
        }

        public void setCANTIDAD(String CANTIDAD) {
            this.CANTIDAD = CANTIDAD;
        }
    }
}
