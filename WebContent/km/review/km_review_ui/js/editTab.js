//流程暂存编辑时判断表单和流程模板是否有更新版本，模板有版本更新则进行实例的版本更新操作
Com_AddEventListener(window, "load", function() {
	updateReviewProcessToFinalVersion();
})
function updateReviewProcessToFinalVersion(){
	var docStatus = $("#__docStatus").val();
	var method = $("#__method").val();
	var fdReviewId = $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
	var fdTemplateId = $("[name='fdTemplateId']")[0].value;
	var data = {"processId":$("[name='sysWfBusinessForm.fdProcessId']")[0].value};
	if(method == "edit" && docStatus == "10"){//编辑页面下并且文档状态是草稿
		var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=checkIsDraft";
		var datas = {"fdTemplateId":fdTemplateId};
		var checkIsDefaultUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=checkIsDefaultProcess";
		var checkedPathUrl = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=checkIsNewFormVersion";
		$.ajax({
			type : "POST",
			data : datas,
			url : checkIsDefaultUrl,
			async : true,
			success : function(json){
				if(json != 3){
					$.ajax({
						type : "POST",
						data : data,
						url : checkedPathUrl,
						async : true,
						success : function(re){
							if(re == "true"){
								//当表单没有改变时继续校验流程是否做了新版本更新
								checkIsDialog(url,data);
							}else{
								seajs.use([ 'lui/jquery', 'lui/dialog','lang!sys-ui' ], function($, dialog, lang) {
									var config = {
										html:Data_GetResourceString("km-review:kmReviewMain.saveDraft.content"),
										title:Data_GetResourceString("km-review:kmReviewMain.saveDraft.title"),
										width:"400px",
										buttons:[{
											name : lang['ui.dialog.button.ok'],
											value : true,
											focus : true,
											fn : function(value, dialog) {
												checkIsChangeFilePath(fdReviewId);
												updateProcessToFinalVersion(fdReviewId);
												dialog.hide(value);
												location.reload();
											}
										}]
									};
									var dialog = dialog.confirm(config,function(value){}).on('show', function() {
										this.element.find("[data-lui-mark='dialog.nav.close']").css("display","none");
										this.element.css("position","fixed");
									});
								});
							}
						}
					});
				}else{
					checkIsDialog(url,data);
				}
			}
		});
	}
}
function checkIsDialog(url,data){
	var fdReviewId = $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
	var fdTemplateId = $("[name='fdTemplateId']")[0].value;
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		async : true,
		success : function(json){
			if(json == true || json == "true"){
				//结果为真，进行弹窗
				seajs.use([ 'lui/jquery', 'lui/dialog','lang!sys-ui' ], function($, dialog, lang) {
					var config = {
						html:Data_GetResourceString("km-review:kmReviewMain.saveDraft.content"),
						title:Data_GetResourceString("km-review:kmReviewMain.saveDraft.title"),
						width:"400px",
						buttons:[{
							name : lang['ui.dialog.button.ok'],
							value : true,
							focus : true,
							fn : function(value, dialog) {
								var updateUrl = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=checkTemplate";
								var updateData = {"fdReviewId":fdReviewId,"fdTemplateId":fdTemplateId};
								$.ajax({
									type : "POST",
									data : updateData,
									url : updateUrl,
									async : false,
									dataType : 'json',
									success:function(json){
										checkIsChangeFilePath(fdReviewId);
										updateProcessToFinalVersion(fdReviewId);
										location.reload();
									}
								});
								dialog.hide(value);
							}
						}]
					};
					var dialog = dialog.confirm(config,function(value){}).on('show', function() {
						this.element.find("[data-lui-mark='dialog.nav.close']").css("display","none");
						this.element.css("position","fixed");
					});
				});
			}
		}
	});
}
//更新流程实例的版本继承路径
function updateExtendFilePath(fdReviewId){
	var data = {"fdReviewId":fdReviewId};
	var url = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=updateExtendFilePath";
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		async : false,
		dataType : 'json',
		success:function(json){
		}
	});
}
//暂存流程时判断流程实例中extendFilePath
// 和 表单模板中 fdFormFileName 路径是否相同
function checkIsChangeFilePath(fdReviewId){
	var data = {"fdReviewId":fdReviewId};
	var url = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=isSameFilePath";
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		async : false,
		dataType : 'json',
		success:function(json){
			if(json == "0"){
				updateExtendFilePath(fdReviewId);
			}
		}
	});
}
//更新暂存的流程实例到最新的模板版本
function updateProcessToFinalVersion(fdReviewId){
	var updateUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=updateProcessToFinalVersion";
	var updateData = {"processId":fdReviewId};
	$.ajax({
		type : "POST",
		data : updateData,
		url : updateUrl,
		async : false,
		success:function(result){
		}
	});
}
