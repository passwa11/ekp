<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date,java.util.Calendar,com.landray.kmss.util.*,com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	SysAgendaBaseConfig baseConfig = new SysAgendaBaseConfig();
	String updateAuthDate = baseConfig.getUpdateAuthDate();
	if("now".equals(updateAuthDate)){
		calendar.add(Calendar.DAY_OF_MONTH, 0);//日期减零
	}else if("week".equals(updateAuthDate)){
		calendar.add(Calendar.DAY_OF_MONTH, -7);//日期减七
	}else if("day".equals(updateAuthDate)){
		calendar.add(Calendar.DAY_OF_MONTH, -1);//日期减一
	}else{
		calendar.add(Calendar.MONTH, -1);//月份减一
	}
	String today=DateUtil.convertDateToString(calendar.getTime(),DateUtil.TYPE_DATE,request.getLocale());
%>
<template:include ref="default.edit" sidebar="no">
	
	<template:replace name="head">
        <style type="text/css">
            .lui_form_body{background:white;}
        </style>
        <script type="text/javascript">
            var formInitData = {

            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
        </script>
        <script language="JavaScript">
			seajs.use(['lui/jquery'],function($) {
				$(document).ready(function() {
					$(".tempTB").css({"width":"795px", "min-width":"795px"});
					$(".lui_form_path_frame").css({"width":"795px", "min-width":"795px"});
				})
			});
		</script>
    </template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<%--add页面的按钮--%>
			<ui:button text="${lfn:message('button.update') }" order="2" onclick="commitMethod('save');">
			</ui:button>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="closeWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<%--表单--%>
	<template:replace name="content"> 
		<html:form action="/km/calendar/km_calendar_auth_list/kmCalendarAuthList.do">
	        <div class="lui_form_content_frame" >
	        	<table class="tb_normal" width="95%">
	        		<tr>
						<td class="td_normal_title" width="25%">
							<bean:message bundle="km-calendar" key="kmCalendarAuthList.fdPerson" />
						</td>
						<td colspan="3">
							<%-- 共享人员/组织--%>
                            <div id="_xform_fdPersonIds" _xform_type="address">
                                <xform:address propertyId="fdPersonIds" propertyName="fdPersonNames" required="true" mulSelect="true" orgType="ORG_TYPE_ALL" subject="${lfn:message('km-calendar:kmCalendarAuthList.fdPerson')}" showStatus="edit" style="width:95%;" />
                            </div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="25%">
							<bean:message bundle="km-calendar" key="kmCalendarAuthList.authType.title" />
						</td>
						<td colspan="3">
							<%-- 授予权限类型 --%>
							<div id="_xform_fdAuthType" _xform_type="checkbox">
                                <xform:checkbox property="fdAuthType" htmlElementProperties="id='fdAuthType'" showStatus="edit" notChangedValues="read" value="read">
									<xform:simpleDataSource value="read">${ lfn:message('km-calendar:kmCalendarAuthList.authType.read') }</xform:simpleDataSource>
									<xform:simpleDataSource value="modify">${ lfn:message('km-calendar:kmCalendarAuthList.authType.modify') }</xform:simpleDataSource>
									<xform:simpleDataSource value="edit">${ lfn:message('km-calendar:kmCalendarAuthList.authType.edit') }</xform:simpleDataSource>
                                </xform:checkbox>
                            </div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="25%">
							<bean:message bundle="km-calendar" key="kmCalendarAuthList.fdIsShare.title" />
						</td>
						<td colspan="3">
							<%-- 历史日程生效 --%>
							<div id="_xform_fdIsShare" _xform_type="radio">
								<xform:radio property="fdIsShare" htmlElementProperties="id='fdIsShare'" showStatus="edit" value="false" onValueChange="changeIsShare">
                                    <xform:simpleDataSource value="false">${ lfn:message('km-calendar:kmCalendarAuthList.fdIsShare.no') }</xform:simpleDataSource>
									<xform:simpleDataSource value="true">${ lfn:message('km-calendar:kmCalendarAuthList.fdIsShare.yes') }</xform:simpleDataSource>
                                </xform:radio>
                            </div>
                            <%-- 历史日程共享时间--%>
                            <div id="_xform_fdShareDate" _xform_type="datetime" style="margin-top: 5px;display: none;">
                            	${lfn:message('km-calendar:sysCalendarShareGroup.updateAuthDate')}
                                <xform:datetime property="fdShareDate" required="true" value="<%=today%>" showStatus="edit" dateTimeType="date" style="width:20%;" />
                            </div>
						</td>
					</tr>
	        	</table>
	        </div>
	        <html:hidden property="fdAuthCreatorId" value="${param.fdPersonId }"/>
		    <html:hidden property="method_GET" />
		    <script>
		    	var _validator = $KMSSValidation(document.forms['kmCalendarAuthListForm']);	
		    
		    	window.onload = function(){
		    		changeIsShare();
		    	};
		    	
			    window.commitMethod = function(method){
	        		var formObj = document.kmCalendarAuthListForm;
	        		var isShare = $("input[name='fdIsShare']:checked").val();
	        		if (_validator.validate()) {
						if (isShare == 'false') {
							$("input[name='fdShareDate']").val('');
						}
						Com_Submit(formObj, method);
					}
		        };
		        
		        window.changeIsShare = function(){
		        	var checked = $("input[name='fdIsShare']:checked").val();
		        	if (checked == 'true') {
						$('#_xform_fdShareDate').show();
						_validator.addElements($("input[name='fdShareDate']")[0], 'required');
					} else {
						$('#_xform_fdShareDate').hide();
						_validator.removeElements($("input[name='fdShareDate']")[0], 'required');
					}
		        };
		        
			    function closeWindow(){
		        	seajs.use('lui/dialog', function(dialog) {
						dialog.confirm(Com_Parameter.CloseInfo,
								function(value) {
									if (value) {
										_closeWindow();
									} else
										return;
								});
					});
		        }
		        function _closeWindow(){
		        	// 遍历所有父窗口判断是否存在$dialog
		        	var parent = window;
		        	while (parent) {
		        		if (typeof(parent.$dialog) != 'undefined') {
		        			parent.$dialog.hide("null");
		        			return;
		        		}
		        		if (parent == parent.parent)
		        			break;
		        		parent = parent.parent;
		        	}
	
		        	try {
		        		var win = window;
		        		for (var frameWin = win.parent; frameWin != null && frameWin != win; frameWin = win.parent) {
		        			if (frameWin["Frame_CloseWindow"] != null) {
		        				frameWin["Frame_CloseWindow"](win);
		        				return;
		        			}
		        			win = frameWin;
		        		}
		        	} catch (e) {
		        	}
		        	try {
		        		top.opener = top;
		        		top.open("", "_self");
		        		top.close();
		        	} catch (e) {
		        	}
		        }
		    </script>
		</html:form>
	</template:replace>
	
</template:include>