define([
  "dojo/_base/declare",
  "mui/form/Category",
  "mui/address/AddressMixin",
  "dojo/query",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/on",
  "mui/util",
  "dojo/_base/lang",
  "dojo/_base/array",
  "mui/dialog/Dialog",
  "mui/i18n/i18n!sys-mobile",
  "mui/i18n/i18n!sys-lbpmservice"
], function(
  declare,
  Category,
  AddressMixin,
  query,
  domConstruct,
  domStyle,
  domClass,
  on,
  util,
  lang,
  array,
  Dialog,
  msg,
  Msg
) {
  var freeSubFlowNodes = declare(
    "sys.lbpmservice.mobile.node.group.freesubflownode.freeSubFlowNodes",
    [Category, AddressMixin],
    {
      subject: "设置下一步",

      isMul: true,

      type: ORG_TYPE_POSTORPERSON,

      idField: "_freeSubFlowNodes_",

      id: "freeSubFlowNodes",

      groupNodeId: "",

      //是否显示头像
      showHeadImg: true,

      _cateDialogPrefix: "__freeSubFlowNodes__",

      //新地址本
      isNew: true,

      //部门限制
      deptLimit: "",

      //例外类别id
      exceptValue: "",

      maxPageSize: 300,

      dataUrl:
        "/sys/organization/mobile/address.do?method=addressList&parentId=!{parentId}&orgType=!{selType}&deptLimit=!{deptLimit}&maxPageSize=!{maxPageSize}",

      iconUrl: "/sys/lbpmservice/mobile/freeflow/image.jsp?orgId=!{orgId}",

      searchUrl:
        "/sys/organization/mobile/address.do?method=searchList&keyword=!{keyword}&orgType=!{orgType}&deptLimit=!{deptLimit}",

      buildRendering: function() {
        this.inherited(arguments)
        domClass.add(this.domNode, "muiAddressForm")
        if (this.showHeadImg) {
          domClass.add(this.domNode, "freeFlowShowHeadImg")
        }
      },

      postCreate: function() {
        this.inherited(arguments)
      },

      domNodeClick: function() {
        if (this.showHeadImg) {
          var evtNode = query(arguments[0].target).closest(".muiCategoryAdd")
          if (evtNode.length <= 0) {
            return
          }
        }
        var evtNode = query(arguments[0].target).closest(".freeFlowOrg")
        if (evtNode.length > 0) {
          return
        }
        this.defer(function() {
          this._selectCate()
        }, 350)
      },

      buildValue: function(domContainer) {
        domConstruct.empty(domContainer)
        domClass.replace(
          domContainer,
          "freeFlowFiledShow",
          domContainer.className
        )
        if (this.curIds != null && this.curIds != "") {
          var ids = this.curIds.split(this.splitStr)
          if (ids.length > 0) {
            this.addSubNodeToGroup()
          }
        }

        this.updateFreeSubFlowNodes(domContainer)

        // 显示添加子节点按钮图标+（对应设置下一步)，如果已添加过子节点，则需去掉添加子节点按钮图标+
        if (lbpm.myAddedSubNodes.length == 0) {
          var freeSubFlowNode = lbpm.nodes[lbpm.nowFreeSubFlowNodeId]
          if (
            lbpm.nodes[lbpm.nowNodeId].groupNodeId ==
              lbpm.nowFreeSubFlowNodeId ||
            !freeSubFlowNode.initSubNodeId
          ) {
            var className = "muiCategoryAdd mui mui-plus"
            this.muiCategoryAddNode = domConstruct.create(
              "div",
              {className: className},
              domContainer
            )
            domStyle.set(this.muiCategoryAddNode, "display", "inline-block")
            domStyle.set(this.muiCategoryAddNode, "margin-left", "0.3rem")
          }
        } else {
          this.muiCategoryAddNode = null
        }

        // 切断父类设置的事件绑定
        this.disconnect(this.orgIconClickHandle)
        // 重新绑定
        this.connect(
          this.cateFieldShow,
          on.selector(".freeFlowOrgIcon", "click"),
          function(evt) {
            if (evt.stopPropagation) evt.stopPropagation()
            if (evt.cancelBubble) evt.cancelBubble = true
            if (evt.preventDefault) evt.preventDefault()
            if (evt.returnValue) evt.returnValue = false
            var nodes = query(evt.target).closest(".freeFlowOrg")
            nodes.forEach(function(orgDom) {
              var id = orgDom.getAttribute("data-id")
              var node = lbpm.nodes[id]
              if (node.handlerIds.split(";").length <= 1) {
                return
              }
              // 当前操作的节点对象
              this.operatingNode = node
              // 当前操作节点的Dom对象
              this.operatingNodeDom = orgDom
              var store = array.map(node.handlerIds.split(";"), function(
                handlerId,
                i
              ) {
                return {
                  text: util.formatText(node.handlerNames.split(";")[i]),
                  value: handlerId
                }
              })
              var canEdit = false
              if (node.Status == "1") {
                if (lbpm.myAddedSubNodes.contains(node.id)) {
                  canEdit = true
                }
              }

              var itemHtmls = array.map(store, function(item) {
                var temp =
                  '<div data-dojo-type="sys/lbpmservice/mobile/freeflow/NodeHandlersListItem" class="_nodeHandlerListItem" name="_listItem_nodeHandlers" value="!{value}"' +
                  " data-dojo-props=\"mul:false,text:'!{text}',nodeId:'" +
                  node.id +
                  "',canEdit:" +
                  canEdit +
                  '"></div>'
                return temp
                  .replace("!{text}", item.text)
                  .replace("!{value}", item.value)
              })
              var html =
                "<div class='muiFormSelectElement'>" +
                itemHtmls.join("") +
                "</div>"

              // 针对多个审批人的节点弹出对应的节点处理人列表
              this.defer(function() {
                if (this.dialog != null) {
                  return
                }

                var buttons = []
                if (canEdit) {
                  buttons = [
                    {
                      title: msg["mui.button.cancel"],
                      fn: function(dialog) {
                        dialog.hide()
                      }
                    },
                    {
                      title: msg["mui.button.ok"],
                      fn: lang.hitch(this, this._modifyNodeHandlers)
                    }
                  ]
                } else {
                  buttons = [
                    {title: "", fn: null},
                    {title: "", fn: null}
                  ]
                }
                this.dialog = Dialog.element({
                  showClass: "muiDialogElementShow muiFormSelect",
                  element: html,
                  position: "bottom",
                  scrollable: false,
                  parseable: true,
                  buttons: buttons,
                  onDrawed: function() {
                    // 调整样式使删除item后弹出框的高度能自动跟随变化
                    domStyle.set(this.containerNode, "min-height", "7%")
                    domStyle.set(this.contentNode, "height", "auto")
                  },
                  callback: lang.hitch(this, function() {
                    this.dialog = null
                  })
                })
                // 设置节点处理人列表顶部的标题
                var nameDom = query(orgDom).children(".name")[0]
                var title = domConstruct.toDom(
                  "<div class='muiDialogElementButton_bottom' style='width:100%'>" +
                    nameDom.innerText +
                    "</div>"
                )
                domConstruct.place(title, this.dialog.buttonsDom[0], "after")
                if (canEdit) {
                  domStyle.set(this.dialog.buttonsDom[0], "width", "15%")
                  domStyle.set(this.dialog.buttonsDom[1], "width", "15%")
                  domStyle.set(title, {"width": "70%", "float": "left"})
                }
              }, 300)
            }, this)
          }
        )
      },

      _buildOneOrg: function(
        domContainer,
        node,
        isNextFreeSubFlow,
        initSubNodeId
      ) {
        var isMulHandlers = false
        if (node.handlerNames) {
          if (node.handlerNames.split(";").length > 1) {
            isMulHandlers = true
          }
        }

        if (isMulHandlers) {
          var icon = util.formatUrl(
            util.urlResolver(this.iconUrl, {
              orgId: ""
            })
          )
          this.buildOrgNode(domContainer, node, icon)
        } else {
          var icon = util.formatUrl(
            util.urlResolver(this.iconUrl, {
              orgId: node.handlerIds
            })
          )
          this.buildOrgNode(
            domContainer,
            node,
            icon,
            isNextFreeSubFlow,
            initSubNodeId
          )
        }
      },
      buildOrgNode: function(
        domContainer,
        node,
        icon,
        isNextFreeSubFlow,
        initSubNodeId
      ) {
        // 构建节点图标(头像)
        var tmpOrgDom = this.buildNodeIcon(domContainer, node, icon)
        // 构建节点类型图标
        this.buildNodeTypeIcon(tmpOrgDom, node)
        // 构建删除按钮图标
        this.buildDeleteIcon(tmpOrgDom, node, isNextFreeSubFlow, initSubNodeId)
        // 构建箭头
        this.buildArrowIcon(tmpOrgDom, node)
      },

      _delOneOrg: function(orgDom, id) {
        this.inherited(arguments)
        this.deleteFreeSubFlowNode(id)
        // 删除本次添加的子节点，需要重新出现添加子节点按钮+
        if (
          lbpm.myAddedSubNodes.length == 0 &&
          this.muiCategoryAddNode == null
        ) {
          var freeSubFlowNode = lbpm.nodes[lbpm.nowFreeSubFlowNodeId]
          if (
            lbpm.nodes[lbpm.nowNodeId].groupNodeId ==
              lbpm.nowFreeSubFlowNodeId ||
            freeSubFlowNode.initSubNodeId == null ||
            freeSubFlowNode.initSubNodeId == ""
          ) {
            var className = "muiCategoryAdd mui mui-plus"
            this.muiCategoryAddNode = domConstruct.create(
              "div",
              {className: className},
              this.contentNode
            )
            domStyle.set(this.muiCategoryAddNode, "display", "inline-block")
            domStyle.set(this.muiCategoryAddNode, "margin-left", "0.3rem")
          }
        }
      },

      //构建节点图标（头像）
      buildNodeIcon: function(domContainer, node, icon) {
        var tmpOrgDom = domConstruct.create(
          "div",
          {className: "freeFlowOrg", "data-id": node.id},
          domContainer
        )
        var nodeIconStyle = {
          style: {
            background: "url(" + icon + ") center center no-repeat",
            backgroundSize: "cover",
            display: "inline-block",
            border: "1px solid"
          },
          className: "freeFlowOrgIcon"
        }
        if (lbpm.nowNodeId == node.id) {
          nodeIconStyle.style.border = "1px solid #ffcccc"
        }
        domConstruct.create("div", nodeIconStyle, tmpOrgDom)
        var nodeTitle = this._getNodeTitle(node)

        domConstruct.create(
          "div",
          {
            className: "name",
            innerHTML: nodeTitle
          },
          tmpOrgDom
        )
        return tmpOrgDom
      },

      //构建节点类型图标
      buildNodeTypeIcon: function(domContainer, node) {
        var iconUrl = util.formatUrl(
          "/sys/lbpmservice/mobile/resource/image/" + node.XMLNODENAME + ".png"
        )
        domConstruct.create(
          "div",
          {
            style: {
              background: "url(" + iconUrl + ") center center no-repeat",
              backgroundSize: "cover",
              display: "block"
            },
            className: "nodeTypeIcon"
          },
          domContainer
        )
      },

      //构建删除图标
      buildDeleteIcon: function(
        domContainer,
        node,
        isNextFreeSubFlow,
        initSubNodeId
      ) {
        var canDelete = false
        if (node.Status == "1") {
          if (lbpm.myAddedSubNodes.contains(node.id)) {
            canDelete = true
          }
        }
        if (node.Status != "3") {
          //下一节点是自由子流程节点且模板没配置初始环节时或当前节点是自由子流程节点模板配置的初始环节时，具有删除子节点的权利
          if (
            (isNextFreeSubFlow && initSubNodeId == null) ||
            lbpm.nowNodeId == initSubNodeId
          ) {
            canDelete = true
          }
        }
        //添加删除按钮
        if (canDelete) {
          var delOrgDom = domConstruct.create(
            "div",
            {className: "del mui mui-close"},
            domContainer
          )
          this.connect(delOrgDom, "touchend", function(evt) {
            if (evt.stopPropagation) evt.stopPropagation()
            if (evt.cancelBubble) evt.cancelBubble = true
            if (evt.preventDefault) evt.preventDefault()
            if (evt.returnValue) evt.returnValue = false
            var nodes = query(evt.target).closest(".freeFlowOrg")
            nodes.forEach(function(orgDom) {
              var id = orgDom.getAttribute("data-id")
              this.defer(function() {
                // 同时关注时，必须要异步处理
                this._delOneOrg(orgDom, id)
              }, 300)
            }, this)
          })
        }
      },

      // 构建箭头图标
      buildArrowIcon: function(domContainer, node) {
        if (node.id == lbpm.nowNodeId) {
          this.isPassedRoute = false
        }
        var nextNodeId = node.endLines[0].endNode.id
        var nextNode = lbpm.nodes[nextNodeId]
        if (nextNode.endLines.length == 0) {
          if (lbpm.myAddedSubNodes.length == 0) {
            var freeSubFlowNode = lbpm.nodes[lbpm.nowFreeSubFlowNodeId]
            if (
              lbpm.nodes[lbpm.nowNodeId].groupNodeId ==
                lbpm.nowFreeSubFlowNodeId ||
              freeSubFlowNode.initSubNodeId == null ||
              freeSubFlowNode.initSubNodeId == ""
            ) {
              // 添加节点按钮图标（设置下一处理人）前的箭头显示
              var arrowUrl = util.formatUrl(
                "/sys/lbpmservice/mobile/resource/image/arrowGoto.png"
              )
              domConstruct.create(
                "div",
                {
                  style: {
                    background: "url(" + arrowUrl + ") center center no-repeat",
                    backgroundSize: "cover",
                    display: "block"
                  },
                  className: "arrowGoIcon"
                },
                domContainer
              )
            }
          }
          return
        }
        if (lbpm.myAddedSubNodes.contains(nextNodeId)) {
          this.isPassedRoute = false
        }
        var arrowUrl = util.formatUrl(
          "/sys/lbpmservice/mobile/resource/image/arrowGoto.png"
        )

        if (this.isPassedRoute) {
          arrowUrl = util.formatUrl(
            "/sys/lbpmservice/mobile/resource/image/arrowGone.png"
          )
        }

        domConstruct.create(
          "div",
          {
            style: {
              background: "url(" + arrowUrl + ") center center no-repeat",
              backgroundSize: "cover",
              display: "block"
            },
            className: "arrowGoIcon"
          },
          domContainer
        )
      },

      buildOptIcon: function(optContainer) {
        if (!this.showHeadImg) this.inherited(arguments)
        this.muiCategoryAddNode = optContainer
      },

      // 构建节点标题（底部的名称）
      _getNodeTitle: function(node) {
        var handlerName = node.handlerNames
        var handlerSize = handlerName.split(";").length
        var nodeTitle = handlerName
        if (handlerSize > 1) {
          if (node.XMLNODENAME == "reviewNode") {
            if (node.processType == "0") {
              nodeTitle = lang.replace(
                Msg["mui.freeFlow.reviewNode.processType_0.nodeTitle"],
                [handlerSize]
              )
            } else if (node.processType == "1") {
              nodeTitle = lang.replace(
                Msg["mui.freeFlow.reviewNode.processType_1.nodeTitle"],
                [handlerSize]
              )
            } else if (node.processType == "2") {
              nodeTitle = lang.replace(
                Msg["mui.freeFlow.reviewNode.processType_2.nodeTitle"],
                [handlerSize]
              )
            }
          } else if (node.XMLNODENAME == "signNode") {
            if (node.processType == "0") {
              nodeTitle = lang.replace(
                Msg["mui.freeFlow.signNode.processType_0.nodeTitle"],
                [handlerSize]
              )
            } else if (node.processType == "1") {
              nodeTitle = lang.replace(
                Msg["mui.freeFlow.signNode.processType_1.nodeTitle"],
                [handlerSize]
              )
            } else if (node.processType == "2") {
              nodeTitle = lang.replace(
                Msg["mui.freeFlow.signNode.processType_2.nodeTitle"],
                [handlerSize]
              )
            }
          } else if (node.XMLNODENAME == "sendNode") {
            nodeTitle = Msg["mui.freeFlow.sendNode.nodeTitle"]
          }
        }
        return nodeTitle
      },

      _modifyNodeHandlers: function(dialog) {
        var _handlerIds = ""
        var _handlerNames = ""
        var isChange = false
        array.forEach(dialog.htmlWdgts, function(wdt) {
          if (wdt && wdt.name == "_listItem_nodeHandlers") {
            if (wdt._destroyed) {
              isChange = true
            } else {
              _handlerIds += ";" + wdt.value
              _handlerNames += ";" + wdt.text
            }
          }
        })
        if (!isChange) {
          dialog.hide()
          return
        }
        _handlerIds = _handlerIds.slice(1)
        _handlerNames = _handlerNames.slice(1)

        this.modifyNodeHandlersInFreeFlow(
          this.operatingNode.id,
          _handlerIds,
          _handlerNames
        )

        var orgIconDom = query(this.operatingNodeDom).children(
          ".freeFlowOrgIcon"
        )[0]
        if (_handlerIds.split(";").length == 1) {
          var icon = util.formatUrl(
            util.urlResolver(this.iconUrl, {
              orgId: _handlerIds
            })
          )
          domStyle.set(
            orgIconDom,
            "background",
            "url(" + icon + ") center center / cover no-repeat"
          )
        }

        var nameDom = query(this.operatingNodeDom).children(".name")[0]
        nameDom.innerText = this._getNodeTitle(
          lbpm.nodes[this.operatingNode.id]
        )
        dialog.hide()
      },

      returnDialog: function(srcObj, evt) {
        if (srcObj.key == this.idField) {
          this.inherited(arguments)
          this.curIds = ""
          this.curNames = ""
        }
      },

      // 以下是自由流流程图相关操作js
      addSubNodeToGroup: function() {
        var FlowChartObject = this.getFlowChartObject()
        if (FlowChartObject) {
          var ids = this.curIds.split(this.splitStr)
          var names = this.curNames.split(this.splitStr)
          if (ids.length > 0) {
            lbpm.subNodeHandlerIds = new Array()
            lbpm.subNodeHandlerNames = new Array()
            for (var i = 0; i < ids.length; i++) {
              lbpm.subNodeHandlerIds.push(ids[i])
              lbpm.subNodeHandlerNames.push(names[i])
            }
            if (lbpm.myAddedSubNodes.length == 0) {
              var subNode = FlowChartObject.Nodes.createSubNode(
                "reviewNode",
                lbpm.nowFreeSubFlowNodeId,
                "freeSubFlowNode"
              )
              // 设置节点处理人id以及name信息
              subNode.Data["handlerIds"] = lbpm.subNodeHandlerIds.join(";")
              subNode.Data["handlerNames"] = lbpm.subNodeHandlerNames.join(";")
              var data = new KMSSData()
              data.AddBeanData(
                "getOperTypesByNodeService&nodeType=freeFlowReviewNode"
              )
              data = data.GetHashMapArray()
              for (var j = 0; j < data.length; j++) {
                if (data[j].isDefault == "true") {
                  subNode.Data["operations"]["refId"] = data[j].value
                  break
                }
              }
              if (lbpm.globals.defOperRefId) {
                subNode.Data["operations"]["refId"] = lbpm.globals.defOperRefId
              }
              if(FlowChartObject.ProcessData.opinionSortIds){
  				var oIds = FlowChartObject.ProcessData.opinionSortIds.split(";");
  				if(Com_ArrayGetIndex(oIds, lbpm.nowFreeSubFlowNodeId) > -1){
  					oIds.push(subNode.Data["id"]);
  				}
  				FlowChartObject.ProcessData.opinionSortIds = oIds.join(";");
  			}
              lbpm.myAddedSubNodes.push(subNode.Data.id)
            } else {
              var subNode = FlowChartObject.Nodes.GetNodeById(
                lbpm.myAddedSubNodes[0]
              )
              subNode.Data["handlerIds"] = lbpm.subNodeHandlerIds.join(";")
              subNode.Data["handlerNames"] = lbpm.subNodeHandlerNames.join(";")
            }
          }
          this.updateFlowXml(FlowChartObject)
        }
      },

      updateFreeSubFlowNodes: function(domContainer) {
        domConstruct.empty(domContainer)
        var freeSubFlowNode = lbpm.nodes[lbpm.nowFreeSubFlowNodeId]
        if (freeSubFlowNode != null) {
          var groupStartNode = lbpm.nodes[freeSubFlowNode.startNodeId]
          var isNextFreeSubFlow =
            lbpm.globals.getNextNodeObj(lbpm.nowNodeId).id ==
            lbpm.nowFreeSubFlowNodeId
              ? true
              : false
          this.isPassedRoute = true
          this.appendFreeSubFlowNode(
            domContainer,
            groupStartNode,
            isNextFreeSubFlow,
            freeSubFlowNode.initSubNodeId
          )
        }
      },

      appendFreeSubFlowNode: function(
        domContainer,
        nodeObj,
        isNextFreeSubFlow,
        initSubNodeId
      ) {
        var node = nodeObj.endLines[0].endNode
        if (node.endLines.length == 0) {
          return
        }
        if (lbpm.nowNodeId == "N2" && initSubNodeId == null) {
          if (
            node.XMLNODENAME != "groupEndNode" &&
            node.XMLNODENAME != "groupStartNode"
          ) {
            lbpm.myAddedSubNodes.push(node.id)
          }
        }
        this._buildOneOrg(domContainer, node, isNextFreeSubFlow, initSubNodeId)
        if (!this.edit) {
          domConstruct.create("span", {innerHTML: this.splitStr}, domContainer)
        }
        this.appendFreeSubFlowNode(
          domContainer,
          node,
          isNextFreeSubFlow,
          initSubNodeId
        )
      },

      deleteFreeSubFlowNode: function(subNodeId) {
        var node = lbpm.nodes[subNodeId]
        if (!node) {
          return
        }
        var FlowChartObject = this.getFlowChartObject()
        FlowChartObject.Nodes.deleteSubNode(subNodeId)
        if (lbpm.myAddedSubNodes.contains(subNodeId)) {
          lbpm.myAddedSubNodes = new Array()
          lbpm.subNodeHandlerIds = new Array()
          lbpm.subNodeHandlerNames = new Array()
        }
        this.updateFlowXml(FlowChartObject)
      },

      modifyNodeHandlersInFreeFlow: function(nodeId, ids, names) {
        var FlowChartObject = this.getFlowChartObject()
        var nodeObj = FlowChartObject.Nodes.GetNodeById(nodeId)
        nodeObj.Data["handlerNames"] = names
        nodeObj.Data["handlerIds"] = ids
        this.updateFlowXml(FlowChartObject)
      },

      getFlowChartObject: function() {
        var iframe = document.getElementById("WF_IFrame")
        return iframe.contentWindow.FlowChartObject
      },

      updateFlowXml: function(FlowChartObject) {
        var flowXml = FlowChartObject.BuildFlowXML()
        if (!flowXml) {
          return
        }
        var processXMLObj = document.getElementsByName(
          "sysWfBusinessForm.fdFlowContent"
        )[0]
        processXMLObj.value = flowXml
        lbpm.globals.parseXMLObj()
        lbpm.modifys = {}
        $("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1"
        lbpm.events.mainFrameSynch()
      }
    }
  )
  return freeSubFlowNodes
})
