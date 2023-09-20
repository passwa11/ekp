package com.landray.kmss.km.review.service.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.BasicInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.ContentInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.ParticipantInfo;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.ElecChannelTxCodeEnum;
import com.landray.kmss.elec.device.client.ElecContractInfo;
import com.landray.kmss.elec.device.client.ElecParticipantClassifyEnum;
import com.landray.kmss.elec.device.client.ElecParticipantRoleEnum;
import com.landray.kmss.elec.device.client.ElecParticipantTargetEnum;
import com.landray.kmss.elec.device.client.ElecParticipantTypeEnum;
import com.landray.kmss.elec.device.client.ElecSignAuthTypeEnum;
import com.landray.kmss.elec.device.client.ElecSignModeEnum;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;
import com.landray.kmss.elec.device.service.IElecChannelAnsyService;
import com.landray.kmss.elec.device.service.IElecChannelContractService;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewNumberLock;
import com.landray.kmss.km.review.model.KmReviewOutSign;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewOutSignService;
import com.landray.kmss.km.review.service.IKmReviewYqqSignService;
import com.landray.kmss.km.review.util.ZipUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
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
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ws.security.util.UUIDGenerator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class KmReviewYqqSignServiceImp implements IKmReviewYqqSignService,IElecChannelAnsyService{

	private static final Log logger = LogFactory
			.getLog(KmReviewYqqSignServiceImp.class);

	public static final String status_code_init = "00";// 发送签署成功
	public static final String status_code_received = "01";// 发送签署后回调
	public static final String status_code_callback = "02";// 当前签署人签署完成后回调
	public static final String status_code_finish = "03";// 所有人签署完成

	public static final String callback_type_started = "envelopeStarted";
	public static final String callback_type_handing = "participantHandling";
	public static final String callback_type_handled = "participantConfirmed";
	public static final String callback_type_end = "envelopeCompleted";

	public static final String outsign_type_yqq = "yqq";

	private static final String MODEL_ALIAS_NAME = "km-Review";

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

	private IKmReviewMainService kmReviewMainService;

	public IKmReviewMainService getKmReviewMainService() {
		if (kmReviewMainService == null) {
			kmReviewMainService = (IKmReviewMainService) SpringBeanUtil
					.getBean("kmReviewMainService");
		}
		return kmReviewMainService;
	}

	private IKmReviewOutSignService kmReviewOutSignService;

	public IKmReviewOutSignService getKmReviewOutSignService() {
		if (kmReviewOutSignService == null) {
			kmReviewOutSignService = (IKmReviewOutSignService) SpringBeanUtil
					.getBean("kmReviewOutSignService");
		}
		return kmReviewOutSignService;
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
	public Boolean sendYqq(KmReviewMain kmReviewMain, String phone,
						   String fdEnterprise,
						   List<SysOrgPerson> elements) throws Exception {
		logger.info("进入易企签附加选项...");
		logger.info("mainModel ID：" + kmReviewMain.getFdId());
		KmReviewNumberLock kmReviewNumberLock = new KmReviewNumberLock();
		Boolean successStauts = true;
		try {
			getComponentLockService().tryLock(kmReviewNumberLock,
					kmReviewMain.getFdId());

			SysOrgPerson user = UserUtil.getUser();

			ElecAdditionalInfo additionalInfo = new ElecAdditionalInfo();
			additionalInfo.setTxCode(ElecChannelTxCodeEnum.UPLOAD_CONTRACT);
			ElecChannelContractSignInfo contractSignInfo = new ElecChannelContractSignInfo();
			BasicInfo basicInfo = contractSignInfo.new BasicInfo();
			basicInfo.setContractNo(kmReviewMain.getFdId());
			basicInfo.setContractName(kmReviewMain.getDocSubject());
			JSONObject metaMap = new JSONObject();
			metaMap.put("signId", kmReviewMain.getFdId());
			basicInfo.setMetadata(metaMap);
			additionalInfo.setKeyword1(kmReviewMain.getFdId());
			additionalInfo.setKeyword2(kmReviewMain.getDocSubject());
			additionalInfo.setKeyword3(MODEL_ALIAS_NAME);
			contractSignInfo.setCustomTag(
					MODEL_ALIAS_NAME + "-" + kmReviewMain.getFdId());

			// 附件
			List<ContentInfo> contentInfos = new ArrayList<>();
			List<SysAttMain> yqqSignedAtts = getSysAttMainCoreInnerService()
					.findByModelKey(
							"com.landray.kmss.km.review.model.KmReviewMain",
							kmReviewMain.getFdId(), "yqqSigned");
			List<SysAttMain> attList = getSysAttMainCoreInnerService()
					.findByModelKey(
							"com.landray.kmss.km.review.model.KmReviewMain",
							kmReviewMain.getFdId(), "fdSignFile");

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

			List<ParticipantInfo> participantInfos = new ArrayList<>();
			// 发送方:我方签约主体
			ParticipantInfo participantInfo = contractSignInfo.new ParticipantInfo();
			participantInfo.setParticipantTarget(ElecParticipantTargetEnum.SENDER);
			participantInfo
					.setUserName(user.getFdName());
			if (StringUtil.isNotNull(phone)) {
				participantInfo
						.setPhone(phone.trim());
			} else {
				participantInfo
						.setPhone(user.getFdMobileNo());
			}
			participantInfo
					.setParticipantClassify(ElecParticipantClassifyEnum.INTERNAL_USERS);
			participantInfos.add(participantInfo);

			if (elements != null && elements.size() > 0) {
				// 签署方
				for (SysOrgPerson person : elements) {
					ParticipantInfo participantInfoSend = getParticipantInfo(
							contractSignInfo, phone, fdEnterprise, person);

					participantInfos.add(participantInfoSend);
				}
			} else {
				ParticipantInfo participantInfoSend = getParticipantInfo(
						contractSignInfo, phone, fdEnterprise, user);
				participantInfos.add(participantInfoSend);
			}

			contractSignInfo.setBasicInfo(basicInfo);
			contractSignInfo.setContentInfos(contentInfos);
			contractSignInfo.setParticipantInfos(participantInfos);
			if (logger.isDebugEnabled()) {
				logger.debug("易企签附加选项:开始调用易企签服务");
			}
			ElecChannelResponseMessage<ElecContractInfo> message = (ElecChannelResponseMessage<ElecContractInfo>) getElecYqqService()
					.uploadContract(contractSignInfo,
							additionalInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("易企签附加选项:调用易企签服务结束");
			}
			getComponentLockService().unLock(kmReviewNumberLock);
			// 新增签署记录信息
			String code = message.getCode();
			if (YQQ_CODE_NOT_FOUND_SENDER.equals(code)) {
				// 发送方不存在或者不在本企业
				successStauts = false;
			}
		} catch (Exception e) {
			getComponentLockService().unLock(kmReviewNumberLock);
			if (logger.isDebugEnabled()) {
				logger.debug(
						"KmReviewYqqSignServiceImp - Exception:"
								+ e.getMessage());
			}
			throw new Exception(e);
		} finally {
			getComponentLockService().unLock(kmReviewNumberLock);
		}
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
				//临时文件夹路径
				String tempDirPath = formatReadFilePath(null,
						"/yqqtemp"+sysAttFile.getFdFilePath());
				InputStream is = sysAttUploadService.getFileData(sysAttMain.getFdFileId());
				if (logger.isDebugEnabled()) {
					logger.debug(
							"kmreview-getMultiContentInfos-path:"
									+ tempDirPath);
				}
				File tempDir = new File(tempDirPath);
				if (!tempDir.exists()) {
					tempDir.mkdirs();
				}
				//临时文件路径
				String tempFilePath = tempDir+"//tempFile";
				File tmpFile = new File(tempFilePath);
				copyToFile(is, tmpFile);
				FileInputStream inputFile = new FileInputStream(tmpFile);
				byte[] buffer = new byte[(int) tmpFile.length()];
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
			//临时文件夹路径
			String tempDirPath = formatReadFilePath(null,
					"/yqqtemp"+sysAttFile.getFdFilePath());
			InputStream is = sysAttUploadService.getFileData(sysAttMain.getFdFileId());
			if (logger.isDebugEnabled()) {
				logger.debug("kmreview-getContentInfos-path:" + tempDirPath);
			}
			File tempDir = new File(tempDirPath);
			if (!tempDir.exists()) {
				tempDir.mkdirs();
			}
			//临时文件路径
			String tempFilePath = tempDir+"//tempFile";
			File tmpFile = new File(tempFilePath);
			copyToFile(is, tmpFile);
			FileInputStream inputFile = new FileInputStream(tmpFile);
			byte[] buffer = new byte[(int) tmpFile.length()];
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

	public String formatReadFilePath(String pathPrefix,
									 String fileRelativePath) {
		if (StringUtil.isNull(pathPrefix)) {
			return ResourceUtil.getKmssConfigString("kmss.resource.path")
					+ fileRelativePath;
		}
		return pathPrefix + fileRelativePath;
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
			//临时文件夹路径
			String tempDirPath = formatReadFilePath(null,
					"/yqqtemp"+sysAttFile.getFdFilePath());
			InputStream is = sysAttUploadService.getFileData(sysAttMain.getFdFileId());
			if (logger.isDebugEnabled()) {
				logger.debug(
						"kmreview-getRarContentInfos-path:"
								+ tempDirPath);
			}
			File tempDir = new File(tempDirPath);
			if (!tempDir.exists()) {
				tempDir.mkdirs();
			}
			//临时文件路径
			String tempFilePath = tempDir+"//tempFile";
			File tmpFile = new File(tempFilePath);
			copyToFile(is, tmpFile);
			// 解压
			String destPath = tmpFile.getParentFile().getAbsolutePath()
					+ tmpFile.getName().substring(0, tmpFile.getName().length() - 4);
			ZipUtil.unZip(tempFilePath, destPath);

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

	private ParticipantInfo getParticipantInfo(
			ElecChannelContractSignInfo contractSignInfo, String phone,
			String fdEnterprise,
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
				if (logger.isDebugEnabled()) {
					logger.debug("进入启动:");
				}
				this.startedCallback(jsonobj);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("易企签附加选项：开始事件回调出错,signId:" + signId + "Exception:"
						+ e.getMessage());
			}
		} else if (callback_type_handled.equals(event)) {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("进入参与人确认事件:");
				}
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
				if (logger.isDebugEnabled()) {
					logger.debug("进入完成事件:");
				}
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

		KmReviewMain kmReviewMain = (KmReviewMain) getKmReviewMainService()
				.findByPrimaryKey(signId);
		SysOrgElement element = getReceiver(jsonobj);
		if (element != null) {
			updateOutSign(signId, element.getFdId(), status_code_received, url);
			sendNotify(kmReviewMain, element, url);
		}
	}

	private static final String msgContent = "您好，您有一份审批流程（%s）待签署，请及时处理。点击链接查看详情";

	private void sendNotify(KmReviewMain kmReviewMain,
							SysOrgElement element, String url) throws Exception {
		// 发送待办、短信通知
		NotifyContext notifyContext = getSysNotifyMainCoreService()
				.getContext("");
		List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
		targets.add(element);
		notifyContext.setNotifyTarget(targets);
		notifyContext.setSubject(new StringBuilder()
				.append(String.format(msgContent,
						kmReviewMain
								.getDocSubject()))
				.toString());
		notifyContext.setNotifyType("todo;mobile");
		notifyContext.setKey("sendMainSign");
		notifyContext.setLink(url);
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		getSysNotifyMainCoreService().send(kmReviewMain, notifyContext,
				null);
	}

	private void updateOutSign(String signId, String personId, String status,
							   String url) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmReviewOutSign.fdMainId=:fdMainId and kmReviewOutSign.docCreator.fdId =:docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", personId);
		KmReviewOutSign kmReviewOutSign = (KmReviewOutSign) getKmReviewOutSignService()
				.findFirstOne(hqlInfo);
		if (null != kmReviewOutSign) {
			kmReviewOutSign.setDocCreateTime(new Date());
			if (StringUtil.isNotNull(url)) {
				kmReviewOutSign.setFdUrl(url);
			}
			kmReviewOutSign.setFdStatus(status);
			getKmReviewOutSignService().update(kmReviewOutSign);
		}
	}

	private SysOrgElement getReceiver(JSONObject jsonobj) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("审批流程获取当前签署人");
		}
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
			if (logger.isDebugEnabled()) {
				logger.debug(name + "--" + phone);
			}

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysOrgPerson.fdMobileNo = :fdMobileNo ");
			hqlInfo.setParameter("fdMobileNo", phone);
			SysOrgPerson person =(SysOrgPerson) getSysOrgPersonService()
					.findFirstOne(hqlInfo);
			if (null != person) {
				ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
						.getBean("sysOrgElementService");
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"sysOrgElement.fdName = :fdName and sysOrgElement.fdId = :fdId");
				hql.setParameter("fdName", name);
				hql.setParameter("fdId", person.getFdId());
				SysOrgElement element = (SysOrgElement) sysOrgElementService
						.findFirstOne(hql);
				if (null != element) {
					return element;
				}

			}
		}
		return null;
	}

	private void handledCallback(JSONObject jsonobj) throws Exception {
		String signId = jsonobj.getJSONObject("rawData").getString("customTag")
				.replace(MODEL_ALIAS_NAME + "-", "");
		KmReviewMain kmReviewMain = (KmReviewMain) getKmReviewMainService()
				.findByPrimaryKey(signId);
		SysOrgElement element = getReceiver(jsonobj);
		if (element != null) {
			updateOutSign(signId, element.getFdId(), status_code_callback,
					null);
			removeNotify(kmReviewMain, element);
		}
	}

	private void removeNotify(KmReviewMain kmReviewMain,
							  SysOrgElement element) throws Exception {
		SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService()
				.findByPrimaryKey(element.getFdId());
		getSysNotifyMainCoreService().getTodoProvider().removePerson(
				kmReviewMain, "sendMainSign", person);

	}

	private void endCallback(JSONObject jsonobj) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("审批流程完成事件");
			logger.debug(jsonobj.toString());
		}
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
		KmReviewMain kmReviewMain = (KmReviewMain) getKmReviewMainService()
				.findByPrimaryKey(signId);

		List<SysAttMain> attList = getSysAttMainCoreInnerService()
				.findByModelKey(
						"com.landray.kmss.km.review.model.KmReviewMain",
						kmReviewMain.getFdId(), "fdSignFile");
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

		this.addAttFile(kmReviewMain.getFdId(),
				"com.landray.kmss.km.review.model.KmReviewMain",
				"yqqSigned", path, fileType);

	}


	private void downloadFile(File storeFile, String downloadUrl) {
		BufferedInputStream bis = null;
		FileOutputStream fis = null;
		try {
			logger.info("易企签签署完成，"
					+ ""
					+ "文件下载原链接：" + downloadUrl);
			HttpURLConnection conn = null;
			//循环获取重定向链接
			do {
				URL url = new URL(downloadUrl);
				conn = (HttpURLConnection) url.openConnection();
				//设置超时间为3秒
				conn.setConnectTimeout(3*1000);
				//防止屏蔽程序抓取而返回403错误
				conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				conn.setInstanceFollowRedirects(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    break;
                }
				downloadUrl = conn.getHeaderField("Location");
				logger.info("易企签签署完成，重定向下载实际链接：" + downloadUrl);
			} while(true);
			logger.info("易企签签署完成，最终下载实际链接：" + downloadUrl);
			bis = new BufferedInputStream(
					conn.getInputStream());
			fis = new FileOutputStream(storeFile);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = bis.read(buffer, 0, 1024)) != -1) {
				fis.write(buffer, 0, count);
			}
			fis.close();
			bis.close();
		} catch (Exception e) {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
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

	public void copyToFile(InputStream inputStream, File file) throws IOException {
		try(OutputStream outputStream = new FileOutputStream(file)) {
			IOUtils.copy(inputStream, outputStream);
		}
	}


}
