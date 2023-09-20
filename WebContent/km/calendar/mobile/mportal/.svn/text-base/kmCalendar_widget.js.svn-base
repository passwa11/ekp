define(
		[ "mui/util" ],
		function(util) {
			function portletLoad(params, load) {
				var displayType = util.getUrlParameter(params, "displayType");
				var rowsize = util.getUrlParameter(params, "rowsize");
				
				// 门户专用
				var moreUrl = '/km/calendar/mobile';

				var html = '<div class="mPortalCalendarView" data-dojo-type="km/calendar/mobile/mportal/js/CalendarView" data-dojo-props="defaultDisplay:\'!{displayType}\'">'
						 + '	<div data-dojo-type="mui/calendar/CalendarHeader" data-dojo-props="showDatePicker:false,moreUrl:\'!{moreUrl}\'"></div>'
						 + '	<div data-dojo-type="mui/calendar/CalendarWeek"></div>'
						 + '	<div data-dojo-type="mui/calendar/CalendarContent"></div>'
						 + '	<div class="muiCalendarBottom" data-dojo-type="mui/calendar/CalendarBottom" data-dojo-props="isPortal:true,url:\'/km/calendar/km_calendar_main/kmCalendarMain.do?method=addEvent\',resize:function(){}">'
						 + '		<div class="CalendarListView" data-dojo-type="dojox/mobile/View">'
						 + '			<ul class="mui_ekp_portal_date_datails" data-dojo-type="mui/calendar/CalendarJsonStoreList" '
						 + '				data-dojo-mixins="km/calendar/mobile/mportal/js/CalendarItemListMixin" '
						 + "				data-dojo-props=\"url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=data&fdStart=!{fdStart}&fdEnd=!{fdEnd}',rowsize:'!{rowsize}'\"></ul>"
						 + '		</div>'
						 + '	</div>'
						 + '</div>';

				html = util.urlResolver(html, {
					displayType : displayType,
					rowsize : rowsize,
					moreUrl : moreUrl,
					fdStart : "!{fdStart}",
					fdEnd : "!{fdEnd}"
				});
				load({html: html, cssUrls: ["/sys/mobile/css/themes/default/calendar.css"]});
			}

			return {
				load : function(params, require, load) {
					portletLoad(params, load);
				},
				dependences: [
						"/km/calendar/mobile/mportal/js/CalendarView.js",
						"/km/calendar/mobile/resource/js/list/CalendarItemListMixin.js",
						"/km/calendar/mobile/resource/js/list/item/CalendarItemMixin.js",
						"/km/calendar/mobile/mportal/js/CalendarItemListMixin.js",
						"/sys/mobile/js/mui/calendar/CalendarUtil.js",
						"/sys/mobile/js/mui/calendar/termCalendar.js",
						"/sys/mobile/js/mui/calendar/CalendarView.js",
						"/sys/mobile/js/mui/calendar/CalendarContent.js",
						"/sys/mobile/js/mui/calendar/CalendarWeek.js",
						"/sys/mobile/js/mui/calendar/CalendarHeader.js",
						"/sys/mobile/js/mui/calendar/CalendarBottom.js",
						"/sys/mobile/js/mui/calendar/CalendarJsonStoreList.js",
						"/sys/mobile/js/mui/calendar/base/CalendarBase.js",
						"/sys/mobile/js/mui/calendar/_HeaderExternalViewMixin.js",
						"/sys/mobile/js/mui/calendar/base/CalendarScrollable.js",
						"/sys/mobile/js/mui/calendar/_BottomEventMixin.js",
						"/sys/mobile/js/mui/calendar/_ContentEventMixin.js"
				]
			}
		})
