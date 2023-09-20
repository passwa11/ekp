package com.landray.kmss.hr.staff.service.robot;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffPersonFamily;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonFamilyService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 更新家庭信息
 */
public class HrStaffFamilyRobotServiceImp extends AbstractRobotNodeServiceImp {
    private Logger logger = LoggerFactory.getLogger(HrStaffFamilyRobotServiceImp.class);

    //家庭信息
    private IHrStaffPersonFamilyService hrStaffPersonFamilyService;

    public void setHrStaffPersonFamilyService(IHrStaffPersonFamilyService hrStaffPersonFamilyService) {
        this.hrStaffPersonFamilyService = hrStaffPersonFamilyService;
    }

    // 员工信息
    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
        this.hrStaffPersonInfoService = hrStaffPersonInfoService;
    }

    @Override
    public void execute(TaskExecutionContext context) throws Exception {
        JSONObject json = (JSONObject) JSONValue.parse(getConfigContent(context));
        saveMainModel(context, json);
    }

    private void saveMainModel(TaskExecutionContext context, JSONObject json)
            throws Exception {
        JSONObject parameters = (JSONObject) json.get("params");
        IBaseModel mainModel = context.getMainModel();
        if (mainModel instanceof IExtendDataModel) {
            // 校验家庭员工是否存在于人事档案中，并保存家庭信息
            checkSavePersonInfo(parameters, mainModel);
        }
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
        HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrStaffPersonInfoService
                .findByPrimaryKey(fdApplicantId, null, true);
        if (personInfo == null) {
            throw new KmssException(new KmssMessage(ResourceUtil.getString(
                    "hrStaffAttendanceManageDetailed.robot.fdApplicant.nofind",
                    "hr-staff", null, fdApplicantName)));
        }
        return personInfo;
    }

    /**
     * 校验员工
     * @param parameters
     * @param mainModel
     * @return
     * @throws Exception
     */
    private HrStaffPersonInfo checkSavePersonInfo(JSONObject parameters, IBaseModel mainModel) throws Exception {
        //获取家庭明细表的姓名字段
        String fieldValue = (String) parameters.get("fdName");
        IExtendDataModel model = (IExtendDataModel) mainModel;
        Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();

        //获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段
        String fdApplicant = (String) modelData.get(parameters.get("fdApplicant"));
        logger.info("身份证号为：{}",fdApplicant);
        HrStaffPersonInfo personInfo = HrStaffPersonUtil.getPersonInfoByIdCard(fdApplicant);
        if(personInfo == null){
            return null;
        }

        //获取两段key值
        String[] fieldValues = fieldValue.split("\\.");
        if (fieldValues.length == 2) {
            //获取家庭明细list
            Object familyList = modelData.get(fieldValues[0]);
            //页面可能增加多行不同的人员，转list遍历处理
            List<Map<String, Object>> fileList = (List) familyList;

            if (null != fileList && fileList.size() > 0) {
                for (Map<String, Object> map : fileList) {
                    //校验人员是否存在于人事档案中
                    //校验成功，保存家庭信息
                    HrStaffPersonFamily detailed = new HrStaffPersonFamily();
                    detailed.setFdPersonInfo(personInfo);
                    // 家庭关系
                    BeanUtils.setProperty(detailed, "fdRelated", map
                            .get(parameters.get("fdRelated").toString().replace(fieldValues[0] + ".", "")));
                    // 姓名
                    BeanUtils.setProperty(detailed, "fdName", map
                            .get(parameters.get("fdName").toString().replace(fieldValues[0] + ".", "")));
                    // 任职单位
                    BeanUtils.setProperty(detailed, "fdCompany", map
                            .get(parameters.get("fdCompany").toString().replace(fieldValues[0] + ".", "")));
                    // 职业
                    BeanUtils.setProperty(detailed, "fdOccupation", map
                            .get(parameters.get("fdOccupation").toString().replace(fieldValues[0] + ".", "")));
                    // 联系电话
                    BeanUtils.setProperty(detailed, "fdConnect", map
                            .get(parameters.get("fdConnect").toString().replace(fieldValues[0] + ".", "")));
                    // 家庭住址
                    BeanUtils.setProperty(detailed, "fdMemo", map
                            .get(parameters.get("fdMemo").toString().replace(fieldValues[0] + ".", "")));

                    // 流程URL
                    detailed.setFdRelatedProcess(getUrl(model));
                    hrStaffPersonFamilyService.add(detailed);
                }
            }
        }
        return null;
    }

    /**
     * 获取流程URL
     *
     * @param mainModel
     * @return
     */
    private String getUrl(IBaseModel mainModel) throws Exception {
        String modelName = mainModel.getClass().getName();
        if (modelName.contains("$$")) {
            modelName = modelName.substring(0, modelName.indexOf("$$"));
        }
        SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
        String url = dictModel.getUrl();
        if (url != null) {
            url = url.replace("${fdId}", mainModel.getFdId());
        }
        return url;
    }
}
