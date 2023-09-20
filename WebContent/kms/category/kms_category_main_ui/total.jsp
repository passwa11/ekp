<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
[ 
	<kmss:ifModuleExist path="/kms/multidoc/">
	{count_url:'/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=countByAuthCheck&orderby=docPublishTime&ordertype=down&docCategoryId=&q.type=multidoc&q.template=1'},
	</kmss:ifModuleExist>
	<kmss:ifModuleExist path="/kms/wiki/">
	{count_url:'/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=countByAuthCheck&orderby=docPublishTime&ordertype=down&docCategoryId=&q.type=wiki&q.template=2'},
	</kmss:ifModuleExist>
	<kmss:ifModuleExist path="/kms/kem/">
	{count_url:'/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=countByAuthCheck&orderby=docPublishTime&ordertype=down&docCategoryId=&q.type=kem&q.kem=kem&q.template=3'}
	</kmss:ifModuleExist>
]
