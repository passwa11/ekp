<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.time.util.SysTimeUtil"%>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<script>
		function deleteDoc(delUrl){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
					if(isOk){
						Com_OpenWindow(delUrl,'_self');
					}	
				});
			});
		}
		</script>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<c:if test="${sysAttendCategory.fdType eq 1 }">
				<c:if test="${(sysAttendMainForm.fdStatus==0 || sysAttendMainForm.fdStatus==2 || sysAttendMainForm.fdStatus==3 || sysAttendCategory.fdOsdReviewType==1 && sysAttendMainForm.fdStatus==1 && sysAttendMainForm.fdOutside) && sysAttendMainForm.fdState!=2 && (empty sysAttendMainExcForm || sysAttendMainExcForm.fdStatus==3)}">
				<%--<c:if test="${(sysAttendMainForm.fdStatus==0 || sysAttendCategory.fdOsdReviewType==1 && sysAttendMainForm.fdStatus==1 && sysAttendMainForm.fdOutside) && sysAttendMainForm.fdState!=2 && (empty sysAttendMainExcForm || sysAttendMainExcForm.fdStatus==3)}">--%>
					<kmss:auth requestURL="/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=addExc&fdAttendMainId=${param.fdId }" requestMethod="GET">
						<ui:button text="${ lfn:message('sys-attend:sysAttendMainExc.submit.exception') }" 
								onclick="addMainExc();" order="1">
						</ui:button>
					</kmss:auth>
				</c:if>
				<c:if test="${sysAttendMainForm.fdState!=2 && (empty sysAttendMainExcForm || sysAttendMainExcForm.fdStatus!=2) && (sysAttendMainForm.fdStatus==0 || sysAttendMainForm.fdStatus==2 || sysAttendMainForm.fdStatus==3)}">
					<kmss:auth requestURL="/sys/attend/sys_attend_main/sysAttendMain.do?method=editByAdmin&fdId=${param.fdId}" requestMethod="GET">
						<ui:button text="${ lfn:message('sys-attend:sysAttendMain.editByAdmin') }" order="3" onclick="Com_OpenWindow('sysAttendMain.do?method=editByAdmin&fdId=${param.fdId}','_self');">
						</ui:button>
					</kmss:auth>
				</c:if>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-attend:table.sysAttendMain') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				${ lfn:message('sys-attend:table.sysAttendMain') }
			</div>
			<div class='lui_form_baseinfo'>
			</div>
		</div>
		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<c:if test="${sysAttendCategory.fdType eq 1}">
							${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }
						</c:if>
						<c:if test="${sysAttendCategory.fdType eq 2}">
							${ lfn:message('sys-attend:sysAttendCategory.custom.fdName') }
						</c:if>
					</td>
					<%-- 考勤组名 --%>
					<td width="85%" colspan="3">
						<c:out value="${sysAttendMainForm.fdCategoryName }" />
					</td>
				</tr>
				<%-- 会议签到来源 --%>
				<c:if test="${not empty sysAttendCategory.fdAppName }">
				<tr>
					<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendCategory.fdAppName"/>
					</td>
					<td width="85%" colspan="3">
						<a data-href="${LUI_ContextPath }${sysAttendCategory.fdAppUrl}&showtab=attend&expandCateId=${sysAttendCategory.fdId}#${sysAttendCategory.fdId}" onclick="Com_OpenNewWindow(this)" target="_blank" class="com_btn_link">${sysAttendCategory.fdAppName }</a>
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.docCreator"/>
					</td>
					<%-- 签到人 --%>
					<td width="35%">
						<c:out value="${sysAttendMainForm.docCreatorName}" />
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.export.dept') }
					</td>
					<%-- 部门 --%>
					<td width="35%">
						<c:out value="${sysAttendMainForm.docCreatorDept}" />
					</td>
				</tr>
				<tr>
					<c:set var="hasLocation" value="${not empty sysAttendMain.fdWifiName || not empty sysAttendMain.fdLocation}"></c:set>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.docCreateTime"/>
					</td>
					<%-- 打卡时间 --%>
					<td <c:if test="${!hasLocation }">colspan="3"</c:if>>
						<c:out value="${sysAttendMainForm.docCreateTime }" />
					</td>
					<%-- 打卡方式 --%>
					<c:if test="${hasLocation }">
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendCategoryRule.fdMode"/>
					</td>
					<td width="35%">
						<c:choose>
						    <c:when test="${sysAttendMain.fdAppName=='dingding'}">
                                ${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.mechine') }
                            </c:when>
							<c:when test="${not empty sysAttendMain.fdWifiName}">
								${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.wifi') }
							</c:when>
							<c:when test="${sysAttendMain.fdAppName!='dingding' && not empty sysAttendMain.fdLocation}">
								${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.map') }
							</c:when>
						</c:choose>
					</td>
					</c:if>
				</tr>
				<%-- 打卡地点 --%>
				<c:choose>
					<c:when test="${not empty sysAttendMainForm.fdWifiName }">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-attend" key="sysAttendMain.fdWifiName"/>
							</td>
							<td width="35%">
								${sysAttendMainForm.fdWifiName}
							</td>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-attend" key="sysAttendMain.fdWifiMacIp"/>
							</td>
							<td width="35%">
								${sysAttendMainForm.fdWifiMacIp}
							</td>
						</tr>
					</c:when>
					<c:when test="${not empty sysAttendMainForm.fdLocation }">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-attend" key="sysAttendMain.fdLocation"/>
							</td>
							<td colspan="3">
							    <c:choose>
							        <c:when test="${sysAttendMain.fdAppName=='dingding'}">
							            ${sysAttendMainForm.fdLocation}
							        </c:when>
							        <c:otherwise>
										<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
										<c:set var="fdLocationCoordinate" value="${sysAttendMainForm.fdLat}${','}${sysAttendMainForm.fdLng}"/>
										<map:location propertyName="fdLocation" nameValue="${sysAttendMainForm.fdLocation }"
											propertyCoordinate="fdLocationCoordinate" coordinateValue="${fdLocationCoordinate }"
											showStatus="view"></map:location>
							        </c:otherwise>
							    </c:choose>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<%-- 打卡状态 --%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus"/>
					</td>
					<td width="85%" colspan="3">
					<c:if test="${sysAttendCategory.fdType eq 1 }">
						<c:choose>
							<c:when test="${(sysAttendMainForm.fdStatus=='0' || sysAttendMainForm.fdStatus=='2' || sysAttendMainForm.fdStatus=='3') && sysAttendMainForm.fdState =='2'}">
								${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }
							</c:when>
							<c:otherwise>
								<sunbor:enumsShow value="${sysAttendMainForm.fdStatus}" enumsType="sysAttendMain_fdStatus" />
							</c:otherwise>
						</c:choose>
						<c:if test="${sysAttendMain.fdOutside}">
							(${ lfn:message('sys-attend:sysAttendMain.outside')})
						</c:if>
					</c:if>
						
					<c:if test="${sysAttendCategory.fdType eq 2 }">
						<sunbor:enumsShow value="${sysAttendMainForm.fdStatus}" enumsType="sysAttendMain_fdStatus" />
					</c:if>
					</td>
				</tr>
				<%--相关流程 --%>
				<c:if test="${sysAttendMainForm.fdStatus=='4' || sysAttendMainForm.fdStatus=='5' || sysAttendMainForm.fdStatus=='6'}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.view.buss"/>
						</td>
						<td width="85%" colspan="3">
							<c:choose>
								<c:when test="${sysAttendMainForm.fdStatus=='4'}">
									<table class="tb_normal" width="100%">
										<tr>
											<td class="td_normal_title" width=10%><bean:message bundle="sys-attend" key="sysAttendMain.view.docCreatorName"/></td>
											<td class="td_normal_title" width=20%><bean:message bundle="sys-attend" key="sysAttendMain.view.travel.busStartTime"/></td>
											<td class="td_normal_title" width=20%><bean:message bundle="sys-attend" key="sysAttendMain.view.travel.busEndTime"/></td>
											<td class="td_normal_title"><bean:message bundle="sys-attend" key="sysAttendMain.view.buss.review"/></td>
										</tr>
										<tr>
											<td><c:out value="${sysAttendMainForm.docCreatorName}" /></td>
											<td>${fdBusStartTime }</td>
											<td>${fdBusEndTime }</td>
											<td><a data-href="${LUI_ContextPath }${sysAttendMainForm.fdBusinessUrl}" onclick="Com_OpenNewWindow(this)" target="_blank">${sysAttendBusiness.fdProcessName }</a></td>
										</tr>
									</table>
								</c:when>
								<c:when test="${sysAttendMainForm.fdStatus=='6'}">
									<table class="tb_normal" width="100%">
										<tr>
											<td class="td_normal_title" width=10%><bean:message bundle="sys-attend" key="sysAttendMain.view.docCreatorName"/></td>
											<td class="td_normal_title" width=10%><bean:message bundle="sys-attend" key="sysAttendMain.view.out.date"/></td>
											<td class="td_normal_title" width=20%><bean:message bundle="sys-attend" key="sysAttendMain.view.out.startTime"/></td>
											<td class="td_normal_title" width=20%><bean:message bundle="sys-attend" key="sysAttendMain.view.out.endTime"/></td>
											<td class="td_normal_title" width=20%><bean:message bundle="sys-attend" key="sysAttendMain.view.out.totalTime"/></td>
											<td class="td_normal_title"><bean:message bundle="sys-attend" key="sysAttendMain.view.buss.review"/></td>
										</tr>
										<tr>
											<td><c:out value="${sysAttendMainForm.docCreatorName}" /></td>
											<td><kmss:showDate value="${sysAttendBusiness.fdBusStartTime}" type="date" ></kmss:showDate></td>
											<td><kmss:showDate value="${sysAttendBusiness.fdBusStartTime}" type="time" ></kmss:showDate></td>
											<td><kmss:showDate value="${sysAttendBusiness.fdBusEndTime}" type="time" ></kmss:showDate></td>
											<td>
												<c:set var="_fdCountHour" value="${sysAttendBusiness.fdCountHour }" />
												<%
													Float _fdCountHour = (Float)pageContext.getAttribute("_fdCountHour");
													_fdCountHour = _fdCountHour==null ?0f:_fdCountHour;
													pageContext.setAttribute("_fdCountHourTxt", SysTimeUtil.formatLeaveTimeStr(0f, _fdCountHour));
												%>
												${_fdCountHourTxt }
											</td>
											<td><a data-href="${LUI_ContextPath }${sysAttendMainForm.fdBusinessUrl}" onclick="Com_OpenNewWindow(this)" target="_blank">${sysAttendBusiness.fdProcessName }</a></td>
										</tr>
									</table>
								</c:when>
								<c:otherwise>
									<table class="tb_normal" width="100%">
										<tr>
											<td class="td_normal_title" width=10%><bean:message bundle="sys-attend" key="sysAttendMain.view.docCreatorName"/></td>
											<td class="td_normal_title" width=10%><bean:message bundle="sys-attend" key="sysAttendMain.view.leave.busType"/></td>
											<td class="td_normal_title" width=20%><bean:message bundle="sys-attend" key="sysAttendMain.view.leave.busStartTime"/></td>
											<td class="td_normal_title" width=20%><bean:message bundle="sys-attend" key="sysAttendMain.view.leave.busEndTime"/></td>
											<td class="td_normal_title"><bean:message bundle="sys-attend" key="sysAttendMain.view.buss.review"/></td>
										</tr>
										<tr>
											<td><c:out value="${sysAttendMainForm.docCreatorName}" /></td>
											<td><c:out value="${fdOffTypeText}" /></td>
											<td>${fdBusStartTime }</td>
											<td>${fdBusEndTime }</td>
											<td><a data-href="${LUI_ContextPath }${sysAttendMainForm.fdBusinessUrl}" onclick="Com_OpenNewWindow(this)" target="_blank">${sysAttendBusiness.fdProcessName }</a></td>
										</tr>
									</table>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
				<%-- 会议签到不显示备注和图片 --%>
				<c:if test="${empty sysAttendCategory.fdAppName }">
				<%-- 备注 --%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.fdDesc"/>
					</td>
					<td width="85%" colspan="3">
						<xform:textarea property="fdDesc" style="width:85%" showStatus="view" />
					</td>
				</tr>
				<%-- 图片 --%>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMainExc.fdDesc.picture') }
					</td>
					<td width="85%" colspan="3">
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formBeanName" value="sysAttendMainForm" />
							<c:param name="fdKey" value="attachment"/>
							<c:param name="fdModelName" value="com.landray.kmss.sys.attend.model.SysAttendMain"></c:param>
							<c:param name="fdModelId" value="${sysAttendMainForm.fdId }"></c:param>
						</c:import>
					</td>
				</tr>
				</c:if>
				<%-- 设备信息 --%>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.export.fdDeviceInfo') }
					</td>
					<td width="85%" colspan="3">
						<c:choose>
							<c:when test="${sysAttendMainForm.fdAppName=='dingding' }">
								<sunbor:enumsShow value="${sysAttendMainForm.fdSourceType}" enumsType="sysAttendMain_fdSourceType" />
							</c:when>
							<c:otherwise>
								${sysAttendMainForm.fdDeviceInfo }
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
                    <td class="td_normal_title" width=15%>
                        ${ lfn:message('sys-attend:sysAttendMain.fdAppName') }
                    </td>
                    <td width="85%" colspan="3">
                        <c:choose>
							<c:when test="${sysAttendMainForm.fdAppName=='dingding' }">
								${ lfn:message('sys-attend:sysAttendMain.fdAppName.dingDing') }
							</c:when>
							<c:when test="${sysAttendMainForm.fdAppName=='qywx' }">
								${ lfn:message('sys-attend:sysAttendMain.fdAppName.qywx') }
							</c:when>
							<c:when test="${empty sysAttendMainForm.fdAppName }">
								${ lfn:message('sys-attend:sysAttendMain.fdAppName.ekp') }
							</c:when>
							<c:otherwise>
								${sysAttendMainForm.fdAppName }
							</c:otherwise>
						</c:choose>
                    </td>
                </tr>
				<%-- 修改打卡记录 --%>
				<c:if test="${sysAttendMainForm.docAlterorName!=null || sysAttendMainForm.docAlterTime!=null || sysAttendMainForm.fdAlterRecord!=null}">
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.docAlteror') }
					</td>
					<td width="35%">
						${sysAttendMainForm.docAlterorName }
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.docAlterTime') }
					</td>
					<td width="35%">
						${sysAttendMainForm.docAlterTime }
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.fdAlterRecord') }
					</td>
					<td colspan="3">
						${sysAttendMainForm.fdAlterRecord }
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.illustrate') }
					</td>
					<td colspan="3">
						<span style="color:red">${ lfn:message('sys-attend:sysAttendMain.illustrateContent') }</span>
					</td>
				</tr>
				</c:if>
				
				<%-- 异常处理 --%>
				<c:if test="${not empty sysAttendMainExcForm}">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.fdDesc.setting') }</td>
						<td colspan="3">
							<xform:textarea value="${sysAttendMainExcForm.fdDesc}" showStatus="view" property="fdDesc"></xform:textarea>
							
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysAttendMainExcForm" />
								<c:param name="fdKey" value="attachment"/>
							</c:import>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.fdStatus') }</td>
						<td>
							【<sunbor:enumsShow value="${sysAttendMainExcForm.docStatus}" enumsType="sysAttendMainExc_docStatus" />】
						</td>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.fdHandler') }</td>
						<td>
							<kmss:showWfPropertyValues idValue="${sysAttendMainExcForm.fdId}" propertyName="handlerName" />
						</td>
					</tr>
					<c:if test="${sysAttendMainExcForm.fdStatus==1  }">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docCreateTime1') }</td>
						<td colspan="3">
							<c:out value="${sysAttendMainExcForm.docCreateTime}" />
						</td>
					</tr>
					</c:if>
					<c:if test="${sysAttendMainExcForm.fdStatus!=1  }">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docCreateTime1') }</td>
						<td>
							<c:out value="${sysAttendMainExcForm.docCreateTime}" />
						</td>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docHandleTime') }</td>
						<td>
							<c:out value="${sysAttendMainExcForm.docHandleTime}" />
						</td>
					</tr>
					</c:if>
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.exception') }</td>
						<td colspan="3">
							<a data-href="${LUI_ContextPath }/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=view&fdId=${sysAttendMainExcForm.fdId}" onclick="Com_OpenNewWindow(this)" target="_blank" class="com_btn_link">${ lfn:message('sys-attend:sysAttendMain.viewException') }</a>
						</td>
					</tr>
				</c:if>
				
			</table> 
		</div>
		<c:if test="${not empty sysAttendMainExcForm }">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-supportExpand="true" var-expand="true"  var-average='false' var-useMaxWidth='true'>
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendMainExcForm" />
					<c:param name="fdKey" value="attendMainExc" />
				</c:import>
			</ui:tabpanel>
		</c:if> 
		<script>
		seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
			window.addMainExc = function addMainExc() {
				var hasBusSetting = "${fn:length(sysAttendCategory.busSetting)>0 }";
				${sysAttendCategory.busSetting }

				if(hasBusSetting){
					dialog.iframe('/sys/attend/sys_attend_main/sysAttendMain_view_addExc.jsp?fdAttendMainId=${sysAttendMainForm.fdId }&fdCategoryId=${sysAttendMainForm.fdCategoryId }',
							"${ lfn:message('sys-attend:sysAttendMainExc.select.exception') }",
							null,
							{width:450, height:300});
				} else {
					Com_OpenWindow('${LUI_ContextPath }/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=addExc&fdAttendMainId=${sysAttendMainForm.fdId }','_blank');
				}
			};
		});
		</script>
	</template:replace>
</template:include>