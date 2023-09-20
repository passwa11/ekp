seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env','lang!fssc-budget','lang!','lang!eop-basedata'], function($, dialog , topic,env,lang,comlang,baseLang) {
	window.afterSelectCompany=function(data){
		if(!data){
			return;
		}
		$("[name='fdCurrencyId']").val(data[0]['fdBudgetCurrency.id']);
		$("[name='fdCurrencyName']").val(data[0]['fdBudgetCurrency.name']);
		var fdCompanyId=data[0].fdId;  //当前选择公司ID
		var fdOldCompanyId=$("[name='fdOldCompanyId']").val(); //选择前公司Id
		if(fdCompanyId!=fdOldCompanyId){
			//替换对应公司Id
			$("[name='fdOldCompanyId']").val(fdCompanyId);
			//清除明细
			$("#TABLE_DocList_fdDetailList_Form tr:not(:first)").remove();
			DocList_TableInfo["TABLE_DocList_fdDetailList_Form"].lastIndex=1;
		}
	};
	window.selectBudgetItem=function(name){
		dialogSelect(false,'eop_basedata_budget_item_com_fdBudgetItem','fdDetailList_Form[*].'+name+'Id','fdDetailList_Form[*].'+name+'Name',validateSame,{'selectType':'adjust','docTemplateName':$("[name='docTemplateName']").val()});	
		}
	window.afterSelectBudgetScheme=function(data){
		window.open(env.fn.formatUrl("/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=add&i.docTemplate="+$("input[name='docTemplateId']").val()+"&i.fdBudgetScheme="+data[0]["fdId"]),"_self");
	}
	//填写完金额触发
	window.checkSameDimension=function(val,obj){
		if($("input[name='fdSchemeType']").val()=='2'){ //预算追加不校验
			return true;
		}
		return checkSame(val,obj);
	};
	//选择完对象、期间触发
	window.validateSame=function(data){
		debugger
		var field=data.field;
		if(data[0]){
			var parentName=data[0]['fdParent.name'];
			if(field.indexOf("fdBorrowBudgetItemId")>=0){
				//借入
				var newField= field.replace("fdBorrowBudgetItemId","fdBorrowParentName");
				$("input[name='"+newField+"']").val(parentName)
			}else if(field.indexOf("fdLendBudgetItemId")>=0){
				//借出
				var newField= field.replace("fdLendBudgetItemId","fdLendParentName");
				$("input[name='"+newField+"']").val(parentName)
			}
		}
		if($("input[name='fdSchemeType']").val()=='2'){ //预算追加不校验
			return true;
		}
		if(!data){
			return;
		}
		var field=data.field;
		if(!checkSame(data[0].fdId,$("[name='"+field+"']")[0])){
			dialog.alert(lang['message.fsscBudgetAdjust.fdDimension.error']);
		}
	};
	window.checkSame=function(value,object){
		var rtnFlag=false;
		//获取校验对象在表格中的行序号
		var index = object.name.substring(object.name.indexOf("[")+1,object.name.indexOf("]"));
		var borrowObjList=$("[name*='fdDetailList_Form["+index+"].fdBorrow']");
		var lendObjList=$("[name*='fdDetailList_Form["+index+"].fdLend']");
		var borrowParams = {};
		var lendParams = {};
		//借出方和借入方是成对出现的，只做一次循环
		for(var i=0;i<borrowObjList.length;i++){
			var borrowPro=borrowObjList[i].name.substring(borrowObjList[i].name.indexOf(".")+1,borrowObjList[i].name.length);
			var value=borrowObjList[i].value;
			borrowPro=borrowPro.replace("Borrow",""); 
			borrowParams[borrowPro]=value;
			if(!value){
				rtnFlag=true;//说明数据没填写完全，不继续校验
				break;
			}
			var lendPro=lendObjList[i].name.substring(lendObjList[i].name.indexOf(".")+1,lendObjList[i].name.length);
			var value=lendObjList[i].value;
			lendPro=lendPro.replace("Lend",""); 
			lendParams[lendPro]=value;
			if(!value){
				rtnFlag=true;//说明数据没填写完全，不继续校验
				break;
			}
		}
		for(var key in borrowParams){
	　　　　if(lendParams[key]!=borrowParams[key]){//同样的维度，值不一致，说明不同，不可能一致，不需要校验
				rtnFlag=true;
				break;
	　　　　}
	　　}
		return rtnFlag;
	};
	//增加一行调整明细
	window.FSSC_AddAdjustDetail = function(){
		DocList_AddRow('TABLE_DocList_fdDetailList_Form');
		var len=$("#TABLE_DocList_fdDetailList_Form >tbody >tr").find("input[name$='fdMoney']").length;
		if(len>0){
			linkSelect(len-1);  //增加明细，给新增的明细的期间选择拼接
			//新增的默认当前年、季、月期间
			var currentDate=new Date();
			var year=currentDate.getFullYear(),month=currentDate.getMonth(),quarter=parseInt(month/3);
			var currentPeriod="";
			if(formInitData['fdSchemePeriod']=="5"){//年度只拼接前后两年
				currentPeriod="5"+year+"0000";
			}else if(formInitData['fdSchemePeriod']=="3"){
				currentPeriod="3"+year+("0"+quarter)+"00";
			}else if(formInitData['fdSchemePeriod']=="1"){
				currentPeriod="1"+year+(month>9?month:("0"+month))+"00";
			}
			$("select[name='fdDetailList_Form["+(len-1)+"].fdBorrowPeriod']").val(currentPeriod);
			$("select[name='fdDetailList_Form["+(len-1)+"].fdLendPeriod']").val(currentPeriod);
		}
	}
	//动态拼接期间选择框
	window.linkSelect=function(index){
		if(formInitData["adjustType"]=="2"){//追加
			$("select[name='fdDetailList_Form["+index+"].fdBorrowPeriod']").html(getOptions());
		}
		if(formInitData["adjustType"]=="1"){//调整
			$("select[name='fdDetailList_Form["+index+"].fdBorrowPeriod']").html(getOptions());
			$("select[name='fdDetailList_Form["+index+"].fdLendPeriod']").html(getOptions());
		}	
	}
	
	//根据期间不同拼接不同的选择
	window.getOptions=function(){
		var options="";
		var year=(new Date()).getFullYear(),preYear=year-1,nextYear=year+1;
		if(formInitData['fdSchemePeriod']=="5"){//年度只拼接前后两年
			options='<option value="5'+preYear+'0000">'+preYear+'年</option><option value="5'+year+'0000">'+year+'年</option>';
		}else if(formInitData['fdSchemePeriod']=="3"){
			options='<option value="3'+preYear+'0300">'+preYear+'年第4季度</option><option value="3'+year+'0000">'+year+'年第1季度</option><option value="3'+year+'0100">'+year+'年第2季度</option>'
				+'<option value="3'+year+'0200">'+year+'年第3季度</option><option value="3'+year+'0300">'+year+'年第4季度</option><option value="3'+nextYear+'0000">'+nextYear+'年第1季度</option>';
		}else if(formInitData['fdSchemePeriod']=="1"){
			options='<option value="1'+preYear+'1100">'+preYear+'年12月</option><option value="1'+year+'0000">'+year+'年1月</option>'
				+'<option value="1'+year+'0100">'+year+'年2月</option><option value="1'+year+'0200">'+year+'年3月</option>'
				+'<option value="1'+year+'0300">'+year+'年4月</option><option value="1'+year+'0400">'+year+'年5月</option>'
				+'<option value="1'+year+'0500">'+year+'年6月</option><option value="1'+year+'0600">'+year+'年7月</option>'
				+'<option value="1'+year+'0700">'+year+'年8月</option><option value="1'+year+'0800">'+year+'年9月</option>'
				+'<option value="1'+year+'0900">'+year+'年10月</option><option value="1'+year+'1000">'+year+'年11月</option>'
				+'<option value="1'+year+'1100">'+year+'年12月</option><option value="1'+nextYear+'0000">'+nextYear+'年1月</option>';
		}
		return options;
	}
	//提交前校验明细不能为空
	Com_Parameter.event["submit"].push(function(){ 
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		if($("#TABLE_DocList_fdDetailList_Form >tbody >tr").find("input[name$='fdMoney']").length==0){
			dialog.alert(lang['message.budget.nodetail.tips']);
			return false;
		}
		return true;
	 });
	/*******************************************
	 * 校验预算币种必需有值
	 *******************************************/
	Com_Parameter.event["submit"].push(function(){ // 通过submit队列来添加校验函数，这样校验失败，会终止表单提交。
		var submitFlag=true;
		if(!$("input[name='fdCurrencyId']").val()){
			dialog.alert(baseLang["message.common.budget.currency.notSet"]);
			submitFlag=false;
		}
		return submitFlag;
	});
});

