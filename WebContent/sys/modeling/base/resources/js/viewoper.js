seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function($, dialog, topic, dialogCommon) {
	function clickOperation(url, fdDefType, listviewId, fdAppModelId){
		clickOperation(url, fdDefType, listviewId, fdAppModelId,"false","listView");
	}
	function clickOperation(url, fdDefType, listviewId, fdAppModelId,listValid,viewType){
		if (fdDefType) {
			//默认操作
			if (fdDefType == 0) {
				//新建
				addDoc();
			} else if (fdDefType == 3) {
				//编辑
				edit();
			} else if (fdDefType == 5) {
				//删除
				deleteOne();
			} else if (fdDefType == 6) {
				//批量删除
				delDoc();
			} else if (fdDefType == 7) {
				//导出
				exportResult(listviewId, fdAppModelId,viewType);
			} else if (fdDefType == 8) {
				// 打印
				printDoc();
			}  else if (fdDefType == 9) {
				// 导入
				importDoc();
			} else if(fdDefType == 10){
				//批量打印
				batchPrint();
			} else {
				console.log(fdDefType);
			}
		}else{
			//自定义拦截
			if (listValid && listValid === "true") {
				var selected=getSelected();
				if (!selected || selected.length <=0) {
					dialog.alert("请勾选需要处理的数据");
					return;
				}
			}
			//判断是否是支付场景下的操作，并解析操作配置 获取操作的支付场景
			var otherOperationPay=false;
			var fdOprId=getUrlParam("fdOprId",url);
			var fdId=getUrlParam("fdId",url);
			if (fdOprId){
				var oprUrl="/sys/modeling/main/sysModelingOperation.do?method=doCheckPayment&fdOprId="+fdOprId;
				$.ajax({
					url : handleOperUrl(oprUrl),
					type : 'post',
					async : false,
					data : $.param(buildParams(),true),
					success:function(dataOper){
						if(!dataOper || dataOper.indexOf("{") < 0){
							return;
						}
						var data = $.parseJSON(dataOper);
						otherOperationPay=data.status;
					}
				})
			}else{
				return;
			}
			if (otherOperationPay==true){
				//支付场景操作分支 统一下单 tradeType支付方式是NATIVE PC二维码支付
				var doPayUrl="/sys/modeling/main/sysModelingOperation.do?method=doPayOperation&tradeType=NATIVE&fdId="+fdId+"&fdOprId="+fdOprId;
				$.ajax({
					url : handleOperUrl(doPayUrl),
					type : 'post',
					async : false,
					success:function(json){
						//下单成功后返回二维码链接
						if(!json || json.indexOf("{") < 0){
							return;
						}
						var data = $.parseJSON(json);
						if(data.status){
							var url = "/sys/modeling/base/mechanism/paymentCode.jsp?modelName="+data.modelName+"&modelId="+data.modelId+"&fdKey="+data.fdKey+"&codeUrl="+data.codeUrl;
							dialog.iframe(url, "二维码支付",
								function (value) {
									//付款结束后，更新低代码业务字段
									var doUpdatePayInfoUrl="/sys/modeling/main/sysModelingOperation.do?method=doUpdatePayInfo&modelName="+data.modelName+"&modelId="+data.modelId+"&fdKey="+data.fdKey+"&fdOprId="+fdOprId;
									$.ajax({
										url : handleOperUrl(doUpdatePayInfoUrl),
										type : 'post',
										async : false,
										success:function(infoData){
											if(!infoData || infoData.indexOf("{") < 0){
												return;
											}
											var updata = $.parseJSON(infoData);
											dialog.alert(updata.title);
										}
									})
									setTimeout(function(){
										location.reload();
									}, 2000);

								}, {
									width: 400,
									height: 400
								});
						}else{
							dialog.alert(data.title);
							return;
						}
					}
				})

			}else{
				//通用场景操作 分支
				$.ajax({
					url : handleOperUrl(url),
					type : 'post',
					async : false,
					data : $.param(buildParams(),true),
					success : clickOperationCallback
				})
			}
		}
	}

	function clickOperationCallback(dataRaw){
		if(!dataRaw || dataRaw.indexOf("{") < 0){
			return;
		}
		var data = $.parseJSON(dataRaw);
		if(!data.status){
			dialog.result(data);
			return;
		}
		if(!data.type){
			return;
		}
		if(data.type == 'open'){
			//有视图
			Com_OpenWindow(handleOperUrl(data.url), "_blank");
		} else if (data.type == 'msg'){
			//无视图
			dialog.result(data);
			if(data.status==true){
				setTimeout(function(){
					location.reload();
				}, 2000);
			}
		} else if (data.type == 'dlg'){
			//弹框
			var param = { width : 750, height : 600 };
			if (data.param)
				param = data.param;
			dialog.iframe(data.url, data.title, function(dialogCallback){
				//回调
				clickOperationCallback(dialogCallback)
				topic.publish('list.refresh');
			}, param);
		}
	}

	/**
	 * 处理Com_OpenWindow的url
	 */
	function handleOperUrl(url){
		if(!url)
			return "/";
		if(url.indexOf("/") === 0){
			url = url.substring(1);
		}
		return Com_Parameter.ContextPath + url;
	}

	/**
	 * 调用操作时的参数
	 */
	function buildParams(){
		var params = {
			"List_Selected": getSelected()
		};
		return params;
	}

	/**
	 * 列表中所有选择的记录
	 */
	function getSelected(){
		var selected = [];
		$("input[name='List_Selected']:checked").each(function() {
			selected.push($(this).val());
		});
		return selected;
	}

	/**
	 * 获取url中的参数
	 */
	function getUrlParam(name,url) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = url.substr(1).match(reg);  //匹配目标参数
		if (r != null) return unescape(r[2]); return null; //返回参数值
	}

	window.clickOperation = clickOperation;

	window.copyFlowDoc = function(copyUrl) {
		// 检查模板表单是否有更新
		Com_OpenWindow(copyUrl, '_blank');
		return;
	};
});