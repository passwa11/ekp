package com.landray.kmss.hr.ratify.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.sys.formula.parser.FormulaParser;

public class HrRatifyTitleUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrRatifyTitleUtil.class);

	/**
	 * 根据标题规则生成标题
	 * 
	 * @param modelObj
	 * @throws Exception
	 */
	public static void genTitle(IBaseModel modelObj) throws Exception {
		HrRatifyMain mainModel = (HrRatifyMain) modelObj;
		String mainRegulation = mainModel.getTitleRegulation();
		if (StringUtils.isNotBlank(mainRegulation)) {
			FormulaParser formulaParser = FormulaParser.getInstance(modelObj);
			Object docSubject = formulaParser.parseValueScript(mainRegulation);
			if (docSubject == null || "".equals(docSubject)) {
				throw new Exception("docSubject is null");
			}
			mainModel.setDocSubject(
					HrRatifyTitleUtil.convertObjToString(docSubject));
		}
	}

	/**
	 * 
	 * 
	 * @param obj
	 * @param propName
	 * @return
	 */
	public static String convertObjToString(Object obj) {
		Object scriptValue = obj;
		String reString = "";

		if (scriptValue == null) {
			reString = "";
		} else {
			if (scriptValue.toString().length() > 200) {
				reString = scriptValue.toString().substring(0, 199);
			} else {
				reString = scriptValue.toString();
			}
		}
		return reString;
	}
}
