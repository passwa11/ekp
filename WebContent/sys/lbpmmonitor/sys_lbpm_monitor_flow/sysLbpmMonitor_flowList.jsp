<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmProcess" list="${queryPage.list }"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdModelName">
		</list:data-column>
		<list:data-column col="index">
		     ${status+1}
		</list:data-column>
		<c:if test="${not empty processRestart}">
			<!--标题-->
			<list:data-column col="subject"
				title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docSubject') }"
				escape="false" style="text-align:left;min-width:180px">
				<c:if test="${not empty subjectMap[lbpmProcess.fdId]}">
		             <span class="com_subject">${subjectMap[lbpmProcess.fdId]}</span>
		        </c:if>
			</list:data-column>
			<!--操作者-->
			<list:data-column col="operator"
				title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.operator') }"
				escape="false" style="text-align:center;">
				<c:if test="${not empty operator[lbpmProcess.fdId]}">
		             ${operator[lbpmProcess.fdId]}
		        </c:if>
			</list:data-column>
			<!--重启时间-->
			<list:data-column col="restartDate"
				title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.restartDate') }"
				escape="false" style="text-align:center;">
				<c:if test="${not empty restartDate[lbpmProcess.fdId]}">
		             <kmss:showDate value="${restartDate[lbpmProcess.fdId]}" type="datetime"/>
		        </c:if>
			</list:data-column>
			<!--重启次数-->
			<list:data-column col="restartCount"
				title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.restartCount') }"
				escape="false" style="text-align:center;">
				<c:if test="${not empty restartCount[lbpmProcess.fdId]}">
		             ${restartCount[lbpmProcess.fdId]}
		        </c:if>
			</list:data-column>
	    </c:if>
	    <c:if test="${empty processRestart}">
	    	<c:if test="${param.method == 'getLimitExpired' || param.method == 'getExpired'}">
				<!--标题-->
				<list:data-column col="subject"
					title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docSubject') }"
					escape="false" headerClass="width80" style="text-align:left;min-width:180px">
					<c:if test="${not empty subjectMap[lbpmProcess.fdId]}">
			             <div class="textEllipsis width80" title='<c:out value="${subjectMap[lbpmProcess.fdId]}"></c:out>'>
							${subjectMap[lbpmProcess.fdId]}
						</div>
			        </c:if>
				</list:data-column>	
			</c:if>
			<c:if test="${param.method != 'getLimitExpired' && param.method != 'getExpired'}">
				<!--标题-->
				<list:data-column col="subject"
					title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docSubject') }"
					escape="false" style="text-align:left;min-width:180px">
					<c:if test="${not empty subjectMap[lbpmProcess.fdId]}">
			             <span class="com_subject">${subjectMap[lbpmProcess.fdId]}</span>
			        </c:if>
				</list:data-column>
			</c:if>
			<!--创建人-->
			<list:data-column headerStyle="width:8%" property="fdCreator.fdName"
				title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthor') }">
			</list:data-column>
			<!--创建时间-->
			<list:data-column headerStyle="width:130px" property="fdCreateTime"
				title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthorTime') }">
			</list:data-column>
			<!--结束时间-->
			<c:if test="${param.fdType=='finish'}">
				<list:data-column headerStyle="width:130px" property="fdEndedTime"
					title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.endTime') }">
				</list:data-column>
			</c:if>
			<!--状态-->
			<c:if test="${param.method == 'getRecentHandle' || param.method == 'getInvalidHandler' || lbpmProcess.fdStatus!='21' || (param.method == 'listChildren' && param.fdStatus == 'all')}">
				<c:if test="${param.method != 'getExpired' && param.method != 'getLimitExpired'}">
					<list:data-column headerStyle="width:10%" col="fdStatus"
						title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.status') }"
						escape="false">
						<c:if test="${lbpmProcess.fdStatus=='20'}">
							${ lfn:message('sys-lbpmmonitor:status.activated') }
						</c:if>
						<c:if test="${lbpmProcess.fdStatus=='21'}">
							${ lfn:message('sys-lbpmmonitor:status.error') }
						</c:if>
						<c:if test="${lbpmProcess.fdStatus=='30'}">
							${ lfn:message('sys-lbpmmonitor:status.completed') }
						</c:if>
						<c:if test="${lbpmProcess.fdStatus=='00'}">
							${ lfn:message('sys-lbpmmonitor:status.discard') }
						</c:if>
						<c:if test="${lbpmProcess.fdStatus=='40'}">
							${ lfn:message('sys-lbpmmonitor:status.suspend') }
						</c:if>
					</list:data-column>
				</c:if>
			</c:if>
			<c:if test="${param.fdType!='finish' || (param.method == 'listChildren' && param.fdStatus == 'all')}">
				<!--当前处理（节点）-->
				<list:data-column headerClass="width100" col="nodeName" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.nodeName') }" escape="false">
					<kmss:showWfPropertyValues var="nodevalue" idValue="${lbpmProcess.fdId}" propertyName="nodeName" />
					    <div class="textEllipsis width100" style="width:100px;" title='<c:out value="${nodevalue}"></c:out>'>
					        <c:out value="${nodevalue}"></c:out>
					    </div>
				</list:data-column>
				<!--当前处理人-->
				<list:data-column headerClass="width100" col="handlerName" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.currentHandler') }" escape="false">
				   <kmss:showWfPropertyValues var="handlerValue" idValue="${lbpmProcess.fdId}" propertyName="handlerName" />
					    <div class="textEllipsis width80" style="font-weight:bold;" title='<c:out value="${handlerValue}"></c:out>'>
					        <c:out value="${handlerValue}"></c:out>
					    </div>
				</list:data-column>
			</c:if>
	    </c:if>
		
		
		<c:if test="${param.method == 'getLimitExpired' || param.method == 'getExpired' || param.method == 'getDraftExpired'}">
			<c:if test="${param.method == 'getLimitExpired' || param.method == 'getExpired'}">
				<!--送审时间-->
				<list:data-column headerStyle="width:130px" col="arrivalTime"
					title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.arrivalTime') }"
					escape="false" >
					<c:if test="${not empty arrivalTimeMap[lbpmProcess.fdId]}">
						${arrivalTimeMap[lbpmProcess.fdId]}
					</c:if>
				</list:data-column>
			</c:if>
			<!--办理时限-->
			<list:data-column headerStyle="width:75px" col="timeLimit"
				title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.timeLimit') }"
				escape="false" >
				<c:if test="${not empty timeLimitMap[lbpmProcess.fdId]}">
					${timeLimitMap[lbpmProcess.fdId]}
				</c:if>
			</list:data-column>
			<!--超时时间-->
			<list:data-column headerStyle="width:100px" col="timeout"
				title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.timeout') }"
				escape="false" >
				<c:if test="${not empty timeoutMap[lbpmProcess.fdId]}">
					${timeoutMap[lbpmProcess.fdId]}
				</c:if>
			</list:data-column>
			<list:data-column headerStyle="width:100px" col="operations" title="${ lfn:message('list.operation') }" escape="false" style="min-width:50px">
				<!--操作按钮 开始-->
				<div class="conf_show_more_w">
					<div class="conf_btn_edit">
						<!-- 催办 -->
						<a class="btn_txt" href="javascript:onekeyPress('${lbpmProcess.fdId}')">${ lfn:message('sys-lbpmservice:lbpm.operation.admin_press') }</a>
					</div>
				</div>
				<!--操作按钮 结束-->
			</list:data-column>
		</c:if>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>