seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env'], function($, dialog , topic,env) {
	Com_AddEventListener(window,"load",function(){
		var fdModelName=$("input[name='fdModelName']").val();
		if(fdModelName){
			$("#_xform_fdCateId").show();
		}
	});
});