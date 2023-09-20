package com.landray.kmss.hrext.staff.actions;

import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hrext.staff.model.HrextStaffExpand;
import com.landray.kmss.hrext.staff.forms.HrextStaffExpandForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.hrext.staff.service.IHrextStaffExpandService;
import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hrext.staff.util.HrextStaffUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

/**
  * 流程人事同步 Action
  */
public class HrextStaffExpandAction extends ExtendAction {

    private IHrextStaffExpandService hrextStaffExpandService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrextStaffExpandService == null) {
            hrextStaffExpandService = (IHrextStaffExpandService) getBean("hrextStaffExpandService");
        }
        return hrextStaffExpandService;
    }

    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrextStaffExpand.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hrext.staff.util.HrextStaffUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hrext.staff.model.HrextStaffExpand.class);
        com.landray.kmss.hrext.staff.util.HrextStaffUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrextStaffExpandForm hrextStaffExpandForm = (HrextStaffExpandForm) super.createNewForm(mapping, form, request, response);
        ((IHrextStaffExpandService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return hrextStaffExpandForm;
    }

    public ActionForward queryReviewOnWayHoliday(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-delete", true, getClass());
        KmssMessages messages = new KmssMessages();

        //假期模板
        String tempId = request.getParameter("tempId");

        Map<String, BigDecimal> holidayMap = new HashMap<>();
        try {
            //获取在途流程
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("kmReviewMain.fdTemplate.fdId = :tempId and kmReviewMain.docCreator.fdId = :docCreatorId  and (kmReviewMain.docStatus = '20' or kmReviewMain.docStatus = '11') ");
            hqlInfo.setParameter("tempId",tempId);
            hqlInfo.setParameter("docCreatorId",UserUtil.getUser().getFdId());
            List<KmReviewMain> kmReviewMainList = getKmReviewMainService().findList(hqlInfo);

            for (KmReviewMain kmReviewMain : kmReviewMainList) {

                List holidayList = (List)getValue(kmReviewMain, "holidayList");
                for (Object holiday : holidayList) {
                   // holiday
                    Map<String, Object> detailMap = (Map<String, Object>) holiday;
                    String name = (String) detailMap.get("holidayType");
                    String time = detailMap.get("holidayTime")+"";
                    BigDecimal decimalTime = null;
                    try{
                        decimalTime = new BigDecimal(time);
                    }catch (Exception e){
                        decimalTime = new BigDecimal("0");
                    }
                    if(holidayMap.get(name) == null){
                        holidayMap.put(name,decimalTime);
                    }else{
                        holidayMap.put(name, holidayMap.get(name).add(decimalTime));
                    }
                }

            }
        } catch (Exception e) {
            messages.addError(e);
        }
        JSONArray array = new JSONArray();
        Set<Map.Entry<String, BigDecimal>> entries = holidayMap.entrySet();
        for (Map.Entry<String, BigDecimal> entry : entries) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",entry.getKey());
            jsonObject.put("value",entry.getValue());
            array.add(jsonObject);
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(array.toString());
        response.getWriter().flush();
        response.getWriter().close();

       return null;
    }


    private Object getValue(IBaseModel mainModel, String fieldName) {
        Object obj = null;
        if (StringUtil.isNotNull(fieldName)) {
            // 主文档的属性
            if (PropertyUtils.isReadable(mainModel, fieldName)) {
                // 主文档数据
                try {
                    obj = PropertyUtils.getProperty(mainModel, fieldName);
                } catch (Exception e) {
                    logger.warn("获取属性值错误！ 域模型：" + mainModel.getClass().getName() + "，属性名："  + fieldName);
                }
            } else {
                if (mainModel instanceof IExtendDataModel) {
                    IExtendDataModel dataModel = (IExtendDataModel) mainModel;
                    List list = null;
                    try {
                        list = ObjectXML.objectXMLDecoderByString(dataModel.getExtendDataXML());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!ArrayUtil.isEmpty(list)) {
                        Map<String, Object> map = (Map<String, Object>) list.get(0);
                        obj = map.get(fieldName);
                    }
                }
            }
        }
        return obj;
    }
    private IKmReviewMainService kmReviewMainService;

    public IKmReviewMainService getKmReviewMainService() {
        if(kmReviewMainService==null){
            kmReviewMainService = (IKmReviewMainService) SpringBeanUtil.getBean("kmReviewMainService");
        }
        return kmReviewMainService;
    }

}
