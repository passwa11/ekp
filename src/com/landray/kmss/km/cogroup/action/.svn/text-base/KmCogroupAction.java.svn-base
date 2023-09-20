package com.landray.kmss.km.cogroup.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.cogroup.constant.KKContants;
import com.landray.kmss.km.cogroup.interfaces.ICogroupConfigService;
import com.landray.kmss.km.cogroup.util.AES;
import com.landray.kmss.km.cogroup.util.CogroupUtil;
import com.landray.kmss.km.cogroup.util.HttpRequest;
import com.landray.kmss.km.cogroup.util.PluginUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCurrentInfoService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * @author 孙佳
 */
public class KmCogroupAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmCogroupAction.class);

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	/**
	 * <p>获取创建群聊请求参数</p>
	 * @author 孙佳
	 */
	public String getCreateGroupInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject map = new JSONObject();
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			if (StringUtil.isNull(fdId) || StringUtil.isNull(modelName)) {
				throw new Exception("请求参数不能为空!");
			}
			SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);

			IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(sysDictModel.getServiceBean());
			IBaseModel baseModel = baseService.findByPrimaryKey(fdId);

			String messageKey = ResourceUtil.getString(sysDictModel.getMessageKey()); //模块名称
			String url = ModelUtil.getModelUrl(baseModel); //查看url
			//获取文档标题
			String fdName = SysDataDict.getInstance().getModel(modelName).getDisplayProperty();
			String docValue = ModelUtil.getModelPropertyString(baseModel, fdName, null, null);
			//获取创建人、创建时间
			String descUser = CogroupUtil.getModelDocCreatorProperyValue(baseModel, "docCreator", null);
			String docCreateTime = CogroupUtil.getModelDocCreateTimeProperyValue(baseModel, "docCreateTime",
					Locale.CHINESE);
			List<String> userList = ((ILbpmProcessCurrentInfoService) SpringBeanUtil
					.getBean("lbpmProcessCurrentInfoService")).getAllHandlersLoginNames(fdId);

			if (StringUtil.isNull(messageKey)) {
				throw new Exception("模块名称不能为空！");
			}
			if (StringUtil.isNull(url)) {
				throw new Exception("业务URL不能为空！");
			}
			if (StringUtil.isNull(docValue)) {
				throw new Exception("文档标题不能为空！");
			}
			String simpleModel = getModelSimpleClassName(baseModel);
			map.put("fdId", StringUtil.linkString(simpleModel, "_", fdId));
			map.put("url", StringUtil.formatUrl(url));
			map.put("bizTypeName", "流程沟通");
			map.put("groupName", docValue);
			map.put("descUser", descUser);
			map.put("descTime", docCreateTime);
			map.put("descTimePC",
					DateUtil.convertDateToString(DateUtil.convertStringToDate(docCreateTime, null), "yyyyMMddHHmmss"));
			map.put("userList", userList.toArray(new String[0]));
			if (UserOperHelper.allowLogOper("getCreateGroupInfo", null)) {
				UserOperHelper.logMessage(map.toString());
				UserOperHelper.setOperSuccess(true);
			}
			logger.info(map.toString());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();


		} catch (Exception e) {
			logger.info("-------------------创建群聊失败-----------------------");
			e.printStackTrace();
		}
		return null;
	}

	private static String getModelSimpleClassName(Object mainModel) {
		// 如果传入的是一个String类型的全类名，则不需要再解析了
		String rtnVal = mainModel.getClass().getSimpleName();
		int i = rtnVal.indexOf('$');
		if (i > -1) {
            rtnVal = rtnVal.substring(0, i);
        }
		return rtnVal;
	}

	/**
	 * <p>获取群id</p>
	 * @return
	 * @author 孙佳
	 * @throws IOException 
	 */
	public String getGroupInfoByBiz(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("-------------------调用kk serverj接口获取群id,以便kk群跳转-----------------------");
		JSONObject res = new JSONObject();
		try {
			JSONObject param = new JSONObject();
			String bizType = request.getParameter("bizType");
			String bizId = request.getParameter("bizId");
			if (StringUtil.isNull(bizType) || StringUtil.isNull(bizId)) {
				res.put("errorMsg", "参数不能为空");
				throw new Exception("参数不能为空：bizType=" + bizType + ",bizId=" + bizId);
			}
			ICogroupConfigService configService = PluginUtil.getConfig("com.landray.kmss.third.im.kk");
			String kk5ServerUrl = configService
					.getValuebyKey(KKContants.KK_INNER_DOMAIN);
			if (StringUtil.isNull(kk5ServerUrl)) {
				res.put("errorMsg", "kk内网地址为空，请先配置kk一体化！");
				throw new Exception("kk内网地址为空，请先配置kk一体化！");
			}
			param.put("bizType", bizType);
			param.put("bizId", bizId);
			String result = HttpRequest.sendPost(kk5ServerUrl + KKContants.KK_SERVERJ_GET_GROUP_INFOBYBIZ,
					param.toString(), getHeadParam(configService));
			if (StringUtil.isNotNull(result)) {
				JSONObject json = JSONObject.fromObject(result);
				String resultCode = json.get("result").toString();
				res.put("result", resultCode);
				if ("0".equals(resultCode)) {
					JSONObject groupInfo = JSONObject.fromObject(json.get("groupInfo"));
					res.put("groupID", groupInfo.get("groupID").toString());
					res.put("groupState", groupInfo.get("groupState").toString());
				} else {
					res.put("errorMsg", json.get("errorMsg").toString());
				}
			}
		} catch (Exception e) {
			logger.warn("获取群id失败:",e);
			res.put("errorMsg", e.getMessage()==null?"空指针异常":e.getMessage());
		}
		if (UserOperHelper.allowLogOper("getGroupInfoByBiz", null)) {
			UserOperHelper.logMessage(res.toString());
			UserOperHelper.setOperSuccess(true);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(res.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * <p>获取群消息记录url</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 孙佳
	 */
	public String getGroupRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("-------------------调用kk serverj接口获取群消息记录url-----------------------");
		JSONObject res = new JSONObject();
		try {
			JSONObject param = new JSONObject();
			String groupID = request.getParameter("groupID");
			if (StringUtil.isNull(groupID)) {
				res.put("errorMsg", "群id不能为空");
				throw new Exception("参数不能为空：groupID=" + groupID);
			}
			ICogroupConfigService configService = PluginUtil.getConfig("com.landray.kmss.third.im.kk");
			String kk5ServerUrl = configService.getValuebyKey(KKContants.KK_INNER_DOMAIN);
			if (StringUtil.isNull(kk5ServerUrl)) {
				res.put("errorMsg", "kk内网地址为空，请先配置kk一体化！");
				throw new Exception("kk内网地址为空，请先配置kk一体化！");
			}
			param.put("groupId", groupID);
			String result = HttpRequest.sendPost(kk5ServerUrl + KKContants.KK_SERVERJ_GET_GROUP_MESSAGE,
					param.toString(), getHeadParam(configService));
			if (StringUtil.isNotNull(result)) {
				JSONObject json = JSONObject.fromObject(result);
				String resultCode = json.get("result").toString();
				res.put("result", resultCode);
				if ("0".equals(resultCode)) {
					res.put("pageUrl", json.get("pageUrl").toString());
				} else {
					res.put("errorMsg", json.get("errorMsg").toString());
				}
			}
		} catch (Exception e) {
			logger.info("-------------------获取群id失败-----------------------");
			e.printStackTrace();
		}
		if (UserOperHelper.allowLogOper("getGroupRecord", null)) {
			UserOperHelper.logMessage(res.toString());
			UserOperHelper.setOperSuccess(true);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(res.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * <p>获取鉴权</p>
	 * @author 孙佳
	 */
	private Map<String, String> getHeadParam(ICogroupConfigService configService) {
		Map<String, String> headParam = new HashMap<String, String>();
		String serverjAuthId = configService.getValuebyKey(KKContants.KK_SERVERJ_AUTHID);
		String sererrjAuthKey = configService.getValuebyKey(KKContants.KK_SERERRJ_AUTHKEY);
		//签名
		String retStrFormatNowDate = String.valueOf(System.currentTimeMillis());
		String serverjAuthSign = AES.encrypt2str(serverjAuthId + "|" + retStrFormatNowDate, sererrjAuthKey);
		headParam.put("push-id", serverjAuthId);
		headParam.put("push-sign", serverjAuthSign);
		return headParam;
	}
}
