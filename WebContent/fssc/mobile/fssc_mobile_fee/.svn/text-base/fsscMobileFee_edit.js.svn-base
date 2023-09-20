var dayFlag=true;  //全局变量
var apply_money_params,total_money_target,detail_div_body,current_city_list;
$(document).ready(function(){
 		$('.txtstrong').remove();
 		$('.inputsgl').removeClass();
 		var dateObj=$(".dateClass").find("input");
 		for(var i=0;i<dateObj.length;i++){
 			if($($(".dateClass").find("input").get(i)).data("show")!="readOnly"){
 				var id=$(".dateClass").find("input").get(i).getAttribute("id");
 	 			var name=$(".dateClass").find("input").get(i).getAttribute("name");
 	 			selectTime(id,name);
 			}
 		}
 		
 		var current_city_list=$("input[inittype='currentCityId']");
 		//如果有默认城市的元素，启动自动定位设置值
 		var method = Com_GetUrlParameter(window.location.href,'method');
		if(method=='add'){
 		if(current_city_list&&current_city_list.length>0){
 			$("input[inittype='currentCityId']").val("");
 			$("input[inittype='currentCityName']").val("");
 	 		getDefaultPlaceInit(setPlaceInit);//获取定位地址	
 		}
		}else{
			getDefaultPlaceInit();//编辑时只获取定位，不设置默认值
		}
 		setMainValidate();
 		//绑定数字校验
 		$("[validator*='number']").change(function(){
 			var val=$(this).val();
 			if(!isNumber(val)){
 				var subject=$(this).attr('subject');
				jqtoast(fsscLang['errors.number'].replace('{0}',subject?subject:''));
				$(this).val('');
 			}
 		});
 		//绑定正整数校验
 		$("[validator*='digits']").change(function(){
 			var val=$(this).val();
 			if(!isDigits(val)){
 				var subject=$(this).attr('subject');
 				jqtoast(fsscLang['errors.integer'].replace('{0}',subject?subject:''));
 				$(this).val('');
 			}
 		});
 		//绑定货币校验
 		$("[validator*='currency-dollar']").change(function(){
 			var val=$(this).val();
 			if(!isCurrencyDollar(val)){
 				var subject=$(this).attr('subject');
 				jqtoast(fsscLang['errors.dollar'].replace('{0}',subject?subject:''));
 				$(this).val('');
 			}
 		});
 		//绑定预算、标准匹配
 		var budgetMatchList=formInitData['budgetMatchList'];
 		if(budgetMatchList){
 			budgetMatchList=budgetMatchList.substring(1,budgetMatchList.length-1).split(',');
			for(var i=1;i<budgetMatchList.length;i++){
				var fieldName=budgetMatchList[i].replace(/\s+/g,"");
				if(fieldName){
					$("input[name='extendDataFormInfo.value("+fieldName+")']").change(function(){
						matchBudget("main",null);
						matchStandard("main",null);
			 		});
					var nameObj=$("input[name='extendDataFormInfo.value("+fieldName+"_name)']");
					if(nameObj&&nameObj.length>0){
						var changeFunc=$(nameObj).attr('onchange');
						if(changeFunc){
							var funcparam=changeFunc.replace("changeValue('[",'').replace("]');","").replace("changeValue('",'').replace("');","");
							if(funcparam){
								funcparam+=",";
							}
							funcparam+='{"params":"main","func":"matchBudget"}';
							funcparam+=',{"params":"main","func":"matchStandard"}';
							$(nameObj).attr('onchange',"changeValue('["+funcparam+"]');");
						}else{
							$(nameObj).attr('onchange',"matchBudget('main',null);matchStandard('main',null);");
						}
					}
				}
			}
			var displayList=formInitData['displayList'];
			if(displayList){
				displayList=displayList.substring(1,displayList.length-1).split(',');
				if(displayList[2]){
					var otherName=displayList[2].replace(/\s+/g,"");
					$("input[name='extendDataFormInfo.value("+otherName+"_cost_rate)']").val(1.0);
					$("input[name='extendDataFormInfo.value("+otherName+"_budget_rate)']").val(1.0);
				}
			}
 		}
 	});
    function setPlaceInit(){
    	if(current_city_list){
    		for(var i=0;i<current_city_list.length;i++){
    			current_city_list[i].value=currentCityId;
    		}
    	}
    	var current_city_name_list=$("input[inittype='currentCityName']");
    	if(current_city_name_list){
    		for(var i=0;i<current_city_name_list.length;i++){
    			current_city_name_list[i].value=currentCity;
    		}
    	}
    }
   
    if (isdingding()) {
        dd.ready(function() {
            dd.ui.webViewBounce.disable();
        });
    }
    // 禁用body滚动
    function forbiddenScroll() {
    		document.body.style.overflow='hidden'
    }
    // 启用滚动
    function ableScroll() {
        document.body.style.overflow='auto'
    }
    // 弹出新建行程弹窗
    $('.ld-newApplicationForm-travelInfo-btn').click(function() {
        $('.ld-travel-detail-body').addClass('ld-travel-detail-body-show')
            // $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
        forbiddenScroll()
    })
    $('.ld-save-btn').click(function() {
            $('.ld-travel-detail-body').removeClass('ld-travel-detail-body-show')
                // $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
            ableScroll()
        })
        // 费用明细
    $('.ld-newApplicationForm-costInfo-btn').click(function() {
        $('.ld-entertain-main-body').addClass('ld-entertain-main-body-show')
            // $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
        forbiddenScroll()
    })
    $('.ld-entertain-detail-btn').click(function() {
            $('.ld-entertain-main-body').removeClass('ld-entertain-main-body-show')
                // $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
            ableScroll()
        })
        // 新增账户
    $('.ld-newApplicationForm-account-btn').click(function() {
        $('.ld-addAccount-body').addClass('ld-addAccount-body-show')
            // $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
        forbiddenScroll()
    })
    $('.ld-addAccount-btn').click(function() {
            $('.ld-addAccount-body').removeClass('ld-addAccount-body-show')
                // $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
            ableScroll()
        })
        // 新增行程
    $('.ld-newApplicationForm-trip-btn').click(function() {
        $('.ld-addTravel-body').addClass('ld-addTravel-body-show')
            // $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
        forbiddenScroll()
    })
    $('.ld-addTrip-btn').click(function() {
        $('.ld-addTravel-body').removeClass('ld-addTravel-body-show')
            // $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
        ableScroll()
    })


/*************************************************************************
 * 选择对象
*************************************************************************/
function selectObject(id,name,dataSource,baseOn){
	var docTemplateId=$("[name='fdTemplateId']").val();
	var fdFeildId=id.substring(id.indexOf("(")+1,id.indexOf(")"));
	dataSource=resolveUrl(dataSource,baseOn);
	var event = event ? event : window.event;
	var obj = event.srcElement ? event.srcElement : event.target;
	if(docTemplateId){
		$.ajax({
			url: formInitData["LUI_ContextPath"] + dataSource,
			type: 'post',
			async:false,
		}).error(function(data){
				console.log("获取信息失败"+data);
		}).success(function(data){
			 console.log("获取信息成功");
	      	 var rtn = JSON.parse(data);  //json数组
	      	 var objData=rtn["data"];
	      	 var nameObj=$("[name='"+name+"']");
	      	 var idObj=$("[name='"+id+"']");
	      	 //由于picker.js每次只是隐藏，下次重新创建导致无法通过id绑定控件，所以每次新建前清除上一次的对象
	      	 $(".picker").remove();
	      	 var curValue = idObj.val(),selectedIndex=0;
	      	 if(curValue!=null){
	      		 for(var i=0;i<objData.length;i++){
	      			 if(objData[i].value==curValue){
	      				 selectedIndex = i;
	      				 break;
	      			 }
	      		 }
	      	 }
	         var picker = new Picker({
	             data: [objData],
	             selectedIndex:[selectedIndex]
	           });
	          picker.on('picker.select', function (selectedVal, selectedIndex) {
	        	  
	           });
	          picker.on('picker.change', function (index, selectedIndex) {
	        	  
	           });
	          picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
	        	  if(objData[selectedIndex]){
		        	  nameObj.val(objData[selectedIndex].text);
		        	  idObj.val(objData[selectedIndex].value);
		        	  var change_event=nameObj.attr("onchange");
		        	  var params;
	        	  	  if(change_event){
	        	  		params=change_event.replace("changeValue('","").replace("');","");
	        	  	  }
	        	  	  changeValue(params);//选择完对象触发对应的联动函数
	        	  	  var fdMappFeild=$("[name='fdMappFeild']").val();
	        	  	  if(fdMappFeild){
	        	  		fdMappFeild=JSON.parse(fdMappFeild); 
	        	  		if(id=='extendDataFormInfo.value('+fdMappFeild.fdCompanyId+')'){//公司
	            	  		clearData();
	            	  	}
	        	  		if(id=='extendDataFormInfo.value('+fdMappFeild.fdProjectId+')'){//项目
	            	  		clearWbsData();
	            	  	}
	        	  	  }
	        	  }
	           });
	          picker.show();
	          //回车搜索
	          $("#search_input").keypress(function (e) {
	              if (e.which == 13) {
	              	var keyword=$("[name='pick_keyword']").val();
	              	if(keyword){
	              		$.ajax({
	              	           type: 'post',
	              	           url:formInitData["LUI_ContextPath"] + dataSource,
	              	           data: {"keyword":keyword},
	              	       }).success(function (data) {
	              	    	   console.log('获取信息成功');
	              	    	   var rtn = JSON.parse(data);
	              	    	   picker.refillColumn(0, rtn.data);
	              	    	   objData=rtn.data;
	              	       }).error(function (data) {
	              	    	   console.log('获取信息失败');
	              	       })
	              	}
	              }
	      	});
	          //获取到焦点
	          $("#search_input").focus(function(){
	      		$(".weui-icon-clear").attr("style","display:block;");
	      	}) 
		      //取消
	          $(".weui-icon-clear").click(function (e) {
	          	$.ajax({
	   	           type: 'post',
	   	           url:formInitData["LUI_ContextPath"] + dataSource,
	   	           data: {"keyword":''},
	   	       }).success(function (data) {
	   	    	   console.log('获取分类信息成功');
	   	    	   var rtn = JSON.parse(data);
	   	    	  picker.refillColumn(0, rtn.data);
	   	    	  objData=rtn.data;
	   	    	  $("[name='pick_keyword']").val('');
	   	       }).error(function (data) {
	   	    	   console.log('获取分类信息失败');
	   	       })
	      	});
		});
	}
}
/*************************************************************************
 * 获取页面对应的参数值
 *************************************************************************/
