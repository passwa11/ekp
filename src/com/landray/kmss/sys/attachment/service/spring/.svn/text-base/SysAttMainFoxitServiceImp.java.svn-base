package com.landray.kmss.sys.attachment.service.spring;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.ISysAttMainFoxitService;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.util.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 实现接口
 */
public class SysAttMainFoxitServiceImp implements ISysAttMainFoxitService {
    protected ISysAttMainCoreInnerService sysAttMainService;
    protected ISysAttMainCoreInnerService getSysAttMainCoreInnerService() {
        if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
        }
        return sysAttMainService;
    }
    /**
     * 响应元数据
     * @param request
     * @param sysAttMain 附件
     * @param searchSysAttMain 是否需要查询附件
     * @throws Exception
     */
    @Override
    public void createRequestMateData(HttpServletRequest request, SysAttMain sysAttMain,  Boolean searchSysAttMain) throws Exception{
        if (searchSysAttMain) {
            List<SysAttMain> sysAttMains = getSysAttMainCoreInnerService().findAttListByModel(sysAttMain.getFdModelName(),
                    sysAttMain.getFdModelId());
            if(!sysAttMains.isEmpty()) {
                sysAttMain = sysAttMains.get(0);
            }
        }

        long expires = System.currentTimeMillis() + (3 * 60 * 1000);// 下载链接3分钟有效
        // 不支持直连时（代理）走统一的系统附件下载url，因为对外时可能未登录，要添加签名验证
        String sign = getRestSign(sysAttMain.getFdId(), expires);
        SysAttMainFoxitBuilder sysAttMainFoxitBuilder = new SysAttMainFoxitBuilder();
        sysAttMainFoxitBuilder.creatFoxitAuthorization(request) //福昕license
                .createSysAuthorization(request, sysAttMain) // 附件授权情况
                .createSysFileInfo(request, sysAttMain, sign, expires) // 附件信息
                .createWaterInfo(request) // 水印信息
                .createOthersInfo(request); //其它信息
    }

    /**
     * 为下载链接签名
     *
     * @param expires
     * @param attMainId
     * @return
     * @throws Exception
     */
    private String getRestSign(String attMainId, long expires) throws Exception {
        String signStr = expires + ":" + attMainId;
        ISysRestserviceServerMainService sysRestMainService = (ISysRestserviceServerMainService) SpringBeanUtil
                .getBean("sysRestserviceServerMainService");
        SysRestserviceServerMain sysRestserviceServerMain = sysRestMainService
                .findByServiceBean("sysAttachmentRestService");
        List<SysRestserviceServerPolicy> webPolicys = sysRestserviceServerMain.getFdPolicy();
        if (ArrayUtil.isEmpty(webPolicys)) {
            return "";
        }
        SysRestserviceServerPolicy webPolicy = webPolicys.get(0);
        String sign = SignUtil.getHMAC(signStr + ":" + webPolicy.getFdLoginId(),
                StringUtil.isNotNull(webPolicy.getFdPassword()) ? webPolicy.getFdPassword() : webPolicy.getFdId());

        return sign;
    }
}
