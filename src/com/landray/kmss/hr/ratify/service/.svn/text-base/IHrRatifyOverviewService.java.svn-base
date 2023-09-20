package com.landray.kmss.hr.ratify.service;

import java.util.List;

import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyTemPre;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IHrRatifyOverviewService extends IExtendDataService {
	public List<HrRatifyMain> getLatestDoc() throws Exception;

	public List<HrRatifyMain> getHotDoc() throws Exception;

	public String updateReview() throws Exception;

	public String getReviewPre() throws Exception;

	public List<HrRatifyTemPre> getContentList() throws Exception;

	public Integer getDocAmount(SysCategoryMain sysCategoryMain)
			throws Exception;

	public Integer getDocAmount(String templateId) throws Exception;

	public List getSecTemplateList(String categoryId) throws Exception;

	public List getSecCategoryList(String categoryId) throws Exception;
}
