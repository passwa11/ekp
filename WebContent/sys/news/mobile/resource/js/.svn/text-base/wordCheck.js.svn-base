/**
 * 从checkword_script.jsp移动过来，如果要查看历史变更，请查询从checkword_script.jsp历史
 */
define(["mui/util", "dojo/request", "mui/dialog/Tip"], function (
  util,
  request,
  Tip
) {
  return {
    form_wordCheck: function (content, warnText) {
      var result = true;
      var url = util.formatUrl(
        "/sys/profile/sysCommonSensitiveConfig.do?method=getIsHasSensitiveword"
      );
      var data = {
        formName: "sysNewsMainForm",
        content: encodeURIComponent(content),
      };
      request
        .post(url, {
          data: data,
          handleAs: "json",
          sync: true,
        })
        .then(
          function (data) {
            var json = eval(data);
            var flag = json.flag;
            var hasWarn = flag == true || flag == "true";
            if (hasWarn) {
              Tip.fail({ text: warnText, width: 200, height: 100 });
            }
            result = !hasWarn;
          },
          function (data) {
            result = true;
          }
        );
      return result;
    },
  };
});
