<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:set var="svgTitle" value="${param.svgTitle}"/>
<c:set var="svgStyle" value="${param.svgStyle}"/>
<c:set var="svgColor" value="${param.svgColor}"/>
<c:set var="iconSize" value="${param.iconSize}"/>

<span class="svg_browse_box" title="${ svgTitle }">
<svg width="${ iconSize }px" height="${ iconSize }px" style="${ svgStyle }" viewBox="0 0 14 14" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
    <g id="控件" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <g id="icon整合" transform="translate(-9.000000, -95.000000)">
            <g id="通用/操作类-浏览数" transform="translate(9.000000, 95.000000)">
                <rect id="矩形" x="0" y="0" width="14" height="14"></rect>
                <path d="M6.99812993,2.0681513 C9.8611425,2.0681513 11.8617659,3.71210087 13,7 C11.8887725,10.3439561 9.88877247,12.0159342 7,12.0159342 C4.11122753,12.0159342 2.11122753,10.3439561 1,7 C2.13574072,3.71210087 4.13511736,2.0681513 6.99812993,2.0681513 Z M6.99812993,3.0681513 C4.75229173,3.0681513 3.17210641,4.24432221 2.15464829,6.76558067 L2.061,7.005 L2.14756117,7.23766325 C3.11294248,9.72644107 4.62157138,10.9314962 6.76762254,11.0116441 L7,11.0159342 C9.26997649,11.0159342 10.8525796,9.815326 11.8524388,7.23766325 L11.938,7.006 L11.8451216,6.7661557 C10.8620251,4.33465074 9.35643224,3.15389871 7.23624066,3.07266068 L6.99812993,3.0681513 Z M7,4.5 C8.38071187,4.5 9.5,5.61928813 9.5,7 C9.5,8.38071187 8.38071187,9.5 7,9.5 C5.61928813,9.5 4.5,8.38071187 4.5,7 C4.5,5.61928813 5.61928813,4.5 7,4.5 Z M7,5.5 C6.17157288,5.5 5.5,6.17157288 5.5,7 C5.5,7.82842712 6.17157288,8.5 7,8.5 C7.82842712,8.5 8.5,7.82842712 8.5,7 C8.5,6.17157288 7.82842712,5.5 7,5.5 Z" 
                	id="蒙版" fill="${ svgColor? svgColor : '#333333' }" fill-rule="nonzero"></path>
            </g>
        </g>
    </g>
</svg>
</span>