package com.maomao.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.maomao.mapper.TbBrandMapper;
import com.maomao.pojo.TbBrand;
import com.maomao.pojo.TbBrandExample;
import com.maomao.pojo.TbBrandExample.Criteria;
import com.maomao.sellergoods.service.BrandService;

import entity.PageResult;
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	@Autowired
	private TbBrandMapper brandMapper;
	@Override
	public List<TbBrand> findAll() {
		// TODO Auto-generated method stub
		return brandMapper.selectByExample(null);
	}
	@Override
	//分页
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> page= (Page<TbBrand>) brandMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}
	@Override
	public void add(TbBrand brand) {
		// TODO Auto-generated method stub
		brandMapper.insertSelective(brand);
	}
	@Override
	public TbBrand findbyid(Long id) {
		// TODO Auto-generated method stub
		return brandMapper.selectByPrimaryKey(id);
	}
	@Override
	public void update(TbBrand brand) {
		// TODO Auto-generated method stub
		brandMapper.updateByPrimaryKey(brand);
	}
	@Override
	public void delete(long[] ids) {
		// TODO Auto-generated method stub
		for(long id:ids){
			brandMapper.deleteByPrimaryKey(id);
		}
	}
	@Override
	public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbBrandExample example=new TbBrandExample();//创建查询条件
		
		Criteria criteria =example.createCriteria();
		if(brand!=null){
			if(brand.getName()!=null&&brand.getName().length()>0){
				criteria.andNameLike("%"+brand.getName()+"%");//添加更具name进行模糊查询
			}
			if(brand.getFirstChar()!=null&&brand.getFirstChar().length()>0){
				criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");//添加更具firstChar进行模糊查询
			}
		}
		
		Page<TbBrand> page= (Page<TbBrand>) brandMapper.selectByExample(example);
		
		return new PageResult(page.getTotal(), page.getResult());
	}
	@Override
	public List<Map> selectOptionList() {
		// TODO Auto-generated method stub
		return brandMapper.selectOptionList();
	}

}
