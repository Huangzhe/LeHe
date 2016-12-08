package com.sh.lynn.hz.lehe.net;

/**
 * Created by hyz84 on 16/12/7.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.file.FileUtils;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by zhaodaizheng on 16/5/20.
 */
public class FrescoUtil {

    private static final String TAG = "FrescoUtil";
    public static final String IMAGE_PIC_CACHE_DIR = Environment.getExternalStorageDirectory().getPath() + "/LeHe/image/";

    /**
     * 保存图片
     *
     * @param context
     * @param picUrl
     */
    public static void savePicture(String picUrl, String imageName, Context context) {

        File picDir = new File(IMAGE_PIC_CACHE_DIR, imageName);

        if (!picDir.exists()) {
            picDir.mkdir();
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(Uri.parse(picUrl)), "Image");
        File cacheFile = getCachedImageOnDisk(cacheKey);
        if (cacheFile == null) {
            //downLoadImage(picUrl, imageName, context);
            return;
        } else {
            copyTo(cacheFile, picDir, "down");
        }
    }

    public static File getCachedImageOnDisk(CacheKey cacheKey) {
        File localFile = null;
        if (cacheKey != null) {
            if (ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainDiskStorageCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }


    /**
     * 复制文件
     *
     * @param src 源文件
     * @param dir 目标文件
     * @return
     */
    public static boolean copyTo(File src, File dir, String filename) {

        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(src);
            in = fi.getChannel();//得到对应的文件通道
            File dst;
            dst = new File(dir, filename + ".jpg");
            fo = new FileOutputStream(dst);
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {

                if (fi != null) {
                    fi.close();
                }

                if (in != null) {
                    in.close();
                }

                if (fo != null) {
                    fo.close();
                }

                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

    }


    public static void saveGif(final String url,  Context context,BaseDataSubscriber<CloseableReference<PooledByteBuffer>> baseDataSubscriber) {
        int index = url.lastIndexOf("/");
       final String fileName =url.substring(index);

        Uri uri = Uri.parse(url);
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<PooledByteBuffer>>
                dataSource = imagePipeline.fetchEncodedImage(imageRequest, context);
        dataSource.subscribe(baseDataSubscriber, CallerThreadExecutor.getInstance());
    }

    public static void savePic(String fileName, ByteArrayOutputStream bos) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LeHe/image/";
        File dirs = new File(dir);
        try {
            FileUtils.mkdirs(dirs);
        } catch (FileUtils.CreateDirectoryException e) {
            e.printStackTrace();
        }
        File target = new File(dir, fileName);
        FileOutputStream fos =null;
        try {
             fos = new FileOutputStream(target);
            bos.writeTo(fos);

            fos.flush();

            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fos!=null)
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(bos!=null)
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}