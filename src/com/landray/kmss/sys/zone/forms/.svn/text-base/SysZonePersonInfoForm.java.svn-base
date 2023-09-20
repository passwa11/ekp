/**
 * 
 */
package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.evaluation.forms.EvaluationForm;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationCountForm;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationForm;
import com.landray.kmss.sys.fans.interfaces.ISysFansMainForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.readlog.forms.ReadLogForm;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogForm;
import com.landray.kmss.sys.tag.forms.SysTagMainForm;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainForm;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.plugin.SysZonePersonInfoBean;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class SysZonePersonInfoForm extends ExtendForm implements
		IAttachmentForm, ISysTagMainForm,ISysEvaluationForm,
		ISysEvaluationCountForm,ISysFansMainForm, ISysReadLogForm{
	private String fdSex;// 性别
	private String fdCompanyPhone;// 公司电话
	private String fdEnglishName;// 英文名
	private String fdSignature;// 个性签名
	private String mobilPhone;// 手机号码
	private String email;// 邮箱
	private String fdDefaultLang;// 默认语言

	private String personId;// 组织架构ID
	protected String personName;// 组织架构
	protected String dep;// 隶属部门
	protected String post;// 所属岗位
	protected String leader;// 直属上司

	// ==============员工黄页==开始==========================
	/**
	 * 关注数
	 */
	protected String fdAttentionNum = null;

	/**
	 * @return 关注数
	 */
	public String getFdAttentionNum() {
		return fdAttentionNum;
	}

	/**
	 * @param fdAttentionNum
	 *            关注数
	 */
	public void setFdAttentionNum(String fdAttentionNum) {
		this.fdAttentionNum = fdAttentionNum;
	}

	/**
	 * 粉丝数
	 */
	protected String fdFansNum = null;

	@Override
    public void setFdFansNum(String fdFansNum) {
		this.fdFansNum = fdFansNum;
	}

	/**
	 * @return 粉丝数
	 */
	@Override
    public String getFdFansNum() {
		return fdFansNum;
	}

	// 初始化，防止查看时拋异常
	private SysTagMainForm sysTagMainForm = new SysTagMainForm();

	@Override
	public SysTagMainForm getSysTagMainForm() {
		return sysTagMainForm;
	}

	// ==============员工黄页==结束==========================

	public String getFdSex() {
		return fdSex;
	}

	public void setFdSex(String fdSex) {
		this.fdSex = fdSex;
	}

	public String getFdCompanyPhone() {
		return fdCompanyPhone;
	}

	public void setFdCompanyPhone(String fdCompanyPhone) {
		this.fdCompanyPhone = fdCompanyPhone;
	}

	public String getFdEnglishName() {
		return fdEnglishName;
	}

	public void setFdEnglishName(String fdEnglishName) {
		this.fdEnglishName = fdEnglishName;
	}

	public String getFdSignature() {
		return fdSignature;
	}

	public void setFdSignature(String fdSignature) {
		this.fdSignature = fdSignature;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getMobilPhone() {
		return mobilPhone;
	}

	public void setMobilPhone(String mobilPhone) {
		this.mobilPhone = mobilPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFdDefaultLang() {
		return fdDefaultLang;
	}

	public void setFdDefaultLang(String fdDefaultLang) {
		this.fdDefaultLang = fdDefaultLang;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	// ================员工黄页===开始=====================
	public void setAutoHashMap(AutoHashMap autoHashMap) {
		this.autoHashMap = autoHashMap;
	}

	// ================员工黄页===结束====================
	/*
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdSex = null;// 性别
		fdCompanyPhone = null;// 公司电话
		fdEnglishName = null;// 英文名
		fdSignature = null;// 个性签名

		personId = null;// 组织架构ID
		personName = null;// 组织架构
		dep = null;// 隶属部门
		post = null;// 所属岗位
		leader = null;// 直属上司
		mobilPhone = null;// 手机号码
		email = null;// 邮箱
		sysTagMainForm = new SysTagMainForm();
		
		isContactPrivate = null;
		isDepInfoPrivate = null;
		isRelationshipPrivate = null;
		isWorkmatePrivate = null;
		
		authAttDownloadIds = null;
		authAttDownloadNames = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysZonePersonInfo.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdId", new FormConvertor_IDToModel(
					"person", SysOrgPerson.class));
			
			toModelPropertyMap.put("authAttDownloadIds",
					new FormConvertor_IDsToModelList("authAttDownloads",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
	
	public static String getLangDisplayName(HttpServletRequest request,
			String value) {
		if (StringUtil.isNotNull(value)) {
			String langStr = ResourceUtil
					.getKmssConfigString("kmss.lang.support");
			if (StringUtil.isNotNull(langStr)) {
				String v = "|" + value;
				String[] langArr = langStr.split(";");
				for (int i = 0; i < langArr.length; i++) {
					if (langArr[i].endsWith(v)) {
						return langArr[i].substring(0,
								langArr[i].length() - v.length());
					}
				}
			}
		}
		return ResourceUtil.getString("message.defaultLang",
				request.getLocale());
	}
	
	public static String getLangSelectHtml(HttpServletRequest request,
			String propertyName, String value) {

		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\"").append(propertyName).append("\">");
		sb.append("<option value=\"\">").append(
				ResourceUtil.getString("message.defaultLang", request
						.getLocale()))
				.append("</option>");
		String langStr = ResourceUtil.getKmssConfigString("kmss.lang.support");
		if (StringUtil.isNotNull(langStr)) {
			String[] langArr = langStr.trim().split(";");
			for (int i = 0; i < langArr.length; i++) {
				String[] langInfo = langArr[i].split("\\|");
				sb.append("<option value=\"").append(langInfo[1]).append("\"");
				if (langInfo[1].equals(value)) {
					sb.append(" selected");
				}
				sb.append(">").append(langInfo[0]).append("</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	public boolean isEkpZone() {
		return PersonInfoServiceGetter.getPersonInfoService() instanceof SysZonePersonInfoBean;
	}

	protected EvaluationForm evaluationForm = new EvaluationForm();

	@Override
    public EvaluationForm getEvaluationForm() {
		return evaluationForm;
	}

	public void setEvaluationForm(EvaluationForm evaluationForm) {
		this.evaluationForm = evaluationForm;
	}

	/**
	 * 点评统计
	 */
	protected String docEvalCount = null;

	/**
	 * @return 点评统计
	 */
	@Override
    public String getDocEvalCount() {
		return docEvalCount;
	}

	/**
	 * @param docEvalCount
	 *            点评统计
	 */
	@Override
    public void setDocEvalCount(String docEvalCount) {
		this.docEvalCount = docEvalCount;
	}
	//阅读机制要用
	public String getDocStatus() {
		return SysDocConstant.DOC_STATUS_PUBLISH;
	}
	
	/*
	* 文档阅读次数
	*/
	private String docReadCount = null;

	public String getDocReadCount() {
			return docReadCount;
		}

	public void setDocReadCount(String docReadCount) {
		this.docReadCount = docReadCount;
	}

	/*
	 * 阅读机制
	 */
	private ReadLogForm readLogForm = new ReadLogForm();

	@Override
    public ReadLogForm getReadLogForm() {
		return readLogForm;
	}
	
	//==隐私设置==
	String isContactPrivate = null;
	
	String isDepInfoPrivate = null;
	
	String isRelationshipPrivate = null;
	
	String isWorkmatePrivate = null;
	
	public String getIsContactPrivate() {
		return isContactPrivate;
	}

	public void setIsContactPrivate(String isContactPrivate) {
		this.isContactPrivate = isContactPrivate;
	}

	public String getisDepInfoPrivate() {
		return isDepInfoPrivate;
	}

	public void setisDepInfoPrivate(String isDepInfoPrivate) {
		this.isDepInfoPrivate = isDepInfoPrivate;
	}

	public String getIsRelationshipPrivate() {
		return isRelationshipPrivate;
	}

	public void setIsRelationshipPrivate(String isRelationshipPrivate) {
		this.isRelationshipPrivate = isRelationshipPrivate;
	}

	public String getIsWorkmatePrivate() {
		return isWorkmatePrivate;
	}

	public void setIsWorkmatePrivate(String isWorkmatePrivate) {
		this.isWorkmatePrivate = isWorkmatePrivate;
	}
	
	//==隐私设置结束==
	
	/*
	 * 可下载者
	 */
	protected String authAttDownloadIds = null;

	public String getAuthAttDownloadIds() {
		return authAttDownloadIds;
	}

	public void setAuthAttDownloadIds(String authAttDownloadIds) {
		this.authAttDownloadIds = authAttDownloadIds;
	}

	/*
	 * 可下载者名称
	 */
	protected String authAttDownloadNames = null;

	public String getAuthAttDownloadNames() {
		return authAttDownloadNames;
	}

	public void setAuthAttDownloadNames(String authAttDownloadNames) {
		this.authAttDownloadNames = authAttDownloadNames;
	}
	
	/*
	 * 不可下载标记
	 */
	protected String authAttNodownload = null;

	public String getAuthAttNodownload() {
		return authAttNodownload;
	}

	public void setAuthAttNodownload(String authAttNodownload) {
		this.authAttNodownload = authAttNodownload;
	}
}
