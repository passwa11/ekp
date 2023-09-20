//流程暂存编辑时判断表单和流程模板是否有更新版本，模板有版本更新则进行实例的版本更新操作
require(["dojo/request","dojo/ready","dojo/query","mui/i18n/i18n!sys-mobile","mui/i18n/i18n!km-review:kmReviewMain.saveDraft","mui/dialog/Dialog","dojo/dom-construct"], function(request,ready,query,Msg,Msg1,Dialog,domConstruct) {
	ready(function(){
		//流程暂存编辑时判断表单和流程模板是否有更新版本，模板有版本更新则进行实例的版本更新操作
		updateReviewProcessToFinalVersion();
	});
	function updateReviewProcessToFinalVersion (){
		var docStatus = query("#__docStatus").val();
		var method = query("#__method").val();
		var fdReviewId = query("[name='sysWfBusinessForm.fdProcessId']")[0].value;
		var fdTemplateId = query("[name='fdTemplateId']")[0].value;
		if(method == "edit" && docStatus == "10"){//编辑页面下并且文档状态是草稿
			var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=checkIsDraft";
			var data = {"processId":query("[name='sysWfBusinessForm.fdProcessId']")[0].value};
			var datas = {"fdTemplateId":fdTemplateId};
			var checkIsDefaultUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=checkIsDefaultProcess";
			var checkedPathUrl = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=checkIsNewFormVersion";
			request.post(checkIsDefaultUrl,{data:datas,handleAs:'json',sync:true}).then(function(result){
				if(result !=3){
					request.post(checkedPathUrl,{data:data,handleAs:'json',sync:true}).then(function(re){
						if(re == true){
							//当表单没有改变时继续校验流程是否做了新版本更新
							checkIsDialog(url,data);
						}else{
							var html = '<span style="font-size:14px">'+Msg1["kmReviewMain.saveDraft.content"]+'</span>';
							var title = Msg1['kmReviewMain.saveDraft.title'];
							var canClose = false;
							var callback = function(value,dialog){
								if(value){
									var updateUrl = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=checkTemplate";
									var updateData = {"fdReviewId":query("[name='sysWfBusinessForm.fdProcessId']")[0].value,"fdTemplateId":fdTemplateId};
									request.post(updateUrl,{data:updateData,handleAs:'json',sync:true}).then(
										function(result){
											checkIsChangeFilePath(fdReviewId);
											updateProcessToFinalVersion(fdReviewId);
										},
										function(e){
										}
									);
								}
							};
							var dialogCallBack = function(){
								callback(true);
							}
							var contentNode = domConstruct.create('div', {
								className : 'muiConfirmDialogElement',
								innerHTML : '<div>' + html + '</div>'
							});
							var options = {
								'title' : title,
								'showClass' : 'muiConfirmDialogShow',
								'element' : contentNode,
								'scrollable' : false,
								'parseable' : false,
								'canClose' : false,
								'callback' : dialogCallBack,
								'buttons' : [{
									title : '<span style="font-size:1.6rem">' + Msg["mui.button.ok"] + '</span>',
									fn : function(dialog) {
										dialog.hide();
										callback(true, dialog);
										window.location.reload();
									}
								} ]
							};
							var dialog = Dialog.element(options);
						}
					});
				}else{
					checkIsDialog(url,data);
				}
			});

		}
	}
	function checkIsDialog(url,data){
		var fdReviewId = query("[name='sysWfBusinessForm.fdProcessId']")[0].value;
		var fdTemplateId = query("[name='fdTemplateId']")[0].value;
		request.post(url,{data:data,handleAs:'json',sync:true}).then(
			function(res){
				//成功后回调
				if(res == true || res == "true"){
					var html = '<span style="font-size:14px">'+Msg1["kmReviewMain.saveDraft.content"]+'</span>';
					var title = Msg1['kmReviewMain.saveDraft.title'];
					var canClose = false;
					var callback = function(value,dialog){
						if(value){
							var updateUrl = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=checkTemplate";
							var updateData = {"fdReviewId":query("[name='sysWfBusinessForm.fdProcessId']")[0].value,"fdTemplateId":fdTemplateId};
							request.post(updateUrl,{data:updateData,handleAs:'json',sync:true}).then(
								function(result){
									checkIsChangeFilePath(fdReviewId);
									updateProcessToFinalVersion(fdReviewId);
								},
								function(e){
								}
							);
						}
					};
					var dialogCallBack = function(){
						callback(true);
					}
					var contentNode = domConstruct.create('div', {
						className : 'muiConfirmDialogElement',
						innerHTML : '<div>' + html + '</div>'
					});
					var options = {
						'title' : title,
						'showClass' : 'muiConfirmDialogShow',
						'element' : contentNode,
						'scrollable' : false,
						'parseable' : false,
						'canClose' : false,
						'callback' : dialogCallBack,
						'buttons' : [{
							title : '<span style="font-size:1.6rem">' + Msg["mui.button.ok"] + '</span>',
							fn : function(dialog) {
								dialog.hide();
								callback(true, dialog);
								window.location.reload();
							}
						} ]
					};
					var dialog = Dialog.element(options);
				}
			},function(error){
				//错误回调
			});
	}
	//更新流程实例的版本继承路径
	function updateExtendFilePath(fdReviewId){
		var data = {"fdReviewId":fdReviewId};
		var url = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=updateExtendFilePath";
		request.post(url,{data:data,handleAs:'json',sync:true}).then(
			function(res){

			},function(error){
				//错误回调
			});
	}
	//暂存流程时判断流程实例中extendFilePath
	// 和 表单模板中 fdFormFileName 路径是否相同
	function checkIsChangeFilePath(fdReviewId){
		var data = {"fdReviewId":fdReviewId};
		var url = Com_Parameter.ContextPath + "km/review/km_review_main/kmReviewMain.do?method=isSameFilePath";
		request.post(url,{data:data,handleAs:'json',sync:true}).then(
			function(res){
				if(res == "0"){
					updateExtendFilePath(fdReviewId);
				}
			},function(error){
				//错误回调
			});
	}
	//更新暂存的流程实例到最新的模板版本
	function updateProcessToFinalVersion(fdReviewId){
		var updateUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=updateProcessToFinalVersion";
		var updateData = {"processId":fdReviewId};
		request.post(updateUrl,{data:updateData,handleAs:'json',sync:true}).then(
			function(res){

			},function(error){
				//错误回调
			});
	}
});