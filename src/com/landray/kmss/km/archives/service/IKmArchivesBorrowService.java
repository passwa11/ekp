package com.landray.kmss.km.archives.service;

import java.util.Date;
import java.util.List;

import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IKmArchivesBorrowService extends IExtendDataService {

    public abstract List<KmArchivesBorrow> findByDocTemplate(KmArchivesTemplate docTemplate) throws Exception;

    public abstract List<KmArchivesBorrow> findByFdBorrowDetails(KmArchivesDetails fdBorrowDetails) throws Exception;

	/**
	 * 根据档案ID删除借阅申请
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public void deleteByArchivesId(String fdId) throws Exception;
	
    /**
     * 获取(我已审)档案借阅统计数字
     * @param startTime  统计范围起始时间（可为空）
     * @param endTime 统计范围截至时间（可为空）  
     * @return
     * @throws Exception
     */	
	public Long getApprovedStatisticalCount(Date startTime, Date endTime) throws Exception;
}
