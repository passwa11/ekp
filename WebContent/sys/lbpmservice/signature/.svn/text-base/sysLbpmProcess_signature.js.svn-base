//流程页签初始化时将签章按钮插入其他按钮后
lbpm.onLoadEvents.delay.push(function () {
	if (typeof(seajs) != 'undefined') {
		if(lbpm && lbpm.approveType == "right"){
			var html = '<div class="lui-lbpm-opinion-signature lui-lbpm-opinion-btn" title="'+lbpm.constant.signatureName+'" onclick="signature();">';
			if(Com_Parameter.dingXForm === "true") {
				html += ' <i><div style="display: none" class="lui-lbpm-option-btn-text">电子签名</div></i>';
			}else{
				html += ' <i></i>';
			}
			html += '</div>';
			var btnLen= $('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').length;
			//如果超过3个按钮，则按三个按钮计算，并把当前按钮放置到更多中
			if(btnLen-1 >= 3){
				//转移最后一个到更多中
				$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").append("<li>"+$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').eq(btnLen-2).prop("outerHTML")  + "</li>");
				$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').eq(btnLen-2).remove();
				
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
				$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").before(html)
				$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').css({
			        'width':100/btnLen+'%'
			    });
			}
		}else{
			var func = "/km/signature/km_signature_main/kmSignatureMain_showSig.jsp";
			var html = '&nbsp;&nbsp;';
			html += '<a href="javascript:;" class="com_btn_link" id="signature" onclick="signature();">';
			html += lbpm.constant.signatureName;
			html += '</a>';
			$("#optionButtons").append(html);
		}
		
	}else{//非新UED模块屏蔽流程签章功能
		document.getElementById("showSignature").style.display = "none";
	}
	if(lbpm && lbpm.events && lbpm.events.addListener){
		lbpm.events.addListener(lbpm.constant.EVENT_HANDLERTYPECHANGE, function(param){
			if(param!='' && param!=lbpm.constant.PROCESSORROLETYPE){
				$("#showSignature").hide();
			}else{
		 		// #60715 起草节点隐藏审批意见框
		 		var showSignature = document.getElementById("showSignature");
		 		if(lbpm.nowNodeId == "N2" && Lbpm_SettingInfo.isDraftNodeDisplayOpinion == "true") {
		 			if(lbpm.constant.DOCSTATUS != "11" && Lbpm_SettingInfo.isNewPageAndDraftsManRecallPage == "true") {
		 				lbpm.globals.hiddenObject(showSignature, true);
		 			}
		 			if(lbpm.constant.DOCSTATUS == "11" && Lbpm_SettingInfo.isRejectPage == "true") {
		 				lbpm.globals.hiddenObject(showSignature, true);
		 			}		
		 		} else {
		 			if(fileIds.length > 0){
		 				$("#showSignature").show();
		 			}else{
		 				$("#showSignature").hide();
		 			}
		 		}
			}
		});
	}
	if(lbpm && lbpm.constant && lbpm.constant.ROLETYPE!='' && lbpm.constant.ROLETYPE!=lbpm.constant.PROCESSORROLETYPE){
		$("#showSignature").hide();
	}
	if (typeof(seajs) != 'undefined') {//非新UED模块屏蔽流程签章功能
		if(lbpm && lbpm.allMyProcessorInfoObj && lbpm.allMyProcessorInfoObj.length>0 && !lbpm.hideDescriptionOnDraftNode){
			loadAutoSignaturePic();
		}
		if(lbpm && lbpm.approveType == "right" && fileIds.length == 0){
			$("#showSignature").hide();
		}
	}
});
if (typeof(seajs) != 'undefined') {//非新UED模块屏蔽流程签章功能
	var fileIds = [];
	//loadAutoSignaturePic();
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {
		//新建
		window.signature = function() {
			var url = "/km/signature/km_signature_main/kmSignatureMain_showSig.jsp";
			dialog.iframe(url,lbpm.constant.signatureName,function(rtn){
				if(rtn!=null){
					var file = {
						fdAttId:rtn.attId
		            };
					signatureImgShow(file); 
				}
			},{width:800,height:270});
		};
	});
	
    //签章图片显示
	function signatureImgShow(file){
		if (!file || !file.fdAttId){
			return ;
		}
		var flag = true;
		if(fileIds.length>0){
            for(var i = 0;i < fileIds.length;i++){
				if(fileIds[i].fdAttId == file.fdAttId){
					flag = false;
				}
            }
		}
        if(flag){
			fileIds.push(file);
			var imageUl = $("#signaturePicUL");
			var html = '<li id="'+file.fdAttId+'"><img width="100" height="75" src="'+Com_Parameter.ContextPath+'sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId='+file.fdAttId+'"/>';
			html += '<span id="'+file.fdAttId+'" class="btn_canncel_img" onclick="deletex(this);"></span></li>';
			if(imageUl.length > 0){
				if(imageUl[0].innerHTML==""){
					imageUl.html(html);
				}else{
					imageUl.append(html);
				}
			}else{
				var rowhtml = '<kmss:ifModuleExist path="/km/signature/">';
				rowhtml += '<tr id="showSignature"><td class="td_normal_title" width="15%">'+lbpm.constant.signatureSigPic+'</td>';
				rowhtml += '<td colspan="3" width="85%" id="signaturePic"><ul id="signaturePicUL" class="clearfloat lui_sns_signatureList">'+html+'</ul></td></tr>';
				rowhtml += '</kmss:ifModuleExist>';
				$("#descriptionRow").after(rowhtml);
			}
			$("#showSignature").show();
        }else{
			alert(lbpm.constant.signatureReupload);
        }
	}
	
    //加载自动签名：加载默认个人签名的并且开启免密签名，流程新建的是自动加载出来不需要弹框选择
	function loadAutoSignaturePic(){
		var autoSignPicUrl = Com_Parameter.ContextPath + "km/signature/km_signature_main/kmSignatureMain.do?method=confirmSignature";
		$.ajax({
		     type:"post",
		     url:autoSignPicUrl,
		     data:{"autoSignature":true},
		     async:true,
		     success:function(data){
				if (data.flag == '1') {
					var file = {fdAttId:data.attId };
					signatureImgShow(file);
	            };
			}
	    });
	}
	
	//删除附件
	function deletex(obj){
		//debugger;
		var tmpfileIds = [];
        for(var i= 0;i<fileIds.length;i++){
	        if(fileIds[i].fdAttId != obj.id){
	        	tmpfileIds.push(fileIds[i]);
		    }
        }
        fileIds = tmpfileIds;
        if(confirm(lbpm.constant.signatureConfirm)){
	        $("li[id='"+obj.id+"']").remove();
		}
        if(fileIds.length == 0 && lbpm && lbpm.approveType == "right"){
    		$("#showSignature").hide();
    	}
	}

	//监听流程提交事件，绑定签章信息, #67801 修改为confirm事件，公文套红签章提交时有可能取消，导致签名重复
	Com_Parameter.event["confirm"].push(function(){     //流程提交生成附件信息
		var flag = false;
		//#60715 起草节点隐藏审批意见框
		if(lbpm.nowNodeId == "N2" && Lbpm_SettingInfo.isDraftNodeDisplayOpinion == "true"){
			if(lbpm.constant.DOCSTATUS != "11" && Lbpm_SettingInfo.isNewPageAndDraftsManRecallPage == "true"){
				fileIds.length = 0;
			}else if(lbpm.constant.DOCSTATUS == "11" && Lbpm_SettingInfo.isRejectPage == "true"){
				fileIds.length = 0;
			}else{
				
			}
		}else{
			
		} 
		if(fileIds.length>0){
			var fdAttIds = "";
			for(var i= 0;i<fileIds.length;i++){
				if(i != fileIds.length-1){
					fdAttIds += fileIds[i].fdAttId + ";";
				}else{
					fdAttIds += fileIds[i].fdAttId;
				}
			}
			var fdKey = lbpm.constant.signatureAuditNoteFdId;
			var fdModelId = lbpm.modelId;
			var fdModelName = lbpm.modelName;
			var checkUrl = Com_Parameter.ContextPath + "km/signature/km_signature_main/kmSignatureMain.do?method=submitSignature";
			$.ajax({
			     type:"post",
			     url:checkUrl,
			     data:{"fdAttIds":fdAttIds,"fdKey":fdKey,"fdModelId":fdModelId,"fdModelName":fdModelName},
			     async:false,
			     success:function(data){
			    	 flag = data.flag;
				}
		    });
		}else{
			flag = true;
		}
		return flag;
	});
}