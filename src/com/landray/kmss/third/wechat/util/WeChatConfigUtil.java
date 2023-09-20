package com.landray.kmss.third.wechat.util;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.third.wechat.model.WechatConfig;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

public class WeChatConfigUtil {
	
//	public static String wechatBaseUrl;
	
	public static String scene;
	
//	public static String notifyUrl;
//	
//	public static String oauthUrl;
//	
//	public static String enable;
//	
//	
//	public static String qyWechatBaseUrl;
//	
//	public static String qyNotifyUrl;
//	
//	public static String qyOauthUrl;
//	
//	public static String qyEnable;
	
//	public static Properties properties = new Properties();
	static {
		TransactionStatus status = TransactionUtils.beginNewReadTransaction();
		try {
//			InputStream confStream = WeChatConfigUtil.class
//			.getResourceAsStream("wechatconfig.properties");
//			properties.load(confStream);
//			
//			wechatBaseUrl = properties.getProperty("lwechat.base.url");
//			notifyUrl = properties.getProperty("lwechat.notify.url");
//			oauthUrl = properties.getProperty("lwechat.oauth.url");
//			enable = properties.getProperty("lwechat.enable");
//			
//			qyWechatBaseUrl = properties.getProperty("lwechat.qybase.url");
//			qyNotifyUrl = properties.getProperty("lwechat.qynotify.url");
//			qyOauthUrl = properties.getProperty("lwechat.qyoauth.url");
//			qyEnable = properties.getProperty("lwechat.qyenable");
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			Query query = baseDao.getHibernateSession().createQuery("from WechatConfig where fdScene is not null");
			query.setFirstResult(0);
			query.setMaxResults(1);
			List list = query.list();
			if(list!=null && list.size()>0){
				WechatConfig wc = (WechatConfig)list.get(0);
				scene = wc.getFdScene();
			}
			TransactionUtils.commit(status);
		} catch (Exception e) {
			TransactionUtils.rollback(status);
			e.printStackTrace();
		}
	}
	
//	public static void initPropertis() {
//		try {
//			InputStream confStream = WeChatConfigUtil.class
//				.getResourceAsStream("wechatconfig.properties");
//			properties.load(confStream);
//			
//			wechatBaseUrl = properties.getProperty("lwechat.base.url");
//			notifyUrl = properties.getProperty("lwechat.notify.url");
//			oauthUrl = properties.getProperty("lwechat.oauth.url");
//			enable = properties.getProperty("lwechat.enable");
//			
//			qyWechatBaseUrl = properties.getProperty("lwechat.qybase.url");
//			qyNotifyUrl = properties.getProperty("lwechat.qynotify.url");
//			qyOauthUrl = properties.getProperty("lwechat.qyoauth.url");
//			qyEnable = properties.getProperty("lwechat.qyenable");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
