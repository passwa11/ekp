package com.landray.kmss.km.calendar.actions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.forms.KmCalendarPersonGroupForm;
import com.landray.kmss.km.calendar.model.KmCalendarPersonGroup;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.km.calendar.service.IKmCalendarPersonGroupService;
import com.landray.kmss.km.calendar.util.CalendarSysOrgUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 后台群组设置Action
 */
public class KmCalendarPersonGroupAction extends ExtendAction {

    protected IKmCalendarPersonGroupService kmCalendarPersonGroupService;

    @Override
    protected IKmCalendarPersonGroupService
    getServiceImp(HttpServletRequest request) {
        if (kmCalendarPersonGroupService == null) {
            kmCalendarPersonGroupService = (IKmCalendarPersonGroupService) getBean(
                    "kmCalendarPersonGroupService");
        }
        return kmCalendarPersonGroupService;
    }

    protected IKmCalendarAuthService kmCalendarAuthService;

    public IKmCalendarAuthService getKmCalendarAuthService() {
        if (kmCalendarAuthService == null) {
            kmCalendarAuthService = (IKmCalendarAuthService) getBean(
                    "kmCalendarAuthService");
        }
        return kmCalendarAuthService;
    }

    protected ISysOrgCoreService sysOrgCoreService;

    protected ISysOrgCoreService getSysOrgCoreService() {
        if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean(
                    "sysOrgCoreService");
        }
        return sysOrgCoreService;
    }

    @Override
    protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        super.changeFindPageHQLInfo(request, hqlInfo);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmCalendarPersonGroup.class);
    }

    public ActionForward listPersonGroupJson(ActionMapping mapping,
                                             ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        List<KmCalendarPersonGroup> groups = getServiceImp(request)
                .getUserPersonGroup(UserUtil.getUser().getFdId());
        JSONArray jsonArray = new JSONArray();
        String fdOperType = request.getParameter("fdOperType");
        for (KmCalendarPersonGroup group : groups) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", group.getFdId());
            jsonObj.put("name", StringUtil.XMLEscape(group.getDocSubject()));
            jsonArray.add(jsonObj);
        }
        if ("portlet".equals(fdOperType)) {
            request.setAttribute("groupList", jsonArray);
            return getActionForward("groupPortlet_list", mapping, form, request,
                    response);
        }
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(jsonArray.toString());
        return null;
    }

    /**
     * 获取用户群组供移动端select框使用
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @description:
     * @return: com.landray.kmss.web.action.ActionForward
     * @author: wangjf
     * @time: 2021/9/6 4:19 下午
     */
    public ActionForward listPersonGroupSelectJson(ActionMapping mapping,
                                                   ActionForm form, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        List<KmCalendarPersonGroup> groups = getServiceImp(request)
                .getUserPersonGroup(UserUtil.getUser().getFdId());
        JSONArray jsonArray = new JSONArray();
        String fdOperType = request.getParameter("fdOperType");
        for (KmCalendarPersonGroup group : groups) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("value", group.getFdId());
            jsonObj.put("text", StringUtil.XMLEscape(group.getDocSubject()));
            jsonArray.add(jsonObj);
        }
        if ("portlet".equals(fdOperType)) {
            request.setAttribute("groupList", jsonArray);
            return getActionForward("groupPortlet_list", mapping, form, request,
                    response);
        }
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(jsonArray.toString());
        return null;
    }

    public ActionForward listPersonGroupSearch(ActionMapping mapping,
                                               ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String personGroupId = request.getParameter("personGroupId");
        //关键词
        String keyword = request.getParameter("keyword");
        SysOrgPerson curUser = UserUtil.getUser();
        List<SysOrgElement> groupMembers = new ArrayList<SysOrgElement>();
        if (StringUtil.isNotNull(personGroupId)) {
            request.setAttribute("performanceApprove", "y");
            Map<String, List<SysOrgElement>> maps = getServiceImp(request)
                    .getFdPersonGroup(new RequestContext(request));
            groupMembers = maps.get("persons");
        }
        JSONArray arr = new JSONArray();
        groupMembers = groupMembers.stream().sorted(Comparator.comparing(SysOrgElement::getFdOrder,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
        List<SysOrgElement> selves = groupMembers.stream().filter(ele->ele.equals(curUser)).collect(Collectors.toList());
        arr.addAll(selves.stream().filter(ele->ele.equals(curUser)).map(ele->{
            String eleId = ele.getFdId();
            String eleName = ele.getFdName();
            SysOrgElement parent = ele.getFdParent();
            String eleDept = parent != null ? parent.getDeptLevelNames()
                    : ele.getDeptLevelNames();
            boolean canRead = true;
            boolean canEditor = true;
            boolean canModifier = true;
            JSONObject obj = new JSONObject();
            obj.put("id", eleId);
            obj.put("name", eleName);
            obj.put("dept", eleDept);
            obj.put("canRead", canRead);
            obj.put("canEditor", canEditor);
            obj.put("canModifier", canModifier);
            return obj;
        }).collect(Collectors.toList()));
        groupMembers.removeAll(selves);
        if(groupMembers.size() > 0){
            List<String> eleIds = groupMembers.stream().map(ele->ele.getFdId()).collect(Collectors.toList());
            ExecutorService threadPool = CalendarSysOrgUtil.getThreadPool();
            Future<Map<String, List<String>>> readerFuture = threadPool.submit(new Callable<Map<String, List<String>>>() {
                @Override
                public Map<String, List<String>> call() throws Exception {
                    return getKmCalendarAuthService().getHierarchyIdsFromReaderAuth(eleIds);
                }
            });
            Future<Map<String, List<String>>> editorFuture = threadPool.submit(new Callable<Map<String, List<String>>>() {
                @Override
                public Map<String, List<String>> call() throws Exception {
                    return getKmCalendarAuthService().getHierarchyIdsFromEditorAuth(eleIds);
                }
            });
            Future<Map<String, List<String>>> modifierFuture = threadPool.submit(new Callable<Map<String, List<String>>>() {
                @Override
                public Map<String, List<String>> call() throws Exception {
                    return getKmCalendarAuthService().getHierarchyIdsFromModifierAuth(eleIds);
                }
            });
            Map<String, List<String>> readerMap = readerFuture.get();
            Map<String, List<String>> editorMap = editorFuture.get();
            Map<String, List<String>> modifierMap = modifierFuture.get();
            for (SysOrgElement ele : groupMembers) {
                String eleId = ele.getFdId();
                String eleName = ele.getFdName();
                SysOrgElement parent = ele.getFdParent();
                String eleDept = parent != null ? parent.getDeptLevelNames()
                        : ele.getDeptLevelNames();
                boolean canRead = false;
                boolean canEditor = false;
                boolean canModifier = false;
                List<SysOrgElement> list;
                List<String> hierarchyIds = readerMap.get(eleId);
                if(hierarchyIds != null && hierarchyIds.size() > 0){
                    list = getServiceImp(request).getSysOrgElements(new HashSet<String>(hierarchyIds), false);
                    if(list != null){
                        canRead = list.contains(curUser);
                    }
                }
                hierarchyIds = editorMap.get(eleId);
                if(hierarchyIds != null && hierarchyIds.size() > 0){
                    list = getServiceImp(request).getSysOrgElements(new HashSet<String>(hierarchyIds), false);
                    if(list != null){
                        canEditor = list.contains(curUser);
                    }
                }

                hierarchyIds = modifierMap.get(eleId);
                if(hierarchyIds != null && hierarchyIds.size() > 0){
                    list = getServiceImp(request).getSysOrgElements(new HashSet<String>(hierarchyIds), false);
                    if(list != null){
                        canModifier = list.contains(curUser);
                    }
                }
                JSONObject obj = new JSONObject();
                obj.put("id", eleId);
                obj.put("name", eleName);
                obj.put("dept", eleDept);
                obj.put("canRead", canRead);
                obj.put("canEditor", canEditor);
                obj.put("canModifier", canModifier);
                arr.add(obj);
            }
        }
        PrintWriter out = response.getWriter();
        if (StringUtil.isNotNull(keyword)) {
            JSONArray arr2 = new JSONArray();
            Iterator it = arr.iterator();
            while (it.hasNext()) {
                JSONObject obj2 = (JSONObject) it.next();
                String name = (String) obj2.get("name");
                if (name.contains(keyword)) {
                    arr2.add(obj2);
                }
            }
            out.println(arr2.toString());
        } else {
            out.write(arr.toString());
        }
        return null;
    }

    @Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-edit", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            loadActionForm(mapping, form, request, response);
            KmCalendarPersonGroupForm kmCalendarPersonGroupForm = (KmCalendarPersonGroupForm) form;
            JSONObject obj = genBeforeChangePerson(kmCalendarPersonGroupForm,
                    request);
            kmCalendarPersonGroupForm.setBeforeChangePerson(obj.toString());
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-edit", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("edit", mapping, form, request, response);
        }
    }

    public JSONObject genBeforeChangePerson(
            KmCalendarPersonGroupForm kmCalendarPersonGroupForm,
            HttpServletRequest request) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("fdPersonGroupIds",
                kmCalendarPersonGroupForm.getFdPersonGroupIds());
        obj.put("fdPersonGroupNames",
                kmCalendarPersonGroupForm.getFdPersonGroupNames());
        return obj;
    }


    @Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String[] ids = request.getParameterValues("List_Selected");
            kmCalendarPersonGroupService.deleteMainGroup(ids);
            super.deleteall(mapping, form, request, response);
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException || e instanceof ConstraintViolationException
                    || (e.getCause() != null && e.getCause() instanceof ConstraintViolationException)) {
                messages.addError(new KmssMessage("km-calendar:kmCalendarMainGroup.delete.message").setThrowable(e)
                        .setMessageType(KmssMessage.MESSAGE_ERROR));
            } else {
                messages.addError(e);
            }
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(
                KmssReturnPage.BUTTON_RETURN).save(request);
        TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

}
