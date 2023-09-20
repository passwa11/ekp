package com.landray.kmss.sys.news.service.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.elec.device.client.*;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.BasicInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.ContentInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.ParticipantInfo;
import com.landray.kmss.elec.device.service.IElecChannelAnsyService;
import com.landray.kmss.elec.device.service.IElecChannelContractService;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.model.SysNewsNumberLock;
import com.landray.kmss.sys.news.model.SysNewsOutSign;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.sys.news.service.ISysNewsOutSignService;
import com.landray.kmss.sys.news.service.ISysNewsYqqSignService;
import com.landray.kmss.sys.news.util.ZipUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.ws.security.util.UUIDGenerator;
import org.slf4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SysNewsYqqSignServiceImp implements ISysNewsYqqSignService,IElecChannelAnsyService{

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysNewsYqqSignServiceImp.class);

	public static final String status_code_init = "00";// 发送签署成功
	public static final String status_code_received = "01";// 发送签署后回调
	public static final String status_code_callback = "02";// 当前签署人签署完成后回调
	public static final String status_code_finish = "03";// 所有人签署完成

	public static final String callback_type_started = "envelopeStarted";
	public static final String callback_type_handing = "participantHandling";
	public static final String callback_type_handled = "participantConfirmed";
	public static final String callback_type_end = "envelopeCompleted";

	public static final String outsign_type_yqq = "yqq";

	private static final String MODEL_ALIAS_NAME = "sys-news";
	
	private static final String YQQ_CODE_NOT_FOUND_SENDER = "100550174";

	private IElecChannelContractService elecYqqService = null;

	public IElecChannelContractService getElecYqqService() {
		if (elecYqqService == null) {
			IExtension[] extensions = Plugin.getExtensions(
					"com.landray.kmss.elec.device.contractService",
					IElecChannelRequestMessage.class.getName(), "convertor");
			for (IExtension extension : extensions) {
				String channel = Plugin.getParamValueString(extension,
						"channel");
				if ("yqq".equals(channel)) {
					elecYqqService = Plugin.getParamValue(extension, "bean");
				}
			}
		}
		return elecYqqService;
	}

	private ISysAttMainCoreInnerService sysAttMainCoreInnerService = null;

	public ISysAttMainCoreInnerService getSysAttMainCoreInnerService() {
		if (sysAttMainCoreInnerService == null) {
			sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainCoreInnerService;
	}

	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}

	private ISysAttUploadService sysAttUploadService = null;

	public ISysAttUploadService getSysAttUploadService() {
		if (sysAttUploadService == null) {
			sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
					.getBean("sysAttUploadService");
		}
		return sysAttUploadService;
	}

	private ISysNewsMainService sysNewsMainService;

	public ISysNewsMainService getSysNewsMainService() {
		if (sysNewsMainService == null) {
			sysNewsMainService = (ISysNewsMainService) SpringBeanUtil
					.getBean("sysNewsMainService");
		}
		return sysNewsMainService;
	}

	private ISysNewsOutSignService sysNewsOutSignService;

	public ISysNewsOutSignService getSysNewsOutSignService() {
		if (sysNewsOutSignService == null) {
			sysNewsOutSignService = (ISysNewsOutSignService) SpringBeanUtil
					.getBean("sysNewsOutSignService");
		}
		return sysNewsOutSignService;
	}
	
	ISysNotifyMainCoreService sysNotifyMainCoreService = null;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
					.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}
	
	ISysOrgPersonService sysOrgPersonService = null;;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	@Override
    public Boolean sendYqq(SysNewsMain sysNewsMain, String phone, String fdEnterprise,
                           List<SysOrgPerson> elements) throws Exception {
		logger.info("进入易企签附加选项...");
		logger.info("mainModel ID：" + sysNewsMain.getFdId());
		Boolean successStauts = true;
		SysNewsNumberLock sysNewsNumberLock = new SysNewsNumberLock();
		try {
			getComponentLockService().tryLock(sysNewsNumberLock,
					sysNewsMain.getFdId());
			logger.debug("SysNewsYqqSignServiceImp-sendYqq_start");
			SysOrgPerson user = UserUtil.getUser();
			ElecAdditionalInfo additionalInfo = new ElecAdditionalInfo();
			additionalInfo.setTxCode(ElecChannelTxCodeEnum.UPLOAD_CONTRACT);
			ElecChannelContractSignInfo contractSignInfo = new ElecChannelContractSignInfo();
			BasicInfo basicInfo = contractSignInfo.new BasicInfo();
			basicInfo.setContractNo(sysNewsMain.getFdId());
			basicInfo.setContractName(sysNewsMain.getDocSubject());
			JSONObject metaMap = new JSONObject();
			metaMap.put("signId", sysNewsMain.getFdId());
			basicInfo.setMetadata(metaMap);
			additionalInfo.setKeyword1(sysNewsMain.getFdId());
			additionalInfo.setKeyword2(sysNewsMain.getDocSubject());
			additionalInfo.setKeyword3(MODEL_ALIAS_NAME);
			contractSignInfo.setCustomTag(
					MODEL_ALIAS_NAME + "-" + sysNewsMain.getFdId());

			logger.debug("SysNewsYqqSignServiceImp-sendYqq-contentInfos"
					+ contractSignInfo);

			// 附件
			List<ContentInfo> contentInfos = new ArrayList<>();
			List<SysAttMain> yqqSignedAtts = getSysAttMainCoreInnerService()
					.findByModelKey(
							"com.landray.kmss.sys.news.model.SysNewsMain",
							sysNewsMain.getFdId(), "yqqSigned");
			List<SysAttMain> attList = getSysAttMainCoreInnerService()
					.findByModelKey(
							"com.landray.kmss.sys.news.model.SysNewsMain",
							sysNewsMain.getFdId(), "fdSignFile");

			if (yqqSignedAtts != null && yqqSignedAtts.size() > 0) {// 并行不在已签署文件上，在签署
				if (attList != null) {
					if (attList.size() == 1) {
						contentInfos = getContentInfos(contractSignInfo,
								yqqSignedAtts);
					} else if (attList.size() > 1) {
						contentInfos = getRarContentInfos(contractSignInfo,
								yqqSignedAtts);
					}
				}
			} else {
				if (attList != null) {
					if (attList.size() == 1) {
						contentInfos = getContentInfos(contractSignInfo,
								attList);
					} else if (attList.size() > 1) {
						contentInfos = getMultiContentInfos(contractSignInfo,
								attList);
					}
				}

			}
			logger.debug("SysNewsYqqSignServiceImp-sendYqq-contentInfos附件:"
					+ contentInfos);
			List<ParticipantInfo> participantInfos = new ArrayList<>();
			// 发送方:我方签约主体
			ParticipantInfo participantInfo = contractSignInfo.new ParticipantInfo();
			participantInfo.setParticipantTarget(ElecParticipantTargetEnum.SENDER);
			participantInfo
					.setUserName(user.getFdName());
			if (StringUtil.isNotNull(fdEnterprise)) {
				participantInfo.setParticipantType(ElecParticipantTypeEnum.ENTERPRISE);
				participantInfo.setEnterpriseName(fdEnterprise);
			} else {
				participantInfo.setParticipantType(ElecParticipantTypeEnum.PERSON);
			}
			if (StringUtil.isNotNull(phone)) {
				participantInfo
						.setPhone(phone.trim());
			} else {
				participantInfo
						.setPhone(user.getFdMobileNo());
			}
			participantInfo
					.setParticipantClassify(ElecParticipantClassifyEnum.INTERNAL_USERS);
			logger.debug("SysNewsYqqSignServiceImp-sendYqq-contentInfos:签约主体"
					+ participantInfo);
			participantInfos.add(participantInfo);
			if (elements != null && elements.size() > 0) {
				// 签署方
				for (SysOrgPerson person : elements) {
					ParticipantInfo participantInfoSend = getParticipantInfo(
							contractSignInfo, phone,fdEnterprise, person);

					participantInfos.add(participantInfoSend);
				}
			} else {
				ParticipantInfo participantInfoSend = getParticipantInfo(
						contractSignInfo, phone, fdEnterprise,user);
				participantInfos.add(participantInfoSend);
			}
			logger.debug("SysNewsYqqSignServiceImp-sendYqq-participantInfos:"
					+ participantInfos);

			contractSignInfo.setBasicInfo(basicInfo);
			contractSignInfo.setContentInfos(contentInfos);
			contractSignInfo.setParticipantInfos(participantInfos);
			logger.debug("易企签附加选项:开始调用易企签服务");
			ElecChannelResponseMessage<ElecContractInfo> message = (ElecChannelResponseMessage<ElecContractInfo>) getElecYqqService()
					.uploadContract(contractSignInfo,
					additionalInfo);
			logger.debug("易企签附加选项:调用易企签服务结束");
			getComponentLockService().unLock(sysNewsNumberLock);
			// 新增签署记录信息
			String code = message.getCode();
			if (YQQ_CODE_NOT_FOUND_SENDER.equals(code)) {
				// 发送方不存在或者不在本企业
				successStauts = false;
			}
		} catch (Exception e) {
			getComponentLockService().unLock(sysNewsNumberLock);
			logger.debug(
					"SysNewsYqqSignServiceImp - Exception:"
							+ e.getMessage());
			throw new Exception(e);
		} finally {
			getComponentLockService().unLock(sysNewsNumberLock);
		}
		logger.debug("SysNewsYqqSignServiceImp-sendYqq_end");
		return successStauts;

	}

	private List<ContentInfo> getMultiContentInfos(
			ElecChannelContractSignInfo contractSignInfo,
			List<SysAttMain> attList) throws Exception {
		List<ContentInfo> contentInfos = new ArrayList<>();
		if (attList != null && attList.size() > 0) {
			ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
					.getBean("sysAttUploadService");
			for (int i = 0; i < attList.size(); i++) {
				SysAttMain sysAttMain = attList.get(i);
				ContentInfo contentInfo1 = contractSignInfo.new ContentInfo();
				contentInfo1.setFileId(sysAttMain.getFdId());
				SysAttFile sysAttFile = sysAttUploadService
						.getFileById(sysAttMain.getFdFileId());
				String path = formatReadFilePath(null,
						sysAttFile.getFdFilePath());
				logger.debug(
						"news-getMultiContentInfos-path:" + path);
				File file = new File(path);
				FileInputStream inputFile = new FileInputStream(file);
				byte[] buffer = new byte[(int) file.length()];
				int count;
				while ((count = inputFile.read(buffer)) > -1) {
				}
				inputFile.close();
				String base64Str = Base64.encodeBase64String(buffer);
				contentInfo1.setFileBase64(base64Str);
				contentInfo1.setIsAttached(false);
				contentInfo1.setSequence(i + 1);
				contentInfos.add(contentInfo1);
			}
		}
		return contentInfos;
	}
	private List<ContentInfo> getContentInfos(
			ElecChannelContractSignInfo contractSignInfo,
			List<SysAttMain> attList) throws Exception {
		List<ContentInfo> contentInfos = new ArrayList<>();
		if (attList != null && attList.size() > 0) {
			ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
					.getBean("sysAttUploadService");
			SysAttMain sysAttMain = attList.get(0);
			ContentInfo contentInfo1 = contractSignInfo.new ContentInfo();
			contentInfo1.setFileId(sysAttMain.getFdFileId());
			SysAttFile sysAttFile = sysAttUploadService
					.getFileById(sysAttMain.getFdFileId());

			String path = formatReadFilePath(null, sysAttFile.getFdFilePath());
			logger.debug(
					"SysNewsYqqSignServiceImp-getContentInfos-path:" + path);
			File file = new File(path);
			FileInputStream inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			int count;
			while ((count = inputFile.read(buffer)) > -1) {
			}
			inputFile.close();
			String base64Str = Base64.encodeBase64String(buffer);
			contentInfo1.setFileBase64(base64Str);
			contentInfo1.setIsAttached(false);
			contentInfo1.setSequence(1);
			contentInfos.add(contentInfo1);
		}
		return contentInfos;
	}

	// 获取压缩文件中的所有文件
	private List<ContentInfo> getRarContentInfos(
			ElecChannelContractSignInfo contractSignInfo,
			List<SysAttMain> attList) throws Exception {
		List<ContentInfo> contentInfos = new ArrayList<>();
		if (attList != null && attList.size() > 0) {
			ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
					.getBean("sysAttUploadService");
			SysAttMain sysAttMain = attList.get(0);
			SysAttFile sysAttFile = sysAttUploadService
					.getFileById(sysAttMain.getFdFileId());
			
			String formatReadFilePath = formatReadFilePath(null,
					sysAttFile.getFdFilePath());

			logger.debug(
					"SysNewsYqqSignServiceImp-getRarContentInfos-path:"
							+ formatReadFilePath);
			File file = new File(formatReadFilePath);
			// 解压
			String destPath = file.getParentFile().getAbsolutePath()
					+ file.getName().substring(0, file.getName().length() - 4);
			ZipUtil.unZip(formatReadFilePath, destPath);

			File rarFile = new File(destPath);

			if (rarFile.exists()) {
				if (rarFile.isDirectory()) {
					File[] files = rarFile.listFiles();
					for (int i = 0; i < files.length; i++) {
						File file2 = files[i];
						FileInputStream inputFile = new FileInputStream(
								file2);
						byte[] buffer = new byte[(int) file2.length()];
						int count;
						while ((count = inputFile.read(buffer)) > -1) {
						}
						inputFile.close();
						String base64Str = Base64.encodeBase64String(buffer);
						ContentInfo contentInfo1 = contractSignInfo.new ContentInfo();
						contentInfo1.setFileId(UUIDGenerator.getUUID());
						contentInfo1.setFileBase64(base64Str);
						contentInfo1.setIsAttached(false);
						contentInfo1.setSequence(i + 1);
						contentInfos.add(contentInfo1);
					}
				}
			}
		}
		return contentInfos;
	}
	
	public String formatReadFilePath(String pathPrefix, String fileRelativePath) {
		if(StringUtil.isNull(pathPrefix)){
			return ResourceUtil.getKmssConfigString("kmss.resource.path") + fileRelativePath;
		}
		return pathPrefix + fileRelativePath;
	}

	private ParticipantInfo getParticipantInfo(
			ElecChannelContractSignInfo contractSignInfo, String phone,String fdEnterprise,
			SysOrgPerson person) {
		ParticipantInfo participantInfoSend = contractSignInfo.new ParticipantInfo();
		participantInfoSend
				.setParticipantTarget(ElecParticipantTargetEnum.RECEIVER);
		participantInfoSend
				.setUserName(
						person.getFdName());
		if (StringUtil.isNotNull(phone)) {
			participantInfoSend
					.setPhone(
							phone.trim());
		} else {
			participantInfoSend
					.setPhone(person.getFdMobileNo());
		}
		
		if (StringUtil.isNotNull(fdEnterprise)) {
			participantInfoSend.setParticipantType(ElecParticipantTypeEnum.ENTERPRISE);
			participantInfoSend.setEnterpriseName(fdEnterprise);
		} else {
			participantInfoSend.setParticipantType(ElecParticipantTypeEnum.PERSON);
		}
		
		participantInfoSend
				.setParticipantRole(ElecParticipantRoleEnum.SIGNER);
		participantInfoSend.setSignAuthTypeEnum(Arrays
				.asList(ElecSignAuthTypeEnum.SIGN_PASSWORD,
						ElecSignAuthTypeEnum.SMS_VERIFICATION_CODE));
		participantInfoSend
				.setParticipantClassify(
						ElecParticipantClassifyEnum.INTERNAL_USERS);
		participantInfoSend.setAssignedSequence(1);
		participantInfoSend.setSignMode(ElecSignModeEnum.NON_EMBEDDED);
		return participantInfoSend;
	}

	@Override
	public Object execute(JSONObject json) throws Exception {
		logger.info("进入易企签回调方法...");
		JSONObject jsonobj = JSON.parseObject(json.toString());
		String event = jsonobj.getString("event");
		logger.info("回调类型:" + event);
		String signId = jsonobj.getJSONObject("rawData").getString("customTag")
				.replace(MODEL_ALIAS_NAME + "-", "");
		if (callback_type_handing.equals(event)) {
			// 启动
			try {
				logger.debug("进入启动:");
				this.startedCallback(jsonobj);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("易企签附加选项：开始事件回调出错,signId:" + signId + "Exception:"
						+ e.getMessage());
			}
		} else if (callback_type_handled.equals(event)) {
			try {
				logger.debug("进入参与人确认事件:");
				this.handledCallback(jsonobj);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
						"易企签附加选项：参与人确认事件回调出错,signId:" + signId + "Exception:"
								+ e.getMessage());
			}
		} else if (callback_type_end.equals(event)) {
			// 结束
			try {
				logger.debug("完成事件回调:");
				this.endCallback(jsonobj);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("易企签附加选项：完成事件回调出错,signId:" + signId + "Exception:"
						+ e.getMessage());
			}
		}
		return null;
	}

	private void startedCallback(JSONObject jsonobj) throws Exception {
		String url = jsonobj.getJSONObject("rawData").getString("actionUrl");
		String signId = jsonobj.getJSONObject("rawData").getString("customTag")
				.replace(MODEL_ALIAS_NAME + "-", "");
		
		SysNewsMain sysNewsMain = (SysNewsMain) getSysNewsMainService()
				.findByPrimaryKey(signId);
		SysOrgElement element = getReceiver(jsonobj);
		if (element != null) {
			updateOutSign(signId, element.getFdId(), status_code_received, url);
			sendNotify(sysNewsMain, element, url);
		}
	}

	private static final String msgContent = "您好，您有一份新闻（%s）待签署，请及时处理。点击链接查看详情";

	private void sendNotify(SysNewsMain sysNewsMain,
			SysOrgElement element, String url) throws Exception {
		// 发送待办、短信通知
		NotifyContext notifyContext = getSysNotifyMainCoreService()
				.getContext("");
		List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
		targets.add(element);
		notifyContext.setNotifyTarget(targets);
		notifyContext.setSubject(new StringBuilder()
				.append(String.format(msgContent,
						sysNewsMain
								.getDocSubject()))
				.toString());
		notifyContext.setNotifyType("todo;mobile");
		notifyContext.setKey("sendMainSign");
		notifyContext.setLink(url);
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		getSysNotifyMainCoreService().send(sysNewsMain, notifyContext,
				null);
	}

	private void updateOutSign(String signId, String personId, String status,
			String url) throws Exception {
		 HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysNewsOutSign.fdMainId=:fdMainId and sysNewsOutSign.docCreator.fdId =:docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		 hqlInfo.setParameter("docCreatorId", personId);
		List<SysNewsOutSign> sysNewsOutSignList = getSysNewsOutSignService()
				.findList(hqlInfo);
		if (sysNewsOutSignList != null && sysNewsOutSignList.size() > 0) {
			SysNewsOutSign sysNewsOutSign = sysNewsOutSignList.get(0);
			sysNewsOutSign.setDocCreateTime(new Date());
			if (StringUtil.isNotNull(url)) {
				sysNewsOutSign.setFdUrl(url);
			}
			sysNewsOutSign.setFdStatus(status);
			getSysNewsOutSignService().update(sysNewsOutSign);
		 }
	}

	private SysOrgElement getReceiver(JSONObject jsonobj) throws Exception {
		logger.debug("新闻获取当前签署人");
		JSONObject receiverParticipant = jsonobj.getJSONObject("rawData")
				.getJSONObject("receiverParticipant");
		// 当前签署人姓名
		String name = receiverParticipant.getString("name");
		String phone = null;
		JSONObject contactMetadata = receiverParticipant
				.getJSONObject("contactMetadata");
		JSONArray contacts = contactMetadata.getJSONArray("contacts");
		for (int i = 0; i < contacts.size(); i++) {
			JSONObject obj = contacts.getJSONObject(i);
			if (obj.containsKey("sms")) {
				phone = obj.getString("sms");
			}
		}

		// 根据电话号码和名字获取人员Id
		if (StringUtil.isNotNull(name) && StringUtil.isNotNull(phone)) {
			logger.debug(name + "--" + phone);

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysOrgPerson.fdMobileNo = :fdMobileNo ");
			hqlInfo.setParameter("fdMobileNo", phone);
			List<SysOrgPerson> list = getSysOrgPersonService()
					.findList(hqlInfo);
			if (list != null && list.size() > 0) {
				SysOrgPerson person = list.get(0);
				ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
						.getBean("sysOrgElementService");
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"sysOrgElement.fdName = :fdName and sysOrgElement.fdId = :fdId");
				hql.setParameter("fdName", name);
				hql.setParameter("fdId", person.getFdId());
				List<SysOrgElement> elements = sysOrgElementService
						.findList(hql);
				if (elements != null && elements.size() > 0) {
					return elements.get(0);
				}

			}
		}
		return null;
	}

	private void handledCallback(JSONObject jsonobj) throws Exception {
		String signId = jsonobj.getJSONObject("rawData").getString("customTag")
				.replace(MODEL_ALIAS_NAME + "-", "");
		SysNewsMain sysNewsMain = (SysNewsMain) getSysNewsMainService()
				.findByPrimaryKey(signId);
		SysOrgElement element = getReceiver(jsonobj);
		if (element != null) {
			updateOutSign(signId, element.getFdId(), status_code_callback,
					null);
			removeNotify(sysNewsMain, element);
		}
	}

	private void removeNotify(SysNewsMain sysNewsMain,
			SysOrgElement element) throws Exception {
		SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService()
				.findByPrimaryKey(element.getFdId());
		getSysNotifyMainCoreService().getTodoProvider().removePerson(
				sysNewsMain, "sendMainSign", person);

	}
	
	private void endCallback(JSONObject jsonobj) throws Exception {
		logger.debug("新闻完成事件");
		logger.debug(jsonobj.toString());
		logger.info("下载签署后的文件...");
		String url = jsonobj.getJSONObject("rawData")
				.getJSONObject("signData")
				.getString("url");
		String dirPath = ResourceUtil
				.getKmssConfigString("kmss.resource.path")
				.replace("/", "//")
				+ "//yqqsign";
		String wsid = jsonobj.getJSONObject("rawData")
				.getJSONObject("basicEnvelope").getString("wsid");
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String signId = jsonobj.getJSONObject("rawData").getString("customTag")
				.replace(MODEL_ALIAS_NAME + "-", "");
		SysNewsMain sysNewsMain = (SysNewsMain) getSysNewsMainService()
				.findByPrimaryKey(signId);

		List<SysAttMain> attList = getSysAttMainCoreInnerService()
				.findByModelKey(
						"com.landray.kmss.sys.news.model.SysNewsMain",
						sysNewsMain.getFdId(), "fdSignFile");
		String path = "";
		String fileType = "";
		if (attList != null && attList.size() == 1) {
			path = dirPath + "//" + UUIDGenerator.getUUID() + ".pdf";
			fileType = ".pdf";
		}

		if (attList != null && attList.size() > 1) {
			path = dirPath + "//" + UUIDGenerator.getUUID() + ".zip";
			fileType = ".zip";

		}
		
		this.downloadFile(new File(path), url);
		// 将文档添加到附件列表
		logger.info("将签署后文件挂到新闻中...");

		this.addAttFile(sysNewsMain.getFdId(),
				"com.landray.kmss.sys.news.model.SysNewsMain",
				"yqqSigned", path, fileType);

	}


	private void downloadFile(File storeFile, String downloadUrl) {
		try {
			URL url = new URL(downloadUrl);
			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			FileOutputStream fis = new FileOutputStream(storeFile);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = bis.read(buffer, 0, 1024)) != -1) {
				fis.write(buffer, 0, count);
			}
			fis.close();
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SysAttMain addAttFile(String fdModelId, String fdModelName,
			String fdKey, String filePath, String fileType) throws Exception {
		SysAttMain attMain = new SysAttMain();
		try {
			File file = new File(filePath);
			InputStream in = null;
			if (file.exists()) {
				in = new FileInputStream(file);
			}

			attMain.setInputStream(in);
			attMain.setFdModelId(fdModelId);
			attMain.setFdModelName(fdModelName);
			attMain.setFdKey(fdKey);
			double fileSize = in.available();
			attMain.setFdSize(fileSize);
			attMain.setFdContentType("application/msword");
			attMain.setFdAttType("office");
			attMain.setFdFileName(UUIDGenerator.getUUID() + fileType);
		} catch (Exception e) {
		}
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		sysAttMainService.add(attMain);
		return attMain;
	}

}
