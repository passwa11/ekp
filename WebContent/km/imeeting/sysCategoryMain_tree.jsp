<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%@ include file="/sys/category/sys_category_main/sysCategoryMain_tree.jsp" %>

<script>
    window.updateCateRight = function () {
        if (!List_CheckSelect()) {
            return;
        }
        seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
            dialog.confirm({
                title: "${lfn:message('km-imeeting:mobile.imeeting.select')}",
                html: "${lfn:message('km-imeeting:kmImeeting.cchangeRight.info')}",
                iconType: " ",
                buttons: [{
                    name: "${lfn:message('km-imeeting:table.kmImeetingMain')}",
                    fn: function (value, dialog1) {
                        doUpdateCateRight("KmImeetingMain");
                        dialog1.hide();
                    }
                }, {
                    name: "${lfn:message('km-imeeting:table.kmImeetingSummary')}",
                    fn: function (value, dialog1) {
                        doUpdateCateRight("KmImeetingSummary");
                        dialog1.hide();
                    }
                }],
                width: 300
            });
        });
    }

    window.doUpdateCateRight = function (model) {
        var selList = LKSTree.GetCheckedNode();
        var c = 0, s = "";
        for (var i = selList.length - 1; i >= 0; i--) {
            if (c > 0) {
                s += ",";
            }
            c++;
            s += selList[i].value;
        }
        document.getElementById('fdIds').value = s;
        var url = s_contextPath + "/sys/right/cchange_cate_right/cchange_cate_right.jsp";
        url += "?method=cChangeCateRight&tmpModelName=${JsParam.modelName}&mainModelName=com.landray.kmss.km.imeeting.model." + model;
        url += "&templateName=${JsParam.templateName}&categoryName=${JsParam.categoryName}&authReaderNoteFlag=${JsParam.authReaderNoteFlag}";
        window.open(url);
    }
</script>