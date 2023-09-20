package com.landray.kmss.third.im.kk.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.model.KkImConfig;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.util.HttpRequest;
import com.landray.kmss.third.im.kk.util.KKConfigUtil;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;
/**
 * 文档类业务接口实现
 * 
 * @author 
 * @version 1.0 2017-08-16
 */
public class KkImConfigServiceImp extends ExtendDataServiceImp implements IKkImConfigService, ApplicationListener {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkImConfigServiceImp.class);
	public IPdaModuleConfigMainService pdaModuleConfigMainService;

	public IPdaModuleConfigMainService getPdaModuleConfigMainService() {
		if (pdaModuleConfigMainService == null) {
			pdaModuleConfigMainService = (IPdaModuleConfigMainService) SpringBeanUtil
					.getBean("pdaModuleConfigMainService");
		}
		return pdaModuleConfigMainService;
	}

	@Override
	public void updateValueBykey(String key, String value) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" kkImConfig.fdKey = :key");
			hqlInfo.setParameter("key", key);
			List<KkImConfig> list = this.findList(hqlInfo);
			if (list == null || list.isEmpty()) {
				KkImConfig config = new KkImConfig();
				config.setFdKey(key);
				config.setFdValue(value);
				this.add(config);
			} else {
				String hql = "UPDATE KkImConfig SET fdValue = :value WHERE fdKey = :key";
				this.getBaseDao().getHibernateSession().createQuery(hql)
						.setParameter("value", value).setParameter("key", key)
						.executeUpdate();
			}
			//this.getBaseDao().getHibernateSession().clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll() {
		String hql = "DELETE FROM KkImConfig";
		this.getBaseDao().getHibernateSession().createQuery(hql).executeUpdate();
		this.getBaseDao().getHibernateSession().clear();
	}

	/**
	 * <p>事件监听</p>
	 * @param arg0
	 * @author 孙佳
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if ("orgVisible".equals(event.getSource().toString())) {
			//发送可见性同步通知
			logger.info("----------发送可见性同步通知-------------");
			sendSynkknotify(KeyConstants.EKP_ORG_VISIBLE);
		} else if ("mobileApp".equals(event.getSource().toString())) {
			//发送移动应用同步通知
			logger.info("----------发送移动应用同步通知-------------");
			sendSynkknotify(KeyConstants.EKP_MOBILE_APP);
		} else if ("sso".equals(event.getSource().toString())) {
			//发送sso同步通知
			logger.info("----------发送sso同步通知-------------");
			sendSynkknotify(KeyConstants.EKP_SSO);
		} else if ("com.landray.kmss.third.intell.model.IntellConfig".equals(event.getSource().toString())) {
			//发送智能助手同步通知
			logger.info("----------发送智能助手同步通知-------------");
			sendSynkknotify(KeyConstants.ROBOT);
		} else if ("extendApp".equals(event.getSource().toString())) {
			//发送其他业务参数同步通知
			logger.info("----------发送其他业务参数同步通知-------------");
			sendSynkknotify(KeyConstants.EXTEND_APP);
		}
	}

	/**
	 * <p>向kk发送同步通知</p>
	 * @author 孙佳
	 */
	private void sendSynkknotify(String type) {
		boolean org = false, sso = false, pc_app = false, mobile_app = false, org_visible = false, robot = false,
				extend_app = false;
		try {
			//检查是否开启一体化配置
			String kkConfigStatus = getValuebyKey(KeyConstants.KK_CONFIG_SATUS);
			if (StringUtil.isNull(kkConfigStatus) || "false".equals(kkConfigStatus)) {
				return;
			}

			String conAddress = getValuebyKey(KeyConstants.KK_CONSOLE_ADDRESS);
			//签名
			String secretkey = getValuebyKey(KeyConstants.EKP_SECRETKEY);
			String sign = KKConfigUtil.getCurrDateSign(secretkey);

			// 调用kk交换参数接口
			JSONObject param = new JSONObject();
			if (type.equals(KeyConstants.EKP_ORG)) {
				org = true;
			}
			if (type.equals(KeyConstants.EKP_SSO)) {
				sso = true;
			}
			if (type.equals(KeyConstants.EKP_PC_APP)) {
				pc_app = true;
			}
			if (type.equals(KeyConstants.EKP_MOBILE_APP)) {
				mobile_app = true;
			}
			if (type.equals(KeyConstants.EKP_ORG_VISIBLE)) {
				org_visible = true;
			}
			if (type.equals(KeyConstants.ROBOT)) {
				robot = true;
			}
			if (type.equals(KeyConstants.EXTEND_APP)) {
				extend_app = true;
			}
			param.put("org", org);
			param.put("sso", sso);
			param.put("pc_app", pc_app);
			param.put("mobile_app", mobile_app);
			param.put("org_visible", org_visible);
			param.put("robot", robot);
			param.put("extend_app", extend_app);
			String url = conAddress + "ekp_exchange?action=notify&sign=" + sign;
			String result = HttpRequest.sendPost(url, param.toString());
			logger.info(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key获取对应的value
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getValuebyKey(String key) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" kkImConfig.fdKey = :key");
			hqlInfo.setParameter("key", key);
			List<KkImConfig> list = this.findList(hqlInfo);
			if (null != list && list.size() > 0) {
				return list.get(0).getFdValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isEnableKKConfig() {
		try {
			String hql = "select kkImConfig from KkImConfig kkImConfig where kkImConfig.fdKey =?";
			List<KkImConfig> list = (List<KkImConfig>) this.getBaseDao().getHibernateTemplate()
					.find(hql, new Object[] { KeyConstants.KK_CONFIG_SATUS });
			if (null != list && list.size() > 0) {
				String value = list.get(0).getFdValue();
				return Boolean.TRUE.equals(Boolean.valueOf(value));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	

	@Override
	public boolean isChangeKKqrcodeEnabled() {
		try {
			String hql = "select kkImConfig from KkImConfig kkImConfig where kkImConfig.fdKey =?";
			List<KkImConfig> list = (List<KkImConfig>) this.getBaseDao().getHibernateTemplate()
					.find(hql, new Object[] { KeyConstants.CHANGE_KK_QRCODE_ENABLED });
			System.out.println(list);
			if (null != list && list.size() > 0) {
				String value = list.get(0).getFdValue();
				return Boolean.TRUE.equals(Boolean.valueOf(value));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public JSONObject getKKGroupAppConfig() {
		JSONObject json = new JSONObject();
		String enableTransfer = this.getValuebyKey("imTransferEnable");
		boolean enable = StringUtil.isNull(enableTransfer)
				|| "true".equals(enableTransfer);
		json.put("enable", enable);
		return json;
	}

	@Override
	public String getAppCode(String urlPrefix) {
		if(StringUtil.isNull(urlPrefix)){
			return null;
		}
		try {
			List<PdaModuleConfigMain> list = getPdaModuleConfigMainService()
					.findList(new HQLInfo());
			String appCode = null;
			for (PdaModuleConfigMain configMain : list) {
				if ("1".equals(configMain.getFdStatus())) {
					if (configMain.getFdUrlPrefix() != null && configMain
							.getFdUrlPrefix().indexOf(urlPrefix) > -1) {
						appCode = configMain.getFdId();
						break;
					}
				}
			}
			return appCode;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取appcode失败:", e);
		}
		return null;
	}

	
}