function resolveUrl(dataSource,baseOn){
	if(!dataSource){
		return dataSource;
	}
	if(!dataSource.startsWith("/")){
		dataSource="/"+dataSource;
	}
	baseOn=mapToJson(baseOn);
	var baseUrl=dataSource.split('&')[0];
	var params=dataSource.split('&');
	for(var i=1;i<params.length;i++){
		var param=params[i];
		if(param&&param.split('=').length==2){
			var param_name=param.split('=')[0];
			var key=param.split('=')[1];
			var baseOnId=baseOn[key];
			var value="";
			if(baseOnId){//带$$的公式定义器参数
				var value=$("[name='extendDataFormInfo.value("+baseOnId+".id)']").val();//组织架构
				if(!value){
					value=$("[name='extendDataFormInfo.value("+baseOnId+")']").val();//对象
				}
			}else{
				if(key.startsWith('!')){//!{fdTemplateId}
					key=key.substring(2,key.length-1);
					value=$("[name='"+key+"']").val();
				}else{//flag=fee
					value=key;
				}
			}
			if(value){
				baseUrl+="&"+param_name+"="+value;
			}
		}
	}
	return baseUrl;
}

  /**
  *map字符串转换为json对象
  */
  function mapToJson(mapStr) {
	  var jsonStr="{";
	  var mapStr=mapStr.substring(1,mapStr.length-1);
	  var maps=mapStr.split(",");
	  for(var i=0;i<maps.length;i++){
		  var map=maps[i].split("=");
		  if(map.length==2){
			  if(map[0].indexOf(';')>-1){//多个参数
				  var keys=map[0].split(';');
				  var vals=map[1].split(';');
				  for(var n=0;n<keys.length;n++){
					  jsonStr+='"'+keys[n]+'":"'+vals[n]+'",';
				  }
			  }else{
				  jsonStr+='"'+map[0]+'":"'+map[1]+'",';
			  }
		  }
	  }
	  if(jsonStr){
		  jsonStr=jsonStr.substring(0,jsonStr.length-1);
		  jsonStr+="}";
	  }
	  return JSON.parse(jsonStr);
  }
  
  /*************************************************************************
   * 选择时间
   *************************************************************************/
  function selectTime(id,name){
  	var myDate = new Date(); 
  	var year = myDate.getFullYear();
  	var month = myDate.getMonth()*1+1;
  	var day =myDate.getDate();
  	var change_event=$("[id='"+id+"']").attr("onchange");
  	var params;
  	if(change_event){
  		params=change_event.replace("changeValue('","").replace("');","");
  	}
  	if(params){
  		new Mdate(id, {
 		   acceptId: id,
 		   acceptName: name,
 		   beginYear: year-1,
 		   beginMonth: "1",
 		   beginDay: "1",
 		   endYear: "2100",
 		   endMonth: "12",
 		   endDay: "1",
 		   format: "-",
 		   action:changeValue,
 		   params:params
 		});	
  	}else{
  		new Mdate(id, {
 		   acceptId: id,
 		   acceptName: name,
 		   beginYear: year,
 		   beginMonth: month,
 		   beginDay: day,
 		   endYear: "2100",
 		   endMonth: "12",
 		   endDay: "1",
 		   format: "-"
 		});	
  	}
  }
  /*************************************************************************
   * 新增明细
   *************************************************************************/
  function addDetail(id,editFlag){
	  var detail_id='TABLE_DL_'+id;
	  DocList_AddRow(detail_id);
	  $(".ld-entertain-detail").find("input").each(function () {
		  var inittype=$(this).attr("inittype"); 
		  if(!inittype&&this.name.substring(0,1)!='_'){//无初始化值的清空
			  $(this).val('');
			  $("[name$='_cost_rate)'][class='detail']").val(1.0);
			  $("[name$='_budget_rate)'][class='detail']").val(1.0);  //设置明细弹框div汇率默认为1
		  }
		  if(inittype=='currentCityName'){
			  $(this).val(currentCity);
		  }
		  if(inittype=='currentCityId'){
			  $(this).val(currentCityId);
		  }
		  var validate=$(this).attr("validator");
		  if($(this).attr("type")=='text'&&validate&&validate.indexOf('required')>-1){
			  var html,parent=$(this).parent();
			  while(!html&&parent){
				  $parent=$(parent);
				  html=$parent.find("span").html();
				  if(!html){
					  parent=$parent.parent();
				  }
			  }
			  $parent.find("span").html(html+'<span style="margin-left:2px;color:#d02300;">*</span>');
		  }
      });
      $("div[id='div_"+id+"'][class='ld-entertain-main-body']").addClass('ld-entertain-main-body-show');
      $("div[id='div_"+id+"']").attr('style','z-index:100;');
      forbiddenScroll();
      $("[name='editFlag']").val(editFlag);
      $(".detail_btn").attr('style','position: fixed;bottom:0;margin-bottom:0.2rem;');
      window.addEventListener("resize", function() {
  		if(document.activeElement.tagName == "INPUT" || document.activeElement.tagName == "TEXTAREA") {
  			window.setTimeout(function() {
  				document.activeElement.scrollIntoViewIfNeeded();
  			}, 0);
  		}
  	})
  }
  
  /*************************************************************************
   * 保存明细
   *************************************************************************/
  function saveDetail(id){
  	  var editFlag=$("[name='editFlag']").val();
  	  var index;
	  if(editFlag=='0'){
		  index=$("#TABLE_DL_"+id+" > tbody > tr").length-1;  //基准行新建的索引
	  }else if(editFlag=='1'){
		  index=$("[name='detailIndex']").val();
	  }
	  if(!dayFlag){
		  jqalert({
	          title:'',
	          content:fsscLang['fssc-mobile:fssc.mobile.date.diff.tips'],
	          yestext:fsscLang['button.ok']
	      })
	      return;
	  }
	  var inputObjs=$("#div_"+id).find("input,textarea");
	  var msg=fsscLang['errors.required'];
	  var tbHtml="";
	  for(var i=0;i<inputObjs.length;i++){
		  var inputObj=inputObjs[i];
		  if(inputObj){
			  $inputObj=$(inputObj);
			  var validate=$inputObj.attr("validator");
			  //必填
			  if(validate&&validate.indexOf('required')>-1&&!$inputObj.val()){
				  var subject=$inputObj.attr('subject');
				  if(!subject){
					  subject="";
				  }
				  jqtoast(msg.replace("{0}",subject));
				  return false;
			  }
			  var field_name=inputObj.name;
			  var field_value=inputObj.value;
			  if(field_name){
				  var fieldIndex=field_name.indexOf(id)+id.length;
				  var hidden_name=field_name.substring(0,fieldIndex)+'.'+index+field_name.substring(fieldIndex,field_name.length);
				  $("[name='"+hidden_name+"']").val(field_value);
			  }
			  if($(inputObj).attr("subject")){
				  var checkValue=(field_value.indexOf(';')>-1)?field_value.split(';')[0]:field_value;
				  var domObj=$("input[name='_"+field_name+"'][value='"+checkValue+"']").parent().find('.checkbox_item_text');
				  if(domObj&&domObj.length>0){//单选，根据值获取对应的显示文本
					  if(field_value.indexOf(';')>-1){
						  var textValues='';
						  var field_value_arr=field_value.split(';');
						  for(var t=0;t<field_value_arr.length;t++){
							  var textValue= $("input[name='_"+field_name+"'][value='"+field_value_arr[t]+"']").parent().find('.checkbox_item_text').html();
							  if(textValue){
								  textValues+=textValue+';';
							  }
						  }
						  if(textValues){
							  field_value=textValues.substring(0,textValues.length-1); 
						  }
					  }else{
						  field_value= $("input[name='_"+field_name+"'][value='"+field_value+"']").parent().find('.checkbox_item_text').html();
					  }
				  }
				  tbHtml+="<div><span>"+$(inputObj).attr("subject")+"</span>:"+"<span style=\"margin-left:0.5rem;\">"+(field_value?field_value:"")+"</span></div>";
			  }
		  }
	  }
	  matchBudget(index,editFlag);
	  matchStandard(index,editFlag);
	  var fdMappFeild=$("[name='fdMappFeild']").val();
 	  if(fdMappFeild){
 		 fdMappFeild=JSON.parse(fdMappFeild); 
 	  }
 	  var tab_id=fdMappFeild['fdTableId'];  //映射的明细ID
 	  if(id==tab_id){//费控的明细表用费控的样式显示
 		 var displayList=formInitData["displayList"];
 		  if(displayList){
 			  displayList=displayList.substring(1,displayList.length-1).split(',');
 			  var arr=new Array(displayList.length); 
 			  for(var n=0;n<displayList.length;n++){
 				  var property_name=displayList[n].replace(/\s+/g,""); 
 				  var val=$("[name='extendDataFormInfo.value("+property_name+")']").val();
 				  var name=$("[name='extendDataFormInfo.value("+property_name+".name)']").val();
 				  if(!name){
 					  name=$("[name='extendDataFormInfo.value("+property_name+"_name)']").val();
 				  }
 				  if(name||val){
 					  arr[n]=name?name:val;  //对象的话显示名称
 				  }else{
 					  arr[n]="";  //对象的话显示名称
 				  }
 			  }
 		  }
 		  var createTime=$("[name='docCreateTime']").val();
 		  var liHtml="<ul><li class=\"ld-notSubmit-list-item\" onclick=\"editDetail('"+id+"','1');\"><div class=\"ld-newApplicationForm-travelInfo-top\">";
 		  liHtml+=" <div><img src=\""+formInitData["LUI_ContextPath"]+"/fssc/mobile/resource/images/taxi.png\" alt=\"\"><span class=\"feeTypeName\">"+(arr.length>0?arr[0]:"")+"</span>";
 		  liHtml+="<div class=\"ld-notSubmit-list-top\">";
 		  var fdMappFeild=$("[name='fdMappFeild']").val();
 	  	  if(fdMappFeild){
 	  		fdMappFeild=JSON.parse(fdMappFeild); 
 	  	  }
 		  var budgetStatusId=fdMappFeild["fdRuleId"];
 		  var budgetStatusName="extendDataFormInfo.value("+budgetStatusId+")";
 		  var budgetStatus=$("[name='"+budgetStatusName+"']").val();
 		  if(budgetStatus){
 			  liHtml+="<span class=\"ld-notSubmit-entryType_"+budgetStatus+"\">"+fsscLang['fssc-fee:py.budget.'+budgetStatus]+"</span>";
 		  }
 		  var standardStatusId=fdMappFeild["fdStandardId"];
 		  var standardStatusName="extendDataFormInfo.value("+standardStatusId+")";
 		  var stardarStatus=$("[name='"+standardStatusName+"']").val();
 		  if(stardarStatus){
 			  liHtml+="<span class=\"ld-notSubmit-entryType_"+stardarStatus+"\">"+fsscLang['fssc-fee:py.standard.'+stardarStatus]+"</span>";
 		  }
 		  liHtml+="</div>";
 		  liHtml+="</div><i onclick=\"deleteDetail('"+id+"');\"></i></div> <div class=\"ld-notSubmit-list-bottom\"><div class=\"ld-notSubmit-list-bottom-info\">";
 		  liHtml+=" <div><span>"+(createTime?createTime.substring(0,10):"")+"</span><span class=\"ld-verticalLine\"></span><span>"+(arr.length>3?arr[3]:$("[name='docCreatorName']").val())+"</span></div>";
 		  var currency_code=$("[name='currency_code']").val();
 		  if(!currency_code){
 			  var fdCompanyId=getFieldValue(fdMappFeild["fdCompanyId"]);
 			  var kms = new KMSSData();
 			  kms.AddBeanData("eopBasedataCurrencyService&authCurrent=true&type=localCurrencyInfo&fdCompanyId="+fdCompanyId);
 			  kms = kms.GetHashMapArray();
 			  if(kms&&kms.length>0){
 				  currency_code=kms[0].code?kms[0].code:'';
 			  }
 		  }
 		  liHtml+=" <span><span class=\"currency_code\">"+(currency_code?currency_code:'￥')+"</span>"+Number(arr.length>1&&arr[1]?arr[1]:"0.0").toFixed(2)+"</span></div><p></p></div></li></ul>";
 		 if(editFlag=='0'){
 			  $("[name='detailIndex_."+index+"."+id+"']").parent().append(liHtml);
 		  }else{
 			  $("[name='detailIndex_."+index+"."+id+"']").parent().find(".ld-notSubmit-list-item").remove();
 			  $("[name='detailIndex_."+index+"."+id+"']").parent().append(liHtml);
 		  }
 	  }else{//其他明细表
 		 var cssTable="<li class=\"ld-notSubmit-list-item\" onclick=\"editDetail('"+id+"','1');\"><div class=\"ld-newApplicationForm-travelInfo-top\"> " +
 		 		"<div><span class=\"feeTypeName\"></span><div class=\"ld-notSubmit-list-top\"></div></div><i onclick=\"deleteDetail('"+id+"');\"></i></div> " +
 		 		"<div class=\"ld-notSubmit-list-bottom\">"+tbHtml+"</div></li>";
 		 if(editFlag=='0'){
 			  $("[name='detailIndex_."+index+"."+id+"']").parent().append(cssTable);
 		  }else{
 			  $("[name='detailIndex_."+index+"."+id+"']").parent().find(".ld-notSubmit-list-item").remove();
 			  $("[name='detailIndex_."+index+"."+id+"']").parent().append(cssTable);
 		  }
 	  }
	  $('#div_'+id).attr('class','ld-entertain-main-body');
	//隐藏明细，将必填星号去除
	  $(".ld-entertain-detail").find("input,textarea").each(function () {
		  var validate=$(this).attr("validator");
		  if($(this).attr("type")=='text'&&validate&&validate.indexOf('required')>-1){
			  var html,parent=$(this).parent();
			  while(!html&&parent){
				  $parent=$(parent);
				  html=$parent.find("span").html();
				  if(!html){
					  parent=$parent.parent();
				  }
			  }
			  if(html){
				  $parent.find("span").html(html.replace('<span style="margin-left:2px;color:#d02300;">*</span>',''));
			  }
		  }
      });
	  $(".ld-entertain-detail").find("input,textarea").each(function () {
		  var inittype=$(this).attr("inittype"); 
		  if(!inittype){//无初始化值的清空
			  if(this.name.substring(0,1)!='_'){
				  $(this).val('');
				  $("[name$='_cost_rate']").val(1.0);
				  $("[name$='_budget_rate']").val(1.0);  //默认为1
			  }else{//单选及多选的组件，不能直接设置默认值
				  $(this).parent().parent().find("checkbox_item,checkbox_item_multi").removeClass("checked");
				  $(this).parent().parent().find("input[name^='_']").prop("checked",false);
			  }
		  }else{
			  if(this.name.substring(0,1)!='_'){
				  $(this).val(inittype);
			  }else{
				  if(inittype.indexOf(this.value)>-1){
					  $(this).prop("checked",true).parent().addClass("checked");
				  }else{
					  $(this).prop("checked",false).parent().removeClass("checked");
				  }
			  }
		  }
      });
	  $("[name='currency_code']").val('');
	  sumTotal(apply_money_params,total_money_target);
	  $("[name='detailIndex']").val('');  //保存清除index
	  $("[name='editFlag']").val('');  //清除标识位
      ableScroll();
      $(".detail_btn").attr('style','');
      $("div[id='div_"+id+"']").attr('style','');
      reCaculateMain();
      
  }
  
  
  function cancelDetail(id){
	  $('#div_'+id).attr('class','ld-entertain-main-body');
	  if($("[name='editFlag']").val()=='0'){//新建，点击取消需删除默认加载的一行明细
		  var index=$("#TABLE_DL_"+id+" tbody >tr").length-1;
		  FSSC_Fee_DeleteRow("TABLE_DL_"+id,index);
	  }
	  $("[name='detailIndex']").val('');  //清除index
	  $("[name='editFlag']").val('');  //清除标识位
	  //隐藏明细，将必填星号去除
	  $(".ld-entertain-detail").find("input,textarea").each(function () {
		  var validate=$(this).attr("validator");
		  if($(this).attr("type")=='text'&&validate&&validate.indexOf('required')>-1){
			  var html,parent=$(this).parent();
			  while(!html&&parent){
				  $parent=$(parent);
				  html=$parent.find("span").html();
				  if(!html){
					  parent=$parent.parent();
				  }
			  }
			  if(html){
				  $parent.find("span").html(html.replace('<span style="margin-left:2px;color:#d02300;">*</span>',''));
			  }
		  }
		  var inittype=$(this).attr("inittype"); 
		  if(this.name.substring(0,1)!='_'&&inittype!='currentCityName'&&inittype!='currentCityId'){
			  $(this).val(inittype);
		  }else{
			  if(inittype.indexOf(this.value)>-1){
				  $(this).prop("checked",true).parent().addClass("checked");
			  }else{
				  $(this).prop("checked",false).parent().removeClass("checked");
			  }
		  }
      });
	  $(".detail_btn").attr('style','');
	  ableScroll();
	  reCaculateMain();
  }
  
