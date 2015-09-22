/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.Simulacion;

import com.ag.model.*;
import com.ag.view.*;
import com.ag.model.view.ComboLista;
import com.ag.model.view.DataBalanceHijo;
import com.ag.model.view.DataRangosBalance;
import com.ag.service.SpringContext;
import com.ag.model.view.Nodo;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import com.ag.model.view.*;
import com.ag.service.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;


@ManagedBean
@SessionScoped
public class STreeBean {

    private String idComponente;    
    private TreeNode root; 
    private TreeNode selectedNode;
    private HashMap data;
    private SpringContext context;
    private ArbolManager arbolManager;
    private SBalanceManager balanceManager;
    private String tipoArbol;
    private String periodo = "201205";
    private String estilo = "font-size:12px;";
    private List<DataBalanceHijo> infoBalance;
    private SBalances balance;
    //  private String mostrarPanelGrid;
    private DataRangosBalance dataRangosBalance;
    private Boolean mostrarTablaZona;
    private Boolean mostrarTablaTrafo;
    private MapBean mapa;
    private String url;
    private HashMap zoomTipoComponente;
    private ComboLista periodoSelected;
    private List<ComboLista> listPeriodos;
    private boolean isInicial = true;
    private SMedida medida;
    private SAtrComponenteMedida atrComponenteMedida;
    private SAtrComponente  atrComponente;
    private SComponenteMedida componenteMedida;
    private SComponente componente;
    private PadreHijo relacionGeo,relacionElec;
    private List<DataValue> listCantXTipoUso;
    private String cantSumNoMedidos;
    private List<SMedida> listMedida;
    private String codigoComponenteSelected;
    private long factorMultiplicador;
    private BigDecimal cargaInstalada;
    private BigDecimal consumoFacturado;
    private BigDecimal potencia;
    private short fases;
    private short diasFacturados;
    private List<Tbltipo> tiposConexiones;
    private String tipoConexion;
    private List<Tbltipo> tiposUsos;
    private String tipoUso;
    private List<ZonaGeografica> zonas;
    private BigDecimal codigoZona;   
    private List<SComponente> trafos;
    private BigDecimal codigoTrafo;
    private BigDecimal codigoTrafoActual;  
    
    private String rutaComponente;
    private String idClienteAbuscar;
    private String tipoComponente;
    
    private String idBarCir;
    
     private PieChartModel tortaTotal;
    private PieChartModel tortaFuncionan;
    private PieChartModel tortaNoFuncionan;
    private boolean mostrarTorta;
    
    private CartesianChartModel categoryModel;  
    private boolean mostrarChartLine;
    
    private String grafico;   
    private String graficoVE;
    
    private List<SComponente> trafosBajo;
    private List<SComponente> trafosMedio;
    private List<SComponente> trafosCritico;
    private List<SComponente> trafosNegativos;
    private List<SComponente> trafosInconsistentes;
    private List<SComponente> trafosSinBalances;
    //Pto.20140721.ini
    private ZonaGeografica zonaGeografica;
    private String nombreZona;
    private String nombreDelegacion;
    private String nombreAreaEnergetica;
    private String nombreCircuito;
    private String matriculaCircuito;
    private String idComercialAreaMt;
    private String idComercialCircuito;
    //Pto.20140721.Fin

    public STreeBean() {
        grafico="SPestanaGraficaTorta.xhtml"; 
        graficoVE="SVEpestanaGraficaTorta.xhtml";
        context = SpringContext.getInstance();
        // Obtenemos el servicio ArbolManager
        arbolManager = (ArbolManager) context.getBean("SArbolManager");
        periodo = "201205"; 
    }

