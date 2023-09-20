<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/log/resource/import/jshead.jsp"%>

<div id="optBarDiv">
	<!-- 审计 -->
	<kmss:auth requestURL="/sys/log/sys_log_bak/sysLogBak.do?method=audit" requestMethod="POST">
		<c:if test="${sysLogBak.fdStatus == 0}">
			<input type="button" value="<bean:message bundle="sys-log" key="sysLogBak.button.audit"/>" onclick="if(!confirmAudit())return;Com_OpenWindow('sysLogBak.do?method=audit&List_Selected=${JsParam.fdId}','_self');">
		</c:if>
	</kmss:auth>
	<!-- 备注 -->
	<kmss:auth requestURL="/sys/log/sys_log_bak/sysLogBak.do?method=desc" requestMethod="POST">
		<input type="button" value="<bean:message key="button.edit"/><bean:message bundle="sys-log" key="sysLogBak.fdDesc"/>" onclick="desc('${JsParam.fdId}');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-log" key="table.sysLogBak"/></div>
<center>
<script type="text/javascript">
		function confirmAudit(msg){
			var del = confirm('<bean:message bundle="sys-log" key="page.comfirmAudit"/>');
			return del;
		}
		
	 	seajs.use(['lui/dialog'], function(dialog) {
 			//编辑备注
	 		window.desc = function(id) {
	 			var title = '<bean:message key="button.edit"/><bean:message bundle="sys-log" key="sysLogBak.fdDesc"/>';
	 	    	var actionUrl = '<c:url value="/sys/log/sys_log_bak/sysLogBak.do?method=desc"/>';
	 			dialog.iframe('/sys/log/sys_log_bak/import/iframe_desc.jsp?fdId='+id, title,
	 				function (value){
	 	                // 回调方法
	 					if(value) {
	 	                    dialog.result(value);
	 	                	window.location.reload();
	 					}
	 				},
	 				{width:600,height:400,params:{url:actionUrl,data:"fdId="+id}}
	 			);
	 		}
	 	});
		
</script>
<table class="tb_normal" width=95%>
		<tr>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('date.label')}
	        </td>
	        <td colspan="5" width="90.0%">
	            <div id="_xform_fdYear" _xform_type="text">
	                <xform:text property="fdYear" showStatus="view" style="width:95%;" />${lfn:message('calendar.year')}
	                <xform:text property="fdMonth" showStatus="view" style="width:95%;" />${lfn:message('calendar.month')}
	            </div>
	        </td>
	    </tr>
	    <tr>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdBackupStatus')}
	        </td>
	        <td width="23.3%">
	            <div id="_xform_fdBackupStatus" _xform_type="select">
	                <xform:select property="fdBackupStatus" htmlElementProperties="id='fdBackupStatus'" showStatus="view">
	                    <xform:enumsDataSource enumsType="sys_log_backup_backupStatus" />
	                </xform:select>
	            </div>
	        </td>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdBackupDate')}
	        </td>
	        <td width="23.3%">
	        	<xform:datetime property="fdBackupDate"/>
	        </td>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdBackupSource')}
	        </td>
	        <td width="23.3%">
	            <div id="_xform_fdBackupSource" _xform_type="select">
	                <xform:select property="fdBackupSource" htmlElementProperties="id='fdBackupSource'" showStatus="view">
	                    <xform:enumsDataSource enumsType="sys_log_backup_detail_source" />
	                </xform:select>
	            </div>
	        </td>
	    </tr>
	    <tr>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdCleanStatus')}
	        </td>
	        <td width="23.3%">
	            <div id="_xform_fdCleanStatus" _xform_type="select">
	                <xform:select property="fdCleanStatus" htmlElementProperties="id='fdCleanStatus'" showStatus="view">
	                    <xform:enumsDataSource enumsType="sys_log_backup_cleanStatus" />
	                </xform:select>
	            </div>
	        </td>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdCleanDate')}
	        </td>
	        <td width="23.3%">
	        	<xform:datetime property="fdCleanDate"/>
	        </td>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdCleanSource')}
	        </td>
	        <td width="23.3%">
	            <div id="_xform_fdCleanSource" _xform_type="select">
	                <xform:select property="fdCleanSource" htmlElementProperties="id='fdCleanSource'" showStatus="view">
	                    <xform:enumsDataSource enumsType="sys_log_backup_detail_source" />
	                </xform:select>
	            </div>
	        </td>
	    </tr>
	    <tr>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdRecoveryStatus')}
	        </td>
	        <td width="23.3%">
	            <div id="_xform_fdRecoveryStatus" _xform_type="select">
	                <xform:select property="fdRecoveryStatus" htmlElementProperties="id='fdRecoveryStatus'" showStatus="view">
	                    <xform:enumsDataSource enumsType="sys_log_backup_recoveryStatus" />
	                </xform:select>
	            </div>
	        </td>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdRecoveryDate')}
	        </td>
	        <td width="46.6%" colspan="3">
	        	<xform:datetime property="fdRecoveryDate"/>
	        </td>
	    </tr>
	    <tr>
	        <td class="td_normal_title" width="10%">
	            ${lfn:message('sys-log:sysLogBak.fdDesc')}
	        </td>
	        <td colspan="5" width="90.0%">
	            <div id="_xform_fdDesc" _xform_type="textarea">
	                <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
	            </div>
	        </td>
	    </tr>
