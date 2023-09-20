package com.landray.kmss.third.pda.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.convertor.IModelToFormConvertor;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.model.PdaModuleCate;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaModuleConfigView;
import com.landray.kmss.third.pda.model.PdaModuleLabelView;
import com.landray.kmss.third.pda.service.IPdaDataShowService;
import com.landray.kmss.third.pda.service.IPdaModuleCateService;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.third.pda.service.IPdaModuleConfigViewService;
import com.landray.kmss.third.pda.util.ImageFilterUtil;
import com.landray.kmss.third.pda.util.PdaPlugin;
import com.landray.kmss.third.pda.util.StrutsAndDictCfgUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PdaDataShowServiceImp implements IPdaDataShowService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PdaDataShowServiceImp.class);

	private IPdaModuleConfigViewService viewCfgService = null;

	public void setViewCfgService(IPdaModuleConfigViewService viewCfgService) {
		this.viewCfgService = viewCfgService;
	}

	private IPdaModuleConfigMainService configMainService = null;

	public void setConfigMainService(
			IPdaModuleConfigMainService configMainService) {
		this.configMainService = configMainService;
	}

	private String contextPath = null;

	/**
	 * 获取模块列表
	 */
	@Override
	public List getPdaModuleList() throws Exception {
		HQLInfo info = new HQLInfo();
		info
				.setSelectBlock("pdaModuleConfigMain.fdId,pdaModuleConfigMain.fdName,pdaModuleConfigMain.fdIconUrl");
		info
				.setWhereBlock("pdaModuleConfigMain.fdStatus=:status and pdaModuleConfigMain.fdSubMenuType<>:type");
		info.setOrderBy("pdaModuleConfigMain.fdOrder asc");
		info.setParameter("status", "1");
		info.setParameter("type", PdaModuleConfigConstant.PDA_MENUS_APP);
		// 使用权限过滤
		info.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_READER);
		List list = configMainService.findList(info);
		List rtnList = null;
		if (list != null && list.size() > 0) {
			rtnList = new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				Map map = new HashMap();
				map.put("fdId", object[0]);
				map.put("fdName", object[1]);
				map.put("fdIconUrl", object[2]);
				rtnList.add(map);
			}
		}
		return rtnList;

	}

	/**
	 * 获取模块列表
	 */
	@Override
	public List getPdaModuleListByCate() throws Exception {
		List rtnListx = new ArrayList();
		List<PdaModuleCate> moduleCates = getModuleCates();
		for (PdaModuleCate moduleCate : moduleCates) {
			HQLInfo info = new HQLInfo();
			// info
			// .setSelectBlock("pdaModuleConfigMain.fdId,pdaModuleConfigMain.fdName,pdaModuleConfigMain.fdIconUrl");
			info
					.setWhereBlock("pdaModuleConfigMain.fdStatus=:status and pdaModuleConfigMain.fdSubMenuType <> :type and pdaModuleConfigMain.fdModuleCate.fdId = :fdCateId");
			info.setOrderBy("pdaModuleConfigMain.fdOrder asc");
			info.setParameter("status", "1");
			info.setParameter("type", PdaModuleConfigConstant.PDA_MENUS_APP);
			info.setParameter("fdCateId", moduleCate.getFdId());
			// 使用权限过滤
			info.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
					SysAuthConstant.AuthCheck.SYS_READER);
			List<PdaModuleConfigMain> list = configMainService.findList(info);
			if (list.size() > 0) {
				Map mapx = new HashMap();
				mapx.put("cateName", moduleCate.getFdName());
				List rtnList = null;
				if (list != null && list.size() > 0) {
					rtnList = new ArrayList();
					for (PdaModuleConfigMain pdaModuleConfigMain : list) {
						Map map = new HashMap();
						map.put("fdId", pdaModuleConfigMain.getFdId());
						map.put("fdName", pdaModuleConfigMain.getFdName());
						map.put("fdIconUrl", pdaModuleConfigMain.getFdIconUrl());
						rtnList.add(map);
					}
					mapx.put("list", rtnList);
				}
				rtnListx.add(mapx);
			}
		}
		// 添加没有设置分类的模块
		HQLInfo infox = new HQLInfo();
		// infox
		// .setSelectBlock("pdaModuleConfigMain.fdId,pdaModuleConfigMain.fdName,pdaModuleConfigMain.fdIconUrl");
		infox
				.setWhereBlock("pdaModuleConfigMain.fdStatus=:status and pdaModuleConfigMain.fdSubMenuType <>:type and pdaModuleConfigMain.fdModuleCate.fdId is null");
		infox.setOrderBy("pdaModuleConfigMain.fdOrder asc");
		infox.setParameter("status", "1");
		infox.setParameter("type", PdaModuleConfigConstant.PDA_MENUS_APP);
		// 使用权限过滤
		infox.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_READER);
		List<PdaModuleConfigMain> listx = configMainService.findList(infox);
		if (listx.size() > 0) {
			Map mapx = new HashMap();
			mapx.put("cateName", "other");
			List rtnList = new ArrayList();
			if (listx != null && listx.size() > 0) {
				for (PdaModuleConfigMain pdaModuleConfigMain : listx) {
					Map map = new HashMap();
					map.put("fdId", pdaModuleConfigMain.getFdId());
					map.put("fdName", pdaModuleConfigMain.getFdName());
					map.put("fdIconUrl", pdaModuleConfigMain.getFdIconUrl());
					rtnList.add(map);
				}
				mapx.put("list", rtnList);
			}
			rtnListx.add(mapx);
		}
		return rtnListx;
	}

	private List<PdaModuleCate> getModuleCates() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy(" pdaModuleCate.fdOrder asc");
		IPdaModuleCateService pdaModuleCateService = (IPdaModuleCateService) SpringBeanUtil
				.getBean("pdaModuleCateService");
		return pdaModuleCateService.findList(hqlInfo);
	}

	/**
	 * 获取系统所有设置有PDA功能的文档model
	 * 
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> getSupportPdaCfgMap() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setSelectBlock("pdaModuleConfigView.fdModelName,pdaModuleConfigView.fdName,pdaModuleConfigView.fdKeyword,pdaModuleConfigView.fdModule.fdUrlPrefix");
		hqlInfo.setWhereBlock("pdaModuleConfigView.fdModule.fdStatus='1'");
		List result = viewCfgService.findList(hqlInfo);
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			map.put((String) object[0], (String) object[1]);
			map.put((String) object[2], (String) object[1]);
		}
		return map;
	}

	/**
	 * 获取系统所有pda支持的模块前缀
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<String> getSupportPdaModulesPrefix() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(100);
		hqlInfo.setSelectBlock("pdaModuleConfigMain.fdUrlPrefix");
		hqlInfo.setWhereBlock("pdaModuleConfigMain.fdStatus='1'");
		List result = configMainService.findList(hqlInfo);
		Set<String> set = new HashSet<String>();
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			String fdUrlPrefix = (String) iterator.next();
			if (StringUtil.isNotNull(fdUrlPrefix)) {
				set.add(fdUrlPrefix);
			}
		}
		return set;
	}

	/*****
	 * 获取view设置页面信息
	 * 
	 * @param request
	 * @throws Exception
	 */
	@Override
	public void setViewLabelInfo(HttpServletRequest request) throws Exception {
		// 获取view请求的url
		this.contextPath = request.getContextPath();
		String actionServletPath = (String) request
				.getAttribute("Pda_PrevServletPath");
		String formName = StrutsAndDictCfgUtil.getFormName(request,
				actionServletPath);
		logger.debug("当前请求中formName为：" + formName);
		// 根据请求,获取当前view页面的form对象,及对应的modelToFormMap
		IExtendForm extForm = StrutsAndDictCfgUtil.getForm(request, formName);
		ModelToFormPropertyMap modelToFormMap = StrutsAndDictCfgUtil
				.getModelToFormMap(extForm);
		// 根据表单对象,及请求获取该模块在pda中的显示配置
		PdaModuleConfigView configView = getViewCfg(extForm, actionServletPath);
		request.setAttribute("pda_configView", configView);
		request.setAttribute("pda_formName", formName);
		request.setAttribute("pda_extendJsp", PdaPlugin.getPdaExtendJsp(extForm
				.getModelClass().getName()));
		request.setAttribute("fdModelId", extForm.getFdId());
		// 获取是否强制显示表格界面,是否得APP应用端嵌入的网页
		String isForce = request.getParameter("isforce");
		if (StringUtil.isNull(isForce)) {
            isForce = "1";
        }
		String isAppflag = request.getParameter("isAppflag");
		// 计算该文档的链接,设置到session中便于返回页面调用
		HttpSession session = request.getSession();
		String docUrl = null;
		if (StringUtil.isNotNull(actionServletPath)) {
			docUrl = actionServletPath + "?method=view&fdId="
					+ extForm.getFdId();
			if (StringUtil.isNotNull(isAppflag) && "1".equals(isAppflag)) {
				docUrl = docUrl + "&isAppflag=1";
				session.setAttribute("s_isAppflag", "1");
			}
		}
		// 根据pda模块配置读取相应的值,设置到request中以便显示
		if (configView != null) {
			// 精簡阅读处理
			if ("1".equals(configView.getFdReadingModel())
					&& "1".equals(isForce)) {
				logger.debug("精简阅读处理..");
				Map<String, Object> newsMap = null;
				String newsModelCfg = configView.getFdNewsModelCfgInfo();
				newsMap = parseDocViewInfo(newsModelCfg, extForm,
						modelToFormMap, false);
				if (newsMap != null) {
					String status = (String) newsMap.get("status");
					if (StringUtil.isNotNull(status)) {
						if (isNumeric(status)) {
							if (Integer.valueOf(status) >= 30
									&& "1".equals(isForce)) {
								request.setAttribute("pda_isDocView", "1");
								request.setAttribute("pda_docViewInfo",
										parseDocViewInfo(newsModelCfg, extForm,
												modelToFormMap, true));
								session.setAttribute("S_DocLink", docUrl);
								return;
							}
						}
					}
				}
			}
			// 完整阅读处理
			logger.debug("完整阅读处理..");
			List<PdaModuleLabelView> labelList = configView.getFdItems();
			List filedList = new ArrayList();
			for (Iterator iterator = labelList.iterator(); iterator.hasNext();) {
				PdaModuleLabelView labelView = (PdaModuleLabelView) iterator
						.next();
				if (StringUtil.isNotNull(labelView.getFdExtendUrl())) {
					filedList.add(labelView.getFdExtendUrl());
				} else {
					List cfgList = parseTableViewInfo(labelView.getFdCfgInfo(),
							extForm, modelToFormMap);
					filedList.add(cfgList);
				}
			}
			request.setAttribute("pda_filedList", filedList);
			request.setAttribute("pda_isDocView", "0");
			session.setAttribute("S_DocLink", docUrl);
		}
	}

	@SuppressWarnings("unchecked")
	private PdaModuleConfigView getViewCfg(IExtendForm extForm,
			String actionServletPath) throws Exception {
		if (extForm == null) {
			return null;
		}
		String modulePath = StrutsAndDictCfgUtil
				.getModulePre(actionServletPath);
		String modelClass = extForm.getModelClass().getName();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("pdaModuleConfigView.fdModelName=:modelClass and pdaModuleConfigView.fdKeyword=:modulePath");
		hqlInfo.setOrderBy("pdaModuleConfigView.fdOrder asc");
		hqlInfo.setParameter("modelClass", modelClass);
		hqlInfo.setParameter("modulePath", modulePath);
		hqlInfo.setRowSize(1);
		List<PdaModuleConfigView> list = viewCfgService.findList(hqlInfo);
		if (list != null && list.size() > 0) {
			for (PdaModuleConfigView findpdaModuleConfigView : list) {
				PdaModuleConfigMain fdModule = findpdaModuleConfigView
						.getFdModule();
				if (fdModule != null && "1".equals(fdModule.getFdStatus())) {
					return findpdaModuleConfigView;
				}
			}
		}
		return null;
	}

	// 解析表格配置转换为对应值
	private List parseTableViewInfo(String cfgString, IExtendForm form,
			ModelToFormPropertyMap modelToFormMap) throws Exception {
		JSONObject jsonObj = JSONObject.fromObject(cfgString);
		JSONArray jsonList = jsonObj.getJSONArray("propertyList");
		List<Map<String, Object>> cfgList = new ArrayList<Map<String, Object>>();
		if (jsonList != null && jsonList.size() > 0) {
            for (Iterator iterator = jsonList.iterator(); iterator.hasNext();) {
                JSONObject object = (JSONObject) iterator.next();
                Map<String, Object> tmpMap = getFiledInfo(object, form,
                        modelToFormMap, false);
                if (tmpMap != null && tmpMap.size() > 0) {
                    cfgList.add(tmpMap);
                }
            }
        }
		return cfgList;
	}

	// 解析浏览模式配置,转换为对应值
	private Map<String, Object> parseDocViewInfo(String cfgString,
			IExtendForm form, ModelToFormPropertyMap modelToFormMap,
			boolean isAll) throws Exception {
		if (StringUtil.isNull(cfgString)) {
            return null;
        }
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		JSONObject jsonObj = JSONObject.fromObject(cfgString);

		JSONObject tmpObj = jsonObj.getJSONObject("status");
		Map<String, Object> sub = getFiledInfo(tmpObj, form, modelToFormMap,
				true);
		rtnMap.put("status", sub.get("value"));

		if (!isAll) {
            return rtnMap;
        }

		tmpObj = jsonObj.getJSONObject("subject");
		sub = getFiledInfo(tmpObj, form, modelToFormMap, false);
		rtnMap.put("subject", sub.get("value"));

		JSONArray tmpJsonArr = jsonObj.getJSONArray("contents");
		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
		if (tmpJsonArr != null && tmpJsonArr.size() > 0) {
			for (Iterator iterator = tmpJsonArr.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				Map<String, Object> contMap = getFiledInfo(object, form,
						modelToFormMap, false);
				if (contMap != null) {
                    contents.add(contMap);
                }
			}
		}
		rtnMap.put("contents", contents);

		tmpJsonArr = jsonObj.getJSONArray("attachments");
		List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();
		if (tmpJsonArr != null && tmpJsonArr.size() > 0) {
			for (Iterator iterator = tmpJsonArr.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				Map<String, Object> attMap = getFiledInfo(object, form,
						modelToFormMap, false);
				if (attMap != null) {
                    attachments.add(attMap);
                }
			}
		}
		rtnMap.put("attachments", attachments);

		tmpJsonArr = jsonObj.getJSONArray("summary");
		String summary = "";
		if (tmpJsonArr != null && tmpJsonArr.size() > 0) {
            for (Iterator iterator = tmpJsonArr.iterator(); iterator.hasNext();) {
                JSONObject object = (JSONObject) iterator.next();
                Object tmpVal = getFiledInfo(object, form, modelToFormMap,
                        false).get("value");
                if (tmpVal != null) {
                    summary += " " + tmpVal;
                }
            }
        }
		rtnMap.put("summary", summary);

		Object dataObj = jsonObj.get("others");
		if (dataObj != null) {
			tmpJsonArr = jsonObj.getJSONArray("others");
			List<Map<String, Object>> others = new ArrayList<Map<String, Object>>();
			if (tmpJsonArr != null && tmpJsonArr.size() > 0) {
                for (Iterator iterator = tmpJsonArr.iterator(); iterator
                        .hasNext();) {
                    JSONObject object = (JSONObject) iterator.next();
                    Map<String, Object> otrMap = getFiledInfo(object, form,
                            modelToFormMap, false);
                    if (otrMap != null) {
                        others.add(otrMap);
                    }
                }
            }
			rtnMap.put("others", others);
		}
		return rtnMap;
	}

	/**
	 * 根据对应字段解析出对应值 map中主要数据为:msgKey 多语言key dataType
	 * 数据类型:String,rtf,attachment,xform,flowdef,evaluation,relation,introduce
	 * value 数据值
	 **/
	private Map<String, Object> getFiledInfo(JSONObject jsonObj,
			IExtendForm form, ModelToFormPropertyMap modelToFormMap,
			boolean isStatus) throws Exception {
		if (jsonObj.isEmpty()) {
            return null;
        }
		logger.debug("处理属性配置：" + jsonObj.toString());
		String propType = jsonObj.getString("propertyType");
		Map<String, Object> mechanObj = parseMechansmInfo(jsonObj, form,
				propType);
		if (mechanObj != null) {
			logger.debug("转换后该属性值为：" + mechanObj.toString());
			return mechanObj;
		}

		// 对其他字段的解析
		Map<String, Object> filedObj = new HashMap<String, Object>();
		String propName = jsonObj.getString("propertyName");
		Object popVar = jsonObj.get("displayProp");
		if (popVar != null) {
            if (StringUtil.isNotNull((String) popVar)) {
                propName = (String) popVar;
            }
        }

		filedObj.put("msgKey", ResourceUtil.getString(jsonObj
				.getString("messageKey")));
		filedObj.put("propertyName", jsonObj.getString("propertyName"));
		// 处理规则,对于在modeltoMap中直接能查找到的域,直接获取,
		// 查找不到的用加fdname属性查找,再查找不到的直接使用原值
		Map propMap = modelToFormMap.getPropertyMap();
		Object tmpObj = propMap.get(propName);
		if (tmpObj == null) {
			String otherPropName = propName + ".fdName";
			tmpObj = propMap.get(otherPropName);
			if (tmpObj == null) {
				otherPropName = propName + ".docSubject";
				tmpObj = propMap.get(otherPropName);
			}
			if (tmpObj == null) {
                tmpObj = propName;
            }
		}
		String propArgu = null;
		if (tmpObj != null) {
			if (tmpObj instanceof IModelToFormConvertor) {
				IModelToFormConvertor convert = (IModelToFormConvertor) tmpObj;
				String formFiled = convert.getTPropertyName();
				String[] fileds = formFiled.split(":");
				propArgu = fileds[fileds.length - 1];
			} else {
				String fieldStr = (String) tmpObj;
				propArgu = fieldStr;
			}
		} else {
			propArgu = (String) tmpObj;
		}
		String propVal = null;
		if (StringUtil.isNotNull(propArgu)) {
			propVal = getFiledString(form, propArgu);
			Object typeObj = jsonObj.get("enumType");
			// 枚举类型的附加显示处理
			if (typeObj != null && !isStatus) {
                propVal = parseEnumData((String) typeObj, propVal);
            }
			filedObj.put("value", propVal);
			filedObj.put("dataType", "String");
		} else {
			filedObj.put("value", "");
			filedObj.put("dataType", propType);
		}
		if ("rtf".equalsIgnoreCase(propType)) {
			filedObj.put("dataType", "rtf");
			filedObj.put("value", ImageFilterUtil.replaceImgHtml(
					(String) filedObj.get("value"), this.contextPath));
		}
		logger.debug("转换后该属性值为：" + filedObj.toString());
		return filedObj;
	}

	private Map<String, Object> parseMechansmInfo(JSONObject jsonObj,
			IExtendForm form, String propType) throws Exception {
		// 对流程机制的解析
		if ("flowdef".equalsIgnoreCase(propType)) {
			Map<String, Object> flowMap = new HashMap<String, Object>();
			flowMap.put("dataType", "flowdef");
			flowMap.put("msgKey", jsonObj.getString("propertyText"));
			flowMap.put("modelName", jsonObj.getString("modelName"));
			flowMap.put("templateModelName", jsonObj
					.getString("templateModelName"));
			flowMap.put("key", jsonObj.getString("key"));
			flowMap.put("templatePropertyName", jsonObj
					.getString("templatePropertyName"));
			flowMap.put("flowType", jsonObj.getString("type"));
			return flowMap;
		}
		// 流程日志
		if ("flowlog".equalsIgnoreCase(propType)) {
			Map<String, Object> flowMap = new HashMap<String, Object>();
			flowMap.put("dataType", "flowlog");
			flowMap.put("msgKey", jsonObj.getString("propertyText"));
			flowMap.put("modelName", jsonObj.getString("modelName"));
			flowMap.put("templateModelName", jsonObj
					.getString("templateModelName"));
			flowMap.put("key", jsonObj.getString("key"));
			flowMap.put("templatePropertyName", jsonObj
					.getString("templatePropertyName"));
			flowMap.put("flowType", jsonObj.getString("type"));
			return flowMap;
		}
		// 对xform的解析
		if ("xform".equalsIgnoreCase(propType)) {
			Map<String, Object> xFormMap = new HashMap<String, Object>();
			xFormMap.put("dataType", "xform");
			xFormMap.put("msgKey", jsonObj.getString("propertyText"));
			xFormMap.put("template", jsonObj.getString("template"));
			xFormMap.put("key", jsonObj.getString("key"));
			return xFormMap;
		}

		// 对附件的解析
		if ("attachment".equalsIgnoreCase(propType)) {
			Map<String, Object> attMap = new HashMap<String, Object>();
			String attKey = jsonObj.getString("propertyName");
			if (StringUtil.isNotNull(attKey)) {
				attMap.put("value", attKey);
			}
			if (attMap != null && attMap.size() > 0) {
				attMap.put("msgKey", ResourceUtil.getString(jsonObj
						.getString("messageKey")));
				attMap.put("dataType", "attachment");
				return attMap;
			}
		}

		// 对附件缩略图的解析
		if ("thumb".equalsIgnoreCase(propType)) {
			Map<String, Object> attMap = new HashMap<String, Object>();
			String attKey = jsonObj.getString("propertyName");
			if (StringUtil.isNotNull(attKey)) {
				attMap.put("value", attKey);
			}
			if (attMap != null && attMap.size() > 0) {
				attMap.put("msgKey", ResourceUtil.getString(jsonObj
						.getString("messageKey")));
				attMap.put("dataType", "thumb");
				return attMap;
			}
		}
		// 点评机制
		if ("evaluation".equalsIgnoreCase(propType)) {
			Map<String, Object> evalMap = new HashMap<String, Object>();
			evalMap.put("msgKey", ResourceUtil.getString(
					"pdaModuleConfigView.label.evaluation", "third-pda"));
			evalMap.put("dataType", propType);
			return evalMap;
		}
		// 关联机制
		if ("relation".equalsIgnoreCase(propType)) {
			Map<String, Object> relaMap = new HashMap<String, Object>();
			relaMap.put("msgKey", ResourceUtil.getString(
					"pdaModuleConfigView.label.relation", "third-pda"));
			relaMap.put("dataType", propType);
			return relaMap;
		}
		// 推荐机制
		if ("introduce".equalsIgnoreCase(propType)) {
			Map<String, Object> intrMap = new HashMap<String, Object>();
			intrMap.put("msgKey", ResourceUtil.getString(
					"pdaModuleConfigView.label.introduce", "third-pda"));
			intrMap.put("dataType", propType);
			return intrMap;
		}
		// 属性筛选
		if ("property".equalsIgnoreCase(propType)) {
			Map<String, Object> propMap = new HashMap<String, Object>();
			propMap.put("msgKey", ResourceUtil.getString(
					"pdaModuleConfigView.label.property", "third-pda"));
			propMap.put("dataType", propType);
			return propMap;
		}
		// 子流程
		if ("subflow".equalsIgnoreCase(propType)) {
			Map<String, Object> subMap = new HashMap<String, Object>();
			subMap.put("msgKey", ResourceUtil.getString(
					"pdaModuleConfigView.subflow", "third-pda"));
			subMap.put("dataType", propType);
			return subMap;
		}
		return null;
	}

	// 枚举类型解析
	private String parseEnumData(String type, String value) {
		EnumerationTypeUtil enumUtil = EnumerationTypeUtil.newInstance();
		try {
			return EnumerationTypeUtil.getColumnEnumsLabel(type, value);
		} catch (Exception e) {
			return "";
		}
	}

	// 获取字段值
	private String getFiledString(Object obj, String name) {
		try {
			Object tmpObj = PropertyUtils.getProperty(obj, name);
			if (tmpObj instanceof String) {
                return (String) tmpObj;
            } else {
                return tmpObj.toString();
            }
		} catch (Exception e) {
			return "";
		}
	}

	// 判断字符串是否为数字类型
	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
