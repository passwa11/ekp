package com.landray.kmss.sys.evaluation.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.doc.model.SysDocAuthor;
import com.landray.kmss.sys.doc.model.SysDocBaseInfo;
import com.landray.kmss.sys.evaluation.dao.ISysEvaluationMainDao;
import com.landray.kmss.sys.evaluation.dao.ISysEvaluationNotesDao;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationNotesForm;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationCountModel;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationNotesModel;
import com.landray.kmss.sys.evaluation.model.SysEvaluationNotes;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationNotesService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysEvaluationNotesServiceImp extends BaseCoreInnerServiceImp
						implements ISysEvaluationNotesService, ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private ISysNotifyMainCoreService notifyMainCoreService;
	public void setNotifyMainCoreService(
			ISysNotifyMainCoreService notifyMainCoreService) {
		this.notifyMainCoreService = notifyMainCoreService;
	}
	
	public int getRecordCountByModel(ISysEvaluationModel sysEvaluationModel) {
		return ((ISysEvaluationMainDao) getBaseDao())
				.getRecordCountByModel(sysEvaluationModel);
	}
	@Override
    public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysEvaluationNotesForm sysEvaluationNotesForm = (SysEvaluationNotesForm) form;
		sysEvaluationNotesForm.setFdEvaluatorId(UserUtil.getUser().getFdId());
		sysEvaluationNotesForm.setFdEvaluatorName(UserUtil.getUser().getFdName());
		
		UserOperHelper.logAdd(getModelName());
		SysEvaluationNotes evaluationNotes = (SysEvaluationNotes) convertFormToModel(
				form, null, requestContext);
		evaluationNotes.setFdEvaluationTime(new Date());
		String fdId = super.add(evaluationNotes);
		ISysEvaluationNotesModel noteModel = (ISysEvaluationNotesModel) getMainModel(evaluationNotes);
		String fdNotifyType=requestContext.getParameter("notifyType");
		String isNotify = requestContext.getParameter("isNotify");
		String[] notify = isNotify.split(";");
		List<SysOrgElement> sendRecordList = new ArrayList<>();
		for (int i = 0; i < notify.length; i++) {
			if (!StringUtil.isNull(notify[i])) {
				// 判断是否需要发布通知
				SysEvaluationNotesForm evaluationNotesForm = (SysEvaluationNotesForm) form;
				NotifyContext notifyContext = notifyMainCoreService
				.getContext("sys-evaluation:sysEvaluationMain.notify");
				notifyContext.setNotifyType(fdNotifyType);
				List sendto = new ArrayList();
				SysOrgElement operator = evaluationNotes.getFdEvaluator();
				if("docAuthor".equals(notify[i])) {
					String evaluatorId =  evaluationNotes.getFdEvaluator().getFdId();
					if(noteModel instanceof SysDocBaseInfo) {
						List<SysDocAuthor> authorList = ((SysDocBaseInfo) noteModel).getFdDocAuthorList();
						if(!ArrayUtil.isEmpty(authorList)) {
							for(SysDocAuthor sauthor : authorList) {
								if(!evaluatorId.equals(sauthor.getFdSysOrgElement().getFdId())) {
									if(!sendRecordList.contains(sauthor.getFdSysOrgElement())){
										sendto.add(sauthor.getFdSysOrgElement());
									}
								}
							}
						}
					} else if(!(evaluationNotes.getFdEvaluator().getFdId().equals(noteModel.getDocAuthor().getFdId()))) {
						if(!sendRecordList.contains(noteModel.getDocAuthor())){
							sendto.add(noteModel.getDocAuthor());
						}
					}
				}else if("docCreator".equals(notify[i])&&(!(noteModel.getDocCreator().getFdId().equals(operator.getFdId())))){
					if(!sendRecordList.contains(noteModel.getDocCreator())){
						sendto.add(noteModel.getDocCreator());
					}
				}else if("docAlteror".equals(notify[i])&&(!(noteModel.getDocAlteror().getFdId().equals(operator.getFdId())))){
					if(!sendRecordList.contains(noteModel.getDocAlteror())){
						sendto.add(noteModel.getDocAlteror());
					}
				}
				if(sendto.size() > 0) {
					sendRecordList.addAll(sendto);
					notifyContext.setNotifyTarget(sendto);
					String link = ModelUtil.getModelUrl(noteModel);
					if (link != null){
						//代办URL里面增加点评标识，提供跳转页面特定需求下使用
						link=link+"&dueTo=evalution";
						notifyContext.setLink(link);
					}
					NotifyReplace notifyReplace = new NotifyReplace();
					notifyReplace.addReplaceText("docSubject",
							noteModel.getDocSubject());
					notifyReplace.addReplaceText("fdEvaluator",
							evaluationNotes.getFdEvaluator().getFdName());
					notifyMainCoreService.sendNotify(noteModel, notifyContext,
							notifyReplace);
				}
			}
		}
		if(noteModel instanceof ISysEvaluationCountModel){
			ISysEvaluationCountModel countModel = (ISysEvaluationCountModel) noteModel;
			Integer count = countModel.getDocEvalCount();
			countModel.setDocEvalCount(count == null ? Integer.valueOf(1)
					: Integer.valueOf(count.intValue() + 1));
			saveMainModel(noteModel);
		}
		return fdId;
	}
	/**
	 * 获取段落点评的所有模块名
	 */
	@Override
    public JSONArray listEvalNotesModels(RequestContext requestInfo) throws Exception{
		JSONArray jsonArray = new JSONArray();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(" DISTINCT sysEvaluationNotes.fdModelName ");
		List<String> modelList = findList(hqlInfo);
		
		for(String modelName:modelList){
			SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
			if (dict != null) {
				String title = ResourceUtil.getString(dict.getMessageKey(),
						requestInfo.getRequest().getLocale());
				JSONObject jsonModel = new JSONObject();
				jsonModel.put("modelName", modelName);
				jsonModel.put("text", title);
				jsonArray.add(jsonModel);
			}
		}
		return jsonArray;
	}
	
	//获得段落点评数
	@Override
    public int getNotesCountByModel(IBaseModel model) {
		return ((ISysEvaluationNotesDao) getBaseDao())
				.getNotesCountByModel(model);
	}
	@Override
	public JSONArray listEvalModels(RequestContext requestInfo)
			throws Exception {
		JSONArray jsonArray = new JSONArray();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(" DISTINCT sysEvaluationNotes.fdModelName ");
		List<String> modelList = findList(hqlInfo);
		
		for(String modelName:modelList){
			SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
			if (dict != null) {
				String title = ResourceUtil.getString(dict.getMessageKey(),
						requestInfo.getRequest().getLocale());
				JSONObject jsonModel = new JSONObject();
				jsonModel.put("modelName", modelName);
				jsonModel.put("text", title);
				jsonArray.add(jsonModel);
			}
		}
		return jsonArray;
	}
	
	/**
	 * 获取被点评文档url
	 */
	@Override
    public String getDocUrl(String fdModelId, String fdModelName, RequestContext requestInfo)
				throws Exception{
		IBaseService baseService = (IBaseService) applicationContext
				.getBean(SysDataDict.getInstance().getModel(fdModelName).getServiceBean());
		IBaseModel baseModel = (IBaseModel) baseService
				.findByPrimaryKey(fdModelId);
		String docUrl = requestInfo.getContextPath() + ModelUtil.getModelUrl(baseModel);
		return docUrl;
	}

	
}
