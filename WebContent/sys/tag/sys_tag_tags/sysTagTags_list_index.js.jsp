<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
	
	function _exportTagExcel(){
		// 先校验列表是否有数据
		var obj = document.getElementsByName("List_Selected");
		if (obj == null || obj.length == 0){
            dialog.alert("<bean:message bundle='sys-tag' key='sysTagTags.export.noDataToExport'/>");
            return;
		}
		
      // 如果勾选了复选框，导出所选标签，如果没有勾选，按照当前查询条件导出所有标签，如果没有数据，提示一下
        var dataview = LUI("listview");
        var fdUrl=dataview.source.url;
        var fdExportExcelUrl="${ LUI_ContextPath}"+fdUrl.replace("method=list","method=exportTagExcel");
        
		// 校验是否是勾选
        var values=[];
        var selected = false;
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                values.push(obj[i].value);
                selected = true;
            }
        }
        mess = "${lfn:message('sys-tag:sysTagTags.export.exportAll')}"
        if(selected){
        	mess = "${lfn:message('sys-tag:sysTagTags.export.exportSelected')}";
        	fdExportExcelUrl=fdExportExcelUrl+"&List_Selected="+values;
	    }
        dialog.confirm(mess,
            function(flag, d) {
             if (flag) {
                 Com_OpenWindow(fdExportExcelUrl, '_self');
             }
        });
	}
	
	window.exportTagExcel = _exportTagExcel;
});
</script>