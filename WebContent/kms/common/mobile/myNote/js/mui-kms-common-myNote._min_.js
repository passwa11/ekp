require({cache:{"kms/common/mobile/myNote/js/Loading":function(){define(["dojo/_base/declare","mui/dialog/Tip"],function(c,b){var a=c("mui.kms.common.Loading",null,{loading_working:false,loading_processing:null,loading_error:function(d){b.fail({text:d})},loading_success:function(d){b.success({text:d})},showLoading:function(){window._alert=window.alert;this.loading_processing=b.processing();if(this.loading_working){return false}this.loading_working=true;window.alert=this.loading_Alert;this.loading_processing.show();return true},hideLoading:function(){this.loading_working=false;window.alert=window._alert;this.loading_processing.hide(false)}});return a})},"kms/common/mobile/myNote/js/item/MyNoteCourseItem":function(){define(["dojo/_base/declare","dojo/dom-construct","dojo/dom-class","dojo/dom-style","mui/util","dojo/html","dojo/dom","dojo/on","dojo/_base/array","mui/i18n/_i18n!kms/common/mobile/myNote/js/mui-kms-common-myNote","dojox/mobile/_ItemBase"],function(d,b,k,g,f,e,c,i,h,a,j){return d("kms.common.myNoteCourse.list.item",[j],{fdId:"",fdModelName:"",docCreateTime:"",courseName:"",isDelete:"",noteNum:"",baseClass:"muiMyNoteCourseItem",buildRendering:function(){this.inherited(arguments);this.biludItem();var l=this;i(this.domNode,"click",function(m){l.onClick(m)})},biludItem:function(){b.create("div",{className:"courseName",innerHTML:this.courseName},this.domNode);var m=b.create("div",{className:"bottom"},this.domNode);b.create("div",{className:"time",innerHTML:this.docCreateTime},m);var l=b.create("div",{className:"right"},m);b.create("div",{className:"num",innerHTML:this.noteNum+a["mobile.myNote.msg.unit"]},l);b.create("span",{className:"text",innerHTML:a["mobile.myNote.msg.note"]},l);b.create("div",{className:"hr"},this.domNode)},onClick:function(){var l="/kms/common/mobile/myNote/courseNotes.jsp?";l+="fdModelId="+this.fdId+"&fdModelName="+this.fdModelName;l+="&courseName="+encodeURIComponent(this.courseName);var l=f.formatUrl(l,true);window.open(l,"_self")},_setLabelAttr:function(l){if(l){this._set("label",l)}}})})},"kms/common/mobile/myNote/js/MyNoteCourseNoteListMixin":function(){define(["dojo/_base/declare","dojo/_base/array","dojo/topic","./item/MyNoteCourseNoteItem"],function(b,d,a,c){return b("kms.common.myNoteCourseNote.list.mixin",null,{lazy:false,rowsize:100000,itemRenderer:c})})},"kms/common/mobile/myNote/js/item/MyNoteCourseNoteItem":function(){define(["dojo/_base/declare","dojo/dom-construct","dojo/dom-class","dojo/dom-style","mui/util","dojo/html","dojo/dom","dojo/on","dojo/_base/array","dojo/request","mui/dialog/Confirm","mui/i18n/_i18n!kms/common/mobile/myNote/js/mui-kms-common-myNote","../Loading","dojox/mobile/_ItemBase"],function(e,b,n,h,g,f,d,j,i,c,l,a,k,m){return e("kms.common.myNoteCourseNote.list.item",[m,k],{fdId:"",docCreateTime:"",docCreateTime2:"","docCreator.fdName":"","docCreator.fdId":"",praiseCount:"",evalCount:"",fdCourse:"",isShare:"false",fdNotesContent:"",baseClass:"muiMyNoteCourseNoteItem",buildRendering:function(){this.inherited(arguments);this.biludItem();var o=this;j(this.domNode,"click",function(p){o.onClick(p)})},biludItem:function(){var z=b.create("div",{className:"top"},this.domNode);b.create("div",{className:"content",innerHTML:this.fdNotesContent},z);var u=b.create("div",{className:"second"},z);var A="/kms/common/mobile/myNote/image/share.png";if(!this.isShare||this.isShare=="false"){A="/kms/common/mobile/myNote/image/private.png"}A=g.formatUrl(A,true);b.create("img",{className:"shareImg",src:A},u);var t="/kms/common/mobile/myNote/image/time.svg";t=g.formatUrl(t,true);b.create("img",{className:"timeImg",src:t},u);b.create("div",{className:"time",innerHTML:this.docCreateTime2||this.docCreateTime},u);b.create("div",{className:"hr"},z);var o=b.create("div",{className:"bottom"},this.domNode);var s=b.create("div",{className:"evalContainer"},o);var v="/kms/common/mobile/myNote/image/eval.svg";v=g.formatUrl(v,true);b.create("img",{className:"evalImg",src:v},s);b.create("div",{className:"evalCount",innerHTML:this.evalCount},s);var r=b.create("div",{className:"praiseContainer"},o);var y="/kms/common/mobile/myNote/image/praise.svg";y=g.formatUrl(y,true);b.create("img",{className:"praiseImg",src:y},r);b.create("div",{className:"praiseCount",innerHTML:this.praiseCount},r);var p=b.create("div",{className:"right"},o);var w=b.create("div",{className:"deleteContainer"},p);var q="/kms/common/mobile/myNote/image/delete.svg";q=g.formatUrl(q,true);b.create("img",{className:"deleteImg",src:q},w);this.bottomDeleteNode=w;var x=this;j(this.bottomDeleteNode,"click",function(B){x.onDeleteClick(B)});j(s,"click",function(B){x.onEvalClick(B)})},onEvalClick:function(p){var o="/sys/evaluation/mobile/index.jsp?modelName=com.landray.kmss.kms.common.model.KmsCourseNotes&modelId="+this.fdId;o=g.formatUrl(o);window.open(o,"_self")},onDeleteClick:function(){var o=this;if(this.click_doing){return}this.click_doing=true;new l(a["mobile.myNote.comfirm.delete.content"],a["mobile.myNote.comfirm.delete.title"],function(p,q){if(p){o.startToDelete()}});setTimeout(function(){o.click_doing=false},100)},startToDelete:function(){var p="/kms/common/kms_notes/kmsCourseNotes.do?method=deleteByMobile&fdId="+this.fdId;p=g.formatUrl(p);var o=this;o.showLoading();var q=c.post(p,{data:{},handleAs:"json"});q.response.then(function(t){var u=t.data;o.hideLoading();if(u.success){o.loading_success(a["mobile.myNote.msg.deleteSuccess"]);var s=o.getParent();var r=s.getChildren();if(r.length<=1){s.reload()}o.destroyRecursive()}else{o.loading_error(u.errMsg)}},function(r){o.loading_error(r);console.log(r)})},onClick:function(){},_setLabelAttr:function(o){if(o){this._set("label",o)}}})})},"kms/common/mobile/myNote/js/MyNoteCourseListMixin":function(){define(["dojo/_base/declare","dojo/_base/array","dojo/topic","./item/MyNoteCourseItem"],function(c,d,b,a){return c("kms.common.myNoteCourse.list.mixin",null,{lazy:false,rowsize:10,itemRenderer:a,url:"/kms/common/kms_notes/kmsCourseNotes.do?method=getNoteCourses&mode=myNotes"})})}}});