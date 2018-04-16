package com.smt.smallfat.utils;

import com.csyy.common.ExceptionInfo;
import com.csyy.common.MD5;
import com.csyy.common.UUIDProvider;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.FilePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Created by zhangbin on 16/7/8.
 */
public class FileUtils {

     static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 读取项目中Resources文件夹下面的文件
     *
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {
        try {
            StringBuffer sb = new StringBuffer();
            FileReader freader = new FileReader(new File(filePath));
            BufferedReader buffer = new BufferedReader(freader);
            String str_line = buffer.readLine();
            while (str_line != null) {
                sb.append(str_line);
                sb.append("\n");
                str_line = buffer.readLine();
            }
            buffer.close();
            freader.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取唯一的文件名
     * @return
     */
    public static String getFileName(String name){
        if(name.contains("."))
            return MD5.md5(UUIDProvider.uuid())+name.substring(name.lastIndexOf("."),name.length());
        return MD5.md5(UUIDProvider.uuid());
    }

    /**
     * 根据文件名和文件类型生成存储目录
     * @param name
     * @param contentType
     * @return
     */
    public static String getFilePath(String name,String contentType){
        return getFilePath(name,contentType,Constant.FILE_PATH);
    }

    /**
     * 根据文件名和文件类型生成存储目录
     * @param name
     * @param contentType
     * @param baseUrl
     * @return
     */
    public static String getFilePath(String name,String contentType,String baseUrl){

        String path1 = getFilePath(name);

        String path2 = getFilePath(UUIDProvider.uuid());

        String result = baseUrl + File.separator
                + path1 + path2 + File.separator + name;

        if(!result.contains(".")){
            result = result + ".jpeg";
        }

        return result.replace("//","/");
    }

    public static String getRandomPath(String name,String baseUrl){
        String path1 = getFilePath(name);
        String path2 = getFilePath(UUIDProvider.uuid());
        String result = baseUrl + File.separator
                + path1 + path2 + File.separator;
        return result.replace("//","/");
    }

    public static String getFilePath(String str){

        int code = str.hashCode();

        return FilePath.getCode((code % 26 > 0 ?
                code % 26 : 0 - code % 26) + 1).getPath();
    }


    /**
     * 图片转换base64
     * @param imgFilePath
     * @return
     */
    public static String GetImageStr(String imgFilePath) {
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * base64 转换图片
     * @param imgStr
     * @return
     */
    public static byte[] GenerateImage(String imgStr) {
        if (imgStr == null) // 图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
//            ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(bytes);
            return bytes;
        } catch (Exception e) {
            ExceptionInfo.exceptionInfo(e,logger);
            return null;
        }
    }

//    public static String getFileType(String contentType){
//        return
//    }
//
//    private enum ImageContentType{
//        BMP("bmp","image/bmp"),
//        IEF("ief","image/ief"),
//        JPG("jpg","image/jpg"),
//        PNG("png","image/png"),
//        TIF("tif","image/tif"),
//        DJV("djv","image/vnd.djvu"),
//        RAS("ras","image/x-cmu-raster"),
//        PBM("pbm","image/x-portable-bitmap"),
//        PPM("ppm","image/x-portable-pixmap"),
//        XBM("xbm","image/x-xbitmap"),
//        XWD("xwd","image/x-xwindowdump"),
//        GIF("gif","image/gif"),
//        JPEG("jpeg","image/jpeg"),
//        JPE("jpe","image/jpe"),
//        TIFF("tiff","image/tiff"),
//        DJVU("djvu","image/vnd.djvu"),
//        WBMP("wbmp","image/vnd.wap.wbmp"),
//        PNM("pnm","image/x-portable-anymap"),
//        PGM("pgm","image/x-portable-graymap"),
//        RGB("rgb","image/x-rgb"),
//        XPM("xpm","image/x-xpixmap");
//        private String type;
//        private String contentType;
//         ImageContentType(String type,String contentType){
//            this.type = type;
//            this.contentType = contentType;
//        }
//        public String getType(String contentType){
//            for (ImageContentType c : ImageContentType.values()) {
//                if (c.getContentType().equals(contentType)) {
//                    return c.getType();
//                }
//            }
//            return null;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getContentType() {
//            return contentType;
//        }
//
//        public void setContentType(String contentType) {
//            this.contentType = contentType;
//        }
//    }
}
