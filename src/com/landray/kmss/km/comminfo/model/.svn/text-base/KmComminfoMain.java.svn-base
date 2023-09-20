package com.landray.kmss.km.comminfo.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.comminfo.forms.KmComminfoMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.bookmark.interfaces.ISysBookmarkModel;
import com.landray.kmss.sys.doc.model.SysDocBaseInfo;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.UserUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 叶中奇 常用资料主表
 */
public class KmComminfoMain extends SysDocBaseInfo implements IAttachment, // 附件机制
		ISysBookmarkModel, // 收藏机制
		InterceptFieldEnabled // 大字段加入延时加载
{
	// 文档模版
	protected KmComminfoCategory docCategory = null;

	/**
	 * @param kmComminfoCategory
	 *            要设置的 常用资料类别
	 */
	public KmComminfoCategory getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(KmComminfoCategory docCategory) {
		this.docCategory = docCategory;
	}

	/*
	 * 排序号
	 */
	protected java.lang.Long fdOrder;

	public KmComminfoMain() {
		super();
	}

	@Override
	public Class getFormClass() {
		return KmComminfoMainForm.class;
	}

	/**
	 * @return 返回 排序号
	 */
	public java.lang.Long getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            要设置的 排序号
	 */
	public void setFdOrder(java.lang.Long fdOrder) {
		this.fdOrder = fdOrder;
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
	private Integer docMarkCount;

	@Override
	public Integer getDocMarkCount() {
		return docMarkCount;
	}

	@Override
	public void setDocMarkCount(Integer count) {
		this.docMarkCount = count;
	}

	/*
	 * 修改人name
	 */
	private String docAlterorName = null;

	public String getDocAlterorName() {
		return docAlterorName;
	}

	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}
	
	@Override
	public String getDocStatus() {
		return "30";//默认发布状态
	}

	// ================ModelToForm转换开始=========================
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			// 创建者
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			// 所属类别
			toFormPropertyMap.put("docCategory.fdId", "docCategoryId");
			toFormPropertyMap.put("docCategory.fdName", "docCategoryName");
		}
		return toFormPropertyMap;
	}
	// ================ModelToForm转换结束============================

	@Override
	public Boolean getAuthReaderFlag() {
		if (ArrayUtil.isEmpty(getAuthReaders())) {
            return new Boolean(true);
        } else {
            return new Boolean(false);
        }
	}

	@Override
	protected void recalculateReaderField() {
		// 重新计算可阅读者
		if (authAllReaders == null) {
            authAllReaders = new ArrayList();
        } else {
            authAllReaders.clear();
        }

		if (getAuthReaderFlag().booleanValue()) {
			// everyone
			authAllReaders.add(UserUtil.getEveryoneUser());
			return;
		}

		authAllReaders.add(getDocCreator());

		List tmpList = getAuthOtherReaders();
		if (tmpList != null) {
			ArrayUtil.concatTwoList(tmpList, authAllReaders);
		}
		tmpList = getAuthReaders();
		if (tmpList != null) {
			ArrayUtil.concatTwoList(tmpList, authAllReaders);
		}
		ArrayUtil.concatTwoList(authAllEditors, authAllReaders);
	}
}
