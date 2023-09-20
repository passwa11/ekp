package com.landray.kmss.third.weixin.work.service.spring;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.ImageCompressUtils;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.right.interfaces.IBaseAuthModel;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.ThirdWxworkDynamicinfo;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWxworkDynamicinfoService;
import com.landray.kmss.third.weixin.work.service.IThirdWxworkWriteDynamicInfoService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class RobotNodeSendDynamicInfoToWxworkServiceImp
		extends
			AbstractRobotNodeServiceImp {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(RobotNodeSendDynamicInfoToWxworkServiceImp.class);
	private IThirdWxworkDynamicinfoService thirdWxworkDynamicinfoService;
	private IThirdWxworkWriteDynamicInfoService thirdWxworkWriteDynamicInfoService;

	public void setThirdWxworkDynamicinfoService(
			IThirdWxworkDynamicinfoService thirdWxworkDynamicinfoService) {
		this.thirdWxworkDynamicinfoService = thirdWxworkDynamicinfoService;
	}

	public void setThirdWxworkWriteDynamicInfoService(
			IThirdWxworkWriteDynamicInfoService thirdWxworkWriteDynamicInfoService) {
		this.thirdWxworkWriteDynamicInfoService = thirdWxworkWriteDynamicInfoService;
	}

	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		if ("true".equals(WeixinWorkConfig.newInstance().getWxEnabled())) {
			ThirdWxworkDynamicinfo dynamicinfo = getDynamicinfo(context);// 获取动态信息
			log.debug("=========机器人节点 推送企业微信待办============");
			thirdWxworkWriteDynamicInfoService.writeDynamicInfo(dynamicinfo);// 启动写动态信息到KK5公众号的业务（即是定时任务的业务）
		} else {
			log.info("企业微信集成未开启，机器人节点不推送待办");
		}
	}

	private ThirdWxworkDynamicinfo getDynamicinfo(TaskExecutionContext context)
			throws Exception {
		ThirdWxworkDynamicinfo result = new ThirdWxworkDynamicinfo();
		IBaseModel mainModel = context.getMainModel();
		String docSubject = (String) PropertyUtils.getProperty(mainModel,
				"docSubject");
		JSONObject json = JSONObject.fromObject(getConfigContent(context));
		String agentid = null;
		if(json.containsKey("AgentId")){
			agentid = json.getString("AgentId");
		}
		result.setAgentid(agentid);
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
				if ("true".equals(readerNullToAll)
						&& (authModel.getAuthReaders() == null
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
		String domainName = WeixinWorkConfig.newInstance().getWxDomain();
		if (StringUtil.isNull(domainName)) {
			docUrl = StringUtil.formatUrl(ModelUtil.getModelUrl(mainModel));
		} else {
			if (domainName.trim().endsWith("/")) {
                domainName = domainName.trim().substring(0,
                        domainName.length() - 1);
            }
			docUrl = domainName + ModelUtil.getModelUrl(mainModel);
		}
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
		log.debug("=========modelName========" + modelName);
		log.debug("modelName:" + modelName);
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
		} else if ("com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"
				.equals(modelName)) {
			log.debug("=========知识仓库（文档类） 获取封面图片url========");
			url = getPicURL(
					"com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge",
					mainModel.getFdId(), "spic");
			if (StringUtil.isNull(url)) {
				log.debug("=========知识仓库（文档类） 获取图片url为空，取默认图片========");
				url = getDefualtPic();
			}
		} else if ("com.landray.kmss.kms.wiki.model.KmsWikiMain"
				.equals(modelName)) {
			log.debug("=========知识仓库 （维基类）  获取封面图片url========");
			url = getPicURL(
					"com.landray.kmss.kms.wiki.model.KmsWikiMain",
					mainModel.getFdId(), "spic");
			if (StringUtil.isNull(url)) {
				log.debug("=========知识仓库（维基类） 获取图片url为空，取默认图片========");
				url = getDefualtPic();
			}
		} else {
			url = getDefualtPic();
		}
		log.debug("推送图片的url:" + url);
		return url;
	}

	private String getDefualtPic(){
		String docUrl = "";
		String domainName = WeixinWorkConfig.newInstance().getWxDomain();
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
			String domainName = WeixinWorkConfig.newInstance().getWxDomain();
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
					+ att.getFdId() + "&fileName=" + URLEncoder.encode(att.getFdFileName(), "UTF-8");
			
			docUrl = uploadMedia(docUrl,att);
			
			return docUrl;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String uploadMedia(String mediaFileUrl,SysAttMain att) {
		String rtn = mediaFileUrl;
		try {
			WxworkApiService wxworkApiService = WxworkUtils
					.getWxworkApiService();
			// 拼装请求地址
			String uploadMediaUrl = WxworkUtils.getWxworkApiUrl() +"/media/uploadimg?access_token=ACCESS_TOKEN";
			uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN",
					wxworkApiService.getAccessToken());
			
			// 定义数据分隔符
			String boundary = "------------"+System.currentTimeMillis();
			URL uploadUrl = new URL(uploadMediaUrl);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// 设置请求头Content-Type
			uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			// 获取媒体文件上传的输出流（往微信服务器写数据）
			OutputStream outputStream = uploadConn.getOutputStream();
			// 根据内容类型判断文件扩展名
			String fileExt = URLEncoder.encode(att.getFdModelId()+att.getFdFileName(), "UTF-8");
			// 请求体开始
			outputStream.write(("--" + boundary + "\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n", att.getFdContentType()).getBytes());

			ISysAttMainCoreInnerService attMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainTarget");
			InputStream in = attMainCoreInnerService.getInputStream(att);
			ImageCompressUtils.getInstance().compressImage(ImageIO.read(in), outputStream, 640, 320, true);
			
			// 请求体结束
			outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
			outputStream.close();
			outputStream = null;
			in.close();
			in = null;

			// 获取媒体文件上传的输入流（从微信服务器读数据）
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			uploadConn.disconnect();

			if(buffer!=null){
				JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
				if(jsonObject.containsKey("errcode")&&jsonObject.getInt("errcode")==0){
					rtn = jsonObject.getString("url");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}
		return rtn;
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