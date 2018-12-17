package com.jeespring.common.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.jeespring.common.config.Global;
import com.jeespring.common.utils.SpringContextHolder;
import com.jeespring.modules.sys.entity.Menu;
import com.jeespring.modules.sys.utils.UserUtils;


/**
 * 
 * 类描述：菜单标签
 *
 *
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected Menu menu;//菜单Map
	
	

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
    public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	@Override
    public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			String menu = (String) this.pageContext.getSession().getAttribute("menu");
			if(menu!=null){
				out.print(menu);
			}else{
				menu=end().toString();
				out.print(menu);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		sb.append(getChildOfTree(menu,0));
		//System.out.println(sb);
		return sb;
		
	}
	
	private static String getChildOfTree(Menu parent, int level) {
		StringBuffer menuString = new StringBuffer();
		String href = "";
		if (!parent.hasPermisson()) {
            return "";
        }
		if (level > 0) {//level 为0是功能菜单
			if(parent.hasChildren())
				//menu-open
            {
                menuString.append("<li class=\"treeview\">");
            } else {
                menuString.append("<li>");
            }

			ServletContext context = SpringContextHolder
					.getBean(ServletContext.class);
			if (parent.getHref() != null && parent.getHref().length() > 0) {
				
				
				if(parent.getHref().endsWith(".html")&&!parent.getHref().endsWith("ckfinder.html")){//如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
					href = context.getContextPath() + parent.getHref();
				}
				else if(parent.getHref().contains("http://") || parent.getHref().contains("https://")){
					href = context.getContextPath() + parent.getHref();
				}
				else{
					href = context.getContextPath() + Global.getAdminPath()
					+ parent.getHref();
				}
			}
		}

		if (parent.hasChildren()) {
			if (level > 0) {
			menuString
					.append("<a title=\""+parent.getName()+"\" href=\"javascript:\" data-href=\"blank\" class=\"nav-link\" href=\""
							+ href
							+ "\"><i class=\"fa "+parent.getIcon()+"\"></i> <span class=\"nav-label\">"
							+ parent.getName()
							//+ "</span><span class=\"fa arrow\"></span></a>");
							+ "</span><span class=\"pull-right-container\"><i class=\"fa fa-angle-left pull-right\"></i></span></a>");
			}
			if (level == 1) {
				menuString.append("<ul class=\"nav nav-second-level treeview-menu\" >");
			} else if (level == 2) {
				menuString.append("<ul class=\"nav nav-third-level treeview-menu\" >");
			}else if (level == 3) {
				menuString.append("<ul class=\"nav nav-forth-level treeview-menu\" >");
			} else if (level == 4) {
				menuString.append("<ul class=\"nav nav-fifth-level treeview-menu\" >");
			}
			for (Menu child : parent.getChildren()) {
				if ("1".equals(child.getIsShow())) {
					menuString.append(getChildOfTree(child, level + 1));
				}
			}
			if (level > 0) {
			menuString.append("</ul>");
			}
		} else {
			//javascript:
			menuString.append("<a title=\""+parent.getName()+"\" class=\"nav-link\"  target=\""+parent.getTarget()+"\" href=\"" + href
					+ "\" data-href=\""+href+"\"><i class=\"fa "+parent.getIcon()+"\"></i> <span class=\"nav-label\">"+parent.getName()+"</span></a>");
		}
		if (level > 0) {
			menuString.append("</li>");
		}

		return menuString.toString();
	}
	

	

}
