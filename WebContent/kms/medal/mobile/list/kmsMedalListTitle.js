define([
		"dojo/_base/declare",
		"dojo/dom-construct",
		"dijit/_WidgetBase",
		"dijit/_Container" ],

function(declare, domConstruct, WidgetBase, Container) {

	return declare("kms.medal.list.title", [ WidgetBase, Container ], {

		title : null,
		fdMedalNum : null,
		zone_TA_text : null,
		buildRendering : function() {

			this.inherited(arguments);

			if (!this.title)
				return;
			
			var medalNum = Number(this.fdMedalNum);
			if(medalNum>0){
				
				var titleNode = domConstruct.create('div', {
					className : 'mui_medal_list_wrap',
				}, this.domNode, 'first');
				
				domConstruct.create('div', {
					className : 'mui_medal_banner',
					innerHTML : ('<div class="mui_medal_list_title"><p class="mui_medal_tips">'+this.zone_TA_text+'已获得<em>'+
							this.fdMedalNum +'</em>枚勋章</p><p class="mui_mdeal_emotion_tips">不错呦!</p></div>' )
				}, titleNode );

			}else{
				var titleNode = domConstruct.create('div', {
					className : 'mui_medal_list_wrap mui_medal_list_default',
				}, this.domNode, 'first');
				
				domConstruct.create('div', {
					className : 'mui_medal_banner mui_medal_banner_default',
					innerHTML : ('<div class="mui_medal_list_title mui_medal_banner_default"><p class="mui_medal_tips">'+this.zone_TA_text+'还没有勋章哦'+
							'</p><p class="mui_mdeal_emotion_tips">加油!</p></div>' )
				}, titleNode);

			}

			this.subscribe('/mui/list/loaded', 'onLoad');

		},

		// 数据为空则自我销毁
		onLoad : function(obj, data) {

			if (!obj || !data)
				return;

			if (this.getIndexOfChild(obj) < 0)
				return;

			if (data.length == 0)
				this.destroy();

		}

	});

});
