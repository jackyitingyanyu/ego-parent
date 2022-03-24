package com.ego.cart.service.impl;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;

@Service
public class CartServiceImpl implements CartService{
	
	@Resource
	private JedisDao jedisDaoImpl;

	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	
	@Value("${passport.url}")
	private String passportUrl;
	
	@Value("${cart.key}")
	private String cartKey;
	
	@Override
	public void addCart(long id, int num,HttpServletRequest request) {
		TbItem item = tbItemDubboServiceImpl.selById(id);
		TbItemChild child=new TbItemChild();
		child.setId(item.getId());
		child.setTitle(item.getTitle());
		child.setImages(item.getImage()==null||"".equals(item.getImage())?new String[1]:item.getImage().split(","));
		child.setNum(num);
		child.setPrice(item.getPrice());
		
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser,EgoResult.class);
		jedisDaoImpl.set(cartKey+((LinkedHashMap)er.getData()).get("username"), JsonUtils.objectToJson(child));
		
	}
	
}
