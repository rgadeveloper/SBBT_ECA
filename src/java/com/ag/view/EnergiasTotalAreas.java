package com.ag.view;

import com.ag.model.EnergiaTotalAreas;
import com.ag.model.EnergiaTotalAreasPK;
import com.ag.service.ArchivoManager;
//import com.ag.service.BalanceManager;
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
 * @author RGA
 */
@ManagedBean
@SessionScoped
public class EnergiasTotalAreas implements Serializable {

  private String mensaje, estado;
  @ManagedProperty("#{login}")
  private Login login;
  private UploadedFile file = null;
  private List<EnergiaTotalAreas> energiaTotalAreas;

  public EnergiasTotalAreas() {
    mensaje = "";
    estado = "Energia";
    energiaTotalAreas = new ArrayList();
  }

  public UploadedFile getFile() {
    return file;
  }

  public void setFile(UploadedFile file) {
    this.file = file;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Login getLogin() {
    return login;
  }

  public void setLogin(Login login) {
    this.login = login;
  }

  public void limpiar() {
    file = null;
    mensaje = "";
    estado = "Energia";
    energiaTotalAreas = new ArrayList();
  }

  public void upload() throws IOException {
    if (file != null && file.getFileName() != null && file.getFileName() != "") {
      estado = "Cargado";
      mensaje = "";
      boolean ban = true;
      Object inputStream = file.getInputstream();
      ban = copyFile(file.getInputstream());//eviamos al copyfile
      if (ban) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "El archivo fue cargado correctamente."));
      } else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El archivo no fue cargado correctamente."));
      }
      //SpringContext context = SpringContext.getInstance();
      //ArchivoManager archivoManager = (ArchivoManager) context.getBean("ArchivoManager");
    }else{
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención!", "Debe seleccionar un archivo para poder realizar la carga."));
    }
    limpiar();
  }

  private boolean copyFile(InputStream in) {
    HashMap<String, String> map = new HashMap<String, String>();
    BufferedReader br = null;
    BufferedReader br1 = null;
    String linea = "";
    String columnas[] = null;
    List<String[]> registros = new ArrayList<String[]>();
    int i = 0;
    int control = 0;
    boolean ban = true;

    try {
      br = new BufferedReader(new InputStreamReader(in));
      br1 = new BufferedReader(new InputStreamReader(in));
      linea = br.readLine();
      columnas = linea.split(";");
      for (int j = 0; j < columnas.length; j++) {
        map.put("" + j, columnas[j]);
      }

      //while ((linea = br.readLine()) != null) {
//      br1 = new BufferedReader(new InputStreamReader(in));
//      linea = br1.readLine();
//      while (linea != null) {
        registros.add(linea.split(";"));
        for (int j = 0; j < 4; j++) {
          if (registros.get(i)[j].equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Por favor verifique el registro " + (i + 1) + " en la columna " + map.get("" + j));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ban = false;
            break;
          }
        }
//        if (!ban) {
//          break;
//        }
//        i++;
//      }

    } catch (Exception ex) {
      ex.printStackTrace();
      Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, ex);
      ban = false;
    }
    //periodo;energiaEntrada;energiaSalida;energiaPerdida;porcentaje
    if (ban) {
      int contRegist = 0;
      int periodo = 0;
      SpringContext context = SpringContext.getInstance();
      ArchivoManager archManager = (ArchivoManager) context.getBean("ArchivoManager");
      //BalanceManager balance = (BalanceManager) context.getBean("BalanceManager");
      EnergiaTotalAreas energiaTotalAreasObj;
      EnergiaTotalAreasPK energiaTotalAreasPK;
      energiaTotalAreas = new ArrayList();
      for (Iterator<String[]> it = registros.iterator(); it.hasNext();) {
        try {
          energiaTotalAreasObj = new EnergiaTotalAreas();
          energiaTotalAreasPK = new EnergiaTotalAreasPK();

          String[] regist = it.next();
          contRegist++;
          if (!regist[0].equals("")) {
            if (regist[0].length() == 6) {
              try {
                periodo = Integer.parseInt(regist[0]);
                energiaTotalAreasPK.setPeriodo(periodo);
                if (!regist[1].equals("")) {
                  try {
                    BigDecimal energiaTotal = BigDecimal.ZERO;
                    BigDecimal energiaEntrada = BigDecimal.ZERO;
                    BigDecimal energiaSalida = BigDecimal.ZERO;
                    BigDecimal porcentajeEnergia = BigDecimal.ZERO;
                    regist[1] = regist[1].replace(",", ".");
                    energiaTotal = new BigDecimal(regist[1]);
                    energiaEntrada = new BigDecimal(regist[2]);
                    energiaSalida = new BigDecimal(regist[3]);
                    porcentajeEnergia = new BigDecimal(regist[4]);
                    energiaTotalAreasPK.setEnergiaTotal(energiaTotal);
                    energiaTotalAreasPK.setFechaModif(new Date().getTime());
                    energiaTotalAreasObj.setEnergiaTotalAreasPK(energiaTotalAreasPK);
                    energiaTotalAreasObj.setUsuario(login.getUsuario());
                    energiaTotalAreasObj.setPrograma("energiaTotalAreas.xhtml");
                    energiaTotalAreasObj.setEnergiaEntrada(energiaEntrada);
                    energiaTotalAreasObj.setEnergiaSalida(energiaSalida);
                    energiaTotalAreasObj.setPorcentajePerdidas(porcentajeEnergia);
                    energiaTotalAreas.add(energiaTotalAreasObj);
                  } catch (NumberFormatException e) {
                    mensaje += "La energía total para el registro: " + contRegist + " debe ser de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[1] + "<br>";
                  }
                } else {
                  mensaje += "La energía total para el registro: " + contRegist + " debe ser diferente de null";
                }
              } catch (NumberFormatException e) {
                mensaje += "El periodo para el registro: " + contRegist + " debe ser de tipo numerico. Esta ingresando un periodo con caracteres especiales o letras: " + regist[0] + "<br>";
              }
            }
          } else {
            mensaje += "El periodo para el registro: " + contRegist + " debe ser diferente de null.<br>";
          }
        } catch (NullPointerException e) {
          mensaje += "Se ha producido un error desconocido en el registro: " + contRegist + "<br>";
          e.printStackTrace();
          Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
          mensaje += "Se ha producido un error desconocido en el registro: " + contRegist + "<br>";
          e.printStackTrace();
          Logger.getLogger(CargasExtras.class.getName()).log(Level.SEVERE, null, e);
        }
      }
      if (energiaTotalAreas instanceof List && energiaTotalAreas.size() > 0) {
        //Ahora vamos a insertar los datos que pasaron todas las condiciones
        EnergiaTotalAreas[] eta = new EnergiaTotalAreas[energiaTotalAreas.size()];
        energiaTotalAreas.toArray(eta);
        //
        boolean guardado = archManager.energiaTotalAreasSave(eta);
        if (guardado) {
          ban = true;
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", energiaTotalAreas.size() + " archivos guardados correctamente."));
        } else {
          ban = false;
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Fallida", energiaTotalAreas.size() + " archivos no se pudieron guardar."));
        }
      } else {
        System.out.println("Archivo no pasó");
        ban = false;
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
      Logger.getLogger(EnergiasTotalAreas.class.getName()).log(Level.SEVERE, null, ex);
      ex.printStackTrace();
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No se pudo cargar el archivo"));
    }

    if (ban) {
      msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje!", "Archivo cargado con éxito");
    } else {
      msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Corrija el archivo e intente nuevamente");
    }

    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

}
