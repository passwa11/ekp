/**
 * 导师面板
 */
define(["mui/createUtils"], function(createUtils) {
  return {
    icon: "muis-cluster",
    text: "导师",
    createView: function() {
      return {
        header: {
          tmpl: createUtils.createTemplate(
            null,
            {
              dojoType: "sys/mportal/module/mobile/containers/Header",
              dojoProps: {
                address: "!{userName}老师"
              }
            },
            createUtils.createTemplate(null, {
              dojoType: "sys/mportal/module/mobile/elements/ConciseStatistical",
              dojoMixins: "kms/lservice/mobile/module/js/teacher/Statistical"
            })
          )
        },
        cards: [
          {
            title: {text: "教学跟进", href: ""},
            contents: [
              {
                tmpl: createUtils.createTemplate(null, {
                  dojoType: "sys/mportal/module/mobile/elements/Functional",
                  dojoMixins: "kms/lservice/mobile/module/js/teacher/Functional"
                })
              }
            ]
          }
        ]
      }
    }
  }
})
