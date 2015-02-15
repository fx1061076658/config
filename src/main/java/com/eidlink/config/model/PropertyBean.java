package com.eidlink.config.model;

import java.io.Serializable;

public class PropertyBean implements Serializable{

  private static final long serialVersionUID = -4160272227180581540L;
  
  private String key;
  
  private Object value;
  
  private int index;
  
  

  public int getIndex() {
    return index;
  }
  public void setIndex(int index) {
    this.index = index;
  }
  public void set(String key,Object value){
    this.key = key;
    this.value = value;
  }
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
  
  
  

}
