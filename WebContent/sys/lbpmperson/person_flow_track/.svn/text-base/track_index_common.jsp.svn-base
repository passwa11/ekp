<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:criteria channel="channel_common_track"  expand="false"
	multi="false">
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
	<list:cri-criterion
		title="${ lfn:message('sys-lbpmperson:lbpmperson.flow.docStatus') }"
		key="docStatus" multi="false">
		<list:box-select>
			<list:item-select  cfg-enable="true">
				<ui:source type="Static">
											[
											{text:'${ lfn:message('sys-lbpmperson:lbpmperson.status.append')}',value:'20'},
											{text:'${ lfn:message('sys-lbpmperson:lbpmperson.status.refuse')}',value:'11'},
											{text:'${ lfn:message('sys-lbpmperson:lbpmperson.status.discard')}',value:'00'},
											{text:'${ lfn:message('sys-lbpmperson:lbpmperson.status.publish')}',value:'30'},
											]
										</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<c:if test="${param.fdType!='finish'}">
		<%--当前处理人--%>
		<list:cri-ref ref="criterion.sys.postperson.availableAll"
			key="fdCurrentHandler" multi="false"
			title="${lfn:message('sys-lbpmperson:lbpmperson.flow.currentHandler') }" />
	</c:if>
	<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false"
		title="${lfn:message('sys-lbpmperson:lbpmperson.person.creator') }" />
	<%--创建时间--%>
	<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
		title="${lfn:message('sys-lbpmperson:lbpmperson.flow.docAuthorTime') }" />

</list:criteria>
<!-- 排序 -->
<!-- 排序 -->
<div class="lui_list_operation">
	<div style='color: #979797; float: left; padding-top: 1px;'>${ lfn:message('list.orderType') }：</div>
	<div style="float: left">
		<div style="display: inline-block; vertical-align: middle;">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" channel="channel_common_track">
				<list:sortgroup>
				<c:if test="${param.fdType!='finish'}">
					<list:sort property="fdCreateTime"
						text="${lfn:message('sys-lbpmperson:lbpmperson.order.creatorTime')}"
						group="sort.list" value="down"></list:sort>
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
				</list:sortgroup>
			</ui:toolbar>
		</div>
	</div>
	<div style="float: left;">
		<list:paging layout="sys.ui.paging.top" channel="channel_common_track">
		</list:paging>
	</div>

</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>
<list:listview channel="channel_common_track">
	<ui:source type="AjaxJson">
													{url:'/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=listTrack&modelName=${JsParam.modelName }&fdStatus=${JsParam.fdStatus}&fdType=${JsParam.fdType}'}
											</ui:source>
	<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
		rowHref="!{url}" name="columntable">
		<list:col-serial></list:col-serial>
		<list:col-auto props=""></list:col-auto>
	</list:colTable>
	<ui:event topic="list.loaded">  
									  
									</ui:event>
</list:listview>
<list:paging channel="channel_common_track"></list:paging>
<script>
//解决多子系统页面显示不全问题
domain.autoResize();
	seajs.use([ 'lui/topic' ],function(topic) {
		// 监听新建更新等成功后刷新
		topic.subscribe('successReloadPage', function() {
			window.setTimeout(function() {
				topic.channel("channel_common_track").publish('list.refresh');
			}, 2000);
		});
	});
	Array.prototype.contains = function(arr) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == arr) {
				return true;
			}
		}
		return false;
	};
	
	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
 		window.cancelFollowFun = function(processId) {
			var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=cancelFollow';
			dialog.confirm('<bean:message  bundle="sys-lbpmservice-support" key="lbpmFollow.button.cancelFollow.confirm"/>',function(value){
 				if(value == true) {
 					window.follow_load = dialog.loading();
					$.ajax({
						url : url,
						type : 'POST',
						data : $.param({"processId" : processId}, true),
						dataType : 'json',
						error : function(data) {
							if(window.follow_load != null) {
								window.follow_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
						success: function(data) {
							if(window.follow_load != null){
								window.follow_load.hide(); 
								topic.channel("channel_common_track").publish("list.refresh");
							}
							//dialog.result(data);
						}
				   });
				}
 			});
		};
 	});
</script>

</div>