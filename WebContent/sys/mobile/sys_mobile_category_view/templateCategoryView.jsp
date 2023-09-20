<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include compatibleMode="true" ref="mobile.edit">
    <template:replace name="head">
    </template:replace>
    <template:replace name="content">
        <c:if test="${isSimple == true}">
            <div data-dojo-type="sys/mobile/sys_mobile_category_view/js/TemplateCategoryView" data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin${mixins}"
                 data-dojo-props="${props}"></div>
        </c:if>
        <c:if test="${isSimple == false}">
            <div data-dojo-type="sys/mobile/sys_mobile_category_view/js/TemplateCategoryView" data-dojo-mixins="mui/syscategory/SysCategoryMixin${mixins}"
                 data-dojo-props="${props}"></div>
        </c:if>
    </template:replace>
</template:include>