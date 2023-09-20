<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%
    pageContext.setAttribute("_isJGEnabled", new Boolean(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()));
    pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
    //加载项
    pageContext.setAttribute("_isWpsAddonsEnable", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
%>
<c:set var="mainDocForm" value="${requestScope[param.formName]}" />
<template:include ref="default.edit">
    <template:replace name="content">
        <style>
            .lui_form_path_frame{
                display: none !important;
            }
            .com_qrcode {
                display: none !important;
            }
            .tempTB {
                width: 100% !important;
                max-width:100% !important;
            }
        </style>
        <form name="sysForm" id="sysForm" method="post">
            <c:if test="${mainDocForm.fdUseForm == 'false'}">
                <c:choose>
                    <c:when test="${mainDocForm.fdUseWord == 'true'}">
                        <table class="tb_normal" style="border-top:0;" width="100%">
                            <tr>
                                <td colspan="2">
                                    <div id="wordEdit">
                                        <div id="wordEditWrapper"></div>
                                        <div id="wordEditFloat" style="position: absolute;width:0px;height:0px;filter:alpha(opacity=0);opacity:0;">
                                            <div id="reviewButtonDiv" style="text-align:right;"></div>
                                            <%
                                                pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
                                                pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
                                            %>
                                            <c:choose>
                                                <c:when test="${pageScope._isWpsCloudEnable == 'true'}">
                                                    <c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                                        <c:param name="fdKey" value="mainContent" />
                                                        <c:param name="load" value="true" />
                                                        <c:param name="bindSubmit" value="false"/>
                                                        <c:param name="fdModelId" value="${mainDocForm.fdId}" />
                                                        <c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
                                                        <c:param name="formBeanName" value="${param.formName}" />
                                                        <c:param name="fdTemplateModelId" value="${mainDocForm.fdTemplateId}" />
                                                        <c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
                                                        <c:param name="fdTemplateKey" value="mainContent" />
                                                        <c:param name="fdTempKey" value="${mainDocForm.fdTemplateId}" />
                                                    </c:import>
                                                </c:when>
                                                <c:when test="${pageScope._isWpsWebOfficeEnable == 'true'}">
                                                    <c:import url="/sys/attachment/sys_att_main/wps/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                                        <c:param name="fdKey" value="mainContent" />
                                                        <c:param name="load" value="true" />
                                                        <c:param name="bindSubmit" value="false"/>
                                                        <c:param name="fdModelId" value="${mainDocForm.fdId}" />
                                                        <c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
                                                        <c:param name="formBeanName" value="${param.formName}" />
                                                        <c:param name="fdTemplateModelId" value="${mainDocForm.fdTemplateId}" />
                                                        <c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
                                                        <c:param name="fdTemplateKey" value="mainContent" />
                                                        <c:param name="fdTempKey" value="${mainDocForm.fdTemplateId}" />
                                                    </c:import>
                                                </c:when>

                                                <c:when test="${pageScope._isWpsAddonsEnable == 'true'}">
                                                   <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
																<c:param name="fdKey" value="mainContent" />
																<c:param name="fdMulti" value="false" />
																<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
																<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
																<c:param name="formBeanName" value="kmReviewMainForm" />
																<c:param name="fdTemplateModelId" value="${kmReviewMainForm.fdTemplateId}" />
																<c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
																<c:param name="fdTemplateKey" value="mainContent" />
																<c:param name="templateBeanName" value="kmReviewTemplateForm" />
																<c:param name="showDelete" value="false" />
																<c:param name="wpsExtAppModel" value="kmReviewMain" />
																<c:param name="canRead" value="false" />
																<c:param name="canEdit" value="true" />
																<c:param name="canPrint" value="false" />
																<c:param name="addToPreview" value="false" />
																<c:param  name="hideTips"  value="true"/>
																<c:param  name="hideReplace"  value="true"/>
																<c:param  name="canChangeName"  value="true"/>
																<c:param  name="filenameWidth"  value="250"/>
															</c:import>
                                                </c:when>

                                                <c:otherwise>
                                                    <c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                                        <c:param name="fdKey" value="mainContent" />
                                                        <c:param name="fdAttType" value="office" />
                                                        <c:param name="bindSubmit" value="false"/>
                                                        <c:param name="fdModelId" value="${mainDocForm.fdId}" />
                                                        <c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
                                                        <c:param name="fdTemplateModelId" value="${mainDocForm.fdTemplateId}" />
                                                        <c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
                                                        <c:param name="fdTemplateKey" value="editonline_krt" />
                                                        <c:param name="templateBeanName" value="kmReviewTemplateForm" />
                                                        <c:param name="buttonDiv" value="reviewButtonDiv" />
                                                        <c:param name="showDefault" value="true"/>
                                                        <c:param name="load" value="false" />
                                                        <c:param name="isToImg" value="false"/>
                                                        <c:param  name="attHeight" value="580"/>
                                                    </c:import>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <table class="tb_normal" width=100%>
                            <tr>
                                <td colspan="2">
                                    <kmss:editor property="docContent" width="95%"/>
                                </td>
                            </tr>
                            <!-- 相关附件 -->
                            <tr KMSS_RowType="documentNews">
                                <td class="td_normal_title" width=15%>
                                    <bean:message bundle="km-review" key="kmReviewMain.attachment" />
                                </td>
                                <td>
                                    <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                        <c:param name="fdAttType" value="byte" />
                                        <c:param name="fdMulti" value="true" />
                                        <c:param name="fdImgHtmlProperty" />
                                        <c:param name="fdKey" value="fdAttachment" />
                                        <c:param name="formBeanName" value="${param.formName}" />
                                        <c:param name="uploadAfterSelect" value="true" />
                                    </c:import>
                                </td>
                            </tr>
                        </table>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${mainDocForm.fdUseForm == 'true' || empty mainDocForm.fdUseForm}">
                <%-- 表单 --%>
                <div id="kmReviewXform">
                    <c:import url="/sys/xform/include/sysForm_edit.jsp"
                              charEncoding="UTF-8">
                        <c:param name="formName" value="${param.formName}" />
                        <c:param name="fdKey" value="reviewMainDoc" />
                        <c:param name="messageKey" value="km-review:kmReviewDocumentLableName.reviewContent" />
                        <c:param name="useTab" value="false" />
                    </c:import>
                    <br>
                </div>
            </c:if>
        </form>
        <script language="JavaScript">
            var isDebug = 'true' === '${param.isDebug}';
            var _valdate = $KMSSValidation(document.forms['${param.formName}']);

            //表单提交前通知当前页面
            window.addEventListener("message", receiveMessage, false);
            function receiveMessage(event)
            {
                if(isDebug)
                    console.log("iframe ${mainDocForm.method} receiveMessage:" + event.data.event);
                if(event.data.event === 'beforeFormSubmit') {
                    let isDraft = false;
                    if(event.data.isDraft === true) {
                        isDraft = true;
                    }
                    //校验是否通过
                    let validateResult = validate(isDraft);
                    //向父窗口返回表单的内容
                    let sysForm = getSysForm();
                    parent.postMessage({"event": "sysForm", "validate": validateResult, "form": sysForm}, "*");
                }
            }

            //获取表单的内容
            function getSysForm() {
                var formSerial = {};
                if(isDebug)
                    console.log($("#sysForm"));
                $($("#sysForm").serializeArray()).each(function() {
                    formSerial[this.name] = this.value;
                });
                return formSerial;
            }

            function validate(isDraft) {
                if(isDraft) {
                    //存为草稿时无需校验
                    _valdate.removeElements($('#kmReviewXform')[0], 'required');
                    _changeAttValidate(true);
                } else {
                    _valdate.resetElementsValidate($('#kmReviewXform')[0]);
                    _changeAttValidate(false);
                }
                return _valdate.validate();
            }

            function _changeAttValidate(remove) {
                if (window.Attachment_ObjectInfo) {
                    for (var tmpKey in window.Attachment_ObjectInfo) {
                        if (window.Attachment_ObjectInfo[tmpKey]) {
                            if (remove) {
                                window.Attachment_ObjectInfo[tmpKey]._reqired = window.Attachment_ObjectInfo[tmpKey].required;
                                window.Attachment_ObjectInfo[tmpKey].required = false;
                            } else {
                                if (window.Attachment_ObjectInfo[tmpKey]._reqired != null) {
                                    window.Attachment_ObjectInfo[tmpKey].required = window.Attachment_ObjectInfo[tmpKey]._reqired;
                                }
                            }
                        }
                    }
                }
            }

            // ADD BY WUZB 20171102
            function checkEditType(useForm, useWord) {
                if ('true' == useWord && 'true' != useForm) {
                    var _wordEdit = $('#wordEdit');
                    var wordFloat = $("#wordEditFloat");
                    var reviewButtonDiv = $("#reviewButtonDiv");
                    _wordEdit.css({'display': "block", 'width': "100%", 'height': "600px"});
                    var xw = $("#wordEditWrapper").width();
                    wordFloat.css({
                        'width': xw + 'px',
                        'height': '600px',
                        'filter': 'alpha(opacity=100)',
                        'opacity': '1'
                    });
                    reviewButtonDiv.css({
                        'width': xw + 'px',
                        'height': '25px',
                        'filter': 'alpha(opacity=100)',
                        'opacity': '1'
                    });

                    if ("${pageScope._isWpsWebOfficeEnable}" == "true") {
                        wps_mainContent.load();
                    } else if ("${pageScope._isWpsAddonsEnable}" == "true") {

                       


                    } else {
                        var obj_JG = document.getElementById("JGWebOffice_mainContent");
                        if ("${pageScope._isJGEnabled}" == "true" && obj_JG) {

                            setTimeout(function () {
                                if (Attachment_ObjectInfo['mainContent']) {
                                    jg_attachmentObject_mainContent.load();
                                    jg_attachmentObject_mainContent.show();
                                    jg_attachmentObject_mainContent.ocxObj.Active(true);
                                }
                            }, 1500);

                            seajs.use(['lui/topic'], function (topic) {
                                topic.subscribe("Sidebar", function (data) {
                                    var xw = $("#wordEditWrapper").width();
                                    wordFloat.css({
                                        'width': xw + 'px',
                                        'height': '600px',
                                        'filter': 'alpha(opacity=100)',
                                        'opacity': '1',
                                        'overflow': 'hidden'
                                    });
                                    reviewButtonDiv.css({
                                        'width': xw + 'px',
                                        'height': '25px',
                                        'filter': 'alpha(opacity=100)',
                                        'opacity': '1',
                                        'overflow': 'hidden'
                                    });
                                });
                            });

                            $("#JGWebOffice_mainContent").height("600px");

                        }

                    }

                }
            }

            // ADD BY WUZB 20171102
            LUI.ready(function () {
                checkEditType('${mainDocForm.fdUseForm}', '${mainDocForm.fdUseWord}');
            });

            // ADD BY WUZB 20171102
            Com_Parameter.event["submit"].push(function () {
                var useWord = document.getElementsByName('fdUseWord')[0];
                if ("true" == useWord.value) {

                    if ("${pageScope._isWpsWebOfficeEnable}" != "true" && "${pageScope._isJGEnabled}" == "true") {

                        var obj_JG = document.getElementById("JGWebOffice_mainContent");
                        if (obj_JG && Attachment_ObjectInfo['mainContent'] && jg_attachmentObject_mainContent.hasLoad) {
                            jg_attachmentObject_mainContent.ocxObj.Active(true);
                            jg_attachmentObject_mainContent._submit();
                            return true;
                        }
                    } else {
                        if ("${pageScope._isWpsWebOfficeEnable}" == "true") {
                            wps_mainContent.submit();
                        }
                    }
                } else {
                    if ("${pageScope._isWpsWebOfficeEnable}" != "true" && "${pageScope._isJGEnabled}" == "true") {
                        if ("${mainDocForm.method_GET}" == "add") {
                            if (Attachment_ObjectInfo['mainContent']) {
                                jg_attachmentObject_mainContent.unLoad();
                            }
                        }
                    }
                }
                return true;
            });
        </script>
    </template:replace>
</template:include>