package com.landray.kmss.third.feishu.notify;

import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyLogService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.Assert;

import java.util.Calendar;

/**
 * 飞书审批重试
 */
public class ThirdFeishuApprovalResendProvider implements IThirdFeishuApprovalResendProvider{

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuApprovalResendProvider.class);

    private IThirdFeishuService thirdFeishuService;

    private IThirdFeishuNotifyLogService thirdFeishuNotifyLogService;

    public IThirdFeishuService getThirdFeishuService() {
        if (thirdFeishuService == null) {
            thirdFeishuService = (IThirdFeishuService) SpringBeanUtil.getBean("thirdFeishuService");
        }
        return thirdFeishuService;
    }

    public IThirdFeishuNotifyLogService getThirdFeishuNotifyLogService() {
        if (thirdFeishuNotifyLogService == null) {
            thirdFeishuNotifyLogService = (IThirdFeishuNotifyLogService) SpringBeanUtil.getBean("thirdFeishuNotifyLogService");
        }
        return thirdFeishuNotifyLogService;
    }

    @Override
    public void resend(ThirdFeishuNotifyQueueErr queueError) throws Exception {
        JSONObject json = JSONObject.fromObject(queueError.getFdData());
        ThirdFeishuNotifyLog log = new ThirdFeishuNotifyLog();
        initLog(log, queueError.getFdNotifyId(), queueError.getFdSubject(), json);
        String result = getThirdFeishuService().syncApproval(json.toString());
        log.setFdResData(result);
        //结果，1：成功，2：失败
        log.setFdResult(1);
        saveLog(log);
    }

    /**
     * 初始化日志
     *
     * @param log
     * @param requestData
     * @throws Exception
     */
    private void initLog(ThirdFeishuNotifyLog log, String notifyId, String subject, JSONObject requestData) throws Exception {
        //事件id(流程id)
        log.setFdNotifyId(notifyId);
        //信息id(节点id)
        log.setFdMessageId(requestData.getString("node_id"));
        //日志主题
        log.setDocSubject(subject);
        //发送报文
        log.setFdReqData(requestData.toString());
        //信息类型
        log.setFdType(2);
        //创建时间
        log.setDocCreateTime(Calendar.getInstance().getTime());
        //url
        ThirdFeishuConfig config = new ThirdFeishuConfig();
        String url = config.getFeishuApprovalUrl();
        Assert.notNull(url, "请配置飞书审批中心api地址!");
        log.setFdUrl(url);
    }

    /**
     * 保存日志
     */
    private void saveLog(ThirdFeishuNotifyLog log) {
        //结束时间
        log.setFdRtnTime(Calendar.getInstance().getTime());
        //耗时时间
        log.setFdExpireTime(log.getDocCreateTime().getTime() - log.getFdRtnTime().getTime());
        TransactionStatus status = null;
        Throwable t = null;
        try {
            status = TransactionUtils.beginNewTransaction(10);
            getThirdFeishuNotifyLogService().add(log);
            TransactionUtils.commit(status);
        } catch (Exception e) {
            t = e;
            logger.error(e.getMessage(), e);
        } finally {
            if (t != null && status != null) {
                if (status.isRollbackOnly()) {
                    TransactionUtils.rollback(status);
                }
            }
        }
    }
}
