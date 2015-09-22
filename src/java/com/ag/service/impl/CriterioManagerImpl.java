/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.model.Campania;
import com.ag.model.Componente;
import com.ag.model.ComponenteCampania;
import com.ag.model.ComponenteCampaniaPK;
import com.ag.model.Criterio;
import com.ag.model.Filtros;
import com.ag.model.Tbltipo;
import com.ag.model.Variables;
//import com.ag.model.*;
import com.ag.model.view.Candidato;
import com.ag.model.view.CandidatoSumin;
import com.ag.model.view.Fecha;
import com.ag.model.view.ResultCampania;
import com.ag.service.ConsecutivoManager;
import com.ag.service.CriterioManager;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
//import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Larry
 */
@Service("CriterioManager")
public class CriterioManagerImpl implements CriterioManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    @Autowired
    @Qualifier("ConsecutivoManager")
    private ConsecutivoManager consecutivo;
    private Connection con = null;

    @Override
    public List<Criterio> getCriterios() {
        String hql = "select c "
                + " from Criterio c";
        return dao.find(hql);
    }

    @Override
    public List<Filtros> getFiltros(String idCriterio) {
        String hql = "select f "
                + " from Filtros f"
                + " where f.criterio.idCriterio = " + idCriterio;
        return dao.find(hql);

    }

    @Override
    public List<Variables> getVariables(String idGrupo) {
        String hql = "select t "
                + "from Variables t "
                + "where t.grupo.codigo='" + idGrupo + "'";
        return dao.find(hql);

    }

    @Override
    public List<Tbltipo> getOperador() {
        String hql = "select t "
                + " from Tbltipo t"
                + " where t.grupo.codigo='TFO000'";
        return dao.find(hql);

    }

    @Override
    public List<Tbltipo> getOperadorLogico() {
        String hql = "select t "
                + " from Tbltipo t"
                + " where t.grupo.codigo='TFL000'";
        return dao.find(hql);

    }

    @Override
    public List<Tbltipo> getActividades() {
        String hql = "select t "
                + " from Tbltipo t"
                + " where t.grupo.codigo='TAA000'";
        return dao.find(hql);
    }

    @Override
    public Filtros agregarFiltro(String usuario, String programa, String codigoVariable, String codigoOperador, String valor, String operadorLogico) {
        Calendar hoy = Calendar.getInstance();
        //String idFiltro = consecutivo.getConsecutivo("FILTROS", usuario, programa, "A", "0");

        //BigDecimal id = new BigDecimal(idFiltro);
        Filtros filtro = new Filtros();
        filtro.setUsuario(usuario);
        filtro.setPrograma(programa);
        filtro.setFechaModif(hoy.getTime());
        //filtro.setIdFiltro(id);
        filtro.setCriterio(null);
        filtro.setTbltipo(getTipo("TFF001"));// TIpo Operador 2
        filtro.setVariables(getVariable(codigoVariable));
        filtro.setTbltipo3(getTipo(codigoOperador));// tipo filtro
        filtro.setTbltipo2(getTipo("TFF001"));// tipo operador 2 esto se debe quitar
        filtro.setTbltipo1(getTipo(operadorLogico));//TIpo operador logico
        filtro.setValor1(valor);
        //dao.persist(filtro);
        return filtro;
    }

    @Override
    public Filtros agregarFiltro(String usuario, String programa, String codigoVariable, String codigoOperador, String valor, String operadorLogico, Criterio criterio) {
        Calendar hoy = Calendar.getInstance();
        String idFiltro = consecutivo.getConsecutivo("FILTROS", usuario, programa, "A", "0");
        BigDecimal id = new BigDecimal(idFiltro);
        Filtros filtro = new Filtros();
        filtro.setUsuario(usuario);
        filtro.setPrograma(programa);
        filtro.setFechaModif(hoy.getTime());
        filtro.setIdFiltro(id);
        filtro.setCriterio(criterio);

        //LOB.2013.01.25.INI
       /* filtro.setTbltipo(getTipo("TFF001"));// TIpo Operador 2
         filtro.setVariables(getVariable(codigoVariable));
         filtro.setTbltipo3(getTipo(codigoOperador));// tipo filtro
         filtro.setTbltipo2(getTipo("TFF001"));// tipo operador 2 esto se debe quitar
         filtro.setTbltipo1(getTipo(operadorLogico));//TIpo operador logico
         */
        filtro.setTbltipo(getTipo("TFF001"));// TIpo Operador 2
        filtro.setTbltipo1(getTipo(operadorLogico));// tipo operador
        filtro.setTbltipo2(getTipo("TFF001"));
        filtro.setTbltipo3(getTipo(codigoOperador));// tipo operador 2 esto se debe quitar
        filtro.setVariables(getVariable(codigoVariable));
        /*
         * tbltipo = TIPO_OPERADOR_2
         * tbltipo1 = TIPO_OPERADOR_LOGICO
         * tbltipo2 = TIPO_FILTRO
         * tbltipo3 = TIPO_OPERADOR
         */



        try {
            filtro.setValor1(valor);
            dao.persist(filtro);

        } catch (Exception ex) {
            System.out.println("Agredando Filtro " + ex);

        }
        return filtro;
        //LOB.2013.01.25.FIN
    }

    @Override
    public Tbltipo getTipo(String tipo) {
        String hql = "Select t from Tbltipo t where t.tipo ='" + tipo + "'";
        return dao.findObject(hql);
    }

    @Override
    public Variables getVariable(String var) {
        String hql = "Select t from Variables t where t.idVariable ='" + var + "'";
        return dao.findObject(hql);
    }

    @Override
    public boolean guardarCriterio(String usuario, String programa, String nombreCriterio, List<Filtros> filtros) {
        boolean resultado = true;
        try {
            Criterio criterio = new Criterio();
            Calendar hoy = Calendar.getInstance();
            String idCriterio = consecutivo.getConsecutivo("CRITERIO", usuario, programa, "A", "0");
            BigDecimal id = new BigDecimal(idCriterio);
            criterio.setIdCriterio(id);
            criterio.setDescripcion(nombreCriterio);
            criterio.setUsuario(usuario);
            criterio.setPrograma(programa);
            criterio.setFechaModif(hoy.getTime());
            dao.persist(criterio);
            for (int i = 0; i < filtros.size(); i++) {
                Filtros f = filtros.get(i);
                String idFiltro = consecutivo.getConsecutivo("FILTROS", usuario, programa, "A", "0");
                f.setIdFiltro(new BigDecimal(idFiltro));
                f.setCriterio(criterio);
                dao.persist(f);
            }
        } catch (Exception ex) {
            resultado = false;
        }
        return resultado;
    }

    @Override
    public List<Candidato> getCandidatos(List<Filtros> filtros) throws SQLException {
        String periodo = getPeriodoActual();
        List<Candidato> resultado = new ArrayList<Candidato>();
        String sql = " select distinct COMPONENTE.ID_COMPONENTE,COMPONENTE.NOMBRE,cm.ID_COMERCIAL from COMPONENTE,"
                + " rel_componente_ubicacion ru,"
                + " zona_geografica bar_cir,"
                + " zona_geografica muni_sub,"
                + " zona_geografica zona,"
                + " zona_geografica empresa,"
                + " (Select b.* from balances b where periodo = " + periodo + ") Balances,"
                + " rel_componente_medida rm, "
                + " componente_medida cm "
                + " where COMPONENTE.id_componente = ru.id_componente "
                + " and COMPONENTE.id_tipo_componente = 8 "
                + " and COMPONENTE.estado = 'AC001'"
                + " and ru.periodo_fin > " + periodo
                + " and ru.periodo_ini <= " + periodo
                + " and ru.id_zona = bar_cir.id_zona "
                + " and bar_cir.id_padre = muni_sub.id_zona "
                + " and bar_cir.estado = 'AC001'"
                + " and muni_sub.id_padre = zona.id_zona "
                + " and muni_sub.estado = 'AC001' "
                + " and zona.estado = 'AC001' "
                + " and zona.id_padre = empresa.id_zona "
                + " and empresa.estado = 'AC001' "
                + " and COMPONENTE.id_componente = Balances.id_componente (+)"
                + " and COMPONENTE.id_componente = rm.id_componente"
                + " and rm.periodo_fin > " + periodo
                + " and rm.periodo_ini <= " + periodo
                + " and rm.id_estado = 'AC001'"
                + " and rm.id_componente_medida = cm.id_componente_medida";
        int nOR = 0;
        for (int i = 0; i < filtros.size(); i++) {
            Filtros f = filtros.get(i);
            String operador = "";
            String valor = "";
            int sw = 0;

            if (f.getTbltipo1().getNombre().equals("OR")) {
                if (nOR == 0) {
                    sql += " AND(" + f.getVariables().getTabla()
                            + "." + f.getVariables().getCampo();
                    nOR = 1;
                } else {
                    sql += " " + f.getTbltipo1().getNombre()
                            + " " + f.getVariables().getTabla() + "." + f.getVariables().getCampo();
                }
            } else {
                if (nOR == 1) {
                    sql += ") " + f.getTbltipo1().getNombre()
                            + " " + f.getVariables().getTabla() + "." + f.getVariables().getCampo();
                    nOR = 0;
                } else {
                    sql += " " + f.getTbltipo1().getNombre()
                            + " " + f.getVariables().getTabla() + "." + f.getVariables().getCampo();
                }
            }

            /*sql +=  " " + f.getTbltipo1().getNombre()
             +" " + f.getVariables().getTabla() +"."+ f.getVariables().getCampo();
             */// +" " + f.getTbltipo().getNombre(); //Operador

            if (f.getTbltipo3().getNombre().equals("LIKE '")) {
                operador = " LIKE '";
                sw = 1;
            } else if (f.getTbltipo3().getNombre().equals("NOT LIKE '")) {
                operador = " NOT LIKE '";
                sw = 2;
            } else if (f.getTbltipo3().getNombre().equals("LIKE '%")) {
                operador = " LIKE '%";
                sw = 3;
            } else if (f.getTbltipo3().getNombre().equals("NOT LIKE '%")) {
                operador = " NOT LIKE '%";
                sw = 4;
            } else if (f.getTbltipo3().getNombre().equals("IS NULL")) {
                operador = " IS NULL";
                sw = 5;
            } else if (f.getTbltipo3().getNombre().equals("IS NOT NULL")) {
                operador = " IS NOT NULL";
                sw = 6;
            } else {
                operador = " " + f.getTbltipo3().getNombre();
                sw = 0;
            }

            if (sw == 0) {
                if (f.getVariables().getTbltipo().getTipo().equals("TTV001")) {//Numerico
                    valor += f.getValor1();
                } else if (f.getVariables().getTbltipo().getTipo().equals("TTV001")) {//Caracter
                    valor += " '" + f.getValor1() + "'";
                } else if (f.getVariables().getTbltipo().getTipo().equals("TTV003")) {//FECHA
                    valor += " TO_DATE('" + f.getValor1() + "','DD/MM/YYYY)'";
                }
                sql += operador + valor;
            } else {
                if (sw == 1 || sw == 2) {
                    operador += f.getValor1();
                    operador += "%'";
                } else if (sw == 3 || sw == 4) {
                    operador += f.getValor1();
                    operador += "'";
                }


                sql += operador;
            }

        }

        if (nOR == 1) {
            sql += ")";
        }

        Statement statement = dao.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String idComponente = rs.getString("ID_COMPONENTE");
            String nombre = rs.getString("NOMBRE");
            String idMacro = rs.getString("ID_COMERCIAL");
            boolean check = true;
            Candidato c = new Candidato(idComponente, nombre, idMacro, check);
            resultado.add(c);
        }
        rs.close();
        statement.close();
        return resultado;
    }

    @Override
    public List<CandidatoSumin> getCandidatosSumin(String idTrafos, List<Filtros> filtros) throws SQLException {
        String periodo = getPeriodoActual();
        List<CandidatoSumin> resultado = new ArrayList<CandidatoSumin>();
        String sql = "select c.id_componente, c.nombre, c.id_cliente, trafo.id_cliente "
                + "from componente c, medida medida, rel_componente r, componente trafo "
                + "where c.estado='AC001' and"
                + "      c.id_componente in (select rc.id_componente_hijo "
                + "                          from rel_componente rc"
                + "                          where rc.id_componente in " + idTrafos
                + "                                and rc.periodo_fin > " + periodo
                + "                                and rc.periodo_ini <=" + periodo + ")"
                + "      and c.id_componente=medida.id_componente "
                + "      and medida.periodo=" + periodo
                + "      and r.id_componente_hijo=c.id_componente"
                + "      and trafo.id_componente=r.id_componente ";

        int nOR = 0;
        for (int i = 0; i < filtros.size(); i++) {
            Filtros f = filtros.get(i);
            String operador = "";
            String valor = "";
            int sw = 0;

            if (f.getTbltipo1().getNombre().equals("OR")) {
                if (nOR == 0) {
                    sql += " AND(" + f.getVariables().getTabla()
                            + "." + f.getVariables().getCampo();
                    nOR = 1;
                } else {
                    sql += " " + f.getTbltipo1().getNombre()
                            + " " + f.getVariables().getTabla() + "." + f.getVariables().getCampo();
                }
            } else {
                if (nOR == 1) {
                    sql += ") " + f.getTbltipo1().getNombre()
                            + " " + f.getVariables().getTabla() + "." + f.getVariables().getCampo();
                    nOR = 0;
                } else {
                    sql += " " + f.getTbltipo1().getNombre()
                            + " " + f.getVariables().getTabla() + "." + f.getVariables().getCampo();
                }
            }

            /*sql +=  " " + f.getTbltipo1().getNombre()
             +" " + f.getVariables().getTabla() +"."+ f.getVariables().getCampo();
             */// +" " + f.getTbltipo().getNombre(); //Operador

            if (f.getTbltipo3().getNombre().equals("LIKE '")) {
                operador = " LIKE '";
                sw = 1;
            } else if (f.getTbltipo3().getNombre().equals("NOT LIKE '")) {
                operador = " NOT LIKE '";
                sw = 2;
            } else if (f.getTbltipo3().getNombre().equals("LIKE '%")) {
                operador = " LIKE '%";
                sw = 3;
            } else if (f.getTbltipo3().getNombre().equals("NOT LIKE '%")) {
                operador = " NOT LIKE '%";
                sw = 4;
            } else if (f.getTbltipo3().getNombre().equals("IS NULL")) {
                operador = " IS NULL";
                sw = 5;
            } else if (f.getTbltipo3().getNombre().equals("IS NOT NULL")) {
                operador = " IS NOT NULL";
                sw = 6;
            } else {
                operador = " " + f.getTbltipo3().getNombre();
                sw = 0;
            }

            if (sw == 0) {
                if (f.getVariables().getTbltipo().getTipo().equals("TTV001")) {//Numerico
                    valor += f.getValor1();
                } else if (f.getVariables().getTbltipo().getTipo().equals("TTV001")) {//Caracter
                    valor += " '" + f.getValor1() + "'";
                } else if (f.getVariables().getTbltipo().getTipo().equals("TTV003")) {//FECHA
                    valor += " TO_DATE('" + f.getValor1() + "','DD/MM/YYYY)'";
                }
                sql += operador + valor;
            } else {
                if (sw == 1 || sw == 2) {
                    operador += f.getValor1();
                    operador += "%'";
                } else if (sw == 3 || sw == 4) {
                    operador += f.getValor1();
                    operador += "'";
                }


                sql += operador;
            }

        }

        if (nOR == 1) {
            sql += ")";
        }

        Statement statement = dao.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String idComponente = rs.getString(1);
            String nombre = rs.getString(2);
            String idCliente = rs.getString(3);
            String idClienteTrafo = rs.getString(4);
            boolean check = true;
            CandidatoSumin c = new CandidatoSumin(idComponente, nombre, idCliente, idClienteTrafo, check);
            resultado.add(c);
        }
        rs.close();
        statement.close();
        return resultado;
    }

    public String getPeriodoActual() {
        String hql = " SELECT p.valor "
                + "  FROM Parametro p "
                + " where p.idParametro='PERIODO_ACTUAL'";
        return dao.findObject(hql);
    }

    @Override
    public List<Candidato> getIdMacro() throws SQLException {
        String periodo = getPeriodoActual();
        List<Candidato> resultado = new ArrayList<Candidato>();
        String sql = " select distinct c.ID_COMPONENTE,C.NOMBRE,cm.id_comercial, c.id_cliente, "
                + " bar_cir.nombre circuito, b.cant_sum_fact, zona.nombre zona, b.porc_perdida_mes, "
                + " b.rango_balance "
                + " from componente c,"
                + " rel_componente_ubicacion ru,"
                + " zona_geografica bar_cir,"
                + " zona_geografica muni_sub,"
                + " zona_geografica zona,"
                + " zona_geografica emp,"
                + " (Select b.* from balances b where periodo = " + periodo + ") b,"
                + " rel_componente_medida rm, "
                + " componente_medida cm "
                + " where c.id_componente = ru.id_componente "
                + " and c.id_tipo_componente = 8 "
                + " and c.estado = 'AC001'"
                + " and ru.periodo_fin > " + periodo
                + " and ru.periodo_ini <= " + periodo
                + " and ru.id_zona = bar_cir.id_zona "
                + " and bar_cir.id_padre = muni_sub.id_zona "
                + " and bar_cir.estado = 'AC001'"
                + " and muni_sub.id_padre = zona.id_zona "
                + " and muni_sub.estado = 'AC001' "
                + " and zona.estado = 'AC001' "
                + " and zona.id_padre = emp.id_zona "
                + " and emp.estado = 'AC001' "
                + " and c.id_componente = b.id_componente (+)"
                + " and c.id_componente = rm.id_componente"
                + " and rm.periodo_fin > " + periodo
                + " and rm.periodo_ini <= " + periodo
                + " and rm.id_estado = 'AC001'"
                + " and rm.id_componente_medida = cm.id_componente_medida "
                + " and bar_cir.id_tipo_componente = 3 ";

        Statement statement = dao.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String idComponente = rs.getString("ID_CLIENTE");
            String nombre = rs.getString("NOMBRE");
            String idMacro = rs.getString("ID_COMERCIAL");
            boolean check = true;
            String zona = rs.getString("ZONA");
            String circuito = rs.getString("CIRCUITO");
            String cantSum = rs.getString("cant_sum_fact");
            String porcPerdida = rs.getString("porc_perdida_mes");
            String rango = rs.getString("rango_balance");

            Candidato c = new Candidato(idComponente, nombre, idMacro, check, zona, circuito,
                    cantSum, porcPerdida, rango);
            resultado.add(c);
        }
        rs.close();
        statement.close();
        return resultado;
    }

    @Override
    public void editFiltro(Filtros f) {
        dao.persist(f);
    }

    @Override
    public void deleteFiltro(Filtros f) {
        dao.delete(f);
    }

    @Override
    public Campania saveCampania(String usuario, String programa, String descripcion, String idParametro) {
        String idCampania = consecutivo.getConsecutivo("CAMPANIA", usuario, programa, "A", "0");
        Campania c = new Campania(new BigDecimal(idCampania));
        c.setUsuario(usuario);
        c.setPrograma(programa);
        c.setDescripcion(descripcion);
        c.setPeriodo(getMaxPeriodo());
        String idTipo = idParametro.equals("ACTIVIDAD_MACRO")
                ? getValorParametro(idParametro) : idParametro;
        c.setTbltipo(getTipo(idTipo));
        Fecha date = new Fecha();
        c.setFechaInicio(date.getFechaSistema());
        c.setFechaModif(date.getFecha());
        c.setFechaFin(date.getFechaSistema());
        dao.persist(c);
        return c;
    }

    @Override
    public String saveComponenteCampania(String idComponente, String idTipo, String idMacro, String idParametro, Campania campania) {
        ComponenteCampania cc = new ComponenteCampania();
        cc.setCampania(campania);

        Componente c = idTipo.equals("8")
                ? getComponenteByNombre(idComponente)
                : getComponente(idComponente);

        String actividad = idParametro.equals("ACTIVIDAD_MACRO")
                ? getValorParametro(idParametro) : idParametro;

        cc.setComponente(c);
        cc.setTbltipo(getTipo(idTipo));
        cc.setProcesado("N");
        cc.setActividad(actividad);
        cc.setIdMacro(idMacro);

        ComponenteCampaniaPK componenteCampaniaPK = new ComponenteCampaniaPK(c.getIdComponente().toBigInteger(), campania.getIdCampania().toBigInteger());
        cc.setComponenteCampaniaPK(componenteCampaniaPK);

        dao.persist(cc);
        if (idTipo.equals("9")) {
            idMacro = getIdCliente(idComponente);
        }

        String linea = actividad + "|Null|Null|" + idMacro + "|Null|" + campania.getIdCampania()
                + "|\"" + actividad + "&\"|\"&Null&\"|\"&Null&\"|\"&" + idMacro + "&\"|\"&Null&\"|\"";
        return linea;
    }

    @Override
    public List<ResultCampania> getResultCampanias() {
        List<ResultCampania> resultCampanias = new ArrayList<ResultCampania>();
        String hql = "SELECT c FROM Campania c";
        List data = dao.find(hql);
        if (!data.isEmpty()) {
            for (Iterator it = data.iterator(); it.hasNext();) {
                Campania c = (Campania) it.next();
                String idCampania = c.getIdCampania().toString();
                String nombre = c.getDescripcion();
                String periodo = String.valueOf(c.getPeriodo());
                Tbltipo tipo = c.getTbltipo();
                ResultCampania rc = new ResultCampania(idCampania, nombre, periodo, tipo);
                //demas valores de los query de Larry 
                if (tipo.getTipo().equals("7526")) {//Revisión/Normalización Macro
                    rc.setCantMacrosTotal(cantidadDeMacros("Total", idCampania));
                    rc.setCantMacrosNormales(cantidadDeMacros("Normales", idCampania));
                    rc.setCantMacrosRevisados(cantidadDeMacros("Revisados", idCampania));
                    rc.setCantMacrosNormalizados(cantidadDeMacros("Normalizados", idCampania));
                    rc.setCantMacrosIrregularidad(cantidadDeMacros("Irregularidad", idCampania));
                    rc.setPorcEfectividadMacros(rc.getCantMacrosTotal() != 0
                            ? (rc.getCantMacrosRevisados() / rc.getCantMacrosTotal()) * 100
                            : 0);
                    rc.setMostrarValoresMacros(true);
                } else {//Revisión del usuario               
                    rc.setCantClientes(cantidadDeClientes("Total", idCampania));
                    rc.setCantClientesNormales(cantidadDeClientes("Normales", idCampania));
                    rc.setCantClientesRevisados(cantidadDeClientes("Revisados", idCampania));
                    rc.setCantClientesNormalizados(cantidadDeClientes("Normalizados", idCampania));
                    rc.setCantClientesIrregularidad(cantidadDeClientes("Irregularidad", idCampania));
                    /*double porcMacro=rc.getCantMacrosTotal()!=0?
                     (rc.getCantMacrosRevisados()/rc.getCantMacrosTotal())*100:
                     0;*/
                    rc.setPorcEfectividadClientes(rc.getCantMacrosTotal() != 0
                            ? (rc.getCantMacrosRevisados() / rc.getCantMacrosTotal()) * 100
                            : 0);
                    rc.setMostrarValoresClientes(true);
                }
                rc.setComponenetesAsociados(getComponentesByCampanias(idCampania));
                resultCampanias.add(rc);
            }
        }
        return resultCampanias;
    }

    @Override
    public String importarCampania(String idCampania) {
        try {
            con = dao.getConnection();

            String sql = "{call P_INT_OS(?,?)}";
            CallableStatement statement = con.prepareCall(sql);

            statement.setInt(1, Integer.valueOf(idCampania));

            statement.registerOutParameter(2, Types.VARCHAR);

            statement.executeQuery();

            String error = statement.getString(2);
            con.close();
            return error;

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(InterfazManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Error al ejecutar en la BD";
        }
    }

    @Override
    public List<Componente> suministrosAsociados(String idTrafo) {
        String hql = "SELECT r.componente FROM RelComponente r "
                + "WHERE r.periodoFin=999912 and"
                + " r.estado1.idEstado='AC001' and"
                + " r.relComponentePK.idComponente=" + idTrafo;
        return dao.find(hql);
    }

    public List<ComponenteCampania> getComponentesByCampanias(String idCampania) {
        String hql = "SELECT c FROM ComponenteCampania c WHERE c.componenteCampaniaPK.idCampania =" + idCampania;
        return dao.find(hql);
    }

    public int getMaxPeriodo() {
        String hql = "SELECT max(b.balancesPK.periodo) FROM Balances b";
        int periodo = Integer.valueOf(dao.findObject(hql).toString());
        return periodo;
    }

    public String getValorParametro(String idParametro) {
        String hql = " SELECT p.valor FROM Parametro p WHERE p.idParametro='" + idParametro + "'";
        return dao.findObject(hql).toString();
    }

    public Componente getComponenteByNombre(String nombre) {
        String hql = " select c from Componente c "
                + " where c.nombre = '" + nombre + "'";
        return dao.findObject(hql);
    }

    public Componente getComponente(String idComponente) {
        String hql = " select c from Componente c "
                + " where c.idComponente = " + idComponente;
        return dao.findObject(hql);
    }

    public String getIdCliente(String idComponente) {
        String hql = " select c.idCliente from Componente c "
                + " where c.idComponente = " + idComponente;
        Object object = dao.findObject(hql);
        String idCliente = object != null ? object.toString() : "null";
        return idCliente;
    }

    public double cantidadDeMacros(String con, String idCampania) {
        double result;
        String hql;

        if (con.equals("Total")) {
            hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                    + " and o.tipoComponente=8";
            result = Double.valueOf(dao.findObject(hql).toString());
        } else {
            if (con.equals("Irregularidad")) {
                hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                        + " AND o.irregularidad='S' and o.tipoComponente=8";
                result = Double.valueOf(dao.findObject(hql).toString());
            } else {
                if (con.equals("Normales")) {
                    hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                            + " AND o.irregularidad='N' and o.tipoComponente=8";
                    result = Double.valueOf(dao.findObject(hql).toString());
                } else {
                    if (con.equals("Normalizados")) {
                        hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                                + " AND o.normalizado='S' and o.tipoComponente=8";
                        result = Double.valueOf(dao.findObject(hql).toString());
                    } else {
                        result = 0;
                    }
                }
            }
        }

        return result;
    }

    public double cantidadDeClientes(String con, String idCampania) {
        double result;
        String hql;

        if (con.equals("Total")) {
            hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                    + " and o.tipoComponente=9";
            result = Double.valueOf(dao.findObject(hql).toString());
        } else {
            if (con.equals("Irregularidad")) {
                hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                        + " AND o.irregularidad='S' and o.tipoComponente=9";
                result = Double.valueOf(dao.findObject(hql).toString());
            } else {
                if (con.equals("Normales")) {
                    hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                            + " AND o.irregularidad='N' and o.tipoComponente=9";
                    result = Double.valueOf(dao.findObject(hql).toString());
                } else {
                    if (con.equals("Normalizados")) {
                        hql = "SELECT COUNT(*) FROM  OrdenTrabajo o WHERE o.campania.idCampania = " + idCampania
                                + " AND o.normalizado='S' and o.tipoComponente=9";
                        result = Double.valueOf(dao.findObject(hql).toString());
                    } else {
                        result = 0;
                    }
                }
            }
        }

        return result;
    }
}
