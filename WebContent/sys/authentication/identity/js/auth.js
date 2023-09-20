define(function(require, exports, module) {
	
	seajs.use("sys/authentication/identity/css/custom.css")
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	
	var dialog = require('lui/dialog');
	//var elem = $('<div><input type="hidden" id="validateTypes"><input type="hidden" id="currentValidateType"><div id="div_validateType_1" style="width:99%;height:92%;"> <br>   <div align="left"> 身份校验密码校验<div><br>  <div align="center">请输入您的身份校验密码:</div><br><br>   <div align="center"><input name="fdVerifyPwd" class="inputsgl" value="" type="password" style="width:150px"><span class="txtstrong">*</span>   </div><br><br>   <div align="center">说明:输入校验密码点击并确认则表示,您已同意并承认此行为为您本人意愿</div></div></div></div><div id="div_validateType_2" style="width:99%;height:92%;display:none">    <br>   <div align="left"> 身份校验密码校验<div><br>  <div align="center">抱歉，您尚无法执行此操作:</div><br><br>  <div align="center">您尚且未设置身份校验密码,请在设置完校验密码后再完成此操作</div><br><br>   <div align="center">说明:输入校验密码点击并确认则表示,您已同意并承认此行为为您本人意愿</div></div></div></div><div id="div_validateType_errorMsg"></div><div><input type="button" value="确定"><input type="button" value="取消"></div></div>');
	//var elem = $('<div><input type="hidden" id="validateTypes"><input type="hidden" id="currentValidateType"><div id="div_validate" style="width:99%;height:92%;"><iframe id="ifr_validate"></iframe></div><div id="div_validateType_errorMsg"></div><div><input type="button" value="确定"><input type="button" value="取消"></div></div>');
	var elem = $('<div><input type="hidden" id="validateTypes"><input type="hidden" id="currentValidateType"><div id="div_validate" style="width:490px;height:328pxpx;"><iframe id="ifr_validate" style="width:486px;height:320px;scrolling:no;border:0;"></iframe></div><div id="div_validateType_errorMsg" style="line-height:280px;font-size:20px"></div></div>');
	
	var validate = function(options){
		var config = options;
		var validateType = "";
		if(config.validateType && config.validateType!=''){
			validateType = config.validateType;
			//alert(validateType);
		}else{
			config.callback(true,config.params);
			return;
		}
		
		//element = config.element;
		elem = $('<div id="identity_div"><input type="hidden" id="validateTypes"><input type="hidden" id="currentValidateType"><div id="div_validate" style="width:490px;height:270px;"><iframe id="ifr_validate" style="width:486px;height:270px;scrolling:no;border:0;"></iframe></div><div id="div_validateType_errorMsg" style="line-height:280px;font-size:20px"></div></div>');
		
		var thisDialog = dialog.build( {
				config : {
					width:'500px',
					height:'280px',
					lock : true,
					cache : true,
					close: false,
					content : {
						type : "element",
						elem : elem,
						scroll : false,
						buttons : [ {
							name : "确定",
							value : true,
							focus : true,
							fn : function(value,dialogFr) {
								//dialogFr.hide();
								var data = {};
								data.config = config;
								
								var validateType = elem.find("input[id='currentValidateType']").val();
								if(!validateType || validateType==''){
									config.callback(false,config.params);
									dialogFr.hide();
									return;
								}
								var childWindow = $("#ifr_validate")[0].contentWindow; 
								if(!childWindow.dataValidate()){
									return;
								}
								var submitData = childWindow.buildSubmitData();
								data.submitData = submitData;
								var validateTypes = JSON.parse(elem.find("input[id='validateTypes']").val()); 
								data.validateTypes = validateTypes;
								$.ajax({
									url: Com_Parameter.ContextPath + "sys/authentication/indentity/sysIdentityValidate.do?method=validate&validateType="+validateType,
									type: "POST",
									data: {"data":JSON.stringify(data)},
									dataType:"json",
									async: false,
									success: function(result){
										//debugger;
										var validateType = elem.find("input[id='currentValidateType']").val()
										var errMsg = result.errMsg;
										var validate_result = result.validate_result;
										if(validate_result==true){
											dialogFr.hide();
											config.callback(validate_result,config.params);
											return;
										}
										var validateType_to = result.validateType;
										var validateType_to_page = result.validatePage;
										if(errMsg && !validateType_to){
											elem.find("div[id='div_validate']").hide();  
											elem.find("div[id='div_validateType_errorMsg']").text(errMsg);
											elem.find("div[id='div_validateType_errorMsg']").show();
											elem.find("input[id='validateTypes']").val("");  
											elem.find("input[id='currentValidateType']").val(""); 
										}else{
											//alert(validateType_to+"---"+validateType+"---"+elem.find("input[id='currentValidateType']").val());
											if(validateType_to == validateType){
												//alert(11);
												//debugger;
												if(errMsg){
													//alert(22);
													childWindow.validateResult("校验失败");
												}
											}else{
												//alert(33);
												elem.find("div[id='div_validateType_errorMsg']").hide();  
												elem.find("div[id='div_validate']").show();  
												var validateType = validateTypes[0];
												
												//alert(Com_Parameter.ContextPath+validateType.page.substring(1));
												//elem.find("input[id='validateTypes']").val(JSON.stringify(validateTypes));  
												elem.find("input[id='currentValidateType']").val(validateType_to);  
												elem.find("#ifr_validate").attr('src',Com_Parameter.ContextPath+validateType_to_page.substring(1));
											}
											
										}
										
									}}
								);
							}
						},{
							name : "取消",
							value : true,
							focus : true,
							fn : function(value,dialogFr) {
								dialogFr.hide();
								config.callback(false,config.params);
							}
						}]
					}
				},

				callback : function(value, dialog) {
					//alert($("#div_bodyJsonOut2").html());
					
					currentIndex = null;
					//$(obj).undelegate("select[name='fdAttrField']","change");
				},
				actor : {
					type : "default"
				},
				trigger : {
					type : "default"
				}
			});
			
		
		//alert(11); 
		
		$.ajax({
			url: Com_Parameter.ContextPath + "sys/authentication/indentity/sysIdentityValidate.do?method=config&modelPath="+config.modelPath+"&validateType="+validateType,
			type: "GET",
			//data: config,
			dataType:"json",
			async: false,
			success: function(result){
				debugger;
				//alert(result);
				//var result_json = JSON.parse(result);
				var errMsg = result.errMsg;
				var validateTypes = result.validateTypes;
				
				if(errMsg){
					if(errMsg=="don't need validate"){
						config.callback(true,config.params);
						return;
					}
					thisDialog.show();
					elem.find("div[id='div_validate']").hide();  
					elem.find("div[id='div_validateType_errorMsg']").text(errMsg);
					elem.find("div[id='div_validateType_errorMsg']").show();
					elem.find("input[id='validateTypes']").val("");  
					elem.find("input[id='currentValidateType']").val(""); 
				}else{
					thisDialog.show();
					elem.find("div[id='div_validateType_errorMsg']").hide();  
					elem.find("div[id='div_validate']").show();  
					var validateType = validateTypes[0];
					
					//alert(Com_Parameter.ContextPath+validateType.page.substring(1));
					elem.find("input[id='validateTypes']").val(JSON.stringify(validateTypes));  
					elem.find("input[id='currentValidateType']").val(validateType.key);  
					elem.find("#ifr_validate").attr('src',Com_Parameter.ContextPath+validateType.page.substring(1));
				}
			}}
		);
		//alert(33);
		//elem.find("div[id='div_bodyJsonOut']").hide();
		//alert(elem.html());
	
	}
	
	window.changeIdentityButton = function(title){
		var p = elem.parent().parent().find("div[title='确定']").hide();
		//debugger;
		//elem.parent().parent().find("div[title='确定']").get(0).title=title;
		var b = elem.parent().parent().find("div[title='取消']").get(0);
		$(b).attr('title',title);
		$(b).find('.lui_widget_btn_txt').text(title);
	}
	exports.validate = validate;
});