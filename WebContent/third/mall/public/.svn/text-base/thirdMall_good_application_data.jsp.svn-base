<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script>
    function showDetails(fdId){
        var parentFrame = $("#main_show_iframe", window.parent.parent.parent.parent.document);
        var url = '${LUI_ContextPath}/third/mall/thirdMallPublic.do?method=view&fdId='+fdId+"&fdKeyType=${fdKeyType}";
        if(parentFrame && parentFrame.length > 0){
            var iframe = window.parent.parent.parent.parent.document.getElementById("main_show_iframe").children[0];
            iframe.src=url;
        }
    }
</script>
<!--应用的展示列表-->
<c:forEach items="${queryPage.list}" var="dataBean" varStatus="status">
    <li class="fy-process-list-item">
        <c:choose>
            <c:when test="${ dataBean.fdUse eq 'true' }">
                <div class="fd-insert-over-tip">
                        ${lfn:message('third-mall:kmReuseCommon.status.over')}
                </div>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
        <a onclick="showDetails('${dataBean.fdId}')" href="javascript:void(0)">
            <div class="fy-process-list-img-wrap">
                <div class="imgCenter">
                    <c:choose>
                        <c:when test="${ dataBean.pic eq '' }">
                            <img
                                    src="./resource/images/application.png"/>
                        </c:when>
                        <c:otherwise>
                            <img
                                    src="${mallDomMain}/km/reuse/mobile/kmReusePublicMobileAction.do?method=downloadPic&fdId=${dataBean.pic}"/>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
            <div class="title" title="<c:out value='${dataBean.fdName}'/>"><c:out
                    value="${dataBean.fdName}"/>
            </div>
            <div class="desc">
							  <span class="price">
									<c:choose>
                                        <c:when test="${dataBean.fdBusinessStatus == 'presell'}">
                                            ${lfn:message('third-mall:enums.business_status.presell')}
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${dataBean.fdPrice == 0}">
                                                ${lfn:message('third-mall:kmReuseCommon.free')}
                                            </c:if>
                                            <c:if test="${dataBean.fdPrice != 0}">
                                                ¥<c:out value="${dataBean.fdPrice}"/>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
							  </span>
                <span class="original-price" style="display:none">¥2150</span>
                <div class="fy-process-list-item-read">
                    <i class="fy-icon fy-icon-download"></i>
                    <c:choose>
                        <c:when test="${dataBean.fdDownloadCount > 999}">
                            999+
                        </c:when>
                        <c:otherwise>
                            <c:out value="${dataBean.fdDownloadCount}"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="fy-process-list-item-read">
                    <i class="fy-icon fy-icon-eye"></i>
                    <c:choose>
                        <c:when test="${dataBean.fdReadCount > 999}">
                            999+
                        </c:when>
                        <c:otherwise>
                            <c:out value="${dataBean.fdReadCount}"/>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>

        </a>
    </li>
</c:forEach>