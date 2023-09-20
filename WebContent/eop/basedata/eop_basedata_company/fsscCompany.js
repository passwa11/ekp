$(document).ready(function(){
	var fdJoinSystem=$("input[name='fdJoinSystem']:checked").val();
	if(fdJoinSystem=='U8'){
		$("#systemParam").show();
		$(".U8").show();
		$("#systemTitle").html(messageInfo['eopBasedataCompany.fdSystemParam.U8']);
	}else if(fdJoinSystem=='K3'){
		$("#systemParam").show();
		$(".K3").show();
		$("#systemTitle").html(messageInfo['eopBasedataCompany.fdSystemParam.K3']);
	}else if(fdJoinSystem=='EAS'){
		$(".Eas").show();
	}else if(fdJoinSystem=='K3Cloud'){
		$(".K3Cloud").show();
	}else{
		$("#systemParam").hide();
		$("#systemTitle").html('');
	}
});
function changeSystem(value,dom){
	if(value=='U8'){
		$("#systemParam").show();
		$("#systemTitle").html(messageInfo['eopBasedataCompany.fdSystemParam.U8']);
		$(".U8").show();
		$(".K3").hide();
		$(".Eas").hide();
		$(".K3Cloud").hide();
		clearValue();
	}else if(value=='K3'){
		$("#systemParam").show();
		$("#systemTitle").html(messageInfo['eopBasedataCompany.fdSystemParam.K3']);
		$(".U8").hide();
		$(".K3").show();
		$(".Eas").hide();
		$(".K3Cloud").hide();
		clearValue();
	}else if(value=='EAS'){
		$("#systemParam").hide();
		$(".U8").hide();
		$(".K3").hide();
		$(".Eas").show();
		$(".K3Cloud").hide();
		clearValue();
	}else if(value=='K3Cloud'){
		$("#systemParam").hide();
		$(".U8").hide();
		$(".K3").hide();
		$(".Eas").hide();
		$(".K3Cloud").show();
		clearValue();
	}else{
		$("#systemParam").hide();
		$(".U8").hide();
		$(".K3").hide();
		$(".Eas").hide();
		$(".K3Cloud").hide();
		clearValue();
	}
}

window.clearValue = function(rtn){
	$("input[name='fdUEightUrl']").val("");
	$("input[name='fdKUrl']").val("");
	$("input[name='fdKUserName']").val("");
	$("input[name='fdKPassWord']").val("");
	$("input[name='fdEUserName']").val("");
	$("input[name='fdEPassWord']").val("");
	$("input[name='fdESlnName']").val("");
	$("input[name='fdEDcName']").val("");
	$("input[name='fdELanguage']").val("");
	$("input[name='fdEDbType']").val("");
	$("input[name='fdEAuthPattern']").val("");
	$("input[name='fdELoginWsdlUrl']").val("");
	$("input[name='fdEImportVoucherWsdlUrl']").val("");
	$("input[name='fdSystemParam']").val("");
	$("input[name='fdK3cUrl']").val("");
	$("input[name='fdK3cId']").val("");
	$("input[name='fdK3cPersonName']").val("");
	$("input[name='fdK3cPassword']").val("");
	$("input[name='fdK3cIcid']").val("");
}
