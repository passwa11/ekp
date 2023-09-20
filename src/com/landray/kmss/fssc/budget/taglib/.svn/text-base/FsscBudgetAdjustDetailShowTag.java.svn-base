package com.landray.kmss.fssc.budget.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustDetailForm;
import com.landray.kmss.fssc.budget.util.FsscBudgetParseXmlUtil;

public class FsscBudgetAdjustDetailShowTag extends TagSupport{
	
	/***********************************************************
	 * @param fdSchemeId,预算方案ID
	 * @param type,title拼接表头，datumLine拼接基准行,contentLine内容行
	 * @param detailForm,调整明细表，用于拼接内容行
	 * @param method,区分是查看页面还是编辑页面
	 * @param tdIndex,对应的索引
	 * @param adjustType,预算调整类型，追加还是调整
	 ********************************************************/
	
	private static final long serialVersionUID = 7674781324723268582L;
	
	private String fdSchemeId;
	

	public void setFdSchemeId(String fdSchemeId) {
		this.fdSchemeId = fdSchemeId;
	}

	private String type;

	public void setType(String type) {
		this.type = type;
	}
	
	private FsscBudgetAdjustDetailForm detailForm;
	
	public void setDetailForm(FsscBudgetAdjustDetailForm detailForm) {
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
	
	private String adjustType;
	
	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	@Override
    public int doStartTag()throws JspException{
		  try {
			  StringBuilder htmlStr=new StringBuilder();
			//根据design-xml目录下的import.xml配置顺序读取
			  if("title".equals(type)){//拼接表头显示
				 List<String> titleList=FsscBudgetParseXmlUtil.getAdjustSchemeList("title", fdSchemeId,null,null,null,adjustType);
				for(String title:titleList){
					htmlStr.append("<td style=\"min-width:85px;\">").append(title).append("</td>");
				}
			  }else if("contentline".equals(type)){
					  List<String> contentList=FsscBudgetParseXmlUtil.getAdjustSchemeList("contentline", fdSchemeId,detailForm,method,tdIndex,adjustType);
					  for(String datum:contentList){
						htmlStr.append(datum);
					 }
			  }else if("datumline".equals(type)){
				  List<String> datumList=FsscBudgetParseXmlUtil.getAdjustSchemeList("datumline", fdSchemeId,null,null,null,adjustType);
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
