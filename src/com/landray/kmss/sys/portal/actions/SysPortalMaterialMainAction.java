package com.landray.kmss.sys.portal.actions;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.portal.forms.SysPortalMaterialMainForm;
import com.landray.kmss.sys.portal.model.SysPortalMaterialMain;
import com.landray.kmss.sys.portal.model.SysPortalMaterialTag;
import com.landray.kmss.sys.portal.service.ISysPortalMaterialMainService;
import com.landray.kmss.sys.portal.service.ISysPortalMaterialTagService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SysPortalMaterialMainAction extends ExtendAction {

    private ISysPortalMaterialMainService sysPortalMaterialMainService;
    private ISysPortalMaterialTagService sysPortalMaterialTagService;

    @Override
    public ISysPortalMaterialMainService
    getServiceImp(HttpServletRequest request) {
        if (sysPortalMaterialMainService == null) {
            sysPortalMaterialMainService = (ISysPortalMaterialMainService) getBean("sysPortalMaterialMainService");
        }
        return sysPortalMaterialMainService;
    }

    public ISysPortalMaterialTagService getSysPortalMaterialTagService() {
        if (sysPortalMaterialTagService == null) {
            sysPortalMaterialTagService = (ISysPortalMaterialTagService) getBean(
                    "sysPortalMaterialTagService");
        }
        return sysPortalMaterialTagService;
    }


    @Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
        String where = hqlInfo.getWhereBlock();
        if (StringUtil.isNull(where)) {
            where = " 1=1 ";
        }
        hqlInfo.setWhereBlock(where);
        CriteriaValue cv = new CriteriaValue(request);
        CriteriaUtil.buildHql(cv, hqlInfo, SysPortalMaterialMain.class);
        if (StringUtil.isNotNull(request.getParameter("config"))) {
            hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
        }
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysPortalMaterialMainForm sysPortalMaterialMainForm = (SysPortalMaterialMainForm) super.createNewForm(mapping, form, request, response);
        ((ISysPortalMaterialMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysPortalMaterialMainForm;
    }

    /**
     * 跳转上传页面
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward uploadAdd(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-add", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            ActionForm newForm = createNewForm(mapping, form, request,
                    response);
            if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
            if (UserOperHelper.allowLogOper("Base_UrlParam", "*")) {  
    			UserOperHelper.setEventType(ResourceUtil.getString("common.fileUpLoad.title"));
    			UserOperHelper.setModelNameAndModelDesc(SysPortalMaterialMain.class.getName(),  ResourceUtil.getString("sys-portal:portal.material.library")); 
    		}
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-add", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("uploadAdd", mapping, form, request, response);
        }
    }

    /**
     * 跳转选择页面
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectMaterial(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-list", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String type = request.getParameter("type");
            if (StringUtils.isEmpty(type)) {
                type = "1";
            }
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
            if (s_pageno != null && s_pageno.length() > 0
                    && Integer.parseInt(s_pageno) > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0
                    && Integer.parseInt(s_rowsize) > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            if (isReserve) {
                orderby += " desc";
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setOrderBy(orderby);
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            String whereBlock = "sysPortalMaterialMain.fdType = :type";
            hqlInfo.setWhereBlock(whereBlock);
            hqlInfo.setParameter("type", type);
            changeFindPageHQLInfo(request, hqlInfo);
            Page page = getServiceImp(request).findPage(hqlInfo);
            UserOperHelper.logFindAll(page.getList(),
                    getServiceImp(request).getModelName());

            JSONObject result = new JSONObject();
            result.put("page", getPageJson(page));
            result.put("datas", getGridDatas(page.getList()));
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(result.toJSONString());
            if (UserOperHelper.allowLogOper("Base_UrlParam", "*")) {  
    			UserOperHelper.setEventType(ResourceUtil.getString("button.view"));
    			UserOperHelper.setModelNameAndModelDesc(SysPortalMaterialMain.class.getName(),  ResourceUtil.getString("sys-portal:portal.material.library")); 
    		}
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-list", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("select", mapping, form, request, response);
        }
    }

    /**
     * 格子布局（首页）
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward gridData(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-list", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String type = request.getParameter("type");
            String sort = request.getParameter("sort");
            if (StringUtils.isEmpty(type)) {
                type = "1";
            }
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            //默认按创建时间的降序排序
            if("ture".equals(sort)){
                orderby="sysPortalMaterialMain.docCreateTime";
                ordertype="down";
            }
            boolean isReserve = false;
            if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
                isReserve = true;
            }
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0
                    && Integer.parseInt(s_pageno) > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0
                    && Integer.parseInt(s_rowsize) > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            if (isReserve) {
                orderby += " desc";
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setOrderBy(orderby);
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            String whereBlock = "sysPortalMaterialMain.fdType = :type";
            // 关键字搜索
            CriteriaValue cv = new CriteriaValue(request);
            String tagsName = cv.poll("fdTagsName");
            if (StringUtil.isNotNull(tagsName)) {
                whereBlock = StringUtil.linkString(whereBlock, " and ",
                        " ( fdTags.fdName like :tagsName or  sysPortalMaterialMain.fdName like :tagsName ) ");
                hqlInfo.setParameter("tagsName", "%"
                        + tagsName + "%");
                hqlInfo.setJoinBlock(
                        "left join sysPortalMaterialMain.fdTags fdTags");
            }
            String fdPcType = cv.poll("fdPcType");//类型
            if(StringUtil.isNotNull(fdPcType)){

            }
            hqlInfo.setWhereBlock(whereBlock);
            hqlInfo.setParameter("type", type);
            changeFindPageHQLInfo(request, hqlInfo);
            Page page = getServiceImp(request).findPage(hqlInfo);
            UserOperHelper.logFindAll(page.getList(),
                    getServiceImp(request).getModelName());

            JSONObject result = new JSONObject();
            result.put("page", getPageJson(page));
            result.put("datas", getGridDatas(page.getList()));
            response.setHeader("content-type", "application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(result.toJSONString());
            
            /**
             * 保存素材库
             */

        	if (UserOperHelper.allowLogOper("Base_UrlParam", "*")) {  
    			UserOperHelper.setEventType(ResourceUtil.getString("button.view"));
    			UserOperHelper.setModelNameAndModelDesc(SysPortalMaterialMain.class.getName(),  ResourceUtil.getString("sys-portal:portal.material.library")); 
    		}
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-list", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return null;
        }
    }

    /**
     * 异步保存
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveMaterialAsync(ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
                .getBean("sysAttMainService");

        String type = request.getParameter("type");

        String jsonStr = request.getParameter("materialList");
        JSONArray jsonArray = JSON.parseArray(jsonStr);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jObj = jsonArray.getJSONObject(i);
            String attId = jObj.getString("attid");
            SysAttMain attMain = (SysAttMain) sysAttMainService.findByPrimaryKey(attId, SysAttMain.class, true);
            String title = jObj.getString("title");
            //如果没填写标题，默认取文件名
            if(StringUtil.isNull(title)){
                title = attMain.getFdFileName().split("\\.")[0];
            }
            String size = jObj.getString("size");
            String length = jObj.getString("length");
            String width = jObj.getString("width");
            SysPortalMaterialMainForm mainForm = new SysPortalMaterialMainForm();
            mainForm.setFdName(title);
            mainForm.setFdAttId(attId);
            mainForm.setFdType(type);
            mainForm.setFdSize(size);
            mainForm.setFdLength(length);
            mainForm.setFdWidth(width);
            //tags
            if (jObj.containsKey("tagNames") && StringUtil.isNotNull(jObj.getString("tagNames"))) {
                String tagNames = jObj.getString("tagNames");
                String[] tagNamesArr = tagNames.trim().split(";");
                List<String> tagIds = new ArrayList();
                for (int j = 0; j < tagNamesArr.length; j++) {
                    String tName = tagNamesArr[j];
                    if (tName == null || tName.trim().length() == 0) {
                        continue;
                    }
                    HQLInfo h = new HQLInfo();
                    String whereBlock = "sysPortalMaterialTag.fdName = :tName";
                    h.setWhereBlock(whereBlock);
                    h.setParameter("tName", tName);
                    SysPortalMaterialTag obj = (SysPortalMaterialTag)getSysPortalMaterialTagService()
                            .findFirstOne(h);
                    if (obj == null) {
                        // new
                        SysPortalMaterialTag sysTag = new SysPortalMaterialTag();
                        sysTag.setFdName(tName);
                        String tid = getSysPortalMaterialTagService().add(sysTag);

                        tagIds.add(tid);
                    } else {
                        tagIds.add(obj.getFdId());
                    }
                }
                mainForm.setFdTagIds(StringUtil
                        .join(ArrayUtil.toStringArray(tagIds.toArray()), ";"));
            }
            String mainFdId = getServiceImp(request).add(mainForm,
                    new RequestContext(request));
            String modelName = getServiceImp(request).getModelName();
            SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(mainForm.getFdAttId(), SysAttMain.class, true);
            sysAttMain.setFdModelId(mainFdId);
            sysAttMain.setFdModelName(modelName);
            sysAttMainService.update(sysAttMain);
        }
        /**
         * 保存素材库
         */

    	if (UserOperHelper.allowLogOper("Base_UrlParam", "*")) {  
			UserOperHelper.setEventType(ResourceUtil.getString("button.save"));
			UserOperHelper.setModelNameAndModelDesc(SysPortalMaterialMain.class.getName(),  ResourceUtil.getString("sys-portal:portal.material.library")); 
		}
        JSONObject result = new JSONObject();
        result.put("status", "1");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result.toJSONString());
        return null;
    }

    /**
     * 跳转编辑
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward toEdit(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-add", true, getClass());
        KmssMessages messages = new KmssMessages();
        String junmpJsp = "editPic";
        try {
            String type = request.getParameter("type");
            String fdId = request.getParameter("fdId");
            if (StringUtils.isEmpty(type) || StringUtils.isEmpty(fdId)) {
                return getActionForward("failure", mapping, form, request,
                        response);
            } else {

                SysPortalMaterialMain main = (SysPortalMaterialMain) getServiceImp(
                        request)
                        .findByPrimaryKey(fdId);
                SysPortalMaterialMainForm mainForm = (SysPortalMaterialMainForm) getServiceImp(
                        request).cloneModelToForm(null, main,
                        new RequestContext(request));
                if ("icon".equals(type)) {
                    junmpJsp = "editIcon";
                }
                if (mainForm.getFdTagNames() == null) {
                    mainForm.setFdTagNames("");
                }
                request.setAttribute("main", main);
                request.setAttribute("mainForm", mainForm);
            }

        } catch (Exception e) {
            messages.addError(e);
        }
        /**
         * 保存素材库
         */

    	if (UserOperHelper.allowLogOper("Base_UrlParam", "*")) {  
			UserOperHelper.setEventType(ResourceUtil.getString("button.goEdit"));
			UserOperHelper.setModelNameAndModelDesc(SysPortalMaterialMain.class.getName(),  ResourceUtil.getString("sys-portal:portal.material.library")); 
		}
        TimeCounter.logCurrentTime("Action-add", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward(junmpJsp, mapping, form, request,
                    response);
        }
    }

    /**
     * 更新
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateMaterial(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
                .getBean("sysAttMainService");
        KmssMessages messages = new KmssMessages();
        JSONObject obj = new JSONObject();
        String fdId = request.getParameter("fdId");
        if (StringUtils.isEmpty(fdId)) {
            obj.put("status", -1);
            obj.put("msg", "NotFound");
        } else {
            try {
                SysPortalMaterialMain main = (SysPortalMaterialMain) getServiceImp(
                        request)
                        .findByPrimaryKey(fdId);

                //name
                String fdName = request.getParameter("fdName");
                fdName = StringUtils.isEmpty(fdName) ? main.getFdName() : fdName;
                main.setFdName(fdName);

                if ("1".equals(main.getFdType())) {//图片
                    // att
                    // todo del old att
                    String attId = request.getParameter("attId");
                    attId = StringUtils.isEmpty(attId) ? main.getFdAttId()
                            : attId;
                    main.setFdAttId(attId);
                    // "fdSize":fdSize,"fdLength":fdLength,"fdWidth":fdWidth"
                    String fdSizeStr = request.getParameter("fdSize");
                    SysAttMain sysAttMain = (SysAttMain)sysAttMainService.findByPrimaryKey(attId);
                    String modelId = sysAttMain.getFdModelId();
                    String modelName = sysAttMain.getFdModelName();
                    //兼容素材库旧数据（modelId、modelName为空）
                    if(StringUtil.isNull(modelId) && StringUtil.isNull(modelName)){
                        String fdMolelName = getServiceImp(request).getModelName();
                        sysAttMain.setFdModelId(fdId);
                        sysAttMain.setFdModelName(fdMolelName);
                        sysAttMainService.update(sysAttMain);
                    }
                    if (!StringUtils.isEmpty(fdSizeStr)) {
                        Long fdSize = Long.parseLong(fdSizeStr);
                        fdSize = fdSize > 0 ? fdSize : main.getFdSize();
                        main.setFdSize(fdSize);
                    }
                    String fdLengthStr = request.getParameter("fdLength");
                    if (!StringUtils.isEmpty(fdLengthStr)) {
                        Double fdLength = Double.parseDouble(fdLengthStr);
                        fdLength = fdLength > 0 ? fdLength : main.getFdLength();
                        main.setFdLength(fdLength);
                    }

                    String fdWidthStr = request.getParameter("fdWidth");
                    if (!StringUtils.isEmpty(fdWidthStr)) {
                        Double fdWidth = Double.parseDouble(fdWidthStr);
                        fdWidth = fdWidth > 0 ? fdWidth : main.getFdWidth();
                        main.setFdWidth(fdWidth);
                    }
                }else {
                    //类型为图标
                    String fdAttId = main.getFdAttId();
                    SysAttMain attMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fdAttId, SysAttMain.class, true);
                    if (StringUtil.isNull(attMain.getFdModelId()) && StringUtil.isNull(attMain.getFdModelName())) {//兼容旧数据（ModelId和ModelName为空）
                        String modelName = getServiceImp(request).getModelName();
                        attMain.setFdModelId(main.getFdId());
                        attMain.setFdModelName(modelName);
                        sysAttMainService.update(attMain);
                    }
                }

                // tags
                String fdTags = request.getParameter("fdTags");
                if (StringUtil.isNotNull(fdTags) && fdTags.trim().length()>0) {
                    String[] tagNamesArr = fdTags.split(";");
                    List<SysPortalMaterialTag> tagList = new ArrayList();
                    for (int j = 0; j < tagNamesArr.length; j++) {
                        String tName = tagNamesArr[j];
                        if (tName == null || tName.trim().length() == 0) {
                            continue;
                        }
                        HQLInfo h = new HQLInfo();
                        String whereBlock = "sysPortalMaterialTag.fdName = :tName";
                        h.setWhereBlock(whereBlock);
                        h.setParameter("tName", tName);
                        Object objTag = getSysPortalMaterialTagService()
                                .findFirstOne(h);
                        if (objTag == null) {
                            // new
                            SysPortalMaterialTag sysTag = new SysPortalMaterialTag();
                            sysTag.setFdName(tName);
                            String tid = getSysPortalMaterialTagService()
                                    .add(sysTag);

                            tagList.add(sysTag);
                        } else {
                            tagList.add((SysPortalMaterialTag)objTag);
                        }
                    }

                    main.setFdTags(tagList);
                }
                getServiceImp(request).update(main);
                obj.put("status", 0);
                obj.put("msg", "success");
                
                if (UserOperHelper.allowLogOper("Base_UrlParam", "*")) {  
        			UserOperHelper.setEventType(ResourceUtil.getString("button.update"));
        			UserOperHelper.setModelNameAndModelDesc(SysPortalMaterialMain.class.getName(),  ResourceUtil.getString("sys-portal:portal.material.library")); 
        		}
            } catch (Exception e) {
                messages.addError(e);
                obj.put("status", -1);
                obj.put("msg", messages.getMessages());
            }

        }

        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(obj.toJSONString());

        return null;
    }

    // ------------------------------------------私有功能

    /********************************************
     * 功能 组装格子布局返回参数
     *
     * @param page
     * @return
     ********************************************/
    private JSONObject getPageJson(Page page) {
        JSONObject p = new JSONObject();
        p.put("currentPage", page.getPageno());
        p.put("pageSize", page.getRowsize());
        p.put("totalSize", page.getTotalrows());
        return p;
    }

    private JSONArray getGridDatas(List<SysPortalMaterialMain> list) {
        JSONArray datas = new JSONArray();
        for (SysPortalMaterialMain m : list) {
            datas.add(getGridData(m));
        }
        return datas;
    }

    private JSONArray getGridData(SysPortalMaterialMain materialMain) {
        String[] cols = {"fdId", "fdName", "tags", "imageUrl","defIcon"};
        String fdId = materialMain.getFdId();
        String fdName = materialMain.getFdName();
        String tags = "";

        try {
            List<SysPortalMaterialTag> tagList = materialMain.getFdTags();
            List<String> tagHtml = new ArrayList();
            if (tagList != null && tagList.size() > 1) {
                for (SysPortalMaterialTag tag : tagList) {
                    String tagitem = "<span>" + tag.getFdName() + "</span>";
                    tagHtml.add(tagitem);
                }
                tags = StringUtil.join(tagHtml, ";");
            } else if (tagList != null && tagList.size() == 1) {
                SysPortalMaterialTag tag = tagList.get(0);
                if (!tag.getFdName().isEmpty()) {
                    String tagitem = "<span>" + tag.getFdName() + "</span>";
                    tags = tagitem;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String imageUrl="";
        String defIcon="";
        if(StringUtil.isNotNull(materialMain.getFdImgUrl())){ //如果是内置图标FdImgUrl不为空
            imageUrl = materialMain.getFdImgUrl();
            defIcon="true";
        }else {
            imageUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
                    + materialMain.getFdAttId() + "&open=1";
        }
        Object[] vals = {fdId, fdName, tags, imageUrl,defIcon};
        return getDatas(cols, vals);
    }

    private JSONArray getDatas(String[] cols, Object[] vals) {
        JSONArray datas = new JSONArray();
        for (int i = 0; i < cols.length; i++) {
            try {
                JSONObject jObj = new JSONObject();
                jObj.put("col", cols[i]);
                jObj.put("value", vals[i]);
                datas.add(jObj);
            } catch (Exception e) {
                // TODO: handle exception
                continue;
            }
        }
        return datas;
    }


}
