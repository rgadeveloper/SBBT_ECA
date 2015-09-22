/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service;

import com.ag.model.BalanceEnergia;
import com.ag.model.CargaExtra;
import com.ag.model.EnergiaTotalAreas;
import com.ag.model.Tbltipo;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface ArchivoManager {

  public String[] cargaMacroMasivo(String usuario, String programa, InputStream inputStream);

  public List<Tbltipo> getTipoMacromedidores();

  public String[] cargaMacroIndividual(String trafo, String macro, String tipoMacro, String usuario, String programa);

  public void crearMacroVirtual(String usuario, String programa, String nombre, String direccion, String idComercial);

  public boolean existeMacroVirtual(String idComercial);

  public String[] cargaAsociarMacroCirBar(String usuario, String programa, InputStream inputStream);

  public String[] cargaAsociarTrafoMacro(String usuario, String programa, InputStream inputStream);

  public String balanceVirtual(String periodo, String tipo);

  //--- creado por jose mejia el dia 30/07/2014
  public Tbltipo TipoMacromedidores(String codigo);

  public boolean cargaExtraSave(CargaExtra... cargaExtra);

  public boolean balanceEnergiaSave(BalanceEnergia... balanceEnergia);

  public boolean energiaTotalAreasSave(EnergiaTotalAreas[] energiasTotalAreas);
}
