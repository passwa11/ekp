package com.landray.kmss.sys.attachment.integrate.wps.util;

import com.landray.kmss.util.ArrayUtil;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class BookMarkUtil {
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(BookMarkUtil.class);
	/**
	 * 获取书签
	 * 
	 * @param in
	 *            文件流
	 * @param ext
	 *            文件扩展名
	 * @return
	 * @throws Exception
	 */
	public static List<String> getBookMarkList(InputStream in, String ext)
			throws Exception {
		if (in == null) {
            return null;
        }
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len;
		while ((len = in.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();

		if (in != null) {
			in.close();
		}
		if (baos != null) {
			baos.close();
		}

		byte[] resultBuffer = baos.toByteArray();

		List<String> bookList = new ArrayList<>();
		if ("doc".equals(ext)) {
			try {
				bookList = getDocBookMark(
						new ByteArrayInputStream(resultBuffer));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.warn("获取doc书签出错", e);
				bookList = getDocxBookMark(
						new ByteArrayInputStream(resultBuffer));
			}
		} else {
			try {
				bookList = getDocxBookMark(
						new ByteArrayInputStream(resultBuffer));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.warn("获取docx书签出错", e);
				bookList = getDocBookMark(
						new ByteArrayInputStream(resultBuffer));
			}
		}

		if (logger.isDebugEnabled()) {
			if (!ArrayUtil.isEmpty(bookList)) {
				logger.debug("poi获取书签原结果：" + bookList.toString());
			} else {
				logger.debug("poi获取书签原结果：无");
			}
		}
		List<String> resultList = new ArrayList<String>();
		if (!ArrayUtil.isEmpty(bookList)) {
			// 含有_GoBack、下划线开头的书签，去除
			for (String book : bookList) {
				if (!book.startsWith("_")) {
					resultList.add(book);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			if (!ArrayUtil.isEmpty(resultList)) {
				logger.debug("poi获取书签结果：" + resultList.toString());
			} else {
				logger.debug("poi获取书签结果：无");
			}
		}

		return resultList;
	}

	private static List<String> getDocBookMark(InputStream in)
			throws Exception {
		List<String> bookList;
		try {
			bookList = new ArrayList<>();
			HWPFDocument doc = new HWPFDocument(in);
			Bookmarks bookmarks = doc.getBookmarks();

			for (int i = 0, j = bookmarks.getBookmarksCount(); i < j; i++) {
				Bookmark bookmark = bookmarks.getBookmark(i);
				bookList.add(bookmark.getName());
			}
		} finally {
			IOUtils.closeQuietly(in);
		}

		return bookList;
	}

	private static List<String> getDocxBookMark(InputStream in)
			throws Exception {
		List<String> bookList;
		try {
			bookList = new ArrayList<>();
			XWPFDocument document = new XWPFDocument(in);
			BookMarks bookMarks = new BookMarks(document);

			Collection<BookMark> list = bookMarks.getBookmarkList();
			for (BookMark bookMark : list) {
				bookList.add(bookMark.getBookmarkName());
			}
		} finally {
			IOUtils.closeQuietly(in);
		}
		return bookList;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// FileInputStream in = new FileInputStream("D:\\1.doc");
		// List<String> list = BookMarkUtil.getBookMarkList(in, "doc");
		// for (String string : list) {
		// System.out.println(string);
		// }
	}

}
