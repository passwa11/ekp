package com.landray.kmss.sys.evaluation.service.spring;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreOuterServiceImp;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainModel;
import com.landray.kmss.sys.evaluation.forms.EvaluationForm;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationCoreService;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationForm;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationNotesForm;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationNotesModel;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationMainService;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationNotesService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.NumberUtil;
import com.landray.sso.client.util.StringUtil;

public class SysEvaluationCoreServiceImp extends BaseCoreOuterServiceImp
		implements ISysEvaluationCoreService {
	private ISysEvaluationMainService sysEvaluationMainService;
	private ISysEvaluationNotesService sysEvaluationNotesService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysEvaluationCoreServiceImp.class);

	public void setSysEvaluationMainService(
			ISysEvaluationMainService sysEvaluationMainService) {
		this.sysEvaluationMainService = sysEvaluationMainService;
	}
	
	public void setSysEvaluationNotesService(
			ISysEvaluationNotesService sysEvaluationNotesService) {
		this.sysEvaluationNotesService = sysEvaluationNotesService;
	}

	@Override
	public void delete(IBaseModel model) throws Exception {
		if (logger.isDebugEnabled()) {
            logger.debug("删除相关的点评记录");
        }
		//删除全文点评
		if(model instanceof ISysEvaluationModel){
			sysEvaluationMainService.deleteCoreModels(model);
		}
		//删除段落点评
		if(model instanceof ISysEvaluationNotesModel){
			sysEvaluationNotesService.deleteCoreModels(model);
		}
		
	}

	@Override
	public void convertModelToForm(IExtendForm form, IBaseModel model,
								   RequestContext requestContext) throws Exception {
		// 判断是否有部署发布机制
		if (!(form instanceof ISysEvaluationForm && model instanceof ISysEvaluationModel)) {
            return;
        }
		if (logger.isDebugEnabled()) {
            logger.debug("将点评信息从Model转成Form");
        }
		ISysEvaluationForm mainForm = (ISysEvaluationForm) form;
		ISysEvaluationModel mainModel = (ISysEvaluationModel) model;
		boolean isShow = mainModel.getDocStatus().charAt(0) >= '3';
		EvaluationForm evaluationForm = mainForm.getEvaluationForm();
		if (!isShow) {
			evaluationForm.setFdIsShow("false");
			return;
		}
		Boolean isNewVersion = new Boolean(true);
		if (model instanceof ISysEditionMainModel) {
			ISysEditionMainModel sysEditionModel = (ISysEditionMainModel) model;
			isNewVersion = sysEditionModel.getDocIsNewVersion();
		}
		String modelName = ModelUtil.getModelClassName(model);
		String modelId = model.getFdId().toString();
		if (StringUtil.isNotNull(this.sysEvaluationMainService.getTopEvaluationIdByUserId(modelName, modelId, null))) {
			// 评论过
			evaluationForm.setFdIsCommented("true");
		} else {
			evaluationForm.setFdIsCommented("false");
		}

		// 获取对应的表单与域模型
		evaluationForm.setFdIsShow("true");
		evaluationForm.setFdModelId(modelId);
		evaluationForm.setFdModelName(modelName);
		evaluationForm.setFdIsNewVersion(isNewVersion.toString());
		int count = sysEvaluationMainService.getRecordCountByModel(mainModel);
		int notesCount = sysEvaluationNotesService.getNotesCountByModel(model);//段落点评数量
		if(form instanceof ISysEvaluationNotesForm) {
			((ISysEvaluationNotesForm)form).setEvaluationNotesCount(String.valueOf(notesCount));
		}
		int sumCount = count + notesCount;
		if (sumCount > 0) {
            evaluationForm.setFdEvaluateCount("(" + sumCount + ")");
        }
		String score = "" + sysEvaluationMainService.score(ModelUtil.getModelClassName(model), model.getFdId());
		if(score.length()>3) {
            evaluationForm.setFdEvaluateScore(NumberUtil.roundDecimal(score,"#.#"));
        } else {
            evaluationForm.setFdEvaluateScore(score);
        }
	}

	@Override
	public List getEvaluationList(List modelNameList, Date startDate,
								  Date endDate, boolean isNewVersion) throws Exception {
		if (modelNameList == null || modelNameList.isEmpty()) {
			throw new KmssException(
					new KmssMessage(
							"sys-evaluation:sysEvaluationMain.error.modelNameListIsNull"));
		}
		HQLInfo hqlInfo = new HQLInfo();

		String whereBlock = HQLUtil.buildLogicIN(
				"sysEvaluationMain.fdModelName", modelNameList);
		if (startDate != null) {
			whereBlock += " and sysEvaluationMain.fdEvaluationTime >= :startDate";
			hqlInfo.setParameter("startDate", startDate);
		}

		if (endDate != null) {
			whereBlock += " and sysEvaluationMain.fdEvaluationTime <= :endDate";
			hqlInfo.setParameter("endDate", endDate);
		}

		if (isNewVersion) {
			whereBlock += " and sysEvaluationMain.fdIsNewVersion = 1";
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("sysEvaluationMain.fdEvaluationTime");
		List rtnList = sysEvaluationMainService.findList(hqlInfo);
		return rtnList;
	}

}
