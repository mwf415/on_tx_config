package cn.onlov.tx.oss;


import cn.onlov.tx.util.TXConfigProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

@Component
public class OssUtil {

    private static COSClient cosClient;
    public OssUtil() {
        if (cosClient == null) {
            cosClient = this.createCosClient();
        }

    }


    private COSClient createCosClient() {
        COSCredentials cred = new BasicCOSCredentials(TXConfigProperties.oss_secretId, TXConfigProperties.oss_secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(TXConfigProperties.oss_region));
        COSClient cosClientTemp = new COSClient(cred, clientConfig);
        return cosClientTemp;
    }

    /**
     *文件上传功能
     * @param localFile
     * @return
     */
    public static String uploadFile(File localFile){
        String name = localFile.getName();
        String s = updateOss(localFile, name);
        return s;
    }

    /**
     * 文件上传需要新的名字功能
     * @param localFile
     * @param needNewName
     * @return
     */
    public static String uploadFile(File localFile,boolean needNewName){
        String fileName = UUID.randomUUID().toString();
        String name = localFile.getName();
        String fileType = name.substring(name.lastIndexOf("."),name.length());
        String s = updateOss(localFile, fileName  + fileType);
        return s;
    }

    private static String updateOss(File localFile, String fileName){
        fileName = "home/upload/" + fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(TXConfigProperties.oss_bucketName, fileName, localFile);
        // 设置存储类型：标准存储(Standard), 低频存储(Standard_IA)和归档存储(ARCHIVE)。默认是标准存储(Standard)
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            String etag = putObjectResult.getETag();
            return fileName;
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }finally {
            // 关闭客户端
            cosClient.shutdown();
        }
        return null;
    }


    /**
     * 文件下载功能
     * @param key
     * @param localPath
     * @return
     */

    public static void downLoadOss(String key ,String localPath){
        InputStream is = downLoadOss(key);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(localPath);
            byte[] b = new byte[1024];
            int length;
            while((length= is.read(b))>0){
                fos.write(b,0,length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
       }
        try {
            is.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream downLoadOss(String key){
        String bucketName = TXConfigProperties.oss_bucketName;
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject downObjectMeta = cosClient.getObject(getObjectRequest);
        COSObjectInputStream objectContent = downObjectMeta.getObjectContent();
        return objectContent ;
    }




    public void delete(){
        // 指定文件所在的存储桶
        String bucketName = "demoBucket-1250000000";
// 指定文件在 COS 上的对象键
        String key = "upload_single_demo.txt";

        cosClient.deleteObject(bucketName, key);
    }

}
