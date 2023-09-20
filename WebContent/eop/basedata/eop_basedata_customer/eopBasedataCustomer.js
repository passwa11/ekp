if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
		setTimeout(function(){initData();},3000);
	}else{//非IE
		$(document).ready(function(){
			initData();
		});
	};
function initData(){
	var len=$("#TABLE_DocList_fdAccountList_Form").find("tr").length-3;
	for(var i=0;i<len;i++){
		display(i,$("[name='fdAccountList_Form["+i+"].fdSupplierArea']:checked").val(),true);
	}
}
function changeArea(value,dom){
	var index=dom.name.substring(dom.name.indexOf("[")+1,dom.name.indexOf("]"));
	display(index,value,dom.checked);
}

//显示隐藏必填星号
function display(index,value,checked){
	var tr = DocListFunc_GetParentByTagName("TR");
	if(value=='1'&&checked){//境外，设置境外信息必填
		$(tr).find(".vat").find(".txtstrong").show();
		$(tr).find(".vat").find("input[type=text]").each(function(){
			var validate = $(this).attr("validate")||'';
			if(validate.indexOf('required')==-1){
				validate += ' required';
			}
			$(this).attr("validate",validate);
		});
	}else{
		$(tr).find(".vat").find(".txtstrong").hide();
		$(tr).find(".vat").parent().find(".validation-advice").hide();
		$(tr).find(".vat").find("input[type=text]").each(function(){
			var validate = $(this).attr("validate")||'';
				validate = validate.replace(/required/g,'');
			$(this).attr("validate",validate);
		});
		$(tr).find(".vat").find("[type=text],[type=hidden]").each(function(){
			$(this).val("");
		});
	}
}

//编辑时加载必填项
$(function(){
	$("#TABLE_DocList_fdAccountList_Form tr:gt(0)").each(function(){
		var fdSupplierArea = $(this).find("[name$=fdSupplierArea]:checked").val();
		if(fdSupplierArea =='1'){
			$(this).find(".vat").find(".txtstrong").show();
			var validate = $(this).find(".vat").find("input[type=text]").attr("validate");
			$(this).find(".vat").find("input[type=text]").attr("validate",validate+" required");
		}
	});
});

//选择账户归属地区
window.FSSC_SelectAccountArea = function(){
	var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
	 dialogSelect(false,'fssc_cmb_city_code','fdAccountList_Form[*].fdAccountAreaCode','fdAccountList_Form[*].fdAccountAreaName',function(rtn){
		 if(rtn){
			$("[name='fdAccountList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvincial']);
			$("[name='fdAccountList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
		 }
	 });
}

//选择账户归属地区
window.FSSC_SelectCbsAccountArea = function(){
	var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
	dialogSelect(false,'fssc_cbs_city','fdAccountList_Form[*].fdAccountAreaCode','fdAccountList_Form[*].fdAccountAreaName',function(rtn){
		if(rtn){
			$("[name='fdAccountList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvince']);
			$("[name='fdAccountList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
		}
	});
}
//选择账户归属地区
window.FSSC_SelectCmbIntAccountArea = function(){
	var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
	dialogSelect(false,'fssc_cmbint_city_code','fdAccountList_Form[*].fdAccountAreaCode','fdAccountList_Form[*].fdAccountAreaName',function(rtn){
		if(rtn){
			$("[name='fdAccountList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvincial']);
			$("[name='fdAccountList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
		}
	});
}
