package com.landray.kmss.third.weixin.mutil.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdWxUtil{

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

	public static SysOrgElement cloneBean(SysOrgElement ele) {
		SysOrgElement ele2 = new SysOrgElement();
		if (ele instanceof SysOrgPerson) {
			SysOrgPerson person2 = new SysOrgPerson();
			SysOrgPerson person = (SysOrgPerson) ele;
			person2.setFdDefaultLang(person.getFdDefaultLang());
			person2.setFdEmail(person.getFdEmail());
			person2.setFdHiredate(person.getFdHiredate());
			person2.setFdIsActivated(person.getFdIsActivated());
			person2.setFdLoginName(person.getFdLoginName());
			person2.setFdMobileNo(person.getFdMobileNo());
			person2.setFdNickName(person.getFdNickName());
			person2.setFdSex(person.getFdSex());
			person2.setFdShortNo(person.getFdShortNo());
			person2.setFdStaffingLevel(person.getFdStaffingLevel());
			person2.setFdUserType(person.getFdUserType());
			person2.setFdWechatNo(person.getFdWechatNo());
			person2.setFdWorkPhone(person.getFdWorkPhone());
			ele2 = person2;
		}

		ele2.setFdId(ele.getFdId());
		ele2.setFdName(ele.getFdName());
		ele2.setCustomPropMap(ele.getCustomPropMap());
		ele2.setDynamicMap(ele.getDynamicMap());
		ele2.setFdAlterTime(ele.getFdAlterTime());
		ele2.setFdCreateTime(ele.getFdCreateTime());
		ele2.setFdFlagDeleted(ele.getFdFlagDeleted());
		ele2.setFdHierarchyId(ele.getFdHierarchyId());
		ele2.setFdGroups(ele.getFdGroups());
		ele2.setFdIsAbandon(ele.getFdIsAbandon());
		ele2.setFdIsAvailable(ele.getFdIsAvailable());
		ele2.setFdIsBusiness(ele.getFdIsBusiness());
		ele2.setFdKeyword(ele.getFdKeyword());
		ele2.setFdMemo(ele.getFdMemo());
		ele2.setFdNamePinYin(ele.getFdNamePinYin());
		ele2.setFdNameSimplePinyin(ele.getFdNameSimplePinyin());
		ele2.setFdNo(ele.getFdNo());
		ele2.setFdOrder(ele.getFdOrder());
		ele2.setFdOrgEmail(ele.getFdOrgEmail());
		ele2.setFdOrgType(ele.getFdOrgType());
		ele2.setFdParent(ele.getFdParent());
		ele2.setFdPersons(ele.getFdPersons());
		ele2.setFdPosts(ele.getFdPosts());
		return ele2;
	}
}
