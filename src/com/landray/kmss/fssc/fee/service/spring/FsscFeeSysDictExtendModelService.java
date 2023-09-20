package com.landray.kmss.fssc.fee.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeTemplateService;
import com.landray.kmss.sys.config.dict.SysDictAttachmentProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendElementProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSimpleProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class FsscFeeSysDictExtendModelService implements IXMLDataBean {
	private DictLoadService loader;

	/**
	 * 数据字典加载类
	 */
	public void setLoader(DictLoadService loader) {
		this.loader = loader;
	}
	//TODO
	private IFsscFeeTemplateService fsscFeeTemplateService;

	public void setFsscFeeTemplateService(IFsscFeeTemplateService fsscFeeTemplateService) {
		this.fsscFeeTemplateService = fsscFeeTemplateService;
	}

	private SysDictExtendModel getTemplateDict(String tempId) throws Exception {
		FsscFeeTemplate template = (FsscFeeTemplate) fsscFeeTemplateService
				.findByPrimaryKey(tempId);
		SysDictExtendModel dict = loader
				.loadTemplateExtDict(
						XFormUtil.getFileName(template, "fsscFeeMain"));
		return dict;
	}

	@Override
    public List<Map<String, Object>> getDataList(RequestContext requestInfo)
			throws Exception {
		List<Map<String, Object>> rtn = new ArrayList<Map<String, Object>>();
		String tempId = requestInfo.getParameter("tempId");
		String tempType = requestInfo.getParameter("tempType");
		SysDictExtendModel dict = null;
		if ("template".equals(tempType)) {
			dict = getTemplateDict(tempId);
		} else if ("file".equals(tempType)) {
			dict = loader.loadDictByFileName(requestInfo
					.getParameter("fileName"));
		}
		if (dict == null) {
			return rtn;
		}
		List<?> properties = dict.getPropertyList();
		addProperties(rtn, properties);

		addAttachmentProperty(rtn, dict);
		return rtn;
	}

	private void addAttachmentProperty(List<Map<String, Object>> rtnVal,
			SysDictModel model) {
		List<?> properties = model.getAttachmentPropertyList();
		for (int i = 0; i < properties.size(); i++) {
			SysDictAttachmentProperty property = (SysDictAttachmentProperty) properties
					.get(i);
			if (!property.isCanDisplay()) {
				continue;
			}
			String label = property.getLabel();
			if (StringUtil.isNull(label)) {
				label = ResourceUtil.getString(property.getMessageKey());
			}
			if (StringUtil.isNull(label)) {
				continue;
			}
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("name", property.getName());
			node.put("label", label);
			node.put("type", "Attachment");
			rtnVal.add(node);
		}
	}

	private void addProperties(List<Map<String, Object>> rtn,
			List<?> properties) {
		for (int i = 0; i < properties.size(); i++) {
			Object p = properties.get(i);
			if (!(p instanceof SysDictExtendProperty)) {
				continue;
			}
			SysDictExtendProperty property = (SysDictExtendProperty) p;
			Map<String, Object> obj = new HashMap<String, Object>(4);
			rtn.add(obj);
			obj.put("name", property.getName());
			obj.put("label", property.getLabel());
			obj.put("type", property.getType());
			if (p instanceof SysDictExtendSimpleProperty) {
				if ("RTF".equals(((SysDictExtendSimpleProperty) p).getType())) {
					obj.put("len", "0");
				} else {

					obj.put("len",
							((SysDictExtendSimpleProperty) p).getLength());
				}
			}
			if (p instanceof SysDictExtendElementProperty) {
				SysDictExtendProperty ss = ((SysDictExtendProperty) p);
				if (ss.getMutiValueSplit() != null) {
					obj.put("len", 0);
				} else {
					obj.put("len", 36);
				}
			}

			if (property instanceof SysDictExtendSubTableProperty) {
				obj.put("type", "java.util.List");// TODO 待验证
				SysDictExtendSubTableProperty subTable = (SysDictExtendSubTableProperty) property;
				List<?> subProperties = subTable.getElementDictExtendModel()
						.getPropertyList();
				if (!ArrayUtil.isEmpty(subProperties)) {
					for (int j = 0; j < subProperties.size(); j++) {
						Object cp = subProperties.get(j);
						if (!(cp instanceof SysDictExtendProperty)) {
							continue;
						}
						SysDictExtendProperty cpp = (SysDictExtendProperty) cp;
						Map<String, Object> child = new HashMap<String, Object>(
								4);
						child.put("name",
								property.getName() + "." + cpp.getName());
						child.put("label",
								property.getLabel() + "." + cpp.getLabel());
						child.put("type", cpp.getType() + "[]");
						if (cp instanceof SysDictExtendSimpleProperty) {
							if ("RTF"
									.equals(((SysDictExtendSimpleProperty) cp).getType())) {
								child.put("len", "0");
							} else {
								child.put(
										"len",
										property.getName()
												+ "."
												+ ((SysDictExtendSimpleProperty) cp)
														.getLength());
							}
						}
						if (cp instanceof SysDictExtendElementProperty) {
							SysDictExtendProperty ss = ((SysDictExtendProperty) cp);
							if (ss.getMutiValueSplit() != null) {
								child.put("len", 0);
							} else {
								child.put("len", 36);
							}
						}
						rtn.add(child);
					}
				}
			}
		}
	}
}