//删除明细
  function deleteDetail(id){
	  var event = event ? event : window.event;
	  var obj = event.srcElement ? event.srcElement : event.target;
	  //还可以将obj转换成jquery对象，方便选用其他元素
	  var $obj = $(obj);
	  var index;
	  while(!index){
		  var name=$obj.parent().find("[name^='detailIndex_.']").attr('name');
		  if(!name&&$obj){
			  $obj=$obj.parent();
		  }else{
			  index=name.substring(name.indexOf('.')+1,name.lastIndexOf('.'));  //内容行新建的索引
		  }
		  
	  }
	  var params,totalMoney=0.0;
	  var displayList=formInitData["displayList"];
	  if(displayList){
		  displayList=displayList.substring(1,displayList.length-1);
		  if(displayList){
			  displayList=displayList.split(',');
			  var arr=new Array(displayList.length); 
			  var property_name=displayList[1].replace(/\s+/g,""); //金额
			  var change_event=$("[name='extendDataFormInfo.value("+property_name+")']").attr('onchange');
		  	  if(change_event){
		  		params=change_event.replace("changeValue('","").replace("');","");
		  	  }
		  }
	  }
	  FSSC_Fee_DeleteRow("TABLE_DL_"+id,index);
	  if(params){
		  try{
		  changeValue(params);//重新计算申请总额
		  }catch(e){
			  reCaculateMain();
			  console.log(e);
		  }
	  } 
	  sumTotal(apply_money_params,total_money_target);
	  reCaculateMain();
  }
  
  /**
   * 匹配预算
   * @param v
   * @param e
   */
  function matchBudget(index,editFlag){
	  var fdMappFeild=$("[name='fdMappFeild']").val();
  	  if(fdMappFeild){
  		fdMappFeild=JSON.parse(fdMappFeild); 
  	  }
  	  var budgetStatusId=fdMappFeild["fdRuleId"];  //预算控件ID
  	  if(!budgetStatusId){
   		return ;
  	  }
	  var method=$("[name='method_GET']").val();
	  var param = {
	  		'fdCompanyId':getFieldValue(fdMappFeild["fdCompanyId"]),
	  		'fdProjectId':getFieldValue(fdMappFeild["fdProjectId"]),
	  		'fdWbsId':getFieldValue(fdMappFeild["fdWbsId"]), 
	  		'fdInnerOrderId':getFieldValue(fdMappFeild["fdInnerOrderId"]),
	  		'fdExpenseItemId':getFieldValue(fdMappFeild["fdExpenseItemId"]),
	  		'fdCostCenterId':getFieldValue(fdMappFeild["fdCostCenterId"]),
	  		'fdPersonId':getFieldValue(fdMappFeild["fdPersonId"]),
	  		'fdDeptId':getFieldValue(fdMappFeild["fdDeptId"]),
	  		'fdMoney':getFieldValue(fdMappFeild["fdMoneyId"]),
	  		'fdCurrencyId':getFieldValue(fdMappFeild["fdCurrencyId"]),
	  		'index':index
	  }
	  var budgetInfoId=budgetStatusId.substring(0,budgetStatusId.lastIndexOf('status'))+'info';
  	if(method=="view"&&getFieldValue(budgetInfoId,index)){
  		param[index]['fdBudgetInfo']=getFieldValue(budgetInfoId,index);
  	}
  	$.post(
  		formInitData["LUI_ContextPath"]+'/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=matchBudget',
  		{data:JSON.stringify(param)},
  		function(rtn){
  			rtn = JSON.parse(rtn);
  			var budgetStatusName="extendDataFormInfo.value("+budgetStatusId+")";
  			var budgetInfoName="extendDataFormInfo.value("+budgetInfoId+")";
  			//匹配失败
  			if(rtn.result=='failure'||rtn.budget.result=='0'){
  				jqalert({
                    title:'',
                    content:rtn.message?rtn.message:rtn.budget.errorMessage,
                    yestext:fsscLang['button.ok']
                })
  				$("[name='"+budgetStatusName+"']").val("0");
  				$("[name='"+budgetInfoName+"']").val('');
  				return;
  			}else if(!rtn.budget.data||rtn.budget.data.length==0){ //未匹配到预算
  				$("[name='"+budgetStatusName+"']").val("0");
  				$("[name='"+budgetInfoName+"']").val('');
  				if(isNaN(index)){//主表
  		  			$("[name*='"+budgetStatusName+"']").val(0);
  		  			$("[name*='"+budgetStatusName+"']").hide();
  		  			if($("#mainBudget")&&$("#mainBudget").length>0){
  		  				$("#mainBudget").html(fsscLang['fssc-fee:py.budget.0']);
  		  			}else{
  		  				$("[name*='"+budgetStatusName+"']").parent().append('<span id="mainBudget" style="color:#000;">'+fsscLang['fssc-fee:py.budget.0']+'</span>');
  		  			}
	  			}else{
	  				$("[name*='"+budgetStatusName+"']").val(fdBudgetStatus);
	  			}
  				return;
  			}else{//匹配到预算
  				var budgetInfo = {},fdBudgetStatus = '1';
  		  		var fdMoney = getFieldValue(fdMappFeild["fdMoneyId"]);
  		  		if(!fdMoney){
  		  			return true;
  		  		}
  		  		var info=rtn.budget.data;
  		  		var fdBudgetRate = $("[name*='."+index+".'][name*='_budget_rate']").val(); //明细获取
  		  		$("[name='"+budgetInfoName+"']").val(JSON.stringify(info).replace(/\"/g,"'"));
  		  		//如果在主表中
  		  		if(isNaN(index)){
  		  			fdBudgetRate = $("[name*='_budget_rate']").val(); //主表获取
  		  			var fdBudgetInfo = $("[name='"+budgetInfoName+"']").val()||'[]';
  		  			fdBudgetInfo  = JSON.parse(fdBudgetInfo.replace(/\'/g,'"'));
  		  		}else{//如果在明细中，迭代所有明细的预算信息，处理多条明细匹配到同一条预算的情况
  		  			$("#TABLE_DL_"+fdMappFeild['fdTableId']).find("[name*='."+budgetInfoId.split('.')[1]+"']").each(function(){
  		  				var budget = JSON.parse(this.value.replace(/\'/g,'"')||'[]');
  		  				var k = this.name.replace(/\S+\.(\d+)\.\S+/g,'$1');
  		  				if(k>index){
  		  					return false;  //跳转循环，值累计当前行及之前的行
  		  				}
  		  				var money = $("[name*='."+k+"."+fdMappFeild["fdMoneyId"].replace(/.+\.(.+)$/g,'$1')+"']").val()*1;
  		  				var rate = $("[name*='."+k+"."+fdMappFeild["fdCurrencyId"].replace(/.+\.(.+)$/g,'$1')+"_budget_rate']").val()*1||1;
  		  				for(var i=0;i<budget.length;i++){
  		  					if(!budgetInfo[budget[i].fdBudgetId]){
  		  						budgetInfo[budget[i].fdBudgetId] = numMulti(money,rate);
  		  					}else{
  		  						budgetInfo[budget[i].fdBudgetId] = numAdd(budgetInfo[budget[i].fdBudgetId],numMulti(money,rate));
  		  					}
  		  				}
  		  			});
  		  			$("[name*='."+index+".'][name*='_budget_info']").val(JSON.stringify(info).replace(/\"/g,"'"));
  		  		}
  		  		var showBudget = null,overBudget = [];
  		  		for(var i=0;i<info.length;i++){
  		  			//获取可用金额最少的预算用于展示
  		  			if(!showBudget||showBudget.fdCanUseAmount>info[i].fdCanUseAmount){
  		  				showBudget = info[i];
  		  			}
  		  			if(budgetInfo[info[i].fdBudgetId]!=null){//当新建明细时，若是前面有该预算的
	  		  			var totalMoney=0.0;
	  		  			if(editFlag=='0'){
	  		  				//新建，加上本次明细金额，因为还未给明细隐藏域赋值
	  		  				totalMoney=addPoint(budgetInfo[info[i].fdBudgetId],multiPoint(fdMoney,fdBudgetRate));
	  		  			}else{
	  		  				//编辑，需加上本次修改的金额和原来金额的差额
	  		  				var old_money=0.0;
		  		  			if(!isNaN(index)){
		  		  				old_money=$("[name*='."+index+"."+fdMappFeild["fdMoneyId"].replace(/.+\.(.+)$/g,'$1')+"']").val()*1;
		  		  			}
	  		  				var new_money=multiPoint(fdMoney,fdBudgetRate);
	  		  				totalMoney=addPoint(budgetInfo[info[i].fdBudgetId],numSub(new_money,old_money));
	  		  			}
  		  				//超出预算
  		  				if(totalMoney>info[i].fdCanUseAmount){
  		  					fdBudgetStatus = '2';
  		  				}
  		  			}else{
  		  				//当新建明细时，若是前面无该预算的，直接判断
  		  				//超出预算
  		  				if(multiPoint(fdMoney,fdBudgetRate)>info[i].fdCanUseAmount){
  		  					fdBudgetStatus = '2';
  		  				}
  		  			}
  		  		}
  		  		if(isNaN(index)){//主表
  		  			$("[name*='"+budgetStatusName+"']").val(fdBudgetStatus);
  		  			$("[name*='"+budgetStatusName+"']").hide();
  		  			if($("#mainBudget")&&$("#mainBudget").length>0){
  		  				$("#mainBudget").html(fsscLang['fssc-fee:py.budget.'+fdBudgetStatus]);
  		  			}else{
  		  				$("[name*='"+budgetStatusName+"']").parent().append('<span id="mainBudget" style="color:#000;">'+fsscLang['fssc-fee:py.budget.'+fdBudgetStatus]+'</span>');
  		  			}
	  			}else{
	  				$("[name*='"+budgetStatusName+"']").val(fdBudgetStatus);
	  				$("[name*='."+index+".'][name*='_budget_status']").val(fdBudgetStatus);
	  			}
  			}
  		}
  	);
  }
  
//匹配标准
  function matchStandard(index,editFlag){
	 var fdMappFeild=$("[name='fdMappFeild']").val();
  	if(fdMappFeild){
  		 fdMappFeild=JSON.parse(fdMappFeild); 
  	}
  	var standardStatusId=fdMappFeild["fdStandardId"];
  	if(!standardStatusId){
 		return ;
 	}
	var method=$("[name='method_GET']").val();
  	var params=[];
  	var fdAreaId=getFieldValue(fdMappFeild["fdAreaId"]);
  	if(fdAreaId&&fdAreaId.indexOf("__")>-1){//说明获取的值 ：北京__1675e001c2880471b2b891d4b8a84ca7
  		fdAreaId=fdAreaId.split('__')[1];
  	}
  	var fdBerthId=getFieldValue(fdMappFeild["fdVehicleId"]);  //舱位
  	var fdVehicleId='';
  	if(fdBerthId){
  		var kms = new KMSSData();
		  kms.AddBeanData("eopBasedataBerthService&authCurrent=true&fdBerthId="+fdBerthId);
		  kms = kms.GetHashMapArray();
		  if(kms&&kms.length>0){
			  if(kms[0].fdVehicleId){
				  fdVehicleId=kms[0].fdVehicleId;  //交通工具
			  }
		  }
  	}
  	var param = {
  		'fdCompanyId':getFieldValue(fdMappFeild["fdCompanyId"]),
  		'fdExpenseItemId':getFieldValue(fdMappFeild["fdExpenseItemId"]),
  		'fdSpecialId':getFieldValue(fdMappFeild["fdSpecialId"]),
  		'fdPersonId':getFieldValue(fdMappFeild["fdPersonId"]),
  		'fdMoney':getFieldValue(fdMappFeild["fdMoneyId"]),
  		'fdCurrencyId':getFieldValue(fdMappFeild["fdCurrencyId"]),
  		'fdTravelDays':getFieldValue(fdMappFeild["fdTravelDaysId"]),
  		'fdVehicleId':fdVehicleId,
  		'fdBerthId':fdBerthId,
  		'fdAreaId':fdAreaId,
  		'fdPersonNumber':getFieldValue(fdMappFeild["fdPersonNumId"])||1,
  		'fdPersonId':getFieldValue(fdMappFeild["fdPersonId"]),
  		'index':index
  	};
  	if(!param.fdCompanyId||!param.fdExpenseItemId){
  		return;
  	}
  	params.push(param);
  	$.ajax({
  		url:formInitData["LUI_ContextPath"] + '/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=getStandardData',
  		data:{params:JSON.stringify(params)},
  		async:false,
  		success:function(rtn){
  			rtn = JSON.parse(rtn);
  			if(rtn.result=='failure'){
  				jqalert({
  	              title:'',
  	              content:rtn.message,
  	              yestext:fsscLang['button.ok']
  	          })
  				return;
  			}
  			var standardStatusName="extendDataFormInfo.value("+standardStatusId+")";
  			var statusInfoId=standardStatusName.substring(0,standardStatusName.lastIndexOf('status'))+'info';
  			//如果在主表中
	  		if(isNaN(index)){
	  			$("[name*='"+standardStatusName+"']").val(rtn.data[0].status);
	  			$("[name*='"+statusInfoId+"']").val(rtn.data[0].info);
	  			$("[name*='"+standardStatusName+"']").hide();
	  			if($("#mainStandard")&&$("#mainStandard").length>0){
	  				$("#mainStandard").html(fsscLang['fssc-fee:py.standard.'+rtn.data[0].status]);
	  			}else{
	  				$("[name*='"+standardStatusName+"']").parent().append('<span id="mainStandard" style="color:#000;">'+fsscLang['fssc-fee:py.standard.'+(rtn.data[0].status)]+'</span>');
	  			}
	  		}else{
	  			var standardStatusStatus_index="."+index+standardStatusId.substring(standardStatusId.indexOf("."),standardStatusId.length);
	  			var standardStatusInfo_index=standardStatusStatus_index.replace("status","info");
	  			for(var k=0;k<rtn.data.length;k++){
	  				if(rtn.data[k].status){
	  					$("[name='"+standardStatusName+"']").val(rtn.data[k].status);	
	  					$("[name*='"+standardStatusStatus_index+"']").val(rtn.data[k].status);	
	  				}
	  				if(rtn.data[k].subject){
	  					$("[name='"+statusInfoId+"']").val(rtn.data[k].subject);
	  					$("[name*='"+standardStatusInfo_index+"']").val(rtn.data[k].subject);	
	  				}
	  			}
	  		}
  		}
  	});
  }
  
  function getFieldValue(feild_name,index){
	  var val="";
	  if(!feild_name){
		  return val;
	  }
	  if(feild_name.indexOf('.')>-1){//明细
		  if(index||index==0){//index有值，获取明细
			  var detail_id=feild_name.split('.')[0];
			  var filed_id=feild_name.split('.')[1];
			  val=$("[name='extendDataFormInfo.value("+detail_id+"."+index+"."+filed_id+")']").val();
			  if(!val){
				  val=$("[name='extendDataFormInfo.value("+detail_id+"."+index+"."+filed_id+".id)']").val();
			  }
		  }else{
			  //index没值，是保存明细前校验
			  val=$("[name='extendDataFormInfo.value("+feild_name+")']").val();
			  if(!val){
				  val=$("[name='extendDataFormInfo.value("+feild_name+".id)']").val();
			  }
		  }
	  }else{//主表
		  val=$("[name='extendDataFormInfo.value("+feild_name+")']").val();
		  if(!val){
			  val=$("[name='extendDataFormInfo.value("+feild_name+".id)']").val();
		  }
	  }
	  return val;
  }
  
  
  /*************************************************************************
   * 编辑明细
   *************************************************************************/
  function editDetail(id,editFlag){
	  var event = event ? event : window.event;
	  var obj = event.srcElement ? event.srcElement : event.target;
	  if(obj.tagName=='I'||obj.tagName=='i'){//由删除图标触发的，不做处理
		  return ;
	  }
	  //还可以将obj转换成jquery对象，方便选用其他元素
	  var $obj = $(obj);
	  var index;
	  while(!index){
		  var name=$obj.parent().find("[name^='detailIndex_.']").attr('name');
		  if(!name&&$obj){
			  $obj=$obj.parent();
		  }else{
			  index=name.substring(name.indexOf('.')+1,name.lastIndexOf('.'));  //内容行新建的索引
		  }
		  
	  }
	  var currency_code=$("#TABLE_DL_"+id).find("tr").eq(index).find(".currency_code").html();
	  $("[name='currency_code']").val(currency_code);  //币种符号
	  $("div[id='div_"+id+"'][class='ld-entertain-main-body']").addClass('ld-entertain-main-body-show');
	  $(".detail_btn").attr('style','position: fixed;bottom:0;margin-bottom:0.2rem;');
	  $("div[id='div_"+id+"']").attr('style','z-index:100;');
      forbiddenScroll();
      window.addEventListener("resize", function() {
  		if(document.activeElement.tagName == "INPUT" || document.activeElement.tagName == "TEXTAREA") {
  			window.setTimeout(function() {
  				document.activeElement.scrollIntoViewIfNeeded();
  			}, 0);
  		}
  	  })
	  var inputObjs=$("[name^='extendDataFormInfo.value("+id+"."+index+"']");
	  for(var i=0;i<inputObjs.length;i++){
		  var inputObj=inputObjs[i];
		  if(inputObj){
			  var field_name=inputObj.name;
			  var field_value=inputObj.value;
			  if(field_name&&!field_name.endsWith("fdId)")){
				  var field_name=field_name.substring(25,field_name.length-1);
				  field_name=field_name.replace("."+index,"");
				  $("[name='extendDataFormInfo.value("+field_name+")']").val(field_value);
				  var options = $("[name='_extendDataFormInfo.value("+field_name+")']");
				  if(options.length>0){//处理单选、多选控件
					  options.each(function(){
						  if(field_value.indexOf(this.value)>-1){
							  $(this).prop('checked',true).parent().addClass("checked");
						  }else{
							  $(this).prop('checked',false).parent().removeClass("checked");
						  }
					  })
				  }
				  var validate= $("[name='extendDataFormInfo.value("+field_name+")']").attr("validator");
				  if( $("[name='extendDataFormInfo.value("+field_name+")']").attr("type")=='text'&&validate&&validate.indexOf('required')>-1){
					  var html='',parent= $("[name='extendDataFormInfo.value("+field_name+")']").parent();
					  while(!html&&parent){
						  $parent=$(parent);
						  html=$parent.find("span").html();
						  if(!html){
							  parent=$parent.parent();
						  }
					  }
					  $parent.find("span").html(html+'<span style="margin-left:2px;color:#d02300;">*</span>');
				  }
			  }
		  }
	  }
      $("[name='editFlag']").val(editFlag);
      $("[name='detailIndex']").val(index);
  }
  
  /*************************************************************************
   * 保存表单
   *************************************************************************/
  function submitForm(form,status,method){
	  if(!$("[name='docSubject']").val()&&$("[name='docSubject']").attr('readonly')!='readonly'){
		  jqtoast(fsscLang['errors.required'].replace('{0}',fsscLang['fssc-fee:fsscFeeMain.docSubject']));
		  return false;
	  }
	  var subFlag=true;
	  if('10'!=status){
		  subFlag= validateMainFeild(); 
	  }
	  if(!subFlag){
		 return false; 
	  }
	  //校验明细至少要有一行
	  var detailTbs=$("[id^='TABLE_DL_']");
	  for(var i=0;i<detailTbs.length;i++){
		  var detailTbs_id=detailTbs.get(i).id;
		  var len=$("#"+detailTbs_id).find("tr").length;
		  if(len==0){
			  jqalert({
	              title:'',
	              content:fsscLang['fssc-mobile:fssc.mobile.detail.not.null.tips'],
	              yestext:fsscLang['button.ok']
	          })
			  return false;
		  }
	  }
	  if(subFlag){
		  detail_div_body=$(".ld-entertain-main-body").html();
		  //保存表单前清除明细表展现的div，不然div的input会影响整个表单的提交
		  $(".ld-entertain-main-body").html('');
		  $("[name=docStatus]").val(status);
		  var action = form.action;
		  form.action = Com_SetUrlParameter(action,"status",status);
		  Com_Submit(form, method); 
	  }else{
		  return subFlag;
	  }
	}

  /*************************************************************************
   * 保存附件
   *************************************************************************/
  function uploadFile(files,id){
	  if(files.length==0){
		  return ;
	  }
	  $("#ld-main-upload").attr("style","display: block;");
	  var fdModelId = $("[name='fdId']").val();
	  for(var i=0;i<files.length;i++){
		  var xhr = new XMLHttpRequest();
		  xhr.open("POST", formInitData["LUI_ContextPath"] +"/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=saveAtt&fdModelId="+fdModelId+"&fdModelName=com.landray.kmss.fssc.fee.model.FsscFeeMain&filename="+files[i].name+"&key="+id+"&size="+files[i].size,false);
		  xhr.overrideMimeType("application/octet-stream");
		    // 监听变化
			xhr.onreadystatechange = function(e){
			     if(xhr.readyState===4){
			            if(xhr.status===200){
			                //上传成功
			            	if(xhr.response){
			            		var rtn = JSON.parse(xhr.response);
			            		var attId=rtn.fdId;
			            		var attName=rtn.fdName;
			            		var attHtml="";
			            		attHtml+="<li><div data-attid=\""+attId+"\" class=\"ld-remember-attact-info\" onclick=\"showAtt('"+attId+"','"+attName+"');\"><img src="+getSrcByName(attName)+" alt=\"\">";
			          		  	attHtml+="<span>"+attName+"</span></div><span onclick=\"deleteAtt('"+attId+"','"+attName+"');\"></span></li>";
			          		  $("#att_"+id).append(attHtml);
			            	}
			            	$("#ld-main-upload").attr("style","display: none;");
			            }else{
			         	   alert("上传附件失败！");
			            }
			        }
		    }
			 //直接发送二进制数据
		    if(xhr.sendAsBinary){
		        xhr.sendAsBinary(files[i]);
		    } else {
		        xhr.send(files[i]);
		    }
	  }
	  $("#"+id+"_id").val('');
  }
  
  
  /****************************联动函数  start***************************************/
  /**
   * 统一触发校验函数
   */
  function changeValue(json){
	  if(json){
		  var jsonObj=JSON.parse(json);
		  for(var n=0;n<jsonObj.length;n++){
			  var obj=jsonObj[n];
			  var func=obj.func;
			  if(func){
				  eval(func+"('"+obj.params+"','"+obj.target+"')");
			  }
		  }
	  }
  }
  
  /**
   * 调用change事件并触发caculate计算相关控件的值
   */
  function changeValueAndCaculate(json,currentDom){
		//console.log("===changeValueAndCaculate事件");
		  if(json){
			  var jsonObj=JSON.parse(json);
			  for(var n=0;n<jsonObj.length;n++){
				  var obj=jsonObj[n];
				  var func=obj.func;
				  if(func){
					  eval(func+"('"+obj.params+"','"+obj.target+"')");
				  }
			  }
		  }
		  caculate(currentDom);
	  }
  
  /**
   * 计算天数
   * params 开始时间、结束时间字段名
   * target 天数显示字段
   */
  function CalculateDay(params,target){
	  if(params.split(';')<2){
		  return ;
	  }else{
		  var param1=params.split(';')[0];
		  var param2=params.split(';')[1];
		  var date1=$("[name='extendDataFormInfo.value("+param1+")']").val();
		  var date2=$("[name='extendDataFormInfo.value("+param2+")']").val();
		  var days=DateDiff(date1,date2);
		  if(days<0){
			  jqalert({
	              title:'',
	              content:fsscLang['fssc-mobile:fssc.mobile.date.diff.tips'],
	              yestext:fsscLang['button.ok']
	          })
			  $("[name='extendDataFormInfo.value("+target+")']").val('');
			  dayFlag=false;
			  return ;
		  }else{
			  days = days==null?1:(days*1+1);
			  $("[name='extendDataFormInfo.value("+target+")']").val(days);  //天数加1
			  dayFlag=true;
		  }
	  }
  }
  
   //计算天数差的函数，通用  
     function  DateDiff(startDate,  endDate){    //startDate和startDate是xxxx-xx-xx格式  
	  	var days;
       if(startDate != '' && endDate != ''){
		 	days  =  parseInt((new Date(endDate.replace(/\-/g, '/')).getTime()  -  new Date(startDate.replace(/\-/g, '/')).getTime())  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
	   	}
       return  days  
   }    
  /**
   * 切换申请人重新设置公司信息
   * @returns
   */
  function setCompanyByPerson(params,target){
	  var fdPersonId=$("[name='extendDataFormInfo.value("+params+".id)']").val();
	  $.ajax({
			url: formInitData["LUI_ContextPath"] + '/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=getDefaultCompany',
			type: 'post',
			async:false,
			data: {"personId":fdPersonId},
	   	}).error(function(data){
			console.log("获取公司信息失败"+data);
		}).success(function(data){
			 console.log("获取公司信息成功");
	      	 var rtn = JSON.parse(data);  //json数组
	      	 if(rtn.result=='success'&&rtn.data){
	      		$("[name='extendDataFormInfo.value("+target+"_name)']").val(rtn.data[0].name);
		      	$("[name='extendDataFormInfo.value("+target+")']").val(rtn.data[0].id);
		      	$("[name='fdCompanyId']").val(rtn.data[0].id);
	      	 }
		});
  }
  
  /**
   * 切换申请人重新设置部门信息
   * @returns
   */
  function setDeptByPerson(params,target){
	  var fdPersonId=$("[name='extendDataFormInfo.value("+params+".id)']").val();
	  $.ajax({
			url: formInitData["LUI_ContextPath"] + '/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=getDefaultOrg',
			type: 'post',
			async:false,
			data: {"personId":fdPersonId},
	   	}).error(function(data){
			console.log("获取部门信息失败"+data);
		}).success(function(data){
			 console.log("获取部门信息成功");
	      	 var rtn = JSON.parse(data);  //json数组
	      	 $("[name='extendDataFormInfo.value("+target+".name)']").val(rtn.name);
	      	 $("[name='extendDataFormInfo.value("+target+".id)']").val(rtn.id);
		});
  }
  /**
   * 填写申请金额计算本位币
   * @returns
   */
  function setLocalMoney(params,target){
	  var paramArr=params.split(';');
	  if(paramArr.length!=2){
		  return ;
	  }
	  var fdCompanyId=$("[name='fdCompanyId']").val();
	  var currencyId,currencyName,fdMoney;
	  for(var n=0;n<paramArr.length;n++){
		  currencyName=$("[name='extendDataFormInfo.value("+paramArr[n]+"_name)']").val();
		  if(currencyName){//汇率是对象，name属性有值，则为汇率，不然是申请金额
			  currencyId=$("[name='extendDataFormInfo.value("+paramArr[n]+")']").val();
			  fdMoney=$("[name='extendDataFormInfo.value("+paramArr[(n==0?1:0)]+")']").val();
			  $.ajax({
				  url: formInitData["LUI_ContextPath"] + '/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=getExchangeRate',
				  type: 'post',
				  async:false,
				  data: {"currencyId":currencyId,"fdCompanyId":fdCompanyId},
			  }).error(function(data){
				  console.log("获取币种汇率信息失败"+data);
			  }).success(function(data){
				  console.log("获取币种汇率信息成功");
				  var rtn = JSON.parse(data);  //json数组
				  if(!isNumber(fdMoney)){
					  return ;
				  }
				  if(rtn.rate&&fdMoney){
					  $("[name='extendDataFormInfo.value("+target+")']").val(multiPoint(rtn.rate,fdMoney));
				  }
			  });
		  }
	  }
	  //合计金额计算本币，target为本位币
	  var change_event=$("[name*='"+target+"']").attr("onchange");
	  if(change_event){
	  	params=change_event.replace("changeValue('","").replace("');","");
	  }
	  	changeValue(params);//选择完对象触发对应的联动函数
  }
  /**
   * 计算申请总金额
   * @returns
   */
  function sumMoney(params,target){
	  apply_money_params=params;
	  total_money_target=target;  //设置申请金额和申请总额全局变量，方便明细保存重新计算金额
  }
  
  function sumTotal(apply_money_params,total_money_target){
	  var sumMoney=0.0;
	  var money_field,total_field;
	  if(apply_money_params&&apply_money_params.indexOf('.')>-1){
		  money_field=apply_money_params.split('.')[1];
	  }else{
		  money_field= apply_money_params;
	  }
	  if(total_money_target&&total_money_target.indexOf('.')>-1){
		  total_field=total_money_target.split('.')[1];
	  }else{
		  total_field= total_money_target;
	  }
	  $("[name*='."+money_field+"']").each(function(){
		 var val= $(this).val();
		 if(val){
			 var name=$(this).attr("name");
			 name=name.substring(25,name.length-1);
			 var index=name.substring(name.indexOf('.')+1,name.lastIndexOf('.'));
			 sumMoney=numAdd(sumMoney,val); //合计本币
		 }
	  });
	  $("[name='extendDataFormInfo.value("+total_field+")']").val(formatFloat(sumMoney,2));
  }
  
  /**
   * 清空默认成本中心
   * @param params
   * @param target
   * @returns
   */
  function setCostCenterByCompany(params,target){
	  $("[name*='"+target+"']").val('');
	  $("[name*='"+target+"_name']").val('');
	  var company_field,person_field,fdPersonId;
	  if(params.indexOf(';')==-1){
		  //一个参数,配置的需为公司
		  company_field=params;
	  }
	  if(params.indexOf(';')>-1){
		  company_field=params.split(';')[0] ;
		  person_field=params.split(';')[1] ;
	  }
	  var fdCompanyId=$("[name='extendDataFormInfo.value("+company_field+")']").val();
	  if(!fdCompanyId){//公司无值，说明人员在前
		  fdCompanyId=$("[name='extendDataFormInfo.value("+person_field+")']").val();
		  fdPersonId=$("[name='extendDataFormInfo.value("+company_field+".id)']").val();
	  }else{
		  fdPersonId=$("[name='extendDataFormInfo.value("+person_field+".id)']").val();
	  }
	  if(fdCompanyId&&fdPersonId){
		  var kms = new KMSSData();
		  kms.AddBeanData("eopBasedataCostCenterService&authCurrent=true&flag=defaultCostCenter&fdCompanyId="+fdCompanyId+"&fdPersonId="+fdPersonId);
		  kms = kms.GetHashMapArray();
		  if(kms&&kms.length>0){
			  if(kms[0].id){
				  $("[name='extendDataFormInfo.value("+target+")']").val(kms[0].id);
			  }
			  if(kms[0].name){
				  $("[name='extendDataFormInfo.value("+target+"_name)']").val(kms[0].name);
			  }
		  }
	  }
  }
  /**
   * 重新设置城市
   * @param params
   * @param target
   * @returns
   */
  function setCityByCompany(params,target){
	 
  }
  /**
   * 重新设置币种,由于币种是公共信息的币种，无需重新设置
   * @param params
   * @param target
   * @returns
   */
  function setCurrencyByCompany(params,target){
	  
  }
  
  /**
   * 清空前提条件为公司的数据
   * @param params
   * @param target
   * @returns
   */
  function clearData(params,target){
	  var fdMappFeild=$("[name='fdMappFeild']").val();
  	  if(fdMappFeild){
  		fdMappFeild=JSON.parse(fdMappFeild); 
  		//清空
  		var arr=["fdCostCenterId","fdExpenseItemId","fdProjectId","fdInnerOrderId","fdWbsId","fdAreaId","fdVehicleId"];
  		for(var i=0;i<arr.length;i++){
  			var feild=fdMappFeild[arr[i]];
  	  		var temp=feild.split('.').length==1?feild.split('.')[0]:feild.split('.')[1]
  	  		$("[name*='"+temp+"']").val('');
  		}
  	  }
  }
  /**
   * 清空前提条件为公司的数据
   * @param params
   * @param target
   * @returns
   */
  function clearWbsData(params,target){
	  var fdMappFeild=$("[name='fdMappFeild']").val();
  	  if(fdMappFeild){
  		  fdMappFeild=JSON.parse(fdMappFeild); 
  		  var feild=fdMappFeild["fdWbsId"];
  		  var temp=feild.split('.').length==1?feild.split('.')[0]:feild.split('.')[1]
  		  $("[name*='"+temp+"']").val('');
  	  }
  }
  
//明细切换币种，重新赋值汇率
  function setRate(params){
	  if(params.indexOf(';')==-1){
		  return ;
	  }
	  var company_field=params.split(';')[0];
	  var fdCompanyId=$("[name$='"+company_field+")']").val();
	  var currency_field=params.split(';')[1];
	  var fdCurrencyId=$("[name$='"+currency_field+")']").val();
	  	var kms = new KMSSData();
		kms.AddBeanData("eopBasedataExchangeRateService&authCurrent=true&type=getRateByCurrency&fdCurrencyId="+fdCurrencyId+"&fdCompanyId="+fdCompanyId);
		kms = kms.GetHashMapArray();
		if(kms&&kms.length>0){
			$("[name='extendDataFormInfo.value("+currency_field+"_cost_rate)']").val(kms[0].fdExchangeRate?kms[0].fdExchangeRate:1.0);
			$("[name='extendDataFormInfo.value("+currency_field+"_budget_rate)']").val(kms[0].fdBudgetRate?kms[0].fdBudgetRate:1.0);
			$("[name='currency_code']").val(kms[0].code?kms[0].code:'￥');
		}
  }
 
  /****************************联动函数  end***************************************/
  
  
  /****************************提交校验  star***************************************/
//提交时查找预算
 Com_Parameter.event.submit.push(function(){
	 if($("[name=docStatus]").val()=='10'){
		return true; 
	 }
	var fdMappFeild=$("[name='fdMappFeild']").val();
 	if(fdMappFeild){
 		fdMappFeild=JSON.parse(fdMappFeild); 
 	}
 	var budgetStatusId=fdMappFeild["fdRuleId"];
 	if(!budgetStatusId){//无设置预算，不校验预算
 		return true;
 	}
  	var method=$("[name='method_GET']").val();
  	var fdTemplateId=$("[name='fdTemplateId']").val();
	var budgetInfoId=budgetStatusId.substring(0,budgetStatusId.lastIndexOf('status'))+'info';
  	var params = [],len = $("#TABLE_DL_"+fdMappFeild['fdTableId']).find("[name*='."+budgetInfoId.split('.')[1]+"']").length;
  	if(formInitData['budgetMatchList']){
  		len=1;  //获取主表信息，一次
  	}
  	var msg = {'fdCompanyId':fsscLang['fssc-fee:control.currency.company'],'fdExpenseItemId':fsscLang['fssc-fee:control.standardRule.expenseItem'],'fdMoney':fsscLang['fssc-fee:control.standardRule.money'],'fdCurrencyId':fsscLang['fssc-fee:control.currency.title']}
  	for(var i=0;i<len;i++){
  		var param = {
  				'fdCompanyId':getFieldValue(fdMappFeild["fdCompanyId"],i),
  		  		'fdProjectId':getFieldValue(fdMappFeild["fdProjectId"],i),
  		  		'fdWbsId':getFieldValue(fdMappFeild["fdWbsId"],i), 
  		  		'fdInnerOrderId':getFieldValue(fdMappFeild["fdInnerOrderId"],i),
  		  		'fdExpenseItemId':getFieldValue(fdMappFeild["fdExpenseItemId"],i),
  		  		'fdCostCenterId':getFieldValue(fdMappFeild["fdCostCenterId"],i),
  		  		'fdPersonId':getFieldValue(fdMappFeild["fdPersonId"],i),
  		  		'fdDeptId':getFieldValue(fdMappFeild["fdDeptId"],i),
  		  		'fdMoney':getFieldValue(fdMappFeild["fdMoneyId"],i),
  		  		'fdCurrencyId':getFieldValue(fdMappFeild["fdCurrencyId"],i),
  		  		'fdTemplateId':fdTemplateId,
  		  		'index':i
  		}
  		var budgetStatusId=fdMappFeild["fdRuleId"];
  		var budgetInfoId=budgetStatusId.substring(0,budgetStatusId.lastIndexOf('status'))+'info';
    	if(method=="view"&&getFieldValue(budgetInfoId,i)){
    		param[i]['fdBudgetInfo']=getFieldValue(budgetInfoId,i);
    	}
  		params.push(param);
  	}
  		
  	
  	var pass = true;
  	$.ajax({
  		url:Com_Parameter.ContextPath+'fssc/fee/fssc_fee_main/fsscFeeMain.do?method=getBudgetData',
  		data:$.param({"data":JSON.stringify(params)},true),
  		dataType:'json',
  		type:'post',
  		async:false,
  		success:function(rtn){
  			var money = {};
			var status = [];
			var isNeedBudget = [];
			//校验多条明细占用同一个预算的情况下是否超预算，并显示预算状态图标
			for(var i=0;i<rtn.length;i++){
				isNeedBudget[rtn[i].fdDetailId]=rtn[i].fdIsNeedBudget;
				if(rtn[i].budget.result=='0'&&pass){
					jqalert({
		                    title:'',
		                    content:rtn[i].budget.message,
		                    yestext:fsscLang['button.ok']
		                })
		            $(".ld-entertain-main-body").html(detail_div_body);  //还原detail隐藏div，不然无法编辑
					pass = false;
					continue;
				}
				var budget = rtn[i].budget.data||[];
				$("[name*='."+rtn[i].fdDetailId+".'][name*='_budget_info']").val(JSON.stringify(budget).replace(/\"/g,"'"));
				var applyMoney = params[rtn[i].fdDetailId].fdMoney;
				var bmoney = applyMoney;
				for(var k=0;k<budget.length;k++){
					//弹性控制，需要计算弹性比例
					if(budget[k].fdRule=='3'){
						budget[k].fdElasticMoney = numDiv(numMulti(budget[k].fdTotalAmount,budget[k].fdElasticPercent),100);
					}else{
						budget[k].fdElasticMoney = 0;
					}
					if(money[budget[k].fdBudgetId]){
						money[budget[k].fdBudgetId].fdApplyMoney = numAdd(money[budget[k].fdBudgetId].fdApplyMoney,bmoney*1);
					}else{
						money[budget[k].fdBudgetId] = {
							fdApplyMoney:bmoney*1,
							fdCanUseMoney : budget[k].fdCanUseAmount*1,
							fdRule : budget[k].fdRule,
							fdElasticMoney : budget[k].fdElasticMoney
						}
					}
					//如果不是柔性控制，需要判断是否超预算
					if(money[budget[k].fdBudgetId].fdApplyMoney-money[budget[k].fdBudgetId].fdCanUseMoney>0&&pass){
						if(budget[k].fdRule=='1'){ //刚控
							if(formInitData['budgetMatchList']){
								//刚控超预算不允许提交
								jqalert({
				                    title:'',
				                    content:fsscLang['fssc-fee:tips.budget.over.main'],
				                    yestext:fsscLang['button.ok']
				                })
						  	}else{
						  	//刚控超预算不允许提交
								jqalert({
				                    title:'',
				                    content:fsscLang['fssc-fee:tips.budget.over.row'].replace('{row}',rtn[i].fdDetailId*1+1),
				                    yestext:fsscLang['button.ok']
				                })
						  	}
			                $(".ld-entertain-main-body").html(detail_div_body);  //还原detail隐藏div，不然无法编辑
							pass = false;
						}else if(budget[k].fdRule=='3'&&money[budget[k].fdBudgetId].fdApplyMoney>numAdd(money[budget[k].fdBudgetId].fdCanUseMoney,money[budget[k].fdBudgetId].fdElasticMoney)){ //弹性,且申请金额大于弹性金额
							//柔控不提示，也允许提交
							if(formInitData['budgetMatchList']){
								//刚控超预算不允许提交
								jqalert({
				                    title:'',
				                    content:fsscLang['fssc-fee:tips.budget.over.main'],
				                    yestext:fsscLang['button.ok']
				                })
						  	}else {
						  		jqalert({
									title:'',
									content:fsscLang['fssc-fee:tips.budget.over.row'].replace('{row}',rtn[i].fdDetailId*1+1),
									yestext:fsscLang['button.ok']
								})
						  	}
							$(".ld-entertain-main-body").html(detail_div_body);  //还原detail隐藏div，不然无法编辑
							pass = false;
						}
						status[rtn[i].fdDetailId] = '2';
					}else{
						if(status[rtn[i].fdDetailId]!='2'){//且前面判断没有超的，不然会出现后面预算内覆盖前面超预算信息
							status[rtn[i].fdDetailId] = '1';
						}
					}
				}
			}
  			//设置预算状态显示
  			for(var i=0;i<len;i++){
  				status[i] = status[i]?status[i]:'0';
  				if(status[i]=='0'&&isNeedBudget[i]){//无预算，但费用类型要求必须要有预算
  					jqalert({
	                    title:'',
	                    content:fsscLang['fssc-fee:tips.expense.budgetRequired'].replace('{0}',rtn[i].fdDetailId*1+1).replace('{1}',getFieldValue(fdMappFeild["fdExpenseItemId"]+'_name',i)),
	                    yestext:fsscLang['button.ok']
	                })
	                $(".ld-entertain-main-body").html(detail_div_body);  //还原detail隐藏div，不然无法编辑
					pass = false;
  				}
  				var rule_id=fdMappFeild["fdRuleId"].replace(/.+\.(.+)$/g,'$1');
  				$("[name*='."+i+"."+rule_id+")']").val(status[i]);
  				var budget = $("[name*='."+i+"."+rule_id.replace('status','info')+")']").val();
  				$(".ld-notSubmit-list-top").eq(i).find("span").eq(0).html(fsscLang['fssc-fee:py.budget.'+status[i]]); //显示文字
  				$(".ld-notSubmit-list-top").eq(i).find("span").eq(0).attr('class','ld-notSubmit-entryType_'+status[i]);  //显示样式
  			}
  		}
  	})
  	return pass;
  });
  Com_Parameter.event.submit.push(function(){
  	//暂存不作校验
	  if($("[name=docStatus]").val()=='10'){
			return true; 
		 }
  	var pass = true;
  	var fdMappFeild=$("[name='fdMappFeild']").val();
 	if(fdMappFeild){
 		fdMappFeild=JSON.parse(fdMappFeild); 
 	}
 	var budgetStatusId=fdMappFeild["fdRuleId"];
 	if(!budgetStatusId){//无设置预算，不校验预算
 		return true;
 	}
 	var budgetInfoId="";
 	if(budgetInfoId.indexOf('.')>-1){
 		budgetInfoId=budgetStatusId.split('.')[1].replace('status','info')
 	}else{
 		budgetInfoId=budgetStatusId.replace('status','info');
 	}
  	$("[name*='."+budgetInfoId+"']").each(function(){
  		if(!pass||this.name.indexOf('index')>-1){
  			return;
  		}
  		//未匹配到预算，校验该费用类型是否必须有预算
  		if(!this.value||this.value=='[]'){
  			var index = this.name.replace(/\S+\.(\d+)\.\S+/,'$1');
  			var data = new KMSSData();
  			var fdCompanyId =getFieldValue(fdMappFeild["fdCompanyId"],index);
  			var fdExpenseItemId = getFieldValue(fdMappFeild["fdExpenseItemId"],index);
  			var docTemplateId = $("[name=fdTemplateId]").val();
  			data.AddBeanData("fsscFeeExpenseItemService&fdCompanyId="+fdCompanyId+"&fdExpenseItemId="+fdExpenseItemId+"&docTemplateId="+docTemplateId);
  			data = data.GetHashMapArray();
  			if(data&&data.length>0){
  				if(data[0].result=='true'){
  					var fdExpenseItemName =getFieldValue(fdMappFeild["fdExpenseItemId"]+"_name",index);
  					jqalert({
  	                    title:'',
  	                    content:fsscLang['fssc-fee:tips.expense.budgetRequired'].replace("{1}",fdExpenseItemName).replace("{0}",index*1+1),
  	                    yestext:fsscLang['button.ok']
  	                })
  	                $(".ld-entertain-main-body").html(detail_div_body);  //还原detail隐藏div，不然无法编辑
  					pass = false;
  				}
  			}
  		}
  	})
  	return pass;
  })
  //校验标准
  Com_Parameter.event.submit.push(function(){
	if($("[name=docStatus]").val()=='10'){
		return true; 
	}
	var fdMappFeild=$("[name='fdMappFeild']").val();
 	if(fdMappFeild){
 		fdMappFeild=JSON.parse(fdMappFeild); 
 	}
 	var standardStatusId=fdMappFeild["fdStandardId"];
  	//如果没有标准规则控件，不作校验
  	if(!standardStatusId){
  		return true;
  	}
  	var params = [],len = $("#TABLE_DL_"+fdMappFeild['fdTableId']+" tr").find("[name*='."+standardStatusId.split('.')[1]+"']").length;
  	if(!len){
  		len=1;  //主表
  	}
  	for(var i=0;i<len;i++){
  		if(($("[name*='."+i+"."+fdMappFeild['fdExpenseItemId'].replace(/.+\.(.+)/,"$1")+")']").length==0&&fdMappFeild['fdTableId'])
  				||$("[name*='"+fdMappFeild['fdExpenseItemId'].replace(/.+\.(.+)/,"$1")+")']").length==0){
  			continue;
  		}
  		var fdAreaId=getFieldValue(fdMappFeild["fdAreaId"],i);
  	  	if(fdAreaId&&fdAreaId.indexOf("__")>-1){//说明获取的值 ：北京__1675e001c2880471b2b891d4b8a84ca7
  	  		fdAreaId=fdAreaId.split('__')[1];
  	  	}
  	  var fdBerthId=getFieldValue(fdMappFeild["fdVehicleId"],i);  //舱位
	  var fdVehicleId='';
	  	if(fdBerthId){
	  		var kms = new KMSSData();
			  kms.AddBeanData("eopBasedataBerthService&authCurrent=true&fdBerthId="+fdBerthId);
			  kms = kms.GetHashMapArray();
			  if(kms&&kms.length>0){
				  if(kms[0].fdVehicleId){
					  fdVehicleId=kms[0].fdVehicleId;  //交通工具
				  }
			  }
	  	}
  		params.push({
  			'fdCompanyId':getFieldValue(fdMappFeild["fdCompanyId"],i),
  	  		'fdExpenseItemId':getFieldValue(fdMappFeild["fdExpenseItemId"],i),
  	  		'fdSpecialId':getFieldValue(fdMappFeild["fdSpecialId"],i),
  	  		'fdPersonId':getFieldValue(fdMappFeild["fdPersonId"],i),
  	  		'fdMoney':getFieldValue(fdMappFeild["fdMoneyId"],i),
  	  		'fdCurrencyId':getFieldValue(fdMappFeild["fdCurrencyId"],i),
  	  		'fdTravelDays':getFieldValue(fdMappFeild["fdTravelDaysId"],i),
  	  		'fdVehicleId':fdVehicleId,
	  		'fdBerthId':fdBerthId,
  	  		'fdAreaId':fdAreaId,
  	  		'fdPersonNumber':getFieldValue(fdMappFeild["fdPersonNumId"])||1,
  			'index':i
  		})
  	}
  	var pass = true;
  	$.ajax({
  		url:Com_Parameter.ContextPath + 'fssc/fee/fssc_fee_main/fsscFeeMain.do?method=getStandardData',
  		data:{params:JSON.stringify(params)},
  		async:false,
  		type:'post',
  		success:function(rtn){
  			rtn = JSON.parse(rtn);
  			if(rtn.result=='failure'){
  				jqalert({
                    title:'',
                    content:rtn.message,
                    yestext:fsscLang['button.ok']
                })
                $(".ld-entertain-main-body").html(detail_div_body);  //还原detail隐藏div，不然无法编辑
  				pass = false;
  				return;
  			}
  			var status_id=standardStatusId.replace(/.+\.(.+)/,"$1");
  			var info_id=standardStatusId.replace(/.+\.(.+)/,"$1").replace('status','info');
  			for(var i=0;i<rtn.data.length;i++){
  				$("[name*='."+rtn.data[i].index+"."+status_id+"']").val(rtn.data[i].status);
  				if(rtn.data[i].status=='2'&&rtn.data[i].submit=='0'&&pass){
  					if(fdMappFeild['fdTableId']){//明细提示
  						jqalert({
  	  	                    title:'',
  	  	                    content:fsscLang['fssc-fee:tips.standard.over'].replace("{row}",rtn.data[i].index*1+1),
  	  	                    yestext:fsscLang['button.ok']
  	  	                })
  					}else{
  						jqalert({
  	  	                    title:'',
  	  	                    content:fsscLang['fssc-fee:tips.standard.over.main'],
  	  	                    yestext:fsscLang['button.ok']
  	  	                })
  					}
  	                $(".ld-entertain-main-body").html(detail_div_body);  //还原detail隐藏div，不然无法编辑
  					pass = false;
  				}
  				$(".ld-notSubmit-list-top").eq(i).find("span").eq(1).html(fsscLang['fssc-fee:py.standard.'+rtn.data[i].status]); //显示文字
  				$(".ld-notSubmit-list-top").eq(i).find("span").eq(1).attr('class','ld-notSubmit-entryType_'+rtn.data[i].status);  //显示样式
  			}
  		}
  	});
  	return pass;
  });
  
//保存单据之前更新附件fdModelId，fdModelName
  Com_Parameter.event.submit.push(function(){
  	var params = [],pass = true;
  	$(".ld-remember-attact-info").each(function(){
  		params.push({
  			fdAttId:$(this).data('attid'),
  			fdModelId:$("[name='fdId']").val(),
  			fdModelName:'com.landray.kmss.fssc.fee.model.FsscFeeMain'
  		})
  	})
  	if(params.length>0){
  		$.ajax({
  			url:formInitData["LUI_ContextPath"] +"/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=updateAtt",
  			data:{params:JSON.stringify(params)},
  			dataType:'json',
  			async:false,
  			success:function(rtn){
  				if(rtn.result!='success'){
  					pass = false;
  					jqtoast('更新附件异常：'+rtn.message);
  				}
  			}
  		})
  	}
  	return pass;
  })
  
  /****************************提交校验  end***************************************/
  
//主表设置必填星号
  function setMainValidate(){
	  $(".ld-newApplicationForm-info").find("input[type='text'],textarea").each(function(){
			var validate=$(this).attr("validator");
			if(validate&&validate.indexOf('required')>-1){
				var html,parent=$(this).parent();
				while(!html&&parent){
					$parent=$(parent);
					html=$parent.find(".ld-remember-label").html();
					if(!html){
						parent=$parent.parent();
					}
				}
				$parent.find(".ld-remember-label").html(html+'<span style="margin-left:2px;color:#d02300;">*</span>');
			}
		});
  }

//主表必填校验
  function validateMainFeild(){
	  var subFlag=true;
	  var msg=fsscLang['errors.required'];
	  var docSubject=$("[name='docSubject']").val();//标题单独判断
	  if(!docSubject&&$("[name='docSubject']").attr('readonly')!='readonly'){
		  var subject=$("[name='docSubject']").attr('subject');
		  if(!subject){
			  subject='';
		  }
		  jqtoast(msg.replace("{0}",subject));
		  return false;
	  }
	  //主表字段必填校验
	  $(".ld-newApplicationForm-info").find("input[type='text']").each(function(){
			var validate=$(this).attr("validator");
			// 必填
			if(validate&&validate.indexOf('required')>-1&&!$(this).val()){
				var subject=$(this).attr('subject');
				jqtoast(msg.replace("{0}",subject));
				subFlag=false;
				return false;
			}
	  });
	  if(!subFlag){
		 return false; 
	  }
	  $(".ld-entertain-main-body").find("input[validator$='required']").each(function(){
		  var name=$(this).attr('name');
		  var subject=$(this).attr('subject');
		  var validate=$(this).attr('validator');
		  if(validate.indexOf('required')>-1&&name){
			  name=name.substring(25,name.length-1);
			  var tab_id=name.substring(0,name.indexOf('.'));
			  name=name.substring(name.indexOf('.')+1,name.length);
			  var tab_len=$("[id^='TABLE_DL_']").length;
			  var tr_len=$("#"+tab_id+" tbody>tr").length;
			  for(var j=0;j<tr_len;j++){
				  var val=$("input[name*='"+j+"."+name+"']").val();
				  if(!val){
					  jqtoast(msg.replace("{0}",'第'+(j*1+1)+'行明细'+subject));
					  subFlag=false;
					  return false;
				  }
			  }
		  }
	  });
	  return subFlag;
  }
  
  
  /************************************************************************ 
   * 删除行
   * ************************************************************************/
	 function FSSC_Fee_DeleteRow(tabId,rowIndex){
		var optTR=$("#"+tabId).find("tr")[rowIndex];
		DocList_DeleteRow(optTR);
	}
	 
	 function selectOption(id,name,options){
		 var data = [],options = options.split(';'),selectedIndex = 0;
		 for(var i=0;i<options.length;i++){
			 var opt = options[i].split("\|");
			 data.push({text:opt[0],value:opt[1]})
			 if($("[name='"+id+"']").val()==opt[1]){
				 selectedIndex = i;
			 }
		 }
		 var picker = new Picker({
	        data:[data],
	        selectedIndex:[selectedIndex]
	     });
	     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
	    	 	$("[name='"+name+"']").val(data[selectedIndex[0]].text);
	    	 	$("[name='"+id+"']").val(data[selectedIndex[0]].value);
	     });
	    	 picker.show();
	 }
	 
	 function selectSubmitType(id,name){
		 var displayList=formInitData["displayList"];
		  if(displayList){
			  displayList=displayList.substring(1,displayList.length-1).split(',');
			  var personId=$("[name='extendDataFormInfo.value("+displayList[3]+")']").val();
		  }
		  if(!personId){
			  personId = $("[name=docCreatorId]").val();
		  }
			$.post(
					Com_Parameter.ContextPath+'fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getClaimantStatu',
					{fdPersonId:$("[name=docCreatorId]").val()},
					function(data){
						data = JSON.parse(data);
						var picker = new Picker({
					        data:[data.data]
					     });
					     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
					    	 $('[name='+name+']').val(data.data[selectedIndex[0]].text);
					    	 $('[name='+id+']').val(data.data[selectedIndex[0]].value)
					    	 $("[name=rolesSelectObj]").val(data.data[selectedIndex[0]].value);
					     });
				    	 picker.show();
					}
			);
		 }
	 $(function(){
			var method = Com_GetUrlParameter(window.location.href,'method');
			if(method=='edit'){
				loadStatus();
			}else{
				$('[name=fdSubmitTypeId]').val($('[name=docCreatorId]').val())
				$('[name=fdSubmitType]').val($("[name=docCreatorName]").val())
			}
		})
		function loadStatus(){
			if($("[name=rolesSelectObj]").length==0||!$("[name=rolesSelectObj]").val()){
				setTimeout("loadStatus()",150)
			}else{
				$('[name=fdSubmitTypeId]').val($("[name=rolesSelectObj]").val())
				$('[name=fdSubmitType]').val($("[name=rolesSelectObj] option:selected").text())
			}
		}
 

  
