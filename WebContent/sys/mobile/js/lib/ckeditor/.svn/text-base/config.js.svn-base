CKEDITOR.editorConfig = function (config) {
  config.toolbar = "Basic";
  config.toolbar_Basic = [
    [
      "Undo",
      "Redo",
      "Outdent",
      "Indent",
      "JustifyLeft",
      "JustifyCenter",
      "JustifyRight",
      "JustifyBlock",
      "NumberedList",
      "BulletedList",
      "Link",
      "Unlink",
      "Image",
      "Smiley"
    ],
  ];
  
  
  config.language = dojoConfig.locale;
  config.filebrowserImageUploadUrl =
    dojoConfig.baseUrl +
    "resource/fckeditor/editor/filemanager/upload/simpleuploader?Type=Image&mobile=true";
  config.smiley_path = dojoConfig.baseUrl + "sys/mobile/js/lib/ckeditor/plugins/smiley/images/";
  config.image2_disableResizer = true;
  config.language = dojoConfig.locale;
  config.height = "20rem";
  config.allowedContent = true;
  config.removePlugins = "image";
  config.removeButtons =
    "Cut,Copy,Paste,Anchor,Underline,Strike,Subscript,Superscript";
  config.extraPlugins = 'smiley';
  config.removeDialogTabs = "link:advanced;link:target";
};
