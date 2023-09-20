 LUI.ready(function(){	
	seajs.use(['lui/jquery','lang!','lang!sys-lbpmservice-operation-drafter','lang!sys-lbpmservice'],function($,lang,operationDrafter,lbpmservice){
		//驳回状态下不显示流程操作
         if(formInitData['docStatus'] == '10' || formInitData['docStatus'] == '11'){
             setTimeout(function(){
            	 if(formInitData['approveModel']=='right'){//右侧审批模式
            		 $("div .lui_widget_btn_txt").each(function () {
   						if(lang['button.submit'] == $(this).html()){
   							$(this).parent().parent().parent().hide();
   						}
             		 });
            	 }else if(formInitData['approveModel']=='tiled'){//操作按钮平铺模式
            		 $("div .lui_widget_btn_txt").each(function () {
    						if(operationDrafter['lbpmOperations.fdOperType.draft.submit'] == $(this).html()){
    							$(this).parent().parent().parent().hide();
    						}
              		 });
             	 }else if(formInitData['approveModel']=='dialog'){//简单弹出框模式
            		 $("div .lui_widget_btn_txt").each(function () {
 						if(lbpmservice['lbpmNode.processingNode.identifyRole.button.approve'] == $(this).html()){
 							$(this).parent().parent().parent().hide();
 						}
           		 });
             	 }else{//默认模式
            		 if($(".com_ap_bar2_centre").length > 0 && $("#descriptionRow").length > 0){
                         $(".com_ap_bar2_centre").hide();
                         $("#descriptionRow").hide();
                     } 
            	 }
             },1);
         }else if(formInitData['docStatus'] == '30'){
			 if(formInitData['approveModel']=='right'){//右侧审批模式
			 	setTimeout(function(){
			 		var len=$(".lui-fm-tab-title").length;
			 		for(var i=0;i<len;i++){
						$(".lui-fm-tab-title").eq(i).parent().attr("style","margin-left:40px;");
					}
				},300);
			 }
		 }
	})
});
