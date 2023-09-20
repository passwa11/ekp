package com.landray.kmss.third.pda.service.spring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.forms.PdaModuleLabelListForm;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaModuleLabelView;
import com.landray.kmss.third.pda.model.PdaVersionConfig;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.third.pda.util.PdaPlugin;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 模块配置表业务接口实现
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleConfigMainServiceImp extends BaseServiceImp implements
		IPdaModuleConfigMainService, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		PdaModuleConfigMain mainModel = (PdaModuleConfigMain) modelObj;
		mainModel.setDocCreator(UserUtil.getUser());
		mainModel.setFdCreateTime(new Date());
		for (int i = 0; i < mainModel.getFdLabelList().size(); i++) {
			// 更新列表页签排序
			mainModel.getFdLabelList().get(i).setFdOrder(i);
		}
		for (int i = 0; i < mainModel.getFdViewItems().size(); i++) {
			// 更新展示页面标签排序
			mainModel.getFdViewItems().get(i).setFdOrder(i);
			List<PdaModuleLabelView> fdItems = mainModel.getFdViewItems()
					.get(i).getFdItems();
			for (int j = 0; j < fdItems.size(); j++) {
				// 更新展示页面配置排序
				fdItems.get(j).setFdOrder(j);
			}
		}
		updateMenuVersion();
		String fdId = super.add(mainModel);
		publishEvent("add", mainModel.getFdId());
		return fdId;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		PdaModuleConfigMain mainModel = (PdaModuleConfigMain) modelObj;
		mainModel.setDocAlteror(UserUtil.getUser());
		mainModel.setDocAlterTime(new Date());
		String menuType = mainModel.getFdSubMenuType();
		if (PdaModuleConfigConstant.PDA_MENUS_MODULE.equalsIgnoreCase(menuType)) {
			// 子菜单为module
			mainModel.getFdLabelList().clear();
			mainModel.getFdViewItems().clear();
			mainModel.setFdSubDocLink(null);
		} else if (PdaModuleConfigConstant.PDA_MENUS_DOC
				.equalsIgnoreCase(menuType)) {
			// 子菜单为doc
			mainModel.getFdLabelList().clear();
			mainModel.getFdSubModuleList().clear();
		} else {
			// 子菜单为list或listTab
			mainModel.setFdSubDocLink(null);
			mainModel.getFdSubModuleList().clear();
		}
		for (int i = 0; i < mainModel.getFdLabelList().size(); i++) {
			// 更新列表页签排序
			mainModel.getFdLabelList().get(i).setFdOrder(i);
		}
		for (int i = 0; i < mainModel.getFdViewItems().size(); i++) {
			// 更新展示页面标签排序
			mainModel.getFdViewItems().get(i).setFdOrder(i);
			List<PdaModuleLabelView> fdItems = mainModel.getFdViewItems()
					.get(i).getFdItems();
			for (int j = 0; j < fdItems.size(); j++) {
				// 更新展示页面配置排序
				fdItems.get(j).setFdOrder(j);
			}
		}
		updateMenuVersion();
		super.update(mainModel);
		publishEvent("update", mainModel.getFdId());
	}

	@Override
    protected IExtendForm convertBizModelToForm(IExtendForm form,
                                                IBaseModel model, ConvertorContext context) throws Exception {
		IExtendForm tmpForm = super
				.convertBizModelToForm(form, model, context);
		if (tmpForm instanceof PdaModuleLabelListForm) {
			PdaModuleLabelListForm labelList = (PdaModuleLabelListForm) tmpForm;
			String dbUrl = labelList.getFdDataUrl();
			if (StringUtil.isNotNull(dbUrl)) {
				String createUrl = PdaPlugin.getPdaExtendInfo(
						context.getRequestContext().getRequest(), dbUrl,
						PdaPlugin.PARAM_PDA_EXTEND_CREATEURL).get(
						PdaPlugin.PARAM_PDA_EXTEND_CREATEURL);
				if (StringUtil.isNotNull(createUrl)) {
					labelList
							.setFdCreateUrl(PdaModuleConfigConstant.PDA_CREATE_TAINSIT_URL
									+ labelList.getFdId());
				}
			}
		}
		return tmpForm;
	}

	@Override
    public void updateStatus(String[] ids, String status) throws Exception {
		String sqlIn = HQLUtil.buildLogicIN("pdaModuleConfigMain.fdId",
				ArrayUtil.convertArrayToList(ids));
		getBaseDao().getHibernateSession().createQuery(
				"update PdaModuleConfigMain pdaModuleConfigMain "
						+ "set pdaModuleConfigMain.fdStatus='" + status
						+ "' where " + sqlIn).executeUpdate();
		updateMenuVersion();
		if ("0".equals(status)) {
			publishEvent("appClose", ids);
		} else if ("1".equals(status)) {
			publishEvent("appStart", ids);
		}
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		updateMenuVersion();
		publishEvent("delete", modelObj.getFdId());
		super.delete(modelObj);
	}

	/**
	 * 更新版本号
	 * 
	 * @throws Exception
	 */
	private void updateMenuVersion() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		PdaVersionConfig version = new PdaVersionConfig();
		version.setMenuVersion(df.format(new Date()));
		version.save();
	}

	private void publishEvent(String method, Object fdId) {
		//修改可见性事件
		Map param = new HashMap<String, String>();
		param.put("mehtod", method);
		param.put("fdId", fdId);
		applicationContext.publishEvent(new Event_Common("mobileApp", param));
	}

}
