package com.landray.kmss.hr.staff.service.robot;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.staff.model.HrStaffMoveRecord;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffMoveRecordService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.text.SimpleDateFormat;
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
public class HrStaffEmploymentConfirmationApplicationRobotServiceImp extends AbstractRobotNodeServiceImp {
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
        this.hrStaffMoveRecordService = (IHrStaffMoveRecordService) SpringBeanUtil.getBean("hrStaffMoveRecordService");
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
//            addMoveRecord(personInfo, parameters, mainModel);
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
       
        //异动后岗位
        Map<String, String> fdOrgPost = (Map) modelData.get(parameters.get("fdOrgPost"));
        if (fdOrgPost != null && fdOrgPost.containsKey("id")) {
            List<SysOrgPost> posts = new ArrayList<>();
            posts.add((SysOrgPost) sysOrgPostService.findByPrimaryKey(fdOrgPost.get("id")));
            moveRecord.setFdAfterPosts(posts);
        }
       
        //异动时间
        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fdMoveDate = new Date();
        if(fdMoveDate == null){
            moveRecord.setFdMoveDate(new Date());
        }else{
            moveRecord.setFdMoveDate(fdMoveDate);
        }
        //异动类型
        moveRecord.setFdMoveType("1");
        moveRecord.setFdPersonInfo(personInfo);
        Object obj = personInfo.getFdPosts();
//        hrStaffMoveRecordService = (IHrStaffMoveRecordService) SpringBeanUtil.getBean("hrStaffMoveRecordService");
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
//        String firstDeptId = (String) modelData.get("fd_3b7766e5804952");
//        SysOrgElement sysOrgElement2 = (SysOrgElement) sysOrgElementService.findByPrimaryKey(firstDeptId);
//        SysOrgElement sysOrgElement3 = sysOrgElement2.getHbmThisLeader();
//        Map<String, String> fdOrgPost = (Map) modelData.get(parameters.get("fdOrgPost"));
//        if (fdOrgPost != null && fdOrgPost.containsKey("id")) {
//            List<SysOrgPost> posts = new ArrayList<>();
//            posts.add((SysOrgPost) sysOrgPostService.findByPrimaryKey(fdOrgPost.get("id")));
//            personInfo.setFdOrgPosts(posts);
//        }
        //职类
        BeanUtils.setProperty(personInfo, "fdPositiveTime", modelData.get(parameters.get("fdEmploymentConfirmation")));
//        BeanUtils.setProperty(personInfo, "fdHeadOfFirstLevelDepartment", sysOrgElement2);
        //办公所属区域
        //省
        String status = "";
        if(modelData.get(parameters.get("fdStatus")).equals("正式人员"))
        	status="official";        	
        if(modelData.get(parameters.get("fdStatus")).equals("试用人员"))
        	status="trial";        	
        if(modelData.get(parameters.get("fdStatus")).equals("在职人员"))
        	status="onpost";        	
        if(modelData.get(parameters.get("fdStatus")).equals("返聘人员"))
        	status="rehireAfterRetirement";    
        if(modelData.get(parameters.get("fdStatus")).equals("离职人员"))
        	status="leave";        	
        if(modelData.get(parameters.get("fdStatus")).equals("黑名单"))
        	status="blacklist";        	
        if(modelData.get(parameters.get("fdStatus")).equals("正式员工"))
        	status="official";        	
        BeanUtils.setProperty(personInfo, "fdStatus", status);
        //市
//        BeanUtils.setProperty(personInfo, "fdOfficeAreaCityId", modelData.get(parameters.get("fdOfficeAreaCityId")));
//        //区
//        BeanUtils.setProperty(personInfo, "fdOfficeAreaAreaId", modelData.get(parameters.get("fdOfficeAreaAreaId")));
//        //办公详细地址
//        BeanUtils.setProperty(personInfo, "fdOfficeLocation", modelData.get(parameters.get("fdOfficeLocation")));
        hrStaffPersonInfoService.update(personInfo);
    }

}
