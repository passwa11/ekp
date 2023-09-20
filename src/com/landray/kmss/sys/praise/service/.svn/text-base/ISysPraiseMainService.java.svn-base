package com.landray.kmss.sys.praise.service;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.service.IBaseCoreInnerService;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.praise.model.SysPraiseMain;

public interface ISysPraiseMainService extends IBaseCoreInnerService{
	/**
	 * 获得赞的数量
	 * @param request
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	public Integer getPraiseCount(String fdModelId,String fdModelName,String fdType)
				throws Exception;
	
	/**
	 *  该人员是否已赞过该文档
	 * @param praiserId
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	public Boolean checkPraised(String praiserId,String fdModelId,String fdModelName,String fdType)
				throws Exception;
	
	/**
	 *  获取该人员已赞的点赞记录id
	 * @param praiserId
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	public String getPraiseId(String praiserId,String fdModelId,String fdModelName,String fdType)
				throws Exception;
	
	public List checkPraisedByIds(String praiserId,String fdModelIds,String fdModelName)
				throws Exception;
	
	/**
	 * 点赞或取消点赞
	 * @param fdModelId
	 * @param fdModelName
	 * @param fdType 
	 * @return
	 * @throws Exception
	 */
	public void addOrDel(String fdModelId, String fdModelName,String fdType, RequestContext requestContext) throws Exception;

	public void updatePraiseCount(IBaseService service, BaseModel docbase,
			String string,String fdType) throws Exception;

	/**
	 *  该人员是否已赞或者踩过该文档
	 * @param praiserId
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception 
	 */
	public List<SysPraiseMain> checkPraiseAndNegativeByIds(String fdId, String fdModelIds,
			String fdModelName) throws Exception;
}
