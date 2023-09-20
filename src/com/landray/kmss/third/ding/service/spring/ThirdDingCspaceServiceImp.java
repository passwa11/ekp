package com.landray.kmss.third.ding.service.spring;

import com.dingtalk.api.request.OapiFileUploadChunkRequest;
import com.dingtalk.api.request.OapiFileUploadSingleRequest;
import com.dingtalk.api.request.OapiFileUploadTransactionRequest;
import com.dingtalk.api.response.OapiFileUploadChunkResponse;
import com.dingtalk.api.response.OapiFileUploadSingleResponse;
import com.dingtalk.api.response.OapiFileUploadTransactionResponse;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingCspace;
import com.landray.kmss.third.ding.service.IThirdDingCspaceService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.util.StringUtil;
import com.taobao.api.FileItem;
import com.taobao.api.internal.util.WebUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

public class ThirdDingCspaceServiceImp extends ExtendDataServiceImp implements IThirdDingCspaceService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCspaceServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private ThreadPoolTaskExecutor taskExecutor;

	private final String FILE_SUCCESS = "2";  //成功
	private final String FILE_ING = "1";  //上传或下载中
	private final String FILE_FAILED = "0";  //失败

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingCspace) {
            ThirdDingCspace thirdDingCspace = (ThirdDingCspace) model;
            thirdDingCspace.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingCspace thirdDingCspace = new ThirdDingCspace();
        thirdDingCspace.setDocCreateTime(new Date());
        thirdDingCspace.setDocAlterTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingCspace, requestContext);
        return thirdDingCspace;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingCspace thirdDingCspace = (ThirdDingCspace) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void uploadToDingSpace(String fileName, InputStream in,
			String fdId) {
		logger.debug("-----上传文件----" + fileName);
		taskExecutor.execute(new uploadToDingSpaceRunner(fileName, in, fdId));
		logger.debug("-----线程同步----" + fileName);
	}

	@Override
	public void downloadFromDing(JSONObject jsonObject) {
		logger.debug("-----钉盘下载文件----" + jsonObject);
		taskExecutor.execute(new DownloadFromDingSpaceRunner(jsonObject));
		logger.debug("------钉盘下载文件完成----"+jsonObject);
	}

	class DownloadFromDingSpaceRunner implements Runnable {

		private JSONObject requestObj;

		public DownloadFromDingSpaceRunner(JSONObject requestObj) {
			this.requestObj = requestObj;
		}

		public DownloadFromDingSpaceRunner() {
		}

		@Override
		public void run() {
			String fileName = requestObj.getString("fileName");
			ByteArrayOutputStream byteArrayOutputStream= null;
			String fdId = requestObj.getString("fdId");
			String fdAttId = requestObj.getString("fdAttId");
			String fdDingFileId = requestObj.getString("fileId");

			String fdFileSize = requestObj.getString("fileSize");
			String fdKey = requestObj.getString("fdKey");
			String fdAttType = requestObj.getString("fdAttType");
			String fdModelId = requestObj.getString("fdModelId");
			String fdModelName = requestObj.getString("fdModelName");
			String contentType = requestObj.getString("contentType");
			String userId = requestObj.getString("userId");

			try {

				IThirdDingCspaceService thirdDingCspaceService = (IThirdDingCspaceService) SpringBeanUtil
						.getBean("thirdDingCspaceService");

				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdAttId=:fdAttId and fdDingFileId=:fdDingFileId");
				hqlInfo.setParameter("fdAttId",fdAttId);
				hqlInfo.setParameter("fdDingFileId",fdDingFileId);
				ThirdDingCspace thirdDingCspace = (ThirdDingCspace) thirdDingCspaceService.findFirstOne(hqlInfo);
				if(thirdDingCspace != null){
					try {
						byteArrayOutputStream = DingUtils.getDingApiService().download(requestObj);
						if(byteArrayOutputStream != null){
							ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
							String fileId = sysAttUploadService.addStreamFile(byteArrayOutputStream.toByteArray(),fileName);

							logger.warn("文件fileId:"+fileId);

							Date day = new Date();
							SysAttMain sysAttMain = new SysAttMain();
							sysAttMain.setFdId(fdAttId);
							sysAttMain.setFdSize(Double.valueOf(fdFileSize));
							sysAttMain.setFdKey(fdKey);
							sysAttMain.setFdFileName(fileName);
							sysAttMain.setFdAttType(fdAttType);
							sysAttMain.setFdModelId(fdModelId);
							sysAttMain.setFdModelName(fdModelName);
							sysAttMain.setFdCreatorId(userId);
							sysAttMain.setFdUploaderId(userId);
							sysAttMain.setDocCreateTime(day);
							sysAttMain.setFdUploadTime(day);
							sysAttMain.setFdContentType(contentType);
							sysAttMain.setFdVersion(1);

							sysAttMain.setFdFileId(fileId);

							ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
							sysAttMainCoreInnerService.add(sysAttMain);
							thirdDingCspace.setFdFileId(fileId);
							thirdDingCspace.setFdStatus(FILE_SUCCESS);
						}else{
							thirdDingCspace.setFdStatus(FILE_FAILED);
							thirdDingCspace.setFdErrorMsg("返回的字节流为空！！！");
						}
					} catch (Exception e1) {
						logger.error(e1.getMessage(),e1);
						thirdDingCspace.setFdStatus(FILE_FAILED);
						thirdDingCspace.setFdErrorMsg("【下载错误】"+e1.getMessage());
					}
					thirdDingCspaceService.update(thirdDingCspace);
				}else{
					logger.warn("无法匹配钉盘文件记录："+requestObj);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}

		}
	}

	class uploadToDingSpaceRunner implements Runnable {
		private final String fileName;
		private final String fdId;
		private final InputStream in;

		public uploadToDingSpaceRunner(String fileName, InputStream in,
				String fdId) {
			this.fileName = fileName;
			this.in = in;
			this.fdId = fdId;
		}

		@Override
		public void run() {
			try {

				IThirdDingCspaceService thirdDingCspaceService = (IThirdDingCspaceService) SpringBeanUtil
						.getBean("thirdDingCspaceService");

				ThirdDingCspace thirdDingCspace = (ThirdDingCspace) thirdDingCspaceService
						.findByPrimaryKey(fdId, ThirdDingCspace.class, true);
				logger.debug(
						"===============多线程上传中================" + fileName);
				String mediaId = null;
				try {
					mediaId = uploadToDingSpace(fileName, in);
					logger.debug("----------mediaId:" + mediaId);
					if (StringUtil.isNotNull(mediaId)
							&& !"failed".equals(mediaId)) {
						thirdDingCspace.setFdMediaId(mediaId);
						thirdDingCspace.setFdStatus(FILE_SUCCESS);
						thirdDingCspace.setDocAlterTime(new Date());
					} else {
						thirdDingCspace.setFdStatus(FILE_FAILED);
						thirdDingCspace.setDocAlterTime(new Date());
						thirdDingCspace.setFdErrorMsg("mediaId返回为空！！！");
					}
				} catch (Exception e1) {
					logger.error(e1.getMessage(),e1);
					thirdDingCspace.setFdStatus(FILE_FAILED);
					thirdDingCspace.setDocAlterTime(new Date());
					thirdDingCspace.setFdErrorMsg("【上传异常】"+e1.getMessage());
				}
				thirdDingCspaceService.update(thirdDingCspace);

			} catch (Exception e) {

				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
	}

	public String uploadToDingSpace(String fileName, InputStream in)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		Long agentId = Long.valueOf(DingConfig.newInstance().getDingAgentid());
		try {
			// 计算文件有多少个分片
			long partSize = 8 * 1024 * 1024L; // 8MB

			/**
			 * 从网络中读取InputStream后，可能因网络质量一次读取后InputStream长度为0，所以加入循环
			 */
			long fileSize = 0;
			while (fileSize == 0) {
				fileSize = in.available();
			}
			// 判断如果要上传的文件大小小于一个分片大小则直接简单上传
			if (fileSize <= partSize) {
				OapiFileUploadSingleRequest request = new OapiFileUploadSingleRequest();
				request.setFileSize(fileSize);
				request.setAgentId(String.valueOf(agentId));
				ThirdDingTalkClient client = new ThirdDingTalkClient(
						DingConstant.DING_PREFIX + "/file/upload/single?"
								+ WebUtils.buildQuery(request.getTextParams(),
										"utf-8"));
				// 必须重新new一个请求
				request = new OapiFileUploadSingleRequest();
				request.setFile(new FileItem(fileName, in));
				OapiFileUploadSingleResponse response = client.execute(request,
						token);
				if (0 == response.getErrcode()) {
					in.close();
					return response.getMediaId();
				}else{
					throw new RuntimeException(response.getBody());
				}
				//return "failed";
			}
			// 计算分块数量
			int partCount = (int) (fileSize / partSize);
			if (fileSize % partSize != 0) {
				partCount++;
			}
			// String type = tempOssFileName.split("\\.")[1];
			// 获取分块上传请求的uploadId
			OapiFileUploadTransactionRequest request = new OapiFileUploadTransactionRequest();
			request.setAgentId(String.valueOf(agentId));
			request.setFileSize(fileSize);
			request.setChunkNumbers((long) partCount);
			request.setHttpMethod("GET");
			ThirdDingTalkClient transactionClient = new ThirdDingTalkClient(
					DingConstant.DING_PREFIX + "/file/upload/transaction");
			OapiFileUploadTransactionResponse response = transactionClient
					.execute(request, token);
			if (0 == response.getErrcode()) {
				String uploadId = response.getUploadId();
				// 遍历分块上传。
				byte[] by = new byte[(int) partSize];
				try {
					int i = 1;
					while (in.read(by) != -1) {
						OapiFileUploadChunkRequest uploadChunkRequest = new OapiFileUploadChunkRequest();
						uploadChunkRequest.setAgentId(String.valueOf(agentId));
						uploadChunkRequest.setChunkSequence((long) i);
						uploadChunkRequest.setUploadId(uploadId);
						ThirdDingTalkClient uploadClient = new ThirdDingTalkClient(
								DingConstant.DING_PREFIX + "/file/upload/chunk?"
										+ WebUtils.buildQuery(
												uploadChunkRequest
														.getTextParams(),
												"utf-8"));
						uploadChunkRequest = new OapiFileUploadChunkRequest();
						uploadChunkRequest.setFile(new FileItem(fileName, by));
						OapiFileUploadChunkResponse uploadChunkResponse = uploadClient
								.execute(uploadChunkRequest, token);
						i++;
						if (partCount == i) {
							long lastSize = fileSize
									- (partSize * (partCount - 1));
							by = new byte[(int) lastSize];
						}
					}
				} catch (Exception e) {
					logger.error("文件[{}]遍历分块上传出现错误:{} fileName" + fileName, e);
					throw new RuntimeException("文件[{}]遍历分块上传出现错误:{} fileName" + fileName, e);
					//return "failed";
				} finally {
					if (null != in) {
                        in.close();
                    }
				}
				// 上传分块事务
				OapiFileUploadTransactionRequest transactionRequest = new OapiFileUploadTransactionRequest();
				transactionRequest.setAgentId(String.valueOf(agentId));
				transactionRequest.setFileSize(fileSize);
				transactionRequest.setChunkNumbers((long) partCount);
				transactionRequest.setUploadId(uploadId);
				transactionRequest.setHttpMethod("GET");
				OapiFileUploadTransactionResponse transactionResponse = transactionClient
						.execute(transactionRequest, token);
				if (0 == transactionResponse.getErrcode()) {
					return transactionResponse.getMediaId();
				}
			} else {
				logger.error("获取不了钉盘的分块上传Id:{}" + response.getErrmsg());
				throw new RuntimeException("获取不了钉盘的分块上传Id:{}" + response.getErrmsg());
				//return "failed";
			}
		} catch (Exception e) {
			logger.error("钉盘的分块上传异常:"+e.getMessage(),e);
			throw new RuntimeException("钉盘的分块上传异常:"+e.getMessage());
		}
		return "failed";
	}


}
