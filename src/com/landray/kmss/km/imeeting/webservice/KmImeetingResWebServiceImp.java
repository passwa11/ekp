package com.landray.kmss.km.imeeting.webservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingResCategory;
import com.landray.kmss.km.imeeting.service.IKmImeetingResCategoryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping(value = "/api/km-imeeting/kmImeetingResWebService",method = RequestMethod.POST)
@RestApi(
        docUrl = "/km/imeeting/restService/KmImeetingResRestHelp.jsp",
        name = "kmImeetingResWebService",
        resourceKey = "km-imeeting:kmImeetingRes.rest"
)
public class KmImeetingResWebServiceImp implements IKmImeetingResWebService,KmImeetingWebServiceConstant{
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmImeetingResWebServiceImp.class);

    private IKmImeetingResService kmImeetingResService;

    public void setKmImeetingResService(IKmImeetingResService kmImeetingResService) {
        this.kmImeetingResService = kmImeetingResService;
    }
    private IKmImeetingResCategoryService kmImeetingResCategoryService;

    public void setKmImeetingResCategoryService(IKmImeetingResCategoryService kmImeetingResCategoryService) {
        this.kmImeetingResCategoryService = kmImeetingResCategoryService;
    }

    private ISysWsOrgService sysWsOrgService;

    public void setSysWsOrgService(ISysWsOrgService sysWsOrgService) {
        this.sysWsOrgService = sysWsOrgService;
    }

    private IBackgroundAuthService backgroundAuthService;

    public void setBackgroundAuthService(
            IBackgroundAuthService backgroundAuthService) {
        this.backgroundAuthService = backgroundAuthService;
    }

    private SysOrgElement parseToPerson(String jsonPerson) throws Exception {
        if (StringUtil.isNull(jsonPerson)) {
            return null;
        }
        if (jsonPerson.indexOf("[") > -1) {
            return null;
        } else {
            SysOrgElement tmpOrg = sysWsOrgService
                    .findSysOrgElement(jsonPerson);
            if (tmpOrg != null && tmpOrg.getFdOrgType() == 8) {
                return tmpOrg;
            }
        }
        return null;
    }


    /**
     * 会议室资源查询列表
     * @param
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "/getKmImeetingResList")
    public KmImeetingResResult getKmImeetingResList(KmImeetingResContext context) throws Exception {
        KmImeetingResResult result = new KmImeetingResResult();
        SysOrgElement operator = parseToPerson(context.getTargets());
        if (operator == null) {
                result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
                result.setMessage("操作用户不存在");
                return result;
        }else{//查询所有会议室资源
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("kmImeetingRes.fdIsAvailable =:fdIsAvailable");
            hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
            List<KmImeetingRes> list = kmImeetingResService.findList(hqlInfo);
            if(!ArrayUtil.isEmpty(list) && list!=null){
                JSONArray jsonArr = new JSONArray();
                for(KmImeetingRes kmImeetingRes:list){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("fdId",String.valueOf(StringUtil.getString(kmImeetingRes.getFdId()))); //fdId
                    jsonObject.put("fdName",String.valueOf(StringUtil.getString(kmImeetingRes.getFdName()))); //会议室名称
                    jsonObject.put("fdDetail",String.valueOf(StringUtil.getString(kmImeetingRes.getFdDetail())));//设备
                    jsonObject.put("fdAddressFloor",String.valueOf(StringUtil.getString(kmImeetingRes.getFdAddressFloor())));//楼层
                    jsonObject.put("fdSeats",String.valueOf(StringUtil.getString(kmImeetingRes.getFdSeats())));//容纳人数
                    jsonObject.put("fdUserTime",String.valueOf(kmImeetingRes.getFdUserTime())); //最大使用时长
                    jsonObject.put("docCreator",String.valueOf(StringUtil.getString(kmImeetingRes.getDocCreator().getFdName())));//创建人
                    if(kmImeetingRes.getDocKeeper()!=null) {
                        jsonObject.put("docKeeper", String.valueOf(StringUtil.getString(kmImeetingRes.getDocKeeper().getFdName())));//保管人
                    }
                    jsonObject.put("docCategory",String.valueOf(StringUtil.getString(kmImeetingRes.getDocCategory().getFdName())));//所属分类
                    jsonArr.add(jsonObject);
                }
                result.setCount(list.size()); //返回总条数
                result.setMessage(jsonArr.toJSONString()); //数据
                result.setResultState(RETURN_CONSTANT_STATUS_SUCESS); //成功
            }
        }
        return result;
    }

    /**
     * 根据会议室ID查询会议室详情
     * @param fdId
     * @return
     * @throws Exception
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "/getKmimeetingResById")
    public KmImeetingResResult getKmimeetingResById(String fdId) throws Exception {
        KmImeetingResResult result = new KmImeetingResResult();
        if(StringUtil.isNull(fdId)){
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            result.setMessage("会议室ID不能为空");
            return result;
        }
        JSONArray jsonArr = new JSONArray();
        KmImeetingRes kmImeetingRes = (KmImeetingRes) kmImeetingResService
                .findByPrimaryKey(fdId,null,true);
        if(kmImeetingRes==null){
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL); //失败
            result.setMessage(ResourceUtil.getString(
                    "kmImeeting.webservice.none.error", "km-imeeting")); //查询不到记录
            return result;
        }
        logger.debug("开始查询会议室详情...");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fdId",String.valueOf(StringUtil.getString(kmImeetingRes.getFdId()))); //fdId
        jsonObject.put("fdName",String.valueOf(StringUtil.getString(kmImeetingRes.getFdName()))); //会议室名称
        jsonObject.put("fdDetail",String.valueOf(StringUtil.getString(kmImeetingRes.getFdDetail())));//设备
        jsonObject.put("fdAddressFloor",String.valueOf(StringUtil.getString(kmImeetingRes.getFdAddressFloor())));//楼层
        jsonObject.put("fdSeats",String.valueOf(StringUtil.getString(kmImeetingRes.getFdSeats())));//容纳人数
        jsonObject.put("fdUserTime",String.valueOf(kmImeetingRes.getFdUserTime())); //最大使用时长
        jsonObject.put("docCreator",String.valueOf(StringUtil.getString(kmImeetingRes.getDocCreator().getFdName())));//创建人
        if(kmImeetingRes.getDocKeeper()!=null) {
            jsonObject.put("docKeeper", String.valueOf(StringUtil.getString(kmImeetingRes.getDocKeeper().getFdName())));//保管人
        }
        jsonObject.put("docCategory",String.valueOf(StringUtil.getString(kmImeetingRes.getDocCategory().getFdName())));//所属分类
        logger.debug("结束查询会议室详情...");
        jsonArr.add(jsonObject);
        result.setMessage(jsonArr.toJSONString());
        result.setResultState(RETURN_CONSTANT_STATUS_SUCESS); //成功
        return result;
    }

    /**
     * 新增会议室接口
     * @param resform
     * @return
     * @throws Exception
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "addKmImeetingRes")
    public KmImeetingResResult addKmImeetingRes(KmImeetingResParamterForm resform) throws Exception {
        KmImeetingResResult result = new KmImeetingResResult();
        SysOrgElement creator = null;
        try {
            creator = sysWsOrgService.findSysOrgElement(resform.getDocCreator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(creator ==null){
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            result.setMessage(resform.getDocCreator() + "用户不存在");
            return result;
        }
        // 修改切换用户的方法
        return (KmImeetingResResult) backgroundAuthService.switchUserById(creator
                .getFdId(), new Runner() {
            @Override
            public Object run(Object parameter) throws Exception {
                KmImeetingResParamterForm form = (KmImeetingResParamterForm) parameter;
                KmImeetingResResult result = new KmImeetingResResult();// 返回结果
                // 对象字段校验
                if (!checkNullIfNecessary(form,METHOD_CONSTANT_NAME_ADDRES, result)) {
                    //失败，必要字段不能为空
                    result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
                    return result;
                }
                KmImeetingRes kmImeetingRes = new KmImeetingRes();
                kmImeetingRes.setFdName(form.getFdName()); //会议室名称
                kmImeetingRes.setFdDetail(form.getFdDetail());//设备详情
                kmImeetingRes.setFdAddressFloor(form.getFdAddressFloor());//楼层
                kmImeetingRes.setFdSeats(form.getFdSeats()); //容纳人数
                if(StringUtil.isNotNull(form.getDocKeeper())) {
                    kmImeetingRes.setDocKeeper(sysWsOrgService.findSysOrgElement(form.getDocKeeper())); //保管人
                }
                kmImeetingRes.setDocCreator((SysOrgPerson) sysWsOrgService.findSysOrgElement(form.getDocCreator()));//创建人
                if(StringUtil.isNotNull(form.getFdIsAvailable())) {
                    kmImeetingRes.setFdIsAvailable(Boolean.valueOf(form.getFdIsAvailable()));
                }else{
                    kmImeetingRes.setFdIsAvailable(Boolean.TRUE);
                }
                KmImeetingResCategory category = (KmImeetingResCategory) kmImeetingResCategoryService.findByPrimaryKey(form.getDocCategoryId());
                if(category!=null) {
                    kmImeetingRes.setDocCategory(category); //所属分类
                }
                String modelId = kmImeetingResService.add(kmImeetingRes);
               JSONObject message = new JSONObject();
                message.put("fdId", modelId);
                result.setMessage(message.toString());
                result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
                return result;
            }
        }, resform);
    }
    @Override
    @ResponseBody
    @RequestMapping(value = "updateKmImeetingRes")
    public KmImeetingResResult updateKmImeetingRes(KmImeetingResParamterForm resform) throws Exception {
        KmImeetingResResult result = new KmImeetingResResult();
        SysOrgElement creator = null;
        try {
            creator = sysWsOrgService.findSysOrgElement(resform.getDocCreator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(creator ==null){
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            result.setMessage(resform.getDocCreator() + "用户不存在");
            return result;
        }
        return (KmImeetingResResult) backgroundAuthService.switchUserById(creator
                .getFdId(), new Runner() {
            @Override
            public Object run(Object parameter) throws Exception {
                KmImeetingResParamterForm form = (KmImeetingResParamterForm) parameter;
                // 对象字段校验
                if (!checkNullIfNecessary(form,METHOD_CONSTANT_NAME_UPDATERES, result)) {
                    result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
                    return result;
                }
                KmImeetingRes kmImeetingRes = new KmImeetingRes();
                kmImeetingRes.setFdId(form.getFdId()); //会议室Id
                kmImeetingRes.setFdName(form.getFdName()); //会议室名称
                kmImeetingRes.setFdDetail(form.getFdDetail());//设备详情
                kmImeetingRes.setFdAddressFloor(form.getFdAddressFloor());//楼层
                kmImeetingRes.setFdSeats(form.getFdSeats()); //容纳人数
                if(StringUtil.isNotNull(form.getDocKeeper())) {
                    kmImeetingRes.setDocKeeper(sysWsOrgService.findSysOrgElement(form.getDocKeeper())); //保管人
                }
                kmImeetingRes.setDocCreator((SysOrgPerson) (SysOrgPerson) sysWsOrgService.findSysOrgElement(form.getDocCreator()));//创建人
                kmImeetingRes.setFdIsAvailable(Boolean.valueOf(form.getFdIsAvailable()));
                KmImeetingResCategory category = (KmImeetingResCategory) kmImeetingResCategoryService.findByPrimaryKey(form.getDocCategoryId());
                kmImeetingRes.setDocCategory(category); //所属分类
                kmImeetingResService.update(kmImeetingRes);
                result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
                result.setCount(1);
                return result;
            }
        },resform);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "deleteKmImeetingRes")
    public KmImeetingResResult deleteKmImeetingRes(KmImeetingResParamterForm resform) throws Exception {
        KmImeetingResResult result = new KmImeetingResResult();
            if (!checkNullIfNecessary(resform,METHOD_CONSTANT_NAME_DELETERES, result)) {
                result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
                return result;
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("kmImeetingRes.fdId=:resId");
            hqlInfo.setParameter("resId", resform.getFdId());
            List<KmImeetingRes> list = kmImeetingResService.findList(hqlInfo);
            if(!ArrayUtil.isEmpty(list)){
                KmImeetingRes kmImeetingRes = list.get(0);
                kmImeetingResService.delete(kmImeetingRes); //删除
                result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
                result.setMessage(ResourceUtil.getString(
                        "kmImeeting.webservice.delete.success", "km-imeeting"));
                result.setCount(1);
            }else{
                result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
                result.setMessage(ResourceUtil.getString(
                        "kmImeeting.webservice.delete.error", "km-imeeting"));
                result.setCount(0);
            }
        return result;
    }

    /**
     * 校验必填字段是否为空
     * @param context
     * @param methodKey
     * @param result
     * @return
     * @throws Exception
     */
    private boolean checkNullIfNecessary(Object context,
                                         String methodKey, KmImeetingResResult result) throws Exception {
        if (null == context) {
            result.setMessage(ResourceUtil.getString(
                    "kmImeetingRes.webservice.warning", "km-imeeting"));
            logger.debug("会议室资源上下文为空!");
            return false;
        }
        String fields = "";
        if(METHOD_CONSTANT_NAME_ADDRES.equals(methodKey)){
            fields="fdName;fdAddressFloor;fdSeats;docCreator;docCategoryId";
        }else if(METHOD_CONSTANT_NAME_UPDATERES.equals(methodKey)){
            fields="fdId;fdName;fdAddressFloor;fdSeats;docCategoryId";
        }else if(METHOD_CONSTANT_NAME_DELETERES.equals(methodKey)){
            fields="fdId";
        }
        String[] fileArr = fields.split(";");
        for (int i = 0; i < fileArr.length; i++) {
            if (StringUtil.isNull(fileArr[i])) {
                continue;
            }
            if (isNullProperty(context, fileArr[i])) {
                String filedName = ResourceUtil.getString(
                        "kmImeetingRes.webservice." + fileArr[i], "km-imeeting");
                result.setMessage(ResourceUtil.getString(
                        "kmImeeting.webservice.warning.property",
                        "km-imeeting", null,
                        new Object[] { methodKey, filedName }));
                logger.debug("方法" + methodKey + "中,不允许会议上下文中\"" + filedName
                        + "\"信息为空!");
                return false;
            }

        }
        return true;
    }

    private boolean isNullProperty(Object obj, String name) throws Exception {
        Object tmpObj = PropertyUtils.getProperty(obj, name);
        if (tmpObj instanceof String) {
            return StringUtil.isNull((String) tmpObj)
                    || "null".equalsIgnoreCase((String) tmpObj);
        } else if (tmpObj instanceof Integer) {
            return ((Integer) tmpObj) == 0;
        } else {
            return tmpObj == null;
        }
    }


}
