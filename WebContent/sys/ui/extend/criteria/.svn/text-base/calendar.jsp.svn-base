<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:cri-criterion title="${lfn:message('sys-ui:ui.criteria.date.range') }" multi="false" expand="false" key="fdCreateDate">
	<list:varDef name="title"></list:varDef>
	<list:varDef name="selectBox">
	<list:box-select>
		
			<c:choose>
				<c:when test="${not empty criterionAttrs['cfg-if']}">
					<list:item-select cfg-defaultValue="${not empty criterionAttrs['cfg-defaultValue'] ? criterionAttrs['cfg-defaultValue']:''}" type="lui/criteria/criterion_calendar!${not empty varParams['type'] ? varParams['type']:'CriterionDateDatas'}"  cfg-if="${criterionAttrs['cfg-if']}">
					</list:item-select>
				</c:when>
				<c:otherwise>
					<list:item-select cfg-defaultValue="${not empty criterionAttrs['cfg-defaultValue'] ? criterionAttrs['cfg-defaultValue']:''}" type="lui/criteria/criterion_calendar!${not empty varParams['type'] ? varParams['type']:'CriterionDateDatas'}" >
					</list:item-select>
				</c:otherwise>
			</c:choose>	
		
	</list:box-select>
	</list:varDef>
</list:cri-criterion>