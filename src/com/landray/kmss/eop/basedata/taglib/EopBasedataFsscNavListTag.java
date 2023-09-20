package com.landray.kmss.eop.basedata.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class EopBasedataFsscNavListTag extends TagSupport{
	
	/***********************************************************
	 * @param serviceName,查询数据的service
	 * @param whereBlock,对应的筛选条件
	 * @param displayName,显示名称
	 * @param url,对应的链接，会用fdId替换链接中对应的{id}
	 ********************************************************/
	private static final long serialVersionUID = -1762636703883722268L;
	
	private String modelName;
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	private String whereBlock;
	
	public void setWhereBlock(String whereBlock) {
		this.whereBlock = whereBlock;
	}
	
	private String displayName;

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	private String path;
	
	public void setPath(String path) {
		this.path = path;
	}

	private String orderBy;
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	private IBaseService getServiceImp(String serviceName) {
		return	(IBaseService) (StringUtil.isNotNull(serviceName)? SpringBeanUtil.getBean(serviceName):null);
	}

	@Override
    public int doStartTag()throws JspException{
		  if(StringUtil.isNotNull(modelName)){
			  try {
				  String model=modelName.split("\\.")[modelName.split("\\.").length-1];
				  model = model.substring(0, 1).toLowerCase() + model.substring(1);
				  String serviceName=model+"Service";
				  String selectBlock=model+".fdId"+","+model+"."+displayName;
				  String orderBlock=StringUtil.isNotNull(orderBy)?model+"."+orderBy:null;
				  List resultList=getServiceImp(serviceName).findValue(selectBlock, whereBlock, orderBlock);
				  StringBuilder navStr=new StringBuilder();
				  navStr.append("[");
				  for (int i = 0; i < resultList.size(); i++) {
					  if(i>0){
							navStr.append(",");
						 }
					  Object[] obj=(Object[]) resultList.get(i);
					  if(obj.length>1&&obj[1]!=null){
						  navStr.append("{\"text\" : \""+obj[1]+"\",");
					  }
					  if(obj.length>0&&obj[0]!=null){
						  navStr.append("\"href\" : \"javascript:budget_href('"+obj[0]+"','"+path+"')\",");
					  }
					  navStr.append("\"icon\" : \"lui_iconfont_navleft_com_all\"}");
				}
				  navStr.append("]");
				  pageContext.getOut().write(navStr.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		    return 0;
	}
}
