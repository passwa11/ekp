package com.landray.kmss.hr.staff.service.robot;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.staff.model.*;
import com.landray.kmss.hr.staff.service.*;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异动信息机器人
 * 更新异动信息到人事档案
 *
 * @author liuyang
 * @date 2022-09-03
 */
public class HrStaffMoveRobotServiceImp extends AbstractRobotNodeServiceImp {
    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
        this.hrStaffPersonInfoService = hrStaffPersonInfoService;
    }

    private ISysOrgElementService sysOrgElementService;

    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    private ISysOrgPostService sysOrgPostService;

    public void setSysOrgPostService(ISysOrgPostService sysOrgPostService) {
        this.sysOrgPostService = sysOrgPostService;
    }

    public IHrOrganizationRankService hrOrganizationRankService;

    public void setHrOrganizationRankService(IHrOrganizationRankService hrOrganizationRankService) {
        this.hrOrganizationRankService = hrOrganizationRankService;
    }

    public IHrStaffMoveRecordService hrStaffMoveRecordService;

    public void setHrStaffMoveRecordService(IHrStaffMoveRecordService hrStaffMoveRecordService) {
        this.hrStaffMoveRecordService = hrStaffMoveRecordService;
    }

    public IProvinceService provinceService;

    public ICitiesService citiesService;

    public IAreasService areasService;

    public void setProvinceService(IProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    public void setCitiesService(ICitiesService citiesService) {
        this.citiesService = citiesService;
    }

    public void setAreasService(IAreasService areasService) {
        this.areasService = areasService;
    }

    @Override
    public void execute(TaskExecutionContext context) throws Exception {
        JSONObject json = (JSONObject) JSONValue.parse(getConfigContent(context));
        saveMainModel(context, json);
    }

    private void saveMainModel(TaskExecutionContext context, JSONObject json) throws Exception {
        JSONObject parameters = (JSONObject) json.get("params");
        IBaseModel mainModel = context.getMainModel();
        if (mainModel instanceof IExtendDataModel) {
            IExtendDataModel model = (IExtendDataModel) mainModel;
            Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
            String fdApplicant = (String) parameters.get("fdApplicant");
            HrStaffPersonInfo personInfo = getPersonInfo(fdApplicant, modelData);
            //更新人事档案信息
            updatePersonInfo(parameters, mainModel, personInfo);
            //新增异动记录
            addMoveRecord(personInfo, parameters, mainModel);
        }
    }

    /**
     * 新增异动信息
     *
     * @param personInfo
     */
    private void addMoveRecord(HrStaffPersonInfo personInfo, JSONObject parameters, IBaseModel mainModel) throws Exception {
        IExtendDataModel model = (IExtendDataModel) mainModel;
        Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();

        HrStaffMoveRecord moveRecord = new HrStaffMoveRecord();
        //姓名
        moveRecord.setFdStaffName(personInfo.getFdName());
        //编号
        moveRecord.setFdStaffNumber(personInfo.getFdStaffNo());
        //异动后部门
        Map<String, String> fdOrgParent = (Map) modelData.get(parameters.get("fdOrgParent"));
        if (fdOrgParent != null && fdOrgParent.containsKey("id")) {
            moveRecord.setFdAfterDept((SysOrgElement) sysOrgElementService.findByPrimaryKey(fdOrgParent.get("id")));
        }
        //异动后岗位
        Map<String, String> fdOrgPost = (Map) modelData.get(parameters.get("fdOrgPost"));
        List<SysOrgPost> posts = new ArrayList<>();
        if (fdOrgPost != null && fdOrgPost.containsKey("id")) {
        	SysOrgPost SysOrgPost = (SysOrgPost) sysOrgPostService.findByPrimaryKey(fdOrgPost.get("id"));
            posts.add(SysOrgPost);
        }
        moveRecord.setFdAfterPosts(posts);
        //异动后职级
        String fdOrgRankId = (String)modelData.get(parameters.get("fdOrgRank"));
        HrOrganizationRank hrOrganizationRank = (HrOrganizationRank)hrOrganizationRankService.findByPrimaryKey(fdOrgRankId);
        moveRecord.setFdAfterRank(hrOrganizationRank != null ? hrOrganizationRank.getFdName() : "");
        String fdBeforeOrgRankId = (String)modelData.get(parameters.get("fdBeforeOrgRank"));
        HrOrganizationRank hrBeforeOrganizationRank = (HrOrganizationRank)hrOrganizationRankService.findByName(fdBeforeOrgRankId);
        moveRecord.setFdBeforeRank(fdBeforeOrgRankId);
        //异动后直接上级
        Map<String, String> fdReportLeader = (Map) modelData.get(parameters.get("fdReportLeader"));
        if (fdReportLeader != null && fdReportLeader.containsKey("id")) {
            SysOrgElement fdLeader = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdReportLeader.get("id"));
            moveRecord.setFdAfterLeader(fdLeader);
        }

        String fdBeforeReportLeader =  (String) modelData.get(parameters.get("fdBeforeReportLeader"));
        if (fdBeforeReportLeader != null) {
            SysOrgElement fdLeader = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdBeforeReportLeader);
            moveRecord.setFdBeforeLeader(fdLeader);
        }
        //异动时间
        Date fdMoveDate = (Date) modelData.get(parameters.get("fdMoveDate"));
        if(fdMoveDate == null){
            moveRecord.setFdMoveDate(new Date());
        }else{
            moveRecord.setFdMoveDate(fdMoveDate);
        }

        //异动生效时间
