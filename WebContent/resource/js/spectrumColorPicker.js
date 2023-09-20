/**
 * 资源面板类
 */

window.SpectrumColorPicker = {
    palette: [
        ['white', 'black', 'blanchedalmond',
            'rgb(255, 128, 0);', 'hsv 100 70 50'],
        ['red', '#ffff11', 'green', 'blue', 'violet']
    ],
    temp: {
        input: "<input type='hidden' value='#000000'/>",
        dom: "<div style=\"padding-left: 5px;cursor:pointer;width: 150px;\">\n" +
            "        <span class='colorText' style='margin-right: 10px;letter-spacing: 1;'>#000000</span>\n" +
            "        <span class='colorBox' style=\" display: inline-block; width: 16px; height: 16px; vertical-align: -3;background-color: #000000\"></span>\n" +
            " </div>"

    },
    init: function (valueName) {
        var  updateShow =  function updateShow(self,valueName,oldColor,_color){
            var topicObj = {
                oldColor : oldColor,
                color : _color,
                ele : $(this)
            }
            self.find(".colorText").html(_color);
            self.find("[name='" + valueName + "_sColorPic']").val(_color);
            self.find(".colorBox").css("background-color", _color);
            seajs.use(['lui/jquery','lui/topic'],function($,topic) {
                topic.publish("modeling.SpectrumColorPicker.change",topicObj);
            })
        }
        var self = this;
        var $colorBtn = $("[data-color-mark-id='" + valueName + "']").find("[data-lui-mark=\"colorColor\"]");
        $colorBtn.append(self.temp.dom);
        var $in = $(self.temp.input);
        $in.attr("name", valueName + "_sColorPic");
        $colorBtn.append($in);
        $colorBtn.spectrum({
            showInput: true,
            palette:self.palette,
            preferredFormat: "hex",
            showPalette: true,
            change: function (color) {
                var _color = color.toHexString().toUpperCase();
                var oldColor = $(this).find("[name='" + valueName + "_sColorPic']").val();
                if (_color == "#FFFFFF") {
                    $(this).find(".colorBox").css("border", "1px solid #efefef");
                } else {
                    $(this).find(".colorBox").css("border", "1px solid #FFFFFF");
                }
                updateShow($(this),valueName,oldColor,_color);
            },

            show:function (color){

            },
            move:function (color){
                var _color = color.toHexString().toUpperCase();
                var oldColor = $(this).find("[name='" + valueName + "_sColorPic']").val();
                updateShow($(this),valueName,oldColor,_color);
            },
            hide:function (color){
                var _color = color.toHexString().toUpperCase();
                var oldColor = $(this).find("[name='" + valueName + "_sColorPic']").val();
                updateShow($(this),valueName,oldColor,_color);
            }
        })
    },

    setColor: function (valueName, initColor) {
        var $colorBtn = $("[data-color-mark-id='" + valueName + "']").find("[data-lui-mark=\"colorColor\"]");
        if (initColor) {
            if (initColor == "#FFFFFF") {
                $colorBtn.find(".colorBox").css("border", "1px solid #efefef");
            } else {
                $colorBtn.find(".colorBox").css("border", "1px solid #FFFFFF");
            }
            $colorBtn.find(".colorText").html(initColor);
            $colorBtn.find("[name='" + valueName + "_sColorPic']").val(initColor);
            $colorBtn.find(".colorBox").css("background-color", initColor);
            $colorBtn.spectrum("set", initColor);
        }
    }


};
