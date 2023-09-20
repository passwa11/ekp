package com.landray.kmss.sys.oms.temp;

import java.util.HashMap;
import java.util.Map;


/**
 * 组织机构基础数据导入导出工具类
 *
 * @author zhongzy 2019年12月20日
 *
 */
public class SysOmsTempImportExportUtil {
    public static final int SHEET_DEPT = 0;// 部门模版字段
    public static final int SHEET_POST = 1;// 岗位模版字段
    public static final int SHEET_PERSON = 2;// 人员模版字段
    public static final int SHEET_DEPT_PERSON = 3;// 部门人员关系模版字段
    public static final int SHEET_POST_PERSON = 4;// 岗位人员关系模版字段

//    public static enum OmsTempSynModel {
//
//        /**
//         * 同步部门、人员
//         */
//        OMS_TEMP_SYN_MODEL_1("同步部门、人员",1),
//
//        /**
//         * 同步部门、人员、部门人员关系：人员的部门属性表示主部门，这种模式需要在该部门下新建一个名称为“成员”的岗位，并且将该人员放入该岗位中，
//         * 这种模式下部门人员关系即用来做部门人员关系，又做部门人员排序号
//         */
//        OMS_TEMP_SYN_MODEL_20("同步部门、人员、部门人员关系",20),
//
//        /**
//         * 同步部门、人员、部门人员关系：人员的部门属性无用，系统自动选择其中一个部门为用户主部门，这种模式需要在该部门下新建一个名称为“成员”的岗位，并且将该人员放入该岗位中
//         * 这种模式下部门人员关系即用来做部门人员关系，又做部门人员排序号
//         */
//        OMS_TEMP_SYN_MODEL_21("同步部门、人员、部门人员关系",21),
//
//        /**
//         * 同步部门、岗位、人员、岗位人员关系
//         */
//        OMS_TEMP_SYN_MODEL_30("同步部门、岗位、人员、岗位人员关系",30),
//
//        /**
//         * 同步部门、岗位、人员、岗位人员关系、部门人员关系：人员的部门属性表示主部门，这种模式部门人员关系只用来做排序号
//         */
//        OMS_TEMP_SYN_MODEL_40("同步部门、岗位、人员、岗位人员关系、部门人员关系",40);
//
//        /**
//         * 同步部门、岗位、人员、岗位人员关系、部门人员关系：人员的部门属性无用，暂不支持
//         */
//        //OMS_TEMP_SYN_MODEL_41("同步部门、岗位、人员、岗位人员关系、部门人员关系",41);
//        private String desc;
//        private int value;
//
//        private OmsTempSynModel(String desc,int value){
//            this.value = value;
//            this.desc = desc;
//        }
//
//        public String getDesc() {
//            return desc;
//        }
//
//        public int getValue() {
//            return value;
//        }
//
//    }

    /**
     * 模板字段分类
     * <p>
     * 基本信息，数据多语言 ，职位信息，身份验证信息，其他选填字段
     */
    public static String[] fieldGroup() {
            // 系统管理员不能导入职位信息
        return new String[] { "base", "lang", "job", "identity", "other" };
    };

    /**
     * 获取模板字段
     *
     * @param type
     *            1:部门，2:人员
     * @return
     */
    public static Map<String, String[]> getTemplateFields(int type) {
        Map<String, String[]> map = new HashMap<String, String[]>();
        switch (type) {
            // 部门模版字段
            case SHEET_DEPT:
                // 部门id、部门名称、是否有效、修改时间
                map.put("base", new String[] { "fdDeptId", "fdName", "fdIsAvailable", "fdAlterTime" });
                // 上级部门，排序号
                map.put("other", new String[] { "fdParentid", "fdOrder" });
                break;
            // 岗位模版字段
            case SHEET_POST:
                // 部门id、岗位名称、是否有效、修改时间
                map.put("base", new String[] { "fdPostId", "fdName", "fdIsAvailable", "fdAlterTime" });
                // 上级部门，排序号
                map.put("other", new String[] { "fdParentid", "fdOrder" });
                break;

            // 人员模版字段
            case SHEET_PERSON:
                // 部门id、人员id
                map.put("base", new String[] { "fdPersonId", "fdName", "fdIsAvailable", "fdAlterTime"});
                // 修改时间
                map.put("other", new String[] { "fdParentid", "fdOrder", "fdMobileNo", "fdEmail", "fdSex", "fdExtra" });
                break;

            // 部门人员关系模版字段
            case SHEET_DEPT_PERSON:
                // 部门id、人员id
                map.put("base", new String[] { "fdDeptId", "fdPersonId" });
                // 修改时间
                map.put("other", new String[] { "fdAlterTime" });
                break;
            // 岗位人员关系模版字段
            case SHEET_POST_PERSON:
                // 岗位id、人员id
                map.put("base", new String[] { "fdPostId", "fdPersonId","fdIsAvailable" });
                // 修改时间
                map.put("other", new String[] { "fdAlterTime" });
                break;

        }
        return map;
    }
}