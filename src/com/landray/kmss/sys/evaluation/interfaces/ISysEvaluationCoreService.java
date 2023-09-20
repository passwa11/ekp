package com.landray.kmss.sys.evaluation.interfaces;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.service.ICoreOuterService;

public interface ISysEvaluationCoreService extends ICoreOuterService {
	public List getEvaluationList(List modelNameList, Date startDate, Date endDate, boolean isNewVersion) throws Exception;	
}
