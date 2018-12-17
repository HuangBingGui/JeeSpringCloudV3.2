package com.jeespring.modules.utils.rest;

import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.web.Result;
import com.jeespring.modules.usercenter.entity.SysUserCenter;
import com.jeespring.modules.usercenter.service.SysUserCenterService;
import com.jeespring.modules.utils.service.MapApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * MapAPI百度地图RestController
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@RestController
@RequestMapping(value = "rest/utils/mapapi")
@Api(value="MapAPI百度地图", description="MapAPI百度地图")
public class MapApiRestController extends AbstractBaseController {

    @Autowired
    private MapApiService mapApiService;


    @Autowired
    private SysUserCenterService sysUserCenterService;

    @RequestMapping(value = {"getLocation"},method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="获取地址经纬度(Content-Type为text/html)", notes="获取地址经纬度(Content-Type为text/html)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ak", value = "百度授权码", required = false, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "output", value = "输出格式(json/xml)", required = false, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "address", value = "地址", required = false, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "city", value = "城市", required = false, dataType = "String",paramType="query")
    })
    public Result getLocation(@RequestParam(required=false) String ak, @RequestParam(required=false) String output, @RequestParam(required=false) String address, @RequestParam(required=false) String city) {
        return mapApiService.getLocation(ak,output,address,city);
    }

    @RequestMapping(value = {"getMap"},method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="获取地图(Content-Type为text/html)", notes="获取地图(Content-Type为text/html)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lat", value = "经度", required = false, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "lng", value = "纬度", required = false, dataType = "String",paramType="query"),
    })
    public ModelAndView getMap(@RequestParam(required=false) String lat, @RequestParam(required=false) String lng, HttpServletRequest request, HttpServletResponse response, Model model){
        if(lat=="" || lat == null) {
            lng = "113.24712";
        }
        if(lng=="" || lng == null) {
            lat = "23.098878";
        }
        model.addAttribute("lat", lat);
        model.addAttribute("lng", lng);
        ModelAndView mv = new ModelAndView("modules/utils/map");
        Map map = new HashMap();
        map.put("lat", lat);
        map.put("lng", lng);
        mv.addAllObjects(map);
        return mv;
    }

    @RequestMapping(value = {"getMapUserCenter"},method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="获取用户中心地图(Content-Type为text/html)", notes="获取用户中心地图(Content-Type为text/html)")
    @ApiImplicitParam(name = "sysUserCenter", value = "用户中心", dataType = "SysUserCenter",paramType="query")
    public ModelAndView getMapUserCenter(SysUserCenter sysUserCenter,HttpServletRequest request, HttpServletResponse response, Model model){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            if(sysUserCenter.getBeginCreateDate()==null || sysUserCenter.getEndCreateDate()==null){
                sysUserCenter.setBeginCreateDate(formatDateTime.parse(format.format(date)+" 00:00:00"));
                sysUserCenter.setEndCreateDate(formatDateTime.parse(format.format(date)+" 23:59:59"));
            }else{
                sysUserCenter.setBeginCreateDate(formatDateTime.parse(format.format(sysUserCenter.getBeginCreateDate())+" 00:00:00"));
                sysUserCenter.setEndCreateDate(formatDateTime.parse(format.format(sysUserCenter.getEndCreateDate())+" 23:59:59"));
            }
        }catch (Exception e){}
        List<SysUserCenter> sysUserCenterList=sysUserCenterService.findList(sysUserCenter);
        for (SysUserCenter item:sysUserCenterList) {
			if(item.getUserName()==null) {
                continue;
            }
            if(item.getUserPhone()==null) {
                item.setUserPhone("");
            }
			if(item.getUserName().indexOf("\"")>0) {
                item.setUserName(item.getUserName().replaceAll("\"", ""));
            }
            if(item.getLat()==null || item.getAddress()==null) {
                item.setLat("0");
                item.setLng("0");
                item.setAddress("-");
                item.setCity("-");
            }
            if(item.getLat().indexOf(".")<=0 || item.getAddress().length()<=6) {
                continue;
            }
            if(item.getLat().substring(item.getLat().indexOf(".")).length()<=10 && item.getAddress().length()>6){
                Result result=mapApiService.getLocation("","",item.getAddress(),item.getCity());
                if("0".equals(result.getResultCoe().toString())){
                    int index=result.getResultObject().toString().indexOf("|");
                    int size=result.getResultObject().toString().length();
                    if(index>0){
                        item.setLng(result.getResultObject().toString().substring(0,index));
                        item.setLat(result.getResultObject().toString().substring(index+1));
                        String lat=String.valueOf(Double.valueOf(item.getLat()) +(Math.random()*9+1)*0.00001*5);
                        String lng=String.valueOf(Double.valueOf(item.getLng()) +(Math.random()*9+1)*0.00001*5);
                        item.setLng(lng);
                        item.setLat(lat);
                    }
                    sysUserCenterService.save(item);
                }
            }
        }
        ModelAndView mv = new ModelAndView("modules/utils/mapUserCenter");
        Map map = new HashMap();
        map.put("sysUserCenterList", sysUserCenterList);
        map.put("count", sysUserCenterList.size());
        map.put("sysUserCenter", sysUserCenter);
        mv.addAllObjects(map);
        return mv;
    }
}
