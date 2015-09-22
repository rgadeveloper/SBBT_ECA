package com.ag.reportes;

import com.ag.model.Balances;
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
import java.util.Set;
import java.util.TreeMap;
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

/**
 *
 * @author arodriguezr
 */
@ManagedBean(name = "reporte")
@SessionScoped
public class Reporte {

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

  private List<Componente> trafos;
  private List<TrafoPeriodos> trafoPeriodos;

  private String opcion, opcionCopy;
  private Map<String, String> opciones = new HashMap<String, String>();

  private boolean mostrarTabla;

  private String selectBDI = "";
  private String displaySelectTrafo;
  private String displayInputText = "none";

  public Reporte() {
    context = SpringContext.getInstance();
    reporteManager = (ReporteManager) context.getBean("ReporteManager");

    opciones.put("MES", "MES");
    opciones.put("MOVIL", "MOVIL");

    vistas.put("ELECTRICA", "ELECTRICA");
    vistas.put("GEOGRAFICA", "GEOGRAFICA");
    Map<String, String> electrica = new HashMap<String, String>();
    Map<String, String> geografica = new HashMap<String, String>();

    geografica.put("BARRIO", "7");
    geografica.put("EMPRESA", "0");
    geografica.put("MUNICIPIO", "6");
        //geografica.put("ZONA", "5");
    //Se agrega valor 1 para la zona en vista geografica debido a que no estaba
    //listando las zonas en el reporte de histórico
    geografica.put("ZONA", "1");
    geografica.put("TRANSFORMADORES", "NIV200");

    electrica.put("CIRCUITO", "3");
    electrica.put("EMPRESA", "4");
    electrica.put("ÁREA-MT", "2");
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

        //list = reporteManager.getZonasByTipo("5");
    //Se agrega valor 1 para la busqueda de zonas por tipo en vista geografica
    //debido a que no estaba listando las zonas en el reporte de histórico
    list = reporteManager.getZonasByTipo("1");
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

  public Map ordenaHashMap(Map map) {
    Map mapOrdenado = new TreeMap(map);
    Set ref = mapOrdenado.keySet();
    Iterator it = ref.iterator();
    return mapOrdenado;
  }

  public Map<String, String> llenarMapZona(List list) {
    Iterator iter = list.iterator();
    Map<String, String> map = new HashMap<String, String>();
    //map.put("TODO", "TODO");
    while (iter.hasNext()) {
      ZonaGeografica z = (ZonaGeografica) iter.next();
      String id = z.getIdZona().toString();
//            String nombre = z.getIdComercial() != null
//                    ? z.getIdComercial()
//                    : id + "-" + z.getNombre();
      String nombre = z.getNombre() != null
              ? z.getNombre()
              : id + "-" + z.getIdComercial();
      map.put(nombre, id);
    }
    //Map mapOrdenado = ordenaHashMap(map);
    list.clear();
    return ordenaHashMap(map);
  }

  public Map<String, String> llenarMapSubestacion(List list) {
    Iterator iter = list.iterator();
    Map<String, String> map = new HashMap<String, String>();
    //map.put("TODO", "TODO");
    while (iter.hasNext()) {
      ZonaGeografica z = (ZonaGeografica) iter.next();
      String id = z.getIdZona().toString();
//            String nombre = z.getIdComercial() != null
//                    ? z.getIdComercial() + " /" + reporteManager.getZona(z.getIdPadre().toString()).getIdComercial()
//                    : id + "-" + z.getNombre();
      String nombre = z.getNombre() != null
              ? z.getNombre()
              : id + "-" + z.getIdComercial();
      map.put(nombre, id);
    }
    list.clear();
    return ordenaHashMap(map);
  }

  public Map<String, String> llenarMapTrafo(List list) {
    Iterator iter = list.iterator();
    Map<String, String> map = new HashMap<String, String>();
//        map.put("TODO", "TODO");
    while (iter.hasNext()) {
      Componente c = (Componente) iter.next();
      String id = c.getIdComponente().toString();
//            String nombre = c.getIdCliente() != null
//                    ? c.getIdCliente()
//                    : id + "-" + c.getNombre();
      String nombre = c.getNombre() != null
              ? c.getNombre()
              : id + "-" + c.getIdComponente();
      map.put(nombre, id);
    }
    list.clear();
    return ordenaHashMap(map);
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
      if (tipo.equals("NIV100") || tipo == "NIV100" || tipo.equals("NIV200") || tipo == "NIV200") {
        //selectTrafoEnable = true;
        displaySelectTrafo = "none";
        displayInputText = "block";
        componentes = new HashMap<String, String>();
      } else {
        //selectTrafoEnable = false;
        displaySelectTrafo = "block";
        displayInputText = "none";
        componentes = componentesByTipos.get(tipo);
      }
      
    } else {
      componentes = new HashMap<String, String>();
    }
  }

