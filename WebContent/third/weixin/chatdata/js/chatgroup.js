define(function(require, exports, module) {
	var listview = require('lui/listview/listview');
	var layout = require('lui/view/layout');
	var template = require('lui/listview/template');
	var source = require('lui/data/source');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var env = require('lui/util/env');
	var strutil = require('lui/util/str');

	var ChatGroup = listview.AbstractTable.extend(listview.ResourceTable, {

				className : 'ChatGroup',

				initProps : function($super, _cfg) {
					$super(_cfg);
					this.selected = this.config.isDefault;
					this.channel = this.config.channel;
					this.gridHref = this.config.gridHref;
					this.target = this.config.target || '_blank';
					this.onGridClick = this.config.onGridClick;
					this.name = this.config.name;
					this.columnNum = this.config.columnNum;
				},

				startup : function($super) {
					//debugger;
					if (this.isStartup) {
						return;
					}
					if (!this.source) {
						this.setSource(this.parent.source);
					}
					// if (!this.layout) {
					// 	this.setLayout(new layout.Template({
					// 				src : require
					// 						.resolve('./grid-table-layout.html#'),
					// 				parent : this
					// 			}));
					// }
					if (!this.template) {
						this.setTemplate(new template.Template({
									src : require
											.resolve('./chatgroup-render.html#'),
									parent : this
								}));
					}

					this.on('show', this.onSourceGet, this);
					this.source.on('error', this.onError, this);
					this.source.on('data', this.onSourceLoaded, this);
					this.template.on('error', this.onError, this);
					this.template.on('html', this.doTemplate, this);
					$super();
				},

				onSourceLoaded : function(_data) {
					if (!this.isSelected()) {
						return;
					}
					this.parent._data = {};
					$.extend(true, this.parent._data, _data);

					this._data = _data;
					topic.channel(this.parent).publish('list.changed', _data);
					if (!_data.datas || _data.datas.length == 0) {
						this.noRecode();
						return;
					}
					this.template.get(_data);
				},

				noRecodeLoaded : function(data) {
					//debugger;
					var that = this;
					setTimeout(function() {
						//that.element.html(data);
						$("#ul_datas").html(data);
					},110);
				},

				setTemplate : function(_template) {
					this.template = _template;
				},

				setLayout : function(_layout) {
					this.layout = _layout
				},

				addChild : function($super, child) {
					//debugger;
					if (child instanceof template.AbstractTemplate) {
						this.setTemplate(child);
					}
					if (child instanceof source.BaseSource) {
						this.setSource(child);
					}
					$super(child);
				},

				onClick : function(evt) {
					//debugger;
					var $target = $(evt.target);
					while ($target.length > 0) {
						if ($target.attr('data-lui-mark-id')) {
							var code = '';
							var rowId = $target.attr('data-lui-mark-id');
							if (this.gridHref) {
								for (var j = 0; j < this.kvData.length; j++) {
									if (rowId === this.kvData[j]['rowId']) {
										var href = strutil.variableResolver(
												this.gridHref, this.kvData[j])
										code = ["window.open('",
												env.fn.formatUrl(href), "','",
												this.target, "')"].join('');
										break;
									}
								}

							} else if (this.onGridClick) {
								for (var j = 0; j < this.kvData.length; j++) {
									if (rowId === this.kvData[j]['rowId']) {
										code = strutil.variableResolver(
												this.onGridClick,
												this.kvData[j]);
										break;
									}
								}
							}
							new Function(code).apply(this.element.get(0));
							break;
						}
						$target = $target.parent();
					}
				},

				draw : function() {
					if (this.isDrawed)
						return;

					var self = this;
					if (this.layout) {
						this.layout.on("error", function(msg) {
									self.element.append(msg);
								});
						this.layout.get(this, function(obj) {
									self.doLayout(obj);
								});
					}
					this.element.show();
					if (this.isSelfInit())
						this.resolveUrls(this.parent.cacheEvt);
					this.isDrawed = true;
					return this;
				},

				doLayout : function($super, html) {
					this.layoutHtml = html;
					$super(html);
				},

				clearHtml : function() {
					this.element.empty();
				},

				doTemplate : function(html) {
					debugger;
					var _self = this;
					setTimeout(function() {
						if (_self.template._datas) {
							_self.kvData = _self.template._datas;
						}
						//_self.element.html(_self.layoutHtml);
						$("#ul_datas")
								.html(html);
						_self.isLoad = true;
						_self.fire({
									"name" : "load",
									'table' : _self
								});
						calcWidth();
						$(".lui_chattingRecords_item").click(function(){
							$(".lui_chattingRecords_mask").show().on("click", function(){
								$(".lui_chattingRecords_mask").hide();
								$(".lui_chattingRecords_mask_content").css("transform","translateX(100%)");
							});
							$(".lui_chattingRecords_mask_content").css("transform","translateX(0)");
						});
					}, 100);
				},

				renderLoaded : function($super, datas, render) {
					debugger;
					var txt = '';
					if (render) {
						for (var i = 0; i < datas.length; i++) {
							txt += (render).call(this.template, datas[i], i,
									'chatgroup');
						}
					} else{
						txt = this.template.html.call(this.template, datas);
					}

					this.template.fireHtml(txt);


					//this.template.fireHtml(txt);

					$super(datas, render);
				}
			});

	exports.ChatGroup = ChatGroup;
})