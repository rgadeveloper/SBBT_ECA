package com.ag.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author RGA
 */
@Named(value = "eventMenuBean")
@SessionScoped
public class EventMenuBean implements Serializable {
  
  private final String VERSION = "1.06 (16/09/2015)";

  public EventMenuBean() {
  }

  public String getVERSION() {
    return VERSION;
  }
  
}
