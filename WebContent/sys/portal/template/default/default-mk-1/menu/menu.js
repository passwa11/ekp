var target = render.vars.target ? render.vars.target : "_self";

/**
 * 创建菜单载体区域（DIV）
 * @param root  菜单载体区域最顶层DIV
 * @return 返回  菜单载体区域最底层DIV
 */
function createHeader(root) {
  var domR = $("<div class='lui_single_menu_header_menu_r'></div>");
  domR.appendTo(root);
  var domC = $("<div class='lui_single_menu_header_menu_c'></div>");
  domC.appendTo(domR);
  var div = $("<div class='lui_single_menu_header_menu_item_wrap'></div>");
  div.appendTo(domC);
  return div;
}

/**
 * 创建菜单明细项（并同时绑定鼠标悬停和单击事件）
 * @param parent  菜单明细项父DIV（jQuery DOM）
 * @param data    菜单明细JSON对象
 * @param createLine 是否需要创建分隔竖线(true or false)
 * @return
 */
function createItem(parent, data, createLine) {
  var item = $("<div></div>");
  item.attr("data-portal-id", data.fdId);
  item.addClass("lui_single_menu_header_menu_item");
  item.appendTo(parent);
  var selected = data.selected == null ? false : data.selected;
  if (selected) {
    item.attr(
      "class",
      "lui_single_menu_header_menu_item_current lui_single_menu_header_menu_item_div"
    );
  } else {
    item.attr("class", "lui_single_menu_header_menu_item_div");
  }

  item.hover(
    function (e) {
      // 添加悬停样式
      $(this).addClass("lui_single_menu_header_menu_item_hover");
      // 创建下拉菜单
      var submenuData = getSubmenuData(data.fdId);
      if (submenuData && submenuData.length > 0) {
        var fdId = data.fdId;
        var $menuItem = $(this).find(".lui_single_menu_header_menu_item_r");
        var dropDownMenu = $(
          "div.lui_single_menu_header_dropdown_menu[data-portal-id='" +
            data.fdId +
            "']"
        );
        if (dropDownMenu.length > 0) {
          // 已经创建过下拉菜单的情况下，显示菜单DIV
          dropDownMenu.slideDown();
        } else {
          // 未创建过下拉菜单的情况下，新建菜单DIV
          dropDownMenu = createDropDownMenu($menuItem, data, submenuData);
        }

        // 设置下拉菜单显示的位置CSS样式
        var menuWidth = $menuItem.width(); // 获取当前菜单项的宽度
        var menuHeight = $menuItem.height(); // 获取当前菜单项的高度
        var menuOffset = $menuItem.offset(); // 获取当前菜单项的偏移坐标
        var dropDownMenuTop = menuOffset.top + menuHeight;
        var dropDownMenuLeft = menuOffset.left;
        dropDownMenu.css({
          top: dropDownMenuTop,
          left: dropDownMenuLeft,
          width: menuWidth,
        });
      }
    },
    function (e) {
      // 移除悬停样式
      $(this).removeClass("lui_single_menu_header_menu_item_hover");
      // 移除下拉菜单
      var toTarget = e.toElement || e.relatedTarget;
      var $toTarget = $(toTarget);
      if (
        $toTarget.hasClass("lui_single_menu_header_dropdown_menu") == false &&
        $toTarget.closest(".lui_single_menu_header_dropdown_menu").length == 0
      ) {
        $(
          "div.lui_single_menu_header_dropdown_menu[data-portal-id='" +
            data.fdId +
            "']"
        ).slideUp();
      }
    }
  );
  item.click(data, function (event) {
    var configData = event.data;
    var preItem = $(".lui_single_menu_header_menu_item_current", parent);
    preItem
      .removeClass("lui_single_menu_header_menu_item_current")
      .addClass("lui_single_menu_header_menu_item_div");
    $(item).addClass("lui_single_menu_header_menu_item_current");
    var target = data.target,
      href = data.href;
    var mode = LUI.pageMode();
    if (mode == "quick" && target != "_blank") {
      target = "_content";
      if (data.pageType == "2") {
        target = "_iframe";
      }
    }
    LUI.pageOpen(env.fn.formatUrl(href), target, {
      portalId: data.fdId,
    });
  });
  var domL = $("<div class='lui_single_menu_header_menu_item_l'></div>");
  domL.appendTo(item);
  var domR = $("<div class='lui_single_menu_header_menu_item_r'></div>");
  domR.appendTo(domL);
  var domC = $("<div class='lui_single_menu_header_menu_item_c'></div>");
  domC.appendTo(domR);
  var icon = $("<i class='lui_single_menu_header_icon lui_icon_l'>");
  icon.addClass(data.icon);
  var domC_txt = $("<span>");
  domC_txt
    .append(env.fn.formatText(data.text))
    .attr("title", env.fn.formatText(data.text));
  icon.appendTo(domC);
  domC_txt.appendTo(domC);

  if (createLine) {
    parent.append(
      $(
        "<div class='lui_single_menu_header_menu_item_line' style='display: none;'><span>更多</span></span></div>"
      )
    );
  }
}

