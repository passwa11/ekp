package com.landray.kmss.hr.organization.oms;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.constant.HrStaffConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;

import java.util.*;

/**
 * EKP同步到HR的公共类
 * @author wj
 * @version 3.0 2021-10-12 王京
 */
public class SynchroOrgCommon implements SysOrgConstant {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrgCommon.class);



    private IHrOrganizationElementService hrOrganizationElementService;
    private IHrOrganizationElementService getHrOrganizationElementService(){
        if(hrOrganizationElementService ==null){
            hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil.getBean("hrOrganizationElementService");
        }
        return hrOrganizationElementService;
    }
    private IHrOrganizationPostService hrOrganizationPostService;

    private IHrOrganizationPostService getHrOrganizationPostService(){
        if(hrOrganizationPostService ==null){
            hrOrganizationPostService = (IHrOrganizationPostService) SpringBeanUtil.getBean("hrOrganizationPostService");
        }
        return hrOrganizationPostService;
    }
    private IHrStaffTrackRecordService hrStaffTrackRecordService;

    private IHrStaffTrackRecordService getHrStaffTrackRecordService(){
        if(hrStaffTrackRecordService ==null){
            hrStaffTrackRecordService = (IHrStaffTrackRecordService) SpringBeanUtil.getBean("hrStaffTrackRecordService");
        }
        return hrStaffTrackRecordService;
    }
    /**
     * <p>
     * 复制EKP组织架构到人事组织架构
     * </p>
     *
     * @param hrOrganizationElement
     * @param element
     * @throws Exception
     * @author sunj
     */
    protected void copyEkpOrgToHrOrg(HrOrganizationElement hrOrganizationElement, SysOrgElement element)
            throws Exception {
        if (!element.getFdId().equals(hrOrganizationElement.getFdId())) {
            hrOrganizationElement.setFdId(element.getFdId());
        }
        hrOrganizationElement.setFdName(element.getFdName());
        hrOrganizationElement.setFdNamePinYin(element.getFdNamePinYin());
        hrOrganizationElement.setFdNameSimplePinyin(element.getFdNameSimplePinyin());
        hrOrganizationElement.setFdOrder(element.getFdOrder());
        hrOrganizationElement.setFdNo(element.getFdNo());
        hrOrganizationElement.setFdKeyword(element.getFdKeyword());
        hrOrganizationElement.setFdIsAvailable(element.getFdIsAvailable());
        hrOrganizationElement.setFdIsAbandon(element.getFdIsAbandon());
        hrOrganizationElement.setFdIsBusiness(element.getFdIsBusiness());
        // 来源
        hrOrganizationElement.setFdSource("EKP");
        hrOrganizationElement.setFdMemo(element.getFdMemo());
        hrOrganizationElement.setFdCreateTime(element.getFdCreateTime());
        hrOrganizationElement.setFdHierarchyId(element.getFdHierarchyId());
        //人员信息的处理
        if (element.getFdOrgType().equals(ORG_TYPE_PERSON)) {
            SysOrgPerson orgPerson = (SysOrgPerson) element;
            HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrOrganizationElement;
            personInfo.setFdMobileNo(orgPerson.getFdMobileNo());
            personInfo.setFdEmail(orgPerson.getFdEmail());
            personInfo.setFdLoginName(orgPerson.getFdLoginName());
            personInfo.setFdSex(orgPerson.getFdSex());
            personInfo.setFdOrgPerson(orgPerson);
            //是否登录系统
            personInfo.setFdCanLogin(orgPerson.getFdCanLogin());
            if (element.getFdIsAvailable()) {
                if (EnumUtils.isValidEnum(HrStaffConstant.AllStaffStatus.class, personInfo.getFdStatus())) {
                    personInfo.setFdStatus(personInfo.getFdStatus());
                }
            } else {
                //#138524组织架构无效人员同步至hr时，先判断人员在hr是否为离职、退休、解聘三种状态，如果都不是再置为离职状态 by xub
                if(!"leave".equals(personInfo.getFdStatus()) && !"dismissal".equals(personInfo.getFdStatus()) && !"retire".equals(personInfo.getFdStatus())) {
                    personInfo.setFdStatus("leave");
                }
            }
            //设置人员岗位
            setPersonPost(element,hrOrganizationElement);
        }
        // 最后更新时间
        hrOrganizationElement.setFdAlterTime(element.getFdAlterTime());
    }



    /**
     * 设置领导
     * @param hrOrganizationElement
     * @param element
     * @throws Exception
     */
    protected void setHierarchy(HrOrganizationElement hrOrganizationElement, SysOrgElement element)
            throws Exception {
        if (null != element.getFdParent()) {
            if (getHrOrganizationElementService().getBaseDao().isExist(HrOrganizationElement.class.getName(), element.getFdParent().getFdId())) {
                HrOrganizationElement fdParent = (HrOrganizationElement) getHrOrganizationElementService().findByPrimaryKey(element.getFdParent().getFdId(), null, true);
                hrOrganizationElement.setFdParent(fdParent);
            }
        } else {
            hrOrganizationElement.setFdParent(null);
        }
        if (null != element.getHbmParentOrg()) {
            if (getHrOrganizationElementService().getBaseDao().isExist(HrOrganizationElement.class.getName(), element.getHbmParentOrg().getFdId())) {
                hrOrganizationElement.setHbmParentOrg((HrOrganizationElement) getHrOrganizationElementService().findByPrimaryKey(element.getHbmParentOrg().getFdId(), null, true));
            }
        } else {
            hrOrganizationElement.setHbmParentOrg(null);
        }
        if (null != element.getHbmThisLeader()) {
            if (getHrOrganizationElementService().getBaseDao().isExist(HrOrganizationElement.class.getName(), element.getHbmThisLeader().getFdId())) {
                // 本级领导
                hrOrganizationElement.setHbmThisLeader((HrOrganizationElement) getHrOrganizationElementService().findByPrimaryKey(element.getHbmThisLeader().getFdId(), null, true));
            }
        } else {
            hrOrganizationElement.setHbmThisLeader(null);
        }
        if (null != element.getHbmSuperLeader()) {
            if (getHrOrganizationElementService().getBaseDao().isExist(HrOrganizationElement.class.getName(), element.getHbmSuperLeader().getFdId())) {
                // 上级领导
                HrOrganizationElement elementLeader = (HrOrganizationElement) getHrOrganizationElementService().findByPrimaryKey(element.getHbmSuperLeader().getFdId(), null, true);
                if (null != elementLeader) {
                    hrOrganizationElement.setHbmSuperLeader(elementLeader);
                    hrOrganizationElement.setFdBranLeader(elementLeader);
                }
            }
        } else {
            hrOrganizationElement.setHbmSuperLeader(null);
            hrOrganizationElement.setFdBranLeader(null);
        }
        copyEkpOrgToHrOrg(hrOrganizationElement, element);
    }


    /**
     * 设置人员岗位信息到HR相关表
     * @param sysOrgElement
     * @param hrOrganizationElement
     */
    private void  setPersonPost(SysOrgElement sysOrgElement,HrOrganizationElement hrOrganizationElement) {
        List<HrOrganizationElement> rtnVal = new ArrayList();
        try {
            List<String> postIds = new ArrayList();
            if (CollectionUtils.isNotEmpty(sysOrgElement.getFdPosts())) {
                List<String> mainPostIds = new ArrayList();
                // 当前人员所属岗位
                for (Object post : sysOrgElement.getFdPosts()) {
                    if (post != null && post instanceof SysOrgPost) {
                        SysOrgPost postInfo = (SysOrgPost) post;
                        mainPostIds.add(postInfo.getFdId());
                    }
                }
                if(CollectionUtils.isNotEmpty(mainPostIds)) {
                    postIds.addAll(mainPostIds);
                }
            }
            Map<String, Boolean> setOrgElement = new HashMap<String, Boolean>();
            if (CollectionUtils.isNotEmpty(postIds)) {
                //人员的上级组织 部门
                HrOrganizationElement fdParent = hrOrganizationElement.getFdParent();
                //根据岗位ID查询岗位是否在HR这边同步了
                int i=0;
                for (String postId : postIds) {
                    String fdType = "2";
                    HrOrganizationPost post = (HrOrganizationPost) getHrOrganizationPostService().findByPrimaryKey(postId, null, true);
                    if (null != post) {
                        //同一个岗位执行一次
                        if (setOrgElement.get(post.getFdId()) == null) {
                            if(i==0) {
                                //第一个岗位用于HR的主岗
                                List fdPosts = new ArrayList();
                                fdPosts.add(post);
                                hrOrganizationElement.setFdPosts(fdPosts);
                                //只取一个岗位作为主岗
                                hrOrganizationElement.setFdOrgPosts(fdPosts);
                                fdType = "1";
                            }
                            //任职记录表信息插入 因为HR中任职记录表的岗位目前是单选 所以这里只使用单个岗位
                            List<SysOrgPost> tempPost = new ArrayList();
                            tempPost.add((SysOrgPost) sysOrgElement.getFdPosts().get(i));

                            SysOrgPerson orgPerson = (SysOrgPerson) sysOrgElement;
                            if (getHrStaffTrackRecordService().checkUnique(null, hrOrganizationElement.getFdId(),
                                    null == fdParent ? null : fdParent.getFdId(), post.getFdId(),
                                    orgPerson.getFdStaffingLevel() == null ? null : orgPerson.getFdStaffingLevel().getFdId(), fdType)) {
                                HrStaffTrackRecord trackRecord = new HrStaffTrackRecord();
                                trackRecord.setFdPersonInfo((HrStaffPersonInfo) hrOrganizationElement);
                                trackRecord.setFdEntranceBeginDate(new Date());
                                trackRecord.setFdHrOrgDept(fdParent);
                                //任职岗位 HR表
                                trackRecord.setFdHrOrgPost(post);
                                //任职部门
                                trackRecord.setFdRatifyDept(sysOrgElement.getFdParent());
                                //任职岗位 SYS_ORG表
                                trackRecord.setFdOrgPosts(tempPost);
                                trackRecord.setFdStaffingLevel(orgPerson.getFdStaffingLevel());
                                trackRecord.setFdStatus("1");
                                trackRecord.setFdType(fdType);
                                getHrStaffTrackRecordService().add(trackRecord);
                            }
                            i++;
                        }
                        setOrgElement.put(post.getFdId(), true);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("查询人员岗位出错：" + e);
        }
    }
}
