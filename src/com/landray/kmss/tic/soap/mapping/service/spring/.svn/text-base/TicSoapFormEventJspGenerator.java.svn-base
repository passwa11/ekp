package com.landray.kmss.tic.soap.mapping.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.xform.base.model.AbstractFormTemplate;
import com.landray.kmss.sys.xform.base.service.parse.JspGenerateConext;
import com.landray.kmss.sys.xform.base.service.parse.JspGenerator;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 表单事件引入 jsp 片段
 * 
 * @author zhangtian
 * 
 */
public class TicSoapFormEventJspGenerator implements JspGenerator {

	private String getJspHead(String fdTemplateId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("ticCoreMappingFunc.fdId");
		hqlInfo.setWhereBlock("ticCoreMappingFunc.fdInvokeType=:fdInvokeType and " +
				"ticCoreMappingFunc.fdTemplateId=:fdTemplateId and " +
				"ticCoreMappingFunc.fdIntegrationType=:fdIntegrationType");
		hqlInfo.setParameter("fdInvokeType", 0);
		hqlInfo.setParameter("fdTemplateId", fdTemplateId);
		hqlInfo.setParameter("fdIntegrationType", "3");
		ITicCoreMappingFuncService ticCoreMappingFuncService = (ITicCoreMappingFuncService) 
				SpringBeanUtil.getBean("ticCoreMappingFuncService");
		List list = ticCoreMappingFuncService.findValue(hqlInfo);
		if (list != null && list.size() > 0) {
			return "<%@ include file=\"/tic/soap/mapping/soapFormEventInclude.jsp\"%>\r\n";
		} else {
			return "";
		}
		
	}

	@Override
    public void execute(JspGenerateConext context) throws Exception {
		AbstractFormTemplate template = (AbstractFormTemplate) context
				.getModel();
		String fdTemplateId = template.getFdModelId();
		String jspStr = getJspHead(fdTemplateId);
		if (StringUtil.isNotNull(jspStr)) {
			template.setFdDisplayJsp(template.getFdDisplayJsp() + jspStr);
		}
	}

}
