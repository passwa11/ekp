define(
		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dijit/_WidgetBase",
				"mui/util", "mui/dialog/Tip", "dojo/html",
				"dojo/text!./eval.jsp", "dojo/query", "dojo/_base/lang",
				"dojo/dom", "dojo/_base/array", "dojo/request", "dojo/topic",
				"dijit/registry", "dojo/window", "mui/i18n/i18n!sys-evaluation",
				"mui/form/CheckBox", "sys/attachment/mobile/js/AttachmentList",
				"dojo/text!./eval_att.jsp", "dojo/parser"],
		function(declare, domConstruct, domClass, domStyle, domAttr,
				widgetBase, util, tip, html, tmpl, query, lang, dom, array,
				request, topic, registry, win, msg, CheckBox, AttachmentList, attTmpl, parser) {

			return declare(
					'sys.evaluation.Eval',
					[ widgetBase ],
					{

						mask : null,
						
						attKeyPrefix : "eval_",
						attModelId :"",

						// 限制字数，默认1000
						limitNum : 1000,

						value : 4,

						lock : false,

						submitEnableClass : 'muiEvalSubmitEnable',

						SELECTED_EVENT : '/mui/rating/ratingSelected',
						
						fdIsCommented : false,

						// 点评等级
						grades : {
							'eval_grade_1' : msg['mui.sysEvaluation.mobile.oneStar.showText'],
							'eval_grade_2' : msg['mui.sysEvaluation.mobile.twoStar.showText'],
							'eval_grade_3' : msg['mui.sysEvaluation.mobile.threeStar.showText'],
							'eval_grade_4' : msg['mui.sysEvaluation.mobile.fourStar.showText'],
							'eval_grade_5' : msg['mui.sysEvaluation.mobile.oneStar.fiveText']
						},

						tips : {
							'eval_tip_1' : msg['mui.sysEvaluation.oneStar.showText'],
							'eval_tip_2' : msg['mui.sysEvaluation.twoStar.showText'],
							'eval_tip_3' : msg['mui.sysEvaluation.threeStar.showText'],
							'eval_tip_4' : msg['mui.sysEvaluation.fourStar.showText'],
							'eval_tip_5' : msg['mui.sysEvaluation.oneStar.fiveText']
						},

						buildRendering : function() {
							this.domNode = this.containerNode;
							this.inherited(arguments);
						},
						
						postCreate : function() {
							this.subscribe("/sys/evaluation/header/data", "headerLoaded");
						},

						startup : function() {
							this.inherited(arguments);
							this.connect(this.domNode, "onclick", 'evalClick');
							topic.publish('/sys/evaluation/loaded', [ this ]);

						},

						// 伪输入框点击事件
						evalClick : function(evt) {
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
							this.submitHandle = this.connect(
									this.submitNode[0], "onclick", 'doSubmit');
						},

						bindHideMask : function() {
							this.hideMaskNode.on('click', lang.hitch(this, this.hideMask));
						},

						buildMask : function() {
							if (this.container) {
								this._showMask();
								return;
							}
							var self = this;
							var dhs = new html._ContentSetter({
								parseContent : true,
								onBegin : function() {
									this.content = this.content
											.replace('!{starklass}',
													'mui/rating/Rating')
											.replace('!{startShow}',
													self.fdIsCommented ? "none" : "block")
											.replace('!{additionTxtShow}',
													self.fdIsCommented ? "block" : "none");
											
									if(self.notifyOtherName) {
										var otherNotifyTmp =  '<input type="checkbox" data-dojo-type="mui/form/CheckBox" data-dojo-props="showStatus:\'edit\',name:\'notifyOther\',text:\'' +  self.notifyOtherNameText + '\',value:\'' + self.notifyOtherName + '\'">';
										this.content = this.content.replace('<span id="_notifyOtherName"></span>', otherNotifyTmp);
									}
									this.inherited("onBegin", arguments);
								}
							});
							this.container = domConstruct.create('div', {
								'className' : 'muiEvalMask'
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
							this.submitNode = query('.muiEvalSubmit',
									this.container);
							this.bindSubmit();
							// 绑定输入框相关事件
							this.inputNode = query('.muiEvalMaskText',
									this.container);
							this.bindInput();
							this.setInputValue(this.value);

							// 绑定收起按钮（收起按钮）
							this.hideMaskNode = query('.muiEvalHideMask', this.container);
							this.connect(this.container, 'onclick', this.hideNode);
							
							this.bindHideMask();

							// 监听星星改变事件
							this.subscribe(this.SELECTED_EVENT, lang.hitch(
									this, this.scoreSelected));
						},
						
						hideNode : function(evt) {
							// 点击下方黑色空白区域时关闭窗口
							if("muiEvalMask" == evt.target.className) {
								this.hideMask();
							}
						},

						setInputValue : function(value) {
							var tip = this.tips['eval_tip_' + value];
							this.inputNode[0].value = tip;
							this.validate();
						},

						scoreSelected : function(evt) {
							if (evt != null) {
								var val = evt.value;
								// 设置隐藏域分数值
								query('[name="fdEvaluationScore"]')[0].value = 5 - parseInt(val);
								// 填写默认点评回复
								var inputV = this.inputNode[0].value.trim(), isdefault = this
										.isDefualt(inputV);
								if (inputV && !isdefault)
									return;
								this.setInputValue(val);
							}
						},

						// 判断是否为默认点评内容
						isDefualt : function(val) {
							var isDefault = false;
							for ( var key in this.tips) {
								if (val == this.tips[key]) {
									isDefault = true;
									break;
								}
							}
							return isDefault;
						},

						_showMask : function() {
							domStyle.set(this.container, {
								'display' : 'block',
								'opacity' : 1
							});
							this
									.defer(
											function() {
												domStyle.set(this.domNode, {
													'display' : 'none'
												});
												this.ulContainer
														.style({
															'-webkit-transform' : 'translate3d(0px, 0px, 0px)',
															'opacity' : 1
														});
												this.bulidEvalAtt();
											}, 200);
							
						},

						_hideMask : function(fire) {
							this.ulContainer
									.style({
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
									topic.publish(this.SELECTED_EVENT, {
										value : this.value
									});
									registry.byId('eval_star_opt').set('value',
											this.value);
								}
							}, 500);
							this.defer(function() {
								domStyle.set(this.container, {
									'display' : 'none'
								});
							}, 700);

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
							if (this.isDefualt(this.inputNode[0].value.trim()))
								this.inputNode[0].value = '';
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
								this.submitNode
										.addClass(this.submitEnableClass);
							} else {
								this.submitNode
										.removeClass(this.submitEnableClass);
							}
						},

						// 点评发表提交
						doSubmit : function(evt) {
							if (this.submitLock)
								return;
							if (!this._validate())
								return;
							if(this.attObj && !this.attObj.checkAttRules()) {
								return;
							}
							
							this.set('submitLock', true);
							var fromData = {};
							var self = this;
							
							// 封装隐藏域数据
							array.forEach(query("#eval_formData input"),
											function(domNode) {
												fromData[domAttr.get(domNode,
														"name")] = domNode.value;
											});
							
							var ws = registry.findWidgets(query(".muiEvalNotifyTargetBar")[0]);
							array.forEach(ws, function(widget) {
								if(widget instanceof CheckBox && widget.checked === true) {
									fromData[widget.name] = widget.value;
								}
							});
							
							array.forEach(query(".muiEvalMask input[type='hidden']"),
									function(domNode) {
										fromData[domAttr.get(domNode,
												"name")] = domNode.value;
							});
							fromData["fdId"] = this.attModelId;
							
							fromData['fdEvaluationContent'] = 
								util.formatText(this.inputNode[0].value);
							var promise = request
									.post(
											util
													.formatUrl("/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=save"),
											{
												data : fromData,
												timeout : 30000,
												handleAs: "json"
											});
							promise.response
									.then(function(data) {
										if (data.status == 200) {
											var d = data.data;
											if(d.status == true) {
												self.attModelId = data.data.newId;
												tip.success({text : msg['mui.sysEvaluation.success']});
											} else if(d.msg) {
												tip.warn({text : d.msg});
											}
											self.inputNode[0].value = "";
											// 回顶部
											topic.publish('/mui/list/toTop', this, {time : 0});
											self.defer(function() {
																topic.publish("/mui/list/onPull",
																registry.byId('eval_scollView'));
																self.set('submitLock',
																				false);
															}, 300);
											self.setFdIsCommented(true);
											self.destroyEvalAtt();
										} else
											tip.fail({text : msg['mui.sysEvaluation.fail']});
									});
							this.hideMask();
						},

						// 校验字数，
						_validate : function() {
							var flag = false;
							var val = this.inputNode[0].value.trim();
							var leng = val.length;
							var l = 0;
							for (var i = 0; i < leng; i++) {
								if (val[i].charCodeAt(0) < 299)
									l++;
								else
									l += 2;
							}
							if (l <= 2 * this.limitNum && l > 0)
								flag = true;
							
							if (flag) {
								query('#contentLength', this.container)[0].innerHTML = "";
							} else if(l > 0) {
								query('#contentLength', this.container)[0].innerHTML = Math.ceil((this.limitNum * 2 - l) / 2);
							}
							return flag;
						},
						
						setFdIsCommented : function(flag) {
							if(this.fdIsCommented  === flag) {
								return;
							}
							if(flag) {
								this.fdIsCommented = true;
								if(this.container) {
									domStyle.set(query("[data-mark='muiEvalGrade']")[0], "display" , "none");
									domStyle.set(query("[data-mark='muiEvalAdd']")[0], "display" , "block");
								}
							} else {
								this.fdIsCommented = false;
								if(this.container) {
									domStyle.set(query("[data-mark='muiEvalGrade']")[0], "display" , "block");
									domStyle.set(query("[data-mark='muiEvalAdd']")[0], "display" , "none");
								}
							}
						},
						
						headerLoaded : function(obj, evt) {
							if(evt && evt.myEvalId) {
								this.setFdIsCommented(true);
							} else {
								this.setFdIsCommented(false);
							}
						},
						
						bulidEvalAtt : function() {
							if(this.attObj) {
								return;
							}
							var self = this;
							var html = attTmpl
										.replace('!{fdModelId}', self.attModelId)
						    			.replace('!{fdKey}', self.attKeyPrefix + self.attModelId);
							var attNode = query("#_eval_att_node")[0];
							domConstruct.place(domConstruct.toDom(html), 
									attNode , "last");
							parser.parse(attNode).then(
									function(items) {
										for(var i = 0; i < items.length; i ++) {
											if(items[i] instanceof AttachmentList) {
												self.attObj = items[i];
												domClass.add(self.attObj.domNode, "muiEvalAttList");
												break;
											}
										}
									}
							);
						},
						
						destroyEvalAtt : function() {
							this.attObj && this.attObj.destroyRecursive(false);
							this.attObj = null;
						}
						
					})
		});