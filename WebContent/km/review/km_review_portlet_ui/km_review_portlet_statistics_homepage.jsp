<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
    <template:replace name="head">
        <!-- 系统重置样式-此次不需要引用 -->
        <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/common.css"/>
        <!-- 工作台样式 -->
        <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/review.css"/>
    </template:replace>
    <template:replace name="body">
        <!-- 流程统计 -->
        <div class="lui_review_statist_panel">
            <ul>
                <li>
                    <h3>
                        <i class="lui_review_statist_icon lui_review_statist_icon_todo"></i><span>${ lfn:message('km-review:kmReview.nav.approval.my') }</span>
                    </h3>
                    <p>
                        <span class="lui_review_statist_item portlet_item">${ lfn:message('km-review:kmReview.nav.week') }<a onclick="bindTimeTogether('listApproval','approval','arrivalTime','week')" target="_blank">${statisticsHomeInfo.review.weekCount}</a></span>
                        <span class="lui_review_statist_item portlet_item">${ lfn:message('km-review:kmReview.nav.month') }<a onclick="bindTimeTogether('listApproval','approval','arrivalTime','month')" target="_blank">${statisticsHomeInfo.review.monthCount}</a></span>
                    </p>
                </li>

                <li>
                    <h3>
                        <i class="lui_review_statist_icon lui_review_statist_icon_done"></i><span>${ lfn:message('km-review:kmReview.nav.approved.my') }</span>
                    </h3>
                    <p>
                        <span class="lui_review_statist_item portlet_item">${ lfn:message('km-review:kmReview.nav.month') }<a onclick="bindTimeTogether('listApproved','approved','resolveTime','month')" target="_blank">${statisticsHomeInfo.reviewedInfo.monthCount}</a></span>
                        <span class="lui_review_statist_item portlet_item">${ lfn:message('km-review:kmReview.nav.year') }<a onclick="bindTimeTogether('listApproved','approved','resolveTime','year')" target="_blank">${statisticsHomeInfo.reviewedInfo.yearCount}</a></span>
                    </p>
                </li>

                <li>
                    <h3>
                        <i class="lui_review_statist_icon lui_review_statist_icon_launch"></i><span>${ lfn:message('km-review:kmReview.nav.create.my') }</span>
                    </h3>
                    <p>
                        <span class="lui_review_statist_item portlet_item">${ lfn:message('km-review:kmReview.nav.month') }<a href="${LUI_ContextPath}/km/review/#j_path=%2FlistCreate&mydoc=create" target="_blank">${statisticsHomeInfo.draft.monthCount}</a></span>
                        <span class="lui_review_statist_item portlet_item">${ lfn:message('km-review:kmReview.nav.year') }<a href="${LUI_ContextPath}/km/review/#j_path=%2FlistCreate&mydoc=create" target="_blank">${statisticsHomeInfo.draft.yearCount}</a></span>
                    </p>
                </li>

                <li>
                    <h3>
                        <i class="lui_review_statist_icon lui_review_statist_icon_lib"></i><span>${ lfn:message('km-review:kmReview.nav.all') }</span>
                    </h3>
                    <p>
                        <span class="lui_review_statist_item portlet_item">${ lfn:message('km-review:kmReview.nav.month') }<a href="${LUI_ContextPath}/km/review/#j_path=%2FlistAll&mydoc=all" target="_blank">${statisticsHomeInfo.all.monthCount}</a></span>
                        <span class="lui_review_statist_item portlet_item">${ lfn:message('km-review:kmReview.nav.year') }<a href="${LUI_ContextPath}/km/review/#j_path=%2FlistAll&mydoc=all" target="_blank">${statisticsHomeInfo.all.yearCount}</a></span>
                    </p>
                </li>
            </ul>
        </div>
        <script type="text/javascript">
            /**
             * 获取指定日期的周的第一天、月的第一天、季的第一天、年的第一天
             * @param date new Date()形式，或是自定义参数的new Date()
             * @returns 返回值为格式化的日期，yy-mm-dd
             */
            //日期格式化，返回值形式为yy-mm-dd
            function timeFormat(date) {
                if (!date || typeof (date) === "string") {
                    this.error("参数异常，请检查...");
                }
                var y = date.getFullYear(); //年
                var m = date.getMonth() + 1; //月
                var d = date.getDate(); //日
                return y + "-" + m + "-" + d;
            }

            //获取这周的周一
            function getFirstDayOfWeek() {
                var date = new Date();
                var weekday = date.getDay() || 7; //获取星期几,getDay()返回值是 0（周日） 到 6（周六） 之间的一个整数。0||7为7，即weekday的值为1-7
                date.setDate(date.getDate() - weekday);//往前算（weekday-1）天，年份、月份会自动变化
                return timeFormat(date);
            }

            //获取当月第一天
            function getFirstDayOfMonth() {
                var date = new Date();
                date.setDate(1);
                return timeFormat(date);
            }

            //获取当年第一天
            function getFirstDayOfYear() {
                var date = new Date();
                date.setDate(1);
                date.setMonth(0);
                return timeFormat(date);
            }

            //跳转url拼接日期
            function bindTimeTogether(methodName, doctype, type, timeFlag) {
                if ("week" === timeFlag) {
                    var url = "${LUI_ContextPath}/km/review/#j_path=%2F" + methodName + "&mydoc=" + doctype + "&cri.q=" + type + "%3A" + getFirstDayOfWeek() + "%3B" + type + "%3A" + timeFormat(new Date());
                    window.open(url);
                }
                if ("month" === timeFlag) {
                    var url = "${LUI_ContextPath}/km/review/#j_path=%2F" + methodName + "&mydoc=" + doctype + "&cri.q=" + type + "%3A" + getFirstDayOfMonth() + "%3B" + type + "%3A" + timeFormat(new Date());
                    window.open(url);
                }
                if ("year" === timeFlag) {
                    var url = "${LUI_ContextPath}/km/review/#j_path=%2F" + methodName + "&mydoc=" + doctype + "&cri.q=" + type + "%3A" + getFirstDayOfYear() + "%3B" + type + "%3A" + timeFormat(new Date());
                    window.open(url);
                }
            }
            //自适应
            domain.autoResize();
        </script>
    </template:replace>
</template:include>
