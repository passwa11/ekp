<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.util.WaterMarkUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%
    pageContext.setAttribute("waterMarkContext", UserUtil.getKMSSUser().getUserName());
    pageContext.setAttribute("waterMarkMobileEnable", WaterMarkUtil.getWaterMarkMobileEnable());
    pageContext.setAttribute("waterMarkParams", WaterMarkUtil.getWaterMarkParams());
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/watermarkMobile.js?s_cache=${MUI_Cache}"></script>
<script>
require(["dojo/ready"], function( ready) {

    <%--ready(function() {--%>
    <%--    var waterMarkMobileEnable = "${waterMarkMobileEnable}";--%>
    <%--    if(waterMarkMobileEnable != "true")--%>
    <%--        return;--%>

    <%--    var waterMarkParams = '${waterMarkParams}';--%>

    <%--    if(waterMarkParams!=null || waterMarkParams!='') {--%>
    <%--        var jsonArr = JSON.parse(waterMarkParams);--%>
    <%--        var arr = []--%>
    <%--        for (var i in jsonArr.content) {--%>
    <%--            arr.push(jsonArr.content[i]);--%>
    <%--        }--%>

    <%--    }--%>

    <%--    var watermarkParam = {--%>
    <%--        context: arr,//水印内容--%>
    <%--        // x: parseInt(jsonArr.compose.horizontalCoord),//水印起始位置x轴坐标--%>
    <%--        // y: parseInt(jsonArr.compose.verticalCoord),//水印起始位置Y轴坐标--%>
    <%--        color: jsonArr.compose.fontColor,//水印字体颜色--%>
    <%--        // alpha: jsonArr.compose.alpha/100,//水印透明度--%>
    <%--        fontsize: parseInt(jsonArr.compose.fontSize),//水印字体大小--%>
    <%--        font: jsonArr.compose.fontFamily,//水印字体--%>
    <%--        angle: parseInt(jsonArr.compose.scale),//水印倾斜度数--%>
    <%--        width: 200,//水印宽度--%>
    <%--        height: 80,//水印长度--%>
    <%--    };--%>

    <%--    window.wm = new WaterMark(watermarkParam);--%>

    <%--    wm.render();--%>

    <%--    var timer;--%>
    <%--    var w = Math.max(document.body.scrollWidth, document.body.clientWidth);--%>
    <%--    var h = Math.max(document.body.scrollHeight, document.body.clientHeight, window.innerHeight);--%>
    <%--    function refresh(){--%>
    <%--        var curW = Math.max(document.body.scrollWidth, document.body.clientWidth);--%>
    <%--        var curH = Math.max(document.body.scrollHeight, document.body.clientHeight, window.innerHeight);--%>
    <%--        if (curW != w || curH != h) {--%>
    <%--            w = curW;--%>
    <%--            h = curH;--%>
    <%--            wm.refresh();--%>
    <%--        }--%>
    <%--        timer = setTimeout(refresh, 500);--%>
    <%--    }--%>
    <%--    refresh();--%>
    <%--});--%>

	ready(function() {
        var waterMarkMobileEnable = "${waterMarkMobileEnable}";
        if(waterMarkMobileEnable != "true")
            return;

        try{
            var hasWm = false;
            var top = window.top;
            var pnt = window.parent;
            while(pnt){
                try{
                    if(pnt.wm){
                        hasWm = true;
                        break;
                    }else{
                        if(pnt == top)//如果是顶层了就不再继续
                            break;
                        pnt = pnt.parent;
                    }
                }catch(e){

                }
            }

            if (hasWm) {
                return;
            }else{

                var waterMarkParams = '${waterMarkParams}';

                if(waterMarkParams!=null || waterMarkParams!='') {
                    var jsonArr = JSON.parse(waterMarkParams);
                    var arr = [];
                    for (var key in jsonArr.content) {
                        if(jsonArr.content[key].deleted==false ||jsonArr.content[key].deleted=="false"){
                            arr.push(jsonArr.content[key].value);
                        }
                    }
                }

                var watermarkParam = {
                    context: arr,//水印内容
                    x_space: 0,//水印起始位置x轴坐标
                    y_space: 50,//水印起始位置Y轴坐标
                    color: jsonArr.compose.fontColor,//水印字体颜色
                    alpha: (jsonArr.compose.alpha)/100,//水印透明度
                    fontsize: (jsonArr.compose.fontSize).toString()+"px",//水印字体大小
                    font: jsonArr.compose.fontFamily,//水印字体
                    angle: parseInt(jsonArr.compose.scale),//水印倾斜度数
                    fontbold: jsonArr.compose.fontBold,//水印字体加粗
                    width: 140,//水印宽度
                    height: 80 //水印长度
                };

                window.wm = new WaterMark(watermarkParam);

                wm.render();

                var timer;
                var w = Math.max(document.body.scrollWidth, document.body.clientWidth);
                var h = Math.max(document.body.scrollHeight, document.body.clientHeight, window.innerHeight);
                function refresh(){
                    var curW = Math.max(document.body.scrollWidth, document.body.clientWidth);
                    var curH = Math.max(document.body.scrollHeight, document.body.clientHeight, window.innerHeight);
                    if (curW != w || curH != h) {
                        w = curW;
                        h = curH;
                        wm.refresh();
                    }
                    timer = setTimeout(refresh, 500);
                }
                refresh();

            }
        }catch(e){}
	});

});
</script>