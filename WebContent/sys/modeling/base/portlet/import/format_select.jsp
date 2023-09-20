<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/portlet.css?s_cache=${LUI_Cache}"/>
    </template:replace>
    <template:replace name="content">
    	<div style="overflow: scroll;padding-bottom:63px;height: 100%;box-sizing: border-box;">
        <table class="tb_simple formatSelectTab" width="98%" style="margin-top: 12px">
            <c:if test="${param.fdDevice == 'pc'}">
            <tr >
                <td >
                	<div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                		<div style="display: inline-block;" class="format_select_content_left">
                    		<label><input type="radio" name="format" value="sys.ui.classic" style="display: none" /><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:portal.simple.list.presentation')}</label>
	                	</div>
	                	<div style="display: inline-block;"  class="format_select_content_right">
	                		<div class="formatReviewBox formatReviewBox_simple"></div>
	                	</div>
                	</div>
                </td>
            </tr>
            <tr >
                <td>
                	<div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                		<div style="display: inline-block;" class="format_select_content_left">
                			<label><input type="radio" name="format" value="sys.ui.image.desc" style="display: none"/><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:modelingPortletCfg.format.sys.ui.image.desc')}</label>
	                	</div>
	                	<div style="display: inline-block;"  class="format_select_content_right">
	                		<div class="formatReviewBox formatReviewBox_imgdesc"></div>
	                	</div>
                	</div>
                </td>
            </tr>
            <tr >
                <td>
                	<div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                		<div style="display: inline-block;" class="format_select_content_left">
                    		<label><input type="radio" name="format" value="sys.ui.slide" style="display: none"/><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:modelingPortletCfg.format.sys.ui.slide')}</label>
	                	</div>
	                	<div style="display: inline-block;"  class="format_select_content_right">
	                		<div class="formatReviewBox formatReviewBox_side"></div>
	                	</div>
                	</div>
                </td>
            </tr>
            <tr >
                <td>
                	<div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                		<div style="display: inline-block;" class="format_select_content_left">
                    		<label><input type="radio" name="format" value="sys.ui.listtable" style="display: none"/><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:modelingPortletCfg.format.sys.ui.listtable')}</label>
	                	</div>
	                	<div style="display: inline-block;"  class="format_select_content_right">
	                		<div class="formatReviewBox formatReviewBox_listtable"></div>
	                	</div>
                	</div>
                </td>
            </tr>
            <tr >
                <td>
                    <div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                        <div style="display: inline-block;" class="format_select_content_left">
                            <label><input type="radio" name="format" value="sys.ui.card" style="display: none"/><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:modelingPortletCfg.format.sys.ui.card')}</label>
                        </div>
                        <div style="display: inline-block;"  class="format_select_content_right">
                            <div class="formatReviewBox formatReviewBox_timelineList"></div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr >
                <td>
                    <div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                        <div style="display: inline-block;" class="format_select_content_left">
                            <label><input type="radio" name="format" value="sys.ui.image" style="display: none"/><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:modelingPortletCfg.format.sys.ui.image')}</label>
                        </div>
                        <div style="display: inline-block;"  class="format_select_content_right">
                            <div class="formatReviewBox formatReviewBox_imageGongge"></div>
                        </div>
                    </div>
                </td>
            </tr>
            </c:if>
            <c:if test="${param.fdDevice == 'mobile'}">
                <tr >
                    <td>
                        <div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                            <div style="display: inline-block;" class="format_select_content_left">
                                <label><input type="radio" name="format" value="modeling.mobile.textImg" style="display: none"/><i class="format_flag_no"></i>
                                        ${lfn:message('sys-modeling-base:modelingPortletCfg.format.sys.ui.image.desc')}</label>
                            </div>
                            <div style="display: inline-block;"  class="format_select_content_right">
                                <div class="formatReviewBox formatReviewBox_mobile_textImg"></div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr >
                    <td>
                        <div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                            <div style="display: inline-block;" class="format_select_content_left">
                                <label><input type="radio" name="format" value="modeling.mobile.simple" style="display: none"/><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:modelingPortletCfg.format.modeling.mobile.simple')}</label>
                            </div>
                            <div style="display: inline-block;"  class="format_select_content_right">
                                <div class="formatReviewBox formatReviewBox_mobile_simple"></div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr >
                    <td>
                        <div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                            <div style="display: inline-block;" class="format_select_content_left">
                                <label><input type="radio" name="format" value="modeling.mobile.picSlide" style="display: none"/><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:modelingPortletCfg.format.modeling.mobile.picSlide')}</label>
                            </div>
                            <div style="display: inline-block;"  class="format_select_content_right">
                                <div class="formatReviewBox formatReviewBox_mobile_picSlide"></div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr >
                    <td>
                        <div class="format_select_content format_flag_radio" onclick="changeFlag(this)">
                            <div style="display: inline-block;" class="format_select_content_left">
                                <label><input type="radio" name="format" value="modeling.mobile.cardList" style="display: none"/><i class="format_flag_no"></i>${lfn:message('sys-modeling-base:modelingPortletCfg.format.modeling.mobile.cardList')}</label>
                            </div>
                            <div style="display: inline-block;"  class="format_select_content_right">
                                <div class="formatReviewBox formatReviewBox_mobile_cardList"></div>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:if>
        </table>
        </div>
        <div class="lui_custom_list_boxs">
        	<ui:button styleClass="btn_submit" text="${lfn:message('button.select')}" onclick="doSubmit();"/> 
            <ui:button styleClass="btn_cancel" text="${lfn:message('button.cancel') }" onclick="cancel();"/>
            <%--取消--%>
        </div>

        <script type="text/javascript">
            var selectedVal=null;
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/str'], function ($, dialog, topic, str) {
                function onload(){
                    var checked = '${param.checked}';
                    if (checked === "undefined"){
                        checked = "sys.ui.classic";
                    }
                    $.each($("[name='format']"),function(i,e){
                        var $e = $(e);
                        if(checked === $e.val()){
                            $e.attr("checked","checked")
                            $e.parents(".format_flag_radio").addClass("active");
                            $e.parents(".format_flag_radio").find("i").addClass("format_flag_yes");
                        }
                    });
                    selectedVal = checked;
                }
                onload();
                window.doSubmit = function () {
                    $dialog.hide(selectedVal);
                };

                window.cancel = function () {
                    $dialog.hide(null);
                };
                
            });
            function changeFlag(obj){
                $(".format_flag_radio").removeClass("active");
            	$(".format_flag_radio i").removeClass("format_flag_yes");
            	$(obj).find("i").addClass("format_flag_yes");
                $(obj).addClass("active");
            	$(obj).find("[name='format']").attr("checked",true);
                selectedVal = $(obj).find("[name='format']").val()
            }
        </script>
    </template:replace>
</template:include>