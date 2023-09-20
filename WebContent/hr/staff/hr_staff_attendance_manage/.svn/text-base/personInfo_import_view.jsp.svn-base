<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService,com.landray.kmss.util.SpringBeanUtil,java.util.List,com.landray.kmss.sys.time.model.SysTimeLeaveRule" %>
<%
	ISysTimeLeaveAmountService sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil.getBean("sysTimeLeaveAmountService");
	List<SysTimeLeaveRule> leaveRuleList = sysTimeLeaveAmountService.getAllLeaveRule();
	String leaveNames = "";
	for(SysTimeLeaveRule leaveRule : leaveRuleList) {
		leaveNames += leaveRule.getFdName() + ";";
	}
	pageContext.setAttribute("leaveNames", leaveNames);
%>

<ui:content expand="${('attendanceManage' eq JsParam.anchor || 'attendanceManageDetailed' eq JsParam.anchor) ? 'true' : 'false'}" title="${ lfn:message('hr-staff:table.hrStaffAttendanceManage') }">
	<table class="tb_normal" style="margin: 20px 0" width=98%>
		<%-- 带薪假期 --%>
		<tr class="tr_normal_title" id="attendanceManage">
			<td align="left" colspan="4">
				<label>
					<input type="checkbox" value="true" onclick="this.checked ? $('#paidHoliday').show() : $('#paidHoliday').hide();">
					<bean:message bundle="hr-staff" key="hrStaffAttendanceManage.paidHoliday" />
				</label>
			</td>
		</tr>
		<tr id="paidHoliday" style="display:none">
			<td colspan="4">
				<%-- 带薪假期 --%>
				<list:listview channel="hrStaffAttendanceManage">
					<ui:source type="AjaxJson">
						{url:'/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=list&personId=${ JsParam.personInfoId }'}
					</ui:source>
					<list:colTable isDefault="false" layout="sys.ui.listview.columntable" name="columntable" channel="hrStaffAttendanceManage">
						<list:col-serial></list:col-serial> 
						<list:col-auto props="fdYear;${leaveNames}totalRest;"></list:col-auto>
					</list:colTable>
				</list:listview> 
				<list:paging channel="hrStaffAttendanceManage" />
			</td>
		</tr>
		
		<%-- 请假明细 --%>
		<tr class="tr_normal_title" id="attendanceManageDetailed">
			<td align="left" colspan="4">
				<label>
					<input type="checkbox" value="true" onclick="this.checked ? $('#manageDetailed').show() : $('#manageDetailed').hide();">
					<bean:message bundle="hr-staff" key="table.hrStaffAttendanceManageDetailed" />
				</label>
			</td>
		</tr>
		<tr id="manageDetailed" style="display:none">
			<td colspan="4">
				<%-- 请假明细 --%>
				<list:listview channel="hrStaffAttendanceManageDetailed">
					<ui:source type="AjaxJson">
						{url:'/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=list&personId=${ JsParam.personInfoId }'}
					</ui:source>
					<list:colTable isDefault="false" layout="sys.ui.listview.columntable" name="columntable" channel="hrStaffAttendanceManageDetailed">
						<list:col-serial></list:col-serial> 
						<list:col-auto props="fdLeaveTime;fdReview;fdStartTime;fdEndTime;fdLeaveName;fdOprType;fdOprDesc;"></list:col-auto>
					</list:colTable>
				</list:listview>
				<list:paging channel="hrStaffAttendanceManageDetailed" />
			</td>
		</tr>
		
		<%-- 加班明细 --%>
		<tr class="tr_normal_title" id="attendanceManageDetailed_Overtime">
			<td align="left" colspan="4">
				<label>
					<input type="checkbox" value="true" onclick="this.checked ? $('#manageDetailed_Overtime').show() : $('#manageDetailed_Overtime').hide();">
					<bean:message bundle="hr-staff" key="table.hrStaffAttendanceManageDetailed.overtime" />
				</label>
			</td>
		</tr>
		<tr id="manageDetailed_Overtime" style="display:none">
			<td colspan="4">
				<%-- 加班明细 --%>
				<list:listview channel="hrStaffAttendanceManageDetailed_Overtime">
					<ui:source type="AjaxJson">
						{url:'/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=list&personId=${ JsParam.personInfoId }&fdType=2'}
					</ui:source>
					<list:colTable isDefault="false" layout="sys.ui.listview.columntable" name="columntable" channel="hrStaffAttendanceManageDetailed_Overtime">
						<list:col-serial></list:col-serial> 
						<list:col-auto props="fdLeaveTime;fdReview;fdStartTime;fdEndTime;fdLeaveName;fdOprType;fdOprDesc;"></list:col-auto>
					</list:colTable>
				</list:listview> 
				<list:paging channel="hrStaffAttendanceManageDetailed_Overtime" />
			</td>
		</tr>
	</table>
	
	<script type="text/javascript">
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		// 监听新建更新等成功后刷新
		topic.subscribe('successReloadPage', function() {
			setTimeout(function() {
				seajs.use(['lui/topic'], function(topic) {
					topic.channel("hrStaffAttendanceManage").publish('list.refresh');
				});
			}, 100);
		});

		// 编辑
		window._edit = function(url) {
			Com_OpenWindow(url);
		};

		// 删除
		window._delete = function(url, id) {
			var values = [];
				if(id) {
					values.push(id);
	 		} else {
				$("input[name='List_Selected']:checked").each(function() {
					values.push($(this).val());
				});
	 		}
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
				if(value == true) {
					window.del_load = dialog.loading();
					$.ajax({
						url : url,
						type : 'POST',
						data : $.param({"List_Selected" : values}, true),
						dataType : 'json',
						error : function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
						success: function(data) {
							if(window.del_load != null){
								window.del_load.hide(); 
								topic.channel("hrStaffAttendanceManage").publish('list.refresh');
							}
							dialog.result(data);
						}
				   });
				}
			});
		};

		LUI.ready(function() {
			$("#attendanceManage :checkbox").click();
			// $("#attendanceManageDetailed :checkbox").click();
			// $("#attendanceManageDetailed_Overtime :checkbox").click();
		});
	});
	</script>
</ui:content>
