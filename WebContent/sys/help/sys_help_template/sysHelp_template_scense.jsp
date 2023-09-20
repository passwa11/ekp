<%@ page import="com.landray.kmss.third.mall.util.MallUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style type="text/css">
    #scenseExperencePanel{
        /*position: relative;*/
        /*height: 100%;*/
    }
    img.disconnect{
        position: absolute;
        left: 50%;
        top: 30%;
        transform: translate(-50%, -30%);
    }
    span.disconnect-desc{
        position: absolute;
        left: 50%;
        top: 42%;
        transform: translate(-50%, -42%);
        font-size: 15px;
        color: rgba(22, 155, 213, 1);
    }
    div.disconnect-btn{
        border-width: 0px;
        width: 140px;
        height: 40px;
        background: inherit;
        background-color: rgba(22, 155, 213, 1);
        border: none;
        border-radius: 5px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        line-height: 40px;
        text-align: center;
        color: #fff;
        cursor: pointer;
    }
    div.disconnect-btn-reload{
        position: absolute;
        left: 43%;
        top: 50%;
        transform: translate(-43%, -50%);
    }
    div.disconnect-btn-forward{
        position: absolute;
        left: 57%;
        top: 50%;
        transform: translate(-57%, -50%);
    }
</style>
<script type="text/javascript">
    var scenseUrl = '<%=request.getParameter("scenseUrl")%>';
    var isCustomizedUrl = '<%=request.getParameter("isCustomizedUrl")%>';

    function initPanel() {
        $("#scenseExperencePanel").hide();
        $("#failDiv").html('').hide();
    }

    function showDisconnectPanel($){
        initPanel();
        $("#failDiv").show();
        var $disConnect = $('<img class="disconnect" width="134px" height="106px" src="${LUI_ContextPath}/sys/help/sys_help_template/image/icon-disconnect.png"/>');
        $disConnect.appendTo($("#failDiv"));
        var $message = $('<span class="disconnect-desc"><bean:message bundle="sys-help" key="sysHelpModule.module.disconnect.desc"/></span>');
        $message.appendTo($("#failDiv"));
        var $reload = $('<div class="disconnect-btn disconnect-btn-reload"><bean:message bundle="sys-help" key="sysHelpModule.module.disconnect.reload"/></div>');
        $reload.appendTo($("#failDiv"));
        $reload.click(function(){
            connect2Mall(scenseUrl);
        });
        <kmss:authShow roles="ROLE_SYSHELP_SETTING">
        var $forward = $('<div class="disconnect-btn disconnect-btn-forward"><bean:message bundle="sys-help" key="sysHelpModule.module.disconnect.forward"/></div>');
        $forward.appendTo($("#failDiv"));
        $forward.click(function(){
            window.open("${LUI_ContextPath}/sys/profile/index.jsp#app/mechanism/sys/help");
        });
        </kmss:authShow>
    }
    LUI.ready(function(){
        if(scenseUrl == ""){
            seajs.use(['lui/jquery'], function($) {
                showDisconnectPanel($);
            });
        }
        else{
            var host = '<%=MallUtil.MALL_DOMMAIN%>';
            if(host.length - host.lastIndexOf("/") == 1){
                host = host.substring(0, host.lastIndexOf("/"));
            }
            if(isCustomizedUrl == 'false'){
                if(scenseUrl.startsWith("/")){
                    scenseUrl = host + scenseUrl;
                }
                else{
                    scenseUrl = host + "/" + scenseUrl;
                }
                if(scenseUrl.indexOf("?") > 0){
                    scenseUrl += "&tabs=off";
                }
                else{
                    scenseUrl += "?tabs=off";
                }
            }
            connect2Mall(scenseUrl);
        }
    });

    function connect2Mall(scenseUrl) {
        seajs.use(['lui/jquery'], function($) {
            $.ajax({
                url: scenseUrl,
                type: 'GET',
                dataType: "jsonp",
                timeout: 2000,
                complete: function(response){
                    if(response.status == 200){
                        initPanel();
                        $("#scenseExperencePanel").show();
                        scenseUrl += '&LUIID=!{lui.element.id}';
                        var dataview=LUI("search_Iframe");
                        dataview.source.source={src:scenseUrl};
                        setTimeout(function(){
                            dataview.refresh();
                        },500);
                    }
                    else{
                        showDisconnectPanel($);
                    }
                }
            });
        });
    }
</script>
<div id="scenseExperencePanel">
    <ui:dataview format="sys.ui.iframe" id="search_Iframe">
        <ui:source type="Static">
            {"src":""}
        </ui:source>
    </ui:dataview>
</div>
<div id="failDiv">

</div>