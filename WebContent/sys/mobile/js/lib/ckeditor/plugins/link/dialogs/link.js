/**
 * @license Copyright (c) 2003-2020, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

"use strict";

(function () {
  CKEDITOR.dialog.add("link", function (editor) {
    var plugin = CKEDITOR.plugins.link,
      initialLinkText;

    function createRangeForLink(editor, link) {
      var range = editor.createRange();

      range.setStartBefore(link);
      range.setEndAfter(link);

      return range;
    }

    function insertLinksIntoSelection(editor, data) {
      var attributes = plugin.getLinkAttributes(editor, data),
        ranges = editor.getSelection().getRanges(),
        style = new CKEDITOR.style({
          element: "a",
          attributes: attributes.set,
        }),
        rangesToSelect = [],
        range,
        text,
        nestedLinks,
        i,
        j;

      style.type = CKEDITOR.STYLE_INLINE; // need to override... dunno why.

      
      for (i = 0; i < ranges.length; i++) {
        range = ranges[i];

        // Use link URL as text with a collapsed cursor.
        if (range.collapsed) {
          // Short mailto link text view (https://dev.ckeditor.com/ticket/5736).
          text = new CKEDITOR.dom.text(
            data.linkText ||
              (data.type == "email"
                ? data.email.address
                : attributes.set["data-cke-saved-href"]),
            editor.document
          );
          range.insertNode(text);
          range.selectNodeContents(text);
        } else if (initialLinkText !== data.linkText) {
          text = new CKEDITOR.dom.text(data.linkText, editor.document);

          // Shrink range to preserve block element.
          range.shrink(CKEDITOR.SHRINK_TEXT);

          // Use extractHtmlFromRange to remove markup within the selection. Also this method is a little
          // smarter than range#deleteContents as it plays better e.g. with table cells.
          editor.editable().extractHtmlFromRange(range);

          range.insertNode(text);
        }

        // Editable links nested within current range should be removed, so that the link is applied to whole selection.
        nestedLinks = range._find("a");

        for (j = 0; j < nestedLinks.length; j++) {
          nestedLinks[j].remove(true);
        }

        // Apply style.
        style.applyToRange(range, editor);

        rangesToSelect.push(range);
      }

      editor.getSelection().selectRanges(rangesToSelect);
    }

    function editLinksInSelection(editor, selectedElements, data) {
      var attributes = plugin.getLinkAttributes(editor, data),
        ranges = [],
        isDisplayChanged,
        isEmailEqualDisplay,
        isURLEqualDisplay,
        element,
        href,
        newText,
        i;

      for (i = 0; i < selectedElements.length; i++) {
        // We're only editing an existing link, so just overwrite the attributes.
        element = selectedElements[i];
        href = element.data("cke-saved-href");
        isDisplayChanged = data.linkText && initialLinkText != data.linkText;
        isURLEqualDisplay = href == initialLinkText;
        isEmailEqualDisplay =
          data.type == "email" && href == "mailto:" + initialLinkText;

        element.setAttributes(attributes.set);
        element.removeAttributes(attributes.removed);

        if (isDisplayChanged) {
          // Display text has been changed.
          newText = data.linkText;
        } else if (isURLEqualDisplay || isEmailEqualDisplay) {
          // Update text view when user changes protocol (https://dev.ckeditor.com/ticket/4612).
          // Short mailto link text view (https://dev.ckeditor.com/ticket/5736).
          newText =
            data.type == "email"
              ? data.email.address
              : attributes.set["data-cke-saved-href"];
        }

        if (newText) {
          element.setText(newText);
        }

        ranges.push(createRangeForLink(editor, element));
      }

      // We changed the content, so need to select it again.
      editor.getSelection().selectRanges(ranges);
    }

    // Handles the event when the "Target" selection box is changed.
    var targetChanged = function () {
      var dialog = this.getDialog(),
        popupFeatures = dialog.getContentElement("target", "popupFeatures"),
        targetName = dialog.getContentElement("target", "linkTargetName"),
        value = this.getValue();

      if (!popupFeatures || !targetName) {
        return;
      }

      popupFeatures = popupFeatures.getElement();
      popupFeatures.hide();
      targetName.setValue("");

      switch (value) {
        case "frame":
          targetName.setLabel(editor.lang.link.targetFrameName);
          targetName.getElement().show();
          break;
        case "popup":
          popupFeatures.show();
          targetName.setLabel(editor.lang.link.targetPopupName);
          targetName.getElement().show();
          break;
        default:
          targetName.setValue(value);
          targetName.getElement().hide();
          break;
      }
    };

    var setupParams = function (page, data) {
      if (data[page]) {
        this.setValue(data[page][this.id] || "");
      }
    };

    var setupPopupParams = function (data) {
      return setupParams.call(this, "target", data);
    };

    var setupAdvParams = function (data) {
      return setupParams.call(this, "advanced", data);
    };

    var commitParams = function (page, data) {
      if (!data[page]) {
        data[page] = {};
      }

      data[page][this.id] = this.getValue() || "";
    };

    var commitPopupParams = function (data) {
      return commitParams.call(this, "target", data);
    };

    var commitAdvParams = function (data) {
      return commitParams.call(this, "advanced", data);
    };

    var commonLang = editor.lang.common,
      linkLang = editor.lang.link,
      anchors;

    return {
      title: editor.lang.link.title,
      minWidth: 250,
      minHeight: 110,
      buttons: [CKEDITOR.dialog.cancelButton, CKEDITOR.dialog.okButton],
      getModel: function (editor) {
        var elements = plugin.getSelectedLink(editor, true),
          firstLink = elements[0] || null;

        return firstLink;
      },
      contents: [
        {
          id: "info",
          label: linkLang.title,
          title: linkLang.title,
          elements: [
            {
              type: "vbox",
              id: "urlOptions",
              children: [
                {
                  type: "hbox",
                  widths: ["25%", "75%"],
                  children: [
                    {
                      type: "text",
                      id: "url",
                      label: commonLang.url,
                      required: true,
                      onLoad: function () {
                        this.allowOnChange = true;
                      },
                      validate: function () {
                        var dialog = this.getDialog();

                        if (
                          !editor.config.linkJavaScriptLinksAllowed &&
                          /javascript\:/.test(this.getValue())
                        ) {
                          alert(commonLang.invalidValue); // jshint ignore:line
                          return false;
                        }

                        // Edit Anchor.
                        if (this.getDialog().fakeObj) {
                          return true;
                        }

                        var func = CKEDITOR.dialog.validate.notEmpty(
                          linkLang.noUrl
                        );
                        return func.apply(this);
                      },
                      setup: function (data) {
                        this.allowOnChange = false;
                        if (data.url) {
                          this.setValue(data.url.url);
                        }
                        this.allowOnChange = true;

                        this.getInputElement().setAttribute(
                          "placeholder",
                          "网址"
                        );
                      },
                      commit: function (data) {
                        data.type = "url"
                        var url = this.getValue()
                        data.url = { protocol: "" }
                        if (!url.startsWith("http")) {
                          data.url.protocol = "http://"
                        }
                        data.url.url = this.getValue()
                        this.allowOnChange = false
                      },
                    },
                  ],
                  setup: function () {
                    this.getElement().show();
                  },
                },
              ],
            },
            {
              type: "text",
              id: "linkDisplayText",
              label: linkLang.displayText,
              setup: function () {
                this.enable();
                this.setValue(editor.getSelection().getSelectedText());
                initialLinkText = this.getValue();
                this.getInputElement().setAttribute(
                  "placeholder",
                  linkLang.textPlaceholder
                );
              },
              commit: function (data) {
                data.linkText = this.isEnabled() ? this.getValue() : "";
              },
            },
          ],
        },
      ],
      onShow: function () {
        var editor = this.getParentEditor(),
          selection = editor.getSelection(),
          displayTextField = this.getContentElement("info", "linkDisplayText")
            .getElement()
            .getParent()
            .getParent(),
          elements = plugin.getSelectedLink(editor, true),
          firstLink = elements[0] || null;

        // Fill in all the relevant fields if there's already one link selected.
        if (firstLink && firstLink.hasAttribute("href")) {
          // Don't change selection if some element is already selected.
          // For example - don't destroy fake selection.
          if (!selection.getSelectedElement() && !selection.isInTable()) {
            selection.selectElement(firstLink);
          }
        }

        var data = plugin.parseLinkAttributes(editor, firstLink);

        // Here we'll decide whether or not we want to show Display Text field.
        if (
          elements.length <= 1 &&
          plugin.showDisplayTextForElement(firstLink, editor)
        ) {
          displayTextField.show();
        } else {
          displayTextField.hide();
        }

        // Record down the selected element in the dialog.
        this._.selectedElements = elements;

        this.setupContent(data);
      },
      onOk: function () {
        var data = {};

        // Collect data from fields.
        this.commitContent(data);
        data.type = "url";
        if (data.url.url.startsWith("http")) {
          data.url.protocol = "";
        }
        if (!this._.selectedElements.length) {
          insertLinksIntoSelection(editor, data);
        } else {
          editLinksInSelection(editor, this._.selectedElements, data);

          delete this._.selectedElements;
        }
      },
      onLoad: function () {
        if (!editor.config.linkShowAdvancedTab) {
          this.hidePage("advanced"); //Hide Advanded tab.
        }

        if (!editor.config.linkShowTargetTab) {
          this.hidePage("target"); //Hide Target tab.
        }
      },
      // Inital focus on 'url' field if link is of type URL.
      onFocus: function () {
        var urlField = this.getContentElement("info", "url");
        urlField.select();
      },
    };
  });
})();
// jscs:disable maximumLineLength
/**
 * The e-mail address anti-spam protection option. The protection will be
 * applied when creating or modifying e-mail links through the editor interface.
 *
 * Two methods of protection can be chosen:
 *
 * 1. The e-mail parts (name, domain, and any other query string) are
 *     assembled into a function call pattern. Such function must be
 *     provided by the developer in the pages that will use the contents.
 * 2. Only the e-mail address is obfuscated into a special string that
 *     has no meaning for humans or spam bots, but which is properly
 *     rendered and accepted by the browser.
 *
 * Both approaches require JavaScript to be enabled.
 *
 *		// href="mailto:tester@ckeditor.com?subject=subject&body=body"
 *		config.emailProtection = '';
 *
 *		// href="<a href=\"javascript:void(location.href=\'mailto:\'+String.fromCharCode(116,101,115,116,101,114,64,99,107,101,100,105,116,111,114,46,99,111,109)+\'?subject=subject&body=body\')\">e-mail</a>"
 *		config.emailProtection = 'encode';
 *
 *		// href="javascript:mt('tester','ckeditor.com','subject','body')"
 *		config.emailProtection = 'mt(NAME,DOMAIN,SUBJECT,BODY)';
 *
 * @since 3.1.0
 * @cfg {String} [emailProtection='' (empty string = disabled)]
 * @member CKEDITOR.config
 */
