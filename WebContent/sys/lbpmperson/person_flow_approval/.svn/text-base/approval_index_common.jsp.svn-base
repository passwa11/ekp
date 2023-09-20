<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<list:criteria channel="channel_common_approve"  expand="false"
	multi="false">
		<%---
		<c:if test="${!param.fastop eq 'true' }">
		<list:cri-criterion
			title="${ lfn:message('sys-lbpmperson:lbpmperson.op.fastreview.criterion') }"
			key="fastop">
			<list:box-select>
				<list:item-select cfg-enable="true">
					<ui:source type="Static">
											[
												{text:'${ lfn:message('sys-lbpmperson:lbpmperson.op.all')}',value:'true'}
											]
										</ui:source>
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
	</c:if>
	 --%>
	<%--模块--%>
	<list:cri-criterion
		title="${lfn:message('sys-lbpmperson:lbpmperson.flow.order.module')}"
		key="fdModelName" multi="false" expand="true">
		<list:box-select>
			<list:item-select>
				<ui:source type="AjaxJson">
												{url:'/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=getModule'}
											</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" />

	<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false"
		title="${lfn:message('sys-lbpmperson:lbpmperson.person.creator') }" />


	<%--创建时间--%>
	<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
		title="${lfn:message('sys-lbpmperson:lbpmperson.flow.docAuthorTime') }" />
	<%--送审时间--%>
	<list:cri-ref ref="criterion.sys.calendar" key="arrivalTime"
	  	title="${lfn:message('sys-lbpmperson:lbpmperson.person.arrivalTime') }" />

</list:criteria>
<!-- 排序 -->
<div class="lui_list_operation">
	<!-- 全选 -->
	<div class="lui_list_operation_order_btn">
		<list:selectall channel="channel_common_approve"></list:selectall>
	</div>
	<!-- 分割线 -->
	<div class="lui_list_operation_line"></div>
	<!-- 排序 -->
	<div class="lui_list_operation_sort_btn">
		<div class="lui_list_operation_order_text">
			${ lfn:message('list.orderType') }：
		</div>
		<div class="lui_list_operation_sort_toolbar">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" channel="channel_common_approve">
				<list:sortgroup>
				<c:if test="${param.fdType!='finish'}">
					<list:sort property="fdCreateTime"
						text="${lfn:message('sys-lbpmperson:lbpmperson.order.creatorTime')}"
						group="sort.list"></list:sort>
				</c:if>
				<c:if test="${param.fdType=='finish'}">
					<list:sort property="fdCreateTime"
						text="${lfn:message('sys-lbpmperson:lbpmperson.order.creatorTime')}"
						group="sort.list"></list:sort>
					<list:sort property="fdEndedTime"
						text="${lfn:message('sys-lbpmperson:lbpmperson.order.endTime')}"
						group="sort.list" value="down"></list:sort>
				</c:if>

				<c:if
					test="${param.fdType=='error' || param.method=='getInvalidHandler'}">
					<list:sort property="fdCurrentHandler"
						text="${lfn:message('sys-lbpmperson:lbpmperson.order.currentHandler.short')}"
						group="sort.list"></list:sort>
				</c:if>
				<list:sort property="arrivalTime"
					   text="${lfn:message('sys-lbpmperson:lbpmperson.order.arrivalTime')}"
					   group="sort.list" value="down"></list:sort>
				</list:sortgroup>
			</ui:toolbar>
		</div>
	</div>
	<!-- 分页 -->
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top" channel="channel_common_approve">
		</list:paging>
	</div>
	
										<div style="float:right">
												<div style="display: inline-block;vertical-align: middle;">
													<ui:toolbar>
														  <ui:button text="${lfn:message('sys-lbpmperson:lbpmperson.batchreview')}" onclick="batchreview()" order="2" ></ui:button>
													</ui:toolbar>
												</div>
										</div>
									

