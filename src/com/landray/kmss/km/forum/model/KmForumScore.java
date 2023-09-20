package com.landray.kmss.km.forum.model;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.forum.forms.KmForumScoreForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 用户积分
 */
public class KmForumScore extends BaseModel implements IAttachment,
      InterceptFieldEnabled // 大字段加入延时加载实现接口
{

	/*
	 * 论坛积分
	 */
	protected Integer fdScore = new Integer(0);

	/*
	 * 论坛发贴数
	 */
	protected Integer fdPostCount;

	/*
	 * 论坛回帖数
	 */
	protected Integer fdReplyCount;
	/*
	 * 论坛呢称
	 */
	protected String fdNickName;

	/*
	 * 个性签名
	 */
	protected String fdSign;

	/*
	 * 用户
	 */
	protected SysOrgPerson person = null;

	public KmForumScore() {
		super();
	}

	/**
	 * @return 返回 论坛积分
	 */
	public Integer getFdScore() {
		return fdScore;
	}

	/**
	 * @param fdScore
	 *            要设置的 论坛积分
	 */
	public void setFdScore(Integer fdScore) {
		this.fdScore = fdScore;
	}

	/**
	 * @return 返回 论坛发贴数
	 */
	public Integer getFdPostCount() {
		return fdPostCount;
	}

	/**
	 * @param fdPostCount
	 *            要设置的 论坛发贴数
	 */
	public void setFdPostCount(Integer fdPostCount) {
		this.fdPostCount = fdPostCount;
	}
	
	

	public Integer getFdReplyCount() {
		return fdReplyCount;
	}

	public void setFdReplyCount(Integer fdReplyCount) {
		this.fdReplyCount = fdReplyCount;
	}

	/**
	 * @return 返回 论坛呢称
	 */
	public String getFdNickName() {
		return fdNickName;
	}

	/**
	 * @param fdPostCount
	 *            要设置的 论坛呢称
	 */
	public void setFdNickName(String fdNickName) {
		this.fdNickName = fdNickName;
	}

	/**
	 * @return 返回 个性签名
	 */
	public String getFdSign() {
		return (String) readLazyField("fdSign", fdSign);
	}

	/**
	 * @param fdSign
	 *            要设置的 个性签名
	 */
	public void setFdSign(String fdSign) {
		this.fdSign = (String) writeLazyField("fdSign", this.fdSign, fdSign);
	}

	/**
	 * @return 返回 用户
	 */
	public SysOrgPerson getPerson() {
		return person;
	}

	/**
	 * @param user
	 *            要设置的 用户
	 */
	public void setPerson(SysOrgPerson person) {
		this.person = person;
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
    public Class getFormClass() {
		return KmForumScoreForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("person.fdId", "personId");
			toFormPropertyMap.put("person.fdName", "userName");
			toFormPropertyMap.put("person.fdParent.fdName", "dept");

			toFormPropertyMap.put("person.fdPosts",
					new ModelConvertor_ModelListToString("post", "fdName"));

			toFormPropertyMap.put("fdPostCount", "postCount");
			toFormPropertyMap.put("fdScore", "score");

		}
		return toFormPropertyMap;
	}

}
