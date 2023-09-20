<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.taglib.fn.LuiFunctions"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath()%>/sys/portal/varkind/resource/css/navigationSettings.css" rel="stylesheet" type="text/css">
<%
JSONObject var = JSONObject.fromObject(request.getParameter("var"));
pageContext.setAttribute("luivar", var);
pageContext.setAttribute("luivarid","var_"+IDGenerator.generateID());
pageContext.setAttribute("luivarparam",StringUtil.isNotNull(var.getString("body")) ? JSONObject.fromObject(var.get("body")) : new JSONObject());
pageContext.setAttribute("defaultFixedWidth", "120");
%>
<script>
${param['jsname']}.VarSet.push({
	"name":"${ luivar['key'] }",
	"getter":function(){
		var widthType = $("input[type='radio'][name='${luivarid}']:checked").val();
        var config = {};
        if(widthType){
        	config.widthType = widthType;
        	if(widthType=="width_fixed"){
        		var fixedWidth = $("#"+"${luivarid}_fixed_width").val();
        		config.fixedWidth = fixedWidth;
        	}
        }
        return config;
	},
	"setter":function(config){
	   var widthType = config.widthType||"width_auto";
       $("input[type='radio'][name='${luivarid}'][value='"+widthType+"']").attr("checked","checked");
       if(widthType=="width_fixed"){
    	   var fixedWidth = config.fixedWidth || "${pageScope.defaultFixedWidth}";
    	   var $fixedWidthInput = $("#"+"${luivarid}_fixed_width");
    	   $fixedWidthInput.val(fixedWidth);
    	   $fixedWidthInput.attr("disabled",false);
       }
	},
	"validation":function(){
		var val = this['getter'].call();
		if(val.widthType=="width_fixed" && $.trim(val.fixedWidth)==""){
			return "${ lfn:message('sys-portal:portlet.header.var.navigationSettings.fixedWidth.null.tip') }";
		}
	}
}); 

/**
 * 单选按钮点击事件（选择“固定宽度”时，填充文本框默认值）
 * @param radioDom 单选按钮DOM对象   
 * @return
 */
function changeWidthType(radioDom){
	var widthType = $("input[type='radio'][name='${luivarid}']:checked").val();
	var $fixedWidthInput = $("#"+"${luivarid}_fixed_width");
    if(widthType=="width_fixed"){
    	$fixedWidthInput.attr("disabled",false);
    }else{
    	$fixedWidthInput.val("${pageScope.defaultFixedWidth}");
    	$fixedWidthInput.attr("disabled",true);
    }
}


