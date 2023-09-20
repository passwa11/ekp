package com.landray.kmss.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.io.ReadOnlyAccessFile;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.io.IOUtils;

public class RarUtil {

	public static void unRarFile(String sourcePath) throws Exception {

		unRarFile(sourcePath, null);

	}

	public static void unRarFile(String sourcePath, String destPath)
			throws Exception {

		File file = new File(sourcePath);

		if (!file.exists()) {
            return;
        }

		if (StringUtil.isNull(destPath)) {

			unRarFile(file, file.getParentFile().getAbsolutePath());
			return;

		}

		unRarFile(file, destPath);

	}

	@SuppressWarnings("resource")
	private static void unRarFile(File sourceFile, String destPath)
			throws Exception {

		if (sourceFile == null || StringUtil.isNull(destPath)
				|| !sourceFile.exists()) {
            return;
        }

		ReadOnlyAccessFile readFile = null;

		try {
			readFile = new ReadOnlyAccessFile(sourceFile);
			Archive arc = null;

			arc = new Archive(sourceFile);

			if (arc != null) {

				if (arc.isEncrypted()) {
					return;
				}

				File dstDiretory = new File(destPath);

				if (!dstDiretory.exists()) {
					dstDiretory.mkdirs();
				}

				List<FileHeader> files = arc.getFileHeaders();

				for (FileHeader fh : files) {

					if (fh.isEncrypted()) {
                        return;
                    }

					String name;

					if (fh.isFileHeader() && fh.isUnicode()) {
                        name = fh.getFileNameW();
                    } else {
                        name = fh.getFileNameString();
                    }

					if (fh.isDirectory()) {
						makeDirectory(dstDiretory, name);
						continue;
					}

					FileOutputStream os = null;

					try {

						File out = makeFile(dstDiretory, name);
						os = new FileOutputStream(out);

						arc.extractFile(fh, os);

					} catch (RarException e) {
						throw new Exception(e);
					} finally {
						IOUtils.closeQuietly(os);
					}

				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (readFile != null) {
                readFile.close();
            }
		}

	}

	private static void makeDirectory(File destination, String fileName) {

		String[] dirs = fileName.split("\\\\");

		if (dirs == null) {
            return;
        }

		String path = "";

		for (String dir : dirs) {

			path = path + File.separator + dir;
			new File(destination, path).mkdir();

		}

	}

	private static File makeFile(File destination, String name)
			throws IOException {

		String[] dirs = name.split("\\\\");

		if (dirs == null) {
            return null;
        }

		String path = "";
		int size = dirs.length;

		if (size == 1) {

			return new File(destination, name);

		} else if (size > 1) {

			for (int i = 0; i < dirs.length - 1; i++) {

				path = path + File.separator + dirs[i];
				new File(destination, path).mkdir();

			}

			path = path + File.separator + dirs[dirs.length - 1];
			File f = new File(destination, path);

			f.createNewFile();
			return f;

		} else {

			return null;

		}
	}

}
