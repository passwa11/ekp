<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:set var="fillText" value="${param.fillText}"/>
<c:set var="svgStyle" value="${param.svgStyle}"/>
<c:set var="colorType" value="${param.colorType}"/>
<c:if test="${param.fontSize != null}">
    <c:set var="fontSize" value="${param.fontSize}"/>
</c:if>

<svg width="64px" height="20px" style="${ svgStyle }" viewBox="0 0 64 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
   	<defs>
       	<linearGradient x1="100%" y1="50%" x2="0%" y2="50%" id="linearGradient-1">
           	<stop stop-color="#FFBA82" offset="0%"></stop>
           	<stop stop-color="#FF943E" offset="100%"></stop>
       	</linearGradient>
       	<linearGradient x1="100%" y1="50%" x2="0%" y2="50%" id="linearGradient-2">
           	<stop stop-color="#2196f3" offset="0%"></stop>
           	<stop stop-color="#0787f5" offset="100%"></stop>
       	</linearGradient>
   	</defs>
   	<g id="控件" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
       	<g id="icon整合" transform="translate(-6.000000, -123.000000)">
           	<g id="通用/标签-需要借阅" transform="translate(6.000000, 123.000000)">
               	<g id="编组-31" transform="translate(0.394602, 0.000000)">
                   	<path d="M7.26743614,0 L60.6053982,0 C61.7099677,3.72131305e-15 62.6053982,0.8954305 62.6053982,2 L62.6053982,18 C62.6053982,19.1045695 61.7099677,20 60.6053982,20 L7.26743614,20 C6.5946721,20 5.96698417,19.6617569 5.59701226,19.0998564 L0.329576123,11.0998564 C-0.109858708,10.4324579 -0.109858708,9.56754205 0.329576123,8.90014361 L5.59701226,0.900143613 C5.96698417,0.338243056 6.5946721,5.67673959e-16 7.26743614,0 Z M5.60539821,8 C6.15768296,8 6.60539821,8.44771525 6.60539821,9 L6.60539821,11 C6.60539821,11.5522847 6.15768296,12 5.60539821,12 C5.05311346,12 4.60539821,11.5522847 4.60539821,11 L4.60539821,9 C4.60539821,8.44771525 5.05311346,8 5.60539821,8 Z" id="形状结合" 
                   		fill="url(#linearGradient-${ colorType == '2'? 2 : 1 })"></path>
                   	<text id="需要借阅" font-family="PingFangSC-Regular, PingFang SC" font-size="${ fontSize? fontSize:12 }" font-weight="normal" line-spacing="14" fill="#FFFFFF">
                       	<tspan x="9.75189699" y="14">${ fillText }</tspan>
                   	</text>
               	</g>
           	</g>
       	</g>
   	</g>
</svg>

<script type="text/template" id="borrow_svg_tmpl">
    {$
	<svg width="64px" height="20px" style="{%( _style )%}" viewBox="0 0 64 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
    	<defs>
        	<linearGradient x1="100%" y1="50%" x2="0%" y2="50%" id="linearGradient-1">
            	<stop stop-color="#FFBA82" offset="0%"></stop>
            	<stop stop-color="#FF943E" offset="100%"></stop>
        	</linearGradient>
    	</defs>
    	<g id="控件" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        	<g id="icon整合" transform="translate(-6.000000, -123.000000)">
            	<g id="通用/标签-需要借阅" transform="translate(6.000000, 123.000000)">
                	<g id="编组-31" transform="translate(0.394602, 0.000000)">
                    	<path d="M7.26743614,0 L60.6053982,0 C61.7099677,3.72131305e-15 62.6053982,0.8954305 62.6053982,2 L62.6053982,18 C62.6053982,19.1045695 61.7099677,20 60.6053982,20 L7.26743614,20 C6.5946721,20 5.96698417,19.6617569 5.59701226,19.0998564 L0.329576123,11.0998564 C-0.109858708,10.4324579 -0.109858708,9.56754205 0.329576123,8.90014361 L5.59701226,0.900143613 C5.96698417,0.338243056 6.5946721,5.67673959e-16 7.26743614,0 Z M5.60539821,8 C6.15768296,8 6.60539821,8.44771525 6.60539821,9 L6.60539821,11 C6.60539821,11.5522847 6.15768296,12 5.60539821,12 C5.05311346,12 4.60539821,11.5522847 4.60539821,11 L4.60539821,9 C4.60539821,8.44771525 5.05311346,8 5.60539821,8 Z" id="形状结合" fill="url(#linearGradient-1)"></path>
                    	<text id="需要借阅" font-family="PingFangSC-Regular, PingFang SC" font-size="{%( _fontSize )%}" font-weight="normal" line-spacing="14" fill="#FFFFFF">
                        	<tspan x="9.75189699" y="14">{%( _fillText )%}</tspan>
                    	</text>
                	</g>
            	</g>
        	</g>
    	</g>
	</svg>
    $}
</script>