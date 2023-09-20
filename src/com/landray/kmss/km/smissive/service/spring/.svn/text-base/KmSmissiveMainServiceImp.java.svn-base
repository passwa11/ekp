package com.landray.kmss.km.smissive.service.spring;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCenterOfficeProvider;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.smissive.forms.KmSmissiveMainForm;
import com.landray.kmss.km.smissive.model.KmSmissiveCirculation;
import com.landray.kmss.km.smissive.model.KmSmissiveMain;
import com.landray.kmss.km.smissive.model.KmSmissiveNumber;
import com.landray.kmss.km.smissive.model.KmSmissiveTemplate;
import com.landray.kmss.km.smissive.service.IKmSmissiveMainService;
import com.landray.kmss.km.smissive.service.IKmSmissiveNumberService;
import com.landray.kmss.km.smissive.service.IKmSmissiveTemplateService;
import com.landray.kmss.km.smissive.synchro.KmSmissiveWpsThread;
import com.landray.kmss.km.smissive.util.SmissiveUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.BookMarkUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.FileDowloadUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.number.service.ISysNumberMainMappService;
import com.landray.kmss.sys.number.util.NumberResourceUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 张鹏xn 公文管理业务接口实现
 */
public class KmSmissiveMainServiceImp extends ExtendDataServiceImp implements
		IKmSmissiveMainService, ApplicationListener {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmSmissiveMainServiceImp.class);
	private ThreadPoolTaskExecutor wpsThreadPool;

	public void setWpsThreadPool(ThreadPoolTaskExecutor wpsThreadPool) {
		this.wpsThreadPool = wpsThreadPool;
	}

	private IKmSmissiveNumberService kmSmissiveNumberService;

	public void setKmSmissiveNumberService(IKmSmissiveNumberService kmSmissiveNumberService) {
		this.kmSmissiveNumberService = kmSmissiveNumberService;
	}

	private ISysAttMainCoreInnerService sysAttMainService = null;
	ISysAttachmentWpsCenterOfficeProvider sysAttachmentWpsCenterOfficeProvider = null;

	private ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	private ISysAttachmentWpsCenterOfficeProvider getSysAttachmentWpsCenterOfficeProvider() {
		if (sysAttachmentWpsCenterOfficeProvider == null) {
			sysAttachmentWpsCenterOfficeProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
					.getBean("wpsCenterProvider");
		}

		return sysAttachmentWpsCenterOfficeProvider;
	}

	@Override
	protected IBaseModel initBizModelSetting(RequestContext requestContext)
			throws Exception {
		KmSmissiveMain kmSmissiveMain = new KmSmissiveMain();
		String templateId = requestContext.getParameter("fdTemplateId");
		if (StringUtil.isNull(templateId)) {
            return kmSmissiveMain;
        }
		KmSmissiveTemplate template = (KmSmissiveTemplate) templateService
				.findByPrimaryKey(templateId);
		if (template == null) {
			return kmSmissiveMain;
		}
		kmSmissiveMain.setFdTemplate(template);
		// 写入辅助类别信息
		kmSmissiveMain.setDocProperties(new ArrayList(template
				.getDocProperties()));
		kmSmissiveMain.setFdTitle(template.getFdTmpTitle());
		kmSmissiveMain.setFdFlowFlag(true);

		kmSmissiveMain.setFdUrgency(template.getFdTmpUrgency());
		kmSmissiveMain.setFdSecret(template.getFdTmpSecret());
		kmSmissiveMain.setFdMainDept(template.getFdTmpMainDept());
		kmSmissiveMain.setFdIssuer(template.getFdTmpIssuer());
		kmSmissiveMain.setFdSendDept(template.getFdTmpSendDept());

		kmSmissiveMain.setFdCopyDept(template.getFdTmpCopyDept());

		kmSmissiveMain.setDocAuthor(UserUtil.getUser());
		return kmSmissiveMain;
	}

	@Override
	protected void initCoreServiceFormSetting(IExtendForm form,
			IBaseModel model, RequestContext requestContext) throws Exception {
		KmSmissiveMain kmSmissiveMain = (KmSmissiveMain) model;
		dispatchCoreService.initFormSetting(form, "smissiveDoc", kmSmissiveMain
				.getFdTemplate(), "smissiveDoc", requestContext);
	}

	// 注入类别模板
	private IKmSmissiveTemplateService templateService;

	public void setTemplateService(IKmSmissiveTemplateService templateService) {
		this.templateService = templateService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		KmSmissiveMainForm kmSmissiveMainForm = (KmSmissiveMainForm) form;
		String fdId = super.add(form, requestContext);

		this.getBaseDao().getHibernateTemplate().flush();
		if (SysAttWpsCloudUtil.isEnable() || SysAttWpsCenterUtil.isEnable()) {
			KmSmissiveWpsThread thread = new KmSmissiveWpsThread(
					kmSmissiveMainForm.getFdId());
			wpsThreadPool.execute(thread);
		}

		return fdId;
	}

	@Override
	public void addWpsBookMarks(String fdModelId) throws Exception {
		RequestContext requestContext = new RequestContext();
		KmSmissiveMain kmSmissiveMain = (KmSmissiveMain) this
				.findByPrimaryKey(fdModelId, KmSmissiveMain.class, true);
		KmSmissiveMainForm kmSmissiveMainForm = new KmSmissiveMainForm();
		kmSmissiveMainForm = (KmSmissiveMainForm) this
				.convertModelToForm(kmSmissiveMainForm,
						kmSmissiveMain, requestContext);
		JSONObject bookMarks = getbaseBookMarks(kmSmissiveMainForm);
		addWpsBookMarks(kmSmissiveMain, bookMarks);
	}

	private JSONObject getbaseBookMarks(KmSmissiveMainForm kmSmissiveMainForm) {
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(kmSmissiveMainForm.getDocSubject())) {
			json.put("docSubject", kmSmissiveMainForm.getDocSubject());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getDocAuthorName())) {
			json.put("docAuthorName", kmSmissiveMainForm.getDocAuthorName());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getFdUrgency())) {
			json.put("fdUrgency", kmSmissiveMainForm.getFdUrgency());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getFdTemplateName())) {
			json.put("fdTemplateName", kmSmissiveMainForm.getFdTemplateName());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getDocCreateTime())) {
			json.put("docCreateTime", kmSmissiveMainForm.getDocCreateTime());
			json.put("docCreateTimeCn", SmissiveUtil
					.getCnDate(kmSmissiveMainForm.getDocCreateTime()));
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getFdSecret())) {
			json.put("fdSecret", kmSmissiveMainForm.getFdSecret());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getFdFileNo())) {
			json.put("fdFileNo", kmSmissiveMainForm.getFdFileNo());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getFdMainDeptName())) {
			json.put("fdMainDeptName", kmSmissiveMainForm.getFdMainDeptName());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getFdSendDeptNames())) {
			json.put("fdSendDeptNames",
					kmSmissiveMainForm.getFdSendDeptNames());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getDocPublishTime())) {
			json.put("docPublishTime",
					kmSmissiveMainForm.getDocPublishTime());
			json.put("docPublishTimeCn", SmissiveUtil
					.getCnDate(kmSmissiveMainForm.getDocPublishTime()));
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getFdCopyDeptNames())) {
			json.put("fdCopyDeptNames",
					kmSmissiveMainForm.getFdCopyDeptNames());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getFdIssuerName())) {
			json.put("fdIssuerName",
					kmSmissiveMainForm.getFdIssuerName());
		}
		if (StringUtil.isNotNull(kmSmissiveMainForm.getDocCreatorName())) {
			json.put("docCreatorName",
					kmSmissiveMainForm.getDocCreatorName());
		}

		Iterator iterator = json.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = json.get(key).toString();
			json.put(key,
					value.replaceAll("'", "&#039;").replaceAll("\"", "&#034;"));
		}
		return json;
	}

	private void addWpsBookMarks(KmSmissiveMain kmSmissiveMain,
			JSONObject bookMarks) throws Exception {
		List atts = getSysAttMainService().findByModelKey(
				KmSmissiveMain.class.getName(),
				kmSmissiveMain.getFdId(), "mainOnline");
		if (atts != null && atts.size() > 0) {
			SysAttMain sysAttMain = (SysAttMain) atts.get(0);
			String contentWpsDownloadUrl = SysAttWpsCloudUtil
					.getWpsDownloadUrl(sysAttMain.getFdId());
			if (StringUtil.isNotNull(contentWpsDownloadUrl)) {
				String fileExt = FilenameUtils
						.getExtension(sysAttMain.getFdFileName());
				// 获取正文书签
				List<String> bookMarkList = BookMarkUtil
						.getBookMarkList(FileDowloadUtil
								.getFileInputStream(contentWpsDownloadUrl),
								fileExt);
				if (bookMarkList.size() > 0) {
					addWpsBookMarks(sysAttMain, bookMarkList, bookMarks);
				}
			} else if (SysAttWpsCenterUtil.isEnable()) {
					String fileExt = FilenameUtils
							.getExtension(sysAttMain.getFdFileName());
					// 获取正文书签
					List<String> bookMarkList = BookMarkUtil
							.getBookMarkList(getSysAttMainService().getInputStream(sysAttMain),fileExt);

					if (bookMarkList != null && bookMarkList.size() > 0) {
						addWpsBookMarksToWpsCenter(sysAttMain, bookMarkList, bookMarks);
					}
			}
		}
	}

	/**
	 * 使用WPS中台添加书签内容
	 *
	 * @param sysAttMain
	 * @param bookMarkList
	 * @param bookMarks
	 * @throws Exception
	 */
	private void addWpsBookMarksToWpsCenter(SysAttMain sysAttMain,
											List<String> bookMarkList, JSONObject bookMarks) throws Exception {
		String tempExt = FilenameUtils.getExtension(sysAttMain.getFdFileName());
		List<Map<String, Object>> fileInfos = new ArrayList<>();
		Map<String, Object> fileInfo = new HashMap<>();
		fileInfo.put("templateId", sysAttMain.getFdId());// downurl：套红模板下载地址
		fileInfo.put("templateName", sysAttMain.getFdFileName());// fileID：文件ID
		fileInfos.add(fileInfo);
	//	System.out.println(">>>>>>>>>>sysAttMain.getFdId():" + sysAttMain.getFdId());
		List<Map<String, Object>> fillDatas = new ArrayList<>();
		for (String bookMark : bookMarkList) {
			Map<String, Object> fillData = new HashMap<>();
			fillData.put("bookmark", bookMark);// 书签名称
			if (bookMarks.containsKey(bookMark)) {
				fillData.put("content", bookMarks.get(bookMark));// 填充内容
			} else {
				fillData.put("content", "");// 填充内容
			}
			fillData.put("type", 0);// 填充类型，0-文字，1-文档
			fillDatas.add(fillData);
		}

		String wpsId = getSysAttachmentWpsCenterOfficeProvider().wpsCenterWrapHeader(fileInfos, fillDatas,
				sysAttMain.getFdModelId(),sysAttMain.getFdModelName(),sysAttMain.getFdId(),"",
				UserUtil.getUser().getFdId());

		Thread.sleep(2000); // 书签替换结果返回大概2到3秒

		if (StringUtil.isNotNull(wpsId)) {
			InputStream in  = getSysAttachmentWpsCenterOfficeProvider().downloadByTaskId(wpsId);
			if (logger.isDebugEnabled()) {
				logger.debug("*************  in:" + in);
			}
			sysAttMain.setInputStream(in);
			getSysAttMainService().update(sysAttMain);
		}
	}
	private void addWpsBookMarks(SysAttMain sysAttMain,
			List<String> bookMarkList, JSONObject bookMarks) throws Exception {
		String tempExt = FilenameUtils.getExtension(sysAttMain.getFdFileName());
		List<Map<String, Object>> fileInfos = new ArrayList<>();
		Map<String, Object> fileInfo = new HashMap<>();
		String downUrl = SysAttWpsCloudUtil
				.getWpsDownloadUrl(sysAttMain.getFdId());
		fileInfo.put("location", downUrl);// downurl：套红模板下载地址
		fileInfo.put("fileID", sysAttMain.getFdFileId() + new Date().getTime());// fileID：文件ID
		fileInfo.put("ext", tempExt);// 文件后缀
		fileInfo.put("sourceType", 1);// 1-http文件下载地址（Content-Type：application/json）
		fileInfo.put("args", new HashMap<>());
		fileInfos.add(fileInfo);
		List<Map<String, Object>> fillDatas = new ArrayList<>();
		for (String bookMark : bookMarkList) {
			Map<String, Object> fillData = new HashMap<>();
			fillData.put("bookmark", bookMark);// 书签名称
			if (bookMarks.containsKey(bookMark)) {
				fillData.put("content", bookMarks.get(bookMark));// 填充内容
			} else {
				fillData.put("content", "");// 填充内容
			}
			fillData.put("type", 0);// 填充类型，0-文字，1-文档
			fillDatas.add(fillData);
		}
		String wpsId = SysAttWpsCloudUtil.setRed(fileInfos, fillDatas,
				sysAttMain.getFdModelId(),
				sysAttMain.getFdModelName());
		if (StringUtil.isNotNull(wpsId)) {
			String fdDownloadUrl = SysAttWpsCloudUtil.getResult(wpsId);
			int count = 0;
			Thread.sleep(2000); // 书签替换结果返回大概2到3秒，暂时先停顿两秒，后续通过事件回调来优化
			while (fdDownloadUrl == null && count < 50) {
				count++;
				fdDownloadUrl = SysAttWpsCloudUtil.getResult(wpsId);
			}
			if (StringUtil.isNotNull(fdDownloadUrl)) {
				InputStream in = null;
				in = FileDowloadUtil.getFileInputStream(fdDownloadUrl);
				sysAttMain.setInputStream(in);
				getSysAttMainService().update(sysAttMain);
				SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMain.getFdId());
			}
		}

	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		// TODO Auto-generated method stub
		// 删除传阅记录
		final String fdId = modelObj.getFdId();
		this.getBaseDao().getHibernateTemplate().execute(
				new HibernateCallback<Object>() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException {
						// TODO Auto-generated method stub
						StringBuilder sb = new StringBuilder();
						sb
								.append("delete KmSmissiveCirculation cir where cir.fdSmissiveMain.fdId='");
						sb.append(fdId);
						sb.append("'");

						return session.createQuery(sb.toString())
								.executeUpdate();
					}

				});
		super.delete(modelObj);
	}
	
	private ISysNumberFlowService sysNumberFlowService;
	public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService)
	{
		this.sysNumberFlowService=sysNumberFlowService;
	}


	/**
	 * 发布时触发的通知事件并填写发布时间、文件编号，并检查签发人
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// TODO Auto-generated method stub
		if (event == null) {
            return;
        }
		Object obj = event.getSource();
		// 若该事件不是自己的域模型，不处理
		if (!(obj instanceof KmSmissiveMain)) {
            return;
        }
		// 处理流程发布事件
		if (event instanceof Event_SysFlowFinish) {
			try {
				KmSmissiveMain kmSmissiveMain = (KmSmissiveMain) obj;
				// 判断文档状态是否为发布 若是则进行发布时间、签发人、字号的处理
				if (kmSmissiveMain.getDocStatus().equals(
						SysDocConstant.DOC_STATUS_PUBLISH)) {
					// 填写发布时间
					kmSmissiveMain.setDocPublishTime(new Date());
					// 检查签发人
					if (kmSmissiveMain.getFdIssuer() == null) {
						kmSmissiveMain.setFdIssuer(UserUtil.getUser());
					}
					if(StringUtil.isNull(kmSmissiveMain.getFdFileNo())){
						if (NumberResourceUtil.isModuleNumberEnable(ModelUtil.getModelClassName(kmSmissiveMain))) {
							SysNumberMainMapp sysNumberMainMapp = (SysNumberMainMapp) ((ISysNumberMainMappService) SpringBeanUtil
									.getBean("sysNumberMainMappService")).getSysNumberMainMapp(
											"com.landray.kmss.km.smissive.model.KmSmissiveMain",
											kmSmissiveMain.getFdTemplate().getFdId());
							String tempNumFromDb = "";
							if (sysNumberMainMapp != null) {
								tempNumFromDb = kmSmissiveNumberService
										.getTempNumFromDb(sysNumberMainMapp.getFdNumber().getFdId());
							}

							if (sysNumberMainMapp != null && !"3".equals(sysNumberMainMapp.getFdType())) {
								if (StringUtil.isNotNull(tempNumFromDb)) {
									kmSmissiveMain.setFdFileNo(tempNumFromDb);
									kmSmissiveNumberService.deleteTempNumFromDb(
											sysNumberMainMapp.getFdNumber().getFdId(), tempNumFromDb);
								} else {
									String fileCode = getdocNum(kmSmissiveMain);
									kmSmissiveMain.setFdFileNo(fileCode);
								}
							}
						} else {
							String fileCode = getdocNum(kmSmissiveMain);
							kmSmissiveMain.setFdFileNo(fileCode);
						}
					}
					// 更新
					//kmSmissiveMain.setFdTemplate(template);
					getBaseDao().update(kmSmissiveMain);
				}
			} catch (Exception e) {
				LoggerFactory.getLogger(KmSmissiveMainServiceImp.class).error("",e);
				e.printStackTrace();
			}
		}
	}
	
	private void addTempNumber(String fdNumberId, String fdFileNo) throws Exception {
		KmSmissiveNumber kmSmissiveNumber = new KmSmissiveNumber();
		kmSmissiveNumber.setFdNumberId(fdNumberId);
		kmSmissiveNumber.setFdNumberValue(fdFileNo);
		kmSmissiveNumber.setDocCreator(UserUtil.getUser());
		kmSmissiveNumber.setDocCreateTime(new Date());
		kmSmissiveNumberService.add(kmSmissiveNumber);
	}

	@Override
	public String initdocNumByNumberId(KmSmissiveMain kmSmissiveMain,
									   String fdNumberId) throws Exception {
		String fdFileNo = "";
		fdFileNo = getdocNumByNumberId(kmSmissiveMain, fdNumberId);
		addTempNumber(fdNumberId, fdFileNo);
		return fdFileNo;
	}

	@Override
	public String getdocNumByNumberId(KmSmissiveMain kmSmissiveMain, String fdNumberId) throws Exception {
		String fdFileNo = "";
		// 生成编号规则
		fdFileNo = sysNumberFlowService.generateFlowNumber(kmSmissiveMain,fdNumberId);
		return fdFileNo;
	}
	
	@Override
	public String getdocNum(KmSmissiveMain kmSmissiveMain)
	throws Exception {
		// 生成文件编号
		KmSmissiveTemplate template = (KmSmissiveTemplate) templateService
				.findByPrimaryKey(kmSmissiveMain.getFdTemplate()
						.getFdId());
		Integer fileNo=null;
		String fileCode=null;
		// 增加编号机制，并与原有的编号定义兼容  zhanglz 2012年10月17日 update
		if(NumberResourceUtil.isModuleNumberEnable(ModelUtil.getModelClassName(kmSmissiveMain)))
		{
			fileCode=sysNumberFlowService.generateFlowNumber(kmSmissiveMain);
			fileNo=Integer.valueOf(sysNumberFlowService.getFlowNo(kmSmissiveMain));
		}
		else
		{
			String codePre = template.getFdCodePre();
			fileNo = template.getFdCurNo();
			if (fileNo != null) {
				fileNo++;
			} else {
				fileNo = 1;
			}
			// 同时更新模板流水号
			template.setFdCurNo(fileNo);
			//跨年时，自动更新模板年份，并更新流水号从1开始 2013-1-24 zhanglz
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			String currYear=calendar.get(Calendar.YEAR)+"";
			if (!currYear.equals(template.getFdYear())) {
				template.setFdYear(currYear);
				if (fileNo!=1) {
					template.setFdCurNo(1);
				}
			}

			fileCode = StringUtil.replace(codePre, "%年号%",
					template.getFdYear());
			fileCode = StringUtil.replace(fileCode, "%流水号%", String
					.valueOf(template.getFdCurNo()));
		}
		// 更新
		kmSmissiveMain.setFdTemplate(template);
		return fileCode;
		
	}
	
	@Override
	public String updateDocNum(HttpServletRequest request) throws Exception {
		String fdId = request.getParameter("fdId");
		String fdDocNum = request.getParameter("fdDocNum");
		HQLInfo hqlInfo = new HQLInfo();
		// 编号是否重复
		String isRepeat = "false";
		// 更新文档编号是否成功标志
		String flag = "false";
		int m = 0;
		String whereBlock = " kmSmissiveMain.fdFileNo = :fdFileNo and kmSmissiveMain.fdId <> :fdId";
		hqlInfo.setParameter("fdFileNo", fdDocNum);
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setWhereBlock(whereBlock);
		List kmSmissiveMainList = this.findList(hqlInfo);
		if (kmSmissiveMainList.size() == 0) {
			KmSmissiveMain kmSmissiveMain = (KmSmissiveMain) findByPrimaryKey(fdId);
			if (UserOperHelper.allowLogOper("saveDocNum", getModelName())) {
				UserOperContentHelper.putUpdate(fdId, kmSmissiveMain.getDocSubject(), getModelName())
						.putSimple("fdFileNo", kmSmissiveMain.getFdFileNo(), fdDocNum);
			}
			String hql = " update  KmSmissiveMain kmSmissiveMain set kmSmissiveMain.fdFileNo = '" + fdDocNum
					+ "' where fdId = '" + fdId + "'";
			Query query = this.getBaseDao().getHibernateSession().createQuery(hql);
			m = query.executeUpdate();
		} else {
			isRepeat = "true";
		}
		if(m>0){
			flag = "true";
		}
		JSONObject json = new JSONObject();
		json.put("isRepeat", isRepeat);
		json.put("flag", flag);
		return json.toString();
	}

	@Override
	public void updateAttachmentRight(KmSmissiveMainForm form,
									  RequestContext requestContext) throws Exception {
		// TODO Auto-generated method stub
		KmSmissiveMain model = (KmSmissiveMain) this.getBaseDao()
				.findByPrimaryKey(form.getFdId());
		KmSmissiveMain tempModel = (KmSmissiveMain) convertFormToModel(form,
				null, requestContext);
		model.setAuthAttCopys(tempModel.getAuthAttCopys());
		model.setAuthAttDownloads(tempModel.getAuthAttDownloads());
		model.setAuthAttPrints(tempModel.getAuthAttPrints());
		model.setAuthAttNocopy(tempModel.getAuthAttNocopy());
		model.setAuthAttNodownload(tempModel.getAuthAttNodownload());
		model.setAuthAttNoprint(tempModel.getAuthAttNoprint());
		this.getBaseDao().update(model);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateRight(KmSmissiveMainForm form,
			RequestContext requestContext) throws Exception {
		// TODO Auto-generated method stub
		KmSmissiveMain model = (KmSmissiveMain) this.getBaseDao()
				.findByPrimaryKey(form.getFdId());
		model.setFdNotifyType(form.getFdNotifyType());

		ConvertorContext context = new ConvertorContext();
		context.setBaseService(this);
		context.setSObject(form);
		context.setTObject(model);
		if ("add".equalsIgnoreCase(form.getRightType())) {
			// 保存原来的读者
			List readers = new ArrayList();
			if (model.getAuthReaders() != null) {
				readers.addAll(model.getAuthReaders());
			}

			context.setSPropertyName("newReaderIds");
			FormConvertor_IDsToModelList convertor = new FormConvertor_IDsToModelList(
					"authReaders", SysOrgElement.class);
			convertor.excute(context);
			List notifyReaders = model.getAuthReaders();
			ArrayUtil.concatTwoList(notifyReaders, readers);
			model.setAuthReaders(readers);
			send(model, notifyReaders);
		} else {
			context.setSPropertyName("authReaderIds");
			FormConvertor_IDsToModelList convertor = new FormConvertor_IDsToModelList(
					"authReaders", SysOrgElement.class);
			convertor.excute(context);
			send(model, null);
		}
		this.getBaseDao().update(model);

	}

	// 通知机制 添加sysNotifyMainCoreService属性
	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	// 发送通知
	@SuppressWarnings("unchecked")
	protected void send(KmSmissiveMain model, List addReaders) {
		try {
			// 判断通知人是否为空
			if ((!model.getAuthReaders().isEmpty() && addReaders == null)
					|| (addReaders != null && !addReaders.isEmpty())) {
				// 获取替换文本
				NotifyContext notifyContext = sysNotifyMainCoreService
						.getContext("km-smissive:kmSmissiveMain.notify");

				// 通知方式
				notifyContext.setNotifyType(model.getFdNotifyType());
				// 通知人
				if (addReaders != null) {
					notifyContext.setNotifyTarget(addReaders);
				} else {
					notifyContext.setNotifyTarget(model.getAuthReaders());
				}

				// 设置发布类型为“待办”（默认为待阅）一般用于简单的通知
				notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
				// 设置发布KEY值，为后面的删除准备
				notifyContext.setKey("sendFromKmSmissiveMain");

				// 添加文本
				HashMap<String, String> replaceMap = new HashMap<String, String>();

				// 设置通知标题
				// km-smissive:kmSmissiveMain.docSubject是资源文件里配置的变量
				replaceMap.put("km-smissive:kmSmissiveMain.docSubject", model
						.getDocSubject());

				sysNotifyMainCoreService.send(model, notifyContext, replaceMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LoggerFactory.getLogger(KmSmissiveMainServiceImp.class).error("", e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addCirculation(KmSmissiveMainForm form,
			RequestContext requestContext) throws Exception {
		// TODO Auto-generated method stub
		if (form.getNewReaderIds() == null
				|| "".equalsIgnoreCase(form.getNewReaderIds())) {
			return;
		}
		KmSmissiveMain model = (KmSmissiveMain) this.getBaseDao()
				.findByPrimaryKey(form.getFdId());
		model.setFdNotifyType(form.getFdNotifyType());

		ConvertorContext context = new ConvertorContext();
		context.setBaseService(this);
		context.setSObject(form);
		context.setTObject(model);

		// 保存原来的读者
		List readers = new ArrayList();
		List allReaders = new ArrayList();
		if (model.getAuthReaders() != null) {
			readers.addAll(model.getAuthReaders());
			allReaders.addAll(model.getAuthAllReaders());
		}

		context.setSPropertyName("newReaderIds");
		FormConvertor_IDsToModelList convertor = new FormConvertor_IDsToModelList(
				"authReaders", SysOrgElement.class);
		convertor.excute(context);
		// 增加传阅读者
		List notifyReaders = model.getAuthReaders();
		ArrayUtil.concatTwoList(notifyReaders, readers);
		model.setAuthReaders(readers);
		ArrayUtil.concatTwoList(notifyReaders, allReaders);
		model.setAuthAllReaders(allReaders);
		send(model, notifyReaders);

		// 增加传阅记录
		KmSmissiveCirculation circulationModel = new KmSmissiveCirculation();
		circulationModel.setFdId(IDGenerator.generateID());
		circulationModel.setDocSubject(form.getCirculationReason());
		circulationModel.setFdCirculationIds(form.getNewReaderIds());
		circulationModel.setFdCirculationNames(form.getNewReaderNames());
		circulationModel.setDocCreator(UserUtil.getUser());
		circulationModel.setDocCreateTime(new Date());
		circulationModel.setFdSmissiveMain(model);

		this.getBaseDao().update(circulationModel);

	}

	@Override
	public void updateIssuer(KmSmissiveMainForm form,
							 RequestContext requestContext) throws Exception {
		// TODO Auto-generated method stub
		KmSmissiveMain model = (KmSmissiveMain) this.getBaseDao()
				.findByPrimaryKey(form.getFdId());

		if(UserOperHelper.allowLogOper("modifyIssuer", getModelName())){
			UserOperContentHelper.putUpdate(model.getFdId(),model.getDocSubject(),getModelName()).putSimple("fdIssuerId", model.getFdIssuer().getFdId(), form.getFdIssuerId());
		}
		
		ConvertorContext context = new ConvertorContext();
		context.setBaseService(this);
		context.setSObject(form);
		context.setTObject(model);

		context.setSPropertyName("fdIssuerId");
		FormConvertor_IDToModel convertor = new FormConvertor_IDToModel(
				"fdIssuer", SysOrgPerson.class);
		convertor.excute(context);
		this.getBaseDao().update(model);
	}

	
	/*
	 * 判断编号是否唯一
	 */
	@Override
	public Boolean checkUniqueNum(String fdId, String TempId,
								  String docNum) throws Exception {
		List kmSmissiveMainList = this.findList(
				" kmSmissiveMain.fdFileNo = '" + docNum
						+ "' and kmSmissiveMain.fdId <> '"
						+ fdId + "' ","");
		if (kmSmissiveMainList.size() > 0) {
			return false;
		}
		return true;
	}

}
