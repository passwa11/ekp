package com.landray.kmss.sys.ui.service.spring;

import com.landray.kmss.sys.ui.service.AbstractSysUiCompressExecutor;
import com.landray.kmss.sys.ui.service.ISysUiCompressExecutor;
import com.landray.kmss.sys.ui.util.PcJsOptimizeUtil;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.*;

/**
 * sys/ui模块jsp使用的压缩合并js的执行类
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-10-18
 */public class SysUiCompressExecutor extends AbstractSysUiCompressExecutor implements ISysUiCompressExecutor {


    private static Map<String, Object> filesKey = new HashMap<>();

    private static String[] jsHead_com_combined;

    private static String[] jshead_combined;

    private static String[] default_edit_combined;

    private static String[] default_view_combined;

    private static String[] default_list_all_combined;

    private static String[] default_list_iframe_combined;

    @Override
    public void execute() {
        try{
            //WebContent/sys/ui/jsp/jshead.jsp
            PcJsOptimizeUtil.genMergeCompressResource(getRelatePath() + "jsHead_com_combined.js", Arrays.asList(jsHead_com_combined), true, false);
        }catch (Exception e) {
            logFileCompressError("jsHead_com_combined.js", e);
        }
        try{
            //WebContent/sys/ui/jsp/jshead.jsp
            PcJsOptimizeUtil.genMergeCompressResource(getRelatePath() + "jshead_combined.js", Arrays.asList(jshead_combined), true, false);
        }catch (Exception e) {
            logFileCompressError("jshead_combined.js", e);
        }
        try{
            //WebContent/sys/ui/extend/template/module/edit.jsp
            PcJsOptimizeUtil.genMergeCompressResource(getRelatePath() + "default_edit_combined.js", Arrays.asList(default_edit_combined), true, false);
        }catch (Exception e) {
            logFileCompressError("default_edit_combined.js", e);
        }
        try{
            //WebContent/sys/ui/extend/template/module/view.jsp
            PcJsOptimizeUtil.genMergeCompressResource(getRelatePath() + "default_view_combined.js", Arrays.asList(default_view_combined), true, false);
        }catch (Exception e) {
            logFileCompressError("default_view_combined.js", e);
        }
        try{
            //WebContent/sys/ui/extend/template/module/list/all.jsp
            PcJsOptimizeUtil.genMergeCompressResource(getRelatePath() + "default_list_all_combined.js", Arrays.asList(default_list_all_combined), true, false);
        }catch (Exception e) {
            logFileCompressError("default_list_all_combined.js", e);
        }
        try{
            //WebContent/sys/ui/extend/template/module/list/iframe.jsp
            PcJsOptimizeUtil.genMergeCompressResource(getRelatePath() + "default_list_iframe_combined.js", Arrays.asList(default_list_iframe_combined), true, false);
        }catch (Exception e) {
            logFileCompressError("default_list_iframe_combined.js", e);
        }
    }

    @Override
    public String getRelatePath() {
        return "/sys/ui/";
    }

    @Override
    public Map<String, Object> getFileListMapping() {
        return filesKey;
    }

    @Override
    public boolean disabled() {
        return false;
    }

    @Override
    public boolean enabled() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        jsHead_com_combined = new String[]{ "resource/js/domain.js", "sys/ui/js/LUI.js", "resource/js/common.js",
                "resource/js/sea.js"};

        jshead_combined = new String[]{ "sys/ui/js/parser.js", "sys/ui/js/dialog.js", "sys/ui/js/util/str.js",
                "sys/ui/js/topic.js", "sys/ui/js/Evented.js", "sys/ui/js/view/layout.js",
                "sys/ui/js/base.js", "sys/ui/js/util/env.js", "sys/ui/js/overlay.js",
                "sys/ui/js/dialog/actor.js", "sys/ui/js/dialog/trigger.js", "sys/ui/js/dialog/content.js",
                "sys/ui/js/dragdrop.js", "sys/ui/js/util/crypto.js", "sys/ui/js/Class.js",
                "sys/ui/js/view/Template.js", "sys/ui/js/util/loader.js", "sys/ui/js/toolbar.js",
                "sys/ui/js/element.js", "sys/ui/js/suspend.js", "sys/ui/js/spa/directions/ifDirection.js",
                "sys/ui/js/spa/directions/authDirection.js", "sys/ui/js/spa/directions/mapDirection.js", "sys/ui/js/spa/const.js",
                "sys/ui/js/spa/Spa.js", "sys/ui/js/spa/router.js", "sys/ui/js/spa/values.js",
                "sys/ui/js/spa/direction.js", "sys/ui/js/spa/router/hash.js", "sys/ui/js/framework/router/router-utils.js",
                "sys/ui/js/framework/router/router.js", "sys/ui/js/framework/router/const.js", "sys/ui/js/framework/router/utils/create-route-map.js",
                //增加一些新公共js的提取jsHead.jsp
                "sys/ui/js/imageP/preview.js", "sys/ui/js/imageP/ImageP.js", "sys/ui/js/imageP/Play.js", "sys/ui/js/imageP/Panel.js",
                "sys/ui/js/imageP/Thumb.js", "sys/ui/js/imageP/Value.js", "sys/ui/js/imageP/Path.js", "sys/ui/js/imageP/Toolbar.js",
                "sys/ui/js/menu.js", "sys/ui/js/view/render.js", "sys/ui/js/switch.js", "sys/ui/js/listview/columntable.js",
                "sys/ui/js/listview/paging.js", "sys/ui/js/popup.js", "sys/ui/js/spa/adapters/menuSourceAdapter.js", "sys/ui/js/spa/adapters/menuItemAdapter.js",
                "sys/ui/js/spa/adapters/menuAdapter.js", "sys/authentication/identity/js/auth.js", "sys/ui/js/qrcode/qrcode.js"
        };




        default_edit_combined = new String[]{ "sys/ui/js/menu.js", "sys/ui/js/top.js", "sys/ui/js/framework/adapters/topAdapter.js",
                "sys/ui/js/data/source.js", "sys/ui/js/popup.js", "sys/ui/js/spa/adapters/menuSourceAdapter.js",
                "sys/ui/js/spa/adapters/menuItemAdapter.js", "sys/ui/js/spa/adapters/menuAdapter.js", "sys/ui/js/qrcode.js",
                "sys/ui/js/listview/listview.js", "sys/ui/js/spa/adapters/listviewAdapter.js", "sys/ui/js/panel.js"};

        default_view_combined = new String[]{ "sys/ui/js/top.js", "sys/ui/js/framework/adapters/topAdapter.js", "sys/ui/js/qrcode.js",
                "sys/ui/js/listview/listview.js", "sys/ui/js/data/source.js", "sys/ui/js/spa/adapters/listviewAdapter.js",
                "sys/ui/js/panel.js"};

        default_list_all_combined = new String[]{ "sys/ui/extend/template/module/export.js", "sys/ui/js/refreshTodo.js", "sys/ui/js/dialog_common.js",
                "sys/ui/js/popup.js", "sys/ui/js/data/source.js", "sys/ui/js/view/render.js",
                "sys/ui/js/menu.js", "sys/ui/js/panel.js", "sys/ui/js/listview/paging.js",
                "sys/ui/js/fixed.js", "sys/ui/js/listview/listview.js", "sys/ui/js/listview/columntable.js",
                "sys/ui/js/top.js", "sys/ui/js/spa/adapters/menuSourceAdapter.js", "sys/ui/js/spa/adapters/menuItemAdapter.js",
                "sys/ui/js/spa/adapters/menuAdapter.js", "sys/ui/js/spa/adapters/listviewAdapter.js", "sys/ui/js/framework/adapters/topAdapter.js"};

        default_list_iframe_combined = new String[]{ "sys/ui/extend/template/module/export.js", "sys/ui/js/dialog_common.js", "sys/ui/js/data/source.js",
                "sys/ui/js/view/render.js", "sys/ui/js/panel.js", "sys/ui/js/menu.js",
                "sys/ui/js/listview/paging.js", "sys/ui/js/fixed.js", "sys/ui/js/listview/listview.js",
                "sys/ui/js/listview/columntable.js", "sys/ui/js/top.js", "sys/ui/js/title.js",
                "sys/ui/js/popup.js", "sys/ui/js/spa/adapters/menuSourceAdapter.js", "sys/ui/js/spa/adapters/menuItemAdapter.js",
                "sys/ui/js/spa/adapters/menuAdapter.js", "sys/ui/js/spa/adapters/listviewAdapter.js", "sys/ui/js/framework/adapters/topAdapter.js"};

        filesKey.put("jsHead_com_combined", jsHead_com_combined);
        filesKey.put("jshead_combined", jshead_combined);
        filesKey.put("default_edit_combined", default_edit_combined);
        filesKey.put("default_view_combined", default_view_combined);
        filesKey.put("default_list_all_combined", default_list_all_combined);
        filesKey.put("default_list_iframe_combined", default_list_iframe_combined);
    }
}
