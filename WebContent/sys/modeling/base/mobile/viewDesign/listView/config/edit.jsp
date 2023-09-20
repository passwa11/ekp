<%@ page import="com.landray.kmss.sys.modeling.base.mobile.forms.ModelingAppMobileListViewForm" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
    ModelingAppMobileListViewForm modelingAppMobileListViewForm = (ModelingAppMobileListViewForm) request.getAttribute("modelingAppMobileListViewForm");
    if(modelingAppMobileListViewForm != null){
        String fdViewCfg = modelingAppMobileListViewForm.getFdViewCfg();
        if (StringUtil.isNotNull(fdViewCfg)) {
            //将json的value中的"替换为\"
            fdViewCfg = fdViewCfg.replaceAll("\\\\\"","\\\\\\\\\"");
            pageContext.setAttribute("fdViewCfg",fdViewCfg);
        }
    }
%>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}resource/style/default/doc/document.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/normalize.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/resources/css/swiper.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/resources/css/listView.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/view.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/query.css?s_cache=${LUI_Cache}" />
        <style>
            table.model-view-panel-table > tbody > tr > td.td_normal_title {
                text-align: left;
                padding-left: 20px;
                padding-right: 20px;
            }

            .model-view-panel-table .model-view-panel-table-td {
                padding-left: 20px !important;
                padding-right: 20px !important;
                padding-bottom: 15px;
            }

            .tb_simple .inputsgl {
                border: 1px solid #dfdfdf !important;
            }

            .model-edit-left .swiper-container {
                max-width: none;
            }

            .model-edit-right .swiper-container {
                max-width: 336px;
                border-top-left-radius: 4px;
            }

            div.model-edit-view-oper-head-title i.open, div.model-edit-view-oper-head-title i.close {
                top: 0px;
            }

            div.model-edit-view-oper-head-item div i {
                top: 0px;
                vertical-align: middle;
            }

            div.model-body-wrap .model-body-content-desc {
                padding-bottom: 0;
                margin-top: 14px;
                height: auto;
                line-height: normal;
            }

            div.model-body-wrap .model-body-content-desc i {
                margin-top: 2px;
            }

            .panel-tab-main .panel-tab-main-view {
                margin-top: 15px;
            }

            .model-mask-panel-table-select p {
                color: #999999;
                margin-left: 8px;
            }

            .model-mask-panel-table-select-val.active {
                color: #333333;
            }

            .panel-tab-content .panel-tab-main {
                margin-top: 1px;
            }

            div.value_input.input_radio input {
                width: auto;
            }

            .header-slide-item .model-tab-table-slide-tag-item p {
                padding: 0;
                margin: 0;
                padding-left: 10px;
                padding-right: 20px;
                box-sizing: border-box;
            }
            .mobile_list_view.view_flag_radio_no span {
			    margin-left: 1px;
			}
			.width45{
				width:auto !important;
			}
			.model-edit-view-oper-content {
				padding: 10px !important;
			}
			.model-edit-view-oper-content {
				overflow-x: hidden;
			}
            .model-body-content-phone-top .model-body-content-phone-sort .model-phone-sort-left{
                width: 152px;
            }
        </style>
        <script>
            Com_IncludeFile("doclist.js");
            Com_IncludeFile("view_common.js", Com_Parameter.ContextPath
                + 'sys/modeling/base/resources/js/', 'js', true);
            Com_IncludeFile("swiper2.7.6.min.js", Com_Parameter.ContextPath
                + 'sys/modeling/base/mobile/resources/js/', 'js', true);
        </script>
    </template:replace>
    <template:replace name="content">
        <div class="model-body-content" id="editContent_mobile">
            <!-- 这里放入你的组件 starts -->
            <div class="model-edit">
                <div class="model-edit-left">
                    <div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview_mobile"
                         style="display: none">
                        <div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
                        <div data-lui-type="lui/view/render!Template" style="display:none;">
                            <script type="text/config">
 						{
							src : '/sys/modeling/base/resources/js/preview/listview_mobile.html#'
						}

                            </script>
                        </div>
                    </div>
                </div>
                <div class="model-edit-right">
                    <div class="model-edit-right-wrap">
                        <div class="model-edit-view-title">
                            <p>${ lfn:message('sys-modeling-base:listview.view.configuration') }</p>
                            <div onclick="submitForm('update');"><bean:message key="button.save"/></div>
                        </div>
                        <div class="model-edit-view-content">
                            <div class="model-edit-view-content-wrap">
                                <html:form action="/sys/modeling/base/mobile/modelingAppMobileListView.do">
                                    <center>
                                        <div class="">
                                            <div class="panel-content">
                                                <table class="tb_simple model-view-panel-table" width="100%;" style="">
                                                    <tr lui-pam-hidden="${param.isPcAndMobile}">
                                                        <td class="td_normal_title">
                                                            ${ lfn:message('sys-modeling-base:listview.view.name') }
                                                        </td>
                                                    </tr>
                                                    <tr lui-pam-hidden="${param.isPcAndMobile}">
                                                        <td class="model-view-panel-table-td">
                                                            <!-- <xform:text property="fdName" style="width:100%;" required="true" validators="maxLength(200)" ></xform:text> -->
                                                            <xform:xtext property="fdName" subject="${ lfn:message('sys-modeling-base:listview.view.name') }"
                                                                         style="width:95%;border: 1px solid #DFE3E9;height:30px;"
                                                                         required="true"></xform:xtext>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <div data-lui-type="sys/modeling/base/mobile/resources/js/viewContainer!ViewContainer"
                                                     id="viewContainer" style="display:none;margin-top:10px">
                                                    <script type="text/config">
 												{
													storeData : $.parseJSON('${fdViewCfg}' || '{}'),
													headerClass : 'panel-tab-header',
													mainClass : 'panel-tab-main'
												}

                                                    </script>
                                                    <div class="panel-tab-content">
                                                        <div class="panel-tab-header"></div>
                                                        <div class="panel-tab-main"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </center>
                                    <html:hidden property="fdId"/>
                                    <html:hidden property="fdModelId"/>
                                    <html:hidden property="fdViewCfg"/>
                                </html:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            var _validation = $KMSSValidation();
            var listviewOption = {
                param: {
                    fdId: "${param.fdId}",
                    fdAppId: "${fdAppId}",
                    fdModelId: "${param.fdModelId }",
                    contextPath: Com_Parameter.ContextPath,
                    isInDialog: "${param.isInDialog}",
                    isPcAndMobile: '${param.isPcAndMobile}'
                },
                fdAuthEnabledHit:'<bean:message bundle="sys-modeling-base" key="modelingAppListview.fdAuthEnabled.hit"/>',
                isEnableFlow: {
                    isFlowBoolean: "${isFlowBoolean}"
                },
                <c:if test="${baseInfo!=null}">baseInfo:${baseInfo}</c:if>
            };

            function packageData(callBack) {
                if (!_validation.validate()) {
                    return
                }
                var keyData = LUI("viewContainer").getKeyData();
                //移除标题的校验
                $("#advice-_validate_fdSubject").remove();
                // 校验各个视图的筛选项和预定义字段是否重复，由于该校验是校验完毕直接提交，故不能放置到上面的校验循环里面
                if (!validateFieldRepeat(keyData, "update", callBack(document.modelingAppMobileListViewForm, "update"))) {
                    $("input[name='fdViewCfg']").val(JSON.stringify(keyData));
                }

            }

            function submitForm(method) {
                var keyData = LUI("viewContainer").getKeyData();
                //移除标题的校验
                $("#advice-_validate_fdSubject").remove();
                // 校验各个视图的筛选项和预定义字段是否重复，由于该校验是校验完毕直接提交，故不能放置到上面的校验循环里面
                 if (!validateFieldRepeat(keyData, method))
                {
                    $("input[name='fdViewCfg']").val(JSON.stringify(keyData));
                    Com_Submit(document.modelingAppMobileListViewForm, method);
                }
            }

            function getValidateHtml(subject, type) {
                var html = '<div class="validation-advice" id="advice-_validate_' + type + '" _reminder="true">'
                    + '<table class="validation-table"><tbody><tr>'
                    + '<td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td>'
                    + '<td class="validation-advice-msg"><span class="validation-advice-title">' + subject + '</span> ${ lfn:message("sys-modeling-base:kmReviewMain.notNull") }</td>'
                    + '</tr></tbody></table></div>';
                return html;
            }

            //返回列表页面
            function returnListPage(type) {
                var url = listviewOption.param.contextPath + 'sys/modeling/base/mobile/viewDesign/listView/config/index_body.jsp?fdModelId=' + listviewOption.param.fdModelId + '&method=edit&actionUrl=/sys/modeling/base/mobile/modelingAppMobileListView.do';
                var iframe = window.parent.document.getElementById("trigger_iframe");
                $(iframe).attr("src", url);
                //修改样式
                $(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top", "10px");
                $(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display", "block");
                return false;
            }

            // 移动 -1：上移       1：下移
            function moveTr(direct, dom, type) {
                var tb = $(dom).closest("table")[0];
                var $tr = $(dom).closest("tr");
                var curIndex = $tr.index();
                var lastIndex = tb.rows.length - 1;
                var targetIndex = curIndex;
                if (direct == 1) {
                    if (curIndex >= lastIndex) {
                        alert("${ lfn:message('sys-modeling-base:listview.is.the.end') }");
                        return;
                    }
                    $tr.next().after($tr);
                    targetIndex = curIndex + 1;
                } else {
                    if (curIndex < 1) {
                        alert("${ lfn:message('sys-modeling-base:listview.moved.to.top') }");
                        return;
                    }
                    $tr.prev().before($tr);
                    targetIndex = curIndex - 1;
                }
                if (type && curIndex != targetIndex) {
                    var luiId = $(tb).parents("[data-lui-cid]").eq(0).attr("data-lui-cid");
                    var kclass = LUI(luiId);
                    kclass.moveWgt(type, curIndex, targetIndex);
                }
            }

            //切换选中的位置
            function switchSelectPosition(obj, direct) {
                Com_EventStopPropagation();
                if (flag) {
                    $("[data-lui-position]").removeClass("active");
                    var position = $(obj).attr("data-lui-position");
                    $("[data-lui-position='" + position + "']").addClass("active");
                    if (direct == 'left') {//左边
                        if (position == "fdName") {
                            var viewId = $(obj).attr("view-id");
                            //右边切换
                            LUI("viewContainer").switchView(viewId);
                        } else if (position.indexOf("fdOrderBy") != -1) {
                            $("[data-lui-position='fdOrderBy']").addClass("active");
                            $("#fdOrderBy_more").hide();
                            $("div[data-lui-position='fdOrderBy_more']").removeClass("active");
                        } else if (position.indexOf("fdDisplay") != -1) {
                            $("[data-lui-position='fdDisplay']").addClass("active");
                        } else if (position.indexOf("fdCondition") != -1) {
                            $("[data-lui-position='fdCondition']").addClass("active");
                        }
                    } else {//右边
                        if (position.indexOf("fdOrderBy") != -1) {
                            $("[data-lui-position='fdOrderBy']").addClass("active");
                            $("#fdOrderBy_more").hide();
                            if ($("[data-lui-position='" + position + "']").parents("div[data-lui-position='fdOrderBy_more']").eq(0).length <= 0) {
                                $("div[data-lui-position='fdOrderBy_more']").removeClass("active");
                            }
                        } else if (position.indexOf("fdDisplay") != -1) {
                            $("[data-lui-position='fdDisplay']").addClass("active");
                        }
                    }
                    //进行滚轮处理
                    if (direct == 'left' && position) {
                        var panel = $("[data-lui-position='" + position + "']").parents(".model-edit-view-content").eq(0);
                        var target = $(".model-edit-right").find("[data-lui-position='" + position + "']").eq(0);
                        if (panel && target && target.offset() && panel.offset()) {
                            var scrollTop = target.offset().top - panel.offset().top + panel.scrollTop() - 50;
                            panel.scrollTop(scrollTop)
                        }
                    }
                } else {
                    flag = true;
                }
                return false;
            }

            //初始化
            var lastSelectPostionObj;
            var lastSelectPostionDirect;
            var lastSelectPosition;
            var mySwiper;
            var currentViewId, currentView;
            var flag = true;
            var isDown = false;
            Com_AddEventListener(window, "load", function () {
                //隐藏表格最后一行的向下移动按钮
                $(document).on('detaillist-init', function (e) {
                    var table = e.target;
                    $(table).find(">tbody>tr").last().find("div.down").css("display", "none");
                });
                
                //查询条件“内置条件”和“自定义条件”切换
            	$(document).on("click",".model-query-tab li", function () {
            	       $(this).siblings().removeClass("active");
            	       $(this).addClass("active");
            	       var index = $(this).index();
            	       $(this).closest(".model-query-wrap").find(".model-query-cont").each(function (i, ele) {
            	           if (i == index) {
            	               $(this).siblings().css('display', 'none');
            	               $(this).css('display', 'block');
            	           }
            	       })
            	});
             //查询规则的选中
	             var wheretype = "${modelingAppMobileListViewForm.fdMobileWhereType}";
	             if(wheretype == "1"){
	            	 $("input[name='fdMobileWhereType'][value='1']").attr("checked",true);
	             }else{
	            	 $("input[name='fdMobileWhereType'][value='0']").attr("checked",true);
	             }
            })

            //改变标志，避免拖动事件和点击事件冲突
            function changeFlag(oper) {
                if (oper == 'down') {
                    isDown = true;
                } else if (oper == 'move') {
                    if (isDown) {
                        flag = false;
                    }
                } else if (oper == "up") {
                    isDown = false;
                }
            }

            //创建新的视图
            function createNewView() {
                LUI("viewContainer").createChildView({}, true);
            }

            //切换穿透
            function changeViewFlag(obj, name, value, idName) {
                var curVal = $("[name='" + name + "']").val();
                if (curVal == value) {
                    return;
                }
                var radioObj = $(obj).parents(".view_flag_radio")[0];
                if (value == 0) {
                    $("select[name='" + idName + "']").hide();
                    $("select[name='" + idName + "']").val("");
                    $(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
                    $(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
                } else {
                    $("select[name='" + idName + "']").show();
                    $(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
                    $(radioObj).find(".view_flag_radio_yes i").addClass("view_flag_yes");
                }
                $("[name='" + name + "']").val(value);
            }
            function preValue(value){
            	var newValue = {};
            	var condition = [];
            	var conditionText = [];
            	for(var i = 0;i < value.length;i++){
            		if(value[i].type == "Double" || value[i].type == "BigDecimal"){
            			continue;
            		}else{
            			condition.push(value[i]);
            			conditionText.push(value[i].text);
            		}
            	}
            	newValue.condition = condition;
            	newValue.conditionText = conditionText;
            	return newValue;
            }

            seajs.use(['lui/topic', 'lui/jquery', "lui/dialog","sys/modeling/base/formlog/res/mark/listViewFMMark"],
                function (topic, $, dialog,listViewFMMark) {
                var frameHeight = $(parent.document).find(".modeling-pam-content-frame").eq(0).outerHeight(true);
                function onResizeFitWindow() {
                    var height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 25;
                    if (listviewOption.param.isInDialog) {
                        $('.model-body-content-desc:eq(0)').hide();
                        height = $(".lui_dialog_content", $(parent.parent.document)).height();
                    }
                    if (listviewOption.param.isPcAndMobile) {
                        //Pc+移动
                        $(".model-edit-right-wrap .model-edit-view-content").height(frameHeight - 80);
                        $(".model-edit-left-wrap .model-edit-view-content").height(frameHeight - 60);
                        //
                        $("[lui-pam-hidden='true']").hide();
                        $(".model-body-content-desc").hide();
                        $(".model-edit-view-title").hide();
                        $(".panel-tab-header").hide();
                        $(".panel-tab-main").css({"border": 0});
                        $(".model-edit-left").css({"padding-top": 0});
                        $(".model-edit-left").addClass("model-edit-left-over");
                        $(".model-edit-right").css({
                            "padding-top": 0,
                            "border-top": "1px solid #DDDDDD",
                            "border-bottom": "1px solid #DDDDDD"
                        })
                    } else {
                        $("body", parent.document).find('#trigger_iframe').height(height);
                        $(".model-edit-right-wrap .model-edit-view-content").height(height - 80);
                        $(".model-edit-left .model-body-content-wrap").height(height - 60);
                        $("body", parent.document).css("overflow", "hidden");
                    }
                }

                $(document).resize(function () {
                    onResizeFitWindow();
                });

                //预览加载完毕事件
                topic.subscribe('preview_load_finish', function (ctx) {
                    onResizeFitWindow();

                    mySwiper = new Swiper('.swiper-container.left', {
                        slidesPerView: 3,
                        calculateHeight: true
                    });

                    $(".model-edit-left .model-body-content-phone-wrap").on('click', function () {
                        $("[data-lui-position]").removeClass("active");
                        return false;
                    })
                });

                //切换视图完成事件
                topic.subscribe('switchView_finish', function (data) {
                    //刷新预览
                    currentViewId = data.wgtId;
                    currentView = data.currentView;
                    try {
                        var value = LUI("viewContainer").getKeyData();
                        value["currentViewId"] = currentViewId;
                        LUI("view_preview_mobile").setSourceData(value, 'data');
                        LUI("view_preview_mobile").reRender();
                    } catch (e) {
                    }
                });

                //视图加载完成事件
                topic.subscribe('view_load_finish', function () {
                    //初始化数据
                    try {
                        var value = LUI("viewContainer").getKeyData();
                        value["currentViewId"] = currentViewId;
                        //#123026：移动端筛选项屏蔽数字控件，因为现在移动组件还不支持数字类型
                        var viewsArr = value.views;
                        var newValObj = {};
                        for(var i = 0; i < viewsArr.length;i++){
                        	var viewObj = viewsArr[i];
                        	if(viewObj.id == currentViewId){
                        		newValObj = preValue(viewObj.fdCondition);
                        	}
                        }
                        var conditionStr = newValObj.conditionText.join(";");
                        $("[name *= 'fdConditionText']").val(conditionStr);
                        LUI("view_preview_mobile").setSourceData(value, 'data');
                        LUI("view_preview_mobile").reRender();
                    } catch (e) {
                    }
                });

                //添加数据完成事件
                topic.subscribe('data-create-finish', function (data) {
                    data.table.find("> tbody > tr").last().find(".model-edit-view-oper").click();
                });

                //选择框切换数据后事件
                topic.channel("modeling").subscribe("field.change", function (data) {
                    var selectDom = data.dom;
                    //更新标题
                    var $parent = $(selectDom).parents("div.select_union").eq(0);
                    var text = "";
                    var fieldId = $parent.find("select").eq(0).val();
                    var fieldText = $parent.find("select").eq(0).find("option[value='" + fieldId + "']").text();
                    text = fieldText;
                    fieldId = $parent.find("select").eq(1).val();
                    fieldText = $parent.find("select").eq(1).find("option[value='" + fieldId + "']").text();
                    if (fieldText) {
                        text += "|" + fieldText;
                    }
                    $(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text(text);
                    //刷新预览
                    topic.publish("preview.refresh");
                })

                // 类型切换事件，更新头部标题
                topic.channel("modeling").subscribe("whereType.change", function (data) {
                    var selectDom = data.dom;
                    var text = "";
                    // 0（自定义查询项）|1（内置查询项）
                    if (data.value === "0") {
                        text = data.wgt.fieldWgt.getFieldText();
                    } else if (data.value === "1") {
                        text = $(selectDom).closest(".list-content").find("[data-bind-type-value='1']").find("select option:selected").text();
                    }
                    $(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text(text);
                })

                //切换事件
                topic.subscribe('switchSelectPosition', function (data) {
                    switchSelectPosition(data.dom, 'right');
                });

                //预览更新事件
                topic.subscribe('preview.refresh', function () {
                    //刷新预览
                    try {
                        var value = LUI("viewContainer").getKeyData();
                        value["currentViewId"] = currentViewId;
                        LUI("view_preview_mobile").setSourceData(value, 'data');
                        LUI("view_preview_mobile").reRender();
                        //恢复选择的位置
                        if (lastSelectPostionObj && lastSelectPostionDirect) {
                            switchSelectPosition(lastSelectPostionObj, lastSelectPostionDirect, lastSelectPosition);
                        }
                    } catch (e) {
                    }
                    ;
                })

                //删除行
                window.delTr = function (dom, type) {
                    var tb = $(dom).closest("table")[0];
                    var $tr = $(dom).closest("tr");
                    var curIndex = $tr.index();
                    var luiId = $(tb).parents("[data-lui-cid]").eq(0).attr("data-lui-cid");
                    var kclass = LUI(luiId);
                    if (type == 'orderby') {
                        var orderCollection = kclass.orderCollection;
                        var wgt = orderCollection[curIndex];
                        topic.channel("modeling").publish("order.delete", {"wgt": wgt});
                        topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                        wgt.destroy();
                        return;
                    } else if (type == 'where') {
                    	var name = $(dom).closest("table").attr("name");
                    	var wgt = null;
                    	if(name == "sys_query"){
                    		 var syswhereCollection = kclass.syswhereCollection;
                    		 wgt = syswhereCollection[curIndex];
                    		 topic.channel("modeling").publish("where.delete", {"wgt": wgt,"name":name});
                    	}else if(name == "custom_query"){
                    		 var whereCollection = kclass.whereCollection;
                             wgt = whereCollection[curIndex];
                             topic.channel("modeling").publish("where.delete", {"wgt": wgt,"name":name});
                    	}
                        topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                        wgt.destroy();
                        return;
                    }
                    $tr.remove();
                }

                window.triggleSelectdatetime = function (event, dom, type, name) {
                    var input = $(dom).find("input[name='" + name + "']");
                    if (type == "DateTime") {
                        selectDateTime(event, input);
                    } else if (type == "Date") {
                        selectDate(event, input);
                    } else {
                        selectTime(event, input);
                    }
                }

                // 校验各个视图的筛选项和预定义字段是否重复
                window.validateFieldRepeat = function (keyData, method, callBack) {
                    var isRepeat = false;
                    var revisedKeyData = {};
                    $.extend(true, revisedKeyData, keyData);
                    var views = revisedKeyData.views || [];
                    for (var i = 0; i < views.length; i++) {
                        var view = views[i];
                        if (validateConditionAndWhereBlockFieldRepeat(view)) {
                            isRepeat = true;
                        }
                    }
                    if (isRepeat) {
                        dialog.confirm("${ lfn:message('sys-modeling-base:listview.filter.item.used.query') }", function (value) {
                            if (value === true) {
                                $("input[name='fdViewCfg']").val(JSON.stringify(revisedKeyData));
                                //callBack(form, method);
                                Com_Submit(document.modelingAppMobileListViewForm, method);
                            }
                        });
                    }
                    return isRepeat;
                }

                // 校验筛选项和预定义字段是否重复
                function validateConditionAndWhereBlockFieldRepeat(view) {
                    var isRepeat = false;
                    var conditions = view.fdCondition || [];
                    var conditionTexts = view.fdConditionText.split(";");
                    var whereBlocks = view.fdWhereBlock || [];
                    for (var i = conditions.length - 1; i >= 0; i--) {
                        var condition = conditions[i];
                        for (var j = 0; j < whereBlocks.length; j++) {
                            var whereBlock = whereBlocks[j];
                            var field = whereBlock.field;
                            if (field.indexOf("|") > -1) {
                                field = field.substring(0, field.indexOf("|"));
                            }
                            // 如果存在相同，则删除筛选项的
                            if (field === condition.field) {
                                isRepeat = true;
                                // 理论上conditions的长度跟conditionTexts的相等
                                conditions.splice(i, 1);
                                conditionTexts.splice(i, 1);
                            }
                        }
                    }
                    view.fdConditionText = conditionTexts.join(";");
                    return isRepeat;
                }
                    //表单映射
                    var listViewFMMark_mobile = new listViewFMMark.ListViewFMMark({fdId:"${param.fdId}",isMobile:true});
                    listViewFMMark_mobile.startup();
            });
        </script>
    </template:replace>
</template:include>