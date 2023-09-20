define([ "dojo/_base/declare", "dojo/_base/array", "dojo/topic", "dojo/query", "dijit/registry", "mui/util" ],
		function(declare, array, topic, query, registry, util) {

			return declare("mui.property.PropertyFilterListMixin", null, {

				commonQuery: null,
				
				buildRendering : function() {
					this.inherited(arguments);
					this.subscribe('/mui/property/filter', 'onFilter');
				},

				onFilter : function(obj, values) {
					
					var children = this.getChildren();

					// 初始化，长度为0
					if (children.length == 0)
						return;
					
					// 滚动置顶
					topic.publish('/mui/list/toTop', this);

					var _commonQuery = (values['_common'] || [])[0];
					
					array.forEach(children, function(view) {

						view.reloadTime = 0;
						
						//var child = view.getChildren()[0];
						var list = registry.getEnclosingWidget(query(
								'.mblEdgeToEdgeList', view.domNode)[0]);
						
						var container = list.getParent();
						
						var url =  container.rel.url;
						
						var paramArr = url.split('&'); 
						for(var i = 0; i < paramArr.length; i++) {
							if(paramArr[i].indexOf('q.') > -1){
								url = url.replace('&' + paramArr[i], '');
							}
						}
						
						for (var k in values) {
							if(k.indexOf('_common') > -1) {
								continue
							}
							for (var j = 0; j < values[k].length; j++) {
								url +=  "&q." + encodeURIComponent(k) + '=' + encodeURIComponent(values[k][j]);
							}
						};
						

						if(this.commonQuery) {
							url = url.replace('&' + this.commonQuery, '');
						}
						
						if(_commonQuery){
							url += '&' + _commonQuery;
						}
						
						container.rel.url = url;
						list.url = util.formatUrl(container.rel.url);
						
						if (this.currView == view) {
							list.reload();
						}

					}, this);
					
					this.commonQuery = _commonQuery;
					
				}

			});

		});