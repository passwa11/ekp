package com.landray.kmss.sys.filestore.util;

import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.util.FoxitUtil;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import  static com.landray.kmss.sys.filestore.constant.ConvertConstant.*;

public class FileStoreConvertUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileStoreConvertUtil.class);
    private static ISysFileConvertConfigService sysFileConvertConfigService;

    private static ISysFileConvertConfigService getServiceImp() {
        if (sysFileConvertConfigService == null) {
            sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil
                    .getBean("sysFileConvertConfigService");
        }
        return sysFileConvertConfigService;
    }
    private static List<String> thirdConverters = new ArrayList<>();
    static {
        thirdConverters.add(THIRD_CONVERTER_WPS);
        thirdConverters.add(THIRD_CONVERTER_SHUKE);
        thirdConverters.add(THIRD_CONVERTER_WPS_CENTER);
        thirdConverters.add(THIRD_CONVERTER_DIANJU);
        thirdConverters.add(THIRD_CONVERTER_FOXIT);
    }

    /**
     * 是否开启数科
     * @Parame onlyEnable 只看是否开启服务
     * @return
     */
    public static Boolean isOpenShuKeConverter(Boolean onlyEnable) {
        try {
            Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.sys.filestore.model.SysFileConvertUrlConfig");
            String suwellConvretEnabled = "";
            if (!dataMap.isEmpty()) {
                suwellConvretEnabled = (String) dataMap.get("suwellConvretEnabled");
            }
            if(onlyEnable) {
                return STATUS.equals(suwellConvretEnabled);
            }

            String isConverterSKOFD = getServiceImp().findAppConfigValue("fdKey ='attconvert.converter.type.skofd'", "false");

            if(logger.isDebugEnabled()) {
                logger.debug("是否开启数科->deploy:{}, enable:{},converter:{}", suwellConvretEnabled, isConverterSKOFD);
            }
            return STATUS.equals(suwellConvretEnabled) && STATUS.equals(isConverterSKOFD);
        } catch (Exception e) {
            logger.error("error:", e);
        }

        return false;
    }

    /**
     * 是否开启永中
     * @Parame onlyEnable 只看是否开启服务
     * @return
     */
    public static Boolean isOpenYozoConverter() {
        try {
            String isConverterYozo = getServiceImp().findAppConfigValue("fdKey ='attconvert.converter.type.yozo'", "false");

            if(logger.isDebugEnabled()) {
                logger.debug("是否开启永中->converter:{}", isConverterYozo);
            }
            return  STATUS.equals(isConverterYozo);
        } catch (Exception e) {
            logger.error("error:", e);
        }

        return false;
    }

    /**
     * 是否开启Aspose
     * @Parame onlyEnable 只看是否开启服务
     * @return
     */
    public static Boolean isOpenAsposeConverter() {
        try {
            String isConverterAspose = getServiceImp().findAppConfigValue("fdKey ='attconvert.converter.type.aspose'", "false");

            if(logger.isDebugEnabled()) {
                logger.debug("是否开启Aspose->converter:{}", isConverterAspose);
            }
            return  STATUS.equals(isConverterAspose);
        } catch (Exception e) {
            logger.error("error:", e);
        }

        return false;
    }

    /**
     * 是否开启福昕转换器
     * @Parame onlyEnable 只看是否开启服务
     * @return
     */
    public static Boolean isOpenFoxitConverter(Boolean onlyEnable){
        try {
            String thirdFoxitEnabled = FoxitUtil.configValue("thirdFoxitEnabled");
            Boolean deploy = showConverter("/third/foxit");
            if(onlyEnable) {
                return deploy && STATUS.equals(thirdFoxitEnabled);
            }

            String isConverterFoxit = getServiceImp().findAppConfigValue("fdKey ='attconvert.converter.type.foxit'", "false");

            if(logger.isDebugEnabled()) {
                logger.debug("是否开启福昕->deploy:{}, enable:{},converter:{}",
                        deploy, thirdFoxitEnabled, isConverterFoxit);
            }
            return deploy && STATUS.equals(thirdFoxitEnabled) && STATUS.equals(isConverterFoxit);

        } catch (Exception e) {
            logger.error("error:", e);
        }

        return false;
    }

    /**
     * 是否开启点聚转换器
     * @Parame onlyEnable 只看是否开启服务
     * @return
     */
    public static Boolean isOpenDianjuConverter(Boolean onlyEnable) {
        // 转换队列中未勾选点聚
        try {
            String thirdDianjuEnabled = ConfigUtil.configValue("thirdDianjuEnabled");
            Boolean deploy = showConverter("/third/dianju");
            if(onlyEnable) {
                return deploy && STATUS.equals(thirdDianjuEnabled);
            }

            String isConverterDianju = getServiceImp().findAppConfigValue("fdKey ='attconvert.converter.type.dianju'", "false");

            if(logger.isDebugEnabled()) {
                logger.debug("是否开启点聚->deploy:{}, enable:{},converter:{}",
                        deploy, thirdDianjuEnabled, isConverterDianju);
            }
            return deploy && STATUS.equals(thirdDianjuEnabled) && STATUS.equals(isConverterDianju);
        } catch (Exception e) {
            logger.error("error:", e);
        }

        return false;
    }

    /**
     * 是否开启WPS中台转换器
     * @Parame onlyEnable 只看是否开启服务
     * @return
     */
    public static Boolean isOpenWpsCenterConvert(Boolean onlyEnable) {
        try {
            String thirdWpsCenterEnabled = WpsUtil.configInfo("thirdWpsCenterEnabled");
            Boolean deploy = showConverter("/third/wps");
            if(onlyEnable) {
                return deploy && STATUS.equals(thirdWpsCenterEnabled);
            }
            String isConverterWPSCenter = getServiceImp().findAppConfigValue("fdKey ='attconvert.converter.type.wpsCenter'", "false");

            if(logger.isDebugEnabled()) {
                logger.debug("是否开启WPS中台->deploy:{}, enable:{},converter:{}",
                        deploy, thirdWpsCenterEnabled, isConverterWPSCenter);
            }
            return deploy && STATUS.equals(thirdWpsCenterEnabled) && STATUS.equals(isConverterWPSCenter);
        } catch (Exception e) {
            logger.error("error:", e);
        }

        return false;
    }

    /**
     * 是否开启WPS在线预览转换服务器
     * @Parame onlyEnable 只看是否开启服务
     * @return
     */
    public static Boolean isOpenWpsCloudConvert(Boolean onlyEnable) {
        try {
            String thirdWpsPreviewEnabled = WpsUtil.configInfo("thirdWpsPreviewEnabled");
            Boolean deploy = showConverter("/third/wps");
            if(onlyEnable) {
                return deploy && STATUS.equals(thirdWpsPreviewEnabled);
            }

            String isConverterWPS = getServiceImp().findAppConfigValue("fdKey ='attconvert.converter.type.wps'", "false");
            if(logger.isDebugEnabled()) {
                logger.debug("是否开启WPS在线预览->deploy:{}, enable:{},converter:{}",
                        deploy, thirdWpsPreviewEnabled, isConverterWPS);
            }
            return deploy && STATUS.equals(thirdWpsPreviewEnabled) && STATUS.equals(isConverterWPS);
        } catch (Exception e) {
            logger.error("error:", e);
        }

        return false;
    }

    /**
     * 是否允许执行
     * @param convertType
     * @return
     */
    public static Boolean whetherExecute(String convertType, Boolean onlyEnable) {
      switch (convertType) {
          case THIRD_CONVERTER_WPS :
              return "linux".equals(WpsUtil.configInfo("thirdWpsOS")) && isOpenWpsCloudConvert(onlyEnable);
          case THIRD_CONVERTER_WPS_CENTER :
              return isOpenWpsCenterConvert(onlyEnable);
          case THIRD_CONVERTER_DIANJU :
              return isOpenDianjuConverter(onlyEnable);
          case THIRD_CONVERTER_FOXIT :
              return isOpenFoxitConverter(onlyEnable);
          case THIRD_CONVERTER_SHUKE :
              return isOpenShuKeConverter(onlyEnable);
          default: return false;
      }
    }

    /**
     * 是否包含指定的厂商
     * @param convertType
     * @return
     */
    public static Boolean containsThirdConverter(String convertType) {
       if(thirdConverters.contains(convertType)) {
           if(logger.isDebugEnabled()) {
               logger.debug("包含了转换的厂商:{}", convertType);
           }

           return true;
       }

       return false;
    }

    /**
     * 是否安装模块
     * @param module
     * @return
     */
    public static Boolean showConverter(String module) {
        return  (new File(PluginConfigLocationsUtil.getKmssConfigPath()
                + module).exists()) ? true : false;
    }
}


