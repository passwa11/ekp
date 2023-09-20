package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.BaseModelConvertor;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportColumn;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.imp.validator.EopBasedataValidateContext;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataBusinessService;
import com.landray.kmss.eop.basedata.service.IEopBasedataDetailImportValidator;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.framework.plugin.core.config.IConfigurationElement;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ExcelUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.upload.FormFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EopBasedataBusinessServiceImp extends ExtendDataServiceImp implements IEopBasedataBusinessService,IXMLDataBean{

	@Override
	public void saveEnable(String ids,String modelName) throws Exception {
		String hql="";
		Map<String, SysDictCommonProperty> dictMap = SysDataDict.getInstance().getModel(modelName).getPropertyMap();
		if(dictMap.containsKey("fdIsAvailable")) {
			hql = "update "+modelName+" set fdIsAvailable=:fdIsAvailable where fdId in(:ids)";
		}else if(dictMap.containsKey("fdStatus")) {
			hql = "update "+modelName+" set fdStatus=:fdIsAvailable where fdId in(:ids)";
		}
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		if(dictMap.containsKey("fdIsAvailable")) {
			query.setParameter("fdIsAvailable", true);
		}else if(dictMap.containsKey("fdStatus")) {
			query.setParameter("fdIsAvailable", 0);
		}
		query.setParameterList("ids", Arrays.asList(ids.split(";")));
		query.executeUpdate();
	}
	@Override
	public IBaseModel initModelSetting(RequestContext requestContext) throws Exception {
		IBaseModel model = initBizModelSetting(requestContext);
		if (model != null) {
			sysMetadataService.initModelSetting(requestContext, model);
		}
		return model;
	}
	@Override
	public void saveDisable(String ids,String modelName) throws Exception {
		String hql="";
		Map<String, SysDictCommonProperty> dictMap = SysDataDict.getInstance().getModel(modelName).getPropertyMap();
		if(dictMap.containsKey("fdIsAvailable")) {
			hql = "update "+modelName+" set fdIsAvailable=:fdIsAvailable where fdId in(:ids)";
		}else if(dictMap.containsKey("fdStatus")) {
			hql = "update "+modelName+" set fdStatus=:fdIsAvailable where fdId in(:ids)";
		}
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		if(dictMap.containsKey("fdIsAvailable")) {
			query.setParameter("fdIsAvailable", false);
		}else if(dictMap.containsKey("fdStatus")) {
			query.setParameter("fdIsAvailable", 1);
		}
		query.setParameterList("ids", Arrays.asList(ids.split(";")));
		query.executeUpdate();
	}

	@Override
	public void saveCopy(String ids, String modelName, String fdCompanyIds) throws Exception {
		String keyProperty = "fdCode";
		try {
			BeanUtils.getProperty(Class.forName(modelName).newInstance(), keyProperty);
		} catch (Exception e) {
			keyProperty = SysDataDict.getInstance().getModel(modelName).getDisplayProperty();
		}
		//已选择的档案
		String hql = "from "+modelName+" where fdId in(:ids)";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("ids", Arrays.asList(ids.split(";")));
		List<IBaseModel> list = query.list();
		List<IBaseModel> addList = new ArrayList<IBaseModel>();
		//选择的公司
		hql = "from com.landray.kmss.eop.basedata.model.EopBasedataCompany where fdId in(:ids)";
		query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("ids", Arrays.asList(fdCompanyIds.split(";")));
		List<EopBasedataCompany> companys = query.list();
		//在公司中已存在的档案
		List<String> codes = new ArrayList<String>();
		for(IBaseModel model:list){
			codes.add(BeanUtils.getProperty(model, keyProperty));
		}
		hql = "from "+modelName+" where "+keyProperty+" in(:codes) and fdCompany.fdId in(:ids)";
		query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("codes", codes);
		query.setParameterList("ids", Arrays.asList(fdCompanyIds.split(";")));
		List<IBaseModel> existData = query.list();
		//以档案编码和公司编码为key值存储，以便后续判断
		EopBasedataCompany comp;
		Map<String,IBaseModel> existMap = new HashMap<String,IBaseModel>();
		for(IBaseModel model:existData){
			String code = BeanUtils.getProperty(model, keyProperty);
			comp = (EopBasedataCompany) PropertyUtils.getProperty(model, "fdCompany");
			existMap.put(code+comp.getFdCode(), model);
		}
		//复制档案
		IBaseModel copyModel = null;
		SysOrgPerson user = UserUtil.getUser();
		Date now = new Date();
		List<String> ignoreProperties = new ArrayList<String>();
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		//处理集合属性
		for(SysDictCommonProperty property:dict.getPropertyList()){
			if(ModelUtil.getPropertyType(modelName, property.getName()).indexOf("[]")>-1){
				if(ModelUtil.getPropertyType(modelName, property.getName()).indexOf(".fssc.")>-1){
					ignoreProperties.add(property.getName());
				}
			}
		}
		ignoreProperties.add("fdId");
		ignoreProperties.add("fdHierarchyId");
		for(IBaseModel model:list){
			for(EopBasedataCompany com:companys){
				String key = BeanUtils.getProperty(model, keyProperty)+com.getFdCode();
				//如果该公司下已存在相应档案，跳过
				if(existMap.containsKey(key)){
					continue;
				}
				copyModel = (IBaseModel) Class.forName(modelName).newInstance();
				org.springframework.beans.BeanUtils.copyProperties(model,copyModel, ignoreProperties.toArray(new String[ignoreProperties.size()]));
				BeanUtils.setProperty(copyModel, "docCreator", user);
				BeanUtils.setProperty(copyModel, "docCreateTime", now);
				BeanUtils.setProperty(copyModel, "docAlteror", user);
				BeanUtils.setProperty(copyModel, "docAlterTime", now);
				BeanUtils.setProperty(copyModel, "fdCompany", com);
				addList.add(copyModel);
				
			}
		}
		if(!ArrayUtil.isEmpty(addList)){
			getBaseDao().saveOrUpdateAll(addList);
		}
	}

	@Override
	public List<EopBasedataImportMessage> saveImport(FormFile fdFile,String modelName) throws Exception {
		List<IBaseModel> addList = new ArrayList<IBaseModel>();
		List<IBaseModel> updateList = new ArrayList<IBaseModel>();
		//解析EXCEL文件
		EopBasedataImportContext context = EopBasedataImportUtil.getImportContext(modelName);
		List<List<Object>> dataList = EopBasedataImportUtil.getDataList(fdFile,modelName,context);
		//数据预处理
		beforeImportValidate(modelName, dataList,context);
		List<EopBasedataImportMessage> messages=null;
		//关于关键字数据去重
		messages=checkRepeatData(modelName,dataList,context);
		if(messages==null||messages.size()==0){
			//数据校验
			messages = EopBasedataImportUtil.validateData(dataList,modelName,context);
		}
		//excel数据与现有基础数据校验
		if(messages==null|| messages.size()==0){
			//校验通过，转换为档案
			messages = EopBasedataImportUtil.parseData(dataList, addList, updateList, modelName,context);
		}
		if (messages.size() == 0) {
			//后置业务处理
			afterImportValidate(modelName,addList,updateList,dataList);
			if(!ArrayUtil.isEmpty(addList)){
				for(IBaseModel baseModel:addList) {
					getBaseDao().add(baseModel);
				}
			}
			if(!ArrayUtil.isEmpty(updateList)){
				
				for(IBaseModel baseModel:updateList) {
					getBaseDao().update(baseModel);
				}
				
				//底层与上面一致，为兼容EopBasedataPushAdvice，所以使用上面的
//				getBaseDao().saveOrUpdateAll(updateList);
			}
		}
		return messages;
	}

	@Override
	public void afterImportValidate(String modelName,List<IBaseModel> addList,List<IBaseModel> updateList, List<List<Object>> dataList) throws Exception {
		//设置档案的创建时间、更新时间、是否有效等基础字段
		Date now = new Date();
		SysOrgPerson user = UserUtil.getUser();
		Map<String, SysDictCommonProperty> dictMap=SysDataDict.getInstance().getModel(modelName).getPropertyMap();
		for(IBaseModel model : addList){
			if(dictMap.containsKey("fdStatus")){
				BeanUtils.setProperty(model, "fdStatus", 0);  //货币0为有效，1为无效
				if(dictMap.containsKey("fdIsAvailable")){	//物资信息有fdStatus和fdIsAvailable两个字段
					BeanUtils.setProperty(model, "fdIsAvailable", true);
				}
			}else{
				BeanUtils.setProperty(model, "fdIsAvailable", true);
			}
			if("com.landray.kmss.eop.basedata.model.EopBasedataCompany".equals(modelName)){
				String fdFinancialSystem = EopBasedataFsscUtil.getSwitchValue("fdFinancialSystem");	//开关设置里对接的财务系统
				if(StringUtil.isNull(fdFinancialSystem)){
					BeanUtils.setProperty(model, "fdJoinSystem", null);
					BeanUtils.setProperty(model, "fdUEightUrl", null);
					BeanUtils.setProperty(model, "fdKUrl", null);
					BeanUtils.setProperty(model, "fdKUserName", null);
					BeanUtils.setProperty(model, "fdKPassWord", null);
					BeanUtils.setProperty(model, "fdEUserName", null);
					BeanUtils.setProperty(model, "fdEPassWord", null);
					BeanUtils.setProperty(model, "fdESlnName", null);
					BeanUtils.setProperty(model, "fdEDcName", null);
					BeanUtils.setProperty(model, "fdELanguage", null);
					BeanUtils.setProperty(model, "fdEDbType", null);
					BeanUtils.setProperty(model, "fdEAuthPattern", null);
					BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
					BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
					BeanUtils.setProperty(model, "fdSystemParam", null);
				}else{
					List<String> fdJoinSystemList = Arrays.asList(fdFinancialSystem.split(";"));
					if(!fdJoinSystemList.contains(BeanUtils.getProperty(model, "fdJoinSystem"))){
						BeanUtils.setProperty(model, "fdJoinSystem", null);
						BeanUtils.setProperty(model, "fdUEightUrl", null);
						BeanUtils.setProperty(model, "fdKUrl", null);
						BeanUtils.setProperty(model, "fdKUserName", null);
						BeanUtils.setProperty(model, "fdKPassWord", null);
						BeanUtils.setProperty(model, "fdEUserName", null);
						BeanUtils.setProperty(model, "fdEPassWord", null);
						BeanUtils.setProperty(model, "fdESlnName", null);
						BeanUtils.setProperty(model, "fdEDcName", null);
						BeanUtils.setProperty(model, "fdELanguage", null);
						BeanUtils.setProperty(model, "fdEDbType", null);
						BeanUtils.setProperty(model, "fdEAuthPattern", null);
						BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
						BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
						BeanUtils.setProperty(model, "fdSystemParam", null);
					}else{
						if("K3".equals(BeanUtils.getProperty(model, "fdJoinSystem"))){
							BeanUtils.setProperty(model, "fdUEightUrl", null);
							BeanUtils.setProperty(model, "fdEUserName", null);
							BeanUtils.setProperty(model, "fdEPassWord", null);
							BeanUtils.setProperty(model, "fdESlnName", null);
							BeanUtils.setProperty(model, "fdEDcName", null);
							BeanUtils.setProperty(model, "fdELanguage", null);
							BeanUtils.setProperty(model, "fdEDbType", null);
							BeanUtils.setProperty(model, "fdEAuthPattern", null);
							BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
							BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
						}else if("U8".equals(BeanUtils.getProperty(model, "fdJoinSystem"))){
							BeanUtils.setProperty(model, "fdKUrl", null);
							BeanUtils.setProperty(model, "fdKUserName", null);
							BeanUtils.setProperty(model, "fdKPassWord", null);
							BeanUtils.setProperty(model, "fdEUserName", null);
							BeanUtils.setProperty(model, "fdEPassWord", null);
							BeanUtils.setProperty(model, "fdESlnName", null);
							BeanUtils.setProperty(model, "fdEDcName", null);
							BeanUtils.setProperty(model, "fdELanguage", null);
							BeanUtils.setProperty(model, "fdEDbType", null);
							BeanUtils.setProperty(model, "fdEAuthPattern", null);
							BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
							BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
						}else if("EAS".equals(BeanUtils.getProperty(model, "fdJoinSystem"))){
							BeanUtils.setProperty(model, "fdUEightUrl", null);
							BeanUtils.setProperty(model, "fdKUrl", null);
							BeanUtils.setProperty(model, "fdKUserName", null);
							BeanUtils.setProperty(model, "fdKPassWord", null);
							BeanUtils.setProperty(model, "fdSystemParam", null);
						}else{
							BeanUtils.setProperty(model, "fdUEightUrl", null);
							BeanUtils.setProperty(model, "fdKUrl", null);
							BeanUtils.setProperty(model, "fdKUserName", null);
							BeanUtils.setProperty(model, "fdKPassWord", null);
							BeanUtils.setProperty(model, "fdEUserName", null);
							BeanUtils.setProperty(model, "fdEPassWord", null);
							BeanUtils.setProperty(model, "fdESlnName", null);
							BeanUtils.setProperty(model, "fdEDcName", null);
							BeanUtils.setProperty(model, "fdELanguage", null);
							BeanUtils.setProperty(model, "fdEDbType", null);
							BeanUtils.setProperty(model, "fdEAuthPattern", null);
							BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
							BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
							BeanUtils.setProperty(model, "fdSystemParam", null);
						}
					}
				}
			}
			BeanUtils.setProperty(model, "docCreateTime", now);
			BeanUtils.setProperty(model, "docAlterTime", now);
			BeanUtils.setProperty(model, "docCreator", user);
			BeanUtils.setProperty(model, "docAlteror", user);
		}
		for(IBaseModel model : updateList){
			if(dictMap.containsKey("fdStatus")){
				BeanUtils.setProperty(model, "fdStatus", 0); //货币0为有效，1为无效
				if(dictMap.containsKey("fdIsAvailable")){	//物资信息有fdStatus和fdIsAvailable两个字段
					BeanUtils.setProperty(model, "fdIsAvailable", true);
				}
			}else{
				BeanUtils.setProperty(model, "fdIsAvailable", true);
			}
			if("com.landray.kmss.eop.basedata.model.EopBasedataCompany".equals(modelName)){
				String fdFinancialSystem = EopBasedataFsscUtil.getSwitchValue("fdFinancialSystem");	//开关设置里对接的财务系统
				if(StringUtil.isNull(fdFinancialSystem)){
					BeanUtils.setProperty(model, "fdJoinSystem", null);
					BeanUtils.setProperty(model, "fdUEightUrl", null);
					BeanUtils.setProperty(model, "fdKUrl", null);
					BeanUtils.setProperty(model, "fdKUserName", null);
					BeanUtils.setProperty(model, "fdKPassWord", null);
					BeanUtils.setProperty(model, "fdEUserName", null);
					BeanUtils.setProperty(model, "fdEPassWord", null);
					BeanUtils.setProperty(model, "fdESlnName", null);
					BeanUtils.setProperty(model, "fdEDcName", null);
					BeanUtils.setProperty(model, "fdELanguage", null);
					BeanUtils.setProperty(model, "fdEDbType", null);
					BeanUtils.setProperty(model, "fdEAuthPattern", null);
					BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
					BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
					BeanUtils.setProperty(model, "fdSystemParam", null);
				}else{
					List<String> fdJoinSystemList = Arrays.asList(fdFinancialSystem.split(";"));
					if(!fdJoinSystemList.contains(BeanUtils.getProperty(model, "fdJoinSystem"))){
						BeanUtils.setProperty(model, "fdJoinSystem", null);
						BeanUtils.setProperty(model, "fdUEightUrl", null);
						BeanUtils.setProperty(model, "fdKUrl", null);
						BeanUtils.setProperty(model, "fdKUserName", null);
						BeanUtils.setProperty(model, "fdKPassWord", null);
						BeanUtils.setProperty(model, "fdEUserName", null);
						BeanUtils.setProperty(model, "fdEPassWord", null);
						BeanUtils.setProperty(model, "fdESlnName", null);
						BeanUtils.setProperty(model, "fdEDcName", null);
						BeanUtils.setProperty(model, "fdELanguage", null);
						BeanUtils.setProperty(model, "fdEDbType", null);
						BeanUtils.setProperty(model, "fdEAuthPattern", null);
						BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
						BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
						BeanUtils.setProperty(model, "fdSystemParam", null);
					}else{
						if("K3".equals(BeanUtils.getProperty(model, "fdJoinSystem"))){
							BeanUtils.setProperty(model, "fdUEightUrl", null);
							BeanUtils.setProperty(model, "fdEUserName", null);
							BeanUtils.setProperty(model, "fdEPassWord", null);
							BeanUtils.setProperty(model, "fdESlnName", null);
							BeanUtils.setProperty(model, "fdEDcName", null);
							BeanUtils.setProperty(model, "fdELanguage", null);
							BeanUtils.setProperty(model, "fdEDbType", null);
							BeanUtils.setProperty(model, "fdEAuthPattern", null);
							BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
							BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
						}else if("U8".equals(BeanUtils.getProperty(model, "fdJoinSystem"))){
							BeanUtils.setProperty(model, "fdKUrl", null);
							BeanUtils.setProperty(model, "fdKUserName", null);
							BeanUtils.setProperty(model, "fdKPassWord", null);
							BeanUtils.setProperty(model, "fdEUserName", null);
							BeanUtils.setProperty(model, "fdEPassWord", null);
							BeanUtils.setProperty(model, "fdESlnName", null);
							BeanUtils.setProperty(model, "fdEDcName", null);
							BeanUtils.setProperty(model, "fdELanguage", null);
							BeanUtils.setProperty(model, "fdEDbType", null);
							BeanUtils.setProperty(model, "fdEAuthPattern", null);
							BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
							BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
						}else if("EAS".equals(BeanUtils.getProperty(model, "fdJoinSystem"))){
							BeanUtils.setProperty(model, "fdUEightUrl", null);
							BeanUtils.setProperty(model, "fdKUrl", null);
							BeanUtils.setProperty(model, "fdKUserName", null);
							BeanUtils.setProperty(model, "fdKPassWord", null);
							BeanUtils.setProperty(model, "fdSystemParam", null);
						}else{
							BeanUtils.setProperty(model, "fdUEightUrl", null);
							BeanUtils.setProperty(model, "fdKUrl", null);
							BeanUtils.setProperty(model, "fdKUserName", null);
							BeanUtils.setProperty(model, "fdKPassWord", null);
							BeanUtils.setProperty(model, "fdEUserName", null);
							BeanUtils.setProperty(model, "fdEPassWord", null);
							BeanUtils.setProperty(model, "fdESlnName", null);
							BeanUtils.setProperty(model, "fdEDcName", null);
							BeanUtils.setProperty(model, "fdELanguage", null);
							BeanUtils.setProperty(model, "fdEDbType", null);
							BeanUtils.setProperty(model, "fdEAuthPattern", null);
							BeanUtils.setProperty(model, "fdELoginWsdlUrl", null);
							BeanUtils.setProperty(model, "fdEImportVoucherWsdlUrl", null);
							BeanUtils.setProperty(model, "fdSystemParam", null);
						}
					}
				}
			}
			BeanUtils.setProperty(model, "docAlterTime", now);
			BeanUtils.setProperty(model, "docAlteror", user);
		}
		//维护层级关系
		EopBasedataImportUtil.recalucateTreeRelations(modelName,addList,updateList,dataList);
	}

	/**
	 * 导入数据校验前对数据进行的预先处理，如果有特殊逻辑的请在自己的service中重写此方法
	 * @param modelName
	 * @param dataList
	 * @throws Exception
	 */
	@Override
	public void beforeImportValidate(String modelName, List<List<Object>> dataList,EopBasedataImportContext context) throws Exception {
		
	}
	
	public List<EopBasedataImportMessage> checkRepeatData(String modelName, List<List<Object>> dataList,EopBasedataImportContext context)throws Exception {
		List<EopBasedataImportMessage> messages = new ArrayList<EopBasedataImportMessage>();
		EopBasedataImportMessage msg=new EopBasedataImportMessage();
		String str1="";
		String str2="";
		String arr[] = context.getFdKeyColumn().split(";");
		List<Integer> indexList = new ArrayList<>();
		List<Integer> companyList = new ArrayList<>();
		for (int i = 0; i < context.getFdColumns().size(); i++) {
			for (int j = 0; j < arr.length; j++) {
				if (context.getFdColumns().get(i).getFdName().equals(arr[j])) {
					if("fdCompanyList".equals(arr[j])) {
						companyList.add(i);
					}else{
						indexList.add(i);
					}
				}
			}
		}
		//excel内校验重复
		Map<String,Integer> excelMap=new HashMap<String,Integer>();	//单个公司编码+代码作为key
		Map<String,Integer> codeMap=new HashMap<String,Integer>();	//公司编码为空,代码作为key
		for (int i = 0; i < dataList.size(); i++) {
			str1="";
			for (int k=0;k<indexList.size();k++) {
					str1=dataList.get(i).get(indexList.get(k)+1)+str1;
			}
			str2="";
			for (int k=0;k<companyList.size();k++) {
				str2=dataList.get(i).get(companyList.get(k)+1)+str2;
			}
			String[] str=str2.split(";");
			for (int j = 0; j < str.length; j++) {
				if(excelMap.containsKey(str[j]+"&"+str1)){	//公司编码+代码存在重复
					msg.addMoreMsg(ResourceUtil.getString("message.validation.repeat","eop-basedata").replace("{0}", String.valueOf(i+1)).replace("{1}",  String.valueOf(excelMap.get(str[j]+"&"+str1))));
					continue;
				}else if(codeMap.containsKey(str1)){	//代码存在重复
					msg.addMoreMsg(ResourceUtil.getString("message.validation.repeat","eop-basedata").replace("{0}", String.valueOf(i+1)).replace("{1}",  String.valueOf(codeMap.get(str1))));
					continue;
				}else{
					if(StringUtil.isNotNull(str[j])) {
						excelMap.put(str[j] +"&"+ str1, i + 1);
					}else{
						codeMap.put(str1, i+1);
					}
					continue;
				}
			}
		}
		if(!excelMap.isEmpty() && !codeMap.isEmpty()){	//excel同时存在公司编码为空和不为空的数据,需要校验代码是否存在重复
			for(String key:excelMap.keySet()){
				String key1 = key.substring(0, key.indexOf("&"));
				String code = key.substring(key1.length()+1);
				if(codeMap.containsKey(code)){
					msg.addMoreMsg(ResourceUtil.getString("message.validation.repeat","eop-basedata").replace("{0}", String.valueOf(codeMap.get(code))).replace("{1}",  String.valueOf(excelMap.get(key))));
					continue;
				}
			}
		}

		if(!ArrayUtil.isEmpty(msg.getMoreMessages())){
			msg.addFailMsg(ResourceUtil.getString("message.validation.dataNum","eop-basedata"));
			messages.add(msg);
	    }
		return messages;
	}

	@Override
	public void downloadTemplate(HttpServletResponse response, String modelName) throws Exception {
		EopBasedataImportContext context = EopBasedataImportUtil.getImportContext(modelName);
		List<EopBasedataImportColumn> cols = context.getFdColumns();
		SysDictModel dict = context.getFdDict();
		String filename = dict.getMessageKey();
		filename = ResourceUtil.getString(filename.split(":")[1],filename.split(":")[0]);
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		Workbook workBook = new HSSFWorkbook();
		String sheetName = new String(filename.getBytes("ISO8859-1"), "GBK");
		Sheet sheet = workBook.createSheet(sheetName);
		sheet.addValidationData(EopBasedataImportUtil.setString(cols.size()+1));
		for (int i = 0; i <= cols.size(); i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		CellStyle style = EopBasedataImportUtil.getTitleStyle(workBook);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(ResourceUtil.getString("page.serial"));
		cell.setCellStyle(style);
		int n=0;
		for (int i = 0; i < cols.size(); i++) {
			cell = row.createCell(n+1);
			String text = EopBasedataImportUtil.getTitleText(context,cols.get(i));
			if(!EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_UNUSED.equals(cols.get(i).getFdType())) {
				List<EopBasedataValidateContext> validators = cols.get(i).getFdValidators();
				for(EopBasedataValidateContext val:validators) {
					if("required".contentEquals(val.getFdName())) {
						text+=ResourceUtil.getString("py.required","eop-basedata");
					}
				}
			}
			cell.setCellValue(text);
			cell.setCellStyle(style);
			n++;
		}
		workBook.write(os);
		os.flush();
		os.close();
	}

	@Override
	public void exportData(HttpServletResponse response, String modelName,String fdCompanyId) throws Exception {
		EopBasedataImportContext context = EopBasedataImportUtil.getImportContext(modelName);
		List<EopBasedataImportColumn> cols = context.getFdColumns();
		SysDictModel dict = context.getFdDict();
		String hql = "from "+modelName;
		if(dict.getPropertyMap().containsKey("fdIsAvailable")){
			hql += " where fdIsAvailable=:fdIsAvailable";
		}else if(dict.getPropertyMap().containsKey("fdStatus")){
			hql += " where fdStatus=:fdStatus";
		}
		if(StringUtil.isNotNull(fdCompanyId)){
			hql += " and fdCompany.fdId=:fdCompanyId";
		}
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		if(dict.getPropertyMap().containsKey("fdIsAvailable")){
			query.setParameter("fdIsAvailable", true);
		}else if(dict.getPropertyMap().containsKey("fdStatus")){
			query.setParameter("fdStatus", 0);
		}
		if(StringUtil.isNotNull(fdCompanyId)){
			query.setParameter("fdCompanyId", fdCompanyId);
		}
		List<IBaseModel> models = query.list();
		String filename = dict.getMessageKey();
		filename = ResourceUtil.getString(filename.split(":")[1],filename.split(":")[0]);
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		Workbook workBook = new HSSFWorkbook();
		String sheetName = new String(filename.getBytes("ISO8859-1"), "GBK");
		Sheet sheet = workBook.createSheet(sheetName);
		for (int i = 0; i <= cols.size(); i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		CellStyle title = EopBasedataImportUtil.getTitleStyle(workBook);
		CellStyle content = EopBasedataImportUtil.getNormalStyle(workBook);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(ResourceUtil.getString("page.serial"));
		cell.setCellStyle(title);
		int n=0;
		for (int i = 0; i < cols.size(); i++) {
			String switchField = cols.get(i).getFdSwitchField();
			if(StringUtil.isNotNull(switchField)){ //需要根据开关判断
				String field=switchField.split(":")[0];
				String judgeValue=switchField.split(":")[1];
				String value = EopBasedataFsscUtil.getSwitchValue(field);
				if(!EopBasedataFsscUtil.isContain(value, judgeValue, ";")){
					continue;
				}
			}
			cell = row.createCell(n+1);
			cell.setCellValue(EopBasedataImportUtil.getTitleText(context,cols.get(i)));
			cell.setCellStyle(title);
			n++;
		}
		n=0;
		int index = 1;
		for(IBaseModel model:models){
			row = sheet.createRow(index++);
			cell = row.createCell(0);
			cell.setCellValue(index-1);
			cell.setCellStyle(content);
			for (int i = 0; i < cols.size(); i++) {
				cell = row.createCell(n+1);
				cell.setCellValue(EopBasedataImportUtil.getContentText(context,cols.get(i),model));
				cell.setCellStyle(content);
				n++;
			}
			n=0;
		}
		workBook.write(os);
		os.flush();
		os.close();
	}
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		//如果有层级关系，修改状态时需要做层级更新
		String modelName = ModelUtil.getModelClassName(modelObj);
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		if(dict.getPropertyMap().containsKey("fdParent")){
			String statusName = "";
			if(dict.getPropertyMap().containsKey("fdIsAvailable")){
				statusName = "fdIsAvailable";
			}
			if(dict.getPropertyMap().containsKey("fdStatus")){
				statusName = "fdStatus";
			}
			if(StringUtil.isNotNull(statusName)){
				Object status = PropertyUtils.getProperty(modelObj, statusName);
				String fdHierarchyId = PropertyUtils.getProperty(modelObj, "fdHierarchyId").toString();
				//启用状态，更新所有父级档案为启用
				if("true".equals(status.toString())||"1".equals(status.toString())){
					Query query = getBaseDao().getHibernateSession().createQuery("update "+modelName+" set "+statusName+"=:status where fdId in(:ids)");
					query.setParameter("status", status);
					query.setParameterList("ids", Arrays.asList(fdHierarchyId.split("x")));
					query.executeUpdate();
				}
				//禁用状态，更新所有子级档案为禁用
				else if("false".equals(status.toString())||"0".equals(status.toString())){
					Query query = getBaseDao().getHibernateSession().createQuery("update "+modelName+" set "+statusName+"=:status where fdHierarchyId like :fdHierarchyId");
					query.setParameter("fdHierarchyId", fdHierarchyId+"%");
					query.setParameter("status", status);
					query.executeUpdate();
				}
			}
		}
		super.update(modelObj);
	}
	
	@Override
	public List<String> checkEnable(String ids, String modelName) {
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		String status = "fdIsAvailable",field = "model.fdCode";
		if(dict.getPropertyMap().containsKey("fdStatus")){
			status = "fdStatus";
		}
		if(dict.getPropertyMap().containsKey("fdCompany")){
			field = "concat(model.fdCompany.fdId,model.fdCode)";
		}
		String sql ="select model.fdCode from "+modelName+" model where  model."+status+" = 1 and "+field+" in (:ids )";
		Query query = getBaseDao().getHibernateSession().createQuery(sql);
		query.setParameterList("ids", Arrays.asList(ids.split(";")));
		return  query.list();
	}
	@Override
	public Map<String, Object> detailImport(FormFile fdFile, String modelName, String properties,String fdCompanyId,String docTemplateId) throws Exception {
		JSONObject cols = new JSONObject();
		List<SysDictCommonProperty> props = getDetailProps(modelName,properties,cols);
		Workbook wb = WorkbookFactory.create(fdFile.getInputStream());
		Sheet sheet = wb.getSheetAt(0);
		JSONArray data = new JSONArray();
		List<EopBasedataImportMessage> messages = new ArrayList<EopBasedataImportMessage>();
		Row row = null;
		JSONArray _properties = JSONArray.fromObject(properties);
		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
			JSONObject obj = new JSONObject();
			obj.put("fdCompanyId", fdCompanyId);
			EopBasedataImportMessage message = new EopBasedataImportMessage();
			row = sheet.getRow(i);
			Integer k = 1;
			for(SysDictCommonProperty property:props){
				k = getImportIndex(_properties,property,cols);
				if(k==null) {
					continue;
				}
//				k=k+1;//skip row no
				String value = EopBasedataImportUtil.getCellStringValue(row.getCell(k));
				if(!(property instanceof SysDictListProperty)&&!(property instanceof SysDictModelProperty)&&StringUtil.isNull(value)){
					obj.put(property.getName(),"");
					continue;
				}
				if("Double".equals(property.getType())){
					try {
						obj.put(property.getName(), Double.valueOf(value));
					} catch (Exception e) {
						message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+ResourceUtil.getString("py.format.number.illigeal","eop-basedata"));
					}
				}else if("Integer".equals(property.getType())) {
					try {
						obj.put(property.getName(), Integer.valueOf(value));
					} catch (Exception e) {
						message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+ResourceUtil.getString("py.format.integer.illigeal","eop-basedata"));
					}
				} else if("String".equals(property.getType())){
					if(StringUtil.isNotNull(property.getEnumType())){
						try {
							String[] _value = value.split(";");
							String __ = "";
							for(String val:_value){
								__+=EnumerationTypeUtil.getColumnValueByLabel(property.getEnumType(), val);
							}
							obj.put(property.getName(), __);
						} catch (Exception e) {
							message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+ResourceUtil.getString("py.notAvailable","eop-basedata"));
						}
					}else{
						obj.put(property.getName(), value);
					}
				}else if("Date".equals(property.getType())){
					try{
						DateUtil.convertStringToDate(value, DateUtil.PATTERN_DATE);
						obj.put(property.getName(), value);
					}catch (Exception e) {
						message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+ResourceUtil.getString("py.date.number.illigeal","eop-basedata"));
					}
				}else if("Boolean".equals(property.getType())) {
					try{
						obj.put(property.getName(), EnumerationTypeUtil.getColumnValueByLabel("common_yesno", value));
					}catch (Exception e) {
						message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+ResourceUtil.getString("py.notAvailable","eop-basedata"));
					}
				}else{
					Object object=ExcelUtil.getCellValue((HSSFCell)row.getCell(k+1));
					String code = object!=null?String.valueOf(object):"";
					JSONObject col = cols.getJSONObject(property.getName());
					if(StringUtil.isNull(code)) {
						obj.put(col.get("fdFieldId"), "");
						obj.put(col.get("fdFieldName"), "");
						continue;
					}
					String type = property.getType();
					IEopBasedataDetailImportValidator validator = getImportValidator(type,modelName);
					SysDictModel typeModel = SysDataDict.getInstance().getModel(type);
					EopBasedataImportContext typeCtx = EopBasedataImportUtil.getImportContext(type);
					String tableName = typeModel.getServiceBean();
					tableName = tableName.substring(0,tableName.length()-7);
					//列表
					if(property instanceof SysDictListProperty){
						String hql = "select "+tableName+" from "+type+" "+tableName;
						if(typeModel.getPropertyMap().containsKey("fdCompanyList")){
							hql+=" left join "+tableName+".fdCompanyList comp";
						}
						hql+=" where 1=1";
						if(type.contains("SysOrg")) {//组织架构
							hql+=" and ("+tableName+".fdNo=:fdCode or "+tableName+".fdLoginName=:fdCode)";
						}else if(type.contains("eop.basedata")) {//基础档案
							String key = typeCtx.getFdKeyColumn();
							hql+=" and "+tableName+".fdCode=:fdCode";
						}else {
							String key = typeModel.getDisplayProperty();
							hql+=" and "+tableName+"."+key+"=:fdCode";
						}
						if(typeModel.getPropertyMap().containsKey("fdCompanyList")){
							hql+=" and (comp.fdId=:fdCompanyId or comp is null)";
						}
						if(typeModel.getPropertyMap().containsKey("fdIsAvailable")){
							hql+=" and "+tableName+".fdIsAvailable=:fdIsAvailable";
						}
						if(typeModel.getPropertyMap().containsKey("docStatus")){
							hql+=" and "+tableName+".docStatus=:docStatus";
						}
						String ids = "",names = "";
						String[] _code = code.split(";");
						for(String val:_code){
							Query query = getBaseDao().getHibernateSession().createQuery(hql)
							.setParameter("fdCode", val).setParameter("fdIsAvailable", true);
							if(typeModel.getPropertyMap().containsKey("fdCompanyList")){
								query.setParameter("fdCompanyId", fdCompanyId);
							}
							if(typeModel.getPropertyMap().containsKey("fdIsAvailable")){
								query.setParameter("fdIsAvailable", true);
							}
							if(typeModel.getPropertyMap().containsKey("docStatus")){
								query.setParameter("docStatus", "30");
							}
							List list = query.list();
							if(ArrayUtil.isEmpty(list)){
								message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+val+"无效或不存在");
							}else{
								Boolean valdatePass = true;
								//业务校验
								Map<String,Object> params = new HashMap<String,Object>();
								params.put("docTemplateId", docTemplateId);
								params.put("fdCompanyId", fdCompanyId);
								if(validator!=null&&!validator.validate(params, val)) {
									valdatePass = false;
									message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+code+"无效或不存在");
								}
								if(valdatePass) {
									if(StringUtil.isNotNull(ids)){
										ids+=";";
										names+=";";
									}
									ids +=PropertyUtils.getProperty(list.get(0), col.getString("fdModelPropertyId"));
									names+=PropertyUtils.getProperty(list.get(0), col.getString("fdModelPropertyName"));
								}
							}
						}
						obj.put(col.get("fdFieldId"),ids );
						obj.put(col.get("fdFieldName"),names );
					}else{
						String hql = "select "+tableName+" from "+type+" "+tableName;
						if(typeModel.getPropertyMap().containsKey("fdCompanyList")){
							hql+=" left join "+tableName+".fdCompanyList comp";
						}
						hql+=" where 1=1";
						if(type.contains("SysOrg")) {//组织架构
							hql+=" and ("+tableName+".fdNo=:fdCode or "+tableName+".fdLoginName=:fdCode)";
						}else if(type.contains("eop.basedata")) {//基础档案
							String key = typeCtx.getFdKeyColumn();
							hql+=" and "+tableName+".fdCode=:fdCode";
						}else {
							String key = typeModel.getDisplayProperty();
							hql+=" and "+tableName+"."+key+"=:fdCode";
						}
						if(typeModel.getPropertyMap().containsKey("fdCompanyList")){
							hql+=" and (comp.fdId=:fdCompanyId or comp is null)";
						}
						if(typeModel.getPropertyMap().containsKey("fdIsAvailable")){
							hql+=" and "+tableName+".fdIsAvailable=:fdIsAvailable";
						}
						if(typeModel.getPropertyMap().containsKey("docStatus")){
							hql+=" and "+tableName+".docStatus=:docStatus";
						}
						System.out.println(hql);
						Query query = getBaseDao().getHibernateSession().createQuery(hql);
						query.setParameter("fdCode", code);
						if(typeModel.getPropertyMap().containsKey("docStatus")){
							query.setParameter("docStatus", "30");
						}
						if(typeModel.getPropertyMap().containsKey("fdIsAvailable")) {
							query.setParameter("fdIsAvailable", true);
						}
						if(typeModel.getPropertyMap().containsKey("fdCompanyList")){
							query.setParameter("fdCompanyId", fdCompanyId);
						}
						List list = query.list();
						if(ArrayUtil.isEmpty(list)){
							message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+code+"无效或不存在");
						}else{
							Boolean valdatePass = true;
							//业务校验
							Map<String,Object> params = new HashMap<String,Object>();
							params.put("docTemplateId", docTemplateId);
							params.put("fdCompanyId", fdCompanyId);
							if(validator!=null&&!validator.validate(params, code)) {
								valdatePass = false;
								message.addMoreMsg(ResourceUtil.getString(property.getMessageKey().split(":")[1],property.getMessageKey().split(":")[0])+" : "+code+"无效或不存在");
							}
							String fdModelPropertyId  = col.getString("fdModelPropertyId");
							String fdModelPropertyName  = col.getString("fdModelPropertyName");
							if(valdatePass) {
								obj.put(col.get("fdFieldId"),PropertyUtils.getProperty(list.get(0), fdModelPropertyId));
								obj.put(col.get("fdFieldName"),PropertyUtils.getProperty(list.get(0), fdModelPropertyName));
							}else {
								obj.put(col.get("fdFieldId"),"");
								obj.put(col.get("fdFieldName"),"");
							}
						}
					}
				}
			}
			if(!ArrayUtil.isEmpty(message.getMoreMessages())){
				message.addFailMsg(ResourceUtil.getString("message.validation.rowNum","eop-basedata").replace("{0}", String.valueOf(i)));
				messages.add(message);
			}
			data.add(obj);
		}
		Map<String,Object> rtn = new HashMap<String,Object>();
		rtn.put("data", data);
		rtn.put("cols", cols);
		rtn.put("messages", messages);
		return rtn;
	}
	
	private IEopBasedataDetailImportValidator getImportValidator(String type,String model) {
		IExtension[] exts =Plugin.getExtensions("com.landray.kmss.eop.basedata.import.detail", "*");
		if(exts.length>0) {
			for(IExtension  ext:exts) {
				IConfigurationElement[] eles = ext.getChildren();
				String modelName="",typeName="",service="";
				for(IConfigurationElement  ele:eles) {
					if("model".equals(ele.getAttribute("name"))) {
						modelName = ele.getAttribute("value");
					}
					if("type".equals(ele.getAttribute("name"))) {
						typeName = ele.getAttribute("value");
					}
					if("service".equals(ele.getAttribute("name"))) {
						service = ele.getAttribute("value");
					}
				}
				if(model.equals(modelName)&&type.equals(typeName)) {
					return (IEopBasedataDetailImportValidator) SpringBeanUtil.getBean(service);
				}
			}
		}
		return null;
	}
	private Integer getImportIndex(JSONArray _properties, SysDictCommonProperty property, JSONObject cols) {
		for(int i=0;i<_properties.size();i++) {
			JSONObject prop = _properties.getJSONObject(i);
			JSONObject col = cols.getJSONObject(property.getName());
			if(col!=null) {
				if(col.containsKey("fdFieldName")) {
					String fdFieldName = col.getString("fdFieldName");
					if(prop.getString("name").equals(fdFieldName)) {
						return prop.getInt("index");
					}
				}else if(prop.getString("name").equals(property.getName())){
					return prop.getInt("index");
				}
			}
		}
		return null;
	}
	private List<SysDictCommonProperty> getDetailProps(String modelName, String properties,JSONObject cols)  throws Exception {
		List<SysDictCommonProperty> rtn = new ArrayList<SysDictCommonProperty>();
		JSONArray propList = JSONArray.fromObject(properties);
		IBaseModel model = (IBaseModel) Class.forName(modelName).newInstance();
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		top:for(int i=0;i<propList.size();i++){
			JSONObject field = (JSONObject) propList.get(i);
			String name = field.getString("name");
			JSONObject col = new JSONObject();
			col.put("required", field.get("required"));
			if(dict.getPropertyMap().containsKey(name)){//包含当前属性
				SysDictCommonProperty property = dict.getPropertyMap().get(name);
				rtn.add(property);
				
				col.put("fdName", property.getName());
				col.put("fdType", property.getType());
				col.put("fdIsMulti", false);
				col.put("fdFieldName", property.getName());
				cols.put(property.getName(), col);
			}else{//不包含，则判断是否有类似属性，处理对象属性及对象列表属性
				ModelToFormPropertyMap mmap = model.getToFormPropertyMap();
				SysDictCommonProperty property = null;
				for(Iterator<String> it=(Iterator<String>) mmap.getPropertyMap().keySet().iterator();it.hasNext();) {
					String key = it.next();
					Object convertor = mmap.getPropertyMap().get(key);
					if(convertor instanceof String && convertor.equals(name)) {//Object property
						property = dict.getPropertyMap().get(key.split("\\.")[0]);
						rtn.add(property);
						break;
					}else if(convertor instanceof BaseModelConvertor){
						BaseModelConvertor mctx  =  (BaseModelConvertor) convertor;
						if(mctx instanceof ModelConvertor_ModelListToString) {//对象列表
							ModelConvertor_ModelListToString _ctx = (ModelConvertor_ModelListToString) mctx;
							String []sproperty = _ctx.getSPropertyName().split("\\:");//转换源属性
							String []tproperty = _ctx.getTPropertyName().split("\\:");//转换目标属性
							if(tproperty[1].equals(name)) {
								property = dict.getPropertyMap().get(key);
								col.put("fdName", property.getName());
								col.put("fdType", property.getType());
								col.put("fdIsMulti",true);
								col.put("fdFieldId",tproperty[0]);
								col.put("fdFieldName",tproperty[1]);
								col.put("fdModelPropertyId", sproperty[0]);
								col.put("fdModelPropertyName", sproperty[1]);
								rtn.add(property);
								cols.put(property.getName(), col);
								break top;
							}
						}
					}
				}
				if(property!=null) {
					for(Iterator<String> it=(Iterator<String>) mmap.getPropertyMap().keySet().iterator();it.hasNext();) {
						String key = it.next();
						if(key.startsWith(property.getName()+".")) {
							String value = (String) mmap.getPropertyMap().get(key);
							if(cols.containsKey(property.getName())) {
								col = cols.getJSONObject(property.getName());
							}
							col.put("fdName", property.getName());
							col.put("fdType", property.getType());
							col.put("fdIsMulti",false);
							if(key.contains("Id")) {
								col.put("fdFieldId",value);
								col.put("fdModelPropertyId", key.split("\\.")[1]);
							}else {
								col.put("fdFieldName",value);
								col.put("fdModelPropertyName", key.split("\\.")[1]);
							}
							cols.put(property.getName(), col);
						}
					}
				}
			}
		}
		return rtn;
	}
	@Override
	public void downloadDetailTemplate(HttpServletResponse response, String modelName, String properties)
			throws Exception {
		JSONObject cols = new JSONObject();
		List<SysDictCommonProperty> props = getDetailProps(modelName,properties,cols);
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		String filename = dict.getMessageKey();
		filename = ResourceUtil.getString(filename.split(":")[1],filename.split(":")[0]);
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		Workbook workBook = new HSSFWorkbook();
		String sheetName = new String(filename.getBytes("ISO8859-1"), "GBK");
		Sheet sheet = workBook.createSheet(sheetName);
		for (int i = 0; i <= props.size(); i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		CellStyle title = EopBasedataImportUtil.getTitleStyle(workBook);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(ResourceUtil.getString("page.serial"));
		cell.setCellStyle(title);
		int cellIndex = 1;
		for (int i = 0; i < props.size(); i++) {
			JSONObject col = cols.getJSONObject(props.get(i).getName());
			String val = props.get(i).getMessageKey();
			cell = row.createCell(cellIndex++);
			String msg = ResourceUtil.getString(val.split(":")[1],val.split(":")[0]);
			if(props.get(i).getType().indexOf(".")==-1) {//simple property
				if(col.getBoolean("required")) {
					msg += ResourceUtil.getString("py.required","eop-basedata");
				}
			}
			cell.setCellValue(msg);
			cell.setCellStyle(title);
			if(props.get(i) instanceof SysDictListProperty||props.get(i) instanceof SysDictModelProperty){
				val = props.get(i).getMessageKey();
				cell = row.createCell(cellIndex++);
				msg = ResourceUtil.getString(val.split(":")[1],val.split(":")[0])+"编码";
				if(col.getBoolean("required")) {
					msg += ResourceUtil.getString("py.required","eop-basedata");
				}
				cell.setCellValue(msg);
				cell.setCellStyle(title);
			}
		}
		workBook.write(os);
		os.flush();
		os.close();
	}
	
	
	public JSONObject bankOpenOrClose() throws Exception {
		JSONObject obj=new JSONObject();
		String sql="select eopBasedataSwitch.fdValue from com.landray.kmss.eop.basedata.model.EopBasedataSwitch eopBasedataSwitch where eopBasedataSwitch.fdProperty=:fdProperty ";
		Query query = getBaseDao().getHibernateSession().createQuery(sql);
		query.setParameter("fdProperty", "fdUseBank");
		List list = query.list();
		if(list.isEmpty()){
			obj.put("result", "");
		}else{
			String fdValue =    list.get(0)+"";
			obj.put("result", fdValue);
		}
		return obj;
	}
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String type=requestInfo.getParameter("type");
		List<Map<String,Object>> rtn = new ArrayList<Map<String,Object>>();
		if("getDetailImportCol".equals(type)) {
			String properties=requestInfo.getParameter("properties");
			String modelName=requestInfo.getParameter("modelName");
			JSONArray arr = JSONArray.fromObject(properties);
			JSONObject cols = new JSONObject();
			List<SysDictCommonProperty> props = getDetailProps(modelName,properties,cols);
			int index=1;
			for(int i=0;i<arr.size();i++) {
				JSONObject p = arr.getJSONObject(i);
				for(SysDictCommonProperty prop:props) {
					if(prop.getName().equals(p.get("name"))) {//simple property
						p.put("index", index);
						break;
					}else if(cols.containsKey(prop.getName())) {
						JSONObject col = cols.getJSONObject(prop.getName());
						if(col.containsKey("fdFieldName")&&col.getString("fdFieldName").equals(p.get("name"))) {//Object or List
							p.put("index", index);
							index++;
							break;
						}
					}
				}
				index++;
			}
			Map<String,Object> map= new HashMap<String,Object>();
			map.put("properties", arr.toString());
			rtn.add(map);
		}
		if("getModleDisplayProperty".equals(type)) {
			String modelName = requestInfo.getParameter("modelName");
			try {
				SysDictModel model = SysDataDict.getInstance().getModel(modelName);
				Map<String,Object> map= new HashMap<String,Object>();
				map.put("displayName", model.getDisplayProperty());
				rtn.add(map);
			} catch (Exception e) {
			}
		}
		return rtn;
	}
	
}
