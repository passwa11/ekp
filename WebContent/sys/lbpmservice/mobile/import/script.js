/**
 * script.jsp移动过来的，性能优化
 */
define([
  "dojo/dom-construct",
  'dojo/query',
  "mui/util",
  "dojo/request",
  "dijit/registry",
  "mui/i18n/i18n!sys-lbpmservice:lbpmNode",
  "mui/i18n/i18n!sys-lbpmservice-support:lbpmAssign.fdOperType.assign",
  "dojo/query",
  "dojo/dom-construct"
], function(domConstruct,query ,util, request, registry, msg1, msg2,query,domConstruct) {
  function jsLoad(params, load) {
    var id = util.getUrlParameter(params, "fdId")

    var url = util.formatUrl(
      "/sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=updateIsLook"
    )
    request.post(url, {handleAs: "json", data: {fdModelId: id}})

    function loadAssignInfo() {
    	 $("#operationsRow_Scope")
         .closest("div.actionCenter")
         .show()
       if ($("#assignOprRow").length == 0) {
     	  
         // 构建分发操作信息行
         var oprRowHtml = '<div id="assignOprRow">'
         oprRowHtml +=
           '<div class="titleNode" id="assignOprTitle" lbpmdetail="operation">' +
           msg2['lbpmAssign.fdOperType.assign']+
           "</div>";
         
         oprRowHtml += '<div class="detailNode" id="assignOprContent" lbpmdetail="operation">'
         oprRowHtml += "</div></div>"
         
         $("#operationsRow_Scope").after(oprRowHtml);

         var options = {
           mulSelect: true,
           idField: "assigneeIds",
           nameField: "assigneeNames",
           splitStr: ";",
           selectType: ORG_TYPE_PERSON | ORG_TYPE_POST,
           notNull: false,
           exceptValue: document
             .getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds")
             .value.split(";"),
           text: lbpm.constant.SELECTORG
         }
         var rowContentHtml = ""
         rowContentHtml += lbpm.address.html_build(options)
         rowContentHtml += "<div class='canMultiAssign'>"
         rowContentHtml +=
           '<div id="canMultiAssign" alertText="" key="canMultiAssign" data-dojo-type="mui/form/CheckBox"'
         rowContentHtml +=
           " data-dojo-props=\"name:'canMultiAssign', value:'true', mul:false, text:'"
         rowContentHtml +=
           msg1["lbpmNode.processingNode.operationsTDTitle_Scope.allow.muti"] +
           msg2["lbpmAssign.fdOperType.assign"]
         rowContentHtml += "'\"></div>"
         rowContentHtml += "</div>";
         
         //$(assignOprContent).html(rowContentHtml);
         
          query("#assignOprContent").html(rowContentHtml, {parseContent: true});
          
      }
    }
    lbpm.events.addListener(lbpm.constant.EVENT_CHANGEOPERATION, function() {
      if (
        lbpm.nowNodeId &&
        (lbpm.nodes[lbpm.nowNodeId]["canAssign"] != "true" ||
          lbpm.nodes[lbpm.nowNodeId].endLines[0].endNode.id == "N3")
      ) {
        return
      }
      // 当前选中的操作是通过操作时，加载分发操作信息行
      if (lbpm.currentOperationType == "handler_pass") {
        loadAssignInfo()
        lbpm.globals.hiddenObject($("#assignOprRow")[0], false)
      } else {
        lbpm.globals.hiddenObject($("#assignOprRow")[0], true)
      }
    })
    lbpm.events.addListener(lbpm.constant.EVENT_SETOPERATIONPARAM, function() {
      if (
        lbpm.nowNodeId &&
        (lbpm.nodes[lbpm.nowNodeId]["canAssign"] != "true" ||
          lbpm.nodes[lbpm.nowNodeId].endLines[0].endNode.id == "N3")
      ) {
        return
      }
      // 通过操作提交时，填充分发操作信息到流程操作参数里面
      if (lbpm.currentOperationType == "handler_pass") {
        if (query("#assigneeIds")[0].value) {
          var assignParam = {}
          assignParam["toAssigneeIds"] = $("#assigneeIds")[0].value
          var widget = registry.byId("canMultiAssign")
          if (widget) {
            assignParam["canMultiAssign"] = widget.get("checked")
          }
          lbpm.globals.setOperationParameterJson(
            JSON.stringify(assignParam),
            "assignParam",
            "param"
          )
        }
      }
    })

    load()
  }

  return {
    load: function(params, require, load) {
      jsLoad(params, load)
    }
  }
})
