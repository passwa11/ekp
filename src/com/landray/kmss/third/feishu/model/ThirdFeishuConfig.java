package com.landray.kmss.third.feishu.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.third.feishu.cluster.ThirdFeishuConfigMessage;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;


public class ThirdFeishuConfig extends BaseAppConfig {

	public ThirdFeishuConfig() throws Exception {
		super();
		//默认值处理
		boolean isNeedSave = false;
		String nameSynWay = getOrg2FeishuNameSynWay();
		if(StringUtil.isNull(nameSynWay)){
			setOrg2FeishuNameSynWay("addSyn");
			setOrg2FeishuName("fdName");
			isNeedSave = true;
		}
		String mobileSynWay = getOrg2FeishuMobileSynWay();
		if(StringUtil.isNull(mobileSynWay)){
			setOrg2FeishuMobileSynWay("addSyn");
			setOrg2FeishuMobile("fdMobileNo");
			isNeedSave = true;
		}
		String useridSynWay = getOrg2FeishuUseridSynWay();
		if(StringUtil.isNull(useridSynWay)){
			setOrg2FeishuUseridSynWay("addSyn");
			setOrg2FeishuUserid("fdId");
			isNeedSave = true;
		}
		//兼容老配置处理
		String departmentSynWay = getOrg2FeishuDepartmentSynWay();
		if(StringUtil.isNull(departmentSynWay)){
			String synchroOrg2FeishuMultiDept = getValue("synchroOrg2FeishuMultiDept");
			if("true".equalsIgnoreCase(synchroOrg2FeishuMultiDept)){
				setOrg2FeishuDepartmentSynWay("syn");
				setOrg2FeishuDepartment("fdMuilDept");
				setValue("synchroOrg2FeishuMultiDept", null);
			}
			else{
				setOrg2FeishuDepartmentSynWay("addSyn");
				setOrg2FeishuDepartment("fdDept");
			}
			isNeedSave = true;
		}
		String positionSynWay = getOrg2FeishuPositionSynWay();
		if(StringUtil.isNull(positionSynWay)){
			String synchroOrg2FeishuJob = getValue("synchroOrg2FeishuJob");
			if("true".equalsIgnoreCase(synchroOrg2FeishuJob)){
				setOrg2FeishuPositionSynWay("syn");
				setOrg2FeishuPosition("hbmPosts");
				setValue("synchroOrg2FeishuJob", null);
				isNeedSave = true;
			}
		}
		if(isNeedSave){
			save();
		}
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuConfig.class);

	@Override
	public String getJSPUrl() {
		return "/third/feishu/feishu_config.jsp";
	}

