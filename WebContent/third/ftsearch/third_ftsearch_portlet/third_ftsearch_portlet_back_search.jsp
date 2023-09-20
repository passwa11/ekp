<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
        //搜索
		function thirdFtsearchPortletBackSearch(e){
			var keyField =$(e).closest(".third_ftsearch_portlet_content_table").find("input[name='ftsearch_portle_search_keword']")[0];
			if(keyField.value==""){
				keyField.focus();
				return;
			}else{
				var url = Com_Parameter.ContextPath+"sys/ftsearch/searchBuilder.do?method=search&queryString=";
				url = url + encodeURIComponent(keyField.value);
				url = url + "&newLUI=true";
				url = url + "&seq=" + Math.random();
				url = url + "&searchAll=true";
				window.open(url,"_blank");
			} 
		}
		//热词点击搜索
		function thirdFtsearchPortletBackHotSearch(value){
			if(value!=""){
				var url = Com_Parameter.ContextPath+"sys/ftsearch/searchBuilder.do?method=search&queryString=";
				url = url + encodeURIComponent(value);
				url = url + "&newLUI=true";
				url = url + "&seq=" + Math.random();
				url = url + "&searchAll=true";
				window.open(url,"_blank");
			}
		}
        
	</script>

<div class="third_ftsearch_portlet_background_img">
	<img id="third_ftsearch_portlet-bgImg">
</div>
<div class="third_ftsearch_portlet_content_div">
	<div>
	<table class="third_ftsearch_portlet_content_table">
	        <!-- 搜索框  -->
		    <tr class="firstTr">
		        <td style="width: 55px;">
		            <div class="third_ftsearch_portlet_search_icon">
			            <span class="el-icon-search"></span>
			        </div>
		        </td>
		        <td>
		            <div class="third_ftsearch_portlet_search_left">
		                <input class="third_ftsearch_portlet_search_input" type="text" placeholder="${lfn:message('third-ftsearch:third.ftsearch.portlet.search.tip')}" name="ftsearch_portle_search_keword" onkeydown="if (event.keyCode == 13 && this.value !='') thirdFtsearchPortletBackSearch(this);"/ >
		            </div>
		        </td>
		        <td style="width: 130px;">
		            <span class="third_ftsearch_portlet_search_button" onclick="thirdFtsearchPortletBackSearch(this)">${lfn:message('third-ftsearch:third.ftsearch.portlet.search')}</span>
		        </td>
		    </tr>	    
		    <tr>
			    <td colspan="3">
				    <!-- 搜索热词 -->
				    <div class="third_ftsearch_portlet_search_hot_div" style="display: none;">
				    
				    </div>
			    </td>
		    </tr>
	</table>
	</div>
</div>

