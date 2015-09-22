package com.ag.view;

import com.ag.model.BalanceEnergia;
import com.ag.model.BalanceEnergiaPK;
import com.ag.model.Componente;
import com.ag.service.ArchivoManager;
import com.ag.service.BalanceManager;
import com.ag.service.SpringContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jose Mejia
 */
@ManagedBean
@SessionScoped
public class BalanceEnergiaView implements Serializable {

  /**
   * Creates a new instance of BalanceEnergiaView
   */
  private String mensaje, estado;
  @ManagedProperty("#{login}")
  private Login login;
  private UploadedFile file;
  private String tipoMacroSel;
  private String transformador;
  private String macromedidor;
  private List<BalanceEnergia> balanceEnergias;

  public UploadedFile getFile() {
    return file;
  }

  public void setFile(UploadedFile file) {
    this.file = file;
  }

  public BalanceEnergiaView() {
    mensaje = "";
    estado = "Carga";
    balanceEnergias = new ArrayList();
  }

  public void limpiar() {
    file = null;
    mensaje = "";
    estado = "Carga";
    balanceEnergias = new ArrayList();
  }

  public void upload() throws IOException {
    if (file != null && file.getFileName() != null && file.getFileName() != "") {
      estado = "Cargado";
      mensaje = "";
      boolean ban = true;
      ban = copyFile(file.getInputstream());//eviamos al copyfile            
      if (ban) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "El archivo fue cargado correctamente."));
      } else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El archivo no fue cargado correctamente"));
      }

      //SpringContext context = SpringContext.getInstance();
      //ArchivoManager archivoManager = (ArchivoManager) context.getBean("ArchivoManager");
    } else {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención!", "Debe seleccionar un archivo para poder realizar la carga."));
      limpiar();
    }
  }

  private boolean copyFile(InputStream in) {
    HashMap<String, String> map = new HashMap();
    BufferedReader br = null;
    String linea = "";
    String columnas[] = null;
    List<String[]> registros = new ArrayList();
    int i = 0;
    boolean ban = true;

    try {
      br = new BufferedReader(new InputStreamReader(in));
      columnas = br.readLine().split(";");
      for (int j = 0; j < columnas.length; j++) {
        map.put("" + j, columnas[j]);
      }

      while ((linea = br.readLine()) != null) {
        registros.add(linea.split(";"));
        for (int j = 0; j < 6; j++) {
          if (registros.get(i)[j].equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Por favor verifique el registro " + (i + 1) + " en la columna " + map.get("" + j));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ban = false;
            break;
          }
        }
        if (!ban) {
          break;
        }
        i++;
      }

    } catch (Exception ex) {
      Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, ex);
    }
    //Id_Componente:Id_comercial:Periodo:Energia de entrada:Energia de salida: Energia pérdida
    if (ban) {
      int contRegist = 0;
      SpringContext context = SpringContext.getInstance();
      ArchivoManager archManager = (ArchivoManager) context.getBean("ArchivoManager");
      BalanceManager balance = (BalanceManager) context.getBean("BalanceManager");
      Componente componente;
      String idComponente = "";
      int periodo = 0;

      balanceEnergias = new ArrayList();
      BalanceEnergiaPK pk;
      BalanceEnergia be = new BalanceEnergia();
      for (Iterator<String[]> it = registros.iterator(); it.hasNext();) {
        try {
          componente = new Componente();
          be = new BalanceEnergia();
          String[] regist = it.next();
          contRegist++;

          if (!regist[0].equals("")) {
            idComponente = balance.getAreaMt(regist[0]);
            be.setIdComercial(regist[0]);
            componente = balance.getComponente(idComponente);
            if (componente instanceof Componente && componente != null) {
              pk = new BalanceEnergiaPK();
              pk.setIdComponente(componente.getIdComponente().toBigInteger());
              //Ahora vamos a validar el periodo
              if (!regist[1].equals("")) {
                if (regist[1].length() == 6) {
                  try {
                    periodo = Integer.parseInt(regist[1]);
                    pk.setPeriodo(periodo);
                    be.setBalanceEnergiaPK(pk);
                    if (!regist[2].equals("")) {
                      try {
                        BigDecimal variable = BigDecimal.ZERO;
                        regist[2] = regist[2].replace(",", ".");
                        variable = new BigDecimal(regist[2]);
                        be.setEnergiaEnt(variable);
                        if (!regist[3].equals("")) {
                          try {
                            variable = BigDecimal.ZERO;
                            regist[3] = regist[3].replace(",", ".");
                            variable = new BigDecimal(regist[3]);
                            be.setEnergiaSal(variable);
                            if (!regist[4].equals("")) {
                              try {
                                variable = BigDecimal.ZERO;
                                regist[4] = regist[4].replace(",", ".");
                                variable = new BigDecimal(regist[4]);
                                be.setEnergiaPerdida(variable);
                                if (!regist[5].equals("")) {
                                  try {
                                    variable = BigDecimal.ZERO;
                                    regist[5] = regist[5].replace(",", ".");
                                    variable = new BigDecimal(regist[5]);
                                    be.setPorcPerdTotal(variable);
                                    be.setFechaModif(new Date());
                                    be.setPrograma("BalanceEnergia.xhtml");
                                    be.setUsuario(login.getUsuario());
                                    balanceEnergias.add(be);
                                  } catch (NumberFormatException e) {
                                    mensaje += "El porcentaje total de perdidas para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[5] + "<br>";

                                  } catch (Exception e) {
                                    mensaje += "El porcentaje total de perdidas para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[5] + "<br>";
                                  }

                                } else {
                                  mensaje += "El porcentaje total de perdidas para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + "  debe ser diferente de null.<br>";
                                }
                              } catch (NumberFormatException e) {
                                mensaje += "La energía de salida para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[4] + "<br>";

                              } catch (Exception e) {
                                mensaje += "La energía de salida para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[4] + "<br>";
                              }

                            } else {
                              mensaje += "La energía de perdida para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + "  debe ser diferente de null.<br>";
                            }

                          } catch (NumberFormatException e) {
                            mensaje += "La energía de salida para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[3] + "<br>";

                          } catch (Exception e) {
                            mensaje += "La energía de salida para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[3] + "<br>";
                          }

                        } else {
                          mensaje += "La energía de salida para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + "  debe ser diferente de null.<br>";
                        }
                      } catch (NumberFormatException e) {
                        mensaje += "La energía de entrada para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[2] + "<br>";

                      } catch (Exception e) {
                        mensaje += "La energía de entrada para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[2] + "<br>";
                      }

                    } else {
                      mensaje += "La energía de entrada para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + "  debe ser diferente de null.<br>";
                    }
                  } catch (NumberFormatException e) {
                    mensaje += "El periodo para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[1] + "<br>";

                  } catch (Exception e) {
                    mensaje += "El periodo para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[1] + "<br>";
                  }
                } else {
                  mensaje += "El periodo para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + "  debe ser un periodo valido. Ejemplo: 201403.<br>";
                }

              } else {
                mensaje += "El periodo para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + "  debe ser diferente de null.<br>";
              }
            } else {
              mensaje += "El Codigo del area para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + "  no aparece.<br>";
            }
          } else {
            mensaje += "El codigo del area para el registro: " + contRegist + " y cuyo codigo area es: " + regist[0] + "  debe ser diferente de null.<br>";
          }

        } catch (NullPointerException ex) {
          mensaje += "Se ha producido un error desconocido en el registro: " + contRegist + "<br>";
          Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, ex);
          ex.printStackTrace();
        } catch (Exception ex) {
          mensaje += "Se ha producido un error desconocido en el registro: " + contRegist + "<br>";
          Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, ex);
          ex.printStackTrace();
        }
      }
      if (balanceEnergias instanceof List && balanceEnergias.size() > 0) {
        //Ahora vamos a insertar los datos que pasaron todas las condiciones
        BalanceEnergia[] balanceEnergia = new BalanceEnergia[balanceEnergias.size()];
        balanceEnergias.toArray(balanceEnergia);
        //

        if (archManager.balanceEnergiaSave(balanceEnergia)) {
          ban = true;
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", balanceEnergias.size() + " archivos guardados correctamente."));
        } else {
          ban = false;
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Fallida", balanceEnergias.size() + " archivos no se pudieron guardar."));
        }
      } else {
        System.out.println("Archivo no pasó");
      }

    }

    return ban;
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
   * @return the mensaje
   */
  public String getMensaje() {
    return mensaje;
  }

  /**
   * @param mensaje the mensaje to set
   */
  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  /**
   * @return the estado
   */
  public String getEstado() {
    return estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(String estado) {
    this.estado = estado;
  }

  /**
   * @return the tipoMacroSel
   */
  public String getTipoMacroSel() {
    return tipoMacroSel;
  }

  /**
   * @param tipoMacroSel the tipoMacroSel to set
   */
  public void setTipoMacroSel(String tipoMacroSel) {
    this.tipoMacroSel = tipoMacroSel;
  }

  /**
   * @return the transformador
   */
  public String getTransformador() {
    return transformador;
  }

  /**
   * @param transformador the transformador to set
   */
  public void setTransformador(String transformador) {
    this.transformador = transformador;
  }

  /**
   * @return the macromedidor
   */
  public String getMacromedidor() {
    return macromedidor;
  }

  /**
   * @param macromedidor the macromedidor to set
   */
  public void setMacromedidor(String macromedidor) {
    this.macromedidor = macromedidor;
  }
}
