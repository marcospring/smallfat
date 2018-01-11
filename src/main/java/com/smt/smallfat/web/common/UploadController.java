package com.smt.smallfat.web.common;

import com.csyy.common.JSONUtils;
import com.csyy.common.PropertiesUtils;
import com.csyy.common.UUIDProvider;
import com.csyy.core.exception.CommonException;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.service.common.upload.IFileInfoService;
import com.smt.smallfat.utils.ueditor.ActionEnter;
import com.smt.smallfat.vo.upload.Base64Info;
import com.smt.smallfat.vo.upload.UploadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangbin on 16/5/18.
 */
@Controller
public class UploadController extends BaseController {

    @Resource
    private IFileInfoService fileInfoService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/uploadFile.json")
    public void upload(HttpServletRequest request,HttpServletResponse response) {

        String uuid = UUIDProvider.uuid();

        logger.info("=======================上传文件=======================[{}]", uuid);

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        UploadInfo resultInfo = null;

        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            resultInfo = fileInfoService.fileStored(multiRequest);
        }

        logger.info("文件上传完成=========[{}],[{}]", uuid, JSONUtils.toJson(resultInfo));

        printWriter(response,successResultJSON(resultInfo));
    }



    @RequestMapping("/uploadBase64.json")
    public void uploadBase64(HttpServletResponse response,String fileData, String fileName, String fileType) {

        String uuid = UUIDProvider.uuid();

        logger.info("base64上传文件[{}]", uuid);

        String[] split = fileData.split("base64,");
        if (split.length > 1) {
            String base64 = split[1];
            Base64Info info = new Base64Info();
            info.setFileName(fileName);
            info.setFileType(fileType);
            info.setBase64Str(base64);
            UploadInfo resultInfo = fileInfoService.fileStored("", info);
            logger.info("base64上传文件完成:[{}],[{}]", uuid, JSONUtils.toJson(resultInfo));
            printWriter(response,successResultJSON(resultInfo));
        } else {
            throw new CommonException(ResultConstant.Upload.PARAM_ERROR);
        }

    }





}
