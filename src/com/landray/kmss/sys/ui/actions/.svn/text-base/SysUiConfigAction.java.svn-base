package com.landray.kmss.sys.ui.actions;

import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.appconfig.forms.SysAppConfigForm;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.ui.service.spring.CompressExecutorRunner;
import com.landray.kmss.sys.ui.service.spring.message.SysUiCompressMessage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  门户配置基础配置
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-10-18
 */
public class SysUiConfigAction extends SysAppConfigAction {
    private static final Logger logger = LoggerFactory.getLogger(SysUiConfigAction.class);
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(0, 10,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());
    /**
     * 重写update,为的是在点保存时进行业务操作
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forword = super.update(mapping, form, request, response);
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("保存门户引擎基础设置配置");
            }
            SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
            Map dataMap = appConfigForm.getMap();
            CompressExecutorRunner runner;
            boolean fdIsCompress = false;
            //模板js合并压缩加载开关
            if("true".equals(dataMap.get("fdIsCompress"))){
                fdIsCompress = true;
            }
            runner = new CompressExecutorRunner(fdIsCompress);
            EXECUTOR.invokeAll(Arrays.asList(runner));
            //发集群消息通知其他节点
            if (logger.isDebugEnabled()) {
                logger.debug("发集群消息通知其他节点");
            }
            MessageCenter.getInstance().sendToOther(new SysUiCompressMessage(fdIsCompress));
        } catch (Exception e) {
            logger.error("保存门户引擎基础设置配置前出错了，不影响配置的正常保存，请查看保存前业务逻辑是否有影响");
        }
        return forword;
    }
}
