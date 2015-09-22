
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import com.ag.service.ReporteManager;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrador
 */
public class HiloReporte implements Runnable {

    public static boolean activo;
    private FacesContext ctx;
    private HttpServletResponse response;
    private ReporteManager reporteManager;
    private String periodo, reporte, archivo, delegacion;

    public HiloReporte(FacesContext ctx, HttpServletResponse response, ReporteManager reporteManager, String periodo, String reporte, String archivo) {
        this.ctx = ctx;
        this.response = response;
        this.reporteManager = reporteManager;
        this.periodo = periodo;
        this.reporte = reporte;
        this.archivo = archivo;

    }

    public HiloReporte(FacesContext ctx, HttpServletResponse response, ReporteManager reporteManager, String periodo, String reporte, String archivo, String delegacion) {
        this.ctx = ctx;
        this.response = response;
        this.reporteManager = reporteManager;
        this.periodo = periodo;
        this.reporte = reporte;
        this.archivo = archivo;
        this.delegacion = delegacion;
    }

    public void run() {
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo + "\";");
        ServletOutputStream out = null;
        try {
            System.out.println(activo);
            while (activo);
            activo = true;
            out = response.getOutputStream();
            if (reporte.equals("TRAFO")) {
                reporteManager.reporteTrafos(periodo, out);
            } else if (reporte.equals("TRAFO_DELEGACION")) {
                reporteManager.reporteTrafosDelegacion(periodo, delegacion, response);
            } else if (reporte.equals("SUMINISTRO")) {
                reporteManager.reporteSuministros(periodo, delegacion, out);
            }
            out.flush();
            out.close();
            ctx.responseComplete();

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);            
            ctx.responseComplete();

        } finally {
            activo = false;            
            ctx.responseComplete();
        }

    }

    public static void main(String[] arg) {

        boolean prueba;
        System.out.println("");
    }
}