/**
 * 创建下拉垂直菜单
 * @param $menuItem 悬停选中的菜单元素
 * @param data      菜单明细JSON对象
 * @param submenuData 子级树形菜单数据
 * @return
 */
function createDropDownMenu($menuItem, data, submenuData) {
  var dropDownMenu = $(
    "<div root='true' class='lui_single_menu_header_dropdown_menu'></div>"
  );
  dropDownMenu.attr("data-portal-id", data.fdId);
  dropDownMenu.hover(
    function (e) {},
    function (e) {
      $(this).hide();
    }
  );
  $("body").append(dropDownMenu);
  createDropDownMenuItem(dropDownMenu, submenuData);
  return dropDownMenu;
}

/**
 * 创建下拉垂直菜单明细项
 * @param $parent 父级元素
 * @param submenuData 子级树形菜单数据
 * @return
 */
function createDropDownMenuItem($parent, submenuData) {
  var root = $parent.attr("root");
  var hasChild = false;
  for (var i = 0; i < submenuData.length; i++) {
    var menuObj = submenuData[i];

    // 创建菜单明细项DIV
    var dropDownItem = $(
      "<div class='lui_single_menu_header_dropdown_menu_item'></div>"
    );

    // 创建菜单明细项标题文字DIV
    var dropDownItemText = $(
      "<div class='lui_single_menu_header_dropdown_menu_item_text'></div>"
    );
    dropDownItemText.text(menuObj.text);
    dropDownItem.append(dropDownItemText);

    if (menuObj.children) {
      hasChild = true;
      // 创建菜单明细项展开下级图标DIV
      var dropDownItemDis = $(
        "<div class='lui_single_menu_header_dropdown_menu_item_dis'></div>"
      );
      dropDownItem.append(dropDownItemDis);
    }

    // 鼠标移入悬停
    dropDownItem.mouseenter(menuObj, function (event) {
      var itemData = event.data;
      $(this).addClass("lui_single_menu_header_dropdown_menu_item_hover");
      $(this)
        .children(".lui_single_menu_header_dropdown_menu_item_text")
        .addClass("lui_text_primary");
      if (itemData.children) {
        var dropDownMenu = $(this).children(
          "div.lui_single_menu_header_dropdown_menu"
        );
        if (dropDownMenu.length > 0) {
          dropDownMenu.slideDown();
        } else {
          var subMenu = $(
            "<div class='lui_single_menu_header_dropdown_menu'></div>"
          );
          subMenu.css({
            left: "100%",
            "min-width": "100%",
          });
          $(this).append(subMenu);
          createDropDownMenuItem(subMenu, itemData.children);
        }
      }
    });

    // 鼠标移出
    dropDownItem.mouseleave(menuObj, function (event) {
      var itemData = event.data;
      // 清除悬停样式
      $(this).removeClass("lui_single_menu_header_dropdown_menu_item_hover");
      $(this)
        .children(".lui_single_menu_header_dropdown_menu_item_text")
        .removeClass("lui_text_primary");
      // 移除子菜单
      $(this).children(".lui_single_menu_header_dropdown_menu").hide();
    });

    // 鼠标单击
    dropDownItem.click(menuObj, function (event) {
      var itemData = event.data;
      // 隐藏菜单项
      $(this).closest("div.lui_single_menu_header_dropdown_menu").hide();
      // 打开点击的页面
      doOpenDownItemPage(itemData);
      // 阻止事件冒泡
      event.stopPropagation();
    });

    $parent.append(dropDownItem);
  }

  if ("true" == root && !hasChild) {
    $parent.css("overflow-x", "hidden");
    $parent.css("overflow-y", "auto");
    $parent.css("max-height", "500px");
  }
}

