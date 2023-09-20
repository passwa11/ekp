<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/kms/knowledge/resource/style/template_select.css" />
		<div id="categoryCtx" class="clearfloat">
			<div class="lui_template_frame_item">
				<ul class="lui_template_content">
				</ul>
			</div>
		</div>

		<script type="text/javascript">
			function serializeParams(params) {

				var array = [];
				for ( var kk in params) {
					array.push('qq.' + encodeURIComponent(kk) + '='
							+ encodeURIComponent(params[kk]));
				}
				var str = array.join('&');
				return str;
			}

			var interval = setInterval(loadTemplate, 50);
			function loadTemplate() {

				if (!window['$dialog'])
					return;
				seajs
						.use(
								[
										'lui/jquery',
										'lui/data/source',
										'lui/util/env' ],
								function($, source, env) {

									var url = "/kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do?method=findTemplate";

									if (window['$dialog'].___params) {
										url += ("&" + serializeParams(window['$dialog'].___params));
									}
									$
											.ajax({
												url : env.fn.formatUrl(url),
												dataType : 'json',
												success : function(
														rtnData,
														textStatus) {
													var len = rtnData.length;
													var value, text, desc;
													for (var i = 0; i < len; i++) {
														value = rtnData[i].value;
														text = rtnData[i].text;
														desc = "${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.wiki.desc')}";
														var type = 'wiki';
														if (value == '1') {
															type = 'multidoc';
															desc = "${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.multidoc.desc')}";
														}
														$(
																".lui_template_content")
																.append(
																		"<li><a id='"
																				+ value
																				+ "'"
																				+ " onclick='selected(this)' href='javascript:;'>"
																				+ "<div class='lui_template_left'><span class='icon iconfont_nav lui_iconfont_nav_kms_"+type+"'></span></div>"
																				+ "<div class='lui_template_status'><i></i></div>"
																				+ "<div class='lui_template_right'><h3>"
																				+ text
																				+ "</h3><p>"
																				+ desc
																				+ "</p></div>"
																				+ "</a></li>");
													}
												}
											});
								});
				clearInterval(interval);
			};

			seajs.use([
					'kms/knowledge/kms_knowledge_ui/js/create',
					'lui/util/str',
					'lui/util/env' ], function(create, strutil, env) {

				window.selected = function(e) {

					if (!e)
						return;
					var id = e.id;
					var urlParam = create.getUrl(id);

					var target = '_blank';
					if (window['$dialog'].___params) {
						var param = window['$dialog'].___params;
						urlParam = strutil.variableResolver(urlParam, param);
						if (param.target) {
							target = param.target
						}
					}
					var top = Com_Parameter.top || window.top;
					top.open(env.fn.formatUrl(urlParam), target);

					if (target == '_blank') {
						setTimeout(function() {

							window['$dialog'].hide();
						}, 100);
					}

				}

			})
		</script>
	</template:replace>
</template:include>
