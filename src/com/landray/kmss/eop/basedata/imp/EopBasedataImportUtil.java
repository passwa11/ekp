package com.landray.kmss.eop.basedata.imp;

import com.landray.kmss.common.dao.HQLBuilderImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportColumn;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportReference;
import com.landray.kmss.eop.basedata.imp.interfaces.IEopBasedataImportValidator;
import com.landray.kmss.eop.basedata.imp.validator.EopBasedataValidateContext;
import com.landray.kmss.eop.basedata.imp.validator.EopBasedataValidatorUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.upload.FormFile;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.query.Query;
import org.xml.sax.SAXException;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class EopBasedataImportUtil {
	private static IBaseDao baseDao;
	public static IBaseDao getBaseDao(){
		if(baseDao==null){
			baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		}
		return baseDao;
	}
	private static Map<String,EopBasedataImportContext> config;
	public static EopBasedataImportContext getImportContext(String modelName) throws Exception{
		//由于涉及开关设置，缓存只有重启才会重新加载，所以直接重新加载
		parseConfig(modelName);
		return config.get(modelName);
	}

	private static synchronized void parseConfig(String modelName) throws Exception{
		config = new HashMap<String,EopBasedataImportContext>();
		String basePath = ConfigLocationsUtil.getWebContentPath();
		String[] dirs = ConfigLocationsUtil.getConfigLocationArray(
				basePath, "import.xml", basePath);
		for (int i = 0; i < dirs.length; i++) {
			//防止文件重名
			if(dirs[i].indexOf("design-xml")==-1||dirs[i].indexOf("fssc/base/")>-1){  //排除fssc/base，不然会影响eop/basedata基础数据导入
				continue;
			}
			Document document = getDocument(dirs[i]);
			Element rootElement = document.getRootElement();
			List<Element> elements = rootElement.elements();
			EopBasedataImportContext context = null;
			String fdModelName="";
			for(Element childrenLV1:elements){
				fdModelName=childrenLV1.attributeValue("name");
				if(modelName.equals(fdModelName)) {
					context = new EopBasedataImportContext();
					context.setFdModelName(childrenLV1.attributeValue("name"));
					context.setFdDict(SysDataDict.getInstance().getModel(context.getFdModelName()));
					context.setFdKeyColumn(childrenLV1.attributeValue("key-col"));
					context.setFdColumns(parseColumns(childrenLV1,context));
					config.put(context.getFdModelName(), context);
					break;
				}
			}
		}
	}

	private static List<EopBasedataImportColumn> parseColumns(Element node, EopBasedataImportContext context) throws Exception {
		List<EopBasedataImportColumn> cols = new ArrayList<EopBasedataImportColumn>();
		EopBasedataImportColumn col = null;
		List<Element> elements = node.elements();
		int i=0;
		for(Element childrenLV1:elements){
			if(childrenLV1.attribute("switch-field")!=null) {
				String switchField = (String) childrenLV1.attribute("switch-field").getData();
				if(StringUtil.isNotNull(switchField)){ //需要根据开关判断
					Boolean isContinue=false;
					String field=switchField.split(":")[0];
					String judgeValue=switchField.split(":")[1];
					String value = EopBasedataFsscUtil.getSwitchValue(field);
					if(judgeValue.indexOf("|")>-1) {
						String[] judges=judgeValue.split("\\|");
						for(String judge:judges) {
							if(EopBasedataFsscUtil.isContain(value, judge, ";")) {//满足条件
								isContinue=true;  //或只要一个满足条件，跳出循环判断
								break;
							}
						}
					}else if(judgeValue.indexOf("&")>-1) {
						String[] judges=judgeValue.split("\\&");
						for(String judge:judges) {
							if(!EopBasedataFsscUtil.isContain(value, judge, ";")) {//或只要有一个不满足条件，跳出循环判断
								isContinue=false;
								break;
							}
						}
					}else {
						isContinue=EopBasedataFsscUtil.isContain(value, judgeValue, ";");
					}
					if(!isContinue){
						continue;
					}
				}
			}
			//根据模块判断字段是否需要导入导出
			if(childrenLV1.attribute("ifModuleExist")!=null) {
				String ifModuleExist = (String) childrenLV1.attribute("ifModuleExist").getData();
				if(null==SysConfigs.getInstance().getModule(ifModuleExist)){ //判断模块是否存在
					continue;
				}
			}
			col = new EopBasedataImportColumn();
			col.setFdColumn(++i);  //从1开始，因为导入，导出序号在0
			col.setFdName((String) childrenLV1.attribute("property").getData());
			col.setFdType((String) childrenLV1.attribute("type").getData());
			col.setFdRel(parseRelation(childrenLV1));
			col.setFdValidators(parseValidators(childrenLV1));
			if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_OBJECT.equals(col.getFdType()) ||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_UNUSED.equals(col.getFdType())
					|| EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_LIST.equals(col.getFdType())) {
				try {
					Class.forName(col.getFdRel().getFdReference());
				} catch (Exception e) {
					continue;
				}
			}
			for(SysDictCommonProperty prop:context.getFdDict().getPropertyList()){
				String fdName = col.getFdName();
				if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_UNUSED.equals(col.getFdType())){
					fdName = fdName.split("\\.")[0];
				}
				if(prop.getName().equals(fdName)){
					col.setFdProperty(prop);
				}
			}
			cols.add(col);
		}
		return cols;
	}

	private static List<EopBasedataValidateContext> parseValidators(Element node) {
		List<Element> elements = node.elements();
		List<EopBasedataValidateContext> validators = new ArrayList<EopBasedataValidateContext>();
		for(Element childrenLV1:elements){
			if("validator".equals(childrenLV1.getName())){
				String name = (String) childrenLV1.attribute("rel").getData();
				validators.add(EopBasedataValidatorUtil.getContext(name));
			}
		}
		return validators;
	}

	private static EopBasedataImportReference parseRelation(Element node) {
		List<Element> elements = node.elements();
		EopBasedataImportReference rel = null;
		for(Element childrenLV1:elements){
			if("rel".equals(childrenLV1.getName())){
				rel = new EopBasedataImportReference();
				if(childrenLV1.attribute("foreign")!=null){
					rel.setFdForeign((String) childrenLV1.attribute("foreign").getData());
				}
				rel.setFdReference((String) childrenLV1.attribute("ref").getData());
				rel.setFdType((String) childrenLV1.attribute("type").getData());
				parseRelConfig(rel,childrenLV1);
			}
		}
		return rel;
	}

	private static void parseRelConfig(EopBasedataImportReference rel, Element node) {
		List<Element> elements = node.elements();
		List<Map<String,Object>> refMap = new ArrayList<Map<String,Object>>();
		Map<String,Object> ref = null;
		for(Element childrenLV1:elements){
			if("key".equals(childrenLV1.getName())){
				rel.setFdKey((String) childrenLV1.attribute("name").getData());
			}
			if("field".equals(childrenLV1.getName())){
				ref = new HashMap<String,Object>();
				ref.put("name", (String) childrenLV1.attribute("name").getData());
				ref.put("type", (String) childrenLV1.attribute("type").getData());
				if(childrenLV1.attribute("rel-column")!=null){
					ref.put("rel-column", (String) childrenLV1.attribute("rel-column").getData());
				}
				if(childrenLV1.attribute("data-type")!=null){
					ref.put("data-type", (String) childrenLV1.attribute("data-type").getData());
				}
				if(childrenLV1.attribute("value")!=null&&ref.get("data-type")!=null){
					ref.put("value", getValueByType((String) childrenLV1.attribute("value").getData(),(String)ref.get("data-type")));
				}
				refMap.add(ref);
			}
		}
		rel.setFdFields(refMap);
	}

	private static Object getValueByType(String value, String type) {
		if("String".equals(type)){
			return String.valueOf(value);
		}
		if("Boolean".equals(type)){
			return Boolean.valueOf((String)value);
		}
		if("Integer".equals(type)){
			return Integer.valueOf((String)value);
		}
		if("Double".equals(type)){
			return Double.valueOf((String)value);
		}
		return null;
	}

	public static Document getDocument(String path) {
		SAXReader saxReader = new SAXReader();
		try {
			saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document document = null;
		try {
			File file = new File(path);
			document = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	public static List<EopBasedataImportMessage> validateData(List<List<Object>> dataList,String modelName,EopBasedataImportContext context) throws Exception {
		List<EopBasedataImportMessage> messages = new ArrayList<EopBasedataImportMessage>();
		EopBasedataImportMessage msg = null;
		for(int i=0;i<dataList.size();i++){
			msg = new EopBasedataImportMessage();
			for(EopBasedataImportColumn col:context.getFdColumns()){
				String switchField = col.getFdSwitchField();
				if(StringUtil.isNotNull(switchField)){ //需要根据开关判断
					String field=switchField.split(":")[0];
					String judgeValue=switchField.split(":")[1];
					String value = EopBasedataFsscUtil.getSwitchValue(field);
					if(!EopBasedataFsscUtil.isContain(value, judgeValue, ";")){
						continue;
					}
				}
				for(EopBasedataValidateContext ctx:col.getFdValidators()){
					IEopBasedataImportValidator validator = (IEopBasedataImportValidator) SpringBeanUtil.getBean(ctx.getFdBean());
					if(!validator.validate(context,col,dataList.get(i))){
						String msgKey = col.getFdProperty().getMessageKey();
						msgKey = ResourceUtil.getString(msgKey.split(":")[1], msgKey.split(":")[0]);
						msg.addMoreMsg(ctx.getReplacedString(msgKey,dataList.get(i).get(col.getFdColumn()).toString()));
					}
				}
			}
			if(!ArrayUtil.isEmpty(msg.getMoreMessages())){
				msg.addFailMsg(ResourceUtil.getString("message.validation.rowNum","eop-basedata").replace("{0}", String.valueOf(i+1)));
				messages.add(msg);
			}
		}
		return messages;
	}

	public static List<EopBasedataImportMessage> parseData(List<List<Object>> dataList, List<IBaseModel> addList,
								 List<IBaseModel> updateList,String modelName,EopBasedataImportContext context) throws Exception {
		List<EopBasedataImportMessage> messages = new ArrayList<EopBasedataImportMessage>();
		EopBasedataImportMessage msg=new EopBasedataImportMessage();
		List<IBaseModel> models = null;
		EopBasedataImportColumn col = null;
		List<IBaseModel> curList = new ArrayList<IBaseModel>();
		ArrayUtil.concatTwoList(addList, curList);
		ArrayUtil.concatTwoList(updateList, curList);
		for(int i=0;i<dataList.size();i++){
			List<Object> rowData = dataList.get(i);
			models = getModel(addList,updateList,modelName,rowData,context);
			if(models.size()==1) {
				for (IBaseModel model : models) {
					for (int k = 0; k < rowData.size(); k++) {
						col = context.getColumnByCol(k);
						if (col == null) {
							continue;
						}
						String methodName = "set" + col.getFdType() + "Value";
						Method method = EopBasedataImportUtil.class.getDeclaredMethod(methodName, EopBasedataImportContext.class, String.class, List.class, IBaseModel.class);
						method.setAccessible(true);
						method.invoke(null, context, col.getFdName(), rowData, model);
					}
				}
			}else if(models.size()>1){	//一条Excel数据不允许更新多条基础数据
				msg.addMoreMsg(ResourceUtil.getString("message.validation.more","eop-basedata").replace("{0}", String.valueOf(i+1)).replace("{1}",  String.valueOf(models.size())));
			}
		}
		if(!ArrayUtil.isEmpty(msg.getMoreMessages())){
			msg.addFailMsg(ResourceUtil.getString("message.validation.dataNum","eop-basedata"));
			messages.add(msg);
		}
		return messages;
	}
	@SuppressWarnings("unused")
	private static void setUnusedValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		return;
	}
	@SuppressWarnings("unused")
	private static void setStringValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		BeanUtils.setProperty(model, propertyName, rowData.get(context.getColumnByProperty(propertyName).getFdColumn()));
	}
	@SuppressWarnings("unused")
	private static void setBooleanValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		String value = (String) rowData.get(context.getColumnByProperty(propertyName).getFdColumn());
		if(StringUtil.isNull(value)) {
			return;
		}
		BeanUtils.setProperty(model, propertyName, value);
	}
	@SuppressWarnings("unused")
	private static void setDoubleValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		String value = (String) rowData.get(context.getColumnByProperty(propertyName).getFdColumn());
		if(StringUtil.isNull(value)) {
			return;
		}
		BeanUtils.setProperty(model, propertyName, value);
	}
	@SuppressWarnings("unused")
	private static void setIntegerValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		String value = (String) rowData.get(context.getColumnByProperty(propertyName).getFdColumn());
		if(StringUtil.isNull(value)) {
			return;
		}
		BeanUtils.setProperty(model, propertyName, Integer.valueOf(value));
	}
	@SuppressWarnings("unused")
	private static void setDateValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		String value = (String) rowData.get(context.getColumnByProperty(propertyName).getFdColumn());
		if(StringUtil.isNull(value)) {
			return;
		}
		BeanUtils.setProperty(model, propertyName, DateUtil.convertStringToDate(value, DateUtil.PATTERN_DATE));
	}
	private static void setEnumsValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		EopBasedataImportColumn col = context.getColumnByProperty(propertyName);
		String value = (String) rowData.get(col.getFdColumn());
		SysDictCommonProperty property = col.getFdProperty();
		List<String> enumsTypes = new ArrayList<String>();
		for(String enu:value.split(";")){
			if(StringUtil.isNull(enu)){
				continue;
			}
			enumsTypes.add(EnumerationTypeUtil.getColumnValueByLabel(property.getEnumType(), enu));
		}
		BeanUtils.setProperty(model, propertyName, StringUtil.join(enumsTypes, ";"));
	}
	@SuppressWarnings("unused")
	private static void setRadioValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		setEnumsValue(context, propertyName, rowData, model);
	}
	@SuppressWarnings("unused")
	private static void setListValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		EopBasedataImportColumn col = context.getColumnByProperty(propertyName);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(context.getFdModelName());
		hqlInfo.setFromBlock(col.getFdRel().getFdReference()+" model");
		hqlInfo.setSelectBlock("model");
		getRelHqlInfo(col, hqlInfo,  rowData,model);
		String hql = new HQLBuilderImp().buildQueryHQL(hqlInfo).getHql();
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		for(HQLParameter param:hqlInfo.getParameterList()){
			if(param.getValue() instanceof Collection){
				query.setParameterList(param.getName(), (Collection) param.getValue());
			}else{
				query.setParameter(param.getName(), param.getValue());
			}
		}
		BeanUtils.setProperty(model, propertyName, query.list());
	}
	@SuppressWarnings("unused")
	private static void setObjectValue(EopBasedataImportContext context,String propertyName,List<Object> rowData,IBaseModel model) throws Exception{
		EopBasedataImportColumn col = context.getColumnByProperty(propertyName);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(context.getFdModelName());
		hqlInfo.setFromBlock(col.getFdRel().getFdReference()+" model");
		hqlInfo.setSelectBlock("model");
		getRelHqlInfo(col, hqlInfo,  rowData,model);
		String hql = new HQLBuilderImp().buildQueryHQL(hqlInfo).getHql();
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		for(HQLParameter param:hqlInfo.getParameterList()){
			if(param.getValue() instanceof Collection){
				query.setParameterList(param.getName(), (Collection) param.getValue());
			}else{
				query.setParameter(param.getName(), param.getValue());
			}
		}
		query.setMaxResults(1);
		IBaseModel relModel=(IBaseModel) query.uniqueResult();
		if(EopBasedataImportReference.REL_TYPE_WEAK.equals(col.getFdRel().getFdType())){
			if(null != relModel) {
				String id = (String) PropertyUtils.getProperty(relModel, col.getFdRel().getFdKey());
				BeanUtils.setProperty(model, propertyName, id);
				EopBasedataImportColumn c = getUnuseColumn(context, col);
				if(null != c) {
					String name = (String) PropertyUtils.getProperty(relModel, c.getFdRel().getFdKey());
					BeanUtils.setProperty(model, c.getFdName(), name);
				}
			}
		}else {
			BeanUtils.setProperty(model, propertyName, relModel);
		}
	}

	private static EopBasedataImportColumn getUnuseColumn(EopBasedataImportContext context,EopBasedataImportColumn col) {
		for(EopBasedataImportColumn c:context.getFdColumns()) {
			if(!EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_UNUSED.equals(c.getFdType())) {
				continue;
			}
			if(c.getFdRel().getFdReference().equals(col.getFdRel().getFdReference())) {
				return c;
			}
		}
		return null;
	}

	private static List<IBaseModel> getModel(List<IBaseModel> addList, List<IBaseModel> updateList, String modelName,List<Object> rowData,EopBasedataImportContext context) throws Exception {
		String[] fdKeyColumn = context.getFdKeyColumn().split(";");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(modelName);
		hqlInfo.setFromBlock(modelName+" model");
		hqlInfo.setSelectBlock("model");
		StringBuffer where = new StringBuffer();
		where.append(" 1=1");
		EopBasedataImportColumn col = null;
		Map<String,Integer> index = new HashMap<String,Integer>();
		index.put("paramIndex", 0);
		index.put("modelIndex", 0);
		for(String key:fdKeyColumn){
			col = context.getColumnByProperty(key);
			if(col!=null) {
				getHQLInfo(context, col, hqlInfo, key, index, rowData);
			}
		}
		String hql = new HQLBuilderImp().buildQueryHQL(hqlInfo).getHql();
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		for(HQLParameter param:hqlInfo.getParameterList()){
			if(param.getValue() instanceof Collection){
				query.setParameterList(param.getName(), (Collection) param.getValue());
			}else{
				query.setParameter(param.getName(), param.getValue());
			}
		}
		List<IBaseModel> models = query.list();
		if(ArrayUtil.isEmpty(models)){
			models = new ArrayList<IBaseModel>();
			IBaseModel model = (IBaseModel) Class.forName(modelName).newInstance();
			models.add(model);
			addList.add(model);
		}else if(models.size()==1){
			EopBasedataImportColumn companyColumn=context.getColumnByProperty("fdCompanyList");
			if(companyColumn!=null&&rowData.get(companyColumn.getFdColumn())!=null) {
				List companyList=new ArrayList();
				for(IBaseModel model:models) {
					if(PropertyUtils.isWriteable(model, "fdCompanyList")) {
						companyList.addAll((List)PropertyUtils.getProperty(model, "fdCompanyList"));
					}
				}
				int len=ArrayUtil.isEmpty(companyList)?1:companyList.size();
				String[] dbData=new String[len];
				if(!ArrayUtil.isEmpty(companyList)) {
					dbData=ArrayUtil.joinProperty(companyList, "fdCode", ";", Boolean.TRUE)[0].split(";");
				}
				String[] vals = ((String) rowData.get(companyColumn.getFdColumn())).split(";");
				if(ArrayUtil.isArrayIntersect(dbData, vals)||ArrayUtil.isEmpty(companyList)
					|| StringUtil.isNull((String) rowData.get(companyColumn.getFdColumn()))) {//数据库数据和excel数据公司有交集，更新。或者存在的数据公司为空（公共），或者excel公司为空，更新
					updateList.addAll(models);
				}else {//数据库数据和excel数据公司无交集，新增
					models = new ArrayList<IBaseModel>();
					IBaseModel model = (IBaseModel) Class.forName(modelName).newInstance();
					models.add(model);
					addList.add(model);
				}
			}else {//不包含公司，以编码为唯一性
				updateList.addAll(models);
			}
		}
		return models;
	}


	public static void getRelHqlInfo(EopBasedataImportColumn col,HQLInfo hqlInfo,List<Object> rowData,IBaseModel model) throws Exception{
		EopBasedataImportReference rel = col.getFdRel();
		StringBuffer where = new StringBuffer();
		if(StringUtil.isNotNull(hqlInfo.getWhereBlock())){
			where.append(hqlInfo.getWhereBlock());
		}else{
			where.append("1=1 ");
		}
		int paramIndex = 0;
		if(StringUtil.isNotNull((String)rowData.get(col.getFdColumn()))){
			List<String> data = new ArrayList<String>();
			for(String str:((String)rowData.get(col.getFdColumn())).split(";")){
				if(StringUtil.isNotNull(str)){
					data.add(str);
				}
			}
			if(ArrayUtil.isEmpty(data)){
				where.append("and 1=2 ");
			}else{
				String key=rel.getFdKey();
				if(key.indexOf(";")>-1){//多个匹配条件
					String[] keys=key.split(";");
					int pIndex=paramIndex++;
					for(int i=0,len=keys.length;i<len;i++){
						if(i==0){
							where.append(" and (");
						}else{
							where.append(" or ");
						}
						where.append(" model.").append(keys[i]).append(" in(:param").append(pIndex).append(") ");
						if(i==len-1){
							where.append(" )");
						}
					}
					hqlInfo.setParameter("param"+pIndex,data);
				}else{
					where.append("and model.").append(rel.getFdKey()).append(" in(:param").append(++paramIndex).append(") ");
					hqlInfo.setParameter("param"+paramIndex,data);
				}
			}
		}else{
			where.append("and 1=2 ");
		}
		//如果有额外条件
		if(rel.getFdFields()!=null){
			for(Map<String,Object> field:rel.getFdFields()){
				if(EopBasedataImportReference.FIELD_TYPE_STATIC.equals(field.get("type"))){
					where.append(" and model.").append(field.get("name")).append("=:param").append(++paramIndex);
					hqlInfo.setParameter("param"+paramIndex,field.get("value"));
				}else if(EopBasedataImportReference.FIELD_TYPE_REF.equals(field.get("type"))){
					String relField = (String) field.get("rel-column");
					EopBasedataImportContext ctx = EopBasedataImportUtil.getImportContext(rel.getFdReference());
					EopBasedataImportColumn col_ = ctx.getColumnByProperty(relField);//引用对象的字段
					if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_LIST.equals(col_.getFdType())) {//多选字段，需要设置join
						hqlInfo.setJoinBlock("left join model."+relField+" relField ");
						if("fdCompanyList".equals(col_.getFdName())) {//公司，需要查询公共的数据
							where.append(" and (relField.").append(field.get("name")).append(" in(:param").append(++paramIndex).append(") or relField.").append(field.get("name")).append(" is null)");
						}else {
							where.append(" and relField.").append(field.get("name")).append(" in(:param").append(++paramIndex).append(")");
						}
					}else {
						where.append(" and model.").append(field.get("rel-column")).append(".").append(field.get("name")).append(" in(:param").append(++paramIndex).append(")");
					}
					String val = (String) rowData.get(col_.getFdColumn());
					hqlInfo.setParameter("param"+paramIndex,Arrays.asList(val.split(";")));
				}
			}
		}
		hqlInfo.setWhereBlock(where.toString());
	}
	public static void getHQLInfo(EopBasedataImportContext context,EopBasedataImportColumn col,HQLInfo hqlInfo,String property,Map<String,Integer> index,List<Object> rowData) throws Exception{
		if("fdParent".equals(property)){
			property = "hbmParent";
		}
		StringBuffer where = new StringBuffer();
		StringBuffer joinBlock = new StringBuffer(StringUtil.isNotNull(hqlInfo.getJoinBlock())?hqlInfo.getJoinBlock():"");
		if(StringUtil.isNotNull(hqlInfo.getWhereBlock())){
			where.append(hqlInfo.getWhereBlock());
		}else{
			where.append("1=1 ");
		}
		Integer modelIndex = index.get("modelIndex"),paramIndex = index.get("paramIndex");
		//对象类型
		if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_OBJECT.equals(col.getFdType())||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_LIST.equals(col.getFdType())){
			EopBasedataImportReference rel = col.getFdRel();
			if(EopBasedataImportReference.REL_TYPE_WEAK.equals(rel.getFdType())){
				String relModel = " ref"+(++modelIndex);
				hqlInfo.setJoinBlock(","+rel.getFdReference()+relModel);
				String value = (String)rowData.get(col.getFdColumn());
				//如果没有填写该字段
				if(StringUtil.isNull(value)){
					where.append(" and ").append(relModel).append(".fdId is null");
				}else{
					where.append(" and ").append(relModel).append(".").append(rel.getFdKey()).append(" in(:param").append(++paramIndex).append(")");
					where.append(" and model.").append(property).append(" = ").append(relModel).append(".").append(rel.getFdForeign());
					hqlInfo.setParameter("param"+paramIndex,Arrays.asList(((String)rowData.get(col.getFdColumn())).split(";")));
					//如果有额外条件
					if(rel.getFdFields()!=null){
						for(Map<String,Object> field:rel.getFdFields()){
							if(EopBasedataImportReference.FIELD_TYPE_STATIC.equals(field.get("type"))){
								where.append(" and ").append(relModel).append(".").append(field.get("name")).append("=:param").append(++paramIndex);
								hqlInfo.setParameter("param"+paramIndex,field.get("value"));
							}else if(EopBasedataImportReference.FIELD_TYPE_REF.equals(rel.getFdType())){
								where.append(" and ").append(relModel).append(".").append(field.get("rel-column")).append(".").append(field.get("name")).append(" in(:param").append(++paramIndex).append(")");
								String val = (String) rowData.get(context.getColumnByProperty((String) field.get("rel-column")).getFdColumn());
								hqlInfo.setParameter("param"+paramIndex,Arrays.asList(val.split(";")));
							}
						}
					}
				}
			}else if(EopBasedataImportReference.REL_TYPE_STRONG.equals(rel.getFdType())){
				String relModel = " ref"+(++modelIndex);
				String value = (String)rowData.get(col.getFdColumn());
				//如果没有填写该字段
				if(StringUtil.isNull(value)){
					if(!"fdCompanyList".equals(property)) {
						where.append(" and model.").append(property).append(" is null");
					}
				}else{
					String key=rel.getFdKey();
					if(key.indexOf(";")>-1){
						String[] keys=key.split(";");
						int pIndex=++paramIndex;
						for(int i=0,len=keys.length;i<len;i++){
							if(i==0){
								where.append(" and (");
							}else{
								where.append(" or ");
							}
							where.append(" model.").append(property).append(".").append(keys[i]).append(" in(:param").append(pIndex).append(") ");
							if(i==len-1){
								where.append(" )");
							}
						}
						hqlInfo.setParameter("param"+pIndex,Arrays.asList(((String)rowData.get(col.getFdColumn())).split(";")));
					}else{
						joinBlock.append(" left join model.").append(property).append(" " + property);
						where.append(" and (").append(property).append(".").append(rel.getFdKey()).append(" in(:param").append(++paramIndex).append(") or ").append(property).append(" is null)");
						hqlInfo.setParameter("param"+paramIndex,Arrays.asList(((String)rowData.get(col.getFdColumn())).split(";")));
					}
					//如果有额外条件
					if(!"fdCompanyList".equals(property)) {
						if (rel.getFdFields() != null) {
							for (Map<String, Object> field : rel.getFdFields()) {
								if (EopBasedataImportReference.FIELD_TYPE_STATIC.equals(field.get("type"))) {
									where.append(" and ").append(property).append(".").append(field.get("name")).append("=:param").append(++paramIndex);
									hqlInfo.setParameter("param" + paramIndex, field.get("value"));
								} else if (EopBasedataImportReference.FIELD_TYPE_REF.equals(rel.getFdType())) {
									where.append(" and ").append(relModel).append(".").append(field.get("rel-column")).append(".").append(field.get("name")).append(" in(:param").append(++paramIndex).append(")");
									String val = (String) rowData.get(context.getColumnByProperty((String) field.get("rel-column")).getFdColumn());
									hqlInfo.setParameter("param" + paramIndex, Arrays.asList(val.split(";")));
								}
							}
						}
					}
				}
			}
		}else if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_DOUBLE.equals(col.getFdType())){
			where.append(" and model.").append(property).append(" =:param").append(++paramIndex);
			hqlInfo.setParameter("param"+paramIndex,Double.valueOf((String) rowData.get(col.getFdColumn())));
		}else{
			where.append(" and model.").append(property).append(" in(:param").append(++paramIndex).append(")");
			List<String> paramList = Arrays.asList(((String)rowData.get(col.getFdColumn())).split(";"));
			if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_ENUMS.equals(col.getFdType())||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_RADIO.equals(col.getFdType())){
				for(int i=0;i<paramList.size();i++){
					paramList.set(i, EnumerationTypeUtil.getColumnValueByLabel(context.getFdDict().getPropertyMap().get(col.getFdName()).getEnumType(), paramList.get(i)));
				}
			}
			hqlInfo.setParameter("param"+paramIndex,paramList);
		}
		index.put("modelIndex", modelIndex);
		index.put("paramIndex", paramIndex);
		if(StringUtil.isNotNull(joinBlock.toString())) {
			hqlInfo.setJoinBlock(joinBlock.toString());
		}
		hqlInfo.setWhereBlock(where.toString());
	}
	public static List<List<Object>> getDataList(FormFile fdFile,String modelName,EopBasedataImportContext context) throws Exception {
		Workbook wb = WorkbookFactory.create(fdFile.getInputStream());
		Sheet sheet = wb.getSheetAt(0);
		List<List<Object>> rtn = new ArrayList<List<Object>>();
		List<Object> rowData = null;
		Row row = null;
		String excelData="",excelValue="";
		for(int i=1,j=sheet.getPhysicalNumberOfRows();i<j;i++){
			excelData="";
			rowData = new ArrayList<Object>();
			row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			for(int m=0,n=context.getFdColumns().size();m<=n;m++){
				excelValue=getCellStringValue(row.getCell(m));
				rowData.add(excelValue);
				excelData+=excelValue;
			}
			if(StringUtil.isNull(excelData)){
				continue;
			}
			rtn.add(rowData);
		}
		wb.close();
		return rtn;
	}

	public static String getCellStringValue(Cell cell){
		//判断是否为null或空串
		if (cell==null || "".equals(cell.toString().trim())) {
			return "";
		}
		String cellValue = "";
		switch (cell.getCellType()) {
			case STRING: //字符串类型
				cellValue= cell.getStringCellValue().trim();
				cellValue=StringUtil.isNull(cellValue) ? "" : cellValue;
				break;
			case BOOLEAN:  //布尔类型
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case NUMERIC: //数值类型
				if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
					cellValue =    DateUtil.convertDateToString(cell.getDateCellValue(), "yyyy-MM-dd");
				} else {  //否
					Double val = cell.getNumericCellValue();
					val = Double.valueOf(String.valueOf(val).split("\\.")[1]);
					if(val>0){
						//cellValue = new DecimalFormat("0.00").format(cell.getNumericCellValue());
						// #131204 汇率小数位无需截断
						cellValue = String.valueOf(cell.getNumericCellValue());
					}else{
						cellValue = String.valueOf(cell.getNumericCellValue()).split("\\.")[0];
					}
				}
				break;
			default:
				cellValue = "";
				break;
		}
		return cellValue;
	}

	/**
	 * 创建表头的样式
	 *
	 * @param workBook
	 * @return
	 */
	public static CellStyle getTitleStyle(Workbook workBook) {
		CellStyle style = workBook.createCellStyle();
		Font font = workBook.createFont();// 创建一个字体对象
		font.setFontHeightInPoints((short) 10);// 设置字体的高度
		font.setBold(true);
		font.setBold(true); // 粗体
		font.setFontName("Arial");
		style.setFont(font);// 设置style的字体
		style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setWrapText(true);// 设置自动换行
		style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);// 设置单元格字体显示居中（左右方向）
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
		style.setFillBackgroundColor((short) 12);
		return style;
	}

	/**
	 * 创建内容的样式
	 *
	 * @param workBook
	 * @return
	 */
	public static CellStyle getNormalStyle(Workbook workBook) {
		CellStyle style = workBook.createCellStyle();
		Font font_nobold = workBook.createFont();// 创建一个字体对象
		font_nobold.setFontHeightInPoints((short) 10);// 设置字体的高度
		font_nobold.setBold(false);
		font_nobold.setFontName("Arial");
		style.setFont(font_nobold);// 设置style的字体
		style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setWrapText(true);// 设置自动换行
		style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);// 设置单元格字体显示居中（左右方向）
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
		return style;
	}

	public static String getTitleText(EopBasedataImportContext context, EopBasedataImportColumn col) throws Exception {
		String property = col.getFdName();
		if(property.indexOf(".")>-1){
			property = property.split("\\.")[0];
		}
		String[] msg = context.getColumnByProperty(property).getFdProperty().getMessageKey().split(":");
		String title = ResourceUtil.getString(msg[1],msg[0]);
		if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_UNUSED.equals(col.getFdType())
				||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_OBJECT.equals(col.getFdType())
				||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_LIST.equals(col.getFdType())){
			EopBasedataImportContext ctx = getImportContext(col.getFdRel().getFdReference());
			if(ctx!=null){
				EopBasedataImportColumn relCol = ctx.getColumnByProperty(col.getFdRel().getFdKey());
				String[] relMsg = relCol.getFdProperty().getMessageKey().split(":");
				String relTitle = ResourceUtil.getString(relMsg[1],relMsg[0]);
				title+=relTitle;
			}else{
				SysDictModel model = SysDataDict.getInstance().getModel(col.getFdRel().getFdReference());
				String key=col.getFdRel().getFdKey();
				Map<String, SysDictCommonProperty> dictMap=model.getPropertyMap();
				String[] keys=key.split(";");
				String relTitle="";
				for(int i=0,len=keys.length;i<len;i++){
					String pro=keys[i];
					SysDictCommonProperty dict=dictMap.get(pro);
					String[] relMsg =dict.getMessageKey().split(":");
					if(StringUtil.isNotNull(relTitle)){
						relTitle+="/";
					}
					relTitle += ResourceUtil.getString(relMsg[1],relMsg[0]);
				}
				title+=relTitle;
			}
		}
		return title;
	}

	public static HSSFDataValidation setString(int lastCol){
		// 创建一个规则：1-100的数字
		DVConstraint constraint = DVConstraint.createNumericConstraint(DVConstraint.ValidationType.TEXT_LENGTH,
				DVConstraint.OperatorType.BETWEEN, "1", "2000");
		// 设定在哪个单元格生效
		CellRangeAddressList regions = new CellRangeAddressList(2, 65535, 1, lastCol);
		// 创建规则对象
		HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);
		return ret;
	}

	public static String getContentText(EopBasedataImportContext context, EopBasedataImportColumn col,IBaseModel model) throws Exception {
		Object value = null;
		String property = col.getFdName();
		if(property.indexOf(".")>-1){
			property = property.split("\\.")[0];
		}
		value = PropertyUtils.getProperty(model, property);
		if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_UNUSED.equals(col.getFdType())
				||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_OBJECT.equals(col.getFdType())
				||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_LIST.equals(col.getFdType())){
			if(value instanceof Collection){
				Collection _value = (Collection) value;
				List<String> rtn = new ArrayList<String>();
				for(Iterator it = _value.iterator();it.hasNext();){
					Object element = it.next();
					String val =null;
					String key=col.getFdRel().getFdKey();
					if(key.indexOf(";")>-1){
						String[] keys=key.split(";");
						for(String pro:keys) {
							val=(String) PropertyUtils.getProperty(element, pro);
							if(StringUtil.isNotNull(val)) {
								break;  //多个字段循环获取值，直到取值不为空跳出循环
							}
						}
					}else{
						val=(String) PropertyUtils.getProperty(element, col.getFdRel().getFdKey());
					}
					if(StringUtil.isNotNull(val)){
						rtn.add(val);
					}
				}
				value = StringUtil.join(rtn, ";");
			}else{
				String val =null;
				EopBasedataImportReference fdRel=col.getFdRel();
				if(!EopBasedataImportReference.REL_TYPE_WEAK.equals(fdRel.getFdType())){
					String key=col.getFdRel().getFdKey();
					if(value!=null){
						if(key.indexOf(";")>-1){
							String[] keys=key.split(";");
							for(String pro:keys) {
								val=(String) PropertyUtils.getProperty(value, pro);
								if(StringUtil.isNotNull(val)) {
									break;  //多个字段循环获取值，直到取值不为空跳出循环
								}
							}
						}else{
							val=(String) PropertyUtils.getProperty(value, col.getFdRel().getFdKey());
						}
					}
				}else{
					val=value!=null?String.valueOf(value):"";
				}
				value = value==null?"":val;
			}
		}else if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_ENUMS.equals(col.getFdType())||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_RADIO.equals(col.getFdType())){
			String[] enums = (value==null?"":value.toString()).split(";");
			String enumType = col.getFdProperty().getEnumType();
			if("Boolean".equals(col.getFdProperty().getType())){
				enumType = "common_yesno";
			}
			List<String> values = new ArrayList<String>();
			for(String e:enums){
				values.add(EnumerationTypeUtil.getColumnEnumsLabel(enumType, e));
			}
			value = StringUtil.join(values, ";");
		}else if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_DOUBLE.equals(col.getFdType())&&value!=null) {
			DecimalFormat df = new java.text.DecimalFormat("0.0#####");
			value = df.format(value);
		}else if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_DATE.equals(col.getFdType())&&value!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = DateUtil.convertStringToDate(String.valueOf(value), DateUtil.PATTERN_DATETIME);
			value = sdf.format(date);
		}
		return value==null?"":value.toString();
	}

	public static void recalucateTreeRelations(String modelName, List<IBaseModel> addList, List<IBaseModel> updateList,
											   List<List<Object>> dataList) throws Exception {
		EopBasedataImportContext context = getImportContext(modelName);
		if(context.getColumnByProperty("hbmParent")==null){
			return;
		}
		List<IBaseModel> list = new ArrayList<IBaseModel>();
		ArrayUtil.concatTwoList(updateList, list);
		ArrayUtil.concatTwoList(addList, list);
		EopBasedataImportColumn column = context.getColumnByProperty("hbmParent");
		String[] keyCol = context.getFdKeyColumn().split(";");
		List<Integer> keyColumn = new ArrayList<Integer>();
		for(String key:keyCol){
			keyColumn.add(context.getColumnByProperty(key).getFdColumn());
		}
		for(IBaseModel model:list){
			if(PropertyUtils.getProperty(model, "fdParent")!=null){
				IBaseModel target = (IBaseModel) PropertyUtils.getProperty(model, "fdParent");
				String fdHierarchyId = (String) PropertyUtils.getProperty(target, "fdHierarchyId");
				BeanUtils.setProperty(model, "fdHierarchyId", fdHierarchyId+model.getFdId()+"x");
				continue;
			}else{
				BeanUtils.setProperty(model, "fdHierarchyId", "x"+model.getFdId()+"x");
			}
			String val = null;
			for(List<Object> rowData:dataList){
				boolean cur = true;
				for(Integer col:keyColumn){
					Object value = rowData.get(col);
					Object tar = PropertyUtils.getProperty(model, context.getColumnByCol(col).getFdName());
					if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_OBJECT.equals(context.getColumnByCol(col).getFdType())){
						tar = PropertyUtils.getProperty(tar, context.getColumnByCol(col).getFdRel().getFdKey());
					}
					if(value==null||!value.equals(tar)){
						cur = false;
						break;
					}
				}
				if(cur){
					val = (String) rowData.get(column.getFdColumn());
					break;
				}
			}
			if(StringUtil.isNotNull(val)){
				next:for(IBaseModel target:list){
					if(target.equals(model)){
						continue;
					}
					String __t = (String) PropertyUtils.getProperty(target, column.getFdRel().getFdKey());
					if(!val.equals(__t)){
						continue;
					}
					List<Map<String, Object>> fields = column.getFdRel().getFdFields();
					Object svalue = null,tvalue = null;
					for(Map<String,Object> map:fields){
						if("static".equals(map.get("type"))){
							tvalue = map.get("value").toString();
							svalue = PropertyUtils.getProperty(model, (String) map.get("name")).toString();
						}else if("ref".equals(map.get("type"))){
							tvalue = PropertyUtils.getProperty(target, (String) map.get("rel-column"));
							svalue = PropertyUtils.getProperty(model, (String) map.get("rel-column"));
						}
						if(!tvalue.equals(svalue)){
							continue next;
						}
					}
					BeanUtils.setProperty(model, "fdParent", target);
					if(target!=null){
						String fdHierarchyId = (String) PropertyUtils.getProperty(target, "fdHierarchyId");
						BeanUtils.setProperty(model, "fdHierarchyId", fdHierarchyId+model.getFdId()+"x");
					}
				}
			}
		}
	}

}
