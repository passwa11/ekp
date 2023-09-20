package com.landray.kmss.km.calendar.cms;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.google.common.util.concurrent.RateLimiter;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;

/**
 * 
 * ICMSProvider装饰器
 * 	提供令牌桶限流功能，默认令牌数为25个/秒
 */
public class ICMSProviderRateLimiter implements ICMSProvider {

	//添加日程限速器
	private final RateLimiter addCalElementRateLimiter;
	//删除日程限速器
	private final RateLimiter delCalElementRateLimiter;
	//更新日程限速器
	private final RateLimiter updateCalElementRateLimiter;

	private ICMSProvider provider;
	
	public ICMSProviderRateLimiter(ICMSProvider provider) {
		this.provider = provider;
		this.addCalElementRateLimiter = RateLimiter.create(25.0);
		this.delCalElementRateLimiter = RateLimiter.create(25.0);
		this.updateCalElementRateLimiter = RateLimiter.create(25.0);
	}

	public ICMSProviderRateLimiter(ICMSProvider provider, RateLimiter addCalElementRateLimiter) {
		this.provider = provider;
		Assert.notNull(addCalElementRateLimiter, "addCalElementRateLimiter参数不能为空!!!");
		this.addCalElementRateLimiter = addCalElementRateLimiter;
		this.delCalElementRateLimiter = RateLimiter.create(25.0);
		this.updateCalElementRateLimiter = RateLimiter.create(25.0);
	}

	public ICMSProviderRateLimiter(ICMSProvider provider, RateLimiter addCalElementRateLimiter,
			RateLimiter delCalElementRateLimiter) {
		this.provider = provider;
		Assert.notNull(addCalElementRateLimiter, "addCalElementRateLimiter参数不能为空!!!");
		Assert.notNull(addCalElementRateLimiter, "delCalElementRateLimiter参数不能为空!!!");
		this.addCalElementRateLimiter = addCalElementRateLimiter;
		this.delCalElementRateLimiter = delCalElementRateLimiter;
		this.updateCalElementRateLimiter = RateLimiter.create(25.0);
	}

	public ICMSProviderRateLimiter(ICMSProvider provider, RateLimiter addCalElementRateLimiter,
			RateLimiter delCalElementRateLimiter, RateLimiter updateCalElementRateLimiter) {
		this.provider = provider;
		Assert.notNull(addCalElementRateLimiter, "addCalElementRateLimiter参数不能为空!!!");
		Assert.notNull(addCalElementRateLimiter, "delCalElementRateLimiter参数不能为空!!!");
		Assert.notNull(addCalElementRateLimiter, "updateCalElementRateLimiter参数不能为空!!!");
		this.addCalElementRateLimiter = addCalElementRateLimiter;
		this.delCalElementRateLimiter = delCalElementRateLimiter;
		this.updateCalElementRateLimiter = updateCalElementRateLimiter;
	}

	@Override
	public ICMSProvider getNewInstance(String personId) throws Exception {
		this.provider = this.provider.getNewInstance(personId);
		return this;
	}

	@Override
	public String addCalElement(String personId, SyncroCommonCal syncroCommonCal) throws Exception {
		this.addCalElementRateLimiter.acquire(); 
		return provider.addCalElement(personId, syncroCommonCal);
	}

	@Override
	public boolean updateCalElement(String personId, SyncroCommonCal syncroCommonCal) throws Exception {
		this.updateCalElementRateLimiter.acquire();
		return provider.updateCalElement(personId, syncroCommonCal);
	}

	@Override
	public boolean deleteCalElement(String personId, String uuid) throws Exception {
		this.delCalElementRateLimiter.acquire();
		return provider.deleteCalElement(personId, uuid);
	}

	@Override
	public List<SyncroCommonCal> getCalElements(String personId, Date date) throws Exception {
		return provider.getCalElements(personId, date);
	}

	@Override
	public List<SyncroCommonCal> getAddedCalElements(String personId, Date date) throws Exception {
		return provider.getAddedCalElements(personId, date);
	}

	@Override
	public List<SyncroCommonCal> getDeletedCalElements(String personId, Date date) throws Exception {
		return provider.getDeletedCalElements(personId, date);
	}

	@Override
	public List<SyncroCommonCal> getUpdatedCalElements(String personId, Date date) throws Exception {
		return provider.getUpdatedCalElements(personId, date);
	}

	@Override
	public String getCalType() {
		return provider.getCalType();
	}

	@Override
	public List<String> getPersonIdsToSyncro() {
		return provider.getPersonIdsToSyncro();
	}

	@Override
	public boolean isNeedSyncro(String personId) {
		return provider.isNeedSyncro(personId);
	}

	@Override
	public boolean isSynchroEnable() throws Exception {
		return provider.isSynchroEnable();
	}
}