package com.landray.kmss.km.review.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.formula.parser.FormulaParser;

/**
 * @todo
 * @author 林秀贤
 * @date 2014-9-29
 */
public class KmReviewTitleUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewTitleUtil.class);

	/**
	 * 根据标题规则生成标题
	 * 
	 * @param modelObj
	 * @throws Exception
	 */
	public static void genTitle(IBaseModel modelObj) throws Exception {
		KmReviewMain mainModel = (KmReviewMain) modelObj;
		//String titleRegulation = mainModel.getFdTemplate().getTitleRegulation();
		String mainRegulation = mainModel.getTitleRegulation();
		//优先取主文档规则
		//titleRegulation = StringUtil.isNotNull(mainRegulation) ? mainRegulation :titleRegulation;
		if (StringUtils.isNotBlank(mainRegulation)) {
			FormulaParser formulaParser = FormulaParser.getInstance(modelObj);
			Object docSubject = formulaParser.parseValueScript(mainRegulation);
			if (docSubject == null || "".equals(docSubject)) {
				throw new Exception("docSubject is null");
			}
			if (docSubject instanceof Date) { 
				Date d = (Date) docSubject;
				docSubject = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d); 
			}
			if(logger.isDebugEnabled()){
				//打印堆栈
				//Arrays.stream(Thread.currentThread().getStackTrace()).forEach(thread->logger.debug(thread.toString()));
			}
			mainModel.setDocSubject(KmReviewTitleUtil
					.convertObjToString(docSubject));
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
