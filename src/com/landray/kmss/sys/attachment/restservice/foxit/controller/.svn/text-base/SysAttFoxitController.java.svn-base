package com.landray.kmss.sys.attachment.restservice.foxit.controller;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.sys.attachment.integrate.foxit.ISysAttachmentFoxitProvider;
import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.restservice.foxit.controller.dao.CommonCallbackParam;
import com.landray.kmss.sys.attachment.restservice.foxit.controller.dao.FileInfoMinioBase;
import com.landray.kmss.sys.attachment.restservice.foxit.controller.dao.Results;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.convert.observer.ConvertObservable;
import com.landray.kmss.sys.filestore.convert.observer.ConvertObserver;
import com.landray.kmss.sys.filestore.convert.service.CallbackHandle;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.FoxitObserverParameter;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 福昕Controller
 */
@RestController
@RequestMapping("/api/sys-attachment/foxit")
@RestApi(docUrl = "", name = "SysAttFoxitController", resourceKey = "sys-attachment:attachment.foxit.controller.title")
public class SysAttFoxitController  extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysAttFoxitController.class);
    private static final String FOXIT_DOC_CONTENT_TYPE_PDF = "application/pdf";
    private static final String FOXIT_DOC_CONTENT_TYPE_OFD = "application/ofd";
    private static final String FOXIT_CONVERT_DOC_OFD = "toOFD-Foxit_ofd";
    private static final String FOXIT_CONVERT_DOC_PDF = "toPDF-Foxit_pdf";
    private static final String FOXIT_CONVERT_FILE_PATH = "_convert";
    private static final String FOXIT_CONVERT_FILE_PDF = "_pdf";
    private static final String FOXIT_CONVERT_FILE_OFD= "_ofd";
    private static final String FOXIT_CONVERT_TO_PDF = "toPDF";
    private static final String FOXIT_CONVERT_TO_OFD = "toOFD";
    private static final String FOXIT_DOC_SUFFIX_OFD = ".ofd";
    private static final String FOXIT_DOC_SUFFIX_PDF = ".pdf";
    private static final String PERMISSION_OFF = "0";
    private static final String PERMISSION_ON = "1";
    private static final String PERMISSION_TWO = "2";

    private static ConvertObservable convertObservable = new ConvertObservable();
    private static ConvertObserver convertObserver = new ConvertObserver(getCallbackHandle());
    static {
        convertObservable.addObserver(convertObserver);
    }
    /**
     * 接口组件
     */
    private ISysAttachmentFoxitProvider sysAttachmentFoxitProvider = null;
    private ISysAttachmentFoxitProvider getSysAttachmentFoxitProvider() {
        if(sysAttachmentFoxitProvider == null) {
            sysAttachmentFoxitProvider = (ISysAttachmentFoxitProvider)
                    SpringBeanUtil.getBean("foxitProvider");
        }
        return sysAttachmentFoxitProvider;
    }

    private ISysAttMainCoreInnerService sysAttMainService;


    private ISysAttMainCoreInnerService getSysAttMainService() {
        if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
        }
        return sysAttMainService;
    }

    private ISysFileConvertQueueService convertQueueService;
    private ISysFileConvertQueueService getConvertQueueService() {
        if (convertQueueService == null) {
            convertQueueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
        }
        return convertQueueService;
    }

    private static CallbackHandle callbackHandle = null;
    private static CallbackHandle getCallbackHandle() {
        if (callbackHandle == null) {
            callbackHandle =
                    (CallbackHandle) SpringBeanUtil.getBean("foxitCallbackHandleImpl");
        }

        return callbackHandle;
    }

    private ISysAttUploadService sysAttUploadService = null;
    private ISysAttUploadService getAttUploadService() {
        if (sysAttUploadService == null) {
            sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
        }
        return sysAttUploadService;
    }


    /**
     * 福昕阅读下载EKP文件接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/document", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam String fileId, @RequestParam String sysToken) throws Exception {
        String requestToken = sysToken;
        if(!getSysAttachmentFoxitProvider().checkToken(requestToken)) {
            logger.error("福昕阅读请求系统下载文件，校验token失败:{}", requestToken);
            return;
        }

        try {

            String fdId = fileId;

            if(StringUtil.isNull(fdId)) {
                logger.error("请求下载文件fdId为空");
                return;
            }

            String[] l_fdId = fdId.split(";");
            List<SysAttMain> sysAttMains =  getSysAttMainService().findModelsByIds(l_fdId);

            if(sysAttMains == null || sysAttMains.size() <= 0) {
                logger.error("请求下载的文件不存在，fdId:{}", fdId);
                return;
            }

            SysAttMain sysAttMain = sysAttMains.get(0);

            String contentType = sysAttMain.getFdContentType();
            String fileName = sysAttMain.getFdFileName();
            SysAttFile sysAttFile = ((ISysAttMainCoreInnerService) SpringBeanUtil
                    .getBean("sysAttMainService"))
                    .getFile(sysAttMain.getFdId());
            String filePath = sysAttFile.getFdFilePath();
            if(logger.isDebugEnabled()) {
                logger.debug("福昕阅读下载EKP文件路径:{},文件名:{}", filePath, fileName);
            }

            // 如果是PDF或OFD文件直接输出
            if(fileName.toLowerCase().endsWith(FOXIT_DOC_SUFFIX_OFD)
                    || fileName.toLowerCase().endsWith(FOXIT_DOC_SUFFIX_PDF)) {

                outputFile(response, sysAttMain.getFdFileName(), contentType, filePath);
                return;
            }

            // 输出转换后的文件
            outputConvertFile(response, filePath, fileId);

        } catch (Exception e) {
            logger.error("Foxit下载EKP的文件出现异常:" + e.getStackTrace());
        }

    }

    /**
     * 权限接口
     * @param request
     * @param userId
     * @param fileId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/document/permission", method = RequestMethod.GET)
    public Map getPermission(HttpServletRequest request, @RequestParam String userId,
                             @RequestParam String fileId, @RequestParam String canPrint) throws  Exception{
        if(logger.isDebugEnabled()) {
            logger.debug("Foxit调用权限接口,userId:{},fileId:{},canPrint:{}", userId, fileId, canPrint);
        }

        Map<String, Object> resultMap = new HashMap<>();

        if(StringUtil.isNull(fileId)) {
            logger.error("Foxit调用权限接口，文件ID为空!导致无法获取部分控件权限.");
        }


        resultMap.put("ret", "0");
        resultMap.put("message", "success");
        Map<String, Object> data = getPermissionData(canPrint);

        resultMap.put("data", data);

        if(logger.isDebugEnabled()) {
            logger.debug("Foxit获取权限参数:{}", JSONObject.toJSONString(resultMap));
        }
        return resultMap;
    }

    /**
     * 为清晰水印
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/document/getExplicitWatermarkInfos", method = RequestMethod.GET)
    public Map getExplicitWatermarkInfos(HttpServletRequest request, @RequestParam String userId,
                                         @RequestParam String fileId) throws  Exception{
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("ret", "0");
        resultMap.put("message", "success");
        Map<String, Object> data = new FoxitWaterInfo()
                .getBaseInfo()
                .getWordWater(request, userId)
                .getPicWater(request)
                .getData();
        resultMap.put("data", data);

        if(logger.isDebugEnabled()) {
            logger.debug("Foxit获取水印信息:{}", JSONObject.toJSONString(resultMap));
        }

        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = "/convert/callback", method = RequestMethod.POST)
    public Results convertCallback(@RequestBody CommonCallbackParam req, HttpServletRequest request) {
       if (logger.isDebugEnabled()) {
           logger.debug("福昕转换回调信息是:{}", JSONObject.toJSONString(req));
       }

        List<FileInfoMinioBase> files = req.getFiles();
        FoxitObserverParameter foxitObserverParameter = new FoxitObserverParameter();

        // 业务只会一条转换，所以只会取到一条记录
        for(FileInfoMinioBase file : files) {
            foxitObserverParameter.setFileId(file.getFileId());
            foxitObserverParameter.setDownloadUrl(file.getDownloadPath());
        }

        foxitObserverParameter.setRetCode(req.getCode());

        convertObservable.handleConvertCallback(foxitObserverParameter);

        return Results.ok(req);
    }
    /**
     * 文件已在队列中，如果转换了，则输出；否则提示未转换
     * @param response
     * @param filePath
     * @throws Exception
     */
    public Boolean outputConvertFile(HttpServletResponse response, String filePath, String fileId) throws Exception {

        String fPath = filePath+ FOXIT_CONVERT_FILE_PATH;
        if(logger.isDebugEnabled()) {
            logger.debug("Foxit下载文件根路径:{}", fPath);
        }

        String ofdPath = fPath + File.separator + FOXIT_CONVERT_DOC_OFD;

        SysAttFile sysAttFile = ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
                .getFile(fileId);
        if(sysAttFile == null) {
            logger.warn("文件正在转换中！！！");
            return false;
        }

        String FilePath = getAttUploadService().getAbsouluteFilePath(sysAttFile.getFdId());
        String bakf = FilePath + "_bak";
        SysAttFile file = getAttUploadService().getFileById(sysAttFile.getFdId());
        String pathPrefix = file.getFdCata() == null ? null : file.getFdCata().getFdPath();
        Boolean exist = SysFileLocationUtil.getProxyService(file.getFdAttLocation()).doesFileExist(bakf,pathPrefix);
        // 是否存在Foxit的OFD文件
        if(exist) {
            outputFile(response, FOXIT_CONVERT_DOC_OFD, FOXIT_DOC_CONTENT_TYPE_OFD, ofdPath);
            return true;
        }

        String pdfPath = fPath + File.separator + FOXIT_CONVERT_DOC_PDF;
        // 是否存在Foxit的PDF文件
        if(exist) {
            outputFile(response, FOXIT_CONVERT_DOC_PDF, FOXIT_DOC_CONTENT_TYPE_PDF, pdfPath);
            return true;
        }

        String fName = "";
        String cType = "";
        boolean flag = false;
        List<String> fileNames = SysFileLocationUtil.getProxyService().listFileNames(fPath);
        // 查询文件路径下，随便取一份OFD或PDF文件
        for(String fileName : fileNames) {
            if(fileName.endsWith(FOXIT_CONVERT_FILE_PDF)) {
                int last = fileName.lastIndexOf("/");
                fName = fileName.substring(last + 1, fileName.length());
                cType = FOXIT_DOC_CONTENT_TYPE_PDF;
                flag = true;
                break;
            } else if (fileName.endsWith(FOXIT_CONVERT_FILE_OFD)){
                int last = fileName.lastIndexOf("/");
                fName = fileName.substring(last + 1, fileName.length());
                cType = FOXIT_DOC_CONTENT_TYPE_OFD;
                flag = true;
                break;
            }
        }

        String name = "";
        // 文件名处理
        if(fName.contains(FOXIT_CONVERT_TO_PDF)) {
            name = fName + FOXIT_DOC_SUFFIX_PDF;
        } else if(fName.contains(FOXIT_CONVERT_TO_OFD)){
            name = fName + FOXIT_DOC_SUFFIX_OFD;
        }

        if(!flag) {
            logger.warn("文件正在转换中！！！");
            return false;
        }

        // 文件流写出
        outputFile(response, name, cType, fPath + File.separator + fName);

        return true;
    }

    /**
     * 文件写出
     * @param response
     * @param filename
     * @param contentType
     * @param filePath
     * @throws Exception
     */
    public void outputFile(HttpServletResponse response, String filename,
                          String contentType, String filePath) throws Exception{
        InputStream in = null;
        OutputStream out = null;
        try {
            response.setContentType(contentType);
            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" +
                            new String(filename.getBytes("utf-8"),"ISO8859-1") + "\"");

            if(logger.isDebugEnabled()) {
                logger.debug("请求下载文件，EKP文件所处位置:{}", filePath);
            }

            in = SysFileLocationUtil.getProxyService().readFile(filePath);

            if(in == null) {
                logger.error("文件流不存在，请求查检文件路径:{}", filePath);
            }

            out = response.getOutputStream();
            IOUtil.write(in, out);

        } catch (Exception e) {
            if(out != null) {
                IOUtils.closeQuietly(out);
            }
            if(in != null) {
                IOUtils.closeQuietly(in);
            }
            logger.error("Foxit下载EKP的文件出现异常:" + e.getStackTrace());
        } finally {
            if(out != null) {
                IOUtils.closeQuietly(out);
            }
            if(in != null) {
                IOUtils.closeQuietly(in);
            }
        }
    }

    /**
     * 获取权限的参数
     * @return
     */
    public Map<String, Object> getPermissionData(String canPrint) {

        return new FoxitPermission()
                .setPermission(PERMISSION_TWO)
                .setHead(PERMISSION_OFF).setOpenFileBtn(PERMISSION_OFF)
                .setSaveBtn(PERMISSION_OFF).setPrintBtn(canPrint)
                .setGoToPageBox(PERMISSION_ON).setZoomPageBox(PERMISSION_ON)
                .setPageLayoutBtn(PERMISSION_OFF).setHandToolBtn(PERMISSION_OFF)
                .setTextSelectBtn(PERMISSION_OFF).setHeightLightBtn(PERMISSION_OFF)
                .setUnderlineBtn(PERMISSION_OFF).setPencilBtn(PERMISSION_OFF)
                .setDrawingAnnotBtn(PERMISSION_OFF).setCommentsBtn(PERMISSION_OFF)
                .setElecSignatureBtn(PERMISSION_OFF).setCheckElecSignatureBtn(PERMISSION_OFF)
                .setRotateSwitchBtn(PERMISSION_OFF).setExportBtn(PERMISSION_OFF)
                .setOutlineBtn(PERMISSION_OFF).setThumbnailBtn(PERMISSION_ON)
                .setCommentListBtn(PERMISSION_OFF).setSearchBtn(PERMISSION_ON)
                .setSemanticTreeBtn(PERMISSION_OFF).setAttachmentBtn(PERMISSION_OFF)
                .setPageRange("ALL").getData();
    }
}
