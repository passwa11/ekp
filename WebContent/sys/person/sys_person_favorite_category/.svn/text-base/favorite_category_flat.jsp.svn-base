<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
seajs.use(['lui/jquery','lui/util/str','lui/dialog'], function($,str,dialog) {

	window.favoriteUrlVariableResolver = str.variableResolver;

	window.openCreate = function(id){
		var params = {
				id: id
		};
		var url = "${LUI_ContextPath}${addUrl}";
		url = favoriteUrlVariableResolver(url, params);

		window.open(url,"_blank");
	};

	window.openCategory = function(){
		if("${JsParam.isSimpleCategory}" == 'false' || '${JsParam.cateType}' == 'globalCategory'){
			dialog.categoryForNewFile('${JsParam.modelName}','${addUrl}',false,null,null,null,null,null,true,null,null,'${JsParam.key}');
		}else{
			dialog.simpleCategoryForNewFile('${JsParam.modelName}','${addUrl}',false,null,null,null);
		}
	};

	window.setFavouriteCategory = function(){
		//window.open("${LUI_ContextPath}/sys/person/setting.do?setting=sys_person_favorite_category","_blank");
		handleSettingFavorite();
	};

	window.refreshFavourite = function(){
		if(LUI('favouriteCate').source){
			LUI('favouriteCate').source.get();
		}
	};
});

