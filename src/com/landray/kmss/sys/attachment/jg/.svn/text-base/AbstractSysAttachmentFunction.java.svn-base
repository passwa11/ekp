package com.landray.kmss.sys.attachment.jg;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.StringUtil;

public abstract class AbstractSysAttachmentFunction {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractSysAttachmentFunction.class);

	private ISysAttMainCoreInnerService sysAttMainService = null;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		return sysAttMainService;
	}

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	protected void updateSysAttMain(SysAttMain sysAttMain) throws Exception {
		if (null != sysAttMain) {
			sysAttMain.setInputStream(null);
			sysAttMainService.update(sysAttMain);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("附件为空导致更新失败");
			}
		}
	}

	protected SysAttMain getSysAttMain(String fdId, String modelId,
			String modelName, String key) throws Exception {
		return this.getSysAttMain(fdId, modelId, modelName, key, false);
	}

	protected SysAttMain getSysAttMain(String fdId, String modelId,
			String modelName, String key, boolean lock) throws Exception {
		SysAttMain att = null;
		if (StringUtil.isNotNull(fdId)) {
			att = (SysAttMain) getSysAttMainService().findByPrimaryKey(fdId,
					null, true);
			if (logger.isDebugEnabled()) {
				if (att != null) {
                    logger.debug("根据fdId查找得到文档：fdId:" + fdId);
                }
			}
		}
		if (att == null) {
			if (StringUtil.isNull(modelId)) {
				return null;
			}
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "sysAttMain.fdModelId = :fdModelId";
			hqlInfo.setParameter("fdModelId", modelId);
			if (StringUtil.isNotNull(modelName)) {
				whereBlock += " AND sysAttMain.fdModelName = :fdModelName";
				hqlInfo.setParameter("fdModelName", modelName);
			}
			if (StringUtil.isNotNull(key)) {
				whereBlock += " AND sysAttMain.fdKey = :fdKey";
				hqlInfo.setParameter("fdKey", key);
			}
			hqlInfo.setWhereBlock(whereBlock);
			Object obj = getSysAttMainService().findFirstOne(hqlInfo);
			if (obj!=null) {
				att = (SysAttMain) obj;
				if (logger.isDebugEnabled()) {
					if (att != null) {
                        logger
                                .debug("根据fdModelId、fdModelName、fdKey查找得到文档：fdModelId:"
                                        + modelId
                                        + "; fdModelName:"
                                        + modelName + "; fdKey" + key);
                    }
				}
			}
		}
		if (att != null) {
			att.setInputStream(getSysAttMainService().getInputStream(
					att.getFdId()));
		}
		return att;
	}
}
