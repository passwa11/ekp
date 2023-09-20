package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCostType;

public interface IEopBasedataCostCenterService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataCostCenter> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<EopBasedataCostCenter> findByFdType(EopBasedataCostType fdType) throws Exception;

    public abstract List<EopBasedataCostCenter> findByDocParent(EopBasedataCostCenter fdParent) throws Exception;

    public EopBasedataCostCenter getEopBasedataCostCenterByCode(String fdCompanyId, String fdCode) throws Exception;

	public abstract EopBasedataCostCenter findCostCenterByUserId(String fdCompanyId, String fdPersonId)  throws Exception;
	
	public abstract List<String> getCenterByUserId(String fdUserId)  throws Exception;
}