	public static ThirdFeishuConfig newInstance() {
		ThirdFeishuConfig config = null;
		try {
			config = new ThirdFeishuConfig();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return config;
	}


	public String getFeishuEnabled() {
		return getValue("feishuEnabled");
	}

	public void setFeishuEnabled(String feishuEnabled) {
		setValue("feishuEnabled", feishuEnabled);
	}

	public String getFeishuAppid() {
		return getValue("feishuAppid");
	}

	public void setFeishuAppid(String feishuAppid) {
		setValue("feishuAppid", feishuAppid);
	}

	public String getFeishuAppsecret() {
		return getValue("feishuAppsecret");
	}

	public void setFeishuAppsecret(String feishuAppsecret) {
		setValue("feishuAppsecret", feishuAppsecret);
	}

	public String getFeishuSsoEnabled() {
		return getValue("feishuSsoEnabled");
	}

	public void setFeishuSsoEnabled(String feishuSsoEnabled) {
		setValue("feishuSsoEnabled", feishuSsoEnabled);
	}

	public String getFeishuTodoMsgEnabled() {
		return getValue("feishuTodoMsgEnabled");
	}

	public void setFeishuTodoMsgEnabled(String feishuTodoMsgEnabled) {
		setValue("feishuTodoMsgEnabled", feishuTodoMsgEnabled);
	}

	public String getFeishuToreadMsgEnabled() {
		return getValue("feishuToreadMsgEnabled");
	}

	public void setFeishuToreadMsgEnabled(String feishuToreadMsgEnabled) {
		setValue("feishuToreadMsgEnabled", feishuToreadMsgEnabled);
	}

	public String getFeishuNotifyLogSaveDays() {
		return getValue("feishuNotifyLogSaveDays");
	}

	public void setFeishuNotifyLogSaveDays(String feishuNotifyLogSaveDays) {
		setValue("feishuNotifyLogSaveDays", feishuNotifyLogSaveDays);
	}

	public String getSynchroOrg2Feishu() {
		return getValue("synchroOrg2Feishu");
	}

	public void setSynchroOrg2Feishu(String synchroOrg2Feishu) {
		setValue("synchroOrg2Feishu", synchroOrg2Feishu);
	}

	public String getUpdatePersonMapping() {
		return getValue("updatePersonMapping");
	}

	public void setUpdatePersonMapping(String updatePersonMapping) {
		setValue("updatePersonMapping", updatePersonMapping);
	}

	@Override
    protected String getValue(String name) {
		String value = super.getValue(name);
		if (value != null) {
			value = value.trim();
		}
		return value;
	}

	public String getFeishuSsoOutEnabled() {
		String value = getValue("feishuSsoOutEnabled");
		if (StringUtil.isNull(value)) {
			value = "true";
		}
		return value;
	}

	public void setFeishuSsoOutEnabled(String feishuSsoOutEnabled) {
		setValue("feishuSsoOutEnabled", feishuSsoOutEnabled);
	}

	@Override
    public void save() throws Exception {
		super.save();
		try {
			IThirdFeishuService thirdFeishuService = (IThirdFeishuService) SpringBeanUtil
					.getBean("thirdFeishuService");
			thirdFeishuService.resetToken();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		try {
			MessageCenter
					.getInstance()
					.sendToOther(
							new ThirdFeishuConfigMessage(
									"updateConfig",
									this.getDataMap()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getSynchroOrg2FeishuEkpRootId() {
		return getValue("synchroOrg2FeishuEkpRootId");
	}

	public void setSynchroOrg2FeishuEkpRootId(String synchroOrg2FeishuEkpRootId) {
		setValue("synchroOrg2FeishuEkpRootId",synchroOrg2FeishuEkpRootId);
	}

	public String getSynchroOrg2FeishuEkpRootName() {
		return getValue("synchroOrg2FeishuEkpRootName");
	}

	public void setSynchroOrg2FeishuEkpRootName(String synchroOrg2FeishuEkpRootName) {
		setValue("synchroOrg2FeishuEkpRootName",synchroOrg2FeishuEkpRootName);
	}

	public String getSynchroOrg2FeishuEkpRootSync() {
		String value = getValue("synchroOrg2FeishuEkpRootSync");
		if(StringUtil.isNull(value)){
			value = "true";
		}
		return value;
	}

	public void setSynchroOrg2FeishuEkpRootSync(String synchroOrg2FeishuEkpRootSync) {
		setValue("synchroOrg2FeishuEkpRootSync",synchroOrg2FeishuEkpRootSync);
	}

	public String getSynchroOrg2FeishuOutRangePersonHandle() {
		return getValue("synchroOrg2FeishuOutRangePersonHandle");
	}

	public void setSynchroOrg2FeishuOutRangePersonHandle(String synchroOrg2FeishuOutRangePersonHandle) {
		setValue("synchroOrg2FeishuOutRangePersonHandle",synchroOrg2FeishuOutRangePersonHandle);
	}

	public String getSynchroOrg2FeishuDisablePersonHandle() {
		String value = getValue("synchroOrg2FeishuDisablePersonHandle");
		if(StringUtil.isNull(value)){
			value = "1";
		}
		return value;
	}

	public void setSynchroOrg2FeishuDisablePersonHandle(String synchroOrg2FeishuDisablePersonHandle) {
		setValue("synchroOrg2FeishuDisablePersonHandle",synchroOrg2FeishuDisablePersonHandle);
	}

	public String getSynchroOrg2FeishuFeishuRootId() {
		return getValue("synchroOrg2FeishuFeishuRootId");
	}

	public void setSynchroOrg2FeishuFeishuRootId(String synchroOrg2FeishuFeishuRootId) {
		setValue("synchroOrg2FeishuFeishuRootId",synchroOrg2FeishuFeishuRootId);
	}

	public String getSynchroOrg2FeishuFeishuRootOpenId() {
		return getValue("synchroOrg2FeishuFeishuRootOpenId");
	}

	public void setSynchroOrg2FeishuFeishuRootOpenId(String synchroOrg2FeishuFeishuRootOpenId) {
		setValue("synchroOrg2FeishuFeishuRootOpenId",synchroOrg2FeishuFeishuRootOpenId);
	}

	public String getPcScanLoginEnabled() {
		return getValue("pcScanLoginEnabled");
	}

	public void setPcScanLoginEnabled(String pcScanLoginEnabled) {
		setValue("pcScanLoginEnabled",pcScanLoginEnabled);
	}

	public String getFeishuApprovalEnabled() {
		return getValue("feishuApprovalEnabled");
	}

	public void setFeishuApprovalEnabled(String feishuApprovalEnabled) {
		setValue("feishuApprovalEnabled", feishuApprovalEnabled);
	}

	public String getFeishuApprovalUrl() {
		return getValue("feishuApprovalUrl");
	}

	public void setFeishuApprovalUrl(String feishuApprovalUrl) {
		setValue("feishuApprovalUrl", feishuApprovalUrl);
	}

	/**
	 * 人员同步字段：姓名
	 * @return
	 */
	public String getOrg2FeishuNameSynWay(){
		return getValue("org2Feishu.name.synWay");
	}

	public void setOrg2FeishuNameSynWay(String nameSynWay){
		setValue("org2Feishu.name.synWay", nameSynWay);
	}

	public String getOrg2FeishuName(){
		String name = getValue("org2Feishu.name");
		if(StringUtil.isNull(name)){
			return "fdName";
		}
		return name;
	}

	public void setOrg2FeishuName(String name){
		setValue("org2Feishu.name", name);
	}

	/**
	 * 人员同步字段：手机号
	 */
	public String getOrg2FeishuMobileSynWay(){
		return getValue("org2Feishu.mobile.synWay");
	}

	public void setOrg2FeishuMobileSynWay(String mobileSynWay){
		setValue("org2Feishu.mobile.synWay", mobileSynWay);
	}

	public String getOrg2FeishuMobile(){
		String mobile = getValue("org2Feishu.mobile");
		if(StringUtil.isNull(mobile)){
			return "fdMobileNo";
		}
		return mobile;
	}

	public void setOrg2FeishuMobile(String mobile){
		setValue("org2Feishu.mobile", mobile);
	}

	/**
	 * 人员同步字段：员工UserId
	 */
	public String getOrg2FeishuUseridSynWay(){
		return getValue("org2Feishu.userid.synWay");
	}

	public void setOrg2FeishuUseridSynWay(String userIdSync){
		setValue("org2Feishu.userid.synWay", userIdSync);
	}

	public String getOrg2FeishuUserid(){
		String userid = getValue("org2Feishu.userid");
		if(StringUtil.isNull(userid)){
			return "fdId";
		}
		return userid;
	}

	public void setOrg2FeishuUserid(String userId){
		setValue("org2Feishu.userid", userId);
	}

	/**
	 * 人员同步字段：部门
	 */
	public String getOrg2FeishuDepartmentSynWay(){
		return getValue("org2Feishu.department.synWay");
	}

	public void setOrg2FeishuDepartmentSynWay(String departmentSynWay){
		setValue("org2Feishu.department.synWay", departmentSynWay);
	}

	public String getOrg2FeishuDepartment(){
		String depart = getValue("org2Feishu.department");
		if(StringUtil.isNull(depart)){
			return "fdDept";
		}
		return depart;
	}

	public void setOrg2FeishuDepartment(String department){
		setValue("org2Feishu.department", department);
	}

	/**
	 * 人员同步字段：排序号
	 */
	public String getOrg2FeishuOrderInDeptsSynWay(){
		return getValue("org2Feishu.orderInDepts.synWay");
	}

	public void setOrg2FeishuOrderInDeptsSynWay(String orderInDeptsSynWay){
		setValue("org2Feishu.orderInDepts.synWay", orderInDeptsSynWay);
	}

	public String getOrg2FeishuOrderInDepts(){
		return getValue("org2Feishu.orderInDepts");
	}

	public void setOrg2FeishuOrderInDepts(String orderInDepts){
		setValue("org2Feishu.orderInDepts", orderInDepts);
	}

	/**
	 * 人员同步字段：别名
	 */
	public String getOrg2FeishuAliasSynWay(){
		return getValue("org2Feishu.alias.synWay");
	}

	public void setOrg2FeishuAliasSynWay(String aliasSynWay){
		setValue("org2Feishu.alias.synWay", aliasSynWay);
	}

	public String getOrg2FeishuAlias(){
		return getValue("org2Feishu.alias");
	}

	public void setOrg2FeishuAlias(String alias){
		setValue("org2Feishu.alias", alias);
	}

	/**
	 * 人员同步字段：英文名
	 */
	public String getOrg2FeishuEnglishNameSynWay(){
		return getValue("org2Feishu.english.name.synWay");
	}

	public void setOrg2FeishuEnglishNameSynWay(String englishNameSynWay){
		setValue("org2Feishu.english.name.synWay", englishNameSynWay);
	}

	public String getOrg2FeishuEnglishName(){
		return getValue("org2Feishu.english.name");
	}

	public void getOrg2FeishuEnglishName(String englishName){
		setValue("org2Feishu.english.name", englishName);
	}

	/**
	 * 人员同步字段：工号
	 */
	public String getOrg2FeishuJobnumberSynWay(){
		return getValue("org2Feishu.jobnumber.synWay");
	}

	public void setOrg2FeishuJobnumberSynWay(String jobnumberSynWay){
		setValue("org2Feishu.jobnumber.synWay", jobnumberSynWay);
	}

	public String getOrg2FeishuJobnumber(){
		return getValue("org2Feishu.jobnumber");
	}

	public void setOrg2FeishuJobnumber(String jobnumber){
		setValue("org2Feishu.jobnumber", jobnumber);
	}

	/**
	 * 人员同步字段：职务
	 */
	public String getOrg2FeishuPositionSynWay(){
		return getValue("org2Feishu.position.synWay");
	}

	public void setOrg2FeishuPositionSynWay(String positionSynWay){
		setValue("org2Feishu.position.synWay", positionSynWay);
	}

	public String getOrg2FeishuPosition(){
		return getValue("org2Feishu.position");
	}

	public void setOrg2FeishuPosition(String position){
		setValue("org2Feishu.position", position);
	}

	/**
	 * 人员同步字段：邮箱
	 */
	public String getOrg2FeishuEmailSynWay(){
		return getValue("org2Feishu.email.synWay");
	}

	public void setOrg2FeishuEmailSynWay(String emailSynWay){
		setValue("org2Feishu.email.synWay", emailSynWay);
	}

	public String getOrg2FeishuEmailName(){
		return getValue("org2Feishu.email.name");
	}

	public void setOrg2FeishuEmailName(String email){
		setValue("org2Feishu.email.name", email);
	}

	/**
	 * 人员同步字段：性别
	 */
	public String getOrg2FeishuSexSynWay(){
		return getValue("org2Feishu.sex.synWay");
	}

	public void setOrg2FeishuSexSynWay(String sexSynWay){
		setValue("org2Feishu.sex.synWay", sexSynWay);
	}

	public String getOrg2FeishuSexName(){
		return getValue("org2Feishu.sex.name");
	}

	public void setOrg2FeishuSexName(String sexName){
		setValue("org2Feishu.sex.name", sexName);
	}

	/**
	 * 人员同步字段：办公地点
	 */
	public String getOrg2FeishuWorkPlaceSynWay(){
		return getValue("org2Feishu.workPlace.synWay");
	}

	public void getOrg2FeishuWorkPlaceSynWay(String workPlaceSynWay){
		setValue("org2Feishu.workPlace.synWay", workPlaceSynWay);
	}

	public String getOrg2FeishuWorkPlace(){
		return getValue("org2Feishu.workPlace");
	}

	public void setOrg2FeishuWorkPlace(String workPlace){
		setValue("org2Feishu.workPlace", workPlace);
	}

	/**
	 * 人员同步字段:入职时间
	 */
	public String getOrg2FeishuHiredDateSynWay(){
		return getValue("org2Feishu.hiredDate.synWay");
	}

	public void setOrg2FeishuHiredDateSynWay(String hiredDateSynWay){
		setValue("org2Feishu.hiredDate.synWay", hiredDateSynWay);
	}

	public String getOrg2FeishuHiredDate(){
		return getValue("org2Feishu.hiredDate");
	}

	public void setOrg2FeishuHiredDate(String hiredDate){
		setValue("org2Feishu.hiredDate", hiredDate);
	}

	/**
	 * 人员同步字段:企业邮箱
	 */
	public String getOrg2FeishuOrgEmailSynWay(){
		return getValue("org2Feishu.orgEmail.synWay");
	}

	public void setOrg2FeishuOrgEmailSynWay(String org2FeishuOrgEmailSynWay){
		setValue("org2Feishu.orgEmail.synWay", org2FeishuOrgEmailSynWay);
	}

	public String getOrg2FeishuOrgEmail(){
		return getValue("org2Feishu.orgEmail");
	}

	public void setOrg2FeishuOrgEmail(String orgEmail){
		setValue("org2Feishu.orgEmail", orgEmail);
	}

	/**
	 * 人员同步字段:号码隐藏
	 */
	public String getOrg2FeishuIsHideSynWay(){
		return getValue("org2Feishu.isHide.synWay");
	}

	public void setOrg2FeishuIsHideSynWay(String isHideSynWay){
		setValue("org2Feishu.isHide.synWay", isHideSynWay);
	}

	public String getOrg2FeishuIsHideAll(){
		return getValue("org2Feishu.isHide.all");
	}

	public void setOrg2FeishuIsHideAll(String isHideAll){
		setValue("org2Feishu.isHide.all", isHideAll);
	}

	public String getOrg2FeishuIsHide(){
		return getValue("org2Feishu.isHide");
	}

	public void setOrg2FeishuIsHide(String isHide){
		setValue("org2Feishu.isHide", isHide);
	}
}
