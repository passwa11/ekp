package com.landray.kmss.sys.attachment.jg;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class SysAttJGSaveAsHtmlFunction implements ISysAttachmentJGFunction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGSaveAsHtmlFunction.class);

	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		doSaveAsHtml(request, MsgObj, "HTML");
	}

	protected static void doSaveAsHtml(RequestContext request,
			iMsgServer2000 MsgObj, String attType) throws Exception {
		String fdId = MsgObj.GetMsgByName("RECORDID");
		String fn = MsgObj.GetMsgByName("HTMLNAME");
		String dn = MsgObj.GetMsgByName("DIRECTORY"); // 取得目录名称

		boolean isLogJgEnabled = false;
		String logJgValue = ResourceUtil
				.getKmssConfigString("kmss.isLogJgEnabled");
		if (logJgValue != null) {
			isLogJgEnabled = "true".equals(logJgValue.trim());
		}
		if (logger.isInfoEnabled()) {
			logger.info("保存为" + attType + "：HTMLNAME：" + fn);
			if (isLogJgEnabled) {
				logger.info("iWeboffice控件监控日志:"
						+ "保存为:"
						+ attType
						+ ";HTMLNAME："
						+ fn
						+ ";保存时间:"
						+ DateUtil.convertDateToString(new Date(),
								DateUtil.PATTERN_DATETIME, UserUtil
										.getKMSSUser().getLocale()));
			}
		}
		String hp = JGFilePathUtil.genFilePath(fdId, null);
		MsgObj.MsgTextClear(); // 清除所有变量
		if (StringUtil.isNotNull(dn)) {
			hp = hp + dn + JGFilePathUtil.SEPARATOR;
		}
		MsgObj.MakeDirectory(hp);
		if (MsgObj.MsgFileSave(hp + fn)) { // 保存HTML文件
			MsgObj.MsgError(""); // 清除错误信息
			MsgObj.SetMsgByName("STATUS", "保存" + attType + "成功!"); // 设置状态信息
		} else {
			MsgObj.MsgError("保存" + attType + "失败!"); // 设置错误信息
		}
		MsgObj.MsgFileClear();
	}

}
