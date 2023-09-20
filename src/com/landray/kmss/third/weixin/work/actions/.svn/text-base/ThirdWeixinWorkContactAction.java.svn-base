package com.landray.kmss.third.weixin.work.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.weixin.work.action.ThirdWeixinWorkAction;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinAuthLogForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinAuthLog;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinAuthLogService;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkContactService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdWeixinWorkContactAction extends ExtendAction {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWeixinWorkContactAction.class);

    @Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
        return null;
    }

    private IThirdWeixinWorkContactService thirdWeixinWorkContactService;

    private IThirdWeixinWorkContactService getThirdWeixinWorkContactService(){
        if(thirdWeixinWorkContactService==null){
            thirdWeixinWorkContactService = (IThirdWeixinWorkContactService) SpringBeanUtil.getBean("thirdWeixinWorkContactService");
        }
        return thirdWeixinWorkContactService;
    }

    public ActionForward getGroupTags(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-getGroupTags", true, getClass());
        JSONObject result = new JSONObject();
        JSONArray tags_all = new JSONArray();
        try {
            String group_id = request.getParameter("group_id");
            String[] groupIds = group_id.split(";");
            com.alibaba.fastjson.JSONArray groupIdArray = new com.alibaba.fastjson.JSONArray();
            for(String groupId:groupIds){
                groupId = groupId.substring(6);
                groupIdArray.add(groupId);
            }
            WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
            com.alibaba.fastjson.JSONObject obj = wxworkApiService.listCorpTag(groupIdArray);
            if(obj!=null && obj.getIntValue("errcode")==0){
                com.alibaba.fastjson.JSONArray tagGroups = obj.getJSONArray("tag_group");
                for(int i=0;i<tagGroups.size();i++){
                    com.alibaba.fastjson.JSONObject tagGroup = tagGroups.getJSONObject(i);
                    com.alibaba.fastjson.JSONArray tags = tagGroup.getJSONArray("tag");
                    tags_all.addAll(tags);
                }
            }
            result.put("status", "1");
            result.put("tags", tags_all);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("status", "0");
            result.put("errmsg", e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result);
        return null;
    }

    public ActionForward synchro2ekp(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-synchro2ekp", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            getThirdWeixinWorkContactService().synchro2ekp();
        } catch (Exception e) {
            messages.addError(e);
        }
        KmssReturnPage.getInstance(request).addMessages(messages).addButton(
                KmssReturnPage.BUTTON_CLOSE).save(request);
        TimeCounter.logCurrentTime("Action-synchro2ekp", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }


}
