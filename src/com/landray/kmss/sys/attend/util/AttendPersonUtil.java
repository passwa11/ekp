package com.landray.kmss.sys.attend.util;

import com.google.api.client.util.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.organization.dao.ISysOrgElementDao;
import com.landray.kmss.sys.organization.dao.ISysOrgGroupDao;
import com.landray.kmss.sys.organization.dao.ISysOrgPersonDao;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 考勤对象展开到人的重写类
 * @author 王京
 * @version 1.0 2022-06-14
 */
public class AttendPersonUtil implements SysOrgConstant {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AttendPersonUtil.class);

    private static ISysAttendOrgService sysAttendOrgService;

    public static ISysAttendOrgService getSysAttendOrgService() {
        if (sysAttendOrgService == null) {
            sysAttendOrgService = (ISysAttendOrgService) SpringBeanUtil.getBean("sysAttendOrgService");
        }
        return sysAttendOrgService;
    }

    /**
     * 组织ID，转换成人员ID
     * 不解析部门下的岗位
     * @param orgList
     * @return
     * @throws Exception
     */
    public static List<String> expandToPersonIds(List orgList) throws Exception {
        return getSysAttendOrgService().expandToPersonIds(orgList);
    }
    /**
     * 释放数据库连接资源
     */
    public static void release() {
        getSysAttendOrgService().release();
    }
    /**
     * 获取人员对象
     * 线程级缓存来处理。调用方需要手动清除缓存
     * @param personIds 只接收人员ID
     * @return 返回对应组织下面所有的 人员对象(简易对象，自己new出来的对象)
     * @throws Exception
     */
    public static List<SysOrgElement> expandToPersonSimple(List<String> personIds) throws Exception {
       return  getSysAttendOrgService().expandToPersonSimple(personIds);
    }
   /**
     * 获取人员对象
     * @param orgList
     * @return 返回对应组织下面所有的 人员对象
     * @throws Exception
     */
    public static List<SysOrgElement> expandToPerson(List orgList) throws Exception {
        return  getSysAttendOrgService().expandToPerson(orgList);
    }

    /**
     * 根据人员id获取人员对象
     * @param personIds 人员id列表
     * @throws Exception
     */
    public static List<SysOrgElement> getSysOrgElementById(List<String> personIds) throws Exception {
        return  getSysAttendOrgService().getSysOrgElementById(personIds);
    }
}
