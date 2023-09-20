<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%
	String modelName = "com.landray.kmss.km.imeeting.model.KmImeetingConfig";
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	Map map = sysAppConfigService.findByKey(modelName);
	request.setAttribute("kmImeetingConfig", map);
	request.setAttribute("useCloud", KmImeetingConfigUtil.isUseCloudMng());
%>

<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<%-- 筛选器 --%>
		<list:criteria id="attendCriteria">
			<list:tab-criterion title="" key="mymeeting">
				<list:box-select>
					<list:item-select id="type_for_myAttend"  cfg-required ="true"  type="lui/criteria/select_panel!TabCriterionSelectDatas">
						<ui:source type="Static">
							[{text:'${ lfn:message('km-imeeting:kmImeeting.Host.my') }', value:'myHost'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.Emcc.my') }', value:'myEmcc'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.Sum.my') }', value:'mySummary'}
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:tab-criterion>
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject"> </list:cri-ref>
			<%-- 分类导航 --%>
			<list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" title="${lfn:message('sys-category:menu.sysCategory.index') }" expand="false">
			  <list:varParams modelName="com.landray.kmss.km.imeeting.model.KmImeetingTemplate"/>
			</list:cri-ref>
			<%-- 会议状态 --%>
			<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeeting.examine') }" key="meetingStatus">
				<list:box-select>
					<list:item-select id="status_for_myCreate" cfg-enable="true">
						<ui:source type="Static">
							[
							{text:'${ lfn:message('km-imeeting:kmImeeting.status.draft') }', value:'10'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.status.append') }', value:'20'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.status.reject') }', value:'11'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish') }', value:'30'}
							
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeetingMain.docStatus') }" key="meetingStatus">
				<list:box-select>
					<list:item-select id="status_for_myAttend" type="lui/criteria/select_panel!TabCriterionSelectDatas">
						<ui:source type="Static">
							[{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish.unHold') }', value:'unHold'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish.holding') }', value:'holding'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish.hold') }', value:'hold'}
							,{text:'${ lfn:message('km-imeeting:kmImeeting.status.cancel') }', value:'41'}
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<%-- 主持人、会议发起人、组织部门、召开时间 --%>
			<list:cri-auto modelName="com.landray.kmss.km.imeeting.model.KmImeetingMain" property="fdHost;docCreator;docDept;fdHoldDate" />
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
					<list:sortgroup>
						<list:sort property="docCreateTime" text="${lfn:message('km-imeeting:kmImeetingMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="docPublishTime" text="${lfn:message('km-imeeting:kmImeetingMain.docPublishTime') }" group="sort.list"></list:sort>
						<list:sort property="fdHoldDate" text="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDate') }" group="sort.list"></list:sort>
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
						<c:if test="${useCloud}">
							<kmss:authShow roles="ROLE_KMIMEETING_CREATE_CLOUD">
								 <ui:button title="${lfn:message('km-imeeting:kmImeetingMain.isCloud') }" 
						       		text="${lfn:message('km-imeeting:kmImeetingMain.isCloud') }" 
						       		onclick="window.open('${LUI_ContextPath }/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&noTemplate=true')" order="2" ></ui:button>
							</kmss:authShow>
						</c:if>
						<kmss:authShow roles="ROLE_KMIMEETING_TRANSPORT_EXPORT">
						<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingMain')" order="2" ></ui:button>
					    </kmss:authShow>
					    <kmss:authShow roles="ROLE_KMIMEETING_CREATE">
					    	<% if(KmImeetingConfigUtil.isVideoMeetingEnable()){ %>
					    	<ui:button title="${lfn:message('km-imeeting:table.kmImeetingVideo') }"
					       		text="${lfn:message('km-imeeting:table.kmImeetingVideo') }"
					       		onclick="window.open('${LUI_ContextPath }/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&noTemplate=true')" order="2" >
					       	</ui:button>
					       	<%} %>
						   <ui:button title="${lfn:message('button.add')}" text="${lfn:message('button.add')}" onclick="addDoc()" order="2" ></ui:button>
						</kmss:authShow>
						<kmss:auth
							requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=deleteall&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}"
							requestMethod="GET">
					    	<ui:button id="del" text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc()" ></ui:button>
						</kmss:auth>
						<%-- 收藏 --%>
						<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
							<c:param name="fdTitleProName" value="fdName" />
							<c:param name="fdModelName"  value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
						</c:import>
						<c:import url="/sys/right/import/cchange_doc_right_button.jsp" charEncoding="UTF-8">
							<c:param name="modelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
							<c:param name="authReaderNoteFlag" value="2" />
						</c:import>
						<kmss:auth
							requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeTemplate&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}"
							requestMethod="GET">
							<ui:button id="chgCate" text="${lfn:message('km-imeeting:kmImeeting.btn.translate')}" order="5" onclick="chgSelect();"></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
	<ui:fixed elem=".lui_list_operation"></ui:fixed>
	<%-- 列表视图 --%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=listChildren&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}&except=${JsParam.except}'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.imeeting.model.KmImeetingMain" isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=!{fdId}&path=myHost" name="columntable">
				<list:col-checkbox name="List_Selected"></list:col-checkbox>
				<list:col-serial title="${ lfn:message('page.serial')}"></list:col-serial>
				<list:col-auto></list:col-auto>
			</list:colTable>
		</list:listview>
 	<list:paging  ></list:paging>
 	<script type="text/javascript">
 	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary";
	
	seajs.use([
		'lui/dialog',
		'lui/topic',
		'lui/jquery',
		'km/imeeting/resource/js/dateUtil',
		'lui/util/env',
		'lui/toolbar'
		], function(dialog,topic,$,dateUtil,env,toolbar) {
		
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				setTimeout(function(){
					if(typeof LUI('____calendar____imeeting')!='undefined'){
					LUI('____calendar____imeeting').refreshSchedules();
					}
				}, 100);
			});  
			
			//浏览器窗口变化时，重新渲染日历,避免日历点击事件窜位
			window.onresize = function(){
				setTimeout(function(){
					if(typeof LUI('____calendar____imeeting')!='undefined'){
					LUI('____calendar____imeeting').refreshSchedules();
					}
				}, 100);
			};

			//新建会议
	 		window.addDoc = function() {
					dialog.categoryForNewFile(
							'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
							'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{id}',false,null,null,'${JsParam.categoryId}');
		 	};

			//数据初始化
			window.transformData=function(datas){
				var main=datas.main;
				for(var key in main){
					for(var i=0;i<main[key].list.length;i++){
						var item=main[key].list[i];
						if(checkStatus(item)==-1){
							item.color=$('.meeting_calendar_label_unhold').css('background-color');
						}
						if(checkStatus(item)==0){
							item.color=$('.meeting_calendar_label_holding').css('background-color');
						}
						if(checkStatus(item)==1){
							item.color=$('.meeting_calendar_label_hold').css('background-color');
						}
					}
				}
				return datas;
			};

			//当前会议状态
			var checkStatus=function(item){
				var startDate=dateUtil.parseDate(item.start),endDate=dateUtil.parseDate(item.end);
				var now=new Date(parseFloat('${now}'));
				//未召开
				if(now.getTime()<startDate.getTime()){
					return -1;
				}
				//进行中
				if(now.getTime()>=startDate.getTime() && now.getTime()<=endDate.getTime()){
					return 0;
				}
				//已召开
				if(now.getTime()>endDate.getTime()){
					return 1;
				}
			};
		
			//定位
			var getPos=function(evt,obj){
				var sWidth=obj.width();var sHeight=obj.height();
				x=evt.pageX;
				y=evt.pageY;
				if(y+sHeight>$(window).height()){
					y-=sHeight;
				}
				if(x+sWidth>$(document.body).outerWidth(true)){
					x-=sWidth;
				}
				return {"top":y,"left":x};
			};
			
			//新建
			topic.subscribe('calendar.select',function(arg){
				$('.meeting_calendar_dialog').hide();
				var addDialog=$('#meeting_calendar_add');
				//时间格式2014-7-11~2014-7-12
				var date=dateUtil.formatDate(dateUtil.parseDate(arg.start),'${dateFormatter}');
				if(arg.start!=arg.end){
					date+="~"+arg.end;
				}
				var start = dateUtil.formatDate( dateUtil.parseDate(arg.start,'YYYY-MM-hh'),'${dateFormatter}'),
					end = dateUtil.formatDate( dateUtil.parseDate(arg.end,'YYYY-MM-hh'),'${dateFormatter}');
				addDialog.find('.date').html(date);//时间字符串
				addDialog.find('.fdHoldDate').html(start);//召开时间
				addDialog.find('.fdFinishDate').html(end);//结束时间
				addDialog.find('.resId').html(arg.resId);//地点ID
				addDialog.find('.resName').html(env.fn.formatText(arg.resName) );//地点
				addDialog.css(getPos(arg.evt,addDialog)).fadeIn("fast");
				var nowdate = dateUtil.formatDate(new Date(),'${dateFormatter}');
				var startDate = dateUtil.formatDate(dateUtil.parseDate(arg.start),'${dateFormatter}');
				if(nowdate>startDate){
					$('#book_add_btn').hide();
					$('#meeting_add_btn').hide();
					$('#video_add_btn').hide();
				}else{
					$('#book_add_btn').show();
					$('#meeting_add_btn').show();
					$('#video_add_btn').show();
				}
			});

			//查看
			topic.subscribe('calendar.thing.click',function(arg){
				$('.meeting_calendar_dialog').hide();
				var viewDialog;//弹出框
				if(arg.data.type =="book"){
					viewDialog=$("#meeting_calendar_bookview");//会议室预约弹出框
					viewDialog.find(".fdRemark").html(env.fn.formatText( textEllipsis(arg.data.fdRemark) ));//备注
				}else{
					viewDialog=$("#meeting_calendar_mainview");//会议安排弹出框
					viewDialog.find(".fdHost").html(arg.data.fdHost);//主持人
				}
				//时间格式2014-7-11~2014-7-12
				var date=dateUtil.formatDate(arg.data.start,"${dateTimeFormatter}");
				if(arg.data.start!=arg.data.end){
					date+=" ~ "+dateUtil.formatDate(arg.data.end,"${dateTimeFormatter}");
				}
				viewDialog.find(".fdId").html(arg.data.fdId);//fdId
				viewDialog.find(".fdName").html( env.fn.formatText(arg.data.title) );//会议题目
				viewDialog.find(".fdPlace").html( env.fn.formatText(arg.data.fdPlaceName) );//地点
				viewDialog.find(".fdHoldDate").html(date);//召开时间

				var creator=arg.data.creator;
				if(arg.data.dept){
					creator+="("+arg.data.dept+")";//（部门）
				}
				viewDialog.find(".docCreator").html(creator);//人员（部门）
				
				if(arg.data.type=="book"){//会议预约按钮权限检测
					$('#book_delete_btn,#book_edit_btn').hide();
					$.ajax({
						url: "${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=checkAuth",
						type: 'POST',
						dataType: 'json',
						data: {fdId: arg.data.fdId},
						success: function(data, textStatus, xhr) {//操作成功
							if(data.canEdit){
								$('#book_edit_btn').show();
							}
							if(data.canDelete){
								$('#book_delete_btn').show();
							}
						}
					});
				}else{//会议安排查看按钮权限检测
					$('#meeting_view_btn').hide();
					$.ajax({
						url: "${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=checkViewAuth",
						type: 'POST',
						dataType: 'json',
						data: {fdId: arg.data.fdId},
						success: function(data, textStatus, xhr) {//操作成功
							if(data.canView){
								$('#meeting_view_btn').show();
							}
						}
					});
				}
				var today = new Date();
				if($('#book_change_btn').length > 0 ){
					if(today.getTime() > arg.data.start.getTime() || '${userId}' != arg.data.creatorId){
						$('#book_change_btn').hide();
					}else{
						$('#book_change_btn').show();
					}
				}
				viewDialog.find(".type").html(arg.data.type);
				viewDialog.css(getPos(arg.evt,viewDialog)).fadeIn("fast");
			});
			
		 	var cateId= '', nodeType = '';
		 	window.delDoc = function(draft){
				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				var url = '${LUI_ContextPath }/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=deleteall';
				url = Com_SetUrlParameter(url, 'categoryId', cateId);
				url = Com_SetUrlParameter(url, 'nodeType', nodeType); 
				if(draft == '0'){
					url = '${LUI_ContextPath }/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=deleteall&status=10';
				}
				var config = {
					url : url, // 删除数据的URL
					data : $.param({"List_Selected":values},true), // 要删除的数据
					modelName : "com.landray.kmss.km.imeeting.model.KmImeetingMain" // 主要是判断此文档是否有部署软删除
				};
				// 通用删除方法
				function delCallback(data){
					topic.publish("list.refresh");
					dialog.result(data);
				}
				Com_Delete(config, delCallback);
			};
			
