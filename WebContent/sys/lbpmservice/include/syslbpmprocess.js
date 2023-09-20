Com_IncludeFile("dialog.js|formula.js");
if (this.Doc_LabelInfo) {
	Doc_LabelInfo[Doc_LabelInfo.length] = "Label_Tabel_Workflow_Info";
}
lbpm.operations.getfdUsageContent = function() {
	return $('[name=fdUsageContent]')[0];
};

lbpm.onLoadEvents.once.push( function() {
	lbpm.globals.initialContextParams();
	lbpm.globals.parseXMLObj();
	//设置节点层级，用于排序 #作者：曹映辉 #日期：2013年5月27日
	lbpm.globals.setNodeLevel();
	lbpm.globals.parseSubFormInfoObj();
	lbpm.globals.parseProcessorObj();
	lbpm.events.fireListener(lbpm.constant.EVENT_FILLLBPMOBJATTU,null);
	lbpm.globals.initialSimpleWorkflow();
	lbpm.globals.initialSimpleTagWorkflow();
	lbpm.globals.validateControlItem();
	lbpm.globals.setupAttachmentRow();
	lbpm.events.addListener(lbpm.constant.EVENT_CHANGEWORKITEM, lbpm.globals.setupAttachmentRow);
	/*#148641-流程说明开关开启，若流程说明为空，隐藏当前行，如果流程说明不为空，显示当前行-开始*/
	var flowDescription = $("#fdFlowDescription").text() || "";
	flowDescription = flowDescription.replace(/^\s*|\s*$/g, "");
	if (flowDescription == "") {
		$("#tr_process_description").hide();
	}
	/*#148641-流程说明开关开启，若流程说明为空，隐藏当前行，如果流程说明不为空，显示当前行-结束*/
});

lbpm.onLoadEvents.delay.push( function() {
	lbpm.events.addListener("updateShowLimitTimeOperation",lbpm.globals.updateShowLimitTimeOperation);
	//增加是否是KM-REVIEW业务模块请求的弹窗，如果是则不走下面的流程弹窗
	if(window.fromReview != "true") {
		//判断草稿下是否需要更新到最新模板
		lbpm.globals.updateProcessToFinalVersion();
	}
	//限时总的时间毫秒数
	lbpm.limitTotalTime = window.limitTotalTime;
	lbpm.isTimeoutTotal = window.isTimeoutTotal;
    //开启限时倒计时
    lbpm.globals.updateShowLimitTimeTotal();
    lbpm.globals.updateFdUsageContentTbWidth();
});

if(typeof LUI != "undefined"){
	LUI.ready(function(){
		if(Com_Parameter.dingXForm === "true") {
			lbpm.approveType="right";
		}
		if(lbpm.approveType!="right"){
			return;
		}
		lbpm.globals.setHIddenByRoletype();
		setTimeout(function(){
			lbpm.globals.setHIddenByRoletype();
			//节点帮助
			if($('.lui-lbpm-nodeHelp .nodeDescription').height()<75){
				$('.lui-lbpm-nodeHelp .lui-lbpm-more').removeClass('lookMore');
			}
		},500);
		//流程说明
		if($('#fdFlowDescriptionRow #fdFlowDescription').height()<75){
			$('#fdFlowDescriptionRow .lui-lbpm-more').removeClass('lookMore');
		}
		//折叠控制
		var $fold = $(".lui-lbpm-fold");
		if(lbpm.constant.ISINIT){
			$fold = $(".lui-lbpm-fold-add");
		}
		var num = 0;
		if(lbpm.drafterInfoObj && lbpm.drafterInfoObj.length>0){
			num++;
		}
		if(lbpm.authorityInfoObj && lbpm.authorityInfoObj.length>0 && lbpm.constant.DOCSTATUS!='10'){
			num++;
		}
		if(lbpm.historyhandlerInfoObj && lbpm.historyhandlerInfoObj.length>0 && lbpm.constant.DOCSTATUS!='10'){
			num++;
		}
		if(lbpm.branchAdminInfoObj && lbpm.branchAdminInfoObj.length>0 && lbpm.constant.DOCSTATUS=='20'){
			num++;
		}
		if(lbpm.allMyProcessorInfoObj && lbpm.allMyProcessorInfoObj.length>0 && lbpm.constant.DOCSTATUS!='10'){
			num++;
		}
		if($fold.length>0 && $fold.height()>10 && num>0){
			$(".lui-lbpm-foldOrUnfold-box div").click(function(e){
				if(!e.isPropagationStopped()){//确定stopPropagation是否被调用过
					if($('.lui-lbpm-foldOrUnfold-box i').hasClass('lui-lbpm-outerIconStyle')){
						$('.lui-lbpm-foldOrUnfold-box span').html(lbpm.constant.lbpmRight_fold);
						$('.lui-lbpm-foldOrUnfold-box i').removeClass('lui-lbpm-outerIconStyle');
					}else{
						$('.lui-lbpm-foldOrUnfold-box span').html(lbpm.constant.lbpmRight_Unfold);
						$('.lui-lbpm-foldOrUnfold-box i').addClass('lui-lbpm-outerIconStyle');
					}
					$fold.slideToggle();
				}
				e.stopPropagation();    //阻止冒泡
			})
			$(".lui-lbpm-foldOrUnfold-box div").click();
		}else{
			$(".lui-lbpm-foldOrUnfold").hide();
		}
	});
}

/**
 * 调整审批意见框宽度,增加最大800px限制
 */
lbpm.globals.updateFdUsageContentTbWidth = function() {
	if(lbpm.approveType != "right" && typeof LUI != "undefined"){
    	LUI.ready(function(){
    		//只控制view页面
    		if ($("#process_review_button").length > 0) {
    			var $fdUsageContentTb = $("#fdUsageContentTb");
        		var fdUsageContentTbWidth = $fdUsageContentTb.width();
        		if (fdUsageContentTbWidth > 800) {
        			$fdUsageContentTb.css("max-width","800px");
        		}
    		}
    	});
    }
}

lbpm.globals.updateShowLimitTimeTotal = function(){
	  var formatTotalTime = function(total){
	  	var data = {};
	  	var length = 2;
	  	total = total / 1000;
		// 得到天数
		var day = parseInt(total / (3600 * 24));
		data.day = day;

		// 得到小时数
		total = total - day * 3600 * 24;
		var hour = parseInt(total / 3600);
		data.hour = ('' + hour).length < length ? ((new Array(length + 1)).join('0') + hour).slice(-length) : '' + hour;

		// 得到分钟数
		total = total - hour * 3600;
		var minute = parseInt(total / 60);
		data.minute = ('' + minute).length < length ? ((new Array(length + 1)).join('0') + minute).slice(-length) : '' + minute;

		// 得到秒数
		var second = parseInt(total - minute * 60);
		data.second = ('' + second).length < length ? ((new Array(length + 1)).join('0') + second).slice(-length) : '' + second;

		return data;
	  }

	  setInterval(function(){
		  if(lbpm.limitTotalTime){
			  var totalTime = parseInt(lbpm.limitTotalTime);
			  var isTimeout = lbpm.isTimeoutTotal;
			  var title = "";
			  if(isTimeout == 'true'){//超时，时间增加
				  totalTime = totalTime + 1000;
			  }else if(isTimeout == 'false'){//限时，时间减少
				  totalTime = totalTime - 1000;
			  }
			  if(totalTime < 0){
				  totalTime = Math.abs(totalTime);
				  isTimeout = "true";//超时了
				  title = Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.timeoutRow");
			  }
			  var rtnVal = formatTotalTime(totalTime);
			  if(isTimeout == "true"){
				  title = Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.timeoutRow");
			  }else{
				  title = Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.limitTimeRow");
			  }
			  var detail = '<span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.day+'</span> '+Data_GetResourceString("sys-lbpmservice:FlowChartObject.Lang.Node.day")+' <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.hour+'</span> '+Data_GetResourceString("sys-lbpmservice:FlowChartObject.Lang.Node.hour")+' <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.minute+'</span> '+Data_GetResourceString("sys-lbpmservice:FlowChartObject.Lang.Node.minute")+' <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.second+'</span> '+Data_GetResourceString("sys-lbpmservice:FlowChartObject.Lang.Node.second");;
			  if(lbpm.approveType=="right"){//右侧模式
				 var html = title +" "+ detail;
				 $("#limitTimeRow .lui-lbpm-titleNode").html(html);
			 }else{
				 var html = '<td class="td_normal_title" width="15%">'+title+'</td>';
				 html += '<td colspan="3">'+detail+'</td>';
				 $("#limitTimeRow").html(html);
			 }
			  lbpm.limitTotalTime = totalTime;
			  lbpm.isTimeoutTotal = isTimeout;
		  }
	  }, 1000);
}

