<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="Stylesheet" href="styles/Mcommon.css" />
	<link rel="Stylesheet" href="styles/reset.css" />
	<link rel="Stylesheet" href="styles/Pcommon.css" />
	<link rel="Stylesheet" href="styles/com_datum2.css?id=1" />
    <script type="text/javascript" src="kmsResources/jquery-1.7.1.min.js"></script>
	<title>${lfn:message('sys-ftsearch-db:search.ftsearch.docSnapshot')}</title>
	<style type="text/css">
	font {
	    color: black;
	    background-color: #ffff66;
	    padding: 0 3px;
	    font-weight: bold;
	}
	</style>
    <script type="text/javascript">
        $(function () {
            var length = $(".com_datum_listItem ul li a[id ^= 'list']").length;

            var h1, h2, h3, h4;
   
            setHeight();
            //设置高度
            function setHeight() {
                $(".com_datum_contentListC").css({ "position": "static" });
                h1 = $(".exp_preview.current_page").height();
                h3 = $(".com_datum_contentListC").height();
                h4 = parseInt(h1, 10) > parseInt(h3, 10) ? h1 : h3;
                h2 = parseInt(h4, 10) + 20 + "px";

                $(".com_datum_contentC").height(h4);
                $(".exp_preview.current_page").height(h4);
                $(".com_datum_content").height(h2);
                fixList(); //右侧列表滚到底部即固定
            }

            //右侧列表滚到底部即固定 
            function fixList() {
                var top = $(".com_datum_contentListC").offset().top;
                var left = $(".com_datum_contentListC").offset().left;
                var pos = $(".com_datum_contentListC").position();
                var window_h = $(window).height();
                var scoll_h = h3 + top - window_h;
                var rightList_h = h3 + top; //右侧列表的高度
                if (rightList_h <= window_h) { //当右侧列表没有超过一屏时，即固定在头部
                    //列表浮动
                    $(window).scroll(function () {
                        var scrolls = $(this).scrollTop();
                        $(".com_datum_contentListC").css({position:"relative",left: "0px", top: scrolls+"px",width:"235px" });
                    });
                }
            }
			$("font")[0].id ='moveToId';
            $("html,body").animate({scrollTop:($("#moveToId").offset().top-60)},0);

        });
    </script>
</head>
<body class="com_datum_wrapper" style="background-color: #f2f2f2">

    <div class="com_datum_content clrfix">
        <div class="shadow_L">
        </div>
        <div class="com_datum_contentC clrfix">
            <!-- 左边 开始 -->
            <div class="com_datum_contentLeft">

                <div class="exp_preview current_page" id="exp_preview1" style="left: 0px; top: 0px;">
                    <div class="exp_previewC" style="font-size: 14px">
                    	<h2>${fdDocSubject}</h2>
						${snapshotStr}
                    </div>
                </div>

                <div id="prevExpMask">
                </div>
            </div>
            <!-- 左边 结束 -->
            <!-- 右边列表 开始  com_datum_contentList -->
            
            <div class="com_datum_contentList">
                <div class="shadow_line">
                </div>
                <div class="com_datum_contentListC">
                    <div class="com_datum_listItem">
                        <h3>
							<span class="ico_head"></span>
							<span>${lfn:message('sys-ftsearch-db:sysFtsearch.simdoc.title')}</span>
                        </h3>
<%
String docKey = request.getParameter("docKey");
String[] array = docKey.split("_");
String modelName = array[0];
String fdId = "";
String attachmentId = "";
if(array.length >=2) {
	fdId = array[1];
}
if(array.length >=3) {
	attachmentId = array[2];
}
request.setAttribute("modelName",modelName);
request.setAttribute("fdId",fdId);
request.setAttribute("attachmentId",attachmentId);
%>
			            <c:import url="simdoc.jsp"> 
			            	<c:param name="modelName" value="${modelName}"></c:param>
			            	<c:param name="fdId" value="${fdId}"></c:param>
			            	<c:param name="attachmentId" value="${attachmentId}"></c:param>
			            	<c:param name="needAttachment" value="true"></c:param>
			            </c:import>
                    </div>
                </div>
            </div>
            <!-- 右边列表 结束 -->
        </div>
        <div class="shadow_R">
        </div>
    </div>
    <div class="shadow_B">
    </div>
</body>
</html>