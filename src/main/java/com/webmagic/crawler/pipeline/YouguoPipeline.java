package com.webmagic.crawler.pipeline;

import com.webmagic.crawler.processor.YouguoProcessor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;

/**
 * 入库模块Pipeline
 *
 * @author wpq
 * @date 2018/11/22 14:30
 */
@Component
public class YouguoPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        URL url = null;
        try {
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getKey().contains(YouguoProcessor.title)) {
                    String path = (String) entry.getValue();
                    String[] split = path.split(",");
                    String filePath = split[0].trim();
                    String src = split[1];
                    url = new URL(src);
                    String fielName = src.substring(src.lastIndexOf("/") + 1, src.length());
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath, fielName));
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    DataInputStream dataInputStream = new DataInputStream(url.openStream());
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = dataInputStream.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                    fileOutputStream.write(output.toByteArray());
                    dataInputStream.close();
                    fileOutputStream.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
