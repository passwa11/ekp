package com.landray.kmss.common.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.IFormToModelConvertor;
import com.landray.kmss.common.convertor.IModelToFormConvertor;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.exception.FormModelConvertException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.design.SysCfgFtSearch;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.log.util.UserOperConvertHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.recycle.service.ISysRecycleLogService;
import com.landray.kmss.sys.recycle.util.SysRecycleUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 直接调用了IBaseDao的接口实现了常用的CRUD以及查询等方法。<br>
 * 对于简单的业务，继承该类可以完成大部分功能，但对于复杂的业务逻辑，建议覆盖大部分该类的方法，或者不继承该类。<br>
 * 注意：要继承该类，必须<br>
 * <li>对应的model继承类{@link com.landray.kmss.common.model.IBaseModel IBaseModel}；</li>
 * 
 * @author 叶中奇
 * @version 1.0 2006-04-02
 */
public class BaseServiceImp implements IBaseService {
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImp.class);

	private IBaseDao baseDao;

	protected ICoreOuterService dispatchCoreService = null;

	private static final String METHOD_GET_ADD = "add";
	private static final String METHOD_SAVE = "save";
	private static final String METHOD_SAVE_ADD = "saveadd";

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		String rtnVal = getBaseDao().add(modelObj);
		if (dispatchCoreService != null) {
			dispatchCoreService.add(modelObj);
		}
		return rtnVal;
	}
	
	@Override
    public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String modelName = form.getModelClass().getName();
		modelName = StringUtil.isNotNull(modelName) ? modelName : getModelName();
		UserOperHelper.logAdd(modelName);
		IBaseModel model = convertFormToModel(form, null, requestContext);
		String fdId = model.getFdId();
		if (fdId != null) {
			String reqMethodGet = requestContext.getParameter("method_GET");
			String reqMethod = requestContext.getParameter("method");
			if (METHOD_GET_ADD.equals(reqMethodGet)
					&& (METHOD_SAVE.equals(reqMethod)
							|| METHOD_SAVE_ADD.equals(reqMethod))) {
				if (getBaseDao().isExist(getModelName(), fdId)) {
					throw new UnexpectedRequestException();
				}
			}
		}
		return add(model);
	}

	@Override
    public IExtendForm cloneModelToForm(IExtendForm form, IBaseModel model,
                                        RequestContext requestContext) throws Exception {
		ConvertorContext context = new ConvertorContext();
		context.setBaseService(this);
		context.setObjMap(new HashMap());
		context.setRequestContext(requestContext);
		context.setIsClone(true);
		return convertModelToForm(form, model, context);
	}

	protected IBaseModel convertBizFormToModel(IExtendForm form,
			IBaseModel model, ConvertorContext context) throws Exception {
		String sPropertyName = null;
		try {
			if (form == null) {
				return null;
			}
			TimeCounter.logCurrentTime("service-convertFormToModel", true, form
					.getClass());
			Map objMap = context.getObjMap();
			if (objMap.containsKey(form)) {
				return (IBaseModel) objMap.get(form);
			}
			// 获取主域模型
			if (model == null) {
				boolean formIdNotNull = StringUtil.isNotNull(form.getFdId());
				if (formIdNotNull) {
					model = findByPrimaryKey(form.getFdId(), form
							.getModelClass(), true);
				}
				if (model == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("构建新的" + form.getModelClass().getName()
								+ "域模型");
					}
					model = (IBaseModel) form.getModelClass().newInstance();
					if (formIdNotNull) {
						model.setFdId(form.getFdId());
					}
				}
			}
			form.setFdId(model.getFdId());
			ConvertorContext convertorContext = new ConvertorContext(context);
			convertorContext.setSObject(form);
			convertorContext.setTObject(model);
			//创建初始操作日志
			UserOperConvertHelper.createOper(convertorContext, model, form);
			// 建立域模型与Form模型的映射
			objMap.put(form, model);
			// 获取Form到域模型的映射
			Map propertyMap = form.getToModelPropertyMap().getPropertyMap();
			List propertyList = form.getToModelPropertyMap().getPropertyList();
			// 遍历Form模型的所有属性，转换无映射的属性
			PropertyDescriptor[] sProperties = PropertyUtils
					.getPropertyDescriptors(form);
			for (int i = 0; i < sProperties.length; i++) {
				sPropertyName = sProperties[i].getName();
				if (propertyMap.containsKey(sPropertyName)) {
					continue;
				} else {
					try {
						if (!PropertyUtils.isReadable(form, sPropertyName)
								|| !PropertyUtils.isWriteable(model,
										sPropertyName)) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}
				}
				// 机制Map的内容由机制的CoreService逻辑转换
				if ("mechanismMap".equals(sPropertyName)) {
					continue;
				}
				// 修复 时间动态属性流程审批过程中提交报错
				if ("customPropMap".equals(sPropertyName)) {
					ICoreOuterService dynamicAttributeService = (ICoreOuterService) SpringBeanUtil
							.getBean("dynamicAttributeService");
					dynamicAttributeService.convertFormToModel(form, model,
							context.getRequestContext());
					continue;
				}
				IFormToModelConvertor convertor = new FormConvertor_Common(
						sPropertyName);
				convertorContext.setSPropertyName(sPropertyName);
				convertor.excute(convertorContext);
				if (logger.isDebugEnabled()) {
					logger.debug("成功执行" + form.getClass().getName() + "中Form."
							+ sPropertyName + "->Model."
							+ convertor.getTPropertyName() + "的"
							+ convertor.getClass().getName() + "转换");
				}
			}
			// 转换有映射的属性
			for (int i = 0; i < propertyList.size(); i++) {
				sPropertyName = (String) propertyList.get(i);
				Object propertyInfo = propertyMap.get(sPropertyName);
				if (propertyInfo == null) {
					continue;
				}
				IFormToModelConvertor convertor;
				if (propertyInfo instanceof IFormToModelConvertor) {
					convertor = (IFormToModelConvertor) propertyInfo;
				} else {
					convertor = new FormConvertor_Common((String) propertyInfo);
				}
				convertorContext.setSPropertyName(sPropertyName);
				//当前日志操作对象和操作类型
				IUserOper logOper = convertorContext.getLogOper();
				String logType = convertorContext.getLogType();
				//convert
				convertor.excute(convertorContext);
				//还原convert前的日志操作对象和操作类型
				convertorContext.setLogOper(logOper);
				convertorContext.setLogType(logType);
				if (logger.isDebugEnabled()) {
					logger.debug("成功执行" + form.getClass().getName() + "中Form."
							+ sPropertyName + "->Model."
							+ convertor.getTPropertyName() + "的"
							+ convertor.getClass().getName() + "转换");
				}
			}
			TimeCounter.logCurrentTime("service-convertFormToModel", false,
					form.getClass());
		} catch (Exception e) {
			logger.error("转换Form属性：" + sPropertyName + "时发生错误", e);
			if (sPropertyName == null) {
				throw new FormModelConvertException(e);
			} else {
				throw new FormModelConvertException(sPropertyName, e);
			}
		}
		return model;
	}

	@Override
    public final IBaseModel convertFormToModel(IExtendForm form,
                                               IBaseModel model, ConvertorContext context) throws Exception {
		model = convertBizFormToModel(form, model, context);
		if (dispatchCoreService != null) {
			dispatchCoreService.convertFormToModel(form, model, context
					.getRequestContext());
		}
		// 初始化软删除状态
		if (model instanceof ISysRecycleModel) {
			ISysRecycleModel recycleModel = (ISysRecycleModel) model;
			// 如果没有软删除状态
			if (recycleModel.getDocDeleteFlag() == null) {
				// 标识为正常数据状态
				recycleModel.setDocDeleteFlag(SysRecycleConstant.OPT_TYPE_RECOVER);
			}
		}
		return model;
	}

	/**
	 * <pre>
	 * 转换逻辑入口，这个方法会创建一个新的{@link ConvertorContext}实例，并执行通用的转换逻辑
	 * 子类可谨慎重写，建议在重写时先调用<b>super.</b>{@link #convertFormToModel(IExtendForm, IBaseModel, RequestContext)}
	 * </pre>
	 */
	@Override
    public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
                                         RequestContext requestContext) throws Exception {
		ConvertorContext context = new ConvertorContext();
		context.setBaseService(this);
		context.setObjMap(new HashMap());
		context.setRequestContext(requestContext);
		//以下两个属性是机制转换时可能用到的，用来记录日志的
		if(requestContext.getAttribute(ICoreOuterService.CORE_SERVICE_LOG_OPER_KEY)!=null){
		    context.setLogOper((IUserOper)requestContext.getAttribute(ICoreOuterService.CORE_SERVICE_LOG_OPER_KEY));
		}
		if(requestContext.getAttribute(ICoreOuterService.CORE_SERVICE_LOGTYPE_KEY)!=null){
		    context.setLogType((String)requestContext.getAttribute(ICoreOuterService.CORE_SERVICE_LOGTYPE_KEY));
		}
		return convertFormToModel(form, model, context);
	}

	protected IExtendForm convertBizModelToForm(IExtendForm form,
			IBaseModel model, ConvertorContext context) throws Exception {
		String sPropertyName = null;
		try {
			if (model == null) {
				return null;
			}
			TimeCounter.logCurrentTime("service-convertModelToForm", true,
					model.getClass());
			Map objMap = context.getObjMap();
			if (objMap.containsKey(model)) {
				return (IExtendForm) objMap.get(model);
			}
			// 构造新的Form模型
			if (form == null) {
				form = (IExtendForm) model.getFormClass().newInstance();
			}
			objMap.put(model, form);
			ConvertorContext convertorContext = new ConvertorContext(context);
			convertorContext.setSObject(model);
			convertorContext.setTObject(form);
			// 获取属性映射表
			Map propertyMap = model.getToFormPropertyMap().getPropertyMap();
			List propertyList = model.getToFormPropertyMap().getPropertyList();
			// 遍历域模型的所有属性，转换无映射的属性
			PropertyDescriptor[] sProperties = PropertyUtils
					.getPropertyDescriptors(model);
			for (int i = 0; i < sProperties.length; i++) {
				sPropertyName = sProperties[i].getName();
				if (propertyMap.containsKey(sPropertyName)) {
					continue;
				} else {
					try {
						if (!PropertyUtils.isReadable(model, sPropertyName)
								|| !PropertyUtils.isWriteable(form,
										sPropertyName)) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}
				}

				// 机制Map的内容由机制的CoreService逻辑转换
				if ("mechanismMap".equals(sPropertyName)) {
					continue;
				}

				if ("dynamicMap".equals(sPropertyName)) {
					Map<String, String> dynamicMap_model = model
							.getDynamicMap();
					Map<String, String> dynamicMap_form = form.getDynamicMap();
					if (dynamicMap_model != null && dynamicMap_form != null) {
						dynamicMap_form.putAll(dynamicMap_model);
					}
				} else {
					IModelToFormConvertor convertor = new ModelConvertor_Common(
							sPropertyName);
					convertorContext.setSPropertyName(sPropertyName);
					convertor.excute(convertorContext);

					if (logger.isDebugEnabled()) {
						logger.debug("成功执行" + model.getClass().getName()
								+ "中Model." + sPropertyName + "->Form."
								+ convertor.getTPropertyName() + "的"
								+ convertor.getClass().getName() + "转换");
					}
				}
			}
			// 转换有映射的属性
			for (int i = 0; i < propertyList.size(); i++) {
				sPropertyName = (String) propertyList.get(i);
				IModelToFormConvertor convertor;
				Object propertyInfo = propertyMap.get(sPropertyName);
				if (propertyInfo == null) {
					continue;
				}
				if (propertyInfo instanceof IModelToFormConvertor) {
					convertor = (IModelToFormConvertor) propertyInfo;
				} else {
					convertor = new ModelConvertor_Common((String) propertyInfo);
				}
				convertorContext.setSPropertyName(sPropertyName);
				convertor.excute(convertorContext);
				if (logger.isDebugEnabled()) {
					logger.debug("成功执行" + model.getClass().getName()
							+ "中Model." + sPropertyName + "->Form."
							+ convertor.getTPropertyName() + "的"
							+ convertor.getClass().getName() + "转换");
				}
			}
			TimeCounter.logCurrentTime("service-convertModelToForm", false,
					model.getClass());
		} catch (Exception e) {
			logger.error("转换Model属性：" + sPropertyName + "时发生错误", e);
			if (sPropertyName == null) {
				throw new FormModelConvertException(e);
			} else {
				throw new FormModelConvertException(sPropertyName, e);
			}
		}
		return form;
	}

	@Override
    public final IExtendForm convertModelToForm(IExtendForm form,
                                                IBaseModel model, ConvertorContext context) throws Exception {
		form = convertBizModelToForm(form, model, context);
		if (dispatchCoreService != null) {
			if (context.getIsClone()) {
				form.setFdId(IDGenerator.generateID());
				dispatchCoreService.cloneModelToForm(form, model, context
						.getRequestContext());
			} else {
				dispatchCoreService.convertModelToForm(form, model, context
						.getRequestContext());
			}
		}
		return form;
	}

	@Override
    public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model,
                                          RequestContext requestContext) throws Exception {
		ConvertorContext context = new ConvertorContext();
		context.setBaseService(this);
		context.setObjMap(new HashMap());
		context.setRequestContext(requestContext);
		return convertModelToForm(form, model, context);
	}

	@Override
    public void delete(IBaseModel modelObj) throws Exception {
		UserOperHelper.logDelete(modelObj);
		// huangwq 是否部署了软删除
		String modelClassName = ModelUtil.getModelClassName(modelObj);
		if (SysRecycleUtil
				.isEnableSoftDelete(modelClassName)) {
			deleteSoft(modelObj);
			return;
		}
		if (dispatchCoreService != null) {
			dispatchCoreService.delete(modelObj);
		}
		getBaseDao().delete(modelObj);
	}

	public void deleteSoft(IBaseModel modelObj) throws Exception {
		if (dispatchCoreService != null) {
			dispatchCoreService.deleteSoft(modelObj);
		}

		// 设置软删除信息
		if (modelObj instanceof ISysRecycleModel) {
			ISysRecycleModel recycleModel = (ISysRecycleModel) modelObj;
			// 标识删除状态
			recycleModel.setDocDeleteFlag(SysRecycleConstant.OPT_TYPE_SOFTDELETE);
			// 删除时间
			recycleModel.setDocDeleteTime(new Date());
			// 删除者
			recycleModel.setDocDeleteBy(UserUtil.getUser());
		}
		// 修改“最后修改时间”
		updateLastModifiedTime(modelObj);
		getBaseDao().update(modelObj);
		
		// 删除后同时将所有文档相关的 “待办” 设置为 “已办”
		ISysNotifyTodoService sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		List<?> todos = sysNotifyTodoService.getCoreModels(modelObj, null, null, null);
		for (int i = 0; i < todos.size(); i++) {
			SysNotifyTodo todo = (SysNotifyTodo) todos.get(i);
			sysNotifyTodoService.setTodoDone(todo);
		}

		ISysRecycleLogService sysRecycleLogService = (ISysRecycleLogService) SpringBeanUtil.getBean("sysRecycleLogService");
		sysRecycleLogService.addRecycleLog(modelObj,SysRecycleConstant.OPT_TYPE_SOFTDELETE);
	}

	@Override
    public void deleteHard(IBaseModel modelObj) throws Exception {
		//添加此查询，主要是为了懒加载问题导致删除失败，这里保证session为同一个 洪健2020-3-7
		if(modelObj!=null&&modelObj.getFdId()!=null) {
			
			/**
			 * 有些service未配置dao，导致getBaseDao().getModelName()为null;
			 * 此种就不在查询，直接赋值原有model
			 */
			IBaseModel modelTemp=null;
			if(getBaseDao().getModelName()!=null) {
				 modelTemp=getBaseDao().findByPrimaryKey(modelObj.getFdId());
			}else {
				logger.warn("getBaseDao().getModelName() is null");
				modelTemp=modelObj; 
			}
			
			
			UserOperHelper.logDelete(modelTemp);
			
			if (dispatchCoreService != null) {
				dispatchCoreService.delete(modelTemp);
			}
			getBaseDao().delete(modelTemp);

			ISysRecycleLogService sysRecycleLogService = (ISysRecycleLogService) SpringBeanUtil
					.getBean("sysRecycleLogService");
			sysRecycleLogService.addRecycleLog(modelTemp,
					SysRecycleConstant.OPT_TYPE_HARDDELETE);
		}
	}

	@Override
    public final void delete(String id) throws Exception {
		delete(findByPrimaryKey(id));
	}

	@Override
    public final void delete(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			if (i > 0) {
				flushHibernateSession();
			}
			delete(ids[i]);
		}
	}

	@Override
    public final void deleteHard(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			if (i > 0) {
				flushHibernateSession();
			}
			deleteHard(findByPrimaryKey(ids[i]));
		}
	}

	@Override
    public final void update2Recover(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			if (i > 0) {
				flushHibernateSession();
			}
			update2Recover(findByPrimaryKey(ids[i]));
		}
	}

	@Override
    public void update2Recover(IBaseModel modelObj) throws Exception {
		if (modelObj instanceof ISysRecycleModel) {
			ISysRecycleModel recycleModel = (ISysRecycleModel) modelObj;
			if (UserOperHelper.allowLogOper("Service_Update",
					ModelUtil.getModelClassName(modelObj))) {
				UserOperContentHelper.putUpdate(modelObj)
						.putSimple("docDeleteFlag",
								recycleModel.getDocDeleteFlag(),
								SysRecycleConstant.OPT_TYPE_RECOVER)
						.putSimple("docDeleteTime",
								recycleModel.getDocDeleteTime(), null)
						.putSimple("docDeleteBy", recycleModel.getDocDeleteBy(),
								null);
			}
			// 标识还原状态
			recycleModel.setDocDeleteFlag(SysRecycleConstant.OPT_TYPE_RECOVER);
			// 清空删除时间
			recycleModel.setDocDeleteTime(null);
			// 清空删除者
			recycleModel.setDocDeleteBy(null);
		}
		// 修改“最后修改时间”
		updateLastModifiedTime(modelObj);
		getBaseDao().update(modelObj);

		if (dispatchCoreService != null) {
			dispatchCoreService.update2Recover(modelObj);
		}

		ISysRecycleLogService sysRecycleLogService = (ISysRecycleLogService) SpringBeanUtil
				.getBean("sysRecycleLogService");
		sysRecycleLogService.addRecycleLog(modelObj,
				SysRecycleConstant.OPT_TYPE_RECOVER);
	}

	/**
	 * 更新最后修改时间
	 * 
	 * 全文搜索会根据此字段进行索引，此字段可能并不是所有模块都会有，所以这里根据design.xml中“ftSearch”节点的“timeField”属性值来处理
	 * 
	 * @param modelObj
	 */
	private void updateLastModifiedTime(IBaseModel modelObj) throws Exception {
		// 取ModelName
		String modelName = ModelUtil.getModelClassName(modelObj);
		if (StringUtil.isNull(modelName)) {
			return;
		}
		// 取全文搜索的配置信息
		SysCfgFtSearch search = (SysCfgFtSearch) SysConfigs.getInstance().getFtSearchs().get(modelName);
		if (search != null) {
			// 取“更新最后修改时间”字段名称
			String timeField = search.getTimeField();
			if (StringUtil.isNotNull(timeField)) {
				// 判断model是否有此字段(是否能更新此字段)
				if (PropertyUtils.isWriteable(modelObj, timeField)) {
					// 更新字段值
					PropertyUtils.setProperty(modelObj, timeField, new Date());
				}
			}
		}
	}

	@Override
    public void flushHibernateSession() {
		getBaseDao().flushHibernateSession();
	}

	@Override
    public IBaseModel findByPrimaryKey(String id) throws Exception {
		return getBaseDao().findByPrimaryKey(id);
	}

	@Override
    public IBaseModel findByPrimaryKey(String id, Object modelInfo,
                                       boolean noLazy) throws Exception {
		return getBaseDao().findByPrimaryKey(id, modelInfo, noLazy);
	}

	@Override
    public List findByPrimaryKeys(String[] ids) throws Exception {
		return getBaseDao().findByPrimaryKeys(ids);
	}

	@Override
    public List findList(HQLInfo hqlInfo) throws Exception {
		return getBaseDao().findList(hqlInfo);
	}

	@Override
    public List findList(String whereBlock, String orderBy) throws Exception {
		return getBaseDao().findList(whereBlock, orderBy);
	}

	@Override
    public Page findPage(HQLInfo hqlInfo) throws Exception {
		return getBaseDao().findPage(hqlInfo);
	}

	@Override
    public Page findPage(String whereBlock, String orderBy, int pageno,
                         int rowsize) throws Exception {
		return getBaseDao().findPage(whereBlock, orderBy, pageno, rowsize);
	}

	@Override
    public List findValue(HQLInfo hqlInfo) throws Exception {
		return getBaseDao().findValue(hqlInfo);
	}

	@Override
    public List findValue(String selectBlock, String whereBlock, String orderBy)
			throws Exception {
		return getBaseDao().findValue(selectBlock, whereBlock, orderBy);
	}

	@Override
    public IBaseDao getBaseDao() {
		return baseDao;
	}

	@Override
    public String getModelName() {
		return getBaseDao().getModelName();
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public final void setDispatchCoreService(ICoreOuterService coreService) {
		this.dispatchCoreService = coreService;
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		getBaseDao().update(modelObj);
		if (dispatchCoreService != null) {
			dispatchCoreService.update(modelObj);
		}
	}

	@Override
    public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String modelName = form.getModelClass().getName();
		modelName = StringUtil.isNotNull(modelName) ? modelName : getModelName();
		UserOperHelper.logUpdate(modelName);
		IBaseModel model = convertFormToModel(form, null, requestContext);
		update(model);
	}

	@Override
    public void clearHibernateSession() {
		getBaseDao().clearHibernateSession();
	}

	/**
	 * 查询符合条件的第一条数据，避免使用findList().get(0)
	 * @param hqlInfo
	 * @return
	 * @throws Exception
	 */
	@Override
    public Object findFirstOne(HQLInfo hqlInfo) throws Exception{
		return getBaseDao().findFirstOne(hqlInfo);

	}

	/**
	 * 查询符合条件的第一条数据，避免使用findList().get(0)
	 * @param whereBlock
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	@Override
    public Object findFirstOne(String whereBlock, String orderBy)
			throws Exception{
		return getBaseDao().findFirstOne(whereBlock, orderBy);
	}
}
