package com.landray.kmss.sys.mportal.model;

import java.util.ArrayList;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import java.util.Date;
import java.util.List;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.mportal.forms.SysMportalCompositeForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;

/**
  * 复合门户
  */
public class SysMportalComposite extends ExtendAuthModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdTitle;

    private String fdLogo;

    private Integer fdOrder;

    private String fdLang;

    private Integer fdHeadType;

    private Integer fdNavLayout;

    private Boolean fdHeadChangeEnabled;

    private String fdMd5;

    private Date docAlterTime;

    private Boolean fdEnabled;

    private SysOrgPerson docAlteror;

    private List<SysMportalCpageRelation> pages = new ArrayList<SysMportalCpageRelation>();

    @Override
    public Class<SysMportalCompositeForm> getFormClass() {
        return SysMportalCompositeForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("pages",
					new ModelConvertor_ModelListToFormList("pages"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
        if (!getAuthReaderFlag()) {
        }
    }
    
	/**
	 * @param fdName
	 *            名称
	 */
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}
	
	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

    /**
     * 标题
     */
    public String getFdTitle() {
        return this.fdTitle;
    }

    /**
     * 标题
     */
    public void setFdTitle(String fdTitle) {
        this.fdTitle = fdTitle;
    }

    /**
     * logo
     */
    public String getFdLogo() {
        return this.fdLogo;
    }

    /**
     * logo
     */
    public void setFdLogo(String fdLogo) {
        this.fdLogo = fdLogo;
    }

    /**
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 多语言
     */
    public String getFdLang() {
        return this.fdLang;
    }

    /**
     * 多语言
     */
    public void setFdLang(String fdLang) {
        this.fdLang = fdLang;
    }

    /**
     * 头部设置
     */
    public Integer getFdHeadType() {
        return this.fdHeadType;
    }

    /**
     * 头部设置
     */
    public void setFdHeadType(Integer fdHeadType) {
        this.fdHeadType = fdHeadType;
    }

    /**
     * 导航布局
     */
    public Integer getFdNavLayout() {
        return this.fdNavLayout;
    }

    /**
     * 导航布局
     */
    public void setFdNavLayout(Integer fdNavLayout) {
        this.fdNavLayout = fdNavLayout;
    }

    /**
     * 头部门户切换
     */
    public Boolean getFdHeadChangeEnabled() {
        return this.fdHeadChangeEnabled;
    }

    /**
     * 头部门户切换
     */
    public void setFdHeadChangeEnabled(Boolean fdHeadChangeEnabled) {
        this.fdHeadChangeEnabled = fdHeadChangeEnabled;
    }

    /**
     * md5
     */
    public String getFdMd5() {
        return this.fdMd5;
    }

    /**
     * md5
     */
    public void setFdMd5(String fdMd5) {
        this.fdMd5 = fdMd5;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 是否启用
     */
    public Boolean getFdEnabled() {
        return this.fdEnabled;
    }

    /**
     * 是否启用
     */
    public void setFdEnabled(Boolean fdEnabled) {
        this.fdEnabled = fdEnabled;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

    /**
     * 复合门户列表页
     */
    public List<SysMportalCpageRelation> getPages() {
        return this.pages;
    }

    /**
     * 复合门户列表页
     */
    public void setPages(List<SysMportalCpageRelation> pages) {
        this.pages = pages;
    }

    @Override
    public String getDocSubject() {
        return getFdName();
    }
}