</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>
<list:listview id="list_approve" channel="channel_common_approve">
	<ui:source type="AjaxJson">
													{url:'/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=listApprovalData&modelName=${JsParam.modelName }&fdStatus=${JsParam.fdStatus}&fdType=${JsParam.fdType}&fastop=${param.fastop}'}
											</ui:source>
	<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
		rowHref="!{url}" name="columntable">

		<list:col-checkbox name="List_Selected" headerStyle="width:20px;" ></list:col-checkbox>
		
		<list:col-serial title="${ lfn:message('page.serial')}"
			headerStyle="width:20px"></list:col-serial>

		<list:col-auto props=""></list:col-auto>
		<%-- <list:col-column >
		
		</list:col-column> --%>
	</list:colTable>
	<ui:event topic="list.loaded">
				 
	</ui:event>
</list:listview>
<list:paging channel="channel_common_approve"></list:paging>
<script>
//解决多子系统页面显示不全问题
domain.autoResize();
	seajs.use([ 'lui/topic' ],function(topic) {
		window.__topic = topic;
		// 监听新建更新等成功后刷新
		topic.subscribe('successReloadPage', function() {
			window.setTimeout(function() {
				topic.channel("channel_common_approve").publish('list.refresh');
			}, 2000);
		});
	});
	
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($,dialog,topic) {
	 	//批量审批
 		window.batchreview = function(){
 			var values = [];
			$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			var viewUrl="/sys/lbpmperson/person_flow_approval/batchapprove_dialog.jsp?processIds="+values.join(";");
			//Com_OpenWindow('<c:url value="/sys/lbpmperson/person_flow_approval/batchapprove_dialog.jsp" />');
			dialog.iframe(viewUrl, "${lfn:message('sys-lbpmperson:lbpmperson.batchreview')} ",  
			function(rtn) {
				topic.channel("channel_common_approve").publish('list.refresh');
			}, {width:550, height:350, close: true});
		}
	});
	
	Array.prototype.contains = function(arr) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == arr) {
				return true;
			}
		}
		return false;
	};
	function fastApprove(processId, opType, uuid) {
		var params = {
			"processId" : processId,
			"opType" : opType
		};
		seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
			//Ajax请求后台计算决策节点的分支
			$
					.ajax({
						type : 'post',
						async : true, //指定是否异步处理
						data : params,
						dataType : "json",
						url : Com_Parameter.ContextPath
								+ "sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=approveOne",
						beforeSend : function(XMLHttpRequest) {
							window.fast_approve_load = dialog.loading();
						},
						success : function(data, textStatus) {
							var doc = document.getElementById("reviewOp_" + uuid);
							if (data && data.code == 0 && doc) {
								if (opType == 'pass') {
									doc.innerHTML = '<span ><bean:message bundle="sys-lbpmperson" key="mui.lbpmperson.fastreview.approve"/></span>';
								} else if (opType == 'refuse') {
									doc.innerHTML = '<span ><bean:message bundle="sys-lbpmperson" key="mui.lbpmperson.fastreview.reject"/></span>';
								}

								if (opType == 'assignPass') {
									doc.innerHTML = '<span ><bean:message bundle="sys-lbpmperson" key="mui.lbpmperson.fastreview.assignpass"/></span>';
								} else if (opType == 'assignRefuse') {
									doc.innerHTML = '<span ><bean:message bundle="sys-lbpmperson" key="mui.lbpmperson.fastreview.assignrefuse"/></span>';
								}
							}
							//刷新列表
							if(window.__topic){
								window.__topic.channel("channel_common_approve").publish('list.refresh');
							}else{
									window.__topic = topic;
									topic.channel("channel_common_approve").publish('list.refresh');
							}
							if(window.fast_approve_load !=null){
								window.fast_approve_load.hide();
							}
						},
						complete : function(XMLHttpRequest, textStatus) {

							if(window.fast_approve_load !=null){
								window.fast_approve_load.hide();
							}
						}

					});
		});

	}
</script>
