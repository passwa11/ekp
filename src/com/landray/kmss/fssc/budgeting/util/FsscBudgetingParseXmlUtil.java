package com.landray.kmss.fssc.budgeting.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingDetailForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingDetail;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscBudgetingParseXmlUtil{
	
	protected static IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;
	

	public static IEopBasedataBudgetSchemeService getEopBasedataBudgetSchemeService() {
		if(eopBasedataBudgetSchemeService==null){
			eopBasedataBudgetSchemeService=(IEopBasedataBudgetSchemeService) SpringBeanUtil.getBean("eopBasedataBudgetSchemeService");
		}
		return eopBasedataBudgetSchemeService;
	}
	
	private static List<Map<String,String>> mainConfig;
	public static List<Map<String,String>> getImportProperty() throws Exception{
		if(mainConfig==null){
			parseImportXmlConfig();
		}
		return mainConfig;
	}

	/***********************************************************
	 * 解析预算主表字段配置信息
	 * *********************************************************/
	
	public static final void parseImportXmlConfig() throws Exception{
		mainConfig=new ArrayList<Map<String,String>>();
		String basePath = ConfigLocationsUtil.getKmssConfigPath();
		String configFilePath = basePath+"/fssc/budgeting/design-xml/budgeting.xml";
		Document document = getDocument(configFilePath);
		Element rootElement = document.getRootElement();
		List<Element> elements = rootElement.elements();
		Map<String,String> map=null;
		for(Element child:elements){
			map=new ConcurrentHashMap<String,String>();
			List<Element> e=child.elements();
			for(Element ele:e){
				map.put(ele.getName(), ele.getStringValue());
			}
			Iterator iterator=child.attributeIterator();
			while(iterator.hasNext()){
				Attribute attribute=(Attribute) iterator.next();
				map.put(attribute.getName(), attribute.getValue());
			}
			mainConfig.add(map);
		}
	}
	
	public static Document getDocument(String path) {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			File file = new File(path);
			document = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
	/******************************************************************
	 * @param type获取类型，不同的类型获取xml不同的属性
	 * @param fdSchemeId  对应的预算方案，不同的预算方案需要展现字段不同
	 * @param detailForm  对应明细Form，用于内容行获取字段值
	 * @param method  对应方法，edit需要是编辑状态，view是查看状态
	 * @param tdIndex  索引，拼接内容行
	 * *****************************************************************/
	public static List<String> getSchemeList(String type,String fdSchemeId,FsscBudgetingDetailForm detailForm,String method,String tdIndex) 
			throws Exception{
		SysDataDict dataDict = SysDataDict.getInstance();
		Map<String, SysDictCommonProperty> propMap = dataDict.getModel(FsscBudgetingDetail.class.getName()).getPropertyMap();
		List<String> rtnList=new ArrayList<String>();
		EopBasedataBudgetScheme eopBasedataBudgetScheme=(EopBasedataBudgetScheme) getEopBasedataBudgetSchemeService().findByPrimaryKey(fdSchemeId, null, true);
		if(eopBasedataBudgetScheme==null){
			return rtnList;
		}
		List<Map<String,String>>  propertyList=getImportProperty();
		String fdDimensions=eopBasedataBudgetScheme.getFdDimension()+";";
		//根据design-xml目录下的main.xml配置顺序读取
		if("title".equals(type)){//页面表头展现
			for(Map<String,String> map:propertyList){
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))){//维度和期间处理
					if(map.containsKey("type")&&"model".equals(map.get("type"))){
						rtnList.add(ResourceUtil.getString(map.get("nameMessageKey"), "fssc-budgeting"));
					}else{
						rtnList.add(ResourceUtil.getString(map.get("messageKey"), "fssc-budgeting"));
					}
				}
			}
		}else if("column".equals(type)){//维度和期间
			for(Map<String,String> map:propertyList){
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))){//维度和期间处理
					rtnList.add(map.get("property"));
				}
			}
			rtnList.add("fdElasticPercent");//弹性控制单独处理
		}else if("datumline".equals(type)){//基准行
			for(Map<String,String> map:propertyList){
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))){//维度和期间处理
					rtnList.add(map.get("editHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
							.replaceAll("\\{value\\}", "").replaceAll("\\{id\\}", "").replaceAll("\\{name\\}", "")
							.replaceAll("\\{subject\\}", ResourceUtil.getString(map.get("messageKey")!=null?map.get("messageKey"):map.get("nameMessageKey"), "fssc-budgeting")));//基准行清楚占位符
				}
			}
		}else if("contentline".equals(type)){//内容行
			StringBuilder tdHtml=new StringBuilder();
			for(Map<String,String> map:propertyList){
				String property=map.get("property");
				String propertyType=map.containsKey("type")?map.get("type"):"";
				Object id="";
				Object name="";
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))){//维度和期间处理
					if("model".equals(propertyType)&&propMap.containsKey(property)){
						id=PropertyUtils.getProperty(detailForm, property+"Id");
						name=PropertyUtils.getProperty(detailForm,property+"Name");
					}else if("simaple".equals(propertyType)){
						name=PropertyUtils.getProperty(detailForm, property);
					}
					String goral="";
					SysDictCommonProperty dict=propMap.get(property);
					if("view".equals(method)){
						if(dict!=null&&"String".equals(dict.getType())&&dict.getEnumType()!=null){//枚举单独处理
							name=EnumerationTypeUtil.getColumnEnumsLabel("fssc_budget_rule", String.valueOf(name));
						}
						goral=map.get("viewHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
								.replaceAll("\\[\\*\\]", "["+tdIndex+"]").replaceAll("\\{value\\}", String.valueOf(name!=null?name:""))
								.replaceAll("\\{id\\}", String.valueOf(id!=null?id:""));
					}else if("edit".equals(method)){
						goral=map.get("editHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
								.replaceAll("\\[\\*\\]", "["+tdIndex+"]").replaceAll("\\!\\{index\\}", tdIndex);
						if("model".equals(propertyType)){
							goral=goral.replaceAll("\\{id\\}", String.valueOf(id!=null?id:"")).replaceAll("\\{name\\}",  String.valueOf(name!=null?name:""))
									.replaceAll("\\{subject\\}", ResourceUtil.getString(map.get("nameMessageKey"), "fssc-budgeting"));
						}else{
							goral=goral.replaceAll("\\{subject\\}", ResourceUtil.getString(map.get("messageKey"), "fssc-budgeting"));
							if("String".equals(dict.getType())&&dict.getEnumType()!=null){//枚举单独处理
								goral=goral.replaceAll("value=\""+name+"\"", "value=\""+name+"\" checked=\"true\"");
							}else{
								goral=goral.replaceAll("\\{value\\}", String.valueOf(name!=null?name:""));
							}
						}
					}
					tdHtml.append(goral);
				}
			}
			rtnList.add(tdHtml.toString());
		}
		return rtnList;
	}
}
