package com.landray.kmss.elec.device.service;

import java.util.Collection;
import java.util.List;

import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.ElecChannelUserInfo;
import com.landray.kmss.elec.device.client.ElecContractInfo;
import com.landray.kmss.elec.device.client.ElecEnterpriseAccInfo;
import com.landray.kmss.elec.device.client.ElecPersonalAccInfo;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;
import com.landray.kmss.elec.device.client.IElecChannelResponseMessage;
import com.landray.kmss.elec.device.vo.IElecChannelFileVO;
import com.landray.kmss.elec.device.vo.IElecChannelUrlVO;

/**
*@author yucf
*@date  2019年7月10日
*@Description            渠道合同服务接口
*@deprecated
 * 签署类 {@link com.landray.kmss.elec.core.signature.service.IElecChannelSignService}<br/>
 * 文件类 {@link com.landray.kmss.elec.core.file.service.IElecChannelFileService}
*/
@Deprecated
public interface IElecChannelContractService {
	
	/**
	 * 个人开户
	 * @param personalAccInfo      个人信息
	 * @param additionalInfo
	 * @throws Exception
	 */
	ElecChannelResponseMessage<ElecPersonalAccInfo> openAccount(ElecPersonalAccInfo personalAccInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 企业开户
	 * @param enterpriseAccInfo    企业信息
	 * @param additionalInfo
	 * @throws Exception
	 */
	ElecChannelResponseMessage<ElecEnterpriseAccInfo> openAccount(ElecEnterpriseAccInfo enterpriseAccInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 上传合同
	 * @param contractSignInfo     合同签暑信息
	 * @param additionalInfo
	 * @throws Exception
	 * @deprecated  建议实现com.landray.kmss.elec.core.signature.service.IElecChannelSignService下方法进行签署
	 *
	 */
	@Deprecated
	ElecChannelResponseMessage<? extends IElecChannelResponseMessage> uploadContract(IElecChannelRequestMessage contractSignInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	
	/**
	 * 合同查询 
	 * @param contractNo           合同编号
	 * @param additionalInfo
	 * @throws Exception
	 */
	ElecChannelResponseMessage<ElecContractInfo> queryContract(String contractNo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 单个合同下载(输出文件为pdf格式)
	 * @param contractNo           合同编号 
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<byte[]> downloadContract(String contractNo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 多个合同下载(输出文件为zip格式）
	 * @param contractNos         多个合同编号
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<byte[]> batchDownloadContract(Collection<String> contractNos, ElecAdditionalInfo additionalInfo) throws Exception;
	
	
	/**
	 * 查询渠道用户信息
	 * @param channelUserInfo
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<ElecChannelUserInfo> queryUserInfo(ElecChannelUserInfo channelUserInfo, ElecAdditionalInfo additionalInfo)throws Exception;
	
	/**
	 * 签署文件下载
	 * @param contractNo
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<List<IElecChannelFileVO>> downloadContractFile(String contractNo, ElecAdditionalInfo additionalInfo) throws Exception;

	/**
	 * 签署链接获取
	 * @param contractNo
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<List<IElecChannelUrlVO>> querySignUrls(String contractNo, ElecAdditionalInfo additionalInfo) throws Exception;

	/**
	 * 渠道服务名称
	 * @return
	 */
	String getChannelFlag();

}
