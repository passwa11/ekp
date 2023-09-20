package com.landray.kmss.third.weixin.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.forms.ThirdWeixinChatGroupForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatDataMainService;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatGroupService;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThirdWeixinChatGroupAction extends ExtendAction {

    private IThirdWeixinChatGroupService thirdWeixinChatGroupService;

    private IThirdWeixinChatDataMainService thirdWeixinChatDataMainService;

    private ISysOrgElementService sysOrgElementService;

    private ISysOrgCoreService sysOrgCoreService;

    private IWxworkOmsRelationService wxworkOmsRelationService;

    public IWxworkOmsRelationService getWxworkOmsRelationService() {
        if (wxworkOmsRelationService == null) {
            wxworkOmsRelationService = (IWxworkOmsRelationService) getBean("wxworkOmsRelationService");
        }
        return wxworkOmsRelationService;
    }

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinChatGroupService == null) {
            thirdWeixinChatGroupService = (IThirdWeixinChatGroupService) getBean("thirdWeixinChatGroupService");
        }
        return thirdWeixinChatGroupService;
    }

    public IThirdWeixinChatDataMainService getThirdWeixinChatDataMainService(){
        if (thirdWeixinChatDataMainService == null) {
            thirdWeixinChatDataMainService = (IThirdWeixinChatDataMainService) getBean("thirdWeixinChatDataMainService");
        }
        return thirdWeixinChatDataMainService;
    }

    public ISysOrgElementService getSysOrgElementService(){
        if(sysOrgElementService==null){
            sysOrgElementService = (ISysOrgElementService)getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    public ISysOrgCoreService getSysOrgCoreService(){
        if(sysOrgCoreService==null){
            sysOrgCoreService = (ISysOrgCoreService)getBean("sysOrgCoreService");
        }
        return sysOrgCoreService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        //HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinChatGroup.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        //ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup.class);
        //ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinChatGroupForm thirdWeixinChatGroupForm = (ThirdWeixinChatGroupForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinChatGroupService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinChatGroupForm;
    }

    private void setGroupNameImage(ThirdWeixinChatGroup group){
        if(StringUtil.isNotNull(group.getFdRoomId())){
            group.setGroupNameImage("群聊");
        }else{
            String groupName = group.getFdChatGroupName();
            String groupNameImage = "";
            if(groupName.contains("|")){
                String[] names = groupName.split("\\|");
                for(String name:names){
                    name = name.trim();
                    groupNameImage += name.substring(name.length()-1)+" | ";
                }
            }
            if(StringUtil.isNull(groupNameImage)){
                groupNameImage = groupName;
            }else{
                groupNameImage = groupNameImage.substring(0,groupNameImage.length()-1);
            }
            group.setGroupNameImage(groupNameImage);
        }
    }

    private String getMsgTimeStr(Long msgTime){
        if(msgTime!=null){
            Date date = new Date(msgTime);
            return DateUtil.convertDateToString(date,"yyyyMMdd HH:mm");
        }
        return null;
    }

    private String getMsgTypeShow(String msgType){
        if("image".equals(msgType)){
            return "图片";
        }else if("revoke".equals(msgType)){
            return "撤回消息";
        }else if("agree".equals(msgType)){
            return "同意消息";
        }else if("disagree".equals(msgType)){
            return "不同意消息";
        }else if("voice".equals(msgType)){
            return "语音";
        }else if("video".equals(msgType)){
            return "视频";
        }else if("card".equals(msgType)){
            return "名片";
        }else if("location".equals(msgType)){
            return "位置";
        }else if("file".equals(msgType)){
            return "文件";
        }else if("link".equals(msgType)){
            return "链接";
        }else if("weapp".equals(msgType)){
            return "小程序";
        }else if("chatrecord".equals(msgType)){
            return "会话记录";
        }else if("todo".equals(msgType)){
            return "待办";
        }else if("vote".equals(msgType)){
            return "投票";
        }else if("collect".equals(msgType)){
            return "填表";
        }else if("redpacket".equals(msgType)){
            return "红包";
        }else if("meeting".equals(msgType)){
            return "会议邀请";
        }else if("docmsg".equals(msgType)){
            return "在线文档";
        }else if("markdown".equals(msgType)){
            return "MarkDown消息";
        }else if("news".equals(msgType)){
            return "图文";
        }else if("calendar".equals(msgType)){
            return "日程";
        }else if("mixed".equals(msgType)){
            return "混合消息";
        }else if("meeting_voice_call".equals(msgType)){
            return "音频存档消息";
        }else if("voip_doc_share".equals(msgType)){
            return "音频共享文档";
        }else if("external_redpacket".equals(msgType)){
            return "互通红包";
        }else if("sphfeed".equals(msgType)){
            return "视频号消息";
        }else if("emotion".equals(msgType)){
            return "表情";
        }
        return null;
    }

    private String getMsgContent(ThirdWeixinChatDataMain thirdWeixinChatDataMain,Cipher decrypter) throws Exception {
        if(thirdWeixinChatDataMain!=null){
            String content = thirdWeixinChatDataMain.getFdContent();
            if(StringUtil.isNull(content)){
                content = thirdWeixinChatDataMain.getFdTitle();
            }
            if(thirdWeixinChatDataMain.getFdEncryType()==3){
                content = ChatdataUtil.decry(decrypter,content);
            }
            if(StringUtil.isNull(content)){
                content = "["+getMsgTypeShow(thirdWeixinChatDataMain.getFdMsgType())+"]";
            }
            return content;
        }
        return null;
    }

    private String buildMsgTimeWheleBlock(String[] msgTimes, String whereBlock, HQLInfo hqlInfo, String paraName){
        if(msgTimes!=null && msgTimes.length==2){
            String start = msgTimes[0];
            String end = msgTimes[1];
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
                    whereBlock += " and "+paraName+" < :end";
                    hqlInfo.setParameter("end",endDate.getTime());
                }
            }else{
                if(endDate==null){
                    whereBlock += " and "+paraName+" >= :start";
                    hqlInfo.setParameter("start",startDate.getTime());
                }else{
                    whereBlock += " and "+paraName+" between :start and :end";
                    hqlInfo.setParameter("start",startDate.getTime());
                    hqlInfo.setParameter("end",endDate.getTime());
                }
            }
        }
        return whereBlock;
    }

    private List getWeixinUserIds(List<String> ekpIds) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setSelectBlock("fdAppPkId");
        info.setWhereBlock("fdEkpId in ('"+StringUtil.join(ekpIds,"','")+"')");
        return getWxworkOmsRelationService().findValue(info);
    }

    @Override
    public ActionForward data(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-list", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            boolean isReserve = false;
            if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
                isReserve = true;
            }
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            if (isReserve) {
                orderby += " desc";
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setOrderBy(orderby);
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            changeFindPageHQLInfo(request, hqlInfo);
            String fdChatGroupName = request.getParameter("q.fdChatGroupName");
            String fdContent = request.getParameter("q.fdContent");
            String[] msgTimes = request.getParameterValues("q.msgTime");
            String relateOrg = request.getParameter("q.relateOrg");
            String whereBlock = "1=1 ";

            Page page = null;
            Cipher decrypter = ChatdataUtil.getDecrypter();

            if(StringUtil.isNotNull(fdContent)){
                //whereBlock += " and (fdChatGroupName like :groupName1 or fdRoomId in (select thirdWeixinGroupChat.fdRoomId from com.landray.kmss.third.weixin.model.ThirdWeixinGroupChat thirdWeixinGroupChat left join  thirdWeixinGroupChat.fdMember members where  members.fdAccountName=:groupName2))";
                //whereBlock += " and (fdChatGroupName like :groupName1 or fdRoomId in ('123'))";
                //hqlInfo.setParameter("groupName1","%" + fdChatGroupName + "%");
                //hqlInfo.setParameter("groupName2",fdChatGroupName);
                //HQLInfo info = new HQLInfo();
                hqlInfo.setOrderBy("fdMsgTime desc");
                whereBlock = "(fdContent like :content or fdTitle like :content)";
                hqlInfo.setParameter("content","%"+fdContent+"%");
                whereBlock = buildMsgTimeWheleBlock(msgTimes,whereBlock,hqlInfo,"fdMsgTime");
                hqlInfo.setWhereBlock(whereBlock);
                page = getThirdWeixinChatDataMainService().findPage(hqlInfo);
                List<ThirdWeixinChatDataMain> list = page.getList();
                List<ThirdWeixinChatGroup> list_result = new ArrayList<>();
                if(list!=null){
                    for(ThirdWeixinChatDataMain main:list){
                        ThirdWeixinChatGroup group = main.getFdChatGroup();
                        setGroupNameImage(group);
                        ThirdWeixinChatGroup newGroup = new ThirdWeixinChatGroup();
                        newGroup.setFdId(group.getFdId());
                        newGroup.setFdNewestMsgId(main.getFdMsgId());
                        newGroup.setFdChatGroupName(group.getFdChatGroupName());
                        newGroup.setGroupNameImage(group.getGroupNameImage());
                        newGroup.setNewestMsgTime(getMsgTimeStr(group.getFdNewestMsgTime()));
                        newGroup.setNewestMsg(getMsgContent(main,decrypter));
                        newGroup.setMsgSeq(main.getFdSeq());
                        list_result.add(newGroup);
                    }
                    page.setList(list_result);
                }
            }else{
                if(StringUtil.isNotNull(fdChatGroupName)){
                    whereBlock += " and (fdChatGroupName like :groupName1 or fdRoomId in (select thirdWeixinGroupChat.fdRoomId from com.landray.kmss.third.weixin.model.ThirdWeixinGroupChat thirdWeixinGroupChat left join  thirdWeixinGroupChat.fdMember members where  members.fdAccountName=:groupName2))";
                    hqlInfo.setParameter("groupName1","%" + fdChatGroupName + "%");
                    hqlInfo.setParameter("groupName2",fdChatGroupName);
                }else if(StringUtil.isNotNull(relateOrg)){
                    SysOrgElement element = (SysOrgElement)getSysOrgElementService().findByPrimaryKey(relateOrg);
                    List<SysOrgElement> list = new ArrayList<>();
                    list.add(element);
                    List<String> personIds = getSysOrgCoreService().expandToPersonIds(list);
                    List<String> userIds = getWeixinUserIds(personIds);
                    if(userIds==null || userIds.isEmpty()){
                        whereBlock += "(1=2)";
                    }else {
                        whereBlock += " and (fdUserIdFir in (:userId1) or fdUserIdSec in (:userId2) or fdRoomId in (select thirdWeixinGroupChat.fdRoomId from com.landray.kmss.third.weixin.model.ThirdWeixinGroupChat thirdWeixinGroupChat left join  thirdWeixinGroupChat.fdMember members where  members.fdAccountId in (:userId3)))";
                        hqlInfo.setParameter("userId1", userIds);
                        hqlInfo.setParameter("userId2", userIds);
                        hqlInfo.setParameter("userId3", userIds);
                    }
                }
                whereBlock = buildMsgTimeWheleBlock(msgTimes,whereBlock,hqlInfo,"fdNewestMsgTime");
                hqlInfo.setWhereBlock(whereBlock);
                page = getServiceImp(request).findPage(hqlInfo);
            }

            UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
            List<ThirdWeixinChatGroup> list = page.getList();
            if(list!=null){
                for(ThirdWeixinChatGroup group:list){
                    group.setNewestMsgTime(getMsgTimeStr(group.getFdNewestMsgTime()));
                    String fdNewestMsgId = group.getFdNewestMsgId();
                    if(StringUtil.isNotNull(fdNewestMsgId)){
                        ThirdWeixinChatDataMain thirdWeixinChatDataMain = getThirdWeixinChatDataMainService().findByMsgid(fdNewestMsgId);
                        group.setNewestMsg(getMsgContent(thirdWeixinChatDataMain,decrypter));
                        group.setMsgSeq(thirdWeixinChatDataMain.getFdSeq());
                        setGroupNameImage(group);
                    }
                }
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-list", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("data", mapping, form, request, response);
        }
    }
}
