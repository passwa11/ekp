<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
			<br/>
			<html:hidden property="fdHoldDate" value="${HtmlParam['fdHoldDate']}"/>
			<html:hidden property="fdFinishDate" value="${HtmlParam['fdFinishDate']}"/>
			<p class="txttitle">
				<bean:message bundle="km-imeeting" key="kmImeetingMain.selectHoldPlace" />
			</p>
			<br/>
			<table class="tb_normal" width="98%">
				<tr>
					<%--会议室名称--%>
					<td class="td_normal_title" width=15%>
					  	<bean:message bundle="km-imeeting" key="kmImeetingRes.fdName" />
					</td>
					<td width=35%>
						<xform:text property="fdName" showStatus="edit" style="width:80%"></xform:text>
					</td>
					<%--所属类别--%>
					<td class="td_normal_title" width=15%>
					  	<bean:message bundle="km-imeeting" key="kmImeetingRes.docCategory" />
					</td>
					<td width=35%>
						<xform:dialog propertyId="docCategoryId" propertyName="docCategoryName" showStatus="edit" className="inputsgl" style="width:90%;float:left">
							Dialog_SimpleCategory('com.landray.kmss.km.imeeting.model.KmImeetingResCategory','docCategoryId','docCategoryName',true,';','00');
						</xform:dialog>
					</td>
				</tr>
				<tr>
					<%--查询按钮--%>
					<td colspan="4" style="text-align: center;">
					  	<%--查询所有资源--%>
						<ui:button id="res_all_btn" text="${lfn:message('km-imeeting:kmImeetingRes.query.all') }"   onclick="queryResource('all');"/>
						<%--查询空闲资源--%>
						<ui:button id="res_free_btn" text="${lfn:message('km-imeeting:kmImeetingRes.query.fress') }"  onclick="queryResource('free')"/>
					</td>
				</tr>
				<tr>
					<%--资源列表--%>
					<td colspan="4">
						<list:listview id="listview" style="height:200px;overflow-y:scroll;">
							<ui:source type="AjaxJson" >
								{url:'/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=listResources&type=free&rowsize=3&fdHoldDate=${JsParam.fdHoldDate}&fdFinishDate=${JsParam.fdFinishDate}&resIds=${JsParam.resIds}&bookId=${JsParam.bookId}&exceptResIds=${JsParam.exceptResIds}'}
							</ui:source>
							<list:colTable isDefault="false" layout="sys.ui.listview.listtable"  name="columntable" onRowClick="selectResouce(event,'!{fdId}');">
								<list:col-html title=" " style="text-align:left;width:10%;">
									var disabled="";
									if(row['select']==0) 
										disabled='disabled="disabled"'
									{$ <input 
										{%disabled%}
										type="checkbox" 
										name="resId"
										value="{%row['fdId']%}"   
										data-rowName = "{%row['fdName']%}"
										data-rowUserTime = "{%row['fdUserTime']%}"
										data-select = "{%row['select']%}"
										onclick="selectResouce(event,'{%row['fdId']%}');" /> 
									$}
								</list:col-html>
								<list:col-auto props="fdOrder" ></list:col-auto>
								<list:col-html title="${ lfn:message('km-imeeting:kmImeetingRes.fdName') }" headerClass="width140" styleClass="width140">
									var title="";
									if(row['fdAddressFloor']!="") 
										title=title+'<bean:message bundle="km-imeeting" key="kmImeetingRes.fdAddressFloor"/>:'+row['fdAddressFloor']+'&#10;';
									if(row['fdSeats']!="") 
										title=title+'<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSeats"/>:'+row['fdSeats']+'&#10;';
									if(row['fdDetail']!="") 
										title=title+'<bean:message bundle="km-imeeting" key="kmImeetingRes.fdDetail"/>:'+row['fdDetail'];	
									{$ <div title="{%title%}">{%row['fdName']%}</div> $}									
								</list:col-html>
								<list:col-auto props="docStatus;docCategory.fdName" ></list:col-auto>
							</list:colTable>
							<ui:event topic="list.loaded">
								seajs.use(['lui/jquery'], function($) {
									if('${JsParam.resIds}'){
										var resIds = '${JsParam.resIds}'.split(';');
										for(var i = 0; i < resIds.length; i++){
											var node = $('[name="resId"][value="'+ resIds[i] +'"]');
											node.prop('checked',true);
										}
									}
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
		var selectedResourceMap = (function(){
			var __map = {};
			if('${JsParam.resIds}'){
				var resIds = '${JsParam.resIds}'.split(';');
				var resNames = '${JsParam.resNames}'.split(";");
				for(var i = 0; i < resIds.length; i++){
					__map[resIds[i]] = {resName:resNames[i]};
				}
			}
			return __map;
		})();
		
		//点击资源时触发
		window.selectResouce = function(evt, resId){
			var checkboxNode = $('[name="resId"][value="'+resId+'"]');
			var select = checkboxNode.attr('data-select');
			if(evt.target !== checkboxNode[0] && select == '1'){
				var checked = checkboxNode.prop('checked');
				checkboxNode.prop('checked',!checked);
			}
			if(select !== '1'){
				return;
			}
			if(selectedResourceMap[resId]){
				delete selectedResourceMap[resId];
			}else{
				selectedResourceMap[resId] = {
					resName : checkboxNode.attr('data-rowName'),
					resUserTime : checkboxNode.attr('data-rowUserTime')
				};
			}
		};
		
		//查询资源
		window.queryResource=function(type){
			var url=LUI('listview').source.url;
			LUI('listview').source.setUrl(replaceParameter(url,{
				"type":type,
				"fdName":$('[name="fdName"]').val(),
				"fdHoldDate":$('[name="fdHoldDate"]').val(),
				"fdFinishDate":$('[name="fdFinishDate"]').val(),
				"docCategoryId":$('[name="docCategoryId"]').val()
			}));
			LUI('listview').source.get();
		};
		//提交
		window.selectSubmit=function(){
			var resIdArray = [],
				resNameArray = [],
				resUserTimeArray = [];
			for(var resId in selectedResourceMap){
				
				resIdArray.push(resId);
				if(selectedResourceMap[resId] 
						&& !selectedResourceMap[resId].resName){
					var checkboxNode = $('[name="resId"][value="'+resId+'"]');
					selectedResourceMap[resId].resName = checkboxNode.attr('data-rowName');
					selectedResourceMap[resId].resUserTime = checkboxNode.attr('data-rowUserTime');
				}
				resNameArray.push(selectedResourceMap[resId].resName);
				resUserTimeArray.push(selectedResourceMap[resId].resUserTime);
			}
			
			$dialog.hide({
				resId : resIdArray.join(';'), 
				resName : resNameArray.join(';'), 
				resUserTime : resUserTimeArray.join(';')
			});
		};
		//取消
		window.selectCancel=function(){
			$dialog.hide(null);
		};
		//取消选定
		window.selectCancelSubmit=function(){
			$dialog.hide({resId : '',resName : '',resUserTime : ''});
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