define([
    "dojo/_base/declare",
], function(declare) {
    var claz = declare("tag.util", null, {

        encodeHTML: function(str) {
            if (str == null || str.length == 0) return "";
            return str
                .replace(/&/g, "{#amp}")
                .replace(/</g, "{#lt}")
                .replace(/>/g, "{#gt}")
                .replace(/\'/g, "{#apos}")
                .replace(/\"/g, "{#quot}")
                .replace(/\\/g, "{#slash}")
        },
        decodeHTML: function(str) {
            if (str == null || str.length == 0) return "";
            return (str + "")
                .replace(/{#amp}/g, "&")
                .replace(/{#lt}/g, "<")
                .replace(/{#gt}/g, ">")
                .replace(/{#apos}/g, "'")
                .replace(/{#quot}/g, '"')
                .replace(/{#slash}/g, '\\')
        },
        GenerateId: function() {
            return String(new Date().getTime());
        }
    });
    return new claz();
});
