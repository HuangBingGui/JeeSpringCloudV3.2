// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:23:01
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenUtils.java

package com.jeertd.modules.gen.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeertd.common.config.Global;
import com.jeertd.common.mapper.JaxbMapper;
import com.jeertd.common.utils.*;
import com.jeertd.modules.gen.entity.*;
import com.jeertd.modules.sys.entity.*;
import com.jeertd.modules.sys.utils.UserUtils;
import java.io.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.*;

public class GenUtils
{

    public GenUtils()
    {
    }

    public static void initColumnField(GenTable genTable)
    {
        for(Iterator iterator = genTable.getColumnList().iterator(); iterator.hasNext();)
        {
            GenTableColumn column = (GenTableColumn)iterator.next();
            if(!StringUtils.isNotBlank(column.getId()))
            {
                if(StringUtils.isBlank(column.getComments()))
                    column.setComments(column.getName());
                if(StringUtils.startsWithIgnoreCase(column.getJdbcType(), "CHAR") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "VARCHAR") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NARCHAR"))
                    column.setJavaType("String");
                else
                if(StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATETIME") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATE") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "TIMESTAMP"))
                {
                    column.setJavaType("java.util.Date");
                    column.setShowType("dateselect");
                } else
                if(StringUtils.startsWithIgnoreCase(column.getJdbcType(), "BIGINT") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NUMBER"))
                {
                    String ss[] = StringUtils.split(StringUtils.substringBetween(column.getJdbcType(), "(", ")"), ",");
                    if(ss != null && ss.length == 2 && Integer.parseInt(ss[1]) > 0)
                        column.setJavaType("Double");
                    else
                    if(ss != null && ss.length == 1 && Integer.parseInt(ss[0]) <= 10)
                        column.setJavaType("Integer");
                    else
                        column.setJavaType("Long");
                }
                column.setJavaField(StringUtils.toCamelCase(column.getName()));
                column.setIsPk(genTable.getPkList().contains(column.getName()) ? "1" : "0");
                column.setIsInsert("1");
                if(!StringUtils.equalsIgnoreCase(column.getName(), "id") && !StringUtils.equalsIgnoreCase(column.getName(), "create_by") && !StringUtils.equalsIgnoreCase(column.getName(), "create_date") && !StringUtils.equalsIgnoreCase(column.getName(), "del_flag"))
                    column.setIsEdit("1");
                else
                    column.setIsEdit("0");
                if(StringUtils.equalsIgnoreCase(column.getName(), "name") || StringUtils.equalsIgnoreCase(column.getName(), "title") || StringUtils.equalsIgnoreCase(column.getName(), "remarks") || StringUtils.equalsIgnoreCase(column.getName(), "update_date"))
                    column.setIsList("1");
                else
                    column.setIsList("0");
                if(StringUtils.equalsIgnoreCase(column.getName(), "name") || StringUtils.equalsIgnoreCase(column.getName(), "title"))
                    column.setIsQuery("1");
                else
                    column.setIsQuery("0");
                if(StringUtils.equalsIgnoreCase(column.getName(), "name") || StringUtils.equalsIgnoreCase(column.getName(), "title"))
                    column.setQueryType("like");
                else
                    column.setQueryType("=");
                if(StringUtils.startsWithIgnoreCase(column.getName(), "user_id"))
                {
                    column.setJavaType(User.class.getName());
                    column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
                    column.setShowType("userselect");
                } else
                if(StringUtils.startsWithIgnoreCase(column.getName(), "office_id"))
                {
                    column.setJavaType(Office.class.getName());
                    column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
                    column.setShowType("officeselect");
                } else
                if(StringUtils.startsWithIgnoreCase(column.getName(), "area_id"))
                {
                    column.setJavaType(Area.class.getName());
                    column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
                    column.setShowType("areaselect");
                } else
                if(StringUtils.startsWithIgnoreCase(column.getName(), "create_by") || StringUtils.startsWithIgnoreCase(column.getName(), "update_by"))
                {
                    column.setJavaType(User.class.getName());
                    column.setJavaField((new StringBuilder(String.valueOf(column.getJavaField()))).append(".id").toString());
                    column.setShowType("input");
                } else
                if(StringUtils.startsWithIgnoreCase(column.getName(), "create_date") || StringUtils.startsWithIgnoreCase(column.getName(), "update_date"))
                    column.setShowType("dateselect");
                else
                if(StringUtils.equalsIgnoreCase(column.getName(), "remarks") || StringUtils.equalsIgnoreCase(column.getName(), "content"))
                    column.setShowType("textarea");
                else
                if(StringUtils.equalsIgnoreCase(column.getName(), "parent_id"))
                {
                    column.setJavaType("This");
                    column.setJavaField("parent.id|name");
                    column.setShowType("treeselect");
                } else
                if(StringUtils.equalsIgnoreCase(column.getName(), "parent_ids"))
                {
                    column.setShowType("input");
                    column.setQueryType("like");
                } else
                if(StringUtils.equalsIgnoreCase(column.getName(), "del_flag"))
                {
                    column.setShowType("radiobox");
                    column.setDictType("del_flag");
                } else
                {
                    column.setShowType("input");
                }
            }
        }

    }

    public static String getTemplatePath()
    {
        try
        {
            File file = (new DefaultResourceLoader()).getResource("").getFile();
            if(file != null)
                return (new StringBuilder(String.valueOf(file.getAbsolutePath()))).append(File.separator).append(StringUtils.replaceEach(GenUtils.class.getName(), new String[] {
                    (new StringBuilder("util.")).append(GenUtils.class.getSimpleName()).toString(), "."
                }, new String[] {
                    "template", File.separator
                })).toString();
        }
        catch(Exception e)
        {
            logger.error("{}", e);
        }
        return "";
    }

    public static Object fileToObject(String fileName, Class clazz)
    {
        try
        {
            String pathName = (new StringBuilder("/templates/modules/gen/")).append(fileName).toString();
            Resource resource = new ClassPathResource(pathName);
            InputStream is = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            do
            {
                String line = br.readLine();
                if(line == null)
                    break;
                sb.append(line).append("\r\n");
            } while(true);
            if(is != null)
                is.close();
            if(br != null)
                br.close();
            return JaxbMapper.fromXml(sb.toString(), clazz);
        }
        catch(IOException e)
        {
            logger.warn("Error file convert: {}", e.getMessage());
        }
        return null;
    }

    public static GenConfig getConfig()
    {
        return (GenConfig)fileToObject("config.xml", GenConfig.class);
    }

    public static List getTemplateList(GenConfig config, String category, boolean isChildTable)
    {
        List templateList = (List)Lists.newArrayList();
        if(config != null && config.getCategoryList() != null && category != null)
        {
            for(Iterator iterator = config.getCategoryList().iterator(); iterator.hasNext();)
            {
                GenCategory e = (GenCategory)iterator.next();
                if(category.equals(e.getValue()))
                {
                    List list = null;
                    if(!isChildTable)
                        list = e.getTemplate();
                    else
                        list = e.getChildTableTemplate();
                    if(list != null)
                    {
                        for(Iterator iterator1 = list.iterator(); iterator1.hasNext();)
                        {
                            String s = (String)iterator1.next();
                            if(StringUtils.startsWith(s, GenCategory.CATEGORY_REF))
                            {
                                templateList.addAll(getTemplateList(config, StringUtils.replace(s, GenCategory.CATEGORY_REF, ""), false));
                            } else
                            {
                                GenTemplate template = (GenTemplate)fileToObject(s, GenTemplate.class);
                                if(template != null)
                                    templateList.add(template);
                            }
                        }

                    }
                    break;
                }
            }

        }
        return templateList;
    }

    public static Map getDataModel(GenScheme genScheme)
    {
        Map model = (Map)Maps.newHashMap();
        model.put("packageName", StringUtils.lowerCase(genScheme.getPackageName()));
        model.put("lastPackageName", StringUtils.substringAfterLast((String)model.get("packageName"), "."));
        model.put("moduleName", StringUtils.lowerCase(genScheme.getModuleName()));
        model.put("subModuleName", StringUtils.lowerCase(genScheme.getSubModuleName()));
        model.put("className", StringUtils.uncapitalize(genScheme.getGenTable().getClassName()));
        model.put("ClassName", StringUtils.capitalize(genScheme.getGenTable().getClassName()));
        model.put("functionName", genScheme.getFunctionName());
        model.put("functionNameSimple", genScheme.getFunctionNameSimple());
        model.put("functionAuthor", StringUtils.isNotBlank(genScheme.getFunctionAuthor()) ? ((Object) (genScheme.getFunctionAuthor())) : ((Object) (UserUtils.getUser().getName())));
        model.put("functionVersion", DateUtils.getDate());
        model.put("urlPrefix", (new StringBuilder()).append(model.get("moduleName")).append(StringUtils.isNotBlank(genScheme.getSubModuleName()) ? (new StringBuilder("/")).append(StringUtils.lowerCase(genScheme.getSubModuleName())).toString() : "").append("/").append(model.get("className")).toString());
        model.put("viewPrefix", model.get("urlPrefix"));
        model.put("permissionPrefix", (new StringBuilder()).append(model.get("moduleName")).append(StringUtils.isNotBlank(genScheme.getSubModuleName()) ? (new StringBuilder(":")).append(StringUtils.lowerCase(genScheme.getSubModuleName())).toString() : "").append(":").append(model.get("className")).toString());
        model.put("dbType", Global.getConfig("jdbc.type"));
        model.put("table", genScheme.getGenTable());
        return model;
    }

    public static String generateToFile(GenTemplate tpl, Map model, boolean isReplaceFile)
    {
    	String fileName = Global.getProjectPath() + File.separator + 
    		      StringUtils.replaceEach(FreeMarkers.renderString(new StringBuilder(String.valueOf(tpl.getFilePath())).append("/").toString(), model), 
    		      new String[] { "//", "/", ".." ,"."}, new String[] { File.separator, File.separator, "__",File.separator }).replace("__", "..") + 
    		      FreeMarkers.renderString(tpl.getFileName(), model);
    		    logger.debug(" fileName === " + fileName);
    		    

    		    String content = FreeMarkers.renderString(StringUtils.trimToEmpty(tpl.getContent()), model);
    		    logger.debug(" content === \r\n" + content);
    		    if (isReplaceFile) {
    		      FileUtils.deleteFile(fileName);
    		    }
    		    if (FileUtils.createFile(fileName))
    		    {
    		      FileUtils.writeToFile(fileName, content, true);
    		      logger.debug(" file create === " + fileName);
    		      return "生成成功：" + fileName + "<br/>";
    		    }
    		    logger.debug(" file extents === " + fileName);
    		    return "文件已存在：" + fileName + "<br/>";
    }

    public static void main(String args[])
    {
        try
        {
            GenConfig config = getConfig();
            System.out.println(config);
            System.out.println(JaxbMapper.toXml(config));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static Logger logger = LoggerFactory.getLogger(GenUtils.class);

}
