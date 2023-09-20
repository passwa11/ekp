var exportDialogObj = {
    listviewId : "",
    fdAppModelId: "",
    exportUrl: Com_Parameter.ContextPath + "sys/modeling/main/listview.do?method=exportResult",
    exportNum: "0",
    viewType:""
};
seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic','lang!sys-modeling-main'], function($, dialog, topic,modelingLang) {
    //viewType为空默认为列表视图
    exportResult = function(viewId, fdAppModelId, viewType) {
        showExportDialog(viewId, fdAppModelId, viewType);
    };

    showExportDialog = function(viewId, fdAppModelId, viewType) {
        exportDialogObj.listviewId = viewId;
        exportDialogObj.fdAppModelId = fdAppModelId;
        exportDialogObj.viewType = viewType;
        var selectedData = getSelectIndexs();
        var selectedNum = selectedData.length;
        var hasSelected = selectedData.length > 0;
           if(exportDialogObj.exportNum >0){
        var url = '/sys/modeling/main/listview/export/selectDialog.jsp?viewId=' + viewId + '&viewType=' + viewType +
        '&totalNum=' + exportDialogObj.exportNum + '&hasSelected=' + hasSelected+'&selectedNum='+selectedNum+'&dialogType=export';
        dialog.iframe(url, modelingLang['modeling.export.settings'],
            function (value) {
                exportCallbak(value);
            },
            {
                width: 500,
                height: 510
            }
        );
        }else {
            dialog.alert(modelingLang['modeling.unable.export.current.list.empty']);
        }
    };

    exportCallbak = function(returnValue) {
    	// debugger;
        if(returnValue == null)
            return;
        var selectedData = getSelectIndexs();
        document.getElementsByName("fdNum")[0].value = exportDialogObj.exportNum;
        document.getElementsByName("fdNumStart")[0].value = returnValue["fdNumStart"];
        document.getElementsByName("fdNumEnd")[0].value = returnValue["fdNumEnd"];
        document.getElementsByName("fdKeepRtfStyle")[0].checked = returnValue["fdKeepRtfStyle"];
        document.getElementsByName("fdColumns")[0].value = returnValue["fdColumns"];
        document.getElementsByName("fdExportType")[0].value = returnValue["fdExportType"];
        document.getElementsByName("checkIdValues")[0].value = selectedData.join("|");
        dialog.confirm(modelingLang['modeling.takes.while.export.data'],function(value){
            if(value === true) {
                if(exportDialogObj.viewType == "2"){
                    var listview = LUI('boardListView0');
                }else{
                    var listview = LUI('listview');
                }
                let resolveUrls = listview.table._resolveUrls(listview.sorts);
                let url = Com_Parameter.ContextPath + resolveUrls.substring(1);
                console.log(url)
                if("gantt" == exportDialogObj.viewType){
                    url = url.replace("method=indexData", "method=exportResult");
                }else{
                    url = url.replace("method=data", "method=exportResult");
                }
                exportForm.action = url;
                exportForm.submit();
            }
        });
    };

    topic.subscribe('list.changed', function (evt){
        if(exportDialogObj.viewType && exportDialogObj.viewType == "2"){

        }else{
            exportDialogObj.exportNum = evt.page.totalSize;
        }
    });

    topic.subscribe("modeling.board.list.count",function(evt) {
        exportDialogObj.exportNum = evt.totalSize;
        exportDialogObj.viewType = evt.viewType;
    })

    function getSelectIndexs() {
        var selectedData = [];
        $("[name='List_Selected']:checkbox").each(function(){
            if (this.checked){
                selectedData.push(this.value);
            }
        });

        return selectedData;
    }

    function getKeyValue(datas,key){
        if(datas && datas.length>0 && datas[0] && key){
            for (var j = datas[0].length-1; j >=0; j--) {
                if (datas[0][j].col === key) {
                    return datas[0][j].value;
                }
            }
        }
        return "";
    }

    window.exportResult = exportResult;
});