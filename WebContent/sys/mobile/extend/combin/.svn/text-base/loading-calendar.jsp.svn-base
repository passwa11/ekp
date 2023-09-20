<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<div class="muiCalendarContainer">
	<div class="muiCalendarHead">
		<div class="muiCalendarMonthContent">
			<span class="mui-back mui"></span>
			<span class="muiCalendarMonth">1970年1月</span>
			<span class="mui-forward mui"></span>
		</div>
	</div>
	<table class="muiCalendarTable">
		<thead>
			<tr class="muiCalendarWeek">
				<th><span>日</span></th><th><span>一</span></th><th><span>二</span></th><th><span>三</span></th><th><span>四</span></th><th><span>五</span></th><th><span>六</span></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="var1" begin="0" end="5">
				<tr>
					<c:forEach var="var2" begin="0" end="6">
						<td>
							<span class="muiCalendarDate <c:if test="${ var2 == '1' or var2 == '7' }">muiCalendarHoliday</c:if> <c:if test="${ var1 * 7 + var2 > 30 }">muiCalendarDate muiCalendarDateNext</c:if>">
								${ (var1 * 7 + var2) % 30 + 1 }
							</span>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="muiCalendarBottom">
		<div class="muiCalendarOpt" style="text-align: left;">
			<div class="muiCalendarBottomLeft" data-dojo-attach-point="dateNode">01</div>
			<div class="muiCalendarBottomCenter">
				<div data-dojo-attach-point="y2mNode">1970.01</div>
				<div class="muiCalendarBottomInfo">
					<div data-dojo-attach-point="weekNode">星期二</div>
					<div></div>
				</div>
			</div>
			<div class="muiCalendarBottomRight"></div>
		</div>
		<div class="muiListNoData" style="height: 30rem;padding-top: 10rem;">
			<div class="loader loader-rounded">
			<div class="loader-bounce1"></div>
			<div class="loader-bounce2"></div>
			<div class="loader-bounce3"></div>
		</div>
		</div>
	</div>
</div>