<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="lui_list_create_frame">
    <!-- 人员卡片 Starts -->
    <div class="lui-navtitle-card">
    	
      <c:choose>
      	<c:when test="${not empty varParams.total}">
      		<div class="lui_knowledge_sum_card">
			    <div class="lui_knowledge_sum_card_title">
			        <ui:dataview format="sys.ui.navtitle.total">
						${varParams.total}
						<ui:render ref="sys.ui.navtitle.total"/>
					</ui:dataview>
			    </div>
			</div>
			<script src="${LUI_ContextPath}/sys/ui/extend/dataview/render/cycle.js"> </script>
			<script src="${LUI_ContextPath}/sys/ui/extend/dataview/render/raphael.js"> </script>
    	</c:when>
      	<c:otherwise>
      		<div class="lui-navtitle-card-head">
      			<img src="<person:headimageUrl personId="${KMSS_Parameter_CurrentUserId}" size="b" contextPath="true" />" id="sys_person_userpic_img" />
      		</div>
      	</c:otherwise>
      </c:choose>
      	
      	<c:if test="${not empty varParams.title}">
      		<div class="lui-navtitle-card-title">${varParams.title}</div>
      	</c:if>
      	
      	<c:if test="${not empty varParams.info}">
      	 <ui:dataview format="sys.ui.navtitle.info">
			${varParams.info}
			<ui:render ref="sys.ui.navtitle.info"/>
		</ui:dataview>
		</c:if>
      	<c:if test="${not empty varParams.infonew}">
	      	 <ui:dataview format="sys.ui.navtitle.info.new">
				${varParams.infonew}
				<ui:render ref="sys.ui.navtitle.info.new"/>
			</ui:dataview>
		</c:if>
		<c:if test="${not empty varParams.operation}">
		 <ui:dataview format="sys.ui.navtitle.operation">
			${varParams.operation}
			<ui:render ref="sys.ui.navtitle.operation"/>
		</ui:dataview> 
		</c:if>
    </div>
 </div>
   

