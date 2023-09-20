package com.landray.kmss.km.archives.service.spring;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.archives.model.KmArchivesSign;
import com.landray.kmss.km.archives.service.IKmArchivesSignService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.SignUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class KmArchivesSignServiceImp extends ExtendDataServiceImp
		implements IKmArchivesSignService {
    
	public IKmArchivesSignService getKmArchivesSignService() {
		return (IKmArchivesSignService) SpringBeanUtil
				.getBean("kmArchivesSignService");
	}

	@Override
	public KmArchivesSign getKmArchivesSignByModelId(String fdTempModelId) {
		List<KmArchivesSign> signtempList = null;
		try {
			HQLInfo hql = new HQLInfo();
			hql.setWhereBlock("kmArchivesSign.fdTempModel = :fdTempModel");
			hql.setParameter("fdTempModel", fdTempModelId);
			hql.setOrderBy("kmArchivesSign.fdTempModel asc");
			signtempList = getBaseDao().findValue(hql);
			if(null != signtempList && !signtempList.isEmpty()){
				return signtempList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	@Override
	public String addSignForArchives(String mainModelId, String mainModelDesc) {
		//final long sizes = 100000000;
		//long expires = (long) ((Math.random() + 1) * sizes) + System.currentTimeMillis();
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//生成10位数的随机数 #fix 使用不够随机的伪随机数
		Integer expires = random.nextInt();
		String signStr = expires + ":" + mainModelId;
		String sign = SignUtil.getHMAC(signStr + ":" + mainModelId, mainModelId);
		try {
			if ("kmAgreementApply".equals(mainModelDesc)) {
				//合同管理会有多次归档，但是一个模型只需要保存一条签名记录
				KmArchivesSign kmArchivesSign = getKmArchivesSignByModelId(mainModelId);
				if (kmArchivesSign == null) {
					kmArchivesSign = new KmArchivesSign();
					kmArchivesSign.setFdTempModel(mainModelId);
					kmArchivesSign.setFdTempSign(Long.toString(expires));
					kmArchivesSign.setFdRemarks(mainModelDesc);
					super.add(kmArchivesSign);
				} else {
					kmArchivesSign.setFdTempSign(Long.toString(expires));
					super.update(kmArchivesSign);
				}
			} else {
				//update Archives Sign Temp
				KmArchivesSign kmArchivesSign = new KmArchivesSign();
				kmArchivesSign.setFdTempModel(mainModelId);
				kmArchivesSign.setFdTempSign(Long.toString(expires));
				kmArchivesSign.setFdRemarks(mainModelDesc);
				super.add(kmArchivesSign);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}
	
	@Override
	public boolean validateArchivesSignature(String expires, String fdId, String sign, Logger logger) throws Exception {
		// 外部过来的生成归档页面请求，如果signature验证通过，允许免登录
		boolean auth = checkArchivesSignature(expires, sign,fdId);
		if (auth){
			logger.info("kmReviewArchivesSignValidator.validate success,no need to login. download fdModelId:"
					+ fdId + "\nexpires:" + expires + ",sign:" + sign);
			return true;
		}
		return false;
	}
	
	private boolean checkArchivesSignature(String expires, String sign, String fdId) throws Exception {
		boolean auth = false;
		if(StringUtil.isNotNull(sign) && StringUtil.isNotNull(expires)){
			long expiresTime = Long.valueOf(expires);
			if(expiresTime < System.currentTimeMillis()){
				return false;
			}
			sign = sign.replaceAll(" ", "+");
			KmArchivesSign kmArchivesSign = getKmArchivesSignByModelId(fdId);
			if(null == kmArchivesSign){
				return auth;
			}
			String expiresign = kmArchivesSign.getFdTempSign();
			if(null != expiresign){
				String signStr = expiresign + ":" + fdId;
				String calcSign = SignUtil.getHMAC(signStr + ":" + fdId
						, fdId);
				if(sign.equals(calcSign)){
					auth = true;
					
				}
			}
		}
		return auth;
	}
	
	@Override
	public void deleteSign(String fdModelId) throws Exception {
		try {
			HQLInfo hql = new HQLInfo();
			hql.setWhereBlock("kmArchivesSign.fdTempModel = :fdTempModel");
			hql.setParameter("fdTempModel", fdModelId);
			List<KmArchivesSign> signtempList = getBaseDao().findList(hql);
			if (signtempList.size() > 0) {
				for (KmArchivesSign ks : signtempList) {
					delete(ks);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

