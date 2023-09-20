package com.landray.kmss.sys.ui.xml.model;

import com.landray.kmss.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class SysUiTheme extends SysUiXmlBase {

    public SysUiTheme(String id, String name, String path, String thumb,
                      String help) {
        this.fdId = id;
        this.fdName = name;
        this.fdHelp = help;
        this.fdThumb = thumb;
        this.fdPath = path;
    }

    protected String fdPath;

    public String getFdPath() {
        return fdPath;
    }

    public void setFdPath(String fdPath) {
        this.fdPath = fdPath;
    }

    private Map<String, String> files = new HashMap<String, String>();

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    @Override
    public String getFdHelp() {
        if (StringUtil.isNull(this.fdHelp)) {
            return "/sys/ui/help/theme/style.jsp?fdId=" + this.fdId;
        }
        return this.fdHelp;
    }

    private static SysUiTheme defaultTheme;

    /**
     * 获取一个默认主题
     *
     * @return
     */
    public static SysUiTheme getDefault() {
        if (defaultTheme == null) {
            synchronized (SysUiTheme.class) {
                defaultTheme = new SysUiTheme("default", "{sys-ui:portlet.theme.default}", "/sys/ui/extend/theme/default/", "/sys/ui/extend/theme/default/thumb.jpg", null);
                defaultTheme.getFiles().put("calendar", "style/calendar.css");
                defaultTheme.getFiles().put("widget", "style/widget.css");
                defaultTheme.getFiles().put("iconfont", "style/iconfont.css");
                defaultTheme.getFiles().put("profile", "style/profile.css");
                defaultTheme.getFiles().put("module", "style/module.css");
                defaultTheme.getFiles().put("listview", "style/listview.css");
                defaultTheme.getFiles().put("icon", "style/icon.css");
                defaultTheme.getFiles().put("treefont", "style/treefont.css");
                defaultTheme.getFiles().put("dialog", "style/dialog.css");
                defaultTheme.getFiles().put("common", "style/common.css");
                defaultTheme.getFiles().put("form", "style/form.css");
                defaultTheme.getFiles().put("zone", "style/zone.css");
                defaultTheme.getFiles().put("sns", "style/sns.css");
                defaultTheme.getFiles().put("step", "style/step.css");
                defaultTheme.getFiles().put("portal", "style/portal.css");
                defaultTheme.getFiles().put("chart", "style/chart.css");
            }
        }
        return defaultTheme;
    }
}
