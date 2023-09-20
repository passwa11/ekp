/**
 * 
 */
package com.landray.kmss.km.smissive.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.evaluation.forms.EvaluationForm;
import com.landray.kmss.sys.introduce.forms.IntroduceForm;
import com.landray.kmss.sys.news.forms.SysNewsPublishMainForm;
import com.landray.kmss.sys.readlog.forms.ReadLogForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.tag.forms.SysTagMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 提供机制的申明
 * 
 * @author 张鹏xn
 * 
 */
public abstract class KmSmissiveMainMechanismBaseForm extends ExtendAuthForm {

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 请注意需要在此处加上流程模板的初始化动作
		sysWfBusinessForm = new SysWfBusinessForm();
		// 阅读机制初始化
		readLogForm.reset(mapping, request);

		// evaluationForm = new EvaluationForm();
		// readLogForm = new ReadLogForm();
		// introduceForm = new IntroduceForm();
		// attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
		// sysRelationMainForm = new SysRelationMainForm();
		// SysTagMainForm = new SysTagMainForm();
		// SysNewsPublishMainForm = new SysNewsPublishMainForm();
		super.reset(mapping, request);
	}

	// ******************* 附件（开始）**********************//
	protected AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	// ******************* 附件（结束）**********************//

	// ********** 以下的代码为流程需要的代码，请直接拷贝 **********
	protected SysWfBusinessForm sysWfBusinessForm = new SysWfBusinessForm();

	public SysWfBusinessForm getSysWfBusinessForm() {
		return sysWfBusinessForm;
	}

	/**
	 * @return 返回 文档状态
	 */
	@Override
    public String getDocStatus() {
		return docStatus;
	}

	// ********** 以上的代码为流程需要的代码，请直接拷贝 **********

	// ********** 关联机制(开始)，请直接拷贝 **********

	protected SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();

	public SysRelationMainForm getSysRelationMainForm() {
		return sysRelationMainForm;
	}

	// ********** 关联机制(结束)，请直接拷贝 **********

	// *********************点评机制(开始)，请直接拷贝*********************************//

	protected EvaluationForm evaluationForm = new EvaluationForm();

	public EvaluationForm getEvaluationForm() {
		return evaluationForm;
	}

	// *********************点评机制(结束)，请直接拷贝*********************************//

	// *********************点评统计（开始）******************************//
	protected String docEvalCount;

	public String getDocEvalCount() {

		return docEvalCount;
	}

	public void setDocEvalCount(String count) {
		this.docEvalCount = count;
	}

	// *********************点评统计（结束）******************************//

	// ******************阅读机制(开始)，请直接拷贝***********************
	protected ReadLogForm readLogForm = new ReadLogForm();

	public ReadLogForm getReadLogForm() {
		return readLogForm;
	}

	// ********************阅读机制（结束），请直接拷贝*********************************//

	// ********************阅读统计（开始），请直接拷贝*********************************//
	protected String docReadCount = null;

	public String getDocReadCount() {
		return docReadCount;
	}

	public void setDocReadCount(String docReadCount) {
		this.docReadCount = docReadCount;
	}

	// *********************阅读统计（结束），请直接拷贝*********************************//

	// *********************收藏机制(开始)*********************************//
	protected String docMarkCount;

	public String getDocMarkCount() {
		return docMarkCount;
	}

	public void setDocMarkCount(String count) {
		this.docMarkCount = count;
	}

	// *********************收藏机制(结束)***************************//

	// *********************推荐机制(开始)*********************************//

	protected IntroduceForm introduceForm = new IntroduceForm();

	public IntroduceForm getIntroduceForm() {
		return introduceForm;
	}

	// *********************推荐机制(结束)*********************************//

	// *********************推荐统计(开始)*********************************//
	protected String docIntrCount;

	public String getDocIntrCount() {

		return docIntrCount;
	}

	public void setDocIntrCount(String count) {
		this.docIntrCount = count;

	}

	// *********************推荐统计(结束)*********************************//

	// ==============标签机制 开始==================
	protected SysTagMainForm sysTagMainForm = new SysTagMainForm();

	public SysTagMainForm getSysTagMainForm() {

		return sysTagMainForm;
	}

	// ==============标签机制 结束====================

	// *********************发布机制(开始)***************************//
	protected SysNewsPublishMainForm sysNewsPublishMainForm = new SysNewsPublishMainForm();

	public SysNewsPublishMainForm getSysNewsPublishMainForm() {
		return sysNewsPublishMainForm;
	}

	// *********************发布机制(结束)***************************//
}
