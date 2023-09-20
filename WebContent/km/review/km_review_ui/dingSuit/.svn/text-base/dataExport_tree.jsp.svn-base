<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="renderer" content="webkit" />
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<style>
		html,body{
			height: 100%;
		}
	</style>
	<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
	<title>
		<template:block name="title" />
	</title>
	<template:block name="head" />
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/review/km_review_ui/dingSuit/css/tree.css?s_cache=${LUI_Cache}"/>
</head>
<body> 
	<script type="text/javascript">
		var LKSTree;
		Tree_IncludeCSSFile();
		<template:block name="content" /> 
	</script>
	<div id=treeDiv class="treediv" style="overflow: hidden;"></div>
	<script>generateTree();</script>
</body>
</html>