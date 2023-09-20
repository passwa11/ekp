package com.landray.kmss.km.imeeting.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;

/**
 * 会议室预约业务对象接口
 * 
 * @author 
 * @version 1.0 2014-07-21
 */
public interface IKmImeetingBookService extends IBaseService {
	
	public Map getDataById(String bookId) throws Exception;

	public void addExam(KmImeetingBook meetingBook) throws Exception;

	/**
	 * 查询非重复会议预定HQL构建
	 */
	public HQLInfo buildNormalBookHql(RequestContext requestContext)
			throws Exception;

	/**
	 * 查询非重复会议预定
	 */
	public List<KmImeetingBook> findNormalBook(RequestContext requestContext)
			throws Exception;

	/**
	 * 查询重复会议预定HQL构建
	 */
	public HQLInfo buildRangeBookHql(RequestContext requestContext)
			throws Exception;

	/**
	 * 查询重复会议预定
	 */
	public List<KmImeetingBook> findRangeBook(RequestContext requestContext)
			throws Exception;

	/**
	 * 删除
	 */
	public void delete(String id, RequestContext requestContext)
			throws Exception;

	public List<KmImeetingBook> findKmImeetingBook(RequestContext request,
			boolean showMy) throws Exception;

	public List<KmImeetingBook> findKmImeetingListBook(RequestContext request)
			throws Exception;

}
