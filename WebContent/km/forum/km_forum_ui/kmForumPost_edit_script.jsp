<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
		seajs.use(['lui/dialog','lui/jquery','lui/util/str'], function(dialog,$,strutil) {
				//判断是否可以匿名
				window.getIsAnonymous = function(fdForumId) {
					if(fdForumId == null || fdForumId ==""){
                         return;
					 }
					var url = "${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=getIsAnonymous";
					var data ={fdForumId:fdForumId};
					var anonymousDiv = $("#isAnonymous");
					LUI.$.ajax({
						url: url,
						type: 'get',
						dataType: 'json',
						async: false,
						data: data,
						success: function(data, textStatus, xhr) {
							if(data==true){
								//显示
								if( $("#isAnonymous").is(":hidden")){
								   anonymousDiv.show();
								}
							}else{
								//隐藏区域
								if( $("#isAnonymous").is(":visible")){
								   anonymousDiv.hide();
								   anonymousDiv.find("input[name='fdIsAnonymous']").val(null);
								   anonymousDiv.find("[name$='fdIsAnonymous']:checkbox").attr("checked", false);
								}
							}
						}
					});
				   
			    };

			//获取富文本框内容
		    window.RTF_GetContent = function(prop){
		    	    var instance=CKEDITOR.instances[prop];
		    	    if(instance){
		    	          return instance.getData();
		    	    }
		    	    return "";
		    };
			
			//弹出框选择后回调
            window.afterSelect = function(rtn){
                 if(rtn == null || rtn==false){
                     return;
                 }
                 //返回列表href变化
                 if($("#returnList").attr("href")!=null){
	                 var newHref = Com_SetUrlParameter($("#returnList").attr("href"),"categoryId",rtn.id);
	                 $("#returnList").attr("href",newHref);
                 }
                 $("input[name='fdForumId']").val(rtn.id);
                 $("input[name='fdForumName']").val(decodeURIComponent(rtn.name)).attr('title',decodeURIComponent(rtn.name));
                 //更换版块显示匿名发帖区域
                 getIsAnonymous(rtn.id);
	               //提交表单校验
	 	    		for(var i=0; i<Com_Parameter.event["submit"].length; i++){
	 	    			if(!Com_Parameter.event["submit"][i]()){
	 	    				break;
	 	    			}
	 	    		}
                };
			
			//新建
			window.addDoc = function(isForwardQuickEdit) {
			     dialog.simpleCategoryForNewFile({modelName:"com.landray.kmss.km.forum.model.KmForumCategory",
                    									url:"/km/forum/km_forum_cate/simple-category.jsp",
                    									action:afterSelect});
					};
            //提交表单
		    window.submitKmForumPostForm=function(method, forumId) {
				    	//提交表单校验
			    		var v=RTF_GetContent("docContent");
			    		var docSubject = $('input[name="docSubject"]').val();
				    	if(method !="saveDraft" && method !="updateDraft")	{
				    		if(v==null ||v=="") {
								dialog.alert("<bean:message  bundle='km-forum' key='kmForumPost.topicNotEmpty'/>");
								return;
							}
				    		//检测编辑器多个空格
				    		v = v.replace(/&nbsp;/g,"").replace(/<p>/g,"").replace(/<\/p>/g,"").replace(/\s+/g,"");
				    		if(v==null ||v=="") {
								dialog.alert("<bean:message  bundle='km-forum' key='kmForumPost.topicNotEmpty'/>");
								return;
							}
				    	}				    	
				    	var emoji=/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;
				    	//检测是否存在emoji表情
			    		if(emoji.test(docSubject)) {
							dialog.alert("<bean:message  bundle='km-forum' key='kmForumConfig.emoji.warn.topic'/>");
							return;
						}
			    							    	
			    		if(emoji.test(v)) {
			    			dialog.alert("<bean:message  bundle='km-forum' key='kmForumConfig.emoji.warn'/>");
							return;
						}

						//通知方式校验
						var fdNotifyType = document.getElementsByName("fdNotifyType")[0].value;
	    				if(null == fdNotifyType ||fdNotifyType==""){
	    				$("#fdNotifyType").show();
	    				$("input[type='checkbox']").focus();
	    				return false;
	    				}else{
	    					$("#fdNotifyType").hide();
	    				//return true;
	    				}
						/*var getCheck = document.getElementsByName("fdNotifyType");
						var isCheck = getCheck[0];					
						if(isCheck.value == ""){
							dialog.alert("<bean:message  bundle='km-forum' key='KmForumPost.notify.fdNotifyType.warn'/>");
							//alert("请选择通知方式");
							return;								
						}*/
						//为兼容新UED，暂时把帖子内容校验去掉
						//RTF_UpdateLinkedFieldToForm("docContent");
						Com_Submit(document.kmForumPostForm, method, forumId);
					};
					
		   //通知方式按钮事件
			window.clickCheckBox=function(obj){
				if(obj.checked){	
					    document.getElementById("id_notify_type").style.display="";
					    document.getElementsByName("fdIsNotify")[0].value='1';
					}else{
						document.getElementsByName("fdIsNotify")[0].value='0';
						document.getElementById("id_notify_type").style.display="none";	
					}
				};
			//匿名按钮事件
			window.clickIsAnonymous = function(obj){
				var _fdNotifyTypeNode = document.getElementsByName("_fdIsNotify")[0];
				var _fdIsOnlyViewNode = document.getElementsByName("_fdIsOnlyView")[0];
				if(obj.checked){
					_fdNotifyTypeNode.checked=false;
					document.getElementsByName("fdIsNotify")[0].value='';
				    clickCheckBox(_fdNotifyTypeNode);
				    _fdNotifyTypeNode.disabled='disabled';
				    _fdIsOnlyViewNode.checked=false;
				    document.getElementsByName("fdIsOnlyView")[0].value='';
				    _fdIsOnlyViewNode.disabled='disabled';
				    
				}else{
					_fdNotifyTypeNode.checked=true;
					document.getElementsByName("fdIsNotify")[0].value='1';
					clickCheckBox(_fdNotifyTypeNode);
					$(_fdNotifyTypeNode).removeAttr('disabled');
					$(_fdIsOnlyViewNode).removeAttr('disabled');
				}
			};	
			
				//自适应高度
		    	window.dyniFrameSize = function() {
		    		try {
		    			// 调整高度
		    			//var arguObj = document.getElementById("quickReply_div");
		    			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
		    				var ckeHeight;
		    				try{
		    					ckeHeight = $(".cke_dialog").height() + 50;
		    					//alert($(".cke_dialog") + "---"+ ckeHeight);
		    					window.frameElement.style.height = (ckeHeight)+45+ "px";
		    				}catch(e1){
		    					
		    				}
		    				//var arguHeight = arguObj.offsetHeight;
		    				//if(ckeHeight && ckeHeight>arguHeight){
		    				//	arguHeight = ckeHeight;
		    				//}
		    				
		    				
		    				//window.frameElement.style.width = (arguObj.offsetWidth) + "px";
		    			}
		    		} catch(e) {
		    		}
		    	};
		    	
		    	$(function(){
		    		var fdIsAnonymous = document.getElementsByName("fdIsAnonymous");
					if(fdIsAnonymous && fdIsAnonymous.length>0){
						var value = fdIsAnonymous[0].value;
						if(value=='1'){
							clickIsAnonymous(document.getElementsByName("_fdIsAnonymous")[0]);
						}	
					}
		    	});
		    
		  });
</script>