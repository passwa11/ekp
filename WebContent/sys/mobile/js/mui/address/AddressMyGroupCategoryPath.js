/**
 * 我的群组面包屑
 */
define([ 
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojo/dom-construct",
	"dojox/mobile/TransitionEvent",
	"./AddressCategoryPath",
	"mui/i18n/i18n!sys-mobile:mui"
	],function(declare, lang, domConstruct, TransitionEvent, AddressCategoryPath, Msg) {
	return declare("mui.syscategory.AddressMyGroupCategoryPath", [ AddressCategoryPath ],
		{
			
			titleNode : Msg['mui.mobile.address.myGroup'],
			
			// 我的群组面包屑：  常用群组 > 个人群组 > xx群组
			_createTitleNode: function(){
				var itemTag = domConstruct.create("div", { className: 'muiCatePathTitleItem' }, this.containerNode)
		        var textNode = domConstruct.create("div", {}, itemTag)
		        var labelNode = domConstruct.create("span", {}, textNode)
		        labelNode.innerHTML = Msg['mui.mobile.address.group.path'];
				
				// 点击常用群组，返回群组入口视图
				this.connect(itemTag, "onclick", lang.hitch(this, function() {
					var opts = {
						moveTo: 'groupView_' + this.key
					};
					new TransitionEvent(document.body ,  opts ).dispatch();
				}))
				return this.inherited(arguments);
			},
			
			_chgHeaderInfo: function(srcObj, evt){
				if(!this.isSameChannel(srcObj, evt)){
					return
				}
				if (evt) {
		            if (evt.fdId) {
		            	this._createPath([ evt ])
		            }else{
		            	this._createPath([ ]);
					}
				}
			}
		});
	});