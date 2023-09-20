package com.landray.kmss.third.ding.oms;

import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;

public class DingApiV2ServiceImpl extends DingTokenService implements DingApiV2Service{
    @Override
    public OapiV2UserCreateResponse v2_createUser(OapiV2UserCreateRequest createRequest) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/user/create";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2UserCreateResponse response = client.execute(createRequest,getAccessToken());
        return response;
    }

    @Override
    public OapiV2UserUpdateResponse v2_updateUser(OapiV2UserUpdateRequest userUpdateRequest) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/user/update";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2UserUpdateResponse response = client.execute(userUpdateRequest,getAccessToken());
        return response;
    }

    @Override
    public OapiV2UserDeleteResponse v2_deleteUser(String userid) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/user/delete";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2UserDeleteRequest req = new OapiV2UserDeleteRequest();
        req.setUserid(userid);
        OapiV2UserDeleteResponse response = client.execute(req,getAccessToken());
        return response;
    }

    @Override
    public OapiV2UserGetResponse v2_findUser(String userid) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/user/get";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2UserGetRequest req = new OapiV2UserGetRequest();
        req.setUserid(userid);
        OapiV2UserGetResponse  response = client.execute(req,getAccessToken());
        return response;
    }

    @Override
    public OapiUserListsimpleResponse v2_userListSimple(Long dept_id, Long cursor) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/user/listsimple";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiUserListsimpleRequest req = new OapiUserListsimpleRequest();
        req.setDeptId(dept_id);
        req.setCursor(cursor);
        req.setSize(100L);
        OapiUserListsimpleResponse rsp = client.execute(req, getAccessToken());
        return rsp;
    }

    @Override
    public OapiUserListidResponse v2_userListId(Long dept_id) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/user/listid";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiUserListidRequest req = new OapiUserListidRequest();
        req.setDeptId(dept_id);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2UserListResponse  v2_userList(Long dept_id, Long cursor) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/user/list";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2UserListRequest req = new OapiV2UserListRequest();
        req.setDeptId(dept_id);
        req.setCursor(cursor);
        req.setSize(100L);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiUserCountResponse v2_userCount(Boolean only_active) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/user/count";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiUserCountRequest req = new OapiUserCountRequest();
        req.setOnlyActive(only_active);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiInactiveUserV2GetResponse v2_userInactiveList(OapiInactiveUserV2GetRequest req) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/inactive/user/v2/get";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2UserGetbymobileResponse v2_getUseridByMobile(String mobile) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/user/getbymobile";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(mobile);
        req.setSupportExclusiveAccountSearch(true);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiUserGetbyunionidResponse v2_getUserIdByUnionId(String unionId) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/user/getbyunionid";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiUserGetbyunionidRequest req = new OapiUserGetbyunionidRequest();
        req.setUnionid(unionId);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2SafeSetenableResponse v2_setEnable(String userid) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/safe/setenable";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2SafeSetenableRequest req = new OapiV2SafeSetenableRequest();
        req.setUserid(userid);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2SafeSetdisableResponse v2_setDisable(String userid) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/safe/setdisable";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2SafeSetdisableRequest req = new OapiV2SafeSetdisableRequest();
        req.setUserid(userid);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2SafeQuerystatusResponse v2_querystatus(String userid) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/safe/querystatus";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2SafeQuerystatusRequest req = new OapiV2SafeQuerystatusRequest();
        req.setUserid(userid);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiUserListadminResponse v2_getAdminList(String userid) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/user/listadmin";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        return client.execute(new OapiUserListadminRequest(), getAccessToken());
    }

    @Override
    public OapiUserGetAdminScopeResponse v2_getAdminScope(String userid) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/user/get_admin_scope";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiUserGetAdminScopeRequest req = new OapiUserGetAdminScopeRequest();
        req.setUserid(userid);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2DepartmentCreateResponse v2_deptCreate(OapiV2DepartmentCreateRequest request) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/department/create";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        return client.execute(request, getAccessToken());
    }

    @Override
    public OapiV2DepartmentUpdateResponse v2_deptUpdate(OapiV2DepartmentUpdateRequest request) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/department/update";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        return client.execute(request, getAccessToken());
    }

    @Override
    public OapiV2DepartmentDeleteResponse v2_deptDelete(Long deptId) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/department/delete";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2DepartmentDeleteRequest req = new OapiV2DepartmentDeleteRequest();
        req.setDeptId(100L);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2DepartmentGetResponse v2_getDepartment(Long deptId) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/department/get";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
        req.setDeptId(deptId);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2DepartmentListsubResponse v2_listSub(Long deptId) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/department/get";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(deptId);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2DepartmentListsubidResponse v2_listSubId(Long deptId) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/department/listsubid";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2DepartmentListsubidRequest req = new OapiV2DepartmentListsubidRequest();
        req.setDeptId(deptId);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2DepartmentListparentbydeptResponse v2_listParentByDeptId(Long deptId) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/department/listparentbydept";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2DepartmentListparentbydeptRequest req = new OapiV2DepartmentListparentbydeptRequest();
        req.setDeptId(deptId);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiV2DepartmentListparentbyuserResponse v2_listParentByUserid(String userid) throws Exception {
        String url = DingConstant.DING_PREFIX+ "/topapi/v2/department/listparentbydept";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiV2DepartmentListparentbyuserRequest req = new OapiV2DepartmentListparentbyuserRequest();
        req.setUserid(userid);
        return client.execute(req, getAccessToken());
    }

    @Override
    public OapiSmartworkHrmEmployeeV2ListResponse v2_employeeList(String useridList, String fieldFilterList) throws Exception {
        String url = DingConstant.DING_PREFIX + "/topapi/smartwork/hrm/employee/v2/list";
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiSmartworkHrmEmployeeV2ListRequest req = new OapiSmartworkHrmEmployeeV2ListRequest();
        req.setUseridList(useridList);
        req.setFieldFilterList(fieldFilterList);
        req.setAgentid(Long.valueOf(DingConfig.newInstance().getDingAgentid()));
        return client.execute(req, getAccessToken());
    }
}
