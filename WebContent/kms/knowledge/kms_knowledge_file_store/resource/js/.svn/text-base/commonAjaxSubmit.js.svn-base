define(function(require, exports, module) {

    var dialog = require('lui/dialog');
    var env = require('lui/util/env');
    var knowledgeLang = require('lang!kms-knowledge');

    function isJsonString(str) {
        try {
            if (typeof JSON.parse(str) == "object") {
                return true;
            }
        } catch(e) {
        }
        return false;
    }

    /**
     * 使用ajax提交
     * @param url
     * @param showErrorWithDialog
     */
    function useAjaxSubmit(url, showErrorWithDialog){
        if(showErrorWithDialog == undefined || showErrorWithDialog == null){
            showErrorWithDialog = true;
        }
        url = env.fn.formatUrl(url);
        top.window['__kmsTutor_dialog_error_html'] = "";
        Com_Submit.ajaxSubmit = function(form) {
            var datas = $(form).serializeArray();
            var del_load = dialog.loading();
            $.ajax({
                url : url,
                type : 'POST',
                dataType : 'text',
                data : $.param(datas),
                success : function(data,textStatus,xhr) {
                    if(del_load){
                        del_load.hide();
                    }
                    if(isJsonString(data)){
                        data = JSON.parse(data);
                        if(data.result == "SUCCESS"){
                            if(top.window.__task_dialog_callback){
                                top.window.__task_dialog_callback();
                            }
                            $dialog.hide();
                        }else{
                            dialog.failure(data.errorMsg);
                            Com_Parameter.isSubmit = false;
                        }
                    }else{
                        handleError(showErrorWithDialog,data);
                    }
                },
                error : function(xhr,textStatus,errorThrown) {
                    if(del_load){
                        del_load.hide();
                    }
                    handleError(showErrorWithDialog,xhr.responseText);
                   // dialog.failure("failure");
                   //$dialog.hide();
                }
            });
        }
    }

    /**
     * 使用ajax提交
     * @param url
     * @param showErrorWithDialog
     */
    function doAjax(url, data, showErrorWithDialog){
        if(showErrorWithDialog == undefined || showErrorWithDialog == null){
            showErrorWithDialog = true;
        }
        url = env.fn.formatUrl(url);
        top.window['__common_dialog_error_html'] = "";
        var del_load = dialog.loading();
        $.ajax({
            url : url,
            type : 'POST',
            dataType : 'text',
            data : data,
            success : function(data,textStatus,xhr) {
                if(del_load){
                    del_load.hide();
                }
                if(isJsonString(data)){
                    data = JSON.parse(data);
                    if(data.result == "SUCCESS" || (data.success+"") == "true"){
                        dialog.alert(knowledgeLang["kmsKnowledgeFileStoreExcelImport.provider.serverConnectSuccess"]);
                    }else{
                        dialog.failure(data.errorMsg);
                    }
                }else{
                    handleError(showErrorWithDialog,data);
                }
            },
            error : function(xhr,textStatus,errorThrown) {
                if(del_load){
                    del_load.hide();
                }
                handleError(showErrorWithDialog,xhr.responseText);
            }
        });
    }

    function handleError(showErrorWithDialog, data){
        if(showErrorWithDialog){
            top.window['__common_dialog_error_html'] = data;
            Com_Parameter.isSubmit = false;
            dialog.iframe("/kms/knowledge/kms_knowledge_file_store/error.jsp",knowledgeLang["kmsKnowledgeFileStoreExcelImport.dialog.error.title"],null,{
                width : 600,
                height : 350,
                autoClose: false
                // buttons : [
                //     // {
                //     //     name : "关闭",
                //     //     value : false,
                //     //     styleClass : 'lui_toolbar_btn_gray',
                //     //     fn : function(value, _dialog) {
                //     //         _dialog.hide(value);
                //     //     }
                //     // }
                // ]
            });
        }else{
            document.open();
            document.write(data);
            Com_Parameter.isSubmit = true;
        }
    }


    module.exports = {
        useAjaxSubmit: useAjaxSubmit,
        handleError: handleError,
        doAjax: doAjax
    }
})