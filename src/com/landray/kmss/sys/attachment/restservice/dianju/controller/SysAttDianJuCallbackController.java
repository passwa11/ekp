package com.landray.kmss.sys.attachment.restservice.dianju.controller;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.sys.attachment.actions.SysAttMainAction;
import com.landray.kmss.sys.attachment.integrate.dianju.interfaces.ISysAttachmentDianJuProvider;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertObserverParameter;
import com.landray.kmss.sys.filestore.convert.observer.ConvertObservable;
import com.landray.kmss.sys.filestore.convert.observer.ConvertObserver;
import com.landray.kmss.sys.filestore.convert.service.CallbackHandle;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.annotation.RestApi;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.landray.kmss.util.SpringBeanUtil.getBean;

@RestController
@RequestMapping("/api/sys-attachment/dianjuCallback")
@RestApi(docUrl = "/sys/attachment/restservice/sysAttDianJuRestServiceHelp.jsp", name = "SysAttDianJuCallbackController", resourceKey = "sys-attachment:SysAttDianJuCallback.title")
public class SysAttDianJuCallbackController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(SysAttDianJuCallbackController.class);
    private static ConvertObservable convertObservable = new ConvertObservable();
    private static ConvertObserver convertObserver = new ConvertObserver(getCallbackHandle());
    static {
        convertObservable.addObserver(convertObserver);
    }

    private static CallbackHandle callbackHandle = null;
    private static CallbackHandle getCallbackHandle() {
        if (callbackHandle == null) {
            callbackHandle =
                    (CallbackHandle) SpringBeanUtil.getBean("dianjuCallbackHandleImpl");
        }

        return callbackHandle;
    }

    private SysAttMainAction sysAttMainAction = new SysAttMainAction();

    private ISysAttachmentDianJuProvider sysAttachmentDianJuProvider;

    public ISysAttachmentDianJuProvider getSysAttachmentDianJuProvider() {
        if (sysAttachmentDianJuProvider == null) {
            sysAttachmentDianJuProvider = (ISysAttachmentDianJuProvider) getBean("sysAttachmentDianJuProvider");
        }
        return sysAttachmentDianJuProvider;
    }

    /**
     * 点聚下载EKP文件接口
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (getSysAttachmentDianJuProvider().checkDownloadToken(request.getParameter("si"))) {
            sysAttMainAction.download(emptyMapping, null, request, response);
        } else {
            logger.warn("点聚下载EKP文件，无效的下载请求"+request.getRequestURL().toString());
        }
    }

    /**
     * 转换回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    @ResponseBody
    public Object convert (HttpServletRequest request, HttpServletResponse response) throws Exception {
       if(logger.isDebugEnabled()) {
           logger.debug("点聚转换已经回调接口convert......");
       }

        InputStream is= null;
        StringBuilder callbackInfo= new StringBuilder();

        try {
            is=request.getInputStream();
            byte[] b = new byte[1024];
            for (int n; (n = is.read(b)) != -1;) {
                callbackInfo.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            logger.error("点聚转换回调问题：" + e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("点聚转换回调关闭流出现问题：" + e);
                }
            }
        }

        if(logger.isDebugEnabled()) {
            logger.debug("点聚转换回调的信息是：{}", callbackInfo.toString());
        }
        JSONObject jsonObject = JSONObject.parseObject(callbackInfo.toString());
        String taskId = jsonObject.getString("SERIAL_NUMBER");
        String message = jsonObject.getString("RET_MSG");
        String retCode = jsonObject.getString("retCode");
        if(!"1".equals(retCode)) {
            logger.error("点聚转换失败，回调信息为:SERIAL_NUMBER={},RET_MSG={},retCode={}",taskId, message, retCode);
        }

        if(logger.isDebugEnabled()) {
            logger.debug("点聚转换回调的信息进行解析得到SERIAL_NUMBER={},RET_MSG={}" , taskId, message);
        }

        ConvertObserverParameter convertObserverParameter = new ConvertObserverParameter()
                .setTaskId(taskId)
                .setMessage(message)
                .setRetCode(retCode)
                .setProductName("Dianju");
        convertObservable.handleConvertCallback(convertObserverParameter);

        JSONObject result = new JSONObject();
        result.put("RET_CODE", 1);
        return result.toString();
    }

    public static void main(String[] args) {
        String cfgPath = "com/landray/kmss/sys/attachment/restservice/dianju/util/dianju_lightRead.json";
        try {
            File file = ResourceUtils.getFile("classpath:"+cfgPath);
            JSONObject props = JSONObject.parseObject(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
            System.out.println(props.getString("dianju.lightRead.appUrl"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
