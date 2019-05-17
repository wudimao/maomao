package com.maomao.sellergoods.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.maomao.mapper.TbBrandMapper;
import com.maomao.mapper.TbGoodsDescMapper;
import com.maomao.mapper.TbGoodsMapper;
import com.maomao.mapper.TbItemCatMapper;
import com.maomao.mapper.TbItemMapper;
import com.maomao.mapper.TbSellerMapper;
import com.maomao.pojo.TbBrand;
import com.maomao.pojo.TbGoods;
import com.maomao.pojo.TbGoodsDesc;
import com.maomao.pojo.TbGoodsExample;
import com.maomao.pojo.TbGoodsExample.Criteria;
import com.maomao.pojo.TbItem;
import com.maomao.pojo.TbItemCat;
import com.maomao.pojo.TbItemExample;
import com.maomao.pojo.TbSeller;
import com.maomao.pojogroup.Goods;
import com.maomao.sellergoods.service.GoodsService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	@Autowired
	TbItemMapper itemMapper;
	@Autowired
	TbBrandMapper brandMapper;
	@Autowired
	TbItemCatMapper itemCatMapper;
	@Autowired
	TbSellerMapper sellerMapper;
	
	@Override
	public void add(Goods goods) {
		
		goods.getGoods().setAuditStatus("0");
		goodsMapper.insert(goods.getGoods());
		
		goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
		goodsDescMapper.insert(goods.getGoodsDesc());
		
		
		saveItemList(goods);
		
	}
	
	private void saveItemList(Goods goods){
		if("1".equals(goods.getGoods().getIsEnableSpec())){
			for(TbItem item:goods.getItem()){
				System.out.println(1);
				String title = goods.getGoods().getGoodsName();
				Map <String,Object> map=JSON.parseObject(item.getSpec());
				for(String key:map.keySet()){
					title+=" "+map.get(key);
				}
				item.setTitle(title);
				item.setGoodsId(goods.getGoods().getId());//商品SPU编号
				item.setSellerId(goods.getGoods().getSellerId());//商家编号
				setItemValues(item,goods);
				itemMapper.insert(item);
				
				
			}
		}else{
			
			TbItem item=new TbItem();
			item.setTitle(goods.getGoods().getGoodsName());
			item.setPrice(goods.getGoods().getPrice());
			item.setNum(99999);
			item.setStatus("1");
			item.setIsDefault("1");
			setItemValues(item,goods);
			itemMapper.insert(item);
		}
		
	}

	private void setItemValues(TbItem item,Goods goods ){
		item.setCategoryid(goods.getGoods().getCategory3Id());//商品分类编号（3级）
		item.setCreateTime(new Date());//创建日期
		item.setUpdateTime(new Date());//修改日期 
		TbBrand brand=brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
		item.setBrand(brand.getName());
		TbItemCat itemCat=itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
		item.setCategory(itemCat.getName());
		TbSeller tbSeller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
		item.setSeller(tbSeller.getNickName());
		
		List<Map> list =JSON.parseArray(goods.getGoodsDesc().getItemImages(),Map.class);
		if(list.size()>0){
			item.setImage((String)list.get(0).get("url"));
		}
	}
	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods){
		
		goodsMapper.updateByPrimaryKey(goods.getGoods());
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
		//先删除
		TbItemExample example =new TbItemExample();
		com.maomao.pojo.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(goods.getGoods().getId());
		itemMapper.deleteByExample(example );
		//保存
		saveItemList(goods);
		
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
		Goods goods=new Goods();
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		goods.setGoods(tbGoods);
		TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		goods.setGoodsDesc(tbGoodsDesc);
		
		
		
		TbItemExample example = new TbItemExample();
		com.maomao.pojo.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		goods.setItem(list);
		return goods;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(goods);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteIsNull();
		if(goods!=null){			
				if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public void updateStatus(Long[] ids, String status) {
			// TODO Auto-generated method stub
			for(Long id:ids){
				TbGoods goods = goodsMapper.selectByPrimaryKey(id);
				goods.setAuditStatus(status);
				goodsMapper.updateByPrimaryKey(goods);
			}
		}
	
}
