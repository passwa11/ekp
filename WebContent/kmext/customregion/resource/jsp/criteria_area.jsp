<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- zhangqiang_kf:自定义所在国家省市 字段筛选器 --%>

<c:out  value="${varParams['sourceUrl']}"></c:out>
<list:cri-criterion title="${lfn:message('sys-ui:ui.criteria.number') }" multi="false" expand="false" key="${criterionAttrs['key']}">
	<list:varDef name="title"></list:varDef>
	<list:varDef name="selectBox">
	<list:box-select>
		<list:varDef name="select">
			<list:item-select cfg-message="请先选择成本中心租" 
							  cfg-defaultCountry="一级成本中心组"
							  cfg-defaultProvince="二级成本中心组" 
							  cfg-defaultCity="4" 
							  type="${LUI_ContextPath }/kmext/customregion/resource/jsp/criteria_area!ProvinceAndCitySelector">
			</list:item-select>
		</list:varDef>
	</list:box-select>
	</list:varDef>
</list:cri-criterion>