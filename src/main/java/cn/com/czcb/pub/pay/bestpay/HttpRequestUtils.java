package cn.com.czcb.pub.pay.bestpay;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * function: 发送Http请求 <br/>
 * author:wangjie <br/>
 * date:2016/5/16 <br/>
 */
public class HttpRequestUtils {
    public static String doPost(String url,String jsonStr){
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String result="";
        try {
            StringEntity s = new StringEntity(jsonStr);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            org.apache.http.HttpResponse res = client.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(res.getEntity());// 返回json格式：
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

