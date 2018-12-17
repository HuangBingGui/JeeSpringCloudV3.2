package com.jeespring.modules.sys.rest;

import com.jeespring.common.web.AbstractBaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeespring.modules.sys.entity.Dict;
import com.jeespring.modules.sys.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;

@RestController
@RequestMapping(value={"/rest/sys/dict"})
@Api(value="dict数据字典云接口", description="dict数据字典云接口")
public class DictRestController extends AbstractBaseController {

    @Autowired
    private DictService dictService;

    @RequestMapping(value = {"get"},method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="数据字典获取信息(Content-Type为text/html)", notes="数据字典获取信息(Content-Type为text/html)")
    @ApiImplicitParam(name = "id", value = "数据字典id", required = false, dataType = "String",paramType="query")
    public Result get(@RequestParam(required=false) String id) {
        Dict dict=null;
        if (StringUtils.isNotBlank(id)){
            dict=dictService.get(id);
        }else{
            dict=new Dict();
        }
        Result result = ResultFactory.getSuccessResult();
        result.setResultObject(dict);
        return result;

    }
    @RequestMapping(value = {"get/json"},method ={RequestMethod.POST})
    @ApiOperation(value="数据字典获取信息(Content-Type为application/json)", notes="数据字典获取信息(Content-Type为application/json)")
    @ApiImplicitParam(name = "id", value = "数据字典id", required = false, dataType = "String",paramType="body")
    public Result getJson(@RequestBody(required=false) String id) {
        Dict dict=null;
        if (StringUtils.isNotBlank(id)){
            dict=dictService.get(id);
        }else{
            dict=new Dict();
        }
        Result result = ResultFactory.getSuccessResult();
        result.setResultObject(dict);
        return result;
    }

    //RequiresPermissions("sys:dict:list")
    @RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="数据字典获取列表(Content-Type为text/html)", notes="数据字典获取列表(Content-Type为text/html)")
    @ApiImplicitParam(name = "dict", value = "数据字典", dataType = "Dict",paramType="query")
    public Result list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict);
        Result result = ResultFactory.getSuccessResult();
        result.setResultObject(page);
        return result;
    }

    @RequestMapping(value = {"list/json"},method ={RequestMethod.POST})
    @ApiOperation(value="数据字典获取列表(Content-Type为application/json)", notes="数据字典获取列表(Content-Type为application/json)")
    @ApiImplicitParam(name = "dict", value = "数据字典", dataType = "Dict",paramType="body")
    public Result listJson(@RequestBody Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict);
        Result result = ResultFactory.getSuccessResult();
        result.setResultObject(page);
        return result;
    }

}
