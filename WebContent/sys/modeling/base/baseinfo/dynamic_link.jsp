<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/dynamicLink.css?s_cache=${LUI_Cache}"/>

<td class="td_normal_title" width=15%>
	<%--PC端首页访问路径 --%>
    ${ lfn:message('sys-modeling-base:modeling.baseinfo.PCHomepageAccessPath') }
</td>
<td width=85% class="pcIndexUrlPath">
    <div class="modeling-dylink-add dylink-add-pc">
        <div onclick="addDyLink('pc')"><i class="dynamic-i-add"> </i><span>${ lfn:message('sys-modeling-base:modeling.baseinfo.CustomShortChainAddress')} </span></div>
    </div>
</td>
</tr>
<tr>
	<%--移动端首页访问路径 --%>
    <td class="td_normal_title" width=15%>
        ${ lfn:message('sys-modeling-base:modeling.baseinfo.MobileHomepageAccessPath')}
    </td>
    <td width=85% class="mobileIndexUrlPath">
        <div class="modeling-dylink-add dylink-add-mobile">
            <div onclick="addDyLink('mobile')"><i class="dynamic-i-add"> </i><span>${ lfn:message('sys-modeling-base:modeling.baseinfo.CustomShortChainAddress')} </span></div>
        </div>
    </td>
</tr>

<script>
    seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'sys/modeling/base/baseinfo/dnamic_link'],
        function ($, dialog, topic, dynamicLink) {
            ///目前只支持新建一条路径，当用户新建一条后，“自定义短链路径”按钮消失
            topic.channel("modelingDynamicLink").subscribe("dyLink.push", function (rtn) {
                $(".dylink-add-" + rtn.type).hide();
            });
            topic.channel("modelingDynamicLink").subscribe("dyLink.remove", function (rtn) {
                $(".dylink-add-" + rtn.type).show();
            });


            var appId = "${param.fdId}";
            var originalLink_Pc = new dynamicLink.ModelingDynamicLink({
                "type": "pc",
                "original": true,
                "appId": appId
            });
            var originalLink_Mobile = new dynamicLink.ModelingDynamicLink({
                "type": "mobile",
                "original": true,
                "appId": appId
            });
            var dyLink = {
                "pc": [],
                "mobile": []
            };
            window._dynamic_link_get_load = dialog.loading();
            $.ajax({
                url: Com_Parameter.ContextPath + "sys/modeling/base/dynamicLink.do?method=listLinkByAppId&appId=" + appId,
                method: 'GET',
                async: false
            }).success(function (data) {
                var result = typeof(data) === 'object' ? data : JSON.parse(data);
                if (result.success) {
                    var array = result.array;
                    for (var i = 0; i < array.length; i++) {
                        var t = array[i].type;
                        var link = new dynamicLink.ModelingDynamicLink({
                            "type": t,
                            "appId": appId
                        });
                        link.setValue(array[i].key);
                        dyLink[t].push(link);
                        topic.channel("modelingDynamicLink").publish("dyLink.push", {
                            "type": t
                        });
                    }

                }else {

                }
                window._dynamic_link_get_load.hide();
            });
            window.addDyLink = function (type) {
                var link = new dynamicLink.ModelingDynamicLink({
                    "type": type,
                    "appId": appId
                });
                dyLink[type].push(link);
                topic.channel("modelingDynamicLink").publish("dyLink.push", {
                    "type": type
                });
            }


        });

</script>
