<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	seajs.use(['${KMSS_Parameter_ContextPath}kms/common/kms_comment/import/resource/comment.css']);
	Com_IncludeFile("tempComment.js","${KMSS_Parameter_ContextPath}kms/common/kms_comment/import/templ/","js",true);
	
	var commentAlert = {
			'comment_reply':'<bean:message key="kmsCommentMain.reply" bundle="kms-common" />',
			'comment_delete':'<bean:message key="button.delete"/>',
			'comment_publish':'<bean:message key="kmsCommentMain.publish" bundle="kms-common" />',
			'comment_r_s':'<bean:message key="kmsCommentMain.reply.success" bundle="kms-common" />',
			'comment_d_s':'<bean:message key="kmsCommentMain.delete.success" bundle="kms-common" />',
			'comment_c_write':'<bean:message key="kmsCommentMain.canwrite" bundle="kms-common" />',
			'comment_many':'<bean:message key="kmsCommentMain.toomany" bundle="kms-common" />',
			'comment_del_title': '<bean:message key="page.comfirmDelete"/>'
	};
</script>

<script type="text/javascript">

	function showComment(commentObj){
		var fdModelId = $(commentObj).find("input[name='commentModelId']").val();
		var fdModelName = $(commentObj).find("input[name='commentModelName']").val();
		var countId = $(commentObj).find("input[name='countId']").val();
		var iconId = $(commentObj).find("input[name='iconId']").val();
		
		var commentHtml = $("#comment_"+fdModelId+" #commentList").html();
		var __list = $("#comment_" + fdModelId);
		setTimeout(function(){
			if(__list.is(":hidden")){
				$(".kms_comment").hide();
				__list.slideDown('slow');
			}else{
				__list.slideUp("slow");
			}
		}, 300);
		
		var vt = "commentL_"+fdModelId;
		if(window[vt] == null)
			window[vt] = new Comment_opt("comment_"+fdModelId, countId, fdModelId, fdModelName,iconId);
		var positionId = 'kms_comment_box_' + fdModelId;
		setTimeout(function(){window[vt].__init__();}, 800);	

		if($.trim(commentHtml) == ""){
			window[vt].refreshComment(fdModelId,fdModelName);
		}else{
			//回复按钮
			window[vt].buildReplyBtn(fdModelId);
		}
	}

	
</script>

<script type="text/template" id="kms_comment_templ">
var commentList = data['commentList'];
for(var i = 0;i<commentList.length;i++){
	{$
		<div height="100%">
			<dl class="comment_record_dl">
				<dd class="comment_record_msg" id="{%commentList[i].fdId%}">
					<img src="{%commentList[i].docCommentatorImgUrl%}">
					<div class="img">
					</div>
					<div class="txt" id="comment_txt_{%commentList[i].fdId%}">
						<div class="comment_record_content">
						<ui:person personId="{%commentList[i].docCommentatorId%}" personName="{%commentList[i].docCommentatorName%}"></ui:person>
						$}
						if(!!commentList[i].docParentReplyerId){
							{$
							{%commentAlert.comment_reply%}&nbsp;<ui:person personId="{%commentList[i].docParentReplyerId%}" personName="{%commentList[i].docParentReplyerName%}"></ui:person>
							$}
						}
					{$：{%commentList[i].commentContent%}</div>
						<div class="comment_info">
							{%commentAlert.comment_publish%}：{%commentList[i].docCreateTime%}
							<div class="comment_reply" data-commentator-id="{%commentList[i].docCommentatorId%}" data-commentator-name="{%commentList[i].docCommentatorName%}"></div>
							$}
							if(Com_Parameter.CurrentUserId == commentList[i].docCommentatorId || "${param.commentAuth}" == 'true'){
							{$
							<div class="del" data-comment-id="{%commentList[i].fdId%}"></div>
							$}
							}
							{$
						</div>
					</div>
				</dd>
			</dl>
		</div>
	$}
}
</script>