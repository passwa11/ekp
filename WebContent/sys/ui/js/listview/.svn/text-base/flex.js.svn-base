define(function(require, exports, module) {

	var base = require('lui/base')
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	var toolbar = require('lui/toolbar');
	var popup = require('lui/popup');

	var flex = base.Container
			.extend({

				initProps : function($super, cfg) {

					$super(cfg);

					// 标题
					this.title = cfg.title;

				},

				startup : function($super) {

					$super();

					var self = this;

					var button = toolbar.buildButton({
						'order' : 1,
						'text' : this.title
					});

					this.parent.addButton(button);

					this.configNode = $('<div></div>');

					this.configNode.on('click', function(evt) {

						self.checkboxClick(evt);

					});

					// 监听列表数据，构建选项
					topic.subscribe('columntable.onload', function(data) {

						if (self.checkboxLoaded)
							return;

						var columns = data.columns;

						if (columns.length == 0) {
							return;
						} else {

							self.popDiv = $('<div>').attr('class',
									'lui_listview_flex_popup');
							self.popDiv.append(self.configNode);
							var popupObj = popup.build(button.element,
									self.popDiv, {
										"align" : "down-right"
									});

							button.addChild(popupObj);
						}

						self.buildCheckBoxs(columns);

						self.checkboxLoaded = true;

					});

				},

				buildCheckBoxs : function(columns) {

					for (var i = 0; i < columns.length; i++) {

						var column = columns[i];

						var txt = this.clearHtml(column.title);

						if (!txt)
							continue;

						if (!column.property)
							continue;

						this
								.buildCheckBox(i, txt, column.hide,
										column.property);

					}

				},

				buildCheckBox : function(value, label, hide, prop) {

					var checked = ('true' != hide) ? 'checked' : '';

					$(
							'<label><input type="checkbox" name="' + prop
									+ '" value="' + value + '" ' + checked
									+ '>' + label + '</label>').appendTo(
							this.configNode);

				},

				// 选项点击，改变列表列项
				checkboxClick : function(evt) {

					var target = $(evt.target);

					if (target.is(':checkbox')) {

						topic.publish('columntable.flex', {
							value : target.val(),
							prop : target.attr('name'),
							checked : target.is(':checked')
						});

					}

				},

				// 清楚所有html标签
				clearHtml : function(str) {

					return str.replace(/<[^>]+>/g, "");
				}

			});

	exports.flex = flex;

})