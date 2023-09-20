package com.landray.kmss.km.review.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.elec.core.signature.enums.EqbXFormFieldEnum;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.ElecContractInfo;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewEqbSignService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KmReviewEqbSignListener implements IEventListener {

	private static final Logger logger = LoggerFactory.getLogger(KmReviewEqbSignListener.class);

	private static final String SIGN_TYPE_HOST="host";

	private static final String SIGN_TYPE_SAAS="saas";

	private IKmReviewEqbSignService kmReviewEqbSignService;

	private IKmReviewEqbSignService getKmReviewEqbSignService() {
		if (kmReviewEqbSignService == null) {
			kmReviewEqbSignService = (IKmReviewEqbSignService) SpringBeanUtil.getBean("kmReviewEqbSignService");
		}
		return kmReviewEqbSignService;
	}

	private IKmReviewEqbSignService kmReviewEqbSaasSignService;

	private IKmReviewEqbSignService getKmReviewEqbSaasSignService() {
		if (kmReviewEqbSaasSignService == null) {
			kmReviewEqbSaasSignService = (IKmReviewEqbSignService) SpringBeanUtil.getBean("kmReviewEqbSaasSignService");
		}
		return kmReviewEqbSaasSignService;
	}

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getMainModel() instanceof KmReviewMain) {
			logger.debug("receive KmReviewEqbSignListener,parameter=" + parameter);
			JSONObject params = JSONObject.parseObject(parameter);
			KmReviewMain mainModel = (KmReviewMain) execution.getMainModel();
			Map<String, Object> fieldValues = getFieldValues(params, mainModel);
			// 签署前文件列表List<SysAttMain>
			Object beforeFileListObj = fieldValues.get(EqbXFormFieldEnum.FD_EQB_SIGN_BEFORE_FILES.getFieldId());
			// 签署后文件列表
			//Object afterFileListObj = fieldValues.get(EqbXFormFieldEnum.FD_EQB_SIGN_AFTER_FILES.getFieldId());
			// 签署明细表信息List<Map>
			Object signDetailObj = fieldValues.get(EqbXFormFieldEnum.FD_EQB_SIGN_DETAIL.getFieldId());

			/*
			 * [{fd_eqb_sign_org={name=深圳市南山区林宇超机构, id=178b5287e4f2d66b866a8db46379e30e},
			 * fd_eqb_sign_stand=20, fd_eqb_contract_mobile=13025320039,
			 * fd_eqb_signer_type=10, fd_eqb_sign_desc=备注2, fd_eqb_sign_user={name=林宇超,
			 * id=16c1d9978463e28336e60d846618fe81}}]
			 */
			if (beforeFileListObj == null) {
				logger.info("签署文件为空,不创建E签宝签署流程");
				return;
			}
			if (signDetailObj == null) {
				logger.info("签署明细为空,不创建E签宝签署流程");
				return;
			}
			String eqbSignType = (String)fieldValues.get("eqbSignType");//签署方式
			logger.info("eqbSignType = {}", eqbSignType);
			if(StringUtil.isNull(eqbSignType)){
				eqbSignType = SIGN_TYPE_HOST;
			}

			List<JSONObject> signDetails = coverDetailInfo((List<Map>) signDetailObj,eqbSignType);
			ElecChannelResponseMessage<ElecContractInfo> responseMessage = new ElecChannelResponseMessage<ElecContractInfo>();
			if(SIGN_TYPE_HOST.equals(eqbSignType)){
				responseMessage= (ElecChannelResponseMessage<ElecContractInfo>) getKmReviewEqbSignService()
						.sendEqb(mainModel, signDetails, (List<SysAttMain>) beforeFileListObj);
			}else if(SIGN_TYPE_SAAS.equals(eqbSignType)){
				responseMessage = (ElecChannelResponseMessage<ElecContractInfo>) getKmReviewEqbSaasSignService()
						.sendEqb(mainModel, signDetails, (List<SysAttMain>) beforeFileListObj);
			}

			if (responseMessage.hasError()) {
				logger.info("调用签署接口失败:"
						+ (StringUtil.isNotNull(responseMessage.getDesc()) ? responseMessage.getDesc() : ""));
				return;
			}

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> getFieldValues(JSONObject params, IBaseModel mainModel) throws Exception {
		Map fieldValues = new HashMap();
		try {
			IExtendDataModel model = (IExtendDataModel) mainModel;

			ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil.getBean("sysMetadataParser");
			SysDictModel dictModel = sysMetadataParser.getDictModel(mainModel);
			Iterator iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Entry) iterator.next();
				if (entry.getValue() == null || "".equals(entry.getValue())) {
					continue;
				}
				JSONObject fieldObj = (JSONObject) JSONObject.toJSON(entry.getValue());
				if (fieldObj == null) {
					continue;
				}
				String propertyName = fieldObj.getString("value");
				Map<String, SysDictCommonProperty> propertyMap = dictModel.getPropertyMap();
				SysDictExtendProperty eqbProperty = (SysDictExtendProperty) propertyMap.get(propertyName);
				String type = fieldObj.getString("model");
				if ("eqb".equals(type)) {
					JSONObject customProperties = JSONObject.parseObject(eqbProperty.getCustomElementProperties());
					String signFile = customProperties.getString("signFile");// 文件更新方式0：显示保存过程文件， 1：显示保存最终文件。
					Map map = mainModel.getCustomPropMap();
					map.put("signFileUpdateType", signFile);// 签署文件更新配置
					JSONArray childInfos = (JSONArray) JSONArray.toJSON(customProperties.get("childInfos"));
					/*E签宝  签署方式 start*/
					String eqbSignType = (String)customProperties.get("eqbSignType");
					fieldValues.put("eqbSignType",eqbSignType);
					/*E签宝  签署方式 end*/
					for (Object childObj : childInfos) {
						JSONObject childJsonObj = (JSONObject) childObj;
						String controlType = childJsonObj.getString("type");
						String id = childJsonObj.getString("id");
						if ("attachment".equals(controlType)) {
							List attachmentValues = getAttachmentValues(model, id);
							fieldValues.put(id, attachmentValues);
						} else {
							Object fieldValue = sysMetadataParser.getFieldValue(model, id, true);
							fieldValue = formatFieldValues(id, dictModel, fieldValue);
							fieldValues.put(id, fieldValue);
						}
					}
				}
			}
			return fieldValues;
		} catch (Exception e) {
			logger.error("无法获取值: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object formatFieldValues(String propertyName, SysDictModel dictModel, Object fieldValue) {
		SysDictCommonProperty sysDictCommonProperty = dictModel.getPropertyMap().get(propertyName);
		List<String> dictExtendProperties = new ArrayList<String>();
		if (sysDictCommonProperty instanceof SysDictExtendSubTableProperty) {
			SysDictExtendSubTableProperty dictSubTableProperty = (SysDictExtendSubTableProperty) sysDictCommonProperty;
			SysDictExtendModel subTableDictModel = dictSubTableProperty.getElementDictExtendModel();
			List<SysDictCommonProperty> propertyList = subTableDictModel.getPropertyList();
			for (SysDictCommonProperty dictCommonProperty : propertyList) {
				if (dictCommonProperty instanceof SysDictExtendProperty) {
					dictExtendProperties.add(dictCommonProperty.getName());
				}
			}
			List subTableValues = (List) fieldValue;
			List rtnValues = new ArrayList();
			for (int i = 0; i < subTableValues.size(); i++) {
				Map rowValue = (Map) subTableValues.get(i);
				Map newRowValue = new HashMap();
				for (String childPropertyName : dictExtendProperties) {
					newRowValue.put(childPropertyName, rowValue.get(childPropertyName));
				}
				rtnValues.add(newRowValue);
			}
			return rtnValues;
		}
		return fieldValue;
	}

	@SuppressWarnings("rawtypes")
	private List getAttachmentValues(IBaseModel model, String fdKey) throws Exception {
		ISysAttMainCoreInnerService sysAttachmentService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		String modelName = ModelUtil.getModelClassName(model);
		String modelId = model.getFdId();
		List sysAttMains = sysAttachmentService.findByModelKey(modelName, modelId, fdKey);
		return sysAttMains;
	}

	/**
	 * 转换签署信息
	 * 
	 * @param signDetailObj
	 * @return
	 */
	private List<JSONObject> coverDetailInfo(List<Map> signDetailObj,String eqbSignType) {
		/*
		 * [{fd_eqb_sign_org={name=深圳市南山区林宇超机构, id=178b5287e4f2d66b866a8db46379e30e},
		 * fd_eqb_sign_stand=20, fd_eqb_contract_mobile=13025320039,
		 * fd_eqb_signer_type=10, fd_eqb_sign_desc=备注2, fd_eqb_sign_user={name=林宇超,
		 * id=16c1d9978463e28336e60d846618fe81}}]
		 */
		List<JSONObject> list = new ArrayList<>();
		for (Map map : signDetailObj) {
			JSONObject signDetail = new JSONObject();
			signDetail.put("signType", map.get(EqbXFormFieldEnum.FD_EQB_SIGNER_TYPE.getFieldId()));
			Object userInfo = map.get(EqbXFormFieldEnum.FD_EQB_SIGN_USER.getFieldId());
			if (userInfo != null) {
				signDetail.put("userId", ((Map) userInfo).get("id"));
			}
			signDetail.put("contractMobile", map.get(EqbXFormFieldEnum.FD_EQB_CONTRACT_MOBILE.getFieldId()));
			signDetail.put("signStand", map.get(EqbXFormFieldEnum.FD_EQB_SIGN_STAND.getFieldId()));
			signDetail.put("signDesc", map.get(EqbXFormFieldEnum.FD_EQB_SIGN_DESC.getFieldId()));
			/*签署人证件号码  主体证件号码 */
			signDetail.put("signUserCard", map.get(EqbXFormFieldEnum.FD_EQB_SIGN_USER_CAED.getFieldId()));
			signDetail.put("signOrgCard", map.get(EqbXFormFieldEnum.FD_EQB_SIGN_ORG_CARD.getFieldId()));
			/*签署人证件号码  主体证件号码 */
			if ("10".equals(map.get(EqbXFormFieldEnum.FD_EQB_SIGNER_TYPE.getFieldId()))) {
				Object priseInfo = map.get(EqbXFormFieldEnum.FD_EQB_SIGN_ORG.getFieldId());
				if (userInfo != null) {
					signDetail.put("enterPriseId", ((Map) priseInfo).get("id"));
				}
			}
			signDetail.put("signOrder", list.size() + 1);
			/*E签宝 签署方式 start*/
			if(StringUtil.isNull(eqbSignType)){
				signDetail.put("eqbSignType",SIGN_TYPE_HOST);
			}else{
				signDetail.put("eqbSignType",eqbSignType);
			}
			/*E签宝 签署方式 end*/
			list.add(signDetail);
		}
		return list;
	}

}
