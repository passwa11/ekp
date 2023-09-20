package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.model.ThirdDingCspace;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉盘文件日志
  */
public class ThirdDingCspaceForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdMediaId;

    private String fdAttId;

    private String docCreateTime;

    private String docAlterTime;

    private String fdName;

    private String fdStatus;

    private String fdCreaterId;

    private String fdCreaterName;

	private String fdFileId;

    private String fdDingFileId;

    /**
     *  0:表示上传到钉盘，1表示从钉盘下载
     */
    private String fdType;

    private String fdErrorMsg;

    public String getFdErrorMsg() {
        return fdErrorMsg;
    }

    public void setFdErrorMsg(String fdErrorMsg) {
        this.fdErrorMsg = fdErrorMsg;
    }

    public String getFdDingFileId() {
        return fdDingFileId;
    }

    public void setFdDingFileId(String fdDingFileId) {
        this.fdDingFileId = fdDingFileId;
    }

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

	public String getFdFileId() {
		return fdFileId;
	}

	public void setFdFileId(String fdFileId) {
		this.fdFileId = fdFileId;
	}

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdMediaId = null;
        fdAttId = null;
        docCreateTime = null;
        docAlterTime = null;
        fdName = null;
        fdStatus = null;
        fdCreaterId = null;
        fdCreaterName = null;
		fdFileId = null;
        fdDingFileId = null;
        fdType=null;
        fdErrorMsg=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCspace> getModelClass() {
        return ThirdDingCspace.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCreaterId", new FormConvertor_IDToModel("fdCreater", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * mediaId
     */
    public String getFdMediaId() {
        return this.fdMediaId;
    }

    /**
     * mediaId
     */
    public void setFdMediaId(String fdMediaId) {
        this.fdMediaId = fdMediaId;
    }

    /**
     * 附件
     */
    public String getFdAttId() {
        return this.fdAttId;
    }

    /**
     * 附件
     */
    public void setFdAttId(String fdAttId) {
        this.fdAttId = fdAttId;
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
     * 附件状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 附件状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 操作者
     */
    public String getFdCreaterId() {
        return this.fdCreaterId;
    }

    /**
     * 操作者
     */
    public void setFdCreaterId(String fdCreaterId) {
        this.fdCreaterId = fdCreaterId;
    }

    /**
     * 操作者
     */
    public String getFdCreaterName() {
        return this.fdCreaterName;
    }

    /**
     * 操作者
     */
    public void setFdCreaterName(String fdCreaterName) {
        this.fdCreaterName = fdCreaterName;
    }
}
