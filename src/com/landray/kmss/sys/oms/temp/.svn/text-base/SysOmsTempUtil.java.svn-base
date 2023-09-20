package com.landray.kmss.sys.oms.temp;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.lic.SystemParameter;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;

/**
 * 同步工具类
 * @author yuliang
 * 创建时间：2020年5月30日
 */
public class SysOmsTempUtil {
	private static Log logger = LogFactory.getLog(SysOmsTempUtil.class);
	private static ISysOrgPersonService sysOrgPersonService;
	private static ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}

		return sysOrgPersonService;
	}
	/**
	 * 获取同步配置json字符串
	 * @param synConfig
	 * @return
	 */
	public static String getSynConfigStr(SysOmsSynConfig synConfig) {
		return JSONObject.toJSONString(synConfig);
	}
	
	/**
	 * 获取同步配置对象
	 * @param synConfigStr
	 * @return
	 */
	public static SysOmsSynConfig getSynConfig(String synConfigStr) {
		return JSONObject.parseObject(synConfigStr, SysOmsSynConfig.class);
	}

	/**
	 * 性别转换
	 * @param sex
	 * @return
	 */
	public static String convertSex(String sex){
		if(StringUtil.isNull(sex)) {
            return "";
        }
		if("男".equals(sex) || "1".equals(sex)){
			return "M";
		}else if("女".equals(sex)  || "0".equals(sex)){
			return "F";
		}else if("M".equals(sex) || "F".equals(sex)) {
			return sex;
		}
		return "";
	}
	
	/**
	 * 日期转换
	 * @param date
	 * @return
	 */
	public static Date convertDate(Long date){
		if(date == null) {
            return null;
        }
		try {
			return new Date(date);
		} catch (Exception e) {
			logger.warn("日期转换失败",e);
		}
		return null;
	}
	
	/**
	 * 检查人员总量
	 * @param personId
	 * @throws Exception
	 */
	public static void checkCount() throws Exception {
		
	    if("true".equals(LicenseUtil.get("license-org-person-import"))){
	        int i_count = StringUtil.getIntFromString(LicenseUtil.get("license-org-person"), -1);
	        if (i_count > -1) { //unlimited
	            int count = getPersonAllCount();
	            if (count >= i_count) {
	                // 当可使用数量不足时，不抛异常，只是创建的用户不能登录，无法使用
	                // throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgPerson.error.exceed.limit"));
	                // 保存为受限制账号
	            	 throw new Exception("人员总量超过license限制");
	            }
	        }
	    }else{
	        String s_count = SystemParameter.get("license-org-person");
	        if (StringUtil.isNotNull(s_count)) {
	            int i_count = Integer.valueOf(s_count);
	            if (i_count >= 9999999) {
	                return;
	            }
	            int count = getPersonAllCount();
	            if (count >= i_count) {
	                throw new Exception("人员总量超过license限制");
	            }
	        }
	    }
	}
	
	private static int getPersonAllCount() throws Exception {
		int total = 0;
		long starttime = System.currentTimeMillis();
		//		total = ((Long) getSysOrgPersonService().getBaseDao().getHibernateSession()
		//				.createQuery(
		//				"select count(*) from com.landray.kmss.sys.organization.model.SysOrgPerson where fdIsAvailable is true").iterate().next()).intValue();
		Query query = getSysOrgPersonService().getBaseDao().getHibernateSession()
				.createQuery(
						"select count(*) from com.landray.kmss.sys.organization.model.SysOrgPerson where fdIsAvailable is true");
		// 启用二级缓存
		query.setCacheable(true);
		// 设置缓存模式
		query.setCacheMode(CacheMode.NORMAL);
		// 设置缓存区域
		query.setCacheRegion("sys-oms");
		total = ((Long) query.iterate().next()).intValue();

		if(logger.isDebugEnabled()) {
			 logger.debug("查询人员总数总共耗时"+(System.currentTimeMillis()-starttime)+"ms");
		}
	   
		return total;
     }
}
