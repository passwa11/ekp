package com.landray.kmss.sys.attachment.interfaces;

import net.sf.json.JSONObject;

/**
 * @author 叶正平
 *
 * 附件传输扩展
 *
 */
public interface ISysAttachmentTransmissionProvider {

	/**
	 * 下载附件到其它地方
	 *
	 * @param attId
	 * @return
	 * @throws Exception
	 * {"status":0,"msg":"xxx"} 统一状态0为成功，其它错误自行定义状态
	 */
	public JSONObject downloadToOther(String attId,Long time) throws Exception;

	/**
	 * 上传附件到EKP
	 * @param jsonObject
	 * @return JSONObject
	 * @throws Exception
	 * {"status":0,"msg":"xxx"} 统一状态0为成功，其它错误自行定义状态
	 */
	public JSONObject uploadToEkp(JSONObject jsonObject) throws Exception;


	/**
	 * 执行是否成功
	 * @param attId
	 * @param type 以SysAttConstant定义的传输类型为准，例如DOWNLOAD_TO_DING、UPLOAD_TO_DING
	 * @return
	 * @throws Exception
	 * {"status":0,"msg":"xxx"} 统一状态0为成功，其它错误自行定义状态
	 */
	public JSONObject isSuccess(String attId,String type,Long time) throws Exception;


	/**
	 * @return 是否展示
	 * @throws Exception
	 */
	public boolean isShow() throws Exception;

}
