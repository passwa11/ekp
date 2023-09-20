define(function(require, exports, module) {
	var listview = require('lui/listview/listview');
	var layout = require('lui/view/layout');
	var template = require('lui/listview/template');
	var source = require('lui/data/source');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');

	var MatrixDataView = listview.AbstractTable.extend(listview.ResourceTable, {
				className : 'matrixDataView',
				initProps : function($super, _cfg) {
					$super(_cfg);
					this.selected = this.config.isDefault;
					this.channel = this.config.channel;
					this.name = this.config.name;
				},

				startup : function($super) {
					if (this.isStartup) {
						return;
					}
					if (!this.source) {
						this.setSource(this.parent.source);
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
					this.element.html(data);
				},

				setTemplate : function(_template) {
					this.template = _template
				},

				setLayout : function(_layout) {
					this.layout = _layout
				},

				addChild : function($super, child) {
					if (child instanceof template.AbstractTemplate) {
						this.setTemplate(child);
					}
					if (child instanceof source.BaseSource) {
						this.setSource(child);
					}
					$super(child);
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
					var _self = this;
					setTimeout(function() {
						if (_self.template._datas) {
							_self.kvData = _self.template._datas;
						}
						
						_self.element.html(_self.layoutHtml);
						var content_table = _self.element.find("[data-lui-mark='table.content.inside']");
						content_table.css({"width": content_table.parent().width() + "px"});
						content_table.html(html);
						_self.isLoad = true;
						_self.fire({
									"name" : "load",
									'table' : _self
								});
						_self.element.find(".lui_listview_selectall").on("change", function() {
							_self.selectAll(this.checked)
						});
					}, 100);
				},
				selectAll : function(checked) {
					this.element
							.find('input[type=checkbox][data-lui-mark="table.content.checkbox"]')
							.each(function() {
								this.checked = checked;
							});
				},
				renderLoaded : function($super, datas, render) {
					var txt = this.template.html.call(this.template, datas);
					if(txt)
						this.template.fireHtml(txt);
					$super(datas, render);
				}
			});

	exports.MatrixDataView = MatrixDataView;
})