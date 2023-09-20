<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/third/ctrip/xform/resource/jsp/temp/ctripTemp_import.jsp"%>

<script>
	// 延时，以等待xform_data_hide初始化完毕
	setTimeout(function(){
		var config = {};
		config.domNode = $("xformflag[flagtype='xform_bookticket'][id*='${param.controlId}']");
		config.ticketType = 'plane|hotel';
		config.bookTypeControlId = "${param.bookTypeControlId}";
		config.bookTypeValue = xform_data_hide["${param.bookTypeControlId}"];
		config.showStatus = "${JsParam.ShowStatus}";
		config.modelName = Xform_ObjectInfo.mainModelName;
		config.mainDocStatus = Xform_ObjectInfo.mainDocStatus;
		config.docId = Xform_ObjectInfo.mainFormId;
		config.controlId = "${param.controlId}";
		
		// 添加校验规则
		config.validateService = {};
		config.validateService["plane"] = new PlaneService();
		config.validateService["hotel"] = new HotelService();
		
		new CtripControl(config);
	},100);
	
</script>