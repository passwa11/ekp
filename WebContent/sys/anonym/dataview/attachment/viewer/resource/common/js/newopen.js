function MapObj() {
  /** 存放键的数组(遍历用到) */
  this.keys = new Array()
  /** 存放数据 */
  this.data = new Object()

  /**
   * 放入一个键值对
   * @param {String} key
   * @param {Object} value
   */
  this.put = function(key, value) {
    if (this.data[key] == null) {
      this.keys.push(key)
    }
    this.data[key] = value
  }

  /**
   * 获取某键对应的值
   * @param {String} key
   * @return {Object} value
   */
  this.get = function(key) {
    return this.data[key]
  }

  /**
   * 获取键值对数量
   */
  this.size = function() {
    return this.keys.length
  }
}

domain.register('viewerEvent', function(data) {
  if ('resize' == data.name) {
    var pageNum = Com_GetUrlParameter(data.href, 'pageNum')
    var iframeId = 'dataLoad_' + pageNum
    newOpen.setFrameSize({
      frameObj: document.getElementById(iframeId),
      pageNum: pageNum,
      frameSize: data.size
    })
    if (showAllPage == 'true' && pageNum == totalPageNum) {
      newOpen.allPageFit()
    }
  }
})
var newOpen = {
  pageHeights: new MapObj(),
  pageHeightsShould: new MapObj(),
  goToLoad: new MapObj(),
  goToPageNum: 1,
  preLoadPageNum: 0,
  maxBodyHeight: 0,
  type: 'office.aspose',
  looper : null,
  initialNewOpenViewer: function() {
    currentPage = newOpen.goToPageNum
    newOpen.preLoadPageNum = newOpen.getPreLoadPageNum()

    if (showAllPage == 'true') {
      currentPage = newOpen.goToPageNum = 1
      newOpen.preLoadPageNum = totalPageNum
    }
    var divMiddle = document.getElementById('mainMiddle')
    seajs.use('lui/jquery', function($) {
      $(divMiddle).html('')
    })
    var tableContent =
        "<div id='_____rtf_____content'><table id='viewerContent' align='center' width='100%' height='100%' cellpadding='0px' cellspacing='0px'>"
    var tableInnerHTML = ''

    // 构建各页面外壳
    for (var i = 1; i <= totalPageNum; i++) {
      tableInnerHTML +=
          "<tr id='pageTr_" +
          i +
          "' height='0px'><td id='pageTd_" +
          i +
          "' height='0px' style='text-align:center'><div id='pageDiv_" +
          i +
          "' class='pageDiv'></div></td></tr>"
    }
    seajs.use('lui/jquery', function($) {
      $(divMiddle).html(
          tableContent +
          tableInnerHTML +
          "</table></div><div id='_____rtf__temp_____content' style='width:80%'></div>"
      )
    })
    seajs.use('lui/jquery', function($) {
      $('#totalPageCount').html(totalPageNum)
    })

    window.onscroll = newOpen.scrollHandler
    commonFuncs.setPageToolBar(newOpen.goToPageNum)
    newOpen.startLoadPage()
  },

  startLoadPage: function() {
    newOpen.loadPage(newOpen.goToPageNum, 'setPageToolBar;needScroll')
    if (waterMarkConfig.showWaterMark == 'true') {
      commonFuncs.setWaterMarkBody(document)
    }
  },

  allPageFit: function() {
    var allPageSize = 0
    var pageTr
    var pageTrs = new Array(totalPageNum - 1);
    var eqCounter = 1;
    for (var i = 1; i <= totalPageNum; i++) {
      pageTr = document.getElementById('pageTr_' + i);
      pageTrs[(i-1)] = pageTr;
      allPageSize += pageTr.offsetHeight;
    }
    var tableContent = document.getElementById('viewerContent')
    if (
        tableContent != null &&
        window.frameElement != null &&
        window.frameElement.tagName == 'IFRAME' &&
        newOpen.looper == null
    ) {
      newOpen.looper = setInterval(function(){
        var newPageSize = 0;
        for(var index in pageTrs){
          newPageSize+=pageTrs[index].offsetHeight;
        }
        //console.log(newPageSize,allPageSize);
        if(newPageSize == allPageSize){
          //超过10秒页面高度没有变化,停止轮询
          //console.log(eqCounter);
          if((eqCounter++)>10){
            clearInterval(newOpen.looper);
            return;
          }
        }
        if(newPageSize>allPageSize){
          window.frameElement.style.height = (newPageSize + 16) + "px";
        }else{
          window.frameElement.style.height = (allPageSize + 16) + "px";
        }
        allPageSize = newPageSize;
      },1000);
    }
    if (waterMarkConfig.showWaterMark == 'true') {
      commonFuncs.setWaterMarkBody(document)
    }
  },

  scrollHandler: function() {
    var currentPageNum = parseInt(
        document.getElementById('currentPageIndex').value
    )

    var scrollPageY
    seajs.use('lui/jquery', function($) {
      scrollPageY = $(window).scrollTop()
      var currentScrollPageTr = document.getElementById(
          'pageTr_' + currentPageNum
      )
      var nextScrollPageTr =
          currentPageNum < totalPageNum
              ? document.getElementById('pageTr_' + (currentPageNum + 1))
              : null
      var lastScrollPageTr =
          currentPageNum > 1
              ? document.getElementById('pageTr_' + (currentPageNum - 1))
              : null

      // 应该是预加载功能
      if (currentScrollPageTr != null) {
        if (scrollPageY >= currentScrollPageTr.offsetTop + 2) {
          newOpen.loadPage(currentPageNum + 1, 'scrollHandler')
        }

        if (scrollPageY >= currentScrollPageTr.offsetTop + 2) {
          newOpen.loadPage(currentPageNum - 1, 'scrollHandler')
        }
      }

      if (nextScrollPageTr != null) {
        if (scrollPageY >= nextScrollPageTr.offsetTop + 2) {
          currentPageNum++
          newOpen.loadPage(currentPageNum, 'setPageToolBar')
          newOpen.goToPageNum = currentPageNum
          newOpen.preLoadPageNum = newOpen.getPreLoadPageNum()
        }
      }

      if (lastScrollPageTr != null) {
        if (
            scrollPageY >= lastScrollPageTr.offsetTop + 2 &&
            scrollPageY < currentScrollPageTr.offsetTop
        ) {
          currentPageNum--
          newOpen.loadPage(currentPageNum, 'setPageToolBar')
        }
      }

      if (waterMarkConfig.showWaterMark == 'true') {
        var maxNow = Math.max(
            document.body.scrollHeight,
            document.body.clientHeight
        )
        if (maxNow > newOpen.maxBodyHeight) {
          commonFuncs.setWaterMarkBody(document)
          newOpen.maxBodyHeight = maxNow
        }
      }
    })
  },
  loadPage: function(pageNum, otherParam) {
    newOpen.pageHeightsShould.put(pageNum, 'dataLoad_' + pageNum)
    if (!commonFuncs.isLoadPage(pageNum)) {
      var pageDiv = $('#pageDiv_' + pageNum)
      var dataFrameSrc = ''
      var pageTdInnerHTML = ''
      if (fileKeySufix == '-img') {
        pageTdInnerHTML = "<img style='pointer-events:none;' id='dataLoad_" + pageNum + "'/>"
      } else {
        dataFrameSrc =
            dataSrc +
            '?method=view&viewer=htmlviewer&fdId=' +
            fdId +
            '&filekey=' +
            commonFuncs.getFileName(pageNum) +
            '&pageNum=' +
            pageNum

        pageTdInnerHTML =
            "<iframe id='dataLoad_" +
            pageNum +
            "' style='border:0px;' height='0px' scrolling='no'></iframe>"
      }
      seajs.use('lui/jquery', function($) {
        pageDiv.html(pageTdInnerHTML)
      })
      seajs.use('lui/jquery', function($) {
        if (fileKeySufix == '-img') {
          $('#dataLoad_' + pageNum).load(function() {
            newOpen.setImg(pageNum, otherParam)
          })
          $('#dataLoad_' + pageNum).attr(
              'src',
              dataSrc +
              '?method=view&fdId=' +
              fdId +
              '&filekey=' +
              commonFuncs.getFileName(pageNum) +
              '&pageNum=' +
              pageNum
          )
        } else {
          // 加载完毕后设置宽高
          $('#dataLoad_' + pageNum).load(function() {
            newOpen.setFrame(pageNum, otherParam)
          })
          $('#dataLoad_' + pageNum).attr('src', dataFrameSrc)
        }
      })
    } else {
      if (commonFuncs.contains(otherParam, 'setPageToolBar', true)) {
        commonFuncs.setPageToolBar(pageNum)
      }
      if (commonFuncs.contains(otherParam, 'needScroll', true)) {
        newOpen.scrollToPage(pageNum)
      }
    }
  },
  scrollToPage: function(pageNum) {
    var pageTr = document.getElementById('pageTr_' + pageNum)
    window.scrollTo(0, pageTr.offsetTop)
  },

  setImg: function(pageNum, otherParam) {
    var objImg = document.getElementById('dataLoad_' + pageNum)
    newOpen.setImgSize(objImg, pageNum)
    newOpen.setImgAuthentication(objImg)
    if (pageNum < newOpen.preLoadPageNum) {
      newOpen.loadPage(pageNum + 1, otherParam)
    } else {
      commonFuncs.setPageToolBar(newOpen.goToPageNum)
      if (commonFuncs.contains(otherParam, 'needScroll', true)) {
        newOpen.scrollToPage(newOpen.goToPageNum)
      }
      if (showAllPage == 'true' && pageNum == totalPageNum) {
        newOpen.allPageFit()
      }
    }
  },
  setImgSize: function(objImg, pageNum) {
    var fHeight = 1122
    var fWidth = 793
    if (objImg.width > 100) {
      fWidth = objImg.width
    }
    if (objImg.height > 100) {
      fHeight = objImg.height
    }

    var parentFrame = parent.window.document.getElementById('mainFrame')
    if (parentFrame != null) {
      playerW = newOpen.getPlayerWidth(parentFrame)
      if (fWidth / playerW > 0.96) {
        seajs.use('lui/jquery', function($) {
          $(objImg)
              .parent()
              .css('width', playerW * 0.96)
        })
        newOpen.pageHeights.put(pageNum, (playerW * 0.96) / (fWidth / fHeight))
        newOpen.autoResizeImage(playerW * 0.96, 0, objImg)
      } else {
        seajs.use('lui/jquery', function($) {
          $(objImg)
              .parent()
              .css('width', fWidth)
        })

        seajs.use('lui/jquery', function($) {
          $(objImg).attr('width', fWidth)
        })
        seajs.use('lui/jquery', function($) {
          $(objImg).attr('height', fHeight)
        })
        newOpen.pageHeights.put(pageNum, fHeight)

        this.initSize(fWidth, fHeight)
      }
    } else {
      var availableWidth = newOpen.getAvailableWidth()
      if (fWidth / availableWidth > 0.96) {
        seajs.use('lui/jquery', function($) {
          $(objImg)
              .parent()
              .css('width', availableWidth * 0.96)
        })

        newOpen.pageHeights.put(
            pageNum,
            (availableWidth * 0.96) / (fWidth / fHeight)
        )
        newOpen.autoResizeImage(availableWidth * 0.96, 0, objImg)
      } else {
        seajs.use('lui/jquery', function($) {
          $(objImg)
              .parent()
              .css('width', fWidth)
        })
        seajs.use('lui/jquery', function($) {
          $(objImg).attr('width', fWidth)
        })
        seajs.use('lui/jquery', function($) {
          $(objImg).attr('height', fHeight)
        })
        newOpen.pageHeights.put(pageNum, fHeight)

        this.initSize(fWidth, fHeight)
      }

      if (newOpen.pageHeights.size() == 1) {
        newOpen.scrollToPage(pageNum)
      }
    }
  },
  getPlayerWidth: function(parentFrame) {
    return Number(parentFrame.width)
  },
  getAvailableWidth: function() {
    var temp
    try {
      temp = document.body
    } catch (e) {
      temp = document.documentElement
    }
    return temp.clientWidth
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

    this.initSize(ww, hh)
  },
  setImgAuthentication: function(objImg) {
    objImg.oncontextmenu = function() {
      return commonFuncs.onAuthentication()
    }
    objImg.ondragstart = function() {
      return commonFuncs.onAuthentication()
    }
  },
  setFrame: function(pageNum, otherParam) {
    var frameObj = document.getElementById('dataLoad_' + pageNum)
    var topWindow = Com_Parameter && Com_Parameter.top || frameObj.contentWindow.top
    if (topWindow.fullScreen == 'yes') {
      var frameDoc = frameObj.contentDocument || frameObj.Document
      seajs.use('lui/jquery', function($) {
        $(frameDoc).bind('keydown', commonFuncs.keyDownHandler)
      })
    }
    newOpen.setFrameBodyBackGround(frameObj)
    newOpen.setFrameAuthentication(frameObj)
    if (highFidelity == 'false') {
      newOpen.setFrameSize({ frameObj: frameObj, pageNum: pageNum })
    }
    if (pageNum < newOpen.preLoadPageNum) {
      newOpen.loadPage(pageNum + 1, otherParam)
    } else {
      commonFuncs.setPageToolBar(newOpen.goToPageNum)
      if (commonFuncs.contains(otherParam, 'needScroll', true)) {
        newOpen.scrollToPage(newOpen.goToPageNum)
      }
      if (showAllPage == 'true' && pageNum == totalPageNum) {
        newOpen.allPageFit()
      }
    }
  },
  setFrameAuthentication: function(frameObj) {
    try {
      var bodyEle = frameObj.contentDocument.body || frameObj.Document.body
      bodyEle.oncopy = function() {
        return commonFuncs.onAuthentication()
      }
      bodyEle.oncontextmenu = function() {
        return commonFuncs.onAuthentication()
      }
      bodyEle.onselectstart = function() {
        return commonFuncs.onAuthentication()
      }
      bodyEle.onclick = function(e) {
        if (typeof e == 'undefined') {
          return
        }
        var target = e.target,
            currentTarget = e.currentTarget,
            _href,
            i = 1
        while (target && i <= 2 && target !== currentTarget) {
          if (target.tagName.toLowerCase() === 'a') {
            var href = decodeURI(target.getAttribute('href'))
            if (href) {
              var ii = href.indexOf('?')
              if (ii > -1) {
                href = href.slice(0, ii)
              }
              if (/(doc|xls|ppt|docx|xlsx|pptx|et|ett|wps|wpt)$/i.test(href)) {
                _href = href
              }
            }
            break
          }
          target = target.parentNode
          i++
        }
        if (_href) {
          e.preventDefault()
          LUI.fire(
              {
                type: 'topic',
                name: 'asposeClick',
                data: {
                  hrefText: _href
                }
              },
              parent || window
          )
        }
      }
      if (!commonFuncs.onAuthentication()) {
        bodyEle.style =
            '-moz-user-select:none;-webkit-user-select: none;-ms-user-select: none;-khtml-user-select: none;user-select: none;'
      }
      var doc = frameObj.contentDocument || frameObj.Document
      seajs.use('lui/jquery', function($) {
        $(doc)
            .find('img')
            .each(function() {
              this.oncontextmenu = function() {
                return false
              }
              this.ondragstart = function() {
                return false
              }
            })
      })
      seajs.use('lui/jquery', function($) {
        $(doc)
            .find('image')
            .each(function() {
              this.oncontextmenu = function() {
                return false
              }
              this.ondragstart = function() {
                return false
              }
            })
      })
    } catch (e) {
      //
    }
  },
  setFrameSize: function(frameInfos) {
    var frameObj = frameInfos.frameObj
    var pageNum = frameInfos.pageNum
    var frameSize = frameInfos.frameSize
    if ('undefined' != typeof frameSize) {
    } else {
      if (highFidelity == 'true') {
        return
      }
      frameSize = newOpen.getFrameBodySize(frameObj, pageNum)
    }
    if (frameObj && !window.opera) {
      var fHeight = frameSize.height
      var fWidth = frameSize.width
      var parentFrame = parent.window.document.getElementById('mainFrame')
      var scaleSize
      if (parentFrame != null) {
        playerW = 872
        if (fWidth / playerW > 0.96) {
          scaleSize = (playerW * 0.96) / fWidth
          newOpen.zoomEle(frameObj, scaleSize, scaleSize)
          seajs.use('lui/jquery', function($) {
            $(frameObj)
                .parent()
                .css('width', playerW * 0.96)
            $(frameObj)
                .parent()
                .css('height', (playerW * 0.96) / (fWidth / fHeight))
          })
          newOpen.pageHeights.put(
              pageNum,
              (playerW * 0.96) / (fWidth / fHeight)
          )
        } else {
          newOpen.unZoomEle(frameObj)
          seajs.use('lui/jquery', function($) {
            $(frameObj)
                .parent()
                .css('width', fWidth)
            $(frameObj)
                .parent()
                .css('height', fHeight)
          })
          newOpen.pageHeights.put(pageNum, fHeight)
        }
      } else {
        var availableWidth = newOpen.getAvailableWidth()
        if (fWidth / availableWidth > 0.96) {
          scaleSize = (availableWidth * 0.96) / fWidth
          newOpen.zoomEle(frameObj, scaleSize, scaleSize)
          seajs.use('lui/jquery', function($) {
            $(frameObj)
                .parent()
                .css('width', availableWidth * 0.96)
            $(frameObj)
                .parent()
                .css('height', (availableWidth * 0.96) / (fWidth / fHeight))
          })
          newOpen.pageHeights.put(
              pageNum,
              (availableWidth * 0.96) / (fWidth / fHeight)
          )
        } else {
          newOpen.unZoomEle(frameObj)
          seajs.use('lui/jquery', function($) {
            $(frameObj)
                .parent()
                .css('width', fWidth)
            $(frameObj)
                .parent()
                .css('height', fHeight)
          })
          newOpen.pageHeights.put(pageNum, fHeight)
        }
      }
      frameObj.width = fWidth
      frameObj.height = fHeight

      this.initSize(fWidth, fHeight)

      if (newOpen.pageHeights.size() == 1) {
        newOpen.scrollToPage(pageNum)
      }
    }
  },

  // 第一次获取框高，则未每个iframe外壳设置默认宽高参数
  initSize: function(fWidth, fHeight) {
    if (newOpen.pageHeights.size() == 1) {
      $('.pageDiv').css({
        width: fWidth,
        height: fHeight,
        visibility: 'visible'
      })
    }
  },
  setFrameBodyBackGround: function(frameObj) {
    try {
      var frameDoc = frameObj.contentDocument || frameObj.Document
      var bodyDoc = frameDoc.body
      seajs.use('lui/jquery', function($) {
        $(bodyDoc).css('margin', '0px')
      })
      seajs.use('lui/jquery', function($) {
        $(bodyDoc)
            .find('.awpage')
            .css({ background: 'white', 'margin-top': '0px' })
      })
    } catch (e) {
      //
    }
  },
  getFrameBodySize: function(frameObj, pageNum) {
    var bh = 0
    var bw = 0
    if (commonFuncs.contains(viewerStyle, 'yozo', true)) {
      var resultWH
      var pageWhInfo = viewerParam.pageWh
      var okFind = false
      for (var key in pageWhInfo) {
        var pages = pageWhInfo[key]
        if (typeof pages == 'string') {
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
      var tmpResult = resultWH.split(',')
      bw = parseInt(tmpResult[0])
      bh = parseInt(tmpResult[1])
    }
    if (commonFuncs.contains(viewerStyle, 'aspose', true)) {
      if (highFidelity == 'true') {
        //由页面内容触发
      } else {
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
            if (commonFuncs.contains(viewerStyle, 'ppt', true)) {
              offsetW = chs[i].scrollWidth
              offsetH = chs[i].scrollHeight
            }
            var tbh = ('undefined' != typeof top ? top : 0) + offsetH
            var tbw = ('undefined' != typeof left ? left : 0) + offsetW
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
    }

    return { width: bw, height: bh }
  },
  zoomEle: function(el, xScale, yScale) {
    seajs.use('lui/jquery', function($) {
      var browName = commonFuncs.getBrowser()
      if ('Chrome' == browName || 'Safari' == browName) {
        $(el).css({
          '-webkit-transform': 'scale(' + xScale + ',' + yScale + ')',
          '-webkit-transform-origin': '0px 0px'
        })
      } else if ('IE' == browName) {
        $(el).css('zoom', xScale)
      } else {
        $(el).css({
          transform: 'scale(' + xScale + ',' + yScale + ')',
          transformOrigin: '0px 0px'
        })
      }
    })
  },
  unZoomEle: function(el) {
    seajs.use('lui/jquery', function($) {
      var browName = commonFuncs.getBrowser()
      if ('Chrome' == browName || 'Safari' == browName) {
        $(el).css({
          '-webkit-transform': 'scale(' + 1 + ',' + 1 + ')',
          '-webkit-transform-origin': '0px 0px'
        })
      } else if ('IE' == browName) {
        $(el).css('zoom', 1)
      } else {
        $(el).css({
          transform: 'scale(' + 1 + ',' + 1 + ')',
          transformOrigin: '0px 0px'
        })
      }
    })
  },
  goTo: function(pageNum) {
    newOpen.goToPageNum = pageNum
    newOpen.preLoadPageNum = newOpen.getPreLoadPageNum()
    currentPage = pageNum
    newOpen.loadPage(pageNum, 'setPageToolBar;needScroll;goTo')
  },
  getPreLoadPageNum: function() {
    return newOpen.goToPageNum > totalPageNum
        ? totalPageNum
        : newOpen.goToPageNum
  }
}

function goTo(pageNum) {
  newOpen.goTo(pageNum)
}


newOpen.initialNewOpenViewer();
