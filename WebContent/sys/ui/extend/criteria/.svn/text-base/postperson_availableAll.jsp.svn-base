<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%
	String id = "itemSearch_" +  IDGenerator.generateID();
    pageContext.setAttribute("id", id);
    if (StringUtil.isNotNull(UserUtil.getUser().getFdId())) {
		pageContext.setAttribute("userId", UserUtil.getUser().getFdId());
	}
%>
<list:cri-criterion title="${lfn:message('sys-ui:ui.criteria.postperson') }" expand="false" key="docPosts">
	<list:box-title>
		<div style="line-height: 30px">${criterionAttrs['title']}</div>
	</list:box-title>
	<list:box-select>	
		<c:choose>
			<c:when test="${not empty criterionAttrs['cfg-if']}">
				<list:item-select type="lui/criteria/select_panel!CriterionSelectDatas" cfg-if="${criterionAttrs['cfg-if']}">
					<ui:source type="AjaxJson">
						{url: "/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=criteria&userId=!{userId}&searchText=!{searchText}&orgType=780"}
					</ui:source>
					<list:item-search width="50px" height="22px" id="${id}">
						<ui:event event="search.changed" args="evt">
							var se = this.parent.parent.criterionSelectElement;
							var source = se.source;
							evt.userId = '${userId}';
							if(evt.searchText)
								evt.searchText = encodeURIComponent(evt.searchText);
							source.resolveUrl(evt);
							source.one('data', function(data) {
								if (data && data.length == 1) {
									if (se.multi)
										se.selectedValues.add(data[0].value);
									else
										se.selectedValues.set(data[0].value);
								}
							});
							source.get();
						</ui:event>
					</list:item-search>
				</list:item-select>
			</c:when>
			<c:otherwise>
				<list:item-select type="lui/criteria/select_panel!CriterionSelectDatas">
					<ui:source type="AjaxJson">
						{url: "/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=criteria&userId=!{userId}&searchText=!{searchText}&orgType=780"}
					</ui:source>
					<list:item-search width="50px" height="22px" id="${id}">
						<ui:event event="search.changed" args="evt">
							var se = this.parent.parent.criterionSelectElement;
							var source = se.source;
							evt.userId = '${userId}';
							if(evt.searchText)
								evt.searchText = encodeURIComponent(evt.searchText);
							source.resolveUrl(evt);
							source.one('data', function(data) {
								if (data && data.length == 1) {
									if (se.multi)
										se.selectedValues.add(data[0].value);
									else
										se.selectedValues.set(data[0].value);
								}
							});
							source.get();
						</ui:event>
					</list:item-search>
				</list:item-select>
			</c:otherwise>
		</c:choose>	
	</list:box-select>
</list:cri-criterion>
<script>
LUI.ready(function(){
	function findCriterion(component){
		component = component.parent;
		while(component){
			if(component.className && component.className === 'criterion'){
				return component;
			}
			component = component.parent;
		}
	}
	function findParamInHash(key){
		var params = [], 
			paramsObject = {},
			criString = null,
			cross = false;
		try{
			params = top.location.hash ? top.location.hash.substr(1).split("&") : [];
		}catch(e){
			params = location.hash ? location.hash.substr(1).split("&") : [];
			cross = true;
		}
		if(!cross){
			var innerParams = location.hash ? location.hash.substr(1).split("&") : [];
			params = params.concat(innerParams);
		}
		for (var i = 0; i < params.length; i++) {
			if (!params[i])
				continue;
			var a = params[i].split("=");
			if(a[0] === 'cri.q'){
				criString =  decodeURIComponent(a[1]);
			}
		}
		if(criString){
			var cris = criString.split(';');
			for(var i = 0; i < cris.length; i++){
				if (!cris[i])
					continue;
				var a = cris[i].split(":");
				if(a[0] === key){
					return decodeURIComponent(a[1]);
				}
			}
		}
		return null;
	}
	try{
		var component = LUI('${id}'),
			criterion = findCriterion(component),
			key = criterion.config.key,
			param = findParamInHash(key),
			attr = {};
		attr['userId'] = param ? param : '${userId}';
		var criterionSelectElement = criterion.selectBox.criterionSelectElement,
			source = criterionSelectElement.source;
		source.resolveUrl(attr);
		source.get();
	}catch(e){
		if(window.console){
			window.console.log('init person-availabelAll criterion error');
		}
	}
});
</script>