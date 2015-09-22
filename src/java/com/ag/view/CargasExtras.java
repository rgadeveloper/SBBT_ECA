package com.ag.view;

import com.ag.model.CargaExtra;
import com.ag.model.CargaExtraPK;
import com.ag.model.Componente;
import com.ag.model.Tbltipo;
import com.ag.service.ArchivoManager;
import com.ag.service.BalanceManager;
import com.ag.service.SpringContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
//import java.io.*;
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
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.model.UploadedFile;

/**
 *
 * @author Larry
 */
@ManagedBean
@SessionScoped
public class CargasExtras implements Serializable {

  private String mensaje, estado;
  @ManagedProperty("#{login}")
  private Login login;
  private UploadedFile file;
  private String tipoMacroSel;
  private String transformador;
  private String macromedidor;
  private List<CargaExtra> cargaExtra;

  public UploadedFile getFile() {
    return file;
  }

  public void setFile(UploadedFile file) {
    this.file = file;
  }

  public CargasExtras() {
    mensaje = "";
    estado = "Carga";
    cargaExtra = new ArrayList();
  }

  public void limpiar() {
    file = null;
    mensaje = "";
    estado = "Carga";
    cargaExtra = new ArrayList();
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El archivo no fue cargado correctamente."));
      }
      //SpringContext context = SpringContext.getInstance();
      //ArchivoManager archivoManager = (ArchivoManager) context.getBean("ArchivoManager");
    } else {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención!", "Debe seleccionar un archivo para poder realizar la carga."));
    }
    limpiar();
  }

  private boolean copyFile(InputStream in) {
    HashMap<String, String> map = new HashMap<String, String>();
    BufferedReader br = null;
    String linea = "";
    String columnas[] = null;
    List<String[]> registros = new ArrayList<String[]>();
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
        for (int j = 0; j < 4; j++) {
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
    //Tipo_Carga-id_Componente-Periodo-Consumo
    if (ban) {
      int contRegist = 0;
      SpringContext context = SpringContext.getInstance();
      ArchivoManager archManager = (ArchivoManager) context.getBean("ArchivoManager");
      BalanceManager balance = (BalanceManager) context.getBean("BalanceManager");
      CargaExtra cargaExtraObj;
      CargaExtraPK cargaExtraPK;
      Tbltipo tipoMacro;
      Componente componente;
      cargaExtra = new ArrayList();
      for (Iterator<String[]> it = registros.iterator(); it.hasNext();) {
        try {
          cargaExtraObj = new CargaExtra();
          cargaExtraPK = new CargaExtraPK();
          tipoMacro = new Tbltipo();
          componente = new Componente();

          String[] regist = it.next();
          contRegist++;
          if (!regist[0].equals("")) {
            tipoMacro = archManager.TipoMacromedidores(regist[0]);
            if (tipoMacro instanceof Tbltipo && tipoMacro != null) {
              //verificamos que el tipo de carga a subir exista 
              cargaExtraPK.setTipoCarga(tipoMacro.getTipo());
              //cargaExtraObj.setCargaExtraPK(cargaExtraPK);
              //--continuamos ingresando el tipo de macro medidores
              //buscamos el componeten que exista
              if (!regist[1].equals("")) {
                componente = balance.getComponente(regist[1]);
                if (componente instanceof Componente && componente != null) {
                  cargaExtraPK.setIdComponente(componente.getIdComponente().toBigInteger());
                  //cargaExtraObj.setCargaExtraPK(cargaExtraPK);
                  //verificamos que sea un periodo valido
                  if (!regist[2].equals("")) {
                    if (regist[2].length() == 6) {
                      try {
                        int period = Integer.parseInt(regist[2]);
                        cargaExtraPK.setPeriodo(period);
                        cargaExtraObj.setCargaExtraPK(cargaExtraPK);
                        //--------------------------------------->>//
                        if (!regist[3].equals("")) {
                          try {
                            BigDecimal consumo = BigDecimal.ZERO;
                            regist[3] = regist[3].replace(",", ".");
                            consumo = new BigDecimal(regist[3]);
                            //
                            if (consumo.compareTo(BigDecimal.valueOf(0)) > 0) {
                              cargaExtraObj.setConsumo(consumo);
                              cargaExtraObj.setComponente(componente);
                              cargaExtraObj.setFechaModif(new Date());
                              cargaExtraObj.setPrograma("CargasExtas.xhtml");
                              cargaExtraObj.setTbltipo(tipoMacro);
                              cargaExtraObj.setUsuario(login.getUsuario());
                              cargaExtra.add(cargaExtraObj);//añadimos el objeto a la lista que se va a guardar luego de haber pasado todas las condiciones                                                            
                              //ahora si podemos añadir el objeto a la lista
                            } else {
                              mensaje += "El consumo para el registro: " + contRegist + " cuyo tipo de carga es:" + regist[0] + " y id componente es: " + regist[1] + "  debe ser mayor que cero<br>";
                            }
                          } catch (NumberFormatException e) {
                            mensaje += "El consumo para el registro: " + contRegist + " cuyo tipo de carga es:" + regist[0] + " y id componente es: " + regist[1] + "  debe ser numerico<br>";
                          } catch (Exception e) {
                            mensaje += "El consumo para el registro: " + contRegist + " cuyo tipo de carga es:" + regist[0] + " y id componente es: " + regist[1] + "  debe ser numerico<br>";
                          }
                        } else {
                          mensaje += "El consumo para el registro: " + contRegist + " cuyo tipo de carga es:" + regist[0] + " y id componente es: " + regist[1] + "  debe ser distinto de null.<br>";
                        }
                      } catch (NumberFormatException e) {
                        mensaje += "El periodo para el registro: " + contRegist + " y cuyo id componente es: " + regist[1] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[2] + "<br>";

                      } catch (Exception e) {
                        mensaje += "El periodo para el registro: " + contRegist + " y cuyo id componente es: " + regist[1] + " debe de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[2] + "<br>";
                      }
                    } else {
                      mensaje += "El periodo para el registro: " + contRegist + " y cuyo id componente es: " + regist[1] + "  debe contener 6 numeros. El tamaño actual es de: " + regist[2].length() + "<br>";
                    }
                  } else {
                    mensaje += "El periodo para el registro: " + contRegist + " y cuyo id componente es: " + regist[1] + "  debe ser diferente de null.<br>";
                  }
                } else {
                  mensaje += "El id Componente para el registro: " + contRegist + " y cuyo id componente es: " + regist[1] + "  no existe.<br>";
                }
              } else {
                mensaje += "El id Componente para el registro: " + contRegist + " y cuyo id componente es: " + regist[1] + "  debe ser diferente de null.<br>";
              }
            } else {
              mensaje += "El tipo de carga para el registro: " + contRegist + " y cuyo id componente es: " + regist[1] + "  no existe.<br>";
            }

          } else {
            mensaje += "El tipo de carga para el registro: " + contRegist + " y cuyo id componente es: " + regist[1] + "  debe ser diferente de null<br>";
          }

        } catch (NullPointerException e) {
          mensaje += "Se ha producido un error desconocido en el registro: " + contRegist + "<br>";
          Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
          mensaje += "Se ha producido un error desconocido en el registro: " + contRegist + "<br>";
          Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, e);
        }
      }
      if (cargaExtra instanceof List && cargaExtra.size() > 0) {
        //Ahora vamos a insertar los datos que pasaron todas las condiciones
        CargaExtra[] cargaExt = new CargaExtra[cargaExtra.size()];
        cargaExtra.toArray(cargaExt);
        //
        boolean guardado = archManager.cargaExtraSave(cargaExt);
        if (guardado) {
          ban = true;
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", cargaExtra.size() + " archivos guardados correctamente."));
        } else {
          ban = false;
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Fallida", cargaExtra.size() + " archivos no se pudieron guardar."));
        }
      } else {
        System.out.println("Archivo no pasó");
      }

    }

    return ban;
  }

  public void handleFileUpload(FileUploadEvent event) {
    boolean ban = true;
    FacesMessage msg;
    mensaje = "";
    try {
      ban = copyFile(event.getFile().getInputstream());
    } catch (IOException ex) {
      Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, ex);
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No se pudo cargar el archivo"));
    }

    if (ban) {
      msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje!", "Archivo cargado con éxito");
    } else {
      msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Corrija el archivo e intente nuevamente");
    }

    FacesContext.getCurrentInstance().addMessage(null, msg);
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
