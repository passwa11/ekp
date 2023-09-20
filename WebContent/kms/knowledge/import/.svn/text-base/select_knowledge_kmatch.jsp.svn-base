<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="kmsKnowledgeBaseDocDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory') }"></c:set>	
<%
	KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
	String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabled)) {
%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>	
	<c:set var="kmsKnowledgeBaseDocDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory.categoryTrue') }"></c:set>
<%
	}
%>
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
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}"/>
		    <%--所属分类--%>
			<list:cri-ref ref="criterion.sys.simpleCategory" key="docCategory" multi="false"
					title="${kmsKnowledgeBaseDocDocCategory}">
			 	<list:varParams modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory"/>
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
			<div style='color: #979797;float: left;padding-top:1px;'> 
			
				${ lfn:message('kms-knowledge:kmsKnowledge.list.orderType') }：
			</div>
			<%--排序按钮  --%>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
				<ui:toolbar layout="sys.ui.toolbar.sort" >
					<list:sort property="docCreateTime" text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCreateTime')}"  group="sort.list"></list:sort>
					<list:sort property="docSubject" text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}" group="sort.list"></list:sort>
				</ui:toolbar>
				</div> 
			</div>
			<div style="float:left;">
				<list:paging layout="sys.ui.paging.top">		
				</list:paging>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		 
	 	<list:listview id="listview">
			<ui:source type="AjaxJson">
				{
					url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&orderby=docPublishTime&ordertype=down&docStatus=30&rowsize=12&authType=2&q.template=${param.template}'
				}
			</ui:source>
				<!-- 列表视图 -->
				<list:colTable isDefault="false"
					id="subjectCol"
					onRowClick="if(!${param.mulSelect }) return;selectLink('!{fdId}','!{docSubject}','!{fdKnowledgeType}')"
				   name="columntable">				
			   <list:col-checkbox headerStyle="width:4%"/>
				<list:col-serial headerStyle="width:4%"/>
				<list:col-html
					title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}"
					headerStyle="width:20%">
					{$  
					    {%row['icon']%}
						<span class="com_docSubject">{%row['docSubject']%}</span>
					$}
			
				</list:col-html>
				<list:col-html
					title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docAuthorId')}"
					headerStyle="width:15%">
					{$
						<span class="com_docAuthor">{%row['docAuthor.fdName']%}</span>
					$}
			
				</list:col-html>
				
				<list:col-auto props="docPublishTime;fdTotalCount;docIntrCount;docEvalCount;docScore"/>
				
				<list:col-html
					title="${kmsKnowledgeBaseDocDocCategory}"
					headerStyle="width:10%">
					{$
						<span class="com_docCategory">{%row['docCategory.fdName']%}</span>
					$}
			
				</list:col-html>
				
			</list:colTable>
				<ui:event topic="list.loaded" args="vt">
					var mulSelect = ${param.mulSelect }
					if(!mulSelect){
						$("[name='List_Tongle']").css("display","none");
					}

					
						refreshCheckbox();
						
						//每一行的数据
						var datas = vt.table.kvData;
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
										selectLink(datas[i].fdId, datas[i].docSubject, datas[i].fdKnowledgeType);
								}
							} else {
								
							}
							$("[name='toggle']").val("0");
							
							 setTimeout(function(){
								defaultSelect();
							},10);
						});
						LUI.$('#listview .lui_listview_columntable_table tr').bind('click', function() { //点击列表某一项
						
							if(!${param.mulSelect }){
								$(this).find("input[name='List_Selected']").each(function(){
								if ($(this).is(':checked')) {
		
										this.checked = false;
										LUI('selectedBean').removeValAll();
									} else {
										this.checked = true;
										var values = LUI('selectedBean').getValues();
										if(values.length>0){
											LUI('selectedBean').removeValAll();
										}
										var val = getVal($(this).val());
										if (val != null)
											selectLink(val.fdId, val.docSubject, val.fdKnowledgeType)
		
										
									}	
								});
							}


						});
						
						LUI.$('#listview [name="List_Selected"]').bind('click', function() { //点击列表某一项的checkbox
						
							if (!this.checked) {
								LUI('selectedBean').removeVal(this.value);
							} else {
								var values = LUI('selectedBean').getValues();
								if(values.length>0&&!${param.mulSelect }){
									LUI('selectedBean').removeValAll();
								}
								var val = getVal(this.value);
								if (val != null)
									selectLink(val.fdId, val.docSubject, val.fdKnowledgeType);
							}
						});
						
						$('#listview [name="List_Selected"]').each(function(){
							var val = getVal(this.value);
							var selectedIds = "${JsParam.fdSelectedIds}";
							if(selectedIds.indexOf(this.value) > -1){
									selectLink(val.fdId, val.docSubject, val.fdKnowledgeType);
							}
						});
					
						//首次刷新列表加载链接带入数据
						var refreshNum = $("[name='refreshNum']").val(); //此处放在列表刷新而不放在	$(function(){})中是因为selectedBean在$(function(){})运行时还没有加载出来
						if(refreshNum.indexOf("0")>-1){ 
		 		 				//var ele_id = GetQueryString("ele_id");
		 		 				//var ele_name = GetQueryString("ele_name");
		 		 				var top = Com_Parameter.top || window.top;
		 		 				var fdId = top.window.selectLearnLecturerId;
		 			 			var docSubject =top.window.selectLearnLecturerSubject;
		 			 			if(fdId){
		 			 				var id = fdId.split(";");
		 			 				var name = docSubject.split(";");
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
	 		
	 		window.defaultSelect = function() {
	 			var fdId = top.window.selectLearnLecturerId;
	 			var docSubject =top.window.selectLearnLecturerSubject;
	 			var fdIds;
	 			var docSubjects;
	 			if(fdId && docSubject){
	 				fdIds = fdId.split(";");
	 				docSubjects = docSubject.split(";");
	 				for(var i=0; i<fdIds.length;i++) {
		 				var data = {
	 							"id":fdIds[i],
	 							"name":htmlDecodeByRegExp(docSubjects[i]),
	 							"type":1
	 					};
	 					
	 					if (!LUI('selectedBean').hasVal(data)) {
	 						LUI('selectedBean').addVal(data)
	 					}
	 					$("input[value='"+fdIds[i]+"']").prop("checked",true);
		 			}
	 			}
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
					var box_len = $("input[name='List_Selected']:checked").length;
					if(box_len>7){
					
						$('#listview [name="List_Tongle"]').attr('checked', true);

					}else{
						var toggle = $("[name='toggle']").val();
						if($('#listview [name="List_Tongle"]').is(':checked')&&(toggle.indexOf("0")>-1)){
							$('#listview [name="List_Tongle"]').attr('checked', false);

						}

					}
					defaultSelect();
		 			
				}
	 				
	 				var contains = function(arr, item) {
	 					for(var i = 0; i < arr.length; i++) {
	 						if(item.value == arr[i].fdId) {
	 							return arr[i];
	 						}
	 					}
	 				}
	 				
	 				window.onSubmit =  function() {
						var mulSelect = ${param.mulSelect } ;
	 					var values = LUI('selectedBean').getValues();
	 					if(values.length>1&&!mulSelect){
							dialog.alert("${ lfn:message('kms-knowledge:kmsKnowledge.selectExcessKnowledge')}");
							return false;
	 					}else if(values.length<1){
							dialog.alert("${ lfn:message('kms-knowledge:kmsKnowledge.selectNoKnowledge')}");
							return false;
	 					}
	 					var rtn = [];
	 					
	 					//拼装fdName和fdId
	 					for(var i = 0; i < values.length; i++) {
		 					var data = {fdId:"",docSubject:"",fdKnowledgeType:""}; //不能放在括号外面，因为这样会造成每次data新值都会修改rtn里面的data
	 						data.fdId = values[i].id;
	 						data.docSubject  = values[i].name;
	 						data.fdKnowledgeType = values[i].type;
	 						rtn.push(data);
	 					}
						
	 					if(rtn.length>0){
	 						return rtn;
	 					}
	 			   	

	 				}
	 				

	 				
	 				<%-- 设置选中 --%>
	 				window.selectLink = function(id, docSubject, fdKnowledgeType) { 
	 					var data = {
	 							"id":id,
	 							"name":htmlDecodeByRegExp(docSubject),
	 							"type":fdKnowledgeType
	 					};
	 					if (LUI('selectedBean').hasVal(data)) {
							
	 						LUI('selectedBean').removeVal(data);
							refreshCheckbox();
	 						return;
	 					}
	 					
						var mulSelect = ${param.mulSelect }
	 					var values = LUI('selectedBean').getValues();
						if(values.length>0&&!mulSelect){
							dialog.alert("${ lfn:message('kms-knowledge:kmsKnowledge.selectExcessKnowledge')}");
							refreshCheckbox();
							return ;
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
	 				//用正则表达式实现html解码（反转义）
	 				window.htmlDecodeByRegExp=function (str){  
	 		             var temp = "";
	 		             if(str.length == 0) return "";
	 		             temp = str.replace(/&amp;/g,"&");
	 		             temp = temp.replace(/&lt;/g,"<");
	 		             temp = temp.replace(/&gt;/g,">");
	 		             temp = temp.replace(/&nbsp;/g," ");
	 		             temp = temp.replace(/&#39;/g,"\'");
	 		             temp = temp.replace(/&quot;/g,"\"");
	 		             return temp;  
	 		        }
	 			});
	 	</script>
	</template:replace>
</template:include>