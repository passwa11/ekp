package com.landray.kmss.third.weixin.work.sso;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.api.aes.WXBizMsgCrypt;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxDepart;
import com.landray.kmss.third.weixin.work.oms.SynchroOrgWxwork2Ekp;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkCallbackService;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkContactService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.XMLUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class WxCpEndpointAction extends BaseAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxCpEndpointAction.class);

	protected WxworkApiService wxworkApiService;

	private void init() {
		wxworkApiService = WxworkUtils.getWxworkApiService();
	}

	private SynchroOrgWxwork2Ekp synchroOrgWxwork2Ekp;

	private SynchroOrgWxwork2Ekp
			getSynchroOrgWxwork2Ekp() {
		if (synchroOrgWxwork2Ekp == null) {
			synchroOrgWxwork2Ekp = (SynchroOrgWxwork2Ekp) SpringBeanUtil
					.getBean("synchroOrgWxwork2Ekp");
		}
		return synchroOrgWxwork2Ekp;
	}

	private IThirdWeixinWorkCallbackService thirdWeixinWorkCallbackService;

	private IThirdWeixinWorkCallbackService
			getThirdWeixinWorkCallbackService() {
		if (thirdWeixinWorkCallbackService == null) {
			thirdWeixinWorkCallbackService = (IThirdWeixinWorkCallbackService) SpringBeanUtil
					.getBean("thirdWeixinWorkCallbackService");
		}
		return thirdWeixinWorkCallbackService;
	}

	private IThirdWeixinWorkContactService thirdWeixinWorkContactService;
	private IThirdWeixinWorkContactService getThirdWeixinWorkContactService() {
		if (thirdWeixinWorkContactService == null) {
			thirdWeixinWorkContactService = (IThirdWeixinWorkContactService) SpringBeanUtil
					.getBean("thirdWeixinWorkContactService");
		}
		return thirdWeixinWorkContactService;
	}

	public ActionForward service(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("-----------企业微信回调-----------");
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		init();
		String msgSignature = request.getParameter("msg_signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String echostr = request.getParameter("echostr");

		logger.debug("msgSignature:" + msgSignature + " nonce:" + nonce
				+ "  timestamp:" + timestamp + "  echostr:" + echostr);

		WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
		String sToken = weixinWorkConfig.getWxToken();
		String sCorpID = weixinWorkConfig.getWxCorpid();
		String sEncodingAESKey = weixinWorkConfig.getWxAeskey();
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey,
				sCorpID);

		// echostr不为空为验证请求，否则为实际业务请求
		if (StringUtil.isNotNull(echostr)) {
			String plainText; // 需要返回的明文
			try {
				plainText = wxcpt.VerifyURL(msgSignature, timestamp,
						nonce, echostr);
				logger.warn("verifyurl echostr: " + plainText);
				// 验证URL成功，将sEchoStr返回
				response.getWriter().println(plainText);
			} catch (Exception e) {
				// 验证URL失败，错误原因请查看异常
				logger.error(e.getMessage(), e);
				response.getWriter().println("非法请求");
			}
			return null;
		}

		// 判断是否需要处理回调
		if (isEnableCallback()) {
			handleCallback(request,wxcpt);
		} else {
			logger.debug("！！！没有启用回调功能");
		}
		return null;
	}

	private boolean isEnableCallback(){
		WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
		if(!"true".equals(weixinWorkConfig.getWxEnabled())){
			return false;
		}
		boolean orgCallback = "fromWx".equals(weixinWorkConfig.getSyncSelection());
		boolean contactCallback = "true".equals(weixinWorkConfig.getContactIntegrateEnable()) && "true".equals(weixinWorkConfig.getSyncContactToEkp());
		if(orgCallback || contactCallback){
			return true;
		}
		return false;
	}

	private void handleCallback(HttpServletRequest request,WXBizMsgCrypt wxcpt) throws IOException {
		// 接收post数据
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String str = sb.toString();
		String sReqData = str;
		String msgSignature = request.getParameter("msg_signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		try {
			String sMsg = wxcpt.DecryptMsg(msgSignature, timestamp, nonce,
					sReqData);
			logger.debug("after decrypt msg: " + sMsg);

			JSONObject plainTextJson = XMLUtil.convertXML2JSON(sMsg);
			logger.debug(plainTextJson.toString());
			String eventType = plainTextJson.getString("ChangeType");
			String event = plainTextJson.getString("Event");

			//前面已经判断了是否允许回调
			WeixinWorkConfig config = WeixinWorkConfig.newInstance();
			String wxOrgId2ekp = config.getWx2ekpWxRootId();
			if (StringUtil.isNotNull(wxOrgId2ekp) && "change_contact".equals(event)) {
				List<String> orgIdsArr = Arrays.asList(wxOrgId2ekp.split(";"));
				//企业微信的根部门为'1'
				if(!orgIdsArr.contains("1")){
					if("create_user".equals(eventType)
							|| "update_user".equals(eventType)
							|| "delete_user".equals(eventType)){
						if (!checkUserSyncScope(plainTextJson, orgIdsArr)) {
							return;
						}
					}
					else if("create_party".equals(eventType)
							|| "update_party".equals(eventType)
							|| "delete_party".equals(eventType)){
						if (!checkPartySyncScope(plainTextJson, orgIdsArr)) {
							return;
						}
					}
				}
			}

			String eventTime = "0";
			if (plainTextJson.containsKey("CreateTime")) {
				eventTime = plainTextJson.getString("CreateTime");
			} else if (plainTextJson.containsKey("TimeStamp")) {
				eventTime = plainTextJson.getString("TimeStamp");
			}
			ThirdWeixinWorkCallback info = new ThirdWeixinWorkCallback();
			info.setFdEventType(eventType);
			info.setFdEventTime(Long.parseLong(eventTime));
			info.setDocContent(plainTextJson.toString());
			info.setDocCreateTime(new Date());
			if("change_contact".equals(event)){
				handleOrgCallback(plainTextJson,info);
			}else if("change_external_contact".equals(event) || "change_external_tag".equals(event)){
				handleContactCallback(plainTextJson,info);
			}
			getThirdWeixinWorkCallbackService().add(info);

		} catch (Exception e) {
			logger.error("微信回调消息解密异常:" + e.getMessage(), e); // 解密失败，失败原因请查看异常
		}
	}

	/**
	 * 检查同步人员是否在范围内
	 * @param plainTextJson
	 * @param wxOrgId2ekps
	 * @return
	 * @throws Exception
	 */
	private boolean checkUserSyncScope(JSONObject plainTextJson, List<String> wxOrgId2ekps) throws Exception {
		//人员回调处理
		String deptId = null;
		if(plainTextJson.containsKey("MainDepartment")){
			deptId = plainTextJson.getString("MainDepartment");
		}
		if(StringUtil.isNull(deptId) && plainTextJson.containsKey("Department")){
			if(plainTextJson.get("Department") instanceof String){
				deptId = plainTextJson.getString("Department");
			}
			else if(plainTextJson.get("Department") instanceof net.sf.json.JSONArray){
				logger.warn("当前用户存在多个部门但并无主部门，企业微信又设置了企业微信同步根机构ID，不处理此情形！");
				return false;
			}
		}
		if(StringUtil.isNull(deptId)){
			logger.warn("在企业微信同步根机构ID范围外，不处理");
			return false;
		}
		return checkSyncScope(plainTextJson, wxOrgId2ekps, deptId);
	}

	/**
	 * 检查同步部门是否在范围内
	 * @param plainTextJson
	 * @param wxOrgId2ekps
	 * @return
	 * @throws Exception
	 */
	private boolean checkPartySyncScope(JSONObject plainTextJson,  List<String> wxOrgId2ekps) throws Exception {
		//部门回调处理
		String parentId = null;
		if(plainTextJson.containsKey("ParentId")){
			parentId = plainTextJson.getString("ParentId");
		}
		if(StringUtil.isNull(parentId)){
			//由于微信接口说明，ParentId只有发生变更时才回传，故需要通过api获取部门信息得到parentId
			List<WxDepart> departs = wxworkApiService.departGet(plainTextJson.getString("Id"));
			for (WxDepart d : departs) {
				if (d.getId().toString().equals(plainTextJson.getString("Id"))) {
					if(d.getParentid() != null){
						parentId = d.getParentid().toString();
					}
				}
			}
		}
		if(StringUtil.isNull(parentId)){
			logger.warn("在企业微信同步根机构ID范围外，不处理");
			return false;
		}
		return checkSyncScope(plainTextJson, wxOrgId2ekps, parentId);
	}

	/**
	 * 检查同步数据是否在范围内
	 * @param plainTextJson
	 * @param wxOrgId2ekps
	 * @param appPkId
	 * @return
	 * @throws Exception
	 */
	private boolean checkSyncScope(JSONObject plainTextJson, List<String> wxOrgId2ekps, String appPkId) throws Exception {
		IWxworkOmsRelationService wxworkOmsRelationService = (IWxworkOmsRelationService) SpringBeanUtil.getBean("wxworkOmsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("wxworkOmsRelationModel.fdAppPkId=:appPkId");
		hqlInfo.setParameter("appPkId", appPkId);
		WxworkOmsRelationModel departmentRelationModel = (WxworkOmsRelationModel) wxworkOmsRelationService.findFirstOne(hqlInfo);
		if (departmentRelationModel == null) {
			logger.warn("无法找到所属部门[fdAppPkId:"+appPkId+"]关系数据(可能所属部门在企业微信同步根机构ID范围外)，不处理!");
			return false;
		}
		hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("wxworkOmsRelationModel.fdAppPkId", wxOrgId2ekps));
		List<WxworkOmsRelationModel> rootDepartments = wxworkOmsRelationService.findList(hqlInfo);

		if (rootDepartments != null && rootDepartments.size() > 0) {
			ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
			SysOrgElement ekpDept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(departmentRelationModel.getFdEkpId());

			List<String> rootDepartmentIds = rootDepartments.stream().map(r -> r.getFdEkpId()).collect(Collectors.toList());
			List<SysOrgElement> rootEkpDepts = sysOrgElementService.findByPrimaryKeys(rootDepartmentIds.toArray(new String[rootDepartmentIds.size()]));
			boolean inScope = false;
			for (SysOrgElement root : rootEkpDepts) {
				if (ekpDept.getFdHierarchyId().startsWith(root.getFdHierarchyId())) {
					inScope = true;
					break;
				}
			}
			if (!inScope) {
				logger.warn("在企业微信同步根机构ID范围外，不处理!");
				return false;
			}
		} else {
			logger.warn("根据企业微信同步根机构ID，无法找到任何关系数据!");
		}
		return true;
	}

	private void handleOrgCallback(JSONObject plainTextJson, ThirdWeixinWorkCallback info){
		WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
		if(!"fromWx".equals(weixinWorkConfig.getSyncSelection())){
			logger.debug("没有启用组织回调");
			return;
		}
		Object lock = "lock";
		synchronized (lock) {
			String fdEventTypeTip = null;
			try {
				String eventType = plainTextJson.getString("ChangeType");
				if ("create_user".equals(eventType)) {// 创建人员
					fdEventTypeTip = "新增成员事件";
					getSynchroOrgWxwork2Ekp()
							.saveOrUpdateCallbackUser(plainTextJson, true);
				} else if ("update_user".equals(eventType)) { // 更新人员(禁用也是更新事件)
					fdEventTypeTip = "更新成员事件";
					getSynchroOrgWxwork2Ekp()
							.saveOrUpdateCallbackUser(plainTextJson, false);
				} else if ("delete_user".equals(eventType)) { // 删除人员
					fdEventTypeTip = "删除成员事件";
					getSynchroOrgWxwork2Ekp().deleteCallbackUser(
							plainTextJson.getString("UserID"));

				} else if ("create_party".equals(eventType)) { // 新增部门
					fdEventTypeTip = "新增部门事件";
					getSynchroOrgWxwork2Ekp()
							.saveOrUpdateCallbackDept(plainTextJson, true);
				} else if ("update_party".equals(eventType)) { // 更新部门
					fdEventTypeTip = "更新部门事件";
					getSynchroOrgWxwork2Ekp()
							.saveOrUpdateCallbackDept(plainTextJson, false);
				} else if ("delete_party".equals(eventType)) { // 删除部门
					fdEventTypeTip = "删除部门事件";
					getSynchroOrgWxwork2Ekp().deleteCallbackDept(
							Integer.valueOf(plainTextJson.getString("Id")));
				}
				info.setFdIsSuccess(true);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				info.setFdIsSuccess(false);
				info.setFdErrorInfo(e.getMessage());
			}
			info.setFdEventTypeTip(fdEventTypeTip);
		}
	}

	private void handleContactCallback(JSONObject plainTextJson, ThirdWeixinWorkCallback info){
		WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
		if(!"true".equals(weixinWorkConfig.getContactIntegrateEnable())){
			logger.debug("没有启用客户集成");
			return;
		}
		if(!"true".equals(weixinWorkConfig.getSyncContactToEkp())){
			logger.debug("没有启用客户回调");
			return;
		}
		try {
			getThirdWeixinWorkContactService().contactCallback(plainTextJson,info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			info.setFdIsSuccess(false);
			info.setFdErrorInfo(e.getMessage());
		}
	}
}
