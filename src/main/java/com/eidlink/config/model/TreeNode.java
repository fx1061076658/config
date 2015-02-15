package com.eidlink.config.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TreeNode implements Serializable{

  private static final long serialVersionUID = -6613062810712752930L;
  
  private String id;
  
  private String text;
  
  
  private String state;//ȡֵΪ open closed
  
  private Map<String,String> attributes;
  
  private List<TreeNode> children;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  public List<TreeNode> getChildren() {
    return children;
  }

  public void setChildren(List<TreeNode> children) {
    this.children = children;
  }


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

  
}
