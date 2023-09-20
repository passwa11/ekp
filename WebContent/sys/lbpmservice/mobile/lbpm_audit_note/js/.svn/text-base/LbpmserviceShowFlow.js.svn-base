/**
 * 显示流程状态和流程图通用脚本
 */
define([
  "mui/util",
  "mui/i18n/i18n!sys-lbpmservice:lbpm.tab.graphic",
  "mui/i18n/i18n!sys-lbpmservice-support:lbpm.process.status.processStatus",
  "mui/dialog/Dialog",
  "dojo/dom-style"
], function(util, msg1, msg2, Dialog, domStyle) {
  function jsLoad(params, load) {
    var _flowChartdialog

    // 显示流程图
    window.showFlowChart = window.showFlowChartView = function() {
      var processId = util.getUrlParameter(params, "processId")
      var url = util.formatUrl(
        "/sys/lbpmservice/mobile/lbpm_audit_note/flowchart.jsp?processId=" +
          processId,
        true
      )

      _flowChartdialog = new Dialog.claz({
        title: msg1["lbpm.tab.graphic"],
        element:
          '<div id="flowChartView" class="flowChartView"><iframe id="flowChartFrame" width="100%" height="100%" src="' +
          url +
          '"  frameborder="0"></iframe><div>',
        destroyAfterClose: true,
        closeOnClickDomNode: true,
        scrollable: true,
        parseable: true,
        transform: "bottom",
        position: "bottom",
        iconClose:true,
        showClass: "muiDialogElementShow flowChartViewDialog",
        buttons: []
      })
      _flowChartdialog.show()
      domStyle.set(_flowChartdialog.domNode, {
        position: "fixed"
      })
      domStyle.set(_flowChartdialog.contentNode, {
        "padding-bottom": "0px"
      })

      //设置高度
      var iframeObj = document.getElementById("flowChartFrame")
      var contentHeight = _flowChartdialog.contentNode.offsetHeight - 50
      iframeObj.height = contentHeight
    }
    // 显示流程状态
    window.showProcessStatus = function() {
      var processId = util.getUrlParameter(params, "processId"),
        docStatus = util.getUrlParameter(params, "docStatus")

      var url = util.formatUrl(
        "/sys/lbpmservice/mobile/lbpm_audit_note/lbpm_process_status/processStatus.jsp?processId=" +
          processId +
          "&docStatus=" +
          docStatus,
        true
      )

      _flowChartdialog = new Dialog.claz({
        title: msg2["lbpm.process.status.processStatus"],
        element:
          '<div id="processStatusView" class="flowChartView"><iframe id="processStatusFrame" width="100%" height="100%" src="' +
          url +
          '"  frameborder="0"></iframe><div>',
        destroyAfterClose: true,
        closeOnClickDomNode: true,
        scrollable: true,
        parseable: true,
        transform: "bottom",
        position: "bottom",
        showClass: "muiDialogElementShow flowChartViewDialog",
        buttons: []
      })
      _flowChartdialog.show()
      domStyle.set(_flowChartdialog.domNode, {
        position: "fixed"
      })
      domStyle.set(_flowChartdialog.contentNode, {
        "padding-bottom": "0px"
      })

      //设置高度
      var iframeObj = document.getElementById("processStatusFrame")
      var contentHeight = _flowChartdialog.contentNode.offsetHeight - 50
      iframeObj.height = contentHeight
    }

    load()
  }

  return {
    load: function(params, require, load) {
      jsLoad(params, load)
    }
  }
})
