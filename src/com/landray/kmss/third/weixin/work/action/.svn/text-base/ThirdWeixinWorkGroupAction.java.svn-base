package com.landray.kmss.third.weixin.work.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCurrentInfoService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinWorkGroupForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkGroup;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkGroupService;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.opensymphony.util.BeanUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThirdWeixinWorkGroupAction extends ExtendAction {

	private static final Log logger = LogFactory
			.getLog(ThirdWeixinWorkGroupAction.class);
    private IThirdWeixinWorkGroupService thirdWeixinWorkGroupService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinWorkGroupService == null) {
            thirdWeixinWorkGroupService = (IThirdWeixinWorkGroupService) getBean("thirdWeixinWorkGroupService");
        }
        return thirdWeixinWorkGroupService;
    }

	private static ISysOrgCoreService sysOrgCoreService;

	private static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
                    .getBean("sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinWorkGroup.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil
				.buildHqlInfoDate(hqlInfo, request,
						com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkGroup.class);
		com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil
				.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinWorkGroupForm thirdWeixinWorkGroupForm = (ThirdWeixinWorkGroupForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinWorkGroupService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinWorkGroupForm;
    }

	/**
	 * <p>
	 * 创建群聊
	 * </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 陈火旺
	 */
	public void createGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		try {
			logger.debug("-------------准备创建微信群-----------");
			String userIds = request.getParameter("userIds");
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			String groupID = null, msg = null;
			if (StringUtil.isNull(userIds)) {
				throw new Exception("群成员id不能为空！");
			}
			if (userIds.split(",").length <= 1) {
				msg = "组建微聊人数必须大于一人！";
			} else {
				synchronized (this) {
					groupID = checkGroup(fdId, modelName, request);
					if (StringUtil.isNull(groupID)) {
						//创建群组
						groupID = ((IThirdWeixinWorkGroupService) getServiceImp(
								request)).addGroup(userIds, fdId, modelName);
					}
				}
			}
			json.put("chatId", groupID);
			json.put("corpId", WeixinWorkConfig.newInstance().getWxCorpid());
			json.put("msg", msg);

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
			response.setCharacterEncoding("UTF-8");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 判断群组是否已经存在
	 * @param fdId
	 * @param modelName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String checkGroup(String fdId, String modelName,
			HttpServletRequest request) throws Exception {
		String groupID = null;
		String userId = UserUtil.getUser().getFdId();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdGroupId");
		hqlInfo.setWhereBlock(
				"fdModelId = :fdId and fdModelName = :modelName");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setParameter("modelName", modelName);
		String fdGroupId = (String) ((IThirdWeixinWorkGroupService) getServiceImp(
				request)).findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdGroupId)) {
			//更新群组成员
			groupID = ((IThirdWeixinWorkGroupService) getServiceImp(request))
					.checkGroupByUserId(fdGroupId, userId);
			if (null == groupID) {
				//如果微信中不存在该群组，则删除对应的映射记录
				((IThirdWeixinWorkGroupService) getServiceImp(request))
						.deleteByGroupId(fdGroupId);
			}
		}
		return groupID;
	}

	/**
	 * <p>
	 * 检查企业微信群是否存在,如果存在,检查当前用户是否在群中，如果不在，则添加
	 * </p>
	 * 
	 * @return
	 * @author 陈火旺
	 * @throws IOException
	 */
	public void checkGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		try {
			String groupID = null;
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			groupID = checkGroup(fdId, modelName, request);
			json.put("corpId", WeixinWorkConfig.newInstance().getWxCorpid());
			json.put("groupID", groupID);
			if (UserOperHelper.allowLogOper("checkGroup",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(json.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			json.put("errorMsg",e.getMessage());
		}
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();

	}

	/**
	 * <p>
	 * 获取创建企业微信群聊所需参数
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @author 陈火旺
	 */
	public void getCreateInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			// 获取文档标题
			SysDictModel sysDictModel = SysDataDict.getInstance()
					.getModel(modelName);
			IBaseService baseService = (IBaseService) SpringBeanUtil
					.getBean(sysDictModel.getServiceBean());
			IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
			String fdName = SysDataDict.getInstance().getModel(modelName)
					.getDisplayProperty();
			String docValue = (String) BeanUtils.getValue(baseModel, fdName);
			List<String> userList = ((ILbpmProcessCurrentInfoService) SpringBeanUtil
					.getBean("lbpmProcessCurrentInfoService"))
							.getAllHandlersLoginNames(fdId);
			List<String> wxUserId = new ArrayList<String>();
			if (null != userList && userList.size() > 0) {
				for (String user : userList) {
					SysOrgPerson person = getSysOrgCoreService()
							.findByLoginName(user);
					String d_id = getWxWorkUserId(person.getFdId());
					if (StringUtil.isNotNull(d_id)) {
						wxUserId.add(d_id);
					} else {
						logger.warn("微聊默认带出人员" + person.getFdName()
								+ " 在企业微信对照表信息不存在，请检查！ekpId:"
								+ person.getFdId());
					}
				}
			}
			json.put("corpId", WeixinWorkConfig.newInstance().getWxCorpid());
			json.put("appId", WeixinWorkConfig.newInstance().getWxAgentid());
			json.put("groupName", docValue);
			json.put("userList", wxUserId.toArray(new String[0]));
			logger.debug("微聊默认带出的参与人员：" + wxUserId);
			response.setCharacterEncoding("UTF-8");
			if (UserOperHelper.allowLogOper("getCreateInfo",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(json.toString());
			}
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private String getWxWorkUserId(String userId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdEkpId = :fdEkpId");
		hqlInfo.setParameter("fdEkpId", userId);
		IWxworkOmsRelationService omsRelationService = (IWxworkOmsRelationService) SpringBeanUtil
				.getBean("wxworkOmsRelationService");
		String fdAppPkId = (String) omsRelationService.findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdAppPkId)) {
			return fdAppPkId;
		} else {
			logger.error("用户不存在企业微信中！fdId:" + userId);
		}
		return null;
	}
}
