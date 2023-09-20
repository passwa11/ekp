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
			var multiAdd="";
			var wikiAdd="";
			var kemAdd="";
			
		    
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

									var multidoctext="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsMultidocKnowledge')}";
									var multidocdesc = "${lfn:message('kms-category:kmsCategoryKnowledgeRel.multidoc.desc')}";
									var wikitext="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsWikiMain')}";
									var wikidesc = "${lfn:message('kms-category:kmsCategoryKnowledgeRel.wiki.desc')}";
									var kemtext="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsKemMain')}";
									var kemdesc = "${lfn:message('kms-category:kmsCategoryKnowledgeRel.kem.desc')}";
									<kmss:auth
										requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add"
										requestMethod="GET">
									$(".lui_template_content")
											.append(
													"<li><a id='1'"
															+ " onclick='selected(this)' href='javascript:;'>"
															+ "<div class='lui_template_left'><span class='icon iconfont_nav lui_iconfont_nav_kms_multidoc'></span></div>"
															+ "<div class='lui_template_status'><i></i></div>"
															+ "<div class='lui_template_right'><h3>"
															+ multidoctext
															+ "</h3><p style='width:100%;'>"
															+ multidocdesc
															+ "</p></div>"
															+ "</a></li>");
									</kmss:auth>
									
									<kmss:auth
										requestURL="/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=add"
										requestMethod="GET">
									$(".lui_template_content").append(
													"<li><a id='2'"
															+ " onclick='selected(this)' href='javascript:;'>"
															+ "<div class='lui_template_left'><span class='icon iconfont_nav lui_iconfont_nav_kms_wiki'></span></div>"
															+ "<div class='lui_template_status'><i></i></div>"
															+ "<div class='lui_template_right'><h3>"
															+ wikitext
															+ "</h3><p style='width:100%;'>"
															+ wikidesc
															+ "</p></div>"
															+ "</a></li>");
									</kmss:auth>
									
									<kmss:auth
										requestURL="/kms/kem/kms_kem_main/kmsKemMain.do?method=add"
										requestMethod="GET">
									$(".lui_template_content").append(
													"<li><a id='3'"
															+ " onclick='selected(this)' href='javascript:;'>"
															+ "<div class='lui_template_left'><span class='icon iconfont_nav lui_iconfont_nav_kms_kem'></span></div>"
															+ "<div class='lui_template_status'><i></i></div>"
															+ "<div class='lui_template_right'><h3>"
															+ kemtext
															+ "</h3><p style='width:100%;'>"
															+ kemdesc
															+ "</p></div>"
															+ "</a></li>");
									</kmss:auth>
								});
				
				clearInterval(interval);
			};

			seajs.use([
					'kms/category/resource/js/create',
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