    //@PostConstruct
    public void init() {
        try {
       // Se inicia el contexto donde están los servicios
        //context = SpringContext.getInstance();
        // Obtenemos el servicio ArbolManager
       
        balanceManager =  (SBalanceManager) context.getBean("SBalanceManager");
        setTiposConexiones(balanceManager.getTiposConexionesAll());
        setTiposUsos(balanceManager.getTiposUsosAll());
        
        if (tipoArbol.equals("NIV200")){
           setZonas(balanceManager.getCircuitosOrBarrios("7"));
           setTrafos(balanceManager.getTrafos("7"));
        }else{
           setZonas(balanceManager.getCircuitosOrBarrios("3")); 
           setTrafos(balanceManager.getTrafos("3"));
        }
        
        setZoomTipoComponente();
        String codigoNodo = "-1";
        String tipo = "-1";
        String clave = codigoNodo + tipo;
         int tipoCompo ;
        data = new HashMap();
        root = new DefaultTreeNode(codigoNodo, null);
        //Se llena el valor del periodo con el maximo de la base de datos
        if(isInicial){
            setListPeriodos((List<ComboLista>) arbolManager.getPeriodo());
            periodo = ((ComboLista)getListPeriodos().get(0)).getId();
            
        }
        // Definimos el Objeto Nodo raiz
        Nodo n0 = new Nodo(codigoNodo);
        // Se obtiene el arbol inicial

        List hijos = arbolManager.cargaArbolInicial(getTipoArbol(), periodo);
        
        n0.setHijos(hijos);
        data.put(clave, n0);
        setMapa(new MapBean());
        // Los hijos de nodo raiz son agregados a la estructura data para que se pueda obtener la info a mostrar en el arbol
        if (hijos != null) {
            for (int i = 0; i < hijos.size(); i++) {
                Nodo nodoDataHijo = (Nodo) hijos.get(i);
                data.put(nodoDataHijo.getCodigo() + nodoDataHijo.getTipo(), nodoDataHijo);
                if (i == 0) {
                    //getMapa().setZoomTipoComponente(zoomTipoComponente);
                    //getMapa().setNodo(nodoDataHijo);
                    
                    infoBalance = balanceManager.cuadroMando(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo);
                    balance = balanceManager.getBalances(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo);

                    url= "";
                    tipoCompo = Integer.parseInt(nodoDataHijo.getTipo());

                    if (tipoCompo == 0 || tipoCompo == 1 || tipoCompo == 4 || tipoCompo == 5 ){
                        dataRangosBalance = balanceManager.getRangosZonas(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), tipoArbol, periodo);
                        url = tipoArbol.equals("NIV200")?"SVistaGeograficaEmp.xhtml":"SVistaElectricaEmp.xhtml";
                        trafosBajo=balanceManager.getTrafosByRango(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo, "BAJO");
                        trafosMedio=balanceManager.getTrafosByRango(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo, "MEDIO");
                        trafosCritico=balanceManager.getTrafosByRango(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo, "CRITICO");
                        trafosNegativos=balanceManager.getTrafosByRango(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo, "NEGATIVO");
                        trafosInconsistentes=balanceManager.getTrafosByRango(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo, "INCONSISTENTE");
                        trafosSinBalances=balanceManager.getTrafosByRango(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo, "SIN BALANCE");
                        
                        nombreZona = nodoDataHijo.getNombre();
                        
                        visualizarChartLine(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo(), periodo);
                        visualizarTortas();                                               
                    }
                    if(tipoCompo == 2 || tipoCompo == 13 ) {
                         url = tipoArbol.equals("NIV200")?"SVistaGeograficaPobla.xhtml":"SVistaElectricaArea.xhtml";
                         setMedida(balanceManager.getMedida(nodoDataHijo.getCodigo(), periodo, nodoDataHijo.getTipo()));
                         setAtrComponenteMedida(balanceManager.getAtrComponenteMedida(nodoDataHijo.getCodigo(), periodo));
                         setAtrComponente(balanceManager.getAtrComponente(nodoDataHijo.getCodigo()));
                         setComponenteMedida(balanceManager.getComponenteMedida(nodoDataHijo.getCodigo(), periodo));
                         setComponente(balanceManager.getComponente(nodoDataHijo.getCodigo()));  
                         relacionGeo = balanceManager.getZonaMunicipio(nodoDataHijo.getCodigo(), periodo);
                         relacionElec = balanceManager.getSubestacionCircuito(nodoDataHijo.getCodigo(), periodo);
                         
                         nombreAreaEnergetica = nodoDataHijo.getNombre();
                         idComercialAreaMt = areaMt(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo());
                    }
                    if(tipoCompo == 6 || tipoCompo == 3) {
                         url = tipoArbol.equals("NIV200")?"SVistaGeograficaMunic.xhtml":"SVistaElectricaMunic.xhtml";
                         setMedida(balanceManager.getMedida(nodoDataHijo.getCodigo(), periodo, nodoDataHijo.getTipo()));
                         setAtrComponenteMedida(balanceManager.getAtrComponenteMedida(nodoDataHijo.getCodigo(), periodo));
                         setAtrComponente(balanceManager.getAtrComponente(nodoDataHijo.getCodigo()));
                         setComponenteMedida(balanceManager.getComponenteMedida(nodoDataHijo.getCodigo(), periodo));
                         setComponente(balanceManager.getComponente(nodoDataHijo.getCodigo()));  
                         relacionGeo = balanceManager.getZonaMunicipio(nodoDataHijo.getCodigo(), periodo);
                         relacionElec = balanceManager.getSubestacionCircuito(nodoDataHijo.getCodigo(), periodo);
                         
                         matriculaCircuito = nodoDataHijo.getNombre();
                         nombreCircuito = nombreCircuito(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo());
                         idComercialCircuito = comercial(nodoDataHijo.getCodigo(), nodoDataHijo.getTipo());
                    }
                    if(tipoCompo == 7 || tipoCompo == 11 ||  tipoCompo == 12) {
                         url = tipoArbol.equals("NIV200")?"SVistaGeograficaBarrio.xhtml":"SVistaElectricaDelega.xhtml";
                         setMedida(balanceManager.getMedida(nodoDataHijo.getCodigo(), periodo, nodoDataHijo.getTipo()));
                         setAtrComponenteMedida(balanceManager.getAtrComponenteMedida(nodoDataHijo.getCodigo(), periodo));
                         setAtrComponente(balanceManager.getAtrComponente(nodoDataHijo.getCodigo()));
                         setComponenteMedida(balanceManager.getComponenteMedida(nodoDataHijo.getCodigo(), periodo));
                         setComponente(balanceManager.getComponente(nodoDataHijo.getCodigo()));  
                         relacionGeo = balanceManager.getZonaMunicipio(nodoDataHijo.getCodigo(), periodo);
                         relacionElec = balanceManager.getSubestacionCircuito(nodoDataHijo.getCodigo(), periodo);
                         
                         nombreDelegacion = nodoDataHijo.getNombre();
                    }
                    if(tipoCompo == 8){
                         url = tipoArbol.equals("NIV200")?"SVistaGeograficaTrafo.xhtml":"SVistaElectricaTrafo.xhtml";
                         setMedida(balanceManager.getMedida(nodoDataHijo.getCodigo(), periodo, nodoDataHijo.getTipo()));
                         setAtrComponenteMedida(balanceManager.getAtrComponenteMedidaTrafo(nodoDataHijo.getNombre(), periodo));
                         setAtrComponente(balanceManager.getAtrComponente(nodoDataHijo.getCodigo()));
                         setComponenteMedida(balanceManager.getComponenteMedida(nodoDataHijo.getCodigo(), periodo));
                         setComponente(balanceManager.getComponente(nodoDataHijo.getCodigo()));  
                         relacionGeo = balanceManager.getZonaMunicipio(nodoDataHijo.getCodigo(), periodo);
                         relacionElec = balanceManager.getSubestacionCircuito(nodoDataHijo.getCodigo(), periodo);
                    }
                     if(tipoCompo == 9){
                         url = tipoArbol.equals("NIV200")?"SVistaGeograficaSumin.xhtml":"SVistaElectricaSumin.xhtml";
                         setListMedida(balanceManager.getListMedida(nodoDataHijo.getCodigo(), Integer.parseInt(periodo)) );
                         setAtrComponente(balanceManager.getAtrComponente(nodoDataHijo.getCodigo()));
                         setComponente(balanceManager.getComponente(nodoDataHijo.getCodigo()));
                      }


                }
            }
        }
        // Se agregan los objetos TreeNodo al arbol root
        agregarNodos(clave, root);
        } catch (Exception e) {            
            FacesContext contextm = FacesContext.getCurrentInstance();
            contextm.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Verifique que las interfaces hayan sido ejecutadas para Simulacion.",""));
        }
    }

    public void agregarNodos(String clave, TreeNode padre) {
        Nodo nodoData = (Nodo) data.get(clave);
        if (!nodoData.isSelected()) {
            List hijos = nodoData.getHijos();
            if (hijos != null) {
                for (int i = 0; i < hijos.size(); i++) {
                    Nodo nodoHijo = (Nodo) hijos.get(i);
                    TreeNode nodeView = new DefaultTreeNode(nodoHijo.getCodigo() + nodoHijo.getTipo(), padre);
                }
            }
            nodoData.setSelected(true);
            data.put(clave, nodoData);
        }
    }

    public void obtenerInfo(String clave) {
        Nodo nodoData = (Nodo) data.get(clave);
        if (!nodoData.isSelected()) {
            List hijos = arbolManager.cargaArbolHijos(nodoData.getCodigo(), nodoData.getTipo(), periodo, null);
            nodoData.setHijos(hijos);
            data.put(clave, nodoData);
            if (hijos != null) {
                for (int i = 0; i < hijos.size(); i++) {
                    Nodo nodoDataHijo = (Nodo) hijos.get(i);
                    data.put(nodoDataHijo.getCodigo() + nodoDataHijo.getTipo(), nodoDataHijo);
                }
            }

        }
       /* mapa = new MapBean();
        getMapa().setZoomTipoComponente(zoomTipoComponente);
        getMapa().setNodoList(nodoData);*/
        
    }
    
    public void buscarComponente() throws SQLException{
        try {            
           rutaComponente=tipoComponente.equals("Trafo")?
                          arbolManager.rutaTrafo(idClienteAbuscar, tipoArbol, periodo):
                          arbolManager.rutaSuministro(idClienteAbuscar, tipoArbol, periodo);
        } catch (Exception e) {
           limpiarBuscarComponente(); 
        }finally{
            idClienteAbuscar=null;
            tipoComponente=null;
        }
    }
    
    public void limpiarBuscarComponente(){
        rutaComponente=null; 
        idClienteAbuscar=null;
        tipoComponente=null;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        try{
            selectedNode = event.getTreeNode();
            idComponente = (String) selectedNode.getData();        
            obtenerInfo(idComponente);
            agregarNodos(idComponente, selectedNode);
            setInfoBalance();
            selectedNode.setExpanded(true);
        } catch (Exception ex) {
            url=null;
            ocultarTortas();
            mostrarChartLine=false;
        }
    }
    
    public void onNodeExpanded(NodeExpandEvent event) {//cuando expande en el arbol
        TreeNode nodoSeleccionado = event.getTreeNode();
        if (nodoSeleccionado.getParent() != null) { //si tiene padre para buscar hnos
            List<TreeNode> hermanos = nodoSeleccionado.getParent().getChildren();
            for (Iterator it = hermanos.iterator(); it.hasNext();) {
                TreeNode n = (TreeNode) it.next();
                if (!n.isSelected()) {
                    n.setExpanded(false);
                }
            }
        }
    }
    
    public void onNodeCollapse(NodeCollapseEvent event) {//cuando contrae en el arbol
        TreeNode nodoSeleccionado = event.getTreeNode();
        nodoSeleccionado.setExpanded(false);
    }
   
    public String padreTrafo(BigDecimal id, String tipo){
        String idComercial=" : ";
        ZonaGeografica z =balanceManager.getPadreByTipo(id.toString(), tipo);
        if (z!=null) {
           idComercial=(z.getIdComercial()==null)?" : ":z.getIdComercial(); 
        }        
        return idComercial;
    }
    
    public String comercial(String id, String tipo){
        String idComercial=" : ";
        ZonaGeografica z =balanceManager.getZonaGeografica(id.toString(), tipo);
        if (z!=null) {
           idComercial=(z.getIdComercial()==null)?" : ":z.getIdComercial(); 
        }        
        return idComercial;
    }
    
    public String areaMt(String id, String tipo){
        String idComercial=" : ";
        ZonaGeografica z =balanceManager.getAreaMt(id, tipo);
        if (z!=null) {
           idComercial=(z.getIdComercial()==null)?" : ":z.getIdComercial(); 
        }        
        return idComercial;
    }
    
    public String nombreCircuito(String id, String tipo){
        String nombreComercial=" : ";
        ZonaGeografica z =balanceManager.getZonaGeografica(id, tipo);
        if (z!=null) {
           nombreComercial=(z.getNombreComercial()==null)?" : ":z.getNombreComercial(); 
        }        
        return nombreComercial;
    }
    
    public String perdidaTrafo(BigDecimal id, String tipo){
        String perdida=" : ";
        SBalances b =balanceManager.getBalances(id.toString(), "8", periodo);
        if (b!=null) {
            if (tipo.equals("mes"))
               perdida=(b.getPorcPerdidaMes()==null)?" : ":String.valueOf(b.getPorcPerdidaMes());  
            else
               perdida=(b.getPorcPerdidaMovil()==null)?" : ":String.valueOf(b.getPorcPerdidaMovil());              
        }        
        return perdida;
    }
    
    public void ocultarTortas(){
        tortaTotal=new PieChartModel();
        tortaNoFuncionan=new PieChartModel();
        tortaFuncionan=new PieChartModel();
        mostrarTorta=false;
    }
    
    public void visualizarTortas() { 
       //Para la torta que muestra macros Funcionando y No_Funcionando 
        if (balance!=null) {
          Number fun = balance.getTotalMacrosFunc()==null?0: //error aqui Alex
                     balance.getTotalMacrosFunc().intValue();
            Number noFun = balance.getCantMacrosNoFunc().intValue(); 
            tortaTotal = new PieChartModel();
            tortaTotal.set("Funcionan",fun);
            tortaTotal.set("No funcionan", noFun);           

            //para torta que muestra solo macros Funcionado
            int bajo =dataRangosBalance.getBajo()==null?0:
                        Integer.parseInt(dataRangosBalance.getBajo());
            int medio =dataRangosBalance.getMedio()==null?0:
                        Integer.parseInt(dataRangosBalance.getMedio());
            int critico =dataRangosBalance.getCritico()==null?0:
                            Integer.parseInt(dataRangosBalance.getCritico());
            tortaFuncionan = new PieChartModel();   
            tortaFuncionan.set("Bajo", bajo);
            tortaFuncionan.set("Medio", medio);
            tortaFuncionan.set("Critico", critico);

            //para torta que muestra solo macros No Funcionado
            int negativo =dataRangosBalance.getNegativos()==null?0:
                        Integer.parseInt(dataRangosBalance.getNegativos());
            int inconsistentePositivo =dataRangosBalance.getInconsistentesPositivos()==null?0:
                        Integer.parseInt(dataRangosBalance.getInconsistentesPositivos());
            int inconsistenteNegativo =dataRangosBalance.getInconsistentesNegativos()==null?0:
                        Integer.parseInt(dataRangosBalance.getInconsistentesNegativos());
            int sinBalance =dataRangosBalance.getSinBalance()==null?0:
                            Integer.parseInt(dataRangosBalance.getSinBalance());
           
            tortaNoFuncionan = new PieChartModel();   
            tortaNoFuncionan.set("Negativo", negativo);
            tortaNoFuncionan.set("Inconsistente Positivo", inconsistentePositivo);
            tortaNoFuncionan.set("Inconsistente Negativo", inconsistenteNegativo);
            tortaNoFuncionan.set("Sin balance", sinBalance); 
            mostrarTorta=true;  
        }else{
            mostrarTorta=false;
        }
  
        
    }     
    
    public String [] listarPeriodos(String periodoInicial){
        String periodoObjetivo = periodoInicial;        
        int tam = periodoObjetivo.length();        
        String mes = periodoObjetivo.substring(tam-2);
        String ano = periodoObjetivo.substring(0, tam-2);
        int mesesAtras =balanceManager.getNumMesesAtras();
        String[] periodos = new String[mesesAtras];
        
        for (int i = 0; i <mesesAtras; i++) {            
            if (mes.equals("00")) {
                mes="12";
                ano=String.valueOf(Integer.parseInt(ano)-1);
            }
            periodos[i]=ano+mes;
            int m=Integer.parseInt(mes)-1;
            mes=(m>9)?String.valueOf(m):
                      "0"+String.valueOf(m);
        }
        
        return periodos;        
    }
    
    public void visualizarChartLine(String idComponente, String tipoComponente, String periodoIni) { 
        
        String[] periodos = listarPeriodos(periodoIni);        
        categoryModel = new CartesianChartModel();        
        ChartSeries mes = new ChartSeries();
         mes.setLabel("Mes");
        ChartSeries movil = new ChartSeries();
         movil.setLabel("Movil");
        
        for (int i = periodos.length; i > 0; i--) {
            Number porcMes = 0;
            Number porcMovil = 0;
            String per = periodos[i-1];

            SBalances b = balanceManager.getBalances(idComponente, tipoComponente, per);
            if (b!=null) {    
                porcMes = b.getPorcPerdidaMes()== null? 0:b.getPorcPerdidaMes();
                porcMovil =  b.getPorcPerdidaMovil()== null? 0: b.getPorcPerdidaMovil();
            }
            mes.set(per, porcMes);
            movil.set(per, porcMovil);             
        }  
        categoryModel.addSeries(mes);  
        categoryModel.addSeries(movil); 
        mostrarChartLine=true;
    }      

    public String estiloColor(String nodo) {
        return estilo + (String) ((Nodo) data.get(nodo)).getColor();
    }

    public String nombreNodo(String nodo) {
        return (String) ((Nodo) data.get(nodo)).toString();
    }

    /**
     * @return the infoBalance
     */
    public List<DataBalanceHijo> getInfoBalance() {
        return infoBalance;
    }

    /**
     * @param infoBalance the infoBalance to set
     */
    public void setInfoBalance() {
        Nodo nodoData = (Nodo) data.get(idComponente);
        infoBalance = balanceManager.cuadroMando(nodoData.getCodigo(), nodoData.getTipo(), periodo);
         balance = balanceManager.getBalances(nodoData.getCodigo(), nodoData.getTipo(), periodo);
        int tipoCompo = Integer.parseInt(nodoData.getTipo());
             url= "";
        if (tipoCompo == 0 || tipoCompo == 1 || tipoCompo == 4 || tipoCompo == 5 ){
            dataRangosBalance = balanceManager.getRangosZonas(nodoData.getCodigo(), nodoData.getTipo(), tipoArbol, periodo);
            url = tipoArbol.equals("NIV200")?"SVistaGeograficaEmp.xhtml":"SVistaElectricaEmp.xhtml";
            trafosBajo=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "BAJO");
            trafosMedio=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "MEDIO");
            trafosCritico=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "CRITICO");
            trafosNegativos=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "NEGATIVO");
            trafosInconsistentes=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "INCONSISTENTE");
            trafosSinBalances=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "SIN BALANCE");
            
            nombreZona = nodoData.getNombre();
            
            visualizarChartLine(nodoData.getCodigo(), nodoData.getTipo(), periodo);
            visualizarTortas();           
        }
        if(tipoCompo == 6 || tipoCompo == 3) {            
            dataRangosBalance = balanceManager.getRangosZonas(nodoData.getCodigo(), nodoData.getTipo(), tipoArbol, periodo);
              idBarCir=tipoCompo == 3  || tipoCompo == 6?nodoData.getCodigo():"no";
              url = tipoArbol.equals("NIV200")?"SVistaGeograficaMunic.xhtml":"SVistaElectricaMunic.xhtml";
            trafosBajo=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "BAJO");
            trafosMedio=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "MEDIO");
            trafosCritico=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "CRITICO");
            trafosNegativos=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "NEGATIVO");
            trafosInconsistentes=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "INCONSISTENTE");
            trafosSinBalances=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "SIN BALANCE");
            
            matriculaCircuito = nodoData.getNombre();
            nombreCircuito = nombreCircuito(nodoData.getCodigo(), nodoData.getTipo());
            idComercialCircuito = comercial(nodoData.getCodigo(), nodoData.getTipo());
            
            visualizarChartLine(nodoData.getCodigo(), nodoData.getTipo(), periodo);
            visualizarTortas(); 
        }
        if(tipoCompo == 2 || tipoCompo == 13 ) {  
            dataRangosBalance = balanceManager.getRangosZonas(nodoData.getCodigo(), nodoData.getTipo(), tipoArbol, periodo);
            idBarCir=tipoCompo == 2  || tipoCompo == 13?nodoData.getCodigo():"no";
            url = tipoArbol.equals("NIV200")?"VistaGeograficaPobla.xhtml":"VistaElectricaArea.xhtml";
            trafosBajo=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "BAJO");
            trafosMedio=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "MEDIO");
            trafosCritico=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "CRITICO");
            trafosNegativos=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "NEGATIVO");
            trafosInconsistentes=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "INCONSISTENTE");
            trafosSinBalances=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "SIN BALANCE");
            
            nombreAreaEnergetica = nodoData.getNombre();
            idComercialAreaMt = areaMt(nodoData.getCodigo(), nodoData.getTipo());
            
            visualizarChartLine(nodoData.getCodigo(), nodoData.getTipo(), periodo);
            visualizarTortas();             
        }
        if(tipoCompo == 7 || tipoCompo == 11 ||  tipoCompo == 12) {
           url = tipoArbol.equals("NIV200")?"SVistaGeograficaBarrio.xhtml":"SVistaElectricaDelega.xhtml";
            dataRangosBalance = balanceManager.getRangosZonas(nodoData.getCodigo(), nodoData.getTipo(), tipoArbol, periodo);
            idBarCir=tipoCompo == 11 ||  tipoCompo == 12 || tipoCompo == 7?nodoData.getCodigo():"no";
            trafosBajo=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "BAJO");
            trafosMedio=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "MEDIO");
            trafosCritico=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "CRITICO");
            trafosNegativos=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "NEGATIVO");
            trafosInconsistentes=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "INCONSISTENTE");
            trafosSinBalances=balanceManager.getTrafosByRango(nodoData.getCodigo(), nodoData.getTipo(), periodo, "SIN BALANCE"); 
            
            nombreDelegacion = nodoData.getNombre();
                        
            visualizarChartLine(nodoData.getCodigo(), nodoData.getTipo(), periodo);
            visualizarTortas();            
        }
        if(tipoCompo == 8){
            dataRangosBalance = balanceManager.getRangosZonas(nodoData.getCodigo(), nodoData.getTipo(), tipoArbol, periodo);
             url = tipoArbol.equals("NIV200")?"SVistaGeograficaTrafo.xhtml":"SVistaElectricaTrafo.xhtml";
             setMedida(balanceManager.getMedida(nodoData.getCodigo(), periodo, nodoData.getTipo()));
             setAtrComponenteMedida(balanceManager.getAtrComponenteMedidaTrafo(nodoData.getNombre(), periodo));
             setAtrComponente(balanceManager.getAtrComponente(nodoData.getCodigo()));
             setComponenteMedida(balanceManager.getComponenteMedida(nodoData.getCodigo(), periodo));
             setComponente(balanceManager.getComponente(nodoData.getCodigo()));
             relacionGeo = balanceManager.getZonaMunicipio(nodoData.getCodigo(), periodo);
             relacionElec = balanceManager.getSubestacionCircuito(nodoData.getCodigo(), periodo); 
             listCantXTipoUso = balanceManager.getCantSumXTipoUso(nodoData.getCodigo(), periodo);
             cantSumNoMedidos = balanceManager.getCantSumNoMedidos(nodoData.getCodigo(), periodo);
             
             setCodigoComponenteSelected(nodoData.getCodigo());
             if(atrComponenteMedida.getConstante()!=null) setFactorMultiplicador(atrComponenteMedida.getConstante());
             if(medida.getConsumoFacturado()!=null)setConsumoFacturado(medida.getConsumoFacturado());
             if(atrComponente.getCargaInstalada()!=null)setCargaInstalada(atrComponente.getCargaInstalada());
             if(atrComponente.getPotencia()!=null)setPotencia(atrComponente.getPotencia());
             if(atrComponente.getFases()!=null) setFases(atrComponente.getFases());
             if(atrComponente.getTbltipo().getTipo()!=null)setTipoConexion(atrComponente.getTbltipo().getTipo());
             if(atrComponente.getTbltipo2().getTipo()!=null)setTipoUso(atrComponente.getTbltipo2().getTipo());
             if(componente.getZonaGeografica().getIdZona()!=null)setCodigoZona(componente.getZonaGeografica().getIdZona());             
             visualizarChartLine(nodoData.getCodigo(), nodoData.getTipo(), periodo);
             ocultarTortas();
        }
        if(tipoCompo == 9){
             url = tipoArbol.equals("NIV200")?"SVistaGeograficaSumin.xhtml":"SVistaElectricaSumin.xhtml";

             setListMedida(balanceManager.getListMedida(nodoData.getCodigo(), Integer.parseInt(periodo)) );
             setAtrComponente(balanceManager.getAtrComponente(nodoData.getCodigo()));
             setComponente(balanceManager.getComponente(nodoData.getCodigo()));
            
             setCodigoComponenteSelected(nodoData.getCodigo());             
             SComponente trafoActual=balanceManager.getTrafoBySuministro(nodoData.getCodigo());
             setCodigoTrafo(trafoActual.getIdComponente());
             setCodigoTrafoActual(trafoActual.getIdComponente());
             setMedida(balanceManager.getMedidaActual(nodoData.getCodigo()));
             setConsumoFacturado(medida.getConsumoFacturado());
             setDiasFacturados(medida.getDiasFacturados());
             
             ocultarTortas();
             mostrarChartLine=false;
        }
     

    }

    /**
     * @return the tipoArbol
     */
    public String getTipoArbol() {
        return tipoArbol;
    }

    /**
     * @param tipoArbol the tipoArbol to set
     */
    public void setTipoArbol(String tipoArbol) {
        this.tipoArbol = tipoArbol;
        init();
    }
    
    public void setZoomTipoComponente(){
        List tipos = arbolManager.getZoomMapa();
        zoomTipoComponente = new HashMap();
        for (int i = 0; i < tipos.size(); i++) {
            TipoComponente tipo = (TipoComponente) tipos.get(i);
            zoomTipoComponente.put(String.valueOf(tipo.getIdTipoComponente()), tipo.getZoom());
        }
    }

    public String actualizarPeriodo(){
        this.periodo = periodoSelected.getId();
        System.out.println(periodo);
        return null;
    }    

    /**
     * @return the balance
     */
    public SBalances getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(SBalances balance) {
        this.balance = balance;
    }

    public DataRangosBalance getDataRangosBalance() {
        return dataRangosBalance;
    }

    /**
     * @param dataRangosBalance the dataRangosBalance to set
     */
    public void setDataRangosBalance(DataRangosBalance dataRangosBalance) {
        this.dataRangosBalance = dataRangosBalance;
    }

    public Boolean getMostrarTablaTrafo() {
        return mostrarTablaTrafo;
    }

    public void setMostrarTablaTrafo(Boolean estado) {
        this.mostrarTablaTrafo = estado;
    }

    public Boolean getMostrarTablaZona() {
        return mostrarTablaZona;
    }

    public void setMostrarTablaZona(Boolean estado) {
        this.mostrarTablaZona = estado;
    }

    /**
     * @return the mapa
     */
    public MapBean getMapa() {
        return mapa;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapa(MapBean mapa) {
        this.mapa = mapa;
    }

    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }    
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void displaySelectedSingle(ActionEvent event) {
        if (selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());

            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    

    /**
     * @return the listPeriodos
     */
    public List<ComboLista> getListPeriodos() {
        return listPeriodos;
    }

    /**
     * @param listPeriodos the listPeriodos to set
     */
    public void setListPeriodos(List<ComboLista> listPeriodos) {
        this.listPeriodos = listPeriodos;
    }

    /**
     * @return the periodoSelected
     */
    public ComboLista getPeriodoSelected() {
        return periodoSelected;
    }

    /**
     * @param periodoSelected the periodoSelected to set
     */
    public void setPeriodoSelected(ComboLista periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    /**
     * @return the medida
     */
    public SMedida getMedida() {
        return medida;
    }

    /**
     * @param medida the medida to set
     */
    public void setMedida(SMedida medida) {
        this.medida = medida;
    }

    /**
     * @return the atrComponenteMedida
     */
    public SAtrComponenteMedida getAtrComponenteMedida() {
        return atrComponenteMedida;
    }

    /**
     * @param atrComponenteMedida the atrComponenteMedida to set
     */
    public void setAtrComponenteMedida(SAtrComponenteMedida atrComponenteMedida) {
        this.atrComponenteMedida = atrComponenteMedida;
    }

    /**
     * @return the atrComponente
     */
    public SAtrComponente getAtrComponente() {
        return atrComponente;
    }

    /**
     * @param atrComponente the atrComponente to set
     */
    public void setAtrComponente(SAtrComponente atrComponente) {
        this.atrComponente = atrComponente;
    }

    /**
     * @return the componenteMedida
     */
    public SComponenteMedida getComponenteMedida() {
        return componenteMedida;
    }

    /**
     * @param componenteMedida the componenteMedida to set
     */
    public void setComponenteMedida(SComponenteMedida componenteMedida) {
        this.componenteMedida = componenteMedida;
    }

    /**
     * @return the componente
     */
    public SComponente getComponente() {
        return componente;
    }

    /**
     * @param componente the componente to set
     */
    public void setComponente(SComponente componente) {
        this.componente = componente;
    }

    /**
     * @return the relacionGeo
     */
    public PadreHijo getRelacionGeo() {
        return relacionGeo;
    }

    /**
     * @param relacionGeo the relacionGeo to set
     */
    public void setRelacionGeo(PadreHijo relacionGeo) {
        this.relacionGeo = relacionGeo;
    }

    /**
     * @return the relacionElec
     */
    public PadreHijo getRelacionElec() {
        return relacionElec;
    }

    /**
     * @param relacionElec the relacionElec to set
     */
    public void setRelacionElec(PadreHijo relacionElec) {
        this.relacionElec = relacionElec;
    }
    
    /**
     * @return the relacionElec
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param relacionElec the relacionElec to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the listCantXTipoUso
     */
    public List<DataValue> getListCantXTipoUso() {
        return listCantXTipoUso;
    }

    /**
     * @param listCantXTipoUso the listCantXTipoUso to set
     */
    public void setListCantXTipoUso(List<DataValue> listCantXTipoUso) {
        this.listCantXTipoUso = listCantXTipoUso;
    }

    /**
     * @return the cantSumNoMedidos
     */
    public String getCantSumNoMedidos() {
        return cantSumNoMedidos;
    }

    /**
     * @param cantSumNoMedidos the cantSumNoMedidos to set
     */
    public void setCantSumNoMedidos(String cantSumNoMedidos) {
        this.cantSumNoMedidos = cantSumNoMedidos;
    }

    /**
     * @return the listMedida
     */
    public List<SMedida> getListMedida() {
        return listMedida;
    }

    /**
     * @param listMedida the listMedida to set
     */
    public void setListMedida(List<SMedida> listMedida) {
        this.listMedida = listMedida;
    }    
    
    public void movSuministro(String usuario, String programa){        
        if(!codigoTrafoActual.equals(codigoTrafo)){
            SComponente Trafo=balanceManager.getComponente(codigoTrafo.toString());
            String idTrafoAnterior=codigoTrafoActual.toString();
            balanceManager.movSuministro(usuario, programa, componente, Trafo, idTrafoAnterior);            
        }        
    }
    
    public void movTrafo(String tipo, String usuario, String programa){        
        if(!componente.getZonaGeografica().getIdZona().equals(codigoZona)){
            ZonaGeografica zona=balanceManager.getZonaGeografica(codigoZona.toString());
           // String idZonaAnterior=componente.getZonaGeografica().getIdZona().toString();
            ZonaGeografica idZonaAnt=balanceManager.getZonaByTrafo(componente.getIdComponente().toString(), tipo);
            balanceManager.movTrafo(usuario, programa, componente, zona, idZonaAnt.getIdZona().toString());            
        }        
    }
    
    public void editTrafo(String usuario, String programa) {
        boolean editAtrComponente=false;
        
        if (atrComponenteMedida!=null) {
            if(factorMultiplicador != atrComponenteMedida.getConstante()){
            atrComponenteMedida.setConstante(factorMultiplicador);     
            balanceManager.editAtrComponenteMedida(atrComponenteMedida);
          }
        } 
        
        if(cargaInstalada != atrComponente.getCargaInstalada()){
           atrComponente.setCargaInstalada(cargaInstalada);   
           editAtrComponente=true;
           //balanceManager.editAtrComponente(atrComponente);
        }  
        
        if(!medida.getConsumoFacturado().equals(consumoFacturado)){ //comparacion entre biddecimal
           medida.setConsumoFacturado(consumoFacturado);
           balanceManager.editMedida(medida);
        }
        
        BigDecimal potenciaActual=atrComponente.getPotencia()!=null?
                                  atrComponente.getPotencia():new BigDecimal(0);
        if(!potenciaActual.equals(potencia)){ 
           atrComponente.setPotencia(potencia);
           editAtrComponente=true;
           //balanceManager.editAtrComponente(atrComponente);
        }      
       
        short fasesActual=atrComponente.getFases()!=null?
                                  atrComponente.getFases():0;
        if(fases != fasesActual){ 
            atrComponente.setFases(fases);
            editAtrComponente=true;
            //balanceManager.editAtrComponente(atrComponente);
        }
        
        if(!atrComponente.getTbltipo().getTipo().equals(tipoConexion)){
            Tbltipo tc=balanceManager.getTipo(tipoConexion);
            atrComponente.setTbltipo(tc);
            editAtrComponente=true;
        }
        
        if(!atrComponente.getTbltipo2().getTipo().equals(tipoUso)){
            Tbltipo tu=balanceManager.getTipo(tipoUso);
            atrComponente.setTbltipo2(tu);
            editAtrComponente=true;
        }
        
        if (editAtrComponente==true){
            atrComponente.setUsuario(usuario);
            atrComponente.setPrograma(programa);
            balanceManager.editAtrComponente(atrComponente);
        }
        
        //url = tipoArbol.equals("NIV200")?"SVistaGeograficaTrafo.xhtml":"SVistaElectricaTrafo.xhtml";
        
    }
    
    public void editSuministro(String usuario, String programa) {
        boolean editMedida=false;        
        
        if(!medida.getConsumoFacturado().equals(consumoFacturado)){ //comparacion entre biddecimal
           medida.setConsumoFacturado(consumoFacturado);
           editMedida=true;           
        }
        
        if(diasFacturados != medida.getDiasFacturados()){ 
            medida.setDiasFacturados(diasFacturados);
            editMedida=true;
        }
        
        if (editMedida==true){
            medida.setUsuario(usuario);
            medida.setPrograma(programa);
            balanceManager.editMedida(medida); 
        }
                   
    }

    public String getCodigoComponenteSelected() {
        return codigoComponenteSelected;
    }

    public void setCodigoComponenteSelected(String codigoComponenteSelected) {
        this.codigoComponenteSelected = codigoComponenteSelected;
    }

    public long getFactorMultiplicador() {
        return factorMultiplicador;
    }

    public void setFactorMultiplicador(long factorMultiplicador) {
        this.factorMultiplicador = factorMultiplicador;
    }

    public BigDecimal getConsumoFacturado() {
        return consumoFacturado;
    }

    public void setConsumoFacturado(BigDecimal consumoFacturado) {
        this.consumoFacturado = consumoFacturado;
    }

    public BigDecimal getCargaInstalada() {
        return cargaInstalada;
    }

    public void setCargaInstalada(BigDecimal cargaInstalada) {
        this.cargaInstalada = cargaInstalada;
    }

    public BigDecimal getPotencia() {
        return potencia;
    }

    public void setPotencia(BigDecimal potencia) {
        this.potencia = potencia;
    }

    public short getFases() {
        return fases;
    }

    public void setFases(short fases) {
        this.fases = fases;
    }

    public String getTipoConexion() {
        return tipoConexion;
    }

    public void setTipoConexion(String tipoConexion) {
        this.tipoConexion = tipoConexion;
    }

    public List<Tbltipo> getTiposConexiones() {
        return tiposConexiones;
    }

    public void setTiposConexiones(List<Tbltipo> tiposConexiones) {
        this.tiposConexiones = tiposConexiones;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public List<Tbltipo> getTiposUsos() {
        return tiposUsos;
    }

    public void setTiposUsos(List<Tbltipo> tiposUsos) {
        this.tiposUsos = tiposUsos;
    }

    public BigDecimal getCodigoZona() {
        return codigoZona;
    }

    public void setCodigoZona(BigDecimal codigoZona) {
        this.codigoZona = codigoZona;
    }

    public List<ZonaGeografica> getZonas() {
        return zonas;
    }

    public void setZonas(List<ZonaGeografica> zonas) {
        this.zonas = zonas;
    }

    public BigDecimal getCodigoTrafo() {
        return codigoTrafo;
    }

    public void setCodigoTrafo(BigDecimal codigoTrafo) {
        this.codigoTrafo = codigoTrafo;
    }

    public List<SComponente> getTrafos() {
        return trafos;
    }

    public void setTrafos(List<SComponente> trafos) {
        this.trafos = trafos;
    }

    public BigDecimal getCodigoTrafoActual() {
        return codigoTrafoActual;
    }

    public void setCodigoTrafoActual(BigDecimal codigoTrafoActual) {
        this.codigoTrafoActual = codigoTrafoActual;
    }

    public short getDiasFacturados() {
        return diasFacturados;
    }

    public void setDiasFacturados(short diasFacturados) {
        this.diasFacturados = diasFacturados;
    }

    public String getRutaComponente() {
        return rutaComponente;
    }
    
    public String getTipoComponente() {
        return tipoComponente;
    }
    
    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getIdClienteAbuscar() {
        return idClienteAbuscar;
    }

    public void setIdClienteAbuscar(String idClienteAbuscar) {
        this.idClienteAbuscar = idClienteAbuscar;
    }

    public String getIdBarCir() {
        return idBarCir;
    }

    public void setIdBarCir(String idBarCir) {
        this.idBarCir = idBarCir;
    }
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(String idComponente) {
        this.idComponente = idComponente;
    }

    public String getGrafico() {
        return grafico;
    }

    public void setGrafico(String grafico) {
        this.grafico = grafico;
    }

    public String getGraficoVE() {
        return graficoVE;
    }

    public void setGraficoVE(String graficoVE) {
        this.graficoVE = graficoVE;
    }

    public boolean isMostrarChartLine() {
        return mostrarChartLine;
    }

    public void setMostrarChartLine(boolean mostrarChartLine) {
        this.mostrarChartLine = mostrarChartLine;
    }

    public boolean isMostrarTorta() {
        return mostrarTorta;
    }

    public void setMostrarTorta(boolean mostrarTorta) {
        this.mostrarTorta = mostrarTorta;
    }

    public PieChartModel getTortaFuncionan() {
        return tortaFuncionan;
    }

    public void setTortaFuncionan(PieChartModel tortaFuncionan) {
        this.tortaFuncionan = tortaFuncionan;
    }

    public PieChartModel getTortaNoFuncionan() {
        return tortaNoFuncionan;
    }

    public void setTortaNoFuncionan(PieChartModel tortaNoFuncionan) {
        this.tortaNoFuncionan = tortaNoFuncionan;
    }

    public PieChartModel getTortaTotal() {
        return tortaTotal;
    }

    public void setTortaTotal(PieChartModel tortaTotal) {
        this.tortaTotal = tortaTotal;
    }

    public List<SComponente> getTrafosBajo() {
        return trafosBajo;
    }

    public void setTrafosBajo(List<SComponente> trafosBajo) {
        this.trafosBajo = trafosBajo;
    }

    public List<SComponente> getTrafosCritico() {
        return trafosCritico;
    }

    public void setTrafosCritico(List<SComponente> trafosCritico) {
        this.trafosCritico = trafosCritico;
    }

    public List<SComponente> getTrafosInconsistentes() {
        return trafosInconsistentes;
    }

    public void setTrafosInconsistentes(List<SComponente> trafosInconsistentes) {
        this.trafosInconsistentes = trafosInconsistentes;
    }

    public List<SComponente> getTrafosMedio() {
        return trafosMedio;
    }

    public void setTrafosMedio(List<SComponente> trafosMedio) {
        this.trafosMedio = trafosMedio;
    }

    public List<SComponente> getTrafosNegativos() {
        return trafosNegativos;
    }

    public void setTrafosNegativos(List<SComponente> trafosNegativos) {
        this.trafosNegativos = trafosNegativos;
    }

    public List<SComponente> getTrafosSinBalances() {
        return trafosSinBalances;
    }

    public void setTrafosSinBalances(List<SComponente> trafosSinBalances) {
        this.trafosSinBalances = trafosSinBalances;
    }

    public CartesianChartModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CartesianChartModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    /**
     * @return the zonaGeografica
     */
    public ZonaGeografica getZonaGeografica() {
        return zonaGeografica;
    }

    /**
     * @param zonaGeografica the zonaGeografica to set
     */
    public void setZonaGeografica(ZonaGeografica zonaGeografica) {
        this.zonaGeografica = zonaGeografica;
    }

    /**
     * @return the nombreZona
     */
    public String getNombreZona() {
        return nombreZona;
    }

    /**
     * @param nombreZona the nombreZona to set
     */
    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    /**
     * @return the nombreDelegacion
     */
    public String getNombreDelegacion() {
        return nombreDelegacion;
    }

    /**
     * @param nombreDelegacion the nombreDelegacion to set
     */
    public void setNombreDelegacion(String nombreDelegacion) {
        this.nombreDelegacion = nombreDelegacion;
    }

    /**
     * @return the nombreAreaEnergetica
     */
    public String getNombreAreaEnergetica() {
        return nombreAreaEnergetica;
    }

    /**
     * @param nombreAreaEnergetica the nombreAreaEnergetica to set
     */
    public void setNombreAreaEnergetica(String nombreAreaEnergetica) {
        this.nombreAreaEnergetica = nombreAreaEnergetica;
    }

    /**
     * @return the nombreCircuito
     */
    public String getNombreCircuito() {
        return nombreCircuito;
    }

    /**
     * @param nombreCircuito the nombreCircuito to set
     */
    public void setNombreCircuito(String nombreCircuito) {
        this.nombreCircuito = nombreCircuito;
    }

    /**
     * @return the matriculaCircuito
     */
    public String getMatriculaCircuito() {
        return matriculaCircuito;
    }

    /**
     * @param matriculaCircuito the matriculaCircuito to set
     */
    public void setMatriculaCircuito(String matriculaCircuito) {
        this.matriculaCircuito = matriculaCircuito;
    }

    /**
     * @return the idComercialAreaMt
     */
    public String getIdComercialAreaMt() {
        return idComercialAreaMt;
    }

    /**
     * @param idComercialAreaMt the idComercialAreaMt to set
     */
    public void setIdComercialAreaMt(String idComercialAreaMt) {
        this.idComercialAreaMt = idComercialAreaMt;
    }

    /**
     * @return the idComercialCircuito
     */
    public String getIdComercialCircuito() {
        return idComercialCircuito;
    }

    /**
     * @param idComercialCircuito the idComercialCircuito to set
     */
    public void setIdComercialCircuito(String idComercialCircuito) {
        this.idComercialCircuito = idComercialCircuito;
    }
        
}
