define(["mui/util"], function(util) {
	
  function portletLoad(params, load) {
    var rowsize = util.getUrlParameter(params, "rowsize");
    var type = util.getUrlParameter(params, "type");
     
    var html = '<div data-dojo-type="sys/mportal/mobile/card/JsonstoreCalendarList" class="muiPortalCalendarSimple" '
   	           +'data-dojo-mixins="sys/mportal/mobile/card/CalendarListMixin" '
   	           +"data-dojo-props=\"url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=data&fdStart=!{start}&fdEnd=!{end}&rowsize=!{rowsize}',lazy:false,rowsize:'!{rowsize}',scopeType:'!{type}'\">"
   	           +'</div>';
       
    html = util.urlResolver(html, {
        rowsize: rowsize,
        type: type,
        start: '!{start}',
        end: '!{end}'
    });
    
    load({
        html: html,
        cssUrls: []
    });  
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load);
    },
    dependences: []
  };
  
})
