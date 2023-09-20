define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	
	var dialog = require('lui/dialog');
	var elem = $('<div><input type="hidden" id="validateTypes"><input type="hidden" id="currentValidateType"><div id="div_validateType_1" style="width:99%;height:92%;"> <br>   <div align="left"> 身份校验密码校验<div><br>  <div align="center">请输入您的身份校验密码:</div><br><br>   <div align="center"><input name="fdVerifyPwd" class="inputsgl" value="" type="password" style="width:150px"><span class="txtstrong">*</span>   </div><br><br>   <div align="center">说明:输入校验密码点击并确认则表示,您已同意并承认此行为为您本人意愿</div></div></div></div><div id="div_validateType_2" style="width:99%;height:92%;display:none">    <br>   <div align="left"> 身份校验密码校验<div><br>  <div align="center">抱歉，您尚无法执行此操作:</div><br><br>  <div align="center">您尚且未设置身份校验密码,请在设置完校验密码后再完成此操作</div><br><br>   <div align="center">说明:输入校验密码点击并确认则表示,您已同意并承认此行为为您本人意愿</div></div></div></div><div id="div_validateType_errorMsg"></div><div><input type="button" value="确定"><input type="button" value="取消"></div></div>');
	
	
	var validate2 = base.Base.extend({
		startup : function(){
			var config = this.config,
			element = config.element;
			
			dialog.build( {
					config : {
						width:400,
						height:300,
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
									dialogFr.hide();
								}
							}]
						},
						title : "身份校验"
					},

					callback : function(value, dialog) {
						currentIndex = null;
						$(obj).undelegate("select[name='fdAttrField']","change");
					},
					actor : {
						type : "default"
					},
					trigger : {
						type : "default"
					}
				}).show();
			
			alert(elem.html());
			
//				$(elem).delegate("select[name='fdAttrField']","change",function(event){
//					var option = $(this).find("option:selected");
//					if(option.attr('data-type') == 'enum'){
//						//构建选项
//						var suffix = option.val() + "_" + ++_xform_main_data_show_radioNameNum;
//						$(this).after(xform_main_data_custom_buildEnumHtml(suffix));
//					}
//					xform_main_data_custome_stopBubble(event);
//				});
//				$('.lui_dialog_head_right').hide();
			
		}
	});
	
	var validate = function(options){
		var config = options;
		//element = config.element;
		
		dialog.build( {
				config : {
					width:400,
					height:300,
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
								$.ajax({
									url: Com_Parameter.ContextPath + "/elec/authentication/verify/elecAuthenVerify.do?method=validate&modelPath="+config.modelPath+"&validateType="+validateType,
									type: "GET",
									//data: config,
									dataType:"json",
									async: false,
									success: function(result){
										//alert(result);
										//var result_json = JSON.parse(result);
										var errMsg = result.errMsg;
										var validateTypes = result.validateTypes;
										if(errMsg){
											elem.find("div[id^='div_validateType_']").hide();  
											elem.find("div[id='div_validateType_errorMsg']").text(errMsg);
											elem.find("div[id='div_validateType_errorMsg']").show();
										}else{
											elem.find("div[id^='div_validateType_']").hide();  
											var validateType = validateTypes[0];
											elem.find("div[id='div_validateType_"+validateType+"']").show();
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
							}
						}]
					},
					title : "身份校验"
				},

				callback : function(value, dialog) {
					alert($("#div_bodyJsonOut2").html());
					
					currentIndex = null;
					//$(obj).undelegate("select[name='fdAttrField']","change");
				},
				actor : {
					type : "default"
				},
				trigger : {
					type : "default"
				}
			}).show();
		//alert(11);
		$.ajax({
			url: Com_Parameter.ContextPath + "/elec/authentication/verify/elecAuthenVerify.do?method=config&modelPath="+config.modelPath+"&validateType="+config.validateType,
			type: "GET",
			//data: config,
			dataType:"json",
			async: false,
			success: function(result){
				//alert(result);
				//var result_json = JSON.parse(result);
				var errMsg = result.errMsg;
				var validateTypes = result.validateTypes;
				if(errMsg){
					elem.find("div[id^='div_validateType_']").hide();  
					elem.find("div[id='div_validateType_errorMsg']").text(errMsg);
					elem.find("div[id='div_validateType_errorMsg']").show();
				}else{
					elem.find("div[id^='div_validateType_']").hide();  
					var validateType = validateTypes[0];
					elem.find("input[id='currentValidateType']").val(validateType);
					elem.find("input[id='validateTypes']").val(validateTypes);
					elem.find("div[id='div_validateType_"+validateType+"']").show();
				}
			}}
		);
		//alert(33);
		//elem.find("div[id='div_bodyJsonOut']").hide();
		//alert(elem.html());
		
	
	}
	
	exports.validate = validate;
});