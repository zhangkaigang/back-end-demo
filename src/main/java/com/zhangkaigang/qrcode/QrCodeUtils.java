package com.zhangkaigang.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description:TODO
 * @Author:zhang.kaigang
 * @Date:2022/4/26
 * @Version:1.0
 */
public class QrCodeUtils {

    /**
     *
     * @param content 二维码内容
     * @param qrCodeWidth 二维码宽度
     * @param qrCodeHeight 二维码高度
     * @return
     */
    public static BufferedImage createQrCodeImg(String content, int qrCodeWidth, int qrCodeHeight) {
        Map<EncodeHintType,Object> hits = new HashMap<EncodeHintType, Object>();
        hits.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BufferedImage image = null;
        try {
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("时间：").append(new Date()).append("\n");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    content, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight, hits);
            image = toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;

    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

}
