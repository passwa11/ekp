define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dijit/_WidgetBase",
  "dijit/_TemplatedMixin",
  "mui/dialog/Tip",
  "dojo/dom-class",
  "dojo/dom-construct",
  "dojo/topic",
  "dojo/dom-style",
  "mui/device/adapter",
  "dojo/text!../../tmpl/LocationDialog.jsp",
  "dojox/mobile/ScrollableView",
  "./LocationSearch",
  "./LocationMarkerMixin",
  "mui/map",
  "mui/i18n/i18n!sys-attend",
  "sys/attend/map/mobile/resource/js/aMap/LocationPoiList"
], function(
  declare,
  lang,
  WidgetBase,
  _TemplatedMixin,
  Tip,
  domClass,
  domConstruct,
  topic,
  domStyle,
  adapter,
  tmpl,
  ScrollableView,
  LocationSearch,
  LocationMarkerMixin,
  map,
  Msg,
  LocationPoiList
) {
  return declare(
    "sys.attend.aMap.LocationDialog",
    [WidgetBase, _TemplatedMixin, LocationMarkerMixin],
    {
      name: null, //地址字符串

      location: null, //地址坐标

      detail: null, //详细地址

      __location__: null,

      templateString: tmpl,

      showStatus: "edit",
      isShowPoi: true,
      isShowSearch: true,
      poiRadius:'', //地点选择范围
      
      confirmLocation: function(evt) {
        //属性
      },

      buildRendering: function() {
        this.inherited(arguments);
        this.init();
        if(this.poiRadius && this.poiRadius>0){
        }else{
        	//不限制范围
        	this.poiRadius = '';
        }
        if (this.isShowSearch && !this.poiRadius) {
          this.renderSearch();
        }
        this.renderMap();
        this.renderGeo();
        this.renderList();
        this.renderToolbar();
        domClass.add(this.domNode, "muiLocationDialog");
        domClass.add(this.domNode, "aMapLocationDialog");
      },

      startup: function() {
        this.inherited(arguments)
        this.subscribe("/map/location/poi/change", "doLocationChanged")
      },

      init: function() {
        this.map = new AMap.Map(this.contentdom, {
          resizeEnable: true,
          zoom: 15
        })
      },

      renderSearch: function() {
        var search = (this.search = new LocationSearch({
          map: this.map,
          searchListNode: this.listdom,
          modelName: "",
          needPrompt: false,
          height: "4rem",
          canSearch: this.showStatus == "edit" ? this.isShowSearch : false
        }))
        search.startup()
        search.__parent__ = this
        domConstruct.place(search.domNode, this.searchdom, "last")
        domClass.add(this.searchdom, "muiLocationSearch")
        domClass.add(this.listdom, "muiLocationAMapList")
      },

      renderMap: function() {
        var self = this
        AMap.plugin(
          ["AMap.ToolBar", "AMap.Geolocation"],
          lang.hitch(this, function() {
            var toolbar = new AMap.ToolBar(),
              geolocation = new AMap.Geolocation({
                enableHighAccuracy: true,
                panToLocation: true,
                zoomToAccuracy: true
              })

            this.map.addControl(toolbar)
          })
        )
        domClass.add(this.contentdom, "muiLocationContent");
      },
      renderGeo: function() {
        //增加当前位置定位入口
        var geoBtn = domConstruct.create(
          "div",
          {className: "muiLocationGeoBtn"},
          this.geodom
        );
        domConstruct.create("div", {className: "muiMapGeo"}, geoBtn);
        this.connect(geoBtn, "click", "onCurGeoClick");
        geoBtn.dojoClick = true
        domClass.add(this.geodom, "muiLocationGeoDom");
      },

      renderList: function() {
        var self = this;
        var poiListView = (self.poiListView = new LocationPoiList({
            map: self.map,
            height: "5rem"
          }));
          poiListView.startup();
          var scrolView = new ScrollableView();
          domConstruct.place(scrolView.domNode, self.listdom, "last");
          domConstruct.place(poiListView.domNode, scrolView.domNode, "last");
          scrolView.startup();
          domClass.add(self.listdom, "muiLocationListDom");
          domClass.add(
            poiListView.domNode,
            "muiSignInAddressList muiLocationPoiList"
          );
      },

      renderToolbar: function() {
        var self = this
        var btnClose = domConstruct.create(
          "div",
          {
            className: "muiLocationCloseBtn",
            innerHTML: Msg["mui.button.close"]
          },
          this.toolbardom
        )
        this.connect(btnClose, "click", function() {
          self.confirmLocation()
          self.hide()
        })
        if (this.showStatus == "edit") {
          var btnOk = domConstruct.create(
            "div",
            {className: "muiLocationOkBtn", innerHTML: Msg["mui.button.ok"]},
            this.toolbardom
          )
          this.connect(btnOk, "click", function() {
            self.confirmLocation({
              name: self.name,
              value: self.name,
              location: self.location,
              point: self.location,
              detail: self.detail,
              __location__: self.__location__
            })
            self.hide()
          })
        }
        domClass.add(this.toolbardom, "muiLocationToolbar")
      },

      doLocationChanged: function(obj, evt) {
        this.name = evt.name
        this.location = evt.location
        this.detail = evt.detail
        if (evt.location) {
          var location =
            evt.location instanceof AMap.LngLat
              ? evt.location
              : new AMap.LngLat(evt.location.lng, evt.location.lat)
          //清除地图上所有覆盖物
          this.map.clearMap()
          this.map.panTo(location)
          this.__stopLocationCenter__ = true
          this.createMarker({
            location: location,
            name: evt.name,
            address: evt.detail
          })
        }
      },
      onCurGeoClick: function() {
        var self = this
        adapter.getCurrentCoord(
          function(result) {
            var lngLat = new AMap.LngLat(result.lng, result.lat)
            var type = "baidu" //百度经纬度
            if (result.coordType == 1) {
              type = "gps" //gps经纬度
            }
            if (result.coordType != 5) {
              AMap.convertFrom(lngLat, type, function(status, res) {
                if (status == "complete") {
                  self._setPosition(res.locations[0], {})
                } else {
                  self.showError()
                }
              })
            } else {
              self._setPosition(lngLat, {})
            }
          },
          function() {
            self.showError()
          }
        )
      },

      showError: function() {
        Tip.fail({
          text: "地图加载过程失败,请重试!"
        })
      },
      show: function(evt) {
        var self = this
        var showStatus = evt.showStatus || this.showStatus
        domStyle.set(this.domNode, "display", "block")
        topic.publish("sys/attend/locationDialog/show", this, evt)
        //定位
        if (evt && evt.location && !this.poiRadius) {
          var lngLat = new AMap.LngLat(evt.location.lng, evt.location.lat)
          if (evt.location.coordType == 3) {
            AMap.convertFrom(lngLat, "baidu", function(status, result) {
              if (status == "complete") {
                self._setPosition(result.locations[0], evt)
              } else {
                self.showError()
              }
            })
          } else {
            this._setPosition(lngLat, evt)
          }
        } else if (evt && evt.name && !evt.location && this.search) {
          //名称反向定位
          this.defer(function() {
            this.search.search(evt.name)
            this.search.searchNode.value = evt.name
          })
        } else if (showStatus == "edit") {
          adapter.getCurrentCoord(
            function(result) {
              var lngLat = new AMap.LngLat(result.lng, result.lat)
              var type = "baidu" //百度经纬度
              if (result.coordType == 1) {
                type = "gps" //gps经纬度
              }
              if (result.coordType != 5) {
                AMap.convertFrom(lngLat, type, function(status, res) {
                  if (status == "complete") {
                    self._setPosition(res.locations[0], evt)
                  } else {
                    self.showError()
                  }
                })
              } else {
                self._setPosition(lngLat, {})
              }
            },
            function() {
              self.showError()
            }
          )
        }
        //class
        var showStatus = evt.showStatus || this.showStatus
        if (showStatus == "edit") {
          domClass.add(this.domNode, "edit")
        } else {
          domClass.remove(this.domNode, "edit")
        }
      },
      _setPosition: function(fdLatLng, evt) {
          //先平移至目标位置
          this.map.panTo([fdLatLng.lng, fdLatLng.lat]);
          var self = this;
          map.getAMapLocation(
          {
            coordType: 5,
            lat: fdLatLng.lat,
            lng: fdLatLng.lng,
            poiRadius: evt.radius || self.poiRadius,
            poiPageSize:self.poiRadius && self.poiRadius>0 ? 100:''
          },
          function(result) {
            var __options = {
              name: evt.name || result.title,
              location: fdLatLng,
              address: evt.detail || result.address
            }
            var pois = []
            if (self.showStatus == "edit") {
              var nearPois = result.pois
              if (nearPois && nearPois.length > 0) {
                for (var i = 0; i < nearPois.length; i++) {
                  var latLng = new AMap.LngLat(
                    nearPois[i].point.lng,
                    nearPois[i].point.lat
                  )
                  var poi = {
                    name: nearPois[i].title,
                    title: nearPois[i].title,
                    location: latLng,
                    point: latLng,
                    address: nearPois[i].address
                  };
                  pois.push(poi);
                }
              }
            }
            self.createMarker(__options);
            topic.publish("/location/poi/dataChange", self, {
              pois: pois,
              location: __options
            });
          },
          function() {
            self.showError()
          }
        );
      },
      hide: function() {
        domStyle.set(this.domNode, "display", "none");
        if (this.listdom) {
          domStyle.set(this.listdom, "display", "none");
        }
        this.doLocationChanged(this, {name: "", location: "", detail: ""});
        topic.publish("sys/attend/locationDialog/hide", this);
        //清除地图上所有覆盖物
        this.map.clearMap();
      }
    }
  )
})
