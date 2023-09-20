<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
    </template:replace>
    <template:replace name="content">
        <c:if test="${version eq 'fixed'}">
            <c:import url="edit_fixed.jsp"></c:import>
        </c:if>
        <c:if test="${version eq 'custom'}">
            <c:import url="edit_custom.jsp"></c:import>
        </c:if>
    </template:replace>
</template:include>