define(
		[
				"dojo/_base/declare",
				"./item/kmsMedalListItem",
				"dojo/_base/array" ],

		function(declare, item, array) {

			return declare(
					"kms.medal.list.mixin",
					null,
					{

						lazy : false,

						itemRenderer : item,

						url : '/kms/medal/kms_medal_main/kmsMedalMain.do?method=medals&personId=!{personId}',

						detailUrl:'',
						resolveItems : function(items) {
							
						

							return items;
						},

					});

		});