</script>
<div>
	<div id="favouriteCateDiv">
		<ui:dataview id="favouriteCate">
			<ui:source type="AjaxJson">
				{url: '/sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=favoriteWithCate&getAddUrl=true&modelName=${JsParam.modelName}&key=${JsParam.key }'}
			</ui:source>
			<ui:render type="Template">
			<c:choose>
				<c:when test="${'true' ne param.isSimpleCategory or 'globalCategory' eq param.cateType}">
				{$

					<div class="lui-cate-panel-heading">
						<h2 class="lui-cate-panel-heading-title">
							<i class="icon-collect"></i>
								<span><bean:message bundle="sys-person" key="person.setting.favourite.myTemp"/></span>
								<span class="btn-refresh" onclick="refreshFavourite();"></span>

				$}
			   if( data.length > 0){
			   		{$
								<a class="favourite-setting" href="javascript:void(0)" onclick="handleSettingFavorite();">
									<i class="icon-setting"></i><span><bean:message bundle="sys-person" key="person.setting.btn"/></span>
								</a>
							</h2>
						</div>
						<ul class='lui-cate-panel-list'>
			   		$}

					for(var i=0;i < data.length;i++){
						<%--#152524-选择模块后选择的模板显示的是新样式，正常是应该是老样式
						因为这个单号描述的问题，所以把之前km/review页面的流程发起页的修改进行还原了
						因为流程发起页后来重新做了一版，用了新的页面，这里的修改对个人流程中心产生了影响
						所以还原，注释掉之前修改添加的部分，同时还包括下面的模板添加部分（添加常用流程、全部模板）--%>
						{$
							<li class="lui-cate-panel-list-item">
								 <div class="link-box">
							        <div class="link-box-heading">
							          <p><span>{%env.fn.formatText(data[i].text)%}</span></p>
							        </div>
							        <div class="link-box-body">
							          <a  href="javascript:void(0)" onclick="javascript:openCreate('{%data[i].value%}');" title="{%env.fn.formatText(data[i].text)%}"><bean:message key="button.add"/></a>
							        </div>
									<div class="link-box-footer">
							          <h6 class="link-box-title">
							$}
							          if(data[i].cateName){
							           {$
							         	  <i class="icon"></i><span>{%env.fn.formatText(data[i].cateName)%}</span>
							         	$}
							           }
							{$
							          </h6>
							        </div>
							     </div>
							</li>
						$}
						<%--#145023-流程发起页面调整-开始--%>
						<%--{$
							<li class="lui-cate-panel-list-item new-list-item">
								&lt;%&ndash;li显示Div&ndash;%&gt;
								<div class="link-box-new" onclick="javascript:openCreate('{%data[i].value%}');">
									&lt;%&ndash;图标显示Div&ndash;%&gt;
									<div class="link-box-icon">
						$}
										if(data[i].fdIcon =='lui_icon_l_icon_1' || data[i].fdIcon ==''){
											{$
												<img class="lui_img_l" src="{%Com_Parameter.ContextPath%}sys/ui/extend/theme/default/images/icon_test.png" width="100%">
											$}
										}
										if(data[i].fdIcon =='lui_icon_l_icon_1' || data[i].fdIcon !=''){
											{$
												<img class="lui_img_l" src="{%data[i].fdIcon%}" width="100%">
											$}
										}
						{$
									</div>
									<div class="link-box-text">
										&lt;%&ndash;模板名称Div&ndash;%&gt;
										<div class="link-box-temp">
											<p><span title="{%env.fn.formatText(data[i].text)%}">{%env.fn.formatText(data[i].text)%}</span></p>
										</div>
										&lt;%&ndash;流程分类名称Div&ndash;%&gt;
										<div class="link-box-proce">
						$}
											if(data[i].cateName){
											{$
												<p><span title="{%env.fn.formatText(data[i].cateName)%}">{%env.fn.formatText(data[i].cateName)%}</span></p>
											$}
											}
						{$
										</div>
									</div>
								</div>
							</li>
						$}--%>
						<%--#145023-流程发起页面调整-结束--%>
					}
			 }else{
				 {$
				 			</h2>
						</div>
					<ul class='lui-cate-panel-list'>
					<%--添加分类模板-开始--%>
					<li class="lui-cate-panel-list-item lui-cate-addCate">
						<div class="link-box">
							<div class="link-box-heading" onclick="setFavouriteCategory();">
								<p><span class="btn-add" title="<bean:message bundle="sys-person" key="person.setting.favourite.addTemp"/>"><i></i></span></p>
							</div>
							<div class="link-box-footer">
								<h6 class="link-box-title"><span><bean:message bundle="sys-person" key="person.setting.favourite.addTemp"/></span></h6>
							</div>
						</div>
					</li>
					<%--<li class="lui-cate-panel-list-item lui-cate-addCate addCate-new">
						<div class="link-box-add" >
							<div class="link-box-add-body" onclick="setFavouriteCategory();">
								<div class="link-box-add-icon">
									&lt;%&ndash;<svg src="${LUI_ContextPath}/sys/ui/extend/theme/default/images/link_add.svg"></svg>
									<img src="${LUI_ContextPath}/sys/ui/extend/theme/default/images/link_add.svg">&ndash;%&gt;
								</div>
								<div class="link-box-add-cate">添加常用流程</div>
							</div>
						</div>
					</li>--%>
					<%--添加分类模板-结束--%>
				$}
			 }
			 <%--我常用的流程-全部模板-开始--%>
			 {$
			  		<li class="lui-cate-allCate">
						<div class="link-box" onclick="openCategory();">
							<div class="link-box-heading"></div>
							<div class="link-box-body">
								<a href="javascript:;" class="title"><bean:message bundle="sys-person" key="person.setting.favourite.allTemp"/></a>
							</div>
							<div class="link-box-footer"></div>
						</div>
					</li>
					<%--<li class="lui-cate-allCate">
						<div class="temp-all-box" onclick="openCategory();">
							<div class="temp-all-icon"></div>
							<div class="temp-all-txt">
								<bean:message bundle="sys-person" key="person.setting.favourite.allTemp"/>
							</div>
						</div>
					</li>--%>
				</ul>
			$}
			<%--我常用的流程-全部模板-结束--%>
			</c:when>
			<c:otherwise>
			{$

					<div class="lui-cate-panel-heading">
						<h2 class="lui-cate-panel-heading-title">
							<i class="icon-collect"></i>
								<span><bean:message bundle="sys-person" key="person.setting.favourite.myCate"/></span>
								<span class="btn-refresh" onclick="refreshFavourite();"></span>

				$}
			   if( data.length > 0){
			   		{$

								<a class="favourite-setting" href="javascript:void(0)" onclick="handleSettingFavorite();">
									<i class="icon-setting"></i><span><bean:message bundle="sys-person" key="person.setting.btn"/></span>
								</a>
							</h2>
						</div>
						<ul class='lui-cate-simple-panel-list clearfloat'>
			   		$}

					for(var i=0;i < data.length;i++){
						{$
							<li class="lui-cate-simple-panel-list-item">
								 <div class="link-box">
									<div class="link-box-heading">
										<p class="link-box-title"> <span><i class="icon"></i><span>{%env.fn.formatText(data[i].text)%}</span> </span>
										</p>
									</div>
									<div class="link-box-body">
										<a class="btn-add" onclick="javascript:openCreate('{%data[i].value%}');" title="{%env.fn.formatText(data[i].text)%}"><bean:message key="button.add"/></a>
									</div>
								</div>
							</li>
						$}
						<%--#145023-流程发起页面调整-开始--%>
						<%--{$
							<li class="lui-cate-panel-list-item">
								&lt;%&ndash;li显示Div&ndash;%&gt;
								<div class="link-box-new">
									&lt;%&ndash;图标显示Div&ndash;%&gt;
									<div class="link-box-icon">
										<a onclick="javascript:openCreate('{%data[i].value%}');"></a>
									</div>
									<div class="link-box-text">
										&lt;%&ndash;模板名称Div&ndash;%&gt;
										<div class="link-box-temp">
											<p><span title="{%env.fn.formatText(data[i].text)%}">{%env.fn.formatText(data[i].text)%}</span></p>
										</div>
											&lt;%&ndash;流程分类名称Div&ndash;%&gt;
										<div class="link-box-proce">
											<p><span></span></p>
										</div>
									</div>
								</div>
							</li>
						$}--%>
						<%--#145023-流程发起页面调整-结束--%>
					}
			 }else{
				 {$
				 			</h2>
						</div>
					<ul class='lui-cate-simple-panel-list clearfloat'>
					<li class="lui-cate-simple-panel-list-addCate">
						<div class="link-box-body">
							<a href="javascript:;" onclick="setFavouriteCategory();" class="btn-add"><i></i><span><bean:message bundle="sys-person" key="person.setting.favourite.addCate"/></span>
							</a>
						</div>
					</li>
				$}
			 }
			 {$
			 		<li class="lui-cate-simple-allCate">
						<div class="link-box" onclick="openCategory();">
							<div class="link-box-heading"></div>
							<div class="link-box-body">
								<a href="javascript:;" class="title"><bean:message bundle="sys-person" key="person.setting.favourite.allCate"/></a>
							</div>
							<div class="link-box-footer"></div>
						</div>
					</li>
				</ul>
			$}
			</c:otherwise>
			</c:choose>
			</ui:render>
		</ui:dataview>
	</div>
