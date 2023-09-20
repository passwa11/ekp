define(["mui/createUtils", "mui/i18n/i18n!km-archives:mobile"], function(createUtils, msg) {
  return {
    createView: function() {
      return {
        header: {
          tmpl: createUtils.createTemplate(
            null,
            {
              dojoType: "sys/mportal/module/mobile/containers/Header",
              dojoProps: {
                userName: dojoConfig.CurrentUserName
              }
            },
            createUtils.createTemplate(null, {
              dojoType: "sys/mportal/module/mobile/elements/Statistical",
              dojoMixins: "km/archives/mobile/module/js/MainView/Statistical"
            })
          )
        },
        cards: [
          {
            contents: [
              {
                tmpl: createUtils.createTemplate(null, {
                  dojoType: "sys/mportal/module/mobile/elements/Functional",
                  dojoMixins: "km/archives/mobile/module/js/MainView/Functional"
                })
              }
            ]
          },
          {
              title: {text: msg['mobile.handleArchives']},
              contents: [
                         {
                           tmpl: createUtils.createTemplate(null, {
                             dojoType: "sys/mportal/module/mobile/elements/TabsChart",
                             dojoMixins: "km/archives/mobile/module/js/MainView/TabsChart"
                           })
                         }
              ]
          }
        ]
      }
    }
  }
})
