package com.landray.kmss.fssc.expense.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;
import com.landray.kmss.fssc.expense.forms.FsscExpenseTravelDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 行程明细
  */
public class FsscExpenseTravelDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdBeginDate;

    private Date fdEndDate;

    private Integer fdTravelDays;

    private String fdStartPlace;

    private String fdArrivalId;

    private String fdArrivalPlace;

    private EopBasedataVehicle fdVehicle;

    private EopBasedataBerth fdBerth;
    
    private FsscExpenseMain docMain;

    private List<SysOrgPerson> fdPersonList = new ArrayList<SysOrgPerson>();

    private String fdSubject;

    @Override
    public Class<FsscExpenseTravelDetailForm> getFormClass() {
        return FsscExpenseTravelDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdBeginDate", new ModelConvertor_Common("fdBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdEndDate", new ModelConvertor_Common("fdEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdVehicle.fdName", "fdVehicleName");
            toFormPropertyMap.put("fdVehicle.fdId", "fdVehicleId");
            toFormPropertyMap.put("fdBerth.fdName", "fdBerthName");
            toFormPropertyMap.put("fdBerth.fdId", "fdBerthId");
            toFormPropertyMap.put("fdPersonList", new ModelConvertor_ModelListToString("fdPersonListIds:fdPersonListNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    public FsscExpenseMain getDocMain() {
		return docMain;
	}

	public void setDocMain(FsscExpenseMain docMain) {
		this.docMain = docMain;
	}

	/**
     * 起始(发生)日期
     */
    public Date getFdBeginDate() {
        return this.fdBeginDate;
    }

    /**
     * 起始(发生)日期
     */
    public void setFdBeginDate(Date fdBeginDate) {
        this.fdBeginDate = fdBeginDate;
    }

    /**
     * 结束日期
     */
    public Date getFdEndDate() {
        return this.fdEndDate;
    }

    /**
     * 结束日期
     */
    public void setFdEndDate(Date fdEndDate) {
        this.fdEndDate = fdEndDate;
    }

    /**
     * 天数
     */
    public Integer getFdTravelDays() {
        return this.fdTravelDays;
    }

    /**
     * 天数
     */
    public void setFdTravelDays(Integer fdTravelDays) {
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
    public EopBasedataVehicle getFdVehicle() {
        return this.fdVehicle;
    }

    /**
     * 交通工具
     */
    public void setFdVehicle(EopBasedataVehicle fdVehicle) {
        this.fdVehicle = fdVehicle;
    }

    /**
     * 舱位
     */
    public EopBasedataBerth getFdBerth() {
        return this.fdBerth;
    }

    /**
     * 舱位
     */
    public void setFdBerth(EopBasedataBerth fdBerth) {
        this.fdBerth = fdBerth;
    }

    /**
     * 人员
     */
    public List<SysOrgPerson> getFdPersonList() {
        return this.fdPersonList;
    }

    /**
     * 人员
     */
    public void setFdPersonList(List<SysOrgPerson> fdPersonList) {
        this.fdPersonList = fdPersonList;
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
