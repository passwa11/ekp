package com.landray.kmss.third.ding.util.clean;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskUpdateResponse;
import com.dingtalk.api.response.OapiWorkrecordUpdateResponse;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.notify.provider.ThirdDingTodoTaskProvider;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.util.CleaningToolUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * 清理(更新)钉钉待办辅助工具
 */
public class DingNotifyUpdateUtil {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingNotifyUpdateUtil.class);

    private static IBaseDao baseDao = null;

    private static IBaseDao getBaseDao() {
        if (baseDao == null) {
            baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
        }
        return baseDao;
    }

    /**
     * 更新钉钉待办状态
     * @param data
     * @return
     * @throws Exception
     */
    public static String updateDingNotifyByTool(JSONArray data) throws Exception {
        logger.debug("----待办清理工具清理----");
        if (data == null || data.size() == 0) {
            return null;
        }
        StringBuilder rs = new StringBuilder();
        int successCount = 0;
        List workIds = new ArrayList();
        List taskIds = new ArrayList();
        List ekpNotifyIds = new ArrayList();
        for (int i = 0; i < data.size(); i++) {
            JSONObject curTodo = data.getJSONObject(i);
            logger.debug("准备清理待办：" + curTodo);
            String type = curTodo.getString("type");
            String dingUserid_ekpid = curTodo.getString("ids");
            // 待办任务id
            String taskId = curTodo.getString("dingNotifyId");
            if (CleaningToolUtil.API_WORK.equals(type)) {
                logger.debug("【工作流接口】-待办更新:{}",curTodo.getString("title"));
                OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
                OapiProcessWorkrecordTaskUpdateResponse res = DingNotifyUtil
                        .updateTask(req, DingUtils.dingApiService.getAccessToken(),curTodo.getString("dingInstanceId"),
                                Long.parseLong(taskId),
                                Long.parseLong(DingConfig.newInstance()
                                        .getDingAgentid()),
                                dingUserid_ekpid.split(";")[1]);
                if (res.getErrcode() == 0) {
                    successCount++;
                    logger.info("更新待办成功，详情：" + res.getBody());
                    workIds.add(taskId);
                    ekpNotifyIds.add(curTodo.getString("notifyFdId"));
                } else {
                    rs.append(curTodo.getString("title")+" 更新失败："+res.getErrmsg()+"<br/>");
                    logger.warn("更新待办失败，详情：" + res.getBody());
                }
            } else if (CleaningToolUtil.API_TODO_1.equals(type)) {
                logger.debug("【待办1.0接口】-待办更新:{}",curTodo.getString("title"));
                String userid = curTodo.getString("userid");
                OapiWorkrecordUpdateResponse response = cleanDingNotify_v1(userid, taskId, dingUserid_ekpid.split(";")[1]);
                if (response.getErrcode()==0) {
                    successCount++;
                    logger.warn("------1.0待办成功-----");
                    taskIds.add(taskId);
                    ekpNotifyIds.add(curTodo.getString("notifyFdId"));
                } else {
                    rs.append(curTodo.getString("title")+" 更新失败："+response.getErrmsg()+"<br/>");
                    logger.warn("待办1.0更新失败：{}",response.getBody());
                }
            }else if (CleaningToolUtil.API_TODO_2.equals(type)) {
                logger.debug("【待办1.0接口】-待办更新:{}",curTodo.getString("title"));
                String unionId = curTodo.getString("unionId");
                JSONObject result = cleanDingNotify_v2(unionId, taskId, dingUserid_ekpid.split(";")[1]);
                logger.warn("待办2.0更新结果：{}",result);
                if(result!=null&&result.containsKey("result")&&result.getBoolean("result")){
                    successCount++;
                    logger.warn("2.0待办更新成功");
                    ekpNotifyIds.add(curTodo.getString("notifyFdId"));
                    taskIds.add(ThirdDingTodoTaskProvider.PREFIX_TODO_V2+taskId);
                }else{
                    rs.append(curTodo.getString("title")+" 更新失败："+result+"<br/>");
                    logger.warn("2.0待办更新失败："+result);
                }
            }
        }
        new Thread(new CleanTodoDataRunner(workIds,taskIds,ekpNotifyIds)).start();
        rs.append("成功更新数据:"+successCount+" 条"+" <br/>");
        logger.info("更新成功 {} 条数据",successCount);
        return rs.toString();
    }

    /**
     * 待办2.0接口更新待办
     * @return
     */
    private static JSONObject cleanDingNotify_v2(String unionId, String recordid,String ekpUserId) throws Exception {
        if (StringUtil.isNull(unionId) || StringUtil.isNull(recordid)) {
            logger.debug("更新钉钉待办的用户Id和待办Id不完整，故无法更新");
            throw new RuntimeException("更新钉钉待办的用户Id和待办Id不完整，故无法更新 taskId:"+recordid);
        }
        JSONObject req  = new JSONObject();
        JSONArray executorStatusList = new JSONArray();
        JSONObject user  = new JSONObject();
        user.put("id",unionId);
        user.put("isDone",true);
        executorStatusList.add(user);
        req.put("executorStatusList",executorStatusList);
        return DingUtils.getDingApiService().updateExecutorStatus(unionId,recordid,req);
    }

    /**
     * 更新待办1.0的数据
     * @param userid
     * @param recordid
     * @param ekpUserId
     * @return
     * @throws Exception
     */
    private static OapiWorkrecordUpdateResponse cleanDingNotify_v1(String userid, String recordid,
                                              String ekpUserId) throws Exception {
        if (StringUtil.isNull(userid) || StringUtil.isNull(recordid)) {
            logger.debug("更新钉钉待办的用户Id和待办Id不完整，故无法更新");
            throw new RuntimeException("更新钉钉待办的用户Id和待办Id不完整，故无法更新 recordid:"+recordid);
        }
        DingApiService dingService = DingUtils.getDingApiService();
        String url = DingConstant.DING_PREFIX + "/topapi/workrecord/update"
                + DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
        logger.debug("钉钉接口：" + url);
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
        req.setUserid(userid);
        req.setRecordId(recordid);
        return client.execute(req, dingService.getAccessToken());
    }

    /**
     * 更新钉钉待办后，需要清理ekp系统内相关的记录：映射关系、状态等
     */
    static class CleanTodoDataRunner implements Runnable {

        private List<String> work_ids=null;
        private List<String> task_ids=null;
        private List<String> ekpNotifyIds=null; //ekp待办ID

        public CleanTodoDataRunner(List<String> work_ids, List<String> task_ids,List<String> ekpNotifyIds) {
            this.work_ids = work_ids;
            this.task_ids = task_ids;
            this.ekpNotifyIds = ekpNotifyIds;
        }
        @Override
        public void run() {
            logger.info("清理系统和钉钉待办相关的数据");
            TransactionStatus status = null;
            try {
                status = TransactionUtils.beginNewTransaction();
                if(task_ids!=null && task_ids.size()>0){
                    //删除映射表的数据
                    String sql = "DELETE  FROM third_ding_notify_wr WHERE "+HQLUtil.buildLogicIN("fd_record_id",task_ids);
                    logger.info("清理待办任务(v1.0、v2.0)接口的待办 sql：{}",sql);
                    int count = getBaseDao().getHibernateSession().createNativeQuery(sql).executeUpdate();
                    logger.warn("删除待办映射关系：{} 条",count);
                }
                if(work_ids!=null && work_ids.size()>0){
                    //工作流接口
                    String ids = HQLUtil.buildLogicIN("fd_task_id",work_ids);
                    String sql = "UPDATE third_ding_dtask SET fd_status='22' WHERE "+ids;
                    logger.info("清理工作流接口的待办 sql：{}",sql);
                    Session session =getBaseDao().getHibernateSession();
                    int count = session.createNativeQuery(sql).executeUpdate();
                    logger.warn("更新工作流待办关系：{} 条",count);
                    sql = "UPDATE third_ding_dtask_xform SET fd_status='22' WHERE "+ids;
                    logger.info("更新工作流待办关系 sql：{}",sql);
                    count = session.createNativeQuery(sql).executeUpdate();
                    logger.warn("更新高级审批待办关系：{} 条" , count);
                }
                //清理重发错误队列的更新数据
                if(ekpNotifyIds!=null&& ekpNotifyIds.size()>0){
                    String sql = "DELETE FROM third_ding_notify_queue_err WHERE fd_method in ('update','V2.0_update_executorStatus','V2.0_update') and "+HQLUtil.buildLogicIN("fd_todo_id",ekpNotifyIds);
                    logger.info("清理重发错误队列的更新数据 sql：{}",sql);
                    Session session =getBaseDao().getHibernateSession();
                    int count = session.createNativeQuery(sql).executeUpdate();
                    logger.warn("清理重发错误队列的更新数据：{} 条" , count);
                }
                TransactionUtils.commit(status);
            } catch (Exception e) {
                logger.warn(e.getMessage(),e);
                if (status != null) {
                    try {
                        TransactionUtils.getTransactionManager()
                                .rollback(status);
                    } catch (Exception ex) {
                        logger.error("---事务回滚出错---", ex);
                    }
                }
            }
        }
    }

}
