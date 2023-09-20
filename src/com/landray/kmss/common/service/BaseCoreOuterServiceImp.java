package com.landray.kmss.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.BaseCoreInnerModel;
import com.landray.kmss.common.model.IBaseCoreInnerModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.log.util.UserOperConvertHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserDetailOper;
import com.landray.kmss.sys.log.util.oper.IUserOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.model.LogConvertContext;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.StringUtil;

@SuppressWarnings("all")
public class BaseCoreOuterServiceImp implements ICoreOuterService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseCoreOuterServiceImp.class);

	public void add(IBaseModel model) throws Exception {
		save(model);
	}

	public void cloneModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
	}

	/**
	 * <pre>
	 * 机制与主文档是多对一关系时的标准转换方法，它包含转换和日志功能<b>重写需谨慎！</b>
	 * 如果使用标准的日志功能请使用：
	 * {@link #convertFormMapToModelList(IExtendForm, IBaseModel, Map, List, RequestContext, IBaseCoreInnerService)}
	 * </pre>
	 * @param formMap
	 * @param modelList 元素是同一类型的BaseCoreInnerModel
	 * @param requestContext
	 * @param coreInnerService 
	 * @throws Exception
	 */
	@Deprecated
    protected void convertFormMapToModelList(Map formMap, List modelList,
			RequestContext requestContext, IBaseCoreInnerService coreInnerService)
			throws Exception {
	    
		Iterator keys = formMap.keySet().iterator();
		List oldList = new ArrayList();
		
		oldList.addAll(modelList);
		modelList.clear();
		IUserOper oper4Mechs = (IUserOper)requestContext.getAttribute(mechOperAttKey);
		String mainModelFdId = (String)requestContext.getAttribute(fdIdAttKey);
		IUserDetailOper mechDetailOper = null; 
		if(oper4Mechs!=null){
            mechDetailOper = oper4Mechs.createOper4Detail(DEFAULT_FIELD_NAME);
        }
		//真实的机制名称从第一个BaseCoreInnerModel实例里得到
		String actualMechName = null;
		// 记录循环获取的form，防止拿到的是重复的fdId设置给model
		List<String> formIds = new ArrayList();
		//需要转换的
		while (keys.hasNext()) {
			String key = (String) keys.next();
			IExtendForm form = (IExtendForm) formMap.get(key);
			BaseCoreInnerModel model = null;
			for (int i = 0; i < oldList.size(); i++) {
				model = (BaseCoreInnerModel) oldList.get(i);
				if (ObjectUtil.equals(model.getFdKey(), key))
					break;
				model = null;
			}
			boolean isAdd = false;
			if (model == null) {
				model = (BaseCoreInnerModel) form.getModelClass().newInstance();
				isAdd = true;
				model.setFdKey(key);
				if(!formIds.contains(form.getFdId())){
					formIds.add(form.getFdId());
					if(!coreInnerService.getBaseDao().isExist(form.getModelClass().getName(),form.getFdId())){
						model.setFdId(form.getFdId());
					}
				}
			} else {
				oldList.remove(model);
			}
			
			
			if (logger.isDebugEnabled()){
			    logger.debug("转换：" + model.getClass() + "(" + model.getFdKey() + ")");
			}
			//有日志上下文结构的时候才记录日志
			if(oper4Mechs!=null){
			    if(actualMechName==null){
			        actualMechName = model.getMechanismName();
			    }
		        String logType = isAdd?LogConvertContext.CONVERTTYPE_ADD:LogConvertContext.CONVERTTYPE_UPDATE;
		        IUserOper logOper = isAdd?mechDetailOper.putAdd(model):mechDetailOper.putUpdate(model);
		        requestContext.setAttribute(ICoreOuterService.CORE_SERVICE_LOG_OPER_KEY, logOper);
		        requestContext.setAttribute(ICoreOuterService.CORE_SERVICE_LOGTYPE_KEY, logType);
			}
			try{
			    modelList.add(coreInnerService.convertFormToModel(form, model,requestContext));
			}finally{
			    //无论有无，都尝试清理本次绑定到requstContext的日志信息
			    requestContext.setAttribute(ICoreOuterService.CORE_SERVICE_LOG_OPER_KEY, null);
                requestContext.setAttribute(ICoreOuterService.CORE_SERVICE_LOGTYPE_KEY, null);
			}
		}
		//需删除的
		for (int i = 0; i < oldList.size(); i++) {
			BaseCoreInnerModel model = (BaseCoreInnerModel) oldList.get(i);
			if(oper4Mechs!=null){
                if(actualMechName==null){
                    actualMechName = model.getMechanismName();
                }
                mechDetailOper.putDelete(model);
            }
			if (logger.isDebugEnabled())
				logger.debug("删除：" + model.getClass() + "(" + model.getFdKey()
						+ ")");
			coreInnerService.delete(model);
		}
		if(oper4Mechs!=null 
		        && actualMechName!=null 
		        && mainModelFdId!=null){
		    IUserOper oper4MainModel = UserOperContentHelper.findMainModelOperById(mainModelFdId);
            if(oper4MainModel!=null&&oper4MainModel.getMechsMap()!=null){
                //换机制名
                Map<String, Object> mechsMap = oper4MainModel.getMechsMap();
                Object object = mechsMap.get(mechNamePlaceholder);
                mechsMap.put(actualMechName, object);
                mechsMap.remove(mechNamePlaceholder);
            }
		}
	}

	/**
     * <pre>
     * 机制与主文档是多对一关系时的标准转换方法，它包含转换和日志功能
     * 该方法与
     * {@link #convertFormMapToModelList(Map, List, RequestContext, IBaseCoreInnerService)}
     * 联合使用。
     *
     * </pre>
	 * @param mainForm
	 * @param mainModel
	 * @param formMap
	 * @param modelList
	 * @param requestContext
	 * @param coreInnerService
	 * @throws Exception
	 */
	protected final void convertFormMapToModelList(IExtendForm mainForm,IBaseModel mainModel, 
	        Map formMap, List modelList,RequestContext requestContext, IBaseCoreInnerService coreInnerService)
            throws Exception{
        try{
            setupLogOper4Mech(mainForm,mainModel,requestContext);
            convertFormMapToModelList(formMap, modelList,requestContext, coreInnerService);
        }finally{
            cleanupLogOper4Mech(mainForm,mainModel,requestContext);
        }
	}

	/**
	 * <pre>
	 * 机制随主文档变更时，创建机制的操作日志对象，务必与{@link #cleanupLogOper4Mech(IExtendForm, IBaseModel, RequestContext)}配合使用，
	 * <code>
	 * try{
     *      setupLogOper4Mech(mainForm,mainModel,requestContext);
     *      // doSomething();
     *  }finally{
     *      cleanupLogOper4Mech(mainForm,mainModel,requestContext);
     *  }
	 * </code>
	 * </pre>
	 * @param mainForm
	 * @param mainModel
	 * @param requestContext
	 */
	protected final void setupLogOper4Mech(IExtendForm mainForm,IBaseModel mainModel,RequestContext requestContext){
	    
	    String mainModelName = null;
	    if(mainForm!=null){
	        mainModelName = mainForm.getModelClass().getName();
	    }else if(mainModel!=null){
	        mainModelName = ModelUtil.getModelClassName(mainModel);
	    }
	    if(!UserOperHelper.isMechesUsedModelName(mainModelName)){
            if(logger.isDebugEnabled()){
                logger.debug("当前操作对应的主文档类型与机制对应的主文档不一致, 不记录本次机制操作日志");
            }
            return ;
        }
	    IUserOper oper4Mechs = null;
        String mainModelFdId = null;
        if(mainModel!=null){
            mainModelFdId = mainModel.getFdId();
        }else if(mainForm!=null){
            mainModelFdId = mainForm.getFdId();
        }
        //如果没有主文档ID，那么机制的日志内容就无法绑定到主文档的日志结构中去
        if(mainModelFdId==null){
            if(logger.isWarnEnabled()){
                logger.warn("主文档Id无法获取，无法记录本次机制的操作日志");
            }
        }else{
            oper4Mechs = UserOperConvertHelper.createOper4Mechs(mechNamePlaceholder, mainModelFdId,null,null,mainModelName);
        }
        requestContext.setAttribute(mechOperAttKey, oper4Mechs);
        requestContext.setAttribute(fdIdAttKey, mainModelFdId);
	}
	
	/**
	 * 机制随主文档变更时，清理创建出来的操作日志对象
	 * @param mainForm
	 * @param mainModel
	 * @param requestContext
	 */
	protected final void cleanupLogOper4Mech(IExtendForm mainForm,IBaseModel mainModel,RequestContext requestContext){
	    //clear
	    String mainModelFdId = (String)requestContext.getAttribute(fdIdAttKey);
        requestContext.setAttribute(mechOperAttKey, null);
        requestContext.setAttribute(fdIdAttKey, null);
        if(mainModelFdId!=null){
            //从主文档的Oper里清除掉之前设置的占位机制
            IUserOper oper4MainModel = UserOperContentHelper.findMainModelOperById(mainModelFdId);
            if(oper4MainModel!=null&&oper4MainModel.getMechsMap()!=null){
                oper4MainModel.getMechsMap().remove(mechNamePlaceholder);
            }
        }
	}
	
	/**
	 * 尝试把机制model或form关联到主文档上，请在调用coreInnerService.convertFormToModel()之前调用此方法，以便正确的记录机制日志
	 * 如果机制Form或Model的fdModelId已经有值，该方法不会覆盖，以防干扰业务逻辑
	 * @param mechForm  机制对应的form类， 比如: ISysNewsPublishMainForm.getSysNewsPublishMainForm()的返回值
	 * @param mechModel 机制对应的model类， 比如: ISysNewsPublishMainModel.getSysNewsPublishMain()的返回值
	 */
	protected final void associateMech2Main(IBaseModel mainModel, IExtendForm mechForm, IBaseModel mechModel){
	    if(mainModel!=null && StringUtil.isNull(mainModel.getFdId())){
	        return;
	    }
	    String mainModelFdId = mainModel.getFdId();
	    String mainModelName = ModelUtil.getModelClassName(mainModel);
        if(mechForm instanceof BaseCoreInnerForm){
            if(StringUtil.isNull(((BaseCoreInnerForm)mechForm).getFdModelId())){
                ((BaseCoreInnerForm)mechForm).setFdModelId(mainModelFdId);
            }
            if(StringUtil.isNull(((BaseCoreInnerForm)mechForm).getFdModelName())){
                ((BaseCoreInnerForm)mechForm).setFdModelName(mainModelName);
            }
        }
        if(mechModel instanceof IBaseCoreInnerModel){
            if(StringUtil.isNull(((IBaseCoreInnerModel)mechModel).getFdModelId())){
                ((IBaseCoreInnerModel)mechModel).setFdModelId(mainModelFdId);
            }
            if(StringUtil.isNull(((IBaseCoreInnerModel)mechModel).getFdModelName())){
                ((IBaseCoreInnerModel)mechModel).setFdModelName(mainModelName);
            }
        }
	}
	
	/**
	 * 获取主文档的fdId
	 * @param model
	 * @return  如果model不是IBaseCoreInnerModel，返回model.getFdId()，否则返回null
	 */
	protected final String extractMainModelFdId(IBaseModel model){
	    String mainModelFdId = null;
        if(model!=null 
                && !(model instanceof IBaseCoreInnerModel)){
            mainModelFdId = model.getFdId();
        }else{
          //如果是机制model，fdId就不是主文档的了，留空，由后续逻辑自行完成补充
        }
        return mainModelFdId;
	}
	/**
	 * 机制名称占位符，在记录日志的时候
	 */
	protected static final String mechNamePlaceholder = "__virtualMechanism__";
	protected static final String mechOperAttKey = "BaseCoreOuterService.mechOperAttKey";
	protected static final String fdIdAttKey = "BaseCoreOuterService.fdIdAttKey";
	
	/**
	 * 机制一对多转换时填充的默认的虚拟属性名
	 */
	protected static final String DEFAULT_FIELD_NAME = "items";
	
	/**
	 * <pre>
	 * 机制的转换方法入口，默认不转换，如果某机制需要转换，代码请尽量遵循以下范式
	 * <ul>
	 *     <li>主文档与机制一对一的情况，且会调用到coreInnerService.convertFormToModel()的
	 *     <code>
	 *     if (!(form instanceof ISysNewsPublishMainForm && model instanceof ISysNewsPublishMainModel)){
            return;
        }
        ISysNewsPublishMainForm mainForm = (ISysNewsPublishMainForm) form;
        ISysNewsPublishMainModel mainModel = (ISysNewsPublishMainModel) model;
        SysNewsPublishMainForm sysNewsPublishMainForm = mainForm.getSysNewsPublishMainForm();
        SysNewsPublishMain sysNewsPublishMain = mainModel.getSysNewsPublishMain();
        associateMech2Main(extractMainModelFdId(model),sysNewsPublishMainForm,sysNewsPublishMain);
        SysNewsPublishMain sysNewsPublishMainAfterConvert = (SysNewsPublishMain)sysNewsPublishMainService.convertFormToModel(
                sysNewsPublishMainForm,sysNewsPublishMain,requestContext);
        mainModel.setSysNewsPublishMain(sysNewsPublishMainAfterConvert);
	 *     </code>
	 *     </li>
	 *     <li>主文档与机制一对多的情况，参见发布机制逻辑
	 *     <code>
	 *     if (!(form instanceof ISysLbpmTemplateForm && model instanceof ISysLbpmTemplateModel)) {
            return;
        }
        ISysLbpmTemplateForm mainForm = (ISysLbpmTemplateForm) form;
        ISysLbpmTemplateModel mainModel = (ISysLbpmTemplateModel) model;
        mainModel.setSysWfTemplateModels(getCoreModels(mainModel));
        convertFormMapToModelList(form,model,mainForm.getSysWfTemplateForms(),
                mainModel.getSysWfTemplateModels(), requestContext,
                lbpmTemplateService);
	 *     </code>
	 *     </li>
	 * </ul>
	 * </pre>
	 */
	public void convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
	}

	
	protected void convertModelListToFormMap(Map formMap, List modelList,
			RequestContext requestContext, IBaseCoreInnerService service)
			throws Exception {
		for (int i = 0; i < modelList.size(); i++) {
			BaseCoreInnerModel model = (BaseCoreInnerModel) modelList.get(i);
			formMap.put(model.getFdKey(), service.convertModelToForm(null,
					model, requestContext));
		}
	}

	public void convertModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
	}

	public void delete(IBaseModel model) throws Exception {
	}

	public void initFormSetting(IExtendForm mainForm, String mainKey,
			IBaseModel settingModel, String settingKey,
			RequestContext requestContext) throws Exception {
	}

	public void initModelSetting(IBaseModel mainModel, String mainKey,
			IBaseModel settingModel, String settingKey) throws Exception {
	}

	protected void save(IBaseModel model) throws Exception {
	}

	public void update(IBaseModel model) throws Exception {
		save(model);
	}

	public List<?> exportData(String id, String modelName) throws Exception {
		return Collections.EMPTY_LIST;
	}

	public Class<?> getSourceClass() {
		return getClass();
	}

	@Override
	public void deleteSoft(IBaseModel model) throws Exception {

	}

	@Override
	public void update2Recover(IBaseModel modelObj) throws Exception {

	}
}
