<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
	.statistics-container{
		background-color: #F8F8F8;
		padding: 16px 0;
	}
	.statistics-body {
		width: 100%;
		background: #FFFFFF;
		box-shadow: 0px 0px 6px 0px rgba(0,0,0,0.06);
		border-radius: 4px;
		padding: 24px;

		top: 0;
		height: 64px;
		overflow: hidden;
		min-width:792px;
		margin-bottom: 10px;
	}
	.statistics-content-center {
		position: relative;
		height: 100%;
		overflow: hidden;
	}
	.statistics-content {
		float: left;
		padding: 0 24px;
		position: relative;
		min-width: 175px;
	}
	.statistics-content::after{
		content: '';
		position: absolute;
		top: 10px;
		right: 0;
		width: 1px;
		height: 40px;
		background-color: #eeeeee;

	}
	div.statistics-content-left {
		width: 24px;
		height: 64px;
		float: left;
	}
	div.statistics-content-right {
		position: absolute;
		display: inline-block;
		width: 24px;
		height: 64px;
		right: 16px;
		top: 36px;
	}
	.statistics-content-value-num {
		display: inline-block;
		font-family: DINAlternate-Bold;
		font-size: 28px;
		color: #4285F4;
	}
	.statistics-content-value-unit,.statistics-content-value-percent{
		display: inline-block;
		font-size: 14px;
		color: #4285F4;
		margin-left: 4px;
	}
	.statistics-content-name p{
		font-size: 14px;
		color: #333333;
		margin-bottom: 4px;
	}
	.statistics-content-value p{
		width: 100px;
		height: 28px;
		font-family: DINAlternate-Bold;
		font-size: 28px;
		color: #4285F4;
		line-height: 28px;
		font-weight: 700;
		text-align: left;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
</style>

<div class="statistics-container">
	<div class="statistics-body" style="display: none;">
		<div class="statistics-content-left">
		</div>
		<div class="statistics-content-center">
<c:if test="${fn:length(statisticsInfo) > 0 }">
	<c:forEach items="${statisticsInfo}" var="statisticsEntry" varStatus="vStatus">
			<div class="statistics-content">
				<div class="statistics-content-name"><p>${statisticsEntry.name}</p></div>
				<div class="statistics-content-value"><p>0</p></div>
			</div>
	</c:forEach>
</c:if>
		</div>
		<div class="statistics-content-right">
		</div>
	</div>
</div>


<script>
	Com_IncludeFile('calendar.js');

	seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
		$(".statistics-content-left").on("click",function (){
			$.each($(".statistics-content"), function(index, value) {
				if("block" == $(value).css("display")){
					if(index > 0){
						index--;
						$("#statistics-content-"+index).css("display","block");
						return false;
					}
				}
			});
		});

		$(".statistics-content-right").on("click",function (){
			$(".statistics-content").each(function(index, value) {
				if("block" == $(value).css("display")){
					var maxIndex = $(".statistics-content").length - 1 ;
					var firstTop = $("#statistics-content-0").offset().top;
					var endTop = $("#statistics-content-"+maxIndex).offset().top;
					if(firstTop < endTop - 40){
						$("#statistics-content-"+index).css("display","none");
					}
					return false;
				}
			});
		});
	});
</script>