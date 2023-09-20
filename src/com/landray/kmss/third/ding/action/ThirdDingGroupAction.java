package com.landray.kmss.third.ding.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCurrentInfoService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ding.forms.ThirdDingGroupForm;
import com.landray.kmss.third.ding.model.ThirdDingGroup;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingGroupService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ThirdDingGroupAction extends ExtendAction {

	private static final Logger logger = LoggerFactory.getLogger(ThirdDingGroupAction.class);

	private IThirdDingGroupService thirdDingGroupService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdDingGroupService == null) {
			thirdDingGroupService = (IThirdDingGroupService) getBean("thirdDingGroupService");
		}
		return thirdDingGroupService;
	}

	private static ISysOrgCoreService sysOrgCoreService;

	private static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

	private ISysOrgElementService sysOrgElementService;

	private ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}
	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingGroup.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
		ThirdDingGroupForm thirdDingGroupForm = (ThirdDingGroupForm) super.createNewForm(mapping, form, request,
				response);
		((IThirdDingGroupService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		return thirdDingGroupForm;
	}

	/**
	 * <p>创建群聊</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 孙佳
	 */
	public void createGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			String userIds = request.getParameter("userIds");
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			String groupID = null, msg = null;
			if (StringUtil.isNull(userIds)) {
				throw new Exception("群成员id不能为空！");
			}
			if (userIds.split(",").length <= 1) {
				msg = "组建钉聊人数必须大于一人！";
			} else {
				synchronized (this) {
					groupID = checkGroup(fdId, modelName, request);
					if (StringUtil.isNull(groupID)) {
						groupID = ((IThirdDingGroupService) getServiceImp(request)).addGroup(userIds,null, fdId, modelName);
					}
				}
			}
			json.put("chatId", groupID);
			json.put("corpId", DingUtil.getCorpId());
			json.put("msg", msg);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String checkGroup(String fdId, String modelName, HttpServletRequest request) throws Exception {
		String groupID = null;
		String userId = UserUtil.getUser().getFdId();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdGroupId");
		hqlInfo.setWhereBlock("thirdDingGroup.fdModelId = :fdId and thirdDingGroup.fdModelName = :modelName");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setParameter("modelName", modelName);
		String fdGroupId = (String) (getServiceImp(request)).findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdGroupId)) {
			groupID = ((IThirdDingGroupService) getServiceImp(request)).checkGroupByUserId(fdGroupId, userId);
			if (null == groupID) {
				((IThirdDingGroupService) getServiceImp(request)).deleteByGroupId(fdGroupId);
			}
		}
		return groupID;
	}


	/**
	 * <p>检查钉钉群是否存在,如果存在,检查当前用户是否在群中，如果不在，则添加</p>
	 * @return
	 * @author 孙佳
	 * @throws IOException 
	 */
	public void checkGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			String groupID = null;
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			groupID = checkGroup(fdId, modelName, request);
			json.put("corpId", DingUtil.getCorpId());
			json.put("groupID", groupID);
			response.setCharacterEncoding("UTF-8");
			if (UserOperHelper.allowLogOper("checkGroup",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(json.toString());
			}
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <p>获取创建钉钉群聊所需参数</p>
	 * @param request
	 * @param response
	 * @author 孙佳
	 */
	public void getCreateInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			//获取文档标题
			SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
			IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(sysDictModel.getServiceBean());
			IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
			String fdName = SysDataDict.getInstance().getModel(modelName).getDisplayProperty();
			String docValue = ModelUtil.getModelPropertyString(baseModel, fdName, null, null);
			//获取处理人
			ILbpmProcessCurrentInfoService lbpmProcessCurrentInfoService =(ILbpmProcessCurrentInfoService) SpringBeanUtil.getBean("lbpmProcessCurrentInfoService");
			//List<String> userList = lbpmProcessCurrentInfoService.getAllHandlersLoginNames(fdId);
			List<String> userList = lbpmProcessCurrentInfoService.getAllAndPostHandlersLoginNames(fdId);
			Set<String> dingUserId = new HashSet<String>();
			if (null != userList && userList.size() > 0) {
				for (String user : userList) {
					SysOrgPerson person = getSysOrgCoreService().findByLoginName(user);
					if (person == null) {
						// 人员禁用
						continue;
					}
					String d_id = getDingUserId(person.getFdId());
					if (StringUtil.isNotNull(d_id)) {
						dingUserId.add(d_id);
					} else {
						logger.warn("钉聊默认带出人员" + person.getFdName()
								+ " 在钉钉对照表信息不存在，请检查！ekpId:" + person.getFdId());
					}
				}
			}

			//获取抄送人
			boolean containDeptFlag = false;
			//是否过滤部门下的人员开关
			ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
					.getBean("sysAppConfigService");
			Map configMap = sysAppConfigService.findByKey(
					"com.landray.kmss.km.cogroup.model.GroupConfig");
			if (configMap != null && configMap.containsKey("dingCogroupContainDept")) {
				String dingCogroupContainDept = (String) configMap.get("dingCogroupContainDept");
				if (StringUtil.isNotNull(dingCogroupContainDept) && "true".equalsIgnoreCase(dingCogroupContainDept)) {
					containDeptFlag=true;
				}
			}
			Map<String, List<String>> sendNodeMap =  lbpmProcessCurrentInfoService.getSendNodeHandlersLoginNames(fdId,true);
			List<String> personsIds =null;
			List<String> postList =null;
			List<String> depList =null;
			List<String> groupList =null;
			List<String> orgList =null;

			if(sendNodeMap!=null&&!sendNodeMap.isEmpty()){
				  //解析抄送节点为人的情况
				  if(sendNodeMap.containsKey("personList")){
					 personsIds = sendNodeMap.get("personList");
				  }
				 //岗位的情况
			  	 if(sendNodeMap.containsKey("postList")) {
					postList = sendNodeMap.get("postList");
				 }

				//部门的情况
				if(sendNodeMap.containsKey("depList")) {
					depList = sendNodeMap.get("depList");
				}

				//机构的情况
				if(sendNodeMap.containsKey("orgList")) {
					orgList = sendNodeMap.get("orgList");
				}

				//群组
				if(sendNodeMap.containsKey("groupList")) {
					groupList = sendNodeMap.get("groupList");
				}
				if(groupList!=null&&!groupList.isEmpty()){
					for (String groupId : groupList) {
						SysOrgGroup group = (SysOrgGroup) getSysOrgCoreService().findByPrimaryKey(groupId);
						if(group!=null){
							List<SysOrgElement> elements = group.getFdMembers();
							if(elements!=null && elements.size()>0){
								for (SysOrgElement element : elements) {
									if(SysOrgConstant.ORG_TYPE_DEPT == element.getFdOrgType()){
										if(depList==null){
											depList = new ArrayList<String>();
										}
										depList.add(element.getFdId());
									}else if(SysOrgConstant.ORG_TYPE_POST == element.getFdOrgType()){
										if(postList==null){
											postList = new ArrayList<String>();
										}
										postList.add(element.getFdId());
									}else if(SysOrgConstant.ORG_TYPE_PERSON == element.getFdOrgType()){
										if(personsIds==null){
											personsIds = new ArrayList<String>();
										}
										personsIds.add(element.getFdId());
									}else if(SysOrgConstant.ORG_TYPE_ORG == element.getFdOrgType()){
										if(orgList==null){
											orgList = new ArrayList<String>();
										}
										orgList.add(element.getFdId());
									}

								}
							}
						}
					}

				}
				  if(personsIds!=null&&!personsIds.isEmpty()){
					  for (String userId : personsIds) {
						  String d_id = getDingUserId(userId);
						  if (StringUtil.isNotNull(d_id)) {
							  logger.debug("【抄送节点--人员】对应上了："+d_id);
							  dingUserId.add(d_id);
						  } else {
							  logger.warn("【抄送节点】钉聊默认带出人员"
									  + " 在钉钉对照表信息不存在，请检查！ekpId:" + userId);
						  }

					  }

				  }

				if(postList!=null&&!postList.isEmpty()){
					for (String pid : postList) {
						SysOrgPost post = (SysOrgPost) getSysOrgCoreService().findByPrimaryKey(pid);
						if(post!=null&&post.getFdPersons()!=null&&post.getFdPersons().size()>0){
							List<SysOrgPerson> personsList = post.getFdPersons();
							for(SysOrgPerson per:personsList){
								String d_id = getDingUserId(per.getFdId());
								if (StringUtil.isNotNull(d_id)) {
									logger.debug("【抄送节点--岗位】对应上了："+d_id);
									dingUserId.add(d_id);
								} else {
									logger.warn("【抄送节点】钉聊默认带出人员"
											+ " 在钉钉对照表信息不存在，请检查！ekpId:" + per.getFdId());
								}
							}

						}
					}

				}

				//将机构id合并到部门id中，统一处理
				if(orgList!=null&&orgList.size()>0){
					if(depList==null){
						depList = new ArrayList<String>();
					}
					depList.addAll(orgList);
				}

				if(depList!=null&&!depList.isEmpty()) {
					if(containDeptFlag){
						for (String deptId : depList) {
							SysOrgElement dept = (SysOrgElement) getSysOrgCoreService().findByPrimaryKey(deptId);
							List<SysOrgElement> childrenList = getSysOrgCoreService().findAllChildren(dept, SysOrgConstant.ORG_TYPE_PERSON);
							if(childrenList!=null&&childrenList.size()>0){
								for (SysOrgElement element : childrenList) {
									String d_id = getDingUserId(element.getFdId());
									if (StringUtil.isNotNull(d_id)) {
										logger.debug("【抄送节点--部门人员】对应上了："+d_id);
										dingUserId.add(d_id);
									} else {
										logger.warn("【抄送节点】钉聊默认带出人员"
												+ " 在钉钉对照表信息不存在，请检查！ekpId:" + element.getFdId());
									}
								}
							}

						}
					}else{
						//只是获取当前部门或机构的人员
						for (String deptId : depList) {
							SysOrgElement dept = (SysOrgElement) getSysOrgCoreService().findByPrimaryKey(deptId);
							HQLInfo hqlInfo = new HQLInfo();
							hqlInfo.setWhereBlock("sysOrgElement.fdOrgType=:type and sysOrgElement.hbmParent.fdId =:pid");
							hqlInfo.setParameter("type",8);
							hqlInfo.setParameter("pid",deptId);
							List<SysOrgElement> childrenList = getSysOrgElementService().findList(hqlInfo);
							if(childrenList!=null&&childrenList.size()>0){
								for (SysOrgElement element : childrenList) {
									String d_id = getDingUserId(element.getFdId());
									if (StringUtil.isNotNull(d_id)) {
										logger.debug("【抄送节点--当前部门人员】对应上了："+d_id);
										dingUserId.add(d_id);
									} else {
										logger.warn("【抄送节点】钉聊默认带出人员"
												+ " 在钉钉对照表信息不存在，请检查！ekpId:" + element.getFdId());
									}
								}
							}

						}
					}
				}
			}
			json.put("corpId", DingUtil.getCorpId());
			json.put("appId", DingUtil.getAgentIdByCorpId(null));
			json.put("groupName", docValue);
			json.put("userList", dingUserId.toArray(new String[0]));
			logger.debug("钉聊默认带出的参与人员：" + dingUserId);
			response.setCharacterEncoding("UTF-8");
			if (UserOperHelper.allowLogOper("getCreateInfo",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(json.toString());
			}
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn(e.getMessage(),e);
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
