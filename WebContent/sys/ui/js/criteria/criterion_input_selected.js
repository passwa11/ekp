define(function(require, exports, module) {

	var criterion = require('lui/criteria/criterion');
	var select_panel = require('lui/criteria/select_panel');
	var lang = require('lang!sys-ui');
	var render = require('lui/view/render');
	var source = require('lui/data/source');
	var $ = require('lui/jquery');

	var Criterion = criterion.Criterion.extend({
		initProps : function($super, cfg) {
			cfg.style+="min-width:200px;"
			$super(cfg);
			this.immediately = true;
		},
		doLayout : function() {

			this.selectBox.setParentNode(this.element);
			this.selectBox.draw();
			if (this.expand) {
				this.__move(this);
			}
		},
		__move : function(klass) {

			var pp = klass;
			this.timeout = setTimeout(function() {

				var p = pp;
				while (p) {
					if ('criteria' == p.className
							&& p.element.find('.criterion-other').length > 0) {
						clearTimeout(pp.timeout);
						p.element.find('.criterion-other').append(pp.element);
						pp.element.show();
						return;
					}
					p = p.parent;
				}

				pp.__move(pp);
			}, 100);
		}

	});

	var CriterionSelectBox = criterion.CriterionSelectBox.extend({
		className : "criterion-box-selected",

		draw : function($super) {

			if (this.isDrawed)
				return;
			$super();
			if (this.criterionSelectElement) {
				this.criterionSelectElement.setParentNode(this.element);
				this.criterionSelectElement.draw();
			}
			this.element.show();
		}
	});

	var TextInput = select_panel.CriterionInputDatas
			.extend({
				criteria_selected : false,

				// 当前是否显示title
				showTitle : false,

				initProps : function($super, cfg) {

					this.mutil = false;
					$super(cfg);
					if (Com_Parameter.Lang == 'en-us') {
						this.placeholder = cfg.placeholder ? lang['ui.criteria.insert']
								+ "&nbsp;" + cfg.placeholder
								: lang['ui.criteria.insert'];
					} else {
						this.placeholder = cfg.placeholder ? lang['ui.criteria.insert']
								+ cfg.placeholder
								: lang['ui.criteria.insert'];
					}
				},
				supportMulti : function() {

					return false;
				},
				setMulti : function(m) {

					this.mutil = false;
				},
				startup : function($super) {

					if (this.isStartup) {
						return;
					}
					if (!this.render) {
						this
								.setRender(new render.Template(
										{
											src : require
													.resolve('./template/criterion-textinput-selected-cell.jsp#'),
											parent : this
										}));
						this.render.startup();
					}
					if (!this.source) {

						this.setSource(new source.Static({
							datas : [ {
								'placeholder' : this.placeholder
							} ],
							parent : this
						}));
						if (this.source.startup)
							this.source.startup();
					}

					$super();

				},

				toCriterionWidth : function() {

					var ___e = this;
					while (___e) {
						if (___e instanceof criterion.Criterion) {
							var width = ___e.element.css('width');
							if (width)
								width = parseInt(width);
							if (width > 1)
								return width;
							break;
						}
						___e = ___e.parent;
					}
					return null;
				},

				getCriterion : function() {

					return this.parent.parent;
				},

				doRender : function($super, html) {
					
					$super(html);

					var self = this;

					this._okBtn = this.element.find('.criteria-input-ok');
					this._cancelBtn = this.element
							.find('.criteria-input-cancel');
					this._inputText = this.element.find(':text');
					
					this.limitSpan = this.element.find(".limitContent");
				
					var title = this.getCriterion().config.title;
					
					if (title) {

						var __title = title.replace(/[^\x00-\xff]/g, "***");
						this._titleNode = this.element
								.find('.criteria-input-text-title');

						// 超过15个字符时不显示
						if (__title.length < 16) {
							this._titleNode.html(title + ':');
						}
					}

					$(document.body).bind('click', function(evt) {
						
						self.onBodyClick(evt);
					});
					
					this._okBtn.bind('click', function(evt) {
						self.limitSpan.html("");
						self._inputText.css("padding-right","0");
						self.onClicked(evt);
						evt.stopPropagation();
					});
					this._cancelBtn.bind('click', function(evt) {

						self.onCancel(evt);
						evt.stopPropagation();
					});
					this._inputText.bind({
						'keyup' : function(evt) {

							self.onKeyup(evt);
						},
						'focus' : function(evt) {
							
							self.onFocus(evt);
						},
						'blur' : function(evt) {
						
							self.onBlur(evt);
						},
						'click' : function(evt) {

							evt.stopPropagation();
						}
					});
					LUI.placeholder(this.element);
				},

				triggerPlaceholder : function() {

					// 低版本浏览placeholder在数据回滚情况下提示信息不移除的问题
					if (document.documentMode != null
							&& document.documentMode < 10) {
						this.element.find('[placeholder]').trigger('change');
					}

				},

				onBodyClick : function(evt) {
					
					var sv = this.selectedValues.values;
					if (sv == null || sv.length == 0)
						return;
					if (sv[0].value) {
						this._inputText.val(sv[0].value);
						this.titleShow(true);
						this.ok2cancel();
					}
				},
				onCancel : function(evt) {

					this.remvoeValue();
					this.triggerPlaceholder();

				},
				onClicked : function(evt) {

					this.addValue();
				},

				// 显示标题
				titleShow : function(show) {

					this.triggerPlaceholder();

					if (this.showTitle == show)
						return;

					if (show) {
						this.getCriterion().element.show();
						this._titleNode.show();
						this._inputText.css('padding-left', (this._titleNode
								.width() + 4)
								+ 'px');
						if (document.documentMode == null
								|| document.documentMode >= 10) {
							this._inputText.attr('placeholder', '');
						}
					} else {
						this._titleNode.hide();
						this._inputText.css('padding-left', '0');
						this._inputText.attr('placeholder', this._inputText
								.attr('data-lui-placeholder'));
					}

					this.showTitle = show;
				},

				onBlur : function(evt) {
					
					var value = evt.target.value;
					
					if (!value) {
						this.titleShow(false);
						this.limitSpan.hide();
						this._inputText.css("padding-right","0");
					}

				},
				
				onFocus : function() {
					this.onInput();
					this.titleShow(true);
				},
				onInput:function(){
					this.limitSpan.show();
					this._inputText.css("padding-right","50px");
				
					this.limitSpan.html(this._inputText.val().length+"/700");
					var _this = this;
					$('input').on('input propertychange', function() {
						var textLen = _this._inputText.val().length;
						if(!(_this._inputText.css("padding-right")=="50px")){
							_this._inputText.css("padding-right","50px");
						}
						if(textLen<701){
							_this.limitSpan.html(textLen+"/700");
						}else{
							_this._inputText.val(_this._inputText.val().substring(0,700));
						}
					});
				},
				onKeyup : function(evt) {
					if (this.mutil) {
						this.addValue();
						return;
					}
					if (evt.keyCode == 13){
						this.addValue();
						this._inputText.css("padding-right","0");
						//alert("aa");
					}
					else{
						this.cancel2ok();
					}
						
				},

				remvoeValue : function() {
					this.limitSpan.html("");
					this.selectedValues.removeAll();
					this.cancel2ok();
				},
				addValue : function() {
					this.limitSpan.html("");
					var text = this.element.find(':text');
					var value = text.val();
					if (value == '') {
						this.selectedValues.removeAll();
						return;
					}
					this.selectedValues.set(value);
					this.ok2cancel();
				},
				ok2cancel : function() {

					this._okBtn.hide();
					this._cancelBtn.show();
				},
				cancel2ok : function() {

					this._cancelBtn.hide();
					this._okBtn.show();
				},
				onChanged : function(val) {
					var sv = this.selectedValues.values;
					var text = this.element.find(':text');
					if (sv == null || sv.length == 0) {
						text.val('');
						this.cancel2ok();
						this.titleShow(false);
						return;
					}

					if (text.val() != sv[0].value) {
						text.val(sv[0].value);
					}
					if (!sv[0].text) { // 修补文本
						sv[0].text = sv[0].value;
					}

					this.deffered = $.Deferred();
					this.titleShow(true);
					this.ok2cancel();

				},

				draw : function($super) {

					if (this.isDrawed)
						return;
					$super();
					var self = this;
					this.element.unbind('click.criteria.Select');
					this.element.bind('click.criteria.Select', function(evt) {

						self.onSelected(evt);
					});
					this.emit('draw');
				}
			});

	exports.CriterionSelectBox = CriterionSelectBox;
	exports.Criterion = Criterion;
	exports.TextInput = TextInput;
});