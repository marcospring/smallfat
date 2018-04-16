package com.smt.smallfat.service.common.upload;

import com.csyy.core.apisupport.BaseService;
import com.smt.smallfat.vo.upload.Base64Info;
import com.smt.smallfat.vo.upload.UploadInfo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

/**
 * Created by zhangbin on 16/5/19.
 */
public interface IFileInfoService extends BaseService {

    /**
     * 将流中的所有文件持久化
     *
     * @param multiRequest
     * @return
     */
    UploadInfo fileStored(MultipartHttpServletRequest multiRequest);


    /**
     * 根据id,或uuid 修改
     *
     * @param map
     */
    int editFileInfo(Map<String, Object> map);


    /**
     * 存储base64图片
     *
     * @param fileData
     * @param params
     * @return
     */
    UploadInfo fileStored(String params, Base64Info... fileData);
}
