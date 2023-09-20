<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">

	$(function() {
		var fdHistory_vstatusLength = $('input[name="fdHistory_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdHistory_vstatusLength; rowIndex++) {
			$('input[name="fdHistory_Form['+rowIndex+'].fdPostNew"]').val( $('input[name="fdHistory_Form['+rowIndex+'].fdPost"]').val());
			$('input[name="fdHistory_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdHistory_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdHistory_Form[' + rowIndex + '].fdStartDateNew"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdStartDate"]').val());
			$('input[name="fdHistory_Form[' + rowIndex + '].fdEndDateNew"]').val($('input[name="fdHistory_Form['+rowIndex+ '].fdEndDate"]').val());
		}
		
		var fdEducations_vstatusLength = $('input[name="fdEducations_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdEducations_vstatusLength; rowIndex++) {
			$('input[name="fdEducations_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdEducations_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdEducations_Form['+rowIndex+'].fdMajorNew"]').val( $('input[name="fdEducations_Form['+rowIndex+'].fdMajor"]').val());
			$('input[name="fdEducations_Form['+rowIndex+'].fdAcademicNew"]').val( $('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecordName"]').val());
			$('input[name="fdEducations_Form[' + rowIndex + '].fdEntranceDateNew"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdEntranceDate"]').val());
			$('input[name="fdEducations_Form[' + rowIndex + '].fdGraduationDateNew"]').val($('input[name="fdEducations_Form['+rowIndex+ '].fdGraduationDate"]').val());
		}
		
		var fdTrains_vstatusLength = $('input[name="fdTrains_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdTrains_vstatusLength; rowIndex++) {
			$('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompanyNew"]').val( $('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompany"]').val());
			$('input[name="fdTrains_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdTrains_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdTrains_Form[' + rowIndex + '].fdStartDateNew"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdStartDate"]').val());
			$('input[name="fdTrains_Form[' + rowIndex + '].fdEndDateNew"]').val($('input[name="fdTrains_Form['+rowIndex+ '].fdEndDate"]').val());
		}
		
		var fdCertificate_vstatusLength = $('input[name="fdCertificate_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdCertificate_vstatusLength; rowIndex++) {
			$('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnitNew"]').val( $('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnit"]').val());
			$('input[name="fdCertificate_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdCertificate_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdCertificate_Form[' + rowIndex + '].fdIssueDateNew"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssueDate"]').val());
			$('input[name="fdCertificate_Form[' + rowIndex + '].fdInvalidDateNew"]').val($('input[name="fdCertificate_Form['+rowIndex+ '].fdInvalidDate"]').val());
		}
		
		var fdRewardsPunishments_vstatusLength = $('input[name="fdRewardsPunishments_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdRewardsPunishments_vstatusLength; rowIndex++) {
			$('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdRewardsPunishments_Form[' + rowIndex + '].fdDateNew"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+ '].fdDate"]').val());
		}
		
	});

	/*******************************************
	 * 添加工作经历 
	 *******************************************/

	function HR_AddRatifyEntryNew(form, element) {
		updateRequired(false);
		clearnValue(form, element);
		var newrow = DocList_AddRow(form);
		var rowIndex = $(newrow)[0].rowIndex;
		if ("TABLE_DocList_fdHistory_Form" == form) {
			$('#fdOOrderHistory' + rowIndex).html(rowIndex);
		} else if ("TABLE_DocList_fdEducations_Form" == form) {
			$('#fdOOrderEducations' + rowIndex).html(rowIndex);
		} else if ("TABLE_DocList_fdTrains_Form" == form) {
			$('#fdOOrderTrains' + rowIndex).html(rowIndex);
		} else if ("TABLE_DocList_fdCertificate_Form" == form) {
			$('#fdOOrderCertificate' + rowIndex).html(rowIndex);
		} else if ("TABLE_DocList_fdRewardsPunishments_Form" == form) {
			$('#fdOOrderRewardsPunishments' + rowIndex).html(rowIndex);
		}
		$(newrow).bind(
				'click',
				customClickRow(newrow, "add", $("#" + form + " tr:first"),
						form, element));
		DocList_DeleteRow(newrow);
	}

	/*******************************************
	 * 删除工作经历 
	 *******************************************/
	function HR_DelRatifyEntryNew() {
		updateRequired(true);
		/*$("#table_of_fdHistory_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdProjects_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdEducations_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdTrains_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdCertificate_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdRewardsPunishments_detail_edit").each(function(){
			$(this).remove();
		});*/
	}
	/**
	 * 因为submit会验证弹出层的表单。
	 * 该方法处理 必填和不必填的切换
	 */
	function updateRequired(isDel){
		var mustNameArr=["companyName","fdStartDateHistory","expName"
			,"fdMajorName"
			,"fdEntranceDate"
			,"certifiName"
			,"fdIssuingUnit"
			,"fdIssueDate"
			,"rewPuniName"
				,"fdStartDateTrain","trainName","fdTrainCompany"
		];

		for(var i in mustNameArr){
			var mustNameElement = $("[name='"+mustNameArr[i]+"']");
			if(mustNameElement && mustNameElement.length > 0){
				var element =$(mustNameElement[0]);
				var validate = element.attr('validate');
				if(isDel){
					var reg = new RegExp("required","g");//g,表示全部替换。
					validate =  validate.replace(reg,"");
				}else{
					if(validate.indexOf('required') == -1){
						validate = validate + ' required';
					}
				}
				element.attr('validate',validate)
			}

		}
	}
	/***************************************
	 * 编辑状态查看工作经历
	 **************************************/
	function HR_EditRatifyEntryNew(val, form, element) {
		clearnValue(form, element);
		$(val).parent().addClass("current");
		customClickRow($(val).parent(), "update", "", form, element);
	}
	function HR_ViewRatifyEntryNew(val, form, element) {
		clearnValue(form, element);
		$(val).addClass("current");
		customClickRow($(val),"view", "", form, element);
	}

</script>