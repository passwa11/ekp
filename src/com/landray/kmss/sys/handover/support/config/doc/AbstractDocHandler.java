package com.landray.kmss.sys.handover.support.config.doc;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.handover.support.config.doc.handler.IDocHandler;
import com.landray.kmss.sys.handover.support.util.HandModelUtil;
import com.landray.kmss.sys.handover.support.util.ListSplitUtil;
import com.landray.kmss.sys.handover.support.util.ProcessNodeUtil;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmRtNodeHandlersDefine;
import com.landray.kmss.sys.lbpmservice.support.util.LbpmTemplateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDocHandler implements IHandoverHandler, IDocHandler {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractDocHandler.class);
	
	/* 当前处理人 */
	public static final String HANDLER = "handlerIds";
	/* 备选处理人 */
	public static final String HANDLER_OPT = "optHandlerIds";
	/* 未来处理人 */
	public static final String HANDLER_LATER = "handlerIds_later";
	/* 特权人 */
	public static final String ATT_PRIVILEGERIDS = "privilegerIds";
	/* 意见可阅读者 */
	public static final String TOHER_CAN_VIEW = "otherCanViewCurNodeIds";

	public abstract String getFdAttribute();
	
	/**
	 * 获取条件查询语句
	 * 
	 * @param searchEnd 是否查询已结束节点，如果意见可阅读者是查询已结束节点
	 * @return
	 */
	private String getWhereBlock(String modelTable) {
		String whereBlock = "lbpmRtNodeHandlersDefine.fdProcess.fdId = lbpmProcess.fdId "
				+ "and lbpmRtNodeHandlersDefine.fdHandler.fdId = :orgId "
				+ "and lbpmRtNodeHandlersDefine.fdAttribute = :fdAttribute "
				+ "and lbpmProcess.fdModelName = :fdModelName "
				+ "and lbpmProcess.fdModelId = " + modelTable + ".fdId "
				
				// 过滤废弃的流程
				+ " and lbpmProcess.fdStatus != '00' "
				// 过滤结束的流程
				+ " and lbpmProcess.fdStatus != '30'"
				// 过滤当前处理人
				+ " and lbpmRtNodeHandlersDefine.fdId not in "
					+ "(select define.fdId from LbpmExpecterLog expecterLog,  LbpmRtNodeHandlersDefine define "
					+ "where expecterLog.fdProcessId = define.fdProcess.fdId "
					+ "and expecterLog.fdFactId = define.fdFactId "
					+ "and expecterLog.fdHandler = define.fdHandler "
				    + "and define.fdProcess = lbpmRtNodeHandlersDefine.fdProcess "
					+ "and define.fdAttribute = 'handlerIds' "
					+ "and expecterLog.fdIsActive = true) "
				// 过滤流程异常回滚到起草节点的流程
				+ " and lbpmRtNodeHandlersDefine.fdId not in "
					+ "(select define.fdId from LbpmExpecterLog expecterLog,  LbpmRtNodeHandlersDefine define "
					+ "where expecterLog.fdProcessId = define.fdProcess.fdId "
					+ "and expecterLog.fdFactId = 'N2' "
					+ "and define.fdProcess.docStatus !='11' "
					+ "and define.fdAttribute = 'handlerIds' "
					+ "and define.fdProcess = lbpmRtNodeHandlersDefine.fdProcess "
					+ "and expecterLog.fdIsActive = true) "
				// 过滤结束的节点
				+ " and lbpmRtNodeHandlersDefine.fdId not in "
					+ "(select define.fdId from LbpmAuditNote note, LbpmRtNodeHandlersDefine define "
					+ "where note.fdProcess = define.fdProcess and note.fdActionKey is not null "
					+ "and note.fdFactNodeId = define.fdFactId "
					+ "and define.fdProcess.docStatus !='11' "
					+ "and define.fdProcess = lbpmRtNodeHandlersDefine.fdProcess) ";
		return whereBlock;
	}
	
	/**
	 * 构造查询数量及更新HQL，提供给“备选处理人”，“未来处理人”，“特权人”，“意见可阅读者”使用
	 * 
	 * @param selectBlock
	 * @param modelName
	 * @param orgId
	 * @param attribute
	 * @param searchEnd 是否查询已结束节点，如果意见可阅读者是查询已结束节点
	 * @return
	 */
	public HQLInfo buildHQLInfo(String selectBlock, String modelName, String orgId, String attribute) {
		String modelTable = ModelUtil.getModelTableName(modelName);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(modelName);
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setFromBlock("LbpmRtNodeHandlersDefine lbpmRtNodeHandlersDefine");
		hqlInfo.setJoinBlock(", LbpmProcess lbpmProcess, " + modelName + " " + modelTable);
		hqlInfo.setWhereBlock(getWhereBlock(modelTable));

		hqlInfo.setParameter("fdAttribute", attribute);
		hqlInfo.setParameter("fdModelName", modelName);
		hqlInfo.setParameter("orgId", orgId);
		
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(Integer.MAX_VALUE);
		return hqlInfo;
	}
	
	/**
	 * 构造明细表HQL，提供给“备选处理人”，“未来处理人”，“特权人”，“意见可阅读者”使用
	 * 
	 * @param hqlInfo
	 * @param modelName
	 * @param orgId
	 * @param attribute
	 * @param searchEnd
	 *            是否查询已结束节点，如果意见可阅读者是查询已结束节点
	 */
	public void buildDetailHQLInfo(HQLInfo hqlInfo, String modelName, String orgId, String attribute, RequestContext requestContext) throws Exception {
		String modelTable = ModelUtil.getModelTableName(modelName);
		StringBuffer buff = new StringBuffer();
		if (StringUtil.isNotNull(hqlInfo.getJoinBlock())) {
			buff.append(hqlInfo.getJoinBlock());
		}
		buff.append(", LbpmProcess lbpmProcess, ").append(modelName).append(" ").append(modelTable);
		hqlInfo.setJoinBlock(buff.toString());
		hqlInfo.setModelName("LbpmRtNodeHandlersDefine");
		String whereBlock = StringUtil.linkString(hqlInfo.getWhereBlock()," and ",getWhereBlock(modelTable));
	
		hqlInfo.setWhereBlock(whereBlock);
		this.buildSearchFilterWhereBlock(hqlInfo, modelTable, modelName, requestContext);
		hqlInfo.setParameter("fdAttribute", attribute);
		hqlInfo.setParameter("fdModelName", modelName);
		hqlInfo.setParameter("orgId", orgId);

		hqlInfo.setOrderBy(modelTable + ".fdId desc, lbpmRtNodeHandlersDefine.fdFactId");
	}
	
	
	/**
	 * 构建前端筛选器需要拼装的过滤HQL(例如：通过文档标题进行搜索)
	 * @param hqlInfo
	 * @param modelTable
	 * @param modelName
	 * @param requestContext
	 * @throws Exception
	 */
	public void buildSearchFilterWhereBlock(HQLInfo hqlInfo, String modelTable, String modelName, RequestContext requestContext) throws Exception{
	    StringBuffer sb = new StringBuffer();	
	    String titleSearchText = requestContext.getParameter("q.docSubject"); // 前端搜索框输入的模糊搜索关键字
		if(StringUtil.isNotNull(titleSearchText)){
			titleSearchText = titleSearchText.trim();
			// 获取显示字段的属性名称（数据字典配置中的displayProperty）
			String colName = SysDataDict.getInstance().getModel(modelName).getDisplayProperty();
			if(StringUtil.isNull(colName)){
				Object modelInstance = com.landray.kmss.util.ClassUtils.forName(modelName).newInstance();
				boolean bool = PropertyUtils.isReadable(modelInstance, "docSubject");
				if(bool){
					colName = "docSubject";
				}else{
					bool = PropertyUtils.isReadable(modelInstance, "fdName");
					colName = "fdName";
				}
			}
			if(StringUtil.isNotNull(colName)){
				String whereBlock = hqlInfo.getWhereBlock();
				if(StringUtil.isNotNull(whereBlock)){
					sb.append(whereBlock).append(" and");
				}
				sb.append(" ").append(modelTable).append(".").append(colName).append(" like :titleSearchText");
				hqlInfo.setWhereBlock(sb.toString());
				hqlInfo.setParameter("titleSearchText", "%"+titleSearchText+"%");
			}
		}	   
	}
	
	
	/**
	 * 执行交接
	 * @param context
	 * @param attribute
	 * @param errorMsg
	 * @param searchEnd 是否查询已结束节点，如果意见可阅读者是查询已结束节点
	 * @throws Exception
	 */
	public void execute(HandoverExecuteContext context, String attribute, String errorMsg) throws Exception {
		HQLInfo hqlInfo = buildHQLInfo("lbpmRtNodeHandlersDefine", context.getModule(), context.getFrom().getFdId(), attribute);

		List<String> selectedRecordIds = context.getSelectedRecordIds();
		if (selectedRecordIds != null && selectedRecordIds.size() > 0) {
			if (selectedRecordIds.size() >= 1000) {
				//list大于1000时拆分 #110385
				StringBuffer _whereBlock = new StringBuffer(hqlInfo.getWhereBlock());
				List<List<String>> newList = ListSplitUtil.splitList(selectedRecordIds, ListSplitUtil.MAX_LENGTH);
				for(int i = 0;i < newList.size();i++) {
					if(i == 0) {
						_whereBlock.append(" and (lbpmRtNodeHandlersDefine.fdId in (:selectedRecordIds").append("_").append(i).append(")");
					} else {
						_whereBlock.append(" or lbpmRtNodeHandlersDefine.fdId in (:selectedRecordIds").append("_").append(i).append(")");
					}

					String param = "selectedRecordIds_"+i;
					hqlInfo.setParameter(param, newList.get(i));
				}
				_whereBlock.append(")");
				hqlInfo.setWhereBlock(_whereBlock.toString());
			}else {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						HQLUtil.buildLogicIN("lbpmRtNodeHandlersDefine.fdId", selectedRecordIds)));
			}
		}

		List<?> result = getBaseDao().findPage(hqlInfo).getList();
		long successCount = 0L;
		// 处理可交接的节点
		List<String> handerIds = new ArrayList<String>();
		for (Object obj : result) {
			LbpmRtNodeHandlersDefine define = (LbpmRtNodeHandlersDefine) obj;
			handerIds.add(define.getFdId());
			
			if (context.getTo() == null) {
				// 接收人为空，删除节点处理人
				getBaseDao().delete(define);
			} else {
				define.setFdHandler(context.getTo());
				getBaseDao().update(define);
				
				// 删除重复数据
				deleteRepeat(define);
			}
			successCount += addLog(context,define,SysHandoverConfigLogDetail.STATE_SUCC,errorMsg);
		}
		context.setSuccTotal(successCount);
		
		// 处理忽略的节点记录
		long ignoreCount = 0L;
		List<String> ignoreIds = null;
		if (handerIds.size() > 0) {
			ignoreIds = new ArrayList<String>();
			for (String id : selectedRecordIds) {
				if (!handerIds.contains(id)) {
					ignoreIds.add(id);
				}
			}
		} else {
			ignoreIds = selectedRecordIds;
		}
		
		for (String id : ignoreIds) {
			if (StringUtil.isNull(id)) {
				continue;
			}
			
			LbpmRtNodeHandlersDefine define = (LbpmRtNodeHandlersDefine) getBaseDao().findByPrimaryKey(id, LbpmRtNodeHandlersDefine.class, true);
			ignoreCount += addLog(context, define, SysHandoverConfigLogDetail.STATE_IGNORE, errorMsg);
		}
		context.setIgnoreTotal(ignoreCount);
	}
	
	/**
	 * 交接后，可能会存在节点重复处理人，此时需要删除重复的处理人
	 * 
	 * @param define
	 */
	@SuppressWarnings("unchecked")
	private void deleteRepeat(LbpmRtNodeHandlersDefine define) throws Exception {
		HQLInfo hqlRepeat = new HQLInfo();
		hqlRepeat.setModelName("com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmRtNodeHandlersDefine");
		hqlRepeat.setFromBlock("LbpmRtNodeHandlersDefine lbpmRtNodeHandlersDefine");
		hqlRepeat.setWhereBlock("lbpmRtNodeHandlersDefine.fdAttribute = :fdAttribute " 
				+ "and lbpmRtNodeHandlersDefine.fdFactId = :fdFactId " 
				+ "and lbpmRtNodeHandlersDefine.fdProcess = :fdProcess " 
				+ "and lbpmRtNodeHandlersDefine.fdHandler = :fdHandler");
		hqlRepeat.setParameter("fdAttribute", define.getFdAttribute());
		hqlRepeat.setParameter("fdFactId", define.getFdFactId());
		hqlRepeat.setParameter("fdProcess", define.getFdProcess());
		hqlRepeat.setParameter("fdHandler", define.getFdHandler());
		hqlRepeat.setOrderBy("lbpmRtNodeHandlersDefine.fdIndex");
		hqlRepeat.setPageNo(1);
		hqlRepeat.setRowSize(Integer.MAX_VALUE);
		List<LbpmRtNodeHandlersDefine> repeat = getBaseDao().findPage(hqlRepeat).getList();
		if (repeat.size() > 1) {
			for (int i = 1; i < repeat.size(); i++) {
				getBaseDao().delete(repeat.get(i));
			}
		}
	}
	
	private int addLog(HandoverExecuteContext context, LbpmRtNodeHandlersDefine define, Integer fdState, String errorMsg) {
		int count = 0;
		try {
			// 记录日志
			String docId = define.getFdProcess().getFdModelId();
			String modelName = define.getFdProcess().getFdModelName();
			IBaseModel model = getBaseDao().findByPrimaryKey(docId, modelName, true);
			String docSubject = LbpmTemplateUtil.getDocSubject(model);
			String url = HandModelUtil.getUrl(model);
			String desc = docSubject;
			context.log(define.getFdProcess().getFdId(), context.getModule(), desc, url, ProcessNodeUtil.getProcessNodeName(define.getFdProcess().getFdId(), define.getFdFactId()), fdState);
			count = 1;
		} catch (Exception e) {
			// 出错，跳过不计数
			logger.error("processId:" + define.getFdProcess().getFdId() + errorMsg, e);
		}
		return count;
	}
	
	private IBaseDao baseDao = null;

	public IBaseDao getBaseDao() {
		if (baseDao == null) {
			baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		}
		return baseDao;
	}
}
