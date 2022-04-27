package com.zhangkaigang.qrcode;

import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

/**
 * @Description:将二维码写入PDF文件，如果生成二维码的文件是临时文件，则需要删除临时文件fileOutputStream.close();tempFile.delete();
 * @Author:zhang.kaigang
 * @Date:2022/4/26
 * @Version:1.0
 * 参考：
 * https://blog.csdn.net/u014427391/article/details/91655064
 * https://blog.csdn.net/qq_37043780/article/details/83385656
 */
public class QrCodeToPdf {

    public static void main(String[] args) throws Exception{
        File file = new File("D:\\test.pdf");
        InputStream inputStream = new FileInputStream(file);
        File tempFile = new File("D:\\testTemp.pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
        setQrCodeForPDF(bos, inputStream);
    }

    public static void setQrCodeForPDF(BufferedOutputStream bos, InputStream inputStream) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("当前时间：").append(new Date()).append("\n");
        BufferedImage qrCodeImg = QrCodeUtils.createQrCodeImg(sb.toString(), 175, 175);
        PdfReader reader = new PdfReader(inputStream);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte waterMar;
        PdfGState gs = new PdfGState();
        // 将二维码加在最后一页的右下角
        /*也可以加在每一页
        for (int i = 1; i < total; i++) {
            //content = stamper.getOverContent(i);// 在内容上方加水印
            waterMar = stamper.getUnderContent(1);//在内容下方加水印
        }*/
        waterMar = stamper.getOverContent(total-1);
        // 设置图片透明度为0.2f
//        gs.setFillOpacity(0.2f);
        // 设置笔触字体不透明度为0.4f
//        gs.setStrokeOpacity(0.4f);
        waterMar.beginText();
        com.lowagie.text.Image itextImage = com.lowagie.text.Image.getInstance(qrCodeImg,null);
        itextImage.setWidthPercentage(0.1f);
        itextImage.setSpacingAfter(0.1f);
        itextImage.scalePercent(99);
        itextImage.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        // 位置：左边距，底边距
        itextImage.setAbsolutePosition(500, 0);
        // 自定义大小（在文件上显示的大小）
        itextImage.scaleAbsolute(80,80);
        waterMar.addImage(itextImage);
        waterMar.endText();
        waterMar.stroke();
        stamper.close();
        reader.close();

    }
}
