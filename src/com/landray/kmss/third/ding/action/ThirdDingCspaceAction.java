package com.landray.kmss.third.ding.action;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiFileUploadChunkRequest;
import com.dingtalk.api.request.OapiFileUploadSingleRequest;
import com.dingtalk.api.request.OapiFileUploadTransactionRequest;
import com.dingtalk.api.response.OapiFileUploadChunkResponse;
import com.dingtalk.api.response.OapiFileUploadSingleResponse;
import com.dingtalk.api.response.OapiFileUploadTransactionResponse;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.forms.ThirdDingCspaceForm;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingCspace;
import com.landray.kmss.third.ding.service.IThirdDingCspaceService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.sso.client.util.StringUtil;
import com.taobao.api.FileItem;
import com.taobao.api.internal.util.WebUtils;

import net.sf.json.JSONObject;

public class ThirdDingCspaceAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCspaceAction.class);

    private IThirdDingCspaceService thirdDingCspaceService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingCspaceService == null) {
            thirdDingCspaceService = (IThirdDingCspaceService) getBean("thirdDingCspaceService");
        }
        return thirdDingCspaceService;
    }

	protected ISysAttMainCoreInnerService sysAttMainService;

	protected ISysAttMainCoreInnerService getSysAttMainCoreInnerService() {
		if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) getBean(
                    "sysAttMainService");
        }
		return sysAttMainService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCspace.class);
        //兼容上传的fdType数据
		String fdType = request.getParameter("type");
		String whereBlock = hqlInfo.getWhereBlock();
		if("1".equals(fdType)){
			if(StringUtil.isNull(whereBlock)){
				hqlInfo.setWhereBlock("fdType=:fdType");
			}else{
				hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()+" AND fdType=:fdType");
			}
			hqlInfo.setParameter("fdType","1");
		}else if("0".equals(fdType)){
			if(StringUtil.isNull(whereBlock)){
				hqlInfo.setWhereBlock("fdType!=:fdType or fdType IS NULL");
			}else{
				hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()+" AND (fdType!=:fdType or fdType IS NULL)");
			}
			hqlInfo.setParameter("fdType","1");
		}
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingCspace.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCspaceForm thirdDingCspaceForm = (ThirdDingCspaceForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCspaceService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCspaceForm;
    }

	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.data(mapping, form, request, response);
	}

	// 后端接口上传文件到定盘
	@SuppressWarnings("unchecked")
	public ActionForward uploadFileToDingSpace(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");

		JSONObject json = new JSONObject();
		String attId = request.getParameter("fdId");
		SysOrgElement fdCreater = (SysOrgElement) UserUtil.getUser();
		logger.warn("附件Id:" + attId + "  fdCreater:" + fdCreater.getFdName());
		SysAttMain sysAttMain = (SysAttMain) getSysAttMainCoreInnerService()
				.findByPrimaryKey(attId);
		String name = sysAttMain.getFdFileName();

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"fdAttId=:fdAttId and fdCreater.fdId=:userId and fdFileId=:fdFileId");
		hqlInfo.setParameter("fdAttId", attId);
		hqlInfo.setParameter("userId", fdCreater.getFdId());
		hqlInfo.setParameter("fdFileId", sysAttMain.getFdFileId());
		List<ThirdDingCspace> list = getServiceImp(request).findList(hqlInfo);
		String media_id = null;
		ThirdDingCspace tempCspace = new ThirdDingCspace();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ThirdDingCspace cspace = list.get(i);
				logger.warn("状态：" + cspace.getFdStatus());
				if ("0".equals(cspace.getFdStatus())) {
					// 上传失败
				} else if ("2".equals(cspace.getFdStatus())) {
					// 成功
					media_id = cspace.getFdMediaId();
					break;
				} else if ("1".equals(cspace.getFdStatus())) {
					// 上传中
					json.put("errcode", "-1");
					json.put("errmsg", "文件正在上传中...");
					response.getWriter().print(json);
					return null;
				}
			}

		}
		logger.debug("------media_id:" + media_id);
		if (StringUtil.isNotNull(media_id)) {
			json.put("errcode", "0");
			json.put("media_id", media_id);
			json.put("name", name);
		} else {
			// 先记录日志
			tempCspace.setFdAttId(attId);
			tempCspace.setFdCreater(fdCreater);
			tempCspace.setFdName(name);
			tempCspace.setFdStatus("1");
			tempCspace.setDocCreateTime(new Date());
			tempCspace.setFdFileId(sysAttMain.getFdFileId());
			String id = getServiceImp(request).add(tempCspace);
			((IThirdDingCspaceService) getServiceImp(request))
					.uploadToDingSpace(name,
							getSysAttMainCoreInnerService()
									.getInputStream(attId),
							id);
			json.put("errcode", "-1");
			json.put("errmsg", "文件正在上传中...");
			// response.getWriter().print(json);
		}
		logger.debug("json:" + json);
		response.getWriter().print(json);
		return null;
	}

	// 备份
	public ActionForward uploadFileToDingSpace2(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");

		JSONObject json = new JSONObject();
		String attId = request.getParameter("fdId");
		SysOrgElement fdCreater = (SysOrgElement) UserUtil.getUser();
		logger.warn("附件Id:" + attId + "  fdCreater:" + fdCreater.getFdName());
		SysAttMain sysAttMain = (SysAttMain) getSysAttMainCoreInnerService()
				.findByPrimaryKey(attId);
		String name = sysAttMain.getFdFileName();

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdAttId=:fdAttId and fdCreater.fdId=:userId");
		hqlInfo.setParameter("fdAttId", attId);
		hqlInfo.setParameter("userId", fdCreater.getFdId());
		List<ThirdDingCspace> list = getServiceImp(request).findList(hqlInfo);
		String media_id = null;
		ThirdDingCspace tempCspace = new ThirdDingCspace();



		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ThirdDingCspace cspace = list.get(i);
				logger.warn("状态：" + cspace.getFdStatus());
				if ("0".equals(cspace.getFdStatus())) {
					// 上传失败
				} else if ("2".equals(cspace.getFdStatus())) {
					// 成功
					media_id = cspace.getFdMediaId();
					break;
				} else if ("1".equals(cspace.getFdStatus())) {
					// 上传中
					json.put("errcode", "-1");
					json.put("errmsg", "文件正在上传中...");
					response.getWriter().print(json);
					return null;
				}
			}

		}


		if (StringUtil.isNotNull(media_id)) {
			json.put("errcode", "0");
			json.put("media_id", media_id);
			json.put("name", name);
		} else {
			// 先记录日志
			tempCspace.setFdAttId(attId);
			tempCspace.setFdCreater(fdCreater);
			tempCspace.setFdName(name);
			tempCspace.setFdStatus("1");
			tempCspace.setDocCreateTime(new Date());
			String id = getServiceImp(request).add(tempCspace);
			ThirdDingCspace cspace = (ThirdDingCspace) getServiceImp(request)
					.findByPrimaryKey(id);
			media_id = uploadToDingSpace(name,
					getSysAttMainCoreInnerService().getInputStream(attId));
			logger.warn("media_id:" + media_id + " name:" + name);
			if (StringUtil.isNotNull(media_id)) {
				json.put("errcode", "0");
				json.put("media_id", media_id);
				json.put("name", name);
				cspace.setFdStatus("2");
				cspace.setFdMediaId(media_id);
				getServiceImp(request).update(cspace);
			} else {
				json.put("errcode", "1");
				json.put("errmsg", "上传过程中发生异常");
				json.put("media_id", media_id);
				json.put("name", name);
				cspace.setFdStatus("0");
				getServiceImp(request).update(cspace);
			}

		}
		logger.debug("json:" + json);
		response.getWriter().print(json);

		return null;
	}

	// pc端
	private String oauth2buildAuthorizationUrl(String redirectUri,
			String state) {
		String url = "https://oapi.dingtalk.com/connect/oauth2/authorize?";
		url += "appid=" + DingConfig.newInstance().getDingCorpid();
		try {
			url += "&redirect_uri=" + URLEncoder.encode(redirectUri, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += "&response_type=code";
		url += "&scope=snsapi_base";
		if (state != null) {
			url += "&state=" + state;
		}
		logger.debug("url=" + url);
		return url;
	}

	// 移动端

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
				DingTalkClient client = new DefaultDingTalkClient(
						"https://oapi.dingtalk.com/file/upload/single?"
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
				}
				return "failed";
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
			DingTalkClient transactionClient = new DefaultDingTalkClient(
					"https://oapi.dingtalk.com/file/upload/transaction");
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
						DingTalkClient uploadClient = new DefaultDingTalkClient(
								"https://oapi.dingtalk.com/file/upload/chunk?"
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
					return "failed";
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
				return "failed";
			}
		} catch (Exception e) {
			logger.error("钉盘的分块上传异常:{}", e);
		}
		return "failed";

	}

	// 下载钉盘文件
	public ActionForward download(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		String code = request.getParameter("code");
		log.debug("code:" + code);
		String agentId = request.getParameter("agentId");
		log.debug("agentId:" + agentId);
		if (StringUtil.isNull(agentId)) {
			agentId = DingConfig.newInstance().getDingAgentid();
		}

		String spaceId = request.getParameter("spaceId");
		log.debug("spaceId:" + spaceId);

		String fileId = request.getParameter("fileId");
		log.debug("fileId:" + fileId);

		String fileName = request.getParameter("fileName");
		log.debug("fileName:" + fileName);

		String fileType = request.getParameter("fileType");
		log.debug("fileType:" + fileType);

		json.put("agentId", agentId);
		json.put("code", code);
		json.put("spaceId", spaceId);
		json.put("fileId", fileId);
		json.put("fileName", fileName);
		json.put("fileType", fileType);

		ByteArrayOutputStream out = DingUtils.dingApiService.download(json);
		// 业务处理...

		if (out != null) {
			// 测试下载到本地
			writer2Local(out, fileName);
		} else {
			log.error("从钉盘下载文件异常：" + fileName);
		}
		response.getWriter().print(out);
		return null;
	}

	// 测试将字节流输出到文件
	public void writer2Local(ByteArrayOutputStream out, String fileName) {
		try {
			// 字节数组
			byte[] bytes = out.toByteArray();
			// log.warn("输入流out：" + out);
			FileOutputStream fos = new FileOutputStream("D:\\" + fileName);
			fos.write(bytes, 0, bytes.length);
			if (fos != null) {
                fos.close();
            }
			if (out != null) {
                out.close();
            }
			System.out.println("--------写出成功-------" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
