package com.smt.smallfat.utils.process;

import com.csyy.common.ExceptionInfo;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.exception.ImgHandleResultException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * img缩略图处理
 * Created by zhangbin on 2016/11/8.
 */
public class ImgThumbnailHandle implements ImgHandle {

    Logger logger = LoggerFactory.getLogger(ImgThumbnailHandle.class);

    private ImgHandle handle;

    public ImgThumbnailHandle(ImgHandle handle){
        this.handle = handle;
    }

    @Override
    public void handle() {
        try{
            handle.handle();
        }catch (ImgHandleResultException img){

            logger.info("生成缩略图============");
            try {
                logger.debug("byte[] 转换ByteArrayInputStream 流========");
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(img.getStream());

                logger.debug("ByteArrayInputStream转换image ========");
                Image srcImg  = ImageIO.read(tInputStringStream);

                logger.debug("ByteArrayInputStream转换image 流========");
                int height = srcImg.getHeight(null)* Constant.IMG_THUMBNAIL_WIDTH/srcImg.getWidth(null);
                BufferedImage image = new BufferedImage(Constant.IMG_THUMBNAIL_WIDTH
                    ,height,BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics = image.createGraphics();

                logger.debug("image 写入bufferImg========");
                boolean drawImage = graphics.drawImage(srcImg, 0, 0, Constant.IMG_THUMBNAIL_WIDTH
                        , height, null);
                logger.debug("drawImage===={}",drawImage);
                String savePath = img.getInfo().getFileUrl();

                savePath = savePath.replace(".","_thumbnail.");

                logger.info("缩略图url:[{}]======原高:[{}],现高:[{}],原宽:[{}],现宽:[{}]",savePath
                        ,srcImg.getHeight(null),image.getHeight(),srcImg.getWidth(null),image.getWidth());

                FileOutputStream fos = new FileOutputStream(new File(savePath));
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
                encoder.encode(image);

                image.flush();
                fos.flush();
                fos.close();

                logger.info("缩略图完成url:[{}]======",savePath);

                img.setHasThumbnail(true);

                throw img;

            } catch (IOException e) {
                ExceptionInfo.exceptionInfo(e,logger);
            }

        }
    }

}
