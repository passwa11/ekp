define([
  "dojo/query",
  "mui/dialog/Dialog",
  "dojo/_base/array",
  "dojo/topic",
  "mui/i18n/i18n!sys-lbpmservice",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom",
  "dojo/dom-style"
], function(query, Dialog, array, topic, msg, domConstruct, domClass, dom, domStyle) {
  //初使化签章
  var signatrueHtml = {
    dialogSignatureList: function(commonUsageObjName, usageContents) {
      var dialog = null, html = null
      query("#" + commonUsageObjName).on("click", function() {
        html = buildContentHtml(usageContents)
        setTimeout(function() {
          dialog = Dialog.element({
            element: html,
            showClass: "muiDialogSelect muiFormSelect",
            position: "bottom",
            scrollable: false,
            parseable: true,
            onDrawed: function() {
            	query(".muiRadioGroupPopList .muiCheckBoxContainer").forEach(function(rowNode, rowIndex, rowNodeArray) {
            	   var cbxNodeArray = query("input[type='checkbox'][name='_select_box_signatureObjName']",rowNode);
            	   if(cbxNodeArray.length>0){
            		   var node = cbxNodeArray[0];
                       //是否免密签名
                       var results = eval("(" + usageContents + ")")
                       for (var i = 0; i < results.length; i++) {
                         if ( results[i].id == query(node)[0].defaultValue && !results[i].fdIsFreeSign ) {
                           domClass.add(rowNode,"mui-signature-lock-row");
                           var lockHtml = '<div class="mui mui-signature mui-todo_lock" ></div>'
                           query(node).after(lockHtml);
                         }
                       }           		   
            	   }
            	})
            },
            callback: function() {
              dialog = null
            }
          })
        }, 300)
      })

      topic.subscribe("mui/form/checkbox/change", function(box, data) {
        if (data.name == "_select_box_signatureObjName") {
          if (dialog) dialog.hide()
          confirmSignature(data, null, true)
        }
      })
    },

    showDefaultSignature: function(signatureId) {
      var data = {
        value: signatureId,
        pwd: ""
      }
      confirmSignature(data, null, true)
    },

    // 移动自sysLbpmProcess_signature.jsp
    confirm: function(auditNoteFdId, modelId, modelName) {
      Com_Parameter.event["confirm"].push(function() {
        //流程提交生成附件信息
        var flag = false
        var fdAttIds = ""
        query("#signaturePicUL li").forEach(function(node) {
          var id = query(node)[0].id
          if (id) {
            if (fdAttIds) {
              fdAttIds += ";"
            }
            fdAttIds += id
          }
        })
        if (fdAttIds) {
          var fdKey = auditNoteFdId + "_qz"
          var fdModelId = modelId
          var fdModelName = modelName
          dojo.xhrPost({
            url:
              Com_Parameter.ContextPath +
              "km/signature/km_signature_main/kmSignatureMain.do?method=submitSignature",
            postData: {
              fdAttIds: fdAttIds,
              fdKey: fdKey,
              fdModelId: fdModelId,
              fdModelName: fdModelName
            },
            // TODO
            sync: true,
            load: function(data) {
              var results = eval("(" + data + ")")
              flag = results.flag
            },
            error: function(error) {
              alert(error)
            }
          })
        } else {
          flag = true
        }
        return flag
      })
    }
  }

  function buildContentHtml(usageContents) {
    var selectSignature = findSelectSignature()

    if (null == usageContents || usageContents.length == 0) {
      return "<p>" + msg["mui.operation.commonUsage.none"] + "</p>"
    }
    var html = '<ul class="muiRadioGroupPopList">'
    var temp =
      '<input type="checkbox" data-dojo-type="mui/form/CheckBox" name="_select_box_signatureObjName" value="!{value}" data-dojo-props="tag:\'li\',mul:false,text:\'!{text}\',checked:!{checked},pop:true">'
    var results = eval("(" + usageContents + ")")
    for (var i = 0; i < results.length; i++) {
      //显示选中标记
      var checked = false
      if (array.indexOf(selectSignature, results[i].id) != "-1") {
        checked = true
      }
      html += temp
        .replace("!{text}", results[i].name)
        .replace("!{value}", results[i].id)
        .replace("!{checked}", checked)
    }
    html += "</ul>"
    return "<div class='muiFormSelectElement'>" + html + "</div>"
  }

  function confirmSignature(data, dialog, flag) {
    dojo.xhrPost({
      url:
        Com_Parameter.ContextPath +
        "km/signature/km_signature_main/kmSignatureMain.do?method=confirmSignature",
      postData: {fdMainId: data.value, confirmPassword: data.pwd},
      load: function(res) {
        var results = eval("(" + res + ")")
        if ((results.flag == "0" || results.isFreeSign == false) && flag) {
          showSignaturePwd(data.value)
        }
        if (results.flag == "0" && !flag) {
          //密码不正确
          var warnMsg =
            '<div class="muiSignatureDialogWarningTip">' +
            msg["mui.signature.warn.msg"] +
            "</div>"
          if (query(".muiSignatureDialogWarningTip").length == 0)
            query("#signaturePwd").after(warnMsg)
          domClass.add(dom.byId("signaturePwd"), "has-warning")
        }
        if (results.flag == "1") {
          if (dialog) dialog.hide()
          if (array.indexOf(findSelectSignature(), data.value) == "-1")
            signatureImgShow(results.attId, data.value)
        }
      },
      error: function(error) {
        console.log(error);
      }
    })
  }

  //显示签章图片
  function signatureImgShow(fdAttId, signatureId) {
    if (null == fdAttId || fdAttId == "") {
      return
    }
    var imageUl = query("#signaturePicUL")
    var html =
      '<li id="' +
      fdAttId +
      '" signatureid="' +
      signatureId +
      '"><img width="70" height="70" src="' +
      Com_Parameter.ContextPath +
      "sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=" +
      fdAttId +
      '"/>'
    html +=
      '<span id="del_' + fdAttId + '" class="btn_canncel_img"></span></li>'
    // #60715 起草节点隐藏审批意见框
    if (
      lbpm.nowNodeId == "N2" &&
      Lbpm_SettingInfo.isDraftNodeDisplayOpinion == "true"
    ) {
      if (
        lbpm.constant.DOCSTATUS != "11" &&
        Lbpm_SettingInfo.isNewPageAndDraftsManRecallPage == "true"
      ) {
        return
      } else if (
        lbpm.constant.DOCSTATUS == "11" &&
        Lbpm_SettingInfo.isRejectPage == "true"
      ) {
        return
      } else {
        if (imageUl.length > 0) {
          if (imageUl[0].innerHTML == "") {
            imageUl.html(html);
            query("li","signaturePicUL").forEach(function(domObj){
				 domClass.remove(domObj,"many");
			 })
          } else {
            imageUl.append(html);
            query("li","signaturePicUL").forEach(function(domObj){
				 domClass.add(domObj,"many");
			 })
			 query(".btn_canncel_img").forEach(function(domObj){
				 domClass.add(domObj,"many");
			 })
          }
        } else {
          var rowhtml = '<kmss:ifModuleExist path="/km/signature/">'
          rowhtml +=
            '<tr id="showSignature"><td class="td_normal_title" width="15%"></td>'
          rowhtml +=
            '<td colspan="3" width="85%" id="signaturePic"><div class="titleNode" id="signatureTitleDiv">' +
            msg["mui.lbpmNode.signature"] +
            '</div><ul id="signaturePicUL" class="clearfloat lui_sns_signatureList">' +
            html +
            "</ul></td></tr>"
          rowhtml += "</kmss:ifModuleExist>"
          //query("#commonUsagesRow").after(rowhtml)
        	query("#signatureRow").append(rowhtml);
          domStyle.set(query("#signatureRow")[0],{"display":"block"});
        }
        domStyle.set(query("#signatureTitleDiv")[0],"display","block");
      }
    } else {
      if (imageUl.length > 0) {
        if (imageUl[0].innerHTML == "") {
          imageUl.html(html);
          query("li","signaturePicUL").forEach(function(domObj){
			 domClass.remove(domObj,"many");
		  })
        } else {
          imageUl.append(html);
          query("li","signaturePicUL").forEach(function(domObj){
			 domClass.add(domObj,"many");
		 })
		 query(".btn_canncel_img").forEach(function(domObj){
			 domClass.add(domObj,"many");
		 })
        }
      } else {
        var rowhtml = '<kmss:ifModuleExist path="/km/signature/">'
        rowhtml +=
          '<tr id="showSignature"><td class="td_normal_title" width="15%"></td>'
        rowhtml +=
          '<td colspan="3" width="85%" id="signaturePic"><div class="titleNode" id="signatureTitleDiv">' +
          msg["mui.lbpmNode.signature"] +
          '</div><ul id="signaturePicUL" class="clearfloat lui_sns_signatureList">' +
          html +
          "</ul></td></tr>"
        rowhtml += "</kmss:ifModuleExist>"
        //query("#commonUsagesRow").after(rowhtml)
        	query("#signatureRow").append(rowhtml);
        domStyle.set(query("#signatureRow")[0],{"display":"block"});
      }
      domStyle.set(query("#signatureTitleDiv")[0],"display","block");
    }
    deleteSignature(fdAttId)
  }

  //显示签章密码框
  function showSignaturePwd(signatureId) {
    var contentNode = domConstruct.create("div", {
      className: "muiSignatureDialogElement",
      innerHTML:
        '<div class="muiSignatureDialogTitle">' +
        msg["mui.signature.input.password"] +
        '</div><input class="muiSignatureDialogFormInput" type="password" id="signaturePwd">'
    })
    Dialog.element({
      showClass: "muiBackDialogShow",
      element: contentNode,
      scrollable: false,
      parseable: false,
      buttons: [
        {
          title: msg["mui.signature.cancel"],
          fn: function(dialog) {
            dialog.hide()
          }
        },
        {
          title: msg["mui.signature.ok"],
          fn: function(dialog) {
            var data = {
              value: signatureId,
              pwd: query("#signaturePwd")[0].value
            }
            confirmSignature(data, dialog, false)
          }
        }
      ]
    })
  }

  //删除附件
  function deleteSignature(fdAttId) {
    query("#del_" + fdAttId).on("click", function() {
      query("li[id='" + fdAttId + "']").remove()
      var liDiv = query("li","signaturePicUL");
      if(liDiv.length == 1){
    	  query("li","signaturePicUL").forEach(function(domObj){
			 domClass.remove(domObj,"many");
		  })
      }
      if(liDiv.length == 0){
    	  domStyle.set(query("#signatureTitleDiv")[0],"display","none");
      }
    })
  }

  function findSelectSignature() {
    var selectSignature = []
    query("#signaturePicUL li").forEach(function(node, index, array) {
      selectSignature.push(query(node).attr("signatureid")[0])
    })
    return selectSignature
  }

  return signatrueHtml
})
