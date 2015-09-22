/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view; 

//import com.ag.model.*;
import com.ag.model.Campania;
import com.ag.model.Componente;
import com.ag.model.Criterio;
import com.ag.model.Filtros;
import com.ag.model.Tbltipo;
import com.ag.model.Variables;
import com.ag.model.view.Candidato;
import com.ag.model.view.CandidatoSumin;
import com.ag.model.view.Fecha;
import com.ag.model.view.ResultCampania;
import com.ag.service.CriterioManager;
import com.ag.service.SpringContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
//import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author 85154220
 */
@ManagedBean
@SessionScoped
public class CriteriosView implements Serializable {

    private SpringContext context;
    private CriterioManager criterioManager;
    private String selectedCriterio;
    private List<Criterio> criteriosList;
    private List<Filtros> filtrosList, filtrosListSumin;
    private List<Filtros> filtrosByCriteriosList;
    private Filtros filtroSelected, filtroSelectedSumin;
    private Criterio criterioSelected;
    private BigDecimal idFiltro, idFiltroSumin;
    private List<Variables> variableList, variableListSumin;
    private List<Tbltipo> operadorList;
    private List<Tbltipo> operadorLogList;
    private String codVariable, codOperador, codOperadorLog, valor, criterio;
    private String codVariableSumin, codOperadorSumin, codOperadorLogSumin, valorSumin, criterioSumin;
    @ManagedProperty("#{login}")
    private Login login;
    private List<Candidato> candidatos;
    private List<CandidatoSumin> candidatosSumin;
    private List<Candidato> candidatosIdMacro;
    private List<CandidatoSumin> candidatosIdMacroSumin;
    private List<Candidato> candidatosIdMacroFiltro;
    private List<CandidatoSumin> candidatosIdMacroFiltroSumin;
    private boolean checkAll = true, checkAllCamp = true,checkFiltros;
    private boolean checkAllSumin = true, checkAllCampSumin = true,checkFiltrosSumin = true;   
    private String codVariableEdit, codOperadorEdit, codOperadorLogEdit, valorEdit;
    
    private Candidato[] selectedCandidatos; 
    private String descripcion, descripcionSumin;
    private String descripcionUniverso;
    private List<Tbltipo> actividades;
    private String actividadSelected;
    
    private StreamedContent file;
    
    private List<ResultCampania> resultCampanias;
    private String idCampaniaAimportar;
    
    private boolean mostrarCrearCampania,mostrarCrearCampaniaSumin; 
    
    private String idCriterioAcargar;
    
    boolean filtSumi, filtSumiNO;
    
    public CriteriosView() {
    }

    @PostConstruct
    public void init() {
        context = SpringContext.getInstance();
        criterioManager = (CriterioManager) context.getBean("CriterioManager");
        setCriteriosList(criterioManager.getCriterios());
        setOperadorList(criterioManager.getOperador());
        setOperadorLogList(criterioManager.getOperadorLogico());
        setVariableList(criterioManager.getVariables("ESB000"));
        setVariableListSumin(criterioManager.getVariables("ESB100"));
        setActividades(criterioManager.getActividades());
        setResultCampanias(criterioManager.getResultCampanias());

        filtrosList = new ArrayList<Filtros>();
        filtrosListSumin = new ArrayList<Filtros>();
        filtrosByCriteriosList = new ArrayList<Filtros>();
    }
    
    public void filtSumiChange(){
        if (filtSumi) {
            filtSumi=false;
            filtSumiNO=true;            
        } else {
            filtSumi=true;
            filtSumiNO=false;
            mostrarCrearCampaniaSumin=false;
        }        
    }
    
