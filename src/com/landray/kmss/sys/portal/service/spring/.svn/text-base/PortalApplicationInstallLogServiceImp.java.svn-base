package com.landray.kmss.sys.portal.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.portal.service.IPortalApplicationInstallLogService;
import com.landray.kmss.sys.portal.service.ISysPortalService;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 安装门户业务实现类
 * @author: wangjf
 * @time: 2021/6/22 11:53 上午
 * @version: 1.0
 */

public class PortalApplicationInstallLogServiceImp implements IPortalApplicationInstallLogService, IXMLDataBean {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(PortalApplicationInstallLogServiceImp.class);

    private ISysPortalService getSysPortalService() {
        return (ISysPortalService) SpringBeanUtil.getBean("sysPortalService");
    }

    @Override
    public List getDataList(RequestContext requestContext) throws Exception {
        List<JSONObject> list = new ArrayList<>();
        Map<String, String> resultMap = saveInstall(requestContext);
        JSONObject json = new JSONObject();
        if (CollectionUtils.isEmpty(resultMap)) {
            json.put("status", "error");
            json.put("msg", "安装失败");
        } else {
            if (resultMap.get("fdId") != null) {
                json.put("fdId", resultMap.get("fdId"));
            }
            json.put("status", resultMap.get("status"));
            json.put("msg", resultMap.get("msg"));
        }
        list.add(json);
        return list;
    }

    @Override
    public String preInstall(RequestContext requestContext) throws Exception {
        //可以做各种安装前的事宜，比如验证等。
        String filePath = requestContext.getParameter("filePath");
        // 安装前把文件拷贝出来，拷贝结束后删除下载路径的文件 ,不把文件拷贝出来则会出现#157679 多个文件同时安装的时候将出现该问题
        String tmpFilePath = ResourceUtil.KMSS_RESOURCE_PATH + File.separator + "portalTmp" + File.separator;
        FileUtil.createDir(tmpFilePath, true);
        String targetFilePath = tmpFilePath + File.separator + IDGenerator.generateID() + ".zip";
        FileUtil.copy(filePath,targetFilePath);
        FileUtil.deleteFile(filePath);
        return targetFilePath;
    }

    @Override
    public Map<String, String> saveInstall(RequestContext requestContext) throws Exception {
        String filePath = preInstall(requestContext);
        if (StringUtil.isNull(filePath)) {
            return null;
        }
        Map<String, String> stringStringMap = installIng(filePath, requestContext);
        return stringStringMap;
    }

    @Override
    public Map<String, String> installIng(String filePath, RequestContext requestContext) {
        Map<String, String> result = new HashMap<>();
        if (!filePath.endsWith(".zip")) {
            filePath = filePath + ".zip";
        }
        try {
            String mainId = getSysPortalService().saveImport(filePath, requestContext);
            result.put("fdId", mainId);
            result.put("status", "success");
            result.put("msg", "安装成功");
            postInstall(requestContext, result);
        } catch (Exception e) {
            result.put("status", "error");
            result.put("msg", "安装失败");
            logger.error("门户安装失败", e);
        }
        return result;
    }

    @Override
    public boolean postInstall(RequestContext requestContext, Map<String, String> result) throws Exception {
        if (SpringBeanUtil.getApplicationContext() != null) {
            Map eventMap = new HashMap<>();
            eventMap.put("fdId", requestContext.getParameter("fdId"));
            eventMap.put("fdClientId", requestContext.getParameter("fdClientId"));
            eventMap.put("fdCurrentVersion", requestContext.getParameter("fdCurrentVersion"));
            eventMap.put("fdAttMainId", requestContext.getParameter("fdAttMainId"));
            eventMap.put("fdIsAvailable", "I");
            SpringBeanUtil.getApplicationContext().publishEvent(new Event_Common("portalInstallApplicationComplete", eventMap));
        }
        return true;
    }
}