package com.landray.kmss.km.review.service.spring;

import com.landray.kmss.sys.ui.service.AbstractSysUiCompressExecutor;
import com.landray.kmss.sys.ui.service.ISysUiCompressExecutor;
import com.landray.kmss.sys.ui.util.PcJsOptimizeUtil;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.*;

public class KmReviewCompressExecutor  extends AbstractSysUiCompressExecutor implements ISysUiCompressExecutor {

    private static Map<String, Object> filesKey = new HashMap<>();

    private static Map<String, List<String>> km_review_view_comJs_combined = new LinkedHashMap<>();



    @Override
    public void execute() {
        try{
            //WebContent/km/review/km_review_ui/kmReviewMain_viewHead.jsp
            PcJsOptimizeUtil.genCommonJsMergeCompressResource(getRelatePath() + "km_review_view_comJs_combined.js", km_review_view_comJs_combined, true, false);
        }catch (Exception e) {
            logFileCompressError("km_review_view_comJs_combined.js", e);
        }
    }

    @Override
    public String getRelatePath() {
        return "/km/review/";
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
        km_review_view_comJs_combined.put("resource/js/jquery.js", Arrays.asList(new String[]{"js/jquery.js"}));
        km_review_view_comJs_combined.put("resource/js/address.js", Arrays.asList(new String[]{"js/address.js"}));
        km_review_view_comJs_combined.put("resource/js/watermark.js", Arrays.asList(new String[]{"js/watermark.js"}));
        km_review_view_comJs_combined.put("resource/js/calendar.js", Arrays.asList(new String[]{"js/calendar.js"}));
        km_review_view_comJs_combined.put("resource/js/data.js", Arrays.asList(new String[]{"js/data.js"}));
        km_review_view_comJs_combined.put("resource/js/dialog.js", Arrays.asList(new String[]{"js/dialog.js"}));
        km_review_view_comJs_combined.put("resource/js/doclist.js", Arrays.asList(new String[]{"js/doclist.js"}));
        km_review_view_comJs_combined.put("resource/js/doclistdnd.js", Arrays.asList(new String[]{"js/doclistdnd.js"}));
        km_review_view_comJs_combined.put("resource/js/docutil.js", Arrays.asList(new String[]{"js/docutil.js"}));
        km_review_view_comJs_combined.put("resource/js/formula.js", Arrays.asList(new String[]{"js/formula.js"}));
        km_review_view_comJs_combined.put("resource/ckeditor/ckresize.js", Arrays.asList(new String[]{"ckeditor/ckresize.js"}));
        km_review_view_comJs_combined.put("sys/attachment/js/base64.js", Arrays.asList(new String[]{getContextPath() + "/sys/attachment/js/base64.js"}));
        km_review_view_comJs_combined.put("sys/attachment/js/swf_attachment.js", Arrays.asList(new String[]{getContextPath() + "/sys/attachment/js/swf_attachment.js"}));
        km_review_view_comJs_combined.put("resource/js/form.js", Arrays.asList(new String[]{getContextPath() + "/resource/js/form.js"}));
        km_review_view_comJs_combined.put("resource/style/common/fileIcon/fileIcon.js", Arrays.asList(new String[]{getContextPath() + "/resource/style/common/fileIcon/fileIcon.js"}));
        km_review_view_comJs_combined.put("sys/relation/import/resource/rela.js", Arrays.asList(new String[]{getContextPath() + "/sys/relation/import/resource/rela.js"}));
        km_review_view_comJs_combined.put("resource/js/json2.js", Arrays.asList(new String[]{"js/json2.js"}));
        km_review_view_comJs_combined.put("resource/js/optbar.js", Arrays.asList(new String[]{"js/optbar.js"}));
        km_review_view_comJs_combined.put("resource/js/plugin.js", Arrays.asList(new String[]{"js/plugin.js"}));
        km_review_view_comJs_combined.put("resource/js/popwin.js", Arrays.asList(new String[]{"js/popwin.js"}));
        km_review_view_comJs_combined.put("resource/js/rightmenu.js", Arrays.asList(new String[]{"js/rightmenu.js"}));
        km_review_view_comJs_combined.put("resource/js/treeview.js", Arrays.asList(new String[]{"js/treeview.js"}));
        km_review_view_comJs_combined.put("resource/js/validation.js", Arrays.asList(new String[]{"js/validation.js"}));
        km_review_view_comJs_combined.put("resource/js/xml.js", Arrays.asList(new String[]{"js/xml.js"}));
        km_review_view_comJs_combined.put("resource/style/default/tree/tree_page.js", Arrays.asList(new String[]{"style/default/tree/tree_page.js"}));
        km_review_view_comJs_combined.put("resource/js/xform.js", Arrays.asList(new String[]{"js/xform.js"}));
        km_review_view_comJs_combined.put("resource/js/jquery-ui/jquery.ui.js", Arrays.asList(new String[]{"js/jquery-ui/jquery.ui.js"}));

        filesKey.put("km_review_view_comJs_combined", km_review_view_comJs_combined);
    }
}
