var lockQueen = [];
var curOperator = null;
function attWpsAssitHandleEditor(funcs,type) {
	var result = false;
	var params = funcs[0].OpenDoc;
	if (!params) {
		params = funcs[0].OnlineEditDoc;
	}
	var req = {
			fdId: params['ekpAttMainId'],
			modelId: params['ekpModelId'],
			modelName: params['ekpModelName'],
			fdKey: params['ekpAttMainKey'],
			userId: params['ekpUserId'],
			type: type
		};
	if("unlock" == type){
		req.lockQueen = JSON.stringify(lockQueen);
	}
	$.ajax({
		type: "post",
		url: Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=handleEditWpsOaassist",
		data: req,
		async: false,    //用同步方式
		success: function (data) {
			if (data.success) {
				result = true;
				if("unlock" == type){
					lockQueen = [];
				}
			} else {
				if (type == 'lock') {
					curOperator = data.operatorName;
				}
			}
		}
	});
	return result;
};