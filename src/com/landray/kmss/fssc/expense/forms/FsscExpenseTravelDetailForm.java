package com.landray.kmss.fssc.expense.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.fssc.expense.model.FsscExpenseTravelDetail;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;

/**
  * 行程明细
  */
public class FsscExpenseTravelDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdBeginDate;

    private String fdEndDate;

    private String fdTravelDays;

    private String fdStartPlace;

    private String fdArrivalId;

    private String fdArrivalPlace;

    private String fdVehicleId;

    private String fdVehicleName;

    private String fdBerthId;

    private String fdBerthName;

    private String fdPersonListIds;

    private String fdPersonListNames;

    private String fdSubject;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdBeginDate = null;
        fdEndDate = null;
        fdTravelDays = null;
        fdStartPlace = null;
        fdArrivalId = null;
        fdArrivalPlace = null;
        fdVehicleId = null;
        fdVehicleName = null;
        fdBerthId = null;
        fdBerthName = null;
        fdPersonListIds = null;
        fdPersonListNames = null;
        fdSubject = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseTravelDetail> getModelClass() {
        return FsscExpenseTravelDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdBeginDate", new FormConvertor_Common("fdBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdEndDate", new FormConvertor_Common("fdEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdVehicleId", new FormConvertor_IDToModel("fdVehicle", EopBasedataVehicle.class));
            toModelPropertyMap.put("fdBerthId", new FormConvertor_IDToModel("fdBerth", EopBasedataBerth.class));
            toModelPropertyMap.put("fdPersonListIds", new FormConvertor_IDsToModelList("fdPersonList", SysOrgPerson.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 起始(发生)日期
     */
    public String getFdBeginDate() {
        return this.fdBeginDate;
    }

    /**
     * 起始(发生)日期
     */
    public void setFdBeginDate(String fdBeginDate) {
        this.fdBeginDate = fdBeginDate;
    }

    /**
     * 结束日期
     */
    public String getFdEndDate() {
        return this.fdEndDate;
    }

    /**
     * 结束日期
     */
    public void setFdEndDate(String fdEndDate) {
        this.fdEndDate = fdEndDate;
    }

    /**
     * 天数
     */
    public String getFdTravelDays() {
        return this.fdTravelDays;
    }

    /**
     * 天数
     */
    public void setFdTravelDays(String fdTravelDays) {
        this.fdTravelDays = fdTravelDays;
    }

    /**
     * 出发城市
     */
    public String getFdStartPlace() {
        return this.fdStartPlace;
    }

    /**
     * 出发城市
     */
    public void setFdStartPlace(String fdStartPlace) {
        this.fdStartPlace = fdStartPlace;
    }

    /**
     * 到达城市类型
     */
    public String getFdArrivalId() {
        return this.fdArrivalId;
    }

    /**
     * 到达城市类型
     */
    public void setFdArrivalId(String fdArrivalId) {
        this.fdArrivalId = fdArrivalId;
    }

    /**
     * 到达城市
     */
    public String getFdArrivalPlace() {
        return this.fdArrivalPlace;
    }

    /**
     * 到达城市
     */
    public void setFdArrivalPlace(String fdArrivalPlace) {
        this.fdArrivalPlace = fdArrivalPlace;
    }

    /**
     * 交通工具
     */
    public String getFdVehicleId() {
        return this.fdVehicleId;
    }

    /**
     * 交通工具
     */
    public void setFdVehicleId(String fdVehicleId) {
        this.fdVehicleId = fdVehicleId;
    }

    /**
     * 交通工具
     */
    public String getFdVehicleName() {
        return this.fdVehicleName;
    }

    /**
     * 交通工具
     */
    public void setFdVehicleName(String fdVehicleName) {
        this.fdVehicleName = fdVehicleName;
    }

    /**
     * 舱位
     */
    public String getFdBerthId() {
        return this.fdBerthId;
    }

    /**
     * 舱位
     */
    public void setFdBerthId(String fdBerthId) {
        this.fdBerthId = fdBerthId;
    }

    /**
     * 舱位
     */
    public String getFdBerthName() {
        return this.fdBerthName;
    }

    /**
     * 舱位
     */
    public void setFdBerthName(String fdBerthName) {
        this.fdBerthName = fdBerthName;
    }

    /**
     * 人员
     */
    public String getFdPersonListIds() {
        return this.fdPersonListIds;
    }

    /**
     * 人员
     */
    public void setFdPersonListIds(String fdPersonListIds) {
        this.fdPersonListIds = fdPersonListIds;
    }

    /**
     * 人员
     */
    public String getFdPersonListNames() {
        return this.fdPersonListNames;
    }

    /**
     * 人员
     */
    public void setFdPersonListNames(String fdPersonListNames) {
        this.fdPersonListNames = fdPersonListNames;
    }

    /**
     * 行程
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 行程
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
    }
}
