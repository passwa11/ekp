package com.landray.kmss.sys.portal.util.jsoup;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class JspElement extends Element {

    public JspElement(Tag tag, String baseUri) {
        //jsoup从1.6升级到1.14后，不需要使用JspAttributes
        //super(tag, baseUri, new JspAttributes());
        super(tag, baseUri, new Attributes());
    }

    @Override
    public Element attr(String attributeKey, String attributeValue) {
        Attributes attributes = this.attributes();
        if (attributes.hasKeyIgnoreCase(attributeKey)) {//存在，先删除，在新增
            attributes.removeIgnoreCase(attributeKey);
        }
        attributes.add(attributeKey, attributeValue);
        return this;
    }

	/*
	private Parser getParser(){
		Document doc = this.ownerDocument();
		Parser parser;
		if(doc== null){
			parser = Parser.htmlParser();
			parser.settings(ParseSettings.preserveCase);
			return parser;
		}else{
			parser = doc.parser();
			parser.settings(ParseSettings.preserveCase);
		}
		return parser;
	}
	*/
}
