package com.landray.kmss.fssc.eas.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.IFormToModelConvertor;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.fssc.eas.model.FsscEasParam;
import com.landray.kmss.fssc.eas.model.FsscEasSwitch;
import com.landray.kmss.fssc.eas.service.IFsscEasParamService;
import com.landray.kmss.fssc.eas.service.IFsscEasSwitchService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscEasUtil{

	protected static IFsscEasSwitchService fsscEasSwitchService;

	public static IFsscEasSwitchService getFsscEasSwitchService() {
		if(fsscEasSwitchService==null){
			fsscEasSwitchService=(IFsscEasSwitchService) SpringBeanUtil.getBean("fsscEasSwitchService");
		}
		return fsscEasSwitchService;
	}
	
	protected static IFsscEasParamService fsscEasParamService;

	public static IFsscEasParamService getFsscEasParamService() {
		if(fsscEasParamService==null){
			fsscEasParamService=(IFsscEasParamService) SpringBeanUtil.getBean("fsscEasParamService");
		}
		return fsscEasParamService;
	}

	public static void initModelFromRequest(IBaseModel model,
			RequestContext request) throws Exception {
		if(request.getParameterMap()==null){
			return;
		}
		String modelName = ModelUtil.getModelClassName(model);
		SysDataDict dataDict = SysDataDict.getInstance();
		Map<String, SysDictCommonProperty> propMap = dataDict
				.getModel(modelName).getPropertyMap();
		for (Entry<String, String[]> entry : request.getParameterMap()
				.entrySet()) {
			// key以i.开头
			String key = entry.getKey();
			if (key.length() < 3 || !key.startsWith("i.")) {
				continue;
			}
			String[] values = entry.getValue();
			if (values == null || values.length == 0
					|| StringUtil.isNull(values[0])) {
				continue;
			}
			// 属性可写
			String propName = key.substring(2);
			if (!PropertyUtils.isWriteable(model, propName)) {
				continue;
			}
			// 数据字典为简单类型或对象类型
			SysDictCommonProperty prop = propMap.get(propName);
			if (prop == null || prop.isReadOnly() || !prop.isCanDisplay()) {
				continue;
			}
			if (prop instanceof SysDictSimpleProperty) {
				BeanUtils.copyProperty(model, propName, values[0]);
			} else if (prop instanceof SysDictModelProperty) {
				SysDictModel dictModel = dataDict.getModel(prop.getType());
				if (dictModel == null) {
					continue;
				}
				// 获取数据
				IBaseService service = null;
				String beanName = dictModel.getServiceBean();
				if (StringUtil.isNotNull(beanName)) {
					service = (IBaseService) SpringBeanUtil.getBean(beanName);
				}
				if (service == null) {
					continue;
				}
				IBaseModel value = service.findByPrimaryKey(values[0]);
				BeanUtils.copyProperty(model, propName, value);
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static String buildCriteria(String serviceBean, String selectBlock,
			String whereBlock, String orderBy) throws Exception {
		// 查询
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(serviceBean);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_EDITOR);
		List rtnList = service.findValue(hqlInfo);
		JSONArray result = new JSONArray();
		for (Object rtnVal : rtnList) {
			if (rtnVal == null) {
				continue;
			}
			String optValue = null;
			String optText = null;
			if (rtnVal instanceof Object[]) {
				Object[] rtnVals = (Object[]) rtnVal;
				if (rtnVals.length == 0 || rtnVals[0] == null) {
					continue;
				}
				optValue = rtnVals[0].toString();
				if (rtnVals.length > 1 && rtnVals[1] != null) {
					optText = rtnVals[1].toString();
				} else {
					optText = optValue;
				}
			} else {
				optValue = rtnVal.toString();
				optText = optValue;
			}
			JSONObject json = new JSONObject();
			json.put("value", optValue);
			json.put("text", optText);
			result.add(json);
		}
		return result.toString();
	}
	
	@SuppressWarnings("rawtypes")
    public static final void convert(IBaseService baseService, IExtendForm $baseForm, 
            IBaseModel $baseModel,String[] props, RequestContext requestContext) throws Exception{
        if(props==null || props.length<1){
            return;
        }
        ConvertorContext context = new ConvertorContext();
        context.setBaseService(baseService);
        context.setSObject($baseForm);
        context.setTObject($baseModel);
        context.setObjMap(new HashMap());
        context.setRequestContext(requestContext);
        Map propertyMap = $baseForm.getToModelPropertyMap().getPropertyMap();
        for(String prop:props){
            if(prop==null||prop.trim().length()<1){
                continue;
            }
            IFormToModelConvertor convertor = (IFormToModelConvertor)propertyMap.get(prop);
            if(convertor==null){
                convertor = new FormConvertor_Common(prop);
            }
            context.setSPropertyName(prop);
            convertor.excute(context);
        }
    }


	/** 获取对应的属性键值对
	 * @param key
	 * **/
	@SuppressWarnings({ "unchecked" })
	public static final Map<String,String> getSwitchValue(String key) throws Exception{
		Map<String, String> resultMap = new ConcurrentHashMap<>();
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();
		if (StringUtil.isNotNull(key)) {
			whereBlock.append(" fsscEasSwitch.fdProperty=:fdProperty");
			hqlInfo.setParameter("fdProperty", key);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<FsscEasSwitch> switchList = getFsscEasSwitchService().findList(hqlInfo);
		for (FsscEasSwitch fsscEasSwitch : switchList) {
			String value = fsscEasSwitch.getFdValue();
			resultMap.put(fsscEasSwitch.getFdProperty(), StringUtil.isNotNull(value) ? value : "");
		}
		return resultMap;
	}
	
	/** 获取对应的属性键值对
	 * @param key
	 * **/
	@SuppressWarnings({ "unchecked" })
	public static final Map<String,String> getParamValue(String key) throws Exception{
		Map<String, String> resultMap = new ConcurrentHashMap<>();
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();
		if (StringUtil.isNotNull(key)) {
			whereBlock.append(" fsscEasParam.fdProperty=:fdProperty");
			hqlInfo.setParameter("fdProperty", key);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<FsscEasParam> paramList = getFsscEasParamService().findList(hqlInfo);
		for (FsscEasParam fsscEasParam : paramList) {
			String value = fsscEasParam.getFdValue();
			resultMap.put(fsscEasParam.getFdProperty(), StringUtil.isNotNull(value) ? value : "");
		}
		return resultMap;
	}
}
