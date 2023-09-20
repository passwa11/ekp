define(function(require, exports, module) {
	var checkItem = {
		key : "",
		label : "检查项名称",
		result : "success/error/warning",
		submitType : "",
		showNone : false,
		showInfos : {
			pic : {
				fileList : []
			},
			link : {
				linkList : []
			},
			text : {
				content : ""
			}
		}
	}
	var checkResult = [ checkItem ];
})