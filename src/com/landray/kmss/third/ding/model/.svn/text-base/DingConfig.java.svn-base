package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.third.ding.oms.DingOmsConfig;
import com.landray.kmss.third.ding.provider.DingNotifyProvider;
import com.landray.kmss.third.ding.service.IThirdDingClockService;
import com.landray.kmss.third.ding.util.DingInteractivecardUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkRateLimitUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 钉钉配置项
 */
public class DingConfig extends BaseAppConfig {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingConfig.class);

    public static DingConfig newInstance() {
        DingConfig config = null;
        try {
            config = new DingConfig();
        } catch (Exception e) {
            logger.error("", e);
        }
        return config;
    }

    @Override
    public void save() throws Exception {
        // ISV方式默认禁用手机号码回调功能
        if ("3".equals(getDevModel())) {
            setDingMobileEnabled("false");
        }
        if (StringUtil.isNotNull(getDingOmsOutEnabled())) {
            setDingOmsOutEnabled(null);
            setDingOmsInEnabled(null);
            setLdingEnabled(null);
        }
        DingConfig config = newInstance();
        String oldSyncSelection = config.getSyncSelection();
        logger.info("初始值oldDingOrgId" + config.getDingOrgId());
        String oldDingOrgId = config.getDingOrgId() == null ? "" : config.getDingOrgId();
        super.save();
        //同步选择修改 后台清空同步时间戳
        String newSyncSelection = getSyncSelection();
        String newDingOrgId = getDingOrgId();
        logger.info("同步选择修改 后台清空同步时间戳参数：oldSyncSelection：" + oldSyncSelection + "，oldDingOrgId：" + oldDingOrgId + "，newSyncSelection：" + newSyncSelection + "，newDingOrgId：" + newDingOrgId);
        //（当同步选择修改 并且 同步选择的方式为1 2） 或者 ekp 中 根机构修改 清空同步时间戳
        if (StringUtil.isNotNull(newSyncSelection) && ("1".equals(newSyncSelection) || "2".equals(newSyncSelection))) {
            if (StringUtil.isNotNull(oldSyncSelection) && (!oldSyncSelection.equals(newSyncSelection) || !oldDingOrgId.equals(newDingOrgId))) {
                try {
                    DingOmsConfig omsconfig = new DingOmsConfig();
                    String oldLastUpdateTime = omsconfig.getLastUpdateTime();
                    omsconfig.setLastUpdateTime("");
                    if (UserOperHelper.allowLogOper("cleanTime", "*")) {
                        UserOperHelper.setModelNameAndModelDesc(null,
                                ResourceUtil.getString("third-ding:module.third.ding"));
                        UserOperContentHelper.putUpdate("").putSimple("lastUpdateTime", oldLastUpdateTime, "");
                    }
                    omsconfig.save();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("钉钉集成配置提交，同步选择修改，或者ekp中根机构修改，清空同步时间戳出现异常", e);

                }
            }
        }

        if ("true".equals(getDingEnabled())) {
            try {
                DingUtils.getDingApiService().getAccessToken(true);
                DingUtils.getDingApiService().getJsapiTicket(true);
                // DingUtils.getDingApiService().getAccessToken(true);
                // DingUtils.getDingApiService().getJsapiTicket(true);

                boolean ldingExist = new File(PluginConfigLocationsUtil.getKmssConfigPath() + "/third/lding").exists();
                if (ldingExist) {
                    BaseAppConfig ldingConfig = (BaseAppConfig) com.landray.kmss.util.ClassUtils.forName("com.landray.kmss.third.lding.model.LdingConfig").newInstance();
                    ldingConfig.getDataMap().put("ldingEnabled", "false");
                    ldingConfig.save();
                }

                // 通用待办模板构建 默认不创建了
                if (DingUtil.checkNotifyApiType("WF")) {
                    logger.warn("------旧待办接口创建模板-----");
                    DingNotifyProvider provider = (DingNotifyProvider) SpringBeanUtil
                            .getBean("dingNotifyProvider");
                    provider.resetTemplate();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }

        if("true".equals(this.getInteractiveCardEnable())){
            DingInteractivecardUtil.getCallbackRouteKey(true);
        }

        ThirdDingTalkRateLimitUtil
                .setRateLimitEnable(this.getDingEnableRateLimit(),
                        this.getDingEnableRateLimitCount());

        //更新钉钉同步速度
        IThirdDingClockService thirdDingClockService = (IThirdDingClockService)SpringBeanUtil.getBean("thirdDingClockService");
        thirdDingClockService.updateRate(this.getDingEnableRateLimitCount());
    }

    public DingConfig() throws Exception {
        super();
        // ==================以下为默认值===================

        // 是否开启钉钉集成
        if (StringUtil.isNull(getDingEnabled())) {
            setDingEnabled("false");
        }

        // 是否单点
        if (StringUtil.isNull(getDingOauth2Enabled())) {
            setDingOauth2Enabled("false");
        }

        // 是否推送消息
        if (StringUtil.isNull(getDingTodoEnabled())) {
            setDingTodoEnabled("false");
        } /*else {
			setDingLogDays("30");
		}*/

        // 是否推送待阅消息
        if (StringUtil.isNull(getDingTodotype2Enabled())) {
            setDingTodotype2Enabled("false");
        }
        /**
         * #86835【蓝钉管理台-修改】EKP使用钉钉集成组件与蓝钉对接
         */
//		if(StringUtil.isNull(getSyncSelection())){
//			if (StringUtil.isNotNull(getDingOmsOutEnabled()) || StringUtil.isNotNull(getDingOmsInEnabled()) || StringUtil.isNotNull(getLdingEnabled())) {
//				if ("true".equals(getDingOmsOutEnabled())){
//					setSyncSelection("1");
//				}else if("true".equals(getDingOmsInEnabled())){
//					setSyncSelection("2");
//				} else if ("true".equals(getLdingEnabled())) {
//					setSyncSelection("4");
//				}else {
//					setSyncSelection("0");
//				}
//			}else {
//				setSyncSelection("0");
//			}
//			
//		}


        if (StringUtil.isNull(getDingPcScanLoginEnabled())) {
            setDingPcScanLoginEnabled("false");
        }

        if (StringUtil.isNull(getDingMobileEnabled())) {
            setDingMobileEnabled("false");
        }

        if (StringUtil.isNull(getDingRangeEnabled())) {
            setDingRangeEnabled("false");
        }
        if (StringUtil.isNull(getDingHideRangeEnabled())) {
            setDingHideRangeEnabled("false");
        }
        if (StringUtil.isNull(getDingWorkRecordEnabled())) {
            setDingWorkRecordEnabled("false");
        }
        if (StringUtil.isNull(getDingTodotype1Enabled())) {
            setDingTodotype1Enabled("false");
        }
        if (StringUtil.isNull(getDingTodotype1Enabled())) {
            setDingTodotype1Enabled("false");
        }
        if (StringUtil.isNull(getDingFlowEnabled())) {
            setDingFlowEnabled("false");
        }
        if (StringUtil.isNull(getDingAttendEnabled())) {
            setDingAttendEnabled("false");
        }
        if (StringUtil.isNull(getDingScheduleEnabled())) {
            setDingScheduleEnabled("false");
        }
        if (StringUtil.isNull(getDingImeetingEnabled())) {
            setDingImeetingEnabled("false");
        }
        if (StringUtil.isNull(getDingTaskEnabled())) {
            setDingTaskEnabled("false");
        }
        if (StringUtil.isNull(getDingAppEnabled())) {
            setDingAppEnabled("false");
        }
        // if (StringUtil.isNull(getDingOmsCreateDeptGroup())) {
        // setDingOmsCreateDeptGroup("false");
        // }
        if (StringUtil.isNull(getDingOmsRootFlag())) {
            setDingOmsRootFlag("false");
        }
        if (StringUtil.isNull(getDingOmsExternal())) {
            setDingOmsExternal("false");
        }
        if (StringUtil.isNull(getNotifyClearDay())) {
            setNotifyClearDay("2019-06-01");
        }
        // 开发方式，默认是企业内部自主开发并兼容历史数据
        if (StringUtil.isNull(getDevModel())) {
            if (StringUtil.isNotNull(getDingCorpSecret())) {
                setDevModel("1");
            } else if (StringUtil.isNotNull(getAppKey()) && StringUtil.isNotNull(getAppSecret())) {
                setDevModel("2");
            } else if (StringUtil.isNotNull(getCustomKey()) && StringUtil.isNotNull(getCustomSecret())) {
                setDevModel("3");
            } else {
                setDevModel("1");
            }
        }

        if ("false".equals(getDingEnabled())) {
            setDingOauth2Enabled("false");
            setDingTodoEnabled("false");
            setDingTodotype1Enabled("false");
            setDingTodotype2Enabled("false");
            setDingWorkRecordEnabled("false");
            /**
             * 蓝钉管理台-修改】EKP使用钉钉集成组件与蓝钉对接
             * 0 - 不同步；   1-同步，从本系统同步至钉钉；——组织架构同步到钉钉的定时任务的开关； 2、同步，从钉钉同步至本系统；——后台从钉钉同步到本系统的定时任务的开关；
             * 3、不同步，仅从钉钉获取人员对应关系——后台更新人员对照表的定时任务的开关； 4、不同步，从蓝钉管理台获取人员对应关系——后台从蓝钉获取人员对照表的定时任务的开关；
             */
            setSyncSelection("0");
            setDingPcScanLoginEnabled("false");
        }

        if (StringUtil.isNull(getDingSize())) {
            setDingSize("2000");
        }
        if (StringUtil.isNull(getDingTodoPcOpenType())) {
            setDingTodoPcOpenType("out");
        }

        // 组织架构默认值
        // --ekp到钉钉
        if (StringUtil.isNull(getOrg2dingEmail())) {
            setOrg2dingEmail("fdEmail");
        }
        if (StringUtil.isNull(getOrg2dingOrgEmail())) {
            setOrg2dingOrgEmail("fdEmail");
        }

        if (StringUtil.isNull(getOrg2dingPosition())) {
            setOrg2dingPosition("hbmPosts");
        }
        if (StringUtil.isNull(getOrg2dingTel())) {
            setOrg2dingTel("fdWorkPhone");
        }
        if (StringUtil.isNull(getOrg2dingJobnumber())) {
            setOrg2dingJobnumber("fdNo");
        }
        if (StringUtil.isNull(getOrg2dingRemark())) {
            setOrg2dingRemark("fdMemo");
        }
        // --钉钉到ekp
        if (StringUtil.isNull(getDing2ekpName())) {
            setDing2ekpName("name");
        }
        if (StringUtil.isNull(getDing2ekpLoginName())) {
            setDing2ekpLoginName("mobile");
        }
        if (StringUtil.isNull(getDing2ekpFdNickName())) {
            setDing2ekpFdNickName("remark");
        }
        if (StringUtil.isNull(getDing2ekpFdNo())) {
            setDing2ekpFdNo("jobnumber");
        }
        if (StringUtil.isNull(getDing2ekpEmail())) {
            setDing2ekpEmail("email");
        }
        if (StringUtil.isNull(getDing2ekpTel())) {
            setDing2ekpTel("tel");
        }
        if (StringUtil.isNull(getDing2ekpOrder())) {
            setDing2ekpOrder("order");
        }
        if (StringUtil.isNull(getDing2ekpFdShortNo())) {
            setDing2ekpFdShortNo("tel");
        }
        if (StringUtil.isNull(getDing2ekpFdMemo())) {
            setDing2ekpFdMemo("remark");
        }
        if (StringUtil.isNull(getNotifyApiType())) {
            setNotifyApiType("TODO");
        }
    }

    // 是否开启钉钉集成
    public String getDingEnabled() {
        return getValue("dingEnabled");
    }

    public void setDingEnabled(String dingEnabled) {
        setValue("dingEnabled", dingEnabled);
    }

    // 钉钉企业ID
    public String getDingCorpid() {
        return trimValue(getValue("dingCorpid"));
    }

    public void setDingCorpid(String dingCorpid) {
        setValue("dingCorpid", dingCorpid);
    }

    // 钉钉企业Secret
    public String getDingCorpSecret() {
        return trimValue(getValue("dingCorpSecret"));
    }

    public void setDingCorpSecret(String dingCorpSecret) {
        setValue("dingCorpSecret", dingCorpSecret);
    }

    // 钉钉域名
    public String getDingDomain() {
        return trimValue(getValue("dingDomain"));
    }

    public void setDingDomain(String dingDomain) {
        setValue("dingDomain", dingDomain);
    }

    // 钉钉回调URL
    public String getDingCallbackurl() {
        return trimValue(getValue("dingCallbackurl"));
    }

    // 钉钉到ekp同步多余组织信息处理
    public String getDing2ekpOrgHandle() {
        return getValue("ding.ekpOrgHandle");
    }

    // 钉钉到ekp同步的的根机构id
    public String getDingOrgId2ekp() {
        return trimValue(getValue("dingOrgId2ekp"));
    }

    public void setDingCallbackurl(String dingCallbackurl) {
        setValue("dingCallbackurl", dingCallbackurl);
    }

    // 钉钉端已经注册过的URL回调URL
    public String getOldDingCallbackurl() {
        return trimValue(getValue("oldDingCallbackurl"));
    }

    public void setOldDingCallbackurl(String oldDingCallbackurl) {
        setValue("oldDingCallbackurl", oldDingCallbackurl);
    }

    // 回调异常信息
    public String getDingCallbackErro() {
        return getValue("dingCallbackErro");
    }

    public void setDingCallbackErro(String dingCallbackErro) {
        setValue("dingCallbackErro", dingCallbackErro);
    }

    // 钉钉Token
    public String getDingToken() {
        return trimValue(getValue("dingToken"));
    }

    public void setDingToken(String dingToken) {
        setValue("dingToken", dingToken);
    }

    // 钉钉AESKEY
    public String getDingAeskey() {
        return trimValue(getValue("dingAeskey"));
    }

    public void setDingAeskey(String dingAeskey) {
        setValue("dingAeskey", dingAeskey);
    }

    // 钉钉是否单点
    public String getDingOauth2Enabled() {
        return getValue("dingOauth2Enabled");
    }

    public void setDingOauth2Enabled(String dingOauth2Enabled) {
        setValue("dingOauth2Enabled", dingOauth2Enabled);
    }

    // 钉钉是否开启消息推送
    public String getDingTodoEnabled() {
        return getValue("dingTodoEnabled");
    }

    public void setDingTodoEnabled(String dingTodoEnabled) {
        setValue("dingTodoEnabled", dingTodoEnabled);
    }

    // 微应用ID
    public String getDingAgentid() {
        return trimValue(getValue("dingAgentid"));
    }

    public void setDingAgentid(String dingAgentid) {
        setValue("dingAgentid", dingAgentid);
    }

    // 消息标题颜色
    public String getDingTitleColor() {
        return trimValue(getValue("dingTitleColor"));
    }

    public void setDingTitleColor(String dingTitleColor) {
        setValue("dingTitleColor", dingTitleColor);
    }

    // 是否推送待阅
    public String getDingTodotype2Enabled() {
        return getValue("dingTodotype2Enabled");
    }

    public void setDingTodotype2Enabled(String dingTodotype2Enabled) {
        setValue("dingTodotype2Enabled", dingTodotype2Enabled);
    }

    // 是否推送待办
    public String getDingTodotype1Enabled() {
        return getValue("dingTodotype1Enabled");
    }

    public void setDingTodotype1Enabled(String dingTodotype1Enabled) {
        setValue("dingTodotype1Enabled", dingTodotype1Enabled);
    }

    // 钉钉套件异常通知人
    public String getAttendanceErrorNotifyOrgId() {
        return getValue("attendance.error.notify.OrgId");
    }

    public void setAttendanceErrorNotifyOrgId(String orgId) {
        setValue("attendance.error.notify.OrgId", orgId);
    }

    // 钉钉高级审批开关
    public String getAttendanceEnabled() {
        return getValue("attendanceEnabled");
    }

    public void setAttendanceEnabled(String orgId) {
        setValue("attendanceEnabled", orgId);
    }

    // 钉钉套件开关：dingSuitEnabled
    public String getDingSuitEnabled() {
        return getValue("dingSuitEnabled");
    }

    public void setDingSuitEnabled(String flag) {
        setValue("dingSuitEnabled", flag);
    }

    /**
     * 【蓝钉管理台-修改】EKP使用钉钉集成组件与蓝钉对接
     *
     * @return
     */
    public String getSyncSelection() {
        return getValue("syncSelection");
    }

    public void setSyncSelection(String syncSelection) {
        setValue("syncSelection", syncSelection);
    }

    // 是否开启组织架构接出（EKP到钉钉）
    public String getDingOmsOutEnabled() {
        return getValue("dingOmsOutEnabled");
    }

    public void setDingOmsOutEnabled(String dingOmsOutEnabled) {
        setValue("dingOmsOutEnabled", dingOmsOutEnabled);
    }

    // EKP中根机构ID
    public String getDingOrgId() {
        return trimValue(getValue("dingOrgId"));
    }

    public void setDingOrgId(String dingOrgId) {
        setValue("dingOrgId", dingOrgId);
    }

    // EKP中根机构名
    public String getDingOrgName() {
        return trimValue(getValue("dingOrgName"));
    }

    public void setDingOrgName(String dingOrgName) {
        setValue("dingOrgName", dingOrgName);
    }

    // 组织架构接入配置（钉钉到EKP）
    public String getDingOmsInEnabled() {
        return getValue("dingOmsInEnabled");
    }

    public void setDingOmsInEnabled(String dingOmsInEnabled) {
        setValue("dingOmsInEnabled", dingOmsInEnabled);
    }

    // 是否同步根机构到钉钉
    public String getDingOmsRootFlag() {
        return getValue("dingOmsRootFlag");
    }

    public void setDingOmsRootFlag(String dingOmsRootFlag) {
        setValue("dingOmsRootFlag", dingOmsRootFlag);
    }

    // 是否实时同步生态组织
    public String getDingOmsExternal() {
        return getValue("dingOmsExternal");
    }

    public void setDingOmsExternal(String dingOmsExternal) {
        setValue("dingOmsExternal", dingOmsExternal);
    }

    // 钉钉中根部门ID
    public String getDingDeptid() {
        return trimValue(getValue("dingDeptid"));
    }

    public void setDingDeptid(String dingDeptid) {
        setValue("dingDeptid", dingDeptid);
    }

    // 钉钉中是否创建一个关联此部门的企业群
    public String getDingOmsCreateDeptGroup() {
        return getValue("dingOmsCreateDeptGroup");
    }

    public void setDingOmsCreateDeptGroup(String dingOmsCreateDeptGroup) {
        setValue("dingOmsCreateDeptGroup", dingOmsCreateDeptGroup);
    }

    // 是否同步钉钉部门
    public String getDingOmsInDeptEnabled() {
        return getValue("dingOmsInDeptEnabled");
    }

    public void setDingOmsInDeptEnabled(String dingOmsInDeptEnabled) {
        setValue("dingOmsInDeptEnabled", dingOmsInDeptEnabled);
    }

    // 是否同步关联外部通讯录
    public String getAssociatedExternalEnabled() {
        return getValue("dingOmsInAssociatedExternalEnabled");
    }

    // 是否同步部门主管
    public String getDingOmsInDeptManagerEnabled() {
        return getValue("dingOmsInDeptManagerEnabled");
    }

    public void setDingOmsInDeptManagerEnabled(String dingOmsInDeptManagerEnabled) {
        setValue("dingOmsInDeptManagerEnabled", dingOmsInDeptManagerEnabled);
    }

    // 是否同步人员多部门
    public String getDingOmsInMoreDeptEnabled() {
        return getValue("dingOmsInMoreDeptEnabled");
    }

    public void setDingOmsInMoreDeptEnabled(String dingOmsInMoreDeptEnabled) {
        setValue("dingOmsInMoreDeptEnabled", dingOmsInMoreDeptEnabled);
    }

    // EKP中根机构ID
    public String getDingInOrgId() {
        return trimValue(getValue("dingInOrgId"));
    }

    public void setDingInOrgId(String dingInOrgId) {
        setValue("dingInOrgId", dingInOrgId);
    }

    // 钉钉代理服务器
    public String getDingProxy() {
        return getValue("dingProxy");
    }

    public void setDingProxy(String dingProxy) {
        setValue("dingProxy", dingProxy);
    }

    // 钉钉扫码登陆
    public String getDingPcScanLoginEnabled() {
        return getValue("dingPcScanLoginEnabled");
    }

    public void setDingPcScanLoginEnabled(String dingPcScanLoginEnabled) {
        setValue("dingPcScanLoginEnabled", dingPcScanLoginEnabled);
    }

    public String getDingPcScanappId() {
        return getValue("dingPcScanappId");
    }

    public void setDingPcScanappId(String pcScanappId) {
        setValue("dingPcScanappId", pcScanappId);
    }

    public String getDingPcScanappSecret() {
        return trimValue(getValue("dingPcScanappSecret"));
    }

    public void setDingPcScanappSecret(String appSecret) {
        setValue("dingPcScanappSecret", appSecret);
    }

    public String getDingMobileEnabled() {
        return getValue("dingMobileEnabled");
    }

    public void setDingMobileEnabled(String dingMobileEnabled) {
        setValue("dingMobileEnabled", dingMobileEnabled);
    }

    public String getUserLeaveListenerEnable() {
        return getValue("userLeaveListenerEnable");
    }

    public void setUserLeaveListenerEnable(String userLeaveListenerEnable) {
        setValue("userLeaveListenerEnable", userLeaveListenerEnable);
    }

    /**
     * @return 可选值为mobile|id|loginname,默认是loginname
     */
    public String getWxLoginName() {
        return trimValue(getValue("wxLoginName"));
    }

    public void setWxLoginName(String wxLoginName) {
        setValue("wxLoginName", wxLoginName);
    }

    public String getDingWorkPhoneEnabled() {
        return getValue("dingWorkPhoneEnabled");
    }

    public void setDingWorkPhoneEnabled(String dingWorkPhoneEnabled) {
        setValue("dingWorkPhoneEnabled", dingWorkPhoneEnabled);
    }

    public String getDingNoEnabled() {
        return getValue("dingNoEnabled");
    }

    public void setDingNoEnabled(String dingNoEnabled) {
        setValue("dingNoEnabled", dingNoEnabled);
    }

    @Override
    public String getJSPUrl() {
        return "/third/ding/ding_config.jsp";
    }

    //1默认倒序，0默认升序
    public String getDingPersonOrder() {
        return getValue("dingPersonOrder");
    }

    public void setDingPersonOrder(String dingPersonOrder) {
        setValue("dingPersonOrder", dingPersonOrder);
    }

    //是否同步职位
    public String getDingPostEnabled() {
        return getValue("dingPostEnabled");
    }

    public void setDingPostEnabled(String dingPostEnabled) {
        setValue("dingPostEnabled", dingPostEnabled);
    }

    // 是否组织查看范围
    public String getDingRangeEnabled() {
        return getValue("dingRangeEnabled");
    }

    public void setDingRangeEnabled(String dingRangeEnabled) {
        setValue("dingRangeEnabled", dingRangeEnabled);
    }

    // 是否组织隐藏可见性范围
    public String getDingHideRangeEnabled() {
        return getValue("dingHideRangeEnabled");
    }

    public void setDingHideRangeEnabled(String dingHideRangeEnabled) {
        setValue("dingHideRangeEnabled", dingHideRangeEnabled);
    }

    //是否同步一人多部门
    public String getDingPostMulDeptEnabled() {
        return getValue("dingPostMulDeptEnabled");
    }

    public void setDingPostMulDeptEnabled(String dingPostMulDeptEnabled) {
        setValue("dingPostMulDeptEnabled", dingPostMulDeptEnabled);
    }

    //是否同步部门领导
    public String getDingDeptLeaderEnabled() {
        return getValue("dingDeptLeaderEnabled");
    }

    public void setDingDeptLeaderEnabled(String dingDeptLeaderEnabled) {
        setValue("dingDeptLeaderEnabled", dingDeptLeaderEnabled);
    }

    //是否同步待办到钉钉的待办
    public String getDingWorkRecordEnabled() {
        return getValue("dingWorkRecordEnabled");
    }

    public void setDingWorkRecordEnabled(String dingWorkRecordEnabled) {
        setValue("dingWorkRecordEnabled", dingWorkRecordEnabled);
    }

    /**
     * ---------------------------------组织架构字段优化
     * start------------------------------
     **/
    /*
     * org2ding.name org2ding.name.synWay org2ding.mobile org2ding.userid
     * org2ding.department org2ding.orderInDepts org2ding.email
     * org2ding.position org2ding.tel org2ding.jobnumber org2ding.workPlace
     * org2ding.hiredDate org2ding.remark org2ding.orgEmail org2ding.isHide
     * org2ding.isHide.all org2ding.isSenior
     *
     * org2ding.dept.name org2ding.dept.parentDept org2ding.dept.id
     * org2ding.dept.order org2ding.dept.deptManager
     *
     * org2ding.dept.group.synWay org2ding.dept.group org2ding.dept.group.all
     */
    public String getOrg2dingName() {
        return getValue("org2ding.name");
    }

    public void setOrg2dingName(String org2dingName) {
        setValue("org2ding.name", org2dingName);
    }

    public String getOrg2dingNameSynWay() {
        return getValue("org2ding.name.synWay");
    }

    public void setOrg2dingNameSynWay(String org2dingNameSynWay) {
        setValue("org2ding.name.synWay", org2dingNameSynWay);
    }

    public String getOrg2dingMobile() {
        return getValue("org2ding.mobile");
    }

    public void setOrg2dingMobile(String org2dingMobile) {
        setValue("org2ding.mobile", org2dingMobile);
    }

    public String getOrg2dingMobileSynWay() {
        return getValue("org2ding.mobile.synWay");
    }

    public void setOrg2dingMobileSynWay(String org2dingMobileSynWay) {
        setValue("org2ding.mobile.synWay", org2dingMobileSynWay);
    }

    public String getOrg2dingUserid() {
        return getValue("org2ding.userid");
    }

    public void setOrg2dingUserid(String org2dingUserid) {
        setValue("org2ding.userid", org2dingUserid);
    }

    public String getOrg2dingUseridSynWay() {
        return getValue("org2ding.userid.synWay");
    }

    public void setOrg2dingUseridSynWay(String org2dingUseridSynWay) {
        setValue("org2ding.userid.synWay", org2dingUseridSynWay);
    }

    public String getOrg2dingDepartment() {
        return getValue("org2ding.department");
    }

    public void setOrg2dingDepartment(String org2dingDepartment) {
        setValue("org2ding.department", org2dingDepartment);
    }

    public String getOrg2dingDepartmentSynWay() {
        return getValue("org2ding.department.synWay");
    }

    public void setOrg2dingDepartmentSynWay(String org2dingDepartmentSynWay) {
        setValue("org2ding.department.synWay", org2dingDepartmentSynWay);
    }

    public String getOrg2dingOrderInDepts() {
        return getValue("org2ding.orderInDepts");
    }

    public void setOrg2dingOrderInDepts(String org2dingOrderInDepts) {
        setValue("org2ding.orderInDepts", org2dingOrderInDepts);
    }

    public String getOrg2dingOrderInDeptsSynWay() {
        return getValue("org2ding.orderInDepts.synWay");
    }

    public void
    setOrg2dingOrderInDeptsSynWay(String org2dingOrderInDeptsSynWay) {
        setValue("org2ding.orderInDepts.synWay", org2dingOrderInDeptsSynWay);
    }

    public String getOrg2dingPositionOrder() {
        return getValue("org2ding.position.order");
    }

    public void setOrg2dingPositionOrder(String org2dingPositionOrder) {
        setValue("org2ding.position.order", org2dingPositionOrder);
    }

    public String getOrg2dingEmail() {
        return getValue("org2ding.email");
    }

    public void setOrg2dingEmail(String org2dingEmail) {
        setValue("org2ding.email", org2dingEmail);
    }

    public String getOrg2dingEmailSynWay() {
        return getValue("org2ding.email.synWay");
    }

    public void setOrg2dingEmailSynWay(String org2dingEmailSynWay) {
        setValue("org2ding.email.synWay", org2dingEmailSynWay);
    }

    public String getOrg2dingPosition() {
        return getValue("org2ding.position");
    }

    public void setOrg2dingPosition(String org2dingPosition) {
        setValue("org2ding.position", org2dingPosition);
    }

    public String getOrg2dingPositionSynWay() {
        return getValue("org2ding.position.synWay");
    }

    public void setOrg2dingPositionSynWay(String org2dingPositionSynWay) {
        setValue("org2ding.position.synWay", org2dingPositionSynWay);
    }

    public String getOrg2dingTel() {
        return getValue("org2ding.tel");
    }

    public void setOrg2dingTel(String org2dingTel) {
        setValue("org2ding.tel", org2dingTel);
    }

    public String getOrg2dingTelSynWay() {
        return getValue("org2ding.tel.synWay");
    }

    public void setOrg2dingTelSynWay(String org2dingTelSynWay) {
        setValue("org2ding.tel.synWay", org2dingTelSynWay);
    }

    public String getOrg2dingJobnumber() {
        return getValue("org2ding.jobnumber");
    }

    public void setOrg2dingJobnumber(String org2dingJobnumber) {
        setValue("org2ding.jobnumber", org2dingJobnumber);
    }

    public String getOrg2dingJobnumberSynWay() {
        return getValue("org2ding.jobnumber.synWay");
    }

    public void setOrg2dingJobnumberSynWay(String org2dingJobnumberSynWay) {
        setValue("org2ding.jobnumber.synWay", org2dingJobnumberSynWay);
    }

    public String getOrg2dingWorkPlace() {
        return getValue("org2ding.workPlace");
    }

    public void setOrg2dingWorkPlace(String org2dingWorkPlace) {
        setValue("org2ding.workPlace", org2dingWorkPlace);
    }

    public String getOrg2dingWorkPlaceSynWay() {
        return getValue("org2ding.workPlace.synWay");
    }

    public void setOrg2dingWorkPlaceSynWay(String org2dingWorkPlaceSynWay) {
        setValue("org2ding.workPlace.synWay", org2dingWorkPlaceSynWay);
    }

    public String getOrg2dingHiredDate() {
        return getValue("org2ding.hiredDate");
    }

    public void setOrg2dingHiredDate(String org2dingHiredDate) {
        setValue("org2ding.hiredDate", org2dingHiredDate);
    }

    public String getOrg2dingHiredDateSynWay() {
        return getValue("org2ding.hiredDate.synWay");
    }

    public void setOrg2dingHiredDateSynWay(String org2dingHiredDateSynWay) {
        setValue("org2ding.hiredDate.synWay", org2dingHiredDateSynWay);
    }

    public String getOrg2dingRemark() {
        return getValue("org2ding.remark");
    }

    public void setOrg2dingRemark(String org2dingRemark) {
        setValue("org2ding.remark", org2dingRemark);
    }

    public String getOrg2dingRemarkSynWay() {
        return getValue("org2ding.remark.synWay");
    }

    public void setOrg2dingRemarkSynWay(String org2dingRemarkSynWay) {
        setValue("org2ding.remark.synWay", org2dingRemarkSynWay);
    }

    public String getOrg2dingOrgEmail() {
        return getValue("org2ding.orgEmail");
    }

    public void setOrg2dingOrgEmail(String org2dingOrgEmail) {
        setValue("org2ding.orgEmail", org2dingOrgEmail);
    }

    public String getOrg2dingOrgEmailSynWay() {
        return getValue("org2ding.orgEmail.synWay");
    }

    public void setOrg2dingOrgEmailSynWay(String org2dingOrgEmailSynWay) {
        setValue("org2ding.orgEmail.synWay", org2dingOrgEmailSynWay);
    }

    public String getOrg2dingIsHide() {
        return getValue("org2ding.isHide");
    }

    public void setOrg2dingIsHide(String org2dingIsHide) {
        setValue("org2ding.isHide", org2dingIsHide);
    }

    public String getOrg2dingIsHideSynWay() {
        return getValue("org2ding.isHide.synWay");
    }

    public String getOrg2dingIsHideAll() {
        return getValue("org2ding.isHide.all");
    }

    public void setOrg2dingIsHideSynWay(String org2dingIsHideSynWay) {
        setValue("org2ding.isHide.synWay", org2dingIsHideSynWay);
    }

    public String getOrg2dingIsSenior() {
        return getValue("org2ding.isSenior");
    }

    public void setOrg2dingIsSenior(String org2dingIsSenior) {
        setValue("org2ding.isSenior", org2dingIsSenior);
    }

    public String getOrg2dingIsSeniorSynWay() {
        return getValue("org2ding.isSenior.synWay");
    }

    public void setOrg2dingIsSeniorSynWay(String org2dingIsSeniorSynWay) {
        setValue("org2ding.isSenior.synWay", org2dingIsSeniorSynWay);
    }

    public String getOrg2dingDeptDeptName() {
        return getValue("org2ding.dept.name");
    }

    public void setOrg2dingDeptDeptName(String org2dingDeptName) {
        setValue("org2ding.dept.name", org2dingDeptName);
    }

    public String getOrg2dingDeptNameSynWay() {
        return getValue("org2ding.dept.name.synWay");
    }

    public void setOrg2dingDeptNameSynWay(String org2dingDeptNameSynWay) {
        setValue("org2ding.dept.name.synWay", org2dingDeptNameSynWay);
    }

    public String getOrg2dingDeptDeptParentDept() {
        return getValue("org2ding.dept.parentDept");
    }

    public void setOrg2dingDeptDeptParentDept(String org2dingDeptParentDept) {
        setValue("org2ding.dept.parentDept", org2dingDeptParentDept);
    }

    public String getOrg2dingDeptParentDeptSynWay() {
        return getValue("org2ding.dept.parentDept.synWay");
    }

    public void setOrg2dingDeptParentDeptSynWay(
            String org2dingDeptParentDeptSynWay) {
        setValue("org2ding.dept.parentDept.synWay",
                org2dingDeptParentDeptSynWay);
    }

    public String getOrg2dingDeptDeptId() {
        return getValue("org2ding.dept.id");
    }

    public void setOrg2dingDeptDeptId(String org2dingDeptId) {
        setValue("org2ding.dept.id", org2dingDeptId);
    }

    public String getOrg2dingDeptIdSynWay() {
        return getValue("org2ding.dept.id.synWay");
    }

    public void setOrg2dingDeptIdSynWay(String org2dingDeptIdSynWay) {
        setValue("org2ding.dept.id.synWay", org2dingDeptIdSynWay);
    }

    public String getOrg2dingDeptOrder() {
        return getValue("org2ding.dept.order");
    }

    public void setOrg2dingDeptOrder(String org2dingDeptOrder) {
        setValue("org2ding.dept.order", org2dingDeptOrder);
    }

    public String getOrg2dingDeptOrderSynWay() {
        return getValue("org2ding.dept.order.synWay");
    }

    public void setOrg2dingDeptOrderSynWay(String org2dingDeptOrderSynWay) {
        setValue("org2ding.dept.order.synWay", org2dingDeptOrderSynWay);
    }

    public String getOrg2dingDeptDeptManager() {
        return getValue("org2ding.dept.deptManager");
    }

    public void setOrg2dingDeptDeptDeptManager(String org2dingDeptDeptManager) {
        setValue("org2ding.dept.deptManager", org2dingDeptDeptManager);
    }

    public String getOrg2dingDeptDeptManagerSynWay() {
        return getValue("org2ding.dept.deptManager.synWay");
    }

    public void setOrg2dingDeptDeptManagerSynWay(
            String org2dingDeptDeptManagerSynWay) {
        setValue("org2ding.dept.deptManager.synWay",
                org2dingDeptDeptManagerSynWay);
    }

    public String getOrg2dingDeptGroup() {
        return getValue("org2ding.dept.group");
    }

    public String getOrg2dingDeptGroupAll() {
        return getValue("org2ding.dept.group.all");
    }

    public void setOrg2dingDeptGroup(String org2dingDeptGroup) {
        setValue("org2ding.dept.group", org2dingDeptGroup);
    }

    public String getOrg2dingDeptGroupSynWay() {
        return getValue("org2ding.dept.group.synWay");
    }

    public void setOrg2dingDeptGroupSynWay(String org2dingDeptGroupSynWay) {
        setValue("org2ding.dept.group.synWay", org2dingDeptGroupSynWay);
    }

    // 部门群包含子部门成员
    public String getOrg2dingDeptGroupContainSubDept() {
        return getValue("org2ding.dept.groupContainSubDept");
    }

    public String getOrg2dingDeptGroupContainSubDeptAll() {
        return getValue("org2ding.dept.groupContainSubDept.all");
    }

    public void setOrg2dingDeptGroupContainSubDept(String groupContainSubDept) {
        setValue("org2ding.dept.groupContainSubDept", groupContainSubDept);
    }

    public String getOrg2dingDeptGroupContainSubDeptSynWay() {
        return getValue("org2ding.dept.groupContainSubDept.synWay");
    }

    public void setOrg2dingDeptGroupContainSubDeptSynWay(
            String groupContainSubDeptWay) {
        setValue("org2ding.dept.groupContainSubDept.synWay",
                groupContainSubDeptWay);
    }

    // 钉钉到ekp
    public String getDing2ekpNameSynWay() {
        return getValue("ding2ekp.name.synWay");
    }

    public void setDing2ekpNameSynWay(String ding2ekpNameSynWay) {
        setValue("ding2ekp.name.synWay", ding2ekpNameSynWay);
    }

    public String getDing2ekpName() {
        return getValue("ding2ekp.name");
    }

    public void setDing2ekpName(String ding2ekpName) {
        setValue("ding2ekp.name", ding2ekpName);
    }

    public String getDing2ekpUseridSynWay() {
        return getValue("ding2ekp.userid.synWay");
    }

    public void setDing2ekpUseridSynWay(String ding2ekpUseridSynWay) {
        setValue("ding2ekp.userid.synWay", ding2ekpUseridSynWay);
    }

    public String getDing2ekpUserid() {
        return getValue("ding2ekp.userid");
    }

    public void setDing2ekpUserid(String ding2ekpUserid) {
        setValue("ding2ekp.userid", ding2ekpUserid);
    }

    public String getDing2ekpLoginNameSynWay() {
        return getValue("ding2ekp.loginName.synWay");
    }

    public void setDing2ekpLoginNameSynWay(String ding2ekpLoginNameSynWay) {
        setValue("ding2ekp.loginName.synWay", ding2ekpLoginNameSynWay);
    }

    public String getDing2ekpLoginName() {
        return getValue("ding2ekp.loginName");
    }

    public void setDing2ekpLoginName(String ding2ekpLoginName) {
        setValue("ding2ekp.loginName", ding2ekpLoginName);
    }

    public String getDing2ekpDepartmentSynWay() {
        return getValue("ding2ekp.department.synWay");
    }

    public void setDing2ekpDepartmentSynWay(String ding2ekpDepartmentSynWay) {
        setValue("ding2ekp.department.synWay", ding2ekpDepartmentSynWay);
    }

    public String getDing2ekpDepartment() {
        return getValue("ding2ekp.department");
    }

    public void setDing2ekpDepartment(String ding2ekpDepartment) {
        setValue("ding2ekp.department", ding2ekpDepartment);
    }

    public String getDing2ekpMobileSynWay() {
        return getValue("ding2ekp.mobile.synWay");
    }

    public void setDing2ekpMobileSynWay(String ding2ekpMobileSynWay) {
        setValue("ding2ekp.mobile.synWay", ding2ekpMobileSynWay);
    }

    public String getDing2ekpMobile() {
        return getValue("ding2ekp.mobile");
    }

    public void setDing2ekpMobile(String ding2ekpMobile) {
        setValue("ding2ekp.mobile", ding2ekpMobile);
    }

    public String getDing2ekpFdNickNameSynWay() {
        return getValue("ding2ekp.fdNickName.synWay");
    }

    public void setDing2ekpFdNickNameSynWay(String ding2ekpFdNickNameSynWay) {
        setValue("ding2ekp.fdNickName.synWay", ding2ekpFdNickNameSynWay);
    }

    public String getDing2ekpFdNickName() {
        return getValue("ding2ekp.fdNickName");
    }

    public void setDing2ekpFdNickName(String ding2ekpFdNickName) {
        setValue("ding2ekp.fdNickName", ding2ekpFdNickName);
    }

    public String getDing2ekpFdNoSynWay() {
        return getValue("ding2ekp.fdNo.synWay");
    }

    public void setDing2ekpFdNoSynWay(String ding2ekpFdNoSynWay) {
        setValue("ding2ekp.fdNo.synWay", ding2ekpFdNoSynWay);
    }

    public String getDing2ekpFdNo() {
        return getValue("ding2ekp.fdNo");
    }

    public void setDing2ekpFdNo(String ding2ekpFdNo) {
        setValue("ding2ekp.fdNo", ding2ekpFdNo);
    }

    public String getDing2ekpEmailSynWay() {
        return getValue("ding2ekp.email.synWay");
    }

    public void setDing2ekpEmailSynWay(String ding2ekpEmailSynWay) {
        setValue("ding2ekp.email.synWay", ding2ekpEmailSynWay);
    }

    public String getDing2ekpEmail() {
        return getValue("ding2ekp.email");
    }

    public void setDing2ekpEmail(String ding2ekpEmail) {
        setValue("ding2ekp.email", ding2ekpEmail);
    }

    public String getDing2ekpTelSynWay() {
        return getValue("ding2ekp.tel.synWay");
    }

    public void setDing2ekpTelSynWay(String ding2ekpTelSynWay) {
        setValue("ding2ekp.tel.synWay", ding2ekpTelSynWay);
    }

    public String getDing2ekpTel() {
        return getValue("ding2ekp.tel");
    }

    public void setDing2ekpTel(String ding2ekpTel) {
        setValue("ding2ekp.tel", ding2ekpTel);
    }

    public String getDing2ekpDefLangSynWay() {
        return getValue("ding2ekp.defLang.synWay");
    }

    public void setDing2ekpDefLangSynWay(String ding2ekpDefLangSynWay) {
        setValue("ding2ekp.defLang.synWay", ding2ekpDefLangSynWay);
    }

    public String getDing2ekpDefLang() {
        return getValue("ding2ekp.defLang");
    }

    public void setDing2ekpDefLang(String ding2ekpDefLang) {
        setValue("ding2ekp.defLang", ding2ekpDefLang);
    }

    public String getDing2ekpKeywordSynWay() {
        return getValue("ding2ekp.keyword.synWay");
    }

    public void setDing2ekpKeywordSynWay(String ding2ekpKeywordSynWay) {
        setValue("ding2ekp.keyword.synWay", ding2ekpKeywordSynWay);
    }

    public String getDing2ekpKeyword() {
        return getValue("ding2ekp.keyword");
    }

    public void setDing2ekpKeyword(String ding2ekpKeyword) {
        setValue("ding2ekp.keyword", ding2ekpKeyword);
    }

    public String getDing2ekpOrderSynWay() {
        return getValue("ding2ekp.order.synWay");
    }

    public void setDing2ekpOrderSynWay(String ding2ekpOrderSynWay) {
        setValue("ding2ekp.order.synWay", ding2ekpOrderSynWay);
    }

    public String getDing2ekpOrder() {
        return getValue("ding2ekp.order");
    }

    public void setDing2ekpOrder(String ding2ekpOrder) {
        setValue("ding2ekp.order", ding2ekpOrder);
    }

    public String getDing2ekpSexSynWay() {
        return getValue("ding2ekp.sex.synWay");
    }

    public void setDing2ekpSexSynWay(String ding2ekpSexSynWay) {
        setValue("ding2ekp.sex.synWay", ding2ekpSexSynWay);
    }

    public String getDing2ekpSex() {
        return getValue("ding2ekp.sex");
    }

    public void setDing2ekpSex(String ding2ekpSex) {
        setValue("ding2ekp.sex", ding2ekpSex);
    }

    public String getDing2ekpFdShortNoSynWay() {
        return getValue("ding2ekp.fdShortNo.synWay");
    }

    public void setDing2ekpFdShortNoSynWay(String ding2ekpFdShortNoSynWay) {
        setValue("ding2ekp.fdShortNo.synWay", ding2ekpFdShortNoSynWay);
    }

    public String getDing2ekpFdShortNo() {
        return getValue("ding2ekp.fdShortNo");
    }

    public void setDing2ekpFdShortNo(String ding2ekpFdShortNo) {
        setValue("ding2ekp.fdShortNo", ding2ekpFdShortNo);
    }

    public String getDing2ekpFdIsBusinessSynWay() {
        return getValue("ding2ekp.fdIsBusiness.synWay");
    }

    public void
    setDing2ekpFdIsBusinessSynWay(String ding2ekpFdIsBusinessSynWay) {
        setValue("ding2ekp.fdIsBusiness.synWay", ding2ekpFdIsBusinessSynWay);
    }

    public String getDing2ekpFdIsBusiness() {
        return getValue("ding2ekp.fdIsBusiness");
    }

    public void setDing2ekpFdIsBusiness(String ding2ekpFdIsBusiness) {
        setValue("ding2ekp.fdIsBusiness", ding2ekpFdIsBusiness);
    }

    public String getDing2ekpFdMemoSynWay() {
        return getValue("ding2ekp.fdMemo.synWay");
    }

    public void setDing2ekpFdMemoSynWay(String ding2ekpFdMemoSynWay) {
        setValue("ding2ekp.fdMemo.synWay", ding2ekpFdMemoSynWay);
    }

    public String getDing2ekpFdMemo() {
        return getValue("ding2ekp.fdMemo");
    }

    public void setDing2ekpFdMemo(String ding2ekpFdMemo) {
        setValue("ding2ekp.fdMemo", ding2ekpFdMemo);
    }

    public String getDing2ekpDeptNameSynWay() {
        return getValue("ding2ekp.dept.name.synWay");
    }

    public void setDing2ekpDeptNameSynWay(String ding2ekpDeptNameSynWay) {
        setValue("ding2ekp.dept.name.synWay", ding2ekpDeptNameSynWay);
    }

    public String getDing2ekpDeptName() {
        return getValue("ding2ekp.dept.name");
    }

    public void setDing2ekpDeptName(String ding2ekpDeptName) {
        setValue("ding2ekp.dept.name", ding2ekpDeptName);
    }

    public String getDing2ekpDeptParentDeptSynWay() {
        return getValue("ding2ekp.dept.parentDept.synWay");
    }

    public void setDing2ekpDeptParentDeptSynWay(
            String ding2ekpDeptParentDeptSynWay) {
        setValue("ding2ekp.dept.parentDept.synWay",
                ding2ekpDeptParentDeptSynWay);
    }

    public String getDing2ekpDeptParentDept() {
        return getValue("ding2ekp.dept.parentDept");
    }

    public void setDing2ekpDeptParentDept(String ding2ekpDeptParentDept) {
        setValue("ding2ekp.dept.parentDept", ding2ekpDeptParentDept);
    }

    public String getDing2ekpDeptOrderSynWay() {
        return getValue("ding2ekp.dept.order.synWay");
    }

    public void setDing2ekpDeptOrderSynWay(String ding2ekpDeptOrderSynWay) {
        setValue("ding2ekp.dept.order.synWay", ding2ekpDeptOrderSynWay);
    }

    public String getDing2ekpDeptOrder() {
        return getValue("ding2ekp.dept.order");
    }

    public void setDing2ekpDeptOrder(String ding2ekpDeptOrder) {
        setValue("ding2ekp.dept.order", ding2ekpDeptOrder);
    }

    public String getDing2ekpDeptLeaderSynWay() {
        return getValue("ding2ekp.dept.leader.synWay");
    }

    public void setDing2ekpDeptLeaderSynWay(String ding2ekpDeptLeaderSynWay) {
        setValue("ding2ekp.dept.leader.synWay", ding2ekpDeptLeaderSynWay);
    }

    public String getDing2ekpDeptLeader() {
        return getValue("ding2ekp.dept.leader");
    }

    public void setDing2ekpDeptLeader(String ding2ekpDeptLeader) {
        setValue("ding2ekp.dept.leader", ding2ekpDeptLeader);
    }

    /**
     * ---------------------------------组织架构字段优化
     * end------------------------------
     **/

    /**
     * ---------------------------------业务数据配置------------------------------
     **/

    //流程同步
    public String getDingFlowEnabled() {
        return getValue("dingFlowEnabled");
    }

    public void setDingFlowEnabled(String dingFlowEnabled) {
        setValue("dingFlowEnabled", dingFlowEnabled);
    }

    public String getDingFlowSynType() {
        return getValue("dingFlowSynType");
    }

    public void setDingFlowSynType(String dingFlowSynType) {
        setValue("dingFlowSynType", dingFlowSynType);
    }

    //考勤
    public String getDingAttendEnabled() {
        return getValue("dingAttendEnabled");
    }

    public void setDingAttendEnabled(String dingAttendEnabled) {
        setValue("dingAttendEnabled", dingAttendEnabled);
    }

    //日程
    public String getDingScheduleEnabled() {
        return getValue("dingScheduleEnabled");
    }

    public void setDingScheduleEnabled(String dingScheduleEnabled) {
        setValue("dingScheduleEnabled", dingScheduleEnabled);
    }

    //会议
    public String getDingImeetingEnabled() {
        return getValue("dingImeetingEnabled");
    }

    public void setDingImeetingEnabled(String dingImeetingEnabled) {
        setValue("dingImeetingEnabled", dingImeetingEnabled);
    }

    //任务
    public String getDingTaskEnabled() {
        return getValue("dingTaskEnabled");
    }

    public void setDingTaskEnabled(String dingTaskEnabled) {
        setValue("dingTaskEnabled", dingTaskEnabled);
    }

    //应用配置
    public String getDingAppEnabled() {
        return getValue("dingAppEnabled");
    }

    public void setDingAppEnabled(String dingAppEnabled) {
        setValue("dingAppEnabled", dingAppEnabled);
    }

    public String getDingSize() {
        return getValue("dingSize");
    }

    public void setDingSize(String dingSize) {
        setValue("dingSize", dingSize);
    }

    public String getAppKey() {
        return trimValue(getValue("appKey"));
    }

    public String getAppSecret() {
        return trimValue(getValue("appSecret"));
    }

    public void setAppKey(String appKey) {
        setValue("appKey", appKey);
    }

    public void setAppSecret(String appSecret) {
        setValue("appSecret", appSecret);
    }

    //待办日志清理期限
    public String getDingLogDays() {
        String days = getValue("dingLogDays");
        if (StringUtil.isNull(days)) {
            days = "30";
        }
        return days;
    }

    public int getDingLogDaysParseInt() {
        int rs_days = 30;
        try {
            String days = getValue("dingLogDays");
            if (StringUtil.isNull(days)) {
                days = "30";
            }
            rs_days = Integer.parseInt(days);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs_days;
    }

    public void setDingLogDays(String dingLogDays) {
        setValue("dingLogDays", dingLogDays);
    }

    @Override
    public String getModelDesc() {
        return ResourceUtil.getString("third-ding:module.third.ding");
    }

    public String getDevModel() {
        return getValue("devModel");
    }

    public void setDevModel(String devModel) {
        setValue("devModel", devModel);
    }

    public String getCustomKey() {
        return trimValue(getValue("customKey"));
    }

    public void setCustomKey(String customKey) {
        setValue("customKey", customKey);
    }

    public String getCustomSecret() {
        return trimValue(getValue("customSecret"));
    }

    public void setCustomSecret(String customSecret) {
        setValue("customSecret", customSecret);
    }

    public String getNotifyClearDay() {
        return trimValue(getValue("notifyClearDay"));
    }

    public void setNotifyClearDay(String notifyClearDay) {
        setValue("notifyClearDay", notifyClearDay);
    }

    public String getDingTodoSsoEnabled() {
        return getValue("dingTodoSsoEnabled");
    }

    public void setDingTodoSsoEnabled(String dingTodoSsoEnabled) {
        setValue("dingTodoSsoEnabled", dingTodoSsoEnabled);
    }

    // 设置回调提示 -11：删除回调成功 1：回调成功 2：回调失败 -1：删除回调失败 -2:钉钉端已注册了其他域名的回调事件。
    public void setDingCallback(String dingCallback) {
        setValue("dingCallback", dingCallback);
    }

    public void getDingCallback(String dingCallback) {
        getValue("dingCallback");
    }

    public String getLdingEnabled() {
        return getValue("ldingEnabled");
    }

    public void setLdingEnabled(String ldingEnabled) {
        setValue("ldingEnabled", ldingEnabled);
    }

    public String getLdingSynTime() {
        return getValue("ldingSynTime");
    }

    public void setLdingSynTime(String ldingSynTime) {
        setValue("ldingSynTime", ldingSynTime);
    }

    public String getLdingSynAddress() {
        return getValue("ldingSynAddress");
    }

    public void setLdingSynAddress(String ldingSynAddress) {
        setValue("ldingSynAddress", ldingSynAddress);
    }

    public String getLdingUser() {
        return trimValue(getValue("ldingUser"));
    }

    public void setLdingUser(String ldingUser) {
        setValue("ldingUser", ldingUser);
    }

    public String getLdingPwd() {
        return trimValue(getValue("ldingPwd"));
    }

    public void setLdingPwd(String ldingPwd) {
        setValue("ldingPwd", ldingPwd);
    }

    public String getDingTodoPcOpenType() {
        String dingTodoPcOpenType = getValue("dingTodoPcOpenType");
        if (StringUtil.isNull(dingTodoPcOpenType)) {
            return "out";
        }
        return dingTodoPcOpenType;
    }

    public void setDingTodoPcOpenType(String dingTodoPcOpenType) {
        setValue("dingTodoPcOpenType", dingTodoPcOpenType);
    }

    public String getNotifySynchroErrorWF() {
        logger.debug("默认清理旧待办");
        String handleOldRecord = getValue("notifySynchroErrorWF");
        logger.debug("---------------notifySynchroErrorWF:" + handleOldRecord);
        if (StringUtil.isNull(handleOldRecord)) {
            return "true";
        }
        return handleOldRecord;
    }

    public void setNotifySynchroErrorWF(String notifySynchroErrorWF) {
        setValue("notifySynchroErrorWF", notifySynchroErrorWF);
    }

    public String getDealWithAllErrNotify() {
        return getValue("dealWithAllErrNotify");
    }

    public void setDealWithAllErrNotify(String dealWithAllErrNotify) {
        setValue("dealWithAllErrNotify", dealWithAllErrNotify);
    }

    // 从sys_app_config获取的值进行去空处理
    public String trimValue(String value) {
        if (StringUtil.isNull(value)) {
            return value;
        }
        return value.trim();
    }

    // 默认新待办 旧接口：WF 新接口：WR  待办任务接口V2.0 TODO
    public String getNotifyApiType() {
        String apiType = getValue("notifyApiType");
        if (StringUtil.isNull(apiType)) {
            apiType = "TODO";
        }
        return apiType;
    }

    // 阿里郎模式
    public String getAttendanceDebug() {
        String debug = getValue("attendanceDebug");
        if (StringUtil.isNotNull(debug) && "true".equals(debug)) {
            logger.warn("----------开启阿里郎调试模式--------");
        }
        return debug;
    }

    public void setNotifyApiType(String notifyApiType) {
        setValue("notifyApiType", notifyApiType);
    }

    // 工作通知是否按模块推送 sendAsModel
    public String getSendAsModel() {
        return getValue("sendAsModel");
    }

    public void setSendAsModel(String sendAsModel) {
        setValue("sendAsModel", sendAsModel);
    }

    public String getCompatePostChange() {
        String compatePostChange = getValue("compatePostChange");
        if (StringUtil.isNull(compatePostChange)) {
            return "true";
        }
        return compatePostChange;
    }

    public void setCompatePostChange(String compatePostChange) {
        setValue("compatePostChange", compatePostChange);
    }

    public String getPersonInit() {
        String personInitFlag = getValue("personInitFlag");
        if (StringUtil.isNull(personInitFlag)) {
            return "flase";
        }
        return personInitFlag;
    }

    public void setPersonInit(String personInitFlag) {
        setValue("personInitFlag", personInitFlag);
    }

    public String getCalendarApiVersion() {
        String calendarApiVersion = getValue("calendarApiVersion");
        if (StringUtil.isNull(calendarApiVersion)) {
            return "V1";
        }
        return calendarApiVersion;
    }

    public void setCalendarApiVersion(String calendarApiVersion) {
        setValue("calendarApiVersion", calendarApiVersion);
    }

    public String getDingmngSSOSecret() {
        return getValue("dingmngSSOSecret");
    }

    public void setDingmngSSOSecret(String dingmngSSOSecret) {
        setValue("dingmngSSOSecret", dingmngSSOSecret);
    }

    // 钉盘集成开关
    public String getCspaceEnable() {
        return getValue("cspaceEnable");
    }

    public void setCspaceEnable(String cspaceEnable) {
        setValue("cspaceEnable", cspaceEnable);
    }

    // Media有效时间
    public String getCspaceTime() {
        return getValue("cspaceTime");
    }

    public void setCspaceTime(String cspaceTime) {
        setValue("cspaceTime", cspaceTime);
    }

    // 是否同步管理员
    public String getDingAdminSynEnabled() {
        return getValue("dingAdminSynEnabled");
    }

    public void setDingAdminSynEnabled(String dingAdminSynEnabled) {
        setValue("dingAdminSynEnabled", dingAdminSynEnabled);
    }

    // 管理员群组fdId
    public String getDingAdminGroupId() {
        return getValue("dingAdminGroupId");
    }

    public void setDingAdminGroupId(String dingAdminGroupId) {
        setValue("dingAdminGroupId", dingAdminGroupId);
    }

    // 高级审批管理门户地址
    public String getDingPortalUrl() {
        return getValue("dingPortalUrl");
    }

    public void setDingPortalUrl(String dingPortalUrl) {
        setValue("dingPortalUrl", dingPortalUrl);
    }

    // 同步钉钉角色

    /**
     * 岗位同步
     *
     * @return
     */
    public String getDing2ekpRolePostSynWay() {
        return getValue("ding2ekp.role.post.synWay");
    }

    public void setDing2ekpRolePostSynWay(String ding2ekpRolePostSynWay) {
        setValue("ding2ekp.role.post.synWay", ding2ekpRolePostSynWay);
    }

    public String getDing2ekpRolePost() {
        return getValue("ding2ekp.role.post");
    }

    public void setDing2ekpRolePost(String ding2ekpRolePost) {
        setValue("ding2ekp.role.post", ding2ekpRolePost);
    }

    public String getDing2ekpRoleGroupSynWay() {
        return getValue("ding2ekp.role.group.synWay");
    }

    public void setDing2ekpRoleGroupSynWay(String ding2ekpRoleGroupSynWay) {
        setValue("ding2ekp.role.group.synWay", ding2ekpRoleGroupSynWay);
    }

    public String getDing2ekpRoleGroup() {
        return getValue("ding2ekp.role.group");
    }

    public void setDing2ekpRoleGroup(String ding2ekpRoleGroup) {
        setValue("ding2ekp.role.group", ding2ekpRoleGroup);
    }

    public String getDing2ekpRoleStaffingSynWay() {
        return getValue("ding2ekp.role.staffing.synWay");
    }

    public void
    setDing2ekpRoleStaffingSynWay(String ding2ekpRoleStaffingSynWay) {
        setValue("ding2ekp.role.staffing.synWay", ding2ekpRoleStaffingSynWay);
    }

    public String getDing2ekpRoleStaffing() {
        return getValue("ding2ekp.role.staffing");
    }

    public void setDing2ekpRoleStaffing(String ding2ekpRoleStaffing) {
        setValue("ding2ekp.role.staffing", ding2ekpRoleStaffing);
    }

    public String getDing2ekpRoleRoleline() {
        return getValue("ding2ekp.role.roleline");
    }

    public void setDing2ekpRoleRoleline(String ding2ekpRoleRoleline) {
        setValue("ding2ekp.role.roleline", ding2ekpRoleRoleline);
    }

    public String getDing2ekpRoleRolelineSynWay() {
        return getValue("ding2ekp.role.roleline.synWay");
    }

    public void
    setDing2ekpRoleRolelineSynWay(String ding2ekpRoleRolelineSynWay) {
        setValue("ding2ekp.role.roleline.synWay", ding2ekpRoleRolelineSynWay);
    }

    public String getUpdateMessageStatus() {
        return getValue("notify.updateMessageStatus");
    }

    public void setUpdateMessageStatus(String updateMessageStatus) {
        setValue("notify.updateMessageStatus", updateMessageStatus);
    }

    public String getNotifyIsOnlyShowExecutor() {
        return getValue("notify.isOnlyShowExecutor");
    }

    public void setNotifyIsOnlyShowExecutor(String notifyIsOnlyShowExecutor) {
        setValue("notify.updateMessageStatus", notifyIsOnlyShowExecutor);
    }

    public String getDingEnableRateLimit() {
        return getValue("dingEnableRateLimit");
    }

    public void setDingEnableRateLimit(String dingEnableRateLimit) {
        setValue("dingEnableRateLimit", dingEnableRateLimit);
    }

    public String getDingEnableRateLimitCount() {
        return getValue("dingEnableRateLimitCount");
    }

    public void setDingEnableRateLimitCount(String dingEnableRateLimitCount) {
        setValue("dingEnableRateLimitCount", dingEnableRateLimitCount);
    }

	//-------------专属账号相关配置start -------------------
	public String getExclusiveAccountEnable() {
		return getValue("exclusiveAccountEnable");
	}
	public void setExclusiveAccountEnable(String exclusiveAccountEnable) {
		setValue("exclusiveAccountEnable", exclusiveAccountEnable);
	}

	public String getOrg2dingIsExclusiveAccountSynWay() {
		return getValue("org2ding.isExclusiveAccount.synWay");
	}
	public void setOrg2dingIsExclusiveAccountSynWay(String isExclusiveAccountSynWay) {
		setValue("org2ding.isExclusiveAccount.synWay", isExclusiveAccountSynWay);
	}
	public String getOrg2dingIsExclusiveAccount() {
		return getValue("org2ding.isExclusiveAccount");
	}
	public void setOrg2dingIsExclusiveAccount(String isExclusiveAccount) {
		setValue("org2ding.isExclusiveAccount", isExclusiveAccount);
	}
	public String getOrg2dingIsExclusiveAccountAll() {
		return getValue("org2ding.isExclusiveAccount.all");
	}
	public void setOrg2dingIsExclusiveAccountAll(String isExclusiveAccountAll) {
		setValue("org2ding.isExclusiveAccount.all", isExclusiveAccountAll);
	}

	public String getOrg2dingIsExclusiveAccountTypeSynWay() {
		return getValue("org2ding.exclusiveAccount.type.synWay");
	}
	public void setOrg2dingIsExclusiveAccountTypeSynWay(String isExclusiveTypeSynWay) {
		setValue("org2ding.exclusiveAccount.type.synWay", isExclusiveTypeSynWay);
	}
	public String getOrg2dingIsExclusiveAccountType() {
		return getValue("org2ding.exclusiveAccount.type");
	}
	public void setOrg2dingIsExclusiveAccountType(String isExclusiveAccountType) {
		setValue("org2ding.exclusiveAccount.type", isExclusiveAccountType);
	}

	public String getOrg2dingIsExclusiveAccountLoginNameSynWay() {
		return getValue("org2ding.exclusiveAccount.loginName.synWay");
	}
	public void setOrg2dingIsExclusiveAccountLoginNameSynWay(String isExclusiveLoginNameSynWay) {
		setValue("org2ding.exclusiveAccount.loginName.synWay", isExclusiveLoginNameSynWay);
	}
	public String getOrg2dingIsExclusiveAccountLoginName() {
		return getValue("org2ding.exclusiveAccount.loginName");
	}
	public void setOrg2dingIsExclusiveAccountLoginName(String isExclusiveAccountLoginName) {
		setValue("org2ding.exclusiveAccount.loginName", isExclusiveAccountLoginName);
	}

	public String getOrg2dingIsExclusiveAccountPasswordSynWay() {
		return getValue("org2ding.exclusiveAccount.password.synWay");
	}
	public void setOrg2dingIsExclusiveAccountPasswordSynWay(String isExclusivePasswordSynWay) {
		setValue("org2ding.exclusiveAccount.password.synWay", isExclusivePasswordSynWay);
	}
	public String getOrg2dingIsExclusiveAccountPassword() {
		return getValue("org2ding.exclusiveAccount.password");
	}
	public void setOrg2dingIsExclusiveAccountPassword(String isExclusiveAccountPassword) {
		setValue("org2ding.exclusiveAccount.password", isExclusiveAccountPassword);
	}
	//-------------专属账号相关配置end -------------------

    //互动卡片
    public String getInteractiveCardEnable() {
        return getValue("interactiveCardEnable");
    }
    public void setInteractiveCardEnable(String interactiveCardEnable) {
        setValue("interactiveCardEnable", interactiveCardEnable);
    }
    public String getRobotAgentId() {
        return getValue("robotAgentId");
    }
    public void setRobotAgentId(String robotAgentId) {
        setValue("interactiveCardEnable", robotAgentId);
    }
    public String getRobotAppKey() {
        return getValue("robotAppKey");
    }
    public void setRobotAppKey(String robotAppKey) {
        setValue("robotAppKey", robotAppKey);
    }
    public String getRobotAppSecret() {
        return getValue("robotAppSecret");
    }
    public void setRobotAppSecret(String robotAppSecret) {
        setValue("robotAppSecret", robotAppSecret);
    }


    // 自定义明细查询
    public static List<DingConfigCustom> getCustomData() {
        List<DingConfigCustom> list = new ArrayList<DingConfigCustom>();
        HQLInfo hql = new HQLInfo();
        hql.setWhereBlock("fdField like :fdField and fdKey = :fdKey");
        hql.setParameter("fdKey","com.landray.kmss.third.ding.model.DingConfig");
        hql.setParameter("fdField", "org2ding.custom.%");

        ISysAppConfigService service = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
        HashMap<Integer, DingConfigCustom> map = new HashMap<Integer, DingConfigCustom>();

        try {
            List<SysAppConfig> allCustom = service.findList(hql);
            for (SysAppConfig item : allCustom) {
                String field = item.getFdField();
                String _index = field.substring(field.indexOf("[") + 1,
                        field.indexOf("]"));
                Integer index = Integer.valueOf(_index);
                DingConfigCustom custom = null;
                if (map.containsKey(index)) {
                    custom = map.get(index);
                } else {
                    custom = new DingConfigCustom();
                }
                if (field.contains("title")) {
                    custom.setTitle(item.getFdValue());
                } else if (field.contains("synWay")) {
                    custom.setSynWay(item.getFdValue());
                } else if (field.contains("target")) {
                    custom.setTarget(item.getFdValue());
                }
                map.put(index, custom);
            }
            for (Integer key : map.keySet()) {
                list.add(map.get(key));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }
}
