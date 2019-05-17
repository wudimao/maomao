package com.maomao.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.maomao.mapper.TbSpecificationMapper;
import com.maomao.mapper.TbSpecificationOptionMapper;
import com.maomao.pojo.TbSpecification;
import com.maomao.pojo.TbSpecificationExample;
import com.maomao.pojo.TbSpecificationExample.Criteria;
import com.maomao.pojo.TbSpecificationOption;
import com.maomao.pojo.TbSpecificationOptionExample;
import com.maomao.pojogroup.Specification;
import com.maomao.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		//获取规格实体
		TbSpecification tbSpecification= specification.getSpecification();
		//获取规格选项集合
		List<TbSpecificationOption> list =specification.getSpecificationOptionList();
		
		specificationMapper.insert(tbSpecification);
		
		for(TbSpecificationOption tbSpecificationOption:list){
			tbSpecificationOption.setSpecId(tbSpecification.getId());
			specificationOptionMapper.insert(tbSpecificationOption);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		//修改specification
		TbSpecification tbSpecification =specification.getSpecification();		
		specificationMapper.updateByPrimaryKey(tbSpecification);
		
		//删除specificationOption	
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.maomao.pojo.TbSpecificationOptionExample.Criteria criteria= example.createCriteria();
		criteria.andSpecIdEqualTo(tbSpecification.getId());
		specificationOptionMapper.deleteByExample(example);
		
		List<TbSpecificationOption> list= specification.getSpecificationOptionList();	
		for(TbSpecificationOption option:list){
			option.setSpecId(tbSpecification.getId());
			specificationOptionMapper.insert(option);
		}
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		Specification specification =new Specification();
		specification.setSpecification(specificationMapper.selectByPrimaryKey(id));
		
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.maomao.pojo.TbSpecificationOptionExample.Criteria criteria= example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		
		specification.setSpecificationOptionList(specificationOptionMapper.selectByExample(example));
		
		
		return specification;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
	
		for(Long id:ids){
			TbSpecificationOptionExample example =new TbSpecificationOptionExample();
			com.maomao.pojo.TbSpecificationOptionExample.Criteria criteria= example.createCriteria();
			criteria.andSpecIdNotEqualTo(id);
			specificationMapper.deleteByPrimaryKey(id);
			specificationOptionMapper.deleteByExample(example );
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public List<Map> selectOptionList() {
			// TODO Auto-generated method stub
			return specificationMapper.selectOptionList();
		}
	
}
