<!-- 消息部件顶部提醒内容（  “您有 N 条 需处理事项” 、邮件数量、 刷新图标 、快速审批图标   ）  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="__fdAppName" value="${HtmlParam.fdAppName}"></c:set>
<%
		//模块首页筛选条件
		String __dataType = (String)request.getAttribute("dataType");
		boolean isDone = StringUtil.isNotNull(__dataType)&& __dataType.indexOf("done")!=-1;
		String _fdAppName = (String)pageContext.getAttribute("__fdAppName");
		String fdType = request.getParameter("fdType");
		String crqPrefix="";
		if(isDone){
			crqPrefix = "&cri.list_systoview_done.q=";
		}else{
			crqPrefix = "&cri.list_systoview.q=";
		}
		StringBuffer _crqCon = new StringBuffer();
		List<String> _crqConList = new ArrayList<String>();
		if(StringUtil.isNotNull(_fdAppName)){
			request.setAttribute("_fdAppName", "&fdAppName="+_fdAppName);
			_crqConList.add("fdAppName:"+_fdAppName);
		}
		if("4".equals(fdType)) _crqConList.add("fdType:4");
		
		for(String con : _crqConList){
			if(StringUtil.isNotNull(_crqCon.toString())){
				_crqCon.append(";"+con);
			}else{
				_crqCon.append(con);
			}
		}
		crqPrefix = StringUtil.isNotNull(_crqCon.toString()) ? crqPrefix+_crqCon.toString():"";
		request.setAttribute("crqPrefix", crqPrefix);
%>

<% /**  定义  是否显示邮件标识  变量（默认为true）   **/ %>
<c:set var="isShowEmail" value="true"></c:set>

<% /** --------Start--------- 获取应该展示的事项类型（需处理待办、待阅读事项、待办事项、暂挂事项、已阅读事项、已处理事项）--------Start--------- **/ %>
		<c:if test="${param.fdType == 1 || param.fdType == 13 || empty param.fdType && empty param.finish}">
			<!-- 需处理待办 -->
			<c:set var="infoTip" value="sysNotifyTodo.toDo.info"></c:set>
			<c:set var="fastTodo" value="true"></c:set>
		</c:if>
		<c:if test="${param.fdType == 2}">
			<!-- 待阅读事项 -->
			<c:set var="infoTip" value="sysNotifyTodo.toView.info"></c:set>
		</c:if>
		<c:if test="${param.fdType == 4}">
		    <!-- 待阅读事项 -->
			<c:set var="infoTip" value="sysNotifyTodo.toView.info"></c:set>
		</c:if>		
		<c:if test="${param.fdType == 14}">
			<!-- 待办事项 -->
			<c:set var="infoTip" value="sysNotifyTodo.type.list.all.info"></c:set>
			<c:set var="fastTodo" value="true"></c:set>
		</c:if>
		<c:if test="${param.fdType == 3}">
			<!-- 暂挂事项 -->
			<c:set var="infoTip" value="sysNotifyTodo.suspend.info"></c:set>
		</c:if>
		<c:if test="${param.finish == 1}">
			<c:set var="isShowEmail" value="false"></c:set>
			<c:if test="${param.dataType=='systoviewdone' }">
				<!-- 已阅读事项 -->
				<c:set var="infoTip" value="sysNotifyTodo.done.toViewDone"></c:set>
			</c:if>
			<c:if test="${param.dataType!='systoviewdone' }">
				<!-- 已处理事项 -->
				<c:set var="infoTip" value="sysNotifyTodo.done.label.2"></c:set>
			</c:if>
		</c:if>
<% /** --------End--------- 获取应该展示的事项类型（需处理待办、待阅读事项、待办事项、暂挂事项、已阅读事项、已处理事项）--------End--------- **/ %>

<% /** --------Start--------- 获取应该展示的事项类型对应的消息列表页面路径--------Start--------- **/ %>
<c:if test="${not empty requestScope.dataType}">
	<c:set var="itemPagePath" value="#j_path=%2Fprocess&dataType=${lfn:escapeHtml(dataType)}"></c:set> <!-- #122562 15版本后调整了待办首页归类，导致没法精确的跳转，先给个默认首页 -->
    <c:if test="${requestScope.dataType eq 'todo' }"> <% /**  待办   **/ %>
        <c:set var="itemPagePath" value="#j_path=%2Fprocess&dataType=${lfn:escapeHtml(dataType)}"></c:set>
    </c:if>
    <c:if test="${requestScope.dataType eq 'systoview' }"> <% /**  待阅   **/ %>
        <c:set var="itemPagePath" value="#j_path=%2Fsystem&dataType=${lfn:escapeHtml(dataType)}"></c:set>
    </c:if> 
    <c:if test="${requestScope.dataType eq 'tododone' }"> <% /**  已办   **/ %>
        <c:set var="itemPagePath" value="#j_path=%2Fprocess&dataType=${lfn:escapeHtml(dataType)}"></c:set>
    </c:if> 
    <c:if test="${requestScope.dataType eq 'systoviewdone' }"> <% /**  已阅   **/ %>
        <c:set var="itemPagePath" value="#j_path=%2Fsystem&dataType=${lfn:escapeHtml(dataType)}"></c:set>
    </c:if>              
