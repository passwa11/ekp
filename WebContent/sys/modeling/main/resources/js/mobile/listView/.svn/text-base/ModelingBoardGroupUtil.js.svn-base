/**
 * 
 */
define("sys/modeling/main/resources/js/mobile/listView/ModelingBoardGroupUtil",
		['dojo/_base/declare', 'mui/createUtils'], function(declare, createUtils) {
	
	var h = createUtils.createTemplate;
	
	var claz = declare('sys.modeling.main.resources.js.mobile.listView.ModelingBoardGroupUtil', null, {
		
		getBoardGroupHtml : function(item){
			return h('div', {
				dojoType: 'sys/modeling/main/resources/js/mobile/listView/ModelingSelect',
				dojoProps: {
					data:item.groupInfo,
					value:item.groupField
				}
			});
		}
	});
	return new claz();
})