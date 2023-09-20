/**
 * 表单Item
 */
seajs.use(['lui/jquery', 'lui/dialog','lang!third-mall'], function($, dialog,lang) {
	window.buildNode = function(data){
		var node = $('<div/>').attr("class","cm-items");
		node.attr("id",'xform_id_'+data.fdId);
		node.attr("data-formid",data.fdId);
		/*var hiddenValue ='<input type="hidden" id="xform_group_ids_'+data.fdId+'" value="'+JSOn.stringify(data.xformList)+'"/>';
		node.append(hiddenValue);*/
		var innerNode = $('<div/>').attr("class", "cm-items-inner");
		node.append(innerNode);
		var version = window.parent.version;
		var context = window.parent.document;
		var createUrl = $("[name='add_url']",context).val();
		var url = "";
		//内容
		var $item = $("<div class='item-box'>").appendTo(innerNode);
		var auth = window.parent.isAuth;
		if (data.fdId != "more") {
			if (data.pic && data.pic != "") {
				$item.append('<img src="' + data.pic + '"></img>');
			} else {
				$item.append('<span class="icon-pic"></span>');
			}
			var $content = $('<div class="content">');
			$item.append($content);
			var html = [];
			html.push("<p class='cm-form-box-item-desc'>" + data.fdName + "</p>");
			html.push("<div class='cm-form-box-item-title'>");
			// html.push("<p>" + (data.fdPrice == "0" ? lang["thirdMall.free"] : ("¥" + data.fdPrice)) + "</p>");
			html.push("<div class='cm-form-box-item-data'>");
			html.push("<div class='cm-form-box-item-read'><i class='icon-view'></i><span>" + data.fdReadCount + "</span></div>")
			html.push("<div class='cm-form-box-item-download'><i class='icon-download'></i><span>" + data.fdDownloadCount + "</span></div>");
			html.push("</div>");
			html.push("</div>");
			
		    $content.append(html.join(""));
	        
			//遮罩
			var $itemMask = $('<div class="item-mask">');
			innerNode.append($itemMask);
			var $maskContent = $('<div class="mask-content"></div>');
			$itemMask.append($maskContent);
			var $preview = $("<a href='javascript:void(0);'>" + lang["thirdMall.preview"] + "</a>");
			var $use = $("<a href='javascript:void(0);'>" + lang["thirdMall.use"] + "</a>");
			$maskContent.append($preview);
			$maskContent.append($use);
			//预览

			$preview.on('click',function(){
				var previewUrl = "/third/mall/thirdMallPublic.do?method=view&fdId="+data.fdId+"&fdKeyType="+data.fdKeyType;
				dialog.iframe(previewUrl, lang["thirdMall.template.group"], function (rt) {
					if (rt && rt.result === "true") {

					}
				}, {
					width: 530, height: 550, buttons: [
						{
							name:  lang["mui.thirdMall.use"], value: true, focus: true,
							fn: function (value, _dialog) {
								batchUseGroup(data.xformList,data.fdId,_dialog);
							}
						},
						{
							name:  lang["kmReuseCommon.cancel"], value: false, styleClass: 'lui_toolbar_btn_gray',
							fn: function (value, _dialog) {
								_dialog.hide(value);
							}
						}
					]
				});

				seajs.use(['lui/jquery'],function() {
					$.ajax({
						type: "post",
						url: Com_Parameter.ContextPath+'/third/mall/thirdMallPublic.do?method=readMallLog',
						data: {fdId:data.fdId,fdKeyType:"sysXformGroup"},
						async: true,
						dataType: 'json',
						success: function (data) {

						}
					});
				});
			});
			$use.on('click',function(){
				batchUseGroup(data.xformList,data.fdId,null);

			});
		} else {
			var moreHtml = [];
			moreHtml.push("<div class='more'><span>" + lang["thirdMall.templateMore"] + "</span><span class='icon-more'></span></div>");
			$item.append(moreHtml.join(""));
			node.on('click',function(){
				if (auth == "true") {
					//跳转到云商城页面
					var $navList = LUI("navList").element;
					var thirdMallCreateParam = "&sourceFrom=Reuse&sourceKey=Reuse&type=2";
					var industryId = $navList.find("li.nav_main_selected").attr("data-industryid");
					createUrl = data.absPath + createUrl;
					url = data.url + "&industryId=" + industryId + "&sysVerId=" + version + "&createUrl=" + encodeURIComponent(createUrl + thirdMallCreateParam);
					if(Com_Parameter.dingXForm === "true") {
						//因为钉钉审批高级版后台页面最外层是moduleindex，与\sys\profile\index.jsp不同，frameWin[funcName]能找到对应方法，会导致新建模板页面在viewFrame中打开
						Com_OpenWindow(url, "_blank");
					} else {
						Com_OpenWindow(url);
					}
					window.parent.$dialog.hide();
				} else {
					seajs.use(['lui/dialog'],function(dialog){
						dialog.alert(lang["thirdMall.noAuth"]);
					});
				}
			});
		}
		return node;
	};
	var element = render.parent.element;
	$(element).html("");
	var formInfos = data;
	if(formInfos == null || formInfos.length == 0){
		done();
		noRecode($(element));
	}else{
		var ul = $(element);
		for(var i = 0; i < formInfos.length; i++){
			var formObj = formInfos[i];
			var node = buildNode(formObj);
			node.appendTo(ul);
		}
	}
	var __useIng=false;
	window.batchUseGroup = function(xfromList,fdId,_dialog){
		seajs.use(['lui/jquery','lui/dialog'],function($,pdialog) {
			if(xfromList){
				xfromList=JSON.parse(xfromList);
			}
			var auth = window.parent.isAuth;
			if (auth != "true") {
				pdialog.alert(lang["thirdMall.noAuth"]);
				return false;
			}
			if(!xfromList || xfromList.length ==0){
				seajs.use(['lui/dialog'], function (dialog) {
					pdialog.alert(lang["kmReuseCommon.tip.nohave"]);
				});
				return false;
			}
			//var insertObj=[];
			if(xfromList && xfromList.length > 0){
				var idNameMap={};
				for (var i = 0; i < xfromList.length; i++) {
					//insertObj.push(xfromList[i].xformId);
					idNameMap['xform_id'+xfromList[i].xformId]=xfromList[i].xformName;
				}
				//新建模板
				var createUrl = $("[name='add_url']",window.parent.document).val();
				var url = Com_Parameter.ContextPath + createUrl + "&sourceFrom=Reuse&sourceKey=Reuse&type=6";
				url=url.replace("method=add","method=addBatch");
				url=url+"&formSource=UseMallGroup"
				var param={
					fdThirdTemplateIds:fdId
				};
				if(__useIng){
					//避免重复提交
					return;
				}
				if(_dialog){
					_dialog.hide();
				}
				var dialogLoadding =pdialog.loading();
				__useIng =true;
				$.ajax({
					type: "post",
					url: url,
					data: param,
					async: true,
					dataType: 'json',
					success: function (data) {
						__useIng =false;
						dialogLoadding.hide();
						if(data){
							if(data.status ==-1){
								//标示完成
								if(data.ignore){
									var ignoreIds = data.ignore.split("、");
									if(ignoreIds){
										var names=[];
										for(var index in ignoreIds){
											names.push(idNameMap['xform_id'+ignoreIds[index]]);
										}
										if(names && names.length > 0){
											pdialog.alert(lang['kmReuseXform.batch.success.tip1']+names.join("、")+lang['kmReuseXform.batch.success.tip2']);
											//全部取消
											$("#allSelectBatchUse").prop("checked",false);
											$("input[name='xfromSetId']").each(function (i,e) {
												$(e).prop("checked",false);
											});
										}
									}
								}else{
									pdialog.alert(lang['thirdMall.opt.success']);
								}

							} else {
								pdialog.alert(lang['thirdMall.opt.failure']);
							}
						}
					},
					error:function(e){
						__useIng =false;
						if(dialogLoadding){
							dialogLoadding.hide();
						}
					}
				});

			}
		});
	}

	function hide() {
		var context = window.top.document;
		seajs.use(['lui/jquery'],function($){
			$("[data-lui-mark='dialog.nav.close']",context).trigger("click");
		});
	}



	function  noRecode(context) {
		seajs.use(['lui/util/env','theme!listview'],function(env,listview){
			var	__url = '/resource/jsp/listview_norecord.jsp?_='+new Date().getTime();
			$.ajax({
						url : env.fn.formatUrl(__url),
						dataType : 'text',
						success : function(data, textStatus) {
										context.html(data);
								  }
			});
		})
	};
});