lbpm.globals.updateProcessToFinalVersion = function(){
	var docStatus = $("#__docStatus").val();
	var method = $("#__method").val();
	if(method == "edit" && docStatus == "10"){//编辑页面下并且文档状态是草稿
		var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=checkIsDraft";
		var data = {"processId":$("[name='sysWfBusinessForm.fdProcessId']")[0].value};
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
								html:Data_GetResourceString("sys-lbpmservice:lbpmservice.saveDraft.content"),
								title:Data_GetResourceString("sys-lbpmservice:lbpmservice.saveDraft.title"),
								width:"400px",
								buttons:[{
									name : lang['ui.dialog.button.ok'],
									value : true,
									focus : true,
									fn : function(value, dialog) {
										var updateUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=updateProcessToFinalVersion";
										var updateData = {"processId":$("[name='sysWfBusinessForm.fdProcessId']")[0].value};
										$.ajax({
											type : "POST",
											data : updateData,
											url : updateUrl,
											async : false,
											success:function(result){
												if(result == true || result == "true"){
													//刷新页面
													window.top.location.reload();
												}
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
}


lbpm.globals.setHIddenByRoletype = function(){
	var handlerTypeRow = $('#handlerTypeRow');
	if(handlerTypeRow.length == 0){
		return;
	}
	var saveFormData = LUI("saveFormData");
	if(lbpm.constant.ROLETYPE!='' && lbpm.constant.ROLETYPE!=lbpm.constant.PROCESSORROLETYPE){
		var isNotifyCurrentHandler = Lbpm_SettingInfo["isNotifyCurrentHandler"];
		if ((isNotifyCurrentHandler === "false" && lbpm.constant.ROLETYPE === "authority") || lbpm.constant.ROLETYPE === lbpm.constant.HISTORYHANDLERROLETYPE ||  lbpm.constant.ROLETYPE === lbpm.constant.BRANCHADMINROLETYPE){
			$("#notifyLevelRow").hide();
			$("#notifyTypeRow").hide();
		}
		$("#process_review_ui_button").addClass("lbpmImpotantHidden");
		$("#process_review_panel_button").removeClass("lbpmImpotantHidden");
		$("#optionButtons .com_btn_link").hide();
		$("#commonUsagesDefinite").show();
		$("#assignmentRow").hide();
		$("#notifyOptionTR").hide();
		$("#nextNodeTR").hide();
		$("#fdFlowDescriptionRow").hide();
		$("#operationHandlerDiv").children().appendTo($("#operationOtherDiv"));
		//$("#notifyTypeRow").show();
		$("textarea[name='fdUsageContent']").val("");
		$("#mustSignStar").hide();
		if(saveFormData){
			$("#saveFormData").addClass("lbpmImpotantHidden");
		}
		//私密意见
		$("#privateOpinionCanViewTr").hide();
		$("input[name='privateOpinion']").prop("checked", false);
		$("input[name='privateOpinionCanViewIds']").val("");
		$("input[name='privateOpinionCanViewNames']").val("").attr("validate","");
		$("#privateOpinionCanViewTr").closest("td").find(".validation-advice").hide();
		$("#privateOpinionTr").hide();
		if(lbpm.isFreeFlow){
			//$("#freeflowRow").hide();
		}
	}else{
		$("#process_review_ui_button").removeClass("lbpmImpotantHidden");
		$("#process_review_panel_button").addClass("lbpmImpotantHidden");
		$("#optionButtons .com_btn_link").show();
		if(lbpm.flowcharts && lbpm.flowcharts.description && Lbpm_SettingInfo.isShowFlowDescription == "true") {
			$("#fdFlowDescriptionRow").show();
		}
		$("#notifyTypeRow").hide();
		$("#operationOtherDiv").children().appendTo($("#operationHandlerDiv"));
		if(saveFormData){
			$("#saveFormData").removeClass("lbpmImpotantHidden");
		}
		//私密意见
		var descriptionRow = document.getElementById("descriptionRow");
		if(lbpm.isFreeFlow && Lbpm_SettingInfo.isPrivateOpinion == "true" && descriptionRow && descriptionRow.style.display != 'none'){
			$("#privateOpinionTr").show();
		}
		if(lbpm.isFreeFlow){
			//$("#freeflowRow").show();
			$("#assignmentRow").after($("#freeflowRow"));
		}
	}
	if($("#descriptionRow").is(":hidden") && !lbpm.hideDescriptionOnDraftNode){
		$("#process_review_ui_button").addClass("lbpmImpotantHidden");
		$("#process_review_panel_button").addClass("lbpmImpotantHidden");
	}
	if(lbpm.constant.DOCSTATUS=='10'){
		$("#process_review_ui_button").addClass("lbpmImpotantHidden");
		$("#process_review_panel_button").addClass("lbpmImpotantHidden");
	}
};

lbpm.globals.loadRightInfo = function(){
	if(Com_Parameter.dingXForm === "true") {
		lbpm.approveType = "right";
	}
	if(lbpm.approveType!="right" || lbpm.isLoadRightInfo){
		return;
	}
	var handlerTypeRow = $('#handlerTypeRow');
	if(handlerTypeRow.length > 0){
		var options = [];
		if(lbpm.allMyProcessorInfoObj && lbpm.allMyProcessorInfoObj.length>0 && lbpm.constant.DOCSTATUS!='10'){
			options.push({'text':lbpm.constant.lbpmRight_curHandler,value:lbpm.constant.PROCESSORROLETYPE});
		}
		if(lbpm.drafterInfoObj && lbpm.drafterInfoObj.length>0){
			options.push({'text':lbpm.constant.lbpmRight_drafter,value:lbpm.constant.DRAFTERROLETYPE});
		}
		if(lbpm.authorityInfoObj && lbpm.authorityInfoObj.length>0 && lbpm.constant.DOCSTATUS!='10'){
			options.push({'text':lbpm.constant.lbpmRight_authority,value:lbpm.constant.AUTHORITYROLETYPE});
		}
		if(lbpm.historyhandlerInfoObj && lbpm.historyhandlerInfoObj.length>0 && lbpm.constant.DOCSTATUS!='10'){
			options.push({'text':lbpm.constant.lbpmRight_hisHandler,value:lbpm.constant.HISTORYHANDLERROLETYPE});
		}
		if(lbpm.branchAdminInfoObj && lbpm.branchAdminInfoObj.length>0 && lbpm.constant.DOCSTATUS=='20'){
			options.push({'text':lbpm.constant.lbpmRight_branchadmin,value:lbpm.constant.BRANCHADMINROLETYPE});
		}

		if(options.length>0){
			handlerTypeRow.find(".lui-lbpm-detailNode").html("");
			var html = '<div class="lui_lbpm_handlerType">';
			for(var i = 0;i<options.length;i++){
				html+='<span data-value="'+options[i].value+'">'+options[i].text+'</span>';
			}
			html+='</div>';
			handlerTypeRow.find(".lui-lbpm-detailNode").append(html);
			if(lbpm.allMyProcessorInfoObj && lbpm.allMyProcessorInfoObj.length>0 && lbpm.constant.DOCSTATUS!='10'){
				$(".lui_lbpm_handlerType span[data-value="+lbpm.constant.PROCESSORROLETYPE+"]").addClass("active");
			}else{
				$(".lui_lbpm_handlerType span:eq(0)").addClass("active");
			}
			lbpm.constant.ROLETYPE = $(".lui_lbpm_handlerType span.active").attr("data-value");
			if(options.length == 1){
				lbpm.globals.hiddenObject(handlerTypeRow[0], true);
			}else{
				$(".lui_lbpm_handlerType span").css({
				    'width':100/options.length+'%'
				});
			}
		}else{
			lbpm.globals.hiddenObject(handlerTypeRow[0], true);
			$("#defaultMsgDiv").show();
		}
		$('.lui_lbpm_handlerType>span').click(function() {
			$(this).parent().find('span').removeClass('active');
			$(this).addClass('active');
			var value = $(this).attr("data-value");
			if(lbpm.constant.ROLETYPE == value){
				return;
			}
			lbpm.constant.ROLETYPE = value;
			lbpm.globals.parseProcessorObj();
			lbpm.events.fireListener(lbpm.constant.EVENT_FILLLBPMOBJATTU,null);
			lbpm.globals.initialSimpleWorkflow();
			lbpm.globals.initialSimpleTagWorkflow();
			lbpm.globals.validateControlItem();
			lbpm.globals.setupAttachmentRow();
			lbpm.globals.setHIddenByRoletype();
			lbpm.events.addListener(lbpm.constant.EVENT_CHANGEWORKITEM, lbpm.globals.setupAttachmentRow);
			if(lbpm.nowProcessorInfoObj==null) return;
			lbpm.globals.loadWorkflowInfo();
			lbpm.globals.loadDefaultParameters();
			setTimeout("lbpm.globals.getThroughNodes()",200);
			lbpm.events.fireListener(lbpm.constant.EVENT_HANDLERTYPECHANGE,value);
			if(lbpm.constant.ROLETYPE==''||lbpm.constant.ROLETYPE==lbpm.constant.PROCESSORROLETYPE){
				var group = $("#operationMethodsGroup");
				if (group.attr('view-type') == 'select') {
					// select
					var opt = group.find("option[value^='drafter_refuse_abandon']");
					group.data("drafter_refuse_abandon", opt.parent().html());
					opt.remove(); // 移除操作
				} else {
					// radio
					group.find("input[name='oprGroup'][value^='drafter_refuse_abandon']").parent("label").hide(); // 隐藏操作
				}
			}
			if(value == 'authority' || value == 'historyhandler' || value == 'drafter' || value =='branchadmin'){
				//起草、特权和已处理人操作不进行@
				var btnLen = $('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').length-1;//全部按钮

				if($(".lui-lbpm-opinion-noticeHandler").length > 0){//存在@按钮
					if($(".lui-lbpm-opinion-more").css("display") == 'none'){//没有更多
						//移除@，并更新占比宽度
						$(".lui-lbpm-opinion-noticeHandler").remove();
						$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
					        'width':100/(btnLen-1)+'%'
					    });
					}else{//存在更多
						var moreBtnLen = $(".lui-lbpm-opinion-more .lui-lbpm-opinion-btn").length;
						if(moreBtnLen <= 2){
							if($(".lui-lbpm-opinion-more .lui-lbpm-opinion-noticeHandler").length > 0){
								//@在更多中
								$(".lui-lbpm-opinion-noticeHandler").parent("li").eq(0).remove();
							}else{
								//@不在更多中
								$(".lui-lbpm-opinion-noticeHandler").remove();
							}
							var lis = $(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").find("li");
							for(var i=0; i<lis.length; i++){
								var li = lis[i];
								var html = $(li).html();
								$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").before(html);
								$(li).remove();
							}
							$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
						        'width':100/3+'%'
						    });
							$('.lui-lbpm-opinion-more').css({
								'display':'none'
							})
						}else{
							if($(".lui-lbpm-opinion-more .lui-lbpm-opinion-noticeHandler").length > 0){
								//@在更多中
								$(".lui-lbpm-opinion-noticeHandler").parent("li").eq(0).remove();
							}else{
								//@不在更多中
								$(".lui-lbpm-opinion-noticeHandler").remove();
								var li = $(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").find("li").eq(0);
								$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").before(html);
								$(li).remove();
							}
							$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
						        'width':100/3+'%'
						    });
						}
					}
				}
				var buttonLength = $('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').length-1;
				if($(".saveOpinion").length > 0){//存在暂存按钮
					lbpm.saveOpinionHtml = $(".saveOpinion").prop("outerHTML");
					if($(".lui-lbpm-opinion-more").css("display") == 'none'){//没有更多
						//移除暂存按钮，并更新占比宽度
						$(".saveOpinion").remove();
						$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
							'width':100/(buttonLength-1)+'%'
						});
					}else{//存在更多
						var moreBtnLen = $(".lui-lbpm-opinion-more .lui-lbpm-opinion-btn").length;
						if(moreBtnLen <= 2){
							if($(".lui-lbpm-opinion-more .saveOpinion").length > 0){
								//暂存在更多中
								$(".saveOpinion").parent("li").eq(0).remove();
							}else{
								//暂存不在更多中
								$(".saveOpinion").remove();
							}
							var lis = $(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").find("li");
							for(var i=0; i<lis.length; i++){
								var li = lis[i];
								var html = $(li).html();
								$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").before(html);
								$(li).remove();
							}
							$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
								'width':100/3+'%'
							});
							$('.lui-lbpm-opinion-more').css({
								'display':'none'
							})
						}else{
							if($(".lui-lbpm-opinion-more .saveOpinion").length > 0){
								//暂存在更多中
								$(".saveOpinion").parent("li").eq(0).remove();
							}else{
								//暂存不在更多中
								$(".saveOpinion").remove();
								var li = $(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").find("li").eq(0);
								$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").before(html);
								$(li).remove();
							}
							$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
								'width':100/3+'%'
							});
						}
					}
				}

			}else{
				var title = Data_GetResourceString("sys-lbpmservice:lbpm.process.noticeHandler.name");
				var html = '<div class="lui-lbpm-opinion-noticeHandler lui-lbpm-opinion-btn" title="'+title+'" onclick="lbpm.globals.selectHistoryHandlers(this,\'fdUsageContent\');">';
				var btnLen= $('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').length;
				//如果超过3个按钮，则按三个按钮计算，并把当前按钮放置到更多中
				if(btnLen-1 >= 3){
					//转移最后一个到更多中
					$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").append("<li>"+$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').eq(btnLen-2).prop("outerHTML")  + "</li>");
					$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').eq(btnLen-2).remove();

					html += title;
					html += '<input type="hidden" name="noticeHandlerIds" id="noticeHandlerIds">';
					html += '</div>';
					html = "<li>" + html + "</li>";
					$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").append(html);
					$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
				        'width':100/3+'%'
				    });
					$('.lui-lbpm-opinion-moreList .lui-lbpm-opinion-btn').css({
				        'width':'100%'
				    });
					$('.lui-lbpm-opinion-more').css({
						'display':'inline-block'
					})
				}else{
					html += '@';
					html += '<input type="hidden" name="noticeHandlerIds" id="noticeHandlerIds">';
					html += '</div>';
					//$(".lui-lbpm-opinion-otherFnc").append(html);
					$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").before(html)
					$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
				        'width':100/btnLen+'%'
				    });
				}
				// 把暂存按钮添加回来
				addSaveNote();
			}
			//清空@处理人的内容
			lbpm.globals.emptyNoticeHandlerInfo();
		});
	}
	/*if($('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-moreList ul li').length>0){
		$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more').show();
		$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more').addClass("lui-lbpm-opinion-btn");
	}else{
		$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more').hide();
		$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more').removeClass("lui-lbpm-opinion-btn");
	}*/
	//重置意见区按钮宽度
	var btnLen= $('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').length;
    $('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
      'width':100/btnLen+'%'
    });
    //常用意见
    lbpm.opinionFlag = false;
    $('.lui-lbpm-opinion-outerBox .commonUsedOpinion').mouseover(function(){
    	lbpm.opinionFlag=true;
    	lbpm.opinionTimer= setTimeout(function(){
	        if(lbpm.opinionFlag){
		        $('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').addClass('hoverStyle')
		        $('.lui-lbpm-opinion-outerBox .lui-lbpm-downIcon').addClass('lui-lbpm-foldIcon');
	        }
    	},1000);
	});
    $('.lui-lbpm-opinion-outerBox .commonUsedOpinion').click(function(){
    	var event = window.event;
		var toEle = event.toElement || event.relatedTarget || event.srcElement;
	 	if(toEle && $(toEle).closest('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').length>0){
			return;
		}
        $('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').addClass('hoverStyle')
        $('.lui-lbpm-opinion-outerBox .lui-lbpm-downIcon').addClass('lui-lbpm-foldIcon');
	});
	$('.lui-lbpm-opinion-outerBox .commonUsedOpinion').mouseout(function(){
		if(!$('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').hasClass('hoverStyle')){
			$('.lui-lbpm-opinion-outerBox .lui-lbpm-downIcon').removeClass('lui-lbpm-foldIcon');
	    }else{
	    	var event = window.event;
			var toEle = event.toElement || event.relatedTarget;
	    	if(!(toEle && $(toEle).closest('.lui-lbpm-opinion-outerBox .commonUsedOpinionList,.lui-lbpm-opinion-outerBox .commonUsedOpinion').length>0)){
	    		$('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').removeClass('hoverStyle');
	    		$('.lui-lbpm-opinion-outerBox .lui-lbpm-downIcon').removeClass('lui-lbpm-foldIcon');
	    	}
	    }
		lbpm.opinionFlag=false;
		clearTimeout(lbpm.opinionTimer);
	});
	$('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').mouseenter(function(){
		$(this).addClass('hoverStyle');
		$('.lui-lbpm-opinion-outerBox .lui-lbpm-downIcon').addClass('lui-lbpm-foldIcon');
	});
	$('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').mouseleave(function(){
		var event = window.event;
		var toEle = event.toElement || event.relatedTarget;
		if(!(toEle && $(toEle).closest('.lui-lbpm-opinion-outerBox .commonUsedOpinionList,.lui-lbpm-opinion-outerBox .commonUsedOpinion').length>0)){
    		$('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').removeClass('hoverStyle');
    		$('.lui-lbpm-opinion-outerBox .lui-lbpm-downIcon').removeClass('lui-lbpm-foldIcon');
    	}
	});
	//常用意见自定义
	$('.lui-lbpm-opinion-outerBox .commonUsedOpinionList ul').on('click','li',function(){
		$('.lui-lbpm-opinion-outerBox .commonUsedOpinionList').removeClass('hoverStyle');
		$('.lui-lbpm-opinion-outerBox .lui-lbpm-downIcon').removeClass('lui-lbpm-foldIcon');
		lbpm.globals.setUsages(this);
	});
	// 节点帮助
    $('.lui-lbpm-nodeHelp .lui-lbpm-more').click(function(){
	    $('.lui-lbpm-nodeHelp .nodeDescription').css({
	        "max-height":"none",
	    })
	    $(this).removeClass('lookMore').next().addClass('lookMore')
	})
	$('.lui-lbpm-nodeHelp .lui-lbpm-moreFold').click(function(){
	    $('.lui-lbpm-nodeHelp .nodeDescription').css({
	        "max-height":"75px",
	    })
	    $(this).removeClass('lookMore').prev().addClass('lookMore');
	})
	//流程说明
	$('#fdFlowDescriptionRow .lui-lbpm-more').click(function(){
	    $('#fdFlowDescriptionRow #fdFlowDescription').css({
	        "max-height":"none",
	    })
	    $(this).removeClass('lookMore').next().addClass('lookMore')
	})
	$('#fdFlowDescriptionRow .lui-lbpm-moreFold').click(function(){
	    $('#fdFlowDescriptionRow #fdFlowDescription').css({
	        "max-height":"75px",
	    })
	    $(this).removeClass('lookMore').prev().addClass('lookMore');
	})
	lbpm.isLoadRightInfo = true;
};
function addSaveNote(){
	var title = Data_GetResourceString("sys-lbpmservice:button.saveDraft");
	var html = lbpm.saveOpinionHtml;
	var btnLen= $('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').length;
	//如果超过3个按钮，则按三个按钮计算，并把当前按钮放置到更多中
	if(btnLen-1 >= 3){
		//转移最后一个到更多中
		$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").prepend("<li>"+$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').eq(btnLen-2).prop("outerHTML")  + "</li>");
		$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').eq(btnLen-2).remove();
		html = "<li>" + html + "</li>";
		$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").prepend(html);
		$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
			'width':100/3+'%'
		});
		$('.lui-lbpm-opinion-moreList .lui-lbpm-opinion-btn').css({
			'width':'100%'
		});
		$('.lui-lbpm-opinion-more').css({
			'display':'inline-block'
		})
	}else{
		$(".lui-lbpm-opinion-otherFnc").prepend(html)
		$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
			'width':100/btnLen+'%'
		});
	}
}

