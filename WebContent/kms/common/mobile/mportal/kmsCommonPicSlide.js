define(["mui/util"], function(util) {
	function portletLoad(params, load) {
		var refreshtime = util.getUrlParameter(params, "refreshtime");
		var showSubject = util.getUrlParameter(params, "showSubject");
		var fdIds = util.getUrlParameter(params, "fdIds");


		var html = 
			'<div data-dojo-type="mui/picslide/PicSlide"' +
			'data-dojo-mixins="sys/mportal/mobile/header/PicSlideMixin"' +
			'data-dojo-props="url:\'/kms/common/kms_home_knowledge_intro_portlet/kmsHomeKnowledgeIntroPortlet.do?method=getHomeKnowledgeIntro&fdIds=!{fdIds}&orderby=docAlterTime&ordertype=down\',height:\'15rem\',width:\'100%\',picTensile:true,refreshTime:\'!{refreshtime}\',showType:\'dot\',showSubject:\'!{showSubject}\',openByProxy:true"></div>';
		
		html = util.urlResolver(html, {
			refreshtime: refreshtime,
			showSubject: showSubject,
			fdIds: fdIds
	    })
	    
	load(html)}

	return {
		load: function(params, require, load) {
		  portletLoad(params, load)
		}
	}
})
