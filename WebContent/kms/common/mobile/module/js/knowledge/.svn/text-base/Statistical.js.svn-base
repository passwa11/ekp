define([
  "dojo/_base/declare",
  "sys/mportal/module/mobile/containers/header/CardMixin"
], function(declare, CardMixin) {
  return declare("", [CardMixin], {
    maxRow: 2,
    datas: {
      menus: [
        [
          {
            text: "积分",
            countUrl:
              "/kms/lservice/Lservice.do?method=learnInfo&modelName=com.landray.kmss.kms.integral.model.KmsIntegralPersonTotal.fdTotalScore"
          },
          {
            text: "财富",
            countUrl:
              "/kms/lservice/Lservice.do?method=learnInfo&modelName=com.landray.kmss.kms.integral.model.KmsIntegralPersonTotal.fdTotalRiches"
          }
        ]
      ]
    }
  })
})
