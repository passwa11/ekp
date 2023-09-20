package com.landray.kmss.sys.attachment.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.sys.attachment.util.JgWebOffice;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.RestResponse;

/**
 * @Description: 金格在线编辑
 * @Author: admin
 * @Date: 2020-12-30 10:00
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/sys-attachment/sysJgWebOffice")
public class SysJgWebOfficeController extends BaseController {

    protected final Log log = LogFactory.getLog(SysJgWebOfficeController.class);

    /**
     * @Description 获取session
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?>
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "getJGSession")
    public RestResponse<?> getJGSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);

        Map<String,String> sessionInfo = new HashMap<>();
        sessionInfo.put("JSESSIONID", request.getSession().getId());

        String cookieHeader = request.getHeader("Cookie");
        if (StringUtil.isNotNull(cookieHeader)) {
            String[] cookies = cookieHeader.split(";");
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    String cookie = cookies[i].trim();
                    sessionInfo.put(cookie.substring(0, cookie.indexOf("=")), cookie.substring(cookie.indexOf("=") + 1, cookie.length()));
                }
            }
        }
        return result(request,sessionInfo);
    }

    /**
     * @Description 获取金格控件信息
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?>
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "getJGVersion")
    public RestResponse<?> getJGVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> jgInfo = new HashMap<>();
        //判断金格控件大版本号2009 or 2015
        String JGBigVersion = JgWebOffice.getJGBigVersion();
        jgInfo.put("JGBigVersion", JGBigVersion);

        //判断金格pdf控件大版本号iWebPDF or iWebPDF2018
        String JGBigWebPDFVersion = JgWebOffice.getPDFBigVersion();
        jgInfo.put("JGBigWebPDFVersion", JGBigWebPDFVersion);

        //判断当前操作系统
        String osType = JgWebOffice.getOSType(request);
        jgInfo.put("osType", osType);

        //判断是否开启国产化控件
        String isEnabled = "false";
        String isEnabledZZKK = JgWebOffice.getIsJGHandZzkkEnabled();
        if (null != isEnabledZZKK && "true".equals(isEnabledZZKK)) {
            isEnabled = "true";
        }
        jgInfo.put("isEnabled", isEnabled);

        //判断配置的金格控件类型
        String jgPluginType = JgWebOffice.getJGPluginType();
        jgInfo.put("jgPluginType", jgPluginType);

        //判断金格2003版本使用的办公软件类型
        String jgOfficeType = JgWebOffice.getJGOfficeType();
        jgInfo.put("jgOfficeType", jgOfficeType);

        return result(request, jgInfo);
    }

    /**
     * @Description 获取金格控件信息
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?>
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "getJGOCXInfo")
    public RestResponse<?> getJGOCXInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> jgInfo = new HashMap<>();
        String clsid = ResourceUtil.getKmssConfigString("sys.att.jg.clsid");
        //控件类型
        String plugintype = ResourceUtil.getKmssConfigString("sys.att.jg.plugintype");

        if(StringUtil.isNull(clsid)){
            clsid = "8B23EA28-2009-402F-92C4-59BE0E063499";
            if ("2003".equals(plugintype)) {
                clsid = "23739A7E-5741-4D1C-88D5-D50B18F7C347";
            }
        }
        jgInfo.put("clsid",clsid);

        String JGBigVersion = JgWebOffice.getJGBigVersion();
        if("windows".equals(JgWebOffice.getOSType(request))) {
            if (null != JGBigVersion && JGBigVersion.equals(JgWebOffice.JG_OCX_BIG_VERSION_2015)) {
                //2015
                jgInfo.put("jgOcxUrl", JgWebOffice.getJGOcxURL2015());
                jgInfo.put("jgOcxVersion", JgWebOffice.getJGOcxVersion2015());
                jgInfo.put("jgOcxCopyright", JgWebOffice.getJGOcxCopyRight2015());
            }
        } else {
            if (null != JgWebOffice.getIsJGHandZzkkEnabled() && "true".equals(JgWebOffice.getIsJGHandZzkkEnabled())) {
                //ZZKK
                jgInfo.put("jgOcxCopyright", JgWebOffice.getJGOcxCopyRightZZKK());
            }
        }
        return result(request, jgInfo);
    }

    /**
     * @Description 执行jg函数特性
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?>
     * @throws
     */
    @RequestMapping(value = "execute")
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JgWebOffice ws = new JgWebOffice();
        ws.execute(request, response);
    }

    @RequestMapping(value = "executeAddition")
    public void executeAddition(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JgWebOffice ws = new JgWebOffice();
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
        String isAddition = reqWrapper.getParameter("_addition");
        if ("1".equals(isAddition)) {
            ws.addition(reqWrapper, response);
        }
    }
}