</c:if>
<% /** --------End--------- 获取应该展示的事项类型对应的消息列表页面路径--------End--------- **/ %>

<div class="lui_dataview_classic_heading">

    <% /**  显示信件图标   **/ %>
	<span id="lui_notify_todo_tip" class="lui_dataview_classic_icon"></span>
	
	<span class="lui_notify_count_tip">
        <% /**  您 没有   **/ %>
		<c:if test="${totalrows==0}">
			<bean:message bundle="sys-notify" key="sysNotifyTodo.home.you" />
			<b><bean:message bundle="sys-notify" key="sysNotifyTodo.home.notHave" /></b>
		</c:if>
		<% /**  您有 “N” 条   **/ %>
		<c:if test="${totalrows>0}">
			<bean:message bundle="sys-notify" key="sysNotifyTodo.home.youHave" />
			<b id="toViewCount">${totalrows}</b> <c:if test="${totalrows>1}"><bean:message bundle="sys-notify" key="sysNotifyTodo.home.counts" /></c:if><c:if test="${totalrows==1}"><bean:message bundle="sys-notify" key="sysNotifyTodo.home.count" /></c:if>
		</c:if>
        <% /**  事项类型超链接（需处理待办、待阅读事项、待办事项、暂挂事项、已阅读事项、已处理事项）  **/ %>
		<c:if test="${not empty infoTip}">
			<a class="lui_notify_link_txt" href="${LUI_ContextPath }/sys/notify/index.jsp${pageScope.itemPagePath}${lfn:escapeHtml(_fdAppName)}${crqPrefix}" target="${target}">
			    <bean:message bundle="sys-notify" key="${infoTip}" />
			</a>
		</c:if>
	</span>
	
	<% /**  显示 邮件数量    **/ %>
	<c:if test="${isShowEmail=='true'}">
		<%@ include file="/sys/notify/sys_notify_todo_ui/email.jsp"%>
	</c:if>
	
	<% /**  显示 刷新图标    **/ %>
	<span class="lui_refreshIcon"><a onclick="location.reload();onPublishPortalRefresh()"><img src="${LUI_ContextPath}/sys/notify/resource/images/refresh.png" alt='<bean:message bundle="sys-notify" key="sysNotifyTodo.portal.refresh" />'/></a></span>
	
	<% /**  显示 快速审批图标    **/ %>
	<%
		boolean isCanFastTodo = false;
		String fastTodoIds = new LbpmSettingDefault().getFastTodoIds();
		if(StringUtil.isNull(fastTodoIds) || UserUtil.checkUserIds(Arrays.asList(fastTodoIds.split(";")))){
			isCanFastTodo = true;
		}
		request.setAttribute("isCanFastTodo",isCanFastTodo);
	%>
	<c:if test="${fastTodo && isCanFastTodo}">
			<span class="lui_fastApproveIcon" title='<bean:message bundle="sys-notify" key="sysNotifyTodo.tab.title5" />' ><a href="${LUI_ContextPath }/sys/notify/index.jsp#j_path=%2Ffastview${lfn:escapeHtml(_fdAppName)}${crqPrefix}" target="_blank""><img src="${LUI_ContextPath}/sys/notify/resource/images/fastreview.png" alt='<bean:message bundle="sys-notify" key="sysNotifyTodo.portal.refresh" />'/></a></span>
	</c:if>
	
	
	<% /**  无相关处理数据时的提醒（“您  没有  需处理待办  喝杯咖啡休息一下吧！”）   **/ %>
	<c:if test="${totalrows==0}">
		<!--空值提醒 Starts-->
		<div class="lui-nodata-tips lui-nodata-tips-todo">
			<div class="imgbox"></div>
			<div class="txt">
				<p>
					<bean:message bundle="sys-notify" key="sysNotifyTodo.home.you" />
					&nbsp;<font style="color:#FF6600;"><b><bean:message bundle="sys-notify" key="sysNotifyTodo.home.notHave" /></b></font>&nbsp;
					<bean:message bundle="sys-notify" key="${infoTip}" />
				</p>
				<p><bean:message bundle="sys-notify" key="sysNotifyTodo.home.noData.info" /></p>
			</div>
		</div>
		<!--空值提醒 Ends-->
	</c:if>

</div>