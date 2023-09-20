package com.landray.kmss.km.archives.service;

import java.util.List;

import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public interface IKmArchivesDetailsService extends IExtendDataService {

    public abstract List<KmArchivesDetails> findByFdArchives(KmArchivesMain fdArchives) throws Exception;

	/**
	 * 根据借阅状态查找指定用户的的明细
	 * 
	 * @param fdBorrower
	 * @param fdStatus
	 * @return
	 * @throws Exception
	 */
	public abstract List<KmArchivesDetails>
			findByFdBorrower(SysOrgPerson fdBorrower, String[] fdStatus,
					String fdArchivesId)
					throws Exception;

	/**
	 * 借阅归还提醒
	 * 
	 * @throws Exception
	 */
	public void sendBorrowReturnWarn() throws Exception;

	/**
	 * 根据借阅申请ID删除借阅详细
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public void deleteByBorrowId(String fdId) throws Exception;

	/**
	 * 将已过期的借阅明细收回
	 * 
	 * @throws Exception
	 */
	public void borrowStatusSetExpeired() throws Exception;
}