lbpm.globals.initialContextParams=function(){
	lbpm.globals.getWfBusinessFormModelName();
	lbpm.globals.getWfBusinessFormModelId();
	lbpm.globals.getWfBusinessFormFdKey();
	lbpm.globals.getWfBusinessFormDocStatus();

	lbpm.handlerId=$("[name='sysWfBusinessForm.fdCurHanderId']")[0].value;
	lbpm.draftorName=$("[name='sysWfBusinessForm.fdDraftorName']")[0].value;
};

//解析当前节点XML成对象
lbpm.globals.parseProcessorObj=function(){
	var curNodeXMLObj = WorkFlow_LoadXMLData($("input[name='sysWfBusinessForm.fdCurNodeXML']")[0].value);
	if((!curNodeXMLObj) || !curNodeXMLObj.nodes && !curNodeXMLObj.tasks) return; //流程结束
	//当前节点的详版XML
	if(curNodeXMLObj.nodes){
		for(var i=0,size=curNodeXMLObj.nodes.length;i<size;i++){
			var node = curNodeXMLObj.nodes[i];
			for(o in node){
				if(lbpm.nodes[node.id]){
					lbpm.nodes[node.id][o]=node[o];
				}
				if(lbpm.nodesInit[node.id]){
					lbpm.nodesInit[node.id][o]=node[o];
				}
			}
			if (lbpm.isSubForm) {
				if (!lbpm.nodes[node.id]["subFormId"]) {
					lbpm.nodes[node.id]["subFormId"]="default";
					lbpm.nodesInit[node.id]["subFormId"]="default";
				}
			}
		}
	}
	lbpm.drafterInfoObj=lbpm.globals.getDrafterInfoObj(curNodeXMLObj); //当前用户以起草人身份所拥有的信息
	lbpm.authorityInfoObj=lbpm.globals.getAuthorityInfoObj(curNodeXMLObj);//当前用户以特权人身份所拥有的信息
	lbpm.historyhandlerInfoObj=lbpm.globals.getHistoryhandlerInfoObj(curNodeXMLObj);//当前用户以已处理人身份所拥有的信息
	lbpm.branchAdminInfoObj = lbpm.globals.getBranchAdminInfoObj(curNodeXMLObj);//当前用户以分支特权人所拥有的信息
	var processorInfoObj = lbpm.globals.getProcessorInfoObj(curNodeXMLObj);
	//processorInfoObj，打开特权人窗口或者起草人窗口处理文档时，此对象跟authorityInfoObj或者drafterInfoObj对象一样
	lbpm.globals.loadRightInfo();
	if(lbpm.constant.ROLETYPE!='' && lbpm.constant.ROLETYPE!=lbpm.constant.PROCESSORROLETYPE){
		processorInfoObj = lbpm.globals.getProcessorInfoObj(curNodeXMLObj);
	}
	lbpm.processorInfoObj=processorInfoObj; //当前用户以处理人身份所拥有的信息，如所属哪个工作项
	//当有多个工作项时，也是默认取第一个
	if(processorInfoObj) {
		var selectedIndex = 0;
		if (processorInfoObj.length > 1 && lbpm.defaultTaskId && lbpm.defaultTaskId != "") {
			for(var i=0;i<processorInfoObj.length;i++){
				if (processorInfoObj[i].id == lbpm.defaultTaskId) {
					selectedIndex = i;
					break;
				}
			}
		}
		lbpm.nowProcessorInfoObj=processorInfoObj[selectedIndex];
		if(lbpm.nowProcessorInfoObj) {
			lbpm.nowNodeId=lbpm.nowProcessorInfoObj.nodeId;
			if (lbpm.isFreeFlow) { //自由流
				if (lbpm.nowNodeId == "N2") {
					lbpm.nowNodeFlowPopedom = "2";
				} else {
					lbpm.nowNodeFlowPopedom = lbpm.globals.getNodeObj(lbpm.nowNodeId).flowPopedom;
				}
			}
		}
	}
};

//解析当前多表单信息XML成对象
lbpm.globals.parseSubFormInfoObj = function() {
	if (lbpm.isSubForm) {
		var subFormXMLObj = WorkFlow_LoadXMLData($("input[name='sysWfBusinessForm.fdSubFormXML']")[0].value);
		if (subFormXMLObj.subforms) {
			var subformsInfoObj = $.extend(true, [], subFormXMLObj.subforms);
			lbpm.subFormInfoObj = subformsInfoObj;//当前用户所拥有的子表单信息集
		}
	}
};

lbpm.globals.normalSorter = function(node1, node2) {
	if(node1.y==node2.y)
		return node1.x - node2.x;
	return node1.y - node2.y;
};
//内部方法外部切勿调用 #作者：曹映辉 #日期：2013年5月27日 
lbpm.globals._levelCalc=function(startNodeId,level,allNodes){
	if(!allNodes){
		allNodes=[];
	}
	if(!level){
		level=0;
	}

	level++;
	var nodeObj=lbpm.nodes[startNodeId];
	if(!nodeObj) {
		return;
	}
	if(nodeObj.level<level){
		nodeObj.level=level;
	}
	allNodes.push(nodeObj);
	var nextNodes = lbpm.globals.getNextNodeObjs(startNodeId);
	if(nodeObj.XMLNODENAME == "embeddedSubFlowNode" || nodeObj.XMLNODENAME == "dynamicSubFlowNode"){
		nextNodes = [];
		nextNodes.push(lbpm.nodes[nodeObj.startNodeId]);
	}else if(nodeObj.XMLNODENAME == "groupEndNode"){
		nextNodes = lbpm.globals.getNextNodeObjs(nodeObj.groupNodeId);
	}
	for(var i=0;i<nextNodes.length;i++){
		//防止循环节点 出现死循环
		var isIn=false;
		var nodeDesc = lbpm.nodedescs[nextNodes[i].nodeDescType];
		for(var j=0;j<allNodes.length;j++){

			//列表中已经存在的分支节点 后 表示已经形成了环形
			if(allNodes[j].id==nextNodes[i].id && nodeDesc.isBranch(nextNodes[i]) && nextNodes[i].XMLNODENAME!="joinNode"){
				isIn=true;
				break;
			}
		}
		if(isIn)
		{
			continue;
		}
		lbpm.globals._levelCalc(nextNodes[i].id,level,allNodes);
	}
}
//修改为层级排序方式 #作者：曹映辉 #日期：2013年5月27日 
lbpm.globals.setNodeLevel=function(){
	lbpm.globals._levelCalc("N1");
}
lbpm.globals.levelSorter = function(node1, node2) {
	return node1.level-node2.level;
};

Array.prototype.contains = function (arr){
	for(var i=0;i<this.length;i++){
		if(this[i] == arr){
			return true;
		}
	}
	return false;
}

//系统全局的排序函数
lbpm.globals.getNodeSorter = function() {
	//routingSortNodes=lbpm.globals.getSortNodes();
	return lbpm.globals.levelSorter;
};
/*
 * 解析简版XML成lbpm对象nnn
 */
