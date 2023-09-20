<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

var onc = row['fdNotesContent'];
var nc = encodeHTML(row['fdNotesContent']);
{$
	<div class="notes_reply_infos" id="{%row['fdId']%}">
		<dl class="notes_record_dl">
			<dd class="notes_record_msg">
				
				<div class="txt" id="notes_txt_{%row['fdId']%}">
					<%--<xmp class="notes_record_content" title="{%nc%}">{%onc%}</xmp>--%>
					<div class="notes_record_content" title="{%row['fdNotesContent']%}">{%row['fdNotesContent']%}</div>
					<div class='notes_txt'>
						<div class='notes_time'>{%row['docCreateTime']%}</div>
						<c:set var="fdItem" value="{%row['fdId']%}"/>
						<c:if test="${param.notes_editvalidateAuth}">
							<div class='notes_cancel_share' onclick="cancel_notes('${fdItem}')">
			$}				
							if(row['isShare']=='true'){
			{$					
								${lfn:message('kms-common:kmsCommon.cancelShare')}
			$}					
							}		
			{$				</div>
					   </c:if>
						<div  class= 'button_notes'>
						<c:if test="${param.notes_editvalidateAuth}">
							<div class='edit_notes' onclick="edit_notes('${fdItem}')">
								${lfn:message('kms-common:kmsCommon.edit')}
							</div>
						</c:if>
						<c:if test="${param.notes_deletevalidateAuth}">
							<div class='delete_notes' onclick="notes_delete('${fdItem}')">
								${lfn:message('button.delete')}
							</div>
						</c:if>
					</div>
					</div>
				</div>
			</dd>
			
		</dl>
	</div>
$}