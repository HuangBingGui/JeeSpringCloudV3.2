package com.jeespring.modules.baiduface.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeespring.common.utils.GsonUtils;
import com.jeespring.common.utils.HttpUtil;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人脸识别Controller
 * @author 唐继涛
 * @version 2018-7-13
 */
@RestController
@RequestMapping(value = "/rest/face")
@Api(value = "face百度人脸识别API接口", description = "face百度人脸识别API接口")
public class FaceRecognitionRestController extends AbstractBaseController {
    //设置APPID/AK/SK
    public static final String APP_ID = "11483847";
    public static final String API_KEY = "Hn5zrGgRe5WWiXV1GcWYirFT";
    public static final String SECRET_KEY = "vlzV3XEvuc1zGa9cfi5PpdRuFlfz08gu";
    public String id = null;

    @RequestMapping(value = {"match"}, method = {RequestMethod.POST})
    @ApiOperation(value = "人脸对比(Content-Type为application/json)", notes = "人脸对比(Content-Type为application/json)")
    @ApiImplicitParam(name = "String", value = "人脸对比", dataType = "String")
    public String match(@RequestBody String imgStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match"; //百度人脸对比的API
        try {
//            String str = this.getUser(userId);

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", imgStr);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", imgStr);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");
            images.add(map1);
            images.add(map2);
            String param = GsonUtils.toJson(images);
            String token =  FaceRecognitionRestController.getAuth();
            String result = HttpUtil.post(url, token, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"search"}, method = {RequestMethod.POST})
    @ApiOperation(value = "人脸搜索(Content-Type为application/json)", notes = "人脸搜索(Content-Type为application/json)")
    @ApiImplicitParam(name = "String", value = "人脸搜索", dataType = "String")
    public Result search(@RequestBody String imgStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search"; //百度人脸搜索的API
        Result result = ResultFactory.getSuccessResult();
        try {
            String Str = this.GroupGetlist(); //调用百度查询用户组的API接口，将查询返回的数据接收。
            JSONObject baiJsonObject = JSONObject.parseObject(Str); //将数据转换为json对象类型的数据。
            String str = baiJsonObject.getJSONObject("result").getString("group_id_list").replace("\"", "").replace("[", "").replace("]", "");
            Map<String, Object> map = new HashMap<>();
            map.put("image", imgStr);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", str);
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
            String param = GsonUtils.toJson(map);
            //调用百度的人脸检测API获取百度API返回的数据。
            String token =  FaceRecognitionRestController.getAuth();
            String baiDuResult = HttpUtil.post(url, token, "application/json", param);

            JSONObject returnResult = JSONObject.parseObject(baiDuResult);  //将百度返回的数据转为json对象类型的数据。
            String user = returnResult.getJSONObject("result").getString("user_list").replace("\"", "").replace("[", "")
                    .replace("]", "").replace("{","").replace("}","");
            String[] userList = user.split(",");  //根据逗号分隔用户信息。
            JSONArray jsonArray = (JSONArray) JSONArray.toJSON(userList);  //将字符串数组转为json 数组类型的。

            String[] scoreList = ((String) jsonArray.get(0)).split(":"); //获取json数组第一条数据然后根据:分隔。
            JSONArray json = (JSONArray) JSONArray.toJSON(scoreList);  //获取百度返回user_list数据的score评分。
            String scoreString = String.valueOf(json.get(1));  //将分数值转成string类型的。
            Double score = Double.parseDouble(scoreString);    //将string类型的转换成double。
            //判断分数值
            if(score >= 80){
                result = ResultFactory.getSuccessResult("检测成功！");
            }else {
                result = ResultFactory.getErrorResult("检测失败，请重新扫描!！");
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"add"}, method = {RequestMethod.POST})
    @ApiOperation(value = "人脸注册(Content-Type为application/json)", notes = "人脸注册(Content-Type为application/json)")
    @ApiImplicitParam(name = "String", value = "人脸注册", dataType = "String")
    public Result add(@RequestBody String imgStr, String userId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add"; //百度添加到人脸库的API
        Result result = ResultFactory.getSuccessResult();
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("image", imgStr);
            map.put("group_id", "测试人脸库");
            map.put("user_id", 11);
            map.put("user_info","测试");
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "NORMAL");
            String param = GsonUtils.toJson(map);
            String token =  FaceRecognitionRestController.getAuth();
            String baiDuResult = HttpUtil.post(url, token, "application/json", param);
            if (baiDuResult.contains("SUCCESS")) {
                result = ResultFactory.getSuccessResult("注册成功！");
            } else {
                result = ResultFactory.getErrorResult("注册失败！");
            }
            return result;
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return null;
    }

    /**
     * 组列表查询
     */
    private String GroupGetlist() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/getlist";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("start", 0);
            map.put("length", 100);
            String param = GsonUtils.toJson(map);
            String token =  FaceRecognitionRestController.getAuth();
            String result = HttpUtil.post(url, token, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static  String getAuth() {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + API_KEY
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + SECRET_KEY;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            String access_token = jsonObject.getString("access_token");
            System.out.print(access_token);
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    /*public static void main(String[] args) {

//        new FaceSpot().getAuth();
        getAuth();
    }*/
}
