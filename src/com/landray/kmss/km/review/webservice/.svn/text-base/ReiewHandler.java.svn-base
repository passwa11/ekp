package com.landray.kmss.km.review.webservice;

import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.XMLUtil;

import net.sf.json.JSONObject;

public class ReiewHandler {

	private KmReviewParamterForm webForm;

	public ReiewHandler(KmReviewParamterForm webForm) {
		super();

		this.webForm = webForm;
	}

	public void process() throws Exception {
		// 1. 兼容起草人参数
		convertDrafter();

		// 2. 兼容表单数据
		convertFormValues();

		// 3. 兼容流程参数
		convertFlowParam();
	}

	/**
	 * 兼容起草人参数
	 * 
	 * @throws Exception
	 */
	private void convertDrafter() throws Exception {
		if (StringUtil.isNotNull(webForm.getFdId())) {
			return;
		}
		String docCreator = webForm.getDocCreator();
		if (StringUtil.isNull(docCreator)) {
			throw new Exception("启动流程起草人参数不能为空");
		}

		// 兼容CX的起草人，默认使用登录名
		if (docCreator.trim().charAt(0) != '{') {
			JSONObject json = new JSONObject();
			json.put("LoginName", docCreator);
			webForm.setDocCreator(json.toString());
		}
	}

	/**
	 * 兼容表单数据
	 * 
	 * @throws Exception
	 */
	private void convertFormValues() throws Exception {
		String formValues = webForm.getFormValues();
		if (StringUtil.isNull(formValues)) {
			return;
		}

		// 兼容CX的表单数据，将XML转换为JSON格式
		if (testXML(formValues)) {
			formValues = XMLUtil.convertXML2JSON(formValues).toString();
			webForm.setFormValues(formValues);
		}
	}

	/**
	 * 兼容流程参数
	 * 
	 * @throws Exception
	 */
	private void convertFlowParam() throws Exception {
		String flowParam = webForm.getFlowParam();
		if (StringUtil.isNull(flowParam)) {
			webForm.setFlowParam(null);
		}
	}

	private static boolean testXML(String str) {
		str = str.trim();
		return str.startsWith("<") && str.endsWith(">");
	}

}
