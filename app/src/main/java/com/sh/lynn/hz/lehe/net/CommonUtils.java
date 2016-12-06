package com.sh.lynn.hz.lehe.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.file.FileUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

/**
 * Created by hyz84 on 16/11/23.
 */

public class CommonUtils {

    private final static String TAG = "CommonUtils";

    public static String html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;
    }


    public static String writeResponseBodyToDisk(String fileName,ResponseBody body) {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File dir = new File(path + File.separator +"/LeHe/img/");

            if(!dir.exists())
                dir.mkdirs();
            File file = new File(path + File.separator +"/LeHe/img/",fileName+".jpg");
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;


                }

                outputStream.flush();

                return file.getAbsolutePath();
            } catch (IOException e) {
                return "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return "";
        }
    }


    public static  Bitmap getBitmap(Uri uri) {

        Bitmap bitmap = null;
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(uri.toString()));
        File file = resource.getFile();
        bitmap = BitmapFactory.decodeFile(file.getPath());
        return bitmap;

    }

    public static void saveImage(Uri uri,String fileName){
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(uri.toString()));
        File file = resource.getFile();

        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());


        String dir =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/LeHe/image/";
        File dirs = new File(dir);
        try {
            FileUtils.mkdirs(dirs);
        } catch (FileUtils.CreateDirectoryException e) {
            e.printStackTrace();
        }
        File taget = new File(dir,fileName);

        try {
            FileOutputStream out = new FileOutputStream(taget);
           boolean b = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存"+b);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
