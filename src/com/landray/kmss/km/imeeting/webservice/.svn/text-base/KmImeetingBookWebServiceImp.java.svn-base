package com.landray.kmss.km.imeeting.webservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.annotation.RestApi;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.List;

/**
 * 会议室预定
 */
@Controller
@RequestMapping(value = "/api/km-imeeting/kmImeetingBookWebService",method = RequestMethod.POST)
@RestApi(
        docUrl = "/km/imeeting/restService/KmImeetingBookRestHelp.jsp",
        name = "kmImeetingBookWebService",
        resourceKey = "km-imeeting:kmImeetingBook.rest"
)
public class KmImeetingBookWebServiceImp implements IKmImeetingBookWebService,KmImeetingWebServiceConstant{
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmImeetingBookWebServiceImp.class);

    private IKmImeetingBookService kmImeetingBookService;
    public void setKmImeetingBookService(IKmImeetingBookService kmImeetingBookService) {
        this.kmImeetingBookService = kmImeetingBookService;
    }

    private IKmImeetingResService kmImeetingResService;
    public void setKmImeetingResService(IKmImeetingResService kmImeetingResService) {
        this.kmImeetingResService = kmImeetingResService;
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


    /**
     * 查询指定人员的会议预定
     * @param context
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "getImeetingBook", method = RequestMethod.GET)
    public KmImeetingBookResulut getImeetingBook(KmImeetingBookContext context) throws Exception {
        KmImeetingBookResulut result= new KmImeetingBookResulut();
        //指定人员
        if (StringUtil.isNotNull(context.getTargets())) {
            SysOrgElement targetPerson = sysWsOrgService
                    .findSysOrgElement(context.getTargets());
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("kmImeetingBook.docCreator.fdId =:targetId");
            hqlInfo.setParameter("targetId",targetPerson.getFdId());
            List<KmImeetingBook> list = kmImeetingBookService.findList(hqlInfo);
            if(!ArrayUtil.isEmpty(list)){
                JSONArray jsonArr = new JSONArray();
                for(KmImeetingBook kmImeetingBook:list){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("fdName",String.valueOf(StringUtil.getString(kmImeetingBook.getFdName()))); //会议名称
                    jsonObject.put("fdHoldDate",DateUtil.convertDateToString(kmImeetingBook.getFdHoldDate(),DateUtil.PATTERN_DATETIME)); //召开时间
                    jsonObject.put("fdFinishDate",DateUtil.convertDateToString(kmImeetingBook.getFdFinishDate(),DateUtil.PATTERN_DATETIME)); //结束时间
                    jsonObject.put("fdHoldDuration",kmImeetingBook.getFdHoldDuration()); //会议历时
                    jsonObject.put("fdPlace",kmImeetingBook.getFdPlace().getFdName()); //会议地点
                    jsonObject.put("fdRemark",String.valueOf(StringUtil.getString(kmImeetingBook.getFdRemark()))); //会议备注
                    jsonArr.add(jsonObject);
                }
                result.setMessage(jsonArr.toJSONString()); //数据
                result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
                result.setCount(list.size());
            }else{
                logger.debug("很抱歉，未找到相应的记录");
                result.setMessage(ResourceUtil.getString("kmImeeting.webservice.none.error","km-imeeting"));
                result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
                result.setCount(0);
            }
        }
        return result;
    }

    /**
     * 根据召开时间和结束时间获取该时间段会议预定列表
     * @param form
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "getImeetingBookLists", method = RequestMethod.GET)
    public KmImeetingBookResulut getImeetingBookLists(KmImeetingBookParamterForm form) throws Exception {
        KmImeetingBookResulut result = new KmImeetingBookResulut();
        //判断必填字段是否填写
        if (!checkNullIfNecessary(form, METHOD_CONSTANT_NAME_BOOKLIST,
                result)) {
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL); //失败
        }
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock="1=1";
        if (StringUtil.isNotNull(form.getFdHoldDate())) {
            Date fdHoldDate = DateUtil.convertStringToDate(form
                    .getFdHoldDate(), DateUtil.PATTERN_DATETIME);
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    " kmImeetingBook.fdHoldDate>=:fdStartDate ");
            hqlInfo.setParameter("fdStartDate",fdHoldDate);
        }
        if (StringUtil.isNotNull(form.getFdFinishDate())){
            Date fdFinishDate = DateUtil.convertStringToDate(form
                    .getFdFinishDate(), DateUtil.PATTERN_DATETIME);
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    " kmImeetingBook.fdFinishDate<=:fdEndDate ");
            hqlInfo.setParameter("fdEndDate",fdFinishDate);
        }
        hqlInfo.setWhereBlock(whereBlock);
        List<KmImeetingBook> list = kmImeetingBookService.findList(hqlInfo);
        if(!ArrayUtil.isEmpty(list)){
            JSONArray jsonArr = new JSONArray();
            for(KmImeetingBook kmImeetingBook:list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fdName",String.valueOf(StringUtil.getString(kmImeetingBook.getFdName()))); //会议名称
                jsonObject.put("fdHoldDate",kmImeetingBook.getFdHoldDate()); //召开时间
                jsonObject.put("fdFinishDate",kmImeetingBook.getFdFinishDate()); //结束时间
                jsonObject.put("fdHoldDuration",kmImeetingBook.getFdHoldDuration()); //会议历时
                jsonObject.put("fdPlace",kmImeetingBook.getFdPlace().getFdName()); //会议地点
                jsonObject.put("fdRemark",String.valueOf(StringUtil.getString(kmImeetingBook.getFdRemark()))); //会议备注
                jsonArr.add(jsonObject);
            }
            result.setMessage(jsonArr.toJSONString()); //数据
            result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
            result.setCount(list.size());
        }else{
            logger.debug("很抱歉，未找到相应的记录");
            result.setMessage(ResourceUtil.getString("kmImeeting.webservice.none.error","km-imeeting"));
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            result.setCount(0);
        }
        return result;
    }

    /**
     *  根据会议预定的fdId查询会议预定信息
     * @param fdId
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "getImeetingBookDetail", method = RequestMethod.GET)
    public KmImeetingBookResulut getImeetingBookDetail(String fdId) throws Exception {
        KmImeetingBookResulut result = new KmImeetingBookResulut();
        if(StringUtil.isNull(fdId)){
            result.setMessage(fdId+ ",会议预定ID为空!");
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }
        KmImeetingBook kmImeetingBook = (KmImeetingBook) kmImeetingBookService.findByPrimaryKey(fdId,null,true);
        if(kmImeetingBook ==null){
            result.setCount(1);
            result.setMessage(fdId+",会议预定ID不存在"); //数据
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
        }else {
            JSONArray jsonArr = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fdName", String.valueOf(StringUtil.getString(kmImeetingBook.getFdName()))); //会议名称
                jsonObject.put("fdHoldDate", kmImeetingBook.getFdHoldDate()); //召开时间
                jsonObject.put("fdFinishDate", kmImeetingBook.getFdFinishDate()); //结束时间
                jsonObject.put("fdHoldDuration", kmImeetingBook.getFdHoldDuration()); //会议历时
                jsonObject.put("fdPlace", kmImeetingBook.getFdPlace().getFdName()); //会议地点
                jsonObject.put("fdRemark", String.valueOf(StringUtil.getString(kmImeetingBook.getFdRemark()))); //会议备注
                jsonArr.add(jsonObject);
                result.setCount(1);
                result.setMessage(jsonArr.toJSONString()); //数据
                result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
        }
        return result;
    }

    /**
     * 根据会议室ID查询该会议室-所有预定信息
     * @param fdId 会议室ID
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "getImeetingBookById", method = RequestMethod.GET)
    public KmImeetingBookResulut getImeetingBookById(String fdId) throws Exception {
        KmImeetingBookResulut result = new KmImeetingBookResulut();
        if(StringUtil.isNull(fdId)){
            result.setMessage(fdId+ "会议室ID为空!");
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmImeetingBook.fdPlace.fdId =:placeId");
        hqlInfo.setParameter("placeId", fdId);
        List<KmImeetingBook> bookResult = kmImeetingBookService.findList(hqlInfo);
        if(!ArrayUtil.isEmpty(bookResult)){
            JSONArray jsonArr = new JSONArray();
            for(KmImeetingBook kmImeetingBook:bookResult){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fdName",String.valueOf(StringUtil.getString(kmImeetingBook.getFdName()))); //会议名称
                jsonObject.put("fdHoldDate",kmImeetingBook.getFdHoldDate()); //召开时间
                jsonObject.put("fdFinishDate",kmImeetingBook.getFdFinishDate()); //结束时间
                jsonObject.put("fdHoldDuration",kmImeetingBook.getFdHoldDuration()); //会议历时
                jsonObject.put("fdPlace",kmImeetingBook.getFdPlace().getFdName()); //会议地点
                jsonObject.put("fdRemark",String.valueOf(StringUtil.getString(kmImeetingBook.getFdRemark()))); //会议备注
                jsonArr.add(jsonObject);
            }
            result.setMessage(jsonArr.toJSONString()); //数据
            result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
            result.setCount(bookResult.size());
        }else{
            logger.debug("很抱歉，未找到相应的记录");
            result.setMessage(ResourceUtil.getString("kmImeeting.webservice.none.error","km-imeeting"));
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            result.setCount(0);
        }
        return result;
    }

    /**
     * 预约会议室
     * @param form
     * @return
     * @throws Exception
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "addImeetingBook")
    public KmImeetingBookResulut addImeetingBook(KmImeetingBookParamterForm form) throws Exception {
        KmImeetingBookResulut result = new KmImeetingBookResulut();
        SysOrgElement creator = null;
        try {
            creator = sysWsOrgService.findSysOrgElement(form.getDocCreator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(creator ==null){
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            result.setMessage(form.getDocCreator() + "用户不存在");
            return result;
        }
        //判断必填字段是否填写
        if (!checkNullIfNecessary(form, METHOD_CONSTANT_NAME_ADDBOOK,
                result)) {
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL); //失败
        }

        return (KmImeetingBookResulut) backgroundAuthService.switchUserById(creator.getFdId(),
                new Runner() {
                    @Override
                    public Object run(Object parameter) throws Exception {
                        KmImeetingBookParamterForm form = (KmImeetingBookParamterForm) parameter;
                        KmImeetingBookResulut result = new KmImeetingBookResulut();// 返回结果
                        // 对象字段校验
                        if (!checkNullIfNecessary(form,METHOD_CONSTANT_NAME_ADDBOOK, result)) {
                            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
                            return result;
                        }
                        KmImeetingBook kmImeetingBook = new KmImeetingBook();
                        kmImeetingBook.setFdName(form.getFdName()); //会议名称
                        kmImeetingBook.setDocCreator((SysOrgPerson) sysWsOrgService.findSysOrgElement(form.getDocCreator())); //创建人
                        KmImeetingRes kmImeetingRes = (KmImeetingRes) kmImeetingResService.findByPrimaryKey(form.getFdPlace(),null,true);
                        if(kmImeetingRes!=null) {
                            kmImeetingBook.setFdPlace(kmImeetingRes);//会议地点
                        }else{
                            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
                            result.setMessage(ResourceUtil.getString(
                                    "kmImeeting.webservice.fdplace.error", "km-imeeting"));
                            return result;
                        }
                        Date fdHoldDate = DateUtil.convertStringToDate(form
                                .getFdHoldDate(), DateUtil.PATTERN_DATETIME);
                        Date fdFinishDate = DateUtil.convertStringToDate(form
                                .getFdFinishDate(), DateUtil.PATTERN_DATETIME);
                        kmImeetingBook.setFdHoldDate(fdHoldDate); //召开时间
                        kmImeetingBook.setFdFinishDate(fdFinishDate); //结束时间
                        if(form.getFdHoldDuration() !=null) {
                            kmImeetingBook.setFdHoldDuration(Double.valueOf(form.getFdHoldDuration()));//会议历时
                        }
                        String modelId = kmImeetingBookService.add(kmImeetingBook);

                        JSONObject message = new JSONObject();
                        message.put("fdId", modelId);
                        result.setMessage(message.toString());
                        result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
                        return result;
                    }
                }, form);
    }

    /**
     * 删除会议室预定
     * @param form
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "deleteImeetingBook")
    public KmImeetingBookResulut deleteImeetingBook(KmImeetingBookParamterForm form) throws Exception {
        KmImeetingBookResulut result = new KmImeetingBookResulut();// 返回结果
        //判断必填字段是否填写
        if (!checkNullIfNecessary(form, METHOD_CONSTANT_NAME_CANCELBOOK,
                result)) {
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL); //失败
        }
        // 切换当前用户
        SysOrgElement creator = sysWsOrgService
                .findSysOrgElement(form.getDocCreator());
        if (creator != null) {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("kmImeetingBook.fdId=:bookId and kmImeetingBook.docCreator.fdId =:creatorId");
            hqlInfo.setParameter("bookId", form.getFdId());
            hqlInfo.setParameter("creatorId", creator.getFdId());
            List<KmImeetingBook> list = kmImeetingBookService.findList(hqlInfo);
            if(!ArrayUtil.isEmpty(list)){
                KmImeetingBook kmImeetingBook = list.get(0);
                kmImeetingBookService.delete(kmImeetingBook); //删除
                result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
                result.setMessage("删除成功！");
            }
        }else{
            result.setMessage(form.getDocCreator() + "用户不存在");
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
        }
        return result;
    }

    /**
     * 变更会议室预定
     * @param bookform
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "updateImeetingBook")
    public KmImeetingBookResulut updateImeetingBook(KmImeetingBookParamterForm bookform) throws Exception {
        KmImeetingBookResulut result = new KmImeetingBookResulut();// 返回结果
        // 切换当前用户
        SysOrgElement creator = null;
        try {
            creator = sysWsOrgService
                    .findSysOrgElement(bookform.getDocCreator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (creator == null) {
            result.setMessage(bookform.getDocCreator() + "用户不存在");
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }
        // 对象字段校验
        if (!checkNullIfNecessary(bookform,METHOD_CONSTANT_NAME_UPDATERES, result)) {
            result.setResultState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }
        return (KmImeetingBookResulut) backgroundAuthService.switchUserById(creator
                        .getFdId(), new Runner() {
            @Override
            public Object run(Object parameter) throws Exception {
                KmImeetingBookParamterForm form = (KmImeetingBookParamterForm) parameter;
                KmImeetingBookResulut result = new KmImeetingBookResulut();// 返回结果
                KmImeetingBook kmImeetingBook = new KmImeetingBook();
                kmImeetingBook.setFdName(form.getFdName()); //会议名称
                kmImeetingBook.setDocCreator(sysWsOrgService.findSysOrgElement(bookform.getDocCreator())); //创建人
                KmImeetingRes kmImeetingRes = (KmImeetingRes) kmImeetingResService.findByPrimaryKey(form.getFdPlace(),null,true);
                if(kmImeetingRes!=null) {
                    kmImeetingBook.setFdPlace(kmImeetingRes);//会议地点
                }
                Date fdHoldDate = DateUtil.convertStringToDate(form
                        .getFdHoldDate(), DateUtil.PATTERN_DATETIME);
                Date fdFinishDate = DateUtil.convertStringToDate(form
                        .getFdFinishDate(), DateUtil.PATTERN_DATETIME);
                kmImeetingBook.setFdHoldDate(fdHoldDate); //召开时间
                kmImeetingBook.setFdFinishDate(fdFinishDate); //结束时间
                if(form.getFdHoldDuration() !=null) {
                    kmImeetingBook.setFdHoldDuration(Double.valueOf(form.getFdHoldDuration()));
                }
                kmImeetingBookService.update(kmImeetingBook);
                result.setResultState(RETURN_CONSTANT_STATUS_SUCESS);
                return result;
            }
        }, bookform);
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
                                         String methodKey, KmImeetingBookResulut result) throws Exception {
        if (null == context) {
            result.setMessage(ResourceUtil.getString("kmImeetingBook.webservice.warning","km-imeeting"));
            logger.debug("会议预定上下文为空");
            return false;
        }
        String fields = "";
        if(METHOD_CONSTANT_NAME_BOOKLIST.equals(methodKey)){
            fields="fdHoldDate;fdFinishDate";
        }else if(METHOD_CONSTANT_NAME_ADDBOOK.equals(methodKey)) {
            fields = "fdName;fdHoldDate;fdFinishDate;fdPlace;docCreator";
        }else if(METHOD_CONSTANT_NAME_UPDATERES.equals(methodKey)){
                fields="fdName;fdHoldDate;fdFinishDate;fdPlace;docCreator";
        }else if(METHOD_CONSTANT_NAME_CANCELBOOK.equals(methodKey)){
            fields="fdId;docCreator";
        }
        String[] fileArr = fields.split(";");
        for (int i = 0; i < fileArr.length; i++) {
            if (StringUtil.isNull(fileArr[i])) {
                continue;
            }
            if (isNullProperty(context, fileArr[i])) {
                String filedName = ResourceUtil.getString(
                        "kmImeetingBook.webservice." + fileArr[i], "km-imeeting");
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
