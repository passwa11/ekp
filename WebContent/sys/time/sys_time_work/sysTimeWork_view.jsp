<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">

		<%-- 
		<kmss:auth requestURL="/sys/time/sys_time_work/sysTimeWork.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('sysTimeWork.do?method=edit&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
		--%>
		<kmss:auth requestURL="/sys/time/sys_time_work/sysTimeWork.do?method=delete&fdId=${JsParam.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.delete"/>"
				onclick="if(!confirmDelete())return;Com_OpenWindow('sysTimeWork.do?method=delete&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="top.close();">
</div>
<p class="txttitle"><bean:message  bundle="sys-time" key="table.sysTimeWork"/></p>
<center>
<table class="tb_normal" width=95%>
	<html:hidden name="sysTimeWorkForm" property="fdId"/>
	<html:hidden name="sysTimeWorkForm" property="sysTimeAreaId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeWork.validTime"/>
		</td>
		<td width=85% colspan=3>
			<bean:message  bundle="sys-time" key="sysTimeWork.validTime.start"/>
			<bean:write name="sysTimeWorkForm" property="fdStartTime"/>
			&nbsp;&nbsp;
			<bean:message  bundle="sys-time" key="sysTimeWork.validTime.end"/>
			<c:if test="${sysTimeWorkForm.fdEndTime != null || sysTimeWorkForm.fdEndTime != ''}">
				<bean:write name="sysTimeWorkForm" property="fdEndTime"/>
			</c:if>
			<c:if test="${sysTimeWorkForm.fdEndTime == null || sysTimeWorkForm.fdEndTime == ''}">
				<bean:message  bundle="sys-time" key="sysTimeWork.validTime.end.list"/>
			</c:if>	
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeWork.week"/>
		</td>
		<td colspan="3" width="85%">
			<bean:message  bundle="sys-time" key="sysTimeWork.week.start"/>
			<sunbor:enumsShow value="${sysTimeWorkForm.fdWeekStartTime}" enumsType="common_week_type"/>
			&nbsp;&nbsp;
			<bean:message  bundle="sys-time" key="sysTimeWork.week.end"/>
			<sunbor:enumsShow value="${sysTimeWorkForm.fdWeekEndTime}" enumsType="common_week_type"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			班次类型
		</td>
		<td width="85%" colspan="3">
			<xform:radio property="timeType" showStatus="view" onValueChange="handleChangeType">
				<xform:simpleDataSource value="1">通用</xform:simpleDataSource>
				<xform:simpleDataSource value="2">自定义</xform:simpleDataSource>
			</xform:radio>
		</td>
	</tr>
	<tr>
		<c:choose>
			<c:when test="${sysTimeWorkForm.timeType == '1'}">
				<td class="td_normal_title" width="15%">
					班次名称
				</td>
				<td width="35%">
