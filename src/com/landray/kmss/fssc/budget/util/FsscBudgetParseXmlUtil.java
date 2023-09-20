package com.landray.kmss.fssc.budget.util;

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
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustDetailForm;
import com.landray.kmss.fssc.budget.forms.FsscBudgetDetailForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustDetail;
import com.landray.kmss.fssc.budget.model.FsscBudgetDetail;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class FsscBudgetParseXmlUtil{
	
	protected static IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;
	

	public static IEopBasedataBudgetSchemeService getEopBasedataBudgetSchemeService() {
		if(eopBasedataBudgetSchemeService==null){
			eopBasedataBudgetSchemeService=(IEopBasedataBudgetSchemeService) SpringBeanUtil.getBean("eopBasedataBudgetSchemeService");
		}
		return eopBasedataBudgetSchemeService;
	}
	
	protected static IFsscBudgetMainService fsscBudgetMainService;
	
	public static IFsscBudgetMainService getFsscBudgetMainService() {
		if(fsscBudgetMainService==null){
			fsscBudgetMainService=(IFsscBudgetMainService) SpringBeanUtil.getBean("fsscBudgetMainService");
		}
		return fsscBudgetMainService;
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
		String configFilePath = basePath+"/fssc/budget/design-xml/main.xml";
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
	public static List<String> getSchemeList(String type,String fdSchemeId,
			FsscBudgetDetailForm detailForm,String method,String tdIndex,String fdCompanyId) throws Exception{
		SysDataDict dataDict = SysDataDict.getInstance();
		Map<String, SysDictCommonProperty> propMap = dataDict.getModel(FsscBudgetDetail.class.getName()).getPropertyMap();
		List<String> rtnList=new ArrayList<String>();
		EopBasedataBudgetScheme eopBasedataBudgetScheme=(EopBasedataBudgetScheme) getEopBasedataBudgetSchemeService().findByPrimaryKey(fdSchemeId, null, true);
		if(eopBasedataBudgetScheme==null){
			return rtnList;
		}
		List<Map<String,String>>  propertyList=getImportProperty();
		String fdDimensions=eopBasedataBudgetScheme.getFdDimension()+";";
		String fdPeriods=eopBasedataBudgetScheme.getFdPeriod()+";";
		if(StringUtil.isNotNull(fdCompanyId)&&!FsscCommonUtil.isContain(fdPeriods, "1;", ";")){//不是无限的情况
			String rule=EopBasedataFsscUtil.getDetailPropertyValue(fdCompanyId, "fdRulePeriod");
			if(rule!=null){
				rule+=";";
				if(!"1;".equals(rule)){  //说明预算导入规则设定非全年，才需要两边交集
					String[] str=fdPeriods.split(";");
					for(String s:str){
						if(rule.indexOf(s)==-1){
							fdPeriods=fdPeriods.replace(s+";", ""); //若是方案期间有，公司期间没有，则移除该期间
						}
					}
				}
			}
		}
		//根据design-xml目录下的main.xml配置顺序读取
		if("title".equals(type)){//页面表头展现
			for(Map<String,String> map:propertyList){
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))
						||(map.containsKey("period")&&FsscCommonUtil.isContain(fdPeriods, map.get("period")+";", ";"))){//维度和期间处理
					if(map.containsKey("type")&&"model".equals(map.get("type"))){
						rtnList.add(ResourceUtil.getString(map.get("nameMessageKey"), "fssc-budget"));
					}else{
						rtnList.add(ResourceUtil.getString(map.get("messageKey"), "fssc-budget"));
					}
				}
			}
			rtnList.add(ResourceUtil.getString("fsscBudgetDetail.fdElasticPercent", "fssc-budget"));  //弹性控制单独处理
		}else if("excel".equals(type)){//导出excel模板，带编码
			for(Map<String,String> map:propertyList){
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))
						||(map.containsKey("period")&&FsscCommonUtil.isContain(fdPeriods,map.get("period"),";"))){//维度和期间处理
					if(map.containsKey("type")&&"model".equals(map.get("type"))){
						rtnList.add(ResourceUtil.getString(map.get("nameMessageKey"), "fssc-budget"));
						rtnList.add(ResourceUtil.getString(map.get("codeMessageKey"), "fssc-budget"));
					}else{
						if(map.get("property")!=null&&(map.get("property").endsWith("Rule")||map.get("property").endsWith("Apply"))){
							rtnList.add(ResourceUtil.getString(map.get("messageKey.excel"), "fssc-budget"));
						}else{
							rtnList.add(ResourceUtil.getString(map.get("messageKey"), "fssc-budget"));
						}
					}
				}
			}
			rtnList.add(ResourceUtil.getString("fsscBudgetDetail.fdElasticPercent", "fssc-budget"));  //弹性控制单独处理
		}else if("column".equals(type)){//维度和期间
			for(Map<String,String> map:propertyList){
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))
						||(map.containsKey("period")&&FsscCommonUtil.isContain(fdPeriods, map.get("period")+";", ";"))){//维度和期间处理
					rtnList.add(map.get("property"));
				}
			}
			rtnList.add("fdElasticPercent");//弹性控制单独处理
		}else if("dimension".equals(type)){//只获取维度信息字段
			for(Map<String,String> map:propertyList){
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))){//维度和期间处理
					rtnList.add(map.get("property"));
				}
			}
		}else if("datumline".equals(type)){//基准行
			for(Map<String,String> map:propertyList){
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))
						||(map.containsKey("period")&&FsscCommonUtil.isContain(fdPeriods, map.get("period")+";", ";"))){//维度和期间处理
					rtnList.add(map.get("editHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
							.replaceAll("\\{value\\}", "").replaceAll("\\{id\\}", "").replaceAll("\\{name\\}", "")
							.replaceAll("\\{subject\\}", ResourceUtil.getString(map.get("messageKey")!=null?map.get("messageKey"):map.get("nameMessageKey"), "fssc-budget")));//基准行清楚占位符
				}
			}
		}else if("contentline".equals(type)){//内容行
			StringBuilder tdHtml=new StringBuilder();
			for(Map<String,String> map:propertyList){
				String property=map.get("property");
				String propertyType=map.containsKey("type")?map.get("type"):"";
				Object id="";
				Object name="";
				if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))
						||(map.containsKey("period")&&FsscCommonUtil.isContain(fdPeriods, map.get("period")+";", ";"))){//维度和期间处理
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
							name=EnumerationTypeUtil.getColumnEnumsLabel(dict.getEnumType(), String.valueOf(name));
						}
						if(FsscNumberUtil.isNumber(name)&&name!=null){
							name=NumberUtil.roundDecimal(name, "###.00");
						}
						goral=map.get("viewHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
								.replaceAll("\\*", tdIndex).replaceAll("\\{value\\}", String.valueOf(name!=null?name:""));
					}else if("edit".equals(method)){
						if(FsscNumberUtil.isNumber(name)&&name!=null){
							name=NumberUtil.roundDecimal(name, "###.00");
						}
						goral=map.get("editHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
								.replaceAll("\\[\\*\\]", "["+tdIndex+"]").replaceAll("\\!\\{index\\}", tdIndex);
						if("model".equals(propertyType)){
							goral=goral.replaceAll("\\{id\\}", String.valueOf(id)).replaceAll("\\{name\\}",  String.valueOf(name!=null?name:""))
									.replaceAll("\\{subject\\}", ResourceUtil.getString(map.get("nameMessageKey"), "fssc-budget"));
						}else{
							goral=goral.replaceAll("\\{subject\\}", ResourceUtil.getString(map.get("messageKey"), "fssc-budget"));
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
	
	//获取预算主表配置信息
		private static List<Map<String,String>> adjustConfig;
		public static List<Map<String,String>> getAdjustProperty() throws Exception{
			if(adjustConfig==null){
				parseAdjustXmlConfig();
			}
			return adjustConfig;
		}
		
		/***********************************************************
		 * 解析预算调整字段配置信息
		 * *********************************************************/
		
		public static final void parseAdjustXmlConfig() throws Exception{
			adjustConfig=new ArrayList<Map<String,String>>();
			String basePath = ConfigLocationsUtil.getKmssConfigPath();
			String configFilePath = basePath+"/fssc/budget/design-xml/adjust.xml";
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
				adjustConfig.add(map);
			}
		}
		
		/******************************************************************
		 * @param type获取类型，不同的类型获取xml不同的属性
		 * @param fdSchemeId  对应的预算方案，不同的预算方案需要展现字段不同
		 * @param detailForm  对应明细Form，用于内容行获取字段值
		 * @param method  对应方法，edit需要是编辑状态，view是查看状态
		 * @param tdIndex  索引，拼接内容行
		 * *****************************************************************/
		public static List<String> getAdjustSchemeList(String type,String fdSchemeId,
				FsscBudgetAdjustDetailForm detailForm,String method,String tdIndex,String adjustType) throws Exception{
			SysDataDict dataDict = SysDataDict.getInstance();
			Map<String, SysDictCommonProperty> propMap = dataDict.getModel(FsscBudgetAdjustDetail.class.getName()).getPropertyMap();
			List<String> rtnList=new ArrayList<String>();
			EopBasedataBudgetScheme eopBasedataBudgetScheme=(EopBasedataBudgetScheme) getEopBasedataBudgetSchemeService().findByPrimaryKey(fdSchemeId, null, true);
			if(eopBasedataBudgetScheme==null){
				return rtnList;
			}
			List<Map<String,String>>  propertyList=getAdjustProperty();
			String fdDimensions=eopBasedataBudgetScheme.getFdDimension()+";";
			String fdPeriod=eopBasedataBudgetScheme.getFdPeriod()+";";
			//根据design-xml目录下的adjust.xml配置顺序读取
			if("title".equals(type)){//页面表头展现
				for(Map<String,String> map:propertyList){
					if((((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))
							||(map.containsKey("period")&&!FsscCommonUtil.isContain(fdPeriod, "1;", ";")))
							&&(map.containsKey("adjustType")&&map.get("adjustType").indexOf(adjustType+";")>-1))
							||(map.containsKey("fixed")&&"true".equals(map.get("fixed")))){//维度处理
						if(map.containsKey("type")&&"model".equals(map.get("type"))){
							rtnList.add(ResourceUtil.getString(map.get("nameMessageKey"), "fssc-budget"));
						}else{
							rtnList.add(ResourceUtil.getString(map.get("messageKey"), "fssc-budget"));
						}
					}
				}
			}else if("datumline".equals(type)){//基准行
				for(Map<String,String> map:propertyList){
					if((((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))
							||(map.containsKey("period")&&!FsscCommonUtil.isContain(fdPeriod, "1;", ";")))
							&&(map.containsKey("adjustType")&&map.get("adjustType").indexOf(adjustType+";")>-1))
							||(map.containsKey("fixed")&&"true".equals(map.get("fixed")))){//维度处理,包含期间，非不限，怎么假如期间字段
						rtnList.add(map.get("editHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
								.replaceAll("\\{value\\}", "").replaceAll("\\{id\\}", "").replaceAll("\\{name\\}", "")
								.replaceAll("\\{subject\\}", ResourceUtil.getString(map.get("messageKey")!=null?map.get("messageKey"):map.get("nameMessageKey"), "fssc-budget")));//基准行清楚占位符
					}
				}
			}else if("dimension".equals(type)){//只获取维度信息字段,预算调整使用
				for(Map<String,String> map:propertyList){
					if((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))){//维度和期间处理
						rtnList.add(map.get("property"));
					}
				}
			}else if("contentline".equals(type)){//内容行
				StringBuilder tdHtml=new StringBuilder();
				for(Map<String,String> map:propertyList){
					String property=map.get("property");
					String propertyType=map.containsKey("type")?map.get("type"):"";
					Object id="";
					Object name="";
					if((((map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";"))
							||(map.containsKey("period")&&!FsscCommonUtil.isContain(fdPeriod, "1;", ";")))
							&&(map.containsKey("adjustType")&&map.get("adjustType").indexOf(adjustType+";")>-1))
							||(map.containsKey("fixed")&&"true".equals(map.get("fixed")))){//维度处理
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
								name=EnumerationTypeUtil.getColumnEnumsLabel(dict.getEnumType(), String.valueOf(name));
							}
							if(FsscNumberUtil.isNumber(name)){
								//说明为数字类型或科学技术法，转换两位小数
								name=NumberUtil.roundDecimal(name, "###.00");
							}
							if(map.containsKey("period")&&name!=null&&StringUtil.isNotNull((String)name)) {//期间，显示值需要单独处理
								name=getPeriodView((String)name);
							}
							goral=map.get("viewHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
									.replaceAll("\\*", tdIndex).replaceAll("\\{value\\}", name!=null?String.valueOf(name):"");
						}else if("edit".equals(method)){
							if(FsscNumberUtil.isNumber(name)){
								//说明为数字类型或科学技术法，转换两位小数
								name=NumberUtil.roundDecimal(name, "###.00");
							}
							goral=map.get("editHtml").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
									.replaceAll("\\[\\*\\]", "["+tdIndex+"]").replaceAll("\\!\\{index\\}", tdIndex)
									.replaceAll("\\{subject\\}", ResourceUtil.getString(map.get("messageKey")!=null?map.get("messageKey"):map.get("nameMessageKey"), "fssc-budget"));
							if("model".equals(propertyType)){
								goral=goral.replaceAll("\\{id\\}", id!=null?String.valueOf(id):"").replaceAll("\\{name\\}",  name!=null?String.valueOf(name):"");
							}else{
								if("String".equals(dict.getType())&&dict.getEnumType()!=null){//枚举单独处理
									goral=goral.replaceAll("value=\""+name+"\"", "value=\""+name+"\" checked=\"true\"");
								}else{
									goral=goral.replaceAll("\\{value\\}", name!=null?String.valueOf(name):"");
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
		
		//拼接公司组、公司对应维度字段，优化部分需要使用公司组、公司维度
		public static List<Map<String,String>>  parseCompanyOrCompanyGroup(String fdDimensions) throws Exception{
			List<Map<String,String>> rtnList=new ArrayList<Map<String,String>>();
			if(FsscCommonUtil.isContain(fdDimensions, "1;", ";")){//有公司组
				Map<String,String> propertyMap=new ConcurrentHashMap<>();
				propertyMap.put("type", "model");
				propertyMap.put("codeMessageKey", "fsscBudgetDetail.fdCompanyGroup.fdCode");
				propertyMap.put("nameMessageKey", "fsscBudgetDetail.fdCompanyGroup.fdName");
				propertyMap.put("property", "fdCompanyGroup");
				propertyMap.put("dimension", "1");
				rtnList.add(propertyMap);
			}
			if(FsscCommonUtil.isContain(fdDimensions, "2;", ";")){//有公司
				Map<String,String> propertyMap=new ConcurrentHashMap<>();
				propertyMap.put("type", "model");
				propertyMap.put("codeMessageKey", "fsscBudgetDetail.fdCompany.fdCode");
				propertyMap.put("nameMessageKey", "fsscBudgetDetail.fdCompany.fdName");
				propertyMap.put("property", "fdCompany");
				propertyMap.put("dimension", "1");
				rtnList.add(propertyMap);
			}
			return rtnList;
		}
		
		//根据period值拼接显示信息
		public static String getPeriodView(String periodValue) throws Exception{
			if(StringUtil.isNull((String) periodValue)) {
				return "";
			}
			String peroidText="";
			String periodType=periodValue.substring(0, 1);
			String periodYear=periodValue.substring(1, 5);
			String period=periodValue.substring(5, 7);
			String yearText=ResourceUtil.getString("enums.budget.period.type.report.year","fssc-budget");
			String monthText=ResourceUtil.getString("enums.budget.period.type.report.month","fssc-budget");
			if("5".equals(periodType)) {
				peroidText=periodYear+yearText;
			}else if("3".equals(periodType)) {
				peroidText=periodYear+yearText+ResourceUtil.getString("period.quarter."+period,"fssc-budget");
			}else if("1".equals(periodType)) {
				peroidText=periodYear+yearText+(Integer.parseInt(period)+1)+monthText;
			}
			return peroidText;
		}
}
