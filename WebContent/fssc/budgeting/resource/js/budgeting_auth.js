Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/dialog_common','lui/util/str','lang!fssc-budgeting','lang!','lui/util/env'], function($, dialog, topic,dialogCommon,strutil,lang,comlang,env){
		//下载导入模板
		window.downTemplate=function(type){
			if("approval"==type){
				//审批权限模板
				Com_OpenWindow(env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_approval_auth/fsscBudgetingApproval.xlsx'));
			}else if("budgeting"==type){
				//编制权限模板
				Com_OpenWindow(env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.xlsx'));
			}
		};
		//导入权限数据
		window.importData = function(type){
			var url = "/fssc/budgeting/resource/jsp/fsscBudgetingAuthImport.jsp?type="+type;
			window.dia = dialog.iframe(url,lang['message.import.title'],
					function(data) {
						if(data){
							topic.publish("list.refresh");
						}
					},
					{
						height:'600',
						width:'800',
						"buttons" : [ 
										{
										name : comlang['button.cancel'],
										value : false,
										fn : function(value, dialog) {
												dialog.hide();
											}
										} 
								],
						"content" : {
									scroll :  false
							}
					});				
		};
	});
});

