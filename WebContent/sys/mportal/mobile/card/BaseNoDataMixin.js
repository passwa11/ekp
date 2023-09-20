define([ 'dojo/_base/declare', 'dojo/topic', 'dojo/query', 
         'dojo/dom-style', 'dojo/dom-construct', 'dojo/dom-class',
         'mui/list/item/_TemplateItemMixin', 'mui/i18n/i18n!sys-mobile', 
         'mui/util'],
	
	function(declare, topic, query, domStyle, domConstruct, domClass, TemplateItem, Msg, util) {

		return declare("sys.mportal.BaseNoDataMixin", null, {
			
			nodataImg : util.formatUrl('/sys/mportal/mobile/css/imgs/nodata.png')  ,// nodata图片(路径)
			
			nodataIcon :'mui mui-message',// nodata图标
			
			nodataText : Msg['mui.list.msg.noData'],// nodata文字
				
			buildTemplate : function(widget) {
				
				var self = widget;
				
				require(['dojo/text!mui/list/item/NoDataTempl.tmpl'], function(tmpl){
					
					self.tempItem = new TemplateItem({
						templateString : tmpl,
						baseClass:"muiListNoData",
						text:self.nodataText
					});
					
					var _container = query('.muiListNoDataContainer',self.tempItem.domNode)[0];
					
					if(self.nodataImg){
						// img形式
						domConstruct.create('img',{src:self.nodataImg},_container);
						domClass.add(_container,'muiListNoDataImg');
						
					}else{
						// icon形式
						domConstruct.create('i',{className:self.nodataIcon},_container);
						domClass.add(_container,'muiListNoDataIcon');
						
					}
					
					if(widget.addChild)
						widget.addChild(self.tempItem);
					
					widget.append = false;
					
					// 发布无数据事件
					topic.publish('/mui/list/noData',self);
					
					var parent = self.getParent();
					
					if (!parent)
						return;
					
					var offsetHeight = parent.domNode.offsetHeight;
					offsetHeight = offsetHeight<=0 ? 189:offsetHeight;//如取不到高度则默认189
					
					var h = offsetHeight - self.domNode.offsetTop;
					
					if(!self.tempItem.domNode.style['line-height'])
						domStyle.set(self.tempItem.domNode, {
							'line-height' : h + 'px'
						});
					
				});
				
			}
			
		});
	});