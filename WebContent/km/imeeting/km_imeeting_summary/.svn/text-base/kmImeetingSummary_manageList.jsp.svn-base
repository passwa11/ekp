<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">  
		<list:criteria id="summaryCriteria">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject"> </list:cri-ref>
			<%-- 状态 --%>
			<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeetingSummary.docStatus') }" key="docStatus">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
								[{text:'${ lfn:message('status.draft')}', value:'10'},
								{text:'${ lfn:message('status.examine')}',value:'20'},
								{text:'${ lfn:message('status.refuse')}',value:'11'},
								{text:'${ lfn:message('status.discard')}',value:'00'},
								{text:'${ lfn:message('status.publish')}',value:'30'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<%-- 主持人、会议发起人、组织部门、召开时间 --%>
			<list:cri-auto modelName="com.landray.kmss.km.imeeting.model.KmImeetingSummary" property="docCreator;docCreateTime" />
		</list:criteria>
		
		<%-- 操作栏 --%>
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
						<list:sortgroup>
							<list:sort property="docCreateTime" text="${lfn:message('km-imeeting:kmImeetingSummary.docCreateTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="docPublishTime" text="${lfn:message('km-imeeting:kmImeetingSummary.docPublishTime') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="4" id="Btntoolbar">
					    <c:if test="${JsParam.categoryId==null || JsParam.nodeType=='CATEGORY'}">
							<kmss:authShow roles="ROLE_KMIMEETING_SUMMARY_CREATE">
							   <ui:button title="${lfn:message('button.add')}" text="${lfn:message('button.add')}" onclick="addDoc()" order="2" ></ui:button>
							</kmss:authShow>
						</c:if>
						<c:if test="${JsParam.nodeType=='TEMPLATE'}">	
							<kmss:auth
								requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=add&fdTemplateId=${JsParam.categoryId}&fdModelId=${JsParam.fdModelId}&fdModelName=${JsParam.fdModelName}"
								requestMethod="GET">
								 <ui:button title="${lfn:message('button.add')}" text="${lfn:message('button.add')}" onclick="Com_OpenWindow('${LUI_ContextPath }/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=add&fdTemplateId=${JsParam.categoryId}&fdProjectId=${JsParam.fdProjectId}&fdModelName=${JsParam.fdModelName}');" order="2" ></ui:button>
							</kmss:auth>
						</c:if>
						<kmss:auth
							requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=deleteall&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}"
							requestMethod="GET">
					    	<ui:button id="del" text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc()"></ui:button>
						</kmss:auth>
						<kmss:auth
							requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=changeTemplate&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}"
							requestMethod="GET">
							<ui:button id="chgCate" text="${lfn:message('km-imeeting:kmImeeting.btn.translate')}" order="4" onclick="chgSelect();"></ui:button>
						</kmss:auth>
						<%-- 收藏 --%> 
						<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
							<c:param name="fdTitleProName" value="fdName" />
							<c:param name="fdModelName"	value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
						</c:import>
						<%-- 修改权限 --%>
						<c:import url="/sys/right/import/cchange_doc_right_button.jsp" charEncoding="UTF-8">
							<c:param name="modelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
							<c:param name="authReaderNoteFlag" value="2" />
						</c:import>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		 
		<%-- 列表视图 --%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
						{url:'/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=manageList&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}&except=${JsParam.except}'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary" isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto></list:col-auto>
			</list:colTable>
		</list:listview>
	 	<list:paging></list:paging>
		<script type="text/javascript">
			var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary";
			seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/toolbar'], function($, strutil, dialog , topic ,toolbar) {
				var isFreshWithTemplate = false;
				LUI.ready(function(){
	              if(getValueByHash("fdTemplate")!=""){
	            	  isFreshWithTemplate  = true;
	                 }
				});
				//根据地址获取key对应的筛选值
				var getValueByHash=function(key){
					var hash = window.location.hash;
	                if(hash.indexOf(key)<0){
	                    return "";
	                }
	            	var url = hash.split("cri.q=")[1];
	  			    var reg = new RegExp("(^|;)"+ key +":([^;]*)(;|$)");
				    var r=url.match(reg);
					if(r!=null){
						return unescape(r[2]);
					}
					return "";
				};

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function(){
						seajs.use(['lui/jquery','lui/topic'], function($,topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
				
				//新建会议
		 		window.addDoc = function() {
						dialog.categoryForNewFile(
								'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
								'/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=add&fdTemplateId=!{id}',false,null,null,getValueByHash("fdTemplate"));
			 	};
				//删除
		 		window.delDoc = function(){
		 			var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),function(data){
								if(window.del_load!=null)
									window.del_load.hide();
								if(data!=null && data.status==true){
									topic.publish("list.refresh");
									dialog.success('<bean:message key="return.optSuccess" />');
								}else{
									dialog.failure('<bean:message key="return.optFailure" />');
								}
							},'json');
						}
					});
				};
				
				//分类转移
				window.chgSelect=function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var cfg={
						'modelName':'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
						'mulSelect':false,
						<%-- 如果用户有“分类权限扩充”角色，则允许转移到所有的分类 --%>
						<% 
							if(com.landray.kmss.util.UserUtil.checkRole("ROLE_KMIMEETING_OPTALL")) {
						%>
						'authType':'00',
						<%
							} else {
						%>
						'authType':'01',
						<%	
							}
						%>
						'action':function(value,____dialog){
							if(value.id){
								window.chg_load = dialog.loading();
								$.post('<c:url value="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=changeTemplate"/>',
										$.param({"List_Selected":values,"templateId":value.id},true),function(data){
									if(window.chg_load!=null)
										window.chg_load.hide();
									if(data!=null && data.status==true){
										topic.publish("list.refresh");
										dialog.success('<bean:message key="return.optSuccess" />');
									}else{
										dialog.failure('<bean:message key="return.optFailure" />');
									}
								},'json');
							}
						}
					};
					dialog.category(cfg);
				};

				/******************************************
				  * 验证权限并显示按钮 
				  * param：
				  *       categoryId 模板id
				  *       nodeType 模板类型
				  *****************************************/
				  var cateId= "";
		          var nodeType = ""; 
				  
				var showButtons = function(categoryId,nodeType){
					var checkDelUrl = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=deleteall&categoryId="+categoryId+"&nodeType="+nodeType;
					var checkChgCateUrl = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=changeTemplate&categoryId="+categoryId+"&nodeType="+nodeType;
					var changeRightBatchUrl = "/sys/right/cchange_doc_right/cchange_doc_right.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary&categoryId="+categoryId+"&nodeType="+nodeType;
					var data = new Array();
					data.push(["delBtn",checkDelUrl]);
					data.push(["changeRightBatchBtn",changeRightBatchUrl]);
					data.push(["chgcateBtn",checkChgCateUrl]);
					 $.ajax({
		       			  url: "${LUI_ContextPath}/sys/authorization/SysAuthUrlCheckAction.do?method=checkUrlAuth",
		       			  dataType:"json",
		       			  type:"post",
		       			  data:{"data":LUI.stringify(data)},
		       			  async:false,
		       			  success: function(rtn){
			       		 		for(var i=0;i<rtn.length;i++){
				                  if(rtn[i]['delBtn'] == 'true'){
				                	    if(LUI('del')){ LUI('Btntoolbar').removeButton(LUI('del'));}
				                 		var delBtn = toolbar.buildButton({id:'del',order:'3',text:'${lfn:message("button.deleteall")}',click:'delDoc()'});
				    					LUI('Btntoolbar').addButton(delBtn);
				                   }
				                  if(rtn[i]['changeRightBatchBtn'] == 'true'){
					                 	var changeRightBatchBtn = toolbar.buildButton({id:'docChangeRightBatch',order:'4',text:'${lfn:message("sys-right:right.button.changeRightBatch")}',click:'changeRightCheckSelect("'+categoryId+'","'+nodeType+'")'});
					    				LUI('Btntoolbar').addButton(changeRightBatchBtn);
					               }
				                  if(rtn[i]['chgcateBtn'] == 'true'){
					                 	var chgcateBtn = toolbar.buildButton({id:'chgCate',order:'5',text:'${lfn:message("km-imeeting:kmImeeting.btn.translate")}',click:'chgSelect()'});
					    				LUI('Btntoolbar').addButton(chgcateBtn);
					               }
			  		            }
		       			  }
		       		  });
				};
				
				<%
				   request.setAttribute("admin",UserUtil.getKMSSUser().isAdmin());
				%>
				
				topic.subscribe('criteria.changed',function(evt){
					if("${admin}"=="false"){
						if(LUI('del')){ LUI('Btntoolbar').removeButton(LUI('del'));LUI('del').destroy();}
						if(LUI('chgCate')){LUI('Btntoolbar').removeButton(LUI('chgCate'));LUI('chgCate').destroy();}
						if(LUI('docChangeRightBatch')){LUI('Btntoolbar').removeButton(LUI('docChangeRightBatch'));LUI('docChangeRightBatch').destroy();}
					}
					var hasCate = false;
					for(var i=0;i<evt['criterions'].length;i++){
						  //获取分类id和类型
		             	  if(evt['criterions'][i].key=="fdTemplate"){
		             		 hasCate = true;
		                 	 cateId= evt['criterions'][i].value[0];
			                 nodeType = evt['criterions'][i].nodeType;
		             	  }
						}
						//if(hasCate){
							//分类变化刷新
							showButtons("${JsParam.categoryId}","${JsParam.nodeType}");
						//}
						//筛选器全部清空的情况
						if(evt['criterions'].length==0){
							 showButtons("","");
						}
				});
				
			});
		</script>
	</template:replace>
</template:include>