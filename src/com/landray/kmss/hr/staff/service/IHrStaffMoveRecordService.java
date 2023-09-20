package com.landray.kmss.hr.staff.service;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.forms.HrStaffMoveRecordForm;
import com.landray.kmss.hr.staff.model.HrStaffMoveRecord;
import com.landray.kmss.util.KmssMessage;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

/**
  * 异动信息 服务接口
 * @author liuyang
 */
public interface IHrStaffMoveRecordService extends IBaseService {
    /**
     * 导入excel数据据
     */
    public KmssMessage saveImportData(HrStaffMoveRecordForm moveRecordForm) throws Exception;

    /**
     * 获取当月异动数据
     * @return
     * @throws Exception
     */
    public List<String[]> findMoveMonthData(HttpServletRequest request) throws Exception;
    public List<String[]> findStatListDetail(Date start, Date end, List<String> targetIds)throws Exception;

    /**
     * 获取异动数据
     * @param request
     * @return
     * @throws Exception
     */
    public List<String[]> findMoveAllData(HttpServletRequest request) throws Exception;

    /**
     * 招聘需求报表
     */
    public List<String[]> findRecruitData(HttpServletRequest request) throws Exception;

    public List<String[]> findMoveMonthData1(String fdTransDept, String y, String m, String No) throws Exception;

	public void add1(HrStaffMoveRecord modelObj) throws Exception;


}
