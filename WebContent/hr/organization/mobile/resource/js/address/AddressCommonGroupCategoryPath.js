/**
 * 公共群组面包屑
 */
define([ 
	"dojo/_base/declare", 
	"dojo/_base/lang",
	"dojo/dom-construct",
	"dojox/mobile/TransitionEvent",
	"./AddressCategoryPath",
	"mui/util",
	"mui/i18n/i18n!sys-mobile:mui"
	],function(declare, lang, domConstruct, TransitionEvent, AddressCategoryPath, util, Msg) {
	return declare("mui.syscategory.AddressCommonGroupCategoryPath", [ AddressCategoryPath],
		{
			// 获取详细信息地址
			detailUrl : '/sys/organization/mobile/address.do?method=commonGroupPath&fdId=!{curId}&nodeType=cate',
			
			titleNode : Msg['mui.mobile.address.commonGroup'],
			
			// 公共群组面包屑：  常用群组 > 公共群组 > xx群组
			_createTitleNode: function(){
				var itemTag = domConstruct.create("div", { className:'muiCatePathTitleItem' }, this.containerNode)
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
			
			_chgUrlInfo: function(url, evt){
				if((evt.type | window.ORG_TYPE_GROUP_CATE) === window.ORG_TYPE_GROUP_CATE ){
					return util.setUrlParameter(url, 'nodeType', 'cate');
				}else{
					return util.setUrlParameter(url, 'nodeType', 'group');
				}
			}
	
		});
	});