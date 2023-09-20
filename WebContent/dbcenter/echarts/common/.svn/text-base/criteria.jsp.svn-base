<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:criteria>
 	<c:forEach items="${info.criteriaList}" var="criteria">
 	     <c:choose>
			<c:when test="${(not empty criteria.showForm ) and (criteria.showForm eq 'rangeQuery')}">
				<list:cri-auto title="${criteria.name}"  modelName="${criteria.modelName}" property="${criteria.fieldkey}" expand="false" type="lui/criteria/criterion_calendar!CriterionSingle${criteria.format }Datas"/>	
			</c:when>
			<c:when test="${not empty criteria.orgType and criteria.orgType eq 'ORG_TYPE_PERSON'}">
				<list:cri-ref ref="criterion.sys.person" key="${criteria.name}" title="${criteria.text}" expand="false" multi="${criteria.muti}"/>
			</c:when>
			<c:when test="${not empty criteria.orgType and criteria.orgType eq 'ORG_TYPE_DEPT'}">
				<list:cri-ref ref="criterion.sys.dept" key="${criteria.name}" title="${criteria.text}" expand="false" multi="${criteria.muti}"/>
			</c:when>
			<c:when test="${not empty criteria.orgType and (criteria.orgType eq 'ORG_TYPE_POST' or criteria.orgType eq 'ORG_TYPE_POSTORPERSON')}">
				<list:cri-ref ref="criterion.sys.postperson" key="${criteria.name}" title="${criteria.text}" expand="false" multi="${criteria.muti}"/>
			</c:when>
			<c:otherwise>
				<list:cri-criterion title="${criteria.name}" key="${criteria.key}" expand="false" multi="${criteria.multi}">
					<list:box-select>
						<c:if test="${empty criteria.options}">
							<c:choose>
								<c:when test="${(not empty criteria.format) and (criteria.format eq 'DateTime') or (criteria.format eq 'Time') or (criteria.format eq 'Date')}">
									<list:item-select type="lui/criteria/criterion_calendar!CriterionSingle${criteria.format }Datas" cfg-enable="${criteria.hide=='true'? false:true}"/>
								</c:when>
								<c:otherwise>
									<list:item-select cfg-enable="${criteria.hide=='true'? false:true}" type="lui/criteria/criterion_input!TextInput" />
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${not empty criteria.options}">
							<c:if test="${empty criteria.defaultValue}">
								<list:item-select>
									<ui:source type="Static">${criteria.options}</ui:source>
								</list:item-select>
							</c:if>
							<c:if test="${not empty criteria.defaultValue}">
								<list:item-select cfg-required="false" cfg-defaultValue="${criteria.defaultValue}">
									<ui:source type="Static">${criteria.options}</ui:source>
								</list:item-select>
							</c:if>
						</c:if>
					</list:box-select>
				</list:cri-criterion>
			</c:otherwise>
		</c:choose>
 	   
 		
 	</c:forEach>
 </list:criteria>