<%-- 					<xform:select property="sysTimeCommonId" showStatus="view" value="${sysTimeWorkForm.sysTimeCommonId}"> --%>
<%-- 						<xform:beanDataSource serviceBean="sysTimeCommonTimeService" /> --%>
<%-- 					</xform:select> --%>
${sysTimeWorkForm.sysTimeCommonTimeForm.fdName}	
				</td>
				<td class="td_normal_title" width="15%">
					班次颜色
				</td>
				<td width="35%">
					<div style="text-align: center; line-height: 20px; color: white; 
						display: inline-block; width: 56px; height: 20px; background-color: ${sysTimeWorkForm.sysTimeCommonTimeForm.fdWorkTimeColor}">
					</div>
				</td>
			</c:when>
			<c:otherwise>
				<td class="td_normal_title" width="15%">
					班次名称
				</td>
				<td width="35%">
					${sysTimeWorkForm.sysTimeCommonTimeForm.fdName}
				</td>
				<td class="td_normal_title" width="15%">
					班次颜色
				</td>
				<td width="35%">
					<div style="text-align: center; line-height: 20px; color: white; 
						display: inline-block; width: 56px; height: 20px; background-color: ${sysTimeWorkForm.fdTimeWorkColor}">
					</div>
				</td>
			
			</c:otherwise>
			
		</c:choose>
	</tr>
	<!-- 休息时间 -->
				<c:if test="${not empty sysTimeWorkForm.sysTimeCommonTimeForm.fdRestStartTime && not empty sysTimeWorkForm.sysTimeCommonTimeForm.fdRestEndTime}">
                        <tr id="restRow">
                        	<td class="td_normal_title" width="15%">
								休息开始时间
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdRestStartTime" _xform_type="datetime">

									<xform:select
											property="sysTimeCommonTimeForm.fdRestStartType"
											showPleaseSelect="false"
											title="休息开始时间"
											style="width:30%;margin-right:7px;"
											showStatus="view"
									>
										<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
										<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
									</xform:select>
                                    <xform:datetime property="sysTimeCommonTimeForm.fdRestStartTime" showStatus="view" dateTimeType="time" style="width:60%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
								休息结束时间
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdRestEndTime" _xform_type="datetime">
									<xform:select
											property="sysTimeCommonTimeForm.fdRestEndType"
											showPleaseSelect="false"
											title="休息结束时间"
											style="width:30%;margin-right:7px;"
											showStatus="view"
									>
										<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
										<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
									</xform:select>
                                    <xform:datetime property="sysTimeCommonTimeForm.fdRestEndTime" showStatus="view" dateTimeType="time" style="width:60%;" />
                                </div>
                            </td>
                        </tr>
                    </c:if>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeWork.docCreatorId"/>
		</td><td width=35%>
			${sysTimeWorkForm.docCreatorName}			
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeWork.docCreateTime"/>
		</td><td width=35%>
			${sysTimeWorkForm.docCreateTime}
		</td>
	</tr>

	<!-- 总工时 -->
	<tr class="customClass" >
		<td class="td_normal_title" width="15%">
			统计计算天
		</td>
		<td width="35%">
			<%--统计时按多少天算--%>
			<xform:select showStatus="view" property="sysTimeCommonTimeForm.fdTotalDay" showPleaseSelect="false"
						  title="${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }"  style="width:35%;height:30px;margin-right:7px;"
						  value="${sysTimeCommonTimeForm.fdTotalDay }">
				<xform:simpleDataSource value='1.0'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }</xform:simpleDataSource>
				<xform:simpleDataSource value='0.5'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.half') }</xform:simpleDataSource>
			</xform:select>

		</td>
		<td class="td_normal_title" width="15%">
		</td>
		<td  >
		</td>

	</tr>

	<tr>
		<td colspan=4>
			
			<c:choose>
				<c:when test="${sysTimeWorkForm.timeType == '1'}">
					<table class="tb_normal" width=100% id="commonDetail">
						<tr>
							<td class="td_normal_title" align="center" width=20%>
								<bean:message  bundle="sys-time" key="sysTimeWorkTime.fdWorkStartTime"/>
							</td>
							<td class="td_normal_title" align="center" width=26%>
								<bean:message  bundle="sys-time" key="sysTimeWorkTime.fdWorkEndTime"/>
							</td>
								<%-- 最早打卡 --%>
							<td  class="td_normal_title" align="center" width=20%>
									${ lfn:message('sys-time:sysTimeCommonTime.earliest.startTime') }
							</td>
							<td  class="td_normal_title" align="center" width=26%>
									${ lfn:message('sys-time:sysTimeCommonTime.latest.endTime') }
							</td>
						</tr>					
					</table>
				</c:when>
				<c:otherwise>
					<table class="tb_normal" width=100% id="TABLE_DocList">
						<tr>
							<td class="td_normal_title" align="center" width=5%>
								<bean:message  bundle="sys-time" key="table.sysTimeWorkTime"/>
							</td>
							<td class="td_normal_title" align="center" width=20%>
								<bean:message  bundle="sys-time" key="sysTimeWorkTime.fdWorkStartTime"/>
							</td>
							<td class="td_normal_title" align="center" width=26%>
								<bean:message  bundle="sys-time" key="sysTimeWorkTime.fdWorkEndTime"/>
							</td>
								<%-- 最早打卡 --%>
							<td  class="td_normal_title" align="center" width=20%>
									${ lfn:message('sys-time:sysTimeCommonTime.earliest.startTime') }
							</td>
							<td  class="td_normal_title" align="center" width=26%>
									${ lfn:message('sys-time:sysTimeCommonTime.latest.endTime') }
							</td>
						</tr>
						<c:set var="__detailList" value="${sysTimeWorkForm.sysTimeCommonTimeForm.sysTimeWorkDetails}"></c:set>
						<c:if test="${empty __detailList}">
							<c:set var="__detailList" value="${sysTimeWorkForm.sysTimeWorkTimeFormList}"></c:set>
						</c:if>
						
						<c:forEach items="${__detailList}" var="sysTimeWorkTimeForm" varStatus="vstatus">
						<tr>
							<td>
								<center>
									${vstatus.index+1}
								</center>
							</td>
							<td>
							    <input type="hidden"   name="sysTimeWorkTimeFormList[${vstatus.index}].fdWorkId" value = "${sysTimeWorkForm.fdId}"/>
							    <input type="hidden"   name="sysTimeWorkTimeFormList[${vstatus.index}].fdId" value = "${sysTimeWorkTimeForm.fdId}"/>
								<center>
									<c:out value="${sysTimeWorkTimeForm.fdWorkStartTime}" />
								</center>
							</td>	
							<td>
								<center>
								 	<c:out value="${sysTimeWorkTimeForm.fdWorkEndTime}" />
								 	<c:if test="${empty sysTimeWorkTimeForm.fdOverTimeType || sysTimeWorkTimeForm.fdOverTimeType eq '1'}">
					          		          （${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }）
					          	    </c:if>
					          	    <c:if test="${sysTimeWorkTimeForm.fdOverTimeType eq '2'}">
					          		           （${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }）
					          	    </c:if>
								</center>
							</td>

							<td>
								<center>
									<c:out value="${sysTimeWorkTimeForm.fdStartTime}" />
								</center>
							</td>
							<td>
								<center>
									<c:out value="${sysTimeWorkTimeForm.fdOverTime}" />
									<c:if test="${empty sysTimeWorkTimeForm.fdEndOverTimeType || sysTimeWorkTimeForm.fdEndOverTimeType eq '1'}">
										（${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }）
									</c:if>
									<c:if test="${sysTimeWorkTimeForm.fdEndOverTimeType eq '2'}">
										（${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }）
									</c:if>
								</center>
							</td>

						</tr>	
						</c:forEach>
					</table>

				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
