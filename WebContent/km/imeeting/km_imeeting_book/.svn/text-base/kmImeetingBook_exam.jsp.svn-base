<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<html:form action="/km/imeeting/km_imeeting_book/kmImeetingBook.do">
			<html:hidden property="bookId" value="${JsParam.bookId}" />
			<div>
				<table class="tb_normal" width="98%" style="margin-top: 15px;">
					<tr>
						<td>
							<bean:message key="kmImeetingBook.exam.status" bundle="km-imeeting"/>
						</td>
						<td>
							<xform:radio property="fdHasExam" showStatus="edit" value="${JsParam.fdHasExam}" onValueChange="handleRadioChange">
								<xform:simpleDataSource value="true"><bean:message key="kmImeetingBook.exam.status.yes" bundle="km-imeeting"/></xform:simpleDataSource>
								<xform:simpleDataSource value="false"><bean:message key="kmImeetingBook.exam.status.no" bundle="km-imeeting"/></xform:simpleDataSource>
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td>
							<bean:message key="kmImeetingBook.exam.remark" bundle="km-imeeting"/>
						</td>
						<td>
							<html:textarea property="fdExamRemark" style="width:95%;" onchange="handleTextAreaChange()"/>
							<span style="display: none;" class="txtstrong" id="isRequiredFlag">*</span>
							
							<div style="display: none;" class="txtstrong" id="isRequiredTip">
								<bean:message key="kmImeetingBook.exam.remark.tip" bundle="km-imeeting"/>
							</div>
						</td>
					</tr>
				</table>		
			</div>
			<div style="margin: 20px auto;width: 200px;">
				<center>
					<ui:button text="${lfn:message('button.submit')}"  onclick="save();" />
					<ui:button text="${lfn:message('button.cancel')}" onclick="Com_CloseWindow();" />
	        	</center>
			</div>
		</html:form>
	</template:replace>
</template:include>
<script>Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js",null,"js");</script>
<script>
	seajs.use(['theme!form']);
	seajs.use([
	    'lui/jquery',
	    'lui/dialog',
	    'lui/topic',
	    'lui/toolbar'
	    ], function($,dialog,topic,toolbar) {
			
	        function _closeWindow(){
	        	// 遍历所有父窗口判断是否存在$dialog
	        	var parent = window;
	        	while (parent) {
	        		if (typeof(parent.$dialog) != 'undefined') {
	        			parent.$dialog.hide("success");
	        			return;
	        		}
	        		if (parent == parent.parent)
	        			break;
	        		parent = parent.parent;
	        	}
	
	        	try {
	        		var win = window;
	        		for (var frameWin = win.parent; frameWin != null && frameWin != win; frameWin = win.parent) {
	        			if (frameWin["Frame_CloseWindow"] != null) {
	        				frameWin["Frame_CloseWindow"](win);
	        				return;
	        			}
	        			win = frameWin;
	        		}
	        	} catch (e) {
	        	}
	        	try {
	        		top.opener = top;
	        		top.open("", "_self");
	        		top.close();
	        	} catch (e) {
	        	}
	        }
		
			window.handleRadioChange = function(res) {
				if(res == 'true') {
					$('#isRequiredFlag').hide();
					$('#isRequiredTip').hide();
				} else {
					$('#isRequiredFlag').show();
				}
			}
			
			window.handleTextAreaChange = function() {
				$('#isRequiredTip').hide();
			}
		
			window.save = function(){
				var bookId = $("[name='bookId']").val();
				var fdHasExam;
				var fdExamRemark = $("[name='fdExamRemark']").val();
				$.each($("[name='fdHasExam']"),function(){
					if($(this).is(":checked")){
						fdHasExam = $(this).val();
					}
				});
				if(fdHasExam == 'false' && fdExamRemark == ''){
					//dialog.alert('<bean:message key="kmImeetingBook.exam.remark.tip" bundle="km-imeeting"/>');
					$('#isRequiredTip').show();
					return;
				}
				$.ajax({
					url : Com_Parameter.ContextPath + 'km/imeeting/km_imeeting_book/kmImeetingBook.do?method=exam',
					type : 'POST',
					data : {bookId : bookId, fdHasExam : fdHasExam, fdExamRemark : fdExamRemark},
					dataType : 'json',
					success : function(data){
						_closeWindow();
					}
				});
			}
	});
</script>