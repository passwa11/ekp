<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sunbor.web.tag.Page" %>
<%@ page import="com.landray.kmss.third.mall.util.MallUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
        request.setAttribute("mallDomMain",MallUtil.MALL_DOMMAIN);
%>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <script type="text/javascript">
            seajs.use(['theme!profile']);
            seajs.use(['theme!iconfont']);
            Com_IncludeFile("common.css", "${LUI_ContextPath}/third/mall/resource/css/", "css", true);
        </script>
        <script>
            var total = Number("${queryPage.total}"); // 总页数
            var pageno = Number("${queryPage.pageno}"); // 当前页数
            var totalrows = Number("${queryPage.totalrows}"); // 商品总数
            var rowsize = Number("${queryPage.rowsize}"); // 每页显示商品数

            function initialization() {

                <c:if test="${tabType =='over' || tabType =='all'}">
                    buildPage(); // 初始化翻页按钮
                    if (pageno == 1) { // 位于第一页时，禁止上一页操作
                        $(".up-page")[0].style.display="none";
                        // $(".up-page")[0].className = "fy-disabled";

                    }
                    if (pageno == total) { // 位于末页时，禁止下一页操作
                        $(".down-page")[0].style.display="none";
                        // $(".down-page")[0].className = "fy-disabled";
                    }
                    $(".fy-pagination-jump")[0].children[0].value = pageno; // 到第pageno页
                    if ($("li[index-id='" + pageno + "']")[0]) {
                        $("li[index-id='" + pageno + "']")[0].className = "count-page fy-active";
                    }
                </c:if>
            }

            function numberReckon(pageIndex) { // 计算当前页面商品数量
                var number;
                if (total == 1) { // 页数为1
                    number = totalrows;
                } else if (total == pageIndex) { // 末页
                    number = totalrows - rowsize * (pageIndex - 1);
                } else {
                    number = rowsize;
                }
                return number;
            }

            function buildPage() {
                var firstUrl = "<li class='count-page' index-id=1><a href='javascript:void(0)'>1</a></li>";
                var lastUrl = "<li class='count-page' index-id=" + total + "><a href='javascript:void(0)'>" + total + "</a></li>";
                var nextPageUrl = "<li class='down-page'><a href='javascript:void(0)' aria-label='Next'><span aria-hidden='true'>下一页</span></a></li>";
                var ellipsisUrl = "<li class='fy-ellipsis'><a href='javascript:void(0)'>...</a></li>";
                if (total <= 5) {
                    for (var i = 1; i <= total; i++) {
                        var url = "<li class='count-page' index-id=" + i + "><a href='javascript:void(0)'>" + i + "</a></li>";
                        $(".fy-pagination-list").append(url);
                    }
                } else {
                    $(".fy-pagination-list").append(firstUrl);
                    if (pageno <= 2) {
                        //123...total
                        $(".fy-pagination-list").append("<li class='count-page' index-id=2><a href='javascript:void(0)'>2</a></li>");
                        $(".fy-pagination-list").append("<li class='count-page' index-id=3><a href='javascript:void(0)'>3</a></li>");
                        $(".fy-pagination-list").append(ellipsisUrl);
                    } else if (pageno == 3) {
                        //1234...total
                        for (var i = 2; i <= 4; i++) {
                            var url = "<li class='count-page' index-id=" + i + "><a href='javascript:void(0)'>" + i + "</a></li>";
                            $(".fy-pagination-list").append(url);
                        }
                        $(".fy-pagination-list").append(ellipsisUrl);
                    } else if (pageno == (total - 2)) {
                        //1...pageno-1,pageno,pageno+1,total
                        $(".fy-pagination-list").append(ellipsisUrl);
                        for (var i = (total - 3); i <= (total - 1); i++) {
                            var url = "<li class='count-page' index-id=" + i + "><a href='javascript:void(0)'>" + i + "</a></li>";
                            $(".fy-pagination-list").append(url);
                        }
                    } else if (pageno >= (total - 1)) {
                        //1...total-2,total-1,total
                        $(".fy-pagination-list").append(ellipsisUrl);
                        $(".fy-pagination-list").append("<li class='count-page' index-id=" + (total - 2) + "><a href='javascript:void(0)'>" + (total - 2) + "</a></li>");
                        $(".fy-pagination-list").append("<li class='count-page' index-id=" + (total - 1) + "><a href='javascript:void(0)'>" + (total - 1) + "</a></li>");
                    } else {
                        //1...pageno-1,pageno,pageno+1...total
                        $(".fy-pagination-list").append(ellipsisUrl);
                        $(".fy-pagination-list").append("<li class='count-page' index-id=" + (pageno - 1) + "><a href='javascript:void(0)'>" + (pageno - 1) + "</a></li>");
                        $(".fy-pagination-list").append("<li class='count-page' index-id=" + pageno + "><a href='javascript:void(0)'>" + pageno + "</a></li>");
                        $(".fy-pagination-list").append("<li class='count-page' index-id=" + (pageno + 1) + "><a href='javascript:void(0)'>" + (pageno + 1) + "</a></li>");
                        $(".fy-pagination-list").append(ellipsisUrl);
                    }
                    $(".fy-pagination-list").append(lastUrl);
                }
                $(".fy-pagination-list").append(nextPageUrl);
            }
        </script>
    </template:replace>
    <template:replace name="content">

        <!-- 列表 start -->
        <% if (((Page) request.getAttribute("queryPage")).getTotalrows() == 0) { %>
            <div class="fy-process-tip" id="ulDataList">
                <div class="interlink-main">
                    <c:if test="${tabType=='over'}">
                        <img src="${LUI_ContextPath}/third/mall/resource/images/overNoData.png" alt="">
                        <div class="desc">
                            <span>
                                <bean:message key="third-mall:thirdMall.no_data_tip_1"/>
                            </span>
                           <span stroke="tip">
                                <bean:message key="third-mall:thirdMall.no_data_tip_2"/>
                           </span>
                            </span>
                                <bean:message key="third-mall:thirdMall.no_data_tip_3"/>
                            </span>
                        </div>
                    </c:if>
                    <c:if test="${tabType !='over'}">
                        <img src="${LUI_ContextPath}/third/mall/resource/images/noData@2x.png" alt="">
                        <div class="desc">
                            <span>
                                <bean:message key="third-mall:thirdMall.no_data_tip"/>
                            </span>
                        </div>
                    </c:if>
                </div>
            </div>
        <% } %>
        <% if (((Page) request.getAttribute("queryPage")).getTotalrows() > 00) { %>
            <c:choose>
                <c:when test="${fdKeyType=='sysApplication'}">
                    <!-- 当前是应用-->
                    <ul class="fy-list-process-wrap fy-list-process-app-wrap" id="ulDataList">
                    <%@ include file="./thirdMall_good_application_data.jsp"%>
                    </ul>
                </c:when>
                <c:when test="${fdKeyType=='sysMain'}">
                    <!-- 当前是门户-->
                    <ul class="fy-list-process-wrap" id="ulDataList">
                    <%@ include file="./thirdMall_good_main_data.jsp"%>
                    </ul>
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>
            <!-- 列表 end -->
            <!-- 分页器 start -->
            <c:if test="${tabType =='over' || tabType =='all'}">
                <div class="fy-pagination-container">
                    <ul class="fy-pagination-list">
                        <li class="up-page"><a href="javascript:void(0)"><span
                                aria-hidden="true">${lfn:message('third-mall:kmReuseCommon.lastPage')}</span></a></li>
                    </ul>
                    <div class="fy-pagination-jump">
                            ${lfn:message('third-mall:kmReuseCommon.count')} <c:out
                            value='${queryPage.totalrows}'/> ${lfn:message('third-mall:kmReuseCommon.bars')}，
                            ${lfn:message('third-mall:kmReuseCommon.until')}<input value="1"
                                                                                   oninput="value=value.replace(/[^\d]/g,'')"/>${lfn:message('third-mall:kmReuseCommon.pages')}
                        <button class="confirm-btn">${lfn:message('third-mall:kmReuseCommon.comfirm')}</button>
                    </div>
                </div>
            </c:if>

            <!-- 分页器 end -->
            <script>
                $(document).ready(function () {
                    if (totalrows == 0 && $(".fy-pagination-container").length > 0) {
                        $(".fy-pagination-container")[0].style.display = "none";
                        return;
                    }
                    <c:if test="${tabType =='over' || tabType =='all'}">

                        initialization(); // 页面初始化
                        $(".count-page").click(function () {
                            var pageIndex = $(this).attr("index-id"); // 当前点击页数
                            var number = numberReckon(pageIndex);
                            parent.pageSearch(pageIndex, number); // 执行父页面方法
                        });
                        $(".up-page").click(function () { // 上一页
                            var pageIndex = $(".fy-active").attr("index-id"); // 当前页数
                            var number = numberReckon(Number(pageIndex) - Number(1));
                            parent.pageSearch(Number(pageIndex) - Number(1), number);
                        });
                        $(".down-page").click(function () { // 下一页
                            var pageIndex = $(".fy-active").attr("index-id"); // 当前页数
                            var number = numberReckon(Number(pageIndex) + Number(1));
                            parent.pageSearch(Number(pageIndex) + Number(1), number);
                        });
                        $(".confirm-btn").click(function () { // 确定
                            var pageIndex = $(".fy-pagination-jump")[0].children[0].value; // 指定页数
                            var number = numberReckon(pageIndex);
                            parent.pageSearch(pageIndex, number);
                        });
                    </c:if>
                });
            </script>
        <%}%>
    </template:replace>
</template:include>
