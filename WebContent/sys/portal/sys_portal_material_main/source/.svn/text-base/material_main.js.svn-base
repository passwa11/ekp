Com_IncludeFile("sysAttMain_MessageInfo.jsp?locale=" + Com_Parameter.__sysAttMainlocale__, Com_Parameter.ContextPath + "sys/attachment/sys_att_main/", 'js', true);

seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function ($, dialog, topic, dialogCommon) {
    /******************
    ** 功能 更改素材类型
    ******************/
    function changeMainType(type) {
        var url = Com_Parameter.ContextPath + "sys/portal/sys_portal_material_main/index.jsp?type=" + type;
        window.location.href = url;
    }
    window.changeMainType =changeMainType;
    /******************
     ** 功能 预览
     ******************/
    function onPreview(imgUrl, name) {
        var a = imgUrl.split("fdId=");
        dialog.iframe('/sys/portal/sys_portal_material_main/import/preview_dialog.jsp?imgUrl=' + a[1],
        	Attachment_MessageInfo["sysAttMain.preview"] , function (value) {

            }, { width: 900, height: 600 }
        );
    }
    window.onPreview =onPreview;
    /******************
     ** 功能 图片编辑（跳转）
     ******************/
    function onEdit(fdId) {
        dialog.iframe('/sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=toEdit&type=pic&fdId='+fdId,
        	Attachment_MessageInfo["sysAttMain.material.edit.pic"] , function (value) {
        		topic.publish("list.refresh");
            }, { width: 900, height: 600 }
        );

    }
    function onEditIcon( fdId) {
        dialog.iframe('/sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=toEdit&type=icon&fdId='+fdId,
        	Attachment_MessageInfo["sysAttMain.material.edit.icon"], function (value) {
        		topic.publish("list.refresh");
            }, { width: 300, height: 400 }
        );

    }
    window.onEdit =onEdit;
    window.onEditIcon =onEditIcon;
    /******************
     ** 功能 图片编辑
     ******************/
    var XSSPattern = new RegExp("[$&(){}':,\\[\\].<>]","g");
    window.validStrXss=function(str){
      return   str.match(XSSPattern)
    }

    function validXss(event){
        var $ele = $(event.target);
        var str = $ele.val();
        if(str.match(XSSPattern)){
            $ele.parent().parent().addClass("validError");
        }else{
            $ele.parent().parent().removeClass("validError");
        }
    }
    window.validXss =validXss;
    function filterXss(str) {
        return str.replace(XSSPattern, '');
    }
    function doEditSave(fdId,attId){
    	var fdName = $("input[name='fdName']").val();
        var fdTags = $("input[name='fdTags']").val();
        if(fdName.match(XSSPattern)||fdTags.match(XSSPattern)){
            alert(Attachment_MessageInfo["sysAttMain.material.illegal.name"]);
            return false;
        }
        fdName = filterXss(fdName);
        fdTags = filterXss(fdTags);

    	var fdSize = $("span[name='size']").html();
    	var fdLength = $("span[name='width']").html();
    	var fdWidth = $("span[name='width']").html();
    	var del_load = dialog.loading();
    	var url = Com_Parameter.ContextPath + "sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=updateMaterial";
        $.ajax({
            url: url,
            data: {"fdId":fdId,"attId":attId,"fdName":fdName,"fdTags":fdTags,"fdSize":fdSize,"fdLength":fdLength,"fdWidth":fdWidth },
            dataType: 'json',
            type: 'POST',
            success: function (data) {
            	if(data.status==0)
            	dialog.success(Attachment_MessageInfo["sysAttMain.material.operate.success"]);
            	 del_load.hide();
            	 Com_CloseWindow();
            },
            error: function (req) {
            	 del_load.hide();
            	 Com_CloseWindow();
            }
        });
    }
    window.doEditSave =doEditSave;
    /******************
     **功能  单个删除
     ******************/
    function onDeleteItem(fdId) {
    	var option = window.listOption;
        dialog.confirm(option.lang.comfirmDelete, function (ok) {
            if (ok == true) {
                var del_load = dialog.loading();
                var url = Com_Parameter.ContextPath + "sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=delete";
                url = url + "&fdId=" + fdId;
                $.ajax({
                    url: url,
                    // data: {"fdId":fdId},
                    dataType: 'json',
                    type: 'GET',
                    success: function (data) {
                        if (del_load != null) {
                            del_load.hide();
                            topic.publish("list.refresh");
                        }
                        dialog.result(data);
                    },
                    error: function (req) {
                        if (req.responseJSON) {
                            var data = req.responseJSON;
                            dialog.failure(data.title);
                        } else {
                            dialog.failure(Attachment_MessageInfo["sysAttMain.material.operate.fail"]);
                        }
                        del_load.hide();
                    }
                });
            }
        });

    }
    window.onDeleteItem =onDeleteItem;
    /******************
     **功能  上传
     ******************/
    window.uploadDoc = function (type) {
        dialog.iframe('/sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=uploadAdd&type=' + type,
        	Attachment_MessageInfo["button.upload"],
            function (value) {
                if (value != null) {
                    topic.publish("list.refresh");
                }
            }, { width: 700, height: 500 }
        );
    };

    /******************
     **功能 批量删除
     ******************/
    function getValueByHash(key) {
        var value = Com_GetUrlParameter(location.href, 'q.' + key);
        if (value) {
            return value;
        }
        var hash = window.location.hash;
        if (hash.indexOf(key) < 0) {
            return "";
        }
        var url = hash.split("cri.q=")[1];
        var reg = new RegExp("(^|;)" + key + ":([^;]*)(;|$)");
        var
            r = url.match(reg);
        if (
            r != null) {
            return unescape(
                r[2]);
        }
        return "";
    }

    function deleteAll() {

    	var option = window.listOption;
        var selected = [];
        $("input[name='List_Selected']:checked").each(function () {
            selected.push($(this).val());
        });
        if (selected.length == 0) {
            dialog.alert(option.lang.noSelect);
            return;
        }
        dialog.confirm(option.lang.comfirmDelete, function (ok) {
            if (ok == true) {
                var del_load = dialog.loading();
                var param = {
                    "List_Selected": selected
                };
                var hash = getValueByHash("docCategory");
                if (hash) {
                    param.docCategory = hash;
                }
                hash = getValueByHash("docTemplate");
                if (hash) {
                    param.docTemplate = hash;
                }
                $.ajax({
                    url: option.contextPath + option.basePath + '?method=deleteall',
                    data: $.param(param, true),
                    dataType: 'json',
                    type: 'POST',
                    success: function (data) {
                        if (del_load != null) {
                            del_load.hide();
                            topic.publish("list.refresh");
                        }
                        dialog.result(data);
                    },
                    error: function (req) {
                        if (req.responseJSON) {
                            var data = req.responseJSON;
                            dialog.failure(data.title);
                        } else {
                            dialog.failure(Attachment_MessageInfo["sysAttMain.material.operate.fail"]);
                        }
                        del_load.hide();
                    }
                });
            }
        });
    }
    window.deleteAll = deleteAll;
    /**
     * 图片保存上传
     */
    function uploadClickOK(type){
    	var materialList =[];
    	//var validate = true;
    	$("li[name='imageViewContainer']").each(function(idx,e){
    		var ele =$(e); 
    		var attid = ele.find(".material_upload_li_img").attr("fdid")
    		var title =ele.find(".material_upload_title").val();
    		var size =ele.find("input[name='fdSize']").val();
    		var length =ele.find("input[name='fdLength']").val();
    		var width =ele.find("input[name='fdWidth']").val();
    		// if(!title.length>0){
    		// 	ele.find(".material_upload_title").addClass("requireIn");
    		// 	validate=false;
    		// }
    		var tagNames = ele.find("input[name='tagsName']").val();
    		
    		var obj  = {
    			attid :attid,
    			title:title,
    			tagNames:tagNames,
    			size:size,
    			length:length,
    			width:width
    		}
    		materialList.push(obj);
    	})
    	var jsonStr = JSON.stringify(materialList) 
    	// if(validate){
    		$.ajax(saveMaterialAsyncURL, {
    				dataType: 'json', 
    				data:{"materialList":jsonStr,"type":type},
    				type: 'POST',
    				async: true,
    		
    			success: function (res) {
    				seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
    					window.$dialog.hide({res:res});
    				});
    			}, error: function (xhr, status, errorInfo) {
    				if (window.console) window.console.error(errorInfo);
    			}
    		});
    	// }else{
    	// 	alert("请填写标题");
    	// }
    }
    window.uploadClickOK = uploadClickOK;
    
    
    function showErrorMsg (fId){
    	$("li[fileId='" + fId + "']").find(".failedMsg").toggleClass("failedMsgShow")
    }
    window.showErrorMsg = showErrorMsg;
    /**
     * upload 
     */
    function uploadTitleChange(liId){
    	var ele = $("#"+liId).find(".material_upload_title")
    	var v = ele.val();
    	if(v.length>0){
    		ele.removeClass("requireIn")
    	}else{
    		ele.addClass("requireIn")
    	}
    }
    window.uploadTitleChange = uploadTitleChange;
    function uploadTagChange(liId){
    
    }
    window.uploadTagChange = uploadTagChange;
    
    
    function deleteViewContainer(divId){
    	$("#"+divId).remove();
    	var viewli = $(".lui_material_upload_dlg ").find("[name=imageViewContainer]");
    	if(viewli.length<=0){
    		$(".lui_material_upload_dlg ").removeClass("hasIcon")
    	}
    	var fdId = divId.substring(divId.lastIndexOf("_")+1);
    	var fileList = attachmentObject_defaultUploadLi.fileList
		 for (var i = 0; i < fileList.length; i++) {
	         if (fileList[i].fdId == fdId) {
	        	 fileList[i].fileStatus = -1;
	         }
	     }
    }
    window.deleteViewContainer = deleteViewContainer;
    
    function btnDelViewContainer(event){
    	$( event.target).parents("li").remove();
    	console.log( event.target)
    	
    }
    window.btnDelViewContainer = btnDelViewContainer;
});

