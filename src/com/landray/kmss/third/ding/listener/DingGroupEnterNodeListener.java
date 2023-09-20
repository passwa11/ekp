package com.landray.kmss.third.ding.listener;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.lbpm.engine.builder.ExtAttribute;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCurrentInfoService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ding.model.ThirdDingGroup;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingGroupService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/***
 *  群聊自动加群的监听
 *
 */
public class DingGroupEnterNodeListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingGroupEnterNodeListener.class);

	public DingGroupEnterNodeListener(IOmsRelationService omsRelationService) {
		super();
		this.omsRelationService = omsRelationService;
	}

	public DingGroupEnterNodeListener() {
		super();
	}

	private IOmsRelationService omsRelationService;

	public IOmsRelationService getOmsRelationService() {
		return omsRelationService;
	}

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	private IThirdDingGroupService thirdDingGroupService;

	public IThirdDingGroupService getThirdDingGroupService() {
		if (thirdDingGroupService == null) {
			thirdDingGroupService = (IThirdDingGroupService) SpringBeanUtil.getBean("thirdDingGroupService");
		}
		return thirdDingGroupService;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		}
		return sysOrgElementService;

	}

	private static ISysOrgCoreService sysOrgCoreService;

	private static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

	/**
	 * 审批节点进入事件
	 */
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		try {
            logger.debug("-----新的审批节点进入了------------"+parameter);
			if(execution==null) {
                return;
            }
			IBaseModel baseModel = execution.getMainModel();
			if(baseModel==null) {
                return;
            }

			//钉聊总开关
			ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
					.getBean("sysAppConfigService");
			Map groupMap = sysAppConfigService.findByKey("com.landray.kmss.km.cogroup.model.GroupConfig");
			if (groupMap == null || !groupMap.containsKey("dingCogroupEnable") || !"true".equalsIgnoreCase((String)groupMap.get("dingCogroupEnable"))) {
                 return;  //未开启群协助
			}

			//获取扩展属性
			List<ExtAttribute> extAttributes = execution.getNodeDefinition().getExtAttributes();
			//获取自动建群参数
			boolean autoCreateGroup = false;
			if(extAttributes!=null&&extAttributes.size()>0){
				for (int i=0;i<extAttributes.size();i++){
					ExtAttribute extAttribute = extAttributes.get(i);
					if("autoCreateDingGroup".equals(extAttribute.getName())){
						if("true".equalsIgnoreCase(extAttribute.getValue())){
							autoCreateGroup = true;
						}
						break;
					}
				}
			}

			SysOrgElement element = execution.getWorkitem().getFdExpecter();
			logger.debug("********预计处理人："+element.getFdName()+"  fdId:"+element.getFdId()+" 自动建群："+autoCreateGroup);

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdModelId=:fdModelId");
			hqlInfo.setParameter("fdModelId",baseModel.getFdId());

			ThirdDingGroup thirdDingGroup = (ThirdDingGroup) getThirdDingGroupService().findFirstOne(hqlInfo);
			if(thirdDingGroup != null){
				if (element != null && groupMap != null && groupMap.containsKey("autoEnterGroup")) {
					String autoEnterGroup = (String) groupMap.get("autoEnterGroup");
					if (StringUtil.isNotNull(autoEnterGroup) && "true".equalsIgnoreCase(autoEnterGroup)) {
						logger.debug("-------------开启自动进群-------------");
						logger.debug("有群的记录:"+thirdDingGroup.getFdId()+"  ding:"+thirdDingGroup.getFdGroupId());
						String ding_id = getDingUserId(element.getFdId());
						if(StringUtil.isNotNull(ding_id)){
							getThirdDingGroupService().addUser2Group(thirdDingGroup.getFdGroupId(),ding_id);
						}else{
							logger.warn("没有找到人员："+element.getFdName()+" 相关的对照表记录,自动入群忽略");
						}
					}
				}
			}else{
				logger.debug("没有ThirdDingGroup记录："+baseModel.getFdId()+ " element:"+element);
				if(autoCreateGroup){
					logger.debug("--------准备自动建群-----------");
					autoCreateGroup(baseModel);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void autoCreateGroup(IBaseModel baseModel) {
		try {
			String modelName = baseModel.getClass().getName();
			logger.debug("模块："+modelName);

			//获取处理人
			ILbpmProcessCurrentInfoService lbpmProcessCurrentInfoService =(ILbpmProcessCurrentInfoService) SpringBeanUtil.getBean("lbpmProcessCurrentInfoService");
			List<String> userList = lbpmProcessCurrentInfoService.getAllAndPostHandlersLoginNames(baseModel.getFdId());
			String users = "";
			if (null != userList && userList.size() > 0) {
				for (String user : userList) {
					SysOrgPerson person = getSysOrgCoreService().findByLoginName(user);
					String d_id = getDingUserId(person.getFdId());
					if (StringUtil.isNotNull(d_id)) {
						if(StringUtil.isNull(users)){
							users=d_id;
						}else {
							users+=","+d_id;
						}
					} else {
						logger.warn("钉聊默认带出人员" + person.getFdName()
								+ " 在钉钉对照表信息不存在，请检查！ekpId:" + person.getFdId());
					}
				}
			}
			logger.debug("user:"+users);

			//获取创建人，作为群主
			Class clazz = baseModel.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method.invoke(baseModel);
			String creatorUserId = getDingUserId(docCreator.getFdId());
			if (StringUtil.isNull(creatorUserId)){
				//取第一个人作为群主
				creatorUserId = users.split(",")[0];
			}
			getThirdDingGroupService().addGroup(users,creatorUserId,baseModel.getFdId(),modelName);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	private String getDingUserId(String userId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("omsRelationModel.fdEkpId = :fdEkpId");
		hqlInfo.setParameter("fdEkpId", userId);
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		return (String) omsRelationService.findFirstOne(hqlInfo);
	}
}
