package com.maomao.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.maomao.pojo.TbSpecification;
import com.maomao.pojo.TbSpecificationOption;

public class Specification implements Serializable{
	private TbSpecification specification;
	
	private List<TbSpecificationOption> specificationOptionList;

	public TbSpecification getSpecification() {
		return specification;
	}
	
	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}

	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}

	public void setSpecificationOptionList(
			List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}

	
	
	
}