    public void importarCampania(){
        try {
          if (idCampaniaAimportar!=null && !idCampaniaAimportar.equals("")) {
            String error = criterioManager.importarCampania(idCampaniaAimportar);        
            if (error==null)
                FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha importado con exito.", ""));
            else
                FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La importacion no se ejecuto con exito.", "Detalle:"+error));                
          } else 
              FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Ingrese el Id Campaña.", ""));
         
            
      } catch (Exception e) {
          FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                   "Upss ha ocurrido un error en el sistema.", "Detalle:"+e.getMessage())); 
      }finally{
          idCampaniaAimportar=null;
      }
    }
    
    public void filtroEdit(){
        
        if (!codOperadorLogEdit.equals("no") && !codOperadorLogEdit.equals(filtroSelected.getTbltipo1().getNombre())) {
            filtroSelected.setTbltipo1(criterioManager.getTipo(codOperadorLogEdit));
           
        }
        
        if (!codOperadorEdit.equals("no") && !codOperadorEdit.equals(filtroSelected.getTbltipo3().getNombre())) {
            filtroSelected.setTbltipo3(criterioManager.getTipo(codOperadorEdit));
            
        }
        
        if (!codVariableEdit.equals("no") && !codVariableEdit.equals(filtroSelected.getVariables().getIdVariable().toString())) {
            filtroSelected.setVariables(criterioManager.getVariable(codVariableEdit));
            
        }
        
        /*if (!valorEdit.equals(filtroSelected.getValor1())) {
            filtroSelected.setValor1(valorEdit);
            actualizar=true;
        }*/
        
        
            criterioManager.editFiltro(filtroSelected);
            /*FacesContext.getCurrentInstance().addMessage
                   (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                   "Filtro editado con exito!", "")); */                
           
            //actualizar lista de filtros del criterio seleccionado
        
    }
    
    public String getFechaFormato(Date fecha, String formato){
        Fecha formatearFecha=new Fecha(); 
        return formatearFecha.getFechaFormato(fecha, formato);
    }
    
    public void llenarTablaFiltros(){
        String idCriterio = criterioSelected.getIdCriterio().toString();
        setFiltrosByCriteriosList(criterioManager.getFiltros(idCriterio));
    }
    
    public void cargarCriterio(){
        if (idCriterioAcargar!=null && !idCriterioAcargar.equals("")) {
            setFiltrosList(criterioManager.getFiltros(idCriterioAcargar));
            //idCriterioAcargar=null;
        }       
    }

    public void guardar() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Filtro editado exitosamente"));

    }

    /**
     * @return the selectedCriterio
     */
    public String getSelectedCriterio() {
        return selectedCriterio;
    }

    /**
     * @param selectedCriterio the selectedCriterio to set
     */
    public void setSelectedCriterio(String selectedCriterio) {
        this.selectedCriterio = selectedCriterio;
    }

    /**
     * @return the criteriosList
     */
    public List<Criterio> getCriteriosList() {
        return criteriosList;
    }

    /**
     * @param criteriosList the criteriosList to set
     */
    public void setCriteriosList(List<Criterio> criteriosList) {
        this.criteriosList = criteriosList;
    }

    /**
     * @return the filtrosList
     */
    public List<Filtros> getFiltrosList() {
        return filtrosList;
    }

    /**
     * @param filtrosList the filtrosList to set
     */
    public void setFiltrosList(List<Filtros> filtrosList) {

        this.filtrosList = filtrosList == null ? new ArrayList<Filtros>() : filtrosList;
    }

    public void onChangeCriterio(ActionEvent event) {

        setFiltrosList(criterioManager.getFiltros(String.valueOf(selectedCriterio)));
    }

    /**
     * @return the idFiltro
     */
    public BigDecimal getIdFiltro() {
        return idFiltro;
    }

    /**
     * @param idFiltro the idFiltro to set
     */
    public void setIdFiltro(BigDecimal idFiltro) {
        this.idFiltro = idFiltro;
    }

    public void eliminarFiltro(ActionEvent actionEvent) {
        for (int i = 0; i < filtrosList.size(); i++) {
            Filtros f = filtrosList.get(i);
            if (f.getIdFiltro().equals(idFiltro)) {
                filtrosList.remove(i);
                break;
            }

        }
    }
    
    public void eliminarFiltroSumin(ActionEvent actionEvent) {
        for (int i = 0; i < filtrosListSumin.size(); i++) {
            Filtros f = filtrosListSumin.get(i);
            if (f.getIdFiltro().equals(idFiltroSumin)) {
                filtrosListSumin.remove(i);
                break;
            }

        }
    }

    /**
     * @return the operadorList
     */
    public List<Tbltipo> getOperadorList() {
        return operadorList;
    }

    /**
     * @param operadorList the operadorList to set
     */
    public void setOperadorList(List<Tbltipo> operadorList) {
        this.operadorList = operadorList;
    }

    /**
     * @return the operadorLogList
     */
    public List<Tbltipo> getOperadorLogList() {
        return operadorLogList;
    }

    /**
     * @param operadorLogList the operadorLogList to set
     */
    public void setOperadorLogList(List<Tbltipo> operadorLogList) {
        this.operadorLogList = operadorLogList;
    }

    /**
     * @return the variableList
     */
    public List<Variables> getVariableList() {
        return variableList;
    }

    /**
     * @param variableList the variableList to set
     */
    public void setVariableList(List<Variables> variableList) {
        this.variableList = variableList;
    }

    /**
     * @return the codVariable
     */
    public String getCodVariable() {
        return codVariable;
    }

    /**
     * @param codVariable the codVariable to set
     */
    public void setCodVariable(String codVariable) {
        this.codVariable = codVariable;
    }

    /**
     * @return the codOperador
     */
    public String getCodOperador() {
        return codOperador;
    }

    /**
     * @param codOperador the codOperador to set
     */
    public void setCodOperador(String codOperador) {
        this.codOperador = codOperador;
    }

    /**
     * @return the codOperadorLog
     */
    public String getCodOperadorLog() {
        return codOperadorLog;
    }

    /**
     * @param codOperadorLog the codOperadorLog to set
     */
    public void setCodOperadorLog(String codOperadorLog) {
        this.codOperadorLog = codOperadorLog;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoVariable() {
        String tipoVar = "";




        return tipoVar;
    }

public void listaIDMacro(){

    candidatosIdMacro = new ArrayList<Candidato>();
    }

   
 public void addCriterio() {
        this.filtrosList.add(criterioManager.agregarFiltro(getLogin().getUsuario(), "Criterios.xhtml", codVariable, codOperador, valor, codOperadorLog));
    }
 
 public void addCriterioSumin(){
        this.filtrosListSumin.add(criterioManager.agregarFiltro(getLogin().getUsuario(), "Criterios.xhtml", codVariableSumin, codOperadorSumin, valorSumin, codOperadorLogSumin));
    }
 
 public void addFiltro() {
        this.filtrosByCriteriosList.add(criterioManager.agregarFiltro(getLogin().getUsuario(), "Criterios.xhtml", codVariable, codOperador, valor, codOperadorLog, criterioSelected));
        valor=null;
 }
 
 public void deleteFiltro() {
     if (filtroSelected!=null) {
         criterioManager.deleteFiltro(filtroSelected);
         filtrosByCriteriosList.remove(filtroSelected);
     }       
 }

    public void guardarCriterio() {
        boolean resultado;
        resultado = criterioManager.guardarCriterio(getLogin().getUsuario(), "Criterios.xhtml", criterio, filtrosList);

        if (resultado) {
            setCriteriosList(criterioManager.getCriterios());
            criterio=null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", "Se ha procesado la información correctamente"));           
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Con errores", "Error al almacenar en la BD"));
        }
    }

    /**
     * @return the login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * @return the criterio
     */
    public String getCriterio() {
        return criterio;
    }

    /**
     * @param criterio the criterio to set
     */
    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public void buscarCandidatos() throws SQLException {
        mostrarCrearCampania=false;
        candidatos = criterioManager.getCandidatos(filtrosList);
        if (!candidatos.isEmpty()){
            mostrarCrearCampania=true;
            filtSumi=true;
        }        
    }
    
    public void buscarCandidatosSumin() throws SQLException {
        mostrarCrearCampaniaSumin=false;
        candidatosSumin=new ArrayList<CandidatoSumin>();
        String idTrafos="";
        boolean primero=true;
        for (int i = 0; i < candidatos.size(); i++) {
            Candidato c = candidatos.get(i);
            if (c.isCheck()) {
                if (primero) {
                    idTrafos+="("+c.getIdTrafo();
                    primero=false;
                } else {
                    idTrafos+=","+c.getIdTrafo();
                }                
            }
        }
        idTrafos+=")";    
        if (!primero)candidatosSumin = criterioManager.getCandidatosSumin(idTrafos, filtrosListSumin);
        if (!candidatosSumin.isEmpty()) mostrarCrearCampaniaSumin=true;        
    }

    public void buscarIdMacro() throws SQLException {
        candidatosIdMacro = criterioManager.getIdMacro();
    }

    /**
     * @return the candidatos
     */
    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    /**
     * @param candidatos the candidatos to set
     */
    public void setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    /**
     * @return the checkAll
     */
    public boolean isCheckAll() {
        return checkAll;
    }

    /**
     * @param checkAll the checkAll to set
     */
    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }

    public void seleccionar() {
        if (candidatos != null) {
            for (int i = 0; i < candidatos.size(); i++) {
                Candidato c = candidatos.get(i);
                c.setCheck(checkAll);

            }
        }
    }
    
    public void seleccionarSumin() {
        if (candidatosSumin != null) {
            for (int i = 0; i < candidatosSumin.size(); i++) {
                CandidatoSumin c = candidatosSumin.get(i);
                c.setCheck(checkAll);

            }
        }
    }
    
    public void seleccionarFiltros() {
        if (filtrosList != null) {
            for (int i = 0; i < filtrosList.size(); i++) {
                Filtros f = filtrosList.get(i);
                f.setSelected(checkFiltros);

            }
        }
    }
    
    public void seleccionarFiltrosSumin() {
        if (filtrosListSumin != null) {
            for (int i = 0; i < filtrosListSumin.size(); i++) {
                Filtros f = filtrosListSumin.get(i);
                f.setSelected(checkFiltrosSumin);

            }
        }
    }
    
    public void eliminarFiltros() {
        List<Filtros> filtrosAeliminar=new ArrayList<Filtros>();
        if (filtrosList != null) {            
            for (int i = 0; i < filtrosList.size(); i++) {
                Filtros f = filtrosList.get(i);
                if(f.isSelected()==false){
                    filtrosAeliminar.add(f);
                }
            } 
            
            filtrosList.clear();
            filtrosList=filtrosAeliminar;
                     
        }        
    }
    
    public void eliminarFiltrosSumin() {
        List<Filtros> filtrosAeliminar=new ArrayList<Filtros>();
        if (filtrosListSumin != null) {            
            for (int i = 0; i < filtrosListSumin.size(); i++) {
                Filtros f = filtrosListSumin.get(i);
                if(f.isSelected()==false){
                    filtrosAeliminar.add(f);
                }
            } 
            
            filtrosListSumin.clear();
            filtrosListSumin=filtrosAeliminar;
                     
        }        
    }
    

    public void seleccionarCamp() {
        if (candidatosIdMacroFiltro != null) {
            for (int i = 0; i < candidatosIdMacroFiltro.size(); i++) {
                Candidato c = candidatosIdMacroFiltro.get(i);
                c.setCheck(checkAllCamp);
                candidatosIdMacroFiltro.set(i,c);

            }
        } else if (candidatosIdMacro != null) {
            for (int i = 0; i < candidatosIdMacro.size(); i++) {
                Candidato c = candidatosIdMacro.get(i);
                c.setCheck(checkAllCamp);
                candidatosIdMacro.set(i,c);

            }
        }
    }
    
    public void seleccionarCampSumin(){
        if (candidatosIdMacroFiltroSumin != null) {
            for (int i = 0; i < candidatosIdMacroFiltroSumin.size(); i++) {
                CandidatoSumin c = candidatosIdMacroFiltroSumin.get(i);
                c.setCheck(checkAllCampSumin);
                candidatosIdMacroFiltroSumin.set(i,c);

            }
        } else if (candidatosIdMacroSumin != null){
            for (int i = 0; i < candidatosIdMacroSumin.size(); i++) {
                CandidatoSumin c = candidatosIdMacroSumin.get(i);
                c.setCheck(checkAllCampSumin);
                candidatosIdMacroSumin.set(i,c);

            }
        }
    }
    
     
    public File exportarAruta(String usuario, String programa, List<Candidato> candidatos, String lista, String descripcion){        
        File prueba=null;
        FileWriter archivo = null;
        PrintWriter pw = null;
        boolean devolverFile=false;
      try {  
          prueba=new File(descripcion+".csv");
        //archivo = new FileWriter("E:\\arodriguezr\\Documents\\archivo\\"+descripcion+".csv");
          archivo = new FileWriter(prueba);
          pw = new PrintWriter(archivo);
        //llenar datos de la campaña        
        
        if (candidatos != null) {
            String idParametro=lista.equals("universo")?                       
                                "ACTIVIDAD_MACRO":                                            
                                actividadSelected;                      
                        
            Campania campania=criterioManager.saveCampania(usuario, programa, descripcion, idParametro);       
        
            for (int i = 0; i < candidatos.size(); i++) {
                Candidato c = candidatos.get(i);
                if (c.isCheck()) {
                    //insertar id del trafo en la tabla con el idCampaña
                    String idTrafo=lista.equals("universo")?
                                    c.getIdTrafo():   
                                    c.getNombre();  
                  
                    String idMacro = c.getIdMacro();
                    //String idCampania = campania.getIdCampania().toString();
                    String tipoComponente=lista.equals("filtro") && !actividadSelected.equals("7526")?"9":"8"; 
                    
                    if (lista.equals("filtro") && !actividadSelected.equals("7526")) { //si es en filtro y revision por usuario
                       List<Componente> suministros=criterioManager.suministrosAsociados(c.getIdTrafo());
                       for (Iterator it = suministros.iterator(); it.hasNext();) {
                             Componente s = (Componente) it.next();                             
                             String linea=criterioManager.saveComponenteCampania(s.getIdComponente().toString(), tipoComponente, idMacro, idParametro, campania);
                             pw.println(linea);
                       }
                       
                    } else {                      
                       String linea=criterioManager.saveComponenteCampania(idTrafo, tipoComponente, idMacro, idParametro, campania);
                       pw.println(linea);
                    }                    
                    
                     devolverFile=true;
                }                
            }
        }        
        
    } catch (Exception e) {          
    }finally{
        try {  
            actividadSelected=null;            
            if (null != archivo)
                archivo.close();
        } catch (Exception e2) {
                e2.printStackTrace();
        }
        
    }  
        if (devolverFile){
            //actualizamos la tabla de campañas
            setResultCampanias(criterioManager.getResultCampanias());
            return prueba;
        }             
        else 
            return null;
         
    }
    
    public File exportarArutaSumin(String usuario, String programa, List<CandidatoSumin> candidatos, String descripcion){        
        File prueba=null;
        FileWriter archivo = null;
        PrintWriter pw = null;
        boolean devolverFile=false;
      try {  
          prueba=new File(descripcion+".csv");
        //archivo = new FileWriter("E:\\arodriguezr\\Documents\\archivo\\"+descripcion+".csv");
          archivo = new FileWriter(prueba);
          pw = new PrintWriter(archivo);
        //llenar datos de la campaña        
        
        if (candidatos != null) {
            String idParametro="7502";  //revision usuario                    
                        
            Campania campania=criterioManager.saveCampania(usuario, programa, descripcion, idParametro);       
        
            for (int i = 0; i < candidatos.size(); i++) {
                CandidatoSumin c = candidatos.get(i);
                if (c.isCheck()) {
                    //insertar id del trafo en la tabla con el idCampaña
                    String idTrafo=c.getIdComponente(); 
                  
                    String idMacro = c.getIdCliente();
                    //String idCampania = campania.getIdCampania().toString();
                    String tipoComponente="9";
                    String linea=criterioManager.saveComponenteCampania(idTrafo, tipoComponente, idMacro, idParametro, campania);
                    pw.println(linea);                    
                     devolverFile=true;
                }                
            }
        }        
        
    } catch (Exception e) {          
    }finally{
        try {  
            actividadSelected=null;            
            if (null != archivo)
                archivo.close();
        } catch (Exception e2) {
                e2.printStackTrace();
        }
        
    }  
        if (devolverFile){
            //actualizamos la tabla de campañas
            setResultCampanias(criterioManager.getResultCampanias());
            return prueba;
        }             
        else 
            return null;
         
    }
    
    public StreamedContent descargarFileFiltro(String usuario, String programa) throws FileNotFoundException {  
        if (actividadSelected!=null && !actividadSelected.equals("") && descripcion!=null && !descripcion.equals("")) {
            File archivo = exportarAruta(usuario, programa,candidatos, "filtro", descripcion);
            if (archivo!=null) {
            InputStream stream = new FileInputStream(archivo);   
            descripcion=null;
            file = new DefaultStreamedContent(stream, "csv", archivo.getName());            
            return file; 
            }else{
                return null;
            }  
        } else {                        
            FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Ingrese descripcion y escoja la actividad.", ""));
            actividadSelected=null;
            descripcion=null;
            return null;
        }
       
    }
    
    public StreamedContent descargarFileFiltroSumin(String usuario, String programa) throws FileNotFoundException {  
        if (descripcionSumin!=null && !descripcionSumin.equals("")) {
            File archivo = exportarArutaSumin(usuario, programa,candidatosSumin, descripcionSumin);
            if (archivo!=null) {
            InputStream stream = new FileInputStream(archivo);   
            descripcionSumin=null;
            file = new DefaultStreamedContent(stream, "csv", archivo.getName());            
            return file; 
            }else{
                return null;
            }  
        } else {                        
            FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Ingrese descripcion y escoja la actividad.", ""));
            actividadSelected=null;
            descripcion=null;
            return null;
        }
       
    }
    
    public StreamedContent descargarFileUniverso(String usuario, String programa) throws FileNotFoundException {  
        if (descripcionUniverso!=null && !descripcionUniverso.equals("")) {
            File archivo = exportarAruta(usuario, programa,candidatosIdMacro, "universo", descripcionUniverso);
            
                if (archivo!=null) {
            InputStream stream = new FileInputStream(archivo);     
            descripcionUniverso=null;
            file = new DefaultStreamedContent(stream, "csv", archivo.getName());            
            return file; 
            }else{
                return null;
            }  
        } else {                        
            FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Ingrese la descripcion.", ""));
            descripcionUniverso=null;           
            return null;
        }
       
    }

    /**
     * @return the candidatosIdMacro
     */
    public List<Candidato> getCandidatosIdMacro() {
        return candidatosIdMacro;
    }

    /**
     * @param candidatosIdMacro the candidatosIdMacro to set
     */
    public void setCandidatosIdMacro(List<Candidato> candidatosIdMacro) {
        this.candidatosIdMacro = candidatosIdMacro;
    }

    /**
     * @return the candidatosIdMacroFiltro
     */
    public List<Candidato> getCandidatosIdMacroFiltro() {
        return candidatosIdMacroFiltro;
    }

    /**
     * @param candidatosIdMacroFiltro the candidatosIdMacroFiltro to set
     */
    public void setCandidatosIdMacroFiltro(List<Candidato> candidatosIdMacroFiltro) {
        this.candidatosIdMacroFiltro = candidatosIdMacroFiltro;
    }

    /**
     * @return the checkAllCamp
     */
    public boolean isCheckAllCamp() {
        return checkAllCamp;
    }

    /**
     * @param checkAllCamp the checkAllCamp to set
     */
    public void setCheckAllCamp(boolean checkAllCamp) {
        this.checkAllCamp = checkAllCamp;
    }

    /**
     * @return the checkFiltros
     */
    public boolean isCheckFiltros() {
        return checkFiltros;
    }

    /**
     * @param checkFiltros the checkFiltros to set
     */
    public void setCheckFiltros(boolean checkFiltros) {
        this.checkFiltros = checkFiltros;
    }

    public Filtros getFiltroSelected() {
        return filtroSelected;
    }

    public void setFiltroSelected(Filtros filtroSelected) {
        this.filtroSelected = filtroSelected;
    }

    public Criterio getCriterioSelected() {
        return criterioSelected;
    }

    public void setCriterioSelected(Criterio criterioSelected) {
        this.criterioSelected = criterioSelected;
    }

    public String getCodOperadorEdit() {
        return codOperadorEdit;
    }

    public void setCodOperadorEdit(String codOperadorEdit) {
        this.codOperadorEdit = codOperadorEdit;
    }

    public String getCodOperadorLogEdit() {
        return codOperadorLogEdit;
    }

    public void setCodOperadorLogEdit(String codOperadorLogEdit) {
        this.codOperadorLogEdit = codOperadorLogEdit;
    }

    public String getCodVariableEdit() {
        return codVariableEdit;
    }

    public void setCodVariableEdit(String codVariableEdit) {
        this.codVariableEdit = codVariableEdit;
    }

    public String getValorEdit() {
        return valorEdit;
    }

    public void setValorEdit(String valorEdit) {
        this.valorEdit = valorEdit;
    }

    public Candidato[] getSelectedCandidatos() {
        return selectedCandidatos;
    }

    public void setSelectedCandidatos(Candidato[] selectedCandidatos) {
        this.selectedCandidatos = selectedCandidatos;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDescripcionUniverso() {
        return descripcionUniverso;
    }

    public void setDescripcionUniverso(String descripcionUniverso) {
        this.descripcionUniverso = descripcionUniverso;
    }    

    public List<Filtros> getFiltrosByCriteriosList() {
        return filtrosByCriteriosList;
    }

    public void setFiltrosByCriteriosList(List<Filtros> filtrosByCriteriosList) {
        this.filtrosByCriteriosList = filtrosByCriteriosList;
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

    public List<ResultCampania> getResultCampanias() {
        return resultCampanias;
    }

    public void setResultCampanias(List<ResultCampania> resultCampanias) {
        this.resultCampanias = resultCampanias;
    }

    public String getIdCampaniaAimportar() {
        return idCampaniaAimportar;
    }

    public void setIdCampaniaAimportar(String idCampaniaAimportar) {
        this.idCampaniaAimportar = idCampaniaAimportar;
    }   

    public boolean isMostrarCrearCampania() {
        return mostrarCrearCampania;
    }

    public String getIdCriterioAcargar() {
        return idCriterioAcargar;
    }

    public void setIdCriterioAcargar(String idCriterioAcargar) {
        this.idCriterioAcargar = idCriterioAcargar;
    }

    public List<CandidatoSumin> getCandidatosIdMacroFiltroSumin() {
        return candidatosIdMacroFiltroSumin;
    }

    public void setCandidatosIdMacroFiltroSumin(List<CandidatoSumin> candidatosIdMacroFiltroSumin) {
        this.candidatosIdMacroFiltroSumin = candidatosIdMacroFiltroSumin;
    }

    public List<CandidatoSumin> getCandidatosIdMacroSumin() {
        return candidatosIdMacroSumin;
    }

    public void setCandidatosIdMacroSumin(List<CandidatoSumin> candidatosIdMacroSumin) {
        this.candidatosIdMacroSumin = candidatosIdMacroSumin;
    }

    public List<CandidatoSumin> getCandidatosSumin() {
        return candidatosSumin;
    }

    public void setCandidatosSumin(List<CandidatoSumin> candidatosSumin) {
        this.candidatosSumin = candidatosSumin;
    }

    public boolean isCheckAllCampSumin() {
        return checkAllCampSumin;
    }

    public void setCheckAllCampSumin(boolean checkAllCampSumin) {
        this.checkAllCampSumin = checkAllCampSumin;
    }

    public boolean isCheckAllSumin() {
        return checkAllSumin;
    }

    public void setCheckAllSumin(boolean checkAllSumin) {
        this.checkAllSumin = checkAllSumin;
    }

    public boolean isCheckFiltrosSumin() {
        return checkFiltrosSumin;
    }

    public void setCheckFiltrosSumin(boolean checkFiltrosSumin) {
        this.checkFiltrosSumin = checkFiltrosSumin;
    }

    public String getCodOperadorLogSumin() {
        return codOperadorLogSumin;
    }

    public void setCodOperadorLogSumin(String codOperadorLogSumin) {
        this.codOperadorLogSumin = codOperadorLogSumin;
    }

    public String getCodOperadorSumin() {
        return codOperadorSumin;
    }

    public void setCodOperadorSumin(String codOperadorSumin) {
        this.codOperadorSumin = codOperadorSumin;
    }

    public String getCodVariableSumin() {
        return codVariableSumin;
    }

    public void setCodVariableSumin(String codVariableSumin) {
        this.codVariableSumin = codVariableSumin;
    }

    public String getCriterioSumin() {
        return criterioSumin;
    }

    public void setCriterioSumin(String criterioSumin) {
        this.criterioSumin = criterioSumin;
    }

    public Filtros getFiltroSelectedSumin() {
        return filtroSelectedSumin;
    }

    public void setFiltroSelectedSumin(Filtros filtroSelectedSumin) {
        this.filtroSelectedSumin = filtroSelectedSumin;
    }

    public List<Filtros> getFiltrosListSumin() {
        return filtrosListSumin;
    }

    public void setFiltrosListSumin(List<Filtros> filtrosListSumin) {
        this.filtrosListSumin = filtrosListSumin;
    }

    public BigDecimal getIdFiltroSumin() {
        return idFiltroSumin;
    }

    public void setIdFiltroSumin(BigDecimal idFiltroSumin) {
        this.idFiltroSumin = idFiltroSumin;
    }

    public boolean isMostrarCrearCampaniaSumin() {
        return mostrarCrearCampaniaSumin;
    }

    public void setMostrarCrearCampaniaSumin(boolean mostrarCrearCampaniaSumin) {
        this.mostrarCrearCampaniaSumin = mostrarCrearCampaniaSumin;
    }

    public String getValorSumin() {
        return valorSumin;
    }

    public void setValorSumin(String valorSumin) {
        this.valorSumin = valorSumin;
    }
    
    public List<Variables> getVariableListSumin() {
        return variableListSumin;
    }

    public void setVariableListSumin(List<Variables> variableListSumin) {
        this.variableListSumin = variableListSumin;
    }

    public String getDescripcionSumin() {
        return descripcionSumin;
    }

    public void setDescripcionSumin(String descripcionSumin) {
        this.descripcionSumin = descripcionSumin;
    }

    public boolean isFiltSumi() {
        return filtSumi;
    }

    public void setFiltSumi(boolean filtSumi) {
        this.filtSumi = filtSumi;
    }

    public boolean isFiltSumiNO() {
        return filtSumiNO;
    }

    public void setFiltSumiNO(boolean filtSumiNO) {
        this.filtSumiNO = filtSumiNO;
    }
    
    
}
