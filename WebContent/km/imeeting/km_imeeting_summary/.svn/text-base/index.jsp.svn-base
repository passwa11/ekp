<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list">
	<%-- 标签页标题 --%>
	<template:replace name="title">
		<c:out value="${lfn:message('km-imeeting:module.km.imeeting')}"></c:out>
	</template:replace>
	
	<%-- 导航路径 --%>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }" href="/km/imeeting/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:kmImeeting.tree.summary') }" href="/km/imeeting/km_imeeting_summary/index.jsp" target="_self">
				<ui:menu-item text="${ lfn:message('km-imeeting:kmImeeting.tree.calender') }" href="/km/imeeting/index.jsp" target="_self"></ui:menu-item>
				<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingStat') }" href="/km/imeeting/km_imeeting_stat/index.jsp?stat_key=dept.stat" target="_self"></ui:menu-item>
			   	<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingMain') }" href="/km/imeeting/km_imeeting_main/index.jsp" target="_self"></ui:menu-item>
			</ui:menu-item>
			<ui:menu-source autoFetch="true"  target="_self"  href="/km/imeeting/km_imeeting_summary/index.jsp?categoryId=!{value}">
				<ui:source type="AjaxJson">
					{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&categoryId=${JsParam.categoryId }&currId=!{value}&nodeType=!{nodeType}"} 
				</ui:source>
			</ui:menu-source>
		</ui:menu>
	</template:replace>
	
	<%-- 左侧导航栏 --%>
	<template:replace name="nav">
		
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('km-imeeting:kmImeeting.tree.title') }"></ui:varParam>
			<%-- 会议导航 --%>
			<ui:varParam name="button">
				[
				   <kmss:authShow roles="ROLE_KMIMEETING_SUMMARY_CREATE">
					{
						"text": "${lfn:message('km-imeeting:kmImeeting.btn.add.summary') }",
						"href":"javascript:addDoc();",
						"icon": "lui_icon_l_icon_36"
					}
					</kmss:authShow>
				]
			</ui:varParam>
		</ui:combin>
		
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/km/imeeting/import/nav.jsp" charEncoding="UTF-8">
					<c:param name="key" value="summary"></c:param>
				   	<c:param name="criteria" value="summaryCriteria"></c:param>
				</c:import>
			</ui:accordionpanel>
		</div>
	</template:replace>
	
	<%-- 右侧内容区域 --%>
	<template:replace name="content"> 
	
		<%-- 筛选器 --%>
		<list:criteria id="summaryCriteria">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject"> </list:cri-ref>
			<%-- 分类导航 --%>
			<list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" title="${lfn:message('sys-category:menu.sysCategory.index') }" expand="false">
			  <list:varParams modelName="com.landray.kmss.km.imeeting.model.KmImeetingTemplate"/>
			</list:cri-ref>
			<%-- 我的纪要会议 --%>
			<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeeting.tree.mySummary') }" key="mysummary" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('km-imeeting:kmImeeting.summary.myCreate') }', value:'myCreate'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.summary.myApproval') }',value:'myApproval'}
							, {text:'${ lfn:message('km-imeeting:kmImeeting.summary.myApproved') }', value: 'myApproved'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<%-- 状态 --%>
			<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeetingSummary.docStatus') }" key="docStatus">
				<list:box-select>
					<list:item-select cfg-defaultValue="30">
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
					<ui:toolbar count="3" id="Btntoolbar">
					    <kmss:authShow roles="ROLE_KMIMEETING_SUMMARY_CREATE">
						   <ui:button title="${lfn:message('button.add')}" text="${lfn:message('button.add')}" onclick="addDoc()" order="2" ></ui:button>
						</kmss:authShow>
						<kmss:authShow roles="ROLE_KMIMEETING_TRANSPORT_EXPORT">
						<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary')" order="2" ></ui:button>
					    </kmss:authShow>
						<kmss:auth
							requestURL="/km/meeting/km_imeeting_summary/kmImeetingSummary.do?method=deleteall&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}"
							requestMethod="GET">
					    	<ui:button id="del" text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc()"></ui:button>
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
						<kmss:auth
							requestURL="/km/meeting/km_imeeting_summary/kmImeetingSummary.do?method=changeTemplate&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}"
							requestMethod="GET">
							<ui:button id="chgCate" text="${lfn:message('km-imeeting:kmImeeting.btn.translate')}" order="4" onclick="chgSelect();"></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<%-- 列表视图 --%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
						{url:'/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=listChildren&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary" isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox name="List_Selected"></list:col-checkbox>
				<list:col-serial title="${ lfn:message('page.serial')}"></list:col-serial>
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
				var showButtons = function(categoryId,nodeType){
					var checkDelUrl = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=deleteall&categoryId="+categoryId+"&nodeType="+nodeType;
					var checkChgCateUrl = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=changeTemplate&categoryId="+categoryId+"&nodeType="+nodeType;
					var changeRightBatchUrl = "/sys/right/cchange_doc_right/cchange_doc_right.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary&categoryId="+categoryId+"&nodeType="+nodeType;
					var data = new Array();
					data.push(["delBtn",checkDelUrl]);
					data.push(["chgcateBtn",checkChgCateUrl]);
					data.push(["changeRightBatchBtn",changeRightBatchUrl]);
					 $.ajax({
		       			  url: "${LUI_ContextPath}/sys/authorization/SysAuthUrlCheckAction.do?method=checkUrlAuth",
		       			  dataType:"json",
		       			  type:"post",
		       			  data:{"data":LUI.stringify(data)},
		       			  async:false,
		       			  success: function(rtn){
			       		 		for(var i=0;i<rtn.length;i++){
				                  if(rtn[i]['delBtn'] == 'true'){
				                	  if(!LUI('del')){
				                 		var delBtn = toolbar.buildButton({id:'del',order:'3',text:'${lfn:message("button.delete")}',click:'delDoc()'});
				    					LUI('Btntoolbar').addButton(delBtn);
				                	  }
				                   }
				                   if(rtn[i]['chgcateBtn'] == 'true'){
				                	   if(!LUI('chgCate')){
					                 	var chgcateBtn = toolbar.buildButton({id:'chgCate',order:'5',text:'${lfn:message("km-imeeting:kmImeeting.btn.translate")}',click:'chgSelect()'});
					    				LUI('Btntoolbar').addButton(chgcateBtn);
					    				LUI('Btntoolbar').on('redrawButton',function() {
					                 		chgcateBtn.refreshSatus();
					                 	});
				                	   }
					               }
				                   if(rtn[i]['changeRightBatchBtn'] == 'true'){
				                	   if(!LUI('docChangeRightBatch')){
					                 	var changeRightBatchBtn = toolbar.buildButton({id:'docChangeRightBatch',order:'4',text:'${lfn:message("sys-right:right.button.changeRightBatch")}',click:'changeRightCheckSelect("'+categoryId+'","'+nodeType+'")'});
					    				LUI('Btntoolbar').addButton(changeRightBatchBtn);
				                	   }
					               }
			  		            }
		       			  }
		       		  });
				};
				 <%
				   request.setAttribute("admin",com.landray.kmss.util.UserUtil.getKMSSUser().isAdmin());
				%>
				//根据筛选器分类异步校验权限
				topic.subscribe('criteria.changed',function(evt){
					if("${admin}"=="false"){
						if(LUI('del')){ LUI('Btntoolbar').removeButton(LUI('del'));LUI('del').destroy();}
						if(LUI('chgCate')){ LUI('Btntoolbar').removeButton(LUI('chgCate'));LUI('chgCate').destroy();}
						if(LUI('docChangeRightBatch')){LUI('Btntoolbar').removeButton(LUI('docChangeRightBatch'));LUI('docChangeRightBatch').destroy();}
					}
					var hasCate = false;
					for(var i=0;i<evt['criterions'].length;i++){
					  //获取分类id和类型
	             	  if(evt['criterions'][i].key=="fdTemplate"){
	             		 hasCate = true;
	                 	 var cateId= evt['criterions'][i].value[0];
		                 var nodeType = evt['criterions'][i].nodeType;
		                 //分类变化或者带有分类刷新
		                 if(getValueByHash("fdTemplate")!=cateId || isFreshWithTemplate == true){
		                	showButtons(cateId,nodeType);
		                 }
	             	  }
	             	  if(evt['criterions'][i].key=="docStatus" && evt['criterions'][i].value.length==1) {
							if(evt['criterions'][i].value[0]=='10') {
								if(LUI('del')){ LUI('Btntoolbar').removeButton(LUI('del'));}
								var delBtn = toolbar.buildButton({id:'del',order:'3',text:'${lfn:message("button.delete")}',click:'delDoc()'});
		    					LUI('Btntoolbar').addButton(delBtn);
							}
					  }
					}
	                //清空模板,校验无分类情况
					if(hasCate == false){
						showButtons("","");
					}
	                isFreshWithTemplate = false;
				});
				
			});
		</script>
	</template:replace>
</template:include>