lbpm.globals.parseXMLObj=function()
{
	//解析流程的XML成对象
	var processData = WorkFlow_LoadXMLData(document.getElementById("sysWfBusinessForm.fdFlowContent").value);
	var processDataInit = WorkFlow_LoadXMLData(document.getElementById("sysWfBusinessForm.fdFlowContent").value);
	if(!processData) return;

	//节点排序
	var processNodes=processData.nodes;
	var processNodesInit=processDataInit.nodes;
	//此时流程图未加载完成，不能使用路由排序，只能使用位置排序 #作者：曹映辉 #日期：2013年5月21日 
	processNodes.sort(lbpm.globals.normalSorter);
	processNodesInit.sort(lbpm.globals.normalSorter);
	//流程图需要的属性
	for(o in processData){
		if(o!="nodes" && o!="lines"){
			lbpm.flowcharts[o]=processData[o];
		}
	}
	//去掉被删除的节点与连线
	var idNodesArray= new Array(), idLinesArray= new Array();
	for(var i=0,j=processNodes.length; i<j; i++){
		idNodesArray[i] = processNodes[i].id;
	}
	for(i=0; i<processData.lines.length; i++){
		idLinesArray[i] = processData.lines[i].id;
	}
	for (var o in lbpm.nodes) {
		if(!idNodesArray.contains(o)){
			delete lbpm.nodes[o];
		}
	}
	for (var o in lbpm.lines) {
		if(!idLinesArray.contains(o)){
			delete lbpm.lines[o];
		}
	}

	//节点对象
	for(var i=0,j=processNodes.length; i<j; i++){
		var nodeObj=processNodes[i];
		lbpm.nodes[nodeObj.id]=nodeObj;
		lbpm.nodes[nodeObj.id].startLines=[];
		lbpm.nodes[nodeObj.id].endLines=[];
		lbpm.nodes[nodeObj.id].Status=1;
		lbpm.nodes[nodeObj.id].nodeDescType=lbpm.nodeDescMap[nodeObj.XMLNODENAME];
		lbpm.nodes[nodeObj.id].level=0;                                          //设置默认层级，用于节点排序
	}
	for(var i=0,j=processNodesInit.length; i<j; i++){
		var nodeObjInit=processNodesInit[i];
		lbpm.nodesInit[nodeObjInit.id]=nodeObjInit;
	}
	//连线对象
	for(i=0; i<processData.lines.length; i++){
		var lineObj=processData.lines[i];
		lbpm.lines[lineObj.id]=lineObj;
		//连线的起始指向的节点
		lbpm.lines[lineObj.id].startNode=lbpm.nodes[lineObj.startNodeId];
		//连线的结束指向的节点
		lbpm.lines[lineObj.id].endNode=lbpm.nodes[lineObj.endNodeId];
		//节点的结束连线
		(lbpm.nodes[lineObj.startNodeId].endLines).push(lineObj);
		//节点的开始连线
		(lbpm.nodes[lineObj.endNodeId].startLines).push(lineObj);
	}
	//更新历史节点属性
	var processData = WorkFlow_LoadXMLData(document.getElementById("sysWfBusinessForm.fdTranProcessXML").value);
	for(i=0; i<processData.historyNodes.length; i++){
		var hNodeInfo = processData.historyNodes[i];
		var hNode = lbpm.nodes[hNodeInfo.id];
		if(hNode == null) {
			continue;
		}
//		if (lbpm.isFreeFlow) {
//			// 自由流时，历史节点的Status值就取2，不根据routeType特殊处理，和流程图内的显示保持一致
//			hNode.Status=2;
//		} else {
			if(hNodeInfo.routeType == "BACK" || hNodeInfo.routeType == "JUMP") {
				hNode.Status=1;
			} else {
				hNode.Status=2;
				hNode.targetId = hNodeInfo.targetId;
				hNode.routeType = hNodeInfo.routeType;
			}
//		}

	}
	//设置节点状态是否为当前节点
	for(i=0; i<processData.runningNodes.length; i++){
		lbpm.nodes[processData.runningNodes[i].id].Status=3;
		//自由流，当前节点后续节点状态全部置为1
		if (lbpm.isFreeFlow) {
			var nodes = lbpm.globals.getNextNodes(lbpm.nodes[processData.runningNodes[i].id],[]);
			for(var j = 0;j<nodes.length;j++){
				nodes[j].Status=1;
			}
		}
	}
	lbpm.notifyType=lbpm.flowcharts.notifyType;
	//初始化流程说明
	if(lbpm.flowcharts && lbpm.flowcharts.description) {
		var changedText = Com_HtmlEscape(lbpm.flowcharts.description);
		if(changedText) {
			// &已经被Com_HtmlEscape转义了
			changedText=changedText.replace(/&amp;#xD;&amp;#xA;/g,"<br />");
			changedText=changedText.replace(/&amp;#xD;/g,"");
			changedText=changedText.replace(/&amp;#xA;/g,"<br />");
		}
		changedText = handleDescriptionLang4View(lbpm.flowcharts,changedText);
		/*#149721-政务公文模块的流程说明有值也没有显示-开始*/
		//$("#fdFlowDescription").html(changedText);
        $("span#fdFlowDescription").html(changedText);
		/*#149721-政务公文模块的流程说明有值也没有显示-结束*/
 		if(lbpm.approveType == "right"){
 			$("#fdFlowDescriptionRow #fdFlowDescription").html(changedText);
 			if(lbpm.constant.ISINIT){
 				if(lbpm.isFreeFlow){
 					$("#freeflowRow").before($("#fdFlowDescriptionRow"));
 				}else{
 					$("#manualBranchNodeRow").before($("#fdFlowDescriptionRow"));
 				}
 			}
 		}
	}else{
		if(lbpm.approveType == "right"){
 			$("#fdFlowDescriptionRow").hide();
 		}
	}

	function handleDescriptionLang4View(processData,def){
		function _getLangLabelByJson(defLabel,langsArr,lang){
			if(langsArr==null){
				return defLabel;
			}
			for(var i=0;i<langsArr.length;i++){
				if(lang==langsArr[i]["lang"]){
					return _formatValues(langsArr[i]["value"])||defLabel;
				}
			}
			return _formatValues(defLabel);
		}
		function _formatValues(value){
			value=value||"";
			value  = Com_HtmlEscape(value);
			value=value.replace(/&amp;#xD;&amp;#xA;/g,"<br />");
			value=value.replace(/&amp;#xD;/g,"");
			value=value.replace(/&amp;#xA;/g,"<br />");
			return value;
		}

		if(!_isLangSuportEnabled){
			return def;
		}
		if(processData.descriptionLangJson){
			var descriptionLangJson = $.parseJSON(processData.descriptionLangJson);
			var lang = WorkFlow_GetCurrUserLang();
			var value =  _getLangLabelByJson(processData.description,descriptionLangJson, lang);
			return value||"";
		}
		return def;
	}

};

lbpm.globals.getNextNodes = function(node, nodes) {
	var nexts = lbpm.globals.getNextNodeObjs(node.id);
	for (var i = 0; i < nexts.length; i ++) {
		var nNode = nexts[i];
		if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,nNode)) {
			continue;
		}
		if (lbpm.globals.containNode(nodes, nNode)) {
			continue;
		}
		nodes.push(nNode);
		lbpm.globals.getNextNodes(nNode, nodes);
	}
	return nodes;
};

lbpm.globals.containNode = function(nodes, node) {
	for (var n = 0; n < nodes.length; n ++) {
		if (node.id == nodes[n].id) {
			return true;
		}
	}
	return false;
};

lbpm.globals.objectToJSONString = function (value, replacer, space) {
	return JSON.stringify(value, replacer, space);
};

/**
* @param node 节点对象
* 	JS文件路径
* 功能：根据节点对象获取到节点的类型，用得判断节点的类型的地方
*/
lbpm.globals.checkNodeType=function(nodeType,node){
	if(!node) return false;
	var constObj=lbpm.constant;
	var nodeDescObj=lbpm.nodedescs[node.nodeDescType];
	switch (nodeType){
		case constObj.NODETYPE_HANDLER: // 是否是有处理人类型的节点
			return nodeDescObj["isHandler"](node);
			break;
		case constObj.NODETYPE_CANREFUSE: //是否可以被驳回（是人工处理节点，不是自动运行，不是分支，且uniqueMark为空）		
			if(nodeDescObj["isHandler"](node) && !nodeDescObj["isAutomaticRun"](node) && !nodeDescObj["isBranch"](node)){
				return true;
			}else {
				return false;
			}
			break;
		case constObj.NODETYPE_SEND: //是否是抄送节点(是人工，是自动运行，不是分支，不是子流程，不是并发，uniqueMark为空)
			if(nodeDescObj["isHandler"](node) && nodeDescObj["isAutomaticRun"](node) && !nodeDescObj["isBranch"](node) && !nodeDescObj["isSubProcess"](node) && !nodeDescObj["isConcurrent"](node) && nodeDescObj["uniqueMark"](node)==null){
				return true;
			}else {
				return false;
			}
			break;
		case constObj.NODETYPE_SPLIT: //并发分支开始（不是人工，是自动运行，是分支，不是子流程，是并发，uniqueMark为空）
		if(!nodeDescObj["isHandler"](node) && nodeDescObj["isAutomaticRun"](node) && nodeDescObj["isBranch"](node) && !nodeDescObj["isSubProcess"](node) && nodeDescObj["isConcurrent"](node) && nodeDescObj["uniqueMark"](node)==null){
				return true;
			}else {
				return false;
			}
			break;
		case constObj.NODETYPE_REVIEW: //审批节点类型（是人工，不是自动运行，不是分支，不是子流程，不是并发，uniqueMark为空）
			if(nodeDescObj["isHandler"](node) && !nodeDescObj["isAutomaticRun"](node) && !nodeDescObj["isBranch"](node) && !nodeDescObj["isSubProcess"](node) && !nodeDescObj["isConcurrent"](node) && nodeDescObj["uniqueMark"](node)==null){
				return true;
			}else {
				return false;
			}
			break;
		case constObj.NODETYPE_STARTSUBPROCESS	: //启动子流程（不是人工，是自动运行，不是分支，是子流程，是并发，uniqueMark为空）
			if(!nodeDescObj["isHandler"](node) && nodeDescObj["isAutomaticRun"](node) && !nodeDescObj["isBranch"](node) && nodeDescObj["isSubProcess"](node) && nodeDescObj["isConcurrent"](node) && nodeDescObj["uniqueMark"](node)==null){
				return true;
			}else {
				return false;
			}
			break;
		case constObj.NODETYPE_RECOVERSUBPROCESS: //结束子流程（不是人工，是自动运行，不是分支，是子流程，不是并发，uniqueMark为空）
			if(!nodeDescObj["isHandler"](node) && nodeDescObj["isAutomaticRun"](node) && !nodeDescObj["isBranch"](node) && nodeDescObj["isSubProcess"](node) && !nodeDescObj["isConcurrent"](node) && nodeDescObj["uniqueMark"](node)==null){
				return true;
			}else {
				return false;
			}
			break;
		case constObj.NODETYPE_AUTOBRANCH: //自动分支(不是人工，是自动分支，是分支，不是子流程，不是并发，uniqueMark为空)
			if(!nodeDescObj["isHandler"](node) && nodeDescObj["isAutomaticRun"](node) && nodeDescObj["isBranch"](node) && !nodeDescObj["isSubProcess"](node) && !nodeDescObj["isConcurrent"](node) && nodeDescObj["uniqueMark"](node)==null){
				return true;
			}else {
				return false;
			}
			break;
		case constObj.NODETYPE_MANUALBRANCH: //人工决策(是人工，不是自动运行，是分支，不是子流程，不是并发，uniqueMark为空) 
			if(nodeDescObj["isHandler"](node) && !nodeDescObj["isAutomaticRun"](node) && nodeDescObj["isBranch"](node) && !nodeDescObj["isSubProcess"](node) && !nodeDescObj["isConcurrent"](node) && nodeDescObj["uniqueMark"](node)==null){
				return true;
			}else {
				return false;
			}
			break;
		case constObj.NODETYPE_ROBOT: //机器人(不是人工，是自动运行，不是分支，不是子流程，不是并发，uniqueMark为空) 
			if(!nodeDescObj["isHandler"](node) && nodeDescObj["isAutomaticRun"](node) && !nodeDescObj["isBranch"](node) && !nodeDescObj["isSubProcess"](node) && !nodeDescObj["isConcurrent"](node) && nodeDescObj["uniqueMark"](node)==null){
				return true;
			}else {
				return false;
			}
			break;
		default: //其他固定写死的节点（签字节点、并发分支结束节点、起草节点、开始节点、结束节点）
			if(node.nodeDescType==nodeType) return true;
   	  		break;
	};
	return false;
};
/**
* @param fileUrl
* 	JS文件路径
* 功能：动态加载JS
*/
lbpm.globals.loadJs=function(url){
    var result = $.ajax({
    	  url: url,
    	  async: false
    	}).responseText;
    if ( ( result != null ) && ( !document.getElementById( url ) ) ){
        var oHead = document.getElementsByTagName('HEAD').item(0);
        var oScript = document.createElement( "script" );
        oScript.language = "javascript";
        oScript.type = "text/javascript";
        oScript.id = url;
        oScript.defer = true;
        oScript.text = result;
        oHead.appendChild( oScript );
    }
} ;
lbpm.globals.includeFile=function(fileList, contextPath, extendName){
	var i, j, fileType;
	if(contextPath==null) {
		contextPath = Com_Parameter.ContextPath;
		if(contextPath.substr(contextPath.length-1, contextPath.lenghth) == '/') {
			contextPath = contextPath.substr(0,contextPath.length-1);
		}
	}
	fileList = fileList.split("|");
	for(i=0; i<fileList.length; i++){
		//开启压缩后，若文件在压缩列表内，则无需再引入
		if(lbpm.compressSwitch=="true" && Com_ArrayGetIndex(lbpm.combinedFiles, fileList[i].substr(1))>-1){
			break;
		}
		if(Com_ArrayGetIndex(lbpm.jsfilelists, fileList[i])==-1){
			lbpm.jsfilelists[lbpm.jsfilelists.length] = fileList[i];
			if(fileList[i].indexOf(".jsp")==-1)
				fileList[i] = contextPath+(fileList[i]).toLowerCase();
			else
				fileList[i] = contextPath+(fileList[i]);

			if(extendName==null){
				j = fileList[i].lastIndexOf(".");
				if(j>-1)
					fileType = fileList[i].substring(j+1);
				else
					fileType = "js";
			}else{
				fileType = extendName;
			}
			if(Com_Parameter.Cache && fileList[i].indexOf('s_cache=')<0){
				if(fileList[i].indexOf("?")>=0){
					fileList[i] += "&s_cache=" + Com_Parameter.Cache;
				}else{
					fileList[i] += "?s_cache=" + Com_Parameter.Cache;
				}
			}
			switch(fileType){
			case "js":
				document.writeln("<script src="+fileList[i]+"></script>");
				break;
			case "css":
				document.writeln("<link rel=stylesheet href="+fileList[i]+">");
			}
		}
	}
};
lbpm.globals.initialSimpleWorkflow=function(){

	//动态加载简版审批界面JS add by luorf 20120627
	if($("tr[id=simpleWorkflowRow]").size()>0)
		lbpm.globals.loadJs(Com_Parameter.ContextPath+"sys/lbpmservice/include/syslbpmprocess_simple.js");
};
//加载审批标签js
lbpm.globals.initialSimpleTagWorkflow=function(){
	//动态加载简版审批界面JS add by luorf 20120627 (不再动态引入，通过js引入)
	//lbpm.globals.loadJs(Com_Parameter.ContextPath+"sys/lbpmservice/include/syslbpmprocess_simple_tag.js");
};
lbpm.globals.validateControlItem=function(){
 	var saveDraftButton = document.getElementById("saveDraftButton");
 	var updateButton = document.getElementById("updateButton");

 	var operationItemsRow = document.getElementById("operationItemsRow");
 	var operationMethodsRow = document.getElementById("operationMethodsRow");
 	//var operationsRow = document.getElementById("operationsRow");
 	// var commonUsagesRow = document.getElementById("commonUsagesRow");
 	var descriptionRow = document.getElementById("descriptionRow");
 	//显示隐藏签章
 	var showSignature = document.getElementById("showSignature");
 	var notifyTypeRow = document.getElementById("notifyTypeRow");
 	var attachmentRow = document.getElementById("attachmentRow");
 	var notifyOptionTR = document.getElementById("notifyOptionTR");
 	var checkChangeFlowTR = document.getElementById("checkChangeFlowTR");
	//var oprNames=lbpm.globals.getOperationParameterJson("oprNames");
 	var oprNames=lbpm.globals.getOperationParameterJson("operations");

 	var notifyLevelRow = document.getElementById("notifyLevelRow");
 	var nextNodeRow = document.getElementById("nextNodeRow");

 	if(oprNames == null || oprNames.length==0){
 		//隐藏操作行
 		lbpm.globals.hiddenObject(operationItemsRow, true);
 		lbpm.globals.hiddenObject(operationMethodsRow, true);
 		// lbpm.globals.hiddenObject(commonUsagesRow, true);
 		if(showSignature){
 			lbpm.globals.hiddenObject(showSignature, true);
 		}
 		lbpm.globals.hiddenObject(descriptionRow, true);
 		lbpm.globals.hiddenObject(notifyTypeRow, true);
 		lbpm.globals.hiddenObject(attachmentRow, true);
 		lbpm.globals.hiddenObject(notifyOptionTR, true);
 		lbpm.globals.hiddenObject(checkChangeFlowTR, true);
 		lbpm.globals.hiddenObject(saveDraftButton, true);
 		lbpm.globals.hiddenObject(updateButton, true);

 		lbpm.globals.hiddenObject(notifyLevelRow, true);
 		lbpm.globals.hiddenObject(nextNodeRow, true);
 	}else{
 		// 根据处理人的身份显示不同的操作项
 		lbpm.globals.hiddenObject(operationMethodsRow, false);
 		// lbpm.globals.hiddenObject(commonUsagesRow, false);
 		if(showSignature){
 			if (typeof(seajs) != 'undefined') {
 				lbpm.globals.hiddenObject(showSignature, false);
 			}else{
 				lbpm.globals.hiddenObject(showSignature, true);
 			}
 		}
 		// #60715 起草节点隐藏审批意见框
 		if(lbpm.nowNodeId == "N2" && Lbpm_SettingInfo.isDraftNodeDisplayOpinion == "true" && (lbpm.constant.ROLETYPE=='' || lbpm.constant.ROLETYPE==lbpm.constant.PROCESSORROLETYPE)) {
 			if(lbpm.constant.DOCSTATUS != "11" && Lbpm_SettingInfo.isNewPageAndDraftsManRecallPage == "true") {
 				lbpm.globals.hiddenObject(descriptionRow, true);
 				lbpm.globals.hiddenObject(showSignature, true);
 				lbpm.hideDescriptionOnDraftNode = true;
 			}
 			if(lbpm.constant.DOCSTATUS == "11" && Lbpm_SettingInfo.isRejectPage == "true") {
 				lbpm.globals.hiddenObject(descriptionRow, true);
 				lbpm.globals.hiddenObject(showSignature, true);
 				lbpm.hideDescriptionOnDraftNode = true;
 			}
 		} else {
 			lbpm.globals.hiddenObject(descriptionRow, false);
 		}
 		//lbpm.globals.hiddenObject(descriptionRow, false);
 		lbpm.globals.hiddenObject(notifyTypeRow, false);
 		lbpm.globals.hiddenObject(attachmentRow, false);
 		lbpm.globals.hiddenObject(notifyOptionTR, false);
 		lbpm.globals.hiddenObject(saveDraftButton, false);
 		lbpm.globals.hiddenObject(updateButton, false);
 		lbpm.globals.hiddenObject(checkChangeFlowTR, false);

 		lbpm.globals.hiddenObject(notifyLevelRow, false);
 	 	if(Lbpm_SettingInfo.isNotifyLevelOptional == "false"){
 	 		lbpm.globals.hiddenObject(notifyLevelRow, true);
 	 	}
 	}
 	lbpm.globals.controlProcessStatusRow();

 	if (window.OptBar_Refresh) {
 		OptBar_Refresh(true);
 	}
 	if(!lbpm.nowProcessorInfoObj){
 		$('#lbpm_highLevelTab').each(function() {
			lbpm.globals.setNotionPopedomTRHidden(this);
		});
	}
	lbpm.globals.showHistoryOperationInfos();

	// 控制自由流相关行
	if (lbpm.isFreeFlow) {
		var nodeObj = lbpm.globals.getCurrentNodeObj();
		if (lbpm.allMyProcessorInfoObj && nodeObj && lbpm.allMyProcessorInfoObj.length>0 && (nodeObj["id"]=="N2" || nodeObj["flowPopedom"] == 1 || nodeObj["flowPopedom"] == 2)) {
			var editFreeFlowDIV = document.getElementById("editFreeFlowDIV");
			lbpm.globals.hiddenObject(editFreeFlowDIV, false);
			$(".lbpm_freeflow_modifyFlowContent").show();
		} else {
			var viewFreeFlowDIV = document.getElementById("viewFreeFlowDIV");
			lbpm.globals.hiddenObject(viewFreeFlowDIV, false);
		}
		lbpm.globals.hiddenObject(notifyOptionTR, true);
		//自由流，若意见框隐藏，则私密意见也隐藏
		if(!descriptionRow || descriptionRow.style.display == 'none'){
			$("#privateOpinionTr").hide();
		}
		//自由流默认模板
		if(lbpm.constant.ISINIT){
			$(".lbpm_freeflow_defaultTemp_btn").show();
		}
	}

	//私密意见行-开关控制显示
	if(Lbpm_SettingInfo.isPrivateOpinion == "false"){
		$("#privateOpinionTr").hide();
	}

	//流程说明行-开关控制显示
 	if(Lbpm_SettingInfo.isShowFlowDescription == "false"){
 		if(lbpm.approveType == "right"){
 			$("#fdFlowDescriptionRow").hide();
 		} else {
 	 		var fdFlowDescriptionRow = $("#fdFlowDescription").closest("tr")[0];
 	 		lbpm.globals.hiddenObject(fdFlowDescriptionRow, true);
 		}
 	}
 	//已处理人行-开关控制显示
 	if(Lbpm_SettingInfo.isShowHistoryHandlers == "false"){
 		var historyHandlersRow = $("#historyHandlersRow")[0];
 		lbpm.globals.hiddenObject(historyHandlersRow, true);
 	}
 	//当前处理人行-开关控制显示
 	if(Lbpm_SettingInfo.isShowCurrentHandlers == "false"){
 		var currentHandlersRow = $("#currentHandlersRow")[0];
 		lbpm.globals.hiddenObject(currentHandlersRow, true);
 	}
 	//通知选项行-开关控制显示
 	if (Lbpm_SettingInfo.isShowNotifyType == "false"){
		var notifyOptionTR = $("#notifyOptionTR")[0];
		lbpm.globals.hiddenObject(notifyOptionTR, true);
 	}
 };

// 控制流程状态行
lbpm.globals.controlProcessStatusRow=function(){
	var fdProcessStatus = $("input[name='sysWfBusinessForm.fdProcessStatus']").val();
	if(fdProcessStatus) {
		$("#processStatusRow").show();
		$("#processStatusLabel").html("<font color=red>"+fdProcessStatus+"</font>");
	}
}

//对附件机制的显示行的控制
 lbpm.globals.setupAttachmentRow=function(){
 	var assignmentRow = document.getElementById("assignmentRow");
 	if(assignmentRow == null){
 		return;
 	}
 	if(!lbpm.nowProcessorInfoObj){
 		lbpm.globals.hiddenObject(assignmentRow, true);
 		return;
 	}
 	if(lbpm.constant.ROLETYPE!='' && lbpm.constant.ROLETYPE!=lbpm.constant.PROCESSORROLETYPE){
 		lbpm.globals.hiddenObject(assignmentRow, true);
 		return;
 	}
 	var canAddAuditNoteAtt = lbpm.globals.getCurrentNodeObj().canAddAuditNoteAtt;
 	var fdkey = document.getElementById("sysWfBusinessForm.fdAuditNoteFdId").value;
 	if(canAddAuditNoteAtt=="false"){
 		lbpm.globals.hiddenObject(assignmentRow, true);
 		if((typeof(Attachment_ObjectInfo) != "undefined") && Attachment_ObjectInfo[fdkey]){
 			for(var i = 0; i < Attachment_ObjectInfo[fdkey].fileList.length; i++) {
 				Attachment_ObjectInfo[fdkey].fileList[i].fileStatusBak = Attachment_ObjectInfo[fdkey].fileList[i].fileStatus;
 				Attachment_ObjectInfo[fdkey].fileList[i].fileStatus = -1;
 			}
 		}
 		return;
 	} else {
 		lbpm.globals.hiddenObject(assignmentRow, false);
 		if((typeof(Attachment_ObjectInfo) != "undefined") && Attachment_ObjectInfo[fdkey]){
 			for(var i = 0; i < Attachment_ObjectInfo[fdkey].fileList.length; i++) {
 				if(Attachment_ObjectInfo[fdkey].fileList[i].fileStatusBak){
 					Attachment_ObjectInfo[fdkey].fileList[i].fileStatus = Attachment_ObjectInfo[fdkey].fileList[i].fileStatusBak;
 				}
 			}
 		}
 	}
 	var tasksId = lbpm.globals.getOperationParameterJson("id");
 	var showTableKey = tasksId + '_' + lbpm.handlerId;
 	var attachmentTableArray = assignmentRow.getElementsByTagName("Table");
 	for(var i = 0; i < attachmentTableArray.length; i++){
 		var attachmentTable = attachmentTableArray[i];
 		if(attachmentTable.getAttribute("name") != "attachmentTable"){
 			continue;
 		}
 		if(attachmentTable.getAttribute("id") == showTableKey){
 			lbpm.globals.hiddenObject(attachmentTable, false);
 		}else{
 			lbpm.globals.hiddenObject(attachmentTable, true);
 		}
 	}
 }

lbpm.globals.hiddenObject=function(obj, flag){
	if(obj != null){
		if(flag){
			$(obj).hide();
		}else{
			$(obj).show();
		}
	}
};

//取得当前节点的对象信息
lbpm.globals.getNodeObj=function(nodeId){
	if (nodeId == '' || nodeId == null) {
		return {};
	}
	return lbpm.nodes[nodeId];
};

//取得当前节点的连线对象信息
lbpm.globals.getLineObj=function(nodeId, showStartNode){
	var nodeObj=lbpm.nodes[nodeId];
	if(showStartNode == null || showStartNode == true){
		return nodeObj.startLines[0];
	}else{
		return nodeObj.endLines[0];
	}
};

//取得下一个节点的对象
lbpm.globals.getNextNodeObj=function(nodeId){
	var nodeObj=lbpm.nodes[nodeId];
	return nodeObj.endLines[0].endNode;
};

//获取当前主文档类型
lbpm.globals.getWfBusinessFormModelName=function() {

	var modelName = lbpm.modelName;
	var fdModelName = document.getElementsByName('sysWfBusinessForm.fdModelName');
	//#2202 修改为 优先取 sysWfBusinessForm.fdModelName 中的modelName #曹映辉 日期 2014.08.19
	if(fdModelName && fdModelName.length > 0){
		modelName = lbpm.modelName = fdModelName[0].value;
	}
	return modelName;
};
//获取当前主文档ID
lbpm.globals.getWfBusinessFormModelId=function() {
	var modelId = lbpm.modelId;
	var fdModelId = document.getElementsByName('sysWfBusinessForm.fdModelId');
	if (fdModelId && fdModelId.length>0) {
			modelId = lbpm.modelId = fdModelId[0].value;
	}
	return modelId;
};
//获取当前主文档fdkey
lbpm.globals.getWfBusinessFormFdKey=function() {
	var fdkey = lbpm.constant.FDKEY;
	var _fdkey = document.getElementsByName('sysWfBusinessForm.fdKey');
	if (_fdkey && _fdkey.length > 0) {
		fdkey = lbpm.constant.FDKEY = _fdkey[0].value;
	}
	return fdkey;
};
//获取当前主文档ID
lbpm.globals.getWfBusinessFormDocStatus=function() {
	var docStatus = lbpm.constant.DOCSTATUS;
	var _docStatus = document.getElementsByName('docStatus');
	if (_docStatus && _docStatus.length > 0) {
		docStatus = lbpm.constant.DOCSTATUS = _docStatus[0].value;
	}
	return docStatus;
};

lbpm.globals.checkModifyNodeAuthorization=function(nodeObj, allowModifyNodeId){
	if(nodeObj.mustModifyHandlerNodeIds != null && nodeObj.mustModifyHandlerNodeIds != ""){
		var index = (nodeObj.mustModifyHandlerNodeIds + ";").indexOf(allowModifyNodeId + ";");
		if(index != -1){
			return true;
		}
	}
	return false;
};

//从组织架构中选择并添加默认的备选列表
lbpm.globals.dialog_Address=function(mulSelect, idField, nameField, splitStr, selectType, action, startWith, isMulField, notNull, winTitle, treeTitle, exceptValue, nodeId, defaultOptionBean){
	var dialog = new KMSSDialog(mulSelect);
	dialog.winTitle = winTitle;
	dialog.treeTitle = treeTitle;
	dialog.addressBookParameter = new Array();

	if(selectType==null || selectType==0)
		selectType = ORG_TYPE_ALL;
	dialog.addressBookParameter.exceptValue = exceptValue;
	dialog.addressBookParameter.selectType = selectType;
	dialog.addressBookParameter.startWith = startWith;
	dialog.BindingField(idField, nameField, splitStr, isMulField);
	dialog.SetAfterShow(action);

	if(defaultOptionBean!=null){
		dialog.AddDefaultOptionBeanData(defaultOptionBean);
	}
	if(notNull!=null)
		dialog.notNull = notNull;
	dialog.URL = Com_Parameter.ResPath + "jsp/address_main.jsp";

	dialog.URL = Com_SetUrlParameter(dialog.URL,'mul',(mulSelect?1:0));

	//是否跨域
	var isDomain=false;
	try {
		typeof (top['seajs']);// 跨域错误判断
	} catch (e) {
		isDomain=true;
	}
	// 弹出框自适应
	var width = screen.width * 0.55;
	if(width < 800)
		width = 800;
	var height = screen.height * 0.63;
	if(height < 560)
		height = 560;
	if (!isDomain && typeof (top['seajs']) != 'undefined') {
		Com_EventPreventDefault();
		top.Com_Parameter.Dialog = dialog;
		top['seajs'].use('lui/dialog', function(dialog) {
			var url = '/resource/jsp/address_main.jsp',
				fieldObjs = top.Com_Parameter.Dialog.fieldList;
			url =  Com_SetUrlParameter(url,'mul',(mulSelect?1:0));
//			dialog.iframe(url,Data_GetResourceString('sys-organization:sysOrg.addressBook'),null,{width : 740,height : 540});
			dialog.iframe(url,Data_GetResourceString('sys-organization:sysOrg.addressBook'),null,{width : width,height : height});
			//#21089 防止点击地址本框打开地址本后，鼠标闪烁
			DialogFunc_BlurFieldObj(fieldObjs);
		});
	}else{
		dialog.Show(width, height);
	}

	//dialog.Show(740, 540);
};

//解析当前处理人的Info，返回当前操作对象
lbpm.globals.analysisProcessorInfoToObject=function(){
	return lbpm.nowProcessorInfoObj;
};

//清除操operationsRow信息，保证没有不必要的提示信息
lbpm.globals.handlerOperationClearOperationsRow=function() {
	if (lbpm.globals.destroyOperations) {
		lbpm.globals.destroyOperations();
	}
	$("[lbpmMark='operation']").each(function () {
		$(this).find("[lbpmDetail]").each(function() {
			this.innerHTML='';
		});
		$(this).find("td").each(function () {
			this.innerHTML='';
		});
		lbpm.globals.hiddenObject(this, true);
    });
	$("[lbpmMark='hide']").each(function () {
		lbpm.globals.hiddenObject(this, true);
    });
};

//显示流转日志
lbpm.globals.showHistoryDisplay=function(checkObj){
	var historyTableTR = document.getElementById("historyTableTR");
	var filterRadios = document.getElementById("filterRadio");
	if(checkObj.checked){
		lbpm.globals.hiddenObject(historyTableTR, false);
		lbpm.globals.hiddenObject(filterRadios, false);
	}else{
		lbpm.globals.hiddenObject(historyTableTR, true);
		lbpm.globals.hiddenObject(filterRadios, true);
	}
};

//显示、隐藏更多信息
lbpm.globals.showDetails=function(checked){
	var detailsRow = document.getElementById("showDetails");
	if(checked){
		lbpm.globals.hiddenObject(detailsRow, false);
		lbpm.flow_table_load_Frame();
	}else{
		lbpm.globals.hiddenObject(detailsRow, true);
	}
};

//取得下一节点的对象数组
lbpm.globals.getNextNodeObjs=function (nodeId){
	var nodeObj=lbpm.nodes[nodeId];
	var nextNodeObjs = new Array();
	for(var i = 0,j=nodeObj.endLines.length; i < j; i++){
		nextNodeObjs.push(nodeObj.endLines[i].endNode);
	}
	return nextNodeObjs;
};

//取得上一节点的对象组
lbpm.globals.getPreviousNodeObjs=function(nodeId){
	var nodeObj=lbpm.nodes[nodeId];
	var preNodeObjs = new Array();
	for(var i = 0,j=nodeObj.startLines.length; i < j; i++){
		preNodeObjs.push(nodeObj.startLines[i].startNode);
	}
	return preNodeObjs;
};


//取得上一节点的对象
lbpm.globals.getPreviousNodeObj=function(nodeId){
	return lbpm.globals.getPreviousNodeObjs(nodeId)[0];
};


//获取当前节点对象
lbpm.globals.getCurrentNodeObj=function(){
	if(lbpm.nowNodeId && lbpm.nowNodeId!="")
		return lbpm.nodes[lbpm.nowNodeId];
	else
		return null;
};
lbpm.globals.parseTasksInfo=function(curNodeXMLObj,taskFrom,identity){
	var tasksArr=$.grep(curNodeXMLObj.tasks,function(n,i){
		return n.taskFrom==taskFrom;
	});
	if(tasksArr.length==0) return tasksArr;
	var _tasksArr = $.extend(true, [], tasksArr); // clone对象
	var rtnArr=$.grep(_tasksArr,function(task,i){
		if(task.operations){
			//过滤操作
			var arr=$.grep(task.operations,function(n,i){
				 return n.operationHandlerType==identity;
			});
			if(arr.length==0){
				return false;
			}
			task.operations=arr;
			return true;
		}
		return false;
	});
	return rtnArr;
}

lbpm.globals.getDrafterInfoObj=function(curNodeXMLObj){
	if(curNodeXMLObj==null) return lbpm.drafterInfoObj;
	return lbpm.globals.parseTasksInfo(curNodeXMLObj,"node","drafter");
};

lbpm.globals.getAuthorityInfoObj=function(curNodeXMLObj){
	if(curNodeXMLObj==null) return lbpm.authorityInfoObj;
	return lbpm.globals.parseTasksInfo(curNodeXMLObj,"node","admin");
};
lbpm.globals.getHistoryhandlerInfoObj=function(curNodeXMLObj){
	if(curNodeXMLObj==null) return lbpm.historyhandlerInfoObj;
	return lbpm.globals.parseTasksInfo(curNodeXMLObj,"node","historyhandler");
};
lbpm.globals.getBranchAdminInfoObj=function(curNodeXMLObj){
	if(curNodeXMLObj==null) return lbpm.branchAdminInfoObj;
	return lbpm.globals.parseTasksInfo(curNodeXMLObj,"node","branchadmin");
};
//获取当前用户的对当前流程的信息（多表单模式时，当前方法返回的是根据当前表单id过滤过的事务信息；可从lbpm.allMyProcessorInfoObj中获取未过滤过当前用户的事务信息）
lbpm.globals.getProcessorInfoObj=function(curNodeXMLObj){
	//LBPM当前节点的XML信息解析
	var roleType = lbpm.constant.ROLETYPE;
	if(roleType == lbpm.constant.DRAFTERROLETYPE){
		return lbpm.globals.getDrafterInfoObj(curNodeXMLObj);
	}else if(roleType == lbpm.constant.AUTHORITYROLETYPE){
		return lbpm.globals.getAuthorityInfoObj(curNodeXMLObj);
	}else if(roleType == lbpm.constant.HISTORYHANDLERROLETYPE) {
		var allHistoryHandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj(curNodeXMLObj);
		if(lbpm.defaultOperationType && lbpm.defaultOperationType != "") {
			var historyHandlerInfoObj = new Array();
			$.grep(allHistoryHandlerInfoObj,function(infoObj,i){
				$.grep(infoObj.operations,function(operation,j){
					if (operation.id==lbpm.defaultOperationType) {
						historyHandlerInfoObj.push(infoObj);
						return true;
					} else {
						return false;
					}
				});
			});
			allHistoryHandlerInfoObj = historyHandlerInfoObj;
		}
		if(allHistoryHandlerInfoObj != "" && allHistoryHandlerInfoObj instanceof Array){
				var historyHandlerObjDefault = new Array();
				var historyHandlerObjOther = new Array();
					$.grep(allHistoryHandlerInfoObj,function(infoObj,i){
						$.grep(infoObj.operations,function(operation,j){
							// 第一次遍历则加入数组，防止重复添加
							if(j==0){
								if(!(typeof operation.id === "undefined") && operation.id == "history_handler_back"){
									historyHandlerObjDefault.push(infoObj);
								}else{
									var isOther = true;
									if(historyHandlerObjDefault.length>0){
										for(var h=0;h<historyHandlerObjDefault.length;h++){
											if((infoObj.id) == (historyHandlerObjDefault[h].id)){
												isOther = false;
												break;
											}
										}

									}
									if(isOther){
										historyHandlerObjOther.push(infoObj);
									}
								}
							}
							});

					});
			allHistoryHandlerInfoObj = historyHandlerObjDefault.concat(historyHandlerObjOther);
		}
		return allHistoryHandlerInfoObj;
	}else if(roleType == lbpm.constant.BRANCHADMINROLETYPE){
		return lbpm.globals.getBranchAdminInfoObj(curNodeXMLObj);
	}
	if(curNodeXMLObj==null)	return lbpm.processorInfoObj;

	var allMyProcessorInfoObj = lbpm.allMyProcessorInfoObj = lbpm.globals.parseTasksInfo(curNodeXMLObj,"workitem","handler");
	if (lbpm.isSubForm) {
		var subFormProcessInfoObj = new Array();
		// 多表单模式时根据当前的表单ID过滤事务
		for (var i=0; i<allMyProcessorInfoObj.length; i++) {
			if (lbpm.nowSubFormId==lbpm.nodes[allMyProcessorInfoObj[i]["nodeId"]]["subFormId"]){
				subFormProcessInfoObj.push(allMyProcessorInfoObj[i]);
			}
		}
		return subFormProcessInfoObj;
	} else {
		return allMyProcessorInfoObj;
	}
};

//跳转页面
lbpm.globals.redirectPage=function(successOrFailure){
	if(successOrFailure != lbpm.constant.SUCCESS && successOrFailure != lbpm.constant.FAILURE){
		return;
	}
	var url= Com_Parameter.ContextPath+'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=' + successOrFailure;
	document.forms[0].action=url;
	document.forms[0].submit();
};


lbpm.globals.redirectToEditPage=function(secs){
	if(--secs>0){
		setTimeout("lbpm.globals.redirectToEditPage("+secs+")",10);
	}else{
		var href = location.href;
		href = href.replace("method=view", "method=edit");
		location.href=href;
	}
} ;

lbpm.globals.setHandlerFormulaDialog_=function(idField, nameField, modelName, action, personOnly) {
	//var getFormFieldListFunc="lbpm.globals.getFormFieldList_"+lbpm.constant.FDKEY;
	//var fieldList = (new Function('return (' + getFormFieldListFunc + '());'))();
	var fieldList = lbpm.globals.getFormFieldList();
	if(personOnly){
		Formula_Dialog(idField,
				nameField,
				fieldList,
				"com.landray.kmss.sys.organization.model.SysOrgPerson",
				action,
				"com.landray.kmss.sys.workflow.engine.formula.WorkflowFunction",
				modelName);
	}else{
		Formula_Dialog(idField,
				nameField,
				fieldList,
				"com.landray.kmss.sys.organization.model.SysOrgElement[]",
				action,
				"com.landray.kmss.sys.workflow.engine.formula.WorkflowFunction",
				modelName);
	}
};

lbpm.globals.loading_Show=function() {
	document.body.appendChild(WorkFlow_Loading_Div);
	WorkFlow_Loading_Div.style.top = 200 + document.body.scrollTop;
	WorkFlow_Loading_Div.style.left = document.body.clientWidth / 2 + document.body.scrollLeft -50;
}
lbpm.globals.loading_Hide=function() {
	WorkFlow_Loading_Div.style.display = "none";
	var div = document.getElementById('WorkFlow_Loading_Div');
	if (div)
		document.body.removeChild(WorkFlow_Loading_Div);
};

lbpm.globals.load_Frame=function(td, url) {
	var tdObj = null;
	if(typeof(td)=="string"){
		tdObj = document.getElementById(td);
	}else{
		tdObj = td;
	}
	lbpm.globals.loading_Show();
	if(tdObj!=null){
		Doc_LoadFrame(tdObj, url);
		Com_AddEventListener(tdObj.getElementsByTagName('iframe')[0], 'load', function() {
			lbpm.globals.loading_Hide();
			var iframe = this.contentWindow;
			if(iframe && iframe.FlowChartObject && iframe.FlowChartObject.FormFieldList){
				iframe.FlowChartObject.FormFieldList = lbpm.globals.getFormFieldList();
			}
		});
	}else{
		lbpm.globals.loading_Hide();
	}
};

// 隐藏高级页签
lbpm.globals.setNotionPopedomTRHidden=function(highLevelTR) {
	if (highLevelTR.getAttribute("LKS_LabelName") == null) {
		var highLevelTABLE = document.getElementById("Label_Tabel_Workflow_Info");
		Doc_SetCurrentLabel("Label_Tabel_Workflow_Info", 2);
		var btn = document.getElementById("Label_Tabel_Workflow_Info_Label_Btn_1");
		btn.parentNode.style.display = 'none'; // <nobr>
		highLevelTR = highLevelTABLE.rows[1];
		highLevelTR.cells[0].innerHTML = '';
	} else {
		highLevelTR.parentNode.removeChild(highLevelTR);
	}
};

//转换数组为字符串 add by limh 2010年9月24日
lbpm.globals.arrayToStringByKey=function(arr,key){
	var str="";
	if(arr){
		for(var index=0;index<arr.length;index++){
			str=str+";"+arr[index][key];
		}
		str = str.substring(1);
	}
	return str;
};

lbpm.globals.isPassedSubprocessNode=function() {
	var rtnValue=false;
	$.each(lbpm.nodes, function(index, nodeData) {
		if(nodeData.Status==lbpm.constant.STATUS_PASSED){
			if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_STARTSUBPROCESS,nodeData)) {
				rtnValue=true;
				return false; // 中断循环
			}
		}
	})
	return rtnValue;
};

//取得有效的当前节点
lbpm.globals.getAvailableRunningNodes=function(){
	var RunningNodesArr = new Array();
	$.each(lbpm.nodes, function(index, nodeData) {
		if(nodeData.Status==lbpm.constant.STATUS_RUNNING)
			RunningNodesArr.push(nodeData);
	});
	return RunningNodesArr;
};
//取得有效的历史节点
lbpm.globals.getAvailableHistoryNodes=function(){
	var HistoryNodesArr = new Array();
	$.each(lbpm.nodes, function(index, nodeData) {
		if(nodeData.Status==lbpm.constant.STATUS_PASSED)
			HistoryNodesArr.push(nodeData);
	});
	return HistoryNodesArr;
};

//获取节点的数量
lbpm.globals.getNodeSize=function(){
	var nodeSize = 0;
	$.each(lbpm.nodes, function(index, nodeData) {
		nodeSize = nodeSize + 1;
	});
	return nodeSize;
}

//***************以下为对外函数，提供流程的数据*****************

/*lbpm.globals.setHandlerFormulaDialog=function(idField, nameField, modelName) {
	var action = function(rtv){lbpm.globals.afterChangeHandlerInfoes(rtv,lbpm.constant.ADDRESS_SELECT_FORMULA);};
	lbpm.globals.setHandlerFormulaDialog_(idField, nameField, modelName, action);
};*/

WorkFlow_Loading_Msg = lbpm.constant.LOADINGMSG;
WorkFlow_Loading_Img = document.createElement('img');
WorkFlow_Loading_Img.src = Com_Parameter.ContextPath + "resource/style/common/images/loading.gif";
WorkFlow_Loading_Div = document.createElement('div');
WorkFlow_Loading_Div.id = "WorkFlow_Loading_Div";
WorkFlow_Loading_Div.style.position = "absolute";
WorkFlow_Loading_Div.style.padding = "5px 10px";
WorkFlow_Loading_Div.style.fontSize = "12px";
WorkFlow_Loading_Div.style.backgroundColor = "#F5F5F5";
WorkFlow_loading_Text = document.createElement("label");
WorkFlow_loading_Text.id = 'WorkFlow_loading_Text_Label';
WorkFlow_loading_Text.appendChild(document.createTextNode(WorkFlow_Loading_Msg));
WorkFlow_loading_Text.style.color = "#00F";
WorkFlow_loading_Text.style.height = "16px";
WorkFlow_loading_Text.style.margin = "5px";
WorkFlow_Loading_Div.appendChild(WorkFlow_Loading_Img);
WorkFlow_Loading_Div.appendChild(WorkFlow_loading_Text);

var concurrencyBranchSelect = lbpm.constant.CONCURRENCYBRANCHSELECT;
var concurrencyBranchTitle = lbpm.constant.CONCURRENCYBRANCHTITLE;

//显示审批记录
lbpm.globals.showHistoryOperationInfos=function(){
	var historyInfoTable = document.getElementById("historyTableTR");
	if(historyInfoTable != null){
		if(lbpm.constant.SHOWHISTORYOPERS == null || lbpm.constant.SHOWHISTORYOPERS == 'false'){
			lbpm.globals.hiddenObject(historyInfoTable, true);
			if(lbpm.constant.PRIVILEGERFLAG == 'true'){
				lbpm.globals.hiddenObject(historyInfoTable, false);
			}
		}else{
			lbpm.globals.hiddenObject(historyInfoTable, false);
		}
	}
};

//获取主文档和表单数据字典
lbpm.globals.getFormFieldList=function() {
	var fdKey = lbpm.constant.FDKEY ? lbpm.constant.FDKEY : $("[name='sysWfBusinessForm.fdKey']").val();
	var func="XForm_getXFormDesignerObj_"+fdKey;
	if(window[func]){
		return window[func]();
	}
	try {
		if (window.parent
				&& parent.dialogArguments
				&& parent.dialogArguments.Window
				&& parent.dialogArguments.Window[func]) {
			return parent.dialogArguments.Window[func]();
		} else if (window.parent
				&& parent.opener && parent.opener.Com_Parameter
				&& parent.opener.Com_Parameter.Dialog
				&& parent.opener.Com_Parameter.Dialog.Window
				&& parent.opener.Com_Parameter.Dialog.Window[func]) {
			return parent.opener.Com_Parameter.Dialog.Window[func]();
		}
	}catch(e){
		if(window.console){
			window.console.warn(e);
		}
	}
	var fields =  Formula_GetVarInfoByModelName(lbpm.globals.getWfBusinessFormModelName());
	var modelId = lbpm.globals.getWfBusinessFormModelId();
	var modelName=lbpm.globals.getWfBusinessFormModelName();
	if(modelId){
		//#159053 【服务问题单】【低代码平台-优化】已经设置好的条件分支，在创建文档之后，去修改表单，使用特权人修改历史文档，选择不到表单中新增的字段 特权人修改流程 兼容低代码流程
		if(modelName == "com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain"){
			//获取建模应用modelId,流程取得是mainID
			var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppModelMain.do?method=getAppModelId&mainId="+modelId;
			var appModelId="";
			$.ajax({
				url: url,
				type: "post",
				async:false,
				success: function (data) {
					if(data){
						appModelId=data['modelId'];
					}
				}
			});
			if(appModelId){
				modelId=appModelId;
			}
		}
		var elem = new KMSSData().AddBeanData("lbpmAuthorizeMainService&modelId="+modelId+"&modelName="+lbpm.globals.getWfBusinessFormModelName()).GetHashMapArray();
		if(elem && elem[0]){
			var tempId = elem[0].tempId;
			var extFields =Formula_GetVarInfoByTempId(tempId);
			return fields.concat(extFields);
		}
	}
	return fields;

};


//获取后台参数
lbpm.globals.getOperationParameterJson=function(params,fromWorkitem,nodeObj){
	var processorObj=lbpm.nowProcessorInfoObj;
	var rtnObject =new Object();
	var arr=params.split(":");
	var arrNotValue=lbpm.globals.getNullParamArr(arr,processorObj);
	if(arrNotValue.length>0){
		lbpm.globals.getOperationParameterFromAjax(arrNotValue,fromWorkitem,processorObj,nodeObj);
	}

	for(var i=0,l=arr.length;i<l;i++){
		var param=arr[i];
		if (!processorObj) rtnObject[param]=null;
		else{
			if(processorObj[param]!=null)
				rtnObject[param]=processorObj[param];
			else{
				rtnObject[param]="";
				processorObj[param]="";
			}
		}
	}
	//如果传递过来是一个参数，直接返回值，不返回数组对象了
	if(arr.length==1) return rtnObject[arr[0]];
	return rtnObject;
};
//获取没有缓存的参数数组
lbpm.globals.getNullParamArr=function(arr,processorObj){
	var rtnArr=new Array();
	if(!processorObj) return rtnArr;
	for(var i=0,size=arr.length;i<size;i++){
		if(processorObj[arr[i]]==null) rtnArr.push(arr[i]);
	}
	return rtnArr;
};
//通过AJAX方式获取参数值
lbpm.globals.getOperationParameterFromAjax=function(arr,fromWorkitem,processorObj,nodeObj){
	if(!processorObj) return;
	var jsonObj = {};
	if(fromWorkitem){
		jsonObj.taskType=processorObj.type;
		jsonObj.taskId=processorObj.id;
	}else{
		//如果传递了节点对象，直接取传的节点对象，否则取当前节点对象
		if(nodeObj){
			jsonObj.nodeId=nodeObj.id;
			jsonObj.nodeType=nodeObj.XMLNODENAME;
		}else{
			jsonObj.nodeId=lbpm.nowNodeId;
			jsonObj.nodeType=lbpm.globals.getCurrentNodeObj().XMLNODENAME;
		}
	}
	jsonObj.params=arr.join(":");
	var jsonRtnObj = lbpm.globals._getOperationParameterFromAjax(jsonObj);
	if(jsonRtnObj!=null){
		for(o in jsonRtnObj){
			if(jsonRtnObj[o]!=null)
				processorObj[o]=jsonRtnObj[o];
			else
				processorObj[o]="";
		}
	}else{
		for(var i=0,size=arr.length;i<size;i++){
			processorObj[arr[i]]="";
		}
	};
}
lbpm.globals._getOperationParameterFromAjax = function(jsonObj) {
	var jsonUrl=Com_Parameter.ContextPath+"sys/lbpmservice/include/sysLbpmdata.jsp" + "?m_Seq="+Math.random();
	jsonUrl += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
	jsonUrl += "&modelName=" + lbpm.modelName;
	$.ajaxSettings.async = false;
	var jsonRtnObj=null;
	$.getJSON(jsonUrl,jsonObj, function(json){
		jsonRtnObj = json;
	});
	$.ajaxSettings.async = true;
	return jsonRtnObj;
}
/***********************************************
功能：替换HTML代码中的敏感字符
***********************************************/
lbpm.globals.htmlUnEscape=function(s){
  	if(s==null || s=="")
  		return "";
  	var re = /&amp;/g;
  	s = s.replace(re, "&");
  	re = /&quot;/g;
  	s = s.replace(re, "\"");
  	re = /&#39;/g;
  	s = s.replace(re, "'");
  	re = /&lt;/g;
  	s = s.replace(re, "<");
  	re = /&gt;/g;
  	return s.replace(re, ">");
};

//载入流转日志
lbpm.globals.showAuditNodeLoadIframe=function(){
	var iframe = document.getElementById("auditNodeTD").getElementsByTagName("IFRAME")[0];
	if(iframe.src){
		iframe.src = Com_Parameter.ContextPath+'sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do?method=listFlowLog&fdModelId='+lbpm.modelId;
	}
};

lbpm.globals.showReadLogLoadIframe=function(){
	var iframe = document.getElementById("loadReadLogTD").getElementsByTagName("IFRAME")[0];
	if(iframe.src){
		iframe.src = Com_Parameter.ContextPath+'sys/workflow/sys_wf_read_log/sysWfReadLog.do?method=list&fdModelId='+lbpm.modelId;
	}
};

//加载其他JS
(function(lbpm){
	var dir="/sys/lbpmservice/include/";
	var jsPath=dir+"syslbpmprocess_event.js|"+dir+"syslbpmprocess_submit.js|"+dir+"syslbpmprocess_nodes_filter.js|"+dir+"syslbpmprocess_simple_tag.js";
	lbpm.globals.includeFile(jsPath);
	if (!window.dojo) { // 移动不需要此文件
		lbpm.globals.includeFile(dir+"syslbpmbuildnextnodehandler.js");
	}
})(lbpm);

// 获取当前选中审批操作
lbpm.globals.getCurrentOperation = function() {
	$("[name='oprGroup']").each(function() {
		if (this.checked || this.type == 'select' || this.type == 'select-one') {
			var oprArr = (this.value).split(":");
			lbpm.currentOperationType=oprArr[0];
			lbpm.currentOperationName=oprArr[1];
			return false;
		}
	});
	if (lbpm.currentOperationType == null) {
		return null;
	}
	return {
		type: lbpm.currentOperationType,
		name: lbpm.currentOperationName,
		operation: lbpm.operations[lbpm.currentOperationType]
	};
};

lbpm.globals.initShortReview = function(text) {
	var div = document.createElement('div');// com_approval_bar2
	div.className = 'com_approval_bar2';
	div.innerHTML = '<div class="com_ap_bar2_bottom"><div class="com_ap_bar2_centre"><span>'+text+'</span></div></div>';
	document.body.appendChild(div);
	$(div).bind('click', function(event) {
		// 标签页是否展开
		var tab = LUI('process_review_tabcontent');
		if (tab != null) {
			if (!tab.isShow) {
				var panel = tab.parent;
				$.each(panel.contents, function(i) {
					if (this == tab) {
						//修复#105841 会议变更提交会报错
						try{
							panel.onToggle(i, false, false);
						}
						catch(e){

						}
						return false;
					}
				});
			}
		}
		$('html, body').animate({
	        scrollTop: $("#descriptionRow").offset().top - 200
	    }, 800); // scrollIntoView
	});
	// 快速审批
	function reviewBtnLocating() {
		var form = document.forms[0];
		var left = 0;
		if(form!=null){
			left = ($(form).offset().left - 50);
			if (left < 0) left = 0;
		}
		div.style.left = left + 'px';
	}
	reviewBtnLocating();
	$(window).bind({
		"scroll":function(){
			//reviewBtnLocating();
		},
		"resize":function(){
			reviewBtnLocating();
		}
	});
};

//——————————————自由子流程JS————————————————
lbpm.nowFreeSubFlowNodeId = null;
lbpm.subNodeHandlerIds = new Array();
lbpm.subNodeHandlerNames = new Array();
lbpm.myAddedSubNodes = new Array();

// 下一步设置选人，往自由子流程节点内追加节点
lbpm.globals.addSubNodeToGroup = function(groupNodeId, rtv) {
	if (rtv != null) {
		var rtvData = rtv.data;
		if (rtvData.length > 0) {
			lbpm.subNodeHandlerIds = new Array();
			lbpm.subNodeHandlerNames = new Array();
			for (var i=0; i<rtvData.length; i++) {
				lbpm.subNodeHandlerIds.push(rtvData[i].id);
				lbpm.subNodeHandlerNames.push(rtvData[i].name);
			}
		} else {
			if (lbpm.myAddedSubNodes.length > 0) {
				lbpm.globals.removeMyAddedSubNodes();
			}
			return;
		}
		// 获取流程图对象FlowChartObject
		var sysWfBusinessFormPrefix = $("input[name='sysWfBusinessFormPrefix']").val()!=null ? $("input[name='sysWfBusinessFormPrefix']").val() : "";
		var FlowChartObject = document.getElementById(sysWfBusinessFormPrefix + "WF_IFrame").contentWindow.FlowChartObject;

		if (lbpm.myAddedSubNodes.length == 0) {
			var subNode = FlowChartObject.Nodes.createSubNode("reviewNode",groupNodeId,"freeSubFlowNode");
			// 设置节点处理人id以及name信息
			subNode.Data["handlerIds"] = lbpm.subNodeHandlerIds.join(";");
			subNode.Data["handlerNames"] = lbpm.subNodeHandlerNames.join(";");
			var data = new KMSSData();
			data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowReviewNode");
			data = data.GetHashMapArray();
			for(var j=0;j<data.length;j++){
				if(data[j].isDefault=="true"){
					subNode.Data["operations"]["refId"] = data[j].value;
					break;
				}
			}
			if(FlowChartObject.ProcessData.opinionSortIds){
				var oIds = FlowChartObject.ProcessData.opinionSortIds.split(";");
				if(Com_ArrayGetIndex(oIds, groupNodeId) > -1){
					oIds.push(subNode.Data["id"]);
				}
				FlowChartObject.ProcessData.opinionSortIds = oIds.join(";");
			}
		} else {
			var subNode = FlowChartObject.Nodes.GetNodeById(lbpm.myAddedSubNodes[0]);
			subNode.Data["handlerIds"] = lbpm.subNodeHandlerIds.join(";");
			subNode.Data["handlerNames"] = lbpm.subNodeHandlerNames.join(";");
		}

		var flowXml = FlowChartObject.BuildFlowXML();
		if (!flowXml)
			return;
		var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
		processXMLObj.value = flowXml;
		lbpm.globals.parseXMLObj();
		lbpm.modifys = {};
		$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
		if (lbpm.myAddedSubNodes.length == 0) {
			lbpm.myAddedSubNodes.push(subNode.Data.id);
		}
		lbpm.events.mainFrameSynch();
	}
};

// 移除本次所添加的子节点
lbpm.globals.removeMyAddedSubNodes = function(){
	if (lbpm.myAddedSubNodes.length > 0) {
		var sysWfBusinessFormPrefix = $("input[name='sysWfBusinessFormPrefix']").val()!=null ? $("input[name='sysWfBusinessFormPrefix']").val() : "";
		var FlowChartObject = document.getElementById(sysWfBusinessFormPrefix + "WF_IFrame").contentWindow.FlowChartObject;
		for (var i=0;i<lbpm.myAddedSubNodes.length;i++) {
			var subNode = FlowChartObject.Nodes.GetNodeById(lbpm.myAddedSubNodes[i]);
			if(subNode.LineOut.length==1){
				var endNode = subNode.LineOut[0].EndNode;
				for(var j=subNode.LineIn.length-1; j>=0; j--){
					var line = subNode.LineIn[j];
					line.LinkNode(null, endNode, null, subNode.LineOut[0].Data.endPosition);
				}
			}
			subNode.Delete();
		}

		var flowXml = FlowChartObject.BuildFlowXML();
		if (!flowXml)
			return;
		var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
		processXMLObj.value = flowXml;
		lbpm.globals.parseXMLObj();
		lbpm.myAddedSubNodes = new Array();
		lbpm.subNodeHandlerIds = new Array();
		lbpm.subNodeHandlerNames = new Array();
		lbpm.events.mainFrameSynch();
	}
};

// 自由子节点行删除指定节点
lbpm.globals.delSubNode = function(subNode){
	var freeSubFlowNodeId = subNode.groupNodeId;
	var sysWfBusinessFormPrefix = $("input[name='sysWfBusinessFormPrefix']").val()!=null ? $("input[name='sysWfBusinessFormPrefix']").val() : "";
	var FlowChartObject = document.getElementById(sysWfBusinessFormPrefix + "WF_IFrame").contentWindow.FlowChartObject;
	FlowChartObject.Nodes.deleteSubNode(subNode.id);

	var flowXml = FlowChartObject.BuildFlowXML();
	if (!flowXml)
		return;
	var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
	processXMLObj.value = flowXml;
	lbpm.globals.parseXMLObj();
	if (lbpm.myAddedSubNodes.contains(subNode.id)) {
		lbpm.myAddedSubNodes = new Array();
		lbpm.subNodeHandlerIds = new Array();
		lbpm.subNodeHandlerNames = new Array();
	}
	lbpm.events.mainFrameSynch();
}

// 加载自由子流程行
lbpm.globals.loadFreeSubFlowNodeUL=function(freeSubFlowNode) {
	lbpm.nowFreeSubFlowNodeId = freeSubFlowNode.id;
	var flowNodeUL = $("#freeSubFlowNodeUL");
	if(flowNodeUL.length > 0){
		flowNodeUL.html("");
		var groupStartNode = lbpm.nodes[freeSubFlowNode.startNodeId];
		var groupFirstSubNode = groupStartNode.endLines[0].endNode;
		if (groupFirstSubNode.nodeDescType == "groupEndNodeDesc") {
			//组内除了组开始节点以及组结束节点外不存在其它子节点则不显示自由子流程行
			lbpm.globals.hiddenObject(document.getElementById("freeSubFlowNodeRow"), true);
			return;
		}
		var isNextFreeSubFlow = (lbpm.globals.getNextNodeObj(lbpm.nowNodeId).id == freeSubFlowNode.id) ? true : false;
		var initHtml = "";
		initHtml = '<li id="' + groupStartNode.id + '" class="flowNodeLi_node">';
		if (isNextFreeSubFlow) {
			initHtml += '<div class="flowNodeLi_runningStartNode"></div>';
		} else {
			initHtml += '<div class="flowNodeLi_passedStartNode"></div>';
		}
		initHtml += '</li>';
		flowNodeUL.html(initHtml);
		lbpm.globals.buildFreeSubFlowNodeLI(groupStartNode, flowNodeUL, isNextFreeSubFlow, freeSubFlowNode.initSubNodeId);
		lbpm.globals.hiddenObject(document.getElementById("freeSubFlowNodeRow"), false);
	}
}

// 自由子节点行-子节点DOM构建
lbpm.globals.buildFreeSubFlowNodeLI = function(nodeObj, flowNodeUL, isNextFreeSubFlow, initSubNodeId){
	var nodeId = nodeObj.endLines[0].endNode.id;
	var node = lbpm.nodes[nodeId];
	if (node.endLines.length == 0) {
		return;
	}
	if (lbpm.nowNodeId == "N2" && initSubNodeId == null) {
		if(node.XMLNODENAME != "groupEndNode" && node.XMLNODENAME != "groupStartNode"){
			lbpm.myAddedSubNodes.push(nodeId);
		}
	}
	var html = "";
	var handlerNames = "";
	var handlerDisplayNames = "";
	if (node.handlerNames) {
		handlerNames = node.handlerNames;
		var namesArray = handlerNames.split(";");
		// 根据流转方式决定分隔符的具体显示
		var delimiter = ";";
		if (node.processType == "1") {
			delimiter = "/";
		} else if (node.processType == "2") {
			delimiter = "+";
		}
		if (delimiter!=";") {
			handlerNames = handlerNames.replace(/;/g,delimiter);
		}
		// 控制名称显示长度
		var totalLength=0, maxLength=31;
		for (var i=0;i<namesArray.length;i++) {
			if (totalLength + namesArray[i].length < maxLength) {
				if (handlerDisplayNames != "") {
					handlerDisplayNames += delimiter + namesArray[i];
				} else {
					handlerDisplayNames += namesArray[i];
				}
			} else {
				if (handlerDisplayNames!="") {
					handlerDisplayNames += delimiter + "...";
				} else {
					handlerDisplayNames = namesArray[i].slice(0,maxLength) + "...";
				}
				break;
			}
			totalLength = handlerDisplayNames.length;
		}
	}
	html = '<li id="' + node.id + '" class="flowNodeLi_node">';
	html += '<span style="margin-right:5px">&rarr;</span>';
	if (node.Status == "1") {
		html += '<span class="flowNodeLi_normalNode">';
	} else if (node.Status == "2"){
		html += '<span class="flowNodeLi_passedNode">';
	} else if (node.Status == "3"){
		html += '<span class="flowNodeLi_runningNode">';
	} else {
		html += '<span class="flowNodeLi_normalNode">';
	}

	if (node.XMLNODENAME == "reviewNode") {
		html += '<span class="icon_reviewNode"></span><span class="handlerNameSpan" title="' + handlerNames + '">'+handlerDisplayNames+'</span>';
	} else if (node.XMLNODENAME == "signNode") {
		html += '<span class="icon_signNode"></span><span class="handlerNameSpan" title="' + handlerNames + '">'+handlerDisplayNames+'</span>';
	} else if (node.XMLNODENAME == "sendNode") {
		html += '<span class="icon_sendNode"></span><span class="handlerNameSpan title="' + handlerNames + '">'+handlerDisplayNames+'</span>';
	}
	var canDelete = false;
	if (node.Status == "1") {
		if (lbpm.myAddedSubNodes.contains(node.id)) {
			canDelete = true;
		}
	}
	if (node.Status != "3") {
		//下一节点是自由子流程节点且模板没配置初始环节时或当前节点是自由子流程节点模板配置的初始环节时，具有删除子节点的权利
		if (isNextFreeSubFlow && initSubNodeId == null || lbpm.nowNodeId == initSubNodeId) {
			canDelete = true;
		}
	}
	if (canDelete){
		html += '<span id="'+node.id+'" class="btn_removeNode_img" onclick="lbpm.globals.deleteSubNode(this.id);"></span></span></li>';
	} else {
		html += '</span></li>';
	}

	if(flowNodeUL[0].innerHTML==""){
		flowNodeUL.html(html);
	}else{
		flowNodeUL.append(html);
	}
	lbpm.globals.buildFreeSubFlowNodeLI(node, flowNodeUL, isNextFreeSubFlow, initSubNodeId);
};

//自由流行内删除节点
lbpm.globals.deleteSubNode = function(subNodeId){
	if (lbpm.globals.flowChartLoaded != true) {
		lbpm.flow_chart_load_Frame();
	}
	lbpm.globals.delSubNode(lbpm.nodes[subNodeId]);
};

//设置下一步（自由子流程节点)
lbpm.globals.setNextSubNode = function(groupNodeId){
	if (lbpm.globals.flowChartLoaded != true) {
		lbpm.flow_chart_load_Frame();
	}
	Dialog_Address(true,null,null,';',ORG_TYPE_POSTORPERSON,function myFunc(rtv){lbpm.globals.addSubNodeToGroup(groupNodeId,rtv);},null,null,false,null,null,null,null);
}

/**
 * 根据ID获取催办冷却时间
 */
lbpm.globals.ajaxPressTime=function(opId){
	var pressTimes=0;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=pressNodeIdJson",
		async: false,
		data: {workItemId:opId},
		type: "POST",
		dataType: 'json',
		success: function (data) {
			if(data.pressTimes>0){
				pressTimes=data.pressTimes;
			}
		},
		error: function (er) {
			if(console){
				console.log(er);
			}
		}
	});
	return pressTimes;
}

