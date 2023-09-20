package com.landray.kmss.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import fr.opensagres.poi.xwpf.converter.core.BasicURIResolver;
import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.scanners.ScriptScanner;
import org.htmlparser.tags.Div;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.visitors.NodeVisitor;
import org.w3c.dom.Document;

import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;

/**
 * word转为html
 * 
 * @author chao
 *
 */
public class WordToHtml {

	private static final Log logger = LogFactory.getLog(WordToHtml.class);

	private final String tempImgPath = IDGenerator.generateID();

	private HttpServletRequest request;

	private ISysAttMainCoreInnerService sysAttMainService;

	protected ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
                    .getBean("sysAttMainService");
        }
		return sysAttMainService;
	}

	public WordToHtml(HttpServletRequest request) {
		this.request = request;
	}

	public String convertWordToHtml(InputStream is, String fileName,
			String charset) throws Exception {
		byte[] bs = IOUtils.toByteArray(is);
		InputStream input = new ByteArrayInputStream(bs);
		String content = null;
		try {
			// word文件可能人为的更改扩展名，所以这里做一下兼容
			if (fileName.endsWith("docx")) {
				try {
					content = PoiWord07ToHtml(input, charset);
				} catch (Exception e) {
					if (logger.isWarnEnabled()) {
						logger.warn("word07方式转换失败，尝试使用word03方式转换！fileName："
								+ fileName);
					}
					e.printStackTrace();
					input.reset();
					content = PoiWord03ToHtml(input, charset);
				}
				return content;
			} else if (fileName.endsWith("doc")) {
				try {
					content = PoiWord03ToHtml(input, charset);
				} catch (Exception e) {
					if (logger.isWarnEnabled()) {
						logger.warn("word03方式转换失败，尝试使用word07方式转换！fileName："
								+ fileName);
					}
					e.printStackTrace();
					input.reset();
					content = PoiWord07ToHtml(input, charset);
				}
				return content;
			}
		} finally {
			streamClose(input);
			streamClose(is);
		}
		if (logger.isWarnEnabled()) {
			logger.warn("扩展名不是word文件，忽略转换！fileName：" + fileName);
		}
		return null;
	}

	public String convertWordToHtml(InputStream is, String fileName)
			throws Exception {
		return convertWordToHtml(is, fileName, "UTF-8");
	}

	/**
	 * word07版本(.docx)转html poi:word07在线预览
	 */
	private String PoiWord07ToHtml(InputStream is, String charset)
			throws Exception {
		if (is == null) {
            return null;
        }
		String content = null;
		// 读取文档内容
		XWPFDocument document = new XWPFDocument(is);
		document.createNumbering();
		File imageFolderFile = new File(getTempFolder());
		if (!imageFolderFile.exists()) {
			imageFolderFile.mkdirs();
		}
		// 加载html页面时图片路径
		XHTMLOptions options = XHTMLOptions.create()
				.URIResolver(new BasicURIResolver(tempImgPath));
		// 图片保存文件夹路径
		options.setExtractor(new FileImageExtractor(imageFolderFile));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out, charset);// 自定义编码
		try {
			XHTMLConverter instance = (XHTMLConverter) XHTMLConverter
					.getInstance();
			instance.convert(document, writer, options);
			content = out.toString(charset);
		} finally {
			streamClose(out);
		}
		content = makeAttImage(imageFolderFile, content);
		FileUtil.deleteDir(imageFolderFile);
		// 去除样式（宽度，上下左右边距）
		content = unableStyle(content);
		return content;
	}

	private String unableStyle(String content) throws Exception {
		ScriptScanner.STRICT = false; // 解决htmlparser解析时将文本中的</>当做闭合标签的问题
		Lexer mLexer = new Lexer(new Page(content));
		Parser parser = new Parser(mLexer, new DefaultParserFeedback(
				DefaultParserFeedback.QUIET));
		Node html = parser.parse(new TagNameFilter("html")).elementAt(0);
		if (null != html) {
			html.accept(new NodeVisitor() {
				@Override
				public void visitTag(Tag tag) {
					if ("div".equalsIgnoreCase(tag.getTagName())) {
						Div div = (Div) tag;
						// word07 生成的html字符串会设置样式导致显示不美观，这里去掉设置
						div.setAttribute("style", "");
					}
				}
			});
			content = html.toHtml();
		}
		ScriptScanner.STRICT = true; // 还原
		return content;
	}

	/**
	 * 根据转换html时生成的图片文件夹，生成相应的附件，并替换html字符串中相应的图片路径
	 * 
	 * @param imageFolderFile
	 *            图片文件夹（临时）
	 * @param content
	 *            html字符串
	 * @return
	 * @throws Exception
	 */
	private String makeAttImage(File imageFile, String content)
			throws Exception {
		if (imageFile.isDirectory()) {
			File[] images = imageFile.listFiles();
			for (File file : images) {
				content = makeAttImage(file, content);
			}
		} else {
			FileInputStream input = new FileInputStream(imageFile);
			try {
				// 生成附件
				String fdId = saveToRtfImage(new ByteArrayInputStream(
						IOUtils.toByteArray(input)), imageFile.getName());
				String repalceStr = this.tempImgPath + "[^\"]+?"
						+ imageFile.getName();
				// 替换URL
				content = content.replaceFirst(repalceStr, getImgUrl(fdId));
			} finally {
				streamClose(input);
			}
		}
		return content;
	}

	// 保存word图片的临时文件夹
	private String getTempFolder() {
		String folder = System.getProperty("java.io.tmpdir");
		if (!folder.endsWith("/") && !folder.endsWith("\\")) {
			folder += "/";
		}
		folder += IDGenerator.generateID();
		File f = new File(folder);
		if(!f.exists()){
			f.mkdirs();
		}
		return folder;
	}

	private String getImgUrl(String fdId) {
		return this.request.getContextPath()
				+ "/resource/fckeditor/editor/filemanager/download?fdId="
				+ fdId;
	}

	/**
	 * word03版本(.doc)转html poi:word03在线预览
	 * 
	 * @throws Exception
	 */
	private String PoiWord03ToHtml(InputStream is, String charset)
			throws Exception {
		if (is == null) {
            return null;
        }
		String content = null;
		HWPFDocument wordDocument = new HWPFDocument(is);
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			@Override
			public String savePicture(byte[] content, PictureType pictureType,
									  String suggestedName, float widthInches,
									  float heightInches) { // 图片在html页面加载路径
				String fdId = "";
				try {
					fdId = saveToRtfImage(new ByteArrayInputStream(content),
							suggestedName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return getImgUrl(fdId);
			}
		});
		wordToHtmlConverter.processDocument(wordDocument);
		// 创建html页面并将文档中内容写入页面
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			DOMSource domSource = new DOMSource(htmlDocument);
			StreamResult streamResult = new StreamResult(outStream);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, charset);
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
			content = new String(outStream.toString(charset));
		} finally {
			streamClose(outStream);
		}
		return content;
	}

	/**
	 * 根据图片流生成RTF附件对象，返回附件ID
	 * 
	 * @param input
	 *            ByteArrayInputStream对象，因为此类支持reset方法
	 * @param fileName
	 * @param closed
	 * @return
	 * @throws Exception
	 */
	private String saveToRtfImage(ByteArrayInputStream input, String fileName)
			throws Exception {
		String fdId = null;
		try {
			SysAttRtfData sysAttRtfData = new SysAttRtfData();
			sysAttRtfData.setFdFileName(fileName);
			sysAttRtfData
					.setFdModelId(request.getParameter("fdModelId"));
			sysAttRtfData
					.setFdModelName(
							request.getParameter("fdModelName"));
			sysAttRtfData
					.setFdContentType(
							"image/" + FileTypeUtil.getFileType(input));
			sysAttRtfData.setDocCreateTime(new Date());
			Double size = new Double(input.available());
			sysAttRtfData.setFdSize(size);
			input.reset();
			// 生成附件
			fdId = getSysAttMainService().addRtfData(sysAttRtfData,
					input);
		} finally {
			streamClose(input);
		}
		return fdId;
	}

	private void streamClose(InputStream input) {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	private void streamClose(OutputStream output) {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
