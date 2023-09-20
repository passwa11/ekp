<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/css/tic-jsPlumb.css"> 
 <!-- DEP -->
	   
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jquery-1.9.0-min.js"></script>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jquery-ui-1.9.2.min.js"></script>
		<%--
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/jquery.js"></script>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/jquery-ui/jquery.ui.js"></script>
		 --%>
		
		
		<!-- /DEP -->
		
		<!-- JS -->
		<!-- support lib for bezier stuff -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsBezier-0.4-min.js"></script>
		<!-- jsplumb util -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsPlumb-util-1.3.12-RC1.js"></script>
        <!-- base DOM adapter -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsPlumb-dom-adapter-1.3.12-RC1.js"></script>
		<!-- main jsplumb engine -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsPlumb-1.3.12-RC1.js"></script>
		<!-- connectors, endpoint and overlays  -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsPlumb-defaults-1.3.12-RC1.js"></script>
		<!-- state machine connectors -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsPlumb-connectors-statemachine-1.3.12-RC1.js"></script>
		<!-- SVG renderer -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsPlumb-renderers-svg-1.3.12-RC1.js"></script>
		<!-- canvas renderer -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsPlumb-renderers-canvas-1.3.12-RC1.js"></script>
		<!-- vml renderer -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jsPlumb-renderers-vml-1.3.12-RC1.js"></script>
        <!-- jquery jsPlumb adapter -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/jquery.jsPlumb-1.3.12-RC1.js"></script>
		<!-- /JS -->
		
		<!--  demo code -->
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/ticjs/tic-jsplumb-connectors.js"></script>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plumb/ticjs/tic-jsplumb-jquery.js"></script>
		
</head>
<body onunload="jsPlumb.unload();" data-demo-id="flowchartConnectorsDemo" data-library="jquery">

</body>
</html>