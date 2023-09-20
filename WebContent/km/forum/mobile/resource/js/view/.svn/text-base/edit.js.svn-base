/**
 * 从edit.jsp移动过来，如果要查看历史变更，请查询edit.jsp历史
 */
define([
  "dojo/query",
  "dojo/topic",
  "mui/i18n/i18n!km-forum:kmForumConfig.mobile.word.warn",
  "mui/i18n/i18n!km-forum:kmForumPost.topicNotEmpty",
  "km/forum/mobile/resource/js/view/wordCheck",
  "dijit/registry",
  "mui/dialog/Tip"
], function (query, topic, msg, msg1, wordCheck, registry, Tip) {
  var docContent = null;
  topic.subscribe("/mui/form/valueChanged", function (obj, evt) {
    docContent = evt.value;
  });

  topic.subscribe("km/forum/statChanged", function (obj, val) {
    if ("fdIsAnonymous" == obj.property) {
      if ("1" == val) {
        query('[name="fdIsNotify"]')[0].value = "0";
        query("#fdNotifyTypeTR").style("display", "none");
        query("#fdIsOnlyViewTR").style("display", "none");
        query("#fdIsNotifyTr").style("display", "none");
        query('[name="fdIsOnlyView"]')[0].value = "0";
      } else {
        query('[name="fdIsNotify"]')[0].value = "1";
        query("#fdNotifyTypeTR").style("display", "");
        query("#fdIsOnlyViewTR").style("display", "");
        query("#fdIsNotifyTr").style("display", "");
      }
    }
    if ("fdIsNotify" == obj.property) {
      if ("0" == val) {
        query("#fdNotifyTypeTR").style("display", "none");
      } else {
        query("#fdNotifyTypeTR").style("display", "");
      }
    }
  });

  //提交
  window.commitMethod = function (commitType) {
    // 校验下
    var _validator = registry.byId("scrollView");
    if (!_validator.validate()) {
      return;
    }

    var flag = true;
    var subject = query('[name="docSubject"]')[0].value;
    docContent = CKEDITOR.instances['docContent'].getData()
    if (!docContent) {
      Tip.warn({
        text: msg1['kmForumPost.topicNotEmpty']
      })
      return
    }
    flag = wordCheck.forum_wordCheck(
      subject + " " + docContent,
      msg["kmForumConfig.mobile.word.warn"]
    );
    if (!flag) {
      return;
    }

    if (!query('[name="fdNotifyType"]')[0].value) {
      query('[name="fdIsNotify"]')[0].value = "0";
    }
    if ("save" == commitType) {
      Com_Submit(document.kmForumPostForm, commitType);
    } else {
      Com_Submit(document.kmForumPostForm, commitType);
    }
  };
});
