/**
 * 学员面板
 */
define(["mui/createUtils"], function(createUtils) {
  return {
    icon: "muis-my-schedule",
    text: "学员",
    createView: function() {
      return {
        header: {
          tmpl: createUtils.createTemplate(
            null,
            {
              dojoType: "sys/mportal/module/mobile/containers/Header"
            },
            createUtils.createTemplate(null, {
              dojoType: "sys/mportal/module/mobile/elements/ConciseStatistical",
              dojoMixins: "kms/lservice/mobile/module/js/student/Statistical"
            })
          )
        },
        cards: [
          {
            contents: [
              {
                tmpl: createUtils.createTemplate(null, {
                  dojoType: "sys/mportal/module/mobile/elements/GridFunctional",
                  dojoMixins:
                    "kms/lservice/mobile/module/js/student/GridFunctional"
                })
              }
            ]
          },
          {
            title: {text: "我的学习记录"},
            contents: [
              {
                tmpl: createUtils.createTemplate(null, {
                  dojoType: "sys/mportal/module/mobile/elements/Functional",
                  dojoMixins: "kms/lservice/mobile/module/js/student/Functional"
                })
              }
            ]
          },
          {
            title: {text: "我的学习任务"},
            contents: [
              {
                tmpl: createUtils.createTemplate(null, {
                  dojoType: "kms/lservice/mobile/module/js/student/Task"
                })
              }
            ]
          }
        ]
      }
    }
  }
})
