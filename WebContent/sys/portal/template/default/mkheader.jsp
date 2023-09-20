<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn" %>
<%@ include file="/sys/portal/template/default/reimbursement.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@page import="com.landray.kmss.util.StringUtil,net.sf.json.JSONObject" %>
<%@page import="com.landray.kmss.sys.portal.util.PortalUtil,java.util.Map" %>
<%@ page import="java.util.Locale,java.util.Date" %>
<%@ page import="com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.DateUtil" %>
<%@ page import="com.landray.kmss.sys.notify.service.spring.SysNotifyEmailServiceImp" %>
<%@ page import="com.landray.kmss.sys.profile.util.LoginNameUtil" %>
<link rel="stylesheet" href="${LUI_ContextPath}/sys/portal/template/default/default-mk-1/css/default.css">
<style>
    /* 默认 50px；可调整 70px；90px； 通过padding值进行调整 starts */

    <c:if test="${ param['headH']==null || param['headH']=='50px' }">
    .lui_single_menu_header_1.lui_single_menu_header,
    .lui_single_menu_header_1 .lui_single_menu_header_menu_item_wrap{
        height:50px;
    }
    .lui_single_menu_header_1 .lui_single_menu_header_item,
    .lui_single_menu_header_1 .lui_single_menu_header_menu_item_body .lui_single_menu_header_menu_item_div,
    .lui_single_menu_header_1 .lui_single_menu_header_logo {
        padding-top:0px;
        padding-bottom: 0px;
    }
    .lui_single_menu_header_1 .lui_single_menu_header_searchbar{
        margin-top:10px;
    }
    </c:if>
    <c:if test="${ param['headH']=='60px' }">
    .lui_single_menu_header_1.lui_single_menu_header,
    .lui_single_menu_header_1 .lui_single_menu_header_menu_item_wrap{
        height:60px;
    }
    .lui_single_menu_header_1 .lui_single_menu_header_item,
    .lui_single_menu_header_1 .lui_single_menu_header_menu_item_body .lui_single_menu_header_menu_item_div,
    .lui_single_menu_header_1 .lui_single_menu_header_logo,
    .lui_single_menu_header_1 .lui_single_menu_header_menu_item_line {
        padding-top:5px;
        padding-bottom: 5px;
    }
    .lui_single_menu_header_1 .lui_single_menu_header_searchbar{
        margin-top:15px;
    }
    </c:if>
    <c:if test="${ param['headH']=='70px' }">
    .lui_single_menu_header_1.lui_single_menu_header,
    .lui_single_menu_header_1 .lui_single_menu_header_menu_item_wrap{
        height:70px;
    }
    .lui_single_menu_header_1 .lui_single_menu_header_item,
    .lui_single_menu_header_1 .lui_single_menu_header_menu_item_body .lui_single_menu_header_menu_item_div,
    .lui_single_menu_header_1 .lui_single_menu_header_logo,
    .lui_single_menu_header_1 .lui_single_menu_header_menu_item_line {
        padding-top:10px;
        padding-bottom: 10px;
    }
    .lui_single_menu_header_1 .lui_single_menu_header_searchbar{
        margin-top:20px;
    }
    </c:if>
    /* 默认 50px；可调整 70px；90px； 通过padding值进行调整 ends */
