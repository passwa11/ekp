seajs.use(['lui/jquery','lui/dialog','lang!eop-basedata'], function($, dialog,lang) {
	$(document).ready(function(){
		if(formInitData['fdCompanyGroup']=='false'){
			//不启用公司组，新建或者编辑预算方案时，公司组无法选择。原有的预算方案只能手动置为无效
			$("input[name='_fdDimension'][value=1]").prop("disabled","disabled");
		}
		//标准版费控,部门选项隐藏
		var version=$("[name='version']").val();
		if(version!="true"){
			$("#_xform_fdDimension").find("label").eq(9).css("display","none");
			$("#_xform_fdDimension").find("label").eq(9).next().next().css("display","none");
		}
	});
	/************************************************
	 * 修改期间校验函数，不限其他值置为空，不选中，选月、季度，自动勾选年
	 * 
	 ************************************************/
	window.changeValue=function(){
		var e = arguments.callee.caller.caller.arguments[0] || window.event; 
		e=e.srcElement||e.target;
		var val=$("input[name='fdPeriod']").val();
		if(val.indexOf(e.value)>-1){
			$("input[name='_fdPeriod'][value="+e.value+"]").prop("checked",true);
		}else{
			$("input[name='_fdPeriod'][value="+e.value+"]").prop("checked",false);
		}
		if(val&&e.value==1){//不限
			 $("input[name='_fdPeriod'][value=2]").prop("checked",false);
			 $("input[name='_fdPeriod'][value=3]").prop("checked",false);
			 $("input[name='_fdPeriod'][value=4]").prop("checked",false);
		}else if(val){
			$("input[name='_fdPeriod'][value=1]").prop("checked",false);
			$("input[name='_fdPeriod'][value=2]").prop("checked",true);
		}
		var array =[]; 
		$('input[name="_fdPeriod"]:checked').each(function(){  
			array.push($(this).val());
		}); 
		var fdPeriod="";
		for(var i=0,s=array.length;i<s;i++){
			fdPeriod+=array[i]+";";
		}
	    if(fdPeriod){
	    	$("input[name='fdPeriod']").val(fdPeriod.substring(0,fdPeriod.length-1));
	    }
	}
	/************************************************
	 * 修改预算维度校验函数，部门和成本中心、成本中心组、公司、公司组不能同时选中,选中公司下维度自动勾选公司
	 * 
	 ************************************************/
	window.changeDimension=function(){
		var e = arguments.callee.caller.caller.arguments[0] || window.event; 
		e=e.srcElement||e.target;
		var val=$("input[name='fdDimension']").val();
		if(val&&(e.value==4||e.value==1||e.value==2||e.value==3)&&e.checked){//成本中心、成本中心组
			//判断当前状态是否是选中，若是则取消选中
			$("input[name='_fdDimension'][value="+e.value+"]").prop("checked",true);  //成本中心选中
			$("input[name='_fdDimension'][value=11]").prop("checked",false);  //部门取消选中
		}else if(val&&e.value==11&&e.checked){//部门
			//判断当前状态是否是选中，若是则取消选中
			$("input[name='_fdDimension'][value=3]").prop("checked",false);//成本中心组取消选中
			$("input[name='_fdDimension'][value=4]").prop("checked",false);//成本中心取消选中
			$("input[name='_fdDimension'][value=11]").prop("checked",true);//部门选中
		}
		var array =[]; 
		$('input[name="_fdDimension"]:checked').each(function(){ 
			array.push($(this).val());
		}); 
		var fdDimension="";
		for(var i=0,s=array.length;i<s;i++){
			fdDimension+=array[i]+";";
		}
		if(fdDimension){
			$("input[name='fdDimension']").val(fdDimension.substring(0,fdDimension.length-1));
		}
	};
	Com_Parameter.event.submit.push(function(){
		var method = Com_GetUrlParameter(location.href,"method");
		if(method=="edit"){
			var dbDimension=formInitData["dbDimension"]+";";
			var fdDimension=$("input[name='fdDimension']").val()+";";
			if((dbDimension.indexOf("2;")>-1&&fdDimension.indexOf("2;")==-1)||(dbDimension.indexOf("2;")==-1&&fdDimension.indexOf("2;")>-1)){//编辑了公司维度提示不让修改
				dialog.alert(lang["message.change.company.tips"]);
			}else{
				return true;
			}
		}else{
			return true;
		}
	})
	$KMSSValidation().addValidator('checkName()', lang['message.budget.scheme.same.name'],function(){
		return checkName();
	});
	window.checkName=function(){
		var fdName = $("input[name='fdName']").val();
		//页面上前后去空格
		fdName=$.trim(fdName);
		//把前后去空格的fdName赋值给fdName保证保存至数据库中前后无空格
		$("input[name='fdName']").val(fdName);
		if(fdName!=""&&fdName!=null){
				//判断编辑时 fdName是否发生变化，如发生变化则建议，没有在返回true
			var method=$("input[name='method_GET']").val();
			var fdHiddenName=$("input[name='fdHiddenName']").val();
		    if(method=='edit' && fdName == fdHiddenName){
		    	return true;
		    }
		    fdName=encodeURI(fdName); //中文两次转码
		    fdName=encodeURI(fdName);
			//通过eopBasedataBudgetSchemeService查询数据库中是否已存在改fdName
		    var url ="eopBasedataBudgetSchemeService&fdName="+fdName+"&source="+"validateSameName";
			var data = new KMSSData();
			var rtnVal = data.AddBeanData(url).GetHashMapArray().length;
			//rtnVal大于0说明数据库中存在，校验不通过返回false
			if(rtnVal > 0){
				return false;
			}else{
				return true;
			}
		}else{
			  return true;
		}
	}
});
