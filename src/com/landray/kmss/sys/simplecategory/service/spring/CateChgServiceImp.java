package com.landray.kmss.sys.simplecategory.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.component.bklink.service.ICompBklinkModuleUpdateCheckService;
import com.landray.kmss.component.bklink.util.PluginUtil;
import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainModel;
import com.landray.kmss.sys.edition.service.ISysEditionMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserUpdateOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.sys.simplecategory.forms.CateChgForm;
import com.landray.kmss.sys.simplecategory.interfaces.ISysSimpleCategoryBeforeChangeService;
import com.landray.kmss.sys.simplecategory.service.ICateChgExtend;
import com.landray.kmss.sys.simplecategory.service.ICateChgService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.collections.MapUtils;

/**
 * 简单分类转移
 * 
 * @author wubin
 * @date 2009-09-01
 */
public class CateChgServiceImp implements ICateChgService {

	protected ISysEditionMainService sysEditionMainService;
	
	public void setSysEditionMainService(
			ISysEditionMainService sysEditionMainService) {
		this.sysEditionMainService = sysEditionMainService;
	}
	
	private List<ISysSimpleCategoryBeforeChangeService> getBeforeExtension() {
		List<ISysSimpleCategoryBeforeChangeService> modules
			= new ArrayList<ISysSimpleCategoryBeforeChangeService>();
		IExtension[] extensions = Plugin.getExtensions(
				"com.landray.kmss.sys.simplecategory.change", "*", "before");
		if(extensions != null) {
			for (int i = 0; i < extensions.length; i++) {
				modules.add((ISysSimpleCategoryBeforeChangeService)Plugin.getParamValue(extensions[i], "service"));
			}
		}
		return modules;
	}

	@Override
	public void updateChgCate(CateChgForm form, RequestContext requestContext)
			throws Exception {
		String[] ids = form.getFdIds().split("\\s*[;,]\\s*");
		SysDictModel docDict = SysDataDict.getInstance().getModel(
				form.getModelName());
		IBaseService dbs = (IBaseService) SpringBeanUtil.getBean(docDict
				.getServiceBean());
		IBaseModel cateModel = getCateModel(form);
		List<?> docs = dbs.findByPrimaryKeys(ids);
		UserOperHelper.logUpdate(form.getModelName());
		ICompBklinkModuleUpdateCheckService checkService = null;
		Map<String, ICompBklinkModuleUpdateCheckService> mapService = PluginUtil.get(PluginUtil.PluginEnum.COMP_LINK_UPDATE_CHECK);
		if(MapUtils.isNotEmpty(mapService)) {
			checkService =  mapService.get(form.getModelName());
		}
		for (int i = 0; i < docs.size(); i++) {
			IBaseModel o = (IBaseModel) docs.get(i);
			IUserUpdateOper updateOper = UserOperContentHelper.putUpdate(o);
			// 对文档的分类进行修改
			Object oldValue = PropertyUtils.getProperty(o, form.getDocFkName());
			PropertyUtils.setProperty(o, form.getDocFkName(), cateModel);
			if(null != checkService) {
				try {
					checkService.check(o);
				}catch(Exception e) {
					PropertyUtils.setProperty(o, form.getDocFkName(), oldValue);
					throw e;
				}
			}
			updateOper.putSimple(form.getDocFkName(), oldValue, cateModel);
			// 对该文档分类其它相关的修改
			if (o instanceof BaseAuthModel) {
				((BaseAuthModel) o).setFdLastModifiedTime(new Date());
			}
			// for (int j = 0; j < otherServiceNames.length; j++) {
			// IBaseService os = getOtherBs(otherServiceNames[j]);
			// IBaseModel ot = os.findByPrimaryKey(o.getFdId());
			// PropertyUtils.setProperty(ot, otherCateFkNames[j], cateModel
			// .getFdId());
			// os.getBaseDao().update(ot);
			// }
			List<ISysSimpleCategoryBeforeChangeService> blist = getBeforeExtension();
			if(!ArrayUtil.isEmpty(blist)) {
				for(ISysSimpleCategoryBeforeChangeService service : blist) {
					service.updateBeforeCategoryChange(o, cateModel, requestContext);
				}
			}
			
			if(o instanceof ISysEditionMainModel){
				RequestContext requestContext2=new RequestContext();
				requestContext2.setParameter("fdModelId", o.getFdId());
				requestContext2.setParameter("fdModelName", form.getModelName());
				List list=sysEditionMainService.getEditionHistoryList(requestContext2);          
				if(list.size()>0){
					for(int a=0;a<list.size();a++){
						IBaseModel basemodel=(IBaseModel) list.get(a);
						PropertyUtils.setProperty(basemodel, form.getDocFkName(), cateModel);
						dbs.update(basemodel);
					}
				}
			}
			
			if (dbs instanceof ICateChgExtend) {
				ICateChgExtend extendService = (ICateChgExtend) dbs;
				extendService.updateCateInfo(o, cateModel);
			}
			
			dbs.getBaseDao().update(o);
		}
	}

	private IBaseModel getCateModel(CateChgForm form) throws Exception {
		SysDictModel cateDict = SysDataDict.getInstance().getModel(
				form.getCateModelName());
		IBaseService bs = (IBaseService) SpringBeanUtil.getBean(cateDict
				.getServiceBean());
		return bs.findByPrimaryKey(form.getFdCateId());
	}
}
