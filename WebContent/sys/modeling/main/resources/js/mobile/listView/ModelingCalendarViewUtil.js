/**
 * 
 */
define(['dojo/_base/declare', 'mui/createUtils', "dojo/_base/lang","mui/util","mui/i18n/i18n!sys-modeling-main"],
		function(declare, createUtils, lang,util,modelingLang) {
	
	var h = createUtils.createTemplate;
	
	var claz = declare('sys.modeling.main.resources.js.mobile.business.ModelingCalendarViewUtil', null, {
		
		getCalendarViewHtml : function(item){
			var children = "";
			children += this.getCalendarHeader(item);
			children += this.getCalendarWeek();
			children += this.getCalendarContent();
			children += this.getCalendarMonth();
			children += this.getCalendarBottom(item);

			return h('div', {
				className: 'muiHeaderItemRight',
				dojoType: 'sys/modeling/main/resources/js/mobile/business/calendar/CalendarView',
				dojoProps: {showMode:item.showMode}
			},children);
		},
		getCalendarHeader:function (item){
			return h("div", {
				dojoType: 'mui/calendar/CalendarHeader',
				dojoMixins: 'sys/modeling/main/resources/js/mobile/business/calendar/CalendarHeaderMixin',
				dojoProps: {showMode:item.showMode}
			});
		},
		getCalendarWeek:function (){
			return h("div", {
				dojoType: 'mui/calendar/CalendarWeek'
			});
		},
		getCalendarContent:function (){
			return h("div", {
				dojoType: 'mui/calendar/CalendarContent'
			});
		},
		getCalendarMonth:function (){
			return h("div", {
				dojoType: 'sys/modeling/main/resources/js/mobile/business/calendar/CalendarMonth'
			});
		},
		getCalendarBottom:function (item){
			var children ="";
			children += this.getCalendarDropdownShade();
			children += this.getCalendarHeadTip();
			children += this.getCalendarListScrollableView(item);
			return h("div", {
				dojoType: 'sys/modeling/main/resources/js/mobile/business/calendar/CalendarBottom',
				dojoProps: {showMode:item.showMode}
			},children);
		},
		getCalendarDropdownShade:function (){
			return h("div", {
				dojoType: 'sys/modeling/main/resources/js/mobile/business/calendar/CalendarDropdownShade',
				className: "calendarDropdownShade"
			});
		},
		getCalendarHeadTip:function (){
			return h("div", {
				dojoType: 'sys/modeling/main/resources/js/mobile/business/calendar/CalendarHeadTip',
				className: "muiAttendBussTip"
			});
		},
		getCalendarListScrollableView:function (item){
			var children ="";
			children += this.getGroupJsonStoreList(item);
			return h("div", {
				dojoType: 'mui/calendar/CalendarListScrollableView',
				className: "CalendarListView muiSignInPanelBody"
			},children);
		},
		getGroupJsonStoreList:function (item){
			var url = '/sys/modeling/main/mobile/calendar.do?method=indexData&modelId=!{modelId}&businessId=!{businessId}&type=calendar&fdStart=!{fdStart}&fdEnd=!{fdEnd}';
			var props = {
				noSetLineHeight:true,
				nodataText:modelingLang['mui.modeling.no.schedule'],
				nodataImg:util.formatUrl('/sys/modeling/main/resources/images/nodata@2x.png'),
				url:util.urlResolver(url,{modelId:item.appModelId,businessId:item.listViewsId})
			}
			return h("ul", {
				dojoType: 'sys/modeling/main/resources/js/mobile/business/calendar/GroupJsonStoreList',
				className: "muiEkpSubClockInSchedule",
				id:"modelCalendarList",
				dojoMixins: "sys/modeling/main/resources/js/mobile/business/calendar/GroupItemListMixin",
				dojoProps:props
			});
		}
	});
	
	return new claz();
})