package com.maomao.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomao.pojo.TbBrand;
import com.maomao.sellergoods.service.BrandService;

import entity.Result;
import entity.PageResult;



@RestController
@RequestMapping("/brand")
public class BrandController {
	@Reference
	BrandService brandService;
	
	@RequestMapping("/findAll")
	public List<TbBrand> findAll(){
		return brandService.findAll();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage(int page,int size){
		return brandService.findPage(page,size);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand brand){
	 
		try {
			brandService.add(brand);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Result(false, "添加失败");
		}	
	}
	@RequestMapping("/find")
	public TbBrand find(long id){
		return brandService.findbyid(id);
	}
	
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand brand){
	 
		try {
			brandService.update(brand);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Result(false, "修改失败");
		}	
	}
	
	@RequestMapping("/delete")
	public Result delete(long[] ids){
	 
		try {
			brandService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Result(false, "删除失败");
		}	
	}
	
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbBrand brand,int page,int size){
		return brandService.findPage(brand,page,size);
	}
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList(){
		return brandService.selectOptionList();
	}
}
