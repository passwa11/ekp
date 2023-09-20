<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.Map"%>
<%
	Object _varParams = request.getAttribute("varParams");
	if (_varParams != null) {
		Map<String, Object> varParams = (Map) _varParams;
		Object extProps = varParams.get("extProps");
		if (extProps != null) {
			JSONObject ___obj = JSONObject.fromObject(extProps);
			Iterator it = ___obj.keys();
			JSONArray array = new JSONArray();
			while (it.hasNext()) {
				Object key = it.next();
				array.add(("qq." + key + "="
						+ ___obj.getString(key.toString())).toString());
			}
			String ___extProps = StringUtil.join(array, "&");
			pageContext.setAttribute("extProps", "&" + ___extProps);
		}
		Object _href = varParams.get("href");
		if (_href != null) {
			String urlParam = "categoryId=!{value}";
			String href = String.valueOf(_href);
			if (href.indexOf('?') > 0) {
				href += "&" + urlParam;
			} else {
				href += "?" + urlParam;
			}
			request.setAttribute("href", href);
		}
		Object modelName = varParams.get("modelName");
		request.setAttribute("modelName", modelName);
	}
%>
	<div class="lui_dataview_search_wrap">
	<!-- 搜索条 Starts -->
	<div class="lui_search_bar">
	<div class="search_bar">
	<div class="search_input">
	<input type="text" id="nav_search" data-lui-mark="search.input"/>
	<!-- 删除取消 -->
	<i class="lui_icon_cancel" onclick="javascript:navCleanSearch();"></i>
	</div>
	<div class="search_btn">
	<i class="lui_icon_search" onclick="javascript:navSearch();"></i>
	</div>
	</div>
	</div>

	</div>

	<script>
	var isKeepCriParameter = "false" ;
	function buildSearchPopup(strutil,topic,popup,spaConst,__box,cate,datas,trigger) {
		var modelName = '${modelName}';
		var configs = {
			datas : datas || [],
			modelName:modelName,
			isKeepCriParameter:isKeepCriParameter
		}
		var search_div=$('.lui_dataview_search_wrap');

		//criProps = criProps ? strutil.toJSON(criProps) : '';


		var _cate = new cate.SearchCate(configs);

		$(".lui_dataview_cate_popup_search").remove();

		var popDiv = $('<div>').attr('class', 'lui_dataview_cate_popup');
		popDiv.addClass('lui_dataview_cate_popup_search');
		var _____height = $(window).height();
		popDiv.css('max-height', _____height);
		popDiv.append(_cate.element);
		var cfg = {
			"align" : "down-left"
		};
		var icon_search=$('.lui_icon_search');

		cfg.builder={"trigger":new trigger.HoverTriggerSearch({"element":icon_search,"position":popDiv})};

		var pp = popup.build(search_div, popDiv, cfg);
		_cate.popup = pp;
		pp.addChild(_cate);
		outerWheelDisable(pp.element);
		//if (datas.length > 0) {
		popDiv.css('width', 600);
		popDiv.css('overflow-y', 'auto');

		pp.overlay.show();
		pp.overlay.trigger.show=true;

		(function(pp) {
			return function() {
				topic.subscribe(spaConst.SPA_CHANGE_ADD, function() {
					pp.overlay.trigger.emit('mouseout', {
						timer : 1
					});
				});
			}();
		}(pp));
	}

	function outerWheelDisable(popDivObj){
	if (navigator.userAgent.indexOf("Firefox") > -1){
        popDivObj.get(0).addEventListener('DOMMouseScroll',function(ev){
            if(ev.detail>0){
            var scrollValue = popDivObj.get(0).scrollHeight-popDivObj.height();
                if(scrollValue<=(popDivObj.scrollTop()+6)){
                    ev.preventDefault()
                }
            }else{
                if(popDivObj.scrollTop()<1){
                    ev.preventDefault()
                }
            }
            },false);
	}else{
        var cc = 0;
        popDivObj.on("mousewheel",function(e){
            var scrollValue = popDivObj.get(0).scrollHeight-popDivObj.outerHeight();
                if(e.originalEvent.wheelDelta<0){
                    if(scrollValue-cc>=4){
                        cc+=40;
                    }
            }else{
                if(cc<10){
                    cc = 0;
                }else{
                    cc=cc - 40;
                }
            }
            popDivObj.scrollTop(cc);
            return false;
            });
	    }
	}


	function createFrame() {
		var frame;
		if(typeof jQuery != 'undefined'){
			frame = $("<div class='lui_dataview_cate'/>");
			return frame;
		}
	}
	function createBox() {
		if(typeof jQuery != 'undefined'){
			var box = $('<ul class="lui_dataview_cate_all_box" />');
			return box;
		}
	}
	var __frame = createFrame();
	function navCleanSearch(){
		$("#nav_search").val("");
	}
	function navSearch(){
		var navSearchName=$("#nav_search").val();
		if(navSearchName==""){
			return;
		}
		$.ajax({
			url : '<c:url value="/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=select&modelName="/>'
			+ '${modelName}'
			+ '&searchText='
			+ encodeURIComponent(navSearchName) + '&authType=2&type=03&qSearch=true',
			dataType : 'json',
			success : function(datas) {
				seajs
				.use(
				[
				'lui/jquery',
				'lui/util/str',
				'lui/popup',
				'lui/topic',
				'lui/spa/const',
				'sys/ui/extend/dataview/render/cateAll/cateSearch',
				'sys/ui/extend/dataview/render/cateAll/triggerSearch'],
				function(strutil,$,popup,topic,spaConst,cate,trigger) {
					var __frame = createFrame();
					var __box = createBox();
					__frame.append(__box);
					buildSearchPopup(strutil,topic,popup,spaConst,__box,cate,datas[0],trigger);
				});
			}
		});
	}
	</script>
<c:if test="${not empty varParams.categoryId}">
	<ui:dataview>
		<ui:source type="AjaxJson">
			{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=currentCate&modelName=${varParams.modelName }&currId=${varParams.categoryId}"}
		</ui:source>
		<ui:render type="Template">
			{$
				<div class="lui_list_nav_curPath_frame">
					<div class="lui_icon_s lui_icon_s_icon_position"></div>
					<div class="lui_list_nav_curPath">{% env.fn.formatText(data[0].text)%}</div>
				</div>
			$}
		</ui:render>
	</ui:dataview>
</c:if>
<ui:menu layout="sys.ui.menu.ver.default">
	<ui:menu-source href="${href }" target="${(empty varParams.target) ? '_self' : (varParams.target)}">
		<ui:source type="AjaxJson">
			{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=index&level=1&modelName=${varParams.modelName }&currId=${varParams.categoryId}&authType=2&parentId=!{value}${extProps}&expand="}
		</ui:source>
	</ui:menu-source>
</ui:menu>