</style>
<div class="lui_single_menu_header_h_1 lui_single_menu_header_h"></div>
<div class="lui_single_menu_header lui_single_menu_header_1 lui_single_menu_header_1_${param['headH']}">
    <%@ include file="/sys/portal/sys_portal_notice/import/sysPortalNotice_view.jsp" %>
    <%@ include file="/sys/portal/pop/import/view.jsp" %>
    <%@ include file="/sys/portal/header/config.jsp" %>

    <div style="width: ${ empty param['headW'] ? (empty param['width'] ? '980px' : param['width']) : param['headW']}${ empty param['headW'] ? '' : '%'};min-width:980px;max-width:${ empty param['headW'] ? (param['width'] == '100%' ? '100%' : fdHeaderMaxWidth) : param['headW'] }${ empty param['headW'] ? '' : '%'};"
         class="lui_single_menu_header_box">
        <div class="lui_single_menu_header_left">
            <div class="lui_single_menu_header_logo" style="width:${param['logoW']}px;">
                <%
                    String logoUrl = request.getParameter("logoUrl");
                    if(StringUtil.isNotNull(logoUrl)){
                        if(logoUrl.startsWith("http://") || logoUrl.startsWith("https://")){
                            request.setAttribute("href",logoUrl);
                        }
                    }
                %>
                <c:choose>
                    <c:when test="${empty href}">
                        <a href="<c:url value='/sys/portal/page.jsp' />">
                            <portal:logo/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${href}">
                            <portal:logo/>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <!-- 切换门户 -->
            <c:if test="${ param['showPortal']==null || param['showPortal']=='1' || param['showPortal']=='2' }">
                <div class="lui_single_menu_header_item lui_single_menu_header_portal" data-lui-switch-class='hover'>
                    <c:choose>
                        <c:when test="${ empty requestScope.headerPortalName }">
                            ${lfn:message('sys-portal:header.msg.switchportal')}
                        </c:when>
                        <c:otherwise>
                            ${requestScope.headerPortalName}
                        </c:otherwise>
                    </c:choose>
                    <div class="lui_dropdown_toggle">
                        <span class="lui_single_menu_header_icon lui_icon_l_icon_arrow_d"></span>
                        <i class="lui_single_menu_header_icon"></i>
                    </div>
                    <ui:popup align="down-left" borderWidth="2" style="margin-right:10px;">
                        <c:if test="${param['showPortal']=='2' }">
                            <div class="lui_single_menu_header_portal_popup lui_single_menu_header_1"
                                 style="width:600px;padding: 8px; background:white;max-height:500px;overflow-y: auto;overflow-x: hidden;">
                                <ui:dataview format="sys.ui.treeMenu2">
                                    <ui:source type="AjaxJson">
                                        {"url":"/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=portalNavTree"}
                                    </ui:source>
                                    <ui:render ref="sys.ui.treeMenu2.portal2" cfg-for="switchPortal"></ui:render>
                                </ui:dataview>
                            </div>
                        </c:if>
                        <c:if test="${param['showPortal']!='2' }">
                            <div class="lui_single_menu_header_portal_popup lui_single_menu_header_1"
                                 style="width:220px;max-height:500px;background:white;overflow-y: auto;overflow-x: hidden;">
                                <ui:dataview>
                                    <ui:source type="AjaxJson">
                                        {"url":"/sys/portal/sys_portal_main/sysPortalMain.do?method=portal"}
                                    </ui:source>
                                    <ui:render ref="sys.ui.treeMenu.flat"/>
                                    <ui:event event="treeMenuChanged" args="args">
                                        $(args.element).parents('.lui_tlayout_header_page_popup').parent().hide();
                                        if(args.target != '_blank'){
                                        var $dom = $('[data-channel="'+ args.channel +'"]');
                                        if($dom && $dom.length > 0){
                                        $dom.text(args.text);
                                        }
                                        $(args.element).parents('.lui_tlayout_header_page_popup').find('.selected').removeClass('selected');
                                        $(args.element).addClass('selected');

                                        var appNavNodes = $('.lui_tlayout_header_app span[data-channel="switchAppNav"]');
                                        if(appNavNodes && appNavNodes.length>0){
                                        appNavNodes.text('${lfn:message('sys-portal:portlet.theader.item.app') }');
                                        }
                                        }
                                    </ui:event>
                                    <ui:event event="treeMenuLoaded" args="args">
                                        var pageName = '${requestScope.headerPortalPageName}';
                                        if(pageName){
                                        var index = 0 ;
                                        args.treeItems.each(function(idx,item){
                                        var itemName = $(item).text().trim();
                                        if(pageName.trim() == itemName){
                                        index = idx;
                                        }
                                        });
                                        args.treeItems.eq(index).mouseenter();
                                        }
                                    </ui:event>
                                </ui:dataview>
                            </div>
                        </c:if>
                    </ui:popup>

                </div>
            </c:if>

            <!-- 应用starts -->
            <%
                String showApp = request.getParameter("showApp");
                JSONObject showAppJson = new JSONObject();
                if (StringUtil.isNotNull(showApp) && showApp.indexOf("}") > -1) {
                    showAppJson = JSONObject.fromObject(showApp);
                }
                request.setAttribute("_showApp", showAppJson);
            %>
            <c:if test="${_showApp['showApp']=='true' && not empty _showApp['showAppId'] }">
                <div class="lui_single_menu_header_item lui_single_menu_header_app" data-lui-switch-class='hover'>
                    <div class="lui_dropdown_toggle">
						<span data-channel="switchAppNav">
                                ${lfn:message('sys-portal:portlet.theader.item.app')}
                        </span>
                        <i class="lui_single_menu_header_icon lui_icon_l_icon_arrow_d"></i>
                    </div>
                    <ui:popup align="down-left" id="lui_single_menu_header_app_popup" borderWidth="2"
                              style="width:100%;left:0;border-radius: 0;background:#fff;padding-bottom: 20px;border: none!important;">
                        <div class="lui_single_menu_header_app_popup lui_single_menu_header_1"
                             style="min-width:100%;padding: 8px; background:white;max-height:500px;overflow-y: auto;overflow-x: hidden;">
                            <ui:dataview format="sys.ui.treeMenu2" cfg-channel="switchAppNav">
                                <ui:source type="AjaxJson">
                                    {"url":"/sys/portal/sys_portal_nav/sysPortalNav.do?method=portlet&fdId=${_showApp['showAppId']}"}
                                </ui:source>
                                <ui:render ref="sys.portal.navTree.app" cfg-for="switchAppNav"></ui:render>
                                <ui:event event="appNavChanged" args="evt">
                                    var $dom = $('[data-channel="'+ evt.channel +'"]');
                                    var target = evt.target;
                                    if($dom && $dom.length > 0){
                                    if(target!="_blank"){
                                    $dom.text(evt.text);
                                    }
                                    }
                                    $(evt.element).parents('.lui_single_menu_header_app_popup').parent().hide();
                                    var pageNodes = $('.lui_single_menu_header_page span[data-channel="switchPage"]');
                                    if(pageNodes && pageNodes.length>0 && target!="_blank"){
                                    $('.lui_single_menu_header_page_popup .lui_dataview_treemenu2_portal_lv1_c.selected').removeClass('selected');
                                    }
                                </ui:event>
                            </ui:dataview>
                        </div>
                    </ui:popup>
                    <script type="text/javascript">
                        seajs.use(['lui/jquery'], function ($) {
                            // 控制应用滚动条滚轮滑动至底部或顶部时，继续滑动右侧内容区不会跟随滚动
                            $.fn.addMouseWheel = function () {
                                return $(this).each(function () {
                                    $(this).on("mousewheel DOMMouseScroll", function (event) {
                                        var scrollTop = this.scrollTop,
                                            scrollHeight = this.scrollHeight,
                                            height = this.clientHeight;
                                        var delta = (event.originalEvent.wheelDelta) ? event.originalEvent
                                            .wheelDelta : -(event.originalEvent.detail || 0);
                                        if ((delta > 0 && scrollTop <= delta) || (delta < 0 && scrollHeight -
                                            height - scrollTop <= -1 * delta)) {
                                            this.scrollTop = delta > 0 ? 0 : scrollHeight;
                                            // 向上/向下滚
                                            event.preventDefault();
                                        }
                                    });
                                });
                            };
                            $('.lui_single_menu_header_app_popup').addMouseWheel();

                            // 鼠标移入“应用”时，重新计算设置应用浮动弹出框的宽度
                            $(".lui_single_menu_header_app").mouseenter(function () {
                                // 计算并设置浮动弹出框的宽度（ 取页眉“应用”导航至window窗口最右侧的总宽度，再乘以百分比值 ）
                                var $appPopup = $(".lui_single_menu_header_app_popup");
                                var appPopup_width = ($(window).width() - $(this).offset().left) * 0.8;
                                $appPopup.width(appPopup_width.toFixed(3));
                            });

                        });
                    </script>
                </div>
            </c:if>
            <!-- 应用ends -->

            <!-- 菜单starts -->
            <div class="lui_single_menu_header_menu clearfloat">
                <portal:widget file="/sys/portal/template/default/default-mk-1/menu/menu.jsp"></portal:widget>
            </div>
            <!-- 菜单ends -->
        </div>
        <!--右边个人信息starts-->
        <div class="lui_single_menu_header_right">
            <!-- 搜索starts -->
            <c:if test="${ param['showSearch']==null || param['showSearch']=='true' }">
            <div class="lui_single_menu_header_searchbar" data-lui-switch-class='hover'>
                <portal:widget file="/sys/portal/template/default/default-mk-1/ftsearch/search.jsp"></portal:widget>
            </div>
            </c:if>
            <!-- 搜索ends -->
            <!-- 信息 -->
            <div class="lui_single_menu_header_infobar">
                <!-- 收藏 -->
                <c:if test="${ param['showFavorite']==null  || param['showFavorite']=='true' }">
                <div class="lui_single_menu_header_item lui_single_menu_header_favorite" id="__my_bookmark__"
                     data-lui-switch-class='hover'>
                    <div class="lui_dropdown_toggle">
                        <span class="lui_single_menu_header_icon lui_icon_l_icon_favorite"></span>
                        <span class="lui_header_text lui_header_text_favorite">收藏</span>
                    </div>
                    <ui:popup borderWidth="${ empty param['popupborder'] ? '2' : param['popupborder'] }"
                              align="down-left" positionObject="#__my_bookmark__"
                              style="background:white;margin-right:10px;">
                        <div style="width:260px;">
                            <div class="clearfloat">
                                <div style="float: right;padding: 5px;margin-right:20px;">
                                    <ui:button styleClass="lui_toolbar_btn_gray"
                                               text="${ lfn:message('sys-bookmark:header.msg.favoritemng') }"
                                               href="javascript:void(0);"
                                               onclick="__onOpenPage('${ LUI_ContextPath }/sys/person/setting.do?setting=sys_bookmark_person_cfg','_blank');"
                                               target="_blank"></ui:button>
                                </div>
                            </div>
                            <ui:menu layout="sys.ui.menu.ver.default">
                                <ui:menu-source autoFetch="true" target="_blank">
                                    <ui:source type="AjaxJson">
                                        {"url":"/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=portlet&parentId=!{value}"}
                                    </ui:source>
                                </ui:menu-source>
                            </ui:menu>
                        </div>
                    </ui:popup>
                </div>
                </c:if>
                <!-- 待办待阅-通知 -->
                <c:if test="${ param['showNotify'] == 1 }">
                <div class="lui_single_menu_header_item lui_single_menu_header_notify lui_single_menu_header_notify_msg">
                    <portal:widget file="/sys/portal/template/default/default-mk-1/notify/notify.jsp">
                        <portal:param name="refreshTime"
                                      value="${empty param['refreshTime'] ? '' : param['refreshTime'] }"/>
                    </portal:widget>

                </div>
                </c:if>
                <!-- 待办待阅-分开显示 -->
                <c:if test="${ param['showNotify']==null || param['showNotify'] == 2 }">
                <div class="lui_single_menu_header_item lui_single_menu_header_notify">
                    <portal:widget file="/sys/portal/template/default/default-mk-1/notify/count.jsp">
                        <portal:param name="refreshTime"
                                      value="${empty param['refreshTime'] ? '' : param['refreshTime'] }"/>
                    </portal:widget>
                </div>
                </c:if>
                <!-- 个人信息 -->
                <!-- 日程 -->
                <c:if test="${param['showCalendar']=='true'}">
                <div class="lui_single_menu_header_item lui_single_menu_header_calendar" data-lui-switch-class='hover'>
                    <a href="${LUI_ContextPath}/km/calendar">
                        <i class="lui_single_menu_header_icon lui_icon_l_icon_schedule"></i>
                        <span class="com_prompt_num"></span>
                    </a>
                    <!-- 当前日期 -->
                    <%
                        // 当前日期
                        String s_date = DateUtil.convertDateToString(new Date(), "yyyy-MM-dd");
                    %>
                    <script>
                        var calendar_num = 0;
                        $.ajax({
                            url: "${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=getDayEvents&date=<%=s_date%>&s_ajax=true",
                            type: 'GET',
                            dataType: 'json',
                            success: function (data) {
                                calendar_num = data.length;
                                if(calendar_num == 0){
                                    $(".lui_single_menu_header_calendar .com_prompt_num").hide();
                                }else{
                                    $(".lui_single_menu_header_calendar .com_prompt_num").text(calendar_num);
                                }                            }
                        });
                    </script>
                </div>
                </c:if>
                <!-- 邮件 -->
                <c:if test="${param['showEmail']=='true'}">
                <div class="lui_single_menu_header_item lui_single_menu_header_email" data-lui-switch-class='hover'>
                    <a href="javascript:;" target="_blank">
                        <i class="lui_single_menu_header_icon lui_icon_l_icon_email"></i>
                        <span class="com_prompt_num" id="lui_single_menu_email_num">0</span>
                        <% /**  显示 邮件数量    **/ %>
                        <div class="lui_single_menu_header_email_none"
                             style="display:inline-block;width: 0;height: 0;overflow: hidden;">
                            <%@ include file="/sys/notify/sys_notify_todo_ui/email.jsp" %>
                        </div>
                        <script>
                            var _num = $(".lui_single_menu_header_infobar #num_block").html() || 0;
                            var _mail_url = $(".lui_single_menu_header_infobar #div_num_nozero a").attr("href");
                            var _mailNumCount = 0;
                            var timer = setInterval(function () {
                                _mailNumCount++;
                                _num = 0;
                                var txt = $(".lui_single_menu_header_infobar #num_block").text().trim();
                                if (txt != '\\n\\t' && txt != '') {
                                    _num = txt;
                                }
                                _mail_url = $(".lui_single_menu_header_infobar #div_num_nozero a").attr("href")
                                $(".lui_single_menu_header_infobar #lui_single_menu_email_num").text(_num);
                                $(".lui_single_menu_header_infobar .lui_single_menu_header_email").children('a').first().attr("href", _mail_url);
                                if (_mailNumCount > 5) clearInterval(timer)
                            }, 1000)
                        </script>
                    </a>
                </div>
                </c:if>

                <!-- 显示头像 -->
                <c:if test="${ param['showPerson']==null  || param['showPerson']=='true' }">
                <c:if test="${ param['showAvatar']==null ||  param['showAvatar']=='0' }">
                <div class="lui_single_menu_header_item lui_single_menu_header_person lui_single_menu_header_person_avatar"
                     data-lui-switch-class='hover'>
                    <div class="lui_dropdown_toggle">
							<span class="lui_single_menu_avatar">
								<img alt=""
                                     src="<person:headimageUrl personId='${KMSS_Parameter_CurrentUserId}' contextPath='true' size='m'/>"
                                     contextPath="true" size="90"/>">
							</span>
                        <i class="lui_single_menu_header_icon lui_icon_l_icon_arrow_d"></i>
                    </div>
                    </c:if>
                    <!-- 显示头像+名字 -->
                    <c:if test="${ param['showAvatar']=='1' }">
                    <div class="lui_single_menu_header_item lui_single_menu_header_person lui_single_menu_header_person_avatar"
                         data-lui-switch-class='hover'>
                        <div class="lui_dropdown_toggle">
							<span class="lui_single_menu_avatar">
								<img alt="" src="<person:headimageUrl personId="${KMSS_Parameter_CurrentUserId}"
									contextPath="true" size="90" />">
							</span>
                            <span
                                    class="lui_single_menu_name"><%= PortalUtil.htmlEncode2Portal(java.net.URLDecoder.decode(UserUtil.getKMSSUser().getUserName(), "UTF-8")) %></span>
                            <i class="lui_single_menu_header_icon lui_icon_l_icon_arrow_d"></i>
                        </div>
                        </c:if>
                        <!-- 显示头像+欢迎语+名字 -->
                        <c:if test="${ param['showAvatar']=='2' }">
                        <div class="lui_single_menu_header_item lui_single_menu_header_person lui_single_menu_header_person_avatar"
                             data-lui-switch-class='hover'>
                            <div class="lui_dropdown_toggle">
							<span class="lui_single_menu_avatar">
								<img alt="" src="<person:headimageUrl personId="${KMSS_Parameter_CurrentUserId}"
									contextPath="true" size="90" />">
							</span>
                                <span class="lui_single_menu_wel">${param['showWelcome']}</span>
                                <span
                                        class="lui_single_menu_name"><%= PortalUtil.htmlEncode2Portal(java.net.URLDecoder.decode(UserUtil.getKMSSUser().getUserName(), "UTF-8")) %></span>
                                <i class="lui_single_menu_header_icon lui_icon_l_icon_arrow_d"></i>
                            </div>
                            </c:if>
                            <!-- 名字 -->
                            <c:if test="${ param['showAvatar']=='3' }">
                            <div class="lui_single_menu_header_item lui_single_menu_header_person lui_single_menu_header_person_txt"
                                 data-lui-switch-class='hover'>
                                <div class="lui_dropdown_toggle">
							<span
                                    class="lui_single_menu_name"><%= PortalUtil.htmlEncode2Portal(java.net.URLDecoder.decode(UserUtil.getKMSSUser().getUserName(), "UTF-8")) %></span>
                                    <i class="lui_single_menu_header_icon lui_icon_l_icon_arrow_d"></i>
                                </div>
                                </c:if>
                                <!-- 欢迎语+名字 -->
                                <c:if test="${ param['showAvatar']=='4' }">
                                <div class="lui_single_menu_header_item lui_single_menu_header_person lui_single_menu_header_person_txt"
                                     data-lui-switch-class='hover'>
                                    <div class="lui_dropdown_toggle">
                                        <span class="lui_single_menu_wel">${param['showWelcome']}</span>
                                        <span
                                                class="lui_single_menu_name"><%= PortalUtil.htmlEncode2Portal(java.net.URLDecoder.decode(UserUtil.getKMSSUser().getUserName(), "UTF-8")) %></span>
                                        <i class="lui_single_menu_header_icon lui_icon_l_icon_arrow_d"></i>
                                    </div>
                                    </c:if>
                                    <ui:popup align="down-left" borderWidth="2" style="margin-right:10px;">
                                        <div class="lui_tlayout_header_person_popup">
                                            <ul class="lui_tlayout_dropdown_list">
                                                <li>
                                                    <a onclick="__onOpenPage('${ LUI_ContextPath }/sys/zone/index.do?userid=<%= UserUtil.getKMSSUser().getUserId() %>','_blank')"
                                                       href="javascript:void(0);"><span class="txt">Hi,</span>
                                                        <span
                                                                class="info"><%= PortalUtil.htmlEncode2Portal(java.net.URLDecoder.decode(UserUtil.getKMSSUser().getUserName(), "UTF-8")) %></span>
                                                    </a>
                                                </li>
                                                <%
                                                    String areaName = UserUtil.getKMSSUser().getAuthAreaName();
                                                    if (areaName != null && areaName.trim().length() > 0) {
                                                %>
                                                <li>
                                                    <a onclick="javascript:__switchArea()"
                                                       href="javascript:void(0);"><span class="txt">${
                                                            lfn:message('sys-person:header.msg.areaName') }:</span>
                                                        <span class="area info"><%= areaName %></span>
                                                    </a>
                                                </li>
                                                <%}%>
                                                <li class="lui_tlayout_header_person_hr">
                                                    <a onclick="__onOpenPage('${ LUI_ContextPath }/sys/person','_blank')"
                                                       href="javascript:void(0);">
                                                            ${ lfn:message('sys-person:header.msg.setting') }
                                                    </a>
                                                </li>
                                                <c:if test="${ param['showZone']==null  || param['showZone']=='true' }">
                                                    <kmss:ifModuleExist path="/sns/ispace/">
                                                        <%request.setAttribute("isExistIspace", true);%>
                                                    </kmss:ifModuleExist>
                                                    <%
                                                        Boolean isExistIspace =
                                                                (Boolean) request.getAttribute("isExistIspace");
                                                        if (isExistIspace != null && isExistIspace) {
                                                    %>
                                                    <li>
                                                        <a onclick="__onOpenPage('${ LUI_ContextPath }/sns/ispace','_blank')"
                                                           href="javascript:void(0);">${ lfn:message('sys-person:header.msg.zone') }</a>
                                                    </li>
                                                    <%
                                                    } else {
                                                    %>
                                                    <li>
                                                        <a onclick="__onOpenPage('<person:zoneUrl personId="
									${KMSS_Parameter_CurrentUserId}"/>','_blank')" href="javascript:void(0);">
                                                                ${ lfn:message('sys-person:header.msg.zone') }
                                                        </a>
                                                    </li>
                                                    <%
                                                        }
                                                    %>
                                                </c:if>

                                                <c:if test="${ param['showFollow']==null  || param['showFollow']=='true' }">
                                                    <li>
                                                        <a onclick="__onOpenPage('${ LUI_ContextPath }/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated_person.jsp','_blank')"
                                                           href="javascript:void(0);">
                                                                ${ lfn:message('sys-follow:sysFollow.person.my') }
                                                        </a>
                                                    </li>
                                                </c:if>
                                                    <%--
                                                            <c:if test="${ param['showWarningreport']==null  || param['showWarningreport']=='true' }">
                                                            <li>
                                                                <a id ="_qywechat">
                                                                    ${lfn:message('sys-portal:portlet.header.var.warningreport')}
                                                                </a>
                                                                <ui:popup align="left-top" borderWidth="2">
                                                                    <div >
                                                                        <div id="_qywechatimg"></div>
                                                                    </div>

                                                                </ui:popup>
                                                            </li>
                                                            </c:if>
                                                            --%>
                                                <%
                                                    Map langMap = PortalUtil.getSupportLang();
                                                    request.setAttribute("_langMap", langMap);
                                                %>
                                                <c:if
                                                        test="${fn:length(_langMap)>0 && (param['showLang']==null  || param['showLang']=='true') }">
                                                    <li class="lui_tlayout_header_person_hr">
                                                        <a target="_blank">
                                                                ${ lfn:message('sys-portal:portlet.theader.var.switchLang') }
                                                        </a>
                                                        <ui:popup align="left-top" borderWidth="2"
                                                                  style="background:white;">
                                                            <div class="lui_single_menu_header_person_lang">
                                                                <ul>
                                                                    <c:forEach var="item" items="${_langMap }">
                                                                        <li><a href="javascript:void(0)"
                                                                               onclick="tHeaderChangeLang('${item.key}')">${item.value
                                                                                }</a></li>
                                                                    </c:forEach>
                                                                </ul>
                                                                <script type="text/javascript">
                                                                    function tHeaderChangeLang(value) {
                                                                        var url = window.location.href;
                                                                        var isPage = false;
                                                                        if (url.indexOf('#') > -1) {
                                                                            var urlPrx = url.substring(0, url.indexOf('#'));
                                                                            var hash = url.substring(url.indexOf('#'), url.length);

                                                                            if (hash.indexOf("pageId") > -1) {
                                                                                isPage = true;
                                                                            }
                                                                            url = Com_SetUrlParameter(urlPrx, "j_lang", value);
                                                                            if (!isPage) {
                                                                                url = url + hash;
                                                                            }
                                                                        } else {
                                                                            if (url.indexOf("pageId") > -1) {
                                                                                isPage = true;
                                                                            }
                                                                            if (isPage) {
                                                                                if (location.search != "") {
                                                                                    var paraList = window.location.search.substring(1).split("&");
                                                                                    var newUrl = url.substring(0, url.indexOf("?"));
                                                                                    var i, j, para, pValue;
                                                                                    for (i = 0; i < paraList.length; i++) {
                                                                                        j = paraList[i].indexOf("=");
                                                                                        if (j == -1)
                                                                                            continue;
                                                                                        para = paraList[i].substring(0, j);
                                                                                        pValue = Com_GetUrlParameter(url, para);
                                                                                        if (pValue && para != "pageId" && para != "j_lang")
                                                                                            newUrl = Com_SetUrlParameter(newUrl, para, decodeURIComponent(paraList[i]
                                                                                                .substring(j + 1)));
                                                                                    }
                                                                                    url = newUrl;
                                                                                }
                                                                            }
                                                                            url = Com_SetUrlParameter(url, "j_lang", value);
                                                                        }
                                                                        location.href = url;
                                                                    }
                                                                </script>
                                                            </div>
                                                        </ui:popup>
                                                    </li>
                                                </c:if>
                                                <c:if test="${ param['showManager']==null  || param['showManager']=='true' }">
                                                    <kmss:authShow roles="SYSROLE_ADMIN">
                                                        <c:set var="mngHrClass"
                                                               value="lui_tlayout_header_person_hr"></c:set>
                                                        <c:if
                                                                test="${fn:length(_langMap)>0 && (param['showLang']==null  || param['showLang']=='true') }">
                                                            <c:set var="mngHrClass" value=""></c:set>
                                                        </c:if>
                                                        <li class="${mngHrClass}">
                                                            <a href="${ LUI_ContextPath }/sys" target="_blank">
                                                                    ${ lfn:message('sys-portal:portlet.theader.var.manager') }
                                                            </a>
                                                        </li>
                                                    </kmss:authShow>
                                                </c:if>
                                                <!-- 蓝桥管理台 -->
                                                <kmss:ifModuleExist path="/lding/console">
                                                    <kmss:authShow roles="ROLE_LDINGCONSOLE_DEFAULT">
                                                        <c:if
                                                                test="${ param['showLDingService']==null  || param['showLDingService']=='true' }">
                                                            <li>
                                                                <a href="${ LUI_ContextPath }/lding/console/index.jsp"
                                                                   target="_blank">
                                                                        ${ lfn:message('sys-portal:portlet.theader.var.showLDingService') }
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                    </kmss:authShow>
                                                </kmss:ifModuleExist>
                                                <!-- 蓝小悦售后 -->
                                                <%
                                                    boolean adminFlag = LoginNameUtil.isAdmin();
                                                    request.setAttribute("adminFlag", adminFlag);
                                                %>
                                                <c:if test="${adminFlag eq true}">
                                                    <li>
                                                        <a href="javascript:void(0)" onclick="getBlueAfterUrl()">
                                                                ${ lfn:message('sys-profile:sys.profile.theader.var.afterService') }
                                                        </a>
                                                    </li>
                                                    <script type="text/javascript">
                                                        function getBlueAfterUrl(){
                                                            $.ajax({
                                                                url: '${LUI_ContextPath}/sys/profile/sysProfileBlueAfterAction.do?method=getBlueAfterServiceUrl',
                                                                type: 'POST',
                                                                dataType: 'json',
                                                                success: function(data){
                                                                    var url = decodeURIComponent(data.url);
                                                                    Com_OpenWindow(url, "_blank");
                                                                }
                                                            });
                                                        }
                                                    </script>
                                                </c:if>
                                                <c:if test="${KMSS_Parameter_ClientType!='-3' }">
                                                    <li class="lui_tlayout_header_person_hr">
                                                        <a href="javascript:void(0)" onclick="__sys_logout()">${
                                                                lfn:message('sys-person:header.msg.logout') }
                                                        </a>
                                                    </li>
                                                </c:if>
                                            </ul>
                                        </div>
                                    </ui:popup>
                                </div>
                                </c:if>
                                <!-- 退出 -->
                                <c:if test="${ (param['showLogout']=='true') && KMSS_Parameter_ClientType!='-3'}">
                                    <div class="lui_single_menu_header_item lui_single_menu_header_logout"
                                         data-lui-switch-class='hover'>
                                        <div title="${ lfn:message('sys-person:header.msg.logout') }"
                                             class="lui_dropdown_toggle"
                                             onclick="__sys_logout()">
                                            <i class="lui_single_menu_header_icon lui_icon_l_icon_logout"></i>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- 右边个人信息ends-->

                    </div>
                </div>
                <script type="text/javascript">
                    var messagetips = '<bean:message key="portlet.header.var.warninginfor" bundle="sys-portal"/>';
                    // 页眉消息提醒国际化语言
                    var theaderMsg = {
                        "dialog.locale.winTitle": "${lfn:message('dialog.locale.winTitle')}", // 请选择场所
                        "authArea.not.found.portalPage.tip": "${lfn:message('sys-portal:portlet.theader.authArea.not.found.portalPage.tip')}", // 场所下未找到可访问的门户页面
                        "home.logout.confirm": "${lfn:message('home.logout.confirm')}" // 该操作将退出系统，是否继续？
                    };
                    Com_IncludeFile("ticketcode.js", '${LUI_ContextPath}/sys/portal/designer/js/', "js", true);
                </script>
                <script type="text/javascript"
                        src="${LUI_ContextPath}/sys/portal/template/default/theader.js?s_cache=${LUI_Cache}">
                </script>
                <script type="text/javascript">
                    var navigationSettingConfig = ${ empty param["navigationSettings"] ? "null" : param["navigationSettings"] }; // 经典页面-导航设置 配置内容
                    var messagetips = '<bean:message key="portlet.header.var.warninginfor" bundle="sys-portal"/>';
                    Com_IncludeFile("ticketcode.js", '${LUI_ContextPath}/sys/portal/designer/js/', "js", true);
                </script>