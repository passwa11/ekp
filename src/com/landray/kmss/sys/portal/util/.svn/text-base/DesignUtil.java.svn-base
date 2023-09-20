package com.landray.kmss.sys.portal.util;

import com.landray.kmss.sys.portal.util.jsoup.JspElement;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.SysUiConstant;
import com.landray.kmss.sys.ui.xml.model.SysUiOperation;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiSource;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class DesignUtil {
    public static class HeaderInfo {
        private String logo;
        private String headerId;
        private String headerVars;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getHeaderId() {
            return headerId;
        }

        public void setHeaderId(String headerId) {
            this.headerId = headerId;
        }

        public String getHeaderVars() {
            return headerVars;
        }

        public void setHeaderVars(String headerVars) {
            this.headerVars = headerVars;
        }
    }

    public static class FooterInfo {
        private String footerId;
        private String footerVars;

        public String getFooterId() {
            return footerId;
        }

        public void setFooterId(String footerId) {
            this.footerId = footerId;
        }

        public String getFooterVars() {
            return footerVars;
        }

        public void setFooterVars(String footerVars) {
            this.footerVars = footerVars;
        }

    }

    public static class GuideInfo {
        private String guideId;
        private String guideCfg;

        public String getGuideId() {
            return guideId;
        }

        public void setGuideId(String guideId) {
            this.guideId = guideId;
        }

        public String getGuideCfg() {
            return guideCfg;
        }

        public void setGuideCfg(String guideCfg) {
            this.guideCfg = guideCfg;
        }

    }

    public static class DesignInfo {
        private String body;
        private HeaderInfo header;
        private FooterInfo footer;
        private GuideInfo guide;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public HeaderInfo getHeader() {
            return header;
        }

        public void setHeader(HeaderInfo header) {
            this.header = header;
        }

        public FooterInfo getFooter() {
            return footer;
        }

        public void setFooter(FooterInfo footer) {
            this.footer = footer;
        }

        public GuideInfo getGuide() {
            return guide;
        }

        public void setGuide(GuideInfo guide) {
            this.guide = guide;
        }

    }

    public static DesignInfo compile(String str) throws Exception {
        DesignInfo info = new DesignInfo();
        Parser parser = Parser.htmlParser();
        parser.settings(ParseSettings.preserveCase);
        //Document doc = Jsoup.parse(str);
        Document doc = Jsoup.parse(str, "", parser);
        // doc.outputSettings().indentAmount()
        // 页眉页脚转换
        info.setHeader(convertHeader(doc));
        info.setFooter(convertFooter(doc));
        info.setGuide(converGuide(doc));
        // 部件转换
        convertWidget(doc);
        // System.out.println(doc.html());
        // 容器转换
        convertContainer(doc);
        // System.out.println(doc.html());
        // VBox转换
        convertVBox(doc);
        // System.out.println(doc.html());
        // Editable
        convertEditable(doc);
        // System.out.println(doc.html());
        // Template
        convertTemplate(doc);
        // System.out.println(doc.html());
        //#160872解决门户窗口设置权限导致样式变化的问题
        moveVSpace(doc);
        // 返回结果
        StringBuilder sb = new StringBuilder();
        sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\r\n");
        sb.append("<%@ include file=\"/sys/ui/jsp/common.jsp\"%>\r\n");
        sb.append("<%@ include file=\"/sys/portal/sys_portal_page/page.jsp\"%>\r\n");
        sb.append("<%@ taglib uri=\"/WEB-INF/KmssConfig/sys/portal/portal.tld\" prefix=\"portal\"%>\r\n");
        String html = doc.body().html();
        html = html.replaceAll("kmss:authshow", "kmss:authShow");
        sb.append(html);
        info.setBody(sb.toString());
        return info;
    }

    private static HeaderInfo convertHeader(Document doc) throws Exception {
        Elements elems = doc.select("div[portal-type$=Header]");
        if (elems.size() > 0) {
            Element ele = elems.get(0);
            HeaderInfo hinfo = new HeaderInfo();
            hinfo.setLogo(ele.getElementsByAttributeValue("name", "fdLogo")
                    .attr("value"));
            hinfo.setHeaderId(ele.getElementsByAttributeValue("name",
                    "fdHeader").attr("value"));
            hinfo.setHeaderVars(StringUtil.unescape(ele
                    .getElementsByAttributeValue("name", "fdHeaderVars").attr(
                            "value")));
            elems.remove();
            return hinfo;
        }
        return null;
    }

    private static FooterInfo convertFooter(Document doc) throws Exception {
        Elements elems = doc.select("div[portal-type$=Footer]");
        if (elems.size() > 0) {
            Element ele = elems.get(0);
            FooterInfo finfo = new FooterInfo();
            finfo.setFooterId(ele.getElementsByAttributeValue("name",
                    "fdFooter").attr("value"));
            finfo.setFooterVars(StringUtil.unescape(ele
                    .getElementsByAttributeValue("name", "fdFooterVars").attr(
                            "value")));
            elems.remove();
            return finfo;
        }
        return null;
    }

    private static GuideInfo converGuide(Document doc) throws Exception {
        Elements elems = doc.select("div[portal-type$=Guide]");
        if (elems.size() > 0) {
            Element ele = elems.get(0);
            GuideInfo guideInfo = new GuideInfo();
            guideInfo.setGuideId(ele.getElementsByAttributeValue("name",
                    "fdGuide").attr("value"));
            guideInfo.setGuideCfg(StringUtil.unescape(ele
                    .getElementsByAttributeValue("name", "fdGuideCfg").attr(
                            "value")));
            elems.remove();
            return guideInfo;
        }
        return null;
    }

    private static void convertTemplate(Document doc) throws Exception {
        Elements templates = doc.select("div[portal-type$=Template]");
        for (int i = 0; i < templates.size(); i++) {
            Element ele = templates.get(i);
            String ref = ele.attr("ref");
            String config = ele.attr("data-config");
            Element in = new Element(Tag.valueOf("template:include"), "");
            if (StringUtil.isNotNull(ref)) {
                in.attr("ref", ref);
            }
            if (StringUtil.isNotNull(config)) {
                JSONObject json = JSONObject.fromObject(StringUtil.unescape(config));
                Iterator iterator = json.keys();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    in.attr(key.toString(), json.get(key.toString()).toString());
                }
            }
            while (ele.childNodes().size() > 0) {
                in.appendChild(ele.childNodes().get(0));
            }
            ele.replaceWith(in);
        }
    }

    private static void moveVSpace(Document doc) {
        Elements elementsAuthshow = doc.getElementsByTag("kmss:authshow");
        if (elementsAuthshow != null && elementsAuthshow.size() > 0) {
            for (int i = 0; i < elementsAuthshow.size(); i++) {
                Element authshow = elementsAuthshow.get(i);
                Element vspace = authshow.nextElementSibling();
                if (vspace != null && "lui/vspace!VSpace".equals(vspace.attr("data-lui-type"))) {
                    authshow.append(vspace.toString());
                    vspace.remove();
                }
            }
        }
    }

    private static void convertEditable(Document doc) throws Exception {
        Elements editables = doc.select("div[portal-type$=Editable]");
        for (int i = 0; i < editables.size(); i++) {
            Element ele = editables.get(i);
            removeWidgetDock(ele);
            ele.removeAttr("data-config");
            ele.select(">.widgetDock").removeAttr("class").attr("data-lui-type",
                    "lui/vspace!VSpace");
            String key = ele.attr("portal-key");
            Element in = new Element(Tag.valueOf("template:replace"), "");
            in.attr("name", key);
            // in.appendChild(ele);
            while (ele.childNodes().size() > 0) {
                in.appendChild(ele.childNodes().get(0));
            }
            ele.replaceWith(in);
        }
    }

    private static void convertVBox(Document doc) throws Exception {
        Elements widgets = doc.select("table[portal-type$=VBox]");
        for (int i = 0; i < widgets.size(); i++) {
            Element ele = widgets.get(i);
            ele.removeAttr("portal-type");
            ele.removeAttr("portal-key");

            String config = ele.attr("data-config");
            String style = "table-layout: fixed;";
            JSONObject json = JSONObject
                    .fromObject(StringUtil.unescape(config));
            if (json.containsKey("boxStyle")) {
                style += json.getString("boxStyle");
            }
            ele.attr("style", style);

            ele.removeAttr("data-config");
            ele.select(">tbody>tr>td.containerSpacing").removeAttr("class");

            ele.attr("data-lui-type", "lui/portal!VBox");
            ele.prepend("<script type='text/config'>" + json.toString() + "</script>");
        }
    }

    private static void removeWidgetDock(Element ele) {
        if (ele.children().size() > 1) {
            if (ele.children().get(ele.children().size() - 1).className()
                    .indexOf("widgetDock") >= 0) {
                ele.children().get(ele.children().size() - 1).remove();
            }
            if (ele.children().get(0).className().indexOf("widgetDock") >= 0) {
                ele.children().get(0).remove();
            }
        } else {
            if (ele.children().size() > 0
                    && ele.children().get(0).className().indexOf("widgetDock") >= 0) {
                ele.empty();
            }
        }
    }

    private static void convertContainer(Document doc) throws Exception {
        Elements widgets = doc.select("td[portal-type$=Container]");
        for (int i = 0; i < widgets.size(); i++) {
            Element ele = widgets.get(i);
            removeWidgetDock(ele);
            ele.removeAttr("class");
            ele.removeAttr("portal-key");
            ele.removeAttr("portal-type");

            String config = ele.attr("data-config");
            String style = "";
            JSONObject json = JSONObject
                    .fromObject(StringUtil.unescape(config));
            if (json.containsKey("columnStyle")) {
                style += json.getString("columnStyle");
            }
            if (StringUtil.isNotNull(style)) {
                ele.attr("style", style);
            }

            ele.removeAttr("data-config");
            ele.select(">.widgetDock").removeAttr("class").attr("data-lui-type",
                    "lui/vspace!VSpace");
            ele.attr("data-lui-type", "lui/portal!Container");
            ele.prepend("<script type='text/config'>" + json.toString() + "</script>");
        }
    }

    private static void convertWidget(Document doc) throws Exception {
        Elements widgets = doc.select("div[portal-type$=Widget]");
        for (int i = 0; i < widgets.size(); i++) {
            Element ele = widgets.get(i);
            String key = ele.attr("portal-key");
            Elements els = ele.select(">script[type$=config]");
            JSONObject config = null;
            if (els.size() == 1) {
                config = JSONObject.fromObject(els.get(0).html());
            } else {
                throw new RuntimeException("部件" + key + "配置信息错误");
            }

            Node in = createPortlet(config);
            // System.out.println(in.toString());
            in.attr("id", key);
            // 权限标签
            if (in != null && config.containsKey("authReaderIds")
                    && StringUtil.isNotNull(config.getString("authReaderIds"))) {
                String authReaderIds = config.getString("authReaderIds");
                Element authEle = new JspElement(Tag.valueOf("kmss:authShow"), "");
                authEle.attr("baseOrgIds", authReaderIds);
                authEle.attr("roles", "SYSROLE_ADMIN");
                in = authEle.appendChild(in);
            }
            ele.replaceWith(in);
            // System.out.print(ele.html());
        }
    }

    private static JspElement createContent(JSONObject portlet,
                                            boolean text2iframe) throws Exception {

        if ("sys.ui.jsp".equals(portlet.getString("format"))) {
            JspElement content = new JspElement(Tag.valueOf("portal:widget"),
                    "");
            String sid = portlet.getString("sourceId");
            SysUiSource source = SysUiPluginUtil.getSourceById(sid);
            sid = sid.substring(0, sid.lastIndexOf(".source"));
            SysUiPortlet xport = SysUiPluginUtil.getPortletById(sid);
            if (source == null || xport == null) {
                content.html("sourceId=" + sid + "未注册");
            } else {
                String jsp = source.getFdBody().getSrc();
                if (StringUtil.isNotNull(jsp)) {
                    content.attr("file", jsp);
                } else {
                    content.html(source.getFdBody().getBody());
                }
                JSONObject sourceOpt = portlet.getJSONObject("sourceOpt");
                if (sourceOpt != null && !sourceOpt.isNullObject()
                        && !sourceOpt.isEmpty()) {
                    if (xport.getFdVars().size() > 0) {
                        for (int i = 0; i < xport.getFdVars().size(); i++) {
                            SysUiVar var = xport.getFdVars().get(i);
                            Object val = sourceOpt.get(var.getKey());
                            if (val != null) {
                                JspElement param = new JspElement(
                                        Tag.valueOf("portal:param"), "");
                                param.attr("name", var.getKey());
                                if (val instanceof JSONObject) {
                                    param.attr("value", sourceOpt
                                            .getJSONObject(var.getKey())
                                            .getString(var.getKey()));
                                } else {
                                    param.attr("value",
                                            sourceOpt.getString(var.getKey()));
                                }
                                content.appendChild(param);
                            }
                        }
                    }
                }
            }
            return content;
        } else {
            JspElement content = new JspElement(Tag.valueOf("portal:portlet"), "");
            content.attr("title", portlet.getString("title"));
            if (portlet.containsKey("subtitle")) {
                content.attr("subtitle", portlet.getString("subtitle"));
            }
            if (portlet.containsKey("titleicon")) {
                content.attr("titleicon", portlet.getString("titleicon"));
            }
            if (portlet.containsKey("titleimg")) {
                content.attr("titleimg", portlet.getString("titleimg"));
            }
            if (portlet.get("refresh") != null) {
                String refresh = portlet.getString("refresh").trim();
                if (refresh.length() > 0 && !"0".equals(refresh)) {
                    content.attr("cfg-refresh", ""
                            + (Integer.valueOf(refresh) * 60));
                }
            }
            if (portlet.get("extendClass") != null) {
                String extendClass = portlet.getString("extendClass").trim();
                if (extendClass.length() > 0) {
                    content.attr("cfg-extClass", extendClass);
                }
            }
            JspElement dataview = createDataView(portlet, text2iframe);
            content.appendChild(dataview);
            String sid = portlet.getString("sourceId");
            sid = sid.substring(0, sid.lastIndexOf(".source"));
            SysUiPortlet xport = SysUiPluginUtil.getPortletById(sid);
            if (xport == null) {
                content.html("sourceId=" + sid + "未注册");
            } else {
                JSONObject sourceOpt = portlet.getJSONObject("sourceOpt");
                if (sourceOpt != null && !sourceOpt.isNullObject()
                        && !sourceOpt.isEmpty()) {
                    if (xport.getFdVars().size() > 0) {
                        boolean isNullFlag = true;
                        for (int i = 0; i < xport.getFdVars().size(); i++) {
                            SysUiVar var = xport.getFdVars().get(i);
                            Object val = sourceOpt.get(var.getKey());
                            if (val != null) {
                                if (val instanceof JSONObject) {
                                    if (sourceOpt.getJSONObject(
                                            var.getKey())
                                            .containsKey(var.getKey())) {
                                        content.attr(
                                                "var-" + var.getKey(),
                                                sourceOpt.getJSONObject(
                                                        var.getKey()).getString(
                                                        var.getKey()));
                                        isNullFlag = false;
                                    }
                                } else {
                                    content.attr("var-" + var.getKey(),
                                            sourceOpt.getString(var.getKey()));
                                    isNullFlag = false;
                                }
                            }
                        }
                        if (isNullFlag) {
                            content.html("");
                        }
                    }
                }
                JSONArray operations = null;
                try {
                    operations = portlet.getJSONArray("operations");
                } catch (Exception e) {
                }
                List<SysUiOperation> opts = xport.getFdOperations();
                if (opts.size() > 0) {
                    for (int i = 0; i < opts.size(); i++) {
                        SysUiOperation operation = opts.get(i);
                        if (isExistOperation(operation, operations)) {
                            if ("left".equals(operation.getAlign())) {
                                JspElement opt = new JspElement(
                                        Tag.valueOf("ui:operation"), "");
                                if (StringUtil.isNotNull(operation.getHref())) {
                                    opt.attr("href", operation.getHref());
                                }
                                if (StringUtil.isNotNull(operation.getIcon())) {
                                    opt.attr("icon", operation.getIcon());
                                }
                                if (StringUtil.isNotNull(operation.getName())) {
                                    opt.attr("name", operation.getName());
                                }
                                if (StringUtil.isNotNull(operation.getTarget())) {
                                    opt.attr("target", operation.getTarget());
                                }
                                if (StringUtil.isNotNull(operation.getType())) {
                                    opt.attr("type", operation.getType());
                                }
                                if (StringUtil.isNotNull(operation.getAlign())) {
                                    opt.attr("align", operation.getAlign());
                                }
                                content.appendChild(opt);
                            }
                        }
                    }
                    for (int i = opts.size() - 1; i >= 0; i--) {
                        SysUiOperation operation = opts.get(i);
                        if (isExistOperation(operation, operations)) {
                            if (!"left".equals(operation.getAlign())) {
                                JspElement opt = new JspElement(
                                        Tag.valueOf("ui:operation"), "");
                                if (StringUtil.isNotNull(operation.getHref())) {
                                    opt.attr("href", operation.getHref());
                                }
                                if (StringUtil.isNotNull(operation.getIcon())) {
                                    opt.attr("icon", operation.getIcon());
                                }
                                if (StringUtil.isNotNull(operation.getName())) {
                                    opt.attr("name", operation.getName());
                                }
                                if (StringUtil.isNotNull(operation.getTarget())) {
                                    opt.attr("target", operation.getTarget());
                                }
                                if (StringUtil.isNotNull(operation.getType())) {
                                    opt.attr("type", operation.getType());
                                }
                                if (StringUtil.isNotNull(operation.getAlign())) {
                                    opt.attr("align", operation.getAlign());
                                }
                                content.appendChild(opt);
                            }
                        }
                    }
                }
            }
            if (portlet.getString("sourceId").indexOf(SysUiConstant.SEPARATOR) > 0) {
                String code = portlet.getString("sourceId");
                code = code.substring(0, code.indexOf(SysUiConstant.SEPARATOR));
                //content.attr("cfg-contextPath",SysPortalConfig.getServerUrl(code));
                content.attr("cfg-server", code);
            }
            return content;
        }
    }

    private static JspElement createDataView(JSONObject portlet,
                                             boolean text2iframe) throws Exception {
        JspElement dataview = new JspElement(Tag.valueOf("ui:dataview"), "");
        if (text2iframe && "sys.ui.html".equals(portlet.getString("format"))) {
            dataview.attr("format", "sys.ui.iframe");
            JspElement source = new JspElement(Tag.valueOf("ui:source"), "");
            source.attr("type", "Static");
            String portletId = portlet.getString("sourceId").substring(0, portlet.getString("sourceId").length() - 7);
            if (portletId.indexOf(SysUiConstant.SEPARATOR) >= 0) {
                portletId = portletId.substring(portletId.indexOf(SysUiConstant.SEPARATOR) + SysUiConstant.SEPARATOR.length());
            }
            JSONObject sOpt = new JSONObject();
            JSONObject sourceOpt = portlet.getJSONObject("sourceOpt");
            if (sourceOpt != null && !sourceOpt.isNullObject()
                    && !sourceOpt.isEmpty()) {
                Iterator<?> keys = sourceOpt.keys();
                while (keys.hasNext()) {
                    String key = keys.next().toString();
                    if ("__sourcejsname".equals(key)) {
                        continue;
                    }
                    if (sourceOpt.get(key) instanceof JSONObject) {
                        sOpt.put(key, sourceOpt.getJSONObject(key).getString(key));
                    } else {
                        sOpt.put(key, sourceOpt.get(key).toString());
                    }
                }
            }
            String renderId = portlet.getString("renderId");
            JSONObject renderOpt = portlet.getJSONObject("renderOpt");
            JSONObject rOpt = new JSONObject();
            if (renderOpt != null && !renderOpt.isNullObject()
                    && !renderOpt.isEmpty()) {
                Iterator<?> keys = renderOpt.keys();
                while (keys.hasNext()) {
                    String key = keys.next().toString();
                    if ("__renderjsname".equals(key)) {
                        continue;
                    }
                    if (renderOpt.get(key) instanceof JSONObject) {
                        rOpt.put(key, renderOpt.getJSONObject(key).getString(key));
                    } else {
                        rOpt.put(key, renderOpt.get(key).toString());
                    }
                }
            }
            String url = "/resource/jsp/widget.jsp?portletId=" + URLEncoder.encode(portletId, "UTF-8") + "&sourceOpt=" + URLEncoder.encode(sOpt.toString(), "UTF-8") + "&renderId=" + URLEncoder.encode(renderId, "UTF-8") + "&renderOpt=" + URLEncoder.encode(rOpt.toString(), "UTF-8") + "&LUIID=!{lui.element.id}";
            DataNode text = new DataNode("{\"src\":\"" + url + "\"}");
            source.appendChild(text);
            dataview.appendChild(source);
            JspElement render = new JspElement(Tag.valueOf("ui:render"), "");
            render.attr("ref", "sys.ui.iframe.default");
            dataview.appendChild(render);
        } else {
            dataview.attr("format", portlet.getString("format"));

            JspElement source = new JspElement(Tag.valueOf("ui:source"), "");
            source.attr("ref", portlet.getString("sourceId"));

            JSONObject sourceOpt = portlet.getJSONObject("sourceOpt");
            if (sourceOpt != null && !sourceOpt.isNullObject()
                    && !sourceOpt.isEmpty()) {
                Iterator<?> keys = sourceOpt.keys();
                while (keys.hasNext()) {
                    String key = keys.next().toString();
                    if ("__sourcejsname".equals(key)) {
                        continue;
                    }
                    if (sourceOpt.get(key) instanceof JSONObject) {
                        if (sourceOpt.getJSONObject(key).containsKey(key)) {
                            source.attr("var-" + key + "",
                                    sourceOpt.getJSONObject(key)
                                            .getString(key));
                        }
                    } else {
                        source.attr("var-" + key + "", sourceOpt.get(key)
                                .toString());
                    }
                }
            }
            dataview.appendChild(source);
            JspElement render = new JspElement(Tag.valueOf("ui:render"), "");
            render.attr("ref", portlet.getString("renderId"));
            JSONObject renderOpt = portlet.getJSONObject("renderOpt");
            if (renderOpt != null && !renderOpt.isNullObject()
                    && !renderOpt.isEmpty()) {
                Iterator<?> keys = renderOpt.keys();
                while (keys.hasNext()) {
                    String key = keys.next().toString();
                    if ("__renderjsname".equals(key)) {
                        continue;
                    }
                    if (renderOpt.get(key) instanceof JSONObject) {
                        render.attr("var-" + key + "",
                                renderOpt.getJSONObject(key).getString(key));
                    } else {
                        render.attr("var-" + key + "", renderOpt.get(key)
                                .toString());
                    }
                }
            }
            dataview.appendChild(render);
        }

        // 构建部件在无数据时显示“暂无相关数据”公用tip提醒标识变量
        JspElement var1 = new JspElement(Tag.valueOf("ui:var"), "");
        var1.attr("name", "showNoDataTip");
        var1.attr("value", "true");
        dataview.appendChild(var1);

        // 构建部件在请求数据发生异常时显示“程序发生异常”公用tip提醒标识变量
        JspElement var2 = new JspElement(Tag.valueOf("ui:var"), "");
        var2.attr("name", "showErrorTip");
        var2.attr("value", "true");
        dataview.appendChild(var2);

        return dataview;
    }

    public static Node createPortlet(JSONObject config, boolean text2iframe)
            throws Exception {
        Element in = null;
        if ("panel".equalsIgnoreCase(config.getString("panel"))) {
            String panelType = config.getString("panelType");
            if ("panel".equalsIgnoreCase(panelType)) {
                in = new JspElement(Tag.valueOf("ui:panel"), "");
                in.attr("toggle", "false");
            } else if ("none".equalsIgnoreCase(panelType)) {
                in = new JspElement(Tag.valueOf("ui:nonepanel"), "");
            }
            if (StringUtil.isNotNull(config.getString("layoutId"))) {
                in.attr("layout", config.getString("layoutId"));
            }
            if (StringUtil.isNotNull((String) config.get("height"))) {
                in.attr("height", "" + config.getString("height") + "");
            }
            if (StringUtil.isNotNull((String) config.get("heightExt"))) {
                if ("scroll".equals(config.getString("heightExt"))) {
                    in.attr("scroll", "true");
                }
                if ("auto".equals(config.getString("heightExt"))) {
                    in.attr("scroll", "false");
                }
            }
            JSONObject layoutOpt = config.getJSONObject("layoutOpt");
            if (layoutOpt != null && !layoutOpt.isNullObject()
                    && !layoutOpt.isEmpty()) {
                Iterator<?> keys = layoutOpt.keys();
                while (keys.hasNext()) {
                    String key = keys.next().toString();
                    if (layoutOpt.get(key) instanceof JSONObject) {
                        in.attr("var-" + key + "", layoutOpt.getJSONObject(key)
                                .getString(key));
                    } else {
                        in.attr("var-" + key + "", layoutOpt.get(key)
                                .toString());
                    }
                }
            }
            JSONObject portlet = config.getJSONArray("portlet")
                    .getJSONObject(0);
            in.appendChild(createContent(portlet, (text2iframe || portlet.getString("sourceId").indexOf("://") > 0)));
        } else if ("tabpanel".equalsIgnoreCase(config.getString("panel"))) {
            String panelType = config.getString("panelType");
            if ("v".equalsIgnoreCase(panelType)) {
                in = new JspElement(Tag.valueOf("ui:accordionpanel"), "");
            } else if ("h".equalsIgnoreCase(panelType)) {
                in = new JspElement(Tag.valueOf("ui:tabpanel"), "");
                if (StringUtil.isNotNull((String) config.get("height"))) {
                    in.attr("height", "" + config.getString("height") + "");
                }
                if (StringUtil.isNotNull((String) config.get("heightExt"))) {
                    if ("scroll".equals(config.getString("heightExt"))) {
                        in.attr("scroll", "true");
                    }
                    if ("auto".equals(config.getString("heightExt"))) {
                        in.attr("scroll", "false");
                    }
                }
            } else {
                throw new Exception("类型错误");
            }
            if (StringUtil.isNotNull(config.getString("layoutId"))) {
                in.attr("layout", config.getString("layoutId"));
            }
            JSONObject layoutOpt = config.getJSONObject("layoutOpt");
            if (layoutOpt != null && !layoutOpt.isNullObject()
                    && !layoutOpt.isEmpty()) {
                Iterator<?> keys = layoutOpt.keys();
                while (keys.hasNext()) {
                    String key = keys.next().toString();
                    if (layoutOpt.get(key) instanceof JSONObject) {
                        in.attr("var-" + key + "", layoutOpt.getJSONObject(key)
                                .getString(key));
                    } else {
                        in.attr("var-" + key + "", layoutOpt.get(key)
                                .toString());
                    }
                }
            }
            JSONArray portlets = config.getJSONArray("portlet");
            for (int i = 0; i < portlets.size(); i++) {
                JSONObject portlet = portlets.getJSONObject(i);
                in.appendChild(createContent(portlet, (text2iframe || portlet.getString("sourceId").indexOf("://") > 0)));
            }
        }
        return in;
    }

    public static Node createPortlet(JSONObject config) throws Exception {
        return createPortlet(config, false);
    }

    private static boolean isExistOperation(SysUiOperation opt,
                                            JSONArray operations) {
        if (operations == null || operations.isEmpty()
                || operations.size() <= 0) {
            return false;
        }
        String key = MD5Util.getMD5String(opt.getHref());
        for (int i = 0; i < operations.size(); i++) {
            if (operations.getJSONObject(i).getString("key")
                    .equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String str = FileUtil.getFileString("c:/test.txt");
        System.out.println(compile(str).getBody());
    }
}
