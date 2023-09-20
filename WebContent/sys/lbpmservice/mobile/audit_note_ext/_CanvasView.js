define([
  "dojo/_base/declare",
  "dojox/mobile/View",
  "dojo/query",
  "dojo/dom",
  "dojox/mobile/TransitionEvent",
  "dijit/registry",
  "dojo/_base/window",
  "mui/util",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/ready",
  "mui/dialog/Tip",
  "mui/device/adapter",
  "dojox/mobile/viewRegistry",
  "mui/i18n/i18n!sys-lbpmservice:mui.hand",
], function(
  declare,
  View,
  query,
  dom,
  TransitionEvent,
  registry,
  win,
  util,
  lang,
  array,
  domStyle,
  ready,
  Tip,
  adapter,
  viewRegistry,
  Msg
) {
  return declare("sys.lbpmservice.mobile.audit_note_ext._CanvasView", [View], {
    fdKey: "",

    fdAttType: "pic",

    fdModelId: "",

    fdModelName: "",

    lbpmViewName: null,
    
    uploadStream: true,

    canvas: null,

    fileIds: [],

    _working: false,

    //是否需要自适应画布
    isFitCanvas: true,

    startup: function() {
      this.inherited(arguments);
      this.fileIds = [];
      query(this.domNode).style("display", "none");
      util.addTopView(this.domNode);
      this.findAppBar();
    },

    // 搜索fixed为bottom或top的节点
    findAppBar: function() {
      if (this.domNode.parentNode) {
        for (
          var i = 0, len = this.domNode.parentNode.childNodes.length;
          i < len;
          i++
        ) {
          c = this.domNode.parentNode.childNodes[i];
          this.checkFixedBar(c);
        }
      }
    },

    checkFixedBar: function(node) {
      if (
        node.nodeType === 1 &&
        node.getAttribute("data-dojo-type") == "mui/fixed/Fixed"
      ) {
        this.fixedHeader = node;
      }
    },

    bindEvents: function() {
      var eventPrefix = "attachmentObject_" + this.fdKey + "_";
      this.subscribe(eventPrefix + "success", "AttUploadSuccess");
      this.subscribe(eventPrefix + "success_ereb", "AttUploadSuccessEreb");
      this.subscribe(eventPrefix + "fail", "AttUploadError");
      this.subscribe("sys/lbpmservice/select/pen", "selectPenChange");

      registry
        .byNode(query(".auditNoteHandlerCancel", this.domNode)[0])
        .on("click", lang.hitch(this, this.hideAuditNoteHandlerView));
      registry
        .byNode(query(".auditNoteHandlerClear", this.domNode)[0])
        .on("click", lang.hitch(this, this.auditNoteViewClear));
      registry
        .byNode(query(".auditNoteHandlerSave", this.domNode)[0])
        .on("click", lang.hitch(this, this.auditNoteViewSave));
      if (!this.isXformAuditNote) {
        ready(
          lang.hitch(this, function() {
            var self = this;
            setTimeout(function() {
              //保证附件提交是在校验的后边，避免出现重复附件提交
              Com_Parameter.event["submit"].push(
                lang.hitch(self, self.submitEvent)
              );
            }, 200);
          })
        );
      }
    },

    submitEvent: function() {
      if (!this.uploaded) {
        if (this.fileIds.length > 0) {
          var attachmentObj = window.AttachmentList[this.fdKey];
          var attachmentObj_rawFile =
            window.AttachmentList[this.fdKey + "_rawFile"];
          if (attachmentObj != null) {
            return array.every(
              this.fileIds,
              function(fileId) {
                var fdid = attachmentObj.registFile({
                  filekey: fileId,
                  name: "image.png"
                });
                return fdid != null;
              },
              this
            );
          }
        }
        this.uploaded = true;
      }
      return true;
    },

    resize: function() {
      this.inherited(arguments);
      this.resizeCanvas(false);
    },

    releaseCanvas: function() {
      this.canvas = null;
      query(".canvasDiv", this.domNode).empty();
    },

    resizeCanvas: function(flag) {
      var canvasDiv = query(".canvasDiv", this.domNode)[0];
      var auditNoteHandlerToolbar = query(
        ".auditNoteHandlerToolbar",
        this.domNode
      )[0];
      var auditNoteHandlerHeader = query(
        ".auditNoteHandlerHeader",
        this.domNode
      )[0];
      var screensize = util.getScreenSize();
      var h =
        screensize.h -
        auditNoteHandlerToolbar.offsetHeight -
        auditNoteHandlerHeader.offsetHeight;
      if (this.fixedHeader) {
        h = h - this.fixedHeader.offsetHeight;
      }
      canvasDiv.style.height = h + "px";
      var width = canvasDiv.offsetWidth;
      var height = canvasDiv.offsetHeight;
      
      /*
       * 去掉 洪健 2020-3-26 横屏重新计算手写板高度
       * if (!flag) {
        return;
      }*/
      
      
      var doResize = false;
      var tmpCanvas = document.createElement("canvas");
      if (!this.canvas) {
        canvasDiv.appendChild(tmpCanvas);
        domStyle.set(tmpCanvas, { width: "100%", height: "100%" });
      } else {
        tmpCanvas = this.canvas.canvas;
      }
      var width = canvasDiv.offsetWidth;
      var height = canvasDiv.offsetHeight;
      var canvasWidth = tmpCanvas.width,
        canvasHeight = tmpCanvas.height;
      if (canvasWidth != width || canvasHeight != height) {
        tmpCanvas.width = width;
        tmpCanvas.height = height;
        doResize = true;
      }

      if (!this.canvas) {
        var self = this;
        require(["sys/lbpmservice/mobile/lib/signaturePad"], function(Canvas) {
          self.canvas = new Canvas(tmpCanvas,{backgroundColor:'#fff'});
          if (doResize) {
            self.dowidthchange();
            self.docolorchange();
          }
        });
        return;
      }

      if (doResize) {
        this.dowidthchange();
        this.docolorchange();
      }
    },

    selectPenChange: function(evt) {
      if (evt.name == "selectPenWidth") {
        this.dowidthchange();
      }
      if (evt.name == "selectPenColor") {
        this.docolorchange();
      }
    },

    getLineWidth: function() {
      var lbxPenWidth = query("[name='selectPenWidth']", this.domNode)[0];
      var penWidth = lbxPenWidth.value;
      return penWidth;
    },

    getStrokeStyle: function() {
      var lbxPenColor = query("[name='selectPenColor']", this.domNode)[0];
      var color = lbxPenColor.value;
      return color;
    },

    dowidthchange: function() {
      if (this.canvas) {
        this.canvas.maxWidth = this.getLineWidth();
      }
    },

    docolorchange: function() {
      if (this.canvas) {
        this.canvas.penColor = this.getStrokeStyle();
      }
    },

    auditNoteViewClear: function() {
      this.canvas.clear();
    },

    showAuditNoteHandlerView: function() {
    	
      new TransitionEvent(win.body(), {
        moveTo: this.id,
        transition: "flip"
      }).dispatch();
    },

    onAfterTransitionIn: function() {
      this.inherited(arguments);
      this.resize();
    },

    hideAuditNoteHandlerView: function() {
      if (dom.byId("_flowNav"))
        domStyle.set(dom.byId("_flowNav"), { display: "block" });
      var viewName = this.lbpmViewName ? this.lbpmViewName : "lbpmView";
      if(this.showType == 'dialog'){//流程时弹窗，弹窗时需要返回到前一页
    	  viewName = this.backTo;
    	  if(!viewName){
    		 var view = viewRegistry.getEnclosingView(this.domNode);
			 view = viewRegistry.getParentView(view);
			 viewName = view ? view.id : "scrollView";
    	  }
      }
      new TransitionEvent(win.body(), {
        moveTo: viewName,
        transition: "flip",
        transitionDir: -1
      }).dispatch();
    },

    getResult: function(result) {
      var rtn = {};
      array.forEach(result.firstChild.childNodes, function(child) {
        if (child.nodeType == 1) {
          rtn[child.nodeName] = child.firstChild.nodeValue;
        }
      });
      return rtn;
    },

    workDone: function() {
      this.defer(function() {
        this._working = false;
        if (this.process) {
          this.process.hide();
        }
      }, 300);
    },

    // 自适应画布，只生成有内容的区域图片
    fitCanvas: function() {
      var signObj = this.canvas,
        datas = signObj._data;
      var minX, minY, maxX, maxY;

      array.forEach(
        datas,
        function(data) {
          var point = data.points;
          array.forEach(
            point,
            function(p) {
              var x = p.x;
              y = p.y;

              if (!minX || !maxX) {
                minX = maxX = x;
              }

              if (!minY || !maxY) {
                minY = maxY = y;
              }

              if (x < minX) {
                minX = x;
              }
              if (y < minY) {
                minY = y;
              }
              if (y > maxY) {
                maxY = y;
              }
              if (x > maxX) {
                maxX = x;
              }
            },
            this
          );
        },
        this
      );

      //边缘设置
      var pObj = this.canvasEdgeSet({'minY':minY,'maxY':maxY});
      if(pObj){
    	  minY = pObj.minY;
          maxY = pObj.maxY;
      }
      
      var canvasX = maxX - minX,
        canvasY = maxY - minY;

      var canvas = document.createElement("canvas");
      var ctx = canvas.getContext("2d");
      canvas.width = canvasX + 20;
      canvas.height = canvasY + 20;
      ctx.drawImage(
        signObj.canvas,
        minX,
        minY,
        canvasX,
        canvasY,
        10,
        10,
        canvasX,
        canvasY
      );
      return canvas;
    },
    
    //解决上下边界超出导致的空白问题
    canvasEdgeSet:function(pObj){
    	if(!pObj){
    		return;
    	}
    	if(pObj.minY && pObj.minY < 0){
    		pObj.minY = 0;
        }
        if(pObj.maxY && pObj.maxY > this.canvas.canvas.height){
        	pObj.maxY = this.canvas.canvas.height;
        }
        return pObj;
    },
    
    // 自适应画布，只生成有内容的区域图片，只针对Y轴进行截取
    fitCanvasY: function() {
      var signObj = this.canvas,
        datas = signObj._data;
      var minY, maxY;

      array.forEach(
        datas,
        function(data) {
          var point = data.points;
          array.forEach(
            point,
            function(p) {
              var y = p.y;

              if (!minY || !maxY) {
                minY = maxY = y;
              }

              if (y < minY) {
                minY = y;
              }
              if (y > maxY) {
                maxY = y;
              }
            },
            this
          );
        },
        this
      );

      //边缘设置
      var pObj = this.canvasEdgeSet({'minY':minY,'maxY':maxY});
      if(pObj){
    	  minY = pObj.minY;
          maxY = pObj.maxY;
      }
      
      var canvasY = maxY - minY;

      var canvas = document.createElement("canvas");
      var ctx = canvas.getContext("2d");
      
      canvas.width = signObj.canvas.width;
      canvas.height = canvasY + 20;
     
      ctx.drawImage(
        signObj.canvas,
        0,
        minY,
        signObj.canvas.width,
        canvasY,
        10,
        10,
        signObj.canvas.width,
        canvasY
      );
      return canvas;
    },

    auditNoteViewSave: function() {
      if (dom.byId("_flowNav"))
        domStyle.set(dom.byId("_flowNav"), { display: "block" });
      if (this._working) return;
      if(this.canvas && this.canvas._data && this.canvas._data.length > 0){
          this._working = true;
          this.process = Tip.processing().show();

          var canvas = this.canvas;
          if(this.isFitCanvas){
            canvas = this.fitCanvas();
          }
          adapter.uploadFile({
            options: this,
            evt: { dataURL: canvas.toDataURL("image/png") }
          });
      }else{
        Tip.tip({text:Msg["mui.hand.empty.tip"]}).show();
      }
    },

    AttUploadSuccessEreb: function(srcObj, evt) {
      query("#imgUl li").remove();
      this.fileIds = [];

      if (evt == null || evt.file == null) {
        this.workDone();
        return;
      }
      var file = evt.file;
      this.fileIds.push(file.filekey);
      this.UploadSuccessed(srcObj, evt);
      this.workDone();
    },

    AttUploadSuccess: function(srcObj, evt) {
      if (evt == null || evt.file == null) {
        this.workDone();
        return;
      }
      var file = evt.file;
      this.fileIds.push(file.filekey);
      this.UploadSuccessed(srcObj, evt);
      this.hideAuditNoteHandlerView();
      this.auditNoteViewClear();
      this.workDone();
    },

    UploadSuccessed: function(srcObj, evt) {},

    AttUploadError: function() {
      this.workDone();
    }
  });
});
