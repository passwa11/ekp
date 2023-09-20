package com.landray.kmss.sys.handover.support.config.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.springframework.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils;
import com.landray.kmss.sys.handover.support.config.catetemplate.CateItemHandler;
import com.landray.kmss.sys.recycle.util.SysRecycleUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * 文档权限处理器
 * 
 * @author 潘永辉 2017年7月5日
 *
 */
public class DocAuthItemHandler extends CateItemHandler {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(DocAuthItemHandler.class);

	public DocAuthItemHandler(String fdAttribute) {
		super(fdAttribute);
	}

	/**
	 * 由于文档数量较多，这里的查询需要使用SQL直接查询
	 */
	@Override
	public void search(HandoverSearchContext context) throws Exception {
		String property = getFdAttribute();

		if ("authReaders".equals(property)) {
			// 阅读权限：
			// 查找authAllReaders，如果有交接人有接收人，则不处理
			// 查找authAllReaders，如果有交接人无接收人，则增加接收人，并在authOtherReaders中增加接收人
			// 备注：若部署过程出现有authAllReaders，但无authOtherReaders的模块，需重新讨论方案
			
			getTotal(context, "authAllReaders");
		} else if ("authEditors".equals(property)) {
			// 编辑权限：
			// 查找authAllEditors，如果有交接人有接收人，则不处理
			// 查找authAllEditors，如果有交接人无接收人，则增加接收人，并在authOtherEditors中增加接收人，同时判断authAllReaders，若无接收人则追加
			// 备注：若部署过程出现有authAllEditors，但无authOtherEditors的模块，需重新讨论方案

			getTotal(context, "authAllEditors");
		} else if ("authLbpmReaders".equals(property)) {
			// 流程意见权限（与阅读权限平级）：
			// 查找lbpm_audit_note_reader，如果有交接人有接收人，则不处理
			// 查找lbpm_audit_note_reader，如果有交接人无接收人，则增加接收人
			// 查找lbpm_audit_note_rt_reader，如果有交接人有接收人，则不处理
			// 查找lbpm_audit_note_rt_reader，如果有交接人无接收人，则增加接收人

			String moduleName = context.getModule();

			String fromId = context.getHandoverOrg().getFdId();
			String toId = context.getRecipientOrg().getFdId();
			long total = getTotalForLbpm(moduleName, fromId, toId);
			context.setTotal(total);
		} else if ("authAttPrints".equals(property)) {
			// 附件可打印者：
			// 查找authAttPrints，如果有交接人有接收人，则不处理
			// 查找authAttPrints，如果有交接人无接收人，则增加接收人

			getTotal(context, "authAttPrints");
		} else if ("authAttCopys".equals(property)) {
			// 附件拷贝权限：
			// 查找authAttCopys，如果有交接人有接收人，则不处理
			// 查找authAttCopys，如果有交接人无接收人，则增加接收人

			getTotal(context, "authAttCopys");
		} else if ("authAttDownloads".equals(property)) {
			// 附件下载权限：
			// 查找authAttDownloads，如果有交接人有接收人，则不处理
			// 查找authAttDownloads，如果有交接人无接收人，则增加接收人

			getTotal(context, "authAttDownloads");
		}
	}

	/**
	 * 获取流程意见权限交接数量
	 * 
	 * @param context
	 * @throws Exception
	 */
	private long getTotalForLbpm(String moduleName, String fromId, String toId) throws Exception {
		String[] tableNames = { "lbpm_audit_note_reader", "lbpm_audit_note_rt_reader" };
		String sql = "select count(*) from {table} r, lbpm_process p where r.fd_org_id = ? and r.fd_process_id = p.fd_id and p.fd_model_name = ? "
				+ "and r.fd_note_id not in (select distinct r1.fd_note_id from {table} r1, lbpm_process p1 where p1.fd_model_name = ? and r1.fd_org_id = ? and r1.fd_process_id = p1.fd_id) ";
		long total = 0;

		for (String tableName : tableNames) {
			List<?> list = getBaseDao().getHibernateSession().createNativeQuery(StringUtil.replace(sql, "{table}", tableName)).setString(0, fromId).setString(1, moduleName).setString(2, moduleName).setString(3, toId).list();
			total += Long.valueOf(list.get(0).toString());
		}
		
		return total;
	}

