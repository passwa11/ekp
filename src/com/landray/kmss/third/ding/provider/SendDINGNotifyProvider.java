package com.landray.kmss.third.ding.provider;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProvider;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.notify.util.SysNotifyLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingSendDing;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.service.IThirdDingSendDingService;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class SendDINGNotifyProvider extends BaseSysNotifyProvider {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SendDINGNotifyProvider.class);

	private IThirdDingWorkService thirdDingWorkService;

	public IThirdDingWorkService getThirdDingWorkService() {
		if (thirdDingWorkService == null) {
			return (IThirdDingWorkService) SpringBeanUtil
					.getBean("thirdDingWorkService");
		}
		return thirdDingWorkService;
	}

	private IThirdDingSendDingService thirdDingSendDingService;

	public IThirdDingSendDingService getThirdDingSendDingService() {
		if (thirdDingSendDingService == null) {
			return (IThirdDingSendDingService) SpringBeanUtil
					.getBean("thirdDingSendDingService");
		}
		return thirdDingSendDingService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private String getAgentIdByUrl(String url) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<ThirdDingWork> dingWorkList = getThirdDingWorkService()
				.findList(hqlInfo);
		if (null == dingWorkList || dingWorkList.size() <= 0) {
			return null;
		}
		for (ThirdDingWork dingWork : dingWorkList) {
			if (StringUtil.isNotNull(dingWork.getFdUrlPrefix())) {
				String[] fdUrlPrefix = dingWork.getFdUrlPrefix().split(";");
				for (int i = 0; i < fdUrlPrefix.length; i++) {
					if (url.indexOf(fdUrlPrefix[i]) > -1) {
						// return dingWork.getFdAgentid();
						return dingWork.getFdName();
					}
				}
			}
		}
		return null;
	}

	@Override
	public void send(NotifyContext context) throws Exception {
		if (context instanceof NotifyContextImp) {
			Map<String,List> contentList = new HashMap<String,List>();  //按语言分类推送DING   lang:fdIds
			try {
				if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
					logger.error("钉钉集成未开启，不启用DING功能！");
					return;
				}
				NotifyContextImp contextImp = (NotifyContextImp) context;
				List notifyTargetList = contextImp.getNotifyPersons();
				if (notifyTargetList == null || notifyTargetList.isEmpty()) {
					logger.warn("----通知人员为空不执行消息发送，不推送Ding消息------");
					return;
				}
				for (Iterator<?> it = notifyTargetList.iterator(); it.hasNext();) {
					SysOrgElement elememt = (SysOrgElement) it.next();

					SysOrgPerson p = (SysOrgPerson) sysOrgPersonService
							.findByPrimaryKey(elememt.getFdId(), null, true);

					logger.debug("添加DING对象："+p.getFdName()+" "+p.getFdDefaultLang());
					String lang = p.getFdDefaultLang();
					if(StringUtil.isNull(lang)){
						lang = "0";
					}
					if (!contentList.containsKey(lang)) {
						contentList.put(lang, new ArrayList());
					}
					contentList.get(lang).add(p.getFdId());
				}

				String agentId_name = null;
				String modelName = contextImp.getModelName();

				logger.debug("modelName:" + modelName);
				if (StringUtil.isNotNull(modelName)) {
					try {
						// 根据modelName取得模块前缀
						SysDictModel sysDict = SysDataDict.getInstance()
								.getModel(modelName);
						String messageKey = sysDict.getMessageKey(); // sys-task:table.sysTaskMain
						String model_pre = "";
						logger.debug("messageKey");
						if (StringUtil.isNotNull(messageKey)
								&& messageKey.contains(":")) {
							String prefix = messageKey.split(":")[0];
							logger.debug("prefix:" + prefix);
							if (StringUtil.isNotNull(prefix)
									&& prefix.contains("-")) {
								model_pre = prefix.replace("-", "/");
								logger.debug("model_pre:" + model_pre);
								agentId_name = getAgentIdByUrl(model_pre);
								logger.debug("agentId:" + agentId_name);
							}
						}

					} catch (Exception e) {
						logger.error("根据modelName获取agentId失败");
					}
				}
				logger.debug("发送DING的语言分类有：" + contentList.size());
				List list = new ArrayList();

				String link = contextImp.getLink();
				String domainName = ResourceUtil
						.getKmssConfigString("kmss.urlPrefix");
				if (StringUtil.isNotNull(link)) {
					link = link.trim();

					if (link.indexOf("http") == 0) {

					} else if (link.indexOf("/") == 0) {
						if (domainName.endsWith("/")) {
                            domainName = domainName.substring(0,
                                    domainName.length() - 1);
                        }
						link = domainName + link;
					}
					logger.debug("link:" + link);
				}
				for (String lang : contentList.keySet()) {

					ThirdDingSendDing thirdDingSendDing = new ThirdDingSendDing();
					String sd_id = IDGenerator.generateID();
					thirdDingSendDing.setFdId(sd_id);
					JSONObject remark = new JSONObject();

					list = contentList.get(lang);
					if ("0".equals(lang)) {
						lang = "";
					}
					remark.put("lang", lang);
					remark.put("link", contextImp.getLink());

					String subject = SysNotifyLangUtil.getSubject(
							(NotifyContextImp) context,
							lang);
					logger.debug("发送DING的标题：" + subject);
					thirdDingSendDing.setFdSubject(subject);
					thirdDingSendDing
							.setFdAgentid(StringUtil.isNull(agentId_name) ? "默认"
									: agentId_name);
					thirdDingSendDing.setFdModelName(contextImp.getModelName());
					thirdDingSendDing.setFdModelId(contextImp.getModelId());
					if (StringUtil.isNotNull(link)) {
						thirdDingSendDing.setFdLink(link);
						// 构造跳转link链接，以实现单点
						String temp = domainName
								+ "/third/ding/pc/url_out.jsp?sendDing=true&id="
								+ sd_id;
						//
						link = "dingtalk://dingtalkclient/page/link?url="
								+ URLEncoder.encode(temp, "UTF-8")
								+ "&pc_slide=true";
						subject = subject + "  " + link;
						remark.put("content", subject);
					}
					thirdDingSendDing.setFdRemark(remark.toString()); // 记录发DING的多语言信息
					thirdDingSendDing.setFdTarget(list.toString());
					String rs = DingUtils.getDingApiService().sendDING(subject,
							list,
							agentId_name);
					thirdDingSendDing.setFdResult(rs);
					thirdDingSendDing.setFdIsall(false);
					if (StringUtil.isNotNull(rs)) {
						JSONObject rsJson = JSONObject.fromObject(rs);
						if (rsJson.containsKey("errcode")
								&& rsJson.getInt("errcode") == 0) {
							thirdDingSendDing.setFdStatus(true);
						} else {
							thirdDingSendDing.setFdStatus(false);
						}
					} else {
						logger.error("发DING失败！" + subject);
						thirdDingSendDing.setFdStatus(false);
					}
					getThirdDingSendDingService().add(thirdDingSendDing);
				}

				
			} catch (Exception e) {
				logger.error("发送DING的过程中发生异常！");
				logger.error(e.toString());
			}
			
		}else{
			logger.warn("推送DING的context实现类非NotifyContextImp");
		}
		
//		
//		Map<String,List> contentList = new HashMap<String,List>();  //按语言分类推送DING
//		List<SysOrgElement> targets = context.getNotifyTarget();
//		
//		List receiver = new ArrayList<>();
//		for (SysOrgElement person : targets) {
//			logger.debug("添加DING对象！" + person.getFdName());
//			receiver.add(person.getFdId());
//		}
//
//		String contont = context.getSubject();
//		
//		context.getLink();
//
//		DingUtils.getDingApiService().sendDING(contont, receiver, null);

	}

	@Override
	protected SendDINGNotifyContext
			getExtendContext(NotifyContext context) {

		SendDINGNotifyContext sendDINGNotifyContext = context
				.getExtendContext(SendDINGNotifyContext.class);
		return sendDINGNotifyContext;
	}

}
