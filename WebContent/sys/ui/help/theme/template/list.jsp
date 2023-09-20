<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
    function selectIdText(obj) {
        Com_GetEventObject().cancelBubble = true;
        var span = LUI.$(obj);
        var input = LUI.$("<input style='position: absolute;width:" + (span.width() + 16) + "px; border:0px;'>")
            .insertBefore(span);
        input.val(span.text());
        input[0].select();
        input[0].focus();
        input.click(function () {
            return false;
        });
        input.blur(function () {
            input.remove();
        });
    }
</script>

<ul class="lux-theme-list">
    <c:forEach items="${datas}" var="data" varStatus="vstatus">
        <li class="lux-theme-item" kmss_id="${data.fdId}" <c:choose>
            <c:when test="${not empty onRowClick}">
                style="cursor:pointer;"
                onclick="${onRowClick}"
                kmss_help="${data.fdHelp}"
            </c:when>
            <c:when test="${not empty data.fdHelp}">
                style="cursor:pointer;"
                onclick="location.href='${LUI_ContextPath}${data.fdHelp}';"
            </c:when>
            </c:choose>
           >
            <div class="lux-theme-item-inner">
                <i class="lux-theme-no">${vstatus.index+1}</i>
                <div class="lux-theme-thumb">
                    <img src="${LUI_ContextPath}${data.fdThumb}">
                    <ui:popup>
                        <img src="${LUI_ContextPath}${data.fdThumb}" style="max-height:500px;">
                    </ui:popup>
                </div>
                <p><span>
                        <c:out value="${data.fdId}" />
                    </span>
                    <span>
                        <c:out value="${data.fdName}" />
                    </span>
                    <span>
                        <c:out value="${data.fdPath}" />
                    </span>
                </p>
            </div>
        </li>
    </c:forEach>

</ul>