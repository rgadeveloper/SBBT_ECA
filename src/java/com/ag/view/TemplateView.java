/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.Menu;
import com.ag.service.MenuPerfilManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.menubar.Menubar;
//import org.primefaces.component.menuitem.MenuItem;
//import org.primefaces.component.submenu.Submenu;
//import org.primefaces.model.DefaultMenuModel;
//import org.primefaces.model.MenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author 85154220
 */
@ManagedBean
@SessionScoped
public class TemplateView implements Serializable {

  @ManagedProperty("#{login}")
  private Login login;
  private SpringContext context;
  private MenuPerfilManager menuPerfilManager;
  private Menubar menuBar = new Menubar();
  private MenuModel model = new DefaultMenuModel();
  private String consecutivo;

  public TemplateView() {

  }

  @PostConstruct
  public void init() {
    context = SpringContext.getInstance();
    menuPerfilManager = (MenuPerfilManager) context.getBean("MenuPerfilManager");
    construirMenu(login.getUsuario());

  }

  private void reset() {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    session.invalidate();
  }

  public String getUsuario() {
    return getLogin().getUsuario();
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

  public void construirMenu(String usuario) {
    List<Menu> men = menuPerfilManager.MenuPerfilManagerUsu(usuario);
    adicionarElementosMenuModel(men);
  }

  public void adicionarElementosMenuModel(List<Menu> men) {
    //List <Menu> men= menuPerfilManager.MenuPerfilManagerUsu("COMALEX");
    List<Menu> men2 = new ArrayList<Menu>();
    List<Menu> men3 = new ArrayList<Menu>();
    men2.addAll(men);
    men3.addAll(men);
    model = new DefaultMenuModel();

    for (int i = 0; i < men.size(); i++) {
      if (men.get(i).getCodPadre() == null) {
        DefaultSubMenu submenu = new DefaultSubMenu();
        submenu.setLabel(men.get(i).getNombre());
        for (int j = 0; j < men2.size(); j++) {
          //buscamos el submeu del submenu
          if (men.get(i).getCodigo().equals(men2.get(j).getCodPadre())) {
            //Si es un hijo del submenu lo agregamos
            DefaultSubMenu submenu1 = new DefaultSubMenu();
            submenu1.setLabel(men2.get(j).getNombre());
            submenu1.setIcon(men2.get(j).getIcono());
            int cont = 0;

            for (int k = 0; k < men3.size(); k++) {
              //Buscamos los hijos del submenu del submeni
              if (men2.get(j).getCodigo().equals(men3.get(k).getCodPadre())) {
                DefaultMenuItem item = new DefaultMenuItem();
                item.setValue(men3.get(k).getNombre());
                item.setUrl(men3.get(k).getUrl());
                item.setIcon(men3.get(k).getIcono());
                //item.setOnclick(men3.get(k).getOnClick());
                submenu1.addElement(item);
                cont++;
              }
            }
            if (cont == 0) {
              DefaultMenuItem item = new DefaultMenuItem();
              item.setValue(men2.get(j).getNombre());
              item.setUrl(men2.get(j).getUrl());
              item.setIcon(men2.get(j).getIcono());
              item.setOnclick(men3.get(j).getOnClick());
              submenu.addElement(item);
            } else {
              submenu.addElement(submenu1);
            }
          }
        }
        model.addElement(submenu);
      }

    }
  }

  /**
   * @return the menuBar
   */
  public Menubar getMenuBar() {
    return menuBar;
  }

  /**
   * @param menuBar the menuBar to set
   */
  public void setMenuBar(Menubar menuBar) {
    this.menuBar = menuBar;
  }

  /**
   * @return the model
   */
  public MenuModel getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(MenuModel model) {
    this.model = model;
  }

  /**
   * @return the consecutivo
   */
  public String getConsecutivo() {
    return consecutivo;
  }

  /**
   * @param consecutivo the consecutivo to set
   */
  public void setConsecutivo(String consecutivo) {
    this.consecutivo = consecutivo;
  }

}
