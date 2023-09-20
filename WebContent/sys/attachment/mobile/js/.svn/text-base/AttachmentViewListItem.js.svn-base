define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "mui/util",
    "dojo/topic",
    "dojo/_base/lang",
    "dojo/dom-style",
    "mui/dialog/Tip",
    "mui/i18n/i18n!sys-attachment",
    "dojox/mobile/ProgressBar",
    "sys/attachment/mobile/js/_AttachmentItem",
    "sys/attachment/mobile/js/_AttachmentLinkItem",
    "mui/device/adapter",
    "sys/attachment/mobile/js/_AttachmentViewOnlineMixin",
    "dojo/request"
], function (
    declare,
    domConstruct,
    util,
    topic,
    lang,
    domStyle,
    Tip,
    Msg,
    ProgressBar,
    AttachmentItem,
    AttachmentLinkItem,
    adapter,
    _AttachmentViewOnlineMixin,
    request
) {
    //普通附件项展示类
    return declare(
        "sys.attachment.mobile.js.AttachmentViewListItem",
        [AttachmentItem, AttachmentLinkItem, _AttachmentViewOnlineMixin],
        {
            //-1准备上传,	0上传出错,	1上传中,		2上传成功 ,  3 表示阅读状态
            status: 3,
            thumbUrl:
                "/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId=!{fdId}",

            // 从view.jsp移动到此处
            gennerateDownloadUrl: function () {
                if (!this.href && this.fdId) {
                    this.href =
                        "/sys/attachment/sys_att_main/sysAttMain.do?method=viewDownload&fdId=" +
                        this.fdId;
                    if (this.canDownload) {
                        this.href =
                            "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=" +
                            this.fdId;
                    }
                    if (this.downLoadNoRight) {
                        this.href = "/third/pda/attdownload.jsp?open=1&fdId=" + this.fdId;
                    }
                }
            },

            buildItem: function () {
                this.gennerateDownloadUrl();
                if (!this.justDiaplayName) {
                    var itemL = domConstruct.create(
                        "div",
                        {
                            className: "muiAttachmentItemL " + this.getAttContainerType()
                        },
                        this.containerNode
                    );
                    this.attItemIcon = domConstruct.create(
                        "div",
                        {
                            className: "muiAttachmentItemIcon"
                        },
                        itemL
                    );
                    var iconClass = this.getAttTypeClass();
                    if (this.icon != null && this.icon != "") {
                        iconClass = this.icon;
                    }
                    // #160571  图片较大时加载缓慢导致闪退，不走查看权限展示小图片，只展示小图标
                    /*if (this.getType() == "img") {
                        if (this.canRead) {
                            if (!this.thumb) {
                                this.thumb = this.thumbUrl.replace("!{fdId}", this.fdId);
                            }

                            if (this.thumb) {
                                domConstruct.create(
                                    "img",
                                    {
                                        align: "middle",
                                        src: util.formatUrl(this.thumb)
                                    },
                                    this.attItemIcon
                                );
                            } else {
                                domConstruct.create(
                                    "img",
                                    {
                                        align: "middle",
                                        src: util.formatUrl(this.href)
                                    },
                                    this.attItemIcon
                                );
                            }
                        } else {
                            domConstruct.create(
                                "i",
                                {
                                    className: iconClass
                                },
                                this.attItemIcon
                            );
                        }
                    }*/
                    domConstruct.create(
                        "i",
                        {
                            className: iconClass
                        },
                        this.attItemIcon
                    );
                }
                var itemC = domConstruct.create(
                    "div",
                    {
                        className: "muiAttachmentItemC"
                    },
                    this.containerNode
                );
                domConstruct.create(
                    "span",
                    {
                        className: "muiAttachmentItemName",
                        innerHTML: this.name
                    },
                    itemC
                );
                if (this.size != null && this.size != "") {
                    domConstruct.create(
                        "span",
                        {
                            className: "muiAttachmentItemSize",
                            innerHTML: this.formatFileSize()
                        },
                        itemC
                    );
                }
                if (this.status != 3) {
                    var progress = domConstruct.create(
                        "div",
                        {
                            className: "muiAttachmentItemProgress"
                        },
                        itemC
                    );
                    this.progressBar = new ProgressBar({
                        maximum: 100,
                        value: "0%",
                        label: "0%"
                    });
                    progress.appendChild(this.progressBar.domNode);
                    this.progressBar.startup();
                }
                if (this.href && this.edit == false) {
                    this.connect(itemC, "click", lang.hitch(this._onItemClick));
                    itemC.dojoClick = true;
                }

                this.itemR = domConstruct.create(
                    "div",
                    {
                        className: "muiAttachmentItemR"
                    },
                    this.containerNode
                );
                if (this.edit == false) {
                    if(this.canDownload || this.canRead) {
                        domConstruct.create(
                            "i",
                            {
                                className: "muiAttachmentItemExpand fontmuis muis-more"
                            },
                            this.itemR
                        );
                        this.connect(this.itemR, "click", lang.hitch(this._onMoreItemClick));
                    }
                } else {
                    var prop = {
                        className: "muiAttachmentItemDel",
                        innerHTML: "删除",
                        style: {
                            display: "none"
                        }
                    };
                    if (this.status == 3 || this.status == 2) {
                        prop.style.display = "block";
                    }
                    this.delDom = domConstruct.create("i", prop, this.itemR);

                    this.connect(
                        this.delDom,
                        "click",
                        lang.hitch(this, function (evt) {
                            topic.publish("attachmentObject_" + this.key + "_del", this, {
                                widget: this
                            });
                            if (evt.stopPropagation) evt.stopPropagation();
                            if (evt.cancelBubble) evt.cancelBubble = true;
                            if (evt.preventDefault) evt.preventDefault();
                            if (evt.returnValue) evt.returnValue = false;
                        })
                    );
                }
            },

            _downLoad: function () {
                if (this.canDownload) {
                    // 记录下载日志
                    var logUrl =
                        "/sys/attachment/sys_att_download_log/sysAttDownloadLog.do?method=addDownlodLog&downloadType=manual&fdId=" +
                        this.fdId;
                    request.post(util.formatUrl(logUrl, true));
                    adapter.download({
                        fdId: this.fdId,
                        name: this.name,
                        type: this.type,
                        href: this.href
                    });
                } else {
                    Tip.tip({
                        icon: "mui mui-warn",
                        text: "无权限下载文件"
                    });
                }
            },

            downloadToDing: function () {
                var _this = this;
                var timestamp = (new Date()).getTime();

                var isSuccess = adapter.isSuccess(this.fdId, timestamp);

                if (this.size >= 8 * 1024 * 1024 && !isSuccess) {
                    var time = 0;
                    var overSizeMsg = "";
                    if (this.size < 15 * 1024 * 1024) {
                        time = Math.round(this.size / (15 * 1024 * 1024) * 60);
                        overSizeMsg = Msg['mui.sysAttMain.download.oversize.time'] + time + Msg['mui.sysAttMain.download.oversize.time.sec'];
                    } else {
                        time = Math.round(this.size / (15 * 1024 * 1024));
                        overSizeMsg = Msg['mui.sysAttMain.download.oversize.time'] + time + Msg['mui.sysAttMain.download.oversize.time.min'];
                    }

                    Tip.tip({
                        text: overSizeMsg
                    });

                    setTimeout(function () {
                        adapter.downloadToDing({
                            fdId: _this.fdId,
                            name: _this.name,
                            type: _this.type,
                            href: _this.href,
                            size: _this.size,
                            time: timestamp
                        }, false);
                    }, 50);

                } else {
                    var processing = Tip.processing();//加载中...
                    processing.show();

                    setTimeout(function () {
                        adapter.downloadToDing({
                            fdId: _this.fdId,
                            name: _this.name,
                            type: _this.type,
                            href: _this.href,
                            size: _this.size,
                            time: timestamp
                        }, true);
                        processing.hide();
                    }, 50);
                }
            },

            _onItemClick: function () {
                if(this.canRead || this.canDownload){
                    this._onClick(arguments);
                    if (this.inherited(arguments)) return;
                    this._downLoad();
                }else{
                    Tip.tip({
                        icon: "mui mui-warn",
                        text: "无权限查看"
                    });
                }
            },

            changeProgress: function (val) {
                if (this.progressBar) {
                    var percent = "";
                    if (typeof val == "string") {
                        percent =
                            val.indexOf("%") != -1
                                ? "" + (parseFloat(val) * 100) / this.size + "%"
                                : val;
                    } else {
                        percent = "" + (val * 100) / this.size + "%";
                    }
                    this.progressBar.set("value", percent);
                    this.progressBar.set("label", percent);
                }
            },

            uploadError: function (msg) {
                if (this.progressBar) {
                    this.progressBar.set("value", "0%");
                    this.progressBar.set("label", msg);
                }
                if (this.delDom) {
                    domStyle.set(this.delDom, {
                        display: "block"
                    });
                }
            },

            uploaded: function () {
                if (this.progressBar) {
                    this.progressBar.set("value", "100%");
                    this.progressBar.set("label", "上传成功");
                }
                if (this.delDom) {
                    domStyle.set(this.delDom, {
                        display: "block"
                    });
                }
            }
        }
    );
});
