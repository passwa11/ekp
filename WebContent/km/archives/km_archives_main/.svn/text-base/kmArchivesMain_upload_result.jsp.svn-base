<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
	var result = '${result}';
	// 兼容IE8的字符串转JSON方法
	function strToJson(str){
		var json = eval('(' + str + ')');
		return json;
	}
	var JSONResult =  strToJson(result);
	parent.callback(JSONResult);
</script>
</head>
<body >
</body>
</html>