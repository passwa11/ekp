<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/listview.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<%-- 筛选器 --%>
		<list:criteria id="summaryCriteria">
		
			<%-- 状态 --%>
		<list:tab-criterion title="" key="docStatus">
				<list:box-select>
					<list:item-select cfg-required ="${(JsParam.required==null)?'':(JsParam.required)}" cfg-defaultValue ="${(JsParam.defaultValue==null)?'':(JsParam.defaultValue)}" cfg-enable="true" type="lui/criteria/select_panel!TabCriterionSelectDatas"  cfg-if="(param['mysummary'] == 'myCreate')">
						<ui:source type="Static">
								[
								{text:'${ lfn:message('status.draft')}', value:'10'},
								{text:'${ lfn:message('status.examine')}',value:'20'},
								{text:'${ lfn:message('status.refuse')}',value:'11'},
								{text:'${ lfn:message('status.publish')}',value:'30'}
								]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:tab-criterion>
			<list:tab-criterion title="" key="docStatus">
				<list:box-select>
					<list:item-select cfg-required ="${(JsParam.required==null)?'':(JsParam.required)}" cfg-defaultValue ="${(JsParam.defaultValue==null)?'':(JsParam.defaultValue)}" cfg-enable="true" type="lui/criteria/select_panel!TabCriterionSelectDatas"  cfg-if="(param['mysummary'] == 'myApproved')">
						<ui:source type="Static">
								[
								{text:'${ lfn:message('status.refuse')}',value:'11'},
								{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish') }', value:'30'}
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
						   <ui:button title="${lfn:message('button.add')}" text="${lfn:message('button.add')}" onclick="addDoc()" order="2" cfg-if="param['j_path']!='/abandom/mySummary'"></ui:button>
						</kmss:authShow>
						<kmss:authShow roles="ROLE_KMIMEETING_TRANSPORT_EXPORT">
						<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary')" order="2" ></ui:button>
					    </kmss:authShow>
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
						{url:'/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=listChildren&categoryId=!{categoryId}&nodeType=!{nodeType}'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary" isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox name="List_Selected"></list:col-checkbox>
				<list:col-serial title="${ lfn:message('page.serial')}"></list:col-serial>
				<list:col-auto></list:col-auto>
			</list:colTable>
		</list:listview>
 	<list:paging  ></list:paging>
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
			var url = '${LUI_ContextPath }/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=deleteall';
			url = Com_SetUrlParameter(url, 'categoryId', cateId);
			url = Com_SetUrlParameter(url, 'nodeType', nodeType); 
			if(draft == '0'){
				url = '${LUI_ContextPath }/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=deleteall&status=10';
			}
			var config = {
				url : url, // 删除数据的URL
				data : $.param({"List_Selected":values},true), // 要删除的数据
				modelName : "com.landray.kmss.km.imeeting.model.KmImeetingSummary" // 主要是判断此文档是否有部署软删除
			};
			// 通用删除方法
			function delCallback(data){
				topic.publish("list.refresh");
				dialog.result(data);
			}
			Com_Delete(config, delCallback);
		};
	 	
/* 		//删除
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
		            	 var checkChgCateUrl = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=changeTemplate&categoryId="+cateId+"&nodeType="+nodeType;
			                 var checkDelUrl = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=deleteall&categoryId="+cateId+"&nodeType="+nodeType;
							 var changeRightBatchUrl = "/sys/right/cchange_doc_right/cchange_doc_right.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary&categoryId="+cateId+"&nodeType="+nodeType;
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
		                showButtons(cateId,nodeType);
					//筛选器全部清空的情况
					if(evt['criterions'].length==0){
						 showButtons("","");
					}
				});
				
		
		
	});
 	</script>
</template:replace>
</template:include>	
