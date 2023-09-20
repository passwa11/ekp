package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class EopBasedataFsscTipsTag extends TagSupport{
	
	/** 
	* serialVersionUID
	*/
	
	private static final long serialVersionUID = 6697447148000636782L;
	/**
	 * 
	 */
	
	private String id;
	

	@Override
    public void setId(String id) {
		this.id = id;
	}

	private String content;

	public void setContent(String content) {
		this.content = content;
	}


	@Override
    public int doStartTag()throws JspException{
		  try {
			  StringBuilder htmlStr=new StringBuilder();
			  htmlStr.append("<script>Com_IncludeFile('prompt_script.js','../sys/xform/designer/prompt/');Com_IncludeFile('prompt.css','../sys/xform/designer/prompt/');</script>");
			  htmlStr.append("<div id=\""+id+"\" ishiddeninmobile=\"false\" fd_type=\"prompt\" style=\"display: inline-block; width: 16px; text-align: left;\" flagid=\""+id+"\"");
			  htmlStr.append("isprint=\"add\" class=\"lui_prompt_tooltip\"><label name=\""+id+"\" class=\"lui_prompt_tooltip_drop\">");
			  htmlStr.append("<img src=\""+pageContext.getRequest().getServletContext().getContextPath()+"/sys/xform/designer/prompt/icon-prompt@2x.png\"></label>");
			  htmlStr.append("<div isdesignelement=\"false\" style=\"color: rgb(0, 0, 0); z-index: 2000; display: none;\" name=\""+id+"_content\" class=\"lui_dropdown_tooltip_menu\">");
			  htmlStr.append(content+"</div></div>");
			  pageContext.getOut().write(htmlStr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return 0;
	}
}
