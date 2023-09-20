package com.landray.kmss.fssc.budget.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.fssc.budget.forms.FsscBudgetDetailForm;
import com.landray.kmss.fssc.budget.util.FsscBudgetParseXmlUtil;

public class FsscBudgetDetailShowTag extends TagSupport{
	
	/***********************************************************
	 * @param fdSchemeId,预算方案ID
	 * @param type,title拼接表头，datumLine拼接基准行,contentLine内容行
	 ********************************************************/
	
	private static final long serialVersionUID = 7674781324723268582L;
	
	private String fdSchemeId;
	
	public void setFdSchemeId(String fdSchemeId) {
		this.fdSchemeId = fdSchemeId;
	}
	
	private String fdCompanyId;
	
	public void setFdCompanyId(String fdCompanyId) {
		this.fdCompanyId = fdCompanyId;
	}

	private String type;

	public void setType(String type) {
		this.type = type;
	}
	
	private FsscBudgetDetailForm detailForm;
	
	public void setDetailForm(FsscBudgetDetailForm detailForm) {
		this.detailForm = detailForm;
	}
	
	private String method;
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	private String tdIndex;
	
	public void setTdIndex(String tdIndex) {
		this.tdIndex = tdIndex;
	}

	@Override
    public int doStartTag()throws JspException{
		  try {
			  StringBuilder htmlStr=new StringBuilder();
			//根据design-xml目录下的import.xml配置顺序读取
			  if("title".equals(type)){//拼接表头显示
				 List<String> titleList=FsscBudgetParseXmlUtil.getSchemeList("title", fdSchemeId,null,null,null,fdCompanyId);
				for(String title:titleList){
					htmlStr.append("<td style=\"min-width:85px;\">").append(title).append("</td>");
				}
			  }else if("contentline".equals(type)){
					  List<String> contentList=FsscBudgetParseXmlUtil.getSchemeList("contentline", fdSchemeId,detailForm,method,tdIndex,fdCompanyId);
					  for(String datum:contentList){
						htmlStr.append(datum);
					 }
			  }else if("datumline".equals(type)){
				  List<String> datumList=FsscBudgetParseXmlUtil.getSchemeList("datumline", fdSchemeId,null,null,null,fdCompanyId);
				  for(String datum:datumList){
					htmlStr.append(datum);
				 }
			  }
			  pageContext.getOut().write(htmlStr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return 0;
	}
}
