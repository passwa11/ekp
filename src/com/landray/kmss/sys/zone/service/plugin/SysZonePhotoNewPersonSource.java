package com.landray.kmss.sys.zone.service.plugin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.dict.SysZonePhotoSource;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;


/**
 * 最新员工照片墙数据源
 */
public class SysZonePhotoNewPersonSource implements ISysZonePhotoSourceService{
	

	@Override
	public SysZonePhotoSource getSource(ServletContext servletContext) throws Exception {
		SysZonePhotoSource source = new SysZonePhotoSource();
		source.setId("newperson");
		source.setName("最新员工");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy("fdCreateTime desc");
		hqlInfo.setWhereBlock(" sysOrgPerson.fdIsAvailable=:fdIsAvailable and sysOrgPerson.fdOrgType=:fdOrgType and sysOrgPerson.fdIsBusiness=:fdIsBusiness");
		hqlInfo.setParameter("fdIsBusiness", Boolean.TRUE);
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_PERSON);
		hqlInfo.setRowSize(200);
		ISysOrgPersonService sysOrgPersonService = 
			 (ISysOrgPersonService)SpringBeanUtil.getBean("sysOrgPersonService");
		List<SysOrgPerson> personList = (List<SysOrgPerson>)
				sysOrgPersonService.findPage(hqlInfo).getList();
		/**
		 * MAP必须有三个值 
		 * {
		 * 	href： 点击头像的链接
		 *  imgUrl：鼠标移上去时图片请求路径
		 *  attId ：附件Id或图片流
		 * }
		 */
		ArrayList<Map<String , Object>> list = new ArrayList<Map<String , Object>>();
	    ISysAttMainCoreInnerService sysAttMainService = 
		  (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
		for(SysOrgPerson person : personList) {
			HashMap<String, Object> mPerson = new HashMap<String, Object>();
			List<SysAttMain> attMainList = (List<SysAttMain>)sysAttMainService
				.findByModelKey("com.landray.kmss.sys.zone.model.SysZonePersonInfo", person.getFdId(), 
						SysZoneConstant.MEDIUM_PHOTO_KEY);
			mPerson.put("href", "/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" + person.getFdId());
			mPerson.put("title", person.getFdName());
			if(!ArrayUtil.isEmpty(attMainList)) {
				SysAttMain attMain = attMainList.get(0);	
				mPerson.put("attId", attMain.getFdId());
				mPerson.put("imgUrl", servletContext.getContextPath() 
						+ "/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=" 
						+ person.getFdId() + "&size=" + SysZoneConstant.MEDIUM_PHOTO 
						+ "&_tmp=" + new Date().getTime());
			} else {
				String defaultImg = 
					(StringUtil.isNotNull(person.getFdSex()) && "F".equals(person.getFdSex()))
						? SysZoneConstant.DEFAULT_PERSONIMG_F : SysZoneConstant.DEFAULT_PERSONIMG_M;
				InputStream in = new FileInputStream(ConfigLocationsUtil.getWebContentPath() + 
						defaultImg);
				mPerson.put("byteArray", in);
				mPerson.put("imgUrl", servletContext.getContextPath() + defaultImg);
			}
			list.add(mPerson);
		}
		source.setImgs(list);
		return source;
	}
	// 取上传过头像的人 
	/*@Override 
	public SysZonePhotoSource getSource(ServletContext servletContext) throws Exception {
		SysZonePhotoSource source = new SysZonePhotoSource();
		source.setId("newperson");
		source.setName("最新员工");
		
		String hql = " select sysAttMain.fdId, sysOrgPerson.fdId from com.landray.kmss.sys.attachment.model.SysAttMain sysAttMain," +
					 " com.landray.kmss.sys.organization.model.SysOrgElement sysOrgPerson where " + 
					 " sysOrgPerson.fdIsAvailable=:fdIsAvailable and sysOrgPerson.fdOrgType=:fdOrgType and sysOrgPerson.fdIsBusiness=:fdIsBusiness" +
					 " and sysAttMain.fdModelId = sysOrgPerson.fdId and sysAttMain.fdModelName =:fdModelName " +
					 " and sysAttMain.fdKey=:middleKey order by sysOrgPerson.fdCreateTime desc";
		
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil
			.getBean("KmssBaseDao");
		Query query = baseDao.getHibernateSession().createQuery(hql);
		query.setParameter("fdIsBusiness", Boolean.TRUE)
			.setParameter("fdIsAvailable", Boolean.TRUE)
			.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_PERSON)
			.setParameter("fdModelName", SysZonePersonInfo.class.getName())
			.setParameter("middleKey", SysZoneConstant.MEDIUM_PHOTO_KEY);
			
		query.setFirstResult(0);
		query.setMaxResults(200);
		List attIds = query.list();
		*//**
		 * MAP必须有三个值 
		 * {
		 * 	href： 点击头像的链接
		 *  imgUrl：鼠标移上去时图片请求路径
		 *  attId ：附件Id或图片流
		 * }
		 *//*
		ArrayList<Map<String , Object>> list = new ArrayList<Map<String , Object>>();
		for(Object idArray : attIds) {
			Object[] ids = (Object []) idArray;
			HashMap<String, Object> mPerson = new HashMap<String, Object>();
			mPerson.put("attId", (String)ids[0]);
			mPerson.put("href", "/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" + ids[1].toString());
			mPerson.put("imgUrl", servletContext.getContextPath() 
					+ "/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=" + ids[1].toString() + "&size=" + SysZoneConstant.MEDIUM_PHOTO);
			list.add(mPerson);
		}
		source.setImgs(list);
		return source;
	}*/

}
