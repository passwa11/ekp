package com.landray.kmss.sys.evaluation.service.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.doc.model.SysDocAuthor;
import com.landray.kmss.sys.evaluation.dao.ISysEvaluationMainDao;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationMainForm;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationAvgScoreModel;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationCountModel;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;
import com.landray.kmss.sys.evaluation.model.SysEvaluationMain;
import com.landray.kmss.sys.evaluation.model.SysEvaluationNotesConfig;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 创建日期 2006-九月-01
 * 
 * @author 叶中奇 点评机制业务接口实现
 */
public class SysEvaluationMainServiceImp extends BaseCoreInnerServiceImp
		implements ISysEvaluationMainService, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private ISysNotifyMainCoreService notifyMainCoreService;

	@Override
	public int getRecordCountByModel(ISysEvaluationModel sysEvaluationModel) {
		return ((ISysEvaluationMainDao) getBaseDao())
				.getRecordCountByModel(sysEvaluationModel);
	}

	@Override
	public double score(String modelName, String modelId){
		return ((ISysEvaluationMainDao) getBaseDao()).score(modelName, modelId);
	}
	
	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String fdModelId = requestContext.getParameter("fdModelId");
		String fdModelName = requestContext.getParameter("fdModelName");
		if(StringUtil.isNotNull(fdModelId) && StringUtil.isNotNull(fdModelName)) {
			this.limitEva(fdModelName, fdModelId);
		}
		
		SysEvaluationMainForm sysEvaluationMainForm =
				(SysEvaluationMainForm) form;

		String modelName = sysEvaluationMainForm.getFdModelName();
		String modelId = sysEvaluationMainForm.getFdModelId();
		String userid = UserUtil.getUser().getFdId();

		String fdParentId =
				this.getTopEvaluationIdByUserId(modelName, modelId, userid);
		if (StringUtil.isNotNull(fdParentId)) {
			sysEvaluationMainForm.setFdParentId(fdParentId);
			sysEvaluationMainForm.setFdEvaluationScore(null);
		}
		// 不知道这里为什么要转义，但是转义以后就引来了服务单，请看：#131631
