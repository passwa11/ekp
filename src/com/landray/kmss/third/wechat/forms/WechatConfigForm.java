package com.landray.kmss.third.wechat.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;

import com.landray.kmss.third.wechat.model.WechatConfig;


/**
 * 新 类0 Form
 * 
 * @author 
 * @version 1.0 2014-05-08
 */
public class WechatConfigForm extends ExtendForm {

	protected String fdScene;
	
	public String getFdScene() {
		return fdScene;
	}

	public void setFdScene(String fdScene) {
		this.fdScene = fdScene;
	}

	/**
	 * ekpid
	 */
	protected String fdEkpid = null;
	
	/**
	 * @return ekpid
	 */
	public String getFdEkpid() {
		return fdEkpid;
	}
	
	/**
	 * @param fdEkpid ekpid
	 */
	public void setFdEkpid(String fdEkpid) {
		this.fdEkpid = fdEkpid;
	}
	
	/**
	 * 微信号
	 */
	protected String fdOpenid = null;
	
	/**
	 * @return 微信号
	 */
	public String getFdOpenid() {
		return fdOpenid;
	}
	
	/**
	 * @param fdOpenid 微信号
	 */
	public void setFdOpenid(String fdOpenid) {
		this.fdOpenid = fdOpenid;
	}
	
	/**
	 * 是否推送待办
	 */
	protected String fdPushProcess = null;
	
	/**
	 * @return 是否推送待办
	 */
	public String getFdPushProcess() {
		return fdPushProcess;
	}
	
	/**
	 * @param fdPushProcess 是否推送待办
	 */
	public void setFdPushProcess(String fdPushProcess) {
		this.fdPushProcess = fdPushProcess;
	}
	
	/**
	 * 是否推送待阅
	 */
	protected String fdPushRead = null;
	
	/**
	 * @return 是否推送待阅
	 */
	public String getFdPushRead() {
		return fdPushRead;
	}
	
	/**
	 * @param fdPushRead 是否推送待阅
	 */
	public void setFdPushRead(String fdPushRead) {
		this.fdPushRead = fdPushRead;
	}
	
	/**
	 * 是否链接授权微信
	 */
	protected String fdUrlAccess = null;
	
	/**
	 * @return 是否链接授权微信
	 */
	public String getFdUrlAccess() {
		return fdUrlAccess;
	}
	
	/**
	 * @param fdUrlAccess 是否链接授权微信
	 */
	public void setFdUrlAccess(String fdUrlAccess) {
		this.fdUrlAccess = fdUrlAccess;
	}
	
	/**
	 * 微信昵称
	 */
	protected String fdNickname = null;
	
	/**
	 * @return 微信昵称
	 */
	public String getFdNickname() {
		return fdNickname;
	}
	
	/**
	 * @param fdNickname 微信昵称
	 */
	public void setFdNickname(String fdNickname) {
		this.fdNickname = fdNickname;
	}
	
	/**
	 * 微信图像
	 */
	protected String fdImage = null;
	
	/**
	 * @return 微信图像
	 */
	public String getFdImage() {
		return fdImage;
	}
	
	/**
	 * @param fdImage 微信图像
	 */
	public void setFdImage(String fdImage) {
		this.fdImage = fdImage;
	}

	
	//private String openid;
	//private String publicCode;
	/**
	 * 微信公众
	 */
	private String fdPublicCode;
	
	/**
	 * ekp系统登录名
	 */
	//private String loginName;
	private  String fdLoginName;
	
	/**
	 * ekp系统登录密码
	 */
	//private String passwd;
	private String fdPasswd;
	
	//private String userName;
	
	/**
	 * 用户姓名
	 */
	private String fdUserName;
	
	//private String fansId;
	/**
	 * 粉丝表主键ID  用来生成粉丝头像图片的名称
	 */
	private String fdFansId;
	
	//private String userId;
	//private String deptName;
	/**
	 * 用户所在部门名称
	 */
	private String fdDeptName;
	
	//private String licenseId;
//	private String fdLicenseId;
	
	//private String nickName;
	
//	/**
//	 * 用户随机串
//	 */
//	private String fdRandom ;
//	
//	public String getFdRandom() {
//		return fdRandom;
//	}
//
//	public void setFdRandom(String fdRandom) {
//		this.fdRandom = fdRandom;
//	}

	public String getFdPublicCode() {
		return fdPublicCode;
	}

	public void setFdPublicCode(String fdPublicCode) {
		this.fdPublicCode = fdPublicCode;
	}

	public String getFdLoginName() {
		return fdLoginName;
	}

	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	public String getFdPasswd() {
		return fdPasswd;
	}

	public void setFdPasswd(String fdPasswd) {
		this.fdPasswd = fdPasswd;
	}

	public String getFdUserName() {
		return fdUserName;
	}

	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	public String getFdFansId() {
		return fdFansId;
	}

	public void setFdFansId(String fdFansId) {
		this.fdFansId = fdFansId;
	}

	public String getFdDeptName() {
		return fdDeptName;
	}

	public void setFdDeptName(String fdDeptName) {
		this.fdDeptName = fdDeptName;
	}

//	public String getFdLicenseId() {
//		return fdLicenseId;
//	}
//
//	public void setFdLicenseId(String fdLicenseId) {
//		this.fdLicenseId = fdLicenseId;
//	}
	
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdEkpid = null;
		fdOpenid = null;
		fdPushProcess = "1";
		fdPushRead = "0";
		fdUrlAccess = "1";
		fdNickname = null;
		fdImage = null;
		fdPublicCode=null;
		fdLoginName=null;
		fdPasswd=null;
		fdUserName=null;
		fdFansId=null;
		fdDeptName=null;
		//fdLicenseId=null;
		//fdRandom=null;
		
		super.reset(mapping, request);
	}

	

	@Override
    public Class getModelClass() {
		return WechatConfig.class;
	}
	
	//提交次数统计
	private Integer fdCount;
	
	
	public Integer getFdCount() {
		return fdCount;
	}

	public void setFdCount(Integer fdCount) {
		this.fdCount = fdCount;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
