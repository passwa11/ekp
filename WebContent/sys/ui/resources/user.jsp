<%@ page language="java" pageEncoding="UTF-8" contentType="text/plain; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="java.lang.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.profile.util.ProfileMenuUtil"%>
<%@ page import="com.landray.kmss.common.module.util.ModuleCenter"%>
<%@ page import="com.landray.kmss.common.module.core.proxy.IDynamicProxy"%>
<%@ page import="org.springframework.util.ReflectionUtils"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.lang.reflect.Method"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ page import="com.landray.kmss.common.model.BaseModel"%>
<%@ page import="com.landray.kmss.common.model.BaseCoreInnerModel"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.builder.NodeDefinition"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.node.support.AbstractManualNode"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.constant.LbpmConstants"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.common.service.IBaseService"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	boolean isExistLock = false;
	//判断是否有锁机制模块
    if(ProfileMenuUtil.moduleExist("/component/locker/")){
        isExistLock=true;
    }

	String returnMsg = UserUtil.getUser(request).getFdLoginName();
	Enumeration<String> names = request.getParameterNames();
	String version_form="";
	Long version_model = 0L;
    String lockFdId="";
    String modifyName = "";
    Long modifyMinute = 0L;
    boolean closeDataCoverageWarn = false;
	while (names.hasMoreElements()) {
		String name = names.nextElement();
		try {
		    //1、存在锁机制模块
            if(isExistLock){
                //获取流程信息
                if("sysWfBusinessForm.fdParameterJson".equals(name)){
                    //1、获取taskId
                    String fdParameterJson =request.getParameter(name);
                    if(StringUtil.isNotNull(fdParameterJson)){
                        JSONObject params = JSONObject.fromObject(fdParameterJson);
                        if(params.has("taskId") && params.has("processId") ){
                            String fdTaskId = params.getString("taskId");
                            String fdProcessId = params.getString("processId");
                            IBaseService baseService = (IBaseService) SpringBeanUtil.getBean("KmssBaseService");
                            if(StringUtil.isNotNull(fdTaskId) && StringUtil.isNotNull(fdProcessId)&&baseService.getBaseDao().isExist(LbpmWorkitem.class.getName(),fdTaskId)){
                                IDynamicProxy serviceProxy = ModuleCenter.getServiceProxy("accessManager");
                                LbpmWorkitem lbpmWorkitem = (LbpmWorkitem) serviceProxy.invoke("get",LbpmWorkitem.class,fdTaskId);
                                IDynamicProxy proxy = ModuleCenter.getServiceProxy("lbpmProcessExecuteService");
                                ProcessInstanceInfo load = (ProcessInstanceInfo) proxy.invoke("load",fdProcessId);
                                NodeDefinition definition = load.getProcessDefinitionInfo().getNodeInfo(lbpmWorkitem.getFdNode().getFdActivityId()).getDefinition();
                                if(definition instanceof AbstractManualNode){
                                    AbstractManualNode node = (AbstractManualNode)definition;
                                    //判断这个节点是不是会审
                                    if(LbpmConstants.PROCESS_TYPE_ALL == node.getProcessType()){
                                        //是否勾选"关闭同时审核数据覆盖提醒"
                                        closeDataCoverageWarn = node.isCloseDataCoverageWarn();
                                    }
                                }
                            }
                        }
                    }
                }
                if("componentLockerVersionForm.fdVersion".equals(name)){
                    version_form = request.getParameter(name);
                }else if("componentLockerVersionForm.fdId".equals(name)){
                     lockFdId = request.getParameter(name);
                     IDynamicProxy serviceProxy = ModuleCenter.getServiceProxy("componentLockerVersionService");
                     BaseModel originModel = (BaseModel) serviceProxy.invoke("findByPrimaryKey",lockFdId,null,true);
                     if(originModel!=null){
                     Method getFdVersionMethod = ReflectionUtils.findMethod(originModel.getClass(),"getFdVersion");
                     if(getFdVersionMethod!= null){
                         version_model = (Long) ReflectionUtils.invokeMethod(getFdVersionMethod,originModel);
                     }
                     //获取修改人及时间
                     if(version_model!=null && originModel!=null){
                         IDynamicProxy logProxy  = ModuleCenter.getServiceProxy("componentLockerVersionLogService");
                         String modelId = null;
                         String modelName = null;
                         if(originModel instanceof BaseCoreInnerModel){
                             modelId = ((BaseCoreInnerModel)originModel).getFdModelId();
                             modelName = ((BaseCoreInnerModel)originModel).getFdModelName();
                         }else{
                             Method getFdModelIdMethod = ReflectionUtils.findMethod(originModel.getClass(),"getFdModelId");
                             if(getFdModelIdMethod != null){
                                 modelId = (String) ReflectionUtils.invokeMethod(getFdModelIdMethod,originModel);
                             }
                             Method getFdModelNameMethod = ReflectionUtils.findMethod(originModel.getClass(),"getFdModelName");
                             if(getFdModelNameMethod != null){
                                 modelName = (String) ReflectionUtils.invokeMethod(getFdModelNameMethod,originModel);
                             }
                         }
                         Object logModel = logProxy.invoke("getLog",modelId, modelName,version_model);
                         if (logModel != null) {
                             Method getDocModifyPersonMethod = ReflectionUtils.findMethod(logModel.getClass(),"getDocModifyPerson");
                             if(getDocModifyPersonMethod != null){
                                 SysOrgPerson person =  (SysOrgPerson) ReflectionUtils.invokeMethod(getDocModifyPersonMethod,logModel);
                                 if(person != null){
                                    modifyName = person.getFdName();
                                 }
                             }
                             Method getDocModifyTimeMethod = ReflectionUtils.findMethod(logModel.getClass(),"getDocModifyTime");
                             if(getDocModifyTimeMethod != null){
                                Date modifyTime = (Date) ReflectionUtils.invokeMethod(getDocModifyTimeMethod,logModel);
                                 if(modifyTime != null){
                                    modifyMinute = (new Date().getTime() - modifyTime.getTime()) / 60000L;
                                 }
                             }
                        }
                     }
                     }
                 }
		    }
		} catch (Exception e) {
		    // 这里要将参数取出来，不然不会对参数作“非法值”校验处理
			returnMsg = "invalid_value" + e.getMessage();
			break;
		}
	}
	//流程节点没选择"会审"且没勾选关闭同时审核数据覆盖
	if(!closeDataCoverageWarn){
        //校验锁版本不一致 返回提示不刷新页面
        if(StringUtil.isNotNull(version_form)
                        && Long.parseLong(version_form) < version_model){
            String errorTips = ResourceUtil.getString(
                                "sys.ui.lock.error",
                                "sys-ui", null,
                                new Object[] {modifyName,modifyMinute});
            returnMsg = "locker_version_error##"+errorTips;
        }
    }
%>
<ui:ajaxtext>
<%=returnMsg %>
</ui:ajaxtext>