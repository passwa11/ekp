<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="renderer" content="webkit">

		<title>主数据详情页</title>
		<link rel="stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/mobile/css/ios7.css"/>" />
		<link rel="stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/mobile/css/mobilekeydata.css"/>" />
	</head>

	<body>

    <!-- 详情页 Starts -->
    <div class="mui_keydata_details_panel">
      <div class="mui_keydata_details_panel_heading">
        <h4 class="mui_keydata_details_panel_heading_title">${title }</h4>
      </div>
      <div class="mui_keydata_details_panel_body">
        <ul class="mui_keydata_details_list">
        	<c:forEach items="${showFieldsList }" var="field" varStatus="varStatus">
							<li>
							 	<span class="title">${field[0] }</span>
            					<span class="txt">${field[1] }</span>
							</li>
							
			</c:forEach>
        </ul>
      </div>
    </div>
    <!-- 详情页 Ends -->

	</body>

</html>
      

