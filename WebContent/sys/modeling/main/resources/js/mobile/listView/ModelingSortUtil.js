/**
 * 
 */
define("sys/modeling/main/resources/js/mobile/listView/ModelingSortUtil", 
		['dojo/_base/declare', 'mui/createUtils'], function(declare, createUtils) {
	
	var h = createUtils.createTemplate;
	
	var claz = declare('sys.modeling.main.resources.js.mobile.listView.ModelingSortUtil', null, {
		
		// {name：xxx,value:xxxx,sort:asc|desc}
		getSortHtml : function(item){
			return h('div', {
				dojoType: 'mui/sort/SortItem',
				dojoProps: {
					name: item.value,
					subject: item.name,
					value: item.sort === "asc" ? "up" : "down"
				}
			});
		}
	});
	return new claz();
})