/**
 * 计算冷却时间，返回冷却时间显示文本
 */
lbpm.globals.countDownHtml=function(milliseconds){
	var today=new Date(),//当前时间
	h=today.getHours(),
	m=today.getMinutes(),
	s=today.getSeconds();
	var stopTime=new Date(milliseconds),//结束时间
	stopH=stopTime.getHours(),
	stopM=stopTime.getMinutes(),
	stopS=stopTime.getSeconds();
	var shenyu=stopTime.getTime()-today.getTime(),//倒计时毫秒数
	shengyuD=parseInt(shenyu/(60*60*24*1000)),//转换为天
	D=parseInt(shenyu)-parseInt(shengyuD*60*60*24*1000),//除去天的毫秒数
	shengyuH=parseInt(D/(60*60*1000)),//除去天的毫秒数转换成小时
	H=D-shengyuH*60*60*1000,//除去天、小时的毫秒数
	shengyuM=parseInt(H/(60*1000)),//除去天的毫秒数转换成分钟
	M=H-shengyuM*60*1000;//除去天、小时、分的毫秒数
	S=parseInt((shenyu-shengyuD*60*60*24*1000-shengyuH*60*60*1000-shengyuM*60*1000)/1000)//除去天、小时、分的毫秒数转化为秒

	if(shengyuD>0){
		return (shengyuD+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.day")+shengyuH+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.hour")+shengyuM+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.minute")+S+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.remindedAgain")+"<br>");
	}else{
		return (shengyuH+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.hour")+shengyuM+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.minute")+S+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.remindedAgain")+"<br>");
	}
}

