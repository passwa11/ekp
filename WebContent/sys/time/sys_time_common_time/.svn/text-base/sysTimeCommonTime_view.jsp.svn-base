<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<template:include ref="default.view">
    <template:replace name="head">
        <style type="text/css">
            
            			.lui_paragraph_title{
            				font-size: 15px;
            				color: #15a4fa;
            		    	padding: 15px 0px 5px 0px;
            			}
            			.lui_paragraph_title span{
            				display: inline-block;
            				margin: -2px 5px 0px 0px;
            			}
            			.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
            			    border: 0px;
            			    color: #868686
            			}
            			
            			ul {
            				list-style: none;
            			}
            		
        </style>
        <script type="text/javascript">
            var formInitData = {

            };
            var messageInfo = {

            };
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${sysTimeCommonTimeForm.fdName} - " />
		${ lfn:message('sys-time:sysTimeWork.common') }
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function(dialog) {
                    dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                        if(isOk) {
                            Com_OpenWindow(delUrl, '_self');
                        }
                    });
                });
            }

            function openWindowViaDynamicForm(popurl, params, target) {
                var form = document.createElement('form');
                if(form) {
                    try {
                        target = !target ? '_blank' : target;
                        form.style = "display:none;";
                        form.method = 'post';
                        form.action = popurl;
                        form.target = target;
                        if(params) {
                            for(var key in params) {
                                var
                                v = params[key];
                                var vt = typeof
                                v;
                                var hdn = document.createElement('input');
                                hdn.type = 'hidden';
                                hdn.name = key;
                                if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                                    hdn.value =
                                    v +'';
                                } else {
                                    if($.isArray(
                                        v)) {
                                        hdn.value =
                                        v.join(';');
                                    } else {
                                        hdn.value = toString(
                                            v);
                                    }
                                }
                                form.appendChild(hdn);
                            }
                        }
                        document.body.appendChild(form);
                        form.submit();
                    } finally {
                        document.body.removeChild(form);
                    }
                }
            }

            function doCustomOpt(fdId, optCode) {
                if(!fdId || !optCode) {
                    return;
                }

                if(viewOption.customOpts && viewOption.customOpts[optCode]) {
                    var param = {
                        "List_Selected_Count": 1
                    };
                    var argsObject = viewOption.customOpts[optCode];
                    if(argsObject.popup == 'true') {
                        var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                        for(var arg in argsObject) {
                            param[arg] = argsObject[arg];
                        }
                        openWindowViaDynamicForm(popurl, param, '_self');
                        return;
                    }
                    var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
                    Com_OpenWindow(optAction, '_self');
                }
            }
            window.doCustomOpt = doCustomOpt;
            var viewOption = {
                contextPath: '${LUI_ContextPath}',
                basePath: '/sys/time/sys_time_common_time/sysTimeCommonTime.do',
                customOpts: {

                    ____fork__: 0
                }
            };

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

            <!--edit-->
            <kmss:auth requestURL="/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=edit&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('sysTimeCommonTime.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
            </kmss:auth>
            <!--delete-->
            <kmss:auth requestURL="/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('sysTimeCommonTime.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('sys-time:sysTimeCommonTime.general.shift.settings') }" href="/sys/time/sys_time_common_time/" target="_self" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">

        <ui:tabpage expand="false" var-navwidth="90%">
            <ui:content title="${ lfn:message('sys-time:sysTimeCommonTime.essential.information') }" expand="true">
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
							${ lfn:message('sys-time:sysTimeWork.timeName') }
                        </td>
                        <td width="35%">
                            <div id="_xform_fdName" _xform_type="text">
                                <xform:text property="fdName" showStatus="view" style="width:95%;" required="true"/>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
							${ lfn:message('sys-time:sysTimeCommonTime.abbreviation') }
                        </td>
                        <td width="35%">
                            <div id="_xform_fdNameShort" _xform_type="text">
                                <xform:text property="simpleName" showStatus="view" style="width:95%;" required="true"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
							${ lfn:message('sys-time:sysTimeCommonTime.color.mark') }
                        </td>
                        <td colspan="3" width="85%">
                        	<div style="text-align: center; line-height: 20px; color: white; display: inline-block; 
								width: 56px; height: 20px; background-color: ${sysTimeCommonTimeForm.fdWorkTimeColor}">
							</div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
							${ lfn:message('sys-time:sysTimeCommonTime.status') }
                        </td>
                        <td width="35%">
                            <div id="_xform_status" _xform_type="radio">
                                <xform:radio property="status" htmlElementProperties="id='status'" showStatus="view" required="true">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
							${ lfn:message('sys-time:sysTime.import.sheet2.serial') }
                        </td>
                        <td width="35%">
                            <div id="_xform_fdOrder" _xform_type="text">
                                <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        
                        <!-- 工作时间 -->
                        <tr>
                        	<td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:calendar.worktime') }
                            </td>
                            <td colspan="3" width="85%">
								<table class="tb_normal" width=100% id="TABLE_DocList">
									<tr>
										<td class="td_normal_title" align="center" width=5%>
											${ lfn:message('sys-time:sysTime.import.sheet2.serial') }
										</td>
										<td class="td_normal_title" align="center" width=22%>
											${ lfn:message('sys-time:sysTimeWork.hbmStartTime') }
										</td>
										<td class="td_normal_title" align="center" width=22%>
											${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }
										</td>
                                            <%-- 最早打卡 --%>
                                        <td  class="td_normal_title" align="center" width=22%>
                                                ${ lfn:message('sys-time:sysTimeCommonTime.earliest.startTime') }
                                        </td>
                                        <td  class="td_normal_title" align="center" width=21%>
                                                ${ lfn:message('sys-time:sysTimeCommonTime.latest.endTime') }
                                        </td>
									</tr>
									<c:forEach items="${sysTimeCommonTimeForm.sysTimeWorkDetails}" var="sysTimeWorkDetail" varStatus="vstatus">
									<tr KMSS_IsContentRow="1" class="time-row">
										<td>
											<center>
												${vstatus.index+1}
											    <input type="hidden" name="sysTimeWorkDetails[${vstatus.index}].fdId" value="${sysTimeWorkDetail.fdId}"/>
											</center>
										</td>
										<td>
											<center>
										    	<c:out value="${sysTimeWorkDetail.fdWorkStartTime}" />
										    </center>
										</td>	
										<td>
										    <center>
										    	<c:out value="${sysTimeWorkDetail.fdWorkEndTime}" />
										    	<c:if test="${empty sysTimeWorkDetail.fdOverTimeType || sysTimeWorkDetail.fdOverTimeType eq '1'}">
					          		                                    （${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }）
					          	                </c:if>
					          	                <c:if test="${sysTimeWorkDetail.fdOverTimeType eq '2'}">
					          		                                      （${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }）
					          	                </c:if>
										    </center>
										</td>
                                            <%-- 最早打卡 --%>
                                        <td >
                                            <center>  ${sysTimeWorkDetail.fdStartTime}</center>
                                        </td>
                                            <%-- 最晚打卡 --%>
                                        <td >
                                            <center>
                                            <c:if test="${not empty sysTimeWorkDetail.fdOverTime }">
                                                        ${sysTimeWorkDetail.fdOverTime}
                                                    <c:if test="${empty sysTimeWorkDetail.fdEndOverTimeType || sysTimeWorkDetail.fdEndOverTimeType eq '1'}">
                                                        （${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }）
                                                    </c:if>
                                                    <c:if test="${sysTimeWorkDetail.fdEndOverTimeType eq '2'}">
                                                        （${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }）
                                                    </c:if>
                                            </c:if>
                                            </center>
                                        </td>
									</tr>	
									</c:forEach>
								</table>
                            </td>
						</tr>
						
						<!-- 休息时间 -->
                        <tr id="restRow">
                        	<td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.start.time.lunch.break') }
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdRestStartTime" _xform_type="datetime">
                                    <xform:select
                                            property="fdRestStartType"
                                            showPleaseSelect="false"
                                            style="width:30%;margin-right:7px;"
                                            showStatus="view"
                                    >
                                        <xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
                                        <xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
                                    </xform:select>
                                    <xform:datetime property="fdRestStartTime" showStatus="view" dateTimeType="time" style="width:65%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.end.lunch.break') }
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdRestEndTime" _xform_type="datetime">
                                    <xform:select
                                            property="fdRestEndType"
                                            showPleaseSelect="false"
                                            style="width:30%;margin-right:7px;"
                                            showStatus="view"
                                    >
                                        <xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
                                        <xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
                                    </xform:select>
                                    <xform:datetime property="fdRestEndTime" showStatus="view" dateTimeType="time" style="width:65%;" />
                                </div>
                            </td>
                        </tr>
						
						<!-- 总工时 -->
						<tr>
						  	<td class="td_normal_title" width="15%">
						    	${ lfn:message('sys-time:sysTimeCommonTime.total.working.hours') }
						  	</td>
						  	<td colspan="3" width="85%">
						  		<c:set var="_fdWorkHour" value="${sysTimeCommonTimeForm.fdWorkHour}"></c:set>
						      	<span class="lui_workhour"></span>
                                    <%--统计时按多少天算--%>
                                <xform:select showStatus="view"
                                              property="fdTotalDay" showPleaseSelect="false"
                                              title="${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }"
                                              style="width:35%;height:30px;margin-right:7px;"
                                              value="${sysTimeCommonTimeForm.fdTotalDay }">
                                    <xform:simpleDataSource value='1.0'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }</xform:simpleDataSource>
                                    <xform:simpleDataSource value='0.5'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.half') }</xform:simpleDataSource>
                                </xform:select>

						  	</td>
						</tr>
						
						<!-- 所属排班区域组 -->
						<tr>
						  	<td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.shift.arrangement.area.group') }
						  	</td>
						  	<td colspan="3" width="85%">
								<ul id="areaList"></ul>
						  	</td>
						</tr>
                    </tr>
                </table>
            </ui:content>
        </ui:tabpage>
        
        <script>
        
        	seajs.use(['lui/jquery'], function($) {
        		
        		// 班次时间一条以上时隐藏休息时间
        		if($('.time-row').get().length >= 2) {
        			$('#restRow').hide();
        			
        		}
        		
       			$.get('${LUI_ContextPath}/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=getAreaByCommonId&fdId=${sysTimeCommonTimeForm.fdId}', function(res) {
					$.each(res, function(_, area) {
						$.each(area, function(id, name) {
							$('<li/>')
								.append($('<a/>')
										.attr('href', '${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=view&fdId=' + id)
										.attr('target', '_blank')
										.text(name))
								.appendTo($('#areaList'));
						});
					});
       			});
       			
        	});
        	$(function(){
        		var workHour = "${_fdWorkHour}";
        		workHour= workHour* 1000*60*60;
        		var value = formatWorkHour(workHour);
        		$('.lui_workhour').html(value);
        	})
        	window.formatWorkHour = function(value){
        		var hour = parseInt(value/1000/60/60);
        		var min = Math.round((value/1000/60))%60;
        		var hTxt = "${ lfn:message('date.interval.hour') }";
        		var mTxt = "${ lfn:message('date.interval.minute') }";
        		var hourTxt = "";
        		if(hour>0){
        			hourTxt+=hour+hTxt;
        		}
        		if(min>0){
        			hourTxt+=min+mTxt;
        		}
        		return hourTxt;
        	}
        </script>
        
        
    </template:replace>

</template:include>