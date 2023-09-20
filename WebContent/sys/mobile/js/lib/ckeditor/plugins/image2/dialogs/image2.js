/**
 * @license Copyright (c) 2003-2020, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

/**
 * @fileOverview Image plugin based on Widgets API
 */

"use strict";

CKEDITOR.dialog.add("image2", function (editor) {
  // RegExp: 123, 123px, empty string ""
  var dialog;
  var regexGetSizeOrEmpty = /(^\s*(\d+)(px)?\s*$)|^$/i,
    lang = editor.lang.image2,
    commonLang = editor.lang.common,
    helpers = CKEDITOR.plugins.image2,
    // Editor instance configuration.
    config = editor.config,
    // Content restrictions defined by the widget which
    getNatural = helpers.getNatural,
    // Global variables referring to the dialog's context.
    doc,
    widget,
    image,
    // Global variable referring to this dialog's image pre-loader.
    preLoader,
    // Global variables referring to dialog fields and elements.
    natural;


  // Creates a function that pre-loads images. The callback function passes
  // [image, width, height] or null if loading failed.
  //
  // @returns {Function}
  function createPreLoader () {
    var image = doc.createElement("img"),
      listeners = [];

    function addListener (event, callback) {
      listeners.push(
        image.once(event, function (evt) {
          removeListeners();
          callback(evt);
        })
      );
    }



    function removeListeners () {
      var l;
      while ((l = listeners.pop())) l.removeListener();
    }

    // @param {String} src.
    // @param {Function} callback.
    return function (src, callback, scope) {
      addListener("load", function () {
        // Don't use image.$.(width|height) since it's buggy in IE9-10 (https://dev.ckeditor.com/ticket/11159)
        var dimensions = getNatural(image);

        callback.call(scope, image, dimensions.width, dimensions.height);
      });

      addListener("error", function () {
        callback(null);
      });

      addListener("abort", function () {
        callback(null);
      });

      image.setAttribute(
        "src",
        (config.baseHref || "") +
        src +
        "?" +
        Math.random().toString(16).substring(2)
      );
    };
  }

  var widget 

  var srcBoxChildren = [
    {
      id: "src",
      type: "text",
      commit: function (widget) {
        widget = widget
      },
      setup: function () {

        dialog.on('change', function (evt) {
          var error = evt.data.error
          if (error) {
            if (top.rtfImgUploadLoading) {
            	top.rtfImgUploadLoading.hide()
            	top.rtfImgUploadLoading = null
            }
          }
        })

        var self = this

        this.on("change", function (evt) {
          var url = evt.data.value
          if (top.rtfImgUploadLoading) {
        	  top.rtfImgUploadLoading.hide()
        	  top.rtfImgUploadLoading = null
          }
          dialog.hide()
          if (url) {
            widget.setData("src", url)
          }
        })
      },
    },
  ];

  return {
    title: lang.title,
    minWidth: 250,
    minHeight: 50,
    buttons: [CKEDITOR.dialog.cancelButton, CKEDITOR.dialog.okButton],
    onFocus: function () { },
    onLoad: function () {
      dialog = this;
      // Create a "global" reference to the document for this dialog instance.
      doc = this._.element.getDocument();

      // Create a pre-loader used for determining dimensions of new images.
      preLoader = createPreLoader();
    },

    onOk: function () {
      this.getContentElement("Upload", "uploadButton").click();
      require(["mui/dialog/Tip"], function (Tip) {
    	if(!top.rtfImgUploadLoading){
    		top.rtfImgUploadLoading = Tip.progressing({ text: lang.uploading });
    	}
      });
      return false;
    },

    onShow: function () {
      // Create a "global" reference to edited widget.
      widget = this.getModel();

      // Create a "global" reference to widget's image.
      image = widget.parts.image;

      // Natural dimensions of the image.
      natural = getNatural(image);
    },
    contents: [
      {
        id: "Upload",
        filebrowser: "uploadButton",
        elements: [
          {
            type: "file",
            id: "upload",
            label: lang.btnUpload,
            accept: "image/gif,image/jpeg,image/bmp,image/png,image/tiff",
            style: "height:40px",
            validate: CKEDITOR.dialog.validate.notEmpty(lang.empty)
          },
          {
            type: "fileButton",
            id: "uploadButton",
            filebrowser: "Upload:src",
            className: "cke_dialog_element_hide",
            label: lang.btnUpload,
            for: ["Upload", "upload"],
          },
          {
            type: "vbox",
            padding: 0,
            children: [
              {
                type: "hbox",
                widths: ["100%"],
                className: "cke_dialog_element_hide",
                children: srcBoxChildren,
              },
            ],
          },
        ],
      },
    ],
  };
});
