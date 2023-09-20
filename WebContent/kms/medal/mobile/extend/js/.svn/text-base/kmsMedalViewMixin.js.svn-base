define(
		[
				"dojo/_base/declare",
				"./item/kmsMedalViewItem",
				"dojo/_base/array" ],

		function(declare, item, array) {

			return declare(
					"kms.medal.view.mixin",
					null,
					{

						lazy : false,

						itemRenderer : item,

						url : '/kms/medal/kms_medal_main/kmsMedalMain.do?method=getMedal&selectType=!{selectType}&fdModelName=!{fdModelName}&fdModelId=!{fdModelId}',

						isStudent : null,
						resolveItems : function(items) {
							
							//console.log(items)
							array.forEach(items, function(item, index) {
									item.index = (index + 1);
								}, this);

							return items;
						},

					});

		});
