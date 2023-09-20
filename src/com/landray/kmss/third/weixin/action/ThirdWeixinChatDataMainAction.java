package com.landray.kmss.third.weixin.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.forms.ThirdWeixinChatDataMainForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatDataMainService;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThirdWeixinChatDataMainAction extends ExtendAction {

    private static final Logger logger = LoggerFactory.getLogger(ThirdWeixinChatDataMainAction.class);

    private IThirdWeixinChatDataMainService thirdWeixinChatDataMainService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinChatDataMainService == null) {
            thirdWeixinChatDataMainService = (IThirdWeixinChatDataMainService) getBean("thirdWeixinChatDataMainService");
        }
        return thirdWeixinChatDataMainService;
    }

    private ISysAttMainCoreInnerService sysAttMainService;
    protected ISysAttMainCoreInnerService getSysAttMainService() {
        if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
        }
        return sysAttMainService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinChatDataMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain.class);
        ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinChatDataMainForm thirdWeixinChatDataMainForm = (ThirdWeixinChatDataMainForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinChatDataMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinChatDataMainForm;
    }

    private String getMsgTimeStr(Long msgTime){
        if(msgTime!=null){
            Date date = new Date(msgTime);
            return DateUtil.convertDateToString(date,"yyyyMMdd HH:mm:ss");
        }
        return null;
    }

    private String getPropValue(ThirdWeixinChatDataMain thirdWeixinChatDataMain, Cipher decrypter, String prop) throws Exception {
        String value = (String)PropertyUtils.getProperty(thirdWeixinChatDataMain,prop);
        if(StringUtil.isNull(value)){
            return value;
        }
        if(thirdWeixinChatDataMain.getFdEncryType()==3){
            value = ChatdataUtil.decry(decrypter,value);
        }
        return value;
    }

    private JSONObject buildChatDataObj(ThirdWeixinChatDataMain main,Cipher decrypter) throws Exception {
        JSONObject o = new JSONObject();
        o.put("fdMsgType",main.getFdMsgType());
        o.put("fdId",main.getFdId());
        o.put("fdFrom",main.getFdFrom());
        String fdFromName = main.getFdFromName();
        String fdFromNameImage = "";
        if(StringUtil.isNull(fdFromName)){
            fdFromName = main.getFdFrom();
            fdFromNameImage = fdFromName;
        }else{
            if(fdFromName.length()>2){
                fdFromNameImage = fdFromName.substring(fdFromName.length()-2);
            }
        }
        o.put("fdFromName",fdFromName);
        o.put("fdFromNameImage",fdFromNameImage);
        o.put("fdSeq",main.getFdSeq());
        o.put("fdMsgId",main.getFdMsgId());
        o.put("fdContent",getPropValue(main,decrypter,"fdContent"));
        o.put("fdTitle",getPropValue(main,decrypter,"fdTitle"));
        o.put("fdExtendContent",getPropValue(main,decrypter,"fdExtendContent"));
        o.put("fdMsgTime",getMsgTimeStr(main.getFdMsgTime()));
        o.put("fdLinkUrl",getPropValue(main,decrypter,"fdLinkUrl"));
        if(StringUtil.isNotNull(main.getFdFileId())){
            List<SysAttMain> atts = getSysAttMainService().findAttListByModel(ThirdWeixinChatDataMain.class.getName(),main.getFdId());
            if(atts!=null && !atts.isEmpty()){
                o.put("attId",atts.get(0).getFdId());
            }
        }
        o.put("fdCorpName",main.getFdCorpName());
        o.put("fdUserId",main.getFdUserId());
        o.put("fdUsername",main.getFdUsername());
        if(main.getFdUsername()!=null && main.getFdUsername().length()>2){
            o.put("fdUsernameImage",main.getFdUsername().substring(main.getFdUsername().length()-2));
        }
        if("chatrecord".equals(main.getFdMsgType()) || "mixed".equals(main.getFdMsgType())){
            JSONObject extendContentObj = JSONObject.fromObject(o.getString("fdExtendContent"));
            JSONArray items = extendContentObj.getJSONArray("item");
            if(items!=null){
                for(int i=0;i<items.size();i++){
                    JSONObject item = items.getJSONObject(i);
                    String type = item.getString("type");
                    if("ChatRecordImage".equals(type) || "ChatRecordFile".equals(type) || "ChatRecordVideo".equals(type) || "ChatRecordEmotion".equals(type) || "ChatRecordVoice".equals(type) || "image".equals(type) || "file".equals(type) || "video".equals(type) || "voice".equals(type) || "emotion".equals(type)){
                        String innerContent = item.getString("content");
                        if(StringUtil.isNull(innerContent)){
                            continue;
                        }
                        JSONObject innerContentObj = JSONObject.fromObject(innerContent);
                        String sdkfileid = innerContentObj.getString("sdkfileid");
                        if(StringUtil.isNull(sdkfileid)){
                            continue;
                        }
                        String fdKey = sdkfileid;
                        if(fdKey.length()>450){
                            fdKey = fdKey.substring(0,450);
                        }
                        List<SysAttMain> atts = getSysAttMainService().findByModelKey(ThirdWeixinChatDataMain.class.getName(),main.getFdId(),fdKey);
                        if(atts!=null && !atts.isEmpty()){
                            innerContentObj.put("attId",atts.get(0).getFdId());
                        }
                        item.put("content",innerContentObj.toString());
                    }
                }
            }
            o.put("fdExtendContent",extendContentObj.toString());
        }else if("calendar".equals(main.getFdMsgType())){
            JSONObject calendar = JSONObject.fromObject(o.getString("fdExtendContent"));
            Long starttime = calendar.getLong("starttime");
            Long endtime = calendar.getLong("endtime");
            String starttimeStr = starttime==null?"":DateUtil.convertDateToString(new Date(starttime),"yyyyMMdd HH:mm");
            String endtimeStr = endtime==null?"":DateUtil.convertDateToString(new Date(endtime),"yyyyMMdd HH:mm");
            String time = starttimeStr +" - " +endtimeStr;
            calendar.put("time",time);
            o.put("fdExtendContent",calendar.toString());
        }
        return o;
    }

    public ActionForward listChatData(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-listChatData", true, getClass());
        JSONObject result = new JSONObject();
        try {
            //+"&groupId="+groupId+"&searchText="+searchText+"&startTime="+startTime+"&endTime="+endTime+"&msgType="+msgType+"&startMsgSeq="+startMsgSeq+"&endMsgSeq="+endMsgSeq;
            int rowSize = 20;

            String start = request.getParameter("startTime");
            String end = request.getParameter("endTime");
            String msgType = request.getParameter("msgType");
            String searchText = request.getParameter("searchText");
            String startMsgSeq =  request.getParameter("startMsgSeq");
            String endMsgSeq =  request.getParameter("endMsgSeq");
            String currentMsgSeq = request.getParameter("currentMsgSeq");
            String groupId = request.getParameter("groupId");
            if(StringUtil.isNull(groupId)){
                throw new Exception("groupId 不能为空");
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setOrderBy("fdSeq desc");
            hqlInfo.setPageNo(0);
            hqlInfo.setRowSize(rowSize);
            String whereBlock = "fdChatGroup.fdId = :groupId ";
            hqlInfo.setParameter("groupId",groupId);
            if(StringUtil.isNotNull(msgType) && !"all".equals(msgType)){
                if("image".equals(msgType)){
                    whereBlock += " and (fdMsgType ='image' or fdMsgType='video')";
                }else{
                    whereBlock += " and (fdMsgType = :msgType)";
                    hqlInfo.setParameter("msgType",msgType);
                }
            }
            if(StringUtil.isNotNull(startMsgSeq)){
                whereBlock += " and (fdSeq < :startMsgSeq)";
                hqlInfo.setParameter("startMsgSeq",Long.parseLong(startMsgSeq));
                hqlInfo.setOrderBy("fdSeq desc");
            }else if(StringUtil.isNotNull(endMsgSeq)){
                whereBlock += " and (fdSeq > :endMsgSeq)";
                hqlInfo.setParameter("endMsgSeq",Long.parseLong(endMsgSeq));
            }else if(StringUtil.isNotNull(currentMsgSeq)){
                whereBlock += " and (fdSeq <= :currentMsgSeq)";
                hqlInfo.setParameter("currentMsgSeq",Long.parseLong(currentMsgSeq));
                hqlInfo.setOrderBy("fdSeq desc");
            }
            Date startDate = DateUtil.convertStringToDate(start);
            Date endDate = DateUtil.convertStringToDate(end);

            if(endDate!=null){
                Calendar c = Calendar.getInstance();
                c.setTime(endDate);
                c.add(Calendar.DATE,1);
                endDate = c.getTime();
            }
            if(startDate==null){
                if(endDate!=null){
                    whereBlock += " and fdMsgTime< :end";
                    hqlInfo.setParameter("end",endDate.getTime());
                }
            }else{
                if(endDate==null){
                    whereBlock += " and fdMsgTime>= :start";
                    hqlInfo.setParameter("start",startDate.getTime());
                }else{
                    whereBlock += " and fdMsgTime between :start and :end";
                    hqlInfo.setParameter("start",startDate.getTime());
                    hqlInfo.setParameter("end",endDate.getTime());
                }
            }
            if(StringUtil.isNotNull(searchText)){
                whereBlock += " and (fdFromName=:fromName or fdTitle like :title or fdContent like :content)";
                hqlInfo.setParameter("fromName",searchText);
                hqlInfo.setParameter("title","%"+searchText+"%");
                hqlInfo.setParameter("content","%"+searchText+"%");
            }
            hqlInfo.setWhereBlock(whereBlock);
            Page page = getServiceImp(request).findPage(hqlInfo);
            List<ThirdWeixinChatDataMain> list = page.getList();
            result.put("hasMore",false);
            JSONArray datas = new JSONArray();
            if(list!=null){
                Cipher decrypter = ChatdataUtil.getDecrypter();
                if(list.size()>=rowSize){
                    result.put("hasMore",true);
                }
                for(ThirdWeixinChatDataMain main:list){
                    datas.add(buildChatDataObj(main,decrypter));
                }
            }
            result.put("datas",datas);
            result.put("success",true);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.put("success",false);
            result.put("errorMsg",e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result.toString());
        TimeCounter.logCurrentTime("Action-listChatData", true, getClass());
        return null;
    }
}
