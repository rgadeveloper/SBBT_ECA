package com.ag.view;

import com.ag.service.ArbolManager;
import com.ag.service.BalanceManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class CierreBalanceView implements Serializable {

  private ArbolManager arbolManager;
  private BalanceManager balanceManager;
  private SpringContext context;
  private List<String> listPeriodos;
  private String periodoSeleccionado;
  private boolean isInicial = true;

  public CierreBalanceView() {
    context = SpringContext.getInstance();
    arbolManager = (ArbolManager) context.getBean("ArbolManager");
    balanceManager = (BalanceManager) context.getBean("BalanceManager");
  }

  @PostConstruct
  public void init() {
    if (isInicial) {
      setListPeriodos((List<String>) arbolManager.getPeriodo());
      isInicial = false;
    }
  }

  public ArbolManager getArbolManager() {
    return arbolManager;
  }

  public void setArbolManager(ArbolManager arbolManager) {
    this.arbolManager = arbolManager;
  }

  public BalanceManager getBalanceManager() {
    return balanceManager;
  }

  public void setBalanceManager(BalanceManager balanceManager) {
    this.balanceManager = balanceManager;
  }

  public List<String> getListPeriodos() {
    return listPeriodos;
  }

  public void setListPeriodos(List<String> listPeriodos) {
    this.listPeriodos = listPeriodos;
  }

  public String getPeriodoSeleccionado() {
    return periodoSeleccionado;
  }

  public void setPeriodoSeleccionado(String periodoSeleccionado) {
    this.periodoSeleccionado = periodoSeleccionado;
  }

  public boolean isIsInicial() {
    return isInicial;
  }

  public void setIsInicial(boolean isInicial) {
    this.isInicial = isInicial;
  }

  public void cerrarBalance(ActionEvent actionEvent) {
    System.out.println(periodoSeleccionado);
    try {
      if (!balanceManager.cerrarBalances(periodoSeleccionado)) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrió un error al cerrar balance del periodo "+periodoSeleccionado+"!"));
      } else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Balance para el periodo "+periodoSeleccionado+" cerrado con éxito!"));
      }
    } catch (Exception ex) {
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Ocurrió un error en el sistema!"));
    }
  }
}