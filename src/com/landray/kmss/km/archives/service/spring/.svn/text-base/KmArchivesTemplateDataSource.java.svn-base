package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesTemplateService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSourceWithRequest;

public class KmArchivesTemplateDataSource implements ICustomizeDataSourceWithRequest {

	private IKmArchivesMainService kmArchivesMainService = (IKmArchivesMainService) SpringBeanUtil
			.getBean("kmArchivesMainService");

	private IKmArchivesTemplateService kmArchivesTemplateService = (IKmArchivesTemplateService) SpringBeanUtil
			.getBean("kmArchivesTemplateService");

	private String fdMainId = null;

	@Override
	public void setRequest(ServletRequest request) {
		fdMainId = (String) request.getAttribute("fdMainId");
	}

	@Override
	public Map<String, String> getOptions() {

		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			List<KmArchivesTemplate> kmArchivesTemplateList = null;
			if (StringUtil.isNotNull(fdMainId)) {
				KmArchivesMain kmArchivesMain = (KmArchivesMain) kmArchivesMainService.findByPrimaryKey(fdMainId);
				KmArchivesDense kmArchivesDense = kmArchivesMain.getFdDense();
				if (kmArchivesDense != null) {
					List fdDenseIds = new ArrayList();
					fdDenseIds.add(kmArchivesDense.getFdId());
					kmArchivesTemplateList = kmArchivesTemplateService.getTemplateByDenses(fdDenseIds, false);
				} else {
					kmArchivesTemplateList = kmArchivesTemplateService.getTemplateByDenses(null, false);
				}
			}
			if (kmArchivesTemplateList == null || kmArchivesTemplateList.size() == 0) {
				kmArchivesTemplateList = kmArchivesTemplateService.getTemplateByDenses(null, true);
			}
			for (KmArchivesTemplate kmArchivesTemplate : kmArchivesTemplateList) {
				map.put(kmArchivesTemplate.getFdId(), kmArchivesTemplate.getFdName());
			}
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			return new HashMap<String, String>();
		}
		return map;
	}

	@Override
	public String getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}


}
