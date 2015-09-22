package com.ag.view;

import com.ag.model.view.Nodo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MapBean implements Serializable {

  private MapModel advancedModel;
  private Marker marker;
  private String cordX;
  private String cordY;
  private String nombreComponente;
  private String perdidas;
  private HashMap zoomTipoComponente;
  private String zoom;
  private String center;
  private String tipo;
  private String codigo;
  private String nombreTipo;
  private String numMacroTot;
  private String numMacrosFuncionando;
  private String localizacion;
  private String numSuministrosFact;
  private String tipoUso;

  public MapBean() {
    //Shared coordinates
    /*
     * LatLng coord1 = new LatLng(2.26,-76.36); LatLng coord2 = new
     * LatLng(11.24722,-74.20167);
     *
     * advancedModel.addOverlay(new Marker(coord1, "Popayan"));
     * advancedModel.addOverlay(new Marker(coord2, "Santa Marta"));
     */
  }

  public void createMapa() {
    advancedModel = new DefaultMapModel();
    LatLng coord = new LatLng(Double.parseDouble(cordY), Double.parseDouble(cordX));
    advancedModel.addOverlay(new Marker(coord, codigo + ":" + tipo + ":" + getNombreComponente() + " : " + getPerdidas() + "%"));

  }

  public MapModel getAdvancedModel() {
    return advancedModel;
  }

  public void onMarkerSelect(OverlaySelectEvent event) {
    marker = (Marker) event.getOverlay();
  }

  public Marker getMarker() {
    return marker;
  }

  public void setNodo(Nodo node) {
    this.cordX = node.getCoordenadaX();
    this.cordY = node.getCoordenadaY();
    this.nombreComponente = node.getNombre();
    this.perdidas = node.getPerdidas();
    this.tipo = node.getTipo();
    this.codigo = node.getCodigo();
    this.nombreTipo = node.getNombreTipo();
    this.numMacroTot = node.getNumMacroTot();
    this.numMacrosFuncionando = node.getNumMacrosFuncionando();
    this.numSuministrosFact = node.getNumSuministrosFact();
    setZoom(node.getTipo());
    setCenter(node);
    createMapa();
  }

  public void setNodoList(Nodo nodoPadre) {
    advancedModel = new DefaultMapModel();
    List nodeList = nodoPadre.getHijos();
    setZoom(nodoPadre.getTipo());

    //Se agrega condición para cuando nodeList sea != 0
    if (nodeList != null && nodeList.size() != 0) {
      for (int i = 0; i < nodeList.size(); i++) {
        Nodo nodoDataHijo = (Nodo) nodeList.get(i);
        if (nodoDataHijo.getCoordenadaX() != null) {
          //Si el primer hijo tiene la coordenada x nula no pinta el padre en el mapa
          //Para que lo muestre se agregaron nvl en la consulta para contComponente de ArbolManagerImpl
          if (i == 0) {
            if (nodoPadre.getTipo().equals("8")) {
              setCenter(nodoPadre);
            } else {
              setCenter(nodoDataHijo);
            }
          }
          double x = Double.parseDouble(nodoDataHijo.getCoordenadaX());
          LatLng coord = new LatLng(Double.parseDouble(nodoDataHijo.getCoordenadaY()), x);
          setZoom(nodoDataHijo.getTipo());
          nombreTipo = nodoDataHijo.getNombreTipo();
          numMacroTot = nodoDataHijo.getNumMacroTot();
          numMacrosFuncionando = nodoDataHijo.getNumMacrosFuncionando();
          localizacion = nodoDataHijo.getLocalizacion();
          nombreComponente = nodoDataHijo.getNombre();
          perdidas = nodoDataHijo.getPerdidas();
          numSuministrosFact = nodoDataHijo.getNumSuministrosFact();
          tipoUso = nodoDataHijo.getTipoUso();
          cordX = nodoDataHijo.getCoordenadaX();
          cordY = nodoDataHijo.getCoordenadaY();
          /*if (!nodoDataHijo.getTipo().equals("9")) {
           if (nodoDataHijo.getTipo().equals("8")) {                            
           if (nodoDataHijo.getTipoTrafo().equals("COMERCIAL"))
           advancedModel.addOverlay(new Marker(coord,nodoDataHijo.getCodigo()+":"+nodoDataHijo.getTipo()+":"+ nodoDataHijo.getNombre() + " : " + nodoDataHijo.getPerdidas() + "%", "comercialMarker.png","../resources/images/comercialMarker.png"));
           else if(nodoDataHijo.getTipoTrafo().equals("RESIDENCIAL"))                                 
           advancedModel.addOverlay(new Marker(coord,nodoDataHijo.getCodigo()+":"+nodoDataHijo.getTipo()+":"+ nodoDataHijo.getNombre() + " : " + nodoDataHijo.getPerdidas() + "%", "residencialMarker.png","../resources/images/residencialMarker.png"));
           else if(nodoDataHijo.getTipoTrafo().equals("INDUSTRIAL"))
           advancedModel.addOverlay(new Marker(coord,nodoDataHijo.getCodigo()+":"+nodoDataHijo.getTipo()+":"+ nodoDataHijo.getNombre() + " : " + nodoDataHijo.getPerdidas() + "%", "industrialMarker.png","../resources/images/industrialMarker.png"));
           else
           advancedModel.addOverlay(new Marker(coord,nodoDataHijo.getCodigo()+":"+nodoDataHijo.getTipo()+":"+ nodoDataHijo.getNombre() + " : " + nodoDataHijo.getPerdidas() + "%")); 
           }else
           advancedModel.addOverlay(new Marker(coord,nodoDataHijo.getCodigo()+":"+nodoDataHijo.getTipo()+":"+ nodoDataHijo.getNombre() + " : " + nodoDataHijo.getPerdidas() + "%"));
           } else {*/
          if (nodoDataHijo.getTipo().equals("8")) {
            if (i == 0) {
              double xi = Double.parseDouble(nodoDataHijo.getCoordenadaX());
              LatLng coordC = new LatLng(Double.parseDouble(nodoDataHijo.getCoordenadaY()), xi);
              if (nodoPadre.getTipo().equals("2")) {
                advancedModel.addOverlay(new Marker(coordC, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/cliente/tipo-2.png"));
              } else {
                advancedModel.addOverlay(new Marker(coordC, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/cliente/tipo-6-3-7.png"));
              }
            }
            String carpeta = "";
            if (nodoDataHijo.getTipoUso().equalsIgnoreCase("COMERCIAL")) {
              carpeta = "comercial";
            } else if (nodoDataHijo.getTipoUso().equalsIgnoreCase("RESIDENCIAL")) {
              carpeta = "residencial";
            } else if (nodoDataHijo.getTipoUso().equalsIgnoreCase("INDUSTRIAL")) {
              carpeta = "industrial";
            } else if (nodoDataHijo.getTipoUso().equalsIgnoreCase("OFICIAL")) {
              carpeta = "oficial";
            } else if (nodoDataHijo.getTipoUso().equalsIgnoreCase("ALUM PUBLICO")) {
              carpeta = "alumbrado";
            } else {
              carpeta = "basico";
            }

            if (nodoDataHijo.getNombreColor().equalsIgnoreCase("azul")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "azul.png" : carpeta + "/azul.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("negro")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "negro.png" : carpeta + "/negro.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("amarillo")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "amarillo.png" : carpeta + "/amarillo.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("blanco")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "blanco.png" : carpeta + "/blanco.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("magenta")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "magenta.png" : carpeta + "/magenta.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("pastel")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "pastel.png" : carpeta + "/pastel.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("morado")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "morado.png" : carpeta + "/morado.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("marron")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "marron.png" : carpeta + "/marron.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("purpura")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "marron.png" : carpeta + "/purpura.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("verde")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "verde.png" : carpeta + "/verde.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("rojo")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "rojo.png" : carpeta + "/rojo.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("violeta")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "violeta.png" : carpeta + "/violeta.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("naranja")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "naranja.png" : carpeta + "/naranja.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("fucsia")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "fucsia.png" : carpeta + "/fucsia.png")));
            } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("cyan")) {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "cyan.png" : carpeta + "/cyan.png")));
            } else {
              advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "none.png" : carpeta + "/none.png")));
            }
          } else if (nodoDataHijo.getTipo().equals("9")) {
            if (i == 0) {
              if (nodoPadre.getTipo().equals("8")) {
                double xi = Double.parseDouble(nodoPadre.getCoordenadaX());
                LatLng coordI = new LatLng(Double.parseDouble(nodoPadre.getCoordenadaY()), xi);
                String carpeta = "";
                if (nodoPadre.getTipoUso().equalsIgnoreCase("COMERCIAL")) {
                  carpeta = "comercial";
                } else if (nodoPadre.getTipoUso().equalsIgnoreCase("RESIDENCIAL")) {
                  carpeta = "residencial";
                } else if (nodoPadre.getTipoUso().equalsIgnoreCase("INDUSTRIAL")) {
                  carpeta = "industrial";
                } else if (nodoPadre.getTipoUso().equalsIgnoreCase("OFICIAL")) {
                  carpeta = "oficial";
                } else if (nodoPadre.getTipoUso().equalsIgnoreCase("ALUM PUBLICO")) {
                  carpeta = "alumbrado";
                } else {
                  carpeta = "basico";
                }

                if (nodoPadre.getNombreColor().equalsIgnoreCase("azul")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "azul.png" : carpeta + "/azul.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("amarillo")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "amarillo.png" : carpeta + "/amarillo.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("negro")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "negro.png" : carpeta + "/negro.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("magenta")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "magenta.png" : carpeta + "/magenta.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("pastel")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "pastel.png" : carpeta + "/pastel.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("morado")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "morado.png" : carpeta + "/morado.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("purpura")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "purpura.png" : carpeta + "/purpura.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("marron")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "marron.png" : carpeta + "/marron.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("blanco")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "blanco.png" : carpeta + "/blanco.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("verde")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "verde.png" : carpeta + "/verde.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("rojo")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "rojo.png" : carpeta + "/rojo.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("violeta")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "violeta.png" : carpeta + "/violeta.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("naranja")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "naranja.png" : carpeta + "/naranja.png")));
                } else if (nodoPadre.getNombreColor().equalsIgnoreCase("fucsia")) {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "fucsia.png" : carpeta + "/fucsia.png")));
                } else if (nodoDataHijo.getNombreColor().equalsIgnoreCase("cyan")) {
                  advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "cyan.png" : carpeta + "/cyan.png")));
                } else {
                  advancedModel.addOverlay(new Marker(coordI, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/trafo/" + (carpeta.equals("") ? "none.png" : carpeta + "/none.png")));
                }
              }
            }
            String icono;
            if (nodoDataHijo.getTipoUso().equalsIgnoreCase("COMERCIAL")) {
              icono = "comercial";
            } else if (nodoDataHijo.getTipoUso().equalsIgnoreCase("RESIDENCIAL")) {
              icono = "residencial";
            } else if (nodoDataHijo.getTipoUso().equalsIgnoreCase("INDUSTRIAL")) {
              icono = "industrial";
            } else if (nodoDataHijo.getTipoUso().equalsIgnoreCase("OFICIAL")) {
              icono = "oficial";
            } else if (nodoDataHijo.getTipoUso().equalsIgnoreCase("ALUM PUBLICO")) {
              icono = "alumbrado";
            } else {
              icono = "basico";
            }
            advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/cliente/" + icono + ".png"));
          } else if (nodoDataHijo.getTipo().equals("6") || nodoDataHijo.getTipo().equals("3") || nodoDataHijo.getTipo().equals("7")) {
            advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/cliente/tipo-6-3-7.png"));
          } else if (nodoDataHijo.getTipo().equals("10") || nodoDataHijo.getTipo().equals("11")) {
            advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/cliente/tipo-10-11.png"));
          } else if (nodoDataHijo.getTipo().equals("0") || nodoDataHijo.getTipo().equals("1")) {
            advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/cliente/tipo-0-1.png"));
          } else if (nodoDataHijo.getTipo().equals("2")) {
            advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/cliente/tipo-2.png"));
          } else if (nodoDataHijo.getTipo().equals("4") || nodoDataHijo.getTipo().equals("5")) {
            advancedModel.addOverlay(new Marker(coord, nodoDataHijo.getCodigo() + ":" + nodoDataHijo.getTipo() + ":" + nodoDataHijo.getNombre(), null, "../resources/images/icon-gmap/cliente/tipo-4-5.png"));
          }
        }
      }
    } else {
      /*En caso de que el PCI no tenga hijos, se muestra de todas formas en el mapa*/
      setCenter(nodoPadre);
      double X = Double.parseDouble(nodoPadre.getCoordenadaX());
      LatLng coordenada = new LatLng(Double.parseDouble(nodoPadre.getCoordenadaY()), X);
      advancedModel.addOverlay(new Marker(coordenada, nodoPadre.getCodigo() + ":" + nodoPadre.getTipo() + ":" + nodoPadre.getNombre(), null, "../resources/images/icon-gmap/cliente/" + "basico" + ".png"));
    }
  }

  /**
   * @return the zoomTipoComponente
   */
  public HashMap getZoomTipoComponente() {
    return zoomTipoComponente;
  }

  /**
   * @param zoomTipoComponente the zoomTipoComponente to set
   */
  public void setZoomTipoComponente(HashMap zoomTipoComponente) {
    this.zoomTipoComponente = zoomTipoComponente;
  }

  public void setZoom(String tipoComponente) {
    zoom = (String) zoomTipoComponente.get(tipoComponente);
  }

  public String getZoom() {
    return zoom;
  }

  /**
   * @return the center
   */
  public String getCenter() {
    return center;
  }

  /**
   * @param center the center to set
   */
  public void setCenter(Nodo node) {
    this.center = node.getCoordenadaY() + "," + node.getCoordenadaX();
  }

  /**
   * @return the nombreTipo
   */
  public String getNombreTipo() {
    return nombreTipo;
  }

  /**
   * @param nombreTipo the nombreTipo to set
   */
  public void setNombreTipo(String nombreTipo) {
    this.nombreTipo = nombreTipo;
  }

  /**
   * @return the numMacroTot
   */
  public String getNumMacroTot() {
    return numMacroTot;
  }

  /**
   * @param numMacroTot the numMacroTot to set
   */
  public void setNumMacroTot(String numMacroTot) {
    this.numMacroTot = numMacroTot;
  }

  /**
   * @return the numMacrosFuncionando
   */
  public String getNumMacrosFuncionando() {
    return numMacrosFuncionando;
  }

  /**
   * @param numMacrosFuncionando the numMacrosFuncionando to set
   */
  public void setNumMacrosFuncionando(String numMacrosFuncionando) {
    this.numMacrosFuncionando = numMacrosFuncionando;
  }

  /**
   * @return the localizacion
   */
  public String getLocalizacion() {
    return localizacion;
  }

  /**
   * @param localizacion the localizacion to set
   */
  public void setLocalizacion(String localizacion) {
    this.localizacion = localizacion;
  }

  /**
   * @return the nombreComponente
   */
  public String getNombreComponente() {
    return nombreComponente;
  }

  /**
   * @param nombreComponente the nombreComponente to set
   */
  public void setNombreComponente(String nombreComponente) {
    this.nombreComponente = nombreComponente;
  }

  /**
   * @return the perdidas
   */
  public String getPerdidas() {
    return perdidas;
  }

  /**
   * @param perdidas the perdidas to set
   */
  public void setPerdidas(String perdidas) {
    this.perdidas = perdidas;
  }

  /**
   * @return the numSuministrosFact
   */
  public String getNumSuministrosFact() {
    return numSuministrosFact;
  }

  /**
   * @param numSuministrosFact the numSuministrosFact to set
   */
  public void setNumSuministrosFact(String numSuministrosFact) {
    this.numSuministrosFact = numSuministrosFact;
  }

  public String getTipoUso() {
    return tipoUso;
  }

  public void setTipoUso(String tipoUso) {
    this.tipoUso = tipoUso;
  }
}
