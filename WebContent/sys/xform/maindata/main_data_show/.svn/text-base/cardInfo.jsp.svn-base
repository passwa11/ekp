<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="Stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/css/keydata.css"/>" />
<link rel="Stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/css/pinwheel.css"/>" />
<script type="text/javascript">
			window.onload = function() {
				var h  = document.getElementById("mui_keydata_card_div").scrollHeight;
				parent.postMessage(h, "*");
			}    
		</script>
<div id="mui_keydata_card_div" class="lui_keydata_card" style="width:280px;">
        <span class="lui_keydata_card_avatar"><img src="<c:url value='${icon}' />"></span>
        <div class="lui_keydata_card_content">
          <span class="lui_keydata_card_tag" style="background-color:${color}">${mainDataType }</span>
          <h4 class="lui_keydata_card_title">${title }</h4>
          <c:if test="${auth==true }">
          <ul class="lui_keydata_card_list">
          	  <c:forEach items="${showFieldsList }" var="field" varStatus="varStatus">
							<li>
								<span class="title"  title="${field[0] }">${field[0] }</span>
								<span class="txt"  title="${field[1] }">${field[1] }</span>
							</li>
				</c:forEach>
          </ul>
          </c:if>
          <c:if test="${auth==false }">
          		<p class="lui_keydata_card_nodata">对不起，您没有阅读此内容的权限，请联系管理员</p>
          </c:if>
        </div>
        <c:if test="${auth==true }">
	        <div class="lui_keydata_card_footer">
	        	<div class="lui_keydata_card_footer_item">
		          <a class="card_btn_more" href="${detailUrl }" target="_blank">查看详情</a>
	        	</div>
	        </div>
        </c:if>
</div>

