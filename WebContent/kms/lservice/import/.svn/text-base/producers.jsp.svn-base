<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.kms.lservice.util.CustomerUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<%
	String customerName = StringEscapeUtils.escapeJavaScript(request.getParameter("modelName"));
	String customerId = StringEscapeUtils.escapeJavaScript(request.getParameter("modelId"));

	List<Map<String, String>> producers = CustomerUtil.getProducersByCustomerName(customerName);

	for (Map<String, String> producer : producers) {

		String jsp = producer.get("jsp");
		String modelName = producer.get("modelName");

		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);

		String url = dict.getUrl();

		String title = ResourceUtil.getString(dict.getMessageKey());
%>

<script>
	seajs.use('kms/lservice/import/producers.js', function(producers) {
		producers.initProducer("<%=modelName%>","<%=url%>","<%=title%>","<%=customerName%>","<%=customerId%>");
	});
</script>

<c:import url="<%=jsp%>" charEncoding="UTF-8">

	<c:param name="modelName" value="${ param.modelName}"></c:param>
	<c:param name="modelId" value="${param.modelId }"></c:param>

</c:import>

<%
	}
%>
