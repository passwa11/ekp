<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.landray.kmss.util.ResourceUtil,java.io.File"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String orgType = "ORG_TYPE_POSTORPERSON";
	if("config".equals(request.getParameter("type"))) {
		orgType += "|ORG_TYPE_DEPT";
	}
	request.setAttribute("orgType", orgType);
%>
<template:include ref="default.edit" sidebar="auto">
    <template:replace name="head">
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/handover/resource/css/handover.css?s_cache=${LUI_Cache}" />
         <link rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/toolbar.css?s_cache=${LUI_Cache}" />
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/handover/sys_handover_config/js/jsTree/themes/default/style.min.css?s_cache=${LUI_Cache}" />
        <script src="${LUI_ContextPath}/sys/handover/sys_handover_config/js/jsTree/jstree.js?s_cache=${LUI_Cache}"></script>
        <script type="text/javascript" src="<c:url value="/sys/admin/resource/js/jquery.corner.js"/>?s_cache=${LUI_Cache}"></script>
		<style>
			.jstree-anchor {
				display: inline;
				white-space: break-spaces;
			}
		</style>
	</template:replace>
	<%-- 标题 --%>
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-handover:sysHandoverConfigMain.create') } - ${ lfn:message('sys-handover:module.sys.handover') }"></c:out>
	</template:replace>
	<%-- 路径 --%>
	<template:replace name="path">
	    <ui:menu layout="sys.ui.menu.nav">
		    <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self">
				</ui:menu-item>
				<ui:menu-item text="${lfn:message('sys-handover:module.sys.handover')}" href="/sys/profile/index.jsp#org/handover" target="_self">
			   </ui:menu-item> 
		</ui:menu>
	</template:replace>	
	<%-- 内容 --%>
	<template:replace name="content"> 
	   <!-- 主体开始 -->
        <div id="lui_handover_w main_body" class="lui_handover_w main_body">
            <div class="lui_handover_header">
                <%-- 标题 --%>
                <span>
               		<c:if test="${'doc' eq param.type}">
               			<kmss:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType" /> - 
               			<kmss:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.doc" />
               		</c:if>
               		<c:if test="${'config' eq param.type}">
               			<kmss:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType" /> - 
               			<kmss:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.config" />
               		</c:if>
               		<c:if test="${'item' eq param.type}">
               			<kmss:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType" /> - 
               			<kmss:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.item" />
               		</c:if>
                </span>
                <%-- 重置 --%>
                <ui:button styleClass="btn_reset" text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.reset') }" onclick="resetOperation();"/>
            </div>
            <div class="lui_handover_content">
                <%-- 查询条件 --%>
                <form name="sysHandoverConfigMainForm">
                <input type="hidden" name="fdId" value="${sysHandoverConfigMainForm.fdId}">
                <table class="tb_simple lui_handover_headTb lui_sheet_c_table">
                    <tr>
                        <%-- 交接人 --%>
                        <td width="15%" style="text-align: right;"> <span>${lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }</span></td>
						<td width="35%" id="from_edit"><xform:address propertyId="fdFromId"
							propertyName="fdFromName" style="width:50%"
							onValueChange="changeFromName()"
							subject="${lfn:message('sys-handover:sysHandoverConfigMain.fdFromName')}"
							showStatus="edit" orgType="${orgType}|ORG_FLAG_AVAILABLEYES" textarea="false"></xform:address>
							<span class="txtstrong">*</span>
							<span><a href="javascript:selectInvalid();">${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }</a></span>
						</td>
						<td width="35%" id="from_view" style="display: none">
						    <input style="width:65%;height:23px" type="text" name="fdFromName" disabled/>
						</td>
						<%-- 接收人 --%>
						<td width="15%" style="text-align: right;">
                            <span>${lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }</span>
                        </td>
                        <c:if test="${'item' ne param.type}">
                        <td width="35%">
							<xform:address propertyId="fdToId" propertyName="fdToName" style="width:65%"
								subject="${lfn:message('sys-handover:sysHandoverConfigMain.fdToName')}"
								validators="handoverNameSame" 
								showStatus="edit" orgType="${orgType}" textarea="false"></xform:address>
                        </td>
                        </c:if>
                        <c:if test="${'item' eq param.type}">
                        <td width="35%" id="to_edit">
							<xform:address propertyId="fdToId" propertyName="fdToName" style="width:50%"
								required="true" onValueChange="changeToName()" validators="handoverNameSame"
								subject="${lfn:message('sys-handover:sysHandoverConfigMain.fdToName')}"
								showStatus="edit" orgType="ORG_TYPE_POSTORPERSON|ORG_TYPE_DEPT" textarea="false"></xform:address>
                        </td>
                        <td width="35%" id="to_view" style="display: none">
						    <input style="width:65%;height:23px" type="text" name="fdToName" disabled/>
						</td>
						</c:if>
                    </tr>
                    <c:if test="${'doc' eq param.type}">
                    <tr>
                    	<td width="15%" style="text-align: right;">${lfn:message('sys-handover:sysHandoverConfigMain.execType')}</td>
                    	<td colspan="3">
                    		<label><input type="radio" name="execType" value="0" checked="checked">${lfn:message('sys-handover:sysHandoverConfigMain.execType.quartz')}</label>
                    		<label><input type="radio" name="execType" value="1">${lfn:message('sys-handover:sysHandoverConfigMain.execType.now')}</label>
                    		<br><span id="execType_1" style="display: none;color: red;">${lfn:message('sys-handover:sysHandoverConfigMain.execType.now.desc')}</span>
                    	</td>
                    </tr>
                    </c:if>
                    <c:if test="${'item' eq param.type}">
                    <tr>
                   		<td width="15%" style="text-align: right;">${lfn:message('sys-handover:sysHandoverConfigMain.execType')}</td>
                    	<td>
                    		<label><input type="radio" name="execType" value="0" checked="checked">${lfn:message('sys-handover:sysHandoverConfigMain.execType.quartz')}</label>
                    		<label><input type="radio" name="execType" value="1">${lfn:message('sys-handover:sysHandoverConfigMain.execType.now')}</label>
                    		<br><span id="execType_1" style="display: none;color: red;">${lfn:message('sys-handover:sysHandoverConfigMain.execType.now.desc')}</span>
                    	</td>
                    	<td width="15%" style="text-align: right;">${lfn:message('sys-handover:sysHandoverConfigMain.execMode')}</td>
                    	<td>
                    		<label><input type="radio" name="execMode" value="0" checked="checked">${lfn:message('sys-handover:sysHandoverConfigMain.execMode.replace')}</label>
                    		<label><input type="radio" name="execMode" value="1">${lfn:message('sys-handover:sysHandoverConfigMain.execMode.append')}</label>
                    		<br><span id="execMode_1" style="display: none;color: red;">${lfn:message('sys-handover:sysHandoverConfigMain.execMode.desc')}</span>
                    	</td>
                    </tr>
                    </c:if>
                </table>
                </form>
                <%-- 查询部分--%>
                <div class="lui_handover_search" id="searchDiv">
                	<%-- 交接内容--查询全选 --%>
                    <h3 class="title"> ${ lfn:message('sys-handover:sysHandoverConfigMain.content') } 
                    </h3>
			<div id="div_main" class="div_main">
			<table width="100%" class="tb_normal" cellspacing="1">
			
				<tr>
					<td height="15px" class="rd_title">
						<label>
							<input type="radio" name="check" value="1" onclick="chooesType(this.value);" checked/><bean:message key="sysAdminDbchecker.fdCheckType.1" bundle="sys-admin" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="15px" class="rd_title">
						<label>
							<input type="radio" name="check" value="2" onclick="chooesType(this.value);"/><bean:message key="sysAdminDbchecker.fdCheckType.2" bundle="sys-admin" />
						</label>
					</td>
				</tr>

				<tr id="advance" style="display: none">
					<td align="center" style="border: 0px;">
						<div style="border-bottom: 1px dashed; height: 25px;">
							<div style="float: left; margin-left: 5px; font-weight: bold;"><bean:message key="sysAdminDbchecker.byAppModel" bundle="sys-admin" /></div>
								<div style="float: right; margin-right: 5px;">
									<label>
										<input type="checkbox" name="_searchSelectAll" checked="checked" id="_searchSelectAll" onclick="searchCheckAll();"/><bean:message key="sysAdminDbchecker.selectAll" bundle="sys-admin" />
							  		</label>
						  	</div>
					  	</div>
						 <div class="lui_handover_search_c">
                        <ul class="clrfix">
                                 <%int n = 0;%>
                          <table width="100%">
                           <tr>
                             <c:forEach items="${moduleMapList}" var="module">   
							      <c:forEach items="${module}" var="moduleMap">   
										 <td width="20%">
										     <li><span class="item item_unck">
											      <input type="checkbox" name="searchModuleCheckBox" id="${moduleMap.key}_searchCheck" value="${moduleMap.key}" onclick="checkedSearchSelectAll(this);" checked="checked"/>
											      <label for="${moduleMap.key}_searchCheck">${moduleMap.value}</label>
											      </span>
											 </li>
										 </td>
								   <% 
									 n++;
									 if(n%5 == 0){
										 out.print("</tr><tr>");
								  	 }
								   %>
							      </c:forEach>
					  	 	</c:forEach>
					  	 </tr>
					   </table>
                      </ul>
                    </div>
					</td>
				</tr>
			</table>
			
			</div>
                    <div class="lui_handover_btn_w">
                       <ui:button style="width:100px" text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.search') }" id="search" onclick="searchOperation();"/>&nbsp;&nbsp;
                   </div>
                </div> 
                <%-- 结果部分--%> 
                <div class="lui_handover_searchResult" style="display: none" id="resultDiv">
                    <div id="valueDiv" style="display: none"></div>
                    <h3 class="title"> ${ lfn:message('sys-handover:sysHandoverConfigMain.searchResult') }
	                      <span class="item item_unck ck_all">
		                      <c:choose>
		                    		<c:when test="${param.type eq 'doc' || param.type eq 'item'}">
		                    		</c:when>
		                    		<c:otherwise>
		                    			<%-- 一键展开/折叠--%> 
								    	 <span class="a_spead_onekey" onclick="oneKeyShow(true);">${ lfn:message('sys-handover:sysHandoverConfigMain.onekeySpred') }</span>
								    	 <span class="a_retract_onekey" onclick="oneKeyShow(false);" style="display: none">${ lfn:message('sys-handover:sysHandoverConfigMain.onekeyRetract') }</span>
		                    		</c:otherwise>
		                    	</c:choose>
	                  	  </span>
                  	</h3>
                  	<%--记录显示--%> 
                    <div name="resultContent" class="resultContent" id="resultContent">
                    </div>
                    <div class="lui_handover_btn_w" id="operationArea">
                    	<c:choose>
                    		<c:when test="${param.type eq 'doc' || param.type eq 'item'}">
	                    		<%-- 文档类，默认状态是等待执行 --%>
	                    		<%@ include file="./js/sysHandoverConfigMain_edit_doc_js.jsp"%>
	                    		<ui:button text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.submit') }" id="submit" onclick="submitOperation();"/>
                    		</c:when>
                    		<c:otherwise>
                    			<%-- 配置类，默认状态是执行成功 --%>
	                    		<%@ include file="./js/sysHandoverConfigMain_edit_config_js.jsp"%>
	                          	<ui:button text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.execute') }" id="execute" onclick="executeOperation();"/>
                    		</c:otherwise>
                    	</c:choose>
                    </div>
                    <div class="lui_handover_btn_w" id="operationCompletion" style="display:none">
                   		<span class='handover_result'>${ lfn:message('sys-handover:sysHandoverConfigMain.executeEnd') }</sapn>
                    </div>
                    <%--无记录显示--%> 
                    <div class="no_result_div" id="no_result_div" style="display:none">
                    		<h3 class="title">${lfn:message('sys-handover:sysHandoverConfigMain.noResult.title') }</h3>
                    		<span class="item item_unck ck_all">
						    	 <span class="a_spead_onekey_no" onclick="oneKeyShowModule(true);">${lfn:message('sys-handover:sysHandoverConfigMain.noResult.show') }</span>
						    	 <span class="a_retract_onekey_no" style="display: none" onclick="oneKeyShowModule(false);" style="display: none">${lfn:message('sys-handover:sysHandoverConfigMain.noResult.hide') }</span>
                  	 		</span>
                    </div>
                    <div name="noResultContent" style="display: none" class="noResultContent" id="noResultContent">
                    </div>
                   </div>
               </div>
        </div>
        <!-- 主体结束 -->
		<script type="text/javascript">
		// 选择无效的组织
		function selectInvalid() {
			var _orgType = "${orgType}".replace("|", ",");
			Dialog_AddressList(false,'fdFromId','fdFromName',';','sysHandoverService&orgType='+_orgType,changeFromName,
			'sysHandoverService&orgType='+_orgType+'&keyword=!{keyword}',null,null,"${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }");
		}
		function chooesType(val) {
			var advance = document.getElementById("advance");
			typeVar = val;
			if(val == "1") {
				$("#_searchSelectAll").prop("checked", 'checked');
				searchCheckAll();
				advance.style.display = 'none';
			} else if(val == "3") {
				advance.style.display = 'none';
			} else {
				advance.style.display = '';
			}
			$("#div_main").corner("destroy");
			$("#div_main").corner("13px");
		}
			var jstree;

			// 显示树内容
			function showTree(childrens) {
				if (childrens.length < 1) {
					$("#resultContent").html("<h3 style='text-align:center;'><span class='txtstrong'>${ lfn:message('sys-handover:sysHandoverConfigMain.noResult.info') }</span></h3>");
					$("#operationArea").hide();
					return;
				}
				$("#operationArea").show();
	
				// 如果树已存在，需要销毁
				try {
					if (jstree) {
						jstree.destroy();
					}
				} catch (e) {
				}
				
				var text = "${ lfn:message('sys-handover:sysHandoverConfigMain.fdContent') }";
				<c:if test="${param.type eq 'config'}">
	        		text += "(${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.config') })";
	        	</c:if>
	        	<c:if test="${param.type eq 'doc'}">
	        		text += "(${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.doc') })";
	        	</c:if>
	
				// 创建树
				$('#resultContent').jstree( {
					plugins : [ 'checkbox' ],
					checkbox : {
						// 消除行选中样式
						keep_selected_style : false,
						tie_selection : false,
						whole_node : false
					},
					core : {
						themes : {
							// 消除连线
							dots : false,
							// 消除图标
							icons : false
						},
						data : {
							id : 'handler_root_node',
							text : text,
							state : {
								selected : true
							},
							children : childrens
						}
					}
				})
				.on('click.jstree', function(e) {
					var target = $(e.target);
					
					var id = $(e.target).parents('li').attr('id');
					var node = jstree.get_node(id);
					
					if(target.hasClass("jstree-anchor")) {
						var href = node.a_attr.href;
						if(href !== '#') {
							window.open(href, '_blank');
						}
					}

					if(!target.hasClass("jstree-checkbox")) {
						return false;
					}
					// 判断是否禁用
					if(jstree.is_disabled(id)) {
						return false;
					}
					
					var docId;
					var checked = jstree.is_checked(id);
					if(node.a_attr && node.a_attr.a_id_data) {
						docId = node.a_attr.a_id_data;
						if(node.a_attr.href != "#")
							id = jstree.get_node(id).parent;
					}
					moduleChange(id, checked, docId);
				})
				.on('ready.jstree', function(e, data) {
					jstree = data.instance;
					initTree();
				});
			}

			seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				// 修改交接人
				changeFromName = function () {
					$("#validate_hand").remove();
					var fdFromName = $("input[name='fdFromName']")[0].value;
					if (fdFromName == null || fdFromName == "") {
						showErrorMessage($("input[name='fdFromName']")[0], "${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromNameNotNull') }", true);
						return;
					}
					$($("input[name='fdFromName']")[1]).val(fdFromName);
				};
				// 修改接收人
				changeToName = function () {
					$("#validate_hand").remove();
					var fdToName = $("input[name='fdToName']")[0].value;
					if (fdToName == null || fdToName == "") {
						showErrorMessage($("input[name='fdToName']")[0], "${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromNameNull') }", true);
						return;
					}
					$($("input[name='fdToName']")[1]).val(fdToName);
				};
	
				// 显示错误信息
				showErrorMessage = function (input, message, flag) {
					if ($("#validate_hand").length == 0) {
						var parent = $(input).parent().parent();
						if (flag)
							parent = parent.parent();
						parent.append('<div class="validation-advice" id="validate_hand" _reminder="true"><table class="validation-table"><tbody><tr><td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td><td class="validation-advice-msg">' + message + '</td></tr></tbody></table></div>');
					}
				};
	
				// 查询全选
				searchCheckAll = function () {
					var isChecked = $('#_searchSelectAll').is(":checked");
					$("[name = searchModuleCheckBox]:checkbox").each( function() {
						if (isChecked) {
							$(this).prop("checked", 'checked');
						} else {
							$(this).removeAttr("checked");
						}
					});
				};

				oneKeyShowModule = function(isShow) {
					if (isShow) {
						$($(".a_spead_onekey_no")[0]).hide();
						$($(".a_retract_onekey_no")[0]).show();
						$("#noResultContent").show();
					} else {
						$($(".a_spead_onekey_no")[0]).show();
						$($(".a_retract_onekey_no")[0]).hide();
						$("#noResultContent").hide();
					}
				};
	
				// 显示未数据模块
				showNoResultContent = function (datas) {
					var table = $("<table width='90%' class='tb_simple table_module'></table>");
					var tr;
					for(var i=0; i<datas.length; i++) {
						if(i%3 == 0) {
							tr = $("<tr></tr>");
							tr.appendTo(table);
						}
						var td = $("<td width='33.3%'></td>");
						td.html(datas[i].moduleMessageKey);
						td.appendTo(tr);
					}
					table.appendTo("#noResultContent");
				};

				// 查询页面全选联动
				checkedSearchSelectAll = function(obj) {
					var isChecked = $(obj).is(":checked");
					var selectAll = $("#_searchSelectAll");
					if (!isChecked) {
						if (selectAll.is(":checked")) {
							selectAll.removeAttr("checked");
						}
					} else {
						var checked = true;
						$("#searchDiv").find(":checkbox").each( function() {
							if ($(this).attr("disabled") != "disabled" && $(this).attr("id") != "_searchSelectAll") {
								if (!$(this).is(":checked")) {
									checked = false;
									return;
								}
							}
						});
						if (checked) {
							selectAll.prop("checked", "checked");
						}
					}
				};

				//重置
				resetOperation = function() {
					$("#resultDiv").hide();
					$("#searchDiv").show();
					$("#resultContent").html('');
					$("#noResultContent").html('');
					$("#no_result_div").hide();
					oneKeyShowModule(false);

					$("input[name='fdFromId']").val("");
					$("input[name='fdFromName']").val("");
					$("input[name='fdToId']").val("");
					$("input[name='fdToName']").val("");
					$("#valueDiv").html("");

					$("#from_edit").show();
					$("#from_view").hide();
					$("#to_edit").show();
					$("#to_view").hide();
					
					$("#operationArea").show();
					$("#operationCompletion").hide();

					$("#_searchSelectAll").prop("checked", 'checked');
					//全选
					searchCheckAll();
					oneKeyShow(false);
					
					// 适合改版后的地址本
					$("input[name='fdFromName']").parent().find("ol").empty();
					$("input[name='fdToName']").parent().find("ol").empty();
					
					// 更新主ID
					var url_c = Com_SetUrlParameter(location.href, "method", "getMainLogId");
					LUI.$.ajax( {
						url : url_c,
						type : 'post',
						dataType : 'text',
						data : {t: new Date().getTime()},
						success : function(data, textStatus, xhr) {
							if (data != "") {
								$("input[name='fdId']").val(data);
							}
						}
					});
				};
	
				// 模块查询
				searchOperation = function () {
					var checkValue=$("input[name='check']:checked").val();
					
					if("1" != checkValue && "2" != checkValue){
						dialog.alert("${ lfn:message('sys-handover:sysHandoverConfigMain.searchKeysNotNull') }");
						return;
					}
					
					$("#resultContent").html('');
					$("#validate_hand").remove();

					// 清空查询数量
					var btn = $("#execute .lui_widget_btn_txt");
					if($("#submit").length > 0) {
						btn = $("#submit .lui_widget_btn_txt");
					}
					putCountIntoBtn(btn, 0);
					var no_result_btn = $("#no_result_div .title");
					putCountIntoBtn(no_result_btn, 0);
					// 查询交接人是否为空
					var fdFromId = $("input[name='fdFromId']")[0].value;
					if (fdFromId == null || fdFromId == "") {
						showErrorMessage($("input[name='fdFromId']")[0], "${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromNameNotNull') }", false);
						return;
					}
					
					var fdToId = $("input[name='fdToId']")[0].value;
					if(!requireReceiver()){
						if (fdToId == null || fdToId == "") {
							showErrorMessage($("input[name='fdToId']")[0], "${ lfn:message('sys-handover:sysHandoverConfigMain.fdToNameNull') }", false);
							return;
						}
					}
					
					
					// 获取待查询模块
					var keyArr = new Array();
					var checkFdKeys = false;
					$.each($("[name = searchModuleCheckBox]:checkbox"), function(i, n) {
						if ($(n).is(":checked")) {
							checkFdKeys = true;
							keyArr.push($(n).val());
						}
					});
					if (keyArr.length == 0) {
						dialog.alert("${ lfn:message('sys-handover:sysHandoverConfigMain.searchKeysNotNull') }");
						return;
					}
	
					// 隐藏搜索区域
					$("#from_edit").hide();
					$("#from_view").show();
					$("#to_edit").hide();
					$("#to_view").show();
					$("#searchDiv").css("display", "none");
					// 显示结果区域
					$("#resultDiv").slideDown(1, function() {
						var totalSelect = 0;
						var noResultTotal = 0;
						var noResultDatas = [];
						var childrens = [];
						// 开启进度条
						window.progress = dialog.progress();
						__searchData(keyArr, 0, fdFromId, childrens, noResultDatas, totalSelect, noResultTotal, btn, no_result_btn);
					});
				};
				
				__searchData = function(keyArr, index, fdFromId, childrens, noResultDatas, totalSelect, noResultTotal, btn, no_result_btn) {
					if ($('.lui_dialog_progress_bar').length > 0) {
						searchData(keyArr, 0, fdFromId, childrens, noResultDatas, totalSelect, noResultTotal, btn, no_result_btn);
					} else {
						setTimeout(function() {
							__searchData(keyArr, 0, fdFromId, childrens, noResultDatas, totalSelect, noResultTotal, btn, no_result_btn);
						}, 50); 
					}
				};

				searchData = function(keyArr, index, fdFromId, childrens, noResultDatas, totalSelect, noResultTotal, btn, no_result_btn) {
					if (index >= keyArr.length) {
						$("#execute").show();
						// 显示数量
						putCountIntoBtn(btn, totalSelect);
						putCountIntoBtn(no_result_btn, noResultTotal);

						// 显示无数据模块
						showNoResultContent(noResultDatas);
						
						// 显示树
						showTree(childrens);
						return;
					}
					
					var url = Com_SetUrlParameter(location.href, "method", "search");
					var data = {
						fdKey : keyArr[index++],
						fdFromId : fdFromId
					};
					LUI.$.ajax( {
						url : url,
						type : 'get',
						dataType : 'json',
						async : true,
						data : data,
						success : function(data, textStatus, xhr) {
							if (data == false) {
								dialog.failure("${ lfn:message('sys-handover:sysHandoverConfigMain.searchFailture') }");
							} else {
								if (data.total > 0) {
									childrens.push(buildTreeModel(data));
									// 所有选中的记录数量
									totalSelect += data.total;
								} else {
									noResultTotal++;
									noResultDatas.push(data);
									// 显示无数据模块隐藏/显示栏
									if ($("#no_result_div").is(':hidden')) {
										$("#no_result_div").show();
									}
								}
							}
							if(window.progress) {
								// 设置进度值
								window.progress.setProgress(index, keyArr.length);
							}
							searchData(keyArr, index, fdFromId, childrens, noResultDatas, totalSelect, noResultTotal, btn, no_result_btn);
						}
					});
				};

				// 在按钮上显示数量
				putCountIntoBtn = function(btn, count) {
					var text = btn.text();
					var num = text.replace(/[^0-9]+/g, '');
					if(num != "") {
						btn.text(text.replace(num, count));
					} else {
						btn.text(text + "(" + count + ")");
					}
				};
				requireReceiver = function(){
					var type = Com_GetUrlParameter(window.location.href,'type')
					if(type == 'doc' || type == 'config'){
						return true;
					}
					return false;
				};

				$("input[name=execType]").click(function(){
					if(this.value == "1"){
						$("#execType_1").show();
					} else {
						$("#execType_1").hide();
					}
				});
				$("input[name=execMode]").click(function(){
					if(this.value == "1"){
						$("#execMode_1").show();
					} else {
						$("#execMode_1").hide();
					}
				});
			});
		</script>
	</template:replace>
</template:include>