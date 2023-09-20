package com.landray.kmss.third.feishu.webservice;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonMappingService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.annotation.RestApi;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/api/third-feishu/personMapping", method = RequestMethod.POST)
@RestApi(docUrl = "/third/feishu/restwebservice/FeishuPersonMappingWebServiceHelp.jsp", name = "feishuPersonMappingWebService", resourceKey = "third-feishu:third.feishu.webservice.person.mapping")
public class FeishuPersonMappingWebServiceImp implements IFeishuPersonMappingWebService {

    private IThirdFeishuPersonMappingService thirdFeishuPersonMappingService;

    private IThirdFeishuPersonMappingService getThirdFeishuPersonMappingService(){
        if(this.thirdFeishuPersonMappingService == null) {
            this.thirdFeishuPersonMappingService = (IThirdFeishuPersonMappingService) SpringBeanUtil.getBean("thirdFeishuPersonMappingService");
        }
        return this.thirdFeishuPersonMappingService;
    }

    @ResponseBody
    @RequestMapping(value = "/getBySysOrgIds")
    @Override
    public JSONArray getBySysOrgIds(@RequestBody String[] sysOrgIds) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setRowSize(200);
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
        StringBuilder selectBuilder = new StringBuilder();
        selectBuilder.append("element.fdId");
        selectBuilder.append(",thirdFeishuPersonMapping.fdEmployeeId");
        selectBuilder.append(",thirdFeishuPersonMapping.fdOpenId");
        selectBuilder.append(",thirdFeishuPersonMapping.fdMobileNo");
        selectBuilder.append(",element.fdOrgEmail");
        hqlInfo.setSelectBlock(selectBuilder.toString());
        StringBuilder joinBuilder = new StringBuilder();
        joinBuilder.append(" inner join thirdFeishuPersonMapping.fdEkp element ");
        hqlInfo.setJoinBlock(joinBuilder.toString());
        StringBuilder whereBuilder = new StringBuilder();
        whereBuilder.append(" 1=1 ");
        if (sysOrgIds != null && sysOrgIds.length > 0) {
            whereBuilder.append(" AND ");
            whereBuilder.append(HQLUtil.buildLogicIN("element.fdId", Arrays.asList(sysOrgIds)));
        }
        hqlInfo.setWhereBlock(whereBuilder.toString());
        JSONArray rtn = new JSONArray();
        Page page = getThirdFeishuPersonMappingService().findPage(hqlInfo);
        List<Object[]> result = page.getList();
        int pageNo = page.getPageno();
        while(page.isHasNextPage())
        {
            hqlInfo.setPageNo(++pageNo);
            page = getThirdFeishuPersonMappingService().findPage(hqlInfo);
            result.addAll(page.getList());
        }
        if(CollectionUtils.isNotEmpty(result)) {
            result.stream().forEach(row->{
                JSONObject rowJson = new JSONObject();
                rowJson.put("approvor_id",row[0]);
                rowJson.put("approvor_mobile",row[3]);
                rowJson.put("approvor_email",row[4]);
                rowJson.put("ex_employee_id",row[1]);
                rowJson.put("ex_open_id",row[2]);
                rtn.add(rowJson);
            });
        }
        return rtn;
    }
}
