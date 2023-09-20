/**
 * 
 */
package com.landray.kmss.sys.zone.service.spring;

import java.util.HashMap;

import org.hibernate.query.Query;
import org.hibernate.Session;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.person.interfaces.CategoryStatus;
import com.landray.kmss.sys.person.service.spring.BaseCategoryService;
import com.landray.kmss.sys.zone.model.SysZoneNavLink;
import com.landray.kmss.sys.zone.model.SysZoneNavigation;
import com.landray.kmss.sys.zone.service.ISysZoneNavigationService;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author 傅游翔
 * 
 */
public class SysZoneNavigationServiceImp extends BaseCategoryService implements
		ISysZoneNavigationService {

	@Override
    public SysZoneNavLink findNavLink(String id) throws Exception {
		return (SysZoneNavLink) getBaseDao().findByPrimaryKey(id,
				SysZoneNavLink.class, true);
	}
	


	@Override
	public void updateStatus(String[] ids, String fdStatus, String showType)
			throws Exception {
		//将其他的启用置为禁用
		if(StringUtil.isNotNull(showType) && CategoryStatus.parseStatus(fdStatus) == CategoryStatus.STARTED) {
			if(showType.indexOf(SysZoneConfigUtil.TYPE_PC_KEY) > -1) {
				updateStatusByType( CategoryStatus.STOPED.getValue() , SysZoneConfigUtil.TYPE_PC_KEY, null);
			} 
			if(showType.indexOf(SysZoneConfigUtil.TYPE_MOBILE_KEY) > -1) {
				updateStatusByType( CategoryStatus.STOPED.getValue() , SysZoneConfigUtil.TYPE_MOBILE_KEY, null);
			} 
		}
		if(UserOperHelper.allowLogOper("updateStatus", getModelName())){
			if("1".equals(fdStatus)){
				UserOperHelper.setEventType(ResourceUtil.getString("sys-zone:status.started"));
			}
			if("2".equals(fdStatus)){
				UserOperHelper.setEventType(ResourceUtil.getString("sys-zone:status.stoped"));
			}
			for(int i = 0;i<ids.length;i++){
				UserOperContentHelper.putUpdate(ids[i], "",getModelName());
			}
		}
		
		super.updateStatus(ids, fdStatus);
	}
	
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysZoneNavigation nav = (SysZoneNavigation)modelObj;
		if(CategoryStatus.STARTED.getValue().equals(nav.getFdStatus())) {
			updateStatusByType( CategoryStatus.STOPED.getValue(), nav.getFdShowType(), null);
		}
		return super.add(modelObj);
	}
	
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysZoneNavigation nav = (SysZoneNavigation)modelObj;
		if(CategoryStatus.STARTED.getValue().equals(nav.getFdStatus())) {
			updateStatusByType( CategoryStatus.STOPED.getValue(), nav.getFdShowType(), new String[]{modelObj.getFdId()});
		}
		super.update(modelObj);
	}
	
	private void  updateStatusByType(Integer status, String showType, String[] exceptIds) throws Exception {
		IBaseDao dao = this.getBaseDao(); 
		Session session = dao.getHibernateSession();
		HashMap<String , Object> pro = new HashMap<String , Object>();
		String sql = "update " + dao.getModelName() + " a set a.fdStatus=:stop where a.fdShowType =:showType";
		if(exceptIds != null && exceptIds.length > 0 ) {
			sql = StringUtil.linkString(sql, " and ", " a.fdId not in(:exceptIds)");
			pro.put("exceptIds", Arrays.asList(exceptIds));
		}
		Query q = session.createQuery(sql);
		pro.put("stop", status);
		pro.put("showType", showType);
		q.setProperties(pro);
		q.executeUpdate();
	}
	
	
}
