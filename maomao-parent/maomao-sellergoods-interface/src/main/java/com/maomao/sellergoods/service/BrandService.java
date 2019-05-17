package com.maomao.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.maomao.pojo.TbBrand;

import entity.PageResult;

public interface BrandService {
	public List<TbBrand> findAll();
	
	public PageResult findPage(int pageNum, int pageSize);
	
	public void add(TbBrand brand);
	
	public TbBrand findbyid(Long id);
	
	public void update(TbBrand brand);
	
	public void delete(long[] ids);
	
	public PageResult findPage(TbBrand brand,int pageNum, int pageSize);
	
	public List<Map> selectOptionList(); 
	
}