</div>

<input type="hidden" name="fdCategoryIds" value="">
<input type="hidden" name="fdCategoryNames" value="">

<script>

	var favoriteTmp = {};

	function doSettingFavorite(modelName) {

		var authType = 0;

		switch(modelName) {
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

		var opt = {
			modelName: modelName,
			idField: 'fdCategoryIds',
			nameField: 'fdCategoryNames',
			mulSelect: true,
			authType: authType,
			noFavorite: true,
			notNull: false,
			action: function(params) {

				if(!params) {
					return;
				}

				var ids = (params.id || '').split(';');
				var names = (params.name || '').split(';');
				var _favoriteTmp = {};

				$.each(ids, function(i, id){

					if(!id || !names[i]){
						return;
					}

					_favoriteTmp[id] = names[i];
				});

				var addIds = [];
				var addNames = [];
				$.each(_favoriteTmp, function(key, value){
					if(!favoriteTmp[key]){
						addIds.push(key);
						addNames.push(value);
					}
				});

				if(addIds.length > 0) {
					quickAddOrRemoveFavorite('add', modelName, addIds, addNames);
				}

				var removeIds = [];
				var removeNames = [];
				$.each(favoriteTmp, function(key, value){
					if(!_favoriteTmp[key]){
						removeIds.push(key);
						removeNames.push(value);
					}
				});

				if(removeIds.length > 0) {
					quickAddOrRemoveFavorite('remove', modelName, removeIds, removeNames);
				}

				favoriteTmp = _favoriteTmp;
				refreshFavourite();

			}
		};

		if('${JsParam.isSimpleCategory}' == 'false' || '${JsParam.cateType}' == 'globalCategory') {
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.category(opt);
			});
		} else {
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.simpleCategory(opt);
			});

		}

	}

	function quickAddOrRemoveFavorite(action, modelName, ids, names) {

		var url = null;

		if(action == 'add') {
			url = 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=quickAdd';
		} else if(action == 'remove') {
			url = 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=quickRemove';
		}

		if(!url) {
			return;
		}

		var data = {
			modelName: modelName,
			ids: ids,
			names: names
		};

		$.ajax({
			type : "POST",
			url : Com_Parameter.ContextPath + url,
			data : $.param(data, true),
			dataType : 'json',
			async: false,
			success : function(result) {
			},
			error : function(result) {
				console.error(result);
			}
		});
	}

	function getFavorite() {

		$.ajax({
			url: Com_Parameter.ContextPath + 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=favorite&modelName=${JsParam.modelName}',
			async: false,
			success: function(data) {
				var ids = [];
				var names = [];
				favoriteTmp = {};
				$.each(data, function(i, d){

					favoriteTmp[d.value] = d.text;

					ids.push(d.value);
					names.push(d.text);
				});

				$('input[name="fdCategoryIds"]').val(ids.join(';'));
				$('input[name="fdCategoryNames"]').val(names.join(';'));
			},
			error: function(err) {
				console.error(err);
			}
		});


	}

	window.handleSettingFavorite = function() {
		// 重新获取常用分类数据
		getFavorite();
		doSettingFavorite('${JsParam.modelName}');
	}
</script>

