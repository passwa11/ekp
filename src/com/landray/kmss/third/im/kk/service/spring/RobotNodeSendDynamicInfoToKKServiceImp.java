package com.landray.kmss.third.im.kk.service.spring;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.model.ThirdImKKGzhDynamicinfo;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.service.IThirdImKKGzhDynamicinfoService;
import com.landray.kmss.third.im.kk.service.IThirdImKKWriteDynamicInfoService;
import com.landray.kmss.third.im.kk.util.NotifyConfigUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class RobotNodeSendDynamicInfoToKKServiceImp extends AbstractRobotNodeServiceImp {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(RobotNodeSendDynamicInfoToKKServiceImp.class);

	private IThirdImKKGzhDynamicinfoService thirdImKKGzhDynamicinfoService;
	private IThirdImKKWriteDynamicInfoService thirdImKKWriteDynamicInfoService;

	public IThirdImKKGzhDynamicinfoService getThirdImKKGzhDynamicinfoService() {
		return thirdImKKGzhDynamicinfoService;
	}

	public void setThirdImKKGzhDynamicinfoService(IThirdImKKGzhDynamicinfoService thirdImKKGzhDynamicinfoService) {
		this.thirdImKKGzhDynamicinfoService = thirdImKKGzhDynamicinfoService;
	}

	public IThirdImKKWriteDynamicInfoService getThirdImKKWriteDynamicInfoService() {
		return thirdImKKWriteDynamicInfoService;
	}

	public void setThirdImKKWriteDynamicInfoService(
			IThirdImKKWriteDynamicInfoService thirdImKKWriteDynamicInfoService) {
		this.thirdImKKWriteDynamicInfoService = thirdImKKWriteDynamicInfoService;
	}

	protected IKkImConfigService kkImConfigService;

	public void setKkImConfigService(IKkImConfigService kkImConfigService) {
		this.kkImConfigService = kkImConfigService;
	}

	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		if ("true".equals(kkImConfigService.getValuebyKey(KeyConstants.KK_CONFIG_SATUS))) {
			ThirdImKKGzhDynamicinfo dynamicinfo = null;
			try {
				dynamicinfo = getDynamicinfo(context);// 获取动态信息
			} catch (Exception e) {
				log.debug(ResourceUtil.getString("robotNodeService.getInfoFailure", "third-im-kk"), e);
			}
			if (null != dynamicinfo) {
				try {
					thirdImKKGzhDynamicinfoService.add(dynamicinfo);// 将动态信息保存到数据库
				} catch (Exception e) {
					log.debug(ResourceUtil.getString("robotNodeService.saveInfoFailure", "third-im-kk"), e);
				}
			}
			thirdImKKWriteDynamicInfoService.writeDynamicInfo();// 启动写动态信息到KK5公众号的业务（即是定时任务的业务）
		}
	}

	private ThirdImKKGzhDynamicinfo getDynamicinfo(TaskExecutionContext context) throws Exception {
		ThirdImKKGzhDynamicinfo result = new ThirdImKKGzhDynamicinfo();
		/**
		 * 动态信息形成时间
		 */
		Date docCreateTime = null;
		String gzhConfigContent = getConfigContent(context);
		String[] gzhConfig = gzhConfigContent.substring(gzhConfigContent.indexOf("=") + 1).split("_");
		result.setCorpId(gzhConfig[0]);
		result.setServiceCode(gzhConfig[1]);
		result.setFdProcessId(context.getProcessInstance().getFdId());
		IBaseModel mainModel = context.getMainModel();
		String docUrl = NotifyConfigUtil.getNotifyUrlPrefix() + ModelUtil.getModelUrl(mainModel);
		String docCategory = ModelUtil.getModelPropertyString(mainModel, getCategoryFieldName(mainModel), null, null);
		log.debug("docCategory:" + docCategory);
		if (StringUtil.isNotNull(docCategory)) {
			docCategory = docCategory + ":";
		} else {
			docCategory = "";
		}
		String docSubject = (String) PropertyUtils.getProperty(mainModel, "docSubject");
		result.setDocUrl(docUrl);
		result.setDocTitle(docCategory + docSubject);
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
					return NotifyConfigUtil.getNotifyUrlPrefix()
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

		htmlStr = StringEscapeUtils.unescapeHtml4(htmlStr);
		return htmlStr.trim(); // 返回文本字符串
	}

	private String getCategoryFieldNameOld(IBaseModel model) {
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

	private String getCategoryFieldName(IBaseModel model) {
		log.debug("modelName:" + model.getClass().getName());
		PropertyDescriptor[] sProperties = PropertyUtils
				.getPropertyDescriptors(model);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < sProperties.length; i++) {
			String sPropertyName = sProperties[i].getName();
			try {
				if (!PropertyUtils.isReadable(model, sPropertyName)) {
                    continue;
                }
				if (sPropertyName.toLowerCase().contains("category")
						|| sPropertyName.toLowerCase().contains("template")) {
					list.add(sPropertyName);
				}
			} catch (Exception e) {
				continue;
			}
		}
		String categoryName = "";
		if (list.isEmpty()) {
			categoryName = "";
		}
		if (list.contains("docCategory")) {
			categoryName = "docCategory";
		}
		if (list.contains("fdTemplate")) {
			categoryName = "fdTemplate";
		}
		if (StringUtil.isNull(categoryName)) {
			for (String propName : list) {
				if (propName.toLowerCase().endsWith("category")
						|| propName.toLowerCase().endsWith("template")) {
					categoryName = propName;
				}
			}
		}
		// categoryName = list.get(0);
		log.debug("categoryName:" + categoryName);
		return categoryName;

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
		fieldName.append(tmp1.toLowerCase().substring(0, 1)).append(tmp1.substring(1));
		return fieldName.toString();
	}

}