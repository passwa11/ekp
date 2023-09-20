package com.landray.kmss.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landray.kmss.sys.profile.model.SysCommonSensitiveConfig;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectXML {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ObjectXML.class);

    private  static ObjectMapper mapper = null;
    static {
        mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }
    public static void objectXmlEncoder(Object obj, String fileName)
            throws FileNotFoundException, IOException, Exception {
        String path = fileName.substring(0, fileName.lastIndexOf('/'));
        if (path.indexOf('/') != -1) {
            File pFile = new File(path);
            if (!pFile.exists()) {
                pFile.mkdirs();
            }
        }
        File fo = new File(fileName);
        FileOutputStream fos = new FileOutputStream(fo);
        XMLEncoder encoder = new XMLEncoder(fos);
        encoder.writeObject(obj);
        encoder.flush();
        encoder.close();
        fos.close();
    }
    
    private static final PersistenceDelegate bigDecimalPersistenceDelegate = new DefaultPersistenceDelegate() {
        @Override
        protected Expression instantiate(Object oldInstance,
                Encoder out) {
            BigDecimal bd = (BigDecimal) oldInstance;
            return new Expression(oldInstance, oldInstance
                    .getClass(), "new", new Object[] { bd
                    .toString() });
        }

        @Override
        protected boolean mutatesTo(Object oldInstance,
                Object newInstance) {
            return oldInstance.equals(newInstance);
        }
    };

    public static String objectXmlEncoder(Object obj)
            throws FileNotFoundException, IOException, Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(baos);
        encoder.setPersistenceDelegate(BigDecimal.class,
                bigDecimalPersistenceDelegate);
        encoder.writeObject(obj);
        encoder.flush();
        encoder.close();
        String rtnVal = new String(baos.toByteArray(), "UTF-8");
        baos.close();
        return rtnVal;
    }

    /**
     * 读取由objSource指定的XML文件中的序列化保存的对象,返回的结果经过了List封装
     * 
     * @param objSource
     *            带全部文件路径的文件全名
     * @return 由XML文件里面保存的对象构成的List列表(可能是一个或者多个的序列化保存的对象)
     * @throws FileNotFoundException
     *             指定的对象读取资源不存在
     * @throws IOException
     *             读取发生错误
     * @throws Exception
     *             其他运行时异常发生
     */
    public static List objectXmlDecoder(String objSource)
            throws FileNotFoundException, IOException, Exception {
        File fin = new File(objSource);
        
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(fin);
            return objectXmlDecoder(fileInputStream);
        }finally{
            if(fileInputStream!=null){
                fileInputStream.close();
            }
        }
    }

    public static List objectXMLDecoderByString(String ins) throws Exception {
        String safeIns = ins.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
        ByteArrayInputStream byteArrayInputStream = null;
        try{
            byteArrayInputStream = new ByteArrayInputStream(safeIns.getBytes("UTF-8"));
            return objectXmlDecoder(byteArrayInputStream);
        }finally{
            if(byteArrayInputStream!=null){
                byteArrayInputStream.close();
            }
        }
    }

    public static String objectJsonEncoder(Object obj) throws Exception{
        String string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        return string;
    }

    public static Map objectJsonDecoder(String obj) throws Exception{
        if (StringUtil.isNotNull(obj) && obj.charAt(0) == '<') {
            List datas = objectXMLDecoderByString(obj);
            if (!ArrayUtil.isEmpty(datas)) {
                return (Map) datas.get(0);
            }
        }
        Map datas = mapper.readValue(obj, HashMap.class);
        return datas;
    }

    
    public static List objectXmlDecoder(InputStream ins) throws IOException,
            Exception {
        List objList = new ArrayList();
        ByteArrayOutputStream os = null;
        try{
            int len=-1;
            os = new  ByteArrayOutputStream();//不需要close
            byte[] buffer = new byte[1024];
            while((len=ins.read(buffer, 0,1024))!=-1){
                os.write(buffer, 0, len);
            }
            String xmlStr = os.toString("UTF-8");
            if(logger.isDebugEnabled()){
                logger.debug("ready to security check: " + xmlStr);
            }
            os = null;
            deserializationSecurityCheck(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
            //List<String> blackList = getBlackList();
            ByteArrayInputStream bis = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
            xmlStr = null;// GC
            XMLDecoder decoder = new XMLDecoder(bis);
            Object obj = null;
            try {
                while ((obj = decoder.readObject()) != null) {
                    objList.add(obj);
                }
                // readObject结束是以 ArrayIndexOutOfBoundsException异常为标识
            } catch (ArrayIndexOutOfBoundsException e) {
            } finally {
                decoder.close();
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new IOException(e);
        }
        return objList;
    }
    
    /**
     * 获取xml反序列化的白名单，支持简单的通配符
     * @return
     */
    private static Set<String> getWhiteSet(){
        Set<String> set = new HashSet<>();
        //java.lang.ProcessBuilder是已知明确的禁止类
        SysCommonSensitiveConfig config = SysCommonSensitiveConfig.newInstance();
        String wl = config.getFdXmlDeserializeWhitelist();
        if(StringUtil.isNotNull(wl)){
            String[] split = wl.split("\\s*;\\s*");
            for(String s:split){
                set.add(s.trim());
            }
        }
        return set;
    }
    /**
     * xml反序列化安全校验，进行白名单测试
     * @param is
     * @throws Exception 当出现非名单列表内的类
     */
    private static void  deserializationSecurityCheck(InputStream is) throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(is);
        Set<String> classNameSet = new HashSet<>();
        // 针对 <object class="java.lang.ProcessBuilder">写法的反序列化
        NodeList elementsByTagName = document.getElementsByTagName("object");
        fillClass(elementsByTagName,classNameSet);
        // 针对 <void class="java.lang.ProcessBuilder">写法的反序列化
        NodeList voidTags = document.getElementsByTagName("void");
        fillClass(voidTags,classNameSet);
        if(!classNameSet.isEmpty()){
            Set<String> whiteSet = getWhiteSet();
            for(String className:classNameSet){
                //EKP内部的java类是可信的，如果不是，则要进行白名单过滤
                if(!whiteSet.contains(className)){
                    //如果不在白名单当中，则需要每条正则都匹配一下
                    Iterator<String> iterator = whiteSet.iterator();
                    boolean isOk = false;
                    while(iterator.hasNext()){
                        String next = iterator.next();
                        //带*号的是正则，不带的就用equals
                        if(next.indexOf('*')>-1){
                            Pattern compile = Pattern.compile(next);
                            Matcher matcher = compile.matcher(className);
                            boolean matches = matcher.matches();
                            if(matches){
                                isOk = true;
                                break;// 如果匹配，则表示OK
                            }
                        }else{
                            if(next.equals(className)){
                                isOk = true;
                                break;// 如果匹配，则表示OK
                            }
                        }
                    }
                    if(!isOk){
                        throw new IllegalArgumentException(className+"不在可信范围内，存在反序列化执行命令风险。授信请在‘运维管理’->‘安全管控配置’->‘应用安全’中配置");
                    }
                }else{
                    //如果类名是bu白名单的话，跳过
                }
            }
        }
    }

    private static void fillClass(NodeList tags,Set<String> classNameSet){
        if(tags!=null && tags.getLength()>0){
            for(int i=0;i<tags.getLength();i++){
                Node item = tags.item(i);
                NamedNodeMap attributes = item.getAttributes();
                if(attributes!=null && attributes.getLength()>0){
                    Node namedItem = attributes.getNamedItem("class");
                    if(namedItem!=null){
                        String nodeValue = namedItem.getNodeValue();
                        if(StringUtil.isNotNull(nodeValue)){
                            classNameSet.add(nodeValue.trim());
                        }
                    }
                }
            }
        }
    }
}
