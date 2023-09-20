<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.edit">
    <template:replace name="content">
    	<style>
    		.selectModule{
    			margin-top: 20px;
    		}
    		.selectModule tr, .selectModule td{
    		    border: 1px solid black;
    		}
    		.selectModule td{
    			width: 180px;
    			height: 40px;
    			padding-left: 10px;
    		}
    	</style>
    	<center>
	    	<table class="selectModule">
	    		<tr>
			    	<c:forEach var="data" items="${moduleList}" varStatus="varStatus">
						<td>
							<input type="radio" name="fdModulePath" value="${data.fdModulePath}"/>
							<span id="${data.fdModulePath}">
								<c:if test="${data.fdModuleName == null}">
									${data.fdModulePath}
								</c:if>
								<c:if test="${data.fdModuleName != null}">
									${data.fdModuleName}
								</c:if>
							</span>
						</td>
						<c:if test="${varStatus.index % 4 ==3 }">
				</tr>
				<tr>
						</c:if>
			    	</c:forEach>
			    </tr>
	    	</table>
    	</center>
    </template:replace>
</template:include>