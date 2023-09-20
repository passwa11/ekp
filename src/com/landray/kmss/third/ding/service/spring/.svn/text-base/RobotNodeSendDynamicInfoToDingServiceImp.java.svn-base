package com.landray.kmss.third.ding.service.spring;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.right.interfaces.IBaseAuthModel;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDynamicinfo;
import com.landray.kmss.third.ding.service.IThirdDingDynamicinfoService;
import com.landray.kmss.third.ding.service.IThirdDingWriteDynamicInfoService;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class RobotNodeSendDynamicInfoToDingServiceImp extends AbstractRobotNodeServiceImp {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(RobotNodeSendDynamicInfoToDingServiceImp.class);
	private IThirdDingDynamicinfoService thirdDingDynamicinfoService;
	private IThirdDingWriteDynamicInfoService thirdDingWriteDynamicInfoService;

	public void setThirdDingDynamicinfoService(IThirdDingDynamicinfoService thirdDingDynamicinfoService) {
		this.thirdDingDynamicinfoService = thirdDingDynamicinfoService;
	}

	public void setThirdDingWriteDynamicInfoService(
			IThirdDingWriteDynamicInfoService thirdDingWriteDynamicInfoService) {
		this.thirdDingWriteDynamicInfoService = thirdDingWriteDynamicInfoService;
	}

	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		if ("true".equals(DingConfig.newInstance().getDingEnabled())) {
			ThirdDingDynamicinfo dynamicinfo = getDynamicinfo(context);// 获取动态信息
			thirdDingWriteDynamicInfoService.writeDynamicInfo(dynamicinfo);// 启动写动态信息到KK5公众号的业务（即是定时任务的业务）
		}else{
			log.info("钉钉集成未开启，机器人节点不推送待办");
		}
	}

	private ThirdDingDynamicinfo getDynamicinfo(TaskExecutionContext context) throws Exception {
		ThirdDingDynamicinfo result = new ThirdDingDynamicinfo();
		IBaseModel mainModel = context.getMainModel();
		String docSubject = (String) PropertyUtils.getProperty(mainModel,
				"docSubject");
		JSONObject json = JSONObject.fromObject(getConfigContent(context));
		if(json.containsKey("AgentId")) {
            result.setAgentid(json.getString("AgentId"));
        }
		if (json.containsKey("sendTargetType")) {
			result.setFdSendTargetType(json.getString("sendTargetType"));
		} else {
			result.setFdSendTargetType("all");
		}
		if ("reader".equals(result.getFdSendTargetType())) {
			if (mainModel instanceof IBaseAuthModel) {
				Thread.sleep(200);
				IBaseAuthModel authModel = (IBaseAuthModel) mainModel;
				List readers = authModel.getAuthAllReaders();
				readers.addAll(authModel.getAuthReaders());
				result.setReaders(readers);

				String readerNullToAll = json.getString("readerNullToAll");
				// result.setFdSendTargetType(readerNullToAll);
				if ("true".equals(readerNullToAll) && (authModel.getAuthReaders() == null
						|| authModel.getAuthReaders().isEmpty())) {
					log.debug("可阅读者为空，发送给全部用户," + docSubject);
					result.setFdSendTargetType("all");
				}
			} else {
				log.error(mainModel.getClass().getName()
						+ "未部署权限机制，无法使用可阅读者发送，本地发送发给全部用户," + docSubject);
				result.setFdSendTargetType("all");
			}
		}
		if (json.containsKey("specifiedIds")) {
            result.setFdSpecifiedIds(json.getString("specifiedIds"));
        }
		if (json.containsKey("specifiedNames")) {
            result.setFdSpecifiedNames(json.getString("specifiedNames"));
        }
		String docUrl = null;
		String domainName = DingConfig.newInstance().getDingDomain();
		if(StringUtil.isNull(domainName)){
			docUrl = StringUtil.formatUrl(ModelUtil.getModelUrl(mainModel));
		}else{
			if(domainName.trim().endsWith("/")) {
                domainName = domainName.trim().substring(0, domainName.length()-1);
            }
			docUrl = domainName+ModelUtil.getModelUrl(mainModel);
		}
		String docCategory = ModelUtil.getModelPropertyString(mainModel, getCategoryFieldName(mainModel), null, null);

		Date docCreateTime = null;
		try {
			docCreateTime = (Date) PropertyUtils.getProperty(mainModel, "docCreateTime");
		} catch (Exception exception) {
			docCreateTime = null;
		}
		result.setFdProcessId(context.getProcessInstance().getFdId());
		result.setDocUrl(docUrl);
		if(StringUtil.isNull(docCategory)){
			docCategory = ResourceUtil.getString("calendar.subject");
		}
		result.setDocTitle(docCategory + ":" + docSubject);
		result.setDocDescription(getDescription(mainModel));
		result.setPicUrl(getPicUrl(mainModel));
		result.setDocCreateTime(docCreateTime == null ? new Date() : docCreateTime);
		return result;
	}

	private String getPicUrl(IBaseModel mainModel) {
		if (mainModel instanceof SysNewsMain) {
			SysNewsMain newsMain = (SysNewsMain) mainModel;
			if (newsMain.getFdIsPicNews() != null && newsMain.getFdIsPicNews().booleanValue() == true) {
				ISysAttMainCoreInnerService attMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainTarget");
				try {
					List list = attMainCoreInnerService.findByModelKey("com.landray.kmss.sys.news.model.SysNewsMain",
							mainModel.getFdId(), "Attachment");
					if (list.isEmpty()) {
						return "";
					}
					SysAttMain att = (SysAttMain) list.get(0);
					String docUrl = null;
					String domainName = DingConfig.newInstance().getDingDomain();
					if(StringUtil.isNull(domainName)){
						docUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
					}else{
						if(domainName.trim().endsWith("/")) {
                            domainName = domainName.trim().substring(0, domainName.length()-1);
                        }
						docUrl = domainName;
					}
					return docUrl
							+ "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=" + att.getFdId()
							+ "&fileName=" + att.getFdFileName();
				} catch (Exception e) {
					return "";
				}
			}
		}
		return "";
	}

	private String getDescription(IBaseModel mainModel) {
		String docDescription = "";
		try {
			docDescription = (String) PropertyUtils.getProperty(mainModel, "fdDescription");
		} catch (Exception e) {

		}
		if (StringUtil.isNull(docDescription)) {
			try {
				docDescription = (String) PropertyUtils.getProperty(mainModel, "docContent");
			} catch (Exception e1) {

			}
		}
		if (StringUtil.isNull(docDescription)) {
			try {
				docDescription = (String) PropertyUtils.getProperty(mainModel, "fdContent");
			} catch (Exception e1) {

			}
		}
		if (StringUtil.isNotNull(docDescription)) {
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
		String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
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
		String tempStr = fieldName.toString();
		if(StringUtil.isNotNull(tempStr)&&tempStr.contains("-")){
			String[] tmp0 = tempStr.split("-");
			String tmp1 = tmp0[1].substring(3);
			fieldName.delete(0, fieldName.length());
			fieldName.append(tmp1.toLowerCase().substring(0, 1)).append(tmp1.substring(1));
			return fieldName.toString();
		}else {
			return tempStr;
		}
	}
}