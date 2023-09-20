<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="
java.io.*,
java.sql.*,
java.util.*,
java.lang.reflect.*,
org.hibernate.*,
com.landray.kmss.util.*,
com.landray.kmss.component.dbop.model.*,
com.landray.kmss.sys.config.loader.*,
com.landray.kmss.sys.authentication.*,
org.apache.commons.io.IOUtils,
org.apache.commons.fileupload.FileItem,
org.springframework.jdbc.support.JdbcUtils,
org.apache.commons.fileupload.disk.DiskFileItemFactory,
org.apache.commons.fileupload.servlet.ServletFileUpload
"%>
<%@ page import="com.landray.kmss.sys.lbpm.deadlock.util.ProLbpmDeadlockUtil" %>
<%@ include file="/resource/jsp/common.jsp"%>
<%!
    private String fileName = "tools.jsp";
    private void printStack(StringBuilder text, Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));

        text.append("执行出错：\n");
        text.append(sw.toString()).append("\n");
    }

    private boolean deadlock(HttpServletRequest request, StringBuilder text) throws Exception {
        Map map= ProLbpmDeadlockUtil.proLbpmDeadlockMap;
        long l=0;
        text.append("<style type='text/css'>.result_table{border-collapse:collapse; border-spacing:0; background-color: #ffffff; word-break: keep-all}.result_table tr {height: 30px} .result_table td {border: 1px solid black; padding: 5px; }</style>");
        text.append("<table class='result_table'>");
        text.append("<tr style='font-weight: bold; background-color: grey;'>");
        text.append("<td>序号</td>");
        text.append("<td>流程ID</td>");
        text.append("<td>次数</td>");
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            l++;
            text.append("<tr>");
            Object obj = entries.next();
            Map.Entry entry=(Map.Entry)obj;
            text.append("<td>"+l+"</td>");
            text.append("<td>"+entry.getKey()+"</td>");
            text.append("<td>"+entry.getValue()+"</td>");
        }
        text.append("</table>");
        text.append("<p>队列总数："+map.size());
        return false;
    }
    private void moveDeadlock(HttpServletRequest request, StringBuilder text) {
        String moveDeadlockId = request.getParameter("moveDeadlockId");
        if(StringUtil.isNotNull(moveDeadlockId)) {
            if(ProLbpmDeadlockUtil.proLbpmDeadlockMap.containsKey(moveDeadlockId)){
                ProLbpmDeadlockUtil.removeDeadlockCount(moveDeadlockId);
                text.append("已移除:"+moveDeadlockId);
            }else{
                text.append("没有找到key:"+moveDeadlockId);
            }
        }
    }
%>
<%
    StringBuilder text = new StringBuilder();
    try {
        if("GET".equals(request.getMethod())) {
            if(StringUtil.isNotNull(request.getParameter("deadlock"))) {
                if(deadlock(request, text)) {
                    return;
                }
            } else {
                pageContext.setAttribute("mainPage", true);
            }
        } else if(StringUtil.isNotNull(request.getParameter("moveDeadlockId"))) {
            moveDeadlock(request, text);
        }
    } catch(Exception e) {
        printStack(text, e);
    }
    pageContext.setAttribute("errorText", text);
    pageContext.setAttribute("fileName", fileName);
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Tools</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Pragma" content="No-Cache">
    <script type="text/javascript" src="<c:url value="/resource/js/jquery.js" />"></script>
    <style type="text/css">
        body { background-color: white; font-family: Microsoft YaHei; font-size: 12px; }
        .content { margin: 10px 30px 0 30px; }
        .head { text-align: center; color: #9e9e9e; font-size: 14px; line-height: 44px; font-weight: bold; height: 44px; margin-bottom: 5px; border-bottom: 1px solid #efefef; border-top: 3px solid #4e6d8d; }
        .head span { float: left; width: 120px; display: block; cursor: pointer; }
        .head .selected { color: #333; border-top: 3px solid #ffb100 !important; height: 47px; margin-top: -3px; }
        .row { margin-bottom: 5px; }
        .console a { font-size: 14px }
        .comment { color: grey; font-style: italic; }
    </style>
    <script type="text/javascript">
        $(function() {

            if(window != top) {
                top.$(':submit').removeAttr('disabled');
                top.$('.console').html(document.body.innerHTML.replace(/<script[^>]*?>([\s\S](?!<script))*?<\/script>/ig, ''));
                document.body.innerHTML = '';
                return;
            }

            function _tab() {
                var ind = $(this).index();

                $('.head span').removeClass('selected');
                this.className = 'selected';
                $('[tab]').hide().eq(ind).show();
            };

            $('.head span').click(_tab).first().trigger('click');
            $(document).dblclick(function() {$(':submit').removeAttr('disabled')});
        });

        function xsubmit(form) {
            var sub = true;
            $(form).find(':input').each(function() {
                if(!this.value && this.placeholder) {
                    alert(this.placeholder);
                    return sub = false;
                }
            });
            sub && $(':submit').attr('disabled', true);
            return sub;
        }
    </script>
</head>
<body>
<c:if test="${mainPage }">
    <div class="content">
        <div class="head">
            <span>查询list</span>
            <span>移除队列</span>
        </div>
        <div class="body">
            <!-- admin.do -->
            <div tab="0">
                <form name="deadlockForm" target="yyTarget" action="${fileName }" method="get" onsubmit="return xsubmit(this)">
                    <input type="hidden" name="deadlock" value="true" />
                    <div class="row">
                        <input type="submit" value="获取正在跑重试流程" onclick="$('[name=deadlock]').val('true')" />
                    </div>
                </form>
            </div>
            <!-- 切换用户 -->
            <div tab="1">
                <form name="suserForm" target="yyTarget" action="${fileName }" method="post" onsubmit="return xsubmit(this)">
                    <div class="row">
                        <input type="text" name="moveDeadlockId" placeholder="请输入ID" size="50" />
                        <input type="submit" value="移出重试的ID" />
                    </div>
                </form>
            </div>
        </div>
        <!-- 执行结果 -->
        <div class="console"></div>
        <iframe name="yyTarget" frameborder="0" style="display: none"></iframe>
    </div>
</c:if>
<%-- 非主页 --%>
<c:if test="${not mainPage }">
    <pre>${errorText }</pre>
</c:if>
</body>
</html>
