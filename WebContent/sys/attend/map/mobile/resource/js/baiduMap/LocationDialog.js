define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_TemplatedMixin",
  "dojo/dom-class",
  "dojo/dom-construct",
  "dojo/topic",
  "dojo/dom-style",
  "dojo/text!../../tmpl/LocationDialog.jsp",
  "dojox/mobile/ScrollableView",
  "mui/dialog/Tip",
  "./LocationSearch",
  "./LocationMarkerMixin",
  "mui/device/adapter",
  "mui/map",
  "mui/i18n/i18n!sys-attend",
  "sys/attend/map/mobile/resource/js/baiduMap/LocationPoiList"
], function(
  declare,
  WidgetBase,
  _TemplatedMixin,
  domClass,
  domConstruct,
  topic,
  domStyle,
  tmpl,
  ScrollableView,
  Tip,
  LocationSearch,
  _LocationMarkerMixin,
  adapter,
  map,
  Msg,
  LocationPoiList
) {
  return declare(
    "sys.attend.baiduMap.LocationDialog",
    [WidgetBase, _TemplatedMixin, _LocationMarkerMixin],
    {
      value: null, //地址字符串

      point: null, //地址坐标

      detail: null, //详细地址

      templateString: tmpl,

      __location__: null, //打开dialog的location组件

      showStatus: "edit",
      isShowPoi: true,
      isShowSearch: true,
      poiRadius:'', //地点选择范围
      
      confirmLocation: function(evt) {
        //属性
      },

      buildRendering: function() {
        this.inherited(arguments)
        this.init();
       
        if(this.poiRadius && this.poiRadius>0){
        }else{
        	//不限制范围
        	this.poiRadius = '';
        }
        
        if (this.isShowSearch && !this.poiRadius) {
          this.renderSearch()
        }
        this.renderMap()
        this.renderGeo()
        this.renderList()
        this.renderToolbar()
        domClass.add(this.domNode, "muiLocationDialog")
      },

      startup: function() {
        this.inherited(arguments)
        this.subscribe("/map/location/poi/change", "doLocationChanged")
        this.subscribe("/map/location/center/reset", "resetMapCenter")
      },

      init: function() {
        this.map = new BMap.Map(this.contentdom)
      },

      renderSearch: function() {
        var search = (this.search = new LocationSearch({
          map: this.map,
          modelName: "",
          needPrompt: false,
          height: "4rem",
          canSearch: this.showStatus == "edit" ? this.isShowSearch : false
        }))
        search.startup()
        search.__parent__ = this
        domConstruct.place(search.domNode, this.searchdom, "last")
        domClass.add(this.searchdom, "muiLocationSearch")
      },

      renderMap: function() {
        var self = this
        this.map.enableScrollWheelZoom()
        //			this._initGeoControl();
        var localcity = new BMap.LocalCity()
        localcity.get(function(result) {
          self.map.centerAndZoom(result.center, 15)
        })
        domClass.add(this.contentdom, "muiLocationContent")
      },

      renderGeo: function() {
        //增加当前位置定位入口
        var geoBtn = domConstruct.create(
          "div",
          {className: "muiLocationGeoBtn"},
          this.geodom
        )
        domConstruct.create("div", {className: "muiMapGeo"}, geoBtn)
        this.connect(geoBtn, "click", "onCurGeoClick")
        geoBtn.dojoClick = true
        domClass.add(this.geodom, "muiLocationGeoDom")
      },
      onCurGeoClick: function() {
        var self = this
        var processing = Tip.processing();
		 processing.show();
        adapter.getCurrentCoord(
          function(r) {
        	  processing.hide(false);
            var from = 3 //gcj02坐标
            if (r.coordType == 1) {
              from = 1 //wgs84坐标
            }
            var ___point = new BMap.Point(r.lng, r.lat)
            if (r.coordType != 3) {
              new BMap.Convertor().translate([___point], from, 5, function(
                result
              ) {
                if (result.status == 0) {
                  self._setPosition(result.points[0], {})
                } else {
                  self.showError()
                }
              })
            } else {
              self._setPosition(___point, {});
            }
          },
          function() {
            self.showError();
            processing.hide(false);
          }
        )
      },
      renderList: function() {
        if (this.isShowPoi) {
          var self = this
          var poiListView = (self.poiListView = new LocationPoiList({
              map: self.map,
              height: "5rem"
            }))
            poiListView.startup()
            var scrolView = new ScrollableView()
            domConstruct.place(scrolView.domNode, self.listdom, "last")
            domConstruct.place(poiListView.domNode, scrolView.domNode, "last")
            scrolView.startup()
            domClass.add(self.listdom, "muiLocationListDom")
            domClass.add(
              poiListView.domNode,
              "muiSignInAddressList muiLocationPoiList"
            )
        }
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
              value: self.value,
              point: self.point,
              detail: self.detail,
              __location__: self.__location__
            })
            self.hide()
          })
        }
        domClass.add(this.toolbardom, "muiLocationToolbar")
      },

      doLocationChanged: function(obj, evt) {
        this.value = evt.value
        this.point = evt.point
        this.detail = evt.detail
      },
      resetMapCenter: function(obj, evt) {
        this.map.clearOverlays()
        this.createMarker({
          point: evt.point,
          title: evt.value,
          address: evt.detail
        })
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
        //若限制范围，则只能以当前位置为中心
        if (evt && evt.location && !this.poiRadius) {
          var ___point = new BMap.Point(evt.location.lng, evt.location.lat)
          if (evt.location.coordType == 5) {
            new BMap.Convertor().translate([___point], 3, 5, function(result) {
              if (result.status == 0) {
                self._setPosition(result.points[0], evt)
              } else {
                self.showError()
              }
            })
          } else {
            this._setPosition(___point, evt)
          }
        } else if (evt && evt.value && !evt.location && this.search) {
          //名称反向定位
          this.search.search(evt.value)
          this.defer(function() {
            this.search.searchNode.value = evt.value
          })
        } else if (showStatus == "edit") {
          //默认定位
         var processing = Tip.processing();
		 processing.show();
          adapter.getCurrentCoord(
            function(r) {
              processing.hide(false);
              var from = 3 //gcj02坐标
              if (r.coordType == 1) {
                from = 1 //wgs84坐标
              }
              var ___point = new BMap.Point(r.lng, r.lat)
              if (r.coordType != 3) {
                new BMap.Convertor().translate([___point], from, 5, function(
                  result
                ) {
                  if (result.status == 0) {
                    self._setPosition(result.points[0], {})
                  } else {
                    self.showError()
                  }
                })
              } else {
                self._setPosition(___point, {})
              }
            },
            function() {
              self.showError();
              processing.hide(false);
            }
          )
        }
        //class
        if (showStatus == "edit") {
          domClass.add(this.domNode, "edit")
        } else {
          domClass.remove(this.domNode, "edit")
        }
      },

      _setPosition: function(fdLatLng, evt) {
        var self = this
        map.getBMapLocation(
          {
            coordType: 3,
            lat: fdLatLng.lat,
            lng: fdLatLng.lng,
            poiRadius: evt.radius || self.poiRadius,
            poiPageSize:self.poiRadius && self.poiRadius>0 ? 100:''
          },
          function(result) {
            var title = evt.value || result.title
            var address = evt.detail || result.address
            address = title && address && title == address ? "" : address
            var pois = []

            if (self.showStatus == "edit") {
              var nearPois = result.pois;
              if (nearPois && nearPois.length > 0) {
                for (var i = 0; i < nearPois.length; i++) {
                  var latLng = new BMap.Point(
                    nearPois[i].point.lng,
                    nearPois[i].point.lat
                  )
                  var poi = {
                    name: nearPois[i].title,
                    title: nearPois[i].title,
                    location: latLng,
                    point: latLng,
                    address: nearPois[i].address
                  }
                  pois.push(poi)
                }
              }
            }
            var markerPoit = {
              point: fdLatLng,
              title: title,
              address: address
            }
            self.map.clearOverlays()
            var marker = self.createMarker(markerPoit)
            topic.publish("/location/poi/dataChange", self, {
              pois: pois,
              location: markerPoit
            })
          },
          function() {
            self.showError()
          }
        );
      },

      hide: function() {
        domStyle.set(this.domNode, "display", "none")
        if (this.listdom) {
          domStyle.set(this.listdom, "display", "none")
        }
        this.doLocationChanged(this, {value: "", point: "", detail: ""})

        topic.publish("sys/attend/locationDialog/hide", this)
        //清除地图上所有覆盖物
        this.map.clearOverlays()
      },

      _initGeoControl: function() {
        var geoLocCtrl = new BMap.GeolocationControl({showAddressBar: false})
        this.map.addControl(geoLocCtrl)
        var size = geoLocCtrl.getOffset()
        geoLocCtrl.setOffset(new BMap.Size(size.width, size.height + 80))
        var self = this
        geoLocCtrl.addEventListener("locationSuccess", function(e) {
          var ___point = e.point
          var geoc = new BMap.Geocoder()
          geoc.getLocation(
            ___point,
            function(rs) {
              if (!rs) {
                return
              }
              var title = rs.address
              var address = rs.address
              var pois = rs.surroundingPois
              if (pois.length > 0) {
                title = pois[0].title
                address = pois[0].address
                ___point = pois[0].point
              }
              var markerPoit = {
                point: ___point,
                title: title,
                address: address
              }
              topic.publish("/location/poi/dataChange", self, {
                pois: pois,
                location: markerPoit
              })
            },
            {poiRadius: 500, numPois: 50}
          )
        })
        geoLocCtrl.addEventListener("locationError", function(e) {
          Tip.fail({
            text: "地理定位失败"
          })
        })
      }
    }
  )
})