	/**
	 * 按权限类型获取需要交接的数量
	 * 
	 * @param context
	 * @param propertyMap
	 * @param property
	 * @throws Exception
	 */
	private void getTotal(HandoverSearchContext handoverSearchcontext, String property) throws Exception {
		String moduleName = handoverSearchcontext.getModule();
		SysDictModel dictModel = SysDataDict.getInstance().getModel(moduleName);
		Map<String, SysDictCommonProperty> propertyMap = dictModel.getPropertyMap();

		SysDictCommonProperty commonProperty = propertyMap.get(property);
		if (commonProperty != null && commonProperty instanceof SysDictListProperty) {
			SysDictListProperty listProperty = (SysDictListProperty) commonProperty;

			String fromId = handoverSearchcontext.getHandoverOrg().getFdId();
			String toId = handoverSearchcontext.getRecipientOrg().getFdId();
			String childQuerySql = buildWhereSql(listProperty, dictModel);
			
			String sql = "select count(*) from ("+childQuerySql.toString()+") a ";
			
			if (logger.isDebugEnabled()) {
				logger.debug("“文档权限”查询SQL：" + sql + "参数：[" + fromId + "," + toId + "]");
			}

			List<?> list = getBaseDao().getHibernateSession().createNativeQuery(sql).setString(0, fromId).setString(1, toId).list();
			handoverSearchcontext.setTotal(Long.valueOf(list.get(0).toString()));
		}
	}

