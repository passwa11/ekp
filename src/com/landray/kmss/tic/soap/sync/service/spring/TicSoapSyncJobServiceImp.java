package com.landray.kmss.tic.soap.sync.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.tic.soap.constant.QuartzCfg;
import com.landray.kmss.tic.soap.sync.model.ClocalVo;
import com.landray.kmss.tic.soap.sync.model.TicSoapSyncJob;
import com.landray.kmss.tic.soap.sync.model.TicSoapSyncTempFunc;
import com.landray.kmss.tic.soap.sync.service.ITicSoapSyncJobService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 定时任务业务接口实现
 * 
 * @author
 * @version 1.0 2014-02-20
 */
public class TicSoapSyncJobServiceImp extends BaseServiceImp implements
		ITicSoapSyncJobService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TicSoapSyncJobServiceImp.class);
	private ISysQuartzJobService sysQuartzJobService;

	public void setSysQuartzJobService(ISysQuartzJobService sysQuartzJobService) {
		this.sysQuartzJobService = sysQuartzJobService;
	}

	@Override
    public IBaseModel findByEkpQuartzID(String ekpQuartzID) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdQuartzEkp=:fdQuartzEkp");
		hqlInfo.setParameter("fdQuartzEkp", ekpQuartzID);
		return (IBaseModel) findFirstOne(hqlInfo);
	}

	@Override
    public void updateAfterRunJob(String quartzEkpID) throws Exception {
		IBaseModel ticSoapSync = findByEkpQuartzID(quartzEkpID);
		if (ticSoapSync != null) {
			IBaseModel ekpQuartz = sysQuartzJobService
					.findByPrimaryKey(quartzEkpID);
			popModel(ekpQuartz, ticSoapSync, new String[] { "fdId" });
			super.update(ticSoapSync);
		}

	}

	@Override
    public void updateEnableTicSoapSync(String[] ids, Boolean fenable)
			throws Exception {
		if (ids.length > 0) {
			String hqlUpdate = "update "
					+ this.getModelName()
					+ " as temp set temp.fdEnabled =:fenable where temp.fdQuartzEkp in ("
					+ idsString(ids) + ")";
			this.getBaseDao().getHibernateSession().createQuery(hqlUpdate)
					.setParameter("fenable", fenable).executeUpdate();
		}
	}

	private String idsString(String[] ids) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = ids.length; i < len; i++) {
			sb.append("'");
			sb.append(ids[i]);
			sb.append("'");
			if (i < len - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * soap定时任务添加操作
	 */
	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {

		String fdParameter = "{soapQuartzId:'!{fdid}'}";
		IBaseModel model = convertFormToModel(form, null, requestContext);
		PropertyUtils.setSimpleProperty(model, "fdJobService",
				QuartzCfg.TICSYSSOAP_SERVICEBEAN);
		PropertyUtils.setSimpleProperty(model, "fdJobMethod",
				QuartzCfg.TICSYSSOAP_SERVICEMETHOD);

		// 添加的时候判断激活状态再往定时任务放
		boolean enabled = ((TicSoapSyncJob) model).getFdEnabled();
		if (enabled) {
			IBaseModel destModel = copyQuartz(model, SysQuartzJob.class);
			PropertyUtils
					.setProperty(model, "fdQuartzEkp", destModel.getFdId());
			PropertyUtils.setProperty(destModel, "fdParameter", fdParameter
					.replace("!{fdid}", model.getFdId()));
			String sysQuartzId = sysQuartzJobService.add(destModel);
			System.out.println(sysQuartzId);
		}
		return add(model);
	}

	public void updateWithModel(TicSoapSyncJob modelObj) throws Exception {

		String fdParameter = "{soapQuartzId:'!{fdid}'}";
		String quartzEkpID = (String) PropertyUtils.getProperty(modelObj,
				"fdQuartzEkp");

		SysQuartzJob sysQuartzJob = (SysQuartzJob) sysQuartzJobService
				.findByPrimaryKey(quartzEkpID);
		// 如果不存在 sys_quartz中却是启用状态，构建新的model
		sysQuartzJob = (SysQuartzJob) copyQuartz(modelObj, SysQuartzJob.class);
		sysQuartzJob.setFdId(quartzEkpID);

		// 重新建立关联
		modelObj.setFdQuartzEkp(sysQuartzJob.getFdId());
		modelObj.setFdParameter(fdParameter.replace("!{fdid}", modelObj
				.getFdId()));
		sysQuartzJob.setFdParameter(fdParameter.replace("!{fdid}", modelObj
				.getFdId()));

		// 更新sys_quartz
		if (!isEmptyModel(sysQuartzJob)) {
			// sysQuartzJobService 是saveorupdate 所以如果为空也会存在
			sysQuartzJobService.update(sysQuartzJob);
		}
		super.update(modelObj);

	}

	/**
	 * 是否为空model
	 * 
	 * @param model
	 * @return
	 */
	public boolean isEmptyModel(IBaseModel model) {
		try {
			String fdId = model.getFdId();
			if (!StringUtil.isNull(fdId)) {
				return false;
			}
		} catch (/* ObjectNotFoundException */Exception e) {
			logger.debug("检查到是空数据");
		}
		return true;
	}

	/**
	 * soap定时任务删除操作
	 */
	@Override
	public void delete(IBaseModel modelObj) throws Exception {

		String quartzEkpID = (String) PropertyUtils.getProperty(modelObj,
				"fdQuartzEkp");
		IBaseModel quartzModel = sysQuartzJobService.findByPrimaryKey(
				quartzEkpID, null, true);
		if (!isEmptyModel(quartzModel)) {
			sysQuartzJobService.delete(quartzModel);
		}
		super.delete(modelObj);
	}

	@Override
    public void updateChgEnabled(String[] ids, boolean isEnable)
			throws Exception {
		List<TicSoapSyncJob> jobList = (List<TicSoapSyncJob>) findByPrimaryKeys(ids);
		String[] ekpIds = new String[jobList.size()];
		if (ekpIds.length > 0) {
			for (int i = 0, size = jobList.size(); i < size; i++) {
				// 定时任务数量一般不多检查
				TicSoapSyncJob model = jobList.get(i);
				model.setFdEnabled(isEnable);
				updateWithModel(model);
			}
		}
	}

	/**
	 * soap定时任务更新操作
	 */
	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		TicSoapSyncJob modelObj = (TicSoapSyncJob) convertFormToModel(form,
				null, requestContext);
		updateWithModel(modelObj);
	};

	/**
	 * 把orgModel 数据 targetModel ,filter 字段数据排除 hibernate model
	 * 有session管理，fdid更改了会导致update 不成功所以需要这个转换函数
	 * 
	 * @param orgModel
	 * @param targetModel
	 * @param filter
	 * @throws Exception
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void popModel(IBaseModel orgModel, IBaseModel targetModel,
			String[] filter) throws Exception, InvocationTargetException,
			NoSuchMethodException {
		for (Field o_field : orgModel.getClass().getDeclaredFields()) {
			for (Field t_fieField : targetModel.getClass().getDeclaredFields()) {
				if (!check(t_fieField.getName(), filter)
						&& o_field.getName().equals(t_fieField.getName())
						&& o_field.getType().getName().equals(
								t_fieField.getType().getName())) {
					Object value = PropertyUtils.getProperty(orgModel, o_field
							.getName());
					PropertyUtils.setProperty(targetModel,
							t_fieField.getName(), value);
				}
			}
		}
	}

	/**
	 * 校验字段是否属于过滤字段
	 */
	public boolean check(String fieldName, String[] filterName) {
		if (filterName == null) {
			return false;
		}
		for (String filter : filterName) {
			if (fieldName.equals(filter)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 把orgObjet 数据copy出来并且创建一个新对象,用来做save操作
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
			PropertyUtils.setSimpleProperty(destObject, "fdId", IDGenerator
					.generateID());
		}
		return (IBaseModel) destObject;
	}

	@Override
    public Set findTableByQuartzId(String quartzId) throws Exception {
		TicSoapSyncJob ticSoapSyncJob = (TicSoapSyncJob) findByPrimaryKey(quartzId);
		Set<ClocalVo> tableSet = new HashSet<ClocalVo>();
		if (ticSoapSyncJob != null) {
			List<TicSoapSyncTempFunc> tempFuncList = ticSoapSyncJob
					.getFdSoapInfo();
			for (TicSoapSyncTempFunc ticSoapSyncTempFunc : tempFuncList) {
				String mappingXml = ticSoapSyncTempFunc.getFdSoapXml();
				if (StringUtil.isNotNull(mappingXml)) {
					Document resultDocument = DocumentHelper
							.parseText(mappingXml);
					List<Attribute> clocalList = com.landray.kmss.util.jdom.DocumentWrapper.selectNodes(resultDocument,"/web//@clocal");
					for (Attribute attribute : clocalList) {
						String attrVal = attribute.getValue();
						ClocalVo clocalvo = String2ClocalVo(attrVal);
						if (clocalvo != null) {
							clocalvo.setTempfuncId(ticSoapSyncTempFunc
									.getFdId());
							tableSet.add(clocalvo);
						}
					}
				}
			}
		}
		return tableSet;
	}

	/**
	 * 严格控制clocal格式， 1：dsID:tableName:timestampName;
	 * 
	 * @param clocal
	 * @return
	 */
	private ClocalVo String2ClocalVo(String clocal) {
		ClocalVo clocalvo = null;
		if (StringUtil.isNull(clocal)) {
			return null;
		}
		String[] clocalData = clocal.split(":");
		if (clocalData.length >= 3 && clocalData.length < 5) {
			clocalvo = new ClocalVo();
			clocalvo.setDsID(clocalData[1]);
			clocalvo.setTableName(clocalData[2]);
			clocalvo.setType(clocalData[0]);
			if (clocalData.length == 4) {
				clocalvo.setTimestampName(clocalData[3]);
			}
			return clocalvo;
		}
		return null;

	}

}