/**
 * 打开选中的垂直菜单明细项相应页面
 * @param configData
 * @return
 */
function doOpenDownItemPage(configData) {
  var href = env.fn.formatUrl(configData.href);
  var target = $.trim(configData.target) != "" ? configData.target : target; // 目标页面打开方式( _top:本页面、   _blank：新页面、   _content:内容区 )
  var mode = LUI.pageMode(); // 当前模式：quick（极速模式）

  if (mode == "quick" && target != "_blank") {
    //极速模式且二级树用于切换页面时调用LUI.pageOpen....bad hack
    target = "_iframe";
    LUI.pageOpen(href, target);
  } else {
    if (target == "_content") {
      target = "_top";
    }
    setTimeout(function () {
      window.open(href, target);
    }, 100);
  }
}

/**
 * 根据页面fdId获取子级树形菜单数据（从门户公共部件-多级菜单树中获取）
 * @param portalPageId 门户与页面中间表的fdId
 * @return
 */
function getSubmenuData(portalPageId) {
  var submenuCacheId = "dropdown_menu_submenu_" + portalPageId;
  if (window[submenuCacheId]) {
    return window[submenuCacheId];
  }

  var submenuData = null;
  var params = {
    portalPageId: portalPageId,
  };
  $.ajax({
    url:
      Com_Parameter.ContextPath +
      "sys/portal/sys_portal_nav/sysPortalNav.do?method=getPortletNavByPortalPageId",
    async: false,
    data: params,
    type: "POST",
    dataType: "json",
    success: function (result) {
      if (result && result.fdContent) {
        submenuData = $.parseJSON(result.fdContent);
        window[submenuCacheId] = submenuData;
      } else {
        window[submenuCacheId] = [];
      }
    },
  });

  return submenuData;
}

//改动
// $(".lui_single_menu_header_searchbar").css("width", "auto");

/**
 * 导航栏宽度计算
 * @param box_w 外部容器宽度
 * @param left_w 左侧logo+门户切换+应用切换宽度
 * @param right_w 个人信息宽度
 * @return 导航菜单可用宽度
 */
function getBarWidth(box_w, left_w, right_w) {
  var bar_w = box_w - left_w - right_w - 20;
  return bar_w;
}

/**
 * 克隆更多菜单
 * @param headerItem  菜单明细项父DIV（jQuery DOM）
 * @return
 */
function doCopy(headerItem) {
  var a = $(headerItem).clone(true);
  a.css("display", "block");
  a.find(".lui_single_menu_header_icon").css("display", "none");
  return a;
}

/**
 * 计算更多出现的时机
 * @param menu_box 菜单的可用宽度
 * @param posi 起始菜单
 * @return
 */
