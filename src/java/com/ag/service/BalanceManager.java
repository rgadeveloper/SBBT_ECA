package com.ag.service;

//import com.ag.model.*;
import com.ag.model.AtrComponente;
import com.ag.model.AtrComponenteMedida;
import com.ag.model.BalanceEnergia;
import com.ag.model.Balances;
import com.ag.model.Componente;
import com.ag.model.ComponenteMedida;
import com.ag.model.Estado;
import com.ag.model.Medida;
import com.ag.model.Novedades;
import com.ag.model.RelComponente;
import com.ag.model.RelComponenteMedida;
import com.ag.model.RelComponenteUbicacion;
import com.ag.model.Tbltipo;
import com.ag.model.ZonaGeografica;
import com.ag.model.view.DataBalanceHijo;
import com.ag.model.view.DataRangosBalance;
import com.ag.model.view.DataValue;
import com.ag.model.view.PadreHijo;
import java.math.BigDecimal;
//import com.ag.model.view.*;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface BalanceManager {

  public List<DataBalanceHijo> cuadroMando(String idComponente, String tipoComponente, String periodo);

  public Balances getBalances(String idComponente, String tipoComponente, String periodo);

  public DataRangosBalance getRangosZonas(String idZona, String tipoComponente, String tipoArbol, String periodo);

  public Medida getMedida(String idComponente, String periodo, String idTipoCompo);

  public AtrComponente getAtrComponente(String idComponente);

  public RelComponenteMedida getRelComponenteMedida(String idComponente, String periodo);

  public AtrComponenteMedida getAtrComponenteMedida(String idComponenteMedida, String periodo);

  public AtrComponenteMedida getAtrComponenteMedidaTrafo(String nombre, String periodo);

  public PadreHijo getSubestacionCircuito(String idComponente, String periodo);

  public PadreHijo getZonaMunicipio(String idComponente, String periodo);

  public ComponenteMedida getComponenteMedida(String idComponenteMedida, String periodo);

  public Componente getComponente(String idComponente);

  public List<DataValue> getCantSumXTipoUso(String idComponente, String periodo);

  public String getCantSumNoMedidos(String idComponente, String periodo);

  public List<Medida> getListMedida(String idComponente, int periodo);

  public int getNumMesesAtras();

  public List<Componente> getTrafosByRango(String id, String tipo, String periodo, String descripcion);

  public ZonaGeografica getPadreByTipo(String idComponente, String tipo);

  public ZonaGeografica getZonaGeografica(String idZona, String tipo);

  public ZonaGeografica getAreaMt(String idZona, String tipo);

  public List<Novedades> getNovedadesByTrafo(String idComponenteMedida);

  public void saveNovedad(String usuario, String programa, int periodo, String idTipoNovedad, ComponenteMedida componenteMedida);

  public void editNovedad(Novedades n);

  public void deleteNovedad(Novedades n);

  public List<Tbltipo> getTiposNovedades();

  public Tbltipo getTipo(String tipo);

  public List<RelComponente> RelComponente(String relComponente);

  public List<RelComponenteUbicacion> RelComponenteU(String relComponente);

  public Estado getEstado(String codigo);

  public boolean saveRelCompoente(RelComponente... rc);

  public boolean saveRelCompoenteUbi(RelComponenteUbicacion... rcu);

  public String getAreaMt(String idComercial);

  public BalanceEnergia getBalanceEnergia(String idComponente, String periodo);

  public boolean cerrarBalances(String periodos);

  public BigDecimal getEnergiaTotalAreas(String periodo);

  public BigDecimal getEnergiaEntradaAreas(String periodo);

  public BigDecimal getEnergiaSalidaAreas(String periodo);

  public BigDecimal getPorcentajePerdidaAreas(String periodo);

}
