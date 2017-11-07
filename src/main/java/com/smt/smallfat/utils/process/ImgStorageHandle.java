package com.smt.smallfat.utils.process;

import com.csyy.common.ExceptionInfo;
import com.smt.smallfat.exception.ImgHandleResultException;
import com.smt.smallfat.po.FileStorage;
import com.smt.smallfat.utils.FileUtils;
import com.smt.smallfat.vo.upload.Base64Info;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by zhangbin on 2016/11/8.
 */

public class ImgStorageHandle implements ImgHandle {

    Logger logger = LoggerFactory.getLogger(ImgStorageHandle.class);

    private String params;

    private MultipartFile file;

    private InputStream is;

    private Base64Info info;

    private String baseUrl;

    public ImgStorageHandle(MultipartFile file, String params) {
        this.file = file;
        this.params = params;
        try {
            this.is = file.getInputStream();
        } catch (IOException e) {
            ExceptionInfo.exceptionInfo(e, logger);
        }
    }

    public ImgStorageHandle(MultipartFile file, String params, String baseUrl) {
        this.file = file;
        this.params = params;
        this.baseUrl = baseUrl;
        try {
            this.is = file.getInputStream();
        } catch (IOException e) {
            ExceptionInfo.exceptionInfo(e, logger);
        }
    }

    public ImgStorageHandle(InputStream is, String params) {
        this.is = is;
        this.params = params;
    }

    public ImgStorageHandle(InputStream is, Base64Info info, String params) {
        this.is = is;
        this.info = info;
        this.params = params;
    }


    /**
     * 文件处理
     */
    @Override
    public void handle() {
        ImgHandleResultException img = new ImgHandleResultException();

        if (file == null) {
            img.setInfo(storageBase64());
            img.setStream(FileUtils.GenerateImage(info.getBase64Str()));
        } else {
            img.setInfo(storageFile());
            try {
                byte[] bytes = IOUtils.toByteArray(new FileInputStream(img.getInfo().getFileUrl()));
                img.setStream(bytes);
            } catch (IOException e) {
                ExceptionInfo.exceptionInfo(e, logger);
            }

        }
        img.setHasStoraged(true);
        throw img;
    }

    /**
     * 存储文件
     */
    private FileStorage storageFile() {
        //TODO 先操作数据库,出现异常可以回滚

        //取得当前上传文件的文件名称
        String myFileName = file.getOriginalFilename();

        //构建fileStorage实体
        FileStorage info = FileStorage.getInstance(FileStorage.class);
        info.setFileName(myFileName);
        info.setFileType(file.getContentType());
        info.setFileSize(String.valueOf(file.getSize()));
        String fileName = FileUtils.getFileName(myFileName);
        info.setFileUrl(FileUtils.getFilePath(fileName, info.getFileType(), this.baseUrl));
        if (params == null) params = "";
        info.setParams(params);

        //如果名称不为“”,说明该文件存在，否则说明该文件不存在
        if (myFileName.trim() != "") {
            String path = info.getFileUrl();
            logger.info("存储的路径:" + path);
            File localFile = new File(path);

            //如果目录不存在,创建目录
            if (!localFile.getParentFile().exists() && !localFile.isDirectory()) {
                logger.info("创建目录:{}=========", localFile.getParentFile().getPath());
                localFile.getParentFile().mkdirs();
            }

            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                logger.error("文件:" + myFileName + "操作失败!" + e);
                return null;
            }
        }

        return info;
    }

    private FileStorage storageBase64() {
        FileStorage fs = FileStorage.getInstance(FileStorage.class);
        fs.setFileName(info.getFileName());
        fs.setFileType(info.getFileType());
        fs.setFileSize(String.valueOf(info.getBase64Str().length()));
        String fileName = FileUtils.getFileName(info.getFileName());
        fs.setFileUrl(FileUtils.getFilePath(fileName, fs.getFileType(), this.baseUrl));
        if (params == null) params = "";
        fs.setParams(params);

        //如果名称不为“”,说明该文件存在，否则说明该文件不存在
        if (info.getFileName().trim() != "") {
            String path = fs.getFileUrl();
            logger.info("存储的路径:" + path);
            File localFile = new File(path);

            //如果目录不存在,创建目录
            if (!localFile.getParentFile().exists() && !localFile.getParentFile().isDirectory()) {
                logger.info("创建目录:{}=========", localFile.getParentFile().getPath());

                localFile.getParentFile().mkdirs();
            }

            try {
                OutputStream os = new FileOutputStream(localFile);
                int bytesRead = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((bytesRead = is.read(buffer, 0, 1024 * 8)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                is.close();
            } catch (IOException e) {
                logger.error("文件:" + fs.getFileName() + "操作失败!" + e);
                ExceptionInfo.exceptionInfo(e, logger);
                return null;
            }
        }
        return fs;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public Base64Info getInfo() {
        return info;
    }

    public void setInfo(Base64Info info) {
        this.info = info;
    }
}
