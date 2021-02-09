package cn.sirenia.io;

import cn.sirenia.exception.ExceptionUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.BiConsumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 只支持单文件的压缩和解压
 */
public class ZipUtil {

    public static void zip(InputStream input, OutputStream output, String entryName) {
        try (ZipOutputStream zos = new ZipOutputStream(output)){
            zos.putNextEntry(new ZipEntry(entryName));
            FileUtil.copy(input,zos);
            zos.closeEntry();
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        }
    }

    public static void unZip(InputStream input, BiConsumer<String,ZipInputStream> consumer){
	    try(ZipInputStream zis = new ZipInputStream(input)){
            ZipEntry entry = zis.getNextEntry();
            consumer.accept(entry.getName(),zis);
	        //StreamUtils.copy(zis,output);
	        zis.closeEntry();
	        //return entry.getName();
        } catch (Exception e) {
            ExceptionUtil.wrapRuntimeException(e);
        }
    }
}
