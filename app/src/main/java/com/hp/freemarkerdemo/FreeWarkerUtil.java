package com.hp.freemarkerdemo;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * Created by tangdekun on 2017/8/15.
 * FreeWarker工具类
 * FreeWarker：上面这个html文件，放到应用中，用的时候只要传入数据就行
 */

public class FreeWarkerUtil {
    private static final String TAG = FreeWarkerUtil.class.getSimpleName();
    private static volatile FreeWarkerUtil instance = null;
    private Context mContext;
    private FreeWarkerUtil(Context context) {
        mContext = context;

    }
//    private FreeWarkerUtil() {
//        mContext = EducationApplication_.getAppContext();
//
//    }

    public static FreeWarkerUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (FreeWarkerUtil.class){
                if (instance == null) {
                    instance = new FreeWarkerUtil(context);
                }
            }
        }
        return instance;
    }

    public void prepareTemplate(String ftlName, String tplName)  {
        //获取app目录  data/data/package/file/
        String destPath = mContext.getFilesDir().getAbsolutePath();
        File dir = new File(destPath);
        //判断文件夹是否存在并创建
        if (!dir.exists()) {
            dir.mkdir();
        }
        //需要生成的.ftl模板文件名及路径
        String tempFile = destPath + "/" + "main.ftl";
        if (!(new File(tempFile).exists())) {
            //获取assets中.tpl模板文件
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = mContext.getResources().getAssets().open("main.tpl");
                //生成.ftl模板文件
                fos = new FileOutputStream(tempFile);
                byte[] buffer = new byte[7168];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.flush();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String getHtml(String htmlName, String tplName, Object object){
        String htmlPath = null;
        String destPath =  mContext.getFilesDir().getAbsolutePath();
        FileWriter out = null;
        //数据源
        Map root = new HashMap();
        root.put("user", "user");   //传入字符串
        //root.put("product", object.url());     //传入对象(会报错)
        try {
            Configuration cfg = new Configuration(new Version(2,3,0));
            cfg.setDefaultEncoding("UTF-8");
            //设置报错提示
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            //设置报错提示

//            cfg.
            out = new FileWriter(new File(destPath + "main.html"));
            //设置.ftl模板文件路径
            cfg.setDirectoryForTemplateLoading(new File(destPath));
            //设置template加载的.ftl模板文件名称
            Template temp = cfg.getTemplate("main.ftl");
            //讲数据源和模板生成.html文件
            temp.process(root, out);
            out.flush();
        }  catch (IOException e) {

        } catch (Exception e){

        }finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return destPath+"/"+htmlName+".html";
    }
}