</table>
<br/>
<table class="tb_normal" width="95%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
    <tr align="center" class="tr_normal_title">
        <td style="width:40px;">
            ${lfn:message('page.serial')}
        </td>
        <td style="width:60px;">
            ${lfn:message('sys-log:sysLogBakDetail.fdType')}
        </td>
        <td style="width:60px;">
            ${lfn:message('sys-log:sysLogBakDetail.fdSource')}
        </td>
        <td style="width:100px;">
            ${lfn:message('sys-log:sysLogBakDetail.fdCreator')}
        </td>
        <td style="width:100px;">
            ${lfn:message('sys-log:sysLogBakDetail.fdBeginTime')}
        </td>
        <td style="width:100px;">
            ${lfn:message('sys-log:sysLogBakDetail.fdEndTime')}
        </td>
        <td style="width:80px;">
            ${lfn:message('sys-log:sysLogBakDetail.fdStatus')}
        </td>
        <td style="width:220px;">
            ${lfn:message('sys-log:sysLogBakDetail.fdFileName')}
        </td>
        <td>
            ${lfn:message('sys-log:sysLogBakDetail.fdDesc')}
        </td>
    </tr>
    <c:forEach items="${sysLogBakForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
        <tr KMSS_IsContentRow="1">
            <td align="center">
                ${vstatus.index+1}
            </td>
            <td align="center">
                <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                <div id="_xform_fdDetail_Form[${vstatus.index}].fdType" _xform_type="select">
                    <xform:select property="fdDetail_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdType'" showStatus="view">
                        <xform:enumsDataSource enumsType="sys_log_backup_detail_type" />
                    </xform:select>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdDetail_Form[${vstatus.index}].fdSource" _xform_type="select">
                    <xform:select property="fdDetail_Form[${vstatus.index}].fdSource" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdSource'" showStatus="view">
                        <xform:enumsDataSource enumsType="sys_log_backup_detail_source" />
                    </xform:select>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdDetail_Form[${vstatus.index}].fdCreatorId" _xform_type="address">
                    <xform:address propertyId="fdDetail_Form[${vstatus.index}].fdCreatorId" propertyName="fdDetail_Form[${vstatus.index}].fdCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdDetail_Form[${vstatus.index}].fdBeginTime" _xform_type="datetime">
                    <xform:datetime property="fdDetail_Form[${vstatus.index}].fdBeginTime" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdDetail_Form[${vstatus.index}].fdEndTime" _xform_type="datetime">
                    <xform:datetime property="fdDetail_Form[${vstatus.index}].fdEndTime" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdDetail_Form[${vstatus.index}].fdStatus" _xform_type="select">
                    <xform:select property="fdDetail_Form[${vstatus.index}].fdStatus" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdStatus'" showStatus="view">
                        <xform:enumsDataSource enumsType="sys_log_backup_detail_status" />
                    </xform:select>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdDetail_Form[${vstatus.index}].fdFileName" _xform_type="text">
                    <xform:text property="fdDetail_Form[${vstatus.index}].fdFileName" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td align="left">
                <div id="_xform_fdDetail_Form[${vstatus.index}].fdDesc" _xform_type="textarea">
                    <xform:textarea property="fdDetail_Form[${vstatus.index}].fdDesc" showStatus="view" style="width:95%;" />
                </div>
            </td>
        </tr>
    </c:forEach>
</table>

</center>
<%@ include file="/resource/jsp/view_down.jsp"%>