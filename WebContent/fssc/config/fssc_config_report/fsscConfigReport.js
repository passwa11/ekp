seajs.use(['lui/jquery','lui/dialog','lui/util/env','lang!fssc-budget','lang!'], function($, dialog ,env,lang,commonLang) {
	
	window.FSSC_Search_Score=function(method){
    	var src = env.fn.formatUrl('/fssc/config/fssc_config_score/fsscConfigScore.do?method='+method);
    	//被点赞人
    	var fdAddScorePersonId = $('[name="fdAddScorePersonId"]').val();
    	if(fdAddScorePersonId){
    		src = Com_SetUrlParameter(src,"fdAddScorePersonId",fdAddScorePersonId);
    	}
    	//点赞日期
    	var docCreateTimeStart = $('[name="docCreateTimeStart"]').val();
    	if(docCreateTimeStart){
    		src = Com_SetUrlParameter(src,"docCreateTimeStart",docCreateTimeStart);
    	}
    	var docCreateTimeEnd = $('[name="docCreateTimeEnd"]').val();
    	if(docCreateTimeEnd){
    		src = Com_SetUrlParameter(src,"docCreateTimeEnd",docCreateTimeEnd);
    	}
    	$('#searchIframe').attr("src",src);
    }
    
    //重置
    window.FSSC_Reset=function(method){
    	$("input").attr("value","");
    }
});
