<%@page import="java.util.Map"%>
<%@page
	import="com.landray.kmss.sys.person.service.plugin.FavoriteCategoryHelp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="favoriteUrl" scope="page"
	value="/sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=favorite&modelName=${varParams.modelName}" />

<c:set var="tempate_rander" scope="page">

		{$
			<ul class='lui_list_nav_list'>
		$} for(var i=0;i < data.length;i++){ {$
		<li><a onclick="favorite_href('{%data[i].value%}')"
			href="javascript:;" class="favorite_category_link"
			title="{%env.fn.formatText(data[i].text)%}"><i
				class="iconfont lui_iconfont_dotted"></i>{%env.fn.formatText(data[i].text)%}</a></li>$}
		} {$
	</ul>
		$}
</c:set>

<%
	Object _varParams = request.getAttribute("varParams");
	if (_varParams != null) {
		Map<String, Object> varParams = (Map) _varParams;
		Object criProps = varParams.get("criProps");
		if (criProps == null) {
			pageContext.setAttribute("criProps", "");
		} else {
			pageContext.setAttribute("criProps", criProps);
		}
	}
%>

<script>
	function favorite_href(id, target) {

		seajs.use([
				'lui/util/str',
				'lui/topic',
				'lui/util/env',
				'lui/framework/router/router-utils',
				'lui/spa/const',
				'lui/jquery' ], function(
				str,
				topic,
				env,
				routerUtils,
				spaConst,
				$) {

			if ("${varParams.onClick}" != "") {

				new Function(str.variableResolver("${varParams.onClick}", {
					value : id
				})).call();

				return;
			}

			if (seajs.data.env.isSpa) {
				// 启用路由模式
				var router = routerUtils.getRouter();

				if (router) {

					var data = {
						'docCategory' : id
					}

					var criProps = "${criProps}";
					
					if (criProps) {
						$.extend(data, str.toJSON(criProps));
					}
					router.push('/docCategory', data);
				} else {
					topic.publish(spaConst.SPA_CHANGE_ADD, {
						value : {
							'docCategory' : id
						}
					})
				}

				return;
			}

			window.open(str.variableResolver(env.fn
					.formatUrl("${varParams.href}"), {
				value : id
			}), '_self');

		});

	}
</script>

<c:set var="favoriteTitle" scope="page"
	value="${lfn:message('sys-person:favorite')}" />
<c:choose>
	<c:when
		test="${empty varParams.content or varParams.content == 'true'}">
		<ui:content
			title="${(empty varParams.title) ? favoriteTitle : (varParams.title)}"
			expand="true" toggle="true">
			<ui:dataview id="favouriteCate">
				<ui:source type="AjaxJson">
					{url: '${favoriteUrl}'}
				</ui:source>
				<ui:render type="Template">
					${tempate_rander }
				</ui:render>
			</ui:dataview>
			<ui:operation vertical="top" onclick="handleSettingFavorite();"
				href="/sys/person/setting.do?setting=sys_person_favorite_category"
				name="${lfn:message('sys-person:sysPersonFavoriteCategory.config') }" />
		</ui:content>
	</c:when>
	<c:otherwise>
		<ui:dataview>
			<ui:source type="AjaxJson">
				{url: '${favoriteUrl}'}
			</ui:source>
			<ui:render type="Template">
				${tempate_rander }
			</ui:render>
		</ui:dataview>

		<ui:operation onclick="handleSettingFavorite();"
			href="/sys/person/setting.do?setting=sys_person_favorite_category"
			name="${lfn:message('sys-person:sysPersonFavoriteCategory.config') }" />
	</c:otherwise>
</c:choose>

<input type="hidden" name="fdCategoryIds" value="">
<input type="hidden" name="fdCategoryNames" value="">

<script>
	var categoryModels =
