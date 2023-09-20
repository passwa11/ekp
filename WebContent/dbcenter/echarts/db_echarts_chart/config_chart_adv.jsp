<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="config_item">
	${ lfn:message('dbcenter-echarts:tuxingkuandu') }<input class="inputsgl" name="width" data-dbecharts-config="fdConfig">
</div>
<div class="config_item">
	${ lfn:message('dbcenter-echarts:tuxinggaodu') }<input class="inputsgl" name="height" data-dbecharts-config="fdConfig">
</div>
<div class="config_item2">
	${ lfn:message('dbcenter-echarts:jieshoucanshu') }<input class="inputsgl" name="chart.keys" style="width:380px;" data-dbecharts-config="fdCode">
</div>
<br><br>
<div class="config_item3">
    ${ lfn:message('dbcenter-echarts:loadMapJsFile') }
    <input type="hidden" json='${loadMapJs}' name="loadMapJs" value='<c:if test="true">${loadMapJs}</c:if>'
        id="loadMapJs" data-dbecharts-config="fdConfig">
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'china.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'china.js');">${ lfn:message('dbcenter-echarts:chinaMap') }</label>
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'anhui.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'anhui.js');">${ lfn:message('dbcenter-echarts:anhuiMap') }</label>
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'aomen.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'aomen.js');">${ lfn:message('dbcenter-echarts:aomenMap') }</label>
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'beijing.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'beijing.js');">${ lfn:message('dbcenter-echarts:beijingMap') }</label>        
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'chongqing.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'chongqing.js');">${ lfn:message('dbcenter-echarts:chongqingMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'fujian.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'fujian.js');">${ lfn:message('dbcenter-echarts:fujianMap') }</label>
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'gansu.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'gansu.js');">${ lfn:message('dbcenter-echarts:gansuMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'guangdong.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'guangdong.js');">${ lfn:message('dbcenter-echarts:guangdongMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'guangxi.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'guangxi.js');">${ lfn:message('dbcenter-echarts:guangxiMap') }</label>  
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'guizhou.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'guizhou.js');">${ lfn:message('dbcenter-echarts:guizhouMap') }</label> <br/>     
        
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'hainan.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'hainan.js');">${ lfn:message('dbcenter-echarts:hainanMap') }</label>      
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'hebei.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'hebei.js');">${ lfn:message('dbcenter-echarts:hebeiMap') }</label>      
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'heilongjiang.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'heilongjiang.js');">${ lfn:message('dbcenter-echarts:heilongjiangMap') }</label>        
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'henan.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'henan.js');">${ lfn:message('dbcenter-echarts:henanMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'hubei.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'hubei.js');">${ lfn:message('dbcenter-echarts:hubeiMap') }</label>
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'hunan.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'hunan.js');">${ lfn:message('dbcenter-echarts:hunanMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'jiangsu.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'jiangsu.js');">${ lfn:message('dbcenter-echarts:jiangsuMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'jiangxi.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'jiangxi.js');">${ lfn:message('dbcenter-echarts:jiangxiMap') }</label>  
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'jilin.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'jilin.js');">${ lfn:message('dbcenter-echarts:jilinMap') }</label>  
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'liaoning.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'liaoning.js');">${ lfn:message('dbcenter-echarts:liaoningMap') }</label>      
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'neimenggu.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'neimenggu.js');">${ lfn:message('dbcenter-echarts:neimengguMap') }</label>      
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'ningxia.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'ningxia.js');">${ lfn:message('dbcenter-echarts:ningxiaMap') }</label>        
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'qinghai.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'qinghai.js');">${ lfn:message('dbcenter-echarts:qinghaiMap') }</label><br/>  
            
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'shandong.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'shandong.js');">${ lfn:message('dbcenter-echarts:shandongMap') }</label>
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'shanghai.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'shanghai.js');">${ lfn:message('dbcenter-echarts:shanghaiMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'shanxi.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'shanxi.js');">${ lfn:message('dbcenter-echarts:shanxiMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'shanxi1.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'shanxi1.js');">${ lfn:message('dbcenter-echarts:shanxi1Map') }</label>  
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'sichuan.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'sichuan.js');">${ lfn:message('dbcenter-echarts:sichuanMap') }</label>
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'taiwan.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'taiwan.js');">${ lfn:message('dbcenter-echarts:taiwanMap') }</label>      
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'tianjin.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'tianjin.js');">${ lfn:message('dbcenter-echarts:tianjinMap') }</label>      
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'xianggang.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'xianggang.js');">${ lfn:message('dbcenter-echarts:xianggangMap') }</label>        
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'xinjiang.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'xinjiang.js');">${ lfn:message('dbcenter-echarts:xinjiangMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'xizang.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'xizang.js');">${ lfn:message('dbcenter-echarts:xizangMap') }</label>
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'shanghai.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'shanghai.js');">${ lfn:message('dbcenter-echarts:shanghaiMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'yunnan.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'yunnan.js');">${ lfn:message('dbcenter-echarts:yunnanMap') }</label>    
    <label><input type="checkbox" <c:if test="${fn:contains(loadMapJs, 'zhejiang.js')}">checked</c:if>
        onchange="changeLoadMapJs(this.checked,'zhejiang.js');">${ lfn:message('dbcenter-echarts:zhejiangMap') }</label><br/>  
</div>
<div><br><br>&nbsp;${ lfn:message('dbcenter-echarts:tubiaopeizhi') }:</div>
	<a href="javascript:_showHelpLogInfo('optionsPropertiesAdvHelp','500px','400px',true);"><span id="optionsPropertiesAdvHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
<br>
<textarea name="chart.option" style="width:99%;height:300px;" data-dbecharts-config="fdCode" validate="required" subject="${ lfn:message('dbcenter-echarts:tubiaopeizhi') }" 
	placeholder="${ lfn:message('dbcenter-echarts:adv_placeholder_1') }"></textarea>

<script>
    function changeLoadMapJs(isAdd,jsFileName){
    	var loadMapJs = $("#loadMapJs");
    	var valueStr = loadMapJs.attr("json") || "[]";
        var jsFileMap = JSON.parse(valueStr);
    	if(isAdd) {
    		var isExist = false;
    		for(var i = 0; i < jsFileMap.length; i ++) {
    			 if(jsFileMap[i] === jsFileName) {
    				isExist = true;
    				break;
    			}  
    		} 
            if(!isExist) {
                jsFileMap.push(jsFileName);
            } 
    	}else{
    		var index = jsFileMap.indexOf(jsFileName);
    		index > -1 && jsFileMap.splice(jsFileName, 1);
    	}
    	loadMapJs.attr("json",JSON.stringify(jsFileMap));
    	loadMapJs.val(JSON.stringify(jsFileMap));
    }
</script>