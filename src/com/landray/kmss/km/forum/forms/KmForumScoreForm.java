package com.landray.kmss.km.forum.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;

/**
 * 创建日期 2006-Sep-07
 * 
 * @author 吴兵
 */
public class KmForumScoreForm extends ExtendForm implements IAttachmentForm

{
	private String personId = null;
	
	/*
	 * 论坛积分
	 */
	private String score = null;

	/*
	 * 论坛发帖数
	 */
	private String postCount = null;
	
	/*
	 * 论坛回帖数
	 */
	private String fdReplyCount = null;

	/*
	 * 论坛呢称
	 */
	protected String fdNickName;

	/*
	 * 个性签名
	 */
	protected String fdSign;

	/*
	 * 用户名
	 */
	protected String userName;

	/*
	 * 部门
	 */
	protected String dept;

	/*
	 * 岗位
	 */
	protected String post;
	
	public String getPersonId(){
		return personId;
	}
	public void setPersonId(String personId){
		this.personId = personId;
	}
	
	/**
	 * @return 返回 论坛积分
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score
	 *            要设置的 论坛积分
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * @return 返回 论坛发帖数
	 */
	public String getPostCount() {
		return postCount;
	}

	/**
	 * @param postCount
	 *            要设置的 论坛发帖数
	 */
	public void setPostCount(String postCount) {
		this.postCount = postCount;
	}
	

	public String getFdReplyCount() {
		return fdReplyCount;
	}
	public void setFdReplyCount(String fdReplyCount) {
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
		return fdSign;
	}

	/**
	 * @param fdSign
	 *            要设置的 个性签名
	 */
	public void setFdSign(String fdSign) {
		this.fdSign = fdSign;
	}

	/**
	 * @return 返回 用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            要设置的 用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return 返回 部门
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept
	 *            要设置的 部门
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return 返回 岗位
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @param post
	 *            要设置的 岗位
	 */
	public void setPost(String post) {
		this.post = post;
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
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		personId = null;
		score = null;
		postCount = null;
		fdNickName = null;
		fdSign = null;
		userName = null;
		dept = null;
		post = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmForumScore.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdId", new FormConvertor_IDToModel(
					"person", SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}
	
}
