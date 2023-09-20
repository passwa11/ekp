package com.landray.kmss.hr.staff.service;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.sunbor.web.tag.Page;

/**
 * 合同信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public interface IHrStaffPersonExperienceContractService extends
		IHrStaffPersonExperienceBaseService {
	/**
	 * 根据周期查询对应的合同列表、分页查询
	 * @param searchDateType 周期类型
	 * @return
	 * @throws Exception
	 */
	Page findByContractPage(String searchDateType, Date beginDate, int rowSize, int pageNo) throws Exception ;

	public static final String FD_ATT_KEY = "attHrExpCont";
	public List<HrStaffPersonExperienceContract> findByContract(String EndDate)
	throws Exception;

	public void updateStatus() throws Exception;

	/**
	 * <p>合同续签</p>
	 * @throws Exception
	 * @author sunj
	 */
	public void saveRenewContract(String oldContractId, IExtendForm form, RequestContext requestContext)
			throws Exception;

	/**
	 * <p>查询员工上次签订的合同</p>
	 * @param personId
	 * @return
	 * @author sunj
	 */
	public HrStaffPersonExperienceContract findContractByPersonId(String personId) throws Exception;

	/**
	 * <p>
	 * 查询员工所有签订合同
	 * </p>
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public List<HrStaffPersonExperienceContract>
			findContractListByPersonId(String personId) throws Exception;

	/**
	 * <p>
	 * 查询重复合同
	 * </p>
	 *
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	public List<HrStaffPersonExperienceContract> findByContract(HrStaffPersonInfo hrStaffPersonInfo, RequestContext requestContext) throws Exception;
}
