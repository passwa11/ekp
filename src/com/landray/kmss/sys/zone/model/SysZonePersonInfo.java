/**
 * 
 */
package com.landray.kmss.sys.zone.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.LastModifiedTimeModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationCountModel;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;
import com.landray.kmss.sys.fans.interfaces.ISysFansMain;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogAutoSaveModel;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainModel;
import com.landray.kmss.sys.tag.model.SysTagMain;
import com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.UserUtil;

/**
 * 
 * 
 */
// 字段命名存在问题
@SuppressWarnings("serial")
public class SysZonePersonInfo extends LastModifiedTimeModel implements
		IAttachment, ISysTagMainModel, InterceptFieldEnabled,ISysEvaluationModel,
		ISysEvaluationCountModel, ISysFansMain, ISysReadLogAutoSaveModel {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysZonePersonInfo.class);

	 private String fdSex;//性别
	// private String fdCompanyPhone;//公司电话
	private String fdEnglishName;// 英文名
	private String fdSignature;// 个性签名
	// private String mobilPhone;//手机号码
	// private String email;//邮箱
	private String fdDefaultLang;// 默认语言

	public String getFdDefaultLang() {
		return fdDefaultLang;
	}

	public void setFdDefaultLang(String fdDefaultLang) {
		this.fdDefaultLang = fdDefaultLang;
	}

	private String leader;// 直属领导

	protected SysOrgPerson person;// 组织架构

	// ======== 员工黄页===开始====================
	/**
	 * 关注数
	 */
	protected Integer fdAttentionNum = 0;

	/**
	 * @return 关注数
	 */
	public Integer getFdAttentionNum() {
		return fdAttentionNum;
	}

	/**
	 * @param fdAttentionNum
	 *            关注数
	 */
	public void setFdAttentionNum(Integer fdAttentionNum) {
		this.fdAttentionNum = fdAttentionNum;
	}

	/**
	 * @param fdSex
	 *            性别
	 */
	public String getFdSex() {
		return fdSex;
	}

	public void setFdSex(String fdSex) {
		this.fdSex = fdSex;
	}

	/**
	 * 粉丝数
	 */
	protected Integer fdFansNum = 0;

	/**
	 * @return 粉丝数
	 */
	@Override
	public Integer getFdFansNum() {
		return fdFansNum;
	}

	/**
	 * @param fdFansNum
	 *            粉丝数
	 */
	@Override
	public void setFdFansNum(Integer fdFansNum) {
		this.fdFansNum = fdFansNum;
	}

	/**
	 * 个人资料目录列表
	 */
	protected List<SysZonePersonData> fdDatas;

	/**
	 * @return 个人资料目录列表
	 */
	public List<SysZonePersonData> getFdDatas() {
		return fdDatas;
	}

	/**
	 * @param fdDatas
	 *            个人资料目录列表
	 */
	public void setFdDatas(List<SysZonePersonData> fdDatas) {
		this.fdDatas = fdDatas;
	}

	private Date docCreateTime;

	@Override
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	@Override
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	private SysOrgPerson docCreator;

	@Override
	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	@Override
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	private SysTagMain sysTagMain = null;

	@Override
	public SysTagMain getSysTagMain() {
		return sysTagMain;
	}

	@Override
	public void setSysTagMain(SysTagMain sysTagMain) {
		this.sysTagMain = sysTagMain;
	}

	@Override
	public String getDocStatus() {
		return "30";
	}

	// ======== 员工黄页====结束===================

	public String getFdEnglishName() {
		return fdEnglishName;
	}

	public void setFdEnglishName(String fdEnglishName) {
		this.fdEnglishName = fdEnglishName;
	}

	public String getFdSignature() {
		return (String) readLazyField("fdSignature", fdSignature);
	}

	public void setFdSignature(String fdSignature) {
		this.fdSignature = (String) writeLazyField("fdSignature",
				this.fdSignature, fdSignature);
	}

	public SysOrgPerson getPerson() {
		return person;
	}

	public void setPerson(SysOrgPerson person) {
		this.person = person;
	}

	public String getLeader() {
		try {
			if (leader != null) {
				return leader;
			}
			leader = person.getMyLeader(0).getFdName();
			return leader;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(UserUtil.getUser().getFdName() + ",当前用户没有直属领导!");
			}
			return null;
		}
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	@Override
	public Class<?> getFormClass() {
		return SysZonePersonInfoForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("person.fdId", "personId");
			toFormPropertyMap.put("person.fdName", "personName");
			toFormPropertyMap.put("person.fdParent.fdName", "dep");
			toFormPropertyMap.put("person.fdSex", "fdSex");
			toFormPropertyMap.put("person.fdWorkPhone", "fdCompanyPhone");
			toFormPropertyMap.put("person.fdMobileNo", "mobilPhone");
			toFormPropertyMap.put("person.fdEmail", "email");
			toFormPropertyMap.put("person.fdDefaultLang", "fdDefaultLang");

			toFormPropertyMap.put("person.fdPosts",
					new ModelConvertor_ModelListToString("post", "fdName"));
			
			toFormPropertyMap.put("authAttDownloads",
					new ModelConvertor_ModelListToString(
							"authAttDownloadIds:authAttDownloadNames",
							"fdId:deptLevelNames"));

		}
		return toFormPropertyMap;
	}

	@Override
	public void setDocStatus(String docStatus) {
	}

	@Override
	public String getDocSubject() {
		return person.getFdName();
	}

	/**
	 * 点评统计
	 */
	protected Integer docEvalCount = Integer.valueOf(0);

	/**
	 * @return 点评统计
	 */
	@Override
	public Integer getDocEvalCount() {
		return docEvalCount;
	}

	/**
	 * @param docEvalCount
	 *            点评统计
	 */
	@Override
	public void setDocEvalCount(Integer docEvalCount) {
		this.docEvalCount = docEvalCount;
	}
	
	// ===== 阅读机制（开始） =====
	protected Long docReadCount = new Long(0);

	@Override
	public Long getDocReadCount() {
		return docReadCount;
	}

	@Override
	public void setDocReadCount(Long docReadCount) {
		this.docReadCount = docReadCount;
	}
	
	String readLogSSeparate = null;

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
	// ===== 阅读机制（结束） =====
	
	// ====  隐私设置 =======
	Boolean isContactPrivate = null;
	
	Boolean isDepInfoPrivate = null;
	
	Boolean isRelationshipPrivate = null;
	
	Boolean isWorkmatePrivate = null;
	
	public Boolean getIsContactPrivate() {
		return isContactPrivate;
	}

	public void setIsContactPrivate(Boolean isContactPrivate) {
		this.isContactPrivate = isContactPrivate;
	}

	public Boolean getIsDepInfoPrivate() {
		return isDepInfoPrivate;
	}

	public void setIsDepInfoPrivate(Boolean isDepInfoPrivate) {
		this.isDepInfoPrivate = isDepInfoPrivate;
	}

	public Boolean getIsRelationshipPrivate() {
		return isRelationshipPrivate;
	}

	public void setIsRelationshipPrivate(Boolean isRelationshipPrivate) {
		this.isRelationshipPrivate = isRelationshipPrivate;
	}

	public Boolean getIsWorkmatePrivate() {
		return isWorkmatePrivate;
	}

	public void setIsWorkmatePrivate(Boolean isWorkmatePrivate) {
		this.isWorkmatePrivate = isWorkmatePrivate;
	}

	
	// ====  隐私设置结束 =======
	
	
	/*
	 * 可下载者
	 */
	protected List authAttDownloads = new ArrayList();

	public List getAuthAttDownloads() {
		return authAttDownloads;
	}

	public void setAuthAttDownloads(List authAttDownloads) {
		this.authAttDownloads = authAttDownloads;
	}
	
	/*
	 * 不可下载标记
	 */
	protected Boolean authAttNodownload;

	public Boolean getAuthAttNodownload() {
		if (this.authAttNodownload == null) {
            return Boolean.FALSE;
        }
		return authAttNodownload;
	}

	public void setAuthAttNodownload(Boolean authAttNodownload) {
		this.authAttNodownload = authAttNodownload;
	}
}
