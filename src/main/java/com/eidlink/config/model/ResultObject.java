package com.eidlink.config.model;

import java.io.Serializable;

@SuppressWarnings("hiding")
public class ResultObject<String,T> implements Serializable{

  private static final long serialVersionUID = 114494661603453763L;

  /**
   * ÊÇ·ñ³É¹¦ £¨true/false£©
   */
  private boolean seccess;
  
  private T data;
  
  private String message;

  public boolean isSeccess() {
    return seccess;
  }

  public void setSeccess(boolean seccess) {
    this.seccess = seccess;
  }


  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  public boolean isSuccess(){
    return this.seccess;
  }
  
}
