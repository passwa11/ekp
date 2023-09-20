define([
  "dojo/_base/declare",
  "dojox/mobile/View",
  "dojo/query",
  "dojo/touch",
  "dojo/_base/lang",
  "dojo/on",
  "mui/i18n/i18n!sys-lbpmservice",
  "dojo/request",
  "./signatrueHtml"
], function(
  declare,
  View,
  query,
  touch,
  lang,
  on,
  msg,
  request,
  signatrueHtml
) {
  return declare(
    "sys.lbpmservice.mobile.signature.SignatrueHandlerView",
    [View],
    {
      descriptionDiv: null,

      buttonDiv: null,

      startup: function() {
        this.inherited(arguments)
        this.createBtn()
        this.bindEvents()
      },

      createBtn: function() {
        var obj = query(this.buttonDiv)
        var html = '<div class="handingWay" id="signature">'
        html +=
          '<div class="iconArea"><i class="mui mui-signature mui-sign"></i></div><span class="iconTitle">' +
          msg["mui.lbpmNode.signature"] +
          "</span></div>"

        // 提交拦截
        signatrueHtml.confirm(this.fdKey, this.fdModelId, this.fdModelName)
        this.initialSignature(function(res) {
          var results = eval("(" + res.responseText + ")")
          if (results.length > 0) {
            obj.append(html)
            signatrueHtml.dialogSignatureList("signature", res.responseText)
            setTimeout(function() {
              for (var i = 0; i < results.length; i++) {
                if (results[i].fdIsDefault && results[i].fdIsFreeSign) {
                  if (
                    lbpm &&
                    lbpm.allMyProcessorInfoObj &&
                    lbpm.allMyProcessorInfoObj.length > 0
                  ) {
                    signatrueHtml.showDefaultSignature(results[i].id)
                  }
                }
              }
            }, 500)
          }
        })
      },
      initialSignature: function(callback) {
        var url =
          Com_Parameter.ContextPath +
          "km/signature/km_signature_main/kmSignatureMain.do?method=getSignatureList"
        var responseText = ""
        request.get(url, {handleAs: "test"}).then(function(responseText) {
          responseText = responseText ? decodeURIComponent(responseText) : null
          callback &&
            callback({
              responseText: responseText
            })
        })
      },

      bindEvents: function() {
        lbpm.events.addListener(
          lbpm.constant.EVENT_validateMustSignYourSuggestion,
          function() {
            var imageDiv = query(".auditNoteHandlerImgUl")
            return imageDiv.children().length > 0
          }
        )
        on(
          query(this.descriptionDiv).parent()[0],
          on.selector("#imgUl li .btn_canncel_img", touch.press),
          lang.hitch(this, this.auditNoteViewDelete)
        )
        this.inherited(arguments)
      }
    }
  )
})
