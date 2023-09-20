/**
 * 
 */
package com.landray.kmss.km.smissive.model;

import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.news.model.SysNewsPublishMain;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.sys.tag.model.SysTagMain;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessModel;
import com.landray.kmss.util.AutoHashMap;

/**
 * 提供常用的机制
 * 
 * @author 张鹏xn
 * 
 */
public abstract class KmSmissiveMainMechanismBase extends ExtendAuthModel {
	// ********** 以下的代码为流程需要的代码，请直接拷贝 **********
	protected SysWfBusinessModel sysWfBusinessModel = new SysWfBusinessModel();

	public SysWfBusinessModel getSysWfBusinessModel() {
		return sysWfBusinessModel;
	}

	/*
	 * @return 返回 文档状态
	 */
	@Override
    public String getDocStatus() {
		return docStatus;
	}

	// ********** 以上的代码为流程需要的代码，请直接拷贝 **********

	// =====附件机制(开始)=====
	protected AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	// =====附件机制(结束)=====

	// ********** 关联机制(开始) **********

	/*
	 * 关联域模型信息
	 */
	protected SysRelationMain sysRelationMain = null;

	public SysRelationMain getSysRelationMain() {
		return sysRelationMain;
	}

	public void setSysRelationMain(SysRelationMain sysRelationMain) {
		this.sysRelationMain = sysRelationMain;
	}

	protected String relationSeparate = null;

	/**
	 * 获取关联分表字段
	 * 
	 * @return
	 */
	public String getRelationSeparate() {
		return relationSeparate;
	}

	/**
	 * 设置关联分表字段
	 */
	public void setRelationSeparate(String relationSeparate) {
		this.relationSeparate = relationSeparate;
	}

	// ********** 关联机制(结束) **********

	// **************点评机制(开始)****************************//
	protected String evaluationSeparate;

	public String getEvaluationSeparate() {
		return evaluationSeparate;
	}

	public void setEvaluationSeparate(String evaluationSeparate) {
		this.evaluationSeparate = evaluationSeparate;
	}

	// *******************点评机制(结束)************************//

	// *******************点评统计(开始)**********************//
	protected Integer docEvalCount = Integer.valueOf(0);

	public Integer getDocEvalCount() {

		return docEvalCount;
	}

	public void setDocEvalCount(Integer count) {
		this.docEvalCount = count;

	}

	// *********************点评统计(结束)***********************//

	// **************阅读机制（开始）****************************//

	// ===== 增加分表字段（开始） =====

	protected String readLogSSeparate = null;

	/**
	 * 获取阅读分表字段
	 * 
	 * @return
	 */
	public String getReadLogSeparate() {
		return readLogSSeparate;
	}

	/**
	 * 设置阅读分表字段
	 */
	public void setReadLogSSeparate(String readLogSSeparate) {
		this.readLogSSeparate = readLogSSeparate;
	}

	// ************阅读机制(结束)*******************************//
	// ************阅读统计（开始）****************************//

	protected Long docReadCount = new Long(0);

	public Long getDocReadCount() {
		return docReadCount;
	}

	public void setDocReadCount(Long docReadCount) {
		this.docReadCount = docReadCount;
	}

	// ***********阅读统计(结束)*******************************//

	// *********************收藏机制(开始)***********************//

	protected Integer docMarkCount = Integer.valueOf(0);

	public Integer getDocMarkCount() {
		return docMarkCount;
	}

	public void setDocMarkCount(Integer count) {
		this.docMarkCount = count;
	}

	// *********************收藏机制(结束)***********************//

	// *********************推荐机制(开始)**********************//
	protected Boolean docIsIntroduced = false;
	protected String introduceSeparate = null;

	public Boolean getDocIsIntroduced() {

		return docIsIntroduced;
	}

	public String getIntroduceSeparate() {

		return introduceSeparate;
	}

	public void setDocIsIntroduced(Boolean docIsIntroduced) {
		this.docIsIntroduced = docIsIntroduced;

	}

	public void setIntroduceSeparate(String introduceSeparate) {
		this.introduceSeparate = introduceSeparate;

	}

	// *********************推荐机制(结束)***********************//

	// *********************推荐统计(开始)***********************//
	protected Integer docIntrCount = Integer.valueOf(0);

	public Integer getDocIntrCount() {

		return docIntrCount;
	}

	public void setDocIntrCount(Integer count) {
		this.docIntrCount = count;
	}

	// *********************推荐统计(结束)***********************//

	// ===========标签机制开始=========
	protected SysTagMain sysTagMain = null;

	public SysTagMain getSysTagMain() {

		return sysTagMain;
	}

	public void setSysTagMain(SysTagMain sysTagMain) {
		this.sysTagMain = sysTagMain;
	}

	// ==============标签机制结束========

	// *********************发布机制(开始)***********************//
	protected SysNewsPublishMain sysNewsPublishMain = null;

	public SysNewsPublishMain getSysNewsPublishMain() {
		return sysNewsPublishMain;
	}

	public void setSysNewsPublishMain(SysNewsPublishMain sysNewsPublishMain) {
		this.sysNewsPublishMain = sysNewsPublishMain;
	}

	// *********************发布机制(结束)***********************//
}