/**
 * 计算当前时间剩余的毫秒数
 */
lbpm.globals.remainMilliseconds=function(milliseconds){
	var today=new Date(),//当前时间
	h=today.getHours(),
	m=today.getMinutes(),
	s=today.getSeconds();
	var stopTime=new Date(milliseconds),//结束时间
	stopH=stopTime.getHours(),
	stopM=stopTime.getMinutes(),
	stopS=stopTime.getSeconds();
	var shenyu=stopTime.getTime()-today.getTime();//倒计时毫秒数
	return shenyu;
}

/**
 * 是否隐藏节点编号的显示
 */
lbpm.globals.lbpmIsRemoveNodeIdentifier = function(){
	var isRemove = false;
	if (Lbpm_SettingInfo){
		if ((lbpm.settingInfo.isHideNodeIdentifier === "false" && Lbpm_SettingInfo.hideNodeIdentifierType === "false")&&
			Lbpm_SettingInfo.isRemoveNodeIdentifier === "true"){
			isRemove = true;
		}else if (Lbpm_SettingInfo.isHideNodeIdentifier === "true" && Lbpm_SettingInfo.hideNodeIdentifierType  === "isRemoveNodeIdentifier"){
			isRemove = true;
		}
	}
	return isRemove;
};

/**
 * 是否隐藏所有节点编号的显示
 */
lbpm.globals.lbpmIsHideAllNodeIdentifier = function(){
	var isHideAllNodeIdentifier = false;
	if (Lbpm_SettingInfo && Lbpm_SettingInfo.isHideNodeIdentifier === "true" && Lbpm_SettingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier"){
		isHideAllNodeIdentifier = true;
	}
	return isHideAllNodeIdentifier;
};
