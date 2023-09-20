package com.landray.kmss.third.weixin.service.spring;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.third.weixin.model.ThirdWxDynamicinfo;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.service.IThirdWxDynamicinfoService;
import com.landray.kmss.third.weixin.service.IThirdWxWriteDynamicInfoService;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class RobotNodeSendDynamicInfoToWxServiceImp
		extends
			AbstractRobotNodeServiceImp {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(RobotNodeSendDynamicInfoToWxServiceImp.class);
	private IThirdWxDynamicinfoService thirdWxDynamicinfoService;
	private IThirdWxWriteDynamicInfoService thirdWxWriteDynamicInfoService;

	public void setThirdWxDynamicinfoService(
			IThirdWxDynamicinfoService thirdWxDynamicinfoService) {
		this.thirdWxDynamicinfoService = thirdWxDynamicinfoService;
	}

	public void setThirdWxWriteDynamicInfoService(
			IThirdWxWriteDynamicInfoService thirdWxWriteDynamicInfoService) {
		this.thirdWxWriteDynamicInfoService = thirdWxWriteDynamicInfoService;
	}

	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		if ("true".equals(WeixinConfig.newInstance().getWxEnabled())) {
			ThirdWxDynamicinfo dynamicinfo = getDynamicinfo(context);// 获取动态信息
			thirdWxWriteDynamicInfoService.writeDynamicInfo(dynamicinfo);// 直接执行
		} else {
			log.info("微信企业号集成未开启，机器人节点不推送待办");
		}
	}

	private ThirdWxDynamicinfo getDynamicinfo(TaskExecutionContext context)
			throws Exception {
		ThirdWxDynamicinfo result = new ThirdWxDynamicinfo();
		IBaseModel mainModel = context.getMainModel();
		JSONObject json = JSONObject.fromObject(getConfigContent(context));
		String agentid = null;
		if(json.containsKey("AgentId")){
			agentid = json.getString("AgentId");
		}
		result.setAgentid(agentid);
		String docUrl = null;
		String domainName = WeixinConfig.newInstance().getWxDomain();
		if (StringUtil.isNull(domainName)) {
			docUrl = StringUtil.formatUrl(ModelUtil.getModelUrl(mainModel));
		} else {
			if (domainName.trim().endsWith("/")) {
                domainName = domainName.trim().substring(0,
                        domainName.length() - 1);
            }
			docUrl = domainName + ModelUtil.getModelUrl(mainModel);
		}
		String docSubject = (String) PropertyUtils.getProperty(mainModel,
				"docSubject");
		Date docCreateTime = null;
		try {
			docCreateTime = (Date) PropertyUtils.getProperty(mainModel,
					"docCreateTime");
		} catch (Exception exception) {
			docCreateTime = null;
		}
		result.setFdProcessId(context.getProcessInstance().getFdId());
		result.setDocUrl(docUrl);
		//取消全员推送的分类
//		String docCategory = ModelUtil.getModelPropertyString(mainModel,
//				getCategoryFieldName(mainModel), null, null);
//		if(StringUtil.isNull(docCategory)){
//			docCategory = ResourceUtil.getString("calendar.subject");
//		}
//		result.setDocTitle(docCategory + ":" + docSubject);
		result.setDocTitle(docSubject);
		result.setDocDescription(getDescription(mainModel));
		result.setPicUrl(getPicUrl(mainModel));
		result.setDocCreateTime(
				docCreateTime == null ? new Date() : docCreateTime);
		return result;
	}

	private String getPicUrl(IBaseModel mainModel) {
		String url = "";
		String modelName = ModelUtil.getModelClassName(mainModel);
		if (mainModel instanceof SysNewsMain) {
			SysNewsMain newsMain = (SysNewsMain) mainModel;
			if (newsMain.getFdIsPicNews() != null
					&& newsMain.getFdIsPicNews().booleanValue() == true) {
				url = getPicURL("com.landray.kmss.sys.news.model.SysNewsMain",
						mainModel.getFdId(), "Attachment");
			} else {
				url = getDefualtPic();
			}
		} else if ("com.landray.kmss.km.doc.model.KmDocKnowledge"
				.equals(modelName)) {
			url = getPicURL("com.landray.kmss.km.doc.model.KmDocKnowledge",
					mainModel.getFdId(), "spic");
			if (StringUtil.isNull(url)) {
				url = getDefualtPic();
			}
		} else {
			url = getDefualtPic();
		}
		return url;
	}

	private String getDefualtPic() {
		String docUrl = "";
		String domainName = WeixinConfig.newInstance().getWxDomain();
		if (StringUtil.isNull(domainName)) {
			docUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
		} else {
			if (domainName.trim().endsWith("/")) {
                domainName = domainName.trim().substring(0,
                        domainName.length() - 1);
            }
			docUrl = domainName;
		}
		String fileName = "default.jpg";
		String filePath = ConfigLocationsUtil.getWebContentPath()
				+ "/third/weixin/resource/images/";
		File file = new File(filePath + fileName);
		if (file.exists()) {
			docUrl = docUrl + "/third/weixin/resource/images/" + fileName;
		}
		return docUrl;
	}

	private String getPicURL(String modelName, String fdId, String key) {
		OutputStream out = null;
		InputStream in = null;
		try {
			ISysAttMainCoreInnerService attMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainTarget");
			List list = attMainCoreInnerService.findByModelKey(modelName, fdId,
					key);
			if (list.isEmpty()) {
				return "";
			}
			SysAttMain att = (SysAttMain) list.get(0);
			String docUrl = null;
			String domainName = WeixinConfig.newInstance().getWxDomain();
			if (StringUtil.isNull(domainName)) {
				docUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			} else {
				if (domainName.trim().endsWith("/")) {
                    domainName = domainName.trim().substring(0,
                            domainName.length() - 1);
                }
				docUrl = domainName;
			}
			docUrl = docUrl
					+ "/resource/third/weixin/attachment.do?method=downloadWxPic&fdId="
					+ att.getFdId() + "&fileName=" + att.getFdFileName();
			return docUrl;
		} catch (Exception e) {
			return "";
		}
	}

	private String getDescription(IBaseModel mainModel) {
		String docDescription = "";
		try {
			docDescription = (String) PropertyUtils.getProperty(mainModel,
					"fdDescription");
		} catch (Exception e) {

		}
		if (StringUtil.isNull(docDescription)) {
			try {
				docDescription = (String) PropertyUtils.getProperty(mainModel,
						"docContent");
			} catch (Exception e1) {

			}
		}
		if (StringUtil.isNull(docDescription)) {
			try {
				docDescription = (String) PropertyUtils.getProperty(mainModel,
						"fdContent");
			} catch (Exception e1) {

			}
		}
		if (StringUtil.isNotNull(docDescription)) {
			docDescription = HtmlUtils.htmlUnescape(docDescription);
			docDescription = delHTMLTag(docDescription);
			if (docDescription.length() > 50) {
				docDescription = docDescription.substring(0, 49) + "……";
			}
		}
		return docDescription;
	}

	private String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
//		String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style,
				Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

//		Pattern p_space = Pattern.compile(regEx_space,
//				Pattern.CASE_INSENSITIVE);
//		Matcher m_space = p_space.matcher(htmlStr);
//		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
		return htmlStr.trim(); // 返回文本字符串
	}

	private String getCategoryFieldName(IBaseModel model) {
		Class<? extends IBaseModel> cls = model.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field item : fields) {
			if (item.getName().toLowerCase().contains("category")
					|| item.getName().toLowerCase().contains("template")) {
				return catFieldName(item.getName());
			}
		}
		return "";
	}

	private String catFieldName(String src) {
		StringBuffer fieldName = new StringBuffer();
		char[] tmpChars = src.toCharArray();
		for (char item : tmpChars) {
			if (item == '$') {
				fieldName.append('-');
			} else {
				fieldName.append(item);
			}
		}
		String[] tmp0 = fieldName.toString().split("-");
		String tmp1 = tmp0[1].substring(3);
		fieldName.delete(0, fieldName.length());
		fieldName.append(tmp1.toLowerCase().substring(0, 1))
				.append(tmp1.substring(1));
		return fieldName.toString();
	}
}