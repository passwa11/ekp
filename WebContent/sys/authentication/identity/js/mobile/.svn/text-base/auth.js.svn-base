define([
        "dojo/_base/declare",
        "dojo/dom-construct",
        "mui/util",
        "dojo/dom-style",
        "dojo/dom-attr",
        "dojo/on",
        "dojo/query",
        "dojo/touch"
	], function(declare, domConstruct, util, domStyle, domAttr, on, query, touch) {
	var claz = declare(
				"nnnnn",
				null,
				{
					validate: function(options){
						var self = this;
						var config = options;
						var validateType = "";
						if(config.validateType && config.validateType!=''){
							validateType = config.validateType;
							//alert(validateType);
						}else{
							config.callback(true,config.params);
							return;
						}
						if(query('#validate_window').length>0){
			                domConstruct.destroy(query('#validate_window')[0]);
			              }
						//构造窗口
						self.initValidateWindow();
						//确定按钮事件
						on(query('.btn-confirm')[0],touch.press,function(){
							var data = {};
							data.config = config;
							
							var validateType = query('#currentValidateType')[0].value;
							if(!validateType || validateType==''){
								config.callback(false,config.params);
								domStyle.set(query('#validate_window')[0],'display','none');
								return;
							}
							var childWindow = $("#ifr_validate")[0].contentWindow; 
							//是否有设置验证方式所需的个人信息(无法进行操作页面)
							var isHasSetting = true;
							isHasSetting = childWindow.isHasSetting();
							if(!isHasSetting){
								config.callback(false,config.params);
								domStyle.set(query('#validate_window')[0],'display','none');
								return;
							}
							if(!childWindow.dataValidate()){
								return;
							}
							var submitData = childWindow.buildSubmitData();
							data.submitData = submitData;
							var validateTypes = JSON.parse(query('#validateTypes')[0].value); 
							data.validateTypes = validateTypes;
							
							$.ajax({
								url: Com_Parameter.ContextPath + "sys/authentication/indentity/sysIdentityValidate.do?method=validate&validateType="+validateType,
								type: "POST",
								data: {"data":JSON.stringify(data)},
								dataType:"json",
								async: false,
								success: function(result){
									var validateType = query('#currentValidateType')[0].value;
									var errMsg = result.errMsg;
									var validate_result = result.validate_result;
									if(validate_result==true){
										domStyle.set(query('#validate_window')[0],'display','none');
										config.callback(validate_result,config.params);
										return;
									}
									var validateType_to = result.validateType;
									var validateType_to_page = result.validatePageMobile;
									if(errMsg && !validateType_to){
										domStyle.set(query('#ifr_validate')[0],'display','none');
										query('#span_validateType_errorMsg')[0].innerText = errMsg;
										domStyle.set(query('#div_validateType_errorMsg')[0],'display','block');
										query('#validateTypes')[0].value = "";
										query('#currentValidateType')[0].value = "";
									}else{
										if(validateType_to == validateType){
											if(errMsg){
												childWindow.validateResult("校验失败");
											}
										}else{
											domStyle.set(query('#div_validateType_errorMsg')[0],'display','none');
											domStyle.set(query('#ifr_validate')[0],'display','block');
											var validateType = validateTypes[0];
											query('#currentValidateType')[0].value = validateType_to;
											domAttr.set(query('#ifr_validate')[0], "src", util.formatUrl(validateType_to_page, true));
										}
										
									}
									
								}
							});
							
					      });
						//取消按钮事件
						on(query('.btn-cancel')[0],touch.press, function(){
							domStyle.set(query('#validate_window')[0],'display','none');
							config.callback(false,config.params);
						});
						
						//初始化身份验证配置及验证方式
						$.ajax({
							url: Com_Parameter.ContextPath + "sys/authentication/indentity/sysIdentityValidate.do?method=config&modelPath="+config.modelPath+"&validateType="+validateType,
							type: "GET",
							dataType:"json",
							async: true,
							success: function(result){
								var errMsg = result.errMsg;
								var validateTypes = result.validateTypes;
								if(errMsg){
									if(errMsg=="don't need validate"){
										domStyle.set(query('#validate_window')[0],'display','none');
										config.callback(true,config.params);
										return;
									}
									domStyle.set(query('#ifr_validate')[0],'display','none');
									query('#span_validateType_errorMsg')[0].innerText = errMsg;
									domStyle.set(query('#div_validateType_errorMsg')[0],'display','block');
									query('#validateTypes')[0].value = "";
									query('#currentValidateType')[0].value = "";
								}else{
									domStyle.set(query('#div_validateType_errorMsg')[0],'display','none');
									domStyle.set(query('#ifr_validate')[0],'display','block');
									var validateType = validateTypes[0];
									query('#validateTypes')[0].value = JSON.stringify(validateTypes);
									query('#currentValidateType')[0].value = validateType.key;
									var iframeUrl = validateType.pageMobile;
									domAttr.set(query('#ifr_validate')[0], "src", util.formatUrl(iframeUrl, true));
									
								}
							}
						});
					},
					//构建验证窗口
					initValidateWindow:function(){
						var Width = util.getScreenSize().w;
						var Height = util.getScreenSize().h;
						var div = domConstruct.toDom('<div id="validate_window"></div>');
						domStyle.set(div,'height',Height+'px');
						domStyle.set(div,'width',Width+'px');
						
						domConstruct.place(div, document.body);
						var hiddenInput = domConstruct.toDom('<input type="hidden" id="validateTypes"><input type="hidden" id="currentValidateType">');
						domConstruct.place(hiddenInput, div);
						
						var ifr_validate = domConstruct.toDom('<iframe id="ifr_validate" class="identity_mobile" scrolling:no;"></iframe>');
						var iframeWidth = util.getScreenSize().w;
						var iframeHeight = util.getScreenSize().h - 70;
						domStyle.set(ifr_validate,'width',iframeWidth+'px');
						domStyle.set(ifr_validate,'height',iframeHeight+'px');
						domConstruct.place(ifr_validate, div);
						
						var btnBar = domConstruct.toDom('<div class="btnBar"></div>');
						domStyle.set(btnBar,'width',iframeWidth+'px');
						domConstruct.place(btnBar, div);
						
						var errorMsg = domConstruct.toDom('<div id="div_validateType_errorMsg"></div>');
						domStyle.set(errorMsg,'width',iframeWidth+'px');
						domStyle.set(errorMsg,'height',iframeHeight+'px');
						domStyle.set(errorMsg,'line-height',iframeHeight+'px');
						domConstruct.place(errorMsg, div);
						
						var errorMsgSpan = domConstruct.toDom('<span id="span_validateType_errorMsg"></span>');
						domConstruct.place(errorMsgSpan, errorMsg);
						
						var btns = domConstruct.toDom('<div class="muiVerify-btns"></div>');
						var btnConfirm = domConstruct.toDom('<span class="muiVerify-btn btn-confirm" >确定</span>');
						var btnCancel = domConstruct.toDom('<span class="muiVerify-btn btn-cancel">取消</span>');
						domConstruct.place(btnCancel, btns);
						domConstruct.place(btnConfirm, btns);
						domConstruct.place(btns, btnBar);
					}
				}
			);
	return new claz();
});