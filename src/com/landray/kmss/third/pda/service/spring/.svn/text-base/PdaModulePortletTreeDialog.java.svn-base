package com.landray.kmss.third.pda.service.spring;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.model.PdaHomeCustomPortlet;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaModuleLabelList;
import com.landray.kmss.third.pda.service.IPdaHomeCustomPortletService;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.util.StringUtil;

public class PdaModulePortletTreeDialog implements IXMLDataBean {
	private IPdaModuleConfigMainService pdaModuleConfigMainService;

	public IPdaModuleConfigMainService getPdaModuleConfigMainService() {
		return pdaModuleConfigMainService;
	}

	public void setPdaModuleConfigMainService(
			IPdaModuleConfigMainService pdaModuleConfigMainService) {
		this.pdaModuleConfigMainService = pdaModuleConfigMainService;
	}

	private IPdaHomeCustomPortletService pdaHomeCustomPortletService;

	public IPdaHomeCustomPortletService getPdaHomeCustomPortletService() {
		return pdaHomeCustomPortletService;
	}

	public void setPdaHomeCustomPortletService(
			IPdaHomeCustomPortletService pdaHomeCustomPortletService) {
		this.pdaHomeCustomPortletService = pdaHomeCustomPortletService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnVal = new ArrayList();
		String moduleKey = requestInfo.getParameter("moduleId");
		if (StringUtil.isNull(moduleKey)) {
			addModules(requestInfo, rtnVal);
		} else {
			addPortlets(requestInfo, rtnVal, moduleKey);
		}
		return rtnVal;
	}

	private void addModules(RequestContext requestInfo, List rtnVal)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("pdaModuleConfigMain.fdStatus='1'");
		List moduleList = pdaModuleConfigMainService.findList(hqlInfo);
		for (int i = 0; i < moduleList.size(); i++) {
			PdaModuleConfigMain configMain = (PdaModuleConfigMain) moduleList
					.get(i);
			addModuleNode(requestInfo, rtnVal, configMain);
		}
	}

	private void addModuleNode(RequestContext requestInfo, List rtnVal,
			PdaModuleConfigMain configMain) {
		String text = configMain.getFdName();
		Map node = new HashMap();
		node.put("text", text);
		node.put("beanName", "pdaModulePortletTreeDialog&moduleId="
				+ configMain.getFdId());
		rtnVal.add(node);
	}

	private void addPortlets(RequestContext requestInfo, List rtnVal,
			String moduleKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("pdaModuleConfigMain.fdStatus='1' and pdaModuleConfigMain.fdSubMenuType = :fdSubMenuType "
						+ "and pdaModuleConfigMain.fdId=:urlPrefix");
		hqlInfo.setParameter("fdSubMenuType",
				PdaModuleConfigConstant.PDA_MENUS_LISTTAB);
		hqlInfo.setParameter("urlPrefix", moduleKey);
		List moduleList = pdaModuleConfigMainService.findList(hqlInfo);
		if (moduleList != null && !moduleList.isEmpty()) {
			for (int i = 0; i < moduleList.size(); i++) {
				PdaModuleConfigMain configMain = (PdaModuleConfigMain) moduleList
						.get(i);
				List<PdaModuleLabelList> labelList = configMain
						.getFdLabelList();
				for (int j = 0; j < labelList.size(); j++) {
					PdaModuleLabelList label = labelList.get(j);
					addPortletNode(requestInfo, rtnVal, label);
				}
			}
		}
		HQLInfo hqlInfo1 = new HQLInfo();
		hqlInfo1.setWhereBlock("pdaHomeCustomPortlet.fdModuleId=:moduleKey");
		hqlInfo1.setParameter("moduleKey", moduleKey);
		List portletList = pdaHomeCustomPortletService.findList(hqlInfo1);
		if (portletList != null && !portletList.isEmpty()) {
			for (int j = 0; j < portletList.size(); j++) {
				PdaHomeCustomPortlet customPortlet = (PdaHomeCustomPortlet) portletList
						.get(j);
				addPortletNode(requestInfo, rtnVal, customPortlet);
			}
		}
	}

	private void addPortletNode(RequestContext requestInfo, List rtnVal,
			PdaModuleLabelList label) throws UnsupportedEncodingException {
		Map node = new HashMap();
		StringBuffer value = new StringBuffer();
		value.append("&dataURL="
				+ URLEncoder.encode(label.getFdDataUrl(), "UTF-8"));
		value.append("&type=list");
		value.append("&label=" + label.getFdId());
		value.append("&module=" + label.getFdModule().getFdId());
		value.append("&moduleName=" + label.getFdModule().getFdName());
		node.put("value", value.toString());
		node.put("text", label.getFdName());
		rtnVal.add(node);
	}

	private void addPortletNode(RequestContext requestInfo, List rtnVal,
			PdaHomeCustomPortlet potlet) throws UnsupportedEncodingException {
		Map node = new HashMap();
		StringBuffer value = new StringBuffer();
		String dataUrl = potlet.getFdDataUrl();
		value.append("&dataURL=" + URLEncoder.encode(dataUrl, "UTF-8"));
		if ("pic".equalsIgnoreCase(potlet.getFdType())) {
			value.append("&type=pic");
		} else {
            value.append("&type=list");
        }
		value.append("&module=" + potlet.getFdModuleId());
		value.append("&moduleName=" + potlet.getFdModuleName());
		node.put("text", potlet.getFdName());
		node.put("value", value.toString());
		if (dataUrl.indexOf("!{") > -1
				&& StringUtil.isNotNull(potlet.getFdTemplateClass())) {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			try {
				if (loader.loadClass(potlet.getFdTemplateClass()).newInstance() instanceof ISysSimpleCategoryModel) {
					String str = "sysSimpleCategoryTreeService&authType=00"
							+ "&modelName=" + potlet.getFdTemplateClass();
					node.put("beanName", str);
				} else {
					node.put("beanName",
							"sysCategoryTreeService&showType=0&authType=00&modelName="
									+ potlet.getFdTemplateClass()
									+ "&nodeType=!{nodeType}&getTemplate=2");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		rtnVal.add(node);
	}
}
