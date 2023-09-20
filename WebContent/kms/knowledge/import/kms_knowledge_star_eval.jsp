<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.util.StringUtil"%>

<!-- starTitle 星星图标title -->
<c:set var="starTitle" value="${ param.starTitle }"/>

<!-- starBoxStyle 星星盒子样式 -->
<c:set var="starBoxStyle" value="${ param.starBoxStyle }"/>

<!-- starDarkColor Bad星星颜色 默认 #333333 -->
<c:set var="starDarkColor" value="${ not empty param.starDarkColor? param.starDarkColor : '#333333' }"/>

<!-- starColor Good星星颜色 默认 #FF943E -->
<c:set var="starColor" value="${ not empty param.starColor? param.starColor : '#FF943E' }"/>

<!-- starScore 评分 默认 0.0 -->
<c:set var="starScore" value="${ not empty param.starScore? param.starScore : 0.0 }"/>

<!-- starTotal 星星个数 默认 5 -->
<c:set var="starTotal" value="${ not empty param.starTotal? param.starTotal : 5}"/>

<!-- starSize 星星大小 默认 14px -->
<c:set var="starSize" value="${ not empty param.starSize? param.starSize : 14 }"/>

<!-- starNumSize 星星数字大小 默认 14px -->
<c:set var="starNumSize" value="${ not empty param.starNumSize? param.starNumSize : 14 }"/>

<!-- starTextSize 星星文字大小 默认 12px -->
<c:set var="starTextSize" value="${ not empty param.starTextSize? param.starTextSize : 12 }"/>

<!-- starNumColor 星星数字颜色 默认 #ff943e -->
<c:set var="starNumColor" value="${ not empty param.starNumColor? param.starNumColor : '#ff943e' }"/>

<!-- starTextColor 星星文字颜色 默认 #ff943e -->
<c:set var="starTextColor" value="${ param.starTextColor }"/>

<!-- starText 自定义中文描述  默认显示 -->
<c:set var="starText" value="${ param.starText }"/>

<!-- showText 是否显示中文描述 默认显示-->
<c:set var="showText" value="${ param.showText }"/>

<!-- showNum 是否显示数字  默认显示-->
<c:set var="showNum" value="${ param.showNum }"/>

<!-- starOffset 高亮星星填充偏移量 默认 0 -->
<c:set var="starOffset" value="${ not empty param.starOffset? param.starOffset : 0 }"/>
<% 
	String total = String.valueOf(pageContext.getAttribute("starTotal"));
	String showNum = String.valueOf(pageContext.getAttribute("showNum"));
	Double starTotal = Double.valueOf(total);
	Double starOffset = Double.valueOf(String.valueOf(pageContext.getAttribute("starOffset")));
	Double starSize = Double.valueOf(String.valueOf(pageContext.getAttribute("starSize")));
	Double starNumSize = Double.valueOf(String.valueOf(pageContext.getAttribute("starNumSize")));
	Double starScore = Double.valueOf(String.valueOf(pageContext.getAttribute("starScore")));
	Object[] arr = new Object[starTotal.intValue()];
	Double starScale = starScore / starTotal;
	Double sizeOffset = 3d;
	if(starScale >= 0.5) {
		sizeOffset = 3.5;
		starOffset += 1d;
	}
	Double starTotalWidth = (starSize+ sizeOffset.doubleValue()) * starTotal;
	Double numLeft = starTotalWidth + 5;
	pageContext.setAttribute("starArr", arr);
	pageContext.setAttribute("starScale", starScale);
	pageContext.setAttribute("starScore", starScore);
	pageContext.setAttribute("starTotal", starTotal);
	pageContext.setAttribute("starOffset", starOffset);
	pageContext.setAttribute("starTotalWidth", starTotalWidth);
	Double starTextLeft = starTotalWidth + starNumSize* (total.length() + 1);
	if(StringUtil.isNotNull(showNum) && "false".equals(showNum)) {
		starTextLeft = starTotalWidth + 6;
	}
	pageContext.setAttribute("starTextLeft", starTextLeft);
