<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysTimeLeaveAmountForm.method_GET == 'edit' }">
					<%-- 如果是最后一年度说明可以编辑和删除 --%>
					<c:if test="${lastYear }">
						<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=update">
							<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysTimeLeaveAmountForm, 'update');"></ui:button>
						</kmss:auth>
					</c:if>
				</c:when>
				<c:when test="${ sysTimeLeaveAmountForm.method_GET == 'add' }">
					<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=save">
						<ui:button text="${lfn:message('button.save') }" onclick="Com_Submit(document.sysTimeLeaveAmountForm, 'save');"></ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=saveadd">
						<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysTimeLeaveAmountForm, 'saveadd');"></ui:button>
					</kmss:auth>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<style type="text/css">
		</style>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle" style="margin: 15px 0;">
			${ lfn:message('sys-time:table.sysTimeLeaveAmount') }
		</p>
		
		<html:form action="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do">
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<c:if test="${ not empty lastYear && not lastYear }">
				<h3 style="color:#ff0000;margin: 5px;">${ lfn:message('sys-time:sysTimeLeaveAmount.edit.warn.message') }</h3>
			</c:if>
			<div class="lui_form_content_frame" style="padding-top:20px">
				<c:choose>
					<c:when test="${ sysTimeLeaveAmountForm.method_GET == 'add' }">
						<%@ include file="/sys/time/sys_time_leave_amount/item/item_add.jsp" %>
					</c:when>
					<c:when test="${ sysTimeLeaveAmountForm.method_GET == 'edit' }">
						<%@ include file="/sys/time/sys_time_leave_amount/item/item_edit.jsp" %>
					</c:when>
				</c:choose>
			</div>	
		</html:form>
		
		<script type="text/javascript">
		var validation = $KMSSValidation(document.forms['sysTimeLeaveAmountForm']);
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			
			// 改变人或年份，刷页面
			window.onChangePerson = window.onChangeYear = function() {
				var personId = $('[name="fdPersonId"]').val();
				var fdYear = $('[name="fdYear"]').val();
				if(personId && fdYear) {
					location.href = '${LUI_ContextPath}/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=add'
							+ '&personId=' + personId + '&year=' + fdYear;
				}
			};
			
			// 改变总天数，更新剩余天数
			window.onChangeTotal = function(value, element){
				if(element && value){
					var fieldName = $(element).attr('name');
					var restDayName = fieldName.match(/fdAmountItems\[\d+\]/g)[0] + '.fdRestDay';
					var usedDayName = fieldName.match(/fdAmountItems\[\d+\]/g)[0] + '.fdUsedDay';
					
					var usedDay = parseFloat($('[name="'+ usedDayName +'"]').val() || '0');
					var restDay = parseFloat(value) - usedDay;
					
					$('[name="'+ restDayName +'"]').val(restDay);
				}
			};
			
			// 改变有效日期，更新有效字段
			window.onChangeVDate = function(value, element) {
				var dateFormat = /^(\d{4})-(\d{2})-(\d{2})$/
				if (!dateFormat.test(value)) {
					return;
				}
				if(element && value){
					var fieldName = $(element).attr('name');
					var perfix = fieldName.match(/fdAmountItems\[\d+\]/g)[0];
					var isAvail = isAfterToday(value);
					$('[name="' + perfix + '.fdIsAvail' + '"]').val(isAvail);
				}
			};
			
			var isAfterToday = function(date) {
				if(date) {
					var dateObj = Com_GetDate(date, 'date', Com_Parameter.Date_format);
					if(dateObj){
						var today = new Date();
						today.setHours(0,0,0,0);
						if(today.getTime() > dateObj.getTime()){
							return false;
						}
					}
				}
				return true;
			};
			
			validation.addValidator('fraction', "${ lfn:message('sys-time:sysTimeLeaveRule.atMostHalf') }", function(v, e, o){
				if(v && e){
					return /^[0-9]+(\.[05])?$/g.test(v);
				}
			});
			
			validation.addValidator('yearUnique', "${ lfn:message('sys-time:sysTimeLeaveAmount.yearUnique') }", function(){
				var personId = $('[name="fdPersonId"]').val();
				var _years = $('[name="_years"]').val();
				var fdYear = $('[name="fdYear"]').val();
				if(personId && _years && fdYear){
					var yearArr = _years.split(';');
					for(var i in yearArr){
						if(fdYear == yearArr[i]){
							return false;
						}
					}
				}
				return true;
			});
			//验证年月日
			validation.addValidator('validYearDate', "请输入有效的日期。", function(v, e, o){
				var res=false;
				//格式:yyyy-MM-dd
				var regDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
				var dateFormat = Data_GetResourceString("date.format.date");
				//格式:MM/dd/yyyy
				if(Com_Parameter['Lang']!=null && (Com_Parameter['Lang']!='zh-cn' && Com_Parameter['Lang']!='zh-hk'&&Com_Parameter['Lang']!='ja-jp')){
					if('dd/MM/yyyy'==dateFormat){
						regDate = /^(((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)\/(0[469]|11))|((0[1-9]|[1][0-9]|2[0-9])\/02))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
					}else{
						regDate = /^(((0[13578]|1[02])\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\/(0[1-9]|[12][0-9]|30))|(02\/(0[1-9]|[1][0-9]|2[0-9])))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
					}
				}
				if(v){
					res=regDate.test(v);
				}
				return res;
			});

			validation.addValidator('validDateRange', "${ lfn:message('sys-time:sysTimeLeaveAmount.validDateRange') }", function(v, e, o){
				var fdYear = $('[name="fdYear"]').val();
				if(v && fdYear){
					var validDate = Com_GetDate(v, 'date', Com_Parameter.Date_format);
					if(validDate) {
						var maxDate = new Date();
						maxDate.setFullYear(parseInt(fdYear) + 1, 11, 31);
						maxDate.setHours(0,0,0,0);
						
						var minDate = new Date();
						minDate.setFullYear(parseInt(fdYear), 0, 1);
						minDate.setHours(0,0,0,0);
						if(maxDate.getTime() < validDate.getTime() || minDate.getTime() > validDate.getTime()){
							return false;
						}
					}
				}
				return true;
			});
			validation.addValidator('largerThanUsed', "${ lfn:message('sys-time:sysTimeLeaveAmount.largerThanUsed') }", function(v, e, o){
				if(v && e){
					var fieldName = $(e).attr('name');
					var usedDayFieldName = fieldName.match(/fdAmountItems\[\d+\]/g)[0] + '.fdUsedDay';
					var fdUsedDay = $('[name="'+usedDayFieldName+'"]').val();
					if(fdUsedDay){
						if(parseFloat(v) < parseFloat(fdUsedDay)){
							return false;
						}
					}
				}
				return true;
			});
		});
		</script>
	</template:replace>
</template:include>