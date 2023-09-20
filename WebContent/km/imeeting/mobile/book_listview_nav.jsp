<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{
		url : '/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=list&q.mydoc=waitExam',
		text: '待审核'
	},
	{
		url : '/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=list&q.mydoc=all&q.status=yes',
		text: '已通过'
	},
	{
		url : '/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=list&q.mydoc=all&q.status=no',
		text : '已拒绝'
	}
]