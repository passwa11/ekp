package com.landray.kmss.km.comminfo.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.comminfo.model.KmComminfoCategory;
import com.landray.kmss.km.comminfo.model.KmComminfoMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.bookmark.interfaces.ISysBookmarkForm;
import com.landray.kmss.sys.doc.forms.SysDocBaseInfoForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 徐乃瑞
 */
public class KmComminfoMainForm extends SysDocBaseInfoForm implements
		IAttachmentForm, // 附件机制
		ISysBookmarkForm // 收藏机制

{
	/*
	 * 提交者ID
	 */
	private String docCreatorId = null;

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/*
	 * 分类模版ID,要设置的 所属类别ID
	 */
	private String docCategoryId = null;

	public String getDocCategoryId() {
		return docCategoryId;
	}

	public void setDocCategoryId(String docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	/*
	 * 分类模版名称
	 */
	private String docCategoryName = null;

	public String getDocCategoryName() {
		return docCategoryName;
	}

	public void setDocCategoryName(String docCategoryName) {
		this.docCategoryName = docCategoryName;
	}

	/*
	 * 排序号
	 */
	private String fdOrder = null;

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.docCategoryId = null;
		this.docCategoryName = null;
		autoHashMap.clear();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmComminfoMain.class;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	/*
	 * 被收藏次数
	 */
	private String docMarkCount;

	@Override
    public String getDocMarkCount() {
		return docMarkCount;
	}

	@Override
    public void setDocMarkCount(String count) {
		this.docMarkCount = count;
	}

	// ================FormToModel转换开始===================
	private static FormToModelPropertyMap toModelPropertyMap = null;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			// 创建时间
			toModelPropertyMap.addNoConvertProperty("docCreateTime");

			// 所属类别
			toModelPropertyMap.put("docCategoryId",
					new FormConvertor_IDToModel("docCategory",
							KmComminfoCategory.class));
			// 修改者
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel(
					"docAlteror", SysOrgElement.class));
			// 创建者
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));

		}
		return toModelPropertyMap;
	}
	// ================FormToModel转换结束=======================

}
