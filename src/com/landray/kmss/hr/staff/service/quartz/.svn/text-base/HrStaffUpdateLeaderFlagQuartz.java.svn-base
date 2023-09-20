package com.landray.kmss.hr.staff.service.quartz;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.List;

/**
 * 定时更新部门负责人领导人标识
 * @author liuyang
 */
public class HrStaffUpdateLeaderFlagQuartz {
    private final String[] s = {"一","二","三"};

    private final String[] p = {"COO","CCO","CHO","CFO","董事长"};

    private ISysOrgElementService sysOrgElementService;

    private ISysOrgElementService getSysOrgElementService(){
        if(sysOrgElementService == null){
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    private IHrStaffPersonInfoService getHrStaffPersonInfoService(){
        if(hrStaffPersonInfoService == null){
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
        }
        return hrStaffPersonInfoService;
    }

    private ISysOrgCoreService sysOrgCoreService;

    private ISysOrgCoreService getSysOrgCoreService(){
        if(sysOrgCoreService == null){
            sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
        }
        return sysOrgCoreService;
    }

    /**
     * 更新部门负责人的负责人标识
     * @param context
     * @throws Exception
     */
    public void updateDeptLeaderFlag(SysQuartzJobContext context) throws Exception {
        int total = 0;
        int success = 0;
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdOrgType=2 and fdIsAvailable=true");
        List<SysOrgElement> orgElementList = getSysOrgElementService().findList(info);
        for (SysOrgElement sysOrgElement : orgElementList) {
            int length = sysOrgElement.getFdHierarchyId().split("x").length;
            if (length <= 4) {
                total++;
                SysOrgElement fdLeader = sysOrgElement.getHbmThisLeader();
                if (fdLeader != null) {
                    HrStaffPersonInfo hrStaffPersonInfo = getHrStaffPersonInfoService().findByOrgPersonId(fdLeader.getFdId());
                    if (hrStaffPersonInfo != null) {
                        hrStaffPersonInfo.setFdPrincipalIdentification(s[length - 3] + "级部门负责人");
                        getHrStaffPersonInfoService().update(hrStaffPersonInfo);
                        success++;
                    } else {
                        context.logMessage("人员id:" + fdLeader.getFdId() + ",名称：" + fdLeader.getFdName() + "未找到对应的人事档案信息");
                    }
                } else {
                    context.logMessage("部门id:" + sysOrgElement.getFdId() + ",名称：" + sysOrgElement.getFdName() + "未找到部门负责人");
                }
            }
        }
        context.logMessage("总部门数量：" + total + ",更新成功：" + success);
    }

    /**
     * 更新岗位负责人的负责人标识
     * @param context
     * @throws Exception
     */
    public void updatePostLeaderFlag(SysQuartzJobContext context) throws Exception {
        int total = 0;
        int success = 0;
        List<String> posts = ArrayUtil.asList(p);
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdOrgType=4 and fdIsAvailable=true and " + HQLUtil.buildLogicIN("fdName", posts));
        List<SysOrgElement> sysOrgElementList = getSysOrgElementService().findList(info);
        if(!ArrayUtil.isEmpty(sysOrgElementList)){
            List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(sysOrgElementList);
            for(SysOrgPerson person : personList){
                total++;
                HrStaffPersonInfo hrStaffPersonInfo = getHrStaffPersonInfoService().findByOrgPersonId(person.getFdId());
                if(hrStaffPersonInfo != null){
                    String principal = getLeaderFlag(person, posts);
                    hrStaffPersonInfo.setFdPrincipalIdentification(principal);
                    getHrStaffPersonInfoService().update(hrStaffPersonInfo);
                    context.logMessage("人员id:" + person.getFdId() + ",名称：" + person.getFdName() + "正在修改负责人标识");
                    success++;
                }else{
                    context.logMessage("人员id:" + person.getFdId() + ",名称：" + person.getFdName() + "未找到对应的人事档案信息");
                }
            }
            context.logMessage("总人数：" + total + ",更新成功人数：" + success);
        }
    }

    /**
     * 根据人员，匹配岗位
     * @param person
     * @param name
     * @return
     * @throws Exception
     */
    private String getLeaderFlag(SysOrgPerson person,List<String> name) throws Exception {
        String postName = "";
        List<SysOrgPost> postList = person.getFdPosts();
        for(SysOrgPost post : postList){
            if(name.contains(post.getFdName())){
                postName = post.getFdName();
                break;
            }
        }
        return postName;
    }
}