function getMore(menu_box, posi) {
  // debugger
  var headerItem = $(
    ".lui_single_menu_header_menu .lui_single_menu_header_menu_item_div"
  );
  var more_w = $(
    ".lui_single_menu_header_menu .lui_single_menu_header_menu_item_line"
  ).width();
  var more = $(
    ".lui_single_menu_header_menu .lui_single_menu_header_menu_item_line"
  );
  var arr1 = []; //存储每个项的宽
  var arr2 = []; //存储每项值
  // var posi = 0;//定位数，决定在第几个出现更多
  var real_w = 0; //实际开始计算的宽度
  var limit_w = box_w - header_l_w - header_r_w - 20; //理论临界值的宽度
  limit_w = menu_box;
  var s = false; //临界点判定

  var dwrap = $(
    "<div root='true' class='lui_single_menu_header_drop lui_popup_border_content'></div>"
  ); //下拉创建
  var wrapItem = 0; //下拉项创建

  for (var i = 0; i < headerItem.length; i++) {
    //计算开始
    // console.log('初始',headerItem[i])
    //重置
    if (headerItem[i]) {
      headerItem[i].style.display = "inline-block";
    }
    // if ($(more[i]).hasClass('lui_single_menu_header_menu_item_div') > -1) {
    // 	$(more[i]).removeClass('lui_single_menu_header_menu_item_div');
    // }
    $(more[i]).css("display", "none");

    //塞进数组里 每一个导航页面的宽度
    arr1[i] = headerItem[i].clientWidth;

    if (s) {
      //实际值大于临界值
      //后续的项直接消失,并装进新元素里
      headerItem[i].style.display = "none";
      wrapItem = doCopy(headerItem[i]);
      if (!$(headerItem[i]).hasClass("lui_single_menu_header_menu_item_line")) {
        dwrap.append(wrapItem);
      }

      //界值判断
      if (i == headerItem.length - 1) {
        var dwrap_l =
          real_w +
          $(".lui_single_menu_header_left").width() +
          ($(".lui_portal_header").width() -
            $(".lui_single_menu_header_box").width()) /
            2;
        dwrap.css({
          position: "absolute",
          left: dwrap_l,
          "z-index": 99,
          "white-space": "nowrap",
          display: "none",
        });
        $(".lui_portal_header").append(dwrap);
      }
    } else if (real_w + more_w > limit_w) {
      //实际值大于临界值,临界点
      //实际值要按上一组的more来算
      real_w = real_w - arr1[posi];
      // console.log('进过这里2',i)

      //返回上一组，并弹出上一组的更多,把弄掉的都放进下拉项里
      headerItem[posi].style.display = "none";
      headerItem[i].style.display = "none";
      headerItem.eq(posi).nextAll().hide();

      //塞进下拉项里
      wrapItem = doCopy(headerItem[posi]);
      if (
        !$(headerItem[posi]).hasClass("lui_single_menu_header_menu_item_line")
      ) {
        dwrap.append(wrapItem);
      }
      wrapItem = doCopy(headerItem[i]);
      if (!$(headerItem[i]).hasClass("lui_single_menu_header_menu_item_line")) {
        dwrap.append(wrapItem);
      }

      //清除之前的更多
      $(".lui_single_menu_header_menu_item_line")[theLast].style.display =
        "none";
      $(".lui_single_menu_header_menu_item_line")[theLast + 1].style.display =
        "none";

      // debugger
      console.log("临界~~", posi, arr1[posi]);
      //弹出更多
      if (posi == 0) {
        $(".lui_single_menu_header_menu_item_line")[posi].style.display =
          "inline-block";
      } else {
        $(".lui_single_menu_header_menu_item_line")[posi - 1].style.display =
          "inline-block";
      }

      for (var g = 0; g < arr0.length; g++) {
        if (arr0[g] == posi - 1) {
          return;
        }
      }

      //全局状态改变
      if (posi == 0) {
        arr0[arrPosi] = posi;
      } else {
        arr0[arrPosi] = posi - 1;
      }
      arrPosi++;
      //重置状态
      s = true;
    } else {
      // console.log('其他模式',posi)
      posi = i;
      real_w = arr1[i] + real_w;
      //后续项弹出
      headerItem[i].style.display = "inline-block";
    }
  }

  // 事件流
  $(".lui_single_menu_header_drop").mouseover(function (event) {
    $(event.currentTarget).css("display", "block");
    $(".lui_single_menu_header_menu_item_line").addClass(
      "lui_single_menu_header_menu_item_div lui_single_menu_header_menu_item_hover"
    );
    time_d = false;
  });

  $(".lui_single_menu_header_drop").mouseout(function () {
    $(this).css("display", "none");
    $(".lui_single_menu_header_menu_item_line").removeClass(
      "lui_single_menu_header_menu_item_div lui_single_menu_header_menu_item_hover"
    );
  });
}

