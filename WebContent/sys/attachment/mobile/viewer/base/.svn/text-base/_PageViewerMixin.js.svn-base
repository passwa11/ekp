define([
  "dojo/_base/declare",
  "dojo/json",
  "dojo/dom-attr",
  "dojo/dom-style",
  "dojo/dom-construct",
  "dojo/_base/window",
  "dojo/dom-geometry",
  "dojo/_base/array",
  "mui/util",
  "sys/attachment/mobile/viewer/base/PageValues",
  "sys/attachment/mobile/viewer/base/MapObj",
  "dojo/query",
  "sys/attachment/mobile/js/AttachmentLogApi"
], function(
  declare,
  json,
  domAttr,
  domStyle,
  domConstruct,
  domWindow,
  domGeometry,
  array,
  util,
  PageValues,
  MapObj,
  domQuery,
  AttachmentLogApi
) {
  return declare("sys.attachment.mobile._PageViewerMixin", AttachmentLogApi, {
    pageNode: null,
    pageNodes: null,

    // 预加载多少页
    preLoadPageNum: 3,

    loadedMaxPageNum: 0,

    maxBodyHeight: 0,

    // 当前页
    currentPageNum: 1,

    // 类型
    type: "office.aspose",
    // 定时器，防止多次更新日志
    timer: null,
    // 是否存储记录日志中，防止多次更新日志
    loging: false,
    // 临时页码，防止同一页滚动多次更新日志
    pageTmp: 1,

    contains: function(string, substr, isIgnoreCase) {
      if (isIgnoreCase) {
        string = string.toLowerCase()
        substr = substr.toLowerCase()
      }
      var startChar = substr.substring(0, 1)
      var strLen = substr.length
      for (var j = 0; j < string.length - strLen + 1; j++) {
        if (string.charAt(j) == startChar) {
          // 如果匹配起始字符,开始查找
          if (string.substring(j, j + strLen) == substr) {
            // 如果从j开始的字符与str匹配，那ok
            return true
          }
        }
      }
      return false
    },

    buildRendering: function() {
      if (window.addEventListener)
        window.addEventListener("message", this._receiver, false)
      else if (window.attachEvent)
        window.attachEvent("onmessage", this._receiver)
      this.pageNodes = new MapObj()
      this.pageValues = new PageValues()
      this.inherited(arguments)
      window.pageViewer = this

      if (
        this.viewerStyle &&
        (this.viewerStyle.toLowerCase() == "asposepdf" ||
          this.viewerStyle.toLowerCase() == "asposeppt" ||
          this.viewerStyle.toLowerCase() == "yozopdf" ||
          this.viewerStyle.toLowerCase() == "yozoppt") &&
        attachmentCanDownload != true
      ) {
        var download_div = document.createElement("div")
        download_div.style.position = "absolute"
        download_div.style.top = "0%"
        download_div.style.left = "0%"
        download_div.style.width = "100%"
        download_div.style.height = "100%"
        download_div.style.zIndex = "9999999"
        // download_div.style.backgroundColor='blue';
        domStyle.set(download_div, {
          opacity: ".10",
          "-moz-opacity": "0.1",
          filter: "alpha(opacity=10)"
        })
        this.domNode.appendChild(download_div)
      }

      this.expandProps()

      // 查询播放记录后进行初始化
      this.find(this.fdId)
    },

    // 获取日志完成回调
    findDone: function(data) {
      this.inherited(arguments)

      if (data && data.fdParam) {
        if (data.fdParam) {
          this.currentPageNum = parseInt(data.fdParam)
        }
      }
      this.initialPages()
    },

    postCreate: function() {
      this.inherited(arguments)
      this.bindEvent()
    },

    toJSON: function(str) {
      return new Function("return (" + str + ");")()
    },

    _receiver: function(evt, pageViewer) {
      var callFunction = window.pageViewer.toJSON(evt.data)
      var args = callFunction.args[0]
      if ("resize" == args.name) {
        var pageNum = window.pageViewer._getUrlParameter(args.href, "pageNum")
        var iframeId = "dataLoad_" + pageNum
        window.pageViewer.setPageSize(
          document.getElementById(iframeId),
          {
            w: args.size.width,
            h: args.size.height
          },
          pageNum
        )
      }
    },

    _getUrlParameter: function(url, param) {
      var re = new RegExp()
      re.compile("[\\?&]" + param + "=([^&]*)", "i")
      var arr = re.exec(url)
      if (arr == null) return null
      else return decodeURIComponent(arr[1])
    },

    bindEvent: function() {
      this.scrollEvent = this.connect(window, "scroll", "onScroll")
      this.subscribe("/mui/attachment/viewer/reset", "resetGlobalSetting")
    },
    // 构建页数信息
    expandProps: function() {
      if (!this.viewerParam) return
      try {
        var jsonObj = json.parse(this.viewerParam) // fileKeySufix
        if (jsonObj.hasOwnProperty("totalPageCount")) {
          this.pageValues.set("pageCount", parseInt(jsonObj["totalPageCount"]))
        } else if (jsonObj.hasOwnProperty("htmlPageCount")) {
          this.pageValues.set("pageCount", parseInt(jsonObj["htmlPageCount"]))
        } else if (jsonObj.hasOwnProperty("picPageCount")) {
          this.pageValues.set("pageCount", parseInt(jsonObj["picPageCount"]))
        }
      } catch (e) {
        array.forEach(this.viewerParam.split(","), function(param) {
          var ps = param.split(":")
          if (ps[0] == "totalPageCount") {
            this.pageValues.set("pageCount", parseInt(ps[1]))
            return false
          }
        })
      }
    },
    // 获取当前页
    getPageNum: function() {
      return this.pageValues.get("pageNum")
    },
    // 获取总页数
    getPageCount: function() {
      return this.pageValues.get("pageCount")
    },

    // 初始化
    initialPages: function() {
      this.initialContainer()

      this.preLoadPageNum = this.currentPageNum + this.preLoadPageNum
      var pageCount = this.getPageCount()
      this.preLoadPageNum =
        this.preLoadPageNum > pageCount ? pageCount : this.preLoadPageNum
      this.loadPage(this.currentPageNum)
    },

    // 初始化容器
    initialContainer: function() {
      // 缓存所有容器节点
      this.containers = {}

      var html = '<div class="muiContainer" style=""/>'

      var count = this.getPageCount()

      for (var i = 1; i <= count; i++) {
        var container = domConstruct.toDom(html)

        domConstruct.place(container, this.pageContent, "last")
        this.containers[i] = container
      }
    },

    // 加载页面
    loadPage: function(pageNum) {
      // 越界不加载
      if (pageNum <= 0 || pageNum > this.getPageCount()) {
        return
      }
      if (!this.isLoadPage(pageNum)) {
        var html = ""
        if (this.fileKeySufix == "-img") {
          html =
            "<img onload='window.pageViewer.pageLoaded(" +
            pageNum +
            ",this)' id='dataLoad_" +
            pageNum +
            "' style='border: none;'></img>"
        } else {
          html =
            "<iframe onload='window.pageViewer.pageLoaded(" +
            pageNum +
            ",this)' id='dataLoad_" +
            pageNum +
            "' style='border: none;' scrolling='no'></iframe>"
        }

        try {
          this.pageNode = domConstruct.toDom(html)
          domAttr.set(this.pageNode, "src", this.getSrc(pageNum))
          this.pageNodes.put(pageNum, this.pageNode)
          domConstruct.place(this.pageNode, this.containers[pageNum], "last")
        } catch (error) {
          console.warn(error)
        }
      }
    },

    // 是否加载过
    isLoadPage: function(pageNum) {
      var dataPage = document.getElementById("dataLoad_" + pageNum)
      if (dataPage != null) {
        return true
      } else {
        return false
      }
    },
    getPageSize: function(pageObj) {
      if (pageObj.tagName == "IMG") {
        return {
          w: pageObj.width,
          h: pageObj.height
        }
      }
      if (pageObj.tagName == "IFRAME") {
        return this.getFrameBodySize(pageObj)
      }
    },
    setPage: function(pageObj, pageSize, pageNum) {
      if (pageObj.tagName == "IFRAME") {
        this.setFrameHelpInfo(pageObj)
      }
      if (this.highFidelity == "false" || pageObj.tagName == "IMG") {
        this.setPageSize(pageObj, pageSize, pageNum)
      }
      this.setPageAuth(pageSize)
    },

    
    setFrameHelpInfo: function(pageObj) {
      try {
        var frameDoc = pageObj.contentDocument || pageObj.Document
        var bodyDoc = frameDoc.body
        if (!this.contains(this.viewerStyle, "excel", true)) {
          domAttr.set(pageObj, "scrolling", "no")
        }
        domStyle.set(bodyDoc, "margin", "0px")
        domStyle.set(domQuery(".awpage", bodyDoc)[0], {
          background: "white",
          margin: "0px"
        })
      } catch (e) {}
    },
    setPageSize: function(pageObj, pageSize, pageNum) {
      var fW = pageSize.w
      var fH = pageSize.h
      var availableWidth = domGeometry.position(domWindow.body()).w
      pageObj.width = fW
      pageObj.height = fH
      var parentSize = pageSize
      var pageWrapper = pageObj.parentNode
      if (fW / availableWidth > 0.98) {
        if (pageObj.tagName == "IMG") {
          parentSize = this.autoResizeImage(availableWidth * 0.98, 0, pageObj)
        }
        if (pageObj.tagName == "IFRAME") {
          var scaleSize = (availableWidth * 0.98) / fW
          parentSize = {
            w: availableWidth * 0.98,
            h: (availableWidth * 0.98) / (fW / fH)
          }
          this.zoomFrame(pageObj, scaleSize, scaleSize)
        }
      }

      domStyle.set(pageWrapper, "width", parentSize.w + "px")
      domStyle.set(pageWrapper, "height", parentSize.h + "px")
      this.initialContainerSize(pageNum, parentSize)

      return {w: pageObj.width, h: pageObj.height}
    },

    // 是否已经初始化
    isInited: false,
    // 容器高度
    containerHeight: null,

    // 初始化容器大小
    initialContainerSize: function(pageNum, pageSize) {
      if (this.isInited) {
        return
      }

      // 缓存容器高度，供后面加载计算
      this.containerHeight = pageSize.h

      if (pageNum == this.preLoadPageNum) {
        this.isInited = true

        for (var key in this.containers) {
          if (domStyle.get(this.containers[key], "height")) {
            continue
          }
          domStyle.set(this.containers[key], {
            width: pageSize.w + "px",
            height: pageSize.h + "px"
          })
        }

        // 滚动指定位置，然后再进行显示，优化体验
        var self = this
        setTimeout(function() {
          window.scrollTo(0, self.containers[self.currentPageNum].offsetTop)
          for (var key in self.containers) {
            domStyle.set(self.containers[key], "visibility", "visible")
          }
        }, 1)
      }
    },
    autoResizeImage: function(maxWidth, maxHeight, objImg) {
      var hRatio
      var wRatio
      var Ratio = 1
      var w = objImg.width
      var h = objImg.height
      var ww, hh
      wRatio = maxWidth / w
      hRatio = maxHeight / h
      if (maxWidth == 0 && maxHeight == 0) {
        Ratio = 1
      } else if (maxWidth == 0) {
        //
        if (hRatio < 1) Ratio = hRatio
      } else if (maxHeight == 0) {
        if (wRatio < 1) Ratio = wRatio
      } else if (wRatio < 1 || hRatio < 1) {
        Ratio = wRatio <= hRatio ? wRatio : hRatio
      }
      if (Ratio < 1) {
        ww = w * Ratio
        hh = h * Ratio
      }
      objImg.height = hh
      objImg.width = ww
      return {h: hh, w: ww}
    },
    zoomFrame: function(frameObj, xScale, yScale) {
      domStyle.set(frameObj, {
        "-webkit-transform": "scale(" + xScale + "," + yScale + ")",
        "-webkit-transform-origin": "0px 0px"
      })
    },
    setPageAuth: function(pageObj) {},
    pageLoaded: function(pageNum, pageObj) {
      // 根据是否可拷贝权限变量判断是否需要给IFrame内容添加可拷贝限制CSS样式（不可拷贝时添加相关限制样式）
      if (
        pageObj.tagName &&
        pageObj.tagName.toLowerCase() == "iframe" &&
        attachmentCanCopy != true
      ) {
        var pageBodyObj = pageObj.contentWindow.document.getElementsByTagName(
          "body"
        )[0]
        pageBodyObj.style["-webkit-touch-callout"] = "none"
        pageBodyObj.style["touch-callout"] = "none"
        pageBodyObj.style["-webkit-text-size-adjust"] = "none"
        pageBodyObj.style["-webkit-user-select"] = "none"
        pageBodyObj.style["-khtml-user-select"] = "none"
        pageBodyObj.style["-moz-user-select"] = "none"
        pageBodyObj.style["-ms-user-select"] = "none"
        pageBodyObj.style["user-select"] = "none"
        pageBodyObj.style["-webkit-highlight"] = "none"
        pageBodyObj.style["-webkit-tap-highlight-color"] = "rgba(0,0,0,0)"
      }

      this.loadedMaxPageNum = pageNum
      this.inherited(arguments)

      domStyle.set(pageObj.parentNode, "visibility", "visible")
      this.setPage(pageObj, this.getPageSize(pageObj), pageNum)

      if (pageNum < this.preLoadPageNum) {
        this.loadPage(pageNum + 1)
      }

      if (this.waterMarkConfig.showWaterMark == "true") {
        this.setWaterMarkBody(domWindow.doc)
      }
    },
    getFrameBodySize: function(frameObj) {
      var pageNum = this.getPageNum()
      var bh = 0
      var bw = 0
      if (this.contains(this.viewerStyle, "yozo", true)) {
        var resultWH
        var pageWhInfo = json.parse(this.viewerParam).pageWh
        var okFind = false
        for (var key in pageWhInfo) {
          var pages = pageWhInfo[key]
          if (typeof pages == "string") {
            if (parseInt(pages) == pageNum) {
              okFind = true
            }
          } else {
            for (var i = 0; i < pages.length; i++) {
              if (parseInt(pages[i]) == pageNum) {
                okFind = true
                break
              }
            }
          }
          if (okFind) {
            resultWH = key
            break
          }
        }
        var tmpResult = resultWH.split(",")
        bw = parseInt(tmpResult[0])
        bh = parseInt(tmpResult[1])
      }
      if (this.contains(this.viewerStyle, "aspose", true)) {
        try {
          var frameDoc = frameObj.contentDocument || frameObj.Document
          var chs = frameDoc.body.childNodes
          if (chs.length == 1) {
            chs = chs[0].childNodes
          }
          for (var i = 0; i < chs.length; i++) {
            var top = chs[i].offsetTop
            var left = chs[i].offsetLeft
            var offsetH = chs[i].offsetHeight
            var offsetW = chs[i].offsetWidth
            if (this.contains(this.viewerStyle, "ppt", true)) {
              offsetW = chs[i].scrollWidth
              offsetH = chs[i].scrollHeight
            }
            var tbh = ("undefined" != typeof top ? top : 0) + offsetH
            var tbw = ("undefined" != typeof left ? left : 0) + offsetW
            if (tbh > bh) {
              bh = tbh
            }
            if (tbw > bw) {
              bw = tbw
            }
          }
        } catch (e) {
          //
        }
      }
      return {
        w: bw,
        h: bh
      }
    },
    setWaterMarkBody: function(doc) {
      domQuery(".mask_div").remove()
      var oTemp = doc.createDocumentFragment()
      // 获取页面最大宽度
      var mark_width = Math.max(doc.body.scrollWidth, doc.body.clientWidth)
      // 获取页面最大长度
      var mark_height =
        Math.max(doc.body.scrollHeight, doc.body.clientHeight) - 46
      var markWidth = this.waterMarkConfig.otherInfos.markWidth + 16
      var markHeight = this.waterMarkConfig.otherInfos.markHeight + 6
      var markType = this.waterMarkConfig.markType
      var markOpacity = parseFloat(this.waterMarkConfig.markOpacity)
      var colSpace = parseInt(this.waterMarkConfig.markColSpace)
      var rowSpace = parseInt(this.waterMarkConfig.markRowSpace)
      var cols = parseInt((mark_width - 0 + colSpace) / (markWidth + colSpace))
      if (cols > 1) {
        colSpace = parseInt((mark_width - 0 - markWidth * cols) / (cols - 1))
      } else {
        cols = 1
      }
      var rows = parseInt(
        (mark_height - 46 - 46 + rowSpace) / (markHeight + rowSpace)
      )
      if (rows > 1) {
        rowSpace = parseInt((mark_height - 46 - markHeight * rows) / (rows - 1))
      }
      var rotateType = this.waterMarkConfig.markRotateType
      var angel = parseInt(this.waterMarkConfig.markRotateAngel)
      var rad = angel * (Math.PI / 180)
      var colLeft
      var rowTop
      for (var i = 0; i < rows; i++) {
        rowTop = 46 + (rowSpace + markHeight) * i
        for (var j = 0; j < cols; j++) {
          colLeft = 0 + (markWidth + colSpace) * j
          var mask_div = document.createElement("div")
          mask_div.id = "mask_div" + i + j
          if ("word" == markType) {
            var markWord = this.waterMarkConfig.otherInfos.markWord
            var markWordFontFamily = this.waterMarkConfig.markWordFontFamily
            var markWordFontSize = this.waterMarkConfig.markWordFontSize
            var markWordFontColor = this.waterMarkConfig.markWordFontColor
            mask_div.appendChild(document.createTextNode(markWord))
            mask_div.style.fontSize = markWordFontSize + "px"
            mask_div.style.fontFamily = markWordFontFamily
            mask_div.style.color = markWordFontColor
          }
          if ("pic" == markType) {
            var picUrl = this.waterMarkConfig.otherInfos.picUrl
            var img = document.createElement("img")
            img.src = picUrl
            mask_div.appendChild(img)
          }
          if ("declining" == rotateType) {
            var commonCss = "rotate(" + angel + "deg)"
            var ieCss =
              "progid:DXImageTransform.Microsoft.Matrix(sizingMethod='auto expand', M11="
            ieCss += Math.cos(rad) + ",M12="
            ieCss += -Math.sin(rad) + ",M21="
            ieCss += Math.sin(rad) + ",M22="
            ieCss += Math.cos(rad) + ")"
            domStyle.set(mask_div, {
              "-moz-transform": commonCss,
              "-o-transform": commonCss,
              "-webkit-transform": commonCss,
              "-ms-transform": commonCss,
              transform: commonCss
            })
          }
          mask_div.style.visibility = ""
          mask_div.style.position = "absolute"
          mask_div.style.left = colLeft + "px"
          mask_div.style.top = rowTop + 20 + "px"
          mask_div.style.overflow = "hidden"
          mask_div.style.zIndex = "999999"
          domStyle.set(mask_div, {
            opacity: markOpacity,
            "pointer-events": "none",
            display: "block"
          })
          mask_div.style.textAlign = "center"
          mask_div.style.height = markHeight + "px"
          mask_div.className = "mask_div"
          oTemp.appendChild(mask_div)
        }
      }
      doc.body.appendChild(oTemp)
    },

    setWaterMark: function(pageObj) {
      var pagePos = domGeometry.position(pageObj, true)
      var markDiv
      if (this.contains(this.viewerStyle, "excel", true)) {
        var frameDoc = pageObj.contentDocument || pageObj.Document
        markDiv = domConstruct.create("div", null, domWindow.body(frameDoc))
        var newW = domStyle.set(markDiv, {
          "background-color": "transparent",
          overflow: "hidden",
          position: "absolute",
          left: "0px",
          top: "0px",
          width: pagePos.w + "px",
          height: pagePos.h + "px",
          "z-index": 999
        })
      } else {
        markDiv = domConstruct.create("div", null, domWindow.body())
        domStyle.set(markDiv, {
          "background-color": "transparent",
          overflow: "hidden",
          position: "absolute",
          left: pagePos.x + "px",
          top: pagePos.y + "px",
          width: pagePos.w + "px",
          height: pagePos.h + "px",
          "z-index": 999
        })
      }
      var waterMarkBgType = this.waterMarkConfig.markBgType
      var picUrl =
        "../sys_att_watermark/sysAttWaterMark.do?method=getWaterMarkPNG"
      var bgOpacity = parseFloat(this.waterMarkConfig.markOpacity).toFixed(2)
      this.setWaterMarkInner(
        markDiv,
        waterMarkBgType,
        "url(" + picUrl + ")",
        bgOpacity
      )
    },
    setWaterMarkInner: function(markDiv, waterMarkBgType, bgImage, bgOpacity) {
      domQuery(markDiv).innerHTML("")
      var bgImageDiv
      if (waterMarkBgType == "norepeat-center-center") {
        for (var i = 1; i <= 1; i++) {
          bgImageDiv = domConstruct.create(
            "div",
            {
              class: "bgImage"
            },
            markDiv
          )
          domStyle.set(bgImageDiv, {
            width: "100%",
            height: "100%"
          })
          this.setWaterMarkInnerCommonCss(bgImageDiv, bgImage, bgOpacity)
        }
      }
      if (waterMarkBgType == "repeaty-center-top") {
        for (var i = 1; i <= 5; i++) {
          bgImageDiv = domConstruct.create(
            "div",
            {
              class: "bgImage"
            },
            markDiv
          )
          domStyle.set(bgImageDiv, {
            width: "33%",
            height: "20%",
            margin: "0px auto"
          })
          this.setWaterMarkInnerCommonCss(bgImageDiv, bgImage, bgOpacity)
        }
      }
      if (waterMarkBgType == "repeatx-left-center") {
        for (var i = 1; i <= 3; i++) {
          bgImageDiv = domConstruct.create(
            "div",
            {
              class: "bgImage"
            },
            markDiv
          )
          domStyle.set(bgImageDiv, {
            width: "33%",
            height: "100%",
            float: "left"
          })
          this.setWaterMarkInnerCommonCss(bgImageDiv, bgImage, bgOpacity)
        }
      }
      if (waterMarkBgType == "repeat-left-top") {
        for (var i = 1; i <= 15; i++) {
          bgImageDiv = domConstruct.create(
            "div",
            {
              class: "bgImage"
            },
            markDiv
          )
          domStyle.set(bgImageDiv, {
            width: "33%",
            height: "20%",
            float: "left"
          })
          this.setWaterMarkInnerCommonCss(bgImageDiv, bgImage, bgOpacity)
        }
      }
    },
    setWaterMarkInnerCommonCss: function(bgImageDiv, bgImage, bgOpacity) {
      domStyle.set(bgImageDiv, {
        "background-image": bgImage,
        "background-repeat": "no-repeat",
        "background-position": "center center",
        opacity: bgOpacity,
        "z-index": 9999
      })
    },
    // 删除注册事件
    resetGlobalSetting: function(srcObj, evt) {
      if (this == srcObj) {
        if (this.scrollEvent != null) {
          this.scrollEvent.remove()
          this.scrollEvent = null
        }
      }
    },

    // 更新日志
    updateLog: function(pageNum) {
      if (this.timer) {
        clearTimeout(this.timer)
        this.timer = null
      }

      if (this.pageTmp == pageNum || this.loging) {
        return
      }

      var self = this
      this.timer = setTimeout(function() {
        self.pageTmp = pageNum
        self.loging = true
        self.log(self.fdId, pageNum)
      }, 1000)
    },

    updateDone: function() {
      this.inherited(arguments)
      this.loging = false
    },

    addDone: function(data) {
      this.inherited(arguments)
      if (data) {
        this.loging = false
      }
    },
    /** 已经加载页码 */
    loadedNum: 0,
    
    onScroll: function(evt) {
      // 未初始完毕不触发滚动事件
      if (!this.isInited) {
        return
      }
      // 滚动位置
      var scrollSize = domGeometry.docScroll()
      // 屏幕高度
      var screenSize = util.getScreenSize()
      
      var maxNow = Math.max(
          domWindow.body(domWindow.doc).scrollHeight,
          domWindow.body(domWindow.doc).clientHeight
        )

      if (scrollSize.y > 0) {
        var yTop = scrollSize.y
        var yBottom = scrollSize.y + screenSize.h

        // 向下取整
        var firstPage = Math.floor(yTop / this.containerHeight)
        // 更新日志
        this.updateLog(Math.ceil(yTop / this.containerHeight))
        // 向上取整
        var lastPage = Math.ceil(yBottom / this.containerHeight)
        // 到底了但是由于页面高度不一致导致有空白页面
        if(maxNow <= yBottom+10){
        	lastPage = this.getPageCount()
        }
        for (firstPage; firstPage <= lastPage; firstPage++) {
          this.loadPage(firstPage)
        }
        loadedNum = lastPage
       
      }

      if (this.waterMarkConfig.showWaterMark == "true") {
        if (maxNow > this.maxBodyHeight) {
          this.setWaterMarkBody(domWindow.doc)
          this.maxBodyHeight = maxNow
        }
      }
    }
  })
})
