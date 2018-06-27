package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSONObject;
import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xyauto.interact.broker.server.util.Constants;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@Service
public class FileService {

    @Autowired
    private FastFileStorageClient storageClient;

    public String uploadFile(MultipartFile file) throws IOException {
        DefaultFastFileStorageClient client = (DefaultFastFileStorageClient)storageClient;
        StorePath storePath = client.uploadFile(Constants.fileGroupName, file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()));
        String path = storePath.getFullPath();
        if (path.startsWith("/")) {
            return path;
        }
        return "/"+path;
    }
    
    public String uploadFile(BufferedImage file, String extension) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(file, extension, baos);
        DefaultFastFileStorageClient client = (DefaultFastFileStorageClient)storageClient;
        StorePath storePath = client.uploadFile(Constants.fileGroupName, new ByteArrayInputStream(baos.toByteArray()), baos.size(), extension);
        String path = storePath.getFullPath();
        if (path.startsWith("/")) {
            return path;
        }
        return "/"+path;
    }

    public String uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return storePath.getFullPath();
    }

    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return storePath.getFullPath();
    }

    public byte[] downloadFile(String path) {
        StorePath storePath = StorePath.praseFromUrl(path);
        byte[] file = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadCallback<byte[]>() {
            @Override
            public byte[] recv(InputStream ins) throws IOException {
                ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
                byte[] bcache = new byte[2048];
                int readSize = 0;
                while ((readSize = ins.read(bcache)) > 0) {
                    infoStream.write(bcache, 0, readSize);
                }
                return infoStream.toByteArray();
            }
        });
        return file;
    }
    

    

    


}
