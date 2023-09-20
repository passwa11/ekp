package com.landray.kmss.sys.attachment.integrate.wps.interfaces;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public interface ISysAttachmentWpsCloudOfficeProvider {

	public String getViewUrl(String fileId) throws Exception;

	public String getCode() throws Exception;

	public Boolean isAttHadSyncByAttMainId(String fdAttMainId) throws Exception;
	
	public void syncAttToAddByMainId(String fdAttMainId) throws Exception;
	
	public void syncAttToUpdateByMainId(String fdAttMainId) throws Exception;
	
	public Boolean checkIsWpsUser() throws Exception;

	public Boolean uploadToWpsCloud(String fdAttMainId) throws Exception;

	public void updateAttByMainId(String fdMainId) throws Exception;

	public JSONObject getWpsCloudViewUrl(String fdAttMainId, boolean canEdit,
			String history) throws Exception;
	
	public JSONObject getWpsCloudViewUrl(String fdAttMainId, boolean canEdit,
			String history,boolean contentFlag) throws Exception;

	public JSONObject getWpsCloudViewParam(String fdAttMainId,String mode) throws Exception;

	
	public String getResult(String taskID) throws Exception;

	public String setRed(List<Map<String, Object>> fileInfos,
			List<Map<String, Object>> fillDatas)
			throws Exception;

	public String opRevisions(String downurl, String fileId, String ext)
			throws Exception;

	public String opRevisions(String downurl, String fileId, String ext,
			String fdModelId, String fdModelName) throws Exception;

	public String setRed(List<Map<String, Object>> fileInfos,
			List<Map<String, Object>> fillDatas, String fdModelId,
			String fdModelName) throws Exception;

	public JSONObject getCovertDownload(String taskID) throws Exception;

	public JSONObject getCovertDownload(String fdModelId, String fdModelName,
			String type) throws Exception;

	public String getWpsDownloadUrl(String fdMainId) throws Exception;

	public String opRevisions(String downurl, String fileId, String ext,
			String fdModelId, String fdModelName, String fdAttMainId,
			String type)
			throws Exception;

	public String setRed(List<Map<String, Object>> fileInfos,
			List<Map<String, Object>> fillDatas, String fdModelId,
			String fdModelName, String fdAttMainId, String type)
			throws Exception;

	public String setFormMappingWord(List<Map<String, Object>> fileInfos,
						 List<Map<String, Object>> fillDatas, String fdModelId,
						 String fdModelName, String fdAttMainId,String fileName, String type)
			throws Exception;

	public String getUpdateSysOrgPersonFdId(String fdMainId) throws Exception;

	public Boolean checkWpsVersion(String fdMainId) throws Exception;

	public String getWpsWindowPreviewUrl(String fdMainId) throws Exception;

	public String getWpsLinuxPreviewUrl(String fdMainId) throws Exception;

	/**
	 * 获取wps云文档附件下载地址
	 * 业务模块的一些特殊场景，不受附件机制的自动保存配置项控制
	 * @param fdMainId
	 * @return
	 * @throws Exception
	 */
	public String getWpsOnlineDownloadUrl(String fdMainId) throws Exception;
}
