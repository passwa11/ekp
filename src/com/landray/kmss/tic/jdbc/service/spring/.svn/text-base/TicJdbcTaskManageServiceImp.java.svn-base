package com.landray.kmss.tic.jdbc.service.spring;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.tic.jdbc.constant.TicJdbcConstant;
import com.landray.kmss.tic.jdbc.model.TicJdbcTaskManage;
import com.landray.kmss.tic.jdbc.service.ITicJdbcTaskManageService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;

/**
 * 
 * 
 * @author
 * @version 1.0 2013-07-24
 */
public class TicJdbcTaskManageServiceImp extends BaseServiceImp implements
		ITicJdbcTaskManageService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TicJdbcTaskManageServiceImp.class);
	private ISysQuartzJobService sysQuartzJobService;

	public void setSysQuartzJobService(ISysQuartzJobService sysQuartzJobService) {
		this.sysQuartzJobService = sysQuartzJobService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {

		String fdParameter = "{jdbcTaskQuartzId:'!{fdid}'}";
		IBaseModel model = convertFormToModel(form, null, requestContext);
		PropertyUtils.setSimpleProperty(model, "fdJobService",
				TicJdbcConstant.TICSYSSAP_SERVICEBEAN);
		PropertyUtils.setSimpleProperty(model, "fdJobMethod",
				TicJdbcConstant.TICSYSSAP_SERVICEMETHOD);

	
		boolean enabled = ((TicJdbcTaskManage) model).getFdIsEnabled();
		if (enabled) {
			IBaseModel destModel = copyQuartz(model, SysQuartzJob.class);
			PropertyUtils.setProperty(model, "fdQuartzEkp", destModel.getFdId());
			PropertyUtils.setProperty(destModel, "fdParameter", fdParameter.replace("!{fdid}", model.getFdId()));
			String sysQuartzId = sysQuartzJobService.add(destModel);
			//System.out.println(sysQuartzId);
		}
		return add(model);
	}
	
	@Override
    public void updateChgEnabled(String[] ids, boolean isEnable)
			throws Exception {
		List<TicJdbcTaskManage> jobList = (List<TicJdbcTaskManage>) findByPrimaryKeys(ids);
		String[] ekpIds = new String[jobList.size()];
		if (ekpIds.length > 0) {
			for (int i = 0, size = jobList.size(); i < size; i++) {
				
				TicJdbcTaskManage model = jobList.get(i);
				model.setFdIsEnabled(isEnable);
				updateWithModel(model);
			}
		}
	}

	public void updateWithModel(TicJdbcTaskManage modelObj) throws Exception {
		String fdParameter = "{jdbcTaskQuartzId:'!{fdid}'}";
		String quartzEkpID = (String) PropertyUtils.getProperty(modelObj,
				"fdQuartzEkp");

		SysQuartzJob sysQuartzJob = (SysQuartzJob) sysQuartzJobService
				.findByPrimaryKey(quartzEkpID);
		
		sysQuartzJob = (SysQuartzJob) copyQuartz(modelObj, SysQuartzJob.class);
		sysQuartzJob.setFdId(quartzEkpID);

		
		modelObj.setFdQuartzEkp(sysQuartzJob.getFdId());
		modelObj.setFdParameter(fdParameter.replace("!{fdid}", modelObj.getFdId()));
		sysQuartzJob.setFdParameter(fdParameter.replace("!{fdid}", modelObj.getFdId()));

		
		if (!isEmptyModel(sysQuartzJob)) {
		
			sysQuartzJobService.update(sysQuartzJob);
		}
		super.update(modelObj);

	}
	
	
	public boolean isEmptyModel(IBaseModel model) {
		try {
			String fdId = model.getFdId();
			if (!StringUtil.isNull(fdId)) {
				return false;
			}
		} catch (Exception e) {

		}
		return true;
	}
	
	/**
	 * 
	 * 
	 * @param orgObject
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private IBaseModel copyQuartz(IBaseModel orgObject, Class clazz)
			throws Exception {
		Object destObject = clazz.newInstance();
		if (destObject instanceof IBaseModel) {
			PropertyUtils.copyProperties(destObject, orgObject);
			//
			TicJdbcTaskManage ticJdbcTaskManage=(TicJdbcTaskManage)orgObject;
			
			SysQuartzJob sysQuartzJob=(SysQuartzJob)destObject;
			sysQuartzJob.setFdEnabled(ticJdbcTaskManage.getFdIsEnabled());
			sysQuartzJob.setFdRequired(ticJdbcTaskManage.getFdIsRequired());
			
			PropertyUtils.setSimpleProperty(sysQuartzJob, "fdId", IDGenerator
					.generateID());
		}
		return (IBaseModel) destObject;
	}
	
	@Override
	public void delete(IBaseModel modelObj) throws Exception {

		String quartzEkpID = (String) PropertyUtils.getProperty(modelObj,"fdQuartzEkp");
		IBaseModel quartzModel = sysQuartzJobService.findByPrimaryKey(quartzEkpID, null, true);
		if (!isEmptyModel(quartzModel)) {
			sysQuartzJobService.delete(quartzModel);
		}
		super.delete(modelObj);
	}

}