var root = $("<div class='lui_single_menu_header_menu_l'></div>");
var xdiv = createHeader(root);
var left = $(
  "<div class='lui_single_menu_header_menu_item_left'><i class='lui_single_menu_header_icon lui_icon_l_icon_arrow_solid_l'></i></div>"
)
  .hide()
  .appendTo(xdiv);
var frame = $(
  "<div class='lui_single_menu_header_menu_item_frame'></div>"
).appendTo(xdiv);
var right = $(
  "<div class='lui_single_menu_header_menu_item_right'><i class='lui_single_menu_header_icon lui_icon_l_icon_arrow_solid_r'></i></div>"
)
  .hide()
  .appendTo(xdiv);
var body = $(
  "<div class='lui_single_menu_header_menu_item_body'></div>"
).appendTo(frame);
frame.css("width", "100%");
body.css("white-space", "nowrap");
if (data.length === 0) {
  data = [
    {
      fdId: "",
      text: "样例-工作台",
      fdOrder: "0",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: true,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "产品办公",
      fdOrder: "1",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "信息门户",
      fdOrder: "3",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "市场门户",
      fdOrder: "4",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "客户服务",
      fdOrder: "5",
      icon: "lui_icon_l_icon_1",
      target: "_blank",
      selected: false,
      href: "",
      pageType: "2",
    },
    {
      fdId: "",
      text: "交付门户",
      fdOrder: "6",
      icon: "lui_icon_l_icon_1",
      target: "_blank",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "合伙门户",
      fdOrder: "7",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "营销门户",
      fdOrder: "8",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "新人门户",
      fdOrder: "9",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "投标门户",
      fdOrder: "10",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "财务门户",
      fdOrder: "11",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "服务门户",
      fdOrder: "12",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "产品社区",
      fdOrder: "13",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "蓝凌学院",
      fdOrder: "14",
      icon: "lui_icon_l_icon_1",
      target: "_blank",
      selected: false,
      href: "",
      pageType: "2",
    },
    {
      fdId: "",
      text: "知识社区",
      fdOrder: "15",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "KM门户",
      fdOrder: "16",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "蓝鲸门户",
      fdOrder: "17",
      icon: "lui_icon_l_icon_53",
      target: "_blank",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "蓝钉门户",
      fdOrder: "18",
      icon: "lui_icon_l_icon_1",
      target: "_blank",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "导航门户",
      fdOrder: "19",
      icon: "lui_icon_l_icon_4",
      target: "_blank",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "个人中心",
      fdOrder: "20",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
    {
      fdId: "",
      text: "MKPaaS",
      fdOrder: "22",
      icon: "lui_icon_l_icon_1",
      target: "_blank",
      selected: false,
      href: "",
      pageType: "2",
    },
    {
      fdId: "",
      text: "GKP门户",
      fdOrder: "23",
      icon: "lui_icon_l_icon_1",
      target: "_top",
      selected: false,
      href: "",
      pageType: "1",
    },
  ];
}
for (var i = 0; i < data.length; i++) {
  createItem(body, data[i], i < data.length - 1);
}
done(root);

// 如果后台有配置经典页眉导航设置（navigationSettingConfig 定义在 sys\portal\template\menu\header.jsp）
if (
  typeof navigationSettingConfig !== "undefined" &&
  navigationSettingConfig != null
) {
  // debugger
  if (navigationSettingConfig.widthType == "width_fixed") {
    // 固定宽度
    var fixedWidth = navigationSettingConfig.fixedWidth;
    for (var i = 0; i < data.length; i++) {
      var menuItem = body.children(
        "div[data-portal-id='" + data[i].fdId + "']"
      );
      menuItem
        .find(".lui_single_menu_header_menu_item_r")
        .css("width", fixedWidth);
      var fontCss = {
        overflow: "hidden",
        "text-align": "center",
        "white-space": "nowrap",
        "-o-text-overflow": "-o-text-overflow",
        "text-overflow": "ellipsis",
      };
      menuItem.find(".lui_single_menu_header_menu_item_c").css(fontCss);
    }
  } else if (navigationSettingConfig.widthType == "both_sides_align") {
    // 两端对齐(宽度平分)

    // 菜单区域可展现的总宽度
    var frameWidth = frame.width();
    // 菜单内容实际占用的总宽度
    var bodyWidth = body.width();
    // 当实际占用宽度小于菜单区域可展现的宽度时，才进行宽度平分，否则依然保持宽度自适应
    if (bodyWidth <= frameWidth) {
      // 获取分割线DIV宽度
      var dividingLineWidth = $(
        ".lui_single_menu_header_menu_item_line:first"
      ).width();
      // 计算出所有分割线的总宽度
      var dividingLinetotal_w = dividingLineWidth * (data.length - 1);
      // 计算出可平均分配给各个菜单项的宽度
      var menuItemWidth = parseInt(
        (frameWidth - dividingLinetotal_w) / data.length
      );
      for (var i = 0; i < data.length; i++) {
        var menuItem = body.children(
          "div[data-portal-id='" + data[i].fdId + "']"
        );
        menuItem
          .find(".lui_single_menu_header_menu_item_r")
          .css("width", menuItemWidth);
        var fontCss = {
          overflow: "hidden",
          "text-align": "center",
          "white-space": "nowrap",
          "-o-text-overflow": "-o-text-overflow",
          "text-overflow": "ellipsis",
        };
        menuItem.find(".lui_single_menu_header_menu_item_c").css(fontCss);
      }
    }
  }
}
// 菜单栏位置
var box_w = $(".lui_single_menu_header_box").width();
var header_l_w = $(".lui_single_menu_header_left").width();
var header_r_w = $(".lui_single_menu_header_right").width();
$(".lui_single_menu_header_menu").width(box_w - header_l_w - header_r_w - 20);
$(".lui_single_menu_header_menu").css("left", header_l_w);

// 窗口重置
// window.onresize = function () {
// 	box_w = $(".lui_single_menu_header_box").width();
// 	header_l_w = $(".lui_single_menu_header_left").width();
// 	header_r_w = $(".lui_single_menu_header_right").width();
// 	$(".lui_single_menu_header_menu").width(box_w - header_l_w - header_r_w - 20)
// }

var w1 = xdiv.width();
var w2 = body.width();

seajs.use(["lui/topic"], function (topic) {
  topic.subscribe("lui.page.open", function (evt) {
    if (evt && evt.features) {
      var portalId = evt.features.portalId;
      if (portalId) {
        var item = $('[data-portal-id="' + portalId + '"]', root);
        if (item.length > 0) {
          $("[data-portal-id]", root)
            .removeClass("lui_single_menu_header_menu_item_current")
            .addClass("lui_single_menu_header_menu_item_div");
          $(item).addClass("lui_single_menu_header_menu_item_current");
        }
      }
    }
  });
});

var along = getBarWidth(
  $(".lui_single_menu_header_box").width(),
  $(".lui_single_menu_header_left").width(),
  $(".lui_single_menu_header_right").width()
);
var theLast = 0; //全局变量，减少时间复杂度
var arr0 = []; //全局数组，定位下拉
var arrPosi = 0; //数组定位，下拉
var time_d = false;
getMore(along, 0);

// 搜索展开时候的宽度
var _search_active_w = $(
  ".lui_single_menu_header_search_input_placeholder"
).width();

// 事件流 点击搜索按钮展开搜索框
$(".lui_single_menu_header_searchbar .lui_icon_l_icon_search").click(
  function () {
    // debugger
    var bar_w = getBarWidth(
      $(".lui_single_menu_header_box").width(),
      $(".lui_single_menu_header_left").width(),
      $(".lui_single_menu_header_right").width() + _search_active_w
    ); //此处有坑
    $(".lui_single_menu_header_menu").width(bar_w);
    getMore(bar_w, 0);
    // console.log('展开', bar_w)
  }
);

// 点击取消 收起搜索框
$(".lui_single_menu_header_icon.lui_icon_l_cancel.lui_icon_l_fork").click(
  function () {
    // debugger
    var bar_w = getBarWidth(
      $(".lui_single_menu_header_box").width(),
      $(".lui_single_menu_header_left").width(),
      $(".lui_single_menu_header_right").width() - _search_active_w / 4
      //$(".lui_single_menu_header_right").width() - _search_active_w,
    ); //此处有坑
    $(".lui_single_menu_header_menu").width(bar_w);
    getMore(bar_w, 0);
    // console.log('收起', bar_w)
  }
);

//"更多" 节流
$(".lui_single_menu_header_menu_item_line").mouseover(function (event) {
  time_d = true;
  $(event.currentTarget).addClass("lui_single_menu_header_menu_item_div");
  $(event.currentTarget).addClass("lui_single_menu_header_menu_item_hover");
  var dropFrame = $(".lui_single_menu_header_drop");
  for (var i = 0; i < arr0.length; i++) {
    if (arr0[i] == ($(this).index() - 1) / 2) {
      $(dropFrame[i]).slideDown();
    }
  }
});

$(".lui_single_menu_header_menu_item_line").mouseout(function (event) {
  setTimeout(function () {
    if (time_d) {
      $(event.currentTarget).removeClass(
        "lui_single_menu_header_menu_item_div"
      );
      $(event.currentTarget).removeClass(
        "lui_single_menu_header_menu_item_hover"
      );
      $(".lui_single_menu_header_drop").slideUp();
    }
  }, 100);
});

//窗口缩放
window.onresize = function () {
  box_w = $(".lui_single_menu_header_box").width();
  header_l_w = $(".lui_single_menu_header_left").width();
  header_r_w = $(".lui_single_menu_header_right").width();
  $(".lui_single_menu_header_menu").width(box_w - header_l_w - header_r_w - 20);
  setTimeout(function () {
    var menu_top = $(".lui_single_menu_header_search_dropdown_menu").offset()
      .top;
    var menu_left = $(".lui_single_menu_header_search_dropdown_menu").offset()
      .left;
    var menu_height = $(
      ".lui_single_menu_header_search_dropdown_menu"
    ).height();
    $(".lui_single_menu_header_search_dropdown_menu_list").css({
      top: menu_top + menu_height,
      left: menu_left,
    });

    if (
      $(".lui_single_menu_header_search_input").hasClass(
        "lui_single_menu_header-active"
      )
    ) {
      var bar_w = getBarWidth(
        $(".lui_single_menu_header_box").width(),
        $(".lui_single_menu_header_left").width(),
        $(".lui_single_menu_header_right").width()
      ); //此处无坑
      $(".lui_single_menu_header_menu").width(bar_w);
      getMore(bar_w, 0);
      // console.log(1, $(".lui_single_menu_header_right").width(), $(".lui_single_menu_header_left").width())
    } else {
      var bar_w = getBarWidth(
        $(".lui_single_menu_header_box").width(),
        $(".lui_single_menu_header_left").width(),
        $(".lui_single_menu_header_right").width()
      ); //此处无坑
      $(".lui_single_menu_header_menu").width(bar_w);
      getMore(bar_w, 0);
      // console.log(2)
    }
  }, 300);
};

//改动创建：2021-6-4，除计算外的改动，搜"改动"。坑，搜此处有坑。
//修改：2021-6-7,huangch
