<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="//res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="//rescdn.qqmail.com/node/wwopen/wwopenmng/js/3rd/zepto.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
<%
    boolean isPC = (MobileUtil.getClientType(request) == MobileUtil.WXWORK_PC);
    System.out.println("isPC:"+isPC);
    request.setAttribute("isPC",isPC);
%>

<script>
    window.onpageshow = function (e) {
        if (e.persisted) {
            window.close();
        }
    }

    $(function(){
        var url = "/third/wxwork/jsapi/wxJsapi.do?method=checkLogin&url="+encodeURIComponent(window.location.href.split('#')[0]);
        var isPC = <%=isPC%>+"";
        $.ajax({
            type: "POST",
            url: url,
            async:false,
            dataType: "json",
            success: function(data){
                if(data && data.hadLogin){
                    _closeWindows(data);
                    return;
                }else{
                    var jingHao = "";
                    var temp=window.location.href;
                    if(temp.indexOf("#")>-1){
                        jingHao=encodeURIComponent(temp.substring(temp.indexOf("#")));
                    }
                    var url ="${wxWorkSsoUrl}";
                    if(url.indexOf("WXWORKSSOKEY")>-1){
                        url=url.replace("WXWORKSSOKEY",jingHao)
                    }
                    console.log("url",url);
                    window.location.replace(url);
                    // window.open(url, '_self');
                    return;
                }
            }
        });

        function _closeWindows(signInfo){
            window.close();
            if(isPC != "true"){
                _config(signInfo);
            }
            return;
        }
        function _config(signInfo){
            wx.config({
                debug : false,
                beta : true,
                appId : signInfo.appId,
                timestamp : signInfo.timestamp,
                nonceStr : signInfo.noncestr,
                signature : signInfo.signature,
                jsApiList : ['closeWindow']
            });
            wx.ready(function(){
                wx.closeWindow();
            },function(){
                if(WeixinJSBridge){
                    WeixinJSBridge.call('closeWindow');
                }
            });
        }
    });
</script>
