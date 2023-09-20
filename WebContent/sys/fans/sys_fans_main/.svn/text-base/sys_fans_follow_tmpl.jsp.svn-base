<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var rela = data.relation;
var userId = data.userId;

var attentModelName = $("[name='attentModelName']").val();
var fansModelName = $("[name='fansModelName']").val();
var isFollowPerson = $("[name='isFollowPerson']").val();
if(isFollowPerson!=null&&"false"==isFollowPerson){
	attentModelName = attentModelName;
}else{
	attentModelName = fansModelName;
}
	  if(rela == 0){
          {$
           <a class="sys_zone_btn_focus icon_focusAdd" attent-model-name="{%attentModelName%}" 
              fans-model-name="{%fansModelName%}"
                 is-follow-person="{%isFollowPerson%}" fans-action-type="unfollowed" fans-action-id="{%userId%}" 
                 href="javascript:void(0);" >
                 <span><i></i><span>${lfn:message('sys-zone:sysZonePerson.cared1')}</span></span>
              </a>
           $}
         }else if(rela == 1){
           {$
           <a id="sys_zone_btn_focus" class="sys_zone_btn_focus icon_focused">
                 <span><i></i><span>${lfn:message('sys-zone:sysZonePerson.cancelCared1')}</span></span><em attent-model-name="{%attentModelName%}" 
              fans-model-name="{%fansModelName%}"
                 is-follow-person="{%isFollowPerson%}" fans-action-type="followed" fans-action-id="{%userId%}" 
                 href="javascript:void(0);" >${lfn:message('sys-zone:sysZonePerson.cancelCared')}</em>
              </a>
           $}
         }else if(rela == 2){
           {$
           <a class="sys_zone_btn_focus icon_unfocus" attent-model-name="{%attentModelName%}" 
              fans-model-name="{%fansModelName%}"
                 is-follow-person="{%isFollowPerson%}" fans-action-type="unfollowed" fans-action-id="{%userId%}" 
                 href="javascript:void(0);" >
                 <span><i></i><span>${lfn:message('sys-zone:sysZonePerson.cared1')}</span></span>
              </a>
           $}
         }else if(rela == 3){
           {$
           <a class="sys_zone_btn_focus icon_eachFocus">
                 <span><i></i><span>${lfn:message('sys-zone:sysZonePerson.follow.each')}</span></span><em attent-model-name="{%attentModelName%}" 
              fans-model-name="{%fansModelName%}"
                 is-follow-person="{%isFollowPerson%}" fans-action-type="followed" fans-action-id="{%userId%}" 
                 href="javascript:void(0);" >${lfn:message('sys-zone:sysZonePerson.cancelCared')}</em>
              </a>
           $}
         }

