<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:set var="svgTitle" value="${param.svgTitle}"/>
<c:set var="svgStyle" value="${param.svgStyle}"/>
<c:set var="svgColor" value="${param.svgColor}"/>
<c:set var="iconSize" value="${param.iconSize}"/>

<span class="svg_reviews_box" title="${ svgTitle }">
<svg width="${ iconSize }px" height="${ iconSize }px" style="${ svgStyle }" viewBox="0 0 14 14" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
    <g id="控件" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <g id="icon整合" transform="translate(-77.000000, -95.000000)">
            <g id="通用/操作类-点评" transform="translate(77.000000, 95.000000)">
                <rect id="矩形" x="0" y="0" width="14" height="14"></rect>
                <path d="M13,2 L13,10 L10.24,10 L10.2404851,12 L5.06,10 L1,10 L1,2 L13,2 Z M12,3 L2,3 L2,9 L5.24633024,9 L9.24,10.542 L9.23975741,9 L12,9 L12,3 Z M7,7 L7,8 L4,8 L4,7 L7,7 Z M10,4 L10,5 L4,5 L4,4 L10,4 Z" 
                	id="形状结合" fill="${ svgColor? svgColor : '#333333' }" fill-rule="nonzero"></path>
            </g>
        </g>
    </g>
</svg>
</span>