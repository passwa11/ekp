package com.landray.kmss.sys.evaluation.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationReplyForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseMain;

public class SysEvaluationReply extends BaseModel implements ISysPraiseMain {
	/**
	 * 回复内容
	 */
	protected String docContent;

	/**
	 * @return 回复内容
	 */
	public String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            回复内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = (String) writeLazyField("docContent",
				this.docContent, docContent);
	}
	
	/**
	 * 回复时间
	 */
	protected Date fdReplyTime;

	/**
	 * @return 回复时间
	 */
	public Date getFdReplyTime() {
		return fdReplyTime;
	}

	/**
	 * @param fdReplyTime
	 *            回复时间
	 */
	public void setFdReplyTime(Date fdReplyTime) {
		this.fdReplyTime = fdReplyTime;
	}
	
	protected String fdModelId;

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}
	
	protected String fdModelName;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	
	/**
	 * 回复者
	 */
	protected SysOrgPerson fdReplyer;

	/**
	 * @return 回复者
	 */
	public SysOrgPerson getFdReplyer() {
		return fdReplyer;
	}

	/**
	 * @param fdReplyer
	 *            回复者
	 */
	public void setFdReplyer(SysOrgPerson fdReplyer) {
		this.fdReplyer = fdReplyer;
	}
	
	/**
	 * 父回复id
	 */
	protected String fdParentId;

	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}
	
	/**
	 * 父回复的回复者
	 */
	protected SysOrgPerson fdParentReplyer;

	/**
	 * @return 回复者
	 */
	public SysOrgPerson getFdParentReplyer() {
		return fdParentReplyer;
	}

	/**
	 * @param fdReplyer
	 *            回复者
	 */
	public void setFdParentReplyer(SysOrgPerson fdParentReplyer) {
		this.fdParentReplyer = fdParentReplyer;
	}
	
	/**
	 * 层级ID
	 */
	protected java.lang.String fdHierarchyId;

	/**
	 * @return 返回 层级ID
	 */
	public java.lang.String getFdHierarchyId() {
		return fdHierarchyId;
	}

	/**
	 * @param fdHierarchyId
	 *            要设置的 层级ID
	 */
	public void setFdHierarchyId(java.lang.String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}
	
	/**
	 * 点赞统计
	 */
	protected Integer docPraiseCount = Integer.valueOf(0);

	@Override
    public Integer getDocPraiseCount() {
		return docPraiseCount;
	}

	@Override
    public void setDocPraiseCount(Integer docPraiseCount) {
		this.docPraiseCount = docPraiseCount;
	}

	@Override
    public Class getFormClass() {
		return SysEvaluationReplyForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;
	
	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdReplyer.fdId", "fdReplyerId");
			toFormPropertyMap.put("fdReplyer.fdName", "fdReplyerName");
			toFormPropertyMap.put("fdParentReplyer.fdId", "fdParentReplyerId");
			toFormPropertyMap.put("fdParentReplyer.fdName", "fdParentReplyerName");
		}
		return toFormPropertyMap;
	}
}