Com_AddEventListener(window,'load',function(){
	if(Com_GetUrlParameter(window.location.href,"method")=='add'){
		FSSC_AddAdjustDetail();
	}else if(Com_GetUrlParameter(window.location.href,"method")=='edit'){
		var len=$("#TABLE_DocList_fdDetailList_Form >tbody >tr").find("input[name$='fdMoney']").length;
		if(len>0){
			for(var n=0;n<len;n++){
				linkSelect(n);
				//期间赋值
				$("[name='fdDetailList_Form["+n+"].fdBorrowPeriod']").val($("[name='fdDetailList_Form["+n+"].fdBorrowPeriod']").data('value'));
				$("[name='fdDetailList_Form["+n+"].fdLendPeriod']").val($("[name='fdDetailList_Form["+n+"].fdLendPeriod']").data('value'));
			}
		}
	}
})

LUI.ready(function(){	
	seajs.use('lui/jquery',function($){
		var method=Com_GetUrlParameter(window.location.href,"method");
		var len=$("#TABLE_DocList_fdDetailList_Form >tbody >tr").find("input[name$='fdMoney']").length;
		if(method=="edit"){
			if(len>0){
				for(var i=0;i<len;i++){
					//借入部门编辑初始化显示
					var id=$("[name='fdDetailList_Form["+i+"].fdBorrowDeptId']").val();
					var name=$("[name='fdDetailList_Form["+i+"].fdBorrowDeptName']").val();
					if(id&&name){
						emptyNewAddress("fdDetailList_Form["+i+"].fdBorrowDeptName",null,null,false);
						$("[name='fdDetailList_Form["+i+"].fdBorrowDeptId']").val(id);
						$("[name='fdDetailList_Form["+i+"].fdBorrowDeptName']").val(name);
						var addressInput = $("[xform-name='mf_fdDetailList_Form["+i+"].fdBorrowDeptName']")[0];
					    var addressValues = new Array();
					    addressValues.push({id:id,name:name});
						newAddressAdd(addressInput,addressValues);
					}
					//借入员工编辑初始化显示
					id=$("[name='fdDetailList_Form["+i+"].fdBorrowPersonId']").val();
					name=$("[name='fdDetailList_Form["+i+"].fdBorrowPersonName']").val();
					if(id&&name){
						emptyNewAddress("fdDetailList_Form["+i+"].fdBorrowPersonName",null,null,false);
						$("[name='fdDetailList_Form["+i+"].fdBorrowPersonId']").val(id);
						$("[name='fdDetailList_Form["+i+"].fdBorrowPersonName']").val(name);
						var addressInput = $("[xform-name='mf_fdDetailList_Form["+i+"].fdBorrowPersonName']")[0];
					    var addressValues = new Array();
					    addressValues.push({id:id,name:name});
						newAddressAdd(addressInput,addressValues);
					}
					//借出部门编辑初始化显示
					id=$("[name='fdDetailList_Form["+i+"].fdLendDeptId']").val();
					name=$("[name='fdDetailList_Form["+i+"].fdLendDeptName']").val();
					if(id&&name){
						emptyNewAddress("fdDetailList_Form["+i+"].fdLendDeptName",null,null,false);
						$("[name='fdDetailList_Form["+i+"].fdLendDeptId']").val(id);
						$("[name='fdDetailList_Form["+i+"].fdLendDeptName']").val(name);
						var addressInput = $("[xform-name='mf_fdDetailList_Form["+i+"].fdLendDeptName']")[0];
						var addressValues = new Array();
						addressValues.push({id:id,name:name});
						newAddressAdd(addressInput,addressValues);
					}
					//借出员工编辑初始化显示
					id=$("[name='fdDetailList_Form["+i+"].fdLendPersonId']").val();
					name=$("[name='fdDetailList_Form["+i+"].fdLendPersonName']").val();
					if(id&&name){
						emptyNewAddress("fdDetailList_Form["+i+"].fdLendPersonName",null,null,false);
						$("[name='fdDetailList_Form["+i+"].fdLendPersonId']").val(id);
						$("[name='fdDetailList_Form["+i+"].fdLendPersonName']").val(name);
						var addressInput = $("[xform-name='mf_fdDetailList_Form["+i+"].fdLendPersonName']")[0];
						var addressValues = new Array();
						addressValues.push({id:id,name:name});
						newAddressAdd(addressInput,addressValues);
					}
				}
			}
		}
	});
});
