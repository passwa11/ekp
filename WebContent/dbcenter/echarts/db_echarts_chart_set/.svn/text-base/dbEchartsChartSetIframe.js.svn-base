define(["mui/util"],function(util){
	function portletLoad(params,load){
		var fdId = util.getUrlParameter(params, "fdId")
		var name = util.getUrlParameter(params, "name")
		var height = util.getUrlParameter(params, "height")
		
		var html = '<div data-dojo-type="sys/mportal/mobile/extend/Mpiframe" '+
				   "data-dojo-props=\"url:'/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=view&fdId=!{fdId}&showButton=0&scroll=1&LUIID=!{lui.element.id}',name:\'!{name}\',height:\'!{height}\'\"></div>"
	    
		html = util.urlResolver(html, {
			fdId: fdId,
			name: name,
			height: height
	      })
	    load({
	      html: html
	    })	   
	}
	return{
	    load: function(params, require, load) {
	        portletLoad(params, load)
	      }
	}
})