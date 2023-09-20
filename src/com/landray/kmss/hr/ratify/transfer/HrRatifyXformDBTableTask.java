package com.landray.kmss.hr.ratify.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.service.IHrRatifyMainService;
import com.landray.kmss.hr.ratify.util.PluginUtil;
import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.xform.base.model.SysFormDbTable;
import com.landray.kmss.sys.xform.base.service.ISysFormDbTableService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 人事流程模板自定义表单映射表历史文档映射迁移
 */
public class HrRatifyXformDBTableTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil.getBean("sysAdminTransferTaskService");
		ISysFormDbTableService sysFormDbTableService = (ISysFormDbTableService) SpringBeanUtil.getBean("sysFormDbTableService");
		IHrRatifyMainService hrRatifyMainService = (IHrRatifyMainService) SpringBeanUtil.getBean("hrRatifyMainService");
		
		List<Map<String,String>> templateInfos = new ArrayList<Map<String,String>>();
		Boolean isError = false;
		try {
			List<SysAdminTransferTask> list = sysAdminTransferTaskService.getBaseDao().findValue(null, "sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			if (list != null && list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = list.get(0);
				if (sysAdminTransferTask.getFdStatus() != 1 ||
						sysAdminTransferTask.getFdResult() == 2) {// 未执行或者执行结果是错误的
					//遍历不同类型的模板，一起处理
					Map<String, List<Map<String, String>>> configs = PluginUtil.getConfigs(PluginUtil.EXTENSION_TEMPLATE_POINT_ID);
					List<Map<String, String>> templateExtends = configs.get(PluginUtil.EXTENSION_TEMPLATE_POINT_ID);
					int count = 0;
					//进行数据的更新
					for (Map<String, String> templateExtend : templateExtends) {
						String fdKey = templateExtend.get(PluginUtil.PARAM_KEY);
						String fdModelName =templateExtend.get(PluginUtil.PARAM_MODELNAME);
						if(StringUtil.isNull(fdKey) || StringUtil.isNull(fdModelName)) {
							continue;
						}
						List<SysFormDbTable> sysFormDbTables = sysFormDbTableService.getBaseDao().findList("sysFormDbTable.fdKey='"+fdKey+"' and sysFormDbTable.fdModelName='com.landray.kmss.hr.ratify.model.HrRatifyMain'", null);
						if(sysFormDbTables != null && !sysFormDbTables.isEmpty()) {
							for (SysFormDbTable sysFormDbTable : sysFormDbTables) {
								if(StringUtil.isNotNull(sysFormDbTable.getFdTable())) {
									List<String> ids = this.getHrRatifyMainIds(hrRatifyMainService, sysFormDbTable.getDocCreateTime());
									if(ids != null && !ids.isEmpty()) {
										String sql = "delete from " + sysFormDbTable.getFdTable();
										sql += " where " + HQLUtil.buildLogicIN("fd_id", ids);
										NativeQuery nativeQuery = sysFormDbTableService.getBaseDao().getHibernateSession().createNativeQuery(sql);
										nativeQuery.addSynchronizedQuerySpace(sysFormDbTable.getFdTable());
										int delCount = nativeQuery.executeUpdate();
										if(delCount > 0) {//有删除数据，需要记录对于的模板，用于在日志中提示用户进行历史数据映射恢复
											templateInfos.add(this.getTemplateInfo(sysFormDbTable));
										}
									}
								}
							}
						}
						NativeQuery nativeQuery = sysFormDbTableService.getBaseDao().getHibernateSession().createNativeQuery("update sys_xform_db_table set fd_model_name = '" + fdModelName + "' where fd_key='" + fdKey + "' and fd_model_name='com.landray.kmss.hr.ratify.model.HrRatifyMain'");
						nativeQuery.addSynchronizedQuerySpace("sys_xform_db_table");
						count += nativeQuery.executeUpdate();
					}
					if (logger.isDebugEnabled()) {
						logger.debug("成功处理[" + count + "]条数据。");
					}
				}
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移异常", e);
			isError = true;
		}
		String message = this.getMessageByTemplateInfos(templateInfos);
		if(isError) {
			if(StringUtil.isNotNull(message)) {
				message = "<span>"+ResourceUtil.getString("hr-ratify:hrRatifyTemplate.transfer.error")+"</span><br/>" + message;
			}else {
				message = "<span>"+ResourceUtil.getString("hr-ratify:hrRatifyTemplate.transfer.error")+"</span>";
			}
			return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_RESULT_ERROR,message);
		}
		return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_RESULT_INFO,message);
	}
	
	/**
	 * 获取提示信息
	 * @param templateInfos
	 * @return
	 */
	private String getMessageByTemplateInfos(List<Map<String,String>> templateInfos) {
		if(templateInfos == null || templateInfos.isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(ResourceUtil.getString("hr-ratify:hrRatifyTemplate.transfer.template"));
		for (Map<String, String> map : templateInfos) {
			buffer.append("<br/>");
			if(StringUtil.isNotNull(map.get("url"))) {
				String url = map.get("url");
				buffer.append("<a href='"+url+"' target='_blank'>");
				buffer.append(map.get("fdName"));
				buffer.append("</a>");
			}else {
				buffer.append("<span>");
				buffer.append(map.get("fdName"));
				buffer.append("</span>");
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 获取模板信息
	 * @param sysFormDbTable
	 * @return
	 */
	private Map<String,String> getTemplateInfo(SysFormDbTable sysFormDbTable){
		Map<String,String> info = new HashMap<String, String>();
		info.put("fdId", sysFormDbTable.getFdModelId());//模板ID
		info.put("fdName", sysFormDbTable.getFdFormName());//模板名称
		info.put("url", "");
		try {
			String templateClass = sysFormDbTable.getFdTemplateModel();
			if(StringUtil.isNull(templateClass)) {
				return info;
			}
			IBaseModel model = (IBaseModel) Class.forName(templateClass).newInstance();
			model.setFdId(sysFormDbTable.getFdModelId());
			String url = ModelUtil.getModelUrl(model);
			if(StringUtil.isNotNull(url)) {
				url = StringUtil.formatUrl(url);
				info.put("url", url);
			}
		} catch (Exception e) {
			logger.error("获取模板链接异常",e);
		} 
		return info;
	}
	
	/**
	 * 获取在映射表建立前的数据，也就是历史文档
	 * @param hrRatifyMainService
	 * @param docCreateTime
	 * @return
	 * @throws Exception
	 */
	private List<String> getHrRatifyMainIds(IHrRatifyMainService hrRatifyMainService,Date docCreateTime) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("com.landray.kmss.hr.ratify.model.HrRatifyMain");
		hqlInfo.setWhereBlock("hrRatifyMain.docCreateTime < :docCreateTime");
		hqlInfo.setParameter("docCreateTime", docCreateTime);
		hqlInfo.setSelectBlock("hrRatifyMain.fdId");
		return hrRatifyMainService.findValue(hqlInfo);
	}
}
