<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>

$("#limitLocationsList table[id*='locationsList_']").each(function(){
	if(this.id.indexOf("index")==-1){
		DocList_Info.push(this.id);
	}
})
    var _flag="edit";
    DocList_Info.push("limitLocationsList","wTimeSheet","wifiConfigs","busSettingList","overtimeDeducts");
	var cateValidation = $KMSSValidation(document.forms['sysAttendHisCategoryForm']);
	
	// 初始化旷工规则
	window.initAbsentTime = function() {
		var fdLateToAbsentTime = $('[name$="fdLateToAbsentTime"]:enabled').val();
		if (fdLateToAbsentTime==0) {
			$('[name $="fdLateToAbsentTime"]').val('');
		}
		
		var fdLeftToAbsentTime = $('[name $="fdLeftToAbsentTime"]:enabled').val();
		if (fdLeftToAbsentTime==0) {
			$('[name $="fdLeftToAbsentTime"]').val('');
		}
		
		var fdLateToFullAbsTime = $('[name $="fdLateToFullAbsTime"]:enabled').val();
		if (fdLateToFullAbsTime==0) {
			$('[name $="fdLateToFullAbsTime"]').val('');
		}
		
		var fdLeftToFullAbsTime = $('[name $="fdLeftToFullAbsTime"]:enabled').val();
		if (fdLeftToFullAbsTime==0) {
			$('[name $="fdLeftToFullAbsTime"]').val('');
		}
	};
	// 初始化班次类型
	window.initWorkType = function(fdWork) {
		if(!fdWork) 
			return;
		var workTypeField = $('input[name $="fdWork"]:hidden');
		if(fdWork =='1'){
			workTypeField.val('1');
			hideAndRemoveVld($('#twiceWorkTime'));
			$('[name $="fdWorkTime[0].fdIsAvailable"]').val('true');
			$('[name $="fdWorkTime[1].fdIsAvailable"]').val('false');
			showAndAbled('endTimeOnce');
			showAndAbled('overTimeTypeOnce');
			hideAndDisabled('endTimeTwice');
			hideAndDisabled('endTimeTwice2');
			hideAndDisabled('overTimeTypeTwice');
			showAndAbled('restTimeTB');
			$('#onceType').addClass('active');
			$('#twiceType').removeClass('active');
		} else if(fdWork == '2'){
			workTypeField.val('2');
			showAndResetVld($('#twiceWorkTime'));
			$('[name $="fdWorkTime[0].fdIsAvailable"]').val('true');
			$('[name $="fdWorkTime[1].fdIsAvailable"]').val('true');
			hideAndDisabled('endTimeOnce');
			hideAndDisabled('overTimeTypeOnce');
			showAndAbled('endTimeTwice');
			showAndAbled('endTimeTwice2');
			showAndAbled('overTimeTypeTwice');
			hideAndDisabled('restTimeTB');;
			$('#twiceType').addClass('active');
			$('#onceType').removeClass('active');
		}
	};
	
	// 初始化班制类型
	window.initFdShiftType = function(shiftType,sameWork) {
		if(shiftType == '0') {
			if(sameWork == '0') {
				hideAndRemoveVld($('#notSameWTime'));
				$('#wTimeSheet tr').each(function(){
					$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('false');
					$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
				}); 
				hideAndDisabled('customDateTd');
			} else if(sameWork == '1') {
				hideAndRemoveVld($('#sameWTime'));
				$('[name $="fdWorkTime[0].fdIsAvailable"]').val('false');
				$('[name $="fdWorkTime[1].fdIsAvailable"]').val('false');
				$('#wTimeSheet tr').each(function(){
					var fdWork = $(this).find('[name$=".fdWork"]').val();
					if(fdWork == '1') {
						$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('true');
						$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
					} else if (fdWork == '2'){
						$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('true');
						$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('true');
					}
				});
				hideAndDisabled('appendTimeContent');
			}
			hidTimeArea();
		} else if(shiftType == '1'){
			showTimeArea();
		} else if(shiftType == '2') {
			hideAndRemoveVld($('#notSameWTime'));
			$('#wTimeSheet tr').each(function(){
				$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('false');
				$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
			});
			hideAndDisabled('fdSameWtimeDiv');
			hideAndDisabled('weekTd');
			hideAndDisabled('excTimeContent');
			hideAndDisabled('appendTimeContent');
			hideAndDisabled('holidayContent');
			hidTimeArea();
		}
	};
	
	// 班制类型
	window.changeFdShiftType = function(v) {
		if(!v){
			return;
		}
		if(v == '0') {
			showAndResetVld($('#sameWTime'));
			hideAndRemoveVld($('#notSameWTime'));
			showAndAbled('fdSameWtimeDiv');
			showAndAbled('weekTd');
			hideAndDisabled('customDateTd');
			showAndAbled('excTimeContent');
			showAndAbled('appendTimeContent');
			showAndAbled('holidayContent');
			$('input[name $="fdPeriodType"]:hidden').val('1');//兼容
			$('select[name $="fdSameWorkTime"]').val('0');
			changeSameWTime('0');
			hidTimeArea();
		} else if(v == '1') {
			showTimeArea();
		} else if(v == '2') {
			showAndResetVld($('#sameWTime'));
			hideAndRemoveVld($('#notSameWTime'))
			$('#wTimeSheet tr').each(function(){
				$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('false');
				$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
			}); 
			hideAndDisabled('fdSameWtimeDiv');
			hideAndDisabled('weekTd');
			showAndAbled('customDateTd');
			hideAndDisabled('excTimeContent');
			hideAndDisabled('appendTimeContent');
			hideAndDisabled('holidayContent');
			$('input[name $="fdPeriodType"]:hidden').val('2');//兼容
			changeWorkType('1');
			hidTimeArea();
		}
		
	}
	
	window.showTimeArea = function(){
		hideAndRemoveVld($('#sameWTime'));
		hideAndDisabled('fdSameWtimeDiv');
		hideAndRemoveVld($('#notSameWTime'))
		hideAndRemoveVld($('#flexContent'))
		hideAndDisabled('excTimeContent');
		hideAndDisabled('appendTimeContent');
		hideAndDisabled('holidayContent');
		
		showAndResetVld($('#timeAreaTimeContent'));
		showAndAbled('timeAreaTimeContent');
		$('#onceWorkTime select[name $="fdEndDay"]').prop('disabled', 'disabled');
	},
	window.getShiftType = function(){
		return '${sysAttendHisCategoryForm.sysAttendCategoryForm.fdShiftType}';
	},
	window.hidTimeArea = function(){
		showAndResetVld($('#flexContent'));
		hideAndDisabled('timeAreaTimeContent');
		hideAndRemoveVld($('#timeAreaTimeContent'));
		$('#onceWorkTime select[name $="fdEndDay"]').removeAttr('disabled');
	},
	//选择排班信息
	window.selTimeArea = function(){
		Dialog_List(true, "fdTimeAreaIds", "fdTimeAreaNames", ';',"sysTimeService",function(data){
			if(!data){
				return;
			}
		},"sysTimeService&search=!{keyword}", false, false,"${ lfn:message('sys-attend:sysAttend.tree.config.stat.selCate') }");
							
	}
	window.changeSameWTime = function(v) {
		if(!v) {
			return;
		}
		if(v == '1') {
			hideAndRemoveVld($('#sameWTime'));
			showAndResetVld($('#notSameWTime'));
			$('[name $="fdWorkTime[0].fdIsAvailable"]').val('false');
			$('[name $="fdWorkTime[1].fdIsAvailable"]').val('false');
			$('#wTimeSheet tr').each(function(){
				var fdWork = $(this).find('[name$=".fdWork"]').val();
				if(fdWork == '1') {
					$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('true');
					$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
				} else if (fdWork == '2'){
					$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('true');
					$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('true');
				}
			});
			hideAndDisabled('appendTimeContent');
		} else {
			showAndResetVld($('#sameWTime'));
			hideAndRemoveVld($('#notSameWTime'));
			$('#wTimeSheet tr').each(function(){
				$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('false');
				$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
			}); 
			changeWorkType('1');
			hideAndDisabled('customDateTd');
			showAndAbled('appendTimeContent');
		}
	};
	
	window.changeRoundingType = function(v) {
		if(!v) {
			return;
		}
		if(v == '0') {		
			hideAndDisabled('overtimeHour');
			$('[name $="fdMinUnitHour"]').val('');
			cateValidation.validateElement($('[name $="fdMinUnitHour"]')[0]);
		}
		else{
			showAndAbled('overtimeHour');
		}
	}
	
	// 班次类型，一班制还是两班制
	window.changeWorkType = function(v) {
		if(!v) 
			return;
		var workTypeField = $('input[name $="fdWork"]:hidden');
		if(v =='1'){
			workTypeField.val('1');
			hideAndRemoveVld($('#twiceWorkTime'));
			$('[name $="fdWorkTime[0].fdIsAvailable"]').val('true');
			$('[name $="fdWorkTime[1].fdIsAvailable"]').val('false');
			showAndAbled('endTimeOnce');
			showAndAbled('overTimeTypeOnce');
			hideAndDisabled('endTimeTwice');
			hideAndDisabled('endTimeTwice2');
			hideAndDisabled('overTimeTypeTwice');
			showAndAbled('restTimeTB');
			$('#onceType').addClass('active');
			$('#twiceType').removeClass('active');
			$('[name $="fdWorkTime[0].fdStartTime"]').val('09:00');
			$('[name $="fdWorkTime[0].fdEndTime"]').val('18:00');
			setTimeout(function() {
				calTotalTime();
			}, 0);
			showAndAbled('absFullBody');
		} else if(v=='2'){
			workTypeField.val('2');
			showAndResetVld($('#twiceWorkTime'));
			$('[name $="fdWorkTime[0].fdIsAvailable"]').val('true');
			$('[name $="fdWorkTime[1].fdIsAvailable"]').val('true');
			hideAndDisabled('endTimeOnce');
			hideAndDisabled('overTimeTypeOnce');
			showAndAbled('endTimeTwice');
			showAndAbled('endTimeTwice2');
			showAndAbled('overTimeTypeTwice');
			hideAndDisabled('restTimeTB');
			$('#twiceType').addClass('active');
			$('#onceType').removeClass('active');
			$('[name $="fdWorkTime[0].fdStartTime"]').val('09:00');
			$('[name $="fdWorkTime[0].fdEndTime"]').val('12:00');
			$('[name $="fdWorkTime[1].fdStartTime"]').val('14:00');
			$('[name $="fdWorkTime[1].fdEndTime"]').val('18:00');
			$('[name $="fdEndTime1"]').val('14:00');
			$('[name $="fdStartTime2"]').val('12:00');
			setTimeout(function() {
				calTotalTime();
			}, 0);
			hideAndDisabled('absFullBody');
		}
	};
	
	// 计算总工时
	window.calTotalTime = function () {
		var workType = $('input[name $="fdWork"]:hidden').val();
		var on1 = $('[name $="fdWorkTime[0].fdStartTime"]:enabled:visible').val();
		var off1 = $('[name $="fdWorkTime[0].fdEndTime"]:enabled').val();
		var type1 = $('[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		var on2 = $('[name $="fdWorkTime[1].fdStartTime"]:enabled').val();
		var off2 = $('[name $="fdWorkTime[1].fdEndTime"]:enabled').val();
		var type2 = $('[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		var restStart = $('[name $="fdRestStartTime"]:enabled').val();
		var restEnd = $('[name $="fdRestEndTime"]:enabled').val();
		var totalTimeDiv = $('#totalTimeDiv');
		var fdTotalTime = $('input[name $="fdTotalTime"]:hidden');
		
		var totalTime = 0;
		if(workType == '1' && on1 && off1) {//一班制
			var dateStart1=getDateTime(on1,1);
			var dateEnd1=getDateTime(off1,type1);
			/* var onMin1 = parseInt(on1.split(':')[0]) * 60 + parseInt(on1.split(':')[1]);
			var offMin1 = parseInt(off1.split(':')[0]) * 60 + parseInt(off1.split(':')[1]); */
			var onMin1 = dateStart1.getTime();
			var offMin1 = dateEnd1.getTime();
			if(offMin1 > onMin1) {
				totalTime = (offMin1 - onMin1)/(60*1000);
				if(restStart && restEnd) {
					var restStartMin = parseInt(restStart.split(':')[0]) * 60 + parseInt(restStart.split(':')[1]);
					var restEndMin = parseInt(restEnd.split(':')[0]) * 60 + parseInt(restEnd.split(':')[1]);
					if(restEndMin > restStartMin) {
						totalTime = totalTime - (restEndMin - restStartMin);
					}
				}
			}
			totalTime = parseFloat((totalTime / 60).toFixed(1));
		} else if (workType == '2' && on1 && off1 && on2 && off2) {//两班制
			/* var onMin1 = parseInt(on1.split(':')[0]) * 60 + parseInt(on1.split(':')[1]);
			var offMin1 = parseInt(off1.split(':')[0]) * 60 + parseInt(off1.split(':')[1]);
			var onMin2 = parseInt(on2.split(':')[0]) * 60 + parseInt(on2.split(':')[1]);
			var offMin2 = parseInt(off2.split(':')[0]) * 60 + parseInt(off2.split(':')[1]); */
			var dateStart1=getDateTime(on1,1);
			var dateEnd1=getDateTime(off1,type1);
			var dateStart2=getDateTime(on2,1);
			var dateEnd2=getDateTime(off2,type2);
			var onMin1 = dateStart1.getTime();
			var offMin1 = dateEnd1.getTime();
			var onMin2 = dateStart2.getTime();
			var offMin2 = dateEnd2.getTime();
			if(offMin1 > onMin1 && offMin2 > onMin2) {
				totalTime = parseFloat(((offMin2 - onMin2 + offMin1 - onMin1) / (60*60*1000)).toFixed(1));
			}
		}
		totalTime = totalTime < 0 ? 0 : totalTime;
		totalTimeDiv.html(totalTime  + "${ lfn:message('sys-attend:sysAttendCategory.hour') }");
		fdTotalTime.val(totalTime);
	};
	
	// 选择出差/请假/出差/外出关联的流程
	window.selectTemplate = function selectTemplate(fdTemplateId, fdTemplateName){
		seajs.use(['sys/ui/js/dialog'], function(dialog) {
			dialog.category({
				modelName:"com.landray.kmss.km.review.model.KmReviewTemplate",
				idField: fdTemplateId,
				nameField: fdTemplateName,
				mulSelect:false,
				winTitle:"${ lfn:message('sys-attend:sysAttendCategory.select.template') }",
				canClose:true,
				isShowTemp:true,
				authType:"02",
				notNull:true
			});
	   });
	}
	
	// 是否加班
	window.changefdIsOvt = function(value) {
		var fdIsOvertime = value || $(':hidden[name$="fdIsOvertime"]').val();
		if(fdIsOvertime == 'true') {
			showAndAbled('ovtReview');
			showAndAbled('deductSwitch');
			showAndAbled('ovtMinHour');
			showAndAbled('ovtMinHourTips');	
			showAndAbled('roundingRules');	
			changefdIsOvtDeduct();
		} else {
			hideAndDisabled('ovtMinHourTips');
			hideAndDisabled('ovtMinHour');
			hideAndDisabled('ovtReview');
			hideAndDisabled('deductSwitch');
			hideAndDisabled('deductTips');
			hideAndDisabled('ovtDeduct');
			hideAndDisabled('timePeriod');
			hideAndDisabled('timethreshold');
			hideAndDisabled('roundingRules');
			hideOvtTempl();
		}
	}
	
	// 加班需审批
	window.changefdOvtReviewType = function() {
		var fdOvtReviewType = $(':radio[name$="fdOvtReviewType"]:checked');
		if((fdOvtReviewType.val() == '1' || fdOvtReviewType.val() == '2') && fdOvtReviewType.is(':enabled')) {
			hideAndDisabled('ovtMinHour');
			showOvtTempl();
		} else {
			showAndAbled('ovtMinHour');
			hideOvtTempl();
		}
	}
	
	// 是否加班扣除
	window.changefdIsOvtDeduct = function(value) {
		var fdIsOvertimeDeduct = value || $(':hidden[name$="fdIsOvertimeDeduct"]').val();
		if(fdIsOvertimeDeduct == 'true') {
			showAndAbled('ovtDeduct');
			showAndAbled('deductTips');
			changefdOvtDeductType();
		} else {
			hideAndDisabled('deductTips');
			hideAndDisabled('ovtDeduct');
			hideAndDisabled('timePeriod');
			hideAndDisabled('timethreshold');
		}
	}
	
	// 选择加班扣除方式
	window.changefdOvtDeductType = function() {
		var fdOvtDeductType = $(":radio[name$='fdOvtDeductType']:checked");
		if(fdOvtDeductType.val() == '0' && fdOvtDeductType.is(':enabled')) {
			hideAndDisabled('timethreshold');
			showAndAbled('timePeriod');
			var deductPeriod = $('#overtimeDeducts [over-time-deducts]').length;
			if(deductPeriod <= '0'){ 	
				DocList_AddRow('overtimeDeducts')
			}
		} else if(fdOvtDeductType.val() == '1' && fdOvtDeductType.is(':enabled')) {
			hideAndDisabled('timePeriod');
			showAndAbled('timethreshold');
		}
	}
	
	// 初始化扣除加班类型
	window.initDeductType = function() {
		var fdIsOvertime = '${sysAttendHisCategoryForm.sysAttendCategoryForm.fdIsOvertime}' || $(':hidden[name$="fdIsOvertime"]').val();
		var fdIsOvertimeDeduct = '${sysAttendHisCategoryForm.sysAttendCategoryForm.fdIsOvertimeDeduct}';

		if (fdIsOvertimeDeduct == 'false' || fdIsOvertime == 'false' || fdIsOvertimeDeduct == ''){
			hideAndDisabled('ovtDeduct');
			/* hideAndDisabledForClass('timePeriod'); */
			hideAndDisabled('timethreshold');
			return;
		}
		showAndAbled('deductTips');
		var fdOvtDeductType = '${sysAttendHisCategoryForm.sysAttendCategoryForm.fdOvtDeductType}';
		if (fdOvtDeductType == '0'){
			hideAndDisabled('timethreshold');
			showAndAbled('timePeriod');
		} else if(fdOvtDeductType == '1'){
			showAndAbled('timethreshold');
			hideAndDisabled('timePeriod');
		}
	}
	
	// 展示加班流程
	var showOvtTempl = function(){
		var selectEles = $('#busSettingList select[name$="$fdBusType"]');
		for(var i in selectEles) {
			if(selectEles[i].value == '6')
				return;
		}
		var newTR = DocList_AddRow('busSettingList');
		$(newTR).find(':text[name$="fdBusName"]').val("${ lfn:message('sys-attend:sysAttendMain.fdStatus.overtime') }");
		$(newTR).find('select[name$="fdBusType"]').val('6');
	}
	
	// 隐藏加班流程
	var hideOvtTempl = function(){
		var selectEles = $('#busSettingList select[name$="fdBusType"]');
		for(var i in selectEles) {
			if(selectEles[i].value != '6')
				continue;
			var delTR = $(selectEles[i]).closest('tr');
			DocList_DeleteRow(delTR[0]);
		}
	}
	
	// 弹性上下班
	window.changeIsFlex = function(value) {
		var fdIsFlex = value || $('input[name$="fdIsFlex"]').val();
		if(fdIsFlex == 'true') {
			hideAndDisabled('lateBody');
			hideAndDisabled('leftBody');
			showAndAbled('flexTimeTd');
		} else {
			showAndAbled('lateBody');
			showAndAbled('leftBody');
			hideAndDisabled('flexTimeTd');
		}
	};
	
	// 外勤需审批
	window.changeFdOutside = function(){
		if($('[name$="__fdOutside"]:hidden').val() == 'true') {
			$('[name$="fdRule[0].fdOutside"]:hidden').val('true');
			showAndAbled('osdReviewType')
		} else {
			$('[name$="fdRule[0].fdOutside"]:hidden').val('false');
			hideAndDisabled('osdReviewType')
		}
	};
	
	var showAndAbled = function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.removeAttr('disabled');
		parentDom.show();
	};
	
	var hideAndDisabled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.prop('disabled', 'disabled');
		parentDom.hide();
	};
	
	var hideAndDisabledForClass= function(clazz) {
		var parentDom = $('.' + clazz);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.prop('disabled', 'disabled');
		parentDom.hide();
	};
	
	var hideAndAbled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		parentDom.hide();
	};
	
	var hideAndEnabled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		parentDom.hide();
	};
	
	var showAndResetVld = function(ele) {
		$(ele).show();
		cateValidation.resetElementsValidate($(ele));
	};
	
	var hideAndRemoveVld = function(ele) {
		$(ele).hide();
		cateValidation.removeElements($(ele));
	}
	
	// 标题展开收起事件
	var bindTitleEvent  = function(){
		$('#signTargetsTitle').on('click',function(){
			$('#signTargetsBody').slideToggle();
		});
		$('#signTimeTitle').on('click',function(){
			$('#signTimeBody').slideToggle();
		});
		$('#signTypeTitle').on('click',function(){
			$('#signTypeBody').slideToggle();
		});
		$('#signOffTitle').on('click',function(){
			$('#signOffBody').slideToggle();
		});
		$('#signNotifyTitle').on('click',function(){
			$('#signNotifyBody').slideToggle();
		});
		$('#ruleTitle').on('click',function(){
			$('#ruleBody').slideToggle();
		});
		$('#securityTitle').on('click',function(){
			$('#securityBody').slideToggle();
		});
	}
	
	// 添加地点或WIFI
	window.addPosition = function(listName,obj) {
		if(listName.indexOf("locationsList_")>-1){
			var pidx=listName.replace("locationsList_","");
			var tableobj=$("#locationDemo tbody");
			var reg = new RegExp("pidx", "g" );
			var reg2 = new RegExp("idx", "g" );
			var reg3 = new RegExp("attrname", "g" );
			var htmlobj=tableobj.html().replace(reg,pidx).replace(reg2,$("#"+listName)[0].rows.length-1).replace(reg3,"name");
			var tableobj;
			if(listName==null)
				tableobj = DocListFunc_GetParentByTagName("TABLE");
			else if(typeof(listName)=="string")
				tableobj = document.getElementById(listName);
			var tbInfo = DocList_TableInfo[tableobj.id];
			if(tbInfo.lastIndex=="undefined"||tbInfo.lastIndex==undefined){
				tbInfo.lastIndex=1;
			}
			var newRow = tableobj.insertRow(tbInfo.lastIndex);
			var newCell = newRow.insertCell(0);
			newCell.innerHTML = $(htmlobj).html();
			tbInfo.lastIndex++;
			// 地图组件在明细表中添加会多出一行
			$("#"+listName).trigger($.Event("table-add-child"),newRow);
		}else{
			DocList_AddRow(listName);
		}
		setTimeout(function(){
			onChangePosCount();
		}, 300);
	};
	
	
	// 删除地点或WIFI
	window.deletePosition = function(type){
		if(type && type.indexOf("locationsList_")>-1){
			//删除范围
			var locationCount = $("table[id*='locationsList_']").length;
			var wifiCount = $('#wifiConfigs [data-wifi-config]').length;
			if(locationCount <= 1 && wifiCount == 0 || locationCount == 0 && wifiCount <= 1){
				seajs.use('lui/dialog',function(dialog){
					dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.position') }", null, 'none');
				});
				return;
			}
			var tbInfo = DocList_TableInfo["limitLocationsList"];
			var index=type.replace("locationsList_","");
			var optTR = tbInfo.DOMElement.rows[parseInt(index)+1];
			DocList_DeleteRow_ClearLast(optTR,type);
			//更新DocList_TableInfo
			var beginMake=false;
			for(var arr in DocList_TableInfo){
				if(arr==type){
					beginMake=true;
				}
				if(beginMake && arr.indexOf("locationsList_")>-1){
					var arrIndex=arr.replace("locationsList_","");
					if(DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)]){
						for(var io in DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)]){}
						DocList_TableInfo[arr].DOMElement=DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)].DOMElement;	
						DocList_TableInfo[arr].cells=DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)].cells;	
						DocList_TableInfo[arr].lastIndex=DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)].lastIndex;	
						DocList_TableInfo[arr].firstIndex=DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)].firstIndex;	
					}else{
						DocList_TableInfo[arr]=null;
					}					
				}
			}
			//更新下on事件
		}else{
			//删除地点
			var locationCount = $("table[id*='locationsList_'] div[data-location-container]").length;
			var wifiCount = $('#wifiConfigs [data-wifi-config]').length;
			if(locationCount <= 1 && wifiCount == 0 || locationCount == 0 && wifiCount <= 1){
				seajs.use('lui/dialog',function(dialog){
					dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.position') }", null, 'none');
				});
				return;
			}
			DocList_DeleteRow_ClearLast(null,type);
		}
		
		setTimeout(function(){
			onChangePosCount();
		}, 300);
	};
	
	// 删除加班扣除的时间段
	window.deleteOvtTimePosition = function(){
		var deductPeriod = $('#overtimeDeducts [over-time-deducts]').length;
		if(deductPeriod <= 1){
			seajs.use('lui/dialog',function(dialog){
				dialog.alert("休息时间段，必须设置至少要有一项", null, 'none');
			});
			return;
		}
		DocList_DeleteRow_ClearLast();
	};
	
	// 监听地点和WIFI数量的变化
	window.onChangePosCount = function() {
		var locationCount = $("table[id*='locationsList_'] div[data-location-container]").length;
		var wifiCount = $('#wifiConfigs [data-wifi-config]').length;
		if(locationCount == 0 && wifiCount > 0) {
			hideAndDisabled('fdLimitTR');
			if(!$('#fdOutsideWgt :checkbox').is(':checked')) {
				$('#fdOutsideWgt :checkbox').click();
			}
		} else {
			showAndAbled('fdLimitTR');
		}
	};
	
	window.onDingChange = function(){
		onOnlyDingEnable();
	};
	
	window.onMapChange = function(isReady) {
		if($('[name $="fdCanMap"]:hidden').val() == 'false'){
			$("#limitLocationsList tr:gt(0)").hide();
			$("#limitLocationsList tr:gt(0)").find(":input").prop('disabled', 'disabled');
			//hideAndDisabled('fdMapTR');
			//hideAndDisabled('fdLimitTR');
			//判断wifi是否开启
			if($('[name $="fdCanWifi"]:hidden').val() == 'false'){
				showOrHideOutside(false);
			}
		}else{
			//showAndAbled('fdMapTR');
			//showAndAbled('fdLimitTR');
			$("#limitLocationsList tr:gt(0)").show();
			$("#limitLocationsList tr:gt(0)").find(":input").removeAttr('disabled');
			showOrHideOutside(true);
		}
		if(!isReady)
			onOnlyDingEnable();     
    };
	
	window.onWifiChange = function(isReady) {
		if($('[name $="fdCanWifi"]:hidden').val() == 'false'){
            hideAndDisabled('fdWifiTR');
          //判断map是否开启
			if($('[name $="fdCanMap"]:hidden').val() == 'false'){
				showOrHideOutside(false);
			}
        }else{
            showAndAbled('fdWifiTR');
            showOrHideOutside(true);
        }
		if(!isReady)
			onOnlyDingEnable();
    };
	
	showOrHideOutside = function(isShow){
		if(isShow){
			$('#outsideBody').show();
			return;
		}
		//隐藏
		var value = $('input[name $="fdRule[0].fdOutside"]').val();
		if(value=='true') {
			$('#fdOutsideWgt :checkbox').click();
		}
		$('#outsideBody').hide();
		
	}
	
	// 选择节假日
	function selHoliday(){
		Dialog_List(false, "sysAttendCategoryForm.fdHolidayId", "sysAttendCategoryForm.fdHolidayName", ';',"sysTimeHolidayService",function(data){
			var name = $("input[name='sysAttendCategoryForm.fdHolidayName']").val();

			$("#holidayNameDiv").text(name);
		},"sysTimeHolidayService&search=!{keyword}", false, false,"${ lfn:message('sys-attend:sysAttendCategory.select.holiday') }");

	}
	
	seajs.use(['sys/ui/js/dialog'], function(dialog) {
		window.addTimeSheet = function() {
			var newRow = DocList_AddRow('wTimeSheet');
			$(newRow).find('input').removeAttr('disabled');
			var fdWeek = $(newRow).find('[name$=".fdWeek"]');
			var fdWork = $(newRow).find('[name$=".fdWork"]');
			var fdWorkTimeId1 = $(newRow).find('[name$=".fdWorkTime[0].fdId"]');
			var fdIsAvailable1 = $(newRow).find('[name$=".fdWorkTime[0].fdIsAvailable"]');
			var fdOnTime1 = $(newRow).find('[name$=".fdWorkTime[0].fdStartTime"]');
			var fdOffTime1 = $(newRow).find('[name$=".fdWorkTime[0].fdEndTime"]');
			var fdOverTimeType1 = $(newRow).find('[name$=".fdWorkTime[0].fdOverTimeType"]');
			var fdWorkTimeId2 = $(newRow).find('[name$=".fdWorkTime[1].fdId"]');
			var fdIsAvailable2 = $(newRow).find('[name$=".fdWorkTime[1].fdIsAvailable"]');
			var fdOnTime2 = $(newRow).find('[name$=".fdWorkTime[1].fdStartTime"]');
			var fdOffTime2 = $(newRow).find('[name$=".fdWorkTime[1].fdEndTime"]');
			var fdOverTimeType2 = $(newRow).find('[name$=".fdWorkTime[1].fdOverTimeType"]');
			var fdStartTime1 = $(newRow).find('[name$=".fdStartTime1"]');
			var fdStartTime2 = $(newRow).find('[name$=".fdStartTime2"]');
			var fdEndTime1 = $(newRow).find('[name$=".fdEndTime1"]');
			var fdEndTime2 = $(newRow).find('[name$=".fdEndTime2"]');
			var fdEndDay = $(newRow).find('[name$=".fdEndDay"]');
			var fdRestStartTime = $(newRow).find('[name$=".fdRestStartTime"]');
			var fdRestEndTime = $(newRow).find('[name$=".fdRestEndTime"]');
			var fdTotalTime = $(newRow).find('[name$=".fdTotalTime"]');
			var fdTotalDay = $(newRow).find('[name$=".fdTotalDay"]');
			var fdWeeks = "";
			$('#wTimeSheet').find('[name$=".fdWeek"]').each(function(){
				fdWeeks += $(this).val() + ";";
			});
			
			var url = '/sys/attend/sys_attend_category/sysAttendCategory_edit_tsheet.jsp?'
				+ "fdWork=1&fdStartTime1=00:00&fdEndTime2=23:59&fdEndDay=1&fdRestStartTime=12:00&fdRestEndTime=13:00&fdTotalTime=8&fdOnTime1=09:00&fdOffTime1=18:00"
				+ "&fdWeeks=" + fdWeeks + '&fdIsAvailable1=true&fdIsAvailable1=false&fdOverTimeType1=1';
			
			dialog.iframe(url, "${ lfn:message('sys-attend:sysAttendCategory.timeSheet.setting') }", function(result){
				if(result) {
					var resultObj = {}; 
					$.each(result, function() {
						resultObj[this.name] = this.value;
				    });
					fdWeek.val(resultObj['fdWeek']);
					fdWork.val(resultObj['fdWork']);
					fdWorkTimeId1.val(resultObj['fdWorkTime[0].fdId']);
					fdIsAvailable1.val(resultObj['fdWorkTime[0].fdIsAvailable']);
					fdOnTime1.val(resultObj['fdWorkTime[0].fdStartTime']);
					fdOffTime1.val(resultObj['fdWorkTime[0].fdEndTime']);
					fdOverTimeType1.val(resultObj['fdWorkTime[0].fdOverTimeType']);
					fdWorkTimeId2.val(resultObj['fdWorkTime[1].fdId']);
					fdIsAvailable2.val(resultObj['fdWorkTime[1].fdIsAvailable']);
					fdOnTime2.val(resultObj['fdWorkTime[1].fdStartTime']);
					fdOffTime2.val(resultObj['fdWorkTime[1].fdEndTime']);
					fdOverTimeType2.val(resultObj['fdWorkTime[1].fdOverTimeType']);
					fdStartTime1.val(resultObj['fdStartTime1']);
					fdStartTime2.val(resultObj['fdStartTime2']);
					fdEndTime1.val(resultObj['fdEndTime1']);
					fdEndTime2.val(resultObj['fdEndTime2'] || resultObj['fdEndTime']);
					fdEndDay.val(resultObj['fdEndDay']);
					fdRestStartTime.val(resultObj['fdRestStartTime']);
					fdRestEndTime.val(resultObj['fdRestEndTime']);
					fdTotalTime.val(resultObj['fdTotalTime']);
					fdTotalDay.val(resultObj['fdTotalDay']);

					$(newRow).find('td:eq(0)').html(getFdWeekText(resultObj['fdWeek']));
					var fdWorkTimeText = '';
					if(resultObj['fdWork'] == '1') {
						if(resultObj['fdWorkTime[0].fdOverTimeType']=="2"){
							fdWorkTimeText="(${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') })";
						}
						fdWorkTimeText = resultObj['fdWorkTime[0].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[0].fdEndTime'] +fdWorkTimeText;
					} else if(resultObj['fdWork'] == '2'){
						if(resultObj['fdWorkTime[1].fdOverTimeType']=="2"){
							fdWorkTimeText="(${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') })";
						}
						fdWorkTimeText = resultObj['fdWorkTime[0].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[0].fdEndTime']
										+ ';&nbsp;' + resultObj['fdWorkTime[1].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[1].fdEndTime']+fdWorkTimeText;
					}
					$(newRow).find('td:eq(1)').html(fdWorkTimeText);
					$('#tSheetCountVld').hide();
					setTimeout(function() {
						if($('#wTimeSheet input[name^="fdTimeSheets["][name$="].fdWeek"]').length > 2) {
							$('#tSheetCountTips').show();
						}
					}, 0);
				} else {
					DocList_DeleteRow(newRow);
					setTimeout(function() {
						if($('#wTimeSheet input[name^="fdTimeSheets["]').length <= 0) {
							$('#tSheetCountVld').show();
						}
					}, 0);
				}
			},{width: 950, height: 500});
		};
		
		var getFdWeekText = function (fdWeek) {
			var fdWeekText = "";
			if(fdWeek) {
				var weekList = fdWeek.split(/[,;]/);
				weekList.sort();
				for(var i=0; i<weekList.length; i++) {
					var prefix = fdWeekText ? '、' : '';
					switch (weekList[i]) {
					case '1':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.mon') }";break;
					case '2':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.tue') }";break;
					case '3':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.wed') }";break;
					case '4':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.thu') }";break;
					case '5':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.fri') }";break;
					case '6':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.sat') }";break;
					case '7':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.sun') }";break;
					default :break;
					}
				}
			}
			return fdWeekText;
		};
		
		window.editTimeSheet = function() {
			var editTR = DocListFunc_GetParentByTagName("TR");
			
			var fdWeek = $(editTR).find('[name$=".fdWeek"]');
			var fdWork = $(editTR).find('[name$=".fdWork"]');
			var fdWorkTimeId1 = $(editTR).find('[name$=".fdWorkTime[0].fdId"]');
			var fdIsAvailable1 = $(editTR).find('[name$=".fdWorkTime[0].fdIsAvailable"]');
			var fdOnTime1 = $(editTR).find('[name$=".fdWorkTime[0].fdStartTime"]');
			var fdOffTime1 = $(editTR).find('[name$=".fdWorkTime[0].fdEndTime"]');
			var fdOverTimeType1 = $(editTR).find('[name$=".fdWorkTime[0].fdOverTimeType"]');
			var fdWorkTimeId2 = $(editTR).find('[name$=".fdWorkTime[1].fdId"]');
			var fdIsAvailable2 = $(editTR).find('[name$=".fdWorkTime[1].fdIsAvailable"]');
			var fdOnTime2 = $(editTR).find('[name$=".fdWorkTime[1].fdStartTime"]');
			var fdOffTime2 = $(editTR).find('[name$=".fdWorkTime[1].fdEndTime"]');
			var fdOverTimeType2 = $(editTR).find('[name$=".fdWorkTime[1].fdOverTimeType"]');
			var fdStartTime1 = $(editTR).find('[name$=".fdStartTime1"]');
			var fdStartTime2 = $(editTR).find('[name$=".fdStartTime2"]');
			var fdEndTime1 = $(editTR).find('[name$=".fdEndTime1"]');
			var fdEndTime2 = $(editTR).find('[name$=".fdEndTime2"]');
			var fdEndDay = $(editTR).find('[name$=".fdEndDay"]');
			var fdRestStartTime = $(editTR).find('[name$=".fdRestStartTime"]');
			var fdRestEndTime = $(editTR).find('[name$=".fdRestEndTime"]');
			var fdTotalTime = $(editTR).find('[name$=".fdTotalTime"]');
			var fdTotalDay = $(editTR).find('[name$=".fdTotalDay"]');
            var fdRestEndType = $(editTR).find('[name$=".fdRestEndType"]');
            var fdRestStartType = $(editTR).find('[name$=".fdRestStartType"]');
			var fdWeeks = "";
			$('#wTimeSheet').find('[name$=".fdWeek"]').each(function(){
				fdWeeks += $(this).val() + ";";
			});
			
			var url = '/sys/attend/sys_attend_category/sysAttendCategory_edit_tsheet.jsp?'
					+ "fdWeek=" + fdWeek.val() + "&fdWork=" + fdWork.val() 
					+ "&fdWorkTimeId1=" + fdWorkTimeId1.val() + '&fdIsAvailable1=' + fdIsAvailable1.val() + "&fdOnTime1=" + fdOnTime1.val() + "&fdOffTime1=" + fdOffTime1.val()
					+ "&fdOverTimeType1=" + fdOverTimeType1.val()
					+ "&fdWorkTimeId2=" + fdWorkTimeId2.val() + '&fdIsAvailable2=' + fdIsAvailable2.val() + "&fdOnTime2=" + fdOnTime2.val() + "&fdOffTime2=" + fdOffTime2.val()
					+ "&fdOverTimeType2=" + fdOverTimeType2.val()
					+ "&fdStartTime1=" + fdStartTime1.val() + "&fdStartTime2=" + fdStartTime2.val()
					+ "&fdEndTime1=" + fdEndTime1.val() + "&fdEndTime2=" + fdEndTime2.val() + "&fdEndDay=" + fdEndDay.val()
					+ "&fdRestStartTime=" + fdRestStartTime.val() + "&fdRestEndTime=" + fdRestEndTime.val() + "&fdTotalTime=" + fdTotalTime.val()
					+ "&fdWeeks=" + fdWeeks +"&fdTotalDay=" + (fdTotalDay?fdTotalDay.val():1.0)
                    +"&fdRestEndType=" + fdRestEndType.val()
                    +"&fdRestStartType=" + fdRestStartType.val();
			
			dialog.iframe(url, "${ lfn:message('sys-attend:sysAttendCategory.timeSheet.setting') }", function(result){
				if(result) {
					var resultObj = {}; 
					$.each(result, function() {
						resultObj[this.name] = this.value;
				    });
					fdWeek.val(resultObj['fdWeek']);
					fdWork.val(resultObj['fdWork']);
					fdWorkTimeId1.val(resultObj['fdWorkTime[0].fdId']);
					fdIsAvailable1.val(resultObj['fdWorkTime[0].fdIsAvailable']);
					fdOnTime1.val(resultObj['fdWorkTime[0].fdStartTime']);
					fdOffTime1.val(resultObj['fdWorkTime[0].fdEndTime']);
					fdOverTimeType1.val(resultObj['fdWorkTime[0].fdOverTimeType']);
					fdWorkTimeId2.val(resultObj['fdWorkTime[1].fdId']);
					fdIsAvailable2.val(resultObj['fdWorkTime[1].fdIsAvailable']);
					fdOnTime2.val(resultObj['fdWorkTime[1].fdStartTime']);
					fdOffTime2.val(resultObj['fdWorkTime[1].fdEndTime']);
					fdOverTimeType2.val(resultObj['fdWorkTime[1].fdOverTimeType']);
					fdStartTime1.val(resultObj['fdStartTime1']);
					fdStartTime2.val(resultObj['fdStartTime2']);
					fdEndTime1.val(resultObj['fdEndTime1']);
					fdEndTime2.val(resultObj['fdEndTime2'] || resultObj['fdEndTime']);
					fdEndDay.val(resultObj['fdEndDay']);
					fdRestStartTime.val(resultObj['fdRestStartTime']);
					fdRestEndTime.val(resultObj['fdRestEndTime']);
					fdTotalTime.val(resultObj['fdTotalTime']);
					fdTotalDay.val(resultObj['fdTotalDay']);
                    fdRestEndType.val(resultObj['fdRestEndType']);
                    fdRestStartType.val(resultObj['fdRestStartType']);
					$(editTR).find('td:eq(0)').html(getFdWeekText(resultObj['fdWeek']));
					var fdWorkTimeText = '';
					if(resultObj['fdWork'] == '1') {
						if(resultObj['fdWorkTime[0].fdOverTimeType']=="2"){
							fdWorkTimeText="(${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') })";
						}
						fdWorkTimeText = resultObj['fdWorkTime[0].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[0].fdEndTime'] + fdWorkTimeText;
					} else if(resultObj['fdWork'] == '2'){
						if(resultObj['fdWorkTime[1].fdOverTimeType']=="2"){
							fdWorkTimeText="(${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') })";
						}
						fdWorkTimeText = resultObj['fdWorkTime[0].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[0].fdEndTime']
										+ ';&nbsp;' + resultObj['fdWorkTime[1].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[1].fdEndTime'] + fdWorkTimeText;
					}
					$(editTR).find('td:eq(1)').html(fdWorkTimeText);
				}
			},{width: 950, height: 500});
		};
	});
	
	window.deleteTimeSheet = function() {
		DocList_DeleteRow();
		setTimeout(function() {
			var count = $('#wTimeSheet input[name^="sysAttendCategoryForm.fdTimeSheets["][name$="].fdWeek"]').length;
			if(count <= 0) {
				$('#tSheetCountVld').show();
			}

			if(count <= 2){
				$('#tSheetCountTips').hide();
			}
		}, 0);
	}
	
	window.validateTSheetCount = function() {
		if($('[name $="fdShiftType"]:checked').val() == '0' && $('select[name $="fdSameWorkTime"]').val() == '1'
				&& $('#wTimeSheet input[name^="sysAttendCategoryForm.fdTimeSheets["]').length <= 0) {
			$('#tSheetCountVld').show();
			$("html,body").animate({scrollTop:$('#tSheetCountVld').offset().top - $(window).height()/2},200);
			return false;
		} else {
			$('#tSheetCountVld').hide();
			return true;
		}
	};
	
	var changeIsPatch = function() {
		if($('[name $="fdIsPatch"]').val() == 'false'){
			$('.patchContent').hide();
		} else {
			$('.patchContent').show();
		}
	};
	
	window.changeFocus=function(name){
		$('input[name$="'+name+'"]:enabled:visible').focus();
	}
	
	window.getDateTime=function(time,type){
	    if(!time){
	        return;
        }

		var date=new Date();
		if(type && type==2){
			date.setDate(date.getDate()+1);
		}
        var timeArr =time.split(':');

		date.setHours(parseInt(timeArr[0]),parseInt(timeArr[1]),0);
		return date;
	}
	
	//以下为校验器
	
	cateValidation.addValidator('beforeFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.beforeFirstStart') }", function(v,e,o){
		if(getShiftType() =='1'){
			return true;
		}
		cateValidation.validateElement($('input[name$="fdWorkTime[0].fdStartTime"]:enabled')[0]);
		var firstStart = $('input[name$="fdWorkTime[0].fdStartTime"]:enabled').val();
		if(firstStart && v) {
			return firstStart >= v;
		} else {
			return true;
		}
	});
	
	cateValidation.addValidator('afterOpen', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterOpen') }", function(v,e,o){
		if(getShiftType() =='1'){
			var openTime = $('input[name$="fdAreaStartTime"]:enabled').val();
			var isAcrossDay = $('select[name$="fdEndDay"]:enabled').val();
			if(isAcrossDay != '2' && openTime && v) {
				return openTime <= v;
			}
			return true;
		}
		var openTime = $('input[name$="fdStartTime"]:enabled').val();
		if(openTime && v) {
			return openTime <= v;
		} else {
			return true;
		}
	});
	
	cateValidation.addValidator('afterFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterFirstStart') }", function(v,e,o){
		var firstStart = $('input[name $="fdWorkTime[0].fdStartTime"]:enabled').val();
		var overTimeType = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		if(firstStart && v) {
			if(overTimeType && overTimeType==2) {
				return true;	
			}
			return firstStart <= v;
		} else {
			return true;
		}
	});
	
	cateValidation.addValidator('afterAcrossFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstStart') }", function(v,e,o){
		if($(e).is(':disabled'))
			return true;
		var firstStart = $('input[name $="fdWorkTime[0].fdStartTime"]:enabled').val();
		var overTimeType = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		var overTimeType2 = $('select[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if($(e).attr("name")=="fdWorkTime[1].fdEndTime"){
			overTimeType=overTimeType2;
		}
		if(firstStart && v && overTimeType && overTimeType==2) {
			return firstStart > v;	
		} 
		return true;
	});
	
	cateValidation.addValidator('afterAcrossFirstEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstEnd') }", function(v,e,o){
		if($(e).is(':disabled'))
			return true;
		var firstEnd = $('input[name $="fdWorkTime[0].fdEndTime"]:enabled:visible').val();
		var overTimeType = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		if(firstEnd && v && overTimeType && overTimeType==2) {
			return firstEnd < v;	
		} 
		return true;
	});
	
	cateValidation.addValidator('afterAcrossSecondEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossSecondEnd') }", function(v,e,o){
		if($(e).is(':disabled'))
			return true;
		var secondEnd = $('input[name $="fdWorkTime[1].fdEndTime"]:enabled:visible').val();
		var overTimeType = $('select[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if(secondEnd && v && overTimeType && overTimeType==2) {
			return secondEnd < v;	
		}
		return true;
	});
	
	cateValidation.addValidator('afterFirstEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterFirstEnd') }", function(v,e,o){
		if($(e).is(':disabled'))
			return true;
		var firstEnd = $('input[name $="fdWorkTime[0].fdEndTime"]:enabled').val();
		var overTimeType = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		if(firstEnd && v) {
			if(overTimeType && overTimeType==2) {
				return false;
			} else {
				return firstEnd <= v;
			}
		} else {
			return true;
		}
		
	});
	
	cateValidation.addValidator('afterSecondStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterSecondStart') }", function(v,e,o){
		if($(e).is(':disabled'))
			return true;
		var secondStart = $('input[name $="fdWorkTime[1].fdStartTime"]:enabled').val();
		var overTimeType = $('select[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if(overTimeType && overTimeType==2) {
			return true;
		}
		if(secondStart && v) {
			return secondStart <= v;
		} else {
			return true;
		}
	});
	
	cateValidation.addValidator('afterAcrossSecondStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstStart') }", function(v,e,o){
		if($(e).is(':disabled'))
			return true;
		var secondStart = $('input[name $="fdWorkTime[0].fdStartTime"]:enabled').val();
		var overTimeType = $('select[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if(secondStart && v && overTimeType && overTimeType==2) {
			return secondStart > v;
		} else {
			return true;
		}
	});
	
	cateValidation.addValidator('afterEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterEnd') }", function(v,e,o){
		var firstEnd = $('input[name $="fdWorkTime[0].fdEndTime"]:enabled:visible').val();
		var secondEnd = $('input[name $="fdWorkTime[1].fdEndTime"]:enabled:visible').val();
		var isAcrossDay = $('select[name $="fdEndDay"]:enabled:visible').val();
		var overTimeType = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		if(overTimeType && overTimeType==2) {
			if(isAcrossDay != '2'){
				return false;
			}else{
				var endTime = secondEnd || firstEnd;
				if(endTime) {
					return endTime <v;
				}
			}
		} else {
			if(isAcrossDay != '2'){
				var endTime = secondEnd || firstEnd;
				if(endTime) {
					return endTime <=v;
				}
			}
		}
		return true;
	});
	
	cateValidation.addValidator('afterArcossEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterEnd') }", function(v,e,o){
		var secondEnd = $('input[name $="fdWorkTime[1].fdEndTime"]:enabled:visible').val();
		var isAcrossDay = $('select[name $="fdEndDay"]:enabled:visible').val();
		var overTimeType2 = $('select[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if(overTimeType2 && overTimeType2==2) {
			if(isAcrossDay != '2'){
				return false;
			}else{
				if(secondEnd) {
					return secondEnd <v;
				}
			}
		} else {
			if(isAcrossDay != '2'){
				if(secondEnd) {
					return secondEnd <=v;
				}
			}
		}
		return true;
	});
	
	cateValidation.addValidator('acrossDay', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.acrossDay') }", function(v,e,o){
		if($(e).is(':visible')){
			var isAcrossDay = $('select[name $="fdEndDay"]:enabled:visible').val();
			var fdStartTime = $('input[name $="fdStartTime"]:enabled:visible').val();
			if(getShiftType() =='1'){
				fdStartTime = $('input[name $="fdAreaStartTime"]:enabled:visible').val();
			}
			if(isAcrossDay == '2' && fdStartTime){
				return fdStartTime > v;
			}
		}
		return true;
	});
	
	cateValidation.addValidator('macIp',"${ lfn:message('sys-attend:sysAttendCategory.validate.macIp') }",function(v,e,o){
		if(/[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/.test(v)){
			return true;
		}
		return false;
	});
	cateValidation.addValidator('checkMacIp',"${ lfn:message('sys-attend:sysAttendCategory.validate.macIp.duplicate') }",function(v,e,o){
		if(!isWifiRepeaded(v,true)){
			return true;
		}
		return false;
	});
	
	cateValidation.addValidator('flexTimeVld',"${ lfn:message('sys-attend:sysAttendCategory.validate.fdFlexTime') }",function(v,e,o){
		var onTime1 = $('[name $="fdWorkTime[0].fdStartTime"]:enabled').val();
		var offTime1 = $('[name $="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name $="fdWorkTime[1].fdStartTime"]:enabled').val();
		var offTime2 = $('[name $="fdWorkTime[1].fdEndTime"]:enabled').val();
		var fdEndTime = $('[name ="sysAttendCategoryForm.fdEndTime"]:enabled').val();
		var fdEndDay = $('[name ="sysAttendCategoryForm.fdEndDay"]:enabled').val();
		var type1 = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		var type2 = $('select[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();

		if(!v) {
			return true;
		}
		if(!fdEndTime) {
			return true;
		}
		var endMins = fdEndDay == '2' ? getDateTime(fdEndTime,2).getTime():getDateTime(fdEndTime,1).getTime();

        /* var endMins = fdEndDay == '2' ? (parseInt(fdEndTime.split(':')[0]) + 24) * 60 + parseInt(fdEndTime.split(':')[1]) : parseInt(fdEndTime.split(':')[0] * 60) + parseInt(fdEndTime.split(':')[1]); */
        if(onTime2 && offTime2) {
            var dateStart2=getDateTime(onTime2,1);
            var dateEnd2=getDateTime(offTime2,type2);
            var onMins2 = dateStart2.getTime();
            var offMins2 = dateEnd2.getTime();
            /* var onMins2 = parseInt(onTime2.split(':')[0] * 60) + parseInt(onTime2.split(':')[1]);
            var offMins2 = parseInt(offTime2.split(':')[0] * 60) + parseInt(offTime2.split(':')[1]); */
            if(parseInt(v) > ((offMins2 - onMins2)/(60*1000)))
                return false;
            if(parseInt(v) > ((endMins - offMins2)/(60*1000)))
                return false;
        }
        if(onTime1 && offTime1) {
            var dateStart1=getDateTime(onTime1,1);
            var dateEnd1=getDateTime(offTime1,type1);
            var onMins1 = dateStart1.getTime();
            var offMins1 = dateEnd1.getTime();
            /* var onMins1 = parseInt(onTime1.split(':')[0] * 60) +  parseInt(onTime1.split(':')[1]);
            var offMins1 = parseInt(offTime1.split(':')[0] * 60) + parseInt(offTime1.split(':')[1]); */
            if(parseInt(v) > ((offMins1 - onMins1)/(60*1000)))
                return false;
            if(parseInt(v) > ((endMins - offMins1)/(60*1000)))
                return false;
        }

		return true;
	});
	
	cateValidation.addValidator('maxLate',"${ lfn:message('sys-attend:sysAttendCategory.validate.maxLate') }",function(v,e,o){
		if(!v) {
			return true;
		}
		var fdShiftType = $('[name $="fdShiftType"]:enabled').val();
		if(fdShiftType=="1"){
			return true;
		}
		var onTime1 = $('[name $="fdWorkTime[0].fdStartTime"]:enabled').val();
		var offTime1 = $('[name $="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name $="fdWorkTime[1].fdStartTime"]:enabled').val();
		var offTime2 = $('[name $="fdWorkTime[1].fdEndTime"]:enabled').val();
		var type1 = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		var type2 = $('select[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if(onTime1 && offTime1) {
			var dateStart1=getDateTime(onTime1,1);
			var dateEnd1=getDateTime(offTime1,type1);
			var onMins1 = dateStart1.getTime();
			var offMins1 = dateEnd1.getTime();
			var workMins1 =(offMins1 - onMins1)/(60*1000);
			/* var workMins1 = parseInt(offTime1.split(':')[0]) * 60 + parseInt(offTime1.split(':')[1]) - parseInt(onTime1.split(':')[0]) * 60 - parseInt(onTime1.split(':')[1]); */
			if(parseInt(v) > workMins1) {
				return false;
			}
		}
		var workTypeField = $('input[name $="fdWork"]:hidden').val();
		if(workTypeField=="1"){
			return true;
		}
		if(onTime2 && offTime2) {
			var dateStart2=getDateTime(onTime2,1);
			var dateEnd2=getDateTime(offTime2,type2);
			var onMins2 = dateStart2.getTime();
			var offMins2 = dateEnd2.getTime();
			var workMins2 = (offMins2 - onMins2)/(60*1000);
			/* var workMins2 = parseInt(offTime2.split(':')[0]) * 60 + parseInt(offTime2.split(':')[1]) - parseInt(onTime2.split(':')[0]) * 60 - parseInt(onTime2.split(':')[1]); */
			if(parseInt(v) > workMins2) {
				return false;
			}
		}
		return true;
	});
	
	cateValidation.addValidator('maxLeft',"${ lfn:message('sys-attend:sysAttendCategory.validate.maxLeft') }",function(v,e,o){
		if(!v) {
			return true;
		}
		var fdShiftType = $('[name $="fdShiftType"]:enabled').val();
		if(fdShiftType=="1"){
			return true;
		}
		var onTime1 = $('[name $="fdWorkTime[0].fdStartTime"]:enabled').val();
		var offTime1 = $('[name $="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name $="fdWorkTime[1].fdStartTime"]:enabled').val();
		var offTime2 = $('[name $="fdWorkTime[1].fdEndTime"]:enabled').val();
		var type1 = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		var type2 = $('select[name $="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		
		if(onTime1 && offTime1) {
			var dateStart1=getDateTime(onTime1,1);
			var dateEnd1=getDateTime(offTime1,type1);
			var onMins1 = dateStart1.getTime();
			var offMins1 = dateEnd1.getTime();
			var workMins1 =(offMins1 - onMins1)/(60*1000);
			/* var workMins1 = parseInt(offTime1.split(':')[0]) * 60 + parseInt(offTime1.split(':')[1]) - parseInt(onTime1.split(':')[0]) * 60 - parseInt(onTime1.split(':')[1]); */
			if(parseInt(v) > workMins1) {
				return false;
			}
		}
		var workTypeField = $('input[name $="fdWork"]:hidden').val();
		if(workTypeField=="1"){
			return true;
		}
		if(onTime2 && offTime2) {
			var dateStart2=getDateTime(onTime2,1);
			var dateEnd2=getDateTime(offTime2,type2);
			var onMins2 = dateStart2.getTime();
			var offMins2 = dateEnd2.getTime();
			var workMins2 = (offMins2 - onMins2)/(60*1000);
			/* var workMins2 = parseInt(offTime2.split(':')[0]) * 60 + parseInt(offTime2.split(':')[1]) - parseInt(onTime2.split(':')[0]) * 60 - parseInt(onTime2.split(':')[1]); */
			if(parseInt(v) > workMins2) {
				return false;
			}
		}
		return true;
	});
	
	cateValidation.addValidator('fullLateAbscent',"${ lfn:message('sys-attend:sysAttendCategory.validate.fullAbscentLarger') }",function(v,e,o){
		if(!v) {
			return true;
		}
		
		var halfLateAbs = $('[name $="fdLateToAbsentTime"]:enabled').val();
		if(!halfLateAbs) {
			return true;
		} else {
			return parseInt(halfLateAbs) < parseInt(v);
		}
	});
	
	cateValidation.addValidator('fullLeftAbscent',"${ lfn:message('sys-attend:sysAttendCategory.validate.fullAbscentLarger') }",function(v,e,o){
		if(!v) {
			return true;
		}
		
		var halfLeftAbs = $('[name $="fdLeftToAbsentTime"]:enabled').val();
		if(!halfLeftAbs) {
			return true;
		} else {
			return parseInt(halfLeftAbs) < parseInt(v);
		}
	});
	
	cateValidation.addValidator('minLateToAbs',"${ lfn:message('sys-attend:sysAttendCategory.validate.minEqualLateToAbs') }",function(v,e,o){
		if(!v) {
			return true;
		}
		
		var fdLateTime = $('[name $="fdRule[0].fdLateTime"]:enabled').val();
		if(!fdLateTime || fdLateTime == '0') {
			return parseInt(v) > 0;
		} else {
			return parseInt(v) > parseInt(fdLateTime);
		}
	});
	
	cateValidation.addValidator('minLeftToAbs',"${ lfn:message('sys-attend:sysAttendCategory.validate.minEqualLeftToAbs') }",function(v,e,o){
		if(!v) {
			return true;
		}
		
		var fdLeftTime = $('[name $="fdRule[0].fdLeftTime"]:enabled').val();
		if(!fdLeftTime || fdLeftTime == '0') {
			return parseInt(v) > 0;
		} else {
			return parseInt(v) > parseInt(fdLeftTime);
		}
	});
	
	cateValidation.addValidator('restTimeNull',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeNull') }", function(v,e,o){
		var fieldName = $(e).attr('name');
		var restStart = $('[name $="fdRestStartTime"]:enabled');
		var restEnd = $('[name $="fdRestEndTime"]:enabled');
		if(restStart && restEnd) {
			if(v) {
				if(fieldName.indexOf('fdRestStartTime') > -1) {
					if(!restEnd.val()){
						cateValidation.validateElement(restEnd[0]);
					}
				} else if(fieldName.indexOf('fdRestEndTime') > -1){
					if(!restStart.val()){
						cateValidation.validateElement(restStart[0]);
					}
				}
				return true;
			} else {
				if(fieldName.indexOf('fdRestStartTime') > -1) {
					if(restEnd.val()){
						return false;
					}
				} else if(fieldName.indexOf('fdRestEndTime') > -1){
					if(restStart.val()){
						return false;
					}
				}
				return true;
			}
		} else {
			return true;
		}
	});

    cateValidation.addValidator('restTimeStart',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeStart') }", function(v,e,o){
        //午休时间的验证规则。结束时间必须大于等于开始时间
        if(!v) {
            return true;
        }
        var restStart = $('[name $="fdRestStartTime"]:enabled').val();
        var restEnd = $('[name $="fdRestEndTime"]:enabled').val();
        //午休开始时间类型
        var fdRestStartType = $('select[name $="fdRestStartType"]:enabled').val();
        //午休结束时间类型
        var fdRestEndType = $('select[name $="fdRestEndType"]:enabled').val();

        if(restEnd && restStart){
            //午休开始时间
            restStart =getDateTime(restStart,fdRestStartType);
            //午休结束时间
            restEnd = getDateTime(restEnd,fdRestEndType);
            return restEnd.getTime() >= restStart.getTime();
        }
        return true;

    });

	cateValidation.addValidator('restTimeRange',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeRange') }", function(v,e,o){
        //午休开始时间
        var restStart = $('[name $="sysAttendCategoryForm.fdRestStartTime"]:enabled').val();
        //午休开始类型
        var fdRestStartType = $('select[name $="sysAttendCategoryForm.fdRestStartType"]:enabled').val();
        if(restStart){
            restStart =getDateTime(restStart,fdRestStartType);
        }
        //午休结束时间
        var restEnd = $('[name $="sysAttendCategoryForm.fdRestEndTime"]:enabled').val();
        //午休结束类型
        var fdRestEndType = $('select[name $="sysAttendCategoryForm.fdRestEndType"]:enabled').val();
        if(restEnd){
            restEnd =getDateTime(restEnd,fdRestEndType);
        }
        //1班次的上班时间
        var workStartTime = $('[name $="fdWorkTime[0].fdStartTime"]:enabled').val();
        if(workStartTime){
            workStartTime =getDateTime(workStartTime,1);
        }
        //1班次的下班时间
        var workEndTime = $('[name $="fdWorkTime[0].fdEndTime"]:enabled').val();
        //1班次的下班时间是次日还是当日
        var overTimeType1 = $('select[name $="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
        if(workEndTime){
            workEndTime =getDateTime(workEndTime,overTimeType1);
        }
        if(restStart && restEnd && workStartTime && workEndTime){
            //开始打卡时间 小于休息开始时间，午休结束时间 小于 打卡结束时间
            return workStartTime < restStart && restEnd <= workEndTime;
        }
	});
	
	cateValidation.addValidator('startCompareEnd2',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.endBiggerStart') }", function(v,e,o){
		var name = e.name;
		name = name.substring(0, name.indexOf('.', 0) + 1) + "fdStartTime";
		cateValidation.validateElement($('[name $="'+name+'"]:enabled')[0]);
		return true;
	});
	
	cateValidation.addValidator('startCompareEnd',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.endBiggerStart') }", function(v,e,o){
		if(!v) {
			return true;
		}
		
		var fdStartTimes = $('#overtimeDeducts').find('input[name$="fdStartTime"]');
		var fdEndTimes = $('#overtimeDeducts').find('input[name$="fdEndTime"]');
		
		if(!fdStartTimes || fdStartTimes.length==0 || !fdEndTimes || fdEndTimes.length==0){
			return true;
		}
		
		
		// 当前时间段
		var currentObj = {};
		
		for(var i =0;i<fdStartTimes.length;i++){
			if(e.id == fdStartTimes[i].id || e.id == fdEndTimes[i].id) {
				currentObj.fdStartTime = fdStartTimes[i].value;
				currentObj.fdEndTime = fdEndTimes[i].value;
				break;
			}
		}
		
		if(currentObj.fdStartTime != "" && currentObj.fdEndTime != "" 
				&& currentObj.fdStartTime >= currentObj.fdEndTime) {
			return false;
		}
		
		return true;
	});
	
	cateValidation.addValidator('thresholdBiggerHours',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.thresholdBiggerHours') }", function(v,e,o){
		console.log("----------------------")
		if(!v) {
			return true;
		}
		
		var threshold = $('#timethreshold').find('input[name$="overtimeDeducts[0].fdThreshold"]').val();
		var hours = $('#timethreshold').find('input[name$="overtimeDeducts[0].fdDeductHours"]').val();
		
		if(!threshold || threshold.length==0 || !hours || hours.length==0){
			return true;
		} 
		
		if(parseInt(threshold) <= parseInt(hours)) {
			return false;
		}
		
		return true;
	});
	
	cateValidation.addValidator('thresholdBiggerHours2',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.thresholdBiggerHours') }", function(v,e,o){
		cateValidation.validateElement($('[name $="overtimeDeducts[0].fdThreshold"]:enabled')[0]);
		return true;
	});
	
	cateValidation.addValidator('timeRangeMixed2',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.timeRangeMixed') }", function(v,e,o){
		var name = e.name;
		name = name.substring(0, name.indexOf('.', 0) + 1) + "fdStartTime";
		cateValidation.validateElement($('[name $="'+name+'"]:enabled')[0]);
		return true;
	});
	
	// 校验时间是否交叉
	cateValidation.addValidator('timeRangeMixed',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.timeRangeMixed') }", function(v,e,o){
		if(!v) {
			return true;
		}

		var fdStartTimes = $('#overtimeDeducts').find('input[name$="fdStartTime"]');
		var fdEndTimes = $('#overtimeDeducts').find('input[name$="fdEndTime"]');
		
		if(!fdStartTimes || fdStartTimes.length==0 || !fdEndTimes || fdEndTimes.length==0){
			return false;
		}
		
		// 其他时间段
		var objArr = [];
		// 当前时间段
		var currentObj = {};
		
		for(var i =0;i<fdStartTimes.length;i++){
			if(e.name == fdStartTimes[i].name || e.name == fdEndTimes[i].name) {
				currentObj.fdStartTime = fdStartTimes[i].value;
				currentObj.fdEndTime = fdEndTimes[i].value;
				continue;
			}
			var obj = {};
			obj.fdStartTime = fdStartTimes[i].value;
			obj.fdEndTime = fdEndTimes[i].value;
			objArr.push(obj);
		}
		
		for(var i =0;i<objArr.length;i++){
			if(v > objArr[i].fdStartTime && v < objArr[i].fdEndTime) {
				return false;
			}
			
			if(currentObj.fdStartTime != "" && currentObj.fdEndTime != "") {
				if(currentObj.fdStartTime == objArr[i].fdStartTime
						&& currentObj.fdEndTime == objArr[i].fdEndTime) {
					return false;
				}
			}
		}

		return true;
	});
	

	
	cateValidation.addValidator('restTimeEnd',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeEnd') }", function(v,e,o){
		cateValidation.validateElement($('[name $="fdRestStartTime"]:enabled')[0]);
		return true;
	});
	
	cateValidation.addValidator('firstEndTime',"${ lfn:message('sys-attend:sysAttendCategory.validate.firstEndTime') }", function(v,e,o){
		if(!v) {
			return true;
		}
		var offTime1 = $('[name $="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name $="fdWorkTime[1].fdStartTime"]:enabled').val();
		if(offTime1 && onTime2) {
			return v >= offTime1 && v <= onTime2;
		}
	});
	
	cateValidation.addValidator('secondStartTime',"${ lfn:message('sys-attend:sysAttendCategory.validate.secondStartTime') }", function(v,e,o){
		if(!v) {
			return true;
		}
		var offTime1 = $('[name $="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name $="fdWorkTime[1].fdStartTime"]:enabled').val();
		if(offTime1 && onTime2) {
			return v >= offTime1 && v <= onTime2;
		}
	});
	
	//判断是否为大于0的数(可以为小数点)
	cateValidation.addValidator('minUnitHour',"<span class=\"validation-advice-title\">${ lfn:message('sys-attend:sysAttendCategory.overtime.unit') }</span>&nbsp;${ lfn:message('sys-attend:sysAttendCategory.validate.minUnitHour') }</span>",function(v,e,o){
		var fdRoundingType = $('select[name $="fdRoundingType"]').val();
		if(fdRoundingType=='0'){
			return true;
		}
		if(/^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/.test(v) && fdRoundingType!='0'){
			return true;
		}	
		return false;
	});
	
	seajs.use(['lui/dialog','lui/jquery','sys/attend/resource/js/dateUtil'],function(dialog,$,dateUtil){
		window.onSubmitMethod = function (method){
            if(cateValidation.validate()) {
                var tipTxt = "${ lfn:message('sys-attend:sysAttendCategory.fdEffectTime.staTip') }";
                if(!validateTSheetCount()){
                    return;
                }
                if(!validateAttendMode()){
                    return;
                }
                if(getShiftType()=='1'){
                    $('input[name="sysAttendCategoryForm.fdStartTime"]').val($('input[name$="fdAreaStartTime"]').val());
                    $('input[name="sysAttendCategoryForm.fdEndTime"]').val($('input[name$="fdAreaEndTime"]').val());
                }
                //把fdLimitLocations转成fdLocations
                var fields=$(document.sysAttendHisCategoryForm).serializeArray();

                var index=-1;
                var limitindex=-1;
                var limit;
                var rowindex0="0";
                var locationsTempHiddenDiv=$("#locationsTempHiddenDiv");
                //先清空地址信息的隐藏数据
                locationsTempHiddenDiv.html("");
                for(var i=0;i<fields.length;i++){
                    if(fields[i].name.indexOf("fdLimitLocations")>-1){
                        var name=fields[i].name;
                        var rowIndex,limitIndex;

                        if(name.lastIndexOf(".fdLimit")>40){
                            limit=fields[i].value;
                            index++;
                            limitindex++;
                            rowindex0="0";

                            var childNode = document.createElement('input');
                            childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].fdDataType');
                            childNode.setAttribute('value', 'newdata');
                            childNode.setAttribute('type', 'hidden');
                            locationsTempHiddenDiv.append(childNode);

                            var childNode = document.createElement('input');
                            childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].fdLimit');
                            childNode.setAttribute('value', limit);
                            childNode.setAttribute('type', 'hidden');
                            locationsTempHiddenDiv.append(childNode);

                            var childNode = document.createElement('input');
                            childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].fdRow');
                            childNode.setAttribute('value', "0");
                            childNode.setAttribute('type', 'hidden');
                            locationsTempHiddenDiv.append(childNode);
                            var childNode = document.createElement('input');
                            childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].fdLimitIndex');
                            childNode.setAttribute('value', limitindex);
                            childNode.setAttribute('type', 'hidden');
                            locationsTempHiddenDiv.append(childNode);

                        }else{
                            limitIndex=name.substring(name.indexOf("[")+1);
                            limitIndex=limitIndex.substring(0,limitIndex.indexOf("]"));
                            rowIndex=name.substring(name.lastIndexOf("[")+1);
                            rowIndex=rowIndex.substring(0,rowIndex.indexOf("]"));
                            if(rowindex0!=rowIndex){
                                rowindex0=rowIndex;
                                index++;

                                var childNode = document.createElement('input');
                                childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].fdDataType');
                                childNode.setAttribute('value', 'newdata');
                                childNode.setAttribute('type', 'hidden');
                                locationsTempHiddenDiv.append(childNode);

                                var childNode = document.createElement('input');
                                childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].fdLimit');
                                childNode.setAttribute('value', limit);
                                childNode.setAttribute('type', 'hidden');
                                locationsTempHiddenDiv.append(childNode);

                                var childNode = document.createElement('input');
                                childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].fdRow');
                                childNode.setAttribute('value', rowIndex);
                                childNode.setAttribute('type', 'hidden');
                                locationsTempHiddenDiv.append(childNode);
                                var childNode = document.createElement('input');
                                childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].fdLimitIndex');
                                childNode.setAttribute('value', limitIndex);
                                childNode.setAttribute('type', 'hidden');
                                locationsTempHiddenDiv.append(childNode);
                            }
                            name=name.substring(name.lastIndexOf(".")+1);
                            var childNode = document.createElement('input');
                            childNode.setAttribute('name', 'sysAttendCategoryForm.fdLocations['+index+'].'+name);
                            childNode.setAttribute('value', fields[i].value);
                            childNode.setAttribute('type', 'hidden');
                            locationsTempHiddenDiv.append(childNode);
                        }
                    }
                }
                var fdCanMap = $('input[name$="fdCanMap"]').val();
                if(fdCanMap=='true') {
                    var isMapAddressError =false;
                    //开启地图以后。验证地图中的坐标不是空，才允许提交
                    var fdLocationCoordinate = locationsTempHiddenDiv.find('input[name$="fdLocationCoordinate"]');
                    if (fdLocationCoordinate && fdLocationCoordinate.length > 0) {
                        for(var tempLocaltion=0;tempLocaltion<fdLocationCoordinate.length;tempLocaltion++){
                            if(fdLocationCoordinate[tempLocaltion]) {
                                var v = $(fdLocationCoordinate[tempLocaltion]).val();
                                if(!v || v.length ==0){
                                    isMapAddressError =true;
                                }
                            }
                        }
                    }
                    if (isMapAddressError) {
                        dialog.alert('${ lfn:message("sys-attend:sysAttendCategory.fdLocations.error") }');
                        return;
                    }
                }
			    if(method=='update'){
                    dialog.confirm('${ lfn:message("sys-attend:sysAttendHisCategory.tip6") }', function (flag, d) {

                    }, null, [{
                        name: '${ lfn:message("button.ok") }',
                        value: true,
                        focus: true,
                        fn: function (value, dialog) {
                            dialog.hide(value);
                            Com_Submit(document.sysAttendHisCategoryForm,method);
                        }
                    }, {
                        name: '${ lfn:message("button.cancel") }',
                        value: false,
                        styleClass : 'lui_toolbar_btn_gray',
                        fn: function (value, dialog) {
                            dialog.hide(value);
                        }
                    }]);
                }
            }
		};
		window.getTomorrowDate=function(){
			var now = new Date();
			now.setTime(now.getTime()+24*60*60*1000);
			now.setHours(0,0,0,0)
			return now;
		};
		window.getCategoryStatus = function(d){
			var fdEffectTime = $('input[name $="fdEffectTime"]').val();
			fdEffectTime= Com_GetDate(fdEffectTime, 'date', Com_Parameter.Date_format);
			if(d==1){
				if(fdEffectTime<getTomorrowDate()){
					return '1';
				}
			}else{
				//小于今天
				var now = new Date();
				now.setHours(0,0,0,0)
				if(fdEffectTime< now){
					return '1';
				}
			}
			return '0';
		};
		window.isExistRecord = function(callback){
			var fdTargetIds = $('input[name $="fdTargetIds"]').val();
			if(!fdTargetIds) {
				cateValidation.validate();
				return;
			}
			var datas = {targets:fdTargetIds};
			jQuery.ajax({
	            type: "post", 
	            url: "${LUI_ContextPath}/sys/attend/sys_attend_main/sysAttendMain.do?method=isExistRecord", 
	            dataType: "json",
	            data:datas,
	            success: function (data) {
	            	callback && callback(data);
	            }
			});
		}
		
		cateValidation.addValidator('afterNow',"${ lfn:message('sys-attend:sysAttendCategory.fdEffectTime.tip') }",function(v){
			var result = true;
			var now = new Date();
			now.setHours(0,0,0,0)
			if(v){
				var start=Com_GetDate(v, 'date', Com_Parameter.Date_format);
				if(start.getTime() < now.getTime()){
					result = false;
				}
			}
			return result;
		});
		window.onSecurityMode = function(v,nodes){
			for(var i = 0;i<nodes.length;i++){
				if(nodes[i].value==v){
					var status = $(nodes[i]).data('cfg-selected');
					if(status=='true'){
						$(nodes[i]).data('cfg-selected','').removeAttr('checked');
						$('input[name $="fdSecurityMode"]').val('');
					}else{
						$(nodes[i]).data('cfg-selected','true')
					}
				}else{
					$(nodes[i]).data('cfg-selected','');
				}
			}
		};
		window.validateAttendMode = function(){
			if($('[name$="fdCanMap"]:hidden').val() == 'false' &&
					$('[name$="fdCanWifi"]:hidden').val() == 'false' &&
					$('[name$="fdDingClock"]:hidden').val() == 'false'){
				dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.signType') }");
			    return false;
	        }
			if($('[name$="fdCanMap"]:hidden').val() != 'false' ||
	                $('[name$="fdCanWifi"]:hidden').val() != 'false'){
				var locationCount = $("table[id*='locationsList_'] div[data-location-container]").length;
		        var wifiCount = $('#wifiConfigs [data-wifi-config]').length;
		        if(locationCount < 1 && wifiCount == 0 || locationCount == 0 && wifiCount < 1){
		        	dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.position') }");
		            return false;
		        }
			}
		   return true;
		};
		//打卡方式提醒
		window.onOnlyDingEnable = function(){
			var fdDingClock = $('[name$="fdDingClock"]:hidden').val();
			var fdCanMap = $('[name$="fdCanMap"]:hidden').val();
			var fdCanWifi = $('[name$="fdCanWifi"]:hidden').val();
			if(fdCanMap=='false' && fdCanWifi=='false' && fdDingClock=='true'){
				dialog.alert('${synConfigType}'=='qywx'?"${ lfn:message('sys-attend:sysAttendCategory.validate.signType.wx.tip') }":"${ lfn:message('sys-attend:sysAttendCategory.validate.signType.tip') }");
				return;
			}
			if(fdCanMap=='false' && fdCanWifi=='false' && fdDingClock=='false'){
				dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.signType') }");
				return;
			}
		};
		//批量导入
		window.addBatchPosition = function(listName){
			var uploadActionUrl = '${LUI_ContextPath}/sys/attend/sys_attend_category/sysAttendCategory.do?method=importExcel';
			var downloadTempletUrl =  '${LUI_ContextPath}/sys/attend/sys_attend_category/sysAttendCategory.do?method=downloadTemplate';
			var importTitle = "${lfn:message('sys-time:sysTimeLeaveAmount.import.batch')}";
			var url = '/sys/attend/upload/common_upload_download.jsp';
			url = Com_SetUrlParameter(url, 'uploadActionUrl', uploadActionUrl);
			url = Com_SetUrlParameter(url, 'downLoadUrl', downloadTempletUrl);
			url = Com_SetUrlParameter(url, 'isRollBack', false);
			dialog.iframe(url, importTitle ,function(data) {
			}, {
				width : 680,
				height : 380
			});
		};
		window.importExcelCallback = function(datas){
			var result = datas && datas.data;
			if(result && result.length>0){
				for(var i=0;i<result.length;i++){
					var record = result[i];
					//校验mac是否重复
					if(!isWifiRepeaded(record.mac)){
						DocList_AddRow('wifiConfigs',null,{'fdWifiConfigs[!{index}].fdName':record.name || '','fdWifiConfigs[!{index}].fdMacIp':record.mac});
					}
				}
			}
		};
		window.isWifiRepeaded = function(mac,isCurrent){
			var fdMacIps = $('#wifiConfigs').find('input[name$="fdMacIp"]');
			if(!fdMacIps || fdMacIps.length==0){
				return false;
			}
			var arr = [];
			for(var i =0;i<fdMacIps.length;i++){
				if(mac==fdMacIps[i].value){
					arr.push(mac);
				}
			}
			if(isCurrent){
				//手工编辑场景
				if(arr.length>1){
					return true;
				}
				return false;
			}
			if(arr.length>0){
				return true;
			}
			return false;
		};
		//地点签到重复校验
		window.isLocationRepeated=function(location){
			var fdLocations = $('#fdLimitLocations').find('input[name$="fdLocation"]');
			if(!fdLocations||fdLocations.length==1){
				return true;
			}
			var arr=[];
			for(var i =0;i<fdLocations.length;i++){
				if(location==$(fdLocations[i]).val()){
					arr.push(location);
				}
			}
			if(arr.length>1){
				return false;
			}
			return true;
		};
		cateValidation.addValidator('checkLocationRepeated',"${ lfn:message('sys-attend:sysAttendCategory.validate.location.duplicate') }", function(v, e, o) {
			if (!v) {
				return true;
			}
			return isLocationRepeated(v);
		});
	});
</script>