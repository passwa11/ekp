<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
	  <list:criteria id="sendCriteria">
	  		 <list:tab-criterion title="" key="docStatus"> 
				<list:box-select>
					<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas">
						<ui:source type="Static">
							[{text:'${ lfn:message('status.draft')}', value:'10'},
							 {text:'${ lfn:message('status.examine')}',value:'20'},
							 {text:'${ lfn:message('status.refuse')}',value:'11'},
							 {text:'${ lfn:message('status.publish')}',value:'30'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:tab-criterion>
	        <list:cri-ref key="docSubject" ref="criterion.sys.docSubject"> 
			</list:cri-ref>
			<list:cri-ref ref="criterion.sys.simpleCategory" key="fdTopicCategory" multi="false" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdTopicCategory') }" expand="false">
			  <list:varParams modelName="com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory"/>
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.km.imeeting.model.KmImeetingTopic" property="docCreateTime;fdIsAccept" />
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
									<list:sort property="docCreateTime" text="${lfn:message('km-imeeting:kmImeetingTopic.docCreateTime')}" group="sort.list" value="down"></list:sort>
								    <list:sort property="fdNo" text="${lfn:message('km-imeeting:kmImeetingTopic.fdNo')}" group="sort.list"></list:sort>
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
							<c:if test="${not empty JsParam.fdTemplateId}">
								<kmss:auth requestURL="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=add&fdTemplateId=${JsParam.fdTemplateId}">
								   <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>
								</kmss:auth>
							</c:if>
							<c:if test="${empty JsParam.docCategoryId}">
								<kmss:auth requestURL="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=add">
							    	<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>
								</kmss:auth>
							</c:if>
							<kmss:authShow roles="ROLE_KMIMEETING_TOPIC_TRANSPORT_EXPORT">
								<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingTopic')" order="2" ></ui:button>
							</kmss:authShow>
							<kmss:auth requestURL="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=deleteall">
							  <ui:button id="del" text="${lfn:message('button.deleteall')}" order="3" onclick="delDoc();"></ui:button>
							</kmss:auth>
						</ui:toolbar>
				  </div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview_send">
			<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=list'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.imeeting.model.KmImeetingTopic"   isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
		</list:listview> 
	 	<list:paging></list:paging>	 
	 	<script type="text/javascript">
	 	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary";
		
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/toolbar'], function($, strutil, dialog , topic ,toolbar) {
			
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			
			
			var docCategoryId = '';
			
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
	             }
	             if(AuthCache[cateId]==null){
	                 var checkDelUrl = "/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=deleteall&categoryId="+cateId+"&nodeType="+nodeType;
	                 var data = new Array();
	                 data.push(["delBtn",checkDelUrl]);
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
	       				  }
		       			}else{
                    	   btnInfo.delBtn = false;
                    	  if(LUI('del')){ 
                    	    LUI('Btntoolbar').removeButton(LUI('del'));
                    	    LUI('del').destroy();
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
			
			//根据筛选器分类异步校验权限
			topic.subscribe('criteria.spa.changed',function(evt){
				if("${admin}"=="false"){
					 removeDelBtn();
				}
				var hasTemp = false;    //筛选器中是否包含模板筛选项
				var docStatus = "";    //记录筛选器中状态筛选项的值
				//筛选器变化时清空分类和节点类型的值
				docCategoryId = "";  
				nodeType = "";
				for(var i=0;i<evt['criterions'].length;i++){
				  //获取分类id和类型
             	  if(evt['criterions'][i].key=="fdTopicCategory"){
             		 docCategoryId= evt['criterions'][i].value[0];
	                 nodeType = evt['criterions'][i].nodeType;
	                 hasTemp = true;
             	  }
				}
				//if(hasTemp){
					 //分类变化或者带有分类刷新
	                //showButtons(docCategoryId,nodeType);
				//}
				showButtons(docCategoryId,nodeType);
			});
	 		//新建
			window.addDoc = function() {
				dialog.simpleCategoryForNewFile(
						'com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory',
						'/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=add&fdTemplateId=!{id}',false,null,null,null);
			};
			//删除回调	
			window.delCallback = function(data){
				if(window.del_load!=null)
					window.del_load.hide();
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				}else{
					dialog.failure('<bean:message key="return.optFailure" />');
				}
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
				var config = {
						url : '${LUI_ContextPath}/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=deleteall&categoryId='+docCategoryId, // 删除数据的URL
						data : $.param({"List_Selected":values},true), // 要删除的数据
						modelName : "com.landray.kmss.km.imeeting.model.KmImeetingTopic"
					};
				// 通用删除方法
				Com_Delete(config, delCallback);
			};
		});
		</script>
	</template:replace>	 
</template:include>