</center>

<script>

	Com_IncludeFile("jquery.js");

	var commonDetail = $('#commonDetail');

	// 判断是通用班次获取班次数据并渲染出来
	if('${sysTimeWorkForm.timeType}' == '1') {
		
		$.getJSON(Com_Parameter.ContextPath + 'sys/time/sys_time_common_time/sysTimeCommonTime.do?method=commonTimeById&fdId=${sysTimeWorkForm.sysTimeCommonId}').then(function(res) {
			
			var detail = res[0];
			if(detail) {
				
				$.each(detail.times || [], function(_, d) {
					var str = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type1") })';
					if(d.overTimeType == 2){
						str = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type2") })';
					}

					var str2 = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type1") })';
					if(d.endOverTimeType == 2){
						str2 = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type2") })';
					}
					$('<tr/>').append($('<td align="center">' + d.start + '</td>'))
							.append($('<td align="center">' + d.end + ' ' + str + '</td>'))
							.append($('<td align="center">' + (d.beginTime ? d.beginTime:' ') +'</td>'))
							.append($('<td align="center">' + (d.overTime ? (d.overTime + ' ' + str2) : ' ') + '</td>'))
							.appendTo(commonDetail);
					
				});
				
			}

		});

	}

</script>


<%@ include file="/resource/jsp/view_down.jsp"%>