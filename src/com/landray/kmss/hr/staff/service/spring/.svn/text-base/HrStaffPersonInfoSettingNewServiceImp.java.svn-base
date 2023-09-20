package com.landray.kmss.hr.staff.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoSettingNewService;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

public class HrStaffPersonInfoSettingNewServiceImp extends BaseServiceImp
		implements ApplicationListener<Event_ClusterReady>,IHrStaffPersonInfoSettingNewService, ICheckUniqueBean {

	@Override
	public String add(IBaseModel model) throws Exception{
		//有修改和删除，清空导入里面的缓存数据
		if(HrStaffImportServiceImp.hrConfigMap !=null) {
			HrStaffImportServiceImp.hrConfigMap.clear();
		}
		return super.add(model);
	}
	@Override
	public void update(IBaseModel model) throws Exception{
		if(HrStaffImportServiceImp.hrConfigMap !=null) {
			HrStaffImportServiceImp.hrConfigMap.clear();
		}
		super.update(model);
	}

	@Override
    public List<HrStaffPersonInfoSettingNew> getByType(String fdType)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffPersonInfoSettingNew.fdType = :fdType");
		hqlInfo.setOrderBy("fdOrder asc");
		hqlInfo.setParameter("fdType", fdType);
		return super.findList(hqlInfo);
	}

	// fdName添加唯一性校验
	@Override
    public String checkUnique(RequestContext request) throws Exception {
		String fdId = request.getParameter("fdId");
		String fdName = request.getParameter("fdName");
		String fdType = request.getParameter("fdType");
		HQLInfo hqlInfo = new HQLInfo();
		String result = "";
		hqlInfo.setWhereBlock(
				"hrStaffPersonInfoSettingNew.fdName = :fdName and hrStaffPersonInfoSettingNew.fdType = :fdType");
		hqlInfo.setParameter("fdName", fdName);
		hqlInfo.setParameter("fdType", fdType);
		List<HrStaffPersonInfoSettingNew> lists = findList(hqlInfo);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result += lists.get(0).getFdName();
		}
		return result;
	}

	@Override
	public HrStaffPersonInfoSettingNew getByType(String fdType, String fdId) throws Exception {
		if (StringUtil.isNull(fdId)) {
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffPersonInfoSettingNew.fdType = :fdType and hrStaffPersonInfoSettingNew.fdId = :fdId");
		hqlInfo.setParameter("fdType", fdType);
		hqlInfo.setParameter("fdId", fdId);
		List<HrStaffPersonInfoSettingNew> list = this.findList(hqlInfo);
		return (ArrayUtil.isEmpty(list) ? null : list.get(0));
	}

	@Override
	public void onApplicationEvent(Event_ClusterReady event) { 
		addInitNatureWork(); 
	}

	// 初始化系统默认的工作性质
	private void addInitNatureWork(){
		TransactionStatus status =null;
		try { 
			status =TransactionUtils.beginNewTransaction();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("hrStaffPersonInfoSettingNew.fdType = :fdType");
			hqlInfo.setParameter("fdType", "fdNatureWork");
			List<HrStaffPersonInfoSettingNew> lists = findList(hqlInfo);
			List<String> nameList = new ArrayList<>();
			Map<String, HrStaffPersonInfoSettingNew> nameMap = new HashMap<>();
			if (!ArrayUtil.isEmpty(lists)) {
				for (HrStaffPersonInfoSettingNew natureWork : lists) {
					String name = natureWork.getFdName();
					if (StringUtil.isNotNull(name) && !nameList.contains(name)) {
						nameList.add(name);
						nameMap.put(name, natureWork);
					}
				}
			}

			TransactionUtils.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			if(status !=null) {
				TransactionUtils.rollback(status);
			}
		}
	}

}
