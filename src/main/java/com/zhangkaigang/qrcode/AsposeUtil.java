package com.zhangkaigang.qrcode;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.*;

/**
 * @Description:TODO
 * @Author:zhang.kaigang
 * @Date:2022/4/27
 * @Version:1.0
 */
public class AsposeUtil {

    public static void getAsposeLicense() throws Exception{
        InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream("aspose.license.xml");
        License license = new License();
        license.setLicense(is);
    }


    public static byte[] fileToByte(File tradeFile){
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }

    public static byte[] byteToPdf(byte[] content) {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream inputStream = new ByteArrayInputStream(content)) {
            com.aspose.words.Document document = new com.aspose.words.Document(inputStream);
            document.save(bos, SaveFormat.PDF);
            return bos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] docToDocx(byte[] content) {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream inputStream = new ByteArrayInputStream(content)) {
            Document document = new Document(inputStream);
            document.save(bos, SaveFormat.DOCX);
            return bos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

}