//		String fdEvaluationContent = sysEvaluationMainForm.getFdEvaluationContent();
//		if (StringUtil.isNotNull(fdEvaluationContent))
//			fdEvaluationContent = URLDecoder.decode(fdEvaluationContent.replaceAll("%", "%25"), "utf-8");
//		sysEvaluationMainForm.setFdEvaluationContent(fdEvaluationContent);
		sysEvaluationMainForm.setFdIp(Plugin.currentRequest().getRemoteAddr());
		
		UserOperHelper.logAdd(getModelName());
		
		SysEvaluationMain evaluationMain = (SysEvaluationMain) convertFormToModel(
				form, null, requestContext);
		
		String fdId = super.add(evaluationMain);
		ISysEvaluationModel mainModel = (ISysEvaluationModel) getMainModel(evaluationMain);
		String notifyOther = requestContext.getParameter("notifyOther");
		if (!StringUtil.isNull(requestContext.getParameter("isNotify"))
				|| !StringUtil.isNull(notifyOther)) {
			// 判断是否需要发布通知
			NotifyContext notifyContext = notifyMainCoreService
					.getContext("sys-evaluation:sysEvaluationMain.notify");
			String notifyType = null;
			if (StringUtil.isNotNull(sysEvaluationMainForm.getFdNotifyType())) {
				notifyType = sysEvaluationMainForm.getFdNotifyType();
			} else {
				if (MobileUtil.PC != MobileUtil.getClientType(requestContext)) {
					notifyType = getConfigNotifyType();
				}
			}
			notifyContext.setNotifyType(notifyType);
			List sendto = new ArrayList();
			if (!StringUtil.isNull(requestContext.getParameter("isNotify"))) {
				sendto.add(mainModel.getDocCreator());
			}
			// 判断是否需要发布通知给其他人 修改者： 陈伟
			if (!StringUtil.isNull(notifyOther)) {
				IBaseService baseService = (IBaseService) applicationContext
						.getBean(SysDataDict.getInstance().getModel(
								evaluationMain.getFdModelName())
								.getServiceBean());
				IBaseModel baseModel = (IBaseModel) baseService
						.findByPrimaryKey(evaluationMain.getFdModelId());
				// 判断多作者
				if ("fdDocAuthorList".equals(notifyOther)) {
					List<SysDocAuthor> list = (List<SysDocAuthor>) PropertyUtils
							.getProperty(baseModel, notifyOther);
					if (list.size() > 0) {

						for (SysDocAuthor sysDocAuthor : list) {
							sendto.add(sysDocAuthor.getFdSysOrgElement());
						}
					}
				} else {
                    sendto.add(
                            (SysOrgElement) PropertyUtils.getProperty(baseModel,
                        notifyOther));
                }
			}
			notifyContext.setNotifyTarget(sendto);
			String link = ModelUtil.getModelUrl(mainModel);
			if (link != null){
				//代办URL里面增加点评标识，提供跳转页面特定需求下使用
				link=link+"&dueTo=evalution";
				notifyContext.setLink(link);
			}
			
			// HashMap replaceMap = new HashMap();
			// replaceMap.put("docSubject", mainModel.getDocSubject());
			// replaceMap.put("fdEvaluator", evaluationMain.getFdEvaluator()
			// .getFdName());
			NotifyReplace notifyReplace = new NotifyReplace();
			notifyReplace.addReplaceText("docSubject",
					mainModel.getDocSubject());
			notifyReplace.addReplaceText("fdEvaluator",
					evaluationMain.getFdEvaluator()
					.getFdName());
			notifyMainCoreService.sendNotify(mainModel, notifyContext,
					notifyReplace);
		}
		 if(StringUtil.isNull(fdParentId)) {
			 if(mainModel instanceof ISysEvaluationCountModel||mainModel instanceof ISysEvaluationAvgScoreModel) {
				if (mainModel instanceof ISysEvaluationCountModel) {
					ISysEvaluationCountModel countModel = (ISysEvaluationCountModel) mainModel;
					// 实现一
					// countModel.setDocEvalCount(Integer.valueOf(getRecordCountByModel(mainModel)));
					// 实现二
					Integer count = countModel.getDocEvalCount();
					countModel.setDocEvalCount(count == null ? Integer.valueOf(1)
							: Integer.valueOf(count.intValue() + 1));
				}
				if (mainModel instanceof ISysEvaluationAvgScoreModel) {
					ISysEvaluationAvgScoreModel scoreModel=(ISysEvaluationAvgScoreModel) mainModel;
					Double fdAvgScore=score(mainModel.getClass().getName(), mainModel.getFdId());
					BigDecimal   b   =   new   BigDecimal(fdAvgScore);  
					fdAvgScore=b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					scoreModel.setDocScore(fdAvgScore);
				}
				saveMainModel(mainModel);
			}
		 }
		return fdId;
	}
	
	public static Date getNextDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 判断是否有限制点评频率和次数
	 * @param modelName
	 * @param modelId
	 */
	@SuppressWarnings("unchecked")
	private void limitEva(String modelName, String modelId) throws Exception {
		//String fdMaxTimesOneDay = 
		Date now = new Date();
		SysEvaluationNotesConfig  cfg = new SysEvaluationNotesConfig();
		if(StringUtil.isNotNull(cfg.getFdMaxTimesOneDay())) {
			Date min = getNextDay(now, 0);
			Date max = getNextDay(now, 1);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("count(*)");
			hqlInfo.setWhereBlock("fdModelName=:modelName and fdModelId =:modelId and fdEvaluator.fdId=:userId and fdEvaluationTime>=:min and fdEvaluationTime<:max");
			hqlInfo.setParameter("modelName", modelName);
			hqlInfo.setParameter("modelId", modelId);
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			hqlInfo.setParameter("min", min);
			hqlInfo.setParameter("max", max);
			Long count  = Long.valueOf(this.findFirstOne(hqlInfo).toString());
			Long fdMaxTimesOneDay = Long.parseLong(cfg.getFdMaxTimesOneDay());
			if(count >= fdMaxTimesOneDay && fdMaxTimesOneDay!=0) {
				//间隔小于设置的分钟数
				throw new KmssRuntimeException(new KmssMessage(
						"sys-evaluation:sysEvaluationConfig.setting.fdMaxTimesOneDay.msg", fdMaxTimesOneDay));
			}
		}
		
		Long nowMillis = System.currentTimeMillis();
		if(StringUtil.isNotNull(cfg.getFdIntervalTime())) {
			Long fdIntervalTime = Long.parseLong(cfg.getFdIntervalTime());
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setGetCount(false);
			hqlInfo.setRowSize(1);
			hqlInfo.setPageNo(1);
			hqlInfo.setWhereBlock("fdModelName=:modelName and fdModelId =:modelId and fdEvaluator.fdId=:userId");
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			hqlInfo.setParameter("modelName", modelName);
			hqlInfo.setParameter("modelId", modelId);
			hqlInfo.setOrderBy("fdEvaluationTime desc");
			SysEvaluationMain obj = (SysEvaluationMain)this.findFirstOne(hqlInfo);
			if(obj != null) {
				Long interval = nowMillis - obj.getFdEvaluationTime().getTime();
				if(interval < (fdIntervalTime * 60 * 1000)) {
					//间隔小于设置的分钟数
					throw new KmssRuntimeException(new KmssMessage(
							"sys-evaluation:sysEvaluationConfig.setting.fdIntervalTime.msg", fdIntervalTime));
				}
			}
		}
	}

	/**
	 * 得到admin.do中配置的notifyType
	 * 
	 * @param notifyType
	 * 
	 * @return
	 */
	private String getConfigNotifyType() {
		StringBuilder sb = new StringBuilder();
		String defaultEmail = ResourceUtil
				.getKmssConfigString("kmss.notify.type.email.default");
		String enableEmail = ResourceUtil
				.getKmssConfigString("kmss.notify.type.email.enabled");
		String defaultMobile = ResourceUtil
				.getKmssConfigString("kmss.notify.type.mobile.default");
		String enableMobile = ResourceUtil
				.getKmssConfigString("kmss.notify.type.mobile.enabled");
		String defaultTodo = ResourceUtil
				.getKmssConfigString("kmss.notify.type.todo.default");
		String enableTodo = ResourceUtil
				.getKmssConfigString("kmss.notify.type.todo.enabled");
		if ("true".equals(defaultTodo) && "true".equals(enableTodo)) {
			sb.append("todo;");
		}
		if ("true".equals(defaultEmail) && "true".equals(enableEmail)) {
			sb.append("email;");
		}
		if ("true".equals(defaultMobile) && "true".equals(enableMobile)) {
			sb.append("mobile;");
		}
		return sb.toString();
	}

	public void setNotifyMainCoreService(
			ISysNotifyMainCoreService notifyMainCoreService) {
		this.notifyMainCoreService = notifyMainCoreService;
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		SysEvaluationMain evaluationMain = (SysEvaluationMain) modelObj;
		ISysEvaluationModel mainModel = (ISysEvaluationModel) getMainModel(evaluationMain);
		super.delete(modelObj);
		String fdParentId = evaluationMain.getFdParentId();
		if (StringUtil.isNull(fdParentId)) {
			// 删除追加的评论
			((ISysEvaluationMainDao) this.getBaseDao()).deleteByParentId(evaluationMain.getFdId());

			if (mainModel instanceof ISysEvaluationCountModel || mainModel instanceof ISysEvaluationAvgScoreModel) {
				if (mainModel instanceof ISysEvaluationCountModel) {
					ISysEvaluationCountModel countModel = (ISysEvaluationCountModel) mainModel;
					// 实现一
					// countModel.setDocEvalCount(Integer.valueOf(getRecordCountByModel(mainModel)
					// -1));
					// 实现二
					Integer count = countModel.getDocEvalCount();
					count = (count == null ? Integer.valueOf(0) : Integer.valueOf(count.intValue() - 1));
					countModel.setDocEvalCount(count.intValue() < 0 ? Integer.valueOf(0) : count);
				}
				if (mainModel instanceof ISysEvaluationAvgScoreModel) {
					ISysEvaluationAvgScoreModel scoreModel = (ISysEvaluationAvgScoreModel) mainModel;
					Double fdAvgScore = score(mainModel.getClass().getName(), mainModel.getFdId());
					BigDecimal b = new BigDecimal(fdAvgScore);
					fdAvgScore = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					scoreModel.setDocScore(fdAvgScore);
				}
				saveMainModel(mainModel);
			}
		}

	}
	
	/**
	 * 获取全文点评的所有模块名
	 */
	@Override
	public JSONArray listEvalModels(RequestContext requestInfo) throws Exception{
		JSONArray jsonArray = new JSONArray();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(" DISTINCT sysEvaluationMain.fdModelName ");
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
	 * 列出被点评的文档名称
	 */
	@Override
	public String getEvalDocSubject(String docModelName, String docModelId) throws Exception{
		String docSubject = "";
		SysDictModel docModel = SysDataDict.getInstance().getModel(docModelName);
		if(docModel != null){
			IBaseService baseService = (IBaseService) applicationContext
					.getBean(docModel.getServiceBean());
			IBaseModel baseModel = (IBaseModel) baseService
					.findByPrimaryKey(docModelId);
			
			if(PropertyUtils.isReadable(baseModel, "docSubject")){
				docSubject = (String)PropertyUtils
							.getProperty(baseModel,"docSubject");
			}else if(PropertyUtils.isReadable(baseModel, "fdName")){
				docSubject = (String)PropertyUtils
							.getProperty(baseModel,"fdName");
			}
		}
		return docSubject;
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

	@Override
	public JSONArray getEvalStarDetail(String fdModelName, String fdModelId)
			throws Exception {
		JSONArray array =  new JSONArray();
		List<Object[]> list = ((ISysEvaluationMainDao) getBaseDao()).getEvalStarDetail(fdModelName, fdModelId);
		Iterator<Object[]> iter = list.iterator();
		while(iter.hasNext()) {
			Object[] tmp = iter.next();
			JSONObject json = new JSONObject();
			json.put("score" , tmp[1].toString());
			json.put("times", tmp[0].toString());
			array.add(json);
		}
		return array;
	}
	
	
	
	@Override
	public  String getTopEvaluationIdByUserId(String modelName, String modelId, String userId) throws Exception {
		if(StringUtil.isNull(modelName) || StringUtil.isNull(modelId)) {
			return null;
		}
		if(StringUtil.isNull(userId)) {
			userId = UserUtil.getUser().getFdId();
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdModelName=:fdModelName  and fdModelId=:fdModelId and fdEvaluator.fdId=:userId and fdParentId is null");
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setParameter("fdModelId", modelId);
		hqlInfo.setParameter("fdModelName", modelName);
		hqlInfo.setParameter("userId", userId);
		hqlInfo.setOrderBy("fdEvaluationTime asc");
		Object obj = this.findFirstOne(hqlInfo);
		if(obj != null) {
			return obj.toString();
		}
		return null;
	}
	
	/**
	 * 获取点评列表的附件
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings({"unchecked"})
	public JSONObject getListAtt(String[] list, String modelName) throws Exception {
		HashMap<String, JSONArray> map = new HashMap<String, JSONArray>();
		if(list != null && list.length > 0) {
			
			ISysAttMainCoreInnerService attService = 
					(ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdModelName=:fdModelName and fdModelId in(:ids)");
			hqlInfo.setParameter("fdModelName", modelName);
			hqlInfo.setParameter("ids", Arrays.asList(list));
			List<SysAttMain> attList = (List<SysAttMain>)attService.findList(hqlInfo);
			if(!ArrayUtil.isEmpty(attList)) {
				for(SysAttMain att : attList) {
					String mid = att.getFdModelId();
					if(!map.containsKey(mid)) {
						map.put(mid, new JSONArray());
					}
					
					JSONArray arr = map.get(mid);
					
					JSONObject item = new JSONObject();
					item.put("fdId", att.getFdId());
					item.put("fdKey", att.getFdKey());
					item.put("fdFileName", att.getFdFileName());
					item.put("fdContentType", att.getFdContentType());
					item.put("fdSize", att.getFdSize());
					item.put("fdFileId", att.getFdFileId());
					item.put("downloadSum", att.getDownloadSum());
					
					arr.add(item);
				}
			}
		}
		return JSONObject.fromObject(map);
	}

	@Override
	public List findEvaluationMainList(String fdModelId, String fdModelName, String fdKey) throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		String whereBlock=" fdModelId=:fdModelId and fdModelName=:fdModelName and fdKey=:fdKey";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdModelId", fdModelId);
		hqlInfo.setParameter("fdModelName", fdModelName);
		hqlInfo.setParameter("fdKey", fdKey);
		return this.findList(hqlInfo);
	}
	
	
	
}