$(document).ready(function(){

    $(".lui_prompt_tooltip").hover(function() {
    	var $tooltipMenu = $(this).find(".lui_dropdown_tooltip_menu");
    	if($tooltipMenu.children().length==0){
    		var $tipContent = $("#navigation_settings_tip_content").children().clone();
        	$tooltipMenu.append($tipContent);
        	var tipType = $(this).attr("tip_type");
        	var fontCss = {"color":"#3e9ece","font-weight":"bold"};
        	if(tipType=="width_auto"){
        		$tooltipMenu.find(".width_auto_desc").css(fontCss);
        	}else if(tipType=="width_fixed"){
        		$tooltipMenu.find(".width_fixed_desc").css(fontCss);
        	}else if(tipType=="both_sides_align"){
        		$tooltipMenu.find(".both_sides_align_desc").css(fontCss);
        	}
    	}
        $(this).find(".lui_prompt_tooltip_drop").addClass('hover');
        $tooltipMenu.fadeIn(300);
      }, function() {
    	var $tooltipMenu = $(this).find(".lui_dropdown_tooltip_menu");
        $(this).find(".lui_prompt_tooltip_drop").removeClass('hover');
        $tooltipMenu.fadeOut(300);
    });
});
</script>
<tr>
	<td>${ lfn:msg(luivar['name']) }</td>
	<td>
	    <!-- 自适应宽  -->
		<label>
			<input type="radio" name="${luivarid}" value="width_auto" onclick="changeWidthType()" checked />
			${ lfn:message('sys-portal:portlet.header.var.navigationSettings.item1') }
		</label>
        <!-- 自适应宽  鼠标移入显示帮助---- Starts -->
	    <div class="lui_prompt_tooltip" tip_type="width_auto">
	      <label class="lui_prompt_tooltip_drop">
	         <img src="<%=request.getContextPath()%>/sys/portal/varkind/resource/image/helpTip.png" />
	      </label>
	      <div class="lui_dropdown_tooltip_menu"></div>
	    </div> 
	    <!-- 自适应宽  鼠标移入显示帮助---- Ends -->
		&nbsp;&nbsp;
		<!-- 固定宽度  -->
		<label>
			<input type="radio" name="${luivarid}" value="width_fixed" onclick="changeWidthType()" />
			${ lfn:message('sys-portal:portlet.header.var.navigationSettings.item2') }
		</label>
		<input type="text" class="inputsgl" id="${luivarid}_fixed_width" style="width:36px;" value="${pageScope.defaultFixedWidth}" onkeyup="this.value=this.value.replace(/\D/gi,'');" disabled />px
		<!-- 固定宽度  鼠标移入显示帮助---- Starts -->
	    <div class="lui_prompt_tooltip" tip_type="width_fixed">
	      <label class="lui_prompt_tooltip_drop">
	         <img src="<%=request.getContextPath()%>/sys/portal/varkind/resource/image/helpTip.png" />
	      </label>
	      <div class="lui_dropdown_tooltip_menu"></div>
	    </div> 
	    <!-- 固定宽度  鼠标移入显示帮助---- Ends -->
	    &nbsp;&nbsp;
		<!-- 两端对齐  -->
		<label>
			<input type="radio" name="${luivarid}" value="both_sides_align" onclick="changeWidthType()" />
			${ lfn:message('sys-portal:portlet.header.var.navigationSettings.item3') }
		</label>
		<!-- 两端对齐  鼠标移入显示帮助---- Starts -->
	    <div class="lui_prompt_tooltip" tip_type="both_sides_align">
	      <label class="lui_prompt_tooltip_drop">
	         <img src="<%=request.getContextPath()%>/sys/portal/varkind/resource/image/helpTip.png" />
	      </label>
	      <div class="lui_dropdown_tooltip_menu"></div>
	    </div> 
	    <!-- 两端对齐  鼠标移入显示帮助---- Ends -->			
		
		<!-----------------------Tips内容（默认隐藏）    Start------------------------->
		<div id="navigation_settings_tip_content" style="display:none;">
		    <!-- 自适应宽：导航宽度根据文字长度自行调整，如下图 -->
			<div class="setting_toolip_desc width_auto_desc" style="margin-top:0px;">${lfn:message('sys-portal:portlet.header.var.navigationSettings.help.widthAutoDesc')}</div>
			<div class="setting_toolip_table_div">
			    <table class="tb_normal setting_toolip_table" style="width:360px">
				   <tr>
				     <td>${ lfn:message('sys-portal:portlet.header.var.navigationSettings.A') } </td>
					 <td>${ lfn:message('sys-portal:portlet.header.var.navigationSettings.wordsMore') }</td>
					 <td>${ lfn:message('sys-portal:portlet.header.var.navigationSettings.C') }</td>
					 <td>${ lfn:message('sys-portal:portlet.header.var.navigationSettings.D') }</td>
				   </tr>
				</table>
			</div>
			<!-- 固定宽：导航宽度固定，文字超过导航宽度则省略，文字太少留白，如下图 -->
			<div class="setting_toolip_desc width_fixed_desc">${lfn:message('sys-portal:portlet.header.var.navigationSettings.help.widthFixedDesc')}</div>
			<div class="setting_toolip_table_div">
			    <table class="tb_normal setting_toolip_table">
				   <tr>
				     <td style="width:100px">${ lfn:message('sys-portal:portlet.header.var.navigation') }</td>
					 <td style="width:100px">${ lfn:message('sys-portal:portlet.header.var.navigation.suit') }</td>
					 <td style="width:100px">${ lfn:message('sys-portal:portlet.header.var.navigationSettings.wordsThan') }</td>
					 <td style="width:100px">${ lfn:message('sys-portal:portlet.header.var.navigation') }</td>
				   </tr>
				</table>
			</div>
			<!-- 两端对齐：导航标签宽度平分 -->
			<div class="setting_toolip_desc both_sides_align_desc">${lfn:message('sys-portal:portlet.header.var.navigationSettings.help.bothSidesAlignDesc')}</div>
			<div class="setting_toolip_table_div">
			    <table class="tb_normal setting_toolip_table" style="width:480px">
				   <tr>
				     <td style="width:25%;">${ lfn:message('sys-portal:portlet.header.var.navigationSettings.A') }</td>
					 <td style="width:25%;">${ lfn:message('sys-portal:portlet.header.var.navigationSettings.B') }</td>
					 <td style="width:25%;">${ lfn:message('sys-portal:portlet.header.var.navigationSettings.C') }</td>
					 <td style="width:25%;">${ lfn:message('sys-portal:portlet.header.var.navigationSettings.D') }</td>
				   </tr>
				</table>
			</div>	
			<!-- 注：导航标签中文字符建议长度2-4个字符，最长不超过六个中文字符，如下图 -->
			<div class="setting_toolip_desc">${lfn:message('sys-portal:portlet.header.var.navigationSettings.help.remark.desc')}</div>
			<div class="setting_toolip_table_div">
			    <table class="tb_normal setting_toolip_table" style="width:380px">
				   <tr>
				     <td>${lfn:message('sys-portal:portlet.header.var.navigationSettings.help.remark.numberOfCharacters')}</td>
					 <td>2</td>
					 <td>4</td>
					 <td>6</td>
					 <td>8</td>
				   </tr>
				   <tr>
				     <td>${lfn:message('sys-portal:portlet.header.var.navigationSettings.help.remark.recommendedWidth')}</td>
					 <td>88px</td>
					 <td>120px</td>
					 <td>140px</td>
					 <td>170px</td>
				   </tr>	   
				</table>
			</div>				 		
		</div>	
		<!-----------------------Tips内容（默认隐藏）    End------------------------->
	</td>
</tr>
