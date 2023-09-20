<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="select_view">
	<div class="select_placeholder_view">
		<i class="select_group"></i>
		<c:set var="groupText" value=""></c:set>
		<c:forEach items="${fdGroups}" var="groupItem">
			<c:if test="${groupItem.value eq fdDefaultGroup}">
				<c:set var="groupText" value="${groupItem.text}" scope="page"></c:set>
			</c:if>
		</c:forEach>
		<div class="select_placeholder_text" title="${groupText}">
			${groupText}
			</div>
		<i class="close"></i>
	</div>
	<div class="select_content_view">
		<ul class="select_result_view">
			<c:forEach items="${fdGroups}" var="groupItem">
				<li data-value="${groupItem.value}" onclick="fdGroupChange(this)" title="${groupItem.text}">${groupItem.text}</li>
			</c:forEach>
		</ul>
	</div>
</div>
<script>
	seajs.use([ 'lui/topic','lui/jquery'],function(topic,$) {

		$(".select_placeholder_view").on("click",function (e){
			e.stopPropagation();
			$('.select_content_view').toggleClass("active");
			$('.select_result_view').scrollTop(0);
			$('.close').toggleClass("open");
		})
		$(window).on("click",function () {
			$(".select_content_view").removeClass("active");
			$('.close').removeClass("open");
		})
		$(".select_result_view").on("click","li",function(e){
			if($(this).data("value")){
				$('.select_placeholder_text').text($(this).html());
				$('.select_placeholder_text').attr("title",$(this).html())
			}
			$(".select_content_view").removeClass("active");
			$('.close').toggleClass("open");
		}).on("mouseover", "li", function() {
			$(".select_result_view").find("li").removeClass("status_hover");
			$(this).addClass("status_hover");
		});
	})
</script>