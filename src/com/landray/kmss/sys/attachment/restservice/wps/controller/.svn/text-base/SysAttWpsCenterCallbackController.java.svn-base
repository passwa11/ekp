package com.landray.kmss.sys.attachment.restservice.wps.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.sys.attachment.actions.SysAttMainAction;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCenterOfficeProvider;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.restservice.wps.util.WpsUtil;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.ImageCropUtil;
import com.landray.kmss.sys.attachment.util.SysAttUtil;
import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;
import com.landray.kmss.sys.filestore.convert.cache.ConvertQueueCache;
import com.landray.kmss.sys.filestore.constant.ConvertConstant;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertRedisCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.IWPSCenterCallbackResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.util.WPSCenterCallBusinessUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.landray.kmss.sys.attachment.restservice.wps.util.WpsUtil.checkSignature;
import static com.landray.kmss.util.SpringBeanUtil.getBean;

@RestController
@RequestMapping("/api/sys-attachment/wpsCenterCallback")
@RestApi(docUrl = "/sys/attachment/restservice/sysAttWpsCenterRestServiceHelp.jsp", name = "SysAttWpsCenterCallbackController", resourceKey = "sys-attachment:SysAttWpsCenterCallback.title")
public class SysAttWpsCenterCallbackController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(SysAttWpsCenterCallbackController.class);

    private SysAttMainAction sysAttMainAction = new SysAttMainAction();

    private ISysAttMainCoreInnerService sysAttMainService;

    private ISysAttachmentWpsCenterOfficeProvider sysAttachmentWpsCenterOfficeProvider;

    private ISysAttMainCoreInnerService getSysAttMainService() {
        if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
        }
        return sysAttMainService;
    }

    private ISysAttachmentWpsCenterOfficeProvider getSysAttachmentWpsCenterOfficeProvider() {
        if (sysAttachmentWpsCenterOfficeProvider == null) {
            sysAttachmentWpsCenterOfficeProvider = (ISysAttachmentWpsCenterOfficeProvider)getBean("wpsCenterProvider");
        }
        return sysAttachmentWpsCenterOfficeProvider;
    }

    private IWPSCenterCallbackResult wpsCenterCallbackResult = null;
    private IWPSCenterCallbackResult getWpsCenterCallbackResult() {
        if(wpsCenterCallbackResult == null) {
            wpsCenterCallbackResult = (IWPSCenterCallbackResult) getBean("wpsCenterCallbackResultImp");
        }

        return wpsCenterCallbackResult;
    }


    private String generateDownloadUrl(String fdId, String token) throws Exception {
        String url = null;
        if (StringUtils.isNotEmpty(fdId)) {
            String urlPrefix = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
            if (StringUtils.isEmpty(urlPrefix)) {
                urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            }
            if(urlPrefix.endsWith("/")){
                urlPrefix = urlPrefix.substring(0, urlPrefix.lastIndexOf("/"));
            }
            url = urlPrefix + "/api/sys-attachment/wpsCenterCallback/download?fdId=" + fdId + "&_w_l_token=" + token;
        }
        return url;
    }

    /**
     * （WPS）下载（EKP的）文件接口（带鉴权）
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long s = System.currentTimeMillis();
        String w_l_token = request.getParameter("_w_l_token");
        if (logger.isDebugEnabled()) {
            logger.debug("WPS回调下载文件开始,入参_w_l_token = {}", w_l_token);
        }
        Boolean ok = getSysAttachmentWpsCenterOfficeProvider().validateCallBackToken(w_l_token);
        if (!ok) {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS回调下载文件失败,无效token,入参_w_l_token = {}", w_l_token);
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        sysAttMainAction.download(emptyMapping, null, request, response);
        if(logger.isDebugEnabled()){
            if (logger.isDebugEnabled()) {
                logger.debug("WPS回调下载文件完成,耗时 = {},入参_w_l_token = {}",System.currentTimeMillis() - s, w_l_token);
            }
        }
    }

    /**
     * 获取当前协作者信息（/v1/3rd/user/info）
     *
     * @param request
     * @param response
     * @param ids
     * @return
     * @throws Exception
     */
    @PostMapping("/v1/3rd/user/info")
    public String getUserInfos(HttpServletRequest request, HttpServletResponse response, @RequestBody String ids) throws Exception {
        String w_l_token = request.getParameter("_w_l_token");
        if (logger.isDebugEnabled()) {
            logger.debug("WPS回调获取用户信息开始,入参_w_l_token = {},ids = {}", w_l_token,ids);
        }
        w_l_token = handleLongToken(request, w_l_token);
        Boolean ok = getSysAttachmentWpsCenterOfficeProvider().validateCallBackToken(w_l_token);
        if (!ok) {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS回调获取用户信息失败,无效token,入参_w_l_token = {},ids = {}", w_l_token,ids);
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
        if (StringUtil.isNotNull(ids)) {
            long s = System.currentTimeMillis();
            JSONObject paramJson = JSONObject.fromObject(ids);
            if (paramJson.get("ids") != null) {
                JSONArray idArray = paramJson.getJSONArray("ids");
                if (!idArray.isEmpty()) {
                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < idArray.size(); i++) {
                        String id = idArray.getString(i);
                        SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(id,
                                SysOrgPerson.class, true);
                        if (person != null) {
                            JSONObject user = new JSONObject();
                            user.put("id", person.getFdId());
                            user.put("name", person.getFdName());
                            user.put("avatar_url", getPersonImage(person.getFdId()));
                            jsonArray.add(user);
                        }
                    }
                    jsonObject.put("users", jsonArray);
                    if (logger.isDebugEnabled()) {
                        logger.debug("WPS回调获取用户信息完成,耗时 = {},入参_w_l_token = {},ids = {}",System.currentTimeMillis() - s, w_l_token,ids);
                    }
                    return jsonObject.toString();
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("WPS回调获取用户信息失败,异常入参,入参_w_l_token = {},ids = {}", w_l_token,ids);
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return null;
    }

    private String getPersonImage(String personId) throws Exception {
        String personImageUrl = "";
        String modelName = SysZonePersonInfo.class.getName();
        String fdKey = SysZoneConstant.PHOTO_SRC_KEY + ImageCropUtil.CROP_KEYS[0];
        List list = getSysAttMainService().findByModelKey(modelName, personId, fdKey);
        // 旧的key值
        if (list.isEmpty()) {
            list = getSysAttMainService().findByModelKey(modelName, personId, "zonePersonInfo");
        }
        if (list !=null && !list.isEmpty()) {
            SysAttMain sysAtt = (SysAttMain) list.get(0);
            personImageUrl = generateDownloadUrl(sysAtt.getFdId(), getSysAttachmentWpsCenterOfficeProvider().getLongCallBackToken(personId,getSysAttachmentWpsCenterOfficeProvider().getWpsToken(),true));
        } else {
            String defaultHeadImageUrl = PersonInfoServiceGetter.getPersonDefaultHeadImageUrl(personId);
            String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            if(urlPrefix.endsWith("/")){
                urlPrefix = urlPrefix.substring(0, urlPrefix.lastIndexOf("/"));
            }
            personImageUrl = urlPrefix + defaultHeadImageUrl;
        }
        return personImageUrl;
    }

    private String handleLongToken(HttpServletRequest request,String token) {
        //write：编辑，read：预览
        String fdMode = request.getParameter("_w_fdMode");
        //编辑模式使用中台sdk传递token鉴权
        if ("write".equals(fdMode)) {
            token = request.getHeader("x-wps-weboffice-token");
            if (logger.isDebugEnabled()) {
                logger.debug("编辑模式从中台header获取x-wps-weboffice-token, 赋值给w_l_token = {}", token);
            }
        }
        return token;
    }

    /**
     * WPS获取EKP文件信息
     * @param request
     * @param response
     * @param fdId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/v1/3rd/file/info")
    public Object fileInfo(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("_w_fileId") String fdId)
            throws Exception {
        //用户操作权限，write：可编辑，read：预览
        String fdMode = request.getParameter("_w_fdMode");
        String w_l_token = request.getParameter("_w_l_token");
        if (logger.isDebugEnabled()) {
            logger.debug("WPS回调获取文件信息开始,入参_w_l_token = {},_w_fileId = {}", w_l_token,fdId);
        }
        w_l_token = handleLongToken(request, w_l_token);
        if (fdId == null || fdId.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS回调获取文件信息失败,_w_fileId为空,入参_w_l_token = {},_w_fileId = {}", w_l_token,fdId);
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        SysAttMain sysAttMain = (SysAttMain) getSysAttMainService().findByPrimaryKey(fdId);
        if (sysAttMain == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS回调获取文件信息失败,无效_w_fileId,入参_w_l_token = {},_w_fileId = {}", w_l_token,fdId);
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Boolean ok = getSysAttachmentWpsCenterOfficeProvider().validateCallBackToken(w_l_token);
        if (!ok) {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS回调获取文件信息失败,无效token,入参_w_l_token = {},_w_fileId = {}", w_l_token,fdId);
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        long s = System.currentTimeMillis();

        //用户 id，长度小于 32
        String userid = request.getParameter("_w_userid");
        //用户名称
        String username = request.getParameter("_w_username");
        //文字水印的文字，当 type 为 1 时此字段必选
        String markword = request.getParameter("_w_markWord");
        //限制预览页数
        String preview_pages = request.getParameter("_w_preview_pages");
        //重命名权限，1 为打开该权限，0 为关闭该权限，默认0
        String rename = request.getParameter("_w_rename");
        //历史版本权限，1 为打开该权限，0 为关闭该权限,默认1
        String history = request.getParameter("_w_history");
        //复制
        String copy = request.getParameter("_w_copy");
        //导出 PDF
        String export = request.getParameter("_w_export");
        //打印
        String print = request.getParameter("_w_print");
        String signature = request.getParameter("_w_sign");
        String value = copy + "##" + export + "##" + print;
        boolean success = checkSignature(value, userid, signature);
        if (!success) {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS回调获取文件信息失败,签名校验失败,入参_w_l_token = {},_w_fileId = {}, signature = {}, userid = {}, value = {}", w_l_token, fdId, signature, userid, value);
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
                    .getBean("sysOrgPersonService");
            SysOrgPerson creator = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(sysAttMain.getFdCreatorId());
            String filename = sysAttMain.getFdFileName();
            JSONObject file = new JSONObject();
            JSONObject user = new JSONObject();

            SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(userid,
                    SysOrgPerson.class, true);
            if (person != null) {
                username = person.getFdName();
            }
//            file.put("id", fdId);
            file.put("id", WpsUtil.MD5_32(fdId+"@"+sysAttMain.getFdFileId()));
            file.put("name", filename);
            file.put("version", sysAttMain.getFdVersion());
            file.put("size", new BigDecimal(sysAttMain.getFdSize()));
            file.put("creator", creator.getFdName());
            file.put("create_time", sysAttMain.getDocCreateTime() != null ? sysAttMain.getDocCreateTime().getTime()/1000L
                    : new Date().getTime()/1000L);
            file.put("modifier", username);
            file.put("modify_time", new Date().getTime()/1000L);
            Map<String, String> additionInfo = new HashMap<>();
            additionInfo.put("token", getSysAttachmentWpsCenterOfficeProvider().getOnceCallBackToken(userid,fdId,getSysAttachmentWpsCenterOfficeProvider().getWpsToken()));
            file.put("download_url", SysAttUtil.generateDownloadUrl(sysAttMain, ConvertConstant.THIRD_CONVERTER_WPS_CENTER, additionInfo));
            file.put("preview_pages", StringUtils.isEmpty(preview_pages) ? 0 : Integer.valueOf(preview_pages));
            JSONObject userAcl = new JSONObject();
            userAcl.put("rename", StringUtils.isEmpty(rename) ? 0 : Integer.valueOf(rename));
            userAcl.put("history", StringUtils.isEmpty(history) ? 0 : Integer.valueOf(history));
            userAcl.put("copy", StringUtils.isEmpty(copy) ? 0 : Integer.valueOf(copy));
            userAcl.put("export", StringUtils.isEmpty(export) ? 0 : Integer.valueOf(export));
            userAcl.put("print", StringUtils.isEmpty(print) ? 0 : Integer.valueOf(print));
            file.put("user_acl", userAcl);
            JSONObject watermarkCfg = SysAttViewerUtil.getWaterMarkConfigInDB(true);

            if ("true".equals(watermarkCfg.get("showWaterMark"))) {
                if (StringUtil.isNull(markword)) {
                    markword = username;
                }
                JSONObject watermark = new JSONObject();
                watermark.put("type", 1);
                watermark.put("value", markword);
                String markOpacity = "rgba( 192, 192, 192, 0.6 )";
                if (watermarkCfg.get("markOpacity") != null) {
                    markOpacity = "rgba( 192, 192, 192, " + watermarkCfg.get("markOpacity") + ")";
                }
                watermark.put("fillstyle", markOpacity);
                String markWordFontFamily = "bold 20px Serif";
                if (watermarkCfg.get("markWordFontFamily") != null) {
                    markWordFontFamily = "bold " + watermarkCfg.get("markWordFontSize") + "px "
                            + watermarkCfg.get("markWordFontFamily");
                }
                watermark.put("font", markWordFontFamily);
                watermark.put("rotate", -0.7853982);

                int markRowSpace = 50;
                if (watermarkCfg.get("markRowSpace") != null) {
                    markRowSpace = watermarkCfg.getInt("markRowSpace");
                }
                watermark.put("horizontal", markRowSpace);
                int markColSpace = 100;
                if (watermarkCfg.get("markColSpace") != null) {
                    markColSpace = watermarkCfg.getInt("markColSpace");
                }
                watermark.put("vertical", markColSpace);

                file.put("watermark", watermark);
            }else {
                file.put("watermark", null);
            }

            jsonObject.put("file", file);
            user.put("id", userid);
            user.put("name", username);
            user.put("permission", fdMode);
            user.put("avatar_url", getPersonImage(userid));
            jsonObject.put("user", user);
        } catch (Exception e) {
            logger.error("WPS回调获取文件信息异常：", e);
        }
        if(logger.isDebugEnabled()){
            logger.debug("WPS回调获取文件信息完成,入参_w_l_token = {},_w_fileId = {},耗时 = {}, 文件信息 = {}",
                    w_l_token,fdId,System.currentTimeMillis() - s,jsonObject.toString());
        }
        return jsonObject.toString();
    }

    /**
     * WPS保存文件到EKP
     * @param request
     * @param response
     * @param file
     * @param fdId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/3rd/file/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(HttpServletRequest request, HttpServletResponse response, @RequestParam("_w_userid") String userId,
                       @RequestParam("file") MultipartFile file, @RequestParam("_w_fileId") String fdId)
            throws Exception {
        String w_l_token = request.getParameter("_w_l_token");
        if (logger.isDebugEnabled()) {
            logger.debug("WPS回调保存文件开始,入参_w_l_token = {},_w_fileId = {},_w_userid = {}", w_l_token,fdId,userId);
        }
        w_l_token = handleLongToken(request, w_l_token);
        Boolean ok = getSysAttachmentWpsCenterOfficeProvider().validateCallBackToken(w_l_token);
        if (!ok) {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS回调保存文件失败,无效token,入参_w_l_token = {},_w_fileId = {},_w_userid = {}", w_l_token,fdId,userId);
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        String saveType = request.getHeader("x-weboffice-save-type");
//        if (StringUtils.isNotEmpty(saveType) && "auto".equals(saveType)) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("WPS回调保存文件忽略自动保存,入参_w_l_token = {},_w_fileId = {},_w_userid = {},x-weboffice-save-type = {}"
//                        , w_l_token,fdId,userId,saveType);
//            }
//            return jsonObject;
//        }
        JSONObject fileInfo = new JSONObject();
        if (!file.isEmpty() && StringUtil.isNotNull(fdId)) {
            try {
                SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fdId);
                if (sysAttMain != null) {
                    InputStream in = file.getInputStream();
                    sysAttMain.setInputStream(in);
                    sysAttMain.setFdSize(new Double(in.available()));
                    sysAttMainService.updateByUser(sysAttMain,userId);
                    fileInfo.put("id", fdId);
                    fileInfo.put("name", sysAttMain.getFdFileName());
                    fileInfo.put("version", 1);
                    fileInfo.put("size", new BigDecimal(sysAttMain.getFdSize()));
                    fileInfo.put("download_url", generateDownloadUrl(fdId, getSysAttachmentWpsCenterOfficeProvider().getOnceCallBackToken(userId,fdId,getSysAttachmentWpsCenterOfficeProvider().getWpsToken())));
                    jsonObject.put("file", fileInfo);
                }
            } catch (Exception e) {
                log.error("WPS回调保存文件异常", e);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("WPS回调保存文件完成,入参_w_l_token = {},_w_fileId = {},_w_userid = {},x-weboffice-save-type = {},新文件 = {}"
                    , w_l_token,fdId,userId,saveType,jsonObject.toString());
        }
        return jsonObject.toString();
    }

    @PostMapping("/expiring")
    public void expiring(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuilder jb = new StringBuilder();
        String line = null;
        String sd = null;
        Object deadTime = null;
        try(BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jb.toString());
            deadTime = jsonObject.get("dead_line");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sd = sdf.format(new Date(Long.parseLong(String.valueOf(deadTime.toString())) * 1000));      // 时间戳转换成时间
            Map<String, String> params = new HashMap<>();
            params.put("thirdWpsExpiredTime", sd);
            getSysAttachmentWpsCenterOfficeProvider().saveWpsCenterConfig(params);
        } catch (Exception e) {
            logger.error("记录wps中台证书过期时间异常", e);
        }
    }

    @PostMapping("/v1/3rd/onnotify")
    public Object onnotify(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cmd", required = false) String cmd, @RequestParam(value = "body", required = false) com.alibaba.fastjson.JSONObject requestBody) {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        result.put("result", 0);
        return result;
    }

    @PostMapping("/v1/3rd/file/view/notify")
    public Object viewNotify(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cmd", required = false) String cmd, @RequestParam(value = "body", required = false) com.alibaba.fastjson.JSONObject requestBody) {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        result.put("result", 0);
        return result;
    }

    @PostMapping("/v1/3rd/file/online")
    public Object online(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "ids",required = false) String[] ids) {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        result.put("result", 0);
        return result;
    }

    @PostMapping("/v1/3rd/file/history")
    public Object history(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String id, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer count) {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        result.put("result", 0);
        return result;
    }

    /**
     * 套红、清稿、转换回调的方法
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/integrate/dispatch", method = RequestMethod.POST)
    @ResponseBody
    public Object wpsCenterIntegrateDispatch (HttpServletRequest request) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("WPS套红、清稿、转换回调开始,回调接口：/api/sys-attachment/wpsCenterCallback/integrate/dispatch");
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
            logger.error("WPS套红、清稿、转换回调异常", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("WPS套红、清稿、转换回调关闭流出现问题",e);
                }
            }
        }


        if (StringUtil.isNotNull(callbackInfo.toString())) {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS中台回调的结果是：" + callbackInfo.toString());
            }
            try {
                JSONObject jsonObject = JSONObject.fromObject(callbackInfo.toString());
                String taskId = jsonObject.getString("task_id");
                Object resultData = jsonObject.get("result");

                JSONObject resultDataJson = JSONObject.fromObject(resultData);
                String success = resultDataJson.getString("success");
                String downloadId = resultDataJson.getString("download_id");
                if (logger.isDebugEnabled()) {
                    logger.debug("WPS套红、清稿、转换回调信息TaskId：{}, 结果：{}, 下载：{}", taskId, success, downloadId);
                }

                if ("true".equals(success)) {
                    ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil.getBean("wpsCenterProvider");
                    wpsCenterProvider.dealWpsOperateFile(taskId, downloadId);
                } else {
                    logger.error("WPS中台【转换、清稿、套红】回调的结果是:{}, 因此删除缓存存在任务ID:{}", success, taskId);
                    // 说明：套红、清稿不存在任务信息放入redis中，所以如果回调回来的信息是失败，也会调用此处代码。
                    // 正常不会有影响。 转换失败才会删除掉缓存。
                    // 不加判断的原因是，低版本WSP回调回来没有操作类型说明，所以无法判断是什么类型操作
                    ConvertRedisCache.getInstance().remove(taskId);

                    // 删除沉淀使用的缓存
                    ConvertQueueCache.getInstance().remove(taskId);
                    // 更新已经分配的转换为失败
                    getWpsCenterCallbackResult().doCallbackResult(taskId, "failure");

                    // 回调业务模块接口
                    WPSCenterCallBusinessUtil.callBack(false, taskId);
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("WPS套红、清稿、转换回调完成");
                }
            } catch (Exception e) {
                logger.error("WPS套红、清稿、转换回调异常",e);
            }

        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("WPS中台回调时，没有传回任何信息");
            }

        }

        return null;
    }

}
