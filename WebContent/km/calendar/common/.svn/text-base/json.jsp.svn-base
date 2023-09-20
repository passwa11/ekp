<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{
	<c:if test="${not empty errcode && errcode != '0' }">
		"errcode" : "${ errcode }",
		"errmsg" : "${ errmsg }"
	</c:if>
	<c:if test="${empty errcode || errcode == '0' }">
		"errcode" : "0",
		"errmsg" : "ok",
		<template:block name="content" />
	</c:if>
}