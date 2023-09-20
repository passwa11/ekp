define(
		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dijit/_WidgetBase",
				"mui/util", "mui/dialog/Tip", "dojo/html",
				"dojo/text!./cir.jsp", "dojo/query", "dojo/_base/lang",
				"dojo/dom", "dojo/_base/array", "dojo/request", "dojo/topic",
				"dijit/registry", "dojo/window", "mui/form/validate/Reminder","mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile" ],
		function(declare, domConstruct, domClass, domStyle, domAttr,
				widgetBase, util, tip, html, tmpl, query, lang, dom, array,
				request, topic, registry, win, Reminder,Msg) {
			return declare('sys.circulation.Cir',[ widgetBase ],{

						mask : null,

						// 限制字数，默认500
						limitNum : 500,

						value : 3,

						lock : false,

						submitEnableClass : 'muiCirSubmitEnable',
						
						fdModelId : '',
						
						fdModelName : '',
						
						buildRendering : function() {
							this.domNode = this.containerNode;
							this.inherited(arguments);
						},

						startup : function() {
							this.inherited(arguments);
							this.connect(this.domNode, "onclick", 'cirClick');
							topic.publish('/sys/circulation/loaded', [ this ]);
						},

						// 伪输入框点击事件
						cirClick : function(evt) {
							this.defer(this.showMask, 350);
						},

						bindInput : function() {
							var input = this.inputNode[0];
							this.connect(input, "onfocus", 'doFocus');
							this.connect(input, "onblur", 'doBlur');
							this.connect(input, "onkeyup", 'doKeyup');
							this.connect(input, "oninput", 'doInput');
						},

						bindSubmit : function() {
							this.submitHandle = this.connect(this.submitNode[0], "onclick", 'doSubmit');
						},

						bindHideMask : function() {
							this.hideMaskNode.on('click', lang.hitch(this, this.hideMask));
						},

						buildMask : function() {

							if (this.container) {
								this._showMask();
								return;
							}

							var dhs = new html._ContentSetter({
								parseContent : true,
								onBegin : function() {
									this.content = this.content.replace('!{starklass}','mui/rating/Rating');
									this.inherited("onBegin", arguments);
								}
							});
							this.container = domConstruct.create('div', {
								'className' : 'muiCirMask'
							}, document.body, 'last');

							dhs.node = this.container;
							dhs.set(tmpl);
							dhs.tearDown();
							var self = this;
							setTimeout(function() {
								self.ulContainer = query('ul', self.container);
								self._showMask();
							}, 1);
							// 提交按钮
							this.submitNode = query('.muiCirSubmit',this.container);
							this.bindSubmit();
							// 绑定输入框相关事件
							this.inputNode = query('.muiCirMaskText',this.container);
									
							this.bindInput();
							//this.setInputValue(this.value);

							// 绑定收起按钮（收起按钮）
							this.hideMaskNode = query('.muiCirHideMask', this.container);
							this.connect(this.container, 'onclick', this.hideNode);
							
							this.bindHideMask();

							// 组织架构选择框值发生改变
							this.subscribe("/mui/Category/valueChange", lang.hitch(this, this.addressChange));
									
						},
						
						hideNode : function(evt) {
							// 点击下方黑色空白区域时关闭窗口
							if("muiCirMask" == evt.target.className) {
								this.hideMask(1);//这里传个1进去目的是不清除已经填写的内容
							}
						},

						addressChange : function(evt) {
							this.validate();
						},
						
						_showMask : function() {
							domStyle.set(this.container, {
								'display' : 'block',
								'opacity' : 1
							});
							this.defer(
									function() {
									/*	domStyle.set(this.domNode, {
											'display' : 'none'
										});*/
										this.ulContainer.style({
											'-webkit-transform' : 'translate3d(0px, 0px, 0px)',
											'opacity' : 1
										});
									}, 200);
						},

						_hideMask : function(fire) {
							this.ulContainer.style({
									'-webkit-transform' : 'translate3d(0px, 400%, 0px)',
									'opacity' : 0
								});
							this.defer(function() {
								domStyle.set(this.container, {
									'opacity' : 0
								});
								domStyle.set(this.domNode, {
									'display' : 'block'
								});
								if (!fire) {
									// 重置相关表单值
									this.resetForm();
								}
							}, 500);
							this.defer(function() {
								domStyle.set(this.container, {'display' : 'none'});
							}, 700);
						},

						// 重置相关表单
						resetForm : function() {
							query('input[name="receivedCirCulatorNames"]',this.container)[0].value = '';
							query('input[name="receivedCirCulatorIds"]',this.container)[0].value = '';
							this.inputNode[0].value = '';
						},

						destroyMask : function(fire) {
							if (!this.ulContainer)
								return;
							this._hideMask(fire);
						},

						// 显示遮罩
						showMask : function() {
							if (this.lock == true)
								return;
							this.set('lock', true);
							this.defer(function() {
								this.buildMask();
							}, 350);
						},
						// 隐藏遮罩
						hideMask : function(fire) {
							this.defer(function() {
								this.destroyMask(fire);
								this.set('lock', false);
							}, 350);
						},

						doFocus : function() {
							this.validate();
						},

						doBlur : function() {
							this.validate();
						},

						doKeyup : function() {
							this.validate();
						},

						doInput : function() {
							this.validate();
						},

						validate : function() {
							if (this._validate()) {
								this.submitNode.addClass(this.submitEnableClass);
							} else {
								this.submitNode.removeClass(this.submitEnableClass);
							}
						},

						// 隐藏域，提交会对其进行封装
						hideInput : ['receivedCirCulatorNames','receivedCirCulatorIds' ],
						// 传阅提交
						doSubmit : function(evt) {
							if (!this._validate())
								return;
							var fromData = {};
							var self = this;
							// 封装隐藏域数据
							array.forEach(query("#cir_formData input"),function(domNode) {
								fromData[domAttr.get(domNode,"name")] = domNode.value;
							});

							fromData['fdRemark'] = this.inputNode[0].value;

							array.forEach(this.hideInput, function(item) {
								fromData[item] = query('input[name="' + item+ '"]', this.container)[0].value;
							});

							var promise = request.post(
									util.formatUrl("/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=save&mobile=true"),
									{data : fromData,timeout : 30000}
							).response.then(function(data) {
							
							   if (data.status == 200 && data.getHeader("lui-status") == "true") {
									tip.success({text : Msg['sysCirculationMain.mobile.submit.suc']});
									self.defer(function() {
										topic.publish("/mui/list/onPull",registry.byId('cir_scollView'));
										topic.publish("/mui/category/clear",{key : 'receivedCirCulatorIds'});
									}, 300);
								} else{
									tip.fail({text : Msg['sysCirculationMain.mobile.submit.err']});
								}
							  
							});
							this.hideMask();
						},

						// 校验
						_validate : function() {
							var proto = this.constructor.prototype;
							var flag = true;
							for (name in proto) {
								if (/^_validate[A-Z](.*)$/.test(name)) {
									flag = proto[name].call(this);
									if (!flag){
										return false;
									}
								}
							}
							return flag;
						},

						_validateInput : function() {
							var flag = false;
							var val = this.inputNode[0].value.trim();
							var leng = val.length;
							var l = 0;
							for (var i = 0; i < leng; i++) {
								if (val[i].charCodeAt(i) >=0 || val[i].charCodeAt(i) <= 128){
									l += 1;
								}else{
									l += 3;
								}
							}
							if (l <= this.limitNum && l >= 0){
								flag = true;
							}
							return flag;
						},
						
						
						reminder : null,
						
						_validateAddress : function() {
							var val = query('[name="receivedCirCulatorIds"]')[0].value.trim();
							var self = this;
							if (val) {
								var rtn =  true;
								//取消移动端不能传阅同一个人的限制，保持跟pc端一致
								/*request.post(
									util.formatUrl("/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=cirGoalCheck"), {
									data : {
										fdCirculations : val,
										fdModelName : self.fdModelName,
										fdModelId : self.fdModelId
									},
									sync : true,
									handleAs : 'json'
								}).then(function(data) {
									if(data.isExist) {
										rtn = false;
									    self.showMsg();
									} else {
										self.hideMsg();
									}
								});*/
								return rtn;
							} else {
								self.hideMsg();
								return false;
							}
						},
						
						showMsg : function() {
							if(!this.reminder) {
								this.reminder = new Reminder(document.getElementById("_cir_address"), Msg["sysCirculationMain.mobile.err.same"]);
							}
							this.reminder.show();
						},
						
						hideMsg : function() {
							if(!this.reminder){
								return;
							} 
							this.reminder.hide();
						}

					})
		});