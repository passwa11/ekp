package com.landray.kmss.elec.device.service.spring;

import java.util.List;

import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.ElecChannelUserInfo;
import com.landray.kmss.elec.device.client.ElecContractInfo;
import com.landray.kmss.elec.device.client.ElecEnterpriseAccInfo;
import com.landray.kmss.elec.device.client.ElecPersonalAccInfo;
import com.landray.kmss.elec.device.client.IElecChannelResponseMessage;
import com.landray.kmss.elec.device.service.IElecChannelContractService;
import com.landray.kmss.elec.device.vo.IElecChannelFileVO;
import com.landray.kmss.elec.device.vo.IElecChannelUrlVO;

/**
*@author yucf
*@date  2019年7月10日
*@Description             渠道合同服务
*/

public abstract class AbstractChannelContractServiceImp extends AbstractChannelServiceImp implements IElecChannelContractService {	
	
	@Override
	public ElecChannelResponseMessage<ElecPersonalAccInfo> openAccount(ElecPersonalAccInfo personalAccInfo,
			ElecAdditionalInfo additionalInfo) throws Exception {
		
		logger.info("个人开户开始...");
		
		ElecChannelResponseMessage<ElecPersonalAccInfo> respMsg = (ElecChannelResponseMessage<ElecPersonalAccInfo>) this.process(personalAccInfo, additionalInfo);
		
		logger.info("个人开户完成...");
		
		return respMsg;
	}

	@Override
	public ElecChannelResponseMessage<ElecEnterpriseAccInfo> openAccount(
			ElecEnterpriseAccInfo enterpriseAccInfo,
			ElecAdditionalInfo additionalInfo) throws Exception {
		
		logger.info("企业开户开始...");
		
		ElecChannelResponseMessage<ElecEnterpriseAccInfo> respMsg = (ElecChannelResponseMessage<ElecEnterpriseAccInfo>)  this.process(enterpriseAccInfo, additionalInfo);
		
		logger.info("企业开户完成...");
		
		return respMsg;
		
	}
	

	@Override
	public ElecChannelResponseMessage<ElecContractInfo> queryContract(String contractNo, ElecAdditionalInfo additionalInfo) throws Exception{
			
		logger.info("查询合同开始...");
	
		ElecContractInfo contractInfo = new ElecContractInfo();
		contractInfo.setContractNo(contractNo);
		
		ElecChannelResponseMessage<ElecContractInfo> respMsg = (ElecChannelResponseMessage<ElecContractInfo>) this.process(contractInfo, additionalInfo);
		
		logger.info("查询合同完成...");
		
		return respMsg;
	}

	@Override
	public ElecChannelResponseMessage<ElecChannelUserInfo> queryUserInfo(ElecChannelUserInfo channelUserInfo, ElecAdditionalInfo additionalInfo) throws Exception {
		//由具体的子类实现
		return null;
	}
	
	@Override
	public ElecChannelResponseMessage<List<IElecChannelFileVO>> downloadContractFile(String contractNo, ElecAdditionalInfo additionalInfo) throws Exception{
		return null;
	}

	@Override
	public ElecChannelResponseMessage<List<IElecChannelUrlVO>> querySignUrls(String contractNo,
																			 ElecAdditionalInfo additionalInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getChannelFlag() {
		return null;
	}
}
