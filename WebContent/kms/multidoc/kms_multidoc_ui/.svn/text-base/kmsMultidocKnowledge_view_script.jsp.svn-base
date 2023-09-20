<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">

	// flash滚轮事件
	function mouseWheel(evt) {
		evt = window.event || evt;
		try {
			document.getElementById("att_swf_viewer").mouseWheelScroll(
					evt.wheelDelta);
		} catch (e) {
		}
		if (evt.preventDefault) {
			evt.preventDefault();
		} else {
			evt.returnValue = false;
		}
	}

	LUI.ready( function() {
 
		function slide(evt) {
			var $parent = LUI.$(evt.target);
			while ($parent.length > 0) {
				if ($parent.attr('data-emit')) {
					var emit = $parent.attr('data-emit');
					if ($parent.hasClass('lui_multidoc_slideDown')) {
						$parent.removeClass('lui_multidoc_slideDown');
						$parent.addClass('lui_multidoc_slideUp');
						$parent.parent().next('[data-on="' + emit + '"]').each(
								function() {
									LUI.$(this).slideDown('slow');
								});
						$('#detail_span').html('${lfn:message('kms-multidoc:kmsMultidoc.slideUp')}');
						break;
					}
					if ($parent.hasClass('lui_multidoc_slideUp')) {
						$parent.removeClass('lui_multidoc_slideUp');
						$parent.addClass('lui_multidoc_slideDown');
						$parent.parent().next('[data-on="' + emit + '"]').each(
								function() {
									LUI.$(this).slideUp('slow');
								});
						$('#detail_span').html('${lfn:message('kms-multidoc:kmsMultidoc.slideDown')}');
						break;
					}
				}
				$parent = $parent.parent();
			}
		}
		LUI.$(document).click( function(evt) {
			slide(evt);
		});
	
	    
		LUI.$("#unfoldOrRetract").mouseover(function(){
			$("#unfoldOrRetract").removeClass("lui_knowledge_view_hidden_suspension");
			$("#unfoldOrRetract").addClass("lui_knowledge_view_suspension");
			
		});
		LUI.$("#unfoldOrRetract").mouseout(function(){
			$("#unfoldOrRetract").removeClass("lui_knowledge_view_suspension");
			$("#unfoldOrRetract").addClass("lui_knowledge_view_hidden_suspension");
		});
		 
		
	});
	//纠错
	function changeErrorCorrection(){
		 var url = '<c:url value="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=add&fdModelId=${kmsMultidocKnowledgeForm.fdId }&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge&fdKnowledgeType=1"/>';		 
		 window.open(url);
	}	
	//纠错记录删除
	function delErrorCorrection(obj){
		seajs.use(['lui/dialog', 'lui/topic'],function(dialog) {
 	 		dialog.confirm("${lfn:message('kms-common:kmsCommonDocErrorCorrection.confirmDeleteView')}", function(flag) {
 	 	 		if(flag) {
 	 	 			var url = '<c:url value="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=delete&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc&fdId="/>'+obj;
 	 	 			LUI.$.ajax( {
 						url : url,
 						success : function(data) {
							seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
								dialog.success("${lfn:message('kms-common:kmsCommonDocErrorCorrection.delete')}");
	 	 	 					topic.channel('all_error').publish('list.refresh');
							});
 						}
 					});
				}}
			);
	 	});
		var evt=event||windows.event;
		evt.cancelBubble = true;
		return false;
	}
	//恢复
	function confirmRecover() {
		var html =['<div class="lui_knowledge_reason_box">',
						'<div class="lui_knowledge_recover_title">${lfn:message("kms-knowledge:kmsKnowledge.reason.fill")}</div>',
						'<table class="lui_reason_text"><tr><td>',
							'<textarea  style="height:150px;width:500px" validate="required maxLength(800)"></textarea><span class="txtstrong">*</span>',
						'</td></tr></table>',
					'</div>'].join(" ");
		seajs.use(['lui/dialog','lui/jquery'],function(dialog, $) {
			dialog.build(
				{
					id:'recoverDialog',
					config: {
						height: 300,
						width: 600,
						lock: true,
						title: "${lfn:message('kms-knowledge:kmsKnowledge.dialog.recover')}",
						content: {
							type: "html",
							html: html,
							buttons :  [{
								name : "${lfn:message('sys-ui:ui.dialog.button.ok')}",
								value : true,
								focus : true,
								fn : function(value, _dialog) {
									var validator = $KMSSValidation($('.lui_reason_text')[0]);
									if(!validator.validate())
										return;
									var reason = $('.lui_reason_text textarea').val(),
										loading = dialog.loading();
									_dialog.hide(value);
									$.ajax(
										{
										    type: 'post',
											url: "${ LUI_ContextPath}/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=recover&fdId=${param.fdId}",
											data: {description: reason} ,
											async : false,
											success: function(data) {
												loading.hide();
												if(data.flag) {
													dialog.success("${lfn:message('return.optSuccess')}");
													setTimeout(function(){window.location.reload();}, 500);
												}
												else 	dialog.failure("${lfn:message('return.optFailure')}");
											}
										}
									);
								}
							}, {
								name : "${lfn:message('sys-ui:ui.dialog.button.cancel')}",
								value : false,
								styleClass : 'lui_toolbar_btn_gray',
								fn : function(value, dialog) {
									dialog.hide(value);
								}
							}]
						}
					},
					callback:function() {
						
					}
				}
			).show();
		});
	} 
	//回收
	function confirmRecycle(msg, url, target) {
		seajs.use(['lui/dialog'],function(dialog) {
			dialog.confirm("${lfn:message('kms-knowledge:kmsKnowledge.confirm.recycle')}", function(flag){
				if(flag) {
					Com_OpenWindow(
							'kmsMultidocKnowledge.do?method=recycle&fdId=${param.fdId}','_self');
				}
			} );
		});
	}
	
	//删除
	function confirmDelete() {
		var delUrl = '<c:url value="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do"/>?method=delete&fdId=${param.fdId}';
		Com_Delete_Get(delUrl, 'com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge');
	}
	//调整属性
	 function editProperty() {
		seajs.use(['lui/dialog'],function(dialog){
			dialog.iframe('kmsMultidocKnowledge.do?method=editProperty&type=property&fdId=${param.fdId}', 
							"${lfn:message('kms-multidoc:kmsMultidoc.button.editProperty')}",
							 null, 
							 {	
								width:720,
								height:230,
								buttons:[
									{
										name : "${lfn:message('button.ok')}",
										value : true,
										focus : true,
										fn : function(value,_dialog) {
											//获取弹出窗口的window对象
											var winObj = LUI.$('#dialog_iframe').find('iframe')[0].contentWindow; 
											//验证
											if(!editProValidate(winObj)) {
												return;
											}
											var loading = dialog.loading();
											//获取弹出窗口的document对象里面的form
											var proObj = LUI.$('#dialog_iframe').find('iframe')[0].contentDocument.getElementsByName('kmsMultidocKnowledgeForm')[0];
											var eFlag = editProSubmit(proObj,winObj);
											if(eFlag != null && eFlag=='yes'){
												_dialog.hide();
												loading.hide();
												dialog.success("${lfn:message('kms-multidoc:kmsMultidoc.editPropertySuccess')}");
												setTimeout(function(){window.location.reload();}, 500);												
											} else {
												_dialog.hide();
												loading.hide();
												dialog.success("${lfn:message('kms-multidoc:kmsMultidoc.editPropertyFailure')}");
											}
										}
									}, {
										name : "${lfn:message('button.cancel')}",
										value : false,
										fn : function(value, _dialog) {
											_dialog.hide();
										}
									} 

								]
							}
			);
		});
	 }
	//调整属性异步处理
	 function editProSubmit(_obj) {
		 	var editFlag;
		 	LUI.$.ajax({
				url: '${ LUI_ContextPath}/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=updateProperty&fdId=${param.fdId}',
				type: 'POST',
				dataType: 'json',
				async: false,
				data: LUI.$(_obj).serialize(),
				success: function(data, textStatus, xhr) {
					if (data && data['flag'] === true) {
						//调整成功
						editFlag = 'yes';
					}
				},
				error: function(xhr, textStatus, errorThrown) {
					//调整失败
					editFlag = 'no';
				}
			});
			if(editFlag != null) 
				return editFlag;
	}
	//调整属性验证
	function editProValidate(_winObj) {
		//验证必填项
	 	if(_winObj != null  && !_winObj.Com_Parameter.event["submit"][0]()) {
	 		return false;
	 	}
	 	else return true;
	}

	//显示标签
	var currentTags="${fn:escapeXml(kmsMultidocKnowledgeForm.sysTagMainForm.fdTagNames)}";
	//添加标签
	function addTags(TagsType) {
		var title;
		var contentTitle;
		var value;
		if (TagsType == '2') { // 编辑
			title = "${lfn:message('kms-multidoc:kmsMultidoc.button.editTag')}";
			contentTitle = "${lfn:message('kms-multidoc:kmsMultidoc.editTagsTips')}";
			value = currentTags;
		} else if (TagsType == '3') { //添加
			title = "${lfn:message('kms-multidoc:kmsMultidoc.button.addTag')}";
			contentTitle = "${lfn:message('kms-multidoc:kmsMultidoc.addTagsTips')}";
			value = "";
		}
		seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
			dialog.build( {
				id : 'addTags',
				config : {
					height: 50,
					width:  420,
					lock : true,
					cache : false,
					title : title,
					content : {
						type : "html",
						html : [
								'<div id="tagEdit" class="lui_multidoc_tagEdit">',
								'<span id="tagTips">',contentTitle,'</span>',
								'<input type="text" id="multidoc_tagInput" value="',value,'" style="width:90%" onblur="checkEmpty(this);"></input>',
								'<a href="#" onclick="Dialog_TreeList(true,null,null,\' \',\'sysTagCategorTreeService&type=1&fdCategoryId=!{value}\', \'<bean:message key="sysTagTag.tree" bundle="sys-tag"/>\',\'sysTagByCategoryDatabean&type=getTag&fdCategoryId=!{value}\',afterSelectValue, \'sysTagByCategoryDatabean&key=!{keyword}&type=search\')">',
								 '<span><bean:message key="dialog.selectOther"/>',
						 		  '</span></a>',
						 		  '<span id="tag_empty_tip" class="tag_empty_tip"><bean:message key="kmsMultidoc.button.emptyTag.tip" bundle="kms-multidoc"/></span>',
								'</div>'
							   ].join(" "),
					    iconType : "",
						buttons : [ {
							name : "${lfn:message('button.ok')}",
							value : true,
							focus : true,
							fn : function(value,_dialog) {
								var input =  LUI.$('#multidoc_tagInput').val();
								var inputVal = $.trim(input);
								if(TagsType == '3' && inputVal == ""){
									$("#tag_empty_tip").show();
									return;
								}
								
								_dialog.hide();
								var loading = dialog.loading();
								var updateTagsFlag = updateTags(input, TagsType);
								if( updateTagsFlag != null && updateTagsFlag=='1') {									
									loading.hide();
									if(TagsType == '3')
										dialog.success("${lfn:message('kms-multidoc:kmsMultidoc.addTagsTipsSucccess')}");
									else if(TagsType == '2')
										dialog.success("${lfn:message('kms-multidoc:kmsMultidoc.editTagsTipsSucccess')}");
									setTimeout(function(){
										Com_OpenWindow('kmsMultidocKnowledge.do?method=view&fdId=${param.fdId}','_self');
										}, 1000);
								}
								
							}
						}, {
							name : "${lfn:message('button.cancel')}",
							styleClass:"lui_toolbar_btn_gray",
							value : false,
							fn : function(value, dialog) {
								dialog.hide();
							}
						} ]
					}
				},

				callback : function(value, dialog) {

				},
				actor : {
					type : "default"
				},
				trigger : {
					type : "default"
				}

			}).show(); 
		});
	}

	//校验空标签
	function checkEmpty(evt){
		var evtVal = $(evt).val();
		if($.trim(evtVal) != ""){
			$("#tag_empty_tip").hide();
		}
	}
	
	 //更新标签
	function updateTags(tags,type){
		 //ajax 
		var url="kmsMultidocKnowledgeXMLService&fdId=${kmsMultidocKnowledgeForm.fdId}&type="+type+"&tags="+encodeURIComponent(tags);
		var data = new KMSSData(); 
		data.SendToBean(url,function defaultFun(rtnData){ 
			
		});	
		return 1;    
	 }

	function afterSelectValue(rtnVal){
		if(rtnVal==null)
			return;
		var input =  LUI.$('#multidoc_tagInput').val().trim();
		var nameStr=input.split(" ");
		var name='';
		for(var i=0;i<rtnVal.GetHashMapArray().length;i++){
			var newName=rtnVal.GetHashMapArray()[i]['name'];
					var isExist=1;
					for(var j=0;j<nameStr.length;j++){
						var oldName=nameStr[j];
						if(newName==oldName){
							isExist=0;
						}
					}
					if(isExist==1){
						input = input+" "+ newName;
					}
		}
		LUI.$('#multidoc_tagInput').val( input.trim()) ;
	}


	//置顶
	function setTop(){
		seajs.use(['lui/dialog'],function(dialog){
						dialog.iframe("/kms/multidoc/kms_multidoc_ui/kms_multidoc_index_setTop.jsp?docIds=${param.fdId}", 
										"${lfn:message('kms-multidoc:kmsMultidoc.setTop')}",
										 null, 
										 {	
											width:720,
											height:370,
											buttons:[
														{
															name : "${lfn:message('button.ok')}",
															value : true,
															focus : true,
															fn : function(value,_dialog) {
																commitForm(_dialog);
															}
														}, {
															name : "${lfn:message('button.cancel')}",
															styleClass:"lui_toolbar_btn_gray",
															value : false,
															fn : function(value, _dialog) {
																_dialog.hide();
															}
														} 
													]
										}
						); 
			});
	}

	function commitForm(_dialog){
		var fdSetTopReason = LUI.$('#dialog_iframe').find('iframe')[0].contentDocument.getElementsByName('fdSetTopReason')[0].value;
		if (fdSetTopReason == "") {
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert("${lfn:message('kms-multidoc:kmsMultidoc.setTopReason')}");
			});
	 		return false;
	 	}
		var fdSetTopLevel = LUI.$('#dialog_iframe').find('iframe')[0].contentDocument.getElementsByName('fdSetTopLevel');
		for(var i = 0; i < fdSetTopLevel.length; i++){
			if(fdSetTopLevel[i].checked){
				fdSetTopLevel=fdSetTopLevel[i].value;
				break;
			}
		}
	 	fdSetTopReason = encodeURIComponent(fdSetTopReason);
	 	var categoryId = "${kmsMultidocKnowledgeForm.docCategoryId}";
	 	LUI.$.ajax({
                url: '<c:url value="/kms/multidoc/kms_multidoc_index/kmsMultidocKnowledgeIndex.do" />?method=setTop&'+
	 						'docIds=${param.fdId}&fdSetTopLevel='+fdSetTopLevel+'&fdSetTopReason='+fdSetTopReason+'&categoryId='+categoryId,
                type: 'post',
                success: function(data){
                	data=$.parseJSON(data);
                	if(data["hasRight"]== true){
                		var top = Com_Parameter.top || window.top;
	                	var topWinHref =  top.location.href; 
	                	if(topWinHref.indexOf("method=view") < 0){
	                		_dialog.hide();
	                		seajs.use(['lui/dialog'],function(dialog){
	            				dialog.success("${lfn:message('kms-multidoc:kmsMultidoc.executeSucc')}",
											'#listview');
	            			});
	                		setTimeout(function(){window.location.reload();}, 500);	
	                	}else{
	                		_dialog.hide();
	                		seajs.use(['lui/dialog'],function(dialog){
	                			dialog.success("${lfn:message('kms-multidoc:kmsMultidoc.executeSucc')}",
											'#listview');
	            			});
	                		setTimeout(function(){window.location.reload();}, 500);	
	                	}
                	}else{
                		setTopFail(_dialog);
                	}
                }
   		 }); 
	}

	function setTopFail(_dialog){
		_dialog.hide();
		seajs.use(['lui/dialog'],function(dialog){
			dialog.alert("${lfn:message('kms-multidoc:kmsMultidoc.noRight')}");
		});
	}
	
	//取消置顶
	function cancelTop(){
		seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery', 'lui/util/env'],
				function(dialog, topic, $, env) {
					dialog.confirm("${lfn:message('kms-multidoc:kmsMultidoc.cancelTop')}", function(flag, d) {
						if (flag) {
							var loading = dialog.loading();
							$.post(env.fn.formatUrl('/kms/multidoc/kms_multidoc_index/kmsMultidocKnowledgeIndex.do?method=cancelTop&templateId=${kmsMultidocKnowledgeForm.docCategoryId}&docIds=${param.fdId}'),
									null, function(data, textStatus,
													xhr) {
												if (data["hasRight"]== true) {
													loading.hide();
													dialog.success("${lfn:message('kms-multidoc:kmsMultidoc.executeSucc')}",
															'#listview');
													setTimeout(function(){window.location.reload();}, 500);	
												} else {
													loading.hide();
							                		dialog.alert("${lfn:message('kms-multidoc:kmsMultidoc.noRight')}");
												}
											}, 'json');
						}
					});
				}
		);
	}
	window.fdPraiseTargetPerson = "${kmsMultidocKnowledgeForm.docAuthorId}";
	//编辑
	function toEditView(){
		window.location.href="${LUI_ContextPath }/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=edit&fdId=${param.fdId}"
	}

	/**
	 * 附件权限申请
	 */
	function kmsKnowledgeBorrowAttAuthApplication(fdId){
		var url = "${LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do?method=add&fdDocId="+fdId;
		window.open(url,"_blank");
	}
	
</script>
