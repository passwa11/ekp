define(["mui/createUtils"], function(createUtils) {
	return {
		createView : function() {
			return {
				header: {
					tmpl: createUtils.createTemplate(null, {
						dojoType: "sys/mportal/module/mobile/containers/Header",
						dojoProps: {
							userName : dojoConfig.CurrentUserName,
							showGreet : true,
							href : '/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=view&fdId=' + dojoConfig.CurrentUserId,
							showImg : true
						}
					},
					createUtils.createTemplate(null, {
						dojoType: "sys/mportal/module/mobile/elements/Statistical",
						dojoMixins: "sys/person/mobile/js/Statistical"
					}))
				},
				cards: [{
					contents: [{
						tmpl: createUtils.createTemplate(null, {
							dojoType: 'sys/mportal/module/mobile/elements/Functional',
							dojoProps: {
				            	url : '/sys/person/sys_person_module_data/sysPersonModuleData.do?method=getListData'
				            }
				        })
					}]
				}]
			}
		}
	}
})

/*cards: [
          {
            contents: [
              {
                tmpl: createUtils.createTemplate(null, {
                  dojoType: "sys/mportal/module/mobile/elements/Functional",
                  dojoMixins:
                    "kms/lservice/mobile/module/js/student/FunctionalGrid"
                })
              }
            ]
          },
          {
            title: {text: "我的学习记录", href: ""},
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
            title: {text: "已处理公文"},
            contents: [
              {
                url:
                  "/kms/knowledge/mobile/mportal/kmsKnowledgeBaseDoc_simplelist.js?rowsize=6"
              }
            ]
          }
        ]*/