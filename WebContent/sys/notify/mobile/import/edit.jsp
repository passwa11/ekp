<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:set var="sysNotifyRemindMainContextForm" value="${mainModelForm.sysNotifyRemindMainContextForm}" scope="request" />
<link rel="stylesheet" href="${LUI_ContextPath}/sys/notify/mobile/import/edit.css" />
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<script type="text/javascript">DocList_Info.push("TABLE_${param.fdPrefix}_${param.fdKey}");</script>
<table class="muiAgendaNormal" width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_${param.fdPrefix}_${param.fdKey}" >
	<%--基准行--%>
	<tr data-dojo-type="mui/form/Template" class="muiAgendaNormalTr" data-celltr="true" KMSS_IsReferRow="1" style="display:none;" border='0'>
		<td>
			<input  type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdModelName" value="${param.fdModelName}"  />
			<table class="muiSimple">
				<tr>
					<td class="muiDetailTableNo"  style="padding-left: 1rem;">
						<span class="muiDetailTableNoTxt"><bean:message key="sysNotifyTodo.muiDetailTableNo" bundle="sys-notify"/>!{index}</span>
						<div class="muiDetailTableDel" onclick="_${param.fdPrefix}_${param.fdKey}_del(this);"><bean:message key="button.delete"/></div>
					</td>
				</tr>
				<tr>
					<td>
						<%-- 通知方式 --%>
		 				<kmss:editNotifyType 
		 					multi="false" value="todo" mobile="true" 
		 					property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdNotifyType" 
		 					title="${lfn:message('sys-notify:mui.sysNotify.notify.type')}">
		 				</kmss:editNotifyType>
					</td>
				</tr>
				<tr>
					<td>
						<%-- 提前时间 --%>
						<div data-dojo-type="mui/form/Input" 
							data-dojo-props="name:'sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdBeforeTime',value:'30',showStatus:'edit',subject:'<bean:message key="sysNotify.remind.fdBeforeTime" bundle="sys-notify"/>',validate:'digits required',required:true,orient:'vertical'">
					</td>
				</tr>
				<tr>
					<td>
						<%-- 时间单位 --%>
						<xform:select property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdTimeUnit"
				 			value="minute" showStatus="edit" showPleaseSelect="false" mobile="true" required="true" 
				 			title="${lfn:message('sys-notify:mui.sysNotify.notify.time')}">
							<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
						</xform:select>
					</td>
				</tr>
			</table>
		</td>	
	</tr>
	<%--内容行--%>
	<c:forEach items="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="sysNotifyRemindMainFormListItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" data-celltr="true">
			<td>
				<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdId" value="${sysNotifyRemindMainFormListItem.fdId}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelId" value="${sysNotifyRemindMainFormListItem.fdModelId}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdKey" value="${sysNotifyRemindMainFormListItem.fdKey}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelName" value="${param.fdModelName}" />
				<table class="muiSimple">
					<tr>
						<td class="muiDetailTableNo">
							<span class="muiDetailTableNoTxt"  style="padding-left: 1rem;"><bean:message key="sysNotifyTodo.muiDetailTableNo" bundle="sys-notify"/>${vstatus.index}</span>
							<div class="muiDetailTableDel" onclick="_${param.fdPrefix}_${param.fdKey}_del(this);"><bean:message key="button.delete"/></div>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 通知方式 --%>
							<kmss:editNotifyType 
								multi="false" mobile="true"
								property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdNotifyType"
								title="${lfn:message('sys-notify:mui.sysNotify.notify.type')}">
							</kmss:editNotifyType>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 提前时间 --%>
							<div data-dojo-type="mui/form/Input" 
								data-dojo-props="name:'sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdBeforeTime',
									value:'${sysNotifyRemindMainFormListItem.fdBeforeTime}',
									showStatus:'edit',subject:'<bean:message key="sysNotify.remind.fdBeforeTime" bundle="sys-notify"/>',validate:'digits required',required:true,opt:false,orient:'vertical'">
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 时间单位 --%>
							<xform:select 
								property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdTimeUnit" 
								value="${sysNotifyRemindMainFormListItem.fdTimeUnit}"  
								title="${lfn:message('sys-notify:mui.sysNotify.notify.time')}"
								showStatus="edit" showPleaseSelect="false" mobile="true">
								<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
							</xform:select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="muiDetailTableAdd"  onclick="_${param.fdPrefix}_${param.fdKey}_add();">
	<div class="mblTabBarButtonIconArea">
		<div class="mblTabBarButtonIconParent">
			<i class="mui mui-plus"></i>
		</div>
	</div>
	<div class="mblTabBarButtonLabel" ><bean:message key="sysNotifyTodo.mblTabBarButtonLabel" bundle="sys-notify"/></div>
