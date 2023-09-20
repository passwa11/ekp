define(
		[ "dojo/_base/declare", "mui/property/filter/FilterBase",
				"dojo/dom-construct", "dojo/_base/array", "dojo/topic",
				"dojo/text!./address/tmpl.tmpl", "dojo/html",
				"dojo/_base/lang", "mui/util", "dojo/request" ],
		function(declare, FilterBase, domConstruct, array, topic, tmpl, html,
				lang, util, request) {

			var claz = declare(
					"mui.property.FilterAddress",
					[ FilterBase ],
					{

						detailUrl : '/sys/organization/mobile/address.do?method=detailList&orgIds=!{curId}',

						buildRendering : function() {
							this.inherited(arguments);
							
							this.subscribe('/mui/Category/valueChange', 'onChange');
							this.subscribe('/mui/property/filter/input/change', 'reset');
						},
						
						render : function(ids, names) {
							var self = this;
							var dhs = new html._ContentSetter({
								parseContent : true,
								cleanContent : true,
								node : this.contentNode,
								onBegin : function() {
									this.content = lang.replace(this.content, {
										type : self.type,
										idField : self.name,
										nameField : self.name,
										curIds : ids || '',
										curNames : names || ''
									});
									this.inherited("onBegin", arguments);
								}
							});
							dhs.set(tmpl);
							dhs.parseDeferred.then(lang.hitch(this, function(
									parseResults) {
								self.addressWidget = parseResults[0];
							}));
							dhs.tearDown();
						},


						startup : function() {
							this.inherited(arguments);
							
							var self = this;
							this.getValue(function(_value) {
								
								var value = _value || [];
								// value约定了为数组
								value = lang.isString(value) ? [value] : value;
								if (value.length > 0 && value[0]) {
									var _url = util.urlResolver(self.detailUrl, {
										curId : value[0]
									});

									_url = util.formatUrl(_url);
									var promise = request.post(_url, {
										handleAs : 'json'
									});
									promise.then(lang.hitch(self, function(items) {
										if (items.length > 0) {
											var cateData = items[0];
											this.render(cateData.fdId,
													cateData.label);
										}
									}));
								} else {
									self.render();
								}
								
							});
						},

						onChange : function(obj, evt) {
							if (!evt || obj != this.addressWidget)
								return;

							var value = [evt.curIds];
							
							topic.publish(this.SET_EVENT, this, {
								name: this.name,
								value: value
							});
						},

						reset: function (isReset) {
							if (isReset) {
								this.render("", "");
							}
						}

					});
			return claz;
		});