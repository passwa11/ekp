package com.landray.kmss.sys.mportal.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;
import com.landray.kmss.sys.mportal.model.SysMportalCpageRelation;
import com.landray.kmss.sys.mportal.model.SysMportalComposite;

/**
  * 复合门户
  */
public class SysMportalCompositeForm extends ExtendAuthForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdTitle;

    private String fdLogo;

    private String fdOrder;

    private String fdLang;

    private String fdHeadType;

    private String fdNavLayout;

    private String fdHeadChangeEnabled;

    private String fdMd5;

    private String docCreateTime;

    private String docAlterTime;

    private String fdEnabled;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;
    
    private List pages = new AutoArrayList(SysMportalCpageRelationForm.class);

   	public List getPages() {
   		return pages;
   	}

   	public void setPages(List pages) {
   		this.pages = pages;
   	}

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdTitle = null;
        fdLogo = null;
        fdOrder = null;
        fdLang = null;
        fdHeadType = null;
        fdNavLayout = null;
        fdHeadChangeEnabled = null;
        fdMd5 = null;
        docCreateTime = null;
        docAlterTime = null;
        fdEnabled = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        pages.clear();
        super.reset(mapping, request);
    }

    @Override
    public Class<SysMportalComposite> getModelClass() {
        return SysMportalComposite.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
//            toModelPropertyMap.put("pages",
//					new FormConvertor_FormListToModelList("pages",
//							"sysMportalComposite"));
        }
        return toModelPropertyMap;
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(String fdOrder) {
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
    public String getFdHeadType() {
        return this.fdHeadType;
    }

    /**
     * 头部设置
     */
    public void setFdHeadType(String fdHeadType) {
        this.fdHeadType = fdHeadType;
    }

    /**
     * 导航布局
     */
    public String getFdNavLayout() {
        return this.fdNavLayout;
    }

    /**
     * 导航布局
     */
    public void setFdNavLayout(String fdNavLayout) {
        this.fdNavLayout = fdNavLayout;
    }

    /**
     * 头部门户切换
     */
    public String getFdHeadChangeEnabled() {
        return this.fdHeadChangeEnabled;
    }

    /**
     * 头部门户切换
     */
    public void setFdHeadChangeEnabled(String fdHeadChangeEnabled) {
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
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 是否启用
     */
    public String getFdEnabled() {
        return this.fdEnabled;
    }

    /**
     * 是否启用
     */
    public void setFdEnabled(String fdEnabled) {
        this.fdEnabled = fdEnabled;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 修改人
     */
    public String getDocAlterorId() {
        return this.docAlterorId;
    }

    /**
     * 修改人
     */
    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    /**
     * 修改人
     */
    public String getDocAlterorName() {
        return this.docAlterorName;
    }

    /**
     * 修改人
     */
    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }

    @Override
    public String getAuthReaderNoteFlag() {
        return "2";
    }
}
