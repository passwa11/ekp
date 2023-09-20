<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
        <list:criteria id="criteria1" expand="false">
		    <list:cri-criterion title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.my')}" expand="true" key="myDoc" multi="false">
				<list:box-select>
					<list:item-select cfg-defaultValue="create" cfg-required="true">
						<ui:source type="Static">
						    [{text:'${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.myCreate')}', value:'create'},
							 {text:'${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.myApprovaled')}', value:'approved'},
							 {text:'${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.myApproval')}', value:'approval'}]
						</ui:source>
						<ui:event event="selectedChanged" args="evt">
								var vals = evt.values;
								if (vals.length > 0 && vals[0] != null) {
									var criterionStatus = LUI('fdStatus');
									while(criterionStatus){
										if (criterionStatus.className == 'criterion'){
											criterionStatus = criterionStatus.parent;
											break;
										}
										criterionStatus = criterionStatus.parent;
									}
									if(criterionStatus&&!criterionStatus.expand){
										criterionStatus.expand = true;
									}
									var val = vals[0].value;
									if (val != 'approval') {
										LUI('fdStatus').setEnable(true);
									} else{
										LUI('fdStatus').setEnable(false);
									}
								}else{
								        LUI('fdStatus').setEnable(false);
								}
						 </ui:event>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<%--状态--%>  
			<list:cri-criterion title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.status')}" key="fdStatus" multi="false" expand="true"> 
				<list:box-select>
					<list:item-select id="fdStatus" cfg-enable="false">
						<ui:source type="Static">
							  [{text:'${ lfn:message('sys-lbpmmonitor:status.created') }',value:'10'},
							   {text:'${ lfn:message('sys-lbpmmonitor:status.activated') }',value:'20'},
							   {text:'${ lfn:message('sys-lbpmmonitor:status.completed') }',value:'30'},
							   {text:'${ lfn:message('sys-lbpmmonitor:status.discard') }',value:'00'},
							   {text:'${ lfn:message('sys-lbpmmonitor:status.error') }',value:'21'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<%-- 模块 --%> 
			<list:cri-criterion title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.module')}" key="fdModelName" expand="false"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/sys/lbpmmonitor/sys_lbpmmonitor_person/SysLbpmMonitorPerson.do?method=getModule'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<%-- 创建时间 --%> 
			<list:cri-auto modelName="com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess" property="fdCreateTime"/>     
		</list:criteria>
		
		<!-- 排序 -->
		<div class="lui_list_operation">
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
					<list:sortgroup>
						<list:sort property="fdCreateTime" text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}" group="sort.list" value="down"></list:sort>
						<list:sort property="fdStatus" text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.status')}" group="sort.list"></list:sort>
						<list:sort property="fdModelName" text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.modelName')}" group="sort.list"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
		
	    <ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/sys/lbpmmonitor/sys_lbpmmonitor_person/SysLbpmMonitorPerson.do?method=listChildren'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="!{url}"  name="columntable">
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
			
		</list:listview> 
	 	<list:paging></list:paging>	 	 	