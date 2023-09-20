package com.landray.kmss.third.pda.service.spring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaTabViewConfigMain;
import com.landray.kmss.third.pda.model.PdaTabViewLabelList;
import com.landray.kmss.third.pda.model.PdaVersionConfig;
import com.landray.kmss.third.pda.service.IPdaTabViewConfigMainService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.UserUtil;

public class PdaTabViewConfigMainServiceImp extends BaseServiceImp implements
		IPdaTabViewConfigMainService {

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		PdaTabViewConfigMain mainModel = (PdaTabViewConfigMain) modelObj;
		PdaModuleConfigMain fdTabModule = mainModel.getFdModule();
		String modelName = fdTabModule.getFdName();
		if(mainModel!=null){
			List<PdaTabViewLabelList> fdLabelList = mainModel.getFdLabelList();
			if(fdLabelList!=null && fdLabelList.size()>0){
				for (int i = 0; i < fdLabelList.size(); i++) {
					PdaTabViewLabelList pdaTabViewLabelList = fdLabelList.get(i);
					pdaTabViewLabelList.setFdTabModelName(modelName);
					pdaTabViewLabelList.setFdTabModule(fdTabModule);
					pdaTabViewLabelList.setFdTabOrder(i);
				}
			}
		}
		mainModel.setDocCreator(UserUtil.getUser());
		mainModel.setFdCreateTime(new Date());
		updateMenuVersion();
		return super.add(mainModel);
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		PdaTabViewConfigMain mainModel = (PdaTabViewConfigMain) modelObj;
		PdaModuleConfigMain fdTabModule = mainModel.getFdModule();
		String modelName = fdTabModule.getFdName();
		if(mainModel!=null){
			List<PdaTabViewLabelList> fdLabelList = mainModel.getFdLabelList();
			if(fdLabelList!=null && fdLabelList.size()>0){
				for (int i = 0; i < fdLabelList.size(); i++) {
					PdaTabViewLabelList pdaTabViewLabelList = fdLabelList.get(i);
					pdaTabViewLabelList.setFdTabModelName(modelName);
					pdaTabViewLabelList.setFdTabModule(fdTabModule);
					pdaTabViewLabelList.setFdTabOrder(i);
				}
			}
		}
		mainModel.setDocAlteror(UserUtil.getUser());
		mainModel.setDocAlterTime(new Date());
		updateMenuVersion();
		super.update(mainModel);
	}

	@Override
    public void updateStatus(String[] ids, String status) throws Exception {
		String sqlIn = HQLUtil.buildLogicIN("pdaTabViewConfigMain.fdId",
				ArrayUtil.convertArrayToList(ids));
		getBaseDao().getHibernateSession().createQuery(
				"update PdaTabViewConfigMain pdaTabViewConfigMain "
						+ "set pdaTabViewConfigMain.fdStatus='" + status
						+ "' where " + sqlIn).executeUpdate();
		updateMenuVersion();
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

}
