<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
			<br/>
			<p class="txttitle">
				<bean:message bundle="km-imeeting"  key="kmImeetingEquipment.btn.select"/>
			</p>
			<br/>
			<table class="tb_normal" width="98%">
				<tr>
					<%--会议室名称--%>
					<td class="td_normal_title" width=25%>
					  	<bean:message bundle="km-imeeting" key="kmImeetingEquipment.fdName" />
					</td>
					<td width=75% colspan="3">
						<xform:text property="fdName" showStatus="edit" style="width:70%"></xform:text>
						<ui:button id="res_free_btn" text="${lfn:message('button.search') }"  onclick="queryResource('free')"/>
					</td>
				</tr>
				<tr>
					<%--资源列表--%>
					<td colspan="4">
						<list:listview id="listview" style="height:200px;overflow-y:scroll;">
								<ui:source type="AjaxJson" >
									{url:'/km/imeeting/km_imeeting_equipment/kmImeetingEquipment.do?method=listEquipment&type=free&rowsize=3&fdHoldDate=${JsParam.fdHoldDate}&fdFinishDate=${JsParam.fdFinishDate}&method_GET=${JsParam.method_GET}'}
								</ui:source>
							<list:colTable isDefault="false" layout="sys.ui.listview.listtable"  name="columntable" onRowClick="selectResouce('!{fdId}');">
								<list:col-html title=" " style="text-align:left;width:10%;">
									{$ <input type="checkbox" value="{%row['fdId']%}" name="equipmentId" data="{%row['fdName']%}"> $}
								</list:col-html>
								<list:col-auto props="fdOrder;fdName;" ></list:col-auto>
							</list:colTable>
							<ui:event topic="list.loaded">
								var equipmentIds="${JsParam.equipmentId}".split(";");
								for(var i in equipmentIds){
									if(equipmentIds[i]){
										$('[name="equipmentId"][value="'+equipmentIds[i]+'"]').prop('checked',true);
									}
								}
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

		//选择资源时触发
		window.selectResouce=function(equipmentId){
			//可选中才触发此事件
			if($('[name="equipmentId"][value="'+equipmentId+'"]').prop('checked') ){
				$('[name="equipmentId"][value="'+equipmentId+'"]').prop('checked',false);
			}else{
				$('[name="equipmentId"][value="'+equipmentId+'"]').prop('checked',true);
			}
		};
		//查询资源
		window.queryResource=function(type){
			var url=LUI('listview').source.url;
			LUI('listview').source.setUrl(replaceParameter(url,{
				"type":type,
				"fdName":$('[name="fdName"]').val()
			}));
			LUI('listview').source.get();
		};
		//提交
		window.selectSubmit=function(){
			var equipmentId="",equipmentName="";
			var equipmentElement=$('[name="equipmentId"]');
			for(var i=0;i<equipmentElement.length;i++){
				if($(equipmentElement[i]).prop('checked')==true){
					equipmentId+=$(equipmentElement[i]).val()+";";
					equipmentName+=$(equipmentElement[i]).attr("data")+";";
				}
			}
			if(equipmentId){
				equipmentId=equipmentId.substring(0,equipmentId.length-1);
				equipmentName=equipmentName.substring(0,equipmentName.length-1);
				equipmentName = str.decodeHTML(equipmentName);
			}
			if(typeof($dialog)!="undefined"){
				//dialog.iframe形式
				$dialog.hide({equipmentId:equipmentId,equipmentName:equipmentName});
			}else{
				//兼容window.open和dialog.showModalDialog形式
				if(window.showModalDialog){
					window.returnValue={equipmentId:equipmentId,equipmentName:equipmentName};
				}else{
					opener.dialogCallback({equipmentId:equipmentId,equipmentName:equipmentName});
				}
				window.close();
			}
		};
		//取消
		window.selectCancel=function(){
			if(typeof($dialog)!="undefined"){
				//dialog.iframe形式
				$dialog.hide(null);
			}else{
				//兼容window.open和dialog.showModalDialog形式
				if(window.showModalDialog){
					window.returnValue=null;
				}else{
					opener.dialogCallback(null);
				}
				window.close();
			}
		};
		//取消选定
		window.selectCancelSubmit=function(){
			if(typeof($dialog)!="undefined"){
				//dialog.iframe形式
				$dialog.hide({equipmentId:"",equipmentName:""});
			}else{
				//兼容window.open和dialog.showModalDialog形式
				if(window.showModalDialog){
					window.returnValue={equipmentId:"",equipmentName:""};
				}else{
					opener.dialogCallback({equipmentId:"",equipmentName:""});
				}
				window.close();
			}
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