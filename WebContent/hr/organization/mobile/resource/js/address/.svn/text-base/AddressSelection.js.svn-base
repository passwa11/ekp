define([ "dojo/_base/declare", "mui/iconUtils",
				"mui/category/CategorySelection" , "mui/util", "dojo/dom-construct", "dojo/dom-class"],
		function(declare, iconUtils, CategorySelection, util, domConstruct, domClass) {
			var selection = declare("mui.address.AddressSelection",[ CategorySelection ],{

				//获取详细信息地址
				detailUrl : '/sys/organization/mobile/address.do?method=detailList&orgIds=!{curIds}',

				buildIcon : function(selDom, item) {

					var iconArea = domConstruct.create("div", {
						className : "muiCateSecItemIcon"
					}, selDom);
					var iconNode = domConstruct.create("div", {
						className : "muiCateIcon"
					}, iconArea);
					
					var isPngIcon = false;
					if((item.type | window.ORG_TYPE_ORG) ==  window.ORG_TYPE_ORG){
						item.icon = "address-org";
						isPngIcon = true;
					}
					if((item.type | window.ORG_TYPE_DEPT) ==  window.ORG_TYPE_DEPT){
						item.icon = "address-dept"; 
						isPngIcon = true;
					}
					if((item.type | window.ORG_TYPE_POST) ==  window.ORG_TYPE_POST){
						item.icon = "address-post"; 
						isPngIcon = true;
					}
					if((item.type | window.ORG_TYPE_GROUP) ==  window.ORG_TYPE_GROUP){
						item.icon = "address-group"; 
						isPngIcon = true;
					}
					if(isPngIcon){
						this.buildPNGIcon(item.icon, iconNode);
					}else if (item.icon) {
						iconUtils.setIcon(util.formatUrl(item.icon), null, null, null,
								iconNode);
					}
				},

				buildPNGIcon: function(pngIcon, container){
					domConstruct.create('img', {
						src: util.formatUrl('/sys/mobile/css/themes/default/images/' + pngIcon + '.png')
					}, container);
				},
				
				buildDesc: function(leftArea, item){
					domConstruct.empty(leftArea);
					domClass.add(leftArea, 'muiAddressSelectionItem');
					var titleNode = domConstruct.create('div', {
						className: 'muiAddressSelectionItemName'
					},leftArea);
					domConstruct.create('span',{
						className: 'muiAddressSelectionItemLabelName',
						innerHTML :  util.formatText(item.label)
					},titleNode);
					if(item.staffingLevel){
						domConstruct.create('span',{
							className: 'muiAddressSelectionItemLevelName',
							innerHTML :  util.formatText(item.staffingLevel)
						},titleNode);
					}
					if(item.parentNames){
						domClass.add(leftArea,'muiAddressSelectionItemWithParentNames');
						domConstruct.create('div',{
							className: 'muiAddressSelectionItemParentNames',
							innerHTML: this._formatParentNames(util.formatText(item.parentNames))
						},leftArea);
					}
				},

				_formatParentNames: function(parentNames){
					if(!parentNames){
						return parentNames;
					}
					var formatParentNames = '';
					var parents = parentNames.split('_');
					var length = parents.length;
					var startIndex = length <= 3 ? 0 : length - 3;
					for(var i = startIndex; i < length; i++){
						var name = parents[i];
						if(name.length > 8){
							name = '...' + name.substring(name.length - 8);
						}
						formatParentNames += name + '_';
					}
					formatParentNames = formatParentNames.replace(/_$/,'');
					return formatParentNames;
				}
				
			});
			return selection;
		});