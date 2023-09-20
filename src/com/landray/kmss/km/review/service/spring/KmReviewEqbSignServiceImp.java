package com.landray.kmss.km.review.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.BasicInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.ContentInfo;
import com.landray.kmss.elec.device.client.ElecChannelContractSignInfo.ParticipantInfo;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.ElecContractInfo;
import com.landray.kmss.elec.device.client.ElecContractInfo.Signatory;
import com.landray.kmss.elec.device.client.ElecParticipantClassifyEnum;
import com.landray.kmss.elec.device.client.ElecParticipantTypeEnum;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;
import com.landray.kmss.elec.device.model.ElecFileLock;
import com.landray.kmss.elec.device.service.IElecChannelAnsyService;
import com.landray.kmss.elec.device.service.IElecChannelContractService;
import com.landray.kmss.elec.device.vo.IElecChannelFileVO;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewOutSign;
import com.landray.kmss.km.review.service.IKmReviewEqbSignService;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewOutSignService;
import com.landray.kmss.km.review.util.KmReviewEqbFieldEnum;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KmReviewEqbSignServiceImp implements IElecChannelAnsyService, IKmReviewEqbSignService {
	
	public static final String status_code_init = "00";// 发送签署成功
	public static final String status_code_received = "01";// 发送签署后回调
	public static final String status_code_callback = "02";// 当前签署人签署完成后回调
	public static final String status_code_finish = "03";// 所有人签署完成

	public static final String callback_type_started = "envelopeStarted";
	public static final String callback_type_handing = "participantHandling";
	public static final String callback_type_handled = "participantConfirmed";
	public static final String callback_type_end = "envelopeCompleted";

	public static final String outsign_type_eqb = "eqb";

	private static final String MODEL_ALIAS_NAME = "km-Review";

	private Logger logger = LoggerFactory.getLogger(KmReviewEqbSignServiceImp.class);
	
	private IKmReviewMainService kmReviewMainService;
	
	public IKmReviewMainService getKmReviewMainService() {
		if(kmReviewMainService == null) {
			kmReviewMainService = (IKmReviewMainService) SpringBeanUtil.getBean("kmReviewMainService");
		}
		return kmReviewMainService;
	}
	
	private ISysAttMainCoreInnerService sysAttMainService;
	
	private ISysAttMainCoreInnerService getSysAttMainService() {
		if(sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	
	private ISysAttUploadService sysAttUploadService;
	
	public ISysAttUploadService getSysAttUploadService() {
		if(sysAttUploadService == null) {
			sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		}
		return sysAttUploadService;
	}
	
	private IKmReviewOutSignService kmReviewOutSignService;

	public IKmReviewOutSignService getKmReviewOutSignService() {
		if (kmReviewOutSignService == null) {
			kmReviewOutSignService = (IKmReviewOutSignService) SpringBeanUtil
					.getBean("kmReviewOutSignService");
		}
		return kmReviewOutSignService;
	}
	
	private ISysOrgPersonService sysOrgPersonService;

	private ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private static IComponentLockService componentLockService = null;

	private static IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil.getBean("componentLockService");
		}
		return componentLockService;
	}

	private IElecChannelContractService elecEqbService = null;

	public IElecChannelContractService getElecEqbService() {
		if (elecEqbService == null) {
			IExtension[] extensions = Plugin.getExtensions(
					"com.landray.kmss.elec.device.contractService",
					IElecChannelRequestMessage.class.getName(), "convertor");
			for (IExtension extension : extensions) {
				String channel = Plugin.getParamValueString(extension,"channel");
				if ("eqbSign".equals(channel)) {
					elecEqbService = Plugin.getParamValue(extension,"bean");
					break;
				}
			}
		}
		return elecEqbService;
	}
	
	/**
	 * 完成签署的回调报文:
	 * 
	 * {
	  	"modelName":"",
		"modelId":"",
	    "reqBody" : {
		    "action":"SIGN_FLOW_FINISH",
		    "createTime":"2020-09-21 19:17:29",
		    "endTime":"2020-09-21 19:17:42",
		    "flowId":54991,
		    "flowType":"Common",
		    "resultDescription":"签署成功",
		    "status":2,
		    "finishDocUrlBeans":[
		      {
		        "docFileKey":"$3da1d7b8-d669-4ce0-8d30-341ed9e9ed8b$3299301402",
		        "downloadDocUrl":"https://testesignpro.tsign.cn:6443/esignproweb/rest/filesystem/operation/download?fileKey=$4ef50af7-af89-45ca-952c-
		        9a5a088fcbcb$3547147904&signature=Jrq%2FJ4KCKCx1uwDqCn%2F0VMRso8o%3D%0A&expire=1
		        603279062323",
		        "finishFileKey":"$4ef50af7-af89-45ca-952c-9a5a088fcbcb$3547147904"
		      }
		      {
		        "docFileKey":"$3da1d7b8-d669-4ce0-8d30-341ed9e9ed8b$3299301402",
		        "downloadDocUrl":"https://testesignpro.tsign.cn:6443/esignproweb/rest/filesystem/operation/download?fileKey=$4ef50af7-af89-45ca-952c-
		        9a5a088fcbcb$3547147904&signature=Jrq%2FJ4KCKCx1uwDqCn%2F0VMRso8o%3D%0A&expire=1
		        603279062323",
		        "finishFileKey":"$4ef50af7-af89-45ca-952c-9a5a088fcbcb$3547147904"
		      }
		    ],
		    "accountInfo":{
		      "accountId":"e80c2525-9b48-4151-9b3d-1edd4a6e5b98",
		      "accountUid":"lhuser002",
		      "name":"张二",
		      "type":1
		    },
		    "waitingToSignAccount":[
		      {
		      "accountId":"e80c2525-9b48-4151-9b3d-1edd4a6e5b98",
		      "accountUid":"lhuser002",
		      "name":"张二",
		      "type":1
		      }
		    ]
		  },
		  "transformBody":{}
	  }
	 * 
	 */
	/**
	 * action 为SIGN_FLOW_FINISH 时:
		2 为签署完成
		5 为过期作废
		7 为拒签
		8 为作废(作废签署完成)
		action 为SIGN_FLOW_UPDATE
		时：
		2 为签署完成
		3 为冻结
		4 为解冻
		5为静默签署失败
	 */
	@Override
	public Object execute(JSONObject json) throws Exception {
		logger.info("进入KmReviewEqbSignServiceImp.execute方法:{}", json.toString());
		TransactionStatus transactionStatus = null;
		Throwable t = null;
		try{
			transactionStatus =TransactionUtils.beginNewTransaction();
			doExecute(json);
			TransactionUtils.commit(transactionStatus);
		}catch (Throwable throwable){
			t = throwable;
			throw  throwable;
		}finally {
			if(t != null){
				if(transactionStatus != null){
					TransactionUtils.rollback(transactionStatus);
				}
			}
		}

		return null;
	}

	private Object doExecute(JSONObject json) throws Exception{
		logger.info("e签宝签署回调通知:{}", json.toString());

		JSONObject responseBodyJson = json.getJSONObject("transformBody");
		if(responseBodyJson == null) {
			logger.info("不能收到回调明细数据,reqBody节点为空");
			return null;
		}
		String signId = json.getString("modelId");
		if(StringUtil.isNull(signId)) {
			logger.error("签署完成未能获取到签署ID");
			return null;
		}
		logger.info("签署modelID:{}" , signId);
		String action = responseBodyJson.getString("action");
		logger.info("当前回调动作为:{}",action);

		KmReviewMain kmReviewMain = (KmReviewMain) getKmReviewMainService().findByPrimaryKey(signId, null, true);
		if(kmReviewMain == null) {
			logger.error("未能找到对应的签署主文档记录");
			return null;
		}

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdMainId=:fdMainId");
		hqlInfo.setParameter("fdMainId", signId);
		KmReviewOutSign kmReviewOutSign = (KmReviewOutSign) getKmReviewOutSignService().findFirstOne(hqlInfo);
		if(null != kmReviewOutSign) {
			if(status_code_finish.equals(kmReviewOutSign.getFdStatus())) {//已处理
				logger.info("回调已处理，不再写入文件...");
				return null;
			}
		}
		if("SIGN_FLOW_NOTIFY".equalsIgnoreCase(action)) {
			//更新待签署人列表
			JSONArray waitingToSignAccount = responseBodyJson.getJSONArray("waitingToSignAccount");
			if(waitingToSignAccount != null && waitingToSignAccount.size() > 0) {
				String fdExmsg = kmReviewOutSign.getFdExtmsg();
				logger.info("扩展信息fdExmsg{}", fdExmsg);
				if(StringUtil.isNotNull(fdExmsg)) {
					JSONObject fdExmsgJSON = JSONObject.parseObject(fdExmsg);
					List<String> waitingToSignAccounts = new ArrayList<>();
					for(int i = 0; i < waitingToSignAccount.size(); i++) {
						JSONObject jsonObj = waitingToSignAccount.getJSONObject(i);
						String accountUid = jsonObj.getString("accountUid");
						HQLInfo hql = new HQLInfo();
						hql.setWhereBlock("sysOrgPerson.fdLoginName =:fdLoginName and sysOrgPerson.fdIsAvailable = :fdIsAvailable");
						hql.setParameter("fdLoginName", accountUid);
						hql.setParameter("fdIsAvailable", Boolean.TRUE);
						SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService().findFirstOne(hql);
						if (null != person) {
							waitingToSignAccounts.add(person.getFdId());
						} else {
							waitingToSignAccounts.add(accountUid);
						}
						//String authOrgId = jsonObj.getString("authOrgId");
					}
					fdExmsgJSON.put("waitingToSignAccount", waitingToSignAccounts);
					String updateHql = "update KmReviewOutSign kmReviewOutSign set kmReviewOutSign.fdExtmsg=:fdExtmsg " +
							"where kmReviewOutSign.fdId=:fdId";
					getKmReviewOutSignService().getBaseDao()
							.getHibernateSession().createQuery(updateHql)
							.setParameter("fdExtmsg", fdExmsgJSON.toJSONString()).setParameter("fdId",
							kmReviewOutSign.getFdId()).executeUpdate();
				}
			}
		}else if("SIGN_FLOW_UPDATE".equalsIgnoreCase(action)) {
			//E签宝签署流程更新状态
			logger.debug("签署人信息{};签署状态：{};desc:{}",
					responseBodyJson.getString("accountInfo"),
					responseBodyJson.getInteger("status"),
					responseBodyJson.getString("resultDescription"));
			//根据配置决定中间文件是否需要获取
			String signFileUpdateType = "0";
			String fdExmsg = kmReviewOutSign.getFdExtmsg();
			logger.debug("扩展信息fdExmsg{}", fdExmsg);
			if(StringUtil.isNotNull(fdExmsg)) {
				JSONObject fdExmsgJSON = JSONObject.parseObject(fdExmsg);
				signFileUpdateType = StringUtil.isNull(fdExmsgJSON.getString("signFileUpdateType")) ? signFileUpdateType : fdExmsgJSON.getString("signFileUpdateType");
			}
			if("0".equals(signFileUpdateType)) {
				logger.debug("更新中间文件...");
				updateSignFile(signId, kmReviewMain);
			}
			String updateHql = "update KmReviewOutSign kmReviewOutSign set kmReviewOutSign.fdStatus=:fdStatus " +
					"where kmReviewOutSign.fdId=:fdId";
			getKmReviewOutSignService().getBaseDao()
					.getHibernateSession().createQuery(updateHql)
					.setParameter("fdStatus", status_code_callback).setParameter("fdId",
					kmReviewOutSign.getFdId()).executeUpdate();
		} else if("SIGN_FLOW_FINISH".equalsIgnoreCase(action)) {
			//E签宝签署流程结束状态
			logger.debug("E签宝签署流程结束，开始处理...");
			updateSignFile(signId, kmReviewMain);
			kmReviewOutSign.setFdStatus(status_code_finish);
			String fdExmsg = kmReviewOutSign.getFdExtmsg();
			logger.info("扩展信息fdExmsg{}", fdExmsg);
			if(StringUtil.isNotNull(fdExmsg)) {
				JSONObject fdExmsgJSON = JSONObject.parseObject(fdExmsg);
				fdExmsgJSON.put("waitingToSignAccount", "");
				kmReviewOutSign.setFdExtmsg(fdExmsgJSON.toJSONString());
			}
		}

		logger.info("更新签署状态完成...");
		return null;
	}

	/**
	 * 更新中间文件
	 * @param signId
	 * @param kmReviewMain
	 * @throws Exception
	 */
	private void updateSignFile(String signId, KmReviewMain kmReviewMain) throws Exception {
		ElecFileLock lock = new ElecFileLock(kmReviewMain.getFdId());
		try {
			getComponentLockService().tryLock(lock, "" + Thread.currentThread().getId(), DateUtil.MINUTE);
			logger.debug("拉取签署文件..");
			List<IElecChannelFileVO> fileVOs = getEqbSignFlowAtt(signId);
			logger.debug("拉取结束...");
			if (CollectionUtils.isNotEmpty(fileVOs)) {
				logger.debug("删除旧文件...");
				getSysAttMainService().delete(kmReviewMain, KmReviewEqbFieldEnum.FD_EQB_SIGN_AFTER_FILES.getFieldId());
				logger.debug("删除完毕...");
				for (IElecChannelFileVO fileVO : fileVOs) {
					logger.debug("转换前下载链接：｛｝" + fileVO.getDownLoadUrl());
					String downloadUrl = StringEscapeUtils.unescapeHtml4(fileVO.getDownLoadUrl());
					logger.debug("转换后下载链接：｛｝" + downloadUrl);
					//信任所有证书和HostnameVerifier
					SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
							new TrustStrategy() {
								// 信任所有
								@Override
								public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
									return true;
								}
							}).build();
					SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
						@Override
						public boolean verify(String hostName, SSLSession sslSession) {
							return true; // 证书校验通过
						}
					});
					CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
					HttpResponse response = httpClient.execute(new HttpGet(downloadUrl));
					HttpEntity entity = response.getEntity();
					InputStream is = null;
					byte[] bytes = null;
					try {
						is = entity.getContent();
						bytes = IOUtils.toByteArray(is);
					} catch (Exception e) {
						logger.error("读取流错误", e);
						throw e;
					} finally {
						IOUtils.closeQuietly(is);
					}
					logger.debug("开始写入文件...");
					getSysAttMainService().addAttachment(signId, KmReviewMain.class.getName(), KmReviewEqbFieldEnum.FD_EQB_SIGN_AFTER_FILES.getFieldId(), bytes, fileVO.getFileName(), "attachment");
					logger.info("成功写入签署后文件...");
				}
			}
		} catch (Exception e) {
			logger.error("更新文件过程出错", e);
			throw e;
		} finally {
			getComponentLockService().unLock(lock);
		}
	}


	/**
	 * 
	 * @param bizNO
	 * @return
	 * @throws Exception 
	 */
	private List<IElecChannelFileVO> getEqbSignFlowAtt(String bizNO) throws Exception {
		logger.debug("调用getEqbSignFlowAtt方法");
		ElecChannelResponseMessage<List<IElecChannelFileVO>> responseMessage = getElecEqbService().downloadContractFile(bizNO, null);
		if(!responseMessage.hasError()) {
			logger.debug("responseMessage成功");
			List<IElecChannelFileVO> fileVOs = responseMessage.getData();
			return fileVOs;
		}
		return null;
	}

	@Override
	public ElecChannelResponseMessage<?> sendEqb(KmReviewMain kmReviewMain, List<JSONObject> signers, List<SysAttMain> signFiles)
			throws Exception {
		ElecAdditionalInfo additionalInfo = new ElecAdditionalInfo();
		additionalInfo.setKeyword1(KmReviewMain.class.getName());
		additionalInfo.setKeyword2(kmReviewMain.getFdId());
		
		ElecChannelContractSignInfo elecChannelContractSignInfo = new ElecChannelContractSignInfo();
		
		logger.info("step1: 构造基础信息...");
		BasicInfo basicInfo = elecChannelContractSignInfo.new BasicInfo()
				.setContractName(kmReviewMain.getDocSubject())
				.setContractNo(kmReviewMain.getFdId());
		elecChannelContractSignInfo.setBasicInfo(basicInfo);
//		String contentPath = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
//		elecChannelContractSignInfo.setReturnUrl(contentPath+"/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+kmReviewMain.getFdId()+"&_referer="+contentPath+"/km/review");
		
		logger.info("step2:构造参与方信息...");
		/**
		 * signType
		 * userId
		 * contractMobile
		 * signStand
		 * signDesc
		 * enterPriseId
		 * signOrder
		 */
		for(JSONObject singer:signers) {
			String userId = singer.getString("userId");
			SysOrgPerson user = UserUtil.getUser(userId);
			
			ParticipantInfo pInfo = elecChannelContractSignInfo.new ParticipantInfo()
					.setParticipantType(ElecParticipantTypeEnum.PERSON)
					.setPhone(singer.getString("contractMobile"))
					.setAssignedSequence(singer.getIntValue("signOrder"));
			if(user.getFdIsExternal()) {
				pInfo.setParticipantClassify(ElecParticipantClassifyEnum.EXTERNAL_USERS)
				.setUserId(userId);
			}else {
				pInfo.setParticipantClassify(ElecParticipantClassifyEnum.INTERNAL_USERS)
				.setUserId(user.getFdLoginName());
			}
			if("10".equals(singer.getString("signType"))) {
				pInfo.setParticipantType(ElecParticipantTypeEnum.ENTERPRISE)
				.setEnterpriseId(singer.getString("enterPriseId"));
			}		
			elecChannelContractSignInfo.addParticipantInfo(pInfo);
		}
		
		logger.info("step3:构造文件信息...");
		if (CollectionUtils.isNotEmpty(signFiles)) {
			for(SysAttMain sysAttMain :signFiles) {
				SysAttFile sysAttFile = getSysAttUploadService().getFileById(sysAttMain.getFdFileId());
				String attAbsolutePath = getSysAttUploadService().getAbsouluteFilePath(sysAttFile);
				String pdfPath = attAbsolutePath;
				ContentInfo contentInfo = elecChannelContractSignInfo.new ContentInfo()
						.setFilePath(pdfPath)
						.setFileName(sysAttMain.getFdFileName())
						.setAttLocation(sysAttFile.getFdAttLocation());
				elecChannelContractSignInfo.addContentInfo(contentInfo);
			}
		}

		ElecChannelResponseMessage<ElecContractInfo> responseMessage = (ElecChannelResponseMessage<ElecContractInfo>) getElecEqbService().uploadContract(elecChannelContractSignInfo, additionalInfo);
		if(!responseMessage.hasError()) {
			logger.info("插入签署记录...");
			JSONObject json = new JSONObject();
			String signFileUpdateType = kmReviewMain.getCustomPropMap().get("signFileUpdateType") != null
					? (String) kmReviewMain.getCustomPropMap().get("signFileUpdateType")
					: "0";
			json.put("signFileUpdateType",  signFileUpdateType);//"0":中间签署状态更新，"1":最终签署完成更新
			ElecContractInfo elecContractInfo = responseMessage.getData();
			List<Signatory> Signatories = elecContractInfo.getSignatories();
			JSONObject signInfo = new JSONObject();
			if (CollectionUtils.isNotEmpty(Signatories)) {
				for (Signatory signatory : Signatories) {
					String id = signatory.getUniqueKey();
					logger.debug("EQB personId:" + id);
					String signUrl = signatory.getSignUrl();
					logger.debug("EQB signUrl:" + signUrl);
					HQLInfo hql = new HQLInfo();
					hql.setWhereBlock("sysOrgPerson.fdLoginName =:fdLoginName and sysOrgPerson.fdIsAvailable = :fdIsAvailable");
					hql.setParameter("fdLoginName", id);
					hql.setParameter("fdIsAvailable", Boolean.TRUE);
					SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService().findFirstOne(hql);
					if (null != person) {
						signInfo.put(person.getFdId(), signUrl);
					} else {
						signInfo.put(id, signUrl);
					}
				}
				json.put("eqbSignInfo", signInfo);//将签署人及对应链接放入。
			}
			KmReviewOutSign outsign = new KmReviewOutSign();
			outsign.setFdMainId(kmReviewMain.getFdId());
			outsign.setFdStatus(status_code_init);
			outsign.setFdType(outsign_type_eqb);
			outsign.setDocCreateTime(new Date());
			outsign.setFdExtmsg(json.toString());//配置信息
			outsign.setDocCreator(null);//签署人
			getKmReviewOutSignService().add(outsign);
		}
		return responseMessage;
	
	}
}
