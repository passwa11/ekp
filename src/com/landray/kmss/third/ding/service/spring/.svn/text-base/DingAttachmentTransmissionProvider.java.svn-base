package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.interfaces.ISysAttachmentTransmissionProvider;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingCspace;
import com.landray.kmss.third.ding.service.IThirdDingCspaceService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;

public class DingAttachmentTransmissionProvider
		implements ISysAttachmentTransmissionProvider {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingAttachmentTransmissionProvider.class);

	private final String FILE_SUCCESS = "2";  //成功
	private final String FILE_ING = "1";  //上传或下载中
	private final String FILE_FAILED = "0";  //失败

	public final String DOWNLOAD_TO_DING = "0";
	public final String UPLOAD_FROM_DING = "1";

	private IThirdDingCspaceService thirdDingCspaceService;

	public IThirdDingCspaceService
	getThirdDingCspaceService() {
		if (thirdDingCspaceService == null) {
			thirdDingCspaceService = (IThirdDingCspaceService) SpringBeanUtil
					.getBean("thirdDingCspaceService");
		}
		return thirdDingCspaceService;
	}

	protected ISysAttMainCoreInnerService sysAttMainService;

	protected ISysAttMainCoreInnerService getSysAttMainCoreInnerService() {
		if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
                    .getBean(
                            "sysAttMainService");
        }
		return sysAttMainService;
	}

	/**
	 * 上传附件到钉盘
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject downloadToOther(String attId, Long time)
			throws Exception {
		logger.debug("-------attId:" + attId + "    time:" + time);
		JSONObject result = new JSONObject();
		JSONObject msg = new JSONObject();
		try {
			SysOrgElement fdCreater = (SysOrgElement) UserUtil.getUser();
			logger.debug(
					"附件Id:" + attId + "  fdCreater:" + fdCreater.getFdName());
			SysAttMain sysAttMain = (SysAttMain) getSysAttMainCoreInnerService()
					.findByPrimaryKey(attId);
			String name = sysAttMain.getFdFileName();

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdAttId=:fdAttId and fdCreater.fdId=:userId and fdFileId=:fdFileId and docCreateTime >= :searchDate");
			hqlInfo.setParameter("fdAttId", attId);
			hqlInfo.setParameter("userId", fdCreater.getFdId());
			hqlInfo.setParameter("fdFileId", sysAttMain.getFdFileId());
			String cspaceTime = DingConfig.newInstance().getCspaceTime();
			logger.debug("cspaceTime:" + cspaceTime);
			int limitTime = 30;
			if (StringUtil.isNotNull(cspaceTime)) {
				limitTime = Integer.valueOf(cspaceTime);
				if (limitTime < 0) {
					limitTime = -limitTime;
				}
			}
			logger.debug("limitTime:" + limitTime);
			hqlInfo.setParameter("searchDate",
					new Date(time - limitTime * 1000 * 60)); // 默认查询30分钟内的

			List<ThirdDingCspace> list = getThirdDingCspaceService()
					.findList(hqlInfo);
			String media_id = null;
			ThirdDingCspace tempCspace = new ThirdDingCspace();
			boolean hasUpload = false;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ThirdDingCspace cspace = list.get(i);
					logger.debug("状态：" + cspace.getFdStatus());
					if ("0".equals(cspace.getFdStatus())) {
						// 上传失败
					} else if ("2".equals(cspace.getFdStatus())) {
						// 成功
						media_id = cspace.getFdMediaId();
						break;
					} else if ("1".equals(cspace.getFdStatus())) {
						// 上传中
						hasUpload = true;
					}
				}

			}

			if (StringUtil.isNotNull(media_id)) {
				result.put("status", 0);
				msg.put("url", media_id);
				msg.put("corpId", DingConfig.newInstance().getDingCorpid());
				msg.put("name", tempCspace.getFdName());
				msg.put("message", "ok");
				result.put("msg", msg);
			} else if (hasUpload) {
				result.put("status", 0);
				msg.put("message", "文件正在后台上传中...");
				result.put("msg", msg);
			} else {
				// 先记录日志
				tempCspace.setFdAttId(attId);
				tempCspace.setFdCreater(fdCreater);
				tempCspace.setFdName(name);
				tempCspace.setFdStatus("1");
				tempCspace.setFdType(this.DOWNLOAD_TO_DING);
				tempCspace.setDocCreateTime(new Date(time));
				tempCspace.setFdFileId(sysAttMain.getFdFileId());
				String id = getThirdDingCspaceService().add(tempCspace);

				getThirdDingCspaceService().uploadToDingSpace(name,
						getSysAttMainCoreInnerService()
								.getInputStream(attId),
						id);
				result.put("status", 0);
				msg.put("message", "文件正在后台上传中...");
				result.put("msg", msg);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("status", -1);
			msg.put("errmsg", "程序异常");
			result.put("msg", msg);
		}
		return result;
	}

	@Override
	public JSONObject uploadToEkp(JSONObject jsonObject) throws Exception {

		JSONObject result = new JSONObject();
		JSONObject msg = new JSONObject();
		String fileName = jsonObject.getString("fileName");
		String dingFildId = jsonObject.getString("fileId");
		String fdAttId = jsonObject.getString("fdAttId");

		// 先记录日志
		SysOrgElement fdCreater = (SysOrgElement) UserUtil.getUser();
		ThirdDingCspace tempCspace = new ThirdDingCspace();
		tempCspace.setFdAttId(null);
		tempCspace.setFdCreater(fdCreater);
		tempCspace.setFdName(fileName);
		tempCspace.setFdStatus(FILE_ING);
		tempCspace.setDocCreateTime(new Date());
		tempCspace.setFdType(UPLOAD_FROM_DING);
		tempCspace.setFdDingFileId(dingFildId);
		tempCspace.setFdAttId(fdAttId);
		String id = getThirdDingCspaceService().add(tempCspace);
		String ids = id+"-"+dingFildId;
		jsonObject.put("fdId",id);
		jsonObject.put("ids",ids);
		logger.warn("下载钉盘文件。。。"+fileName);
		getThirdDingCspaceService().downloadFromDing(jsonObject);
		logger.warn("文件正在后台上下载...");
		msg.put("message", "文件正在后台上下载...");

		result.put("ids",id+"-"+dingFildId);
		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public JSONObject isSuccess(String attId, String type, Long time)
			throws Exception {

		logger.debug("-------type:" + type + "    time:" + time);
		JSONObject result = new JSONObject();
		JSONObject msg = new JSONObject();
		try {
			if (SysAttConstant.DOWNLOAD_TO_DING.equals(type)) {
				int status = 0;
				String returnMsg="文件上传中";
				SysOrgElement fdCreater = (SysOrgElement) UserUtil.getUser();
				logger.debug(
						"附件Id:" + attId + "  fdCreater:"
								+ fdCreater.getFdName());
				SysAttMain sysAttMain = (SysAttMain) getSysAttMainCoreInnerService()
						.findByPrimaryKey(attId);

				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdAttId=:fdAttId and fdCreater.fdId=:userId and fdFileId=:fdFileId and docCreateTime >= :searchDate");
				hqlInfo.setParameter("fdAttId", attId);
				hqlInfo.setParameter("userId", fdCreater.getFdId());
				hqlInfo.setParameter("fdFileId", sysAttMain.getFdFileId());
				String cspaceTime = DingConfig.newInstance().getCspaceTime();
				logger.debug("cspaceTime:" + cspaceTime);
				int limitTime = 30;
				if (StringUtil.isNotNull(cspaceTime)) {
					limitTime = Integer.valueOf(cspaceTime);
					if (limitTime < 0) {
						limitTime = -limitTime;
					}
				}
				logger.debug("limitTime:" + limitTime);
				hqlInfo.setParameter("searchDate",
						new Date(time - limitTime * 1000 * 60)); // 查询30分钟内的
				List<ThirdDingCspace> list = getThirdDingCspaceService()
						.findList(hqlInfo);
				String media_id = null;
				ThirdDingCspace tempCspace = new ThirdDingCspace();
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						ThirdDingCspace cspace = list.get(i);
						logger.debug("状态：" + cspace.getFdStatus());
						if (FILE_FAILED.equals(cspace.getFdStatus())) {
							// 上传失败
							status=-1;
							returnMsg=cspace.getFdErrorMsg();
						} else if (FILE_SUCCESS.equals(cspace.getFdStatus())) {
							// 成功
							media_id = cspace.getFdMediaId();
							tempCspace = cspace;
							status=0;
							break;
						} else if (FILE_ING.equals(cspace.getFdStatus())) {
							// 上传中
							status=1;
							returnMsg="文件上传中";
						}
					}
					if (StringUtil.isNotNull(media_id)) {
						result.put("status", 0);
						msg.put("url", media_id);
						msg.put("corpId", DingUtil.getCorpId());
						msg.put("name", tempCspace.getFdName());
						msg.put("message", "ok");
						result.put("msg", msg);
					} else {
						result.put("status", status);
						msg.put("errmsg", returnMsg);
						result.put("msg", msg);
					}
				} else {
					logger.debug(
							"无法找到该附件上传记录     fdId:" + attId + "  fdCreater:"
									+ fdCreater.getFdName());
					result.put("status", -1);
					msg.put("errmsg", "无法找到该附件上传记录 ");
					result.put("msg", msg);
				}
			}else if(SysAttConstant.UPLOAD_TO_DING.equals(type)) {
				//从钉盘上传到ekp
				logger.debug("attId:"+attId);
				String fildId = "";
				String errMsg = null;
				String name = "";

				int status = 0;
				if(StringUtil.isNotNull(attId)&&attId.contains(";")){

					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock("fdAttId=:fdAttId and fdDingFileId=:fdDingFileId");
					hqlInfo.setParameter("fdAttId",attId.split(";")[0]);
					hqlInfo.setParameter("fdDingFileId",attId.split(";")[1]);
					ThirdDingCspace cspace = (ThirdDingCspace) thirdDingCspaceService.findFirstOne(hqlInfo);
					if(cspace != null){
						if (FILE_FAILED.equals(cspace.getFdStatus())) {
							// 上传失败
							errMsg="上传失败";
							String error = cspace.getFdErrorMsg();
							if(StringUtil.isNotNull(error)&&error.contains("{")){
								errMsg = error.substring(error.indexOf("{"),error.length());
							}
							status=-1;
						} else if (FILE_SUCCESS.equals(cspace.getFdStatus())) {
							// 成功
							fildId = cspace.getFdFileId();
							name = cspace.getFdName();
						} else if (FILE_ING.equals(cspace.getFdStatus())) {
							// 上传中
							errMsg="上传中...";
							status=1;
						}
					}else{
						errMsg="无法找到钉盘文件记录："+attId;
					}

				}else{
					errMsg="attId格式错误！！！";
				}
				if (StringUtil.isNotNull(fildId)) {
					result.put("status", 0);
					msg.put("fildId", fildId);
					msg.put("name", name);
					msg.put("message", "ok");
					result.put("msg", msg);
				} else {
					result.put("status", status);
					msg.put("errmsg",errMsg);
					result.put("msg", msg);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("status", -1);
			msg.put("errmsg", "程序异常");
			result.put("msg", msg);
		}

		return result;
	}

	// 是否展示
	@Override
	public boolean isShow() throws Exception {
		String dingEnable = DingConfig.newInstance().getDingEnabled();
		String cspaceEnable = DingConfig.newInstance().getCspaceEnable();
		if (StringUtil.isNotNull(dingEnable) && "true".equals(dingEnable)
				&& StringUtil.isNotNull(cspaceEnable)
				&& "true".equals(cspaceEnable)) {
			return true;
		}
		return false;
	}

}
