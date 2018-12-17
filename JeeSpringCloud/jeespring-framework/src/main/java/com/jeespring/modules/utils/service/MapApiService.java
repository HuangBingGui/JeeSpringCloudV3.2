package com.jeespring.modules.utils.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeespring.common.utils.HttpRequest;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MapAPI百度地图Service
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@Service
public class MapApiService {

    //获取地址经纬度
    public Result getLocation(@RequestParam(required=false) String ak, @RequestParam(required=false) String output, @RequestParam(required=false) String address, @RequestParam(required=false) String city) {
        if(ak=="" || ak==null) {
            ak = "2ae1130ce176b453fb29e59a69b18407";
        }
        if(output=="" || output==null) {
            output = "json";
        }
        if(address=="" || address==null) {
            address = "广州";
        }
        if(city=="" || city==null) {
            city = "广州";
        }
        String url="http://api.map.baidu.com/geocoder/v2/?ak="+ak+"&callback=renderOption&output="+output+"&address="+address+"&city="+city;
        Result result;
        try{
            JSONObject jsonObject = JSON.parseObject(HttpRequest.sendGet(url,"").replace("renderOption&&renderOption(","").replace(")",""));
            JSONObject resultObject = jsonObject.getJSONObject("result");
            JSONObject location = resultObject.getJSONObject("location");
            String lat = location.getString("lat");
            String lng = location.getString("lng");
            result = ResultFactory.getSuccessResult();
            result.setResultObject(lng+"|"+lat);
        }catch (Exception e){
            result = ResultFactory.getErrorResult("MapAPI获取失败！");
        }
        return result;
    }
}
