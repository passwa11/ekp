package com.landray.kmss.sys.webservice2.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictAttachmentProperty;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.util.FormModelAttrsUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysWebserviceDictPropertySelectList implements IXMLDataBean {
	// private static final String EXTENSION_POINT_XFROM =
	// "com.landray.kmss.sys.xform";
	//
	// private static final String EXTENSION_POINT_PROPERTY =
	// "com.landray.kmss.sys.property.setting";
	//
	// private static final String EXTENSION_POINT_SUBFLOW =
	// "com.landray.kmss.sys.workflow.support.oa.subprocess";
	//
	// private static final String extension_item_dict = "config";

	// 需搭配SysWebserviceRestConfigAction的form 对机制的messagekey进行特殊处理
	@Override
	public List<Map<String, String>> getDataList(RequestContext requestInfo) throws Exception {
		String modelName = requestInfo.getParameter("modelName");
		List<Map<String, String>> rtnVal = new ArrayList<Map<String, String>>();
		if (StringUtil.isNull(modelName)) {
            return rtnVal;
        }
		// Object tmpObj = ClassUtils.forName(modelName).newInstance();
		// // 点评机制
		// if (tmpObj instanceof ISysEvaluationModel) {
		// Map<String, String> node = new HashMap<String, String>();
		// node.put("propertyName", "evaluation");
		// node.put("propertyText",
		// ResourceUtil.getString("pdaModuleConfigView.evaluation",
		// "third-pda"));
		// node.put("propertyType", "evaluation");
		// rtnVal.add(node);
		// }
		// // 推荐机制
		// if (tmpObj instanceof ISysIntroduceModel) {
		// Map<String, String> node = new HashMap<String, String>();
		// node.put("propertyName", "introduce");
		// node.put("propertyText",
		// ResourceUtil.getString("pdaModuleConfigView.introduce",
		// "third-pda"));
		// node.put("propertyType", "introduce");
		// rtnVal.add(node);
		// }
		// // 关联机制
		// if (tmpObj instanceof ISysRelationMainModel) {
		// Map<String, String> node = new HashMap<String, String>();
		// node.put("propertyName", "relation");
		// node.put("propertyText",
		// ResourceUtil.getString("pdaModuleConfigView.relation", "third-pda"));
		// node.put("propertyType", "relation");
		// rtnVal.add(node);
		// }
		// // 流程节点
		// SysCfgFlowDef flowDef =
		// SysConfigs.getInstance().getFlowDefByMain(modelName);
		// if (flowDef != null) {
		// Map<String, String> node = new HashMap<String, String>();
		// node.put("propertyName", "flowDef");
		// node.put("propertyText",
		// ResourceUtil.getString("pdaModuleConfigView.flowDef", "third-pda"));
		// node.put("propertyType", "flowDef");
		// node.put("modelName", flowDef.getModelName());
		// node.put("templateModelName", flowDef.getTemplateModelName());
		// node.put("key", flowDef.getKey());
		// node.put("templatePropertyName", flowDef.getTemplatePropertyName());
		// node.put("moduleMessageKey", flowDef.getModuleMessageKey());
		// node.put("type", flowDef.getType());
		// rtnVal.add(node);
		// Map<String, String> node2 = new HashMap<String, String>();
		// node2.putAll(node);
		// node2.put("propertyName", "flowlog");
		// node2.put("propertyText", "流程日志");
		// node2.put("propertyType", "flowlog");
		// rtnVal.add(node2);
		// }
		//
		// // 流程表单
		// IExtension[] extensions = Plugin.getExtensions(EXTENSION_POINT_XFROM,
		// modelName, extension_item_dict);
		// if (extensions != null && extensions.length > 0) {
		// for (IExtension ext : extensions) {
		// Map<String, String> node = new HashMap<String, String>();
		// node.put("propertyName", "xform");
		// node.put("propertyText",
		// ResourceUtil.getString("pdaModuleConfigView.xform", "third-pda"));
		// node.put("propertyType", "xform");
		// node.put("template", (String) Plugin.getParamValueString(ext,
		// "template"));
		// node.put("key", (String) Plugin.getParamValueString(ext, "key"));
		// rtnVal.add(node);
		// }
		// }

		FormModelAttrsUtil attrUtil = new FormModelAttrsUtil();

		SysDictModel model = SysDataDict.getInstance().getModel(modelName);

		Map relationMap = attrUtil.getAttrsRelation(modelName);
		// 数据字典属性列表

		if (relationMap != null && relationMap.size() > 0) {
			List<SysDictCommonProperty> dictPropertyList = model.getPropertyList();
			for (SysDictCommonProperty dictProperty : dictPropertyList) {
				if (StringUtil.isNull(dictProperty.getMessageKey())) {
                    continue;
                }
				String fdPropertyText = ResourceUtil.getString(dictProperty.getMessageKey(), requestInfo.getLocale());
				Map<String, String> node = new HashMap<String, String>();
				// messageKey对应的中文意思不为空的字段
				if (StringUtil.isNull(fdPropertyText) || !relationMap.containsKey(dictProperty.getName())) {
                    node.put("isFormMapping", "false");
                } else {
                    node.put("isFormMapping", "true");
                }

				if (dictProperty instanceof SysDictListProperty) {
                    node.put("isList", "true");
                } else {
                    node.put("isList", "false");
                }

				node.put("propertyName", dictProperty.getName());
				node.put("propertyText", fdPropertyText);
				node.put("propertyType", dictProperty.getType());
				node.put("messageKey", dictProperty.getMessageKey());
				node.put("enumType", dictProperty.getEnumType());
				rtnVal.add(node);
			}

			// 数据字典附件列表
			List<SysDictAttachmentProperty> attPropertyList = model.getAttachmentPropertyList();
			for (SysDictAttachmentProperty attProperty : attPropertyList) {
				String fdPropertyText = ResourceUtil.getString(attProperty.getMessageKey(), requestInfo.getLocale());
				if (StringUtil.isNull(fdPropertyText) || !relationMap.containsKey("attachmentForms")) {
                    continue;
                }
				Map<String, String> node = new HashMap<String, String>();
				node.put("propertyName", attProperty.getName());
				node.put("propertyText", fdPropertyText);
				node.put("propertyType", "attachment");
				node.put("messageKey", attProperty.getMessageKey());

				rtnVal.add(node);
			}
		}
		// // 属性筛选
		// IExtension[] propertyExtensions =
		// Plugin.getExtensions(EXTENSION_POINT_PROPERTY, modelName);
		// if (propertyExtensions != null) {
		// if (propertyExtensions.length > 0) {
		// Map<String, String> node = new HashMap<String, String>();
		// node.put("propertyName", "property");
		// node.put("propertyText",
		// ResourceUtil.getString("pdaModuleConfigView.property", "third-pda"));
		// node.put("propertyType", "property");
		// rtnVal.add(node);
		// }
		// }
		//
		// // 父子流程
		// IExtension[] subExtensions =
		// Plugin.getExtensions(EXTENSION_POINT_SUBFLOW, "*");
		// if (Plugin.getExtension(subExtensions, "modelName", modelName) !=
		// null) {
		// Map<String, String> node = new HashMap<String, String>();
		// node.put("propertyName", "subflow");
		// node.put("propertyText",
		// ResourceUtil.getString("pdaModuleConfigView.subflow", "third-pda"));
		// node.put("propertyType", "subflow");
		// rtnVal.add(node);
		// }

		return rtnVal;
	}
}