//        Date fdEffectiveDate = (Date) modelData.get("fd_3af39d84da95c2");
//        moveRecord.setFdEffectiveDate(fdEffectiveDate);
        //异动类型
        moveRecord.setFdMoveType((String) modelData.get(parameters.get("fdMoveType")));
        moveRecord.setFdPersonInfo(personInfo);
        hrStaffMoveRecordService.add(moveRecord);
    }

    /**
     * 获取合同员工
     *
     * @param fieldValue
     * @param modelData
     * @return
     * @throws Exception
     */
    private HrStaffPersonInfo getPersonInfo(String fieldValue, Map<String, Object> modelData) throws Exception {
        Object fdApplicant = modelData.get(fieldValue);
        String fdApplicantId = BeanUtils.getProperty(fdApplicant, "id");
        String fdApplicantName = BeanUtils.getProperty(fdApplicant, "name");
        HrStaffPersonInfo personInfo = hrStaffPersonInfoService.findByOrgPersonId(fdApplicantId);
        if (personInfo == null) {
            throw new KmssException(new KmssMessage(ResourceUtil.getString(
                    "hrStaffAttendanceManageDetailed.robot.fdApplicant.nofind",
                    "hr-staff", null, fdApplicantName)));
        }
        return personInfo;
    }

    /**
     * 更新人事档案离职信息
     *
     * @param parameters
     * @param mainModel
     * @return
     * @throws Exception
     */
    private void updatePersonInfo(JSONObject parameters, IBaseModel mainModel, HrStaffPersonInfo personInfo) throws Exception {
        IExtendDataModel model = (IExtendDataModel) mainModel;
        Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
//        HashMap<String,String> firstDept = (HashMap<String,String>)modelData.get("fd_3b7766e5804952");
        String firstDeptId = (String) modelData.get("fd_3b7766e5804952");
        SysOrgElement sysOrgElement2 = (SysOrgElement) sysOrgElementService.findByPrimaryKey(firstDeptId);
//        SysOrgElement sysOrgElement3 = sysOrgElement2.getHbmThisLeader();
        //职类
        BeanUtils.setProperty(personInfo, "fdCategory", modelData.get(parameters.get("fdCategory")));
        BeanUtils.setProperty(personInfo, "fdHeadOfFirstLevelDepartment", sysOrgElement2);
        //办公所属区域
        //省
        Province province = provinceService.findByProvinceId((String) modelData.get(parameters.get("fdOfficeAreaProvinceId")));
        BeanUtils.setProperty(personInfo, "fdOfficeAreaProvinceId", province.getProvinceId());
        BeanUtils.setProperty(personInfo, "fdOfficeAreaProvinceName", province.getProvince());
        //市
        Cities cities = citiesService.findByCityId((String) modelData.get(parameters.get("fdOfficeAreaCityId")));
        BeanUtils.setProperty(personInfo, "fdOfficeAreaCityId", cities.getCityId());
        BeanUtils.setProperty(personInfo, "fdOfficeAreaCityName", cities.getCity());
        //区
        Areas areas = areasService.findByAreaId((String) modelData.get(parameters.get("fdOfficeAreaAreaId")));
        BeanUtils.setProperty(personInfo, "fdOfficeAreaAreaId", areas.getAreaId());
        BeanUtils.setProperty(personInfo, "fdOfficeAreaAreaName", areas.getArea());
        //办公详细地址
        BeanUtils.setProperty(personInfo, "fdOfficeLocation", modelData.get(parameters.get("fdOfficeLocation")));
        hrStaffPersonInfoService.update(personInfo);
    }

}
