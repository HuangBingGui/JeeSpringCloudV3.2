// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:23:43
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenCategory.java

package com.jeespring.modules.gen.entity;

import com.jeespring.modules.sys.entity.Dict;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="category")
public class GenCategory
  extends Dict
{
  private static final long serialVersionUID = 1L;
  private List<String> template;
  private List<String> childTableTemplate;
  public static String CATEGORY_REF = "category-ref:";
  
  @XmlElement(name="template")
  public List<String> getTemplate()
  {
    return this.template;
  }
  
  public void setTemplate(List<String> template)
  {
    this.template = template;
  }
  
  @XmlElementWrapper(name="childTable")
  @XmlElement(name="template")
  public List<String> getChildTableTemplate()
  {
    return this.childTableTemplate;
  }
  
  public void setChildTableTemplate(List<String> childTableTemplate)
  {
    this.childTableTemplate = childTableTemplate;
  }
}