/* 			//删除
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
						$.post('<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=deleteall"/>',
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
			}; */
			
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
						if(value && value.id){
							window.chg_load = dialog.loading();
							$.post('<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeTemplate"/>',
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
			
			//字符串截取
			function textEllipsis(text){
				text = text || '';
				if(text && text.length>200){
					text=text.substring(0,200)+"......";
				}
				return text;
			}
							var AuthCache = {};
				window.showButtons = function(cateId,nodeType){
					 if(AuthCache[cateId]){
			             if(AuthCache[cateId].delBtn){
			            	 if(!LUI('del')){ 
				            	 var delBtn = toolbar.buildButton({id:'del',order:'2',text:'${lfn:message("button.deleteall")}',click:'delDoc()'});
		    					 LUI('Btntoolbar').addButton(delBtn);
			            	 }
			             }else{
			            	 if(LUI('del')){ 
			            	   LUI('Btntoolbar').removeButton(LUI('del'));
			            	   LUI('del').destroy();
			            	 }
				         }
			             if(AuthCache[cateId].chgCateBtn){
			            	 if(!LUI('chgCate')){ 
				            	 var chgCateBtn = toolbar.buildButton({id:'chgCate',order:'2',text:'${lfn:message("km-imeeting:kmImeeting.btn.translate")}',click:'chgSelect()'});
		    					 LUI('Btntoolbar').addButton(chgCateBtn);
			            	 }
			             }else{
			            	 if(LUI('chgCate')){ 
			            	   LUI('Btntoolbar').removeButton(LUI('chgCate'));
			            	   LUI('chgCate').destroy();
			            	 }
				         }
			             //批量修改权限按钮
			             if(AuthCache[cateId].changeRightBatchBtn){
			            	 if(!LUI('docChangeRightBatch')){ 
				                 var changeRightBatchBtn = toolbar.buildButton({id:'docChangeRightBatch',order:'4',text:'${lfn:message("sys-right:right.button.changeRightBatch")}',click:'changeRightCheckSelect("'+cateId+'","'+nodeType+'")'});
		    					 LUI('Btntoolbar').addButton(changeRightBatchBtn);
			            	 }
			             }else{
			            	 if(LUI('docChangeRightBatch')){ 
			            	   LUI('Btntoolbar').removeButton(LUI('docChangeRightBatch'));
			            	   LUI('docChangeRightBatch').destroy();
			            	 }
				         }
		             }
		             if(AuthCache[cateId]==null){
		            	 var checkChgCateUrl = "/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeTemplate&categoryId="+cateId+"&nodeType="+nodeType;
			                 var checkDelUrl = "/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=deleteall&categoryId="+cateId+"&nodeType="+nodeType;
							 var changeRightBatchUrl = "/sys/right/cchange_doc_right/cchange_doc_right.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingMain&categoryId="+cateId+"&nodeType="+nodeType;
			                 var data = new Array();
			                 data.push(["delBtn",checkDelUrl]);
			                 data.push(["chgCateBtn",checkChgCateUrl]);
			                 data.push(["changeRightBatchBtn",changeRightBatchUrl]);
			                 $.ajax({
			       			  url: "${LUI_ContextPath}/sys/authorization/SysAuthUrlCheckAction.do?method=checkUrlAuth",
			       			  dataType:"json",
			       			  type:"post",
			       			  data:{"data":LUI.stringify(data)},
			       			  async:false,
			       			  success: function(rtn){
				       			var btnInfo = {};
				       			if(rtn.length > 0){
			       				  for(var i=0;i<rtn.length;i++){
			                 		if(rtn[i]['delBtn'] == 'true'){
			                 		btnInfo.delBtn = true;
			                 		 if(!LUI('del')){ 
				                 		 var delBtn = toolbar.buildButton({id:'del',order:'2',text:'${lfn:message("button.deleteall")}',click:'delDoc()'});
				    					 LUI('Btntoolbar').addButton(delBtn);
			                 		 }
			                       }
			                 		if(rtn[i]['chgCateBtn'] == 'true'){
				                 		btnInfo.chgCateBtn = true;
				                 		 if(!LUI('chgCate')){ 
				                 			 var chgCateBtn = toolbar.buildButton({id:'chgCate',order:'2',text:'${lfn:message("km-imeeting:kmImeeting.btn.translate")}',click:'chgSelect()'});
					    					 LUI('Btntoolbar').addButton(chgCateBtn);
				                 		 }
				                       }
			                 		if(rtn[i]['changeRightBatchBtn'] == 'true'){
				                 		btnInfo.changeRightBatchBtn = true;
				                 		 if(!LUI('docChangeRightBatch')){ 
							                 var changeRightBatchBtn = toolbar.buildButton({id:'docChangeRightBatch',order:'4',text:'${lfn:message("sys-right:right.button.changeRightBatch")}',click:'changeRightCheckSelect("'+cateId+'","'+nodeType+'")'});
					    					 LUI('Btntoolbar').addButton(changeRightBatchBtn);
				                 		 }
				                     } 
			       				  }
				       			}else{
		                    	   btnInfo.delBtn = false;
		                    	   btnInfo.chgCateBtn = false;
		                    	  if(LUI('del')){ 
		                    	    LUI('Btntoolbar').removeButton(LUI('del'));
		                    	    LUI('del').destroy();
		                    	  }
		                    	  if(LUI('chgCate')){ 
			                    	    LUI('Btntoolbar').removeButton(LUI('chgCate'));
			                    	    LUI('chgCate').destroy();
			                      }
		                    	  btnInfo.changeRightBatchBtn = false;
		                    	  if(LUI('docChangeRightBatch')){ 
		                    	    LUI('Btntoolbar').removeButton(LUI('docChangeRightBatch'));
		                    	    LUI('docChangeRightBatch').destroy();
		                    	  }
				                }
			                 	 AuthCache[cateId] = btnInfo;
			  		          }
		                  });
		               }
	            };
		        window.removeDelBtn = function(){
					if(LUI('del')){
	            	    LUI('Btntoolbar').removeButton(LUI('del'));
	            	    LUI('del').destroy();
	            	   }
				};
				 window.removeChgCateBtn = function(){
						if(LUI('chgCate')){
	              	    LUI('Btntoolbar').removeButton(LUI('chgCate'));
	              	    LUI('chgCate').destroy();
	              	   }
				};
				window.removeChangeRightBatchBtn = function(){
					if(LUI('docChangeRightBatch')){
	             	    LUI('Btntoolbar').removeButton(LUI('docChangeRightBatch'));
	             	    LUI('docChangeRightBatch').destroy();
	             	   }
			    };
				
				<%
				   request.setAttribute("admin",UserUtil.getKMSSUser().isAdmin());
				%>
				
				//根据筛选器分类异步校验权限
				topic.subscribe('criteria.spa.changed',function(evt){
					if("${admin}"=="false"){
						 removeDelBtn();
						 removeChgCateBtn();
						 removeChangeRightBatchBtn();
					}
					//removeShowNumberBtn();
					var hasTemp = false;    //筛选器中是否包含模板筛选项
					//筛选器变化时清空分类和节点类型的值
					cateId = "";  
					nodeType = "";
					for(var i=0;i<evt['criterions'].length;i++){
					  //获取分类id和类型
	             	  if(evt['criterions'][i].key=="fdTemplate"){
	                 	 cateId= evt['criterions'][i].value[0];
		                 nodeType = evt['criterions'][i].nodeType;
		                 hasTemp = true;
	             	  }
					}
						 //分类变化或者带有分类刷新
		                showButtons(cateId,nodeType);
					//筛选器全部清空的情况
					/* if(evt['criterions'].length==0){
						 showButtons("","");
					} */
				});
				
			
	});
 	</script>
</template:replace>
</template:include>	
