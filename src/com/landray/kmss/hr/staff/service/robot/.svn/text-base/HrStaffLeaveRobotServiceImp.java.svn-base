package com.landray.kmss.hr.staff.service.robot;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.WordUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 离职信息机器人
 * 更新离职信息到人事档案
 * @author liuyang
 * @date  2022-09-03
 */
public class HrStaffLeaveRobotServiceImp extends AbstractRobotNodeServiceImp {
    private Logger logger = LoggerFactory.getLogger(HrStaffLeaveRobotServiceImp.class);

    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
        this.hrStaffPersonInfoService = hrStaffPersonInfoService;
    }

    private ISysAttMainCoreInnerService sysAttMainService;

    public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
        this.sysAttMainService = sysAttMainService;
    }

    public IHrStaffEmolumentWelfareService hrStaffEmolumentWelfareService;

    public void setHrStaffEmolumentWelfareService(IHrStaffEmolumentWelfareService hrStaffEmolumentWelfareService){
        this.hrStaffEmolumentWelfareService = hrStaffEmolumentWelfareService;
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
            // 校验家庭员工是否存在于人事档案中，并保存家庭信息
            HrStaffPersonInfo info = updatePersonInfo(parameters, mainModel);
            //生成word map参数
            Map<String,String> map = getPersonMap(info);
            //生成离职证明
            generateLeaveCertificate(map,info);
        }
    }

    private Map<String, String> getPersonMap(HrStaffPersonInfo info) throws Exception {
        if (null == info) {
            return null;
        }

        Map<String, String> map = new HashMap(9);
        map.put("fdName", info.getFdName());
        map.put("fdCardId", StringUtil.isNotNull(info.getFdIdCard()) ? info.getFdIdCard() : "");
        map.put("fdStartTime", DateUtil.convertDateToString(info.getFdEntryTime(), "yyyy年MM月dd日"));
        map.put("fdEndTime", DateUtil.convertDateToString(new Date(), "yyyy年MM月dd日"));
        map.put("fdJob", info.getFdStaffingLevel() != null ? info.getFdStaffingLevel().getFdName() : "");
        map.put("fdLeaveTime", DateUtil.convertDateToString(info.getFdResignationDate(), "yyyy年MM月dd日"));
        map.put("fdCompany", StringUtil.isNotNull(info.getFdAffiliatedCompany()) ? info.getFdAffiliatedCompany() : "");

        HrStaffEmolumentWelfare welfare = findEmolumentWelfareByPersonInfo(info);
        if (null != welfare) {
            map.put("fdSocialNumber", StringUtil.isNotNull(welfare.getFdSocialSecurityNumber()) ? welfare.getFdSocialSecurityNumber() : "");
            map.put("fdSurplusAccount", StringUtil.isNotNull(welfare.getFdSurplusAccount()) ? welfare.getFdSurplusAccount() : "");
        }
        return map;
    }

    /**
     * 获取合同员工
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
     * @param parameters
     * @param mainModel
     * @return
     * @throws Exception
     */
    private HrStaffPersonInfo updatePersonInfo(JSONObject parameters, IBaseModel mainModel) throws Exception {
        IExtendDataModel model = (IExtendDataModel) mainModel;
        Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
        String fdApplicant = (String)parameters.get("fdApplicant");
        HrStaffPersonInfo personInfo = getPersonInfo(fdApplicant,modelData);
        //离职日期
        BeanUtils.setProperty(personInfo, "fdResignationDate", modelData.get(parameters.get("fdResignationDate")));
        //离职原因
        BeanUtils.setProperty(personInfo, "fdReasonForResignation", modelData.get(parameters.get("fdReasonForResignation")));
        //离职类型
        BeanUtils.setProperty(personInfo, "fdResignationType", modelData.get(parameters.get("fdResignationType")));
        //人员类别
        String fdStaffType = (String)modelData.get(parameters.get("fdStaffType"));
        if(StringUtil.isNotNull(fdStaffType)){
            BeanUtils.setProperty(personInfo, "fdStaffType",  fdStaffType);
        }
        //人员状态
        BeanUtils.setProperty(personInfo, "fdStatus",  modelData.get(parameters.get("fdStatus")));
        hrStaffPersonInfoService.update(personInfo);
        return personInfo;
    }

    /**
     *
     * @param map
     * @param personInfo
     * @throws Exception
     */
    private void generateLeaveCertificate(Map map, HrStaffPersonInfo personInfo) throws Exception {
        InputStream in = null;
        try {
            String filePath = WordUtil.class.getResource("").getPath() + "template/";
            String fdStaffType = personInfo.getFdStaffType();
            if(StringUtil.isNotNull(fdStaffType) && fdStaffType.indexOf("实习") > -1){
                filePath += "leave_trial.doc";
            }else{
                filePath += "leave.doc";
            }
            logger.info("离职证明路径:" + filePath);
            File file = new File(filePath);
            if(!file.exists()){
                throw new Exception("离职模板不存在");
            }
            in = new FileInputStream(file);
            byte[] b = WordUtil.generateDoc(map, in);
            String fileName = personInfo.getFdName() + "离职证明.doc";
            sysAttMainService.addAttachment(personInfo, "hrStaffPerson", b, fileName, "byte");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * 根据人员获取薪酬福利
     */
    private HrStaffEmolumentWelfare findEmolumentWelfareByPersonInfo(HrStaffPersonInfo hrStaffPersonInfo) throws Exception {
        if (null == hrStaffPersonInfo) {
            return null;
        }
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdPersonInfo.fdId=:fdId");
        info.setParameter("fdId", hrStaffPersonInfo.getFdId());
        List<HrStaffEmolumentWelfare> hrStaffEmolumentWelfares = hrStaffEmolumentWelfareService.findList(info);
        return !ArrayUtil.isEmpty(hrStaffEmolumentWelfares) ? hrStaffEmolumentWelfares.get(0) : null;
    }
}
