<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script>
			seajs.use(["theme!list", "sys/profile/resource/css/operations.css#"]);
		</script>
		<div style="padding:8px;">
		<input name="refreshNum" value="0" type="hidden"/>
		<input name="toggle" value="0" type="hidden"/>
		
		<!-- 查询条件  -->
		<list:criteria id="criteria1" cfg-isSetToHash="false">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-tag:sysTagTags.fdName')}"/>
			<list:cri-criterion title="${lfn:message('sys-tag:sysTagGroupDetail.fdCates')}" key="fdCategorys" multi="false">
				<list:box-select>
					<list:item-select>
						<c:choose>
							<c:when test="${JsParam.cloudTagSign=='1'}">
								<ui:source type="AjaxJson">
									{"url":"/sys/tag/sys_tag_category/sysTagCategory.do?method=getTagCategory&exceptPrivate=false"} 
								</ui:source>
							</c:when>
							<c:otherwise>
								<ui:source type="AjaxJson">
									{"url":"/sys/tag/sys_tag_category/sysTagCategory.do?method=getTagCategory"} 
								</ui:source>
							</c:otherwise>
						</c:choose>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			
			<list:cri-criterion title="${lfn:message('sys-tag:sysTagCategory.fdIsSpecial1') }" key="fdIsSpecial" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-tag:sysTagCategory.isSpecial.yes') }', value:'true'},
							{text:'${ lfn:message('sys-tag:sysTagCategory.isSpecial.no') }',value:'false'},]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<!-- 创建者-->
			<list:cri-ref key="docCreator"  ref="criterion.sys.person"  multi="false" expand="false"
        		title="${lfn:message('sys-tag:sysTagMain.docCreatorId') }">
			</list:cri-ref>
		</list:criteria>
		 <%-- 多选选中组件 --%>
		<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" style="width: 95%;margin: 10px auto;">
			<%--已经选中的值，待加 
			<script type="text/config">
				{
					values:[{'id':"ss",name:'ssssssssssss'}]
				}
			</script>
			--%> 
			<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
			refreshCheckbox();
		</script>
		</div>
		<!-- 列表工具栏 -->
		<div class="lui_list_operation">
			
			<%--排序按钮  --%>
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sort property="docCreateTime" text="${lfn:message('sys-tag:sysTagMain.docCreateTime') }"></list:sort>
						<list:sort property="fdCountQuoteTimes" text="${lfn:message('sys-tag:sysTagTags.fdQuoteTimes') }"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<input type="hidden" name="fdCategoryId" value="${JsParam.fdCategoryId}"/>
			<div style="float:left;">
				<list:paging layout="sys.ui.paging.top">		
				</list:paging>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		 
		 
	 	<list:listview id="listview">
			<c:if test="${JsParam.cloudTagSign=='1'}">
				<ui:source type="AjaxJson">
					{
						url:"/sys/tag/sys_tag_tags/sysTagTags.do?method=list&allTag=true"
					}
				</ui:source>
			</c:if>
			<c:if test="${JsParam.removeAlieTagSign=='1'}">
				<ui:source type="AjaxJson">
					{
						url:"/sys/tag/sys_tag_tags/sysTagTags.do?method=list&type=removeAlieTag&fdRemoveMainId=${JsParam.fdRemoveMainId}"
					}
				</ui:source>
			</c:if>
				<!-- 列表视图 -->
				<list:colTable isDefault="false"
					id="subjectCol"
					onRowClick="selectLink('!{fdId}','!{fdName}')"
					name="columntable">				
			   <list:col-checkbox headerStyle="width:1%"/>
				<list:col-serial headerStyle="width:5%"/>
				<list:col-html
					title="${ lfn:message('sys-tag:sysTagTags.fdName')}"
					headerStyle="width:20%">
					{$
						<span class="com_fdName">{%row['fdName']%}</span>
					$}
			
				</list:col-html>
				
				<list:col-auto props="fdCategorys,docCreator,docCreateTime,fdCountQuoteTimes"></list:col-auto>
				
				
			</list:colTable>
				<ui:event topic="list.loaded" args="vt">
					
					var mulSelec = "${JsParam.mulSelect}";
					mulSelec = mulSelec.substring(0,4);
					if(mulSelec!=null&&mulSelec=="true"){
						var mulSelect = true;
					}
					
					if(!mulSelect){
						$("[name='List_Tongle']").css("display","none");
					}

						//每一行的数据
						var datas = vt.table.kvData;
						
						
						if(datas != null && datas != undefined){
							setTimeout(function() {
								refreshCheckbox();
							}, 100);
						}
						
						function getVal(id) {
							for (var i = 0; i < datas.length; i ++) {
								if (datas[i].fdId == id) {
									return datas[i];
								}
							}
							return null;
						}
						
						//全选
						LUI.$('#listview [name="List_Tongle"]').bind('click', function() {
							$("[name='toggle']").val("1"); //设置flag值是因为removeValAll()会触发refreshCheckbox(),这样会导致全选框按钮被提前设置false，数据紊乱
							LUI('selectedBean').removeValAll();
							if (this.checked) { 
								//LUI('selectedBean').addValAll(datas);
								//不能直接用该方法原因为name和fdName的名字不同
								for (var i = 0; i < datas.length; i ++) {
										selectLink(datas[i].fdId,datas[i].fdName);
								}
							} else {
								
							}
							$("[name='toggle']").val("0");
						});
						
						
						LUI.$('#listview [name="List_Selected"]').bind('click', function() { //点击列表某一项的checkbox
						
							if (!this.checked) {
								LUI('selectedBean').removeVal(this.value);
							} else {
								var values = LUI('selectedBean').getValues();
								if(values.length>0&&!mulSelect){//如果为长度大于0且非多选(即单选)，则清除全部值后重新选中
									LUI('selectedBean').removeValAll();
								}
								var val = getVal(this.value);
								if (val != null)
									selectLink(val.fdId,val.fdName);
							}
						});
						
						
						//首次刷新列表加载链接带入数据
						var refreshNum = $("[name='refreshNum']").val(); //此处放在列表刷新而不放在	$(function(){})中是因为selectedBean在$(function(){})运行时还没有加载出来
						
						if(refreshNum.indexOf("0")>-1){
		 		 			var top = Com_Parameter.top || window.top;	 
		 			 		var fdName = top.window.selectTagNames;
		 			 		var fdId = top.window.selectTagIds;
		 			 		if(fdId){//text未填写时默认传一个空进来,此判断可直接过滤掉不填写时的空字符串情况
		 			 			if(fdName.charAt(fdName.length-1)==";"){
		 			 				fdName = fdName.substring(0,fdName.length-1);
		 			 			}
		 			 			if(fdId.charAt(fdId.length-1)==";"){
		 			 				fdId = fdId.substring(0,fdId.length-1);
		 			 			}
			 			 		
		 			 			var name = fdName.split(/[;；]/);
		 			 			var id = fdId.split(/[;；]/);
		 			 			for(var i=0;i<id.length;i++){
		 			 				selectLink(id[i],name[i]);
		 			 			}
		 			 		}

		 		 			$("[name='refreshNum']").val("1");
						}
				</ui:event>
		</list:listview> 
		 
	 	<list:paging></list:paging>	 
	 	</div>
	 	<script>
	 	seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
	 		
			var mulSelec = "${JsParam.mulSelect }";
			mulSelec = mulSelec.substring(0,4);
			if(mulSelec!=null&&mulSelec=="true"){
				var mulSelect = true;
			}
	 	
	 		window.refreshCheckbox = function() {
				var vals = LUI('selectedBean').getValues();
				LUI.$('[name="List_Selected"]').each(function() {
					for (var i = 0; i < vals.length; i ++) {
						if (vals[i].id == this.value) {
							if (!this.checked)
								this.checked = true;
							return;
						}
					}
					if (this.checked)
						this.checked = false;
				});
			}
	 		
	 		window.refreshCheckbox1 = function() {
				LUI.$('[name="List_Selected"]').each(function() {
					if (this.checked)
						this.checked = false;
				});
			}
	 				
	 				
	 				
	 				window.onSubmit =  function() {
	 					var values = LUI('selectedBean').getValues();
	 					
	 					var rtn = [];
	 					
	 					//拼装fdName和fdId
	 					for(var i = 0; i < values.length; i++) {
		 					var data = {fdId:"",fdName:""}; //不能放在括号外面，因为这样会造成每次data新值都会修改rtn里面的data
	 						data.fdId = values[i].id;
	 						data.fdName = values[i].name;
	 						
	 						rtn.push(data);
	 					}
						
	 					if(rtn.length>=0){
	 						return rtn;
	 					}
	 			   	

	 				}
	 				

	 				
	 				<%-- 设置选中 --%>
	 				window.selectLink = function(iid,ffdName) {
	 					//将html字符转义
	 					var id = iid.replace(/&lt;/g,"<")
									.replace(/&gt;/g,">")
									.replace(/&#034;/g,'"')
									.replace(/&amp;/g,"&")
									.replace(/&#039;/g,"'");
						var fdName = ffdName.replace(/&lt;/g,"<")
									.replace(/&gt;/g,">")
									.replace(/&#034;/g,'"')
									.replace(/&amp;/g,"&")
									.replace(/&#039;/g,"'");
	 					var data = {
	 							"id":id,
	 							"name":fdName
	 					};
	 					if (LUI('selectedBean').hasVal(data)) {
							
	 						LUI('selectedBean').removeVal(data);
							refreshCheckbox();
	 						return;
	 					}
	 					
	 					var values = LUI('selectedBean').getValues();
						if(values.length>0&&!mulSelect){
							refreshCheckbox1();
							LUI('selectedBean').removeValAll();
							LUI('selectedBean').addVal(data);
							return;
						}
						
	 					LUI('selectedBean').addVal(data);
	 					
						refreshCheckbox();

	 				}
	 				
	 				//采用正则表达式获取地址栏参数
	 				window.GetQueryString = function GetQueryString(name)
	 				{
	 				     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	 				     var r = window.location.search.substr(1).match(reg);
	 				     if(r!=null)return  unescape(r[2]); return null;
	 				}
	 			});
	 	</script>
	</template:replace>
</template:include>