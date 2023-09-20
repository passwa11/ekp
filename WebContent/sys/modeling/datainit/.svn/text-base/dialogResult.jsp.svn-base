<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<html>
<head>
    <script>
        seajs.use("lui/dialog", function (dialog) {
            var data = '${data}';
            if(!data) {
                data = '{"status" : false, "title" : "操作失败"}';
            }
            dialog.result(JSON.parse(data));
        });
    </script>
</head>
<body>
</body>
</html>
