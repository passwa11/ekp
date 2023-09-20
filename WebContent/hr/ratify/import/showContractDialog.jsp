<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
			<br/>
			<p class="txttitle">
				${ lfn:message('hr-staff:hrStaffEntry.already.choose') }
			</p>
			<br/>
			<table class="tb_normal" width="98%">
				<tr>
					<%--合同列表--%>
					<td colspan="4">
						<list:listview id="listview" style="height:200px;overflow-y:scroll;">
							<ui:source type="AjaxJson" >
								{url:'/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=list&q.fdPersonInfo=${JsParam.staffId}'}
							</ui:source>
							<list:colTable isDefault="false" layout="sys.ui.listview.listtable"  name="columntable" onRowClick="selectContract('!{fdId}','!{fdBeginDate}','!{fdEndDate}');">
								<list:col-html title=" " style="text-align:left;width:10%;">
									{$
										<span style="display: none;" name-id="{%row['fdId']%}">{%row['fdName']%}</span>
										<span style="display: none;" mark-id="{%row['fdId']%}">{%row['remark']%}</span> 
										<input type="radio" value="{%row['fdId']%}" name="contId" onclick="selectContract('{%row['fdId']%}','{%row['fdBeginDate']%}','{%row['fdEndDate']%}');"> 
									$}
								</list:col-html>
								<list:col-auto props="index" ></list:col-auto>
								<list:col-html title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdName') }" headerClass="width140" styleClass="width140">	
									{$ <div>{%row['fdName']%}</div> $}									
								</list:col-html>
								<list:col-auto props="fdBeginDate;fdEndDate;fdMemo"></list:col-auto>
							</list:colTable>
							<ui:event topic="list.loaded">
								seajs.use(['lui/jquery'], function($) {
									$('[name="contId"][value="${JsParam.contId}"]').click();
								});
							</ui:event>
						</list:listview>
					</td>
				</tr>
				<tr>
					<%--查询按钮--%>
					<td colspan="4" style="text-align: center;">
					  	<%--选择--%>
						<ui:button id="select_resource_submit" text="${lfn:message('button.select')}"   onclick="selectSubmit();"/>
						<%--取消--%>
						<ui:button id="select_resource_cancel" text="${lfn:message('button.cancel') }"  onclick="selectCancel();"/>
						<%--取消选定--%>
						<ui:button id="select_resource_cancel" text="${lfn:message('button.cancelSelect') }"  onclick="selectCancelSubmit();"/>
					</td>
				</tr>
			</table>
	</template:replace>
</template:include>
<script>
	seajs.use([
 	      'lui/jquery',
 	      'lui/dialog',
 	      'lui/topic',
 	      'lui/util/str'
 	        ],function($,dialog,topic,str){

		//已选资源
		var selectedContract={contId:"${JsParam.contId}",contName:"${JsParam.contName}",fdBeginDate:'',fdEndDate:'',fdMemo:''};
		
		//选择资源时触发
		window.selectContract=function(contId,fdBeginDate,fdEndDate){
			var contName = $("span[name-id='"+contId+"']").text();
			var fdMemo = $("span[mark-id='"+contId+"']").text();
			//资源radio置为选中
			$('[name="contId"][value="'+contId+'"]').prop('checked',true);
			selectedContract['contId']=contId;
			selectedContract['contName']=str.decodeHTML(contName).replace(/&#039;/,'\'');
			selectedContract['fdBeginDate']=fdBeginDate;
			selectedContract['fdEndDate']=fdEndDate
			selectedContract['fdMemo']=str.decodeHTML(fdMemo).replace(/&#039;/,'\'');
		};
		//提交
		window.selectSubmit=function(){
			$dialog.hide(selectedContract);
		};
		//取消
		window.selectCancel=function(){
			$dialog.hide(null);
		};
		//取消选定
		window.selectCancelSubmit=function(){
			$dialog.hide({contId:"",contName:"",fdBeginDate:"",fdEndDate:"",fdMemo:""});
		};
		//修改source的URL
		var replaceParameter=function(url,parameterObj){
			for(var key in parameterObj){
				url=Com_SetUrlParameter(url,key,parameterObj[key]);
			}
			return url;
		}
		
	});
</script>