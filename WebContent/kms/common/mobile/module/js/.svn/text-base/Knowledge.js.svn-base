/**
 * 学员面板
 */
define(["mui/createUtils"], function(createUtils) {
  return {
    icon: "muis-my-schedule",
    text: "知识",
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
                dojoMixins: "kms/common/mobile/module/js/knowledge/Statistical"
              })
          )
        },
        cards: [
          {
        	title:{text:"知识应用快捷通道"},
            contents: [
              {
                tmpl: createUtils.createTemplate(null, {
                  dojoType: "sys/mportal/module/mobile/elements/GridFunctional",
                  dojoMixins:
                    "kms/common/mobile/module/js/knowledge/GridFunctional"
                })
              }
            ]
          },
          {
            title: {text: "我的知识指数"},
            contents: [
              {
                tmpl: createUtils.createTemplate(null, {
                  dojoType: "sys/mportal/module/mobile/elements/TabsChart",
                  dojoMixins: "kms/common/mobile/module/js/knowledge/Chart"
                })
              }
            ]
          }
        ]
      }
    }
  }
})