</div>
<br/>
<script type="text/javascript">
require(["dojo/parser", "dojo/dom", "dojo/dom-construct","dojo/dom-style","dojo/dom-geometry","dojo/dom-attr" ,"dijit/registry","dojo/ready","dojo/query","dojo/topic"],
		function(parser, dom, domConstruct,domStyle,domGeometry,domAttr ,registry,ready,query,topic){
	//添加行
	window._${param.fdPrefix}_${param.fdKey}_add = function() {
		event = event || window.event;
		if (event.stopPropagation)
			event.stopPropagation();
		else
			event.cancelBubble = true;
		var newRow = DocList_AddRow("TABLE_${param.fdPrefix}_${param.fdKey}");
		parser.parse(newRow);
		resize();
		window['_${param.fdPrefix}_${param.fdKey}_fixNo']();
	};
	//删除行
	window._${param.fdPrefix}_${param.fdKey}_del = function(domNode) {
		var TR = query(domNode).parents('.muiAgendaNormalTr')[0];
		var modelNameField = query('[name$="fdModelName"]',TR)[0];
		modelNameField.value = "";
		domStyle.set(TR,'display','none');
		resize();
		window['_${param.fdPrefix}_${param.fdKey}_fixNo']();
	};
	
	//调整行号
	window._${param.fdPrefix}_${param.fdKey}_fixNo = function(){
		var tb = query('#TABLE_${param.fdPrefix}_${param.fdKey}')[0];
		var nodes = query('.muiDetailTableNoTxt',tb);
		var visibleIndex = 0;
		nodes.forEach(function(dom){
			var TR = query(dom).parents('.muiAgendaNormalTr')[0];
			var visible = (domStyle.get(TR,'display') != 'none');
			if(visible){
				dom.innerHTML = '${lfn:message("sys-notify:sysNotifyTodo.muiDetailTableNo")}' + (visibleIndex + 1);
				visibleIndex++;
			}
		});
	};
	
	ready(function(){
		if(DocList_TableInfo['TABLE_${param.fdPrefix}_${param.fdKey}']==null){
			DocListFunc_Init();
		}
	});
	
	//重新调整位置
	function resize(){
		var table=query('#TABLE_${param.fdPrefix}_${param.fdKey}')[0],
			tableOffsetTop=_getDomOffsetTop(table),
			sliceHeight= tableOffsetTop+table.offsetHeight;
		topic.publish("/mui/list/toTop",null,{y: 0 - sliceHeight  });
	}
	
	function _getDomOffsetTop(node){
		 var offsetParent = node;
		 var nTp = 0;
		 while (offsetParent!=null && offsetParent!=document.body) {
			 nTp += offsetParent.offsetTop; 
			 offsetParent = offsetParent.offsetParent; 
		 }
		 return nTp;
	};
	
	
    topic.subscribe('mui/form/checkbox/valueChange', function(widget, args) {
        if (args.name.indexOf('__notify_type') > -1 && document.getElementById(args.name) ) {
            var fields = document.getElementsByName(args.name);
            var values = '';
            for (var i = 0; i < fields.length; i++)
                if (fields[i].checked)
                    values += ';' + fields[i].value;
            if (values != '')
                document.getElementById(args.name).value = values.substring(1);
            else
                document.getElementById(args.name).value = '';
        }
    });
	
});
	
</script>


