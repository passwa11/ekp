package com.landray.kmss.third.feishu.listener;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmTemplateService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.third.feishu.constant.ThirdFeishuConstant;
import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyLogService;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyQueueErrService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 飞书集成事件监听器：
 * 同步Ekp审批流程到飞书连接器
 */
public class FeishuApprovalEventListener implements ApplicationListener<Event_Common> {

    private ThreadLocal<LbpmProcess> LbpmProcessThreadLocal = new ThreadLocal<LbpmProcess>();

    private ThreadLocal<ThirdFeishuNotifyLog> notifyLogThreadLocal = new ThreadLocal<ThirdFeishuNotifyLog>();

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FeishuApprovalEventListener.class);

    private IThirdFeishuService thirdFeishuService;

    private ILbpmProcessService lbpmProcessService;

    private ILbpmTemplateService lbpmTemplateService;

    private ISysOrgPersonService sysOrgPersonService;

    private ISysOrgElementService sysOrgElementService;

    private IThirdFeishuNotifyLogService thirdFeishuNotifyLogService;

    private IThirdFeishuNotifyQueueErrService thirdFeishuNotifyQueueErrService;

    public IThirdFeishuNotifyQueueErrService getThirdFeishuNotifyQueueErrService() {
        if (thirdFeishuNotifyQueueErrService == null) {
            thirdFeishuNotifyQueueErrService = (IThirdFeishuNotifyQueueErrService) SpringBeanUtil.getBean("thirdFeishuNotifyQueueErrService");
        }
        return thirdFeishuNotifyQueueErrService;
    }

    public IThirdFeishuNotifyLogService getThirdFeishuNotifyLogService() {
        if (thirdFeishuNotifyLogService == null) {
            thirdFeishuNotifyLogService = (IThirdFeishuNotifyLogService) SpringBeanUtil.getBean("thirdFeishuNotifyLogService");
        }
        return thirdFeishuNotifyLogService;
    }

    public ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    private ILbpmTemplateService getLbpmTemplateServiceImp() {
        if (lbpmTemplateService == null)
            // 通用模板的service
        {
            lbpmTemplateService = (ILbpmTemplateService) SpringBeanUtil
                    .getBean("lbpmTemplateService");
        }
        return lbpmTemplateService;
    }

    protected ILbpmProcessService getLbpmProcessService() {
        if (lbpmProcessService == null) {
            lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
        }
        return lbpmProcessService;
    }

    public IThirdFeishuService getThirdFeishuService() {
        if (thirdFeishuService == null) {
            thirdFeishuService = (IThirdFeishuService) SpringBeanUtil.getBean("thirdFeishuService");
        }
        return thirdFeishuService;
    }

    public ISysOrgPersonService getSysOrgPersonService() {
        if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
        }
        return sysOrgPersonService;
    }

    @Override
    public void onApplicationEvent(Event_Common event) {
        if (logger.isDebugEnabled()) {
            logger.debug("飞书审批同步开始");
        }
        Assert.notNull(event.getSource(), "事件来源参数不能为空!");
        //判断事件来源是否为审批实例
        if ("flyBookCard".equals(event.getSource().toString())) {
            if (isFeishuEnabled()) {
                String requestBody = null;
                Throwable t = null;
                try {
                    //校验请求参数
                    assertEventParams(event);
                    //构造请求报文
                    requestBody = constructRequestBody(event);
                } catch (Exception e) {
                    t = e;
                    logger.error(e.getMessage(), e);
                } finally {
                    if (t == null) {
                        //开始同步飞书审批
                        sychFeishuApproval(event, requestBody);
                    } else {
                        //释放线程本地变量
                        release();
                    }
                }
            }
        }
    }

    /**
     * 同步飞书审批
     *
     * @param event
     * @param requestBody
     */
    private void sychFeishuApproval(Event_Common event, String requestBody) {
        try {
            //初始化日志
            initLog(event, requestBody);
            //发送飞书审批请求
            String result = getThirdFeishuService().syncApproval(requestBody);
            //响应结果
            getFeishuNotifyLog().setFdResData(result);
            //结果，1：成功，2：失败
            getFeishuNotifyLog().setFdResult(1);
        } catch (Exception e) {
            //结果，1：成功，2：失败
            getFeishuNotifyLog().setFdResult(2);
            //异常信息
            getFeishuNotifyLog().setFdErrMsg(e.getMessage());
            logger.error(e.getMessage(), e);
            //添加到失败重试队列
            addErrorQueue(event, requestBody, e.getMessage());
        } finally {
            saveLog();
            release();
        }
    }

    /**
     * 加入重发队列
     *
     * @param event
     * @param reqData
     * @param errorMsg
     */
    private void addErrorQueue(Event_Common event, String reqData, String errorMsg) {
        TransactionStatus status = null;
        Throwable t = null;
        try {
            ThirdFeishuNotifyQueueErr error = new ThirdFeishuNotifyQueueErr();
            error.setFdSubject(getProcessInstance(event).getDocSubject());
            error.setFdMd5(null);
            error.setFdNotifyId(getProcessInstance(event).getFdId());
            error.setFdErrMsg(errorMsg);
            error.setFdData(reqData);
            error.setFdPerson(getProcessInstance(event).getFdCreator());
            error.setDocCreateTime(new Date());
            error.setFdRepeatHandle(0);
            error.setFdFlag(ThirdFeishuConstant.NOTIFY_ERROR_FDFLAG_ERROR);
            error.setFdMethod("syncApproval");
            status = TransactionUtils.beginNewTransaction(10);
            getThirdFeishuNotifyQueueErrService().add(error);
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            t = e;
            logger.error("加入重发队列失败......");
            logger.error(e.getMessage(), e);
        } finally {
            if (t != null && status != null) {
                if (status.isRollbackOnly()) {
                    TransactionUtils.getTransactionManager().rollback(status);
                }
            }
            else{
                if(logger.isDebugEnabled()){
                    logger.debug("已加入重发队列");
                }
            }
        }
    }

    /**
     * 初始化日志
     *
     * @param event
     * @param requestBody
     * @throws Exception
     */
    private void initLog(Event_Common event, String requestBody) throws Exception {
        //事件id(流程id)
        getFeishuNotifyLog().setFdNotifyId(getProcessInstance(event).getFdId());
        //信息id(节点id)
        getFeishuNotifyLog().setFdMessageId((String) event.getParams().get("nodeId"));
        //日志主题
        getFeishuNotifyLog().setDocSubject(getProcessInstance(event).getDocSubject());
        //发送报文
        getFeishuNotifyLog().setFdReqData(requestBody);
        //信息类型
        getFeishuNotifyLog().setFdType(2);
        //创建时间
        getFeishuNotifyLog().setDocCreateTime(Calendar.getInstance().getTime());
        //url
        ThirdFeishuConfig config = new ThirdFeishuConfig();
        String url = config.getFeishuApprovalUrl();

        Assert.notNull(url, "请配置飞书审批中心api地址!");
        getFeishuNotifyLog().setFdUrl(url);
    }

    /**
     * 保存日志
     */
    private void saveLog() {
        //结束时间
        getFeishuNotifyLog().setFdRtnTime(Calendar.getInstance().getTime());
        //耗时时间
        getFeishuNotifyLog().setFdExpireTime(getFeishuNotifyLog().getDocCreateTime().getTime()
                - getFeishuNotifyLog().getFdRtnTime().getTime());
        TransactionStatus status = null;
        Throwable t = null;
        try {
            status = TransactionUtils.beginNewTransaction(10);
            getThirdFeishuNotifyLogService().add(getFeishuNotifyLog());
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

    private ThirdFeishuNotifyLog getFeishuNotifyLog() {
        ThirdFeishuNotifyLog log = notifyLogThreadLocal.get();
        if (log == null) {
            notifyLogThreadLocal.set(new ThirdFeishuNotifyLog());
            log = notifyLogThreadLocal.get();
        }
        return log;
    }

    /**
     * 释放线程本地变量
     */
    private void release() {
        notifyLogThreadLocal.remove();
        notifyLogThreadLocal.set(null);

        LbpmProcessThreadLocal.remove();
        LbpmProcessThreadLocal.set(null);
    }

    /**
     * 构造请求报文
     *  {
     * 	"Message_type": "",
     * 	"pro_def_name": "微信代办通知",
     * 	"pro_def_id": "17f6863bfbd85847e9dd4ba4130beade",
     * 	"group_name": "流程管理模板/微信代办通知",
     * 	"group_id": "17f686350bd3fcbbb44d47242c4b1abc",
     * 	"pro_instance_id": "180219c1dccf65de115a128479f90926",
     * 	"pro_instance_status": "20",
     * 	"starter_id": "1183b0b84ee4f581bba001c47a78b2d9",
     * 	"start_time": 1649831321287,
     * 	"rec_update_time": 1649831379637,
     * 	"node_id": "180219ce253bf951435f75f48c489053",
     * 	"node_name": "飞书审批",
     * 	"node_type": "reviewNode", //reviewNode：审批节点，sendNode: 抄送节点
     * 	"task_id": "180219ce79ca97cfb6b7c2a48b48ec6d", //如果节点类型为reviewNode，则会有任务id
     * 	"task_status": "20", //如果节点类型为reviewNode，则会有任务状态
     * 	"task_create_time": 1649831372699, //如果节点类型为reviewNode，则会有任务创建时间
     * 	"approvors": [{ //处理人/抄送人
     * 		"approvor_mobile": "13723407801",
     * 		"approvor_email": "",
     * 		"approvor_id": "17deb0a177bafe7ad0a09014ba9bfe4f",
     *      "operation": [{
     *          "key": "handler_pass",
     *          "text": "通过"
     *      }] //允许的操作
     *    }]
     * }
     * @param event
     * @return
     * @throws Exception
     */
    private String constructRequestBody(Event_Common event) throws Exception {
        LbpmProcess instance = getProcessInstance(event);
        String templateModelName = (String) event.getParams().get("templateModelName");
        String groupId = null, groupName = null, templateModelId = instance.getFdTemplateModelId(), templateName = null;

        //业务模版类数据字典
        SysDictModel templateModelDictModel = SysDataDict.getInstance().getModel(templateModelName);
        //业务模版service
        IBaseService templateModelService = (IBaseService) SpringBeanUtil.getBean(templateModelDictModel.getServiceBean());
        //业务模版model
        IBaseModel templateModel = templateModelService.findByPrimaryKey(templateModelId);

        templateName = (String) PropertyUtils.getProperty(templateModel, "fdName");
        Assert.notNull(templateName, "业务模版名称无法匹配");

        //获取模版分类全路径
        if (ThirdFeishuUtil.isGlobalCategory(templateModel.getClass().getName())) {
            String categoryFieldName = ThirdFeishuUtil.getFieldNameByType(templateModelDictModel, "com.landray.kmss.sys.category.model.SysCategoryMain");
            SysCategoryMain sysCategoryMain = (SysCategoryMain) PropertyUtils.getProperty(templateModel, categoryFieldName);
            groupId = sysCategoryMain.getFdId();
            groupName = ThirdFeishuUtil.getHierarchyCategoryNames(sysCategoryMain);
        } else if (ThirdFeishuUtil.isSimpleCategory(templateModelName)) {
            Object simpleCategory = PropertyUtils.getProperty(templateModel, "fdTemplate");
            if (simpleCategory == null) {
                simpleCategory = PropertyUtils.getProperty(templateModel, "docTemplate");
            }
            if (simpleCategory != null && simpleCategory instanceof ISysSimpleCategoryModel) {
                groupId = ((ISysSimpleCategoryModel) simpleCategory).getFdId();
                groupName = ThirdFeishuUtil.getHierarchyCategoryNames((ISysSimpleCategoryModel) simpleCategory);
            }
        } else if (ThirdFeishuUtil.isOtherCategory(templateModelName)) {
            groupId = templateModel.getFdId();
            groupName = (String) PropertyUtils.getProperty(templateModel, "fdName");
        }

        if (StringUtils.isNotBlank(groupName)) {
            groupName = ResourceUtil.getString(templateModelDictModel.getMessageKey()) + "/" + groupName;
        }

        //请求报文
        JSONObject requestBody = new JSONObject();
        //信息类型
        requestBody.put("Message_type", event.getParams().get("messageType").toString());

        //审批定义名称（模版名称）
        requestBody.put("pro_def_name", instance.getDocSubject());
        //审批定义id (模版id)
        requestBody.put("pro_def_id", templateModelId);

        //分组名称（分类名称）
        requestBody.put("group_name", groupName);
        //分组id (分类id)
        requestBody.put("group_id", groupId);

        //审批实例id（流程id）
        requestBody.put("pro_instance_id", instance.getFdId());
        //审批实例整体状态
        requestBody.put("pro_instance_status", instance.getFdStatus());
        //审批实例发起人
        requestBody.put("starter_id", instance.getFdCreator().getFdId());

        SysOrgPerson docCreator = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(instance.getFdCreator().getFdId());
        Assert.notNull(docCreator, "无法查询到当前流程审批发起人");
        //审批实例发起人的email
        requestBody.put("starter_email", docCreator.getFdEmail() == null ? "" : docCreator.getFdEmail());
        //审批实例发起人的手机号
        requestBody.put("starter_mobile", docCreator.getFdMobileNo() == null ? "" : docCreator.getFdMobileNo());

        //审批发起时间
        requestBody.put("start_time", instance.getFdCreateTime().getTime());
        if (instance.getFdEndedTime() != null) {
            requestBody.put("end_time", instance.getFdEndedTime().getTime());
        }
        //审批实例最近更新时间
        requestBody.put("rec_update_time", instance.getFdLastHandleTime().getTime());

        String currentNodeId = (String) event.getParams().get("nodeId");
        String currentNodeName = (String) event.getParams().get("nodeName");
        String currentNodeType = (String) event.getParams().get("nodeType");
        //当前节点id
        requestBody.put("node_id", currentNodeId);
        //任务所属节点名称
        requestBody.put("node_name", currentNodeName);
        //节点类型("sendNode":抄送节点，"reviewNode": 审批节点)
        requestBody.put("node_type", currentNodeType);

        if ("reviewNode".equals(currentNodeType)) { //审批节点
            //任务id
            requestBody.put("task_id", event.getParams().get("taskId"));
            //任务状态
            requestBody.put("task_status", event.getParams().get("taskStatus"));
            //任务创建时间
            requestBody.put("task_create_time", event.getParams().get("taskCreateTime"));
        }
        //操作类型
        requestBody.put("operationType", event.getParams().get("operationType"));

        JSONObject handlersOperations = getApprovoersOperation(event);
        requestBody.put("approvors", new JSONArray());
        List<String> nodeHandlers = (List<String>) event.getParams().get("nodeHandlers");
        requestBody.getJSONArray("approvors").addAll(getApprovors(nodeHandlers).stream().map(p -> {
            JSONObject approvorInfo = new JSONObject();
            //任务审批人id
            approvorInfo.put("approvor_id", p[0]);
            //任务审批人邮箱
            approvorInfo.put("approvor_email", p[1]);
            //任务审批人手机号
            approvorInfo.put("approvor_mobile", p[2]);
            //审批人操作
            approvorInfo.put("operations", getHandlerOperations(handlersOperations, p[0].toString()));
            return approvorInfo;
        }).collect(Collectors.toList()));

        String requestContent = requestBody.toString();

        if (logger.isDebugEnabled()) {
            logger.debug("组装请求报文：" + requestContent);
        }
        return requestContent;
    }

    /**
     * 组装处理人操作权限
     *
     * @param handlersOperation
     * @param handlerId
     * @return
     */
    private JSONArray getHandlerOperations(JSONObject handlersOperation, String handlerId) {
        JSONArray operations = new JSONArray();
        if (handlersOperation.containsKey(handlerId)) {
            JSONObject operation = handlersOperation.getJSONObject(handlerId);
            //"通过"操作
            if (operation.containsKey("handler_pass")
                    && "Y".equalsIgnoreCase(operation.getString("handler_pass"))) {
                JSONObject passOperation = new JSONObject();
                passOperation.put("key", "handler_pass");
                passOperation.put("text", operation.getString("handler_pass_text"));
                operations.add(passOperation);
            }

            //"签字"操作
            if (operation.containsKey("handler_sign")
                    && "Y".equalsIgnoreCase(operation.getString("handler_sign"))) {
                JSONObject signOperation = new JSONObject();
                signOperation.put("key", "handler_sign");
                signOperation.put("text", operation.getString("handler_sign_text"));
                operations.add(signOperation);
            }

            //"驳回"操作
            if (operation.containsKey("handler_refuse")
                    && "Y".equalsIgnoreCase(operation.getString("handler_refuse"))) {
                JSONObject refuseOperation = new JSONObject();
                refuseOperation.put("key", "handler_refuse");
                refuseOperation.put("text", operation.getString("handler_refuse_text"));
                operations.add(refuseOperation);
            }

            //"超级驳回"操作
            if (operation.containsKey("handler_superRefuse")
                    && "Y".equalsIgnoreCase(operation.getString("handler_superRefuse"))) {
                JSONObject superRefuseOperation = new JSONObject();
                superRefuseOperation.put("key", "handler_superRefuse");
                superRefuseOperation.put("text", operation.getString("handler_superRefuse_text"));
                operations.add(superRefuseOperation);
            }

            //"加签通过"操作
            if (operation.containsKey("handler_assignPass")
                    && "Y".equalsIgnoreCase(operation.getString("handler_assignPass"))) {
                JSONObject assignPassOperation = new JSONObject();
                assignPassOperation.put("key", "handler_assignPass");
                assignPassOperation.put("text", operation.getString("handler_assignPass_text"));
                operations.add(assignPassOperation);
            }

            //"加签通过"操作
            if (operation.containsKey("handler_assignRefuse")
                    && "Y".equalsIgnoreCase(operation.getString("handler_assignRefuse"))) {
                JSONObject assignRefusePassOperation = new JSONObject();
                assignRefusePassOperation.put("key", "handler_assignRefuse");
                assignRefusePassOperation.put("text", operation.getString("handler_assignRefuse_text"));
                operations.add(assignRefusePassOperation);
            }
        }
        return operations;
    }

    /**
     * 获取处理人操作数据
     *
     * @param event
     * @return
     */
    private JSONObject getApprovoersOperation(Event_Common event) {
        if (event.getParams().containsKey("privateData")) {
            JSONObject privateData = (JSONObject) event.getParams().get("privateData");
            return privateData.getJSONObject("handler"); //handler为当前处理人操作权限集合，other为已处理人信息
        }
        return new JSONObject();
    }

    /**
     * 获取层级审批人
     * @param nodeHandlers
     * @return
     * @throws Exception
     */
    private List<Object[]> getApprovors(List<String> nodeHandlers) throws Exception {
        Set<String> approvorIds = new HashSet<String>();
        List<SysOrgElement> sysOrgElements = getSysOrgElementService().findByPrimaryKeys(nodeHandlers.toArray(new String[nodeHandlers.size()]));
        if (sysOrgElements != null) {
            sysOrgElements.stream().forEach(ele -> {
                try {
                    getSysOrgElementIds(approvorIds, ele);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
        List<Object[]> sysOrgPersons = null;
        if (approvorIds.size() > 0) {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setSelectBlock("sysOrgPerson.fdId, sysOrgPerson.fdEmail, sysOrgPerson.fdMobileNo");
            hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("sysOrgPerson.fdId", Arrays.asList(approvorIds.toArray())));
            sysOrgPersons = getSysOrgPersonService().findList(hqlInfo);
        }
        if (sysOrgPersons == null) {
            sysOrgPersons = new ArrayList<>();
        }
        return sysOrgPersons;
    }

    private void getSysOrgElementIds(Set<String> approvorIds, SysOrgElement approvor) throws Exception {
        if (approvor.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON) {
            approvorIds.add(approvor.getFdId());
        } else if (approvor.getFdOrgType() == SysOrgConstant.ORG_TYPE_POST) {
            approvor.getFdPersons().stream().forEach(p -> {
                approvorIds.add(((SysOrgElement) p).getFdId());
            });
        } else if (approvor.getFdOrgType() == SysOrgConstant.ORG_TYPE_DEPT
                || approvor.getFdOrgType() == SysOrgConstant.ORG_TYPE_ORG) {
            String whereBlock = "sysOrgElement.fdHierarchyId like '%" + approvor.getFdHierarchyId()
                    + "%' and sysOrgElement.fdOrgType = 8";
            List<Object[]> sysOrgElementIds = getSysOrgElementService().findValue("sysOrgElement.fdId", whereBlock, null);
            sysOrgElementIds.stream().forEach(id -> {
                approvorIds.add(id.toString());
            });
        }
    }

    private LbpmProcess getProcessInstance(Event_Common event) throws Exception {
        LbpmProcess process = LbpmProcessThreadLocal.get();
        if (process == null) {
            String processInstanceId = (String) event.getParams().get("processInstanceId");
            process = (LbpmProcess) getLbpmProcessService().findByPrimaryKey(processInstanceId);
            Assert.notNull(process, "无法查询到流程实例[" + processInstanceId + "]");
            Assert.isTrue(process instanceof LbpmProcess, "查询结果为非LbpmProcess类型[" + processInstanceId + "]");
            LbpmProcessThreadLocal.set(process);
        }
        return process;
    }

    private boolean isFeishuEnabled() {
        try {
            ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
            if ("true".equals(config.getFeishuEnabled()) && "true".equals(config.getFeishuApprovalEnabled())) {
                return true;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("飞书未开启集成，不同步审批流程实例到飞书");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    private void assertEventParams(Event_Common event) {
        Map paramMap = event.getParams();
        Assert.notNull(paramMap, "事件参数不能为空!");
        Assert.isTrue(paramMap.containsKey("messageType"), "信息类型参数不能为空!");
        Assert.isTrue(paramMap.containsKey("processInstanceId"), "流程审批实例id参数不能为空!");
        Assert.isTrue(paramMap.containsKey("nodeId"), "当前节点id不能空!");
        Assert.isTrue(paramMap.containsKey("nodeName"), "当前节点名称不能空!");
        Assert.isTrue(paramMap.containsKey("nodeType"), "当前节点类型不能空!");
        Assert.isTrue(paramMap.containsKey("nodeHandlers"), "节点处理人不能空!");
        Assert.isTrue(paramMap.containsKey("templateModelName"), "业务模版类名参数不能为空!");

        Assert.isTrue(StringUtils.isNotBlank((String) paramMap.get("messageType")), "信息类型参数不能为空!");
        Assert.isTrue(StringUtils.isNotBlank((String) paramMap.get("processInstanceId")), "流程审批实例id参数不能为空!");
        Assert.isTrue(StringUtils.isNotBlank((String) paramMap.get("nodeId")), "当前节点id不能空!");
        Assert.isTrue(StringUtils.isNotBlank((String) paramMap.get("nodeName")), "当前节点名称不能空!");
        Assert.isTrue(StringUtils.isNotBlank((String) paramMap.get("nodeType")), "当前节点类型不能空!");
        Assert.isTrue(((List<String>) paramMap.get("nodeHandlers")).size() > 0, "节点处理人不能空!");
        Assert.isTrue(StringUtils.isNotBlank((String) paramMap.get("templateModelName")), "业务模版类名参数不能为空!");

        if ("reviewNode".equals(paramMap.get("nodeType"))) {
            //审批节点
            Assert.isTrue(paramMap.containsKey("taskId"), "节点任务id不能空!");
            Assert.isTrue(paramMap.containsKey("taskStatus"), "节点任务状态不能空!");
            Assert.isTrue(paramMap.containsKey("taskCreateTime"), "节点任务创建时间不能空!");

            Assert.isTrue(StringUtils.isNotBlank((String) paramMap.get("taskId")), "节点任务id不能空!");
            Assert.isTrue(StringUtils.isNotBlank((String) paramMap.get("taskStatus")), "节点任务状态不能空!");
            Assert.isTrue(paramMap.get("taskCreateTime") != null, "节点任务创建时间不能空!");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("飞书审批实例同步传入参数" + paramMap.keySet().toString() + " -> " + paramMap.values().toArray());
        }
    }
}
