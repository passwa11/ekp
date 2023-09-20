package com.landray.kmss.sys.handover.support.config.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 事项交接处理接口
 * 
 * @author 潘永辉 2018年12月13日
 *
 */
public abstract class AbstractItemHandler implements IHandoverHandler {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractItemHandler.class);
	
	/**
	 * 替换交接人
	 */
	public static final Integer EXEC_MODE_REPLACE = 0;
	/**
	 * 追加交接人
	 */
	public static final Integer EXEC_MODE_APPEND = 1;

	private String fdAttribute;

	public AbstractItemHandler(String fdAttribute) {
		this.fdAttribute = fdAttribute;
	}

	public String getFdAttribute() {
		return fdAttribute;
	}

	private IBaseService baseService;

	public IBaseService getBaseService() {
		if (baseService == null) {
			baseService = (IBaseService) SpringBeanUtil.getBean("KmssParentService");
		}
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	
	private ISysOrgCoreService sysOrgCoreService;
	
	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	/**
	 * 查询交接数量
	 */
	@Override
	public void search(HandoverSearchContext context) throws Exception {
		// 获取当前查询的交接属性（这里的属性是在SysTaskProvider定义的）
		String property = getFdAttribute();
		// 获取当前查询的交接modelName
		String moduleName = context.getModule();
		String tableName = ModelUtil.getModelTableName(moduleName);
		// 构建查询语句
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGetCount(true);
		hqlInfo.setGettingCount(true);
		hqlInfo.setModelName(moduleName);
		hqlInfo.setJoinBlock(" inner join " + tableName + "." + property + " " + property);
		hqlInfo.setWhereBlock(property + ".fdId = :userId");
		hqlInfo.setParameter("userId", context.getHandoverOrg().getFdId());
		List<Long> list = getBaseService().findList(hqlInfo);
		// 返回查询到的记录数
		context.setTotal(Long.valueOf(list.get(0).toString()));
	}

	/**
	 * 获取交接的文档ID
	 * 
	 * @param fromOrgId
	 * @param toOrgId
	 * @param moduleName
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public List<String> getHandoverIds(String fromOrgId, String toOrgId,
			String moduleName, String item) throws Exception {
		String tableName = ModelUtil.getModelTableName(moduleName);
		// 构建查询语句
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(tableName + ".fdId");
		hqlInfo.setModelName(moduleName);
		hqlInfo.setWhereBlock(item + ".fdId = :userId");
		hqlInfo.setJoinBlock(" inner join " + tableName + "." + item + " " + item);
		hqlInfo.setParameter("userId", fromOrgId);
		// 查询文档ID
		return getBaseService().findList(hqlInfo);
	}

	/**
	 * 交接明细
	 * 
	 * @param hqlInfo
	 * @param fromOrgId
	 * @param toOrgId
	 * @param moduleName
	 * @param item
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	public abstract ItemDetailPage detail(HQLInfo hqlInfo, String fromOrgId,
			String toOrgId, String moduleName, String item, RequestContext requestContext) throws Exception;
	
	
	/**
	 * 构建查询明细列表时,前端筛选器需要拼装的通用过滤HQL(例如：通过文档标题进行搜索)
	 * @param hqlInfo
	 * @param modelTable
	 * @param moduleName
	 * @param requestContext
	 * @throws Exception
	 */
	public void buildDetailSearchFilterWhereBlock(HQLInfo hqlInfo, String moduleName, RequestContext requestContext) throws Exception{
	    StringBuffer sb = new StringBuffer();	
	    String titleSearchText = requestContext.getParameter("q.docSubject"); // 前端搜索框输入的模糊搜索关键字
		if(StringUtil.isNotNull(titleSearchText)){
			titleSearchText = titleSearchText.trim();
			// 获取显示字段的属性名称（数据字典配置中的displayProperty）
			String colName = SysDataDict.getInstance().getModel(moduleName).getDisplayProperty();
			if(StringUtil.isNull(colName)){
				Object modelInstance = com.landray.kmss.util.ClassUtils.forName(moduleName).newInstance();
				boolean bool = PropertyUtils.isReadable(modelInstance, "docSubject");
				if(bool){
					colName = "docSubject";
				}else{
					bool = PropertyUtils.isReadable(modelInstance, "fdName");
					colName = "fdName";
				}
			}
			if(StringUtil.isNotNull(colName)){
				String moduleTable = ModelUtil.getModelTableName(moduleName);
				String whereBlock = hqlInfo.getWhereBlock();
				if(StringUtil.isNotNull(whereBlock)){
					sb.append(whereBlock).append(" and");
				}
				sb.append(" ").append(moduleTable).append(".").append(colName).append(" like :titleSearchText");
				hqlInfo.setWhereBlock(sb.toString());
				hqlInfo.setParameter("titleSearchText", "%"+titleSearchText+"%");
			}
		}	   
	}
	
	

	/**
	 * 是否要忽略交接（取决于业务逻辑）
	 * 
	 * @param context
	 * @param model
	 * @return
	 */
	protected boolean isIgnore(HandoverExecuteContext context, IBaseModel model) {
		return false;
	}

	/**
	 * 执行交接
	 */
	@Override
	public void execute(HandoverExecuteContext context) throws Exception {
		// 当前模块名称
		String moduleName = context.getModule();
		// 交接的属性
		String property = context.getItem();
		// 交接模式：替换交接人(0), 追加交接人(1)
		Integer execMode = context.getExecMode();
		// 交接人
		SysOrgElement fromOrg = context.getFrom();
		// 接收人
		SysOrgElement toOrg = getSysOrgCoreService().format(context.getTo());
		// 交接的文档ID
		List<String> ids = context.getSelectedRecordIds();
		long successCount = 0L;
		// 处理忽略的节点记录
		long ignoreCount = 0L;
		if (ids == null) {
			context.setSuccTotal(0L);
			return;
		}

		for (String id : ids) {
			IBaseModel model = getBaseService().findByPrimaryKey(id, moduleName, false);
			// 判断业务是否需要忽略
			if (isIgnore(context, model)) {
				context.error(id, "业务逻辑决定要忽略此次交接...");
				addLog(context, model, SysHandoverConfigLogDetail.STATE_IGNORE, "业务逻辑决定要忽略此次交接...");
				ignoreCount++;
				continue;
			}
			boolean isList = false;
			List<SysOrgElement> list = null;
			Object __obj = PropertyUtils.getProperty(model, property);

			// 判断是否是多值
			if (__obj instanceof List) {
				list = (List<SysOrgElement>) __obj;
				isList = true;
			} else {
				list = new ArrayList<SysOrgElement>();
				list.add((SysOrgElement) __obj);
			}
			// 无交接人
			if (!list.contains(fromOrg)) {
				context.error(id, "交接人信息已被更新，请重新查询...");
				addLog(context, model, SysHandoverConfigLogDetail.STATE_IGNORE, "交接人信息已被更新，请重新查询...");
				ignoreCount++;
				continue;
			}
			// 更新
			if (isList) {
				if (EXEC_MODE_REPLACE.equals(execMode)) {
					// 替换模式，需要删除交接人
					Iterator<SysOrgElement> iterator = list.iterator();
					while (iterator.hasNext()) {
						if (iterator.next().getFdId().equals(fromOrg.getFdId())) {
							iterator.remove();
						}
					}
				}
				// 追加模式
				if (!list.contains(toOrg) && toOrg != null) {
					list.add(toOrg);
				}
				// 过滤重复值
				PropertyUtils.setProperty(model, property, new ArrayList<SysOrgElement>(new HashSet<SysOrgElement>(list)));
			} else {
				if (EXEC_MODE_REPLACE.equals(execMode)) {
					// 替换模式
					PropertyUtils.setProperty(model, property, toOrg);
				} else {
					// 追加模式
					context.error(id, "单值属性不允许追加！");
					addLog(context, model, SysHandoverConfigLogDetail.STATE_IGNORE, "单值属性不允许追加！");
					ignoreCount++;
					continue;
				}
			}
			getBaseService().update(model);
			// 记录日志
			addLog(context, model, SysHandoverConfigLogDetail.STATE_SUCC, null);
			successCount++;
		}
		// 需要设置成功数量/忽略数量
		context.setSuccTotal(successCount);
		context.setIgnoreTotal(ignoreCount);
	}
	
	protected int addLog(HandoverExecuteContext context, IBaseModel model, Integer fdState, String errorMsg) {
		int count = 0;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(errorMsg);
			}
			// 记录日志
			String modelName = ModelUtil.getModelClassName(model);
			SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
			String url = getUrl(dictModel, model.getFdId());
			context.log(model.getFdId(), context.getModule(), getSubject(model), url, null, fdState);
			count = 1;
		} catch (Exception e) {
			// 出错，跳过不计数
		}
		return count;
	}

	/**
	 * 获取交接日志的文档标题，如果有需要可以各自实现
	 * <p>
	 * 这里直接取数据字典中的displayProperty
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String getSubject(IBaseModel model) throws Exception {
		SysDictModel dictModel = SysDataDict.getInstance().getModel(ModelUtil.getModelClassName(model));
		String subject = "";
		if (dictModel != null) {
			String displayProperty = dictModel.getDisplayProperty();
			if (StringUtil.isNotNull(displayProperty) && PropertyUtils.isReadable(model, displayProperty)) {
				subject = PropertyUtils.getProperty(model, displayProperty).toString();
			}
		}
		return subject;
	}

	/**
	 * 根据数据字典和ID获取URL
	 * <p>
	 * 这里直接取数据字典中的URL
	 * 
	 * @param model
	 * @param fdId
	 * @return
	 */
	public String getUrl(SysDictModel model, String fdId) {
		String url = model.getUrl();
		if (url == null) {
			return "";
		}
		url = url.replace("${fdId}", fdId);
		return url;
	}

}
