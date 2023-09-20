package com.landray.kmss.elec.device.service.spring;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelErrorCodeEnum;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.ElecChannelTxCodeEnum;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;
import com.landray.kmss.elec.device.handler.IElecChannelHandler;
import com.landray.kmss.elec.device.service.IElecChannelHeartbeatService;
import com.landray.kmss.elec.device.service.IElecChannelService;

/**
*@author yucf
*@date  2019年7月10日
*@Description               渠道通用服务
*/

public abstract class AbstractChannelServiceImp implements IElecChannelService, IElecChannelHeartbeatService {
	
	public static final Logger logger = LoggerFactory.getLogger(AbstractChannelServiceImp.class);
	
	
	//当前操作上下文数据传递
//	protected ThreadLocal<Map<String,Object>> context = new ThreadLocal<Map<String,Object>>(){
//		   protected synchronized Map<String, Object> initialValue() {
//               return new HashMap<String, Object>();
//       }
//	};
	
	//交易处理
	private Map<ElecChannelTxCodeEnum, IElecChannelHandler> txHandlerMap;

	public Map<ElecChannelTxCodeEnum, IElecChannelHandler> getTxHandlerMap() {
		return txHandlerMap;
	}

	public void setTxHandlerMap(Map<ElecChannelTxCodeEnum, IElecChannelHandler> txHandlerMap) {
		this.txHandlerMap = txHandlerMap;
	}
	
	@Override
    public ElecChannelResponseMessage<?> process(IElecChannelRequestMessage requestInfo, ElecAdditionalInfo additionalInfo) throws Exception{
		
//		try{
			
			logger.info("开始处理...");
			
			//1.服务是否可用
			logger.info("step1: 检查服务是否可用...");
			if(!this.isAvailable()){
				return ElecChannelResponseMessage.fail(ElecChannelErrorCodeEnum.SERVICE_IS_UNAVAILABLE);
			}

			//2.适配
			//不同渠道可能数据取值代表不同意思
			logger.info("step2: 参数适配...");
			this.paramAdapter(requestInfo, additionalInfo);
					
			//3.验证
			logger.info("step3: 参数检验...");
			try{
				this.validate(requestInfo, additionalInfo);
			}catch(IllegalArgumentException argEx){
				logger.error("参数完整性检验失败:{}" ,argEx.getMessage());
				return ElecChannelResponseMessage.fail(ElecChannelErrorCodeEnum.ILLEGAL_PARAM.getCode(), argEx.getMessage());
			}
			
			//4.组装报文
			logger.info("step4: 组装报文...");
			Object reqObj = this.assembleTxMessage(requestInfo, additionalInfo);

			//5.发送报文
			logger.info("step5: 发送报文...");
	        Object respObj = send(reqObj, additionalInfo);
	        
	        //6.转换报文
	        logger.info("step6: 报文转换...");
	        ElecChannelResponseMessage<?> respMsg = this.convertResponseMessage(requestInfo, respObj, additionalInfo);
	        
	        //7.数据存储
	        logger.info("step7: 数据存储...");
	        this.persistentData(reqObj, respObj, additionalInfo, respMsg);
	        
	        logger.info("处理完成...");
	        
	        return respMsg;
			
//		}catch(IllegalArgumentException argEx){
//			logger.error(argEx);
//			return ElecChannelResponseMessage.fail(ElecChannelErrorCodeEnum.非法参数);
//		}catch(ElecChannelException clEx){
//			logger.error(clEx);
//			return ElecChannelResponseMessage.fail(clEx.getCode(), clEx.getDesc()); 
//		}catch(Exception ex){
//			logger.error(ex);
//			return ElecChannelResponseMessage.fail(ElecChannelErrorCodeEnum.未知异常.getCode(), ex.getMessage());
//		}		
	}
	
	/**
	 * 参数适配(不同渠道部分枚举值可能有差异，需要转换)
	 * @param requestInfo      请求数据
	 * @param additionalInfo   附加数据
	 * @return
	 * @throws Exception
	 */
	public abstract IElecChannelRequestMessage paramAdapter(IElecChannelRequestMessage requestInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 数据持久化（一般是系统日志及操作日志)
	 * @param reqObj                      第三方包装的请求数据
	 * @param respObj                     第三方响应数据        
	 * @param additionalInfo              请求的额外数据  
	 * @param respMsg                     最终返回的业务数据
	 * @throws Exception
	 */
	public abstract void persistentData(Object reqObj, Object respObj, ElecAdditionalInfo additionalInfo, ElecChannelResponseMessage<?> respMsg) throws Exception;
	
	/**
	 * 参数非空检验
	 * @param requestInfo
	 * @param additionalInfo
	 * @throws IllegalArgumentException
	 */
	public void validate(IElecChannelRequestMessage requestInfo, ElecAdditionalInfo additionalInfo) throws IllegalArgumentException{
		
		Assert.notNull(requestInfo, "交易数据为空！");
		Assert.notNull(additionalInfo, "附加参数为空！");
		Assert.notNull(additionalInfo.getTxCode(), "未指定交易类型！");

		this.getTxHandlerMap().get(additionalInfo.getTxCode()).validate(requestInfo, additionalInfo);
	}
	
	/**
	 * 报文组装
	 * @param requestInfo         业务端请求数据
	 * @param additionalInfo      额外数据
	 * @return
	 * @throws Exception
	 */
	public abstract Object assembleTxMessage(IElecChannelRequestMessage requestInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 发送数据
	 * @param reqObj             第三方要求的报文数据
	 * @param additionalInfo     额外数据
	 * @return
	 * @throws Exception
	 */
	public abstract Object send(Object reqObj, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 报文包装成相应的业务数据
	 * @param requestInfo        业务请求数据
	 * @param respObj            第三方响应数据
	 * @param additionalInfo     请求额外数据
	 * @return
	 * @throws Exception
	 */
	public abstract ElecChannelResponseMessage<?> convertResponseMessage(IElecChannelRequestMessage requestInfo, Object respObj, ElecAdditionalInfo additionalInfo) throws Exception;


}
