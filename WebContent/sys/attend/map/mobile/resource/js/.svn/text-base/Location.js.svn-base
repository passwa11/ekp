define([
    "dojo/_base/declare",
    "mui/form/_InputBase",
    "dojo/dom-construct",
    "mui/util",
    "./LocationStrategy",
    "mui/i18n/i18n!sys-mobile",
    "./common/MapUtil","mui/device/adapter","mui/dialog/Tip", "dojo/topic"
], function(declare, _InputBase, domConstruct, util, strategys, Msg, MapUtil,adapter,Tip, topic) {
    var clz = declare("sys.attend.location", [_InputBase], {
        edit: true,

        value: "",

        coordinateValue: null,

        detailValue: null,
        provinceValue : null,
        cityValue :null,
        districtValue :null,

        strategy: "bmap", //默认使用百度地图
        mapKey: MapUtil.bMapKey,
        mapKeyPc: null,
        mapKeyName: null,
        baseClass: "muiFormEleWrap muiFormLocation",
        inputClass: "muiInput",
        isShowList: true,
        isShowSearch: true,
        isShowPoi: true,

        opt: true,

        postMixInProperties: function() {
            var mapType = dojoConfig.map.mapType
            if (mapType == "qmap") {
                this.strategy = "qmap"
                this.mapKey = dojoConfig.map.qMapKey
                this.mapKeyName = dojoConfig.map.qMapKeyName;
            } else if (mapType == "bmap") {
                this.strategy = "bmap"
                this.mapKey = dojoConfig.map.bMapKey
            } else if (mapType == "amap") {
                this.strategy = "amap";
                this.mapKey = dojoConfig.map.aMapKey;
                this.mapKeyPc = dojoConfig.map.aMapKeyPc;
            }
        },

        buildRendering: function() {
            util.loadCSS("/sys/attend/map/mobile/resource/css/location.css")
            this.subscribe("sys/attend/map/edit.finished", "_setLocationValues")
            this.inherited(arguments)
        },
        startup: function() {
            this.inherited(arguments)
            this.initDefaultValue();
        },

        optText: "定位",

        buildOptIcon: function(optContainer) {
            this.inherited(arguments)
            if(optContainer) {
                this.connect(optContainer, "click", "_onIconClick")
                optContainer.dojoClick = true
            }
        },
        buildViewIcon: function(optContainer) {
            if(optContainer) {
                this.connect(optContainer, "click", "_onIconClick")
                optContainer.dojoClick = true
            }
        },

        buildEdit: function() {
            var placeholder =
                Msg["mui.form.please.input"] + (this.subject ? this.subject : "")
            if (!this.placeholder) this.placeholder = placeholder
            this.contentMapNode = domConstruct.create(
                "div",
                {className: "muiMapInput muiSelInput"},
                this.valueNode
            )
            this.nameNode = domConstruct.create(
                "input",
                {
                    name: this.propertyName,
                    className: this.inputClass,
                    placeholder: this.placeholder || placeholder
                },
                this.contentMapNode
            )
            if(this.isModify=='false'){
                //不允许修改
                this.nameNode.readOnly = true;
            }

            this.coordinateNode = domConstruct.create(
                "input",
                {
                    name: this.propertyCoordinate,
                    type: "hidden"
                },
                this.contentMapNode
            )
            if (this.propertyDetail) {
                this.detailNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyDetail,
                        type: "hidden"
                    },
                    this.contentMapNode
                )
            }
            if (this.propertyProvince) {
                this.provinceNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyProvince,
                        type: "hidden"
                    },
                    this.contentMapNode
                )
            }
            if (this.propertyCity) {
                this.cityNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyCity,
                        type: "hidden"
                    },
                    this.contentMapNode
                )
            }
            if (this.propertyDistrict) {
                this.districtNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyDistrict,
                        type: "hidden"
                    },
                    this.contentMapNode
                )
            }
            this.connect(this.nameNode, "blur", "_onBlur")
            this.connect(this.contentMapNode, "blur", "_onBlur")
        },

        buildView: function() {
            this.nameTxtNode = domConstruct.create(
                "div",
                {
                    innerHTML: this.value,
                    className: "muiMapInput muiInput_View"
                },
                this.valueNode
            );
            this.nameNode = domConstruct.create(
                "input",
                {
                    name: this.propertyName,
                    type: "hidden"
                },
                this.valueNode
            )
            this.coordinateNode = domConstruct.create(
                "input",
                {
                    name: this.propertyCoordinate,
                    type: "hidden"
                },
                this.valueNode
            )
            if (this.propertyDetail) {
                this.detailNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyDetail,
                        type: "hidden"
                    },
                    this.valueNode
                )
            }
            if (this.propertyProvince) {
                this.provinceNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyProvince,
                        type: "hidden"
                    },
                    this.valueNode
                )
            }
            if (this.propertyCity) {
                this.cityNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyCity,
                        type: "hidden"
                    },
                    this.valueNode
                )
            }
            if (this.propertyDistrict) {
                this.districtNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyDistrict,
                        type: "hidden"
                    },
                    this.valueNode
                )
            }
            if (this.coordinateValue) {
                this.buildViewIcon(this.nameTxtNode);
            }
        },

        buildReadOnly: function() {
            this.buildView()
        },

        buildHidden: function() {
            this.nameNode = domConstruct.create(
                "input",
                {
                    name: this.propertyName,
                    type: "hidden"
                },
                this.valueNode
            )
            this.coordinateNode = domConstruct.create(
                "input",
                {
                    name: this.propertyCoordinate,
                    type: "hidden"
                },
                this.valueNode
            )
            if (this.propertyDetail) {
                this.detailNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyDetail,
                        type: "hidden"
                    },
                    this.valueNode
                )
            }
            if (this.propertyProvince) {
                this.provinceNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyProvince,
                        type: "hidden"
                    },
                    this.valueNode
                )
            }
            if (this.propertyCity) {
                this.cityNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyCity,
                        type: "hidden"
                    },
                    this.valueNode
                )
            }
            if (this.propertyDistrict) {
                this.districtNode = domConstruct.create(
                    "input",
                    {
                        name: this.propertyDistrict,
                        type: "hidden"
                    },
                    this.valueNode
                )
            }
        },

        _onIconClick: function() {
            this.openLocationDialog()
        },

        openLocationDialog: function() {
            var self = this
            var _strategy = strategys[this.strategy]
            if (_strategy) {
                require([_strategy], function(strategy) {
                    if (strategy.init) {
                        strategy.init(self)
                    }
                })
            }
        },
        initDefaultValue :function(){
            var self = this;
            if (!this.showStatus){
                this.showStatus = Com_GetUrlParameter(window.location,'method');
            }
            if(this.defaultValue == 'PERSON_POSITION' && this.showStatus != 'view') {
                // 监听初始化回调事件（防止同一个页面有多个控件时，并发请求当前位置）
                topic.subscribe("MAP_INIT_DEFAULT_VALUE", function(event, result) {
                    var coordType = result.coordType;
                    var address = result.address;
                    var point = result.point;
                    self.set("value", result.title);
                    self.set(
                        "coordinateValue",
                        point && point.lat
                            ? MapUtil.formatCoord(point, coordType)
                            : ""
                    );
                    self.set("detailValue", address);
                    self.set("provinceValue", result.addressComponent.province);
                    self.set("cityValue", result.addressComponent.city);
                    self.set("districtValue", result.addressComponent.district);
                });
                if(window.initMapLocation) {
                    // 同一个页面，不管有多少个地图控件，只需要初始化一次
                    return;
                }
                window.initMapLocation = true;
                //初始化当前用户位置
                var processing = Tip.processing();
                processing.show();
                adapter.mixinReady(function(){
                    adapter.getCurrentPosition(function(result) {
                        processing.hide(false);
                        topic.publish("MAP_INIT_DEFAULT_VALUE", this, result);
                    },function(){
                        processing.hide(false);
                    },{getPoi:0});
                });
            }

        },

        _setLocationValues: function(evt, __location__) {
            if (__location__ != this) {
                return
            }
            this.set("value", evt.value);
            this.set(
                "coordinateValue",
                evt.point && evt.point.lat
                    ? MapUtil.formatCoord(evt.point, this.strategy == "bmap" ? 3 : 5)
                    : ""
            );
            this.set("detailValue", evt.detail);
            this.set("provinceValue", evt.province);
            this.set("cityValue", evt.city);
            this.set("districtValue", evt.district);
        },

        _setValueAttr: function(value) {
            this.inherited(arguments);
            this.nameNode.value = value;
            this.value = value;
            if(this.nameTxtNode){
                this.nameTxtNode.innerHTML=value;
            }
        },

        _setCoordinateValueAttr: function(value) {
            this.inherited(arguments)
            this.coordinateNode.value = value
            this.coordinateValue = value
        },

        _setDetailValueAttr: function(value) {
            this.inherited(arguments)
            if (this.detailNode) this.detailNode.value = value
            this.detailValue = value
        },
        _setProvinceValueAttr:function(value){
            this.inherited(arguments);
            if (this.provinceNode) this.provinceNode.value = value;
            this.provinceValue = value;
        },
        _setCityValueAttr:function(value){
            this.inherited(arguments);
            if (this.cityNode) this.cityNode.value = value;
            this.cityValue = value;
        },
        _setDistrictValueAttr:function(value){
            this.inherited(arguments);
            if (this.districtNode) this.districtNode.value = value;
            this.districtValue = value;
        }
    })

    return clz
})
