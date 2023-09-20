define(['dojo/_base/declare'], function (declare) {
    return declare('sys.organization.mobile.js.eco.HideRangeSwitch', null, {
        property: '',
        buildRendering: function () {
            this.inherited(arguments);
            var _value = this.value == 'on' ? true : false;
            this.propDom.value = _value;
        },
        startup: function () {
            this.inherited(arguments);
            var _value = this.value == 'on' ? true : false;
            this.showHideRangeTable(_value);
        },
        onStateChanged: function (newState) {
            this.inherited(arguments);
            var _value = newState == 'on' ? true : false;
            this.propDom.value = _value;
            this.showHideRangeTable(_value);
        },
        showHideRangeTable: function (state) {
            var fdHideRangeTable = document.getElementById("fdHideRangeTable");
            if (state) {
                fdHideRangeTable.style.display = 'inline-table';
            } else {
                fdHideRangeTable.style.display = 'none';
            }
        }
    });
});