package com.landray.kmss.third.ekp.java.tag;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.tag.model.SysTagMain;
import com.landray.kmss.sys.tag.model.SysTagMainRelation;
import com.landray.kmss.sys.tag.service.ISysTagMainService;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.tag.client.TagAddContext;
import com.landray.kmss.util.DateUtil;

public class SysTagSyncTask implements ISysTagSyncTask {

	private static final String FD_KEY = "com.landray.kmss.third.ekp.java.tag.SysTagSyncTask";
	private static final String FD_FIELD = "tagSync";
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private ISysTagMainService sysTagMainService;

	public void setSysTagMainService(ISysTagMainService sysTagMainService) {
		this.sysTagMainService = sysTagMainService;
	}

	private ISysAppConfigService sysAppConfigService;

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	private ISysTagWebServiceClient sysTagWebServiceClient;

	public void setSysTagWebServiceClient(
			ISysTagWebServiceClient sysTagWebServiceClient) {
		this.sysTagWebServiceClient = sysTagWebServiceClient;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	@Override
    @SuppressWarnings("unchecked")
	public void sync() throws Exception {

		EkpJavaConfig config = new EkpJavaConfig();
		String enable = config.getValue("kmss.tag.java.enabled");
		if (!"true".equals(enable)) {
            return;
        }

		Date startDate = null;
		Date endDate = new Date();

		Map<String, Object> configMap = sysAppConfigService.findByKey(FD_KEY);
		Object value = configMap.get(FD_FIELD);

		HQLInfo hqlInfo = new HQLInfo();

		String whereblock = "sysTagMain.docAlterTime <:endTime and sysTagMain.docStatus=:docStatus";

		if (value != null) {
			startDate = DateUtil.convertStringToDate(value.toString(),
					DATE_PATTERN);
			whereblock += " and sysTagMain.docAlterTime >=:startTime ";
			hqlInfo.setParameter("startTime", startDate);
		}

		hqlInfo.setWhereBlock(whereblock);
		hqlInfo.setParameter("endTime", endDate);
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);

		List<SysTagMain> tags = sysTagMainService.findList(hqlInfo);
		TagAddContext context = new TagAddContext();
		context.setAppName(config.getValue("kmss.java.system.name"));

		JSONArray jTags = new JSONArray();

		for (SysTagMain tag : tags) {

			JSONObject jTag = new JSONObject();

			jTag.element("docAlterTime", DateUtil.convertDateToString(
					tag.getDocAlterTime(), DATE_PATTERN));
			jTag.element("docCreateTime", DateUtil.convertDateToString(
					tag.getDocCreateTime(), DATE_PATTERN));

			if (tag.getDocCreator() != null) {
				SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
						.findByPrimaryKey(tag.getDocCreator().getFdId());
				jTag.element("docCreatorName", person.getFdLoginName());
			}
			jTag.element("docStatus", tag.getDocStatus());
			jTag.element("docSubject", tag.getDocSubject());
			jTag.element("fdId", tag.getFdId());
			jTag.element("fdKey", tag.getFdKey());
			jTag.element("fdModelId", tag.getFdModelId());
			jTag.element("fdModelName", tag.getFdModelName());
			jTag.element("fdQueryCondition", tag.getFdQueryCondition());
			jTag.element("fdUrl", tag.getFdUrl());

			List<SysTagMainRelation> relations = tag
					.getSysTagMainRelationList();
			String fdTagNames = "";

			for (int i = 0; i < relations.size(); i++) {
				SysTagMainRelation relation = relations.get(i);

				fdTagNames += (i == 0 ? relation.getFdTagName()
						: (" " + relation.getFdTagName()));

			}

			jTag.element("fdTagNames", fdTagNames);

			jTags.add(jTag);

		}

		context.setTags(jTags.toString());

		sysTagWebServiceClient.addTags(context);

		Map<String, Object> valueMap = new HashMap<String, Object>();
		String endDateStr = DateUtil.convertDateToString(endDate, DATE_PATTERN);

		valueMap.put(FD_FIELD, endDateStr);
		sysAppConfigService.add(FD_KEY, valueMap);

	}
}
