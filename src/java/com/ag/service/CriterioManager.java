/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

//import com.ag.model.*;
import com.ag.model.Campania;
import com.ag.model.Componente;
import com.ag.model.Criterio;
import com.ag.model.Filtros;
import com.ag.model.Tbltipo;
import com.ag.model.Variables;
import com.ag.model.view.Candidato;
import com.ag.model.view.CandidatoSumin;
import com.ag.model.view.ResultCampania;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface CriterioManager {
    
    public List<Criterio> getCriterios();
    
    public List<Filtros> getFiltros(String idCriterio);

    public List<Variables> getVariables(String idGrupo);

    public List<Tbltipo> getOperador();

    public List<Tbltipo> getOperadorLogico();
    
    public List<Tbltipo> getActividades();

    public Filtros agregarFiltro(String usuario,String programa,String codigoVariable,String codigoOperador,String valor,String operadorLogico);
    
    public Filtros agregarFiltro(String usuario,String programa,String codigoVariable,String codigoOperador,String valor,String operadorLogico, Criterio criterio);
    
    public Tbltipo getTipo(String tipo);
    
    public Variables getVariable(String var);
    
    public boolean guardarCriterio(String usuario, String programa, String nombreCriterio, List<Filtros> filtros);
    
    public List<Candidato> getCandidatos(List<Filtros> filtros) throws SQLException; 
    
    public List<CandidatoSumin> getCandidatosSumin(String idTrafos, List<Filtros> filtros) throws SQLException; 
    
    public List<Candidato> getIdMacro() throws SQLException;
    
    public void editFiltro(Filtros f);
    
    public void deleteFiltro(Filtros f);
    
    public Campania saveCampania(String usuario, String programa, String descripcion, String idParametro);
    
    public String saveComponenteCampania(String idComponente, String idTipo, String idMacro, String idParametro, Campania campania);   
    
    public List<ResultCampania> getResultCampanias();
    
    public String importarCampania(String idCampania);
    
    public List<Componente> suministrosAsociados(String idTrafo);
}