<%=FavoriteCategoryHelp.buildCategoryJson()%>
	;
	var favoriteTmp = {};

	function doSettingFavorite(modelName) {

		var ms = categoryModels;
		for (var i = 0; i < ms.length; i++) {
			var m = ms[i];
			if (m.model == modelName) {

				var authType = 0;

				switch (modelName) {
					case 'com.landray.kmss.km.imissive.model.KmImissiveSendTemplate':
					case 'com.landray.kmss.km.imissive.model.KmImissiveReceiveTemplate':
					case 'com.landray.kmss.km.imissive.model.KmImissiveSignTemplate':
					case 'com.landray.kmss.km.review.model.KmReviewTemplate':
						authType = 2;
						break;
					default:
						authType = 0;
						break;
				}

				var dialogjs = m.dialogJS;

				var opt = {
					modelName : modelName,
					idField : 'fdCategoryIds',
					nameField : 'fdCategoryNames',
					mulSelect : true,
					authType : authType,
					noFavorite : true,
					notNull : false,
					action : function(params) {

						if (!params) {
							return;
						}

						var ids = (params.id || '').split(';');
						var names = (params.name || '').split(';');
						var _favoriteTmp = {};

						$.each(ids, function(i, id) {

							if (!id || !names[i]) {
								return;
							}

							_favoriteTmp[id] = names[i];
						});

						var addIds = [];
						var addNames = [];
						$.each(_favoriteTmp, function(key, value) {

							if (!favoriteTmp[key]) {
								addIds.push(key);
								addNames.push(value);
							}
						});

						if (addIds.length > 0) {
							quickAddOrRemoveFavorite('add', modelName, addIds,
									addNames);
						}

						var removeIds = [];
						var removeNames = [];
						$.each(favoriteTmp, function(key, value) {

							if (!_favoriteTmp[key]) {
								removeIds.push(key);
								removeNames.push(value);
							}
						});

						if (removeIds.length > 0) {
							quickAddOrRemoveFavorite('remove', modelName,
									removeIds, removeNames);
						}

						favoriteTmp = _favoriteTmp;
						refreshFavourite();

					}
				};

				if (/dialog\.simpleCategory/.test(dialogjs)) {

					seajs.use([ 'lui/dialog' ], function(dialog) {

						dialog.simpleCategory(opt);
					});

				} else if (/dialog\.category/.test(dialogjs)) {

					seajs.use([ 'lui/dialog' ], function(dialog) {

						dialog.category(opt);
					});
				}

				break;
			}
		}
	}

	function quickAddOrRemoveFavorite(action, modelName, ids, names) {

		var url = null;

		if (action == 'add') {
			url = 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=quickAdd';
		} else if (action == 'remove') {
			url = 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=quickRemove';
		}

		if (!url) {
			return;
		}

		var data = {
			modelName : modelName,
			ids : ids,
			names : names
		};

		$.ajax({
			type : "POST",
			url : Com_Parameter.ContextPath + url,
			data : $.param(data, true),
			dataType : 'json',
			async : false,
			success : function(result) {

			},
			error : function(result) {

				console.error(result);
			}
		});
	}

	function getFavorite() {

		$
				.ajax({
					url : Com_Parameter.ContextPath
							+ 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=favorite&modelName=${varParams.modelName}',
					async : false,
					success : function(data) {

						var ids = [];
						var names = [];
						favoriteTmp = {};
						$.each(data, function(i, d) {

							favoriteTmp[d.value] = d.text;

							ids.push(d.value);
							names.push(d.text);
						});

						$('input[name="fdCategoryIds"]').val(ids.join(';'));
						$('input[name="fdCategoryNames"]').val(names.join(';'));
					},
					error : function(err) {

						console.error(err);
					}
				});

	}

	window.refreshFavourite = function() {

		if (LUI('favouriteCate').source) {
			LUI('favouriteCate').source.get();
		}
	};

	window.handleSettingFavorite = function() {

		// 重新获取常用分类数据
		getFavorite();
		doSettingFavorite('${varParams.modelName}');
	}
</script>
