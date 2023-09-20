<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
			<br/>
			<p class="txttitle">
				<bean:message bundle="km-imeeting" key="kmImeetingMain.selectHoldPlace" />
			</p>
			<br/>
			<table class="tb_normal" width="98%">
				<tr>
					<%--召开时间--%>
					<td class="td_normal_title" width=15%>
					  	<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDate" />
					</td>
					<td width=35%>
						<xform:datetime property="fdHoldDate" showStatus="edit" required="true" value="${HtmlParam['fdHoldDate']}"></xform:datetime>
					</td>
					<%--结束时间--%>
					<td class="td_normal_title" width=15%>
					  	<bean:message bundle="km-imeeting" key="kmImeetingMain.fdFinishDate" />
					</td>
					<td width=35%>
						<xform:datetime property="fdFinishDate" showStatus="edit" required="true" value="${HtmlParam['fdFinishDate']}"></xform:datetime>
					</td>
				</tr>
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
						<list:listview id="listview" style="height:200px;overflow-y:scroll;" cfg-needMinHeight="false">
							<ui:source type="AjaxJson" >
								{url:'/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=listResources&type=free&rowsize=3&fdHoldDate=${JsParam.fdHoldDate}&fdFinishDate=${JsParam.fdFinishDate}&bookId=${JsParam.bookId}&exceptResIds=${JsParam.exceptResIds}'}
							</ui:source>
							<list:colTable isDefault="false" layout="sys.ui.listview.listtable"  name="columntable" onRowClick="selectResouce('!{fdId}','!{fdName}','!{fdUserTime}','!{select}','!{fdAddressFloor}','!{fdSeats}','!{fdDetail}','!{docKeeper.fdName}');">
								<list:col-html title=" " style="text-align:left;width:10%;">
									var disabled="";
									if(row['select']==0) 
										disabled='disabled="disabled"'
									{$ <input type="radio" value="{%row['fdId']%}" name="resId"  {%disabled%} onclick="selectResouce('{%row['fdId']%}','{%row['fdName']%}','{%row['fdUserTime']%}','{%row['select']%}','{%row['fdAddressFloor']%}','{%row['fdSeats']%}','{%row['fdDetail']%}','{%row['docKeeper.fdName']%}');"> $}
								</list:col-html>
								<list:col-auto props="fdOrder" ></list:col-auto>
								<list:col-html title="${ lfn:message('km-imeeting:kmImeetingRes.fdName') }" headerClass="width140" styleClass="width140">
									var title="";
									if(row['fdAddressFloor'] !="" && row['fdAddressFloor'] != null && typeof row['fdAddressFloor'] != "undefined") 
										title=title+'<bean:message bundle="km-imeeting" key="kmImeetingRes.fdAddressFloor"/>:'+row['fdAddressFloor']+'&#10;';
									if(row['fdSeats'] != "" && row['fdSeats'] != null && typeof row['fdSeats'] != "undefined") 
										title=title+'<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSeats"/>:'+row['fdSeats']+'&#10;';
									if(row['fdDetail'] != "" && row['fdDetail'] != null && typeof row['fdDetail'] != "undefined") 
										title=title+'<bean:message bundle="km-imeeting" key="kmImeetingRes.fdDetail"/>:'+row['fdDetail'];	
									{$ <div title="{%title%}">{%row['fdName']%}</div> $}									
								</list:col-html>
								<list:col-auto props="docStatus;docCategory.fdName" ></list:col-auto>
							</list:colTable>
							<ui:event topic="list.loaded">
								seajs.use(['lui/jquery'], function($) {
									$('[name="resId"][value="${JsParam.resId}"]').click();
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
						<ui:button id="select_resource_close" text="${lfn:message('button.cancel') }"  onclick="selectCancel();"/>
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
		var selectedResource={resId:"${JsParam.resId}",resName:"${JsParam.resName}",resFdAddressFloor:"",resFdSeats:"",resFdDetail:"",docKeeperName:""};
		
		//选择资源时触发
		window.selectResouce=function(resId,resName,resUserTime,select,resFdAddressFloor,resFdSeats,resFdDetail,docKeeperName){
			//可选中才触发此事件
			if(select=="1"){
				//资源radio置为选中
				$('[name="resId"][value="'+resId+'"]').prop('checked',true);
				selectedResource['resId']=resId;
				selectedResource['resName']=str.decodeHTML(resName).replace(/&#039;/,'\'');
				selectedResource['resUserTime']=resUserTime;
				selectedResource['resFdAddressFloor']=resFdAddressFloor;
				selectedResource['resFdSeats']=resFdSeats;
				selectedResource['resFdDetail']=str.decodeHTML(resFdDetail).replaceAll('<br>','\r<br>');
				selectedResource['docKeeperName']=docKeeperName;
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
			selectedResource.fdHoldDate=$('[name="fdHoldDate"]').val();
			selectedResource.fdFinishDate=$('[name="fdFinishDate"]').val();
			$dialog.hide(selectedResource);
		};
		//取消
		window.selectCancel=function(){
			$dialog.hide(null);
		};
		//取消选定
		window.selectCancelSubmit=function(){
			$dialog.hide({resId:"",resName:"",resFdAddressFloor:"",resFdSeats:"",resFdDetail:""});
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