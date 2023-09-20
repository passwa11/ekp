<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
	<style type="text/css">
		.muiCheckBoxWrap>.muiCheckItem  {
			padding-right: 0!important;
		}
	</style>
</head>
<div data-dojo-type="mui/table/ScrollableHContainer">
	<div data-dojo-type="mui/table/ScrollableHView">
		<table class="detailTableNormal" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="detailTableNormalTd">
					<table class="muiNormal selectedArchTable" width="100%" border="0" cellspacing="0" cellpadding="0"
						data-dojo-type="km/archives/mobile/resource/js/ArchivesTable">
						<tr>
							<td>${lfn:message('page.serial')}</td>
							<td> ${lfn:message('km-archives:kmArchivesMain.docSubject')}</td>
							<td> ${lfn:message('km-archives:kmArchivesMain.docTemplate')}</td>
							<td> ${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}</td>
							<td> ${lfn:message('km-archives:kmArchivesDetails.fdAuthorityRange')}</td>
							<td> ${lfn:message('km-archives:kmArchivesDetails.fdReturnDate')}</td>
							<td> ${lfn:message('km-archives:kmArchivesDetails.operation')}</td>
							
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>

<script>

	require(['dojo/topic', 'dojo/ready'], function(topic, ready){
		ready(function(){
			
			var archs = [
				<c:forEach items="${kmArchivesBorrowForm.fdBorrowDetail_Form}" var="fdBorrowDetail_FormItem" varStatus="vstatus">
					{
						fdId: '${fdBorrowDetail_FormItem.fdArchives.fdId }',
						fdBorrowerId: '${fdBorrowDetail_FormItem.fdBorrowerId}',
						fdStatus: '${fdBorrowDetail_FormItem.fdStatus}',
						title: '${fdBorrowDetail_FormItem.fdArchives.docSubject }',
						categoryName: '${fdBorrowDetail_FormItem.fdArchives.docTemplate.fdName }',
						fdValidityDate: '${fdBorrowDetail_FormItem.fdArchives.fdValidityDate }'!=''?'<kmss:showDate value="${fdBorrowDetail_FormItem.fdArchives.fdValidityDate }" type="date" />':'<bean:message bundle="km-archives" key="mui.kmArchivesMain.fdValidityDate.forever"/>',
						fdAuthorityRange: '${fdBorrowDetail_FormItem.fdAuthorityRange}',
						fdReturnDate: '${fdBorrowDetail_FormItem.fdReturnDate}'
					},
				</c:forEach>
             ];
			
			topic.publish('km/archives/selectedarch/init', archs);
			
		});
	});

</script>
