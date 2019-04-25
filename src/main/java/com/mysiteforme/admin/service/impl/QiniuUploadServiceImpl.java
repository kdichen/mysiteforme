package com.mysiteforme.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mysiteforme.admin.entity.Rescource;
import com.mysiteforme.admin.entity.UploadInfo;
import com.mysiteforme.admin.exception.MyException;
import com.mysiteforme.admin.service.UploadInfoService;
import com.mysiteforme.admin.service.UploadService;
import com.mysiteforme.admin.util.QETag;
import com.mysiteforme.admin.util.RestResponse;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.xiaoleilu.hutool.util.RandomUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

@Service("qiniuService")
public class QiniuUploadServiceImpl implements UploadService {

    @Autowired
    private UploadInfoService uploadInfoService;


    private UploadInfo getUploadInfo() {
        return uploadInfoService.getOneInfo();
    }

    private UploadManager getUploadManager() {
        Zone z = Zone.zone0();
        Configuration config = new Configuration(z);
        return new UploadManager(config);
    }

    private BucketManager getBucketManager() {
        Zone z = Zone.zone0();
        Configuration config = new Configuration(z);
        Auth auth = Auth.create(getUploadInfo().getQiniuAccessKey(), getUploadInfo().getQiniuSecretKey());
        return new BucketManager(auth, config);
    }

    private String getAuth() {
        if (getUploadInfo() == null) {
            throw new MyException("上传信息配置不存在");
        }
        Auth auth = Auth.create(getUploadInfo().getQiniuAccessKey(), getUploadInfo().getQiniuSecretKey());
        return auth.uploadToken(getUploadInfo().getQiniuBucketName());
    }

    /**
     * 废弃
     *
     * @param file MultipartFile文件对象
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return "";
    }

    /**
     * 废弃
     *
     * @param path 文件地址
     * @return
     */
    @Override
    public Boolean delete(String path) {
        return null;
    }

    /**
     * 废弃
     *
     * @param url 网络文件的地址
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public String uploadNetFile(String url) throws IOException, NoSuchAlgorithmException {
        return "";
    }

    @Override
    public String uploadLocalImg(String localPath) {
        File file = new File(localPath);
        if (!file.exists()) {
            throw new MyException("本地文件不存在");
        }
        QETag tag = new QETag();
        String hash = null;
        try {
            hash = tag.calcETag(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Rescource rescource = new Rescource();
        EntityWrapper<RestResponse> wrapper = new EntityWrapper<>();
        wrapper.eq("hash", hash);
        wrapper.eq("source", "qiniu");
        rescource = rescource.selectOne(wrapper);
        if (rescource != null) {
            return rescource.getWebUrl();
        }
        String filePath = "",
                extName = "",
                name = RandomUtil.randomUUID();
        extName = file.getName().substring(
                file.getName().lastIndexOf("."));
        StringBuffer key = new StringBuffer();
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getQiniuBasePath());
        String qiniuDir = getUploadInfo().getQiniuDir();
        if (StringUtils.isNotBlank(qiniuDir)) {
            key.append(qiniuDir).append("/");
            returnUrl.append(qiniuDir).append("/");
        }
        key.append(name).append(extName);
        returnUrl.append(name).append(extName);
        Response response = null;
        try {
            response = getUploadManager().put(file, key.toString(), getAuth());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        if (response.isOK()) {
            filePath = returnUrl.toString();
            rescource = new Rescource();
            rescource.setFileName(name + extName);
            rescource.setFileSize(new java.text.DecimalFormat("#.##").format(file.length() / 1024) + "kb");
            rescource.setHash(hash);
            rescource.setFileType(StringUtils.isBlank(extName) ? "unknown" : extName);
            rescource.setWebUrl(filePath);
            rescource.setSource("qiniu");
            rescource.insert();
        }
        return filePath;
    }

    @Override
    public String uploadBase64(String base64) {
        StringBuffer key = new StringBuffer();
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getQiniuBasePath());
        String qiniuDir = getUploadInfo().getQiniuDir();
        String fileName = RandomUtil.randomUUID(), filePath;
        if (StringUtils.isNotBlank(qiniuDir)) {
            key.append(qiniuDir).append("/");
            returnUrl.append(qiniuDir).append("/");
        }
        key.append(fileName);
        returnUrl.append(fileName);
        byte[] data = Base64.decodeBase64(base64);
        try {
            getUploadManager().put(data, key.toString(), getAuth());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnUrl.toString();
    }

    @Override
    public Boolean testAccess(UploadInfo uploadInfo) {
        ClassPathResource classPathResource = new ClassPathResource("static/images/userface1.jpg");
        try {
            Auth auth = Auth.create(uploadInfo.getQiniuAccessKey(), uploadInfo.getQiniuSecretKey());
            String authstr = auth.uploadToken(uploadInfo.getQiniuBucketName());
            InputStream inputStream = classPathResource.getInputStream();
            Response response = getUploadManager().put(inputStream, "test.jpg", authstr, null, null);
            if (response.isOK()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
