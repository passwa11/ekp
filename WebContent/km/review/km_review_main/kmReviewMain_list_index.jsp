<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content"> 
		<list:criteria id="criteria1">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
			<%
				if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.review.model.KmReviewMain")){
			%>
			<list:cri-ref title="${ lfn:message('km-review:kmReviewMain.docCreateTime') }" key="partition" ref="criterion.sys.partition" modelName="com.landray.kmss.km.review.model.KmReviewMain" />
			<%
				}
			%>
			<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docStatus" multi="false"/>
			<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="fdNumber" />
			<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${lfn:message('km-review:kmReviewMain.docCreatorName') }" />
		   <%--当前处理人--%>
			<list:cri-ref ref="criterion.sys.postperson.availableAll"
						  key="fdCurrentHandler" multi="false"
						  title="${lfn:message('km-review:kmReviewMain.currentHandler') }" />
		   <%--已处理人--%>
			<list:cri-ref ref="criterion.sys.person"
						  key="fdAlreadyHandler" multi="false"
						  title="${lfn:message('km-review:kmReviewMain.fdAlreadyHandler') }" />
			<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" 
				property="fdDepartment" />
			<%
				if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.review.model.KmReviewMain") == false){
			%>
			<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docCreateTime"/>
			<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docPublishTime"/>
			<%
				}
			%>
			<%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
			<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="authArea"/>
			<%} %>
			<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docProperties"/>			
		</list:criteria>
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sortgroup>
							<list:sort property="docCreateTime" text="${lfn:message('km-review:kmReviewMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="docPublishTime" text="${lfn:message('km-review:kmReviewMain.docPublishTime') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar" count="10">
						<kmss:authShow roles="ROLE_KMREVIEW_CREATE">
						 	<ui:button text="${lfn:message('button.add')}" id="add" onclick="addDoc()" order="8" ></ui:button>
						</kmss:authShow>
						<%-- 分类转移 --%>
						<kmss:auth
								requestURL="/km/review/km_review_main/kmReviewMain.do?method=changeTemplate&categoryId=${param.categoryId}&nodeType=${param.nodeType}"
								requestMethod="GET">
							<ui:button id="chgCate" text="${lfn:message('km-review:button.translate')}" order="5" onclick="chgSelect();"></ui:button>
						</kmss:auth>
						<kmss:auth
							requestURL="/km/review/km_review_main/kmReviewMain.do?method=deleteall&categoryId=${param.categoryId}&nodeType=${param.nodeType}"
							requestMethod="GET">
						<ui:button id="del" text="${lfn:message('button.deleteall')}" order="8" onclick="delDoc()"></ui:button>
						</kmss:auth>
						<%-- 收藏 --%>
						<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
							<c:param name="fdTitleProName" value="docSubject" />
							<c:param name="fdModelName"	value="com.landray.kmss.km.review.model.KmReviewMain" />
						</c:import>
						<%-- 修改权限 --%>
						<c:import url="/sys/right/import/cchange_doc_right_button.jsp" charEncoding="UTF-8">
							<c:param name="modelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
							<c:param name="nodeType" value="${param.nodeType }"></c:param>
							<c:param name="categoryId" value="${param.categoryId}"></c:param>
							<c:param name="authReaderNoteFlag" value="2" />
						</c:import>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview>
			<ui:source type="AjaxJson">
					{url:'/km/review/km_review_main/kmReviewMain.do?method=manageList&categoryId=${param.categoryId}&nodeType=${param.nodeType}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="/km/review/km_review_main/kmReviewMain.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="docSubject;fdNumber;docCreator.fdName;docCreateTime_time;docPublishTime_time;docStatus;nodeName;handlerName"></list:col-auto> 
				<%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
				<list:col-column title="${ lfn:message('sys-authorization:sysAuthArea.authArea') }" property="authArea.fdName"></list:col-column>
				<%} %>
			</list:colTable>
		</list:listview> 
		<br>
	 	<list:paging></list:paging>
	 	<% 
	    request.setAttribute("isAdmin",UserUtil.getKMSSUser().isAdmin());
        %>
	 	<script type="text/javascript">
	 	
	 	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.review.model.KmReviewMain";
	 	seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/toolbar'], function($, strutil, dialog , topic,toolbar) {
		 	var isFreshWithTemplate = false;
		 	LUI.ready(function(){
	 		  // 初始化左则菜单样式
//         	  setTimeout('initMenuNav()', 300);
              if(getValueByHash("fdTemplate")!=""){
            	  isFreshWithTemplate  = true;
                 }else{
                     //初始化门户传递的category
                     var categoryId = "${JsParam.categoryId}";
                     if(categoryId == ""){
                         return;
                     }
                     var hash = window.location.hash;
                     if(hash == ""){
                   	   window.location.hash = "cri.q=fdTemplate:"+categoryId;
                     }else{
                   	   window.location.hash = hash + ";fdTemplate:"+categoryId;
                     }
                  }
			});
	 		//根据地址获取key对应的筛选值
            window.getValueByHash = function(key){
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
		 	//新建
	 		window.addDoc = function() {
					dialog.categoryForNewFile(
							'com.landray.kmss.km.review.model.KmReviewTemplate',
							'/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=!{id}',false,null,null,getValueByHash("fdTemplate"),null,null,true);
		 	};

		 	window.clearAllValue = function() {
		 		
			 	this.location = "${LUI_ContextPath}/km/review";
			};

			 var cateId= "";
             var nodeType = "";
             
             
           //删除文档
     		window.delDoc = function(draft){
     			var values = [];
     			$("input[name='List_Selected']:checked").each(function(){
     				values.push($(this).val());
     			});
     			if(values.length==0){
     				dialog.alert('<bean:message key="page.noSelect"/>');
     				return;
     			}
     			var url = '<c:url value="/km/review/km_review_main/kmReviewMain.do?method=deleteall"/>';
     			url = Com_SetUrlParameter(url, 'categoryId', "${param.categoryId}");
     			url = Com_SetUrlParameter(url, 'nodeType', "${param.nodeType}"); 
     			if(draft == '0'){
     				url = '<c:url value="/km/review/km_review_main/kmReviewMain.do?method=deleteall&status=10"/>';
     			}
     			var config = {
     				url : url, // 删除数据的URL
     				data : $.param({"List_Selected":values},true), // 要删除的数据
     				modelName : "com.landray.kmss.km.review.model.KmReviewMain" // 主要是判断此文档是否有部署软删除
     			};
     			
     			Com_Delete(config, delCallback);
     		};
     		
		 	
			window.delCallback = function(data){
				if(window.del_load!=null){
					window.del_load.hide(); 
					topic.publish("list.refresh");
				}
				dialog.result(data);
			};
			
			//分类转移
			window.chgSelect = function() {
				var values = "";
				$("input[name='List_Selected']:checked").each(function(){
					 values += "," + $(this).val();
					});
				if(values==''){
					dialog.alert('<bean:message bundle="km-review" key="message.trans_doc_select"/>');
					return;
				}
				values = values.substring(1);
				//var url = '/km/review/km_review_main/kmReviewChangeTemplate.jsp?values='+values+'&categoryId=${JsParam.categoryId}';
				//dialog.iframe(url,'<bean:message bundle="km-review" key="button.translate"/>',function(rtn){},{width:500,height:300});
				
				//Com_OpenWindow('<c:url value="/km/review/km_review_main/kmReviewChangeTemplate.jsp" />?values='+values+'&categoryId=${JsParam.categoryId}');
				//return ;
				var cfg={
						'modelName':'com.landray.kmss.km.review.model.KmReviewTemplate',
						'mulSelect':false,
						'authType':'01',
						'action':function(value,____dialog){
							if(value && value.id){
								window.chg_load = dialog.loading();
								$.post('<c:url value="/km/review/km_review_main/kmReviewMain.do?method=changeTemplate"/>',
										$.param({"values":values,"fdTemplateId":value.id},true),function(data){
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
			window.showButtons = function(categoryId,nodeType){
				  var checkChgCateUrl = "/km/review/km_review_main/kmReviewMain.do?method=changeTemplate&categoryId="+categoryId+"&nodeType="+nodeType;
				  var changeRightBatchUrl = "/sys/right/cchange_doc_right/cchange_doc_right.jsp?modelName=com.landray.kmss.km.review.model.KmReviewMain&categoryId="+categoryId+"&nodeType="+nodeType;
				  var checkDelUrl = "/km/review/km_review_main/kmReviewMain.do?method=deleteall&categoryId="+categoryId+"&nodeType="+nodeType;
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
				                 	var chgcateBtn = toolbar.buildButton({id:'chgCate',order:'5',text:'${lfn:message("km-review:button.translate")}',click:'chgSelect()'});
				    				LUI('Btntoolbar').addButton(chgcateBtn);
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
			   request.setAttribute("admin",UserUtil.getKMSSUser().isAdmin());
			%>
			
			//根据筛选器分类异步校验权限
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
						showButtons("${param.categoryId}","${param.nodeType}");
					//}
					//筛选器全部清空的情况
					if(evt['criterions'].length==0){
						 showButtons("","");
					}
			});
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			
			// 左则样式
			/* window.initMenuNav = function() {
		 		var templage = getValueByHash("fdTemplate");
		 		var mydoc = getValueByHash("mydoc");
		 		if(templage != "") {
		 			resetMenuNavStyle($("#menu_nav a[href*=" + templage + "]"));
		 		} else if(mydoc != "") {
		 			resetMenuNavStyle($("#_" + mydoc));
		 		} else {
		 			resetMenuNavStyle($("#_allflow"));
		 		}
		 	} */
			
	 	});
	 	</script>
	</template:replace>
</template:include>