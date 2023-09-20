<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<script>
        var listOption = {
              contextPath: '${LUI_ContextPath}',
              modelName: 'com.landray.kmss.km.archives.model.KmArchivesMain',
              templateName: 'com.landray.kmss.km.archives.model.KmArchivesCategory',
              basePath: '/km/archives/km_archives_main/kmArchivesMain.do',
              canDelete: '${canDelete}',
              mode: 'main_scategory',
              customOpts: {

                  ____fork__: 0
              },
              lang: {
                  noSelect: '${lfn:message("page.noSelect")}',
                  comfirmDelete: '${lfn:message("page.comfirmDelete")}'
              }

          };
       	 Com_IncludeFile("list.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
         seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/spa/const'],function($,dialog,topic,spaConst) {
          	topic.subscribe(spaConst.SPA_CHANGE_VALUES, function(evt) {
          		if(LUI('changeRightBatch')) {
      				LUI('changeRightBatch').setVisible(true);
      			}
          		if(evt && evt.value && evt.value['docStatus']=='00') {
          			//废弃箱隐藏“批量修改权限按钮”
          			if(LUI('changeRightBatch')) {
          				LUI('changeRightBatch').setVisible(false);
          			}
          		}
			});
          	var cateId;
          	window.importArchives = function() {
          		/* dialog.simpleCategoryForNewFile(listOption.templateName,'/km/archives/km_archives_main/kmArchivesMain_upload.jsp?docTemplate=!{id}',
              			false,null,null,getValueByHash("docTemplate")); */
              	if(cateId == null) {
              		dialog.alert("${lfn:message('km-archives:please.choose.category')}");
              	}else {
              		 Com_OpenWindow("${LUI_ContextPath}/km/archives/km_archives_main/kmArchivesMain_upload.jsp?docTemplate="+cateId);
              	}
          	};
          	window.batchUpdate = function() {
          		var selected = [];
                  $("input[name='List_Selected']:checked").each(function(){
                      selected.push($(this).val());
                  });
                  if(selected.length == 0) {
                  	dialog.alert('${lfn:message("page.noSelect")}');
          			return;
                  }
          		var url = "/km/archives/km_archives_main/kmArchivesMain_batchUpdate.jsp?selectedIds="+selected.join(";");
          		dialog.iframe(url,"${lfn:message('km-archives:kmArchivesMain.batchUpdate')}", function(value) {
  					topic.publish('list.refresh');
  				}, {
  					"width" : 500,
  					"height" : 300
  				});
          	};
          	topic.subscribe('criteria.spa.changed',function(evt){
          		cateId = null;
          		if(LUI('btnDelete')) {
				LUI('btnDelete').setVisible(false);
			}
          		for(var i=0;i<evt['criterions'].length;i++){
				//获取分类id和类型
             	if(evt['criterions'][i].key=="docTemplate"){
	                cateId= evt['criterions'][i].value[0];
             	}
			}
          		showButtons(cateId);
          	});
          	function showButtons(categoryId){
          		if(LUI('btnDelete')){
				 var checkDelUrl = "/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall&docTemplate="+categoryId;
				 var data = new Array();
				 data.push(["delBtn",checkDelUrl]);
				 $.ajax({
	            	 url: Com_Parameter.ContextPath + "sys/authorization/SysAuthUrlCheckAction.do?method=checkUrlAuth",
	            	 dataType : 'json',
	      			 type : 'post',
	      			 data:{  data : LUI.stringify(data) },
	      			 async : false,
	      			 success : function(rtn){
	      				 if(rtn && rtn.length > 0 && rtn[0]['delBtn'] == 'true'){
	   						 LUI('btnDelete').setVisible(true);
	   					 }else{
	   						 if(LUI('btnDelete')){//修复预归档列表页js报错
	   							LUI('btnDelete').setVisible(false);
	   						 }
	   					 }
	      			 }
	             });
          		}
		 }
          	
          	//删除
    		window.delPreFileDoc = function(){
          		
   			var values = [];
			$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			/* 软删除配置 */
			var url = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteallPreFile"/>';
			var config = {
				url : url, // 删除数据的URL
				data : $.param({"List_Selected":values},true), // 要删除的数据
				modelName : "com.landray.kmss.km.archives.model.KmArchivesMain"
			};
			// 通用删除方法
			Com_Delete(config, delCallback);
		};
          		
          	//删除
		window.delDoc = function(){
			var values = [];
			$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			var delUrl = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall"/>';
			dialog.iframe('/sys/edition/import/doc_delete_iframe.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain&fdType=POST',
					"<bean:message key='ui.dialog.operation.title' bundle='sys-ui'/>",
					function (value){
                        // 回调方法
						if(value) {
							delCallback(value);
						}
					},
					{width:400,height:160,params:{url:delUrl,data:$.param({"List_Selected":values},true)}}
			);
		};
		window.delCallback = function(data){
			if(window.del_load!=null){
				window.del_load.hide();
				topic.publish("list.refresh");
			}
			dialog.result(data);
		};
		
		//确定归档
		window.confirmFile = function(fdId){
			var url = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=editPreFile&fdId="/>'+fdId;
			window.open(url,"_blank");
		};
		
		//更改分类
		window.changeCategory = function(fdId){
			dialog.simpleCategory("com.landray.kmss.km.archives.model.KmArchivesCategory", "cateId", "cateName", false,
				function(rtn){
				if(rtn != false && rtn !=null && rtn != 'null'){
					dialog.confirm('分类变更后归档操作将启动对应分类的流程审批，是否继续？',function(value){
						if(value==true){
							var url = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=updateChangeCate"/>';
							window.file_load = dialog.loading();
							$.ajax({     
					    	     type:"get",     
					    	     url:url,   
					    	     data:$.param({"fdId":fdId,"categoryId":rtn.id},true),
					    	     async:true,    //用同步方式
					    	     error: function(data){
									if(window.file_load!=null){
										window.file_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
					    	     success:function(data){
					    	    	 if(window.file_load!=null){
										window.file_load.hide(); 
									 }
					    	    	 topic.publish("list.refresh");
					    	    	 dialog.success("操作成功!");
							    }     
					        });
						}
					});
				}
			}, "分类选择", true, null, null, null);
		};
		
		
	  	//确定归档
		window.confirmFiles = function(){
			var values = [];
			$("input[name='List_Selected']:checked").each(function(){
				values.push($(this).val());
			});
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			dialog.confirm('<bean:message key="toBeArchives.tip" bundle="km-archives"/>',function(value){
				if(value==true){
					var url = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=updatePreFiles"/>';
					window.file_load = dialog.loading();
					$.ajax({     
			    	     type:"post",     
			    	     url:url,     
			    	     data: $.param({"List_Selected":values},true),    
			    	     async:true,    //用同步方式 
			    	     error: function(data){
							if(window.file_load!=null){
								window.file_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
			    	     success:function(data){
			    	    	 if(window.file_load!=null){
								window.file_load.hide(); 
							 }
			    	    	 topic.publish("list.refresh");
			    	    	 dialog.success("归档成功!");
					    }     
			        });
				}
			});
		};
		//更改分类
		window.changeCategorys = function(){
			var values = [];
			$("input[name='List_Selected']:checked").each(function(){
				values.push($(this).val());
			});
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			dialog.simpleCategory("com.landray.kmss.km.archives.model.KmArchivesCategory", "cateId", "cateName", false,
				function(rtn){
					if(rtn != false && rtn !=null && rtn != 'null'){
						dialog.confirm('分类变更后归档操作将启动对应分类的流程审批，是否继续？',function(value){
							if(value==true){
								var url = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=updateChangeCates"/>';
								window.file_load = dialog.loading();
								$.ajax({     
						    	     type:"post",     
						    	     url:url,   
						    	     data:$.param({"List_Selected":values,"categoryId":rtn.id},true),
						    	     async:true,    //用同步方式
						    	     error: function(data){
										if(window.file_load!=null){
											window.file_load.hide(); 
										}
										dialog.result(data.responseJSON);
									},
						    	     success:function(data){
						    	    	 if(window.file_load!=null){
											window.file_load.hide(); 
										 }
						    	    	 topic.publish("list.refresh");
						    	    	 dialog.success("归档成功!");
								    }     
						        });
							}
						});
					}
			}, "分类选择", true, null, null, null);
		};
     });
</script>
