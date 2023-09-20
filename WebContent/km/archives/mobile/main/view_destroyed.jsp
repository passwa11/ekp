<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="mobile.view">
    <template:replace name="head">
        <style type="text/css">
        	.lui_form_body {
        		background-color:#fff !important;
        	}
            .lui-destroy {
				padding:10px;
				width: 184px;
				height: 55px;
				line-height: 55px;
				text-align: center;
				color: #f56b6b;
				font-size: 28px;
				font-weight: normal;
				font-family: "Microsoft Yahei";
				text-transform: uppercase;
				background-image: url('./icon-destroy.png');
				background-repeat: no-repeat;
				background-position: 0 0;
				background-size: 100% auto;
				display: block;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
				transform: rotate(-15deg);
				-webkit-transform: rotate(-15deg);
				-moz-transform: rotate(-15deg);
				-o-transform: rotate(-15deg);
			}	
        </style>
    </template:replace>
    <template:replace name="title">
        <c:out value="${kmArchivesMainForm.docSubject} - " />
        <c:out value="${ lfn:message('km-archives:table.kmArchivesMain') }" />
    </template:replace>
    <template:replace name="content">
    	<center>
	        <div class="lui-destroy">
		    	<span>${ lfn:message('km-archives:kmArchivesMain.destroy.already') }</span>
		    </div>
	    </center>
    </template:replace>

</template:include>