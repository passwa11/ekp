package com.landray.kmss.third.pda.service.spring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.pda.model.PdaHomePageConfig;
import com.landray.kmss.third.pda.model.PdaVersionConfig;
import com.landray.kmss.third.pda.service.IPdaHomePageConfigService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.UserUtil;

public class PdaHomePageConfigServiceImp extends BaseServiceImp implements
		IPdaHomePageConfigService {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		PdaHomePageConfig pdaHomePageConfig = (PdaHomePageConfig) modelObj;
		pdaHomePageConfig.setDocCreator(UserUtil.getUser());
		pdaHomePageConfig.setFdCreateTime(new Date());
		for (int i = 0; i < pdaHomePageConfig.getFdPortlets().size(); i++) {
			// 更新列表页签排序
			pdaHomePageConfig.getFdPortlets().get(i).setFdOrder(i);
		}
		if("1".equals(pdaHomePageConfig.getFdIsDefault())){
			String[] ids={pdaHomePageConfig.getFdId()};
			updateStatus(ids);
		}else {
            updateHomePageVersion();
        }
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		PdaHomePageConfig pdaHomePageConfig = (PdaHomePageConfig) modelObj;
		pdaHomePageConfig.setDocAlteror(UserUtil.getUser());
		pdaHomePageConfig.setDocAlterTime(new Date());
		for (int i = 0; i < pdaHomePageConfig.getFdPortlets().size(); i++) {
			// 更新列表页签排序
			pdaHomePageConfig.getFdPortlets().get(i).setFdOrder(i);
		}
		if("1".equals(pdaHomePageConfig.getFdIsDefault())){
			String[] ids={pdaHomePageConfig.getFdId()};
			updateStatus(ids);
		}else {
            updateHomePageVersion();
        }
		super.update(modelObj);
	}

	private void updateHomePageVersion() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		PdaVersionConfig version = new PdaVersionConfig();
		version.setHomePageVersion(df.format(new Date()));
		version.save();
	}

	@Override
	public String getOwerPage(RequestContext requestContext) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("pdaHomePageConfig.fdIsDefault='1'");
		hqlInfo.setSelectBlock("pdaHomePageConfig.fdId");
		return (String) findFirstOne(hqlInfo);
	}

	@Override
	public String[] getAllPages(RequestContext requestContext) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("pdaHomePageConfig.fdId");
		List list = findList(hqlInfo);
		if (!list.isEmpty()) {
            return (String[]) list.toArray(new String[list.size()]);
        }
		return null;
	}

	@Override
	public void updateStatus(String[] ids) throws Exception {
		List idList = ArrayUtil.convertArrayToList(ids);
		String inHql = HQLUtil.buildLogicIN("pdaHomePageConfig.fdId", idList);
		getBaseDao().getHibernateSession().createQuery(
				"update PdaHomePageConfig pdaHomePageConfig"
						+ " set pdaHomePageConfig.fdIsDefault='1' where "
						+ inHql).executeUpdate();
		inHql = HQLUtil.buildLogicIN("pdaHomePageConfig.fdId not", idList);
		getBaseDao().getHibernateSession().createQuery(
				"update PdaHomePageConfig pdaHomePageConfig"
						+ " set pdaHomePageConfig.fdIsDefault='0' where "
						+ inHql).executeUpdate();
		updateHomePageVersion();
	}
}
