/**
 * 
 */
define(['dojo/_base/declare', 'mui/createUtils', "dojo/_base/lang"],
		function(declare, createUtils, AddressMixin, lang) {
	
	var h = createUtils.createTemplate;
	
	var claz = declare('sys.modeling.main.resources.js.mobile.homePage.custom.ModelingCustomPageUtil', null, {
		
		getPortletHtml : function(type,fdMobileId,portletInfo){
			var props = {fdMobileId:fdMobileId,portletInfo:portletInfo};
		 	if (type === 'text') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/textPortlet',
					dojoProps: props
				});
            }else if (type === 'pic') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/imagePortlet',
					dojoProps: props
				});
			}else if (type === 'chart') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/chartPortlet',
					dojoProps: props
				});
			}else if(type === "listViewWithCount"){
				 return h('div', {
					 className: 'muiHeaderItemRight',
					 dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/listViewWithCountPortlet',
					 dojoProps: props
				 });
		 	}else if (type === 'listViewWithIcon') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/listViewWithIconPortlet',
					dojoProps: props
				});
			}else if (type === 'listViewWithMultiTab') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/listViewWithMultiTabPortlet',
					dojoProps: props
				});
			}else if (type === 'listViewWithMultiTabContent') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/listViewWithMultiTabContentPortlet',
					dojoProps: props
				});
			}else if (type === 'shortcutOneRow') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/shortcutOneRowPortlet',
					dojoProps: props
				});
			}
			else if (type === 'shortcutDoubleRow') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/shortcutDoubleRowPortlet',
					dojoProps: props
				});
			}
			else if (type === 'shortcutSlideIcon') {
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/shortcutSlideIconPortlet',
					dojoProps: props
				});
			}else if(type === 'todo'){
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'sys/modeling/main/resources/js/mobile/homePage/custom/todoPortlet',
					dojoProps: props
				});
			}
		}
	});
	
	return new claz();
})