%>

<script>
	seajs.use(['${KMSS_Parameter_ContextPath}kms/knowledge/import/style/star.css']);
</script>

<div class="kms_star_eval" title="${ starTitle }" style="width: ${ starTotalWidth + 100 }px;${starBoxStyle}">
	<div class="kms_star_starbad_div_mark">
		<div class="kms_star_starbad_div">
		    <c:forEach items="${ starArr }">
		    	<c:import url="/kms/knowledge/import/svg/starDarkSvg.jsp">
		    		<c:param name="starSize" value="${ starSize }"></c:param>
		    		<c:param name="starColor" value="${ starDarkColor }"></c:param>
		    	</c:import>
		    </c:forEach>
		</div>
	</div>
	
	<!-- width: ((starSize+3)*5 + 1) * (4.5/5) px -->
	<div class="kms_star_stargood_div_mark" style="width: ${ starTotalWidth * starScale + starOffset }px">
		<div class="kms_star_stargood_div">
			<c:forEach items="${ starArr }">
			 	<c:import url="/kms/knowledge/import/svg/starSvg.jsp">
		    		<c:param name="starSize" value="${ starSize }"></c:param>
		    		<c:param name="starColor" value="${ starColor }"></c:param>
		    	</c:import>
		    </c:forEach>
		</div>
	</div>

	<c:if test="${ showNum != 'false' }">
		<span class="kms_star_eval_score_mark" 
			style="left: ${ starTotalWidth + 6 }px; font-size: ${ starNumSize }px; color: ${ starNumColor }">
			<c:out value="${ starScore }"></c:out>
		</span>
	</c:if>
	
	<c:if test="${ not empty starText }">
		<span 
			class="kms_star_eval_score_title" 
			style="
				left: ${ starTextLeft }px;
				font-size: ${ starTextSize }px;
				color: ${ starTextColor? starTextColor : '#ff943e' }">
			<c:out value="${ starText }"></c:out>
		</span>
	</c:if>
	
	<c:if test="${ empty starText && showText != 'false'}">
		<span class="kms_star_eval_score_title" 
			style="left: ${ starTextLeft + 5 }px; font-size: ${ starTextSize }px;">
			<c:choose>
				<c:when test="${ starScore == 0 }">
					<span style="color: ${ starTextColor? starTextColor : '#666666' }" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.star.1')}">${ lfn:message('kms-knowledge:kmsKnowledge.index.star.1')}</span>
				</c:when>
				<c:when test="${ starScore > 0 && starScore <= (starTotal/5) }">
					<span style="color: ${ starTextColor? starTextColor : '#ffc107' }" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.star.2')}">${ lfn:message('kms-knowledge:kmsKnowledge.index.star.2')}</span>
				</c:when>
				<c:when test="${ starScore > 1 && starScore <= (2*starTotal/5) }">
					<span style="color: ${ starTextColor? starTextColor : '#ff9800' }" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.star.3')}">${ lfn:message('kms-knowledge:kmsKnowledge.index.star.3')}</span>
				</c:when>
				<c:when test="${ starScore > 2 && starScore <= (3*starTotal/5) }">
					<span style="color: ${ starTextColor? starTextColor : '#ff5722' }" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.star.4')}">${ lfn:message('kms-knowledge:kmsKnowledge.index.star.4')}</span>
				</c:when>
				<c:when test="${ starScore > 3 && starScore <= (4*starTotal/5) }">
					<span style="color: ${ starTextColor? starTextColor : '#f44336' }" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.star.5')}">${ lfn:message('kms-knowledge:kmsKnowledge.index.star.5')}</span>
				</c:when>
				<c:when test="${ starScore > 4 && starScore <= (5*starTotal/5) }">
					<span style="color: ${ starTextColor? starTextColor : '#FF0000' }" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.star.6')}">${ lfn:message('kms-knowledge:kmsKnowledge.index.star.6')}</span>
				</c:when>
			</c:choose>
		</span>
	</c:if>
</div> 