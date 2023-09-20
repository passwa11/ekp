<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
    var dialogRtnValue = null;
    var dialogObject = null;
    if(window.showModalDialog){
        dialogObject = window.dialogArguments;
    }else{
        dialogObject = window.opener._rela_dialog;
    }

    function Com_DialogReturn(value){
        window.dialogRtnValue = value;
        beforeClose();
        window.close();
    }

    function beforeClose(){
        var returnValueObj = {};
        if(typeof(dialogRtnValue) != "undefined" && dialogRtnValue !=null && $.isEmptyObject(dialogRtnValue)==false){
            $.extend(true, returnValueObj, dialogRtnValue);
        }
        dialogObject.rtnRelaData = JSON.stringify(returnValueObj);
        dialogObject.AfterShow();
    }

    function changeAttentionType() {
        var url = Com_Parameter.ContextPath+"sys/lbpmext/attention/lbpmExtAttention.do?method=docpage";
        var iframe = document.getElementById("lbpmExtAttentionEntry");
        // 加载相应搜索类型的页面
        iframe.setAttribute("src",encodeURI(url));
    }
    function resizeWindowHeight(){
        var table = document.getElementById("lbpmExtAttentionEntry");
        var h = table.offsetHeight + 50;
        window.innerHeight = h;
    }
    Com_AddEventListener(window, "load", function(){
        setTimeout("changeAttentionType()", 100);
    });
</script>
