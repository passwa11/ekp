<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
	<div class="notes_reply_infos" id="{%row['fdId']%}">
		<dl class="notes_record_dl">
			<dd class="notes_record_msg">
				
				<div class="txt" id="notes_txt_{%row['fdId']%}">
					<div class="notes_record_content" title="{%row['fdNotesContent']%}">{%row['fdNotesContent']%}</div>
					<div class='notes_txt'>
						<div class='notes_recorder'>
							<ui:person personId="{%row['docCreator.fdId']%}" personName="{%row['docCreator.fdName']%}">
							</ui:person>
						</div>
						<div class='notes_time'>{%row['docCreateTime']%}</div>
						<c:set var="fdItem" value="{%row['fdId']%}"/>
						<div class='view_notes' onclick="notes_view('${fdItem}')">
							${lfn:message('kms-common:kmsCommon.view')}
						</div>
						<div class='notes_boder'></div>
					</div>
				</div>
			</dd>
			
		</dl>
	</div>
$}

{$


$}
