package com.landray.kmss.sys.news.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.news.model.SysNewsOutSign;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 签订服务记录
  */
public class SysNewsOutSignForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private String fdMainId;

    private String fdStatus;

    private String fdUrl;

    private String fdExtmsg;

    private String docCreateTime;

	private String docCreatorId;

	private String docCreatorName;

	private String fdType;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMainId = null;
        fdStatus = null;
        fdUrl = null;
        fdExtmsg = null;
        docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		fdType = null;
        super.reset(mapping, request);
    }

	@Override
    public Class<SysNewsOutSign> getModelClass() {
		return SysNewsOutSign.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
            toModelPropertyMap.put("docCreateTime", new FormConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
    }

    /**
     * 主文档id
     */
	public String getFdMainId() {
		return this.fdMainId;
    }

    /**
     * 主文档id
     */
	public void setFdMainId(String fdMainId) {
		this.fdMainId = fdMainId;
    }

    /**
     * 状态码
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态码
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 回调地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 回调地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
    }

    /**
     * 扩展信息
     */
    public String getFdExtmsg() {
        return this.fdExtmsg;
    }

    /**
     * 扩展信息
     */
    public void setFdExtmsg(String fdExtmsg) {
        this.fdExtmsg = fdExtmsg;
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
	 * 签署人
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * 签署人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 签署人
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * 签署人
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 签订类型
	 */
	public String getFdType() {
		return this.fdType;
	}

	/**
	 * 签订类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
}
