<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<span id="main_custom_${param.fdControlId }" style="word-break:break-all;"></span>
<script>
	Com_IncludeFile("chartControl.js",Com_Parameter.ContextPath + 'dbcenter/echarts/application/xform/controls/','js',true);
	Com_IncludeFile("customText.js",Com_Parameter.ContextPath + 'dbcenter/echarts/application/xform/controls/','js',true);
	
	LUI.ready(function(){
		var config = {};
		config.controlId = "${param.fdControlId }";
		config.domNode = $("xformflag[flagid='${param.fdControlId }']");
		config.showStatus = "${param.showstatus}";
		config.valDomNode = $("input[name='${param.fdControlId }']");
		
		var inputs = "${param.inputs}";
		if(inputs){
			config.inputs = JSON.parse(inputs.replace(/quot;/g,"\""));
		}
		 
		var ajaxUrl = Com_Parameter.ContextPath + "dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=getCustomTextByFdId&fdId=${param.categoryId}";
		config.executor = new CustomText({"domNode":$("#main_custom_${param.fdControlId }"),"url" : ajaxUrl});
		
		var ChartControlObj = new ChartControl(config);
		ChartControlObj.load();
	});	
</script>