	private String buildWhereSql(SysDictListProperty listProperty, SysDictModel dictModel) throws Exception {
		String tableName = listProperty.getTable(); // 取表名
		String elementColumn = listProperty.getElementColumn(); // 取机构字段名
		String docColumn = listProperty.getColumn(); // 取主文档字段名
		StringBuffer sql = new StringBuffer(5000);

		sql.append(" select distinct ").append(docColumn);
		sql.append(" from ").append(tableName)
				.append(" where ").append(elementColumn).append(" = ?")
				.append(" and ").append(docColumn).append(" not in ")
				.append("(select distinct ").append(docColumn)
				.append(" from ").append(tableName)
				.append(" where ").append(elementColumn).append(" = ?) ");

//		用于额外的查询条件，
//		比如：KmsMultidocKnowledge和KmsWikiMain，共用了一张权限表，为了区分交接的数据来由自哪个类，额外的条件
//		KmsMultidocKnowledge --> and fd_doc_id in (select distinct fd_fk_id from kms_multidoc_knowledge)
//		KmsWikiMain --> and fd_doc_id in (select distinct fd_fk_id from kms_wiki_main)
//		还比如：KmAssetApplyChange、KmAssetApplyDeal、KmAssetApplyDivert.....共用了一张表，为了区分数据的严，也需要加入额外的条件
//		KmAssetApplyChange --> and subclass_modelname='com.landray.kmss.km.asset.model.KmAssetApplyChange' 
//		或
//		KmAssetApplyDeal --> and subclass_modelname='com.landray.kmss.km.asset.model.KmAssetApplyDeal' 
//		......
		
		String moduleName = dictModel.getModelName();
		String subSql = HandoverPluginUtils.getAuthSubSql(moduleName);
		if (StringUtil.isNotNull(subSql)) {
			sql.append(subSql);
		}
		
		// 排除已被软件删除的数据
		if (SysRecycleUtil.isEnableSoftDelete(dictModel.getModelName())) {
			String deltelColumn = null;
			String __tableName = null;
			try {
				deltelColumn = HibernateUtil.getColumnName(com.landray.kmss.util.ClassUtils.forName(moduleName), "docDeleteFlag");
				__tableName = HibernateUtil.getTableName(com.landray.kmss.util.ClassUtils.forName(moduleName));
			} catch (Exception e) {
				try {
					deltelColumn = HibernateUtil.getColumnName(com.landray.kmss.util.ClassUtils.forName(dictModel.getExtendClass()), "docDeleteFlag");
					__tableName = HibernateUtil.getTableName(com.landray.kmss.util.ClassUtils.forName(dictModel.getExtendClass()));
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
			if (deltelColumn != null && __tableName != null) {
				sql.append(" and ").append(docColumn)
				.append(" in (select distinct fd_id from ").append(__tableName).append(" where ").append(deltelColumn).append(" = 0)");
			}
		}
		
		return sql.toString();
	}

	/**
	 * 查询文档明细
	 * @param hqlInfo
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	public Page detail(HQLInfo hqlInfo, RequestContext requestContext) throws Exception {
		String fromOrgId = requestContext.getParameter("fdFromId");
		String toOrgId = requestContext.getParameter("fdToId");
		String moduleName = requestContext.getParameter("moduleName");
		String item = requestContext.getParameter("item");
		SysDictModel dictModel = SysDataDict.getInstance().getModel(moduleName);
		Page page = null;
		
		if ("authReaders".equals(item)) {
			// 阅读权限：
			// 查找authAllReaders，如果有交接人有接收人，则不处理
			// 查找authAllReaders，如果有交接人无接收人，则增加接收人，并在authOtherReaders中增加接收人
			// 备注：若部署过程出现有authAllReaders，但无authOtherReaders的模块，需重新讨论方案

			page = getDetail(hqlInfo, dictModel, "authAllReaders", fromOrgId, toOrgId, requestContext);
		} else if ("authEditors".equals(item)) {
			// 编辑权限：
			// 查找authAllEditors，如果有交接人有接收人，则不处理
			// 查找authAllEditors，如果有交接人无接收人，则增加接收人，并在authOtherEditors中增加接收人，同时判断authAllReaders，若无接收人则追加
			// 备注：若部署过程出现有authAllEditors，但无authOtherEditors的模块，需重新讨论方案

			page = getDetail(hqlInfo, dictModel, "authAllEditors", fromOrgId, toOrgId, requestContext);
		} else if ("authLbpmReaders".equals(item)) {
			// 流程意见权限（与阅读权限平级）：
			// 查找lbpm_audit_note_reader，如果有交接人有接收人，则不处理
			// 查找lbpm_audit_note_reader，如果有交接人无接收人，则增加接收人
			// 查找lbpm_audit_note_rt_reader，如果有交接人有接收人，则不处理
			// 查找lbpm_audit_note_rt_reader，如果有交接人无接收人，则增加接收人
			
			String modelTable = dictModel.getTable(); // 获取model对应的数据库实体表名
			String titleSearchText = requestContext.getParameter("q.docSubject"); // 前端搜索框输入的模糊搜索关键字
			String searchColumn = null;
			if(StringUtil.isNotNull(titleSearchText)){
				titleSearchText = titleSearchText.trim();
				searchColumn = this.getDisplayPropertyColumn(dictModel, moduleName); // 获取数据库显示字段名称用于进行搜索where条件拼接
			}
			
			StringBuffer sql = new StringBuffer();
			String[] tableNames = { "lbpm_audit_note_reader", "lbpm_audit_note_rt_reader" };
			for(int i=0;i<tableNames.length;i++) {
				String table = tableNames[i];
				sql.append("select p.fd_id, p.fd_model_name, r.fd_note_id, '").append(table).append("', n.fd_fact_node_id, n.fd_fact_node_name ");
				sql.append("from ").append(table).append(" r, lbpm_process p, lbpm_audit_note n,");
				sql.append(modelTable).append(" m ");
				sql.append("where r.fd_org_id = :fromOrgId ");
				sql.append("and r.fd_process_id = p.fd_id and n.fd_id = r.fd_note_id ");
				sql.append("and p.fd_model_id = m.fd_id ");
				if(StringUtil.isNotNull(searchColumn)){
					sql.append("and m.").append(searchColumn).append(" like :titleSearchText ");
				}
				sql.append("and p.fd_model_name = :moduleName ");
				sql.append("and r.fd_note_id not in (select distinct r1.fd_note_id from ")
				.append(table).append(" r1, lbpm_process p1 where p1.fd_model_name = :moduleName and r1.fd_org_id = :toOrgId and r.fd_process_id = p.fd_id)");
				
				if (i < tableNames.length - 1) {
                    sql.append(" union ");
                }
			}
			
			page = new Page();
			page.setRowsize(hqlInfo.getRowSize());
			page.setPageno(hqlInfo.getPageNo());
			page.setTotalrows((int) getTotalForLbpm(moduleName, fromOrgId, toOrgId));
			page.excecute();
			Query q = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
			q.setString("moduleName", moduleName);
			q.setString("fromOrgId", fromOrgId);
			q.setString("toOrgId", toOrgId);
			if(StringUtil.isNotNull(searchColumn)){
				q.setString("titleSearchText", titleSearchText);
			}
			q.setFirstResult(page.getStart());
			q.setMaxResults(page.getRowsize());
			page.setList(q.list());
			return page;
		} else if ("authAttPrints".equals(item)) {
			// 附件可打印者：
			// 查找authAttPrints，如果有交接人有接收人，则不处理
			// 查找authAttPrints，如果有交接人无接收人，则增加接收人

			page = getDetail(hqlInfo, dictModel, "authAttPrints", fromOrgId, toOrgId, requestContext);
		} else if ("authAttCopys".equals(item)) {
			// 附件拷贝权限：
			// 查找authAttCopys，如果有交接人有接收人，则不处理
			// 查找authAttCopys，如果有交接人无接收人，则增加接收人

			page = getDetail(hqlInfo, dictModel, "authAttCopys", fromOrgId, toOrgId, requestContext);
		} else if ("authAttDownloads".equals(item)) {
			// 附件下载权限：
			// 查找authAttDownloads，如果有交接人有接收人，则不处理
			// 查找authAttDownloads，如果有交接人无接收人，则增加接收人

			page = getDetail(hqlInfo, dictModel, "authAttDownloads", fromOrgId, toOrgId, requestContext);
		}
		return page;
	}
	
	
	/**
	 * 获取model对应的实体数据库表的显示字段名称
	 * @param dictModel
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	private String getDisplayPropertyColumn(SysDictModel dictModel,String modelName) throws Exception {
		// 获取显示字段的属性名称（数据字典配置中的displayProperty）
		String disProName = dictModel.getDisplayProperty();
		if(StringUtil.isNull(disProName)){
			Object modelInstance = com.landray.kmss.util.ClassUtils.forName(modelName).newInstance();
			boolean bool = PropertyUtils.isReadable(modelInstance, "docSubject");
			if(bool){
				disProName = "docSubject";
			}else{
				bool = PropertyUtils.isReadable(modelInstance, "fdName");
				disProName = "fdName";
			}
		}		
		String column = null;
		SysDictCommonProperty property = dictModel.getPropertyMap().get(disProName);
		if(property!=null){
			column = property.getColumn();
					
		}
		return column;
	}

	private Page getDetail(HQLInfo hqlInfo, SysDictModel dictModel, String item, String fromOrgId, String toOrgId, RequestContext requestContext) throws Exception {
		String moduleName = dictModel.getModelName();
		SysDictCommonProperty commonProperty = dictModel.getPropertyMap().get(item);
		
		List<String> docIds = new ArrayList<String>();
		Page page = new Page();
		page.setRowsize(hqlInfo.getRowSize());
		page.setPageno(hqlInfo.getPageNo());
		if (commonProperty != null && commonProperty instanceof SysDictListProperty) {
			SysDictListProperty listProperty = (SysDictListProperty) commonProperty;
			
			StringBuffer childQuerySql = new StringBuffer(this.buildWhereSql(listProperty, dictModel));
			
			// 前端筛选器传递的查询参数拼接过滤SQL
			boolean isNeedSearch = false;
			String titleSearchText = requestContext.getParameter("q.docSubject"); // 前端搜索框输入的模糊搜索关键字
			if(StringUtil.isNotNull(titleSearchText)){
				titleSearchText = titleSearchText.trim();
				String searchColumn = null;
				if(StringUtil.isNotNull(titleSearchText)){
					searchColumn = this.getDisplayPropertyColumn(dictModel, moduleName); // 获取数据库显示字段名称用于进行搜索where条件拼接
				}
				if(StringUtil.isNotNull(searchColumn)){
					childQuerySql.append(" and ").append(listProperty.getColumn())
					.append(" in (select distinct fd_id from ").append(dictModel.getTable()).append(" where ").append(searchColumn).append(" like ? )");
					isNeedSearch = true;
				}
				
			}

			String countSql = "select count(*) from (" + childQuerySql.toString() +") a ";

			NativeQuery countQuery = getBaseDao().getHibernateSession().createNativeQuery(countSql);
			countQuery.setString(0, fromOrgId);
			countQuery.setString(1, toOrgId);
			if(isNeedSearch){
				countQuery.setString(2, "%"+titleSearchText+"%");
			}
			List<?> total = countQuery.list();
			
			page.setTotalrows(Long.valueOf(total.get(0).toString()).intValue());
			page.excecute();
			

			String listSql = "select " + listProperty.getColumn() + " from (" + childQuerySql.toString() +") a ";

			if (logger.isDebugEnabled()) {
				logger.debug("“文档权限”查询SQL：" + listSql + "参数：[" + fromOrgId + "," + toOrgId + "]");
			}
			
			NativeQuery listQuery = getBaseDao().getHibernateSession().createNativeQuery(listSql);
			listQuery.setString(0, fromOrgId);
			listQuery.setString(1, toOrgId);
			if(isNeedSearch){
				listQuery.setString(2, "%"+titleSearchText+"%");
			}
			listQuery.setFirstResult(page.getStart());
			listQuery.setMaxResults(page.getRowsize());
			
			List<?> list = listQuery.list();
			
			if (list != null && !list.isEmpty()) {
				for (Object obj : list) {
					if (obj instanceof String) {
						docIds.add(obj.toString());
					} else if (obj instanceof Object[]) {
						Object[] o = (Object[]) obj;
						docIds.add(o[0].toString());
					}
				}
			}
		}

		HQLInfo pageInfo = new HQLInfo();
		String tableName = ModelUtil.getModelTableName(moduleName);
		pageInfo.setModelName(moduleName);
		pageInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		
		List<?> list = null;
		if(docIds.isEmpty()){
			list = new ArrayList<>();
		}else{
			HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN(tableName + ".fdId", "doc" + "0_", docIds);
			pageInfo.setWhereBlock(hqlWrapper.getHql());
			pageInfo.setParameter(hqlWrapper.getParameterList());
			list = getBaseDao().findList(pageInfo);			
		}

		page.setList(list);
		
		return page;
	}

}
