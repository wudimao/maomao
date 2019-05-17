package com.maomao.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.maomao.pojo.TbGoods;
import com.maomao.pojo.TbGoodsDesc;
import com.maomao.pojo.TbItem;

public class Goods implements Serializable{
	private TbGoods goods;
	private TbGoodsDesc goodsDesc;
	private List<TbItem> item ;
	public TbGoods getGoods() {
		return goods;
	}
	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}
	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<TbItem> getItem() {
		return item;
	}
	public void setItem(List<TbItem> item) {
		this.item = item;
	}
	
}
