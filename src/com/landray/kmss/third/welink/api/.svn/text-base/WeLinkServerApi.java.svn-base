package com.landray.kmss.third.welink.api;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.welink.constant.ThirdWelinkConstant;
import com.landray.kmss.third.welink.model.ThirdWelinkConfig;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping;
import com.landray.kmss.third.welink.model.ThirdWelinkToken;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptMappingService;
import com.landray.kmss.third.welink.util.ThirdWelinkApiUtil;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WeLinkServerApi {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WeLinkServerApi.class);

    private static ThirdWelinkToken token;

    private IThirdWelinkDeptMappingService thirdWelinkDeptMappingService;

    public WeLinkServerApi() {
        thirdWelinkDeptMappingService = (IThirdWelinkDeptMappingService) SpringBeanUtil.getBean("thirdWelinkDeptMappingService");
    }

    /**
     * 获取访问token
     *
     * @return
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        if (token != null && !token.isExpired()) {
            return token.getToken();
        }
        String apiUrl = ThirdWelinkConstant.API_URL + "/auth/v2/tickets";
        JSONObject requestBody = new JSONObject();
        ThirdWelinkConfig config = ThirdWelinkConfig.newInstance();
        requestBody.put("client_id", config.getWelinkClientid());
        requestBody.put("client_secret", config.getWelinkClientsecret());
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), null);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "获取access token", requestBody, null, resultObj);
        String tokenStr = resultObj.getString("access_token");
        long expires_in = resultObj.getLong("expires_in");
        token = new ThirdWelinkToken(tokenStr, expires_in);
        return token.getToken();
    }

    /**
     * 查询部门列表
     *
     * @param deptCode
     * @param corpDeptCode 如果参数都为空则查询所有
     * @return
     * @throws Exception
     */
    public JSONArray getSubDepartments(String deptCode, String corpDeptCode) throws Exception {
        JSONArray departmentData = new JSONArray();
        int pageNo = 1;
        int pageSize = 100;
        boolean hasMore = false;
        do {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if (StringUtil.isNotNull(deptCode)) {
                params.add(new BasicNameValuePair("deptCode", deptCode));
            }
            if (StringUtil.isNotNull(corpDeptCode)) {
                params.add(new BasicNameValuePair("corpDeptCode", corpDeptCode));
            }
            if (StringUtil.isNull(deptCode) && StringUtil.isNull(corpDeptCode)) {
                params.add(new BasicNameValuePair("deptCode", "0"));
            }
            params.add(new BasicNameValuePair("recursiveflag", "1"));
            params.add(new BasicNameValuePair("pageNo", String.valueOf(pageNo++)));
            params.add(new BasicNameValuePair("pageSize", String.valueOf(pageSize)));
            String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, "utf-8"));
            paramStr = ThirdWelinkUtil.formatParams(params);
            String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v2/department/list?" + paramStr;
            String token = getAccessToken();
            String result = ThirdWelinkApiUtil.execHttpGet(apiUrl, token);
            JSONObject resultObj = JSONObject.fromObject(result);
            checkResult(apiUrl, "查询部门列表", null, token, resultObj);
            JSONArray resultData = resultObj.getJSONArray("data");
            //接口返回hasMore参数一直都是1，有bug
            //hasMore = resultObj.getInt("hasMore") == 0 ? false : true;
            hasMore = (resultData.size() == pageSize ? true : false);
            departmentData.addAll(resultData);
        } while (hasMore);
        return departmentData;
    }

    /**
     * 新增部门
     *
     * @param dept       当前部门
     * @param parentCode WeLink侧的上一部门id
     * @return
     * @throws Exception
     */
    public String addDepartment(SysOrgElement dept, String parentCode) throws Exception {
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v1/department/create";
        JSONObject requestBody = new JSONObject();
        //客户侧部门唯一编码
        requestBody.put("corpDeptCode", dept.getFdId());
        //WeLink侧上一级部门编码
        if (StringUtil.isNull(parentCode) && dept.getFdParent() == null) {
            requestBody.put("parentCode", 0);
        } else if (StringUtil.isNotNull(parentCode)) {
            requestBody.put("parentCode", parentCode);
        }
        //客户侧上一级部门编码
        if (dept.getFdParent() != null) {
            requestBody.put("corpParentCode", dept.getFdParent().getFdId());
        }
        //部门中文名称
        requestBody.put("deptNameCn", dept.getFdName());
        //部门英文名称
        requestBody.put("deptNameEn", ThirdWelinkUtil.getLocaleSysOrgName(dept, "en-US"));
        /**
         //WeLink侧部门主管的ID，支持传多个，最多支持5个，输入userId
         requestBody.put("managerId", "");
         //客户侧部门主管的ID，支持传多个，最多支持5个，输入corpUserId
         requestBody.put("corpManagerId", "");
         **/
        //支持集团型企业设置部门类型, 1:部门（默认）；2:子公司。
        if (1 == dept.getFdOrgType()) {
            requestBody.put("deptType", "2");
        } else {
            requestBody.put("deptType", "1");
        }
        //部门权限的值是1,2,3
        //说明如下：1：可查看全部；2：仅查看自己; 3：可查看本部门及下级部门
        //requestBody.put("visibleRange", "3");
        //自动在IM创建对应部门群，0:关闭（默认）；1：开启
        //当部门群已经创建后，新人加入部门的人会自动加入该群，移除部门的人会自动移除该群
        requestBody.put("createDeptGroup", 0);
        //创建的部门群是否包含子部门，0:不包含子部门(默认); 1:包含子部门
        requestBody.put("groupContainSubDept", 0);
        //当部门群创建后，可以指定群主ID，可以输入客户侧corpUserId或者WeLink侧userId中的任意一个
        //requestBody.put("deptGroupOwnerId", "");
        setDeptOrderInfo(dept, requestBody);
        String token = getAccessToken();
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "创建部门", requestBody, token, resultObj);
        return resultObj.getString("deptCode");
    }

    /**
     * 更新部门
     *
     * @param dept       部门信息
     * @param deptCode   WeLink侧部门ID
     * @param parentCode WeLink侧上级部门ID
     * @throws Exception
     */
    public void updateDepartment(SysOrgElement dept, String deptCode, String parentCode) throws Exception {
        JSONObject requestBody = new JSONObject();
        if(StringUtil.isNotNull(deptCode)){
            requestBody.put("deptCode", deptCode);
        }
        requestBody.put("corpDeptCode", dept.getFdId());
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v1/department/update";
        //客户侧部门唯一编码
        requestBody.put("corpDeptCode", dept.getFdId());
        //WeLink侧上一级部门编码
        if (StringUtil.isNotNull(parentCode)) {
            requestBody.put("parentCode", parentCode);
        }
        //客户侧上一级部门编码
        if (dept.getFdParent() != null) {
            requestBody.put("corpParentCode", dept.getFdParent().getFdId());
        }
        //部门中文名称
        requestBody.put("deptNameCn", dept.getFdName());
        //部门英文名称
        requestBody.put("deptNameEn", ThirdWelinkUtil.getLocaleSysOrgName(dept, "en-US"));
        /**
         //WeLink侧部门主管的ID，支持传多个，最多支持5个，输入userId
         requestBody.put("managerId", "");
         //客户侧部门主管的ID，支持传多个，最多支持5个，输入corpUserId
         requestBody.put("corpManagerId", "");
         **/
        //支持集团型企业设置部门类型, 1:部门（默认）；2:子公司。
        if (1 == dept.getFdOrgType()) {
            requestBody.put("deptType", "2");
        } else {
            requestBody.put("deptType", "1");
        }
        //部门权限的值是1,2,3
        //说明如下：1：可查看全部；2：仅查看自己; 3：可查看本部门及下级部门
        requestBody.put("visibleRange", "3");
        //自动在IM创建对应部门群，0:关闭（默认）；1：开启
        //当部门群已经创建后，新人加入部门的人会自动加入该群，移除部门的人会自动移除该群
        requestBody.put("createDeptGroup", 0);
        //创建的部门群是否包含子部门，0:不包含子部门(默认); 1:包含子部门
        requestBody.put("groupContainSubDept", 0);
        //当部门群创建后，可以指定群主ID，可以输入客户侧corpUserId或者WeLink侧userId中的任意一个
        //requestBody.put("deptGroupOwnerId", "");
        setDeptOrderInfo(dept, requestBody);
        String token = getAccessToken();
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "更新部门", requestBody, token, resultObj);
    }

    /**
     * 查询部门详情
     *
     * @param deptCode
     * @param corpDeptCode
     * @return
     * @throws Exception
     */
    public JSONObject getDepartmentDetail(String deptCode, String corpDeptCode) throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (StringUtil.isNotNull(deptCode)) {
            params.add(new BasicNameValuePair("deptCode", deptCode));
        }
        if (StringUtil.isNotNull(corpDeptCode)) {
            params.add(new BasicNameValuePair("corpDeptCode", corpDeptCode));
        }
        String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, "utf-8"));
        paramStr = ThirdWelinkUtil.formatParams(params);
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v2/department/detail?" + paramStr;
        String token = getAccessToken();
        String result = ThirdWelinkApiUtil.execHttpGet(apiUrl, token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "获取部门详情", null, token, resultObj);
        return resultObj;
    }

    /**
     * 更新部门主管
     *
     * @param deptCode
     * @param corpDeptCode
     * @param managerIds
     * @param corpManagerIds
     * @throws Exception
     */
    public void updateDepartmentOwners(String deptCode, String corpDeptCode, String[] managerIds, String[] corpManagerIds) throws Exception {
        JSONObject requestBody = getDepartmentDetail(deptCode, corpDeptCode);
        requestBody.remove("code");
        requestBody.remove("message");
        if (managerIds != null) {
            requestBody.put("managerId", JSONArray.fromObject(managerIds));
        }
        if (corpManagerIds != null) {
            requestBody.put("corpManagerId", JSONArray.fromObject(corpManagerIds));
        }
        String token = getAccessToken();
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v1/department/update";
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "更新部门主管", requestBody, token, resultObj);
    }

    /**
     * 删除部门
     *
     * @param deptCode
     * @param corpDeptCode
     * @throws Exception
     */
    public void deleteDepartment(String deptCode, String corpDeptCode) throws Exception {
        JSONObject requestBody = new JSONObject();
        if (StringUtil.isNotNull(deptCode)) {
            requestBody.put("deptCode", deptCode);
        }
        if (StringUtil.isNotNull(corpDeptCode)) {
            requestBody.put("corpDeptCode", corpDeptCode);
        }
        String token = getAccessToken();
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v1/department/delete";
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "删除部门", requestBody, token, resultObj);
    }

    /**
     * 获取部门用户列表
     *
     * @param deptCode
     * @param corpDeptCode
     * @return
     * @throws Exception
     */
    public JSONArray getDepartmentUsers(String deptCode, String corpDeptCode) throws Exception {
        JSONArray userData = new JSONArray();
        int pageNo = 1;
        int pageSize = 50;
        boolean hasMore = false;
        do {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if (StringUtil.isNotNull(deptCode)) {
                params.add(new BasicNameValuePair("deptCode", deptCode));
            }
            if (StringUtil.isNotNull(corpDeptCode)) {
                params.add(new BasicNameValuePair("corpDeptCode", corpDeptCode));
            }
            if (StringUtil.isNull(deptCode) && StringUtil.isNull(corpDeptCode)) {
                params.add(new BasicNameValuePair("deptCode", "0"));
            }
            params.add(new BasicNameValuePair("pageNo", String.valueOf(pageNo++)));
            params.add(new BasicNameValuePair("pageSize", String.valueOf(pageSize)));
            String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, "utf-8"));
            paramStr = ThirdWelinkUtil.formatParams(params);
            String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v1/user/simplelist?" + paramStr;
            String token = getAccessToken();
            String result = ThirdWelinkApiUtil.execHttpGet(apiUrl, token);
            JSONObject resultObj = JSONObject.fromObject(result);
            checkResult(apiUrl, "查询部门用户列表", null, token, resultObj);
            JSONArray resultData = resultObj.getJSONArray("data");
            //接口返回hasMore参数一直都是1，有bug
            //hasMore = resultObj.getInt("hasMore") == 0 ? false : true;
            hasMore = (resultData.size() == pageSize ? true : false);
            userData.addAll(resultData);
        } while (hasMore);
        return userData;
    }

    /**
     * 获取用户详情
     *
     * @param userId
     * @param corpUserId
     * @param mobileNumber
     * @return
     * @throws Exception
     */
    public JSONObject getUserDetail(String userId, String corpUserId, String mobileNumber) throws Exception {
        JSONObject requestBody = new JSONObject();
        if (StringUtil.isNotNull(userId)) {
            requestBody.put("userId", userId);
        } else if (StringUtil.isNotNull(corpUserId)) {
            requestBody.put("corpUserId", corpUserId);
        } else if (StringUtil.isNotNull(mobileNumber)) {
            requestBody.put("mobileNumber", mobileNumber);
        }
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v2/user/detail";
        String token = getAccessToken();
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "查询用户详情", null, token, resultObj);
        return resultObj;
    }

    /**
     * 删除用户
     *
     * @param userId
     * @param corpUserId
     * @param mobileNumber
     * @throws Exception
     */
    public void deleteUser(String userId, String corpUserId, String mobileNumber) throws Exception {
        JSONObject requestBody = new JSONObject();
        if (StringUtil.isNotNull(userId)) {
            requestBody.put("userId", userId);
        }
        if (StringUtil.isNotNull(corpUserId)) {
            requestBody.put("corpUserId", corpUserId);
        }
        if (StringUtil.isNotNull(mobileNumber)) {
            requestBody.put("mobileNumber", mobileNumber);
        }
        requestBody.put("isDeleteUser", 0);
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v1/user/delete";
        String token = getAccessToken();
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "删除用户", null, token, resultObj);
    }

    /**
     * 创建用户
     *
     * @param person
     * @return
     * @throws Exception
     */
    public String addUser(SysOrgPerson person) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("corpUserId", person.getFdId());
        requestBody.put("mobileNumber", person.getFdMobileNo());
        requestBody.put("userNameCn", person.getFdName());
        requestBody.put("userNameEn", ThirdWelinkUtil.getLocaleSysOrgName(person, "en-US"));
        requestBody.put("sex", person.getFdSex());
        requestBody.put("phoneNumber", person.getFdWorkPhone());
        setPersonDeptInfo(person, requestBody);
        setPersonOrderInDeptInfo(person, requestBody);
        requestBody.put("userEmail", person.getFdEmail());
        requestBody.put("remark", person.getFdName());
        requestBody.put("seniorMode", 0);
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v1/user/create";
        String token = getAccessToken();
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "新增用户", requestBody, token, resultObj);
        return resultObj.getString("userId");
    }

    /**
     * 更新用户
     *
     * @param person
     * @throws Exception
     */
    public void updateUser(SysOrgPerson person) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("corpUserId", person.getFdId());
        requestBody.put("mobileNumber", person.getFdMobileNo());
        requestBody.put("userNameCn", person.getFdName());
        requestBody.put("userNameEn", ThirdWelinkUtil.getLocaleSysOrgName(person, "en-US"));
        requestBody.put("sex", person.getFdSex());
        requestBody.put("phoneNumber", person.getFdWorkPhone());
        setPersonDeptInfo(person, requestBody);
        setPersonOrderInDeptInfo(person, requestBody);
        requestBody.put("userEmail", person.getFdEmail());
        requestBody.put("remark", person.getFdName());
        requestBody.put("seniorMode", 0);
        String apiUrl = ThirdWelinkConstant.API_URL + "/contact/v1/user/update";
        String token = getAccessToken();
        String result = ThirdWelinkApiUtil.execHttpPost(apiUrl, requestBody.toString(), token);
        JSONObject resultObj = JSONObject.fromObject(result);
        checkResult(apiUrl, "更新用户", requestBody, token, resultObj);
    }

    /**
     * 检查请求结果
     *
     * @param apiUrl
     * @param requestBody
     * @param token
     * @return
     * @throws Exception
     */
    private void checkResult(String apiUrl, String apiName, JSONObject requestBody, String token, JSONObject resultObj) throws Exception {
        String code = resultObj.getString("code");
        if ("0".equals(code)) {
            if (logger.isDebugEnabled()) {
                logger.debug("----------------------" + apiName + "请求信息----------------------");
                logger.debug("api->" + apiUrl);
                logger.debug("request method->post");
                logger.debug("request body->" + requestBody);
                logger.debug("token->" + token);
                logger.debug("result->" + resultObj.toString());
            }
        } else {
            JSONObject errorInfo = new JSONObject();
            errorInfo.put("desc", apiName + "请求失败信息");
            errorInfo.put("api", apiUrl);
            errorInfo.put("request method", "post");
            errorInfo.put("request body", requestBody);
            errorInfo.put("request token", token);
            errorInfo.put("request result", resultObj.toString());
            logger.error(errorInfo.toString());
            throw new Exception(apiName + "请求失败:" + resultObj.getString("message"));
        }
    }

    /**
     * 获取用户所属部门id
     *
     * @param ele
     * @return
     * @throws Exception
     */
    private void setPersonDeptInfo(SysOrgElement ele, JSONObject requestBody) throws Exception {
        JSONArray deptCodes = new JSONArray();
        JSONArray corpDeptCodes = new JSONArray();
        SysOrgElement parent = ele.getFdParent();
        if (parent == null) {
            requestBody.put("mainDeptCode", "0");
            deptCodes.add("0");
            requestBody.put("deptCodes", deptCodes);
            return;
        }
        ThirdWelinkDeptMapping mapping = thirdWelinkDeptMappingService.findByEkpId(parent.getFdId());
        if (mapping != null) {
            requestBody.put("mainDeptCode", mapping.getFdWelinkId());
            deptCodes.add(mapping.getFdWelinkId());
        } else {
            throw new Exception("员工" + ele.getFdId() + "->" + ele.getFdName() + "所属部门未出现在映射表中");
        }
        requestBody.put("mainCorpDeptCode", parent.getFdId());
        corpDeptCodes.add(parent.getFdId());
        requestBody.put("deptCodes", deptCodes);
        requestBody.put("corpDeptCodes", corpDeptCodes);
    }

    /**
     * 设置用户在部门中的排序
     *
     * @param ele
     * @param requestBody
     * @throws Exception
     */
    private void setPersonOrderInDeptInfo(SysOrgElement ele, JSONObject requestBody) throws Exception {
        Integer order = ele.getFdOrder();
        if (order == null) {
            return;
        }
        if (requestBody.containsKey("mainDeptCode")) {
            JSONObject orderObj = new JSONObject();
            orderObj.put(requestBody.getString("mainDeptCode"), order.intValue());
            requestBody.put("orderInDepts", orderObj);
        }
        if (requestBody.containsKey("mainCorpDeptCode")) {
            JSONObject orderObj = new JSONObject();
            orderObj.put(requestBody.getString("mainCorpDeptCode"), order.intValue());
            requestBody.put("orderInCorpDepts", orderObj);
        }
    }

    /**
     * 设置部门排序
     *
     * @param dept
     * @param requestBody
     */
    private void setDeptOrderInfo(SysOrgElement dept, JSONObject requestBody) {
        if (dept.getFdOrder() == null || dept.getFdOrder().intValue() == 0) {
            requestBody.put("orderNo", 1);
        } else if (dept.getFdOrder().intValue() > 9999) {
            requestBody.put("orderNo", 9999);
        } else {
            requestBody.put("orderNo", dept.getFdOrder());
        }
    }
}
