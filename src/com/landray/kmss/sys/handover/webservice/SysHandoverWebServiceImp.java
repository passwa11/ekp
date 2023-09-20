package com.landray.kmss.sys.handover.webservice;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigMainService;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.annotation.RestApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/sys-handover/sysHandoverRestService", method = RequestMethod.POST)
@RestApi(docUrl = "/sys/handover/sysHandoverWebServiceHelp.jsp", name = "sysHandoverRestService", resourceKey = "sys-handover:sysHandoverRestService.title")
public class SysHandoverWebServiceImp implements ISysHandoverWebService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysHandoverWebServiceImp.class);

    private ISysHandoverConfigMainService sysHandoverConfigMainService;

    private ISysQuartzJobService sysQuartzJobService;

    private ISysWsOrgService sysWsOrgService;

    private IBackgroundAuthService backgroundAuthService;

    public void setSysHandoverConfigMainService(ISysHandoverConfigMainService sysHandoverConfigMainService) {
        this.sysHandoverConfigMainService = sysHandoverConfigMainService;
    }

    public void setSysQuartzJobService(ISysQuartzJobService sysQuartzJobService) {
        this.sysQuartzJobService = sysQuartzJobService;
    }

    public void setSysWsOrgService(ISysWsOrgService sysWsOrgService) {
        this.sysWsOrgService = sysWsOrgService;
    }

    public void setBackgroundAuthService(IBackgroundAuthService backgroundAuthService) {
        this.backgroundAuthService = backgroundAuthService;
    }

    @ResponseBody
    @RequestMapping(value = "addHandover", method = RequestMethod.POST)
    @Override
    public SysHandoverResp addHandover(@RequestBody SysHandoverReq req) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("传入参数：{}", req);
        }
        SysHandoverResp resp = new SysHandoverResp();
        if (StringUtil.isNull(req.getFdCreator())) {
            resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.not.analysis.creator"));
            return resp;
        }
        if (StringUtil.isNull(req.getFdFromElement()) || StringUtil.isNull(req.getFdToElement())) {
            resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.param.not.null"));
            return resp;
        }
        if (req.getFdFromElement().equals(req.getFdToElement())) {
            resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.param.is.equals"));
            return resp;
        }
        SysOrgElement fromElement = sysWsOrgService.findSysOrgElement(req.getFdFromElement());
        SysOrgElement toElement = sysWsOrgService.findSysOrgElement(req.getFdToElement());
        SysOrgElement creator = sysWsOrgService.findSysOrgElement(req.getFdCreator());

        if (creator == null) {
            resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.not.analysis.creator"));
            return resp;
        }

        if (SysOrgConstant.ORG_TYPE_PERSON != creator.getFdOrgType()) {
            resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.creator.not.person"));
            return resp;
        }

        if (fromElement == null || toElement == null) {
            resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.param.not.orgElement"));
            return resp;
        }
        if (!checkType(fromElement)) {
            resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.from.param.error"));
            return resp;
        }
        if (!checkType(toElement)) {
            resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.to.param.error"));
            return resp;
        }

        return (SysHandoverResp) backgroundAuthService.switchUserById(creator.getFdId(), new Runner() {
            @Override
            public Object run(Object parameter) throws Exception {
                Object[] param = (Object[]) parameter;
                SysHandoverReq req = (SysHandoverReq) param[0];
                SysOrgElement fromElement = (SysOrgElement) param[1];
                SysOrgElement toElement = (SysOrgElement) param[2];
                SysHandoverResp resp = new SysHandoverResp();
                if ("all".equals(req.getFdType())) {
                    //交接全部工作
                    StringBuilder title = new StringBuilder();
                    StringBuilder url = new StringBuilder();
                    StringBuilder msg = new StringBuilder();
                    req.setFdType("doc");
                    executeHandover(resp, req, fromElement, toElement);
                    buildResp(resp, req, title, url, msg);
                    req.setFdType("config");
                    executeHandover(resp, req, fromElement, toElement);
                    buildResp(resp, req, title, url, msg);
                    req.setFdType("auth");
                    executeHandover(resp, req, fromElement, toElement);
                    buildResp(resp, req, title, url, msg);
                    req.setFdType("item");
                    executeHandover(resp, req, fromElement, toElement);
                    buildResp(resp, req, title, url, msg);
                    resp.setFdMsg(msg.toString());
                    resp.setFdUrl(url.toString());
                    resp.setFdTitle(title.toString());
                } else {
                    executeHandover(resp, req, fromElement, toElement);
                }
                return resp;
            }
        }, new Object[]{req, fromElement, toElement});
    }

    private void buildResp(SysHandoverResp resp, SysHandoverReq req, StringBuilder title, StringBuilder url, StringBuilder msg) throws Exception {
        switch (req.getFdType()) {
            case "doc":
                if (StringUtil.isNotNull(resp.getFdUrl()) || StringUtil.isNotNull(resp.getFdTitle())) {
                    title.append(ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType.doc")).append(":").append(resp.getFdTitle()).append(";");
                    url.append(ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType.doc")).append(":").append(resp.getFdUrl()).append(";");
                }
                msg.append(ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType.doc")).append(":").append(resp.getFdMsg()).append(";");
                break;
            case "config":
                msg.append(ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType.config")).append(":").append(resp.getFdMsg()).append(";");
                break;
            case "auth":
                msg.append(ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType.auth")).append(":").append(resp.getFdMsg()).append(";");
                break;
            case "item":
                if (StringUtil.isNotNull(resp.getFdUrl()) || StringUtil.isNotNull(resp.getFdTitle())) {
                    title.append(ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType.item")).append(":").append(resp.getFdTitle()).append(";");
                    url.append(ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType.item")).append(":").append(resp.getFdUrl()).append(";");
                }
                msg.append(ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType.item")).append(":").append(resp.getFdMsg()).append(";");
                break;
            default:
                break;
        }
        resp.setFdTitle(null);
        resp.setFdUrl(null);
        resp.setFdMsg(null);
    }

    private void executeHandover(SysHandoverResp resp, SysHandoverReq req, SysOrgElement fromElement, SysOrgElement toElement) throws Exception {
        try {
            List<Map<String, String>> moduleList = new ArrayList<Map<String, String>>();
            RequestContext request = new RequestContext();
            JSONObject jsonObject = null;
            String fdId = null;
            switch (req.getFdType()) {
                case "doc"://在途的流程  submit 定时任务
                    moduleList = HandoverPluginUtils.getDocHandoverModules();
                    jsonObject = searchAndBuildData(moduleList, req, fromElement);
                    if ("0".equals(jsonObject.getString("total"))) {
                        resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.no.doc.data"));
                        break;
                    }
                    request.setParameter("fdFromId", fromElement.getFdId());
                    request.setParameter("fdToId", toElement.getFdId());
                    request.setParameter("fdContent", jsonObject.toString());
                    request.setParameter("type", req.getFdType());
                    fdId = sysHandoverConfigMainService.submit(request);
                    addReturnResult(resp, fdId);
                    break;
                case "config"://后台配置类 execute
                    moduleList = HandoverPluginUtils.getConfigHandoverTypes(req.getFdType());
                    if (addSearchAndExecuteConfigData(moduleList, req, fromElement, toElement)) {
                        resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.done"));
                    } else {
                        resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.no.config.data"));
                    }
                    break;
                case "auth"://文档中权限 submitAuth 权限多选
                    moduleList = HandoverPluginUtils.getConfigHandoverTypes(req.getFdType());
                    if (addSearchAndExecuteAuthData(moduleList, req, fromElement, toElement)) {
                        resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.done"));
                    } else {
                        resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.no.auth.data"));
                    }
                    break;
                case "item"://事项交接 submit 定时任务
                    moduleList = HandoverPluginUtils.getConfigHandoverTypes(req.getFdType());
                    jsonObject = searchAndBuildData(moduleList, req, fromElement);
                    if ("0".equals(jsonObject.getString("total"))) {
                        resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.no.item.data"));
                        break;
                    }
                    request.setParameter("fdFromId", fromElement.getFdId());
                    request.setParameter("fdToId", toElement.getFdId());
                    request.setParameter("fdContent", jsonObject.toString());
                    request.setParameter("type", req.getFdType());
                    request.setParameter("execMode", req.getExecMode());
                    fdId = sysHandoverConfigMainService.submit(request);
                    addReturnResult(resp, fdId);
                    break;
                default:
                    resp.setFdMsg(ResourceUtil.getString("sys-handover:sysHandoverRestService.param.type.error"));
                    break;
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    /**
     * 验证组织类型是否是人员或岗位,是返回true，否则返回false
     *
     * @param element
     * @return
     */
    private boolean checkType(SysOrgElement element) {
        boolean flag = false;
        if (element.getFdOrgType() != null &&
                (SysOrgConstant.ORG_TYPE_POST == element.getFdOrgType() || SysOrgConstant.ORG_TYPE_PERSON == element.getFdOrgType())) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据模块id查询对应需要进行工作交接的数据并返回json
     *
     * @param moduleList
     * @param req
     * @return
     * @throws Exception
     */
    private JSONObject searchAndBuildData(List<Map<String, String>> moduleList, SysHandoverReq req, SysOrgElement fromElement) throws Exception {
        int total = 0;
        JSONObject resultJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Map<String, String> map : moduleList) {
            RequestContext request = new RequestContext();
            request.setParameter("fdFromId", fromElement.getFdId());
            request.setParameter("fdKey", map.keySet().iterator().next());
            request.setParameter("type", req.getFdType());
            JSONObject jsonObject = sysHandoverConfigMainService.search(request);
            if ("0".equals(jsonObject.getString("total"))) {
                continue;
            }
            JSONObject detail = new JSONObject();
            JSONArray itemList = new JSONArray();
            JSONArray itemArray = jsonObject.getJSONArray("item");
            int detailTotal = 0;
            String module = null;
            for (int i = 0; i < itemArray.size(); i++) {
                JSONObject object = (JSONObject) itemArray.get(i);
                if ("0".equals(object.getString("total"))) {
                    continue;
                }
                JSONObject submitItem = new JSONObject();
                submitItem.put("item", object.getString("item"));
                submitItem.put("itemNumber", object.getInt("total"));
                submitItem.put("isAll", true);
                itemList.add(submitItem);
                detailTotal = object.getInt("total");
                module = object.getString("module");
            }
            detail.put("module", module);
            detail.put("total", detailTotal);
            detail.put("items", itemList);
            total += detailTotal;
            jsonArray.add(detail);
        }
        resultJson.put("total", total);
        resultJson.put("modules", jsonArray);
        return resultJson;
    }

    /**
     * 根据模块id查询对应需要进行工作交接的后台配置数据并执行
     *
     * @param moduleList
     * @param req
     * @return
     * @throws Exception
     */
    private boolean addSearchAndExecuteConfigData(List<Map<String, String>> moduleList, SysHandoverReq req, SysOrgElement fromElement, SysOrgElement toElement) throws Exception {
        boolean flag = false;
        String mainId = IDGenerator.generateID();
        for (Map<String, String> map : moduleList) {
            RequestContext request = new RequestContext();
            request.setParameter("fdKey", map.keySet().iterator().next());
            request.setParameter("fdFromId", fromElement.getFdId());
            request.setParameter("fdToId", toElement.getFdId());
            request.setParameter("mainId", mainId);
            request.setParameter("type",req.getFdType());
            JSONObject jsonObject = sysHandoverConfigMainService.search(request);
            if ("0".equals(jsonObject.getString("total"))) {
                continue;
            }
            StringBuilder ids = new StringBuilder();
            //这里存在两种total不为0的数据结构
            if(jsonObject.has("handoverRecords") && StringUtil.isNotNull(jsonObject.getString("handoverRecords"))){
                JSONArray jsonArray = jsonObject.getJSONArray("handoverRecords");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject bean = (JSONObject) jsonArray.get(i);
                    ids.append(bean.getString("id")).append(",");
                }
            }
            if(jsonObject.has("item") && StringUtil.isNotNull(jsonObject.getString("item"))){
                JSONArray itemArray = jsonObject.getJSONArray("item");
                for (int i = 0; i < itemArray.size(); i++) {
                    JSONObject item = (JSONObject) itemArray.get(i);
                    if ("0".equals(item.getString("total"))) {
                        continue;
                    }
                    if(item.has("handoverRecords") && item.get("handoverRecords") != null){
                        JSONArray jsonArray = item.getJSONArray("handoverRecords");
                        for (int j = 0; j < jsonArray.size(); j++) {
                            JSONObject bean = (JSONObject) jsonArray.get(j);
                            ids.append(bean.getString("id")).append(",");
                        }
                    }
                }
            }
            if (StringUtil.isNotNull(ids.toString())) {
                flag = true;
                request.setParameter("ids", ids.toString());
                sysHandoverConfigMainService.execute(request);
            }
        }
        return flag;
    }

    /**
     * 根据模块id查询对应交接权限数据并执行
     *
     * @param moduleList
     * @param req
     * @throws Exception
     */
    private boolean addSearchAndExecuteAuthData(List<Map<String, String>> moduleList, SysHandoverReq req, SysOrgElement fromElement, SysOrgElement toElement) throws Exception {
        boolean flag = false;
        JSONObject paramObject = new JSONObject();
        JSONArray param = new JSONArray();
        JSONArray authReadersArray = new JSONArray();
        JSONArray authEditorsArray = new JSONArray();
        JSONArray authLbpmReadersArray = new JSONArray();
        JSONArray authAttPrintsArray = new JSONArray();
        JSONArray authAttCopysArray = new JSONArray();
        JSONArray authAttDownloadsArray = new JSONArray();
        for (Map<String, String> map : moduleList) {
            RequestContext request = new RequestContext();
            request.setParameter("fdFromId", fromElement.getFdId());
            request.setParameter("fdToId", toElement.getFdId());
            request.setParameter("authType", "authReaders,authEditors,authLbpmReaders,authAttPrints,authAttCopys,authAttDownloads");
            request.setParameter("fdKey", map.keySet().iterator().next());
            request.setParameter("type", req.getFdType());
            JSONObject jsonObject = sysHandoverConfigMainService.search(request);
            if ("0".equals(jsonObject.getString("total"))) {
                continue;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("item");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                if ("0".equals(object.getString("total"))) {
                    continue;
                }
                JSONObject modulesObject = new JSONObject();
                modulesObject.put("id", object.getString("module"));
                modulesObject.put("total", 0);
                switch (object.getString("item")) {
                    case "authReaders":
                        authReadersArray.add(modulesObject);
                        break;
                    case "authEditors":
                        authEditorsArray.add(modulesObject);
                        break;
                    case "authLbpmReaders":
                        authLbpmReadersArray.add(modulesObject);
                        break;
                    case "authAttPrints":
                        authAttPrintsArray.add(modulesObject);
                        break;
                    case "authAttCopys":
                        authAttCopysArray.add(modulesObject);
                        break;
                    case "authAttDownloads":
                        authAttDownloadsArray.add(modulesObject);
                        break;
                    default:
                        break;
                }
            }
        }
        if (!authReadersArray.isEmpty()) {
            JSONObject authReaders = new JSONObject();
            authReaders.put("id", "authReaders");
            authReaders.put("total", 0);
            authReaders.put("modules", authReadersArray);
            param.add(authReaders);
        }
        if (!authEditorsArray.isEmpty()) {
            JSONObject authEditors = new JSONObject();
            authEditors.put("id", "authEditors");
            authEditors.put("total", 0);
            authEditors.put("modules", authEditorsArray);
            param.add(authEditors);
        }
        if (!authLbpmReadersArray.isEmpty()) {
            JSONObject authLbpmReaders = new JSONObject();
            authLbpmReaders.put("id", "authLbpmReaders");
            authLbpmReaders.put("total", 0);
            authLbpmReaders.put("modules", authLbpmReadersArray);
            param.add(authLbpmReaders);
        }
        if (!authAttPrintsArray.isEmpty()) {
            JSONObject authAttPrints = new JSONObject();
            authAttPrints.put("id", "authAttPrints");
            authAttPrints.put("total", 0);
            authAttPrints.put("modules", authAttPrintsArray);
            param.add(authAttPrints);
        }
        if (!authAttCopysArray.isEmpty()) {
            JSONObject authAttCopys = new JSONObject();
            authAttCopys.put("id", "authAttCopys");
            authAttCopys.put("total", 0);
            authAttCopys.put("modules", authAttCopysArray);
            param.add(authAttCopys);
        }
        if (!authAttDownloadsArray.isEmpty()) {
            JSONObject authAttDownloads = new JSONObject();
            authAttDownloads.put("id", "authAttDownloads");
            authAttDownloads.put("total", 0);
            authAttDownloads.put("modules", authAttDownloadsArray);
            param.add(authAttDownloads);
        }

        if (!param.isEmpty()) {
            flag = true;
            paramObject.put("items", param);
            RequestContext request = new RequestContext();
            request.setParameter("fdFromId", fromElement.getFdId());
            request.setParameter("fdToId", toElement.getFdId());
            request.setParameter("type", req.getFdType());
            request.setParameter("fdContent", paramObject.toString());
            sysHandoverConfigMainService.submitAuth(request);
        }
        return flag;
    }

    /**
     * 查询对应定时任务，并填充返回参数
     *
     * @param resp
     * @param fdId
     * @throws Exception
     */
    private void addReturnResult(SysHandoverResp resp, String fdId) throws Exception {
        List<SysQuartzJob> list = sysQuartzJobService.findList(
                "fdModelName='com.landray.kmss.sys.handover.model.SysHandoverConfigMain' and fdModelId='"
                        + fdId + "'", null);
        if (!CollectionUtils.isEmpty(list)) {
            SysQuartzJob job = list.get(0);
            resp.setFdMsg(ResourceUtil.getString("sysHandoverConfigMain.quartz.desc", "sys-handover", null, DateUtil.convertDateToString(job.getFdRunTime(), DateUtil.PATTERN_DATETIME)));
            resp.setFdTitle(job.getFdTitle());
            resp.setFdUrl(job.getFdLink());
        }
    }
}
