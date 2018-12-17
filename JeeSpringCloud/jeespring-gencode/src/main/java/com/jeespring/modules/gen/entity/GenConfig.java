// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:23:48
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenConfig.java

package com.jeespring.modules.gen.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.jeespring.modules.sys.entity.Dict;

@XmlRootElement(name="config")
public class GenConfig
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private List<GenCategory> categoryList;
  private List<Dict> javaTypeList;
  private List<Dict> queryTypeList;
  private List<Dict> showTypeList;
  
  @XmlElementWrapper(name="category")
  @XmlElement(name="category")
  public List<GenCategory> getCategoryList()
  {
    return this.categoryList;
  }
  
  public void setCategoryList(List<GenCategory> categoryList)
  {
    this.categoryList = categoryList;
  }
  
  @XmlElementWrapper(name="javaType")
  @XmlElement(name="dict")
  public List<Dict> getJavaTypeList()
  {
    return this.javaTypeList;
  }
  
  public void setJavaTypeList(List<Dict> javaTypeList)
  {
    this.javaTypeList = javaTypeList;
  }
  
  @XmlElementWrapper(name="queryType")
  @XmlElement(name="dict")
  public List<Dict> getQueryTypeList()
  {
    return this.queryTypeList;
  }
  
  public void setQueryTypeList(List<Dict> queryTypeList)
  {
    this.queryTypeList = queryTypeList;
  }
  
  @XmlElementWrapper(name="showType")
  @XmlElement(name="dict")
  public List<Dict> getShowTypeList()
  {
    return this.showTypeList;
  }
  
  public void setShowTypeList(List<Dict> showTypeList)
  {
    this.showTypeList = showTypeList;
  }
}
