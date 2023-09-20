package com.landray.kmss.third.ding.oms;

import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;

public interface DingApiV2Service {

    /**
     * 新建用户
     */
    OapiV2UserCreateResponse v2_createUser(OapiV2UserCreateRequest createRequest) throws Exception;


    /**
     * 更新用户
     */
    OapiV2UserUpdateResponse v2_updateUser(OapiV2UserUpdateRequest userUpdateRequest) throws Exception;

    /**
     *  删除用户
     */
    OapiV2UserDeleteResponse  v2_deleteUser(String userid)  throws Exception;

    /**
     * 查询用户详情
     * @param userid
     */
    OapiV2UserGetResponse v2_findUser(String userid) throws Exception;


    /**
     *  获取部门用户基础信息
     */
    OapiUserListsimpleResponse v2_userListSimple(Long dept_id, Long cursor) throws Exception;


    /**
     * 获取部门用户userid列表
     */
    OapiUserListidResponse v2_userListId(Long dept_id) throws Exception;


    /**
     *  获取部门用户详情
     */
    OapiV2UserListResponse  v2_userList(Long dept_id, Long cursor) throws Exception;

    /**
     *获取员工人数
     */
    OapiUserCountResponse v2_userCount(Boolean only_active) throws Exception;

    /*
     * 获取未登录钉钉的员工列表
     */
    OapiInactiveUserV2GetResponse v2_userInactiveList(OapiInactiveUserV2GetRequest req) throws Exception;

    /**
     * 根据手机号查询用户
     */
    OapiV2UserGetbymobileResponse v2_getUseridByMobile(String mobile) throws Exception;

    /**
     * 根据unionid获取用户userid
     */
    OapiUserGetbyunionidResponse v2_getUserIdByUnionId(String unionId) throws Exception;

    /**
     * 解冻专属帐号
     */
    OapiV2SafeSetenableResponse  v2_setEnable(String userid) throws Exception;

    /**
     * 专属帐号冻结
     */
    OapiV2SafeSetdisableResponse  v2_setDisable(String userid) throws Exception;


    /**
     * 查询专属帐号状态
     */
    OapiV2SafeQuerystatusResponse  v2_querystatus(String userid) throws Exception;


    /**
     * 获取管理员列表
     */
    OapiUserListadminResponse  v2_getAdminList(String userid) throws Exception;

    /**
     * 获取管理员通讯录权限范围
     */
    OapiUserGetAdminScopeResponse  v2_getAdminScope(String userid) throws Exception;

    /**
     * 创建部门
     */
    OapiV2DepartmentCreateResponse  v2_deptCreate(OapiV2DepartmentCreateRequest request) throws Exception;

    /**
     * 更新部门
     */
    OapiV2DepartmentUpdateResponse  v2_deptUpdate(OapiV2DepartmentUpdateRequest request) throws Exception;

    /**
     * 删除部门
     */
    OapiV2DepartmentDeleteResponse  v2_deptDelete(Long deptId) throws Exception;

    /**
     * 获取部门详情
     */
    OapiV2DepartmentGetResponse  v2_getDepartment(Long deptId) throws Exception;

    /**
     * 获取部门列表
     */
    OapiV2DepartmentListsubResponse v2_listSub(Long deptId) throws Exception;

    /**
     * 获取子部门ID列表
     */
    OapiV2DepartmentListsubidResponse  v2_listSubId(Long deptId) throws Exception;

    /**
     * 获取指定部门的所有父部门列表
     */
    OapiV2DepartmentListparentbydeptResponse  v2_listParentByDeptId(Long deptId) throws Exception;

    /**
     * 获取指定用户的所有父部门列表
     */
    OapiV2DepartmentListparentbyuserResponse  v2_listParentByUserid(String userid) throws Exception;


    /**
     * 获取员工花名册字段信息，如员工的主部门
     * @param useridList 多值用,隔开
     * @param fieldFilterList 多值用,隔开
     * @return
     * @throws Exception
     */
    OapiSmartworkHrmEmployeeV2ListResponse  v2_employeeList(String useridList,String fieldFilterList) throws Exception;

}
