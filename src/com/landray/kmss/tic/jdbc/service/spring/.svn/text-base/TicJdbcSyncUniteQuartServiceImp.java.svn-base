package com.landray.kmss.tic.jdbc.service.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.log.constant.TicCoreLogConstant;
import com.landray.kmss.tic.core.log.interfaces.ITicCoreLogInterface;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.core.log.service.ITicCoreLogMainService;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.core.sync.model.TicCoreSyncJob;
import com.landray.kmss.tic.core.sync.model.TicCoreSyncTempFunc;
import com.landray.kmss.tic.core.sync.service.ITicCoreSyncTempFuncService;
import com.landray.kmss.tic.core.sync.service.ITicCoreSyncUniteQuartzService;
import com.landray.kmss.tic.jdbc.iface.ITicJdbcTaskSync;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.util.JdbcRunSyncType;
import com.landray.kmss.tic.jdbc.util.JdbcSqlUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.util.StringUtil;

import net.sf.json.JSONObject;

public class TicJdbcSyncUniteQuartServiceImp implements ITicCoreSyncUniteQuartzService {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(TicJdbcSyncUniteQuartServiceImp.class);

	private ITicCoreLogInterface ticCoreLogInterface=(ITicCoreLogInterface) SpringBeanUtil
			.getBean("ticCoreLogInterface");

	@Override
    public void executeFuncByTask(TicCoreSyncJob ticCoreSyncJob, TicCoreSyncTempFunc tempFunc, TicCoreFuncBase ticBase)
			throws Exception {
		logger.debug("开始执行ticJdbc定时任务");
		//KmssMessages messages = new KmssMessages();
		//String fdId = (String) jsonObj.get("ticjdbcQuartzId");
		//String errorInfor="";
		TicCoreLogMain ticLog = new TicCoreLogMain(Constant.FD_TYPE_JDBC,
				ticBase.getFdAppType(),
				TicCoreLogConstant.TIC_CORE_LOG_TYPE_SUCCESS,
				ticBase.getFdName(), ticBase.getFdId());

		try {
			// 任务同步
			Date startDate = new Date();
			logger.info("startDate=" + startDate);
			long start = System.currentTimeMillis();
			String fdMessages = "";
			String fdExportPar = "";
			boolean flag=true;
			logger.info("======任务管理同步计时开始======");
			if(ticBase==null){
				return;//函数为空
			}
			TicJdbcDataSet ticJdbcDataSet=(TicJdbcDataSet)ticBase;
			String syncTypeJson=tempFunc.getFdSyncType();//= ticJdbcRelation.getFdSyncType();
			if(StringUtil.isNull(syncTypeJson)){
				return;
			}
			JSONObject json = JSONObject.fromObject(syncTypeJson);
			if(json==null){
				return;
			}
			String syncType = json.getString("syncType");
			if(StringUtil.isNull(syncType)){
				return;
			}
			// 获取任务分发容器
			Map<String, String> syncTypeMap = JdbcRunSyncType.syncTypeMap;
			ITicJdbcTaskSync taskRun = (ITicJdbcTaskSync) SpringBeanUtil
					.getBean(syncTypeMap.get(syncType));
			if(taskRun==null){
				return;//未配置处理类
			}
			String mappConfigJsonStr=tempFunc.getFdMappConfig();
			if(StringUtil.isNull(mappConfigJsonStr)){
				return;//映射为空则不处理
			}
			String fdDataSource=ticJdbcDataSet.getFdDataSource();
			if(StringUtil.isNull(fdDataSource)){
				return;//源表数据源为空
			}
			String fdDataSourceSql=ticJdbcDataSet.getFdSqlExpression();
			if(StringUtil.isNull(fdDataSourceSql)){
				return;//源表sql语句为空
			}
			CompDbcp comp=tempFunc.getFdCompDbcp();
			if(comp==null){
				return;
			}
			String fdTargetSource=comp.getFdId();
			if(StringUtil.isNull(fdTargetSource)){
				return;//目标表数据源为空
			}
			json.put("fdTargetSource",fdTargetSource);
			json.put("fdDataSource",fdDataSource);
			json.put("fdMappConfigJson",mappConfigJsonStr);
			//json.put("fdInParam", tempFunc.getFdInParam());
			fdDataSourceSql = JdbcSqlUtil.frommatSqlRemoveLimitCondition(fdDataSourceSql);
			
			//如果sql语句带有条件参数,需要采用预编译方式处理
			if(com.landray.kmss.util.StringUtil.isNotNull(tempFunc.getFdInParam())){
				//预编译时值的index映射
				Map<String,Object> valueMapping = new HashMap<String,Object>();
				//去除没有传参的查询条件并格式化成预编译sql语句
				fdDataSourceSql = JdbcSqlUtil.removeNoQueryConditionAndFrommat(fdDataSourceSql,JSON.parseObject(tempFunc.getFdInParam()),valueMapping);
				if(valueMapping.size()>0){
					com.alibaba.fastjson.JSONObject mapjo = new com.alibaba.fastjson.JSONObject(valueMapping);
					json.put("inParam", mapjo.toJSONString());
				}
			}
			json.put("fdDataSourceSql",fdDataSourceSql);
			json.put("outParam", ticJdbcDataSet.getFdParaOut());
			
			// 执行任务并返回日志结果
			Map<String, String> logMap = taskRun.run(json);

			tempFunc.setFdSyncType(json.toString());
			ITicCoreSyncTempFuncService ticCoreSyncTempFuncService = (ITicCoreSyncTempFuncService) SpringBeanUtil
					.getBean("ticCoreSyncTempFuncService");
			ticCoreSyncTempFuncService.update(tempFunc);

			// 记录日志信息
			fdExportPar += logMap.get("errorDetail") +"<p></p>";
			fdMessages += logMap.get("message") +"<p></p>";

			if(StringUtils.isNotEmpty(logMap.get("errorDetail"))){
				flag=false;
			}
			// 判断正常日志还是错误日志
			String fdIsErr = TicCoreLogConstant.TIC_CORE_LOG_TYPE_SUCCESS;
			//本次迁移结果标志
			if (!flag) {
				fdIsErr = TicCoreLogConstant.TIC_CORE_LOG_TYPE_ERROR;
			}
			// 写入日志
			//ticCoreLogInterface.saveLogMain(ticCoreSyncJob.getFdSubject(), Constant.FD_TYPE_JDBC, 
			//		startDate, "", fdExportPar, fdMessages, fdIsErr,ticBase.getFdAppType());//从函数上取值
			long end = System.currentTimeMillis();
			logger.info("同步耗时：" + (end - start));
			ticLog.setFdAppType(ticBase.getFdAppType());
			ticLog.setFdEndTime(new Date());
			ticLog.setFdExportParOri(fdExportPar);
			ticLog.setFdIsErr(fdIsErr);
			ticLog.setFdLogType(Constant.FD_TYPE_JDBC);
			ticLog.setFdMessages(fdMessages);
			ticLog.setFdStartTime(startDate);
			ticLog.setFdTimeConsumingOrg((end - start)+"");
			
		} catch (Exception e) {
			ticLog.setFdIsErr(TicCoreLogConstant.TIC_CORE_LOG_TYPE_ERROR);
			ticLog.setFdExtMsg(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			ticCoreLogMainService.saveTicCoreLogMain(ticLog);
		}
	}

	private static ITicCoreLogMainService ticCoreLogMainService = (ITicCoreLogMainService) SpringBeanUtil
			.getBean("ticCoreLogMainService");
}
