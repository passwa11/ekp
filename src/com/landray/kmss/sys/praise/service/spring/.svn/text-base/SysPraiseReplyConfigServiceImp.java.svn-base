package com.landray.kmss.sys.praise.service.spring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.praise.service.ISysPraiseReplyConfigService;
import com.landray.kmss.util.IDGenerator;

public class SysPraiseReplyConfigServiceImp extends BaseServiceImp
		implements ISysPraiseReplyConfigService {

	public ISysAppConfigService sysAppConfigService;

	@Override
    public void view(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Boolean isOpenReply = this.isOpenReply();
		request.setAttribute("isOpenReply", isOpenReply);
		if (isOpenReply != null) {
			String checkReplyText = this.checkReplyText();
			request.setAttribute("replyText", checkReplyText);
		}
	}

	@Override
    public Boolean isOpenReply() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "fdKey =:fdKey and fdField =:fdField";
		hqlInfo.setParameter("fdKey",
				"com.landray.kmss.sys.praise.SysPraiseReplyConfig");
		hqlInfo.setParameter("fdField", "isOpenReply");
		hqlInfo.setWhereBlock(whereBlock);
		SysAppConfig obj = (SysAppConfig)sysAppConfigService.findFirstOne(hqlInfo);

		if (obj == null) {
			return null;
		}
		// 记录日志
		UserOperHelper.logFind(obj);

		if ("true".equals(obj.getFdValue())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
    public String checkReplyText() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "fdKey =:fdKey and fdField =:fdField";
		hqlInfo.setParameter("fdKey",
				"com.landray.kmss.sys.praise.SysPraiseReplyConfig");
		hqlInfo.setParameter("fdField", "replyText");
		hqlInfo.setWhereBlock(whereBlock);
		SysAppConfig obj = (SysAppConfig)sysAppConfigService.findFirstOne(hqlInfo);
		if (obj == null) {
			return null;
		}
		// 记录日志
		UserOperHelper.logFind(obj);
		return obj.getFdValue();
	}

	@Override
    public void updateReplyConfig(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		Boolean isOpenReply =
				Boolean.valueOf(request.getParameter("isOpenReply"));
		String replyText = request.getParameter("replyResultList");
		this.saveOpenConfig(isOpenReply);
		this.saveTextListConfig(replyText);
	}

	private void saveOpenConfig(Boolean isOpenReply) throws Exception {
		SysAppConfig openConfig = this.getOpenConfig();
		if (openConfig == null) {
			openConfig = new SysAppConfig();
			openConfig.setFdId(IDGenerator.generateID());
			openConfig.setFdKey(
					"com.landray.kmss.sys.praise.SysPraiseReplyConfig");
			openConfig.setFdField("isOpenReply");
			openConfig.setFdValue(String.valueOf(isOpenReply));

			if (UserOperHelper.allowLogOper("saveConfig", "")) {
				UserOperContentHelper.putAdd(openConfig, "fdKey", "fdField",
						"fdValue");
			}

			sysAppConfigService.add(openConfig);
		} else {

			if (UserOperHelper.allowLogOper("saveConfig", "")) {
				UserOperContentHelper
						.putUpdate(openConfig.getFdId(), "",
								SysAppConfig.class.getName())
						.putSimple("fdValue", openConfig.getFdValue(),
								isOpenReply);
			}

			openConfig.setFdValue(String.valueOf(isOpenReply));

			sysAppConfigService.update(openConfig);
		}
	}

	private void saveTextListConfig(String replyText) throws Exception {
		SysAppConfig textListConfig = this.getTextListConfig();
		if (textListConfig == null) {
			textListConfig = new SysAppConfig();
			textListConfig.setFdId(IDGenerator.generateID());
			textListConfig.setFdKey(
					"com.landray.kmss.sys.praise.SysPraiseReplyConfig");
			textListConfig.setFdField("replyText");
			textListConfig.setFdValue(replyText);
			sysAppConfigService.add(textListConfig);

			if (UserOperHelper.allowLogOper("saveConfig", "")) {
				UserOperContentHelper.putAdd(textListConfig, "fdKey", "fdField",
						"fdValue");
			}
		} else {

			if (UserOperHelper.allowLogOper("saveConfig", "")) {
				UserOperContentHelper
						.putUpdate(textListConfig.getFdId(), "",
								SysAppConfig.class.getName())
						.putSimple("fdValue", textListConfig.getFdValue(),
								replyText);
			}

			textListConfig.setFdValue(replyText);
			sysAppConfigService.update(textListConfig);
		}
	}

	private SysAppConfig getOpenConfig() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "fdKey =:fdKey and fdField =:fdField";
		hqlInfo.setParameter("fdKey",
				"com.landray.kmss.sys.praise.SysPraiseReplyConfig");
		hqlInfo.setParameter("fdField", "isOpenReply");
		hqlInfo.setWhereBlock(whereBlock);
		SysAppConfig obj = (SysAppConfig)sysAppConfigService.findFirstOne(hqlInfo);
		return obj;
	}

	private SysAppConfig getTextListConfig() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "fdKey =:fdKey and fdField =:fdField";
		hqlInfo.setParameter("fdKey",
				"com.landray.kmss.sys.praise.SysPraiseReplyConfig");
		hqlInfo.setParameter("fdField", "replyText");
		hqlInfo.setWhereBlock(whereBlock);
		SysAppConfig obj = (SysAppConfig)sysAppConfigService.findFirstOne(hqlInfo);
		return obj;
	}

	public ISysAppConfigService getSysAppConfigService() {
		return sysAppConfigService;
	}

	public void
			setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}
}
