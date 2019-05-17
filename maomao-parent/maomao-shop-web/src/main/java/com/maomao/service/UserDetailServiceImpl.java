package com.maomao.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.maomao.pojo.TbSeller;
import com.maomao.sellergoods.service.SellerService;
/*
 * 认证类
 * */
public class UserDetailServiceImpl implements UserDetailsService {

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	private SellerService sellerService;
	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		//构建角色列表
		List<GrantedAuthority> grantAuths =new ArrayList();
		
		grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
	
		
		TbSeller seller= sellerService.findOne(username);
		if(seller!=null){
			if(seller.getStatus().equals("1")){
				return new User(username,seller.getPassword(),grantAuths );
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

}
