// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:23:12
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FreemarkerHelper.java

package com.jeespring.modules.gen.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.util.Map;

public class FreemarkerHelper
{

    public FreemarkerHelper()
    {
    }

    public String parseTemplate(String tplName, String encoding, Map paras)
    {
        try
        {
            StringWriter swriter = new StringWriter();
            Template mytpl = null;
            mytpl = _tplConfig.getTemplate(tplName, encoding);
            mytpl.process(paras, swriter);
            return swriter.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String parseTemplate(String tplName, Map paras)
    {
        return parseTemplate(tplName, "utf-8", paras);
    }

    private static Configuration _tplConfig;

    static 
    {
        _tplConfig = new Configuration();
        _tplConfig.setClassForTemplateLoading(FreemarkerHelper.class, "/");
    }
}
