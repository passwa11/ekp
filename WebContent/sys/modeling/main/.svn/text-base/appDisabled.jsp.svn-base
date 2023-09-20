<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${lfn:message('sys-modeling-main:application.disabled.title')}</title>
    <style>
        .bg-pc {
            width: 100%;
            height: 50%;
            background: url(resources/images/application-disabled-PC.png) no-repeat center;
            background: url(resources/images/application-disabled-PC@2x.png) no-repeat center;
            background-size: 399px 188px;
        }
        /* 从移动端action访问时，路径多一层mobile */
        .bg-mobile {
            width: 100%;
            height: 50%;
            background: url(../resources/images/application-disabled-mobile.png) no-repeat center;
            background: url(../resources/images/application-disabled-mobile@2x.png) no-repeat center;
            background-size: contain;
        }
    </style>
</head>
<body>
    <div class="${param.t =='m' ? 'bg-mobile' : 'bg-pc'}"></div>
</body>
</html>
