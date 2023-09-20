package com.landray.kmss.third.weixin.work.action;


import com.landray.kmss.common.actions.RequestContext;
import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.third.weixin.work.forms.ThirdWeixinWorkLivingForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkLiving;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkLivingService;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

import java.util.List;


public class ThirdWeixinWorkLivingAction extends ExtendAction {

    private static final Logger logger = org.slf4j.LoggerFactory
            .getLogger(ThirdWeixinWorkLivingAction.class);

    private IThirdWeixinWorkLivingService thirdWeixinWorkLivingService;

    private IWxworkOmsRelationService wxworkOmsRelationService;

    public IWxworkOmsRelationService getWxworkOmsRelationService(){
        if (wxworkOmsRelationService == null) {
            wxworkOmsRelationService = (IWxworkOmsRelationService) getBean("wxworkOmsRelationService");
        }
        return wxworkOmsRelationService;
    }


    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinWorkLivingService == null) {
            thirdWeixinWorkLivingService = (IThirdWeixinWorkLivingService) getBean("thirdWeixinWorkLivingService");
        }
        return thirdWeixinWorkLivingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinWorkLiving.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkLiving.class);
        ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinWorkLivingForm thirdWeixinWorkLivingForm = (ThirdWeixinWorkLivingForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinWorkLivingService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinWorkLivingForm;
    }

    /*
     * 创建直播映射关系
     */
    public void createMapping(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();
        try {
            String modelName = request.getParameter("modelName");
            String modelId = request.getParameter("modelId");
            if(StringUtil.isNull(modelName) || StringUtil.isNull(modelId)){
                logger.warn("modelName:{},modelId:{}  ,不允许为空",modelName,modelId);
                json.put("result","fail:modelName或modelId为空");
            }else{
                ThirdWeixinWorkLiving living = new ThirdWeixinWorkLiving();
                living.setFdLivingId(request.getParameter("livingId"));
                living.setFdModelName(modelName);
                living.setFdModelId(modelId);
                living.setFdName(request.getParameter("theme"));
                String id = getServiceImp(request).add(living);
                System.out.println("id:"+id);
                json.put("result","ok");
                json.put("id",id);
            }
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
           json.put("result","fail:"+e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 检查直播是否存在
     */
    public void checkLiving(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        json.put("exist",false);
        json.put("success",false);
        try {
            String modelName = request.getParameter("modelName");
            String modelId = request.getParameter("modelId");
            if(StringUtil.isNull(modelName) || StringUtil.isNull(modelId)){
                logger.warn("modelName:{},modelId:{}  ,不允许为空",modelName,modelId);
                json.put("result","fail:modelName或modelId为空");
            }else{
                List<ThirdWeixinWorkLiving> list =findByModel(request,modelName,modelId);
                if(list != null && list.size() > 0){
                    json.put("exist",true);
                    json.put("result","ok,存在对应的直播:"+list.get(0).getFdLivingId());
                    json.put("livingId",list.get(0).getFdLivingId());
                }else{
                    json.put("result","ok,不存在对应的直播");
                }
                json.put("success",true);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            json.put("result","fail:"+e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 检查直播是否存在
     */
    public void canLiving(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        json.put("success",true);
        try {
            String wxEnable=WeixinWorkConfig.newInstance().getWxEnabled();
            String wxLivingEnabled=WeixinWorkConfig.newInstance().getWxLivingEnabled();
            if(StringUtil.isNotNull(wxEnable)&&"true".equalsIgnoreCase(wxEnable)&&
                    StringUtil.isNotNull(wxLivingEnabled)&&"true".equalsIgnoreCase(wxLivingEnabled)){
                json.put("result","ok");
            }else{
                json.put("success",false);
                json.put("result","fail:企业微信集成或直播开关 未开启");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            json.put("success",false);
            json.put("result","fail:"+e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 创建直播 (安全问题，按时屏蔽js直接调用)
     * 参数：{"modelName":"com.landray.kmss.km.imeeting.model.KmImeetingMain",
     *         "modelId":"主文档ID","anchor_userid":"ekp发起人fdId","theme":"直播标题",
     *         ,"living_start":"直播时间yyyy-MM-dd HH:mm:ss","living_duration":"直播持续时间，单位秒",
     *         "type":"类型：0：通用直播，1：小班课，2：大班课，3：企业培训，4：活动直播，默认 0",
     *         "remind_time":"直播前提醒时间，单位秒","description":"描述",}
     */
   /* public void createLiving(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        json.put("success",false);
        response.setCharacterEncoding("UTF-8");
        try {
            String modelName = request.getParameter("modelName");
            String modelId = request.getParameter("modelId");
            if(StringUtil.isNull(modelName) || StringUtil.isNull(modelId)){
                logger.warn("modelName:{},modelId:{}  ,不允许为空",modelName,modelId);
                json.put("result","fail:modelName或modelId为空");
            }else{

                WxLiving wxLiving = new WxLiving();
                //必填字段
                String anchor_userid = request.getParameter("anchor_userid");
                String theme = request.getParameter("theme");
                String living_start = request.getParameter("living_start"); //2021-11-26 15:00:00
                String living_duration = request.getParameter("living_duration");  //单位是s

                //校验
                boolean isFlag = checkField(anchor_userid,theme,living_start,living_duration,json);
                if(isFlag){
                    WxworkOmsRelationModel relationModel = getWxworkOmsRelationService().findByEkpId(anchor_userid);
                    if(relationModel!=null){
                        //发起人
                        wxLiving.setAnchorUserid(relationModel.getFdAppPkId());
                        //描述
                        String description = request.getParameter("description");
                        if(StringUtil.isNotNull(description)){
                            if(description.length()>300)
                                description=description.substring(0,280)+"...";
                            wxLiving.setDescription(description);
                        }
                        //类型：0：通用直播，1：小班课，2：大班课，3：企业培训，4：活动直播，默认 0
                        String type = request.getParameter("type");
                        if(StringUtil.isNotNull(type)){
                            wxLiving.setType(Integer.valueOf(type));
                        }
                        //标题
                        if(theme.length()>60)
                            theme= theme.substring(0,55)+"...";
                        wxLiving.setTheme(theme);
                        //直播开始时间
                        Long startTime = DateUtil.convertStringToDate(living_start,"yyyy-MM-dd HH:mm:ss").getTime();
                        wxLiving.setLivingStart(startTime/1000);
                        //直播持续时间
                        wxLiving.setLivingDuration(Integer.valueOf(living_duration));
                        //提醒时间
                        String remind_time = request.getParameter("remind_time");
                        if(StringUtil.isNotNull(remind_time)){
                            wxLiving.setRemind_time(Integer.valueOf(remind_time));
                        }
                        JSONObject result =  WxworkUtils.getWxworkApiService().createLiving(wxLiving);
                        logger.warn("直播创建结果："+result);
                        //建立映射关系
                        if(result!=null&&result.getInteger("errcode")==0){
                            json.put("livingId",result.getString("livingid"));
                            ThirdWeixinWorkLiving thirdWeixinWorkLiving = new ThirdWeixinWorkLiving(theme,modelName,modelId,result.getString("livingid"));
                            getServiceImp(request).add(thirdWeixinWorkLiving);
                            json.put("success",true);
                        }
                    }else{
                        json.put("result","fail:直播发起人(id:"+anchor_userid+")的映射关系异常");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            json.put("result","fail:"+e.getMessage());
        }
        response.getWriter().append(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }
*/
    private boolean checkField(String anchor_userid, String theme, String living_start, String living_duration, JSONObject json) {

        StringBuffer str = new StringBuffer();
        boolean accessFlag=true;
        if(StringUtil.isNull(anchor_userid)){
            str.append("发起人anchor_userid为空;");
            accessFlag=false;
        }
        if(StringUtil.isNull(theme)){
            str.append("标题theme为空;");
            accessFlag=false;
        }
        if(StringUtil.isNull(living_start)){
            str.append("开始时间living_start为空;");
            accessFlag=false;
        }
        if(StringUtil.isNull(living_duration)){
            str.append("直播持续时长living_duration为空;");
            accessFlag=false;
        }
        if(!accessFlag) {
            json.put("result","fail:"+str.toString());
        }
        return accessFlag;
    }

    private List<ThirdWeixinWorkLiving> findByModel(HttpServletRequest request, String modelName, String modelId) {
        try {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("fdModelName = :fdModelName and fdModelId=:fdModelId");
            hqlInfo.setParameter("fdModelName",modelName);
            hqlInfo.setParameter("fdModelId",modelId);
            hqlInfo.setOrderBy("docCreateTime desc");
            return getServiceImp(request).findList(hqlInfo);
        } catch (Exception e) {
            logger.warn(e.getMessage(),e);
            return null;
        }

    }


}