  public void llenarDataTable() {
    if (tipo.equals("NIV100") || tipo.equals("NIV200")) componente = selectBDI;
    if ((opcion != null && !opcion.equals("")) && (tipo != null && !tipo.equals("")) && (vista != null && !vista.equals("")) && 
            (componente != null && !componente.equals(""))) {
      if (tipo.equals("3") || tipo.equals("7")) {
        setTrafos(reporteManager.getTrafosByCircuitoOrBarrio(componente));
      } else {
        if (tipo.equals("0") || tipo.equals("4")) {
          setTrafos(reporteManager.getTrafosByEmpresa(componente));
        } else {
          if (tipo.equals("2") || tipo.equals("6")) {
            setTrafos(reporteManager.getTrafosBySubestacion(componente));
          } else {
            if (tipo.equals("1") || tipo.equals("5")) {
              setTrafos(reporteManager.getTrafosByZona(componente));
            } else {
              if (tipo.equals("NIV100") || tipo.equals("NIV200")) {
                setTrafos(componente.equals("TODO")
                        ? reporteManager.getTrafosByVista(tipo)
                        : reporteManager.getTrafoByNombre(componente));
//                                        : reporteManager.getTrafo(componente));
              } else {
                trafos = new ArrayList<Componente>();
              }
            }
          }
        }
      }

      //Llenar trafosPerioos
      llenarTrafosPeriodos();
      mostrarTabla = true;
      opcionCopy = opcion;
      opcion = null;
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

  String[] periodo;

  public void llenarTrafosPeriodos() {
    trafoPeriodos = new ArrayList<TrafoPeriodos>();
    periodo = reporteManager.getPeriodoMax();
    Iterator iter = trafos.iterator();
    //del Componente Seleccionado 
    if (tipo != null && !tipo.equals("") && !tipo.equals("8") && !tipo.equals("NIV100") && !tipo.equals("NIV200")) {
      ZonaGeografica cSeleccionado = reporteManager.getZona(componente, tipo);
      TrafoPeriodos hSeleccionado = new TrafoPeriodos();
      String idComercial = cSeleccionado.getIdComercial() != null
              ? cSeleccionado.getIdComercial()
              : cSeleccionado.getIdZona().toString() + "-" + cSeleccionado.getNombre();
      hSeleccionado.setIdComercial(idComercial);
      hSeleccionado.setTipo(tipo);
      hSeleccionado.setIdTrafo(cSeleccionado.getIdZona().toString());
      hSeleccionado.setNombre(cSeleccionado.getNombre()/*+" /"+reporteManager.getZona(cSeleccionado.getIdPadre().toString()).getIdComercial()*/);
      String[] mes = new String[12];
      String[] movil = new String[12];
      for (int i = 0; i < 12; i++) {
        String porcMes = "-";
        String porcMovil = "-";
        Balances b = reporteManager.getBalanceByPeriodo(cSeleccionado.getIdZona().toString(), periodo[i], tipo);
        if (b != null) {
          porcMes = b.getPorcPerdidaMes() == null ? "-" : String.valueOf(b.getPorcPerdidaMes());
          porcMovil = b.getPorcPerdidaMovil() == null ? "-" : String.valueOf(b.getPorcPerdidaMovil());
        }
        mes[i] = porcMes;
        movil[i] = porcMovil;
      }
      hSeleccionado.setMes1(mes[0]);
      hSeleccionado.setMovil1(movil[0]);
      hSeleccionado.setMes2(mes[1]);
      hSeleccionado.setMovil2(movil[1]);
      hSeleccionado.setMes3(mes[2]);
      hSeleccionado.setMovil3(movil[2]);
      hSeleccionado.setMes4(mes[3]);
      hSeleccionado.setMovil4(movil[3]);
      hSeleccionado.setMes5(mes[4]);
      hSeleccionado.setMovil5(movil[4]);
      hSeleccionado.setMes6(mes[5]);
      hSeleccionado.setMovil6(movil[5]);
      hSeleccionado.setMes7(mes[6]);
      hSeleccionado.setMovil7(movil[6]);
      hSeleccionado.setMes8(mes[7]);
      hSeleccionado.setMovil8(movil[7]);
      hSeleccionado.setMes9(mes[8]);
      hSeleccionado.setMovil9(movil[8]);
      hSeleccionado.setMes10(mes[9]);
      hSeleccionado.setMovil10(movil[9]);
      hSeleccionado.setMes11(mes[10]);
      hSeleccionado.setMovil11(movil[10]);
      hSeleccionado.setMes12(mes[11]);
      hSeleccionado.setMovil12(movil[11]);
      trafoPeriodos.add(hSeleccionado);
    }

    //de los trafos asociados al componente seleccionado 
    while (iter.hasNext()) {
      Componente c = (Componente) iter.next();
      TrafoPeriodos h = new TrafoPeriodos();
      String idComercial = c.getIdCliente() != null
              ? c.getIdCliente()
              : c.getIdComponente().toString() + "-" + c.getNombre();
      h.setIdComercial(idComercial);
      h.setTipo("8");
      h.setIdTrafo(c.getIdComponente().toString());
      h.setNombre(c.getNombre());

      String[] mes = new String[12];
      String[] movil = new String[12];

      for (int i = 0; i < 12; i++) {
        String porcMes = "-";
        String porcMovil = "-";

        Balances b = reporteManager.getBalanceByPeriodo(c.getIdComponente().toString(), periodo[i], "8");
        if (b != null) {
          porcMes = b.getPorcPerdidaMes() == null ? "-" : String.valueOf(b.getPorcPerdidaMes());
          porcMovil = b.getPorcPerdidaMovil() == null ? "-" : String.valueOf(b.getPorcPerdidaMovil());
        }
        mes[i] = porcMes;
        movil[i] = porcMovil;
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
    trafos.clear();
  }

  public String porcentajeByperiodo(String idComponente, int i, String tipo) {
    String porcentaje = "-";
    Balances b = reporteManager.getBalanceByPeriodo(idComponente, periodo[i], tipo);
    if (b != null) {
      if (opcionCopy.equals("MES")) {
        porcentaje = b.getPorcPerdidaMes() == null ? "" : String.valueOf(b.getPorcPerdidaMes());
      } else {
        porcentaje = b.getPorcPerdidaMovil() == null ? "" : String.valueOf(b.getPorcPerdidaMovil());
      }
    }
    return porcentaje;
  }

  JasperPrint jasperPrint;

  public void initj(String formato) throws JRException, URISyntaxException {
    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(trafoPeriodos);
    String reporte;
    Map parameters = new HashMap();
    if (formato.equals("pdf")) {
      parameters.put("logo", "com/ag/reportes/jaspers/logoEca.png");
      reporte = (opcionCopy.equals("MES"))
              ? "historico.jasper"
              : "historicoMovil.jasper";
    } else {
      reporte = (opcionCopy.equals("MES"))
              ? "historicoXLS.jasper"
              : "historicoMovilXLS.jasper";
      parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);//para excel
    }

    parameters.put("periodo", periodo);

    jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("com/ag/reportes/jaspers/" + reporte), parameters, beanCollectionDataSource);
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

  public List<Componente> getTrafos() {
    return trafos;
  }

  public void setTrafos(List<Componente> trafos) {
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

  public List<TrafoPeriodos> getTrafoPeriodos() {
    return trafoPeriodos;
  }

  public void setTrafoPeriodos(List<TrafoPeriodos> trafoPeriodos) {
    this.trafoPeriodos = trafoPeriodos;
  }

  public boolean isMostrarTabla() {
    return mostrarTabla;
  }

  public void setMostrarTabla(boolean mostrarTabla) {
    this.mostrarTabla = mostrarTabla;
  }

  public String getSelectBDI() {
    return selectBDI;
  }

  public void setSelectBDI(String selectBDI) {
    this.selectBDI = selectBDI;
  }

  public String getDisplaySelectTrafo() {
    return displaySelectTrafo;
  }

  public void setDisplaySelectTrafo(String displaySelectTrafo) {
    this.displaySelectTrafo = displaySelectTrafo;
  }

  public String getDisplayInputText() {
    return displayInputText;
  }

  public void setDisplayInputText(String displayInputText) {
    this.displayInputText = displayInputText;
  }
  
}
