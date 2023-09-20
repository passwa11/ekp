<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/third/ctrip/xform/resource/jsp/temp/ctripTemp_import.jsp"%>

<script>
	var config = {};
	config.domNode = $("xformflag[flagtype='xform_ticket_hotel'][id*='${param.controlId}']");
	config.ticketType = 'hotel';
	<xform:editShow>
		config.showStatus = 'edit';
	</xform:editShow>
	<xform:viewShow>
		config.showStatus = 'view';
	</xform:viewShow>
	config.modelName = Xform_ObjectInfo.mainModelName;
	config.docId = Xform_ObjectInfo.mainFormId;
	config.controlId = "${param.controlId}";
	config.validateService = [];
	config.validateService.push(new CommonRequireService("beginDate_relationId"));
	config.validateService.push(new CommonRequireService("hotelBeginDate_relationId"));
	config.validateService.push(new CommonRequireService("toCity_relationId"));
	config.validateService.push(new TravelPeopleService("passengerList_relationId","retinueList_relationId"));
	config.validateService.push(new DateService("beginDate_relationId","endDate_relationId"));
	config.validateService.push(new DateService("hotelBeginDate_relationId","hotelEndDate_relationId"));

	new CtripControl(config);
	

</script>