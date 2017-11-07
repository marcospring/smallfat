package com.smt.smallfat.service.common.upload.impl;

import com.csyy.common.JSONUtils;
import com.csyy.common.PropertiesUtils;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FileStorage;
import com.smt.smallfat.service.common.upload.IFileInfoService;
import com.smt.smallfat.utils.FileUtils;
import com.smt.smallfat.utils.process.ImgHandle;
import com.smt.smallfat.exception.ImgHandleResultException;
import com.smt.smallfat.utils.process.ImgStorageHandle;
import com.smt.smallfat.utils.process.ImgThumbnailHandle;
import com.smt.smallfat.vo.upload.Base64Info;
import com.smt.smallfat.vo.upload.UploadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Created by zhangbin on 16/5/19.
 */
@Service("fileInfoService")
public class FileInfoServiceImpl extends BaseServiceImpl implements IFileInfoService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PropertiesUtils propertiesUtils;

    @Override
    public UploadInfo fileStored(MultipartHttpServletRequest multiRequest) {

        UploadInfo info = new UploadInfo();

        String parameter = multiRequest.getParameter(Constant.PARAM_KEY);

        //取得request中的所有文件名
        Iterator<String> iter = multiRequest.getFileNames();
        List<String> success = new ArrayList<>();
        List<String> error = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        while(iter.hasNext()){
            //记录上传过程起始时的时间，用来计算上传时间
            int pre = (int) System.currentTimeMillis();
            //取得上传文件
            MultipartFile file = multiRequest.getFile(iter.next());

            String url ;
            //记录上传成功失败个数
            if((url= fileHandler(file,parameter))!=null){
                success.add(file.getName());
                urls.add(url);
            }else {
                error.add(file.getName());
            }
            //记录上传该文件后的时间
            int finalTime = (int) System.currentTimeMillis();

            logger.info("文件:"+file.getName()+"处理时间"+String.valueOf(finalTime-pre));
        }

        info.setUrl(urls.toArray(new String[urls.size()]));
        info.setErrorNum(error.size());
        if(error.size()>0 || info.getUrl().length == 0){
            String[] array = new String[error.size()];
            info.setErrorFileNames(array);
            info.setCode(Constant.FILE_UPLOAD_FAIL);
        }else {
            info.setCode(Constant.FILE_UPLOAD_SUCCESS);
        }

        info.setSuccessNum(success.size());

        return info;
    }

    /**
     * 单文件处理
     * @param file
     * @return
     */
//    @Transactional
    private String fileHandler(MultipartFile file,String paramStr){
        logger.error("文件处理开始===============================");
        ImgHandle ish;
        if(file != null){
            if(Boolean.valueOf(propertiesUtils.get("isThumbnail"))) {
                ish = new ImgStorageHandle(file, paramStr,propertiesUtils.get("baseUrl"));
            }else {
                ish = new ImgStorageHandle(file, paramStr);
            }
            return handler(ish);
        }
        return null;
    }

//    @Transactional
    private String fileHandler(Base64Info base64,String paramStr){
        logger.error("文件处理开始===============================");

        byte[] bytes = FileUtils.GenerateImage(base64.getBase64Str());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        if(inputStream != null){
            ImgHandle ish;
            if(Boolean.valueOf(propertiesUtils.get("isThumbnail"))){
                ish = new ImgThumbnailHandle(new ImgStorageHandle(inputStream,base64,paramStr));
            }else {
                ish = new ImgStorageHandle(inputStream,base64,paramStr);
            }
            return handler(ish);
        }
        return null;
    }

    private String handler(ImgHandle ish){

        FileStorage info = FileStorage.getInstance(FileStorage.class);

        try {
            ish.handle();
        }catch (ImgHandleResultException img){
            info = img.getInfo();
        }

        logger.info("addInfo:"+ JSONUtils.toJson(info));

        FileStorage fileStorage = addInfo(info);

        logger.info("FileStorage保存成功"+ JSONUtils.toJson(info));

        return info.getFileUrl();
    }


    @Override
    public UploadInfo fileStored(String params,Base64Info... fileData) {

        UploadInfo info = new UploadInfo();

        List<String> success = new ArrayList<>();
        List<String> error = new ArrayList<>();

        if(fileData != null && fileData.length > 0 ){
            for (Base64Info base64 : fileData) {
                String url = fileHandler(base64, params);
                if(url != null) {
                    success.add(url);
                }else {
                    error.add(url);
                }
            }
        }

        info.setUrl(success.toArray(new String[success.size()]));

        if(success.size() ==  0 || info.getUrl().length == 0 ){
            info.setCode(Constant.FILE_UPLOAD_FAIL);
        }else {
            info.setCode(Constant.FILE_UPLOAD_SUCCESS);
        }

        return info;
    }

    @Transactional
    public int editFileInfo(Map<String,Object> map){

        FileStorage po = CommonBeanUtils.transMap2BasePO(map, FileStorage.getInstance(FileStorage.class));
        return factory.getWriteDataSession().update(FileStorage.class, po);
    }

    @Transactional
    public FileStorage addInfo(FileStorage fileStorage){
        return factory.getWriteDataSession().save(FileStorage.class, fileStorage);
    }


}
