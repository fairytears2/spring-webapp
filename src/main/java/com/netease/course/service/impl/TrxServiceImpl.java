package com.netease.course.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.course.bean.Product;
import com.netease.course.bean.User;
import com.netease.course.dao.ProductMapper;
import com.netease.course.dao.TrxMapper;
import com.netease.course.service.TrxService;

import java.util.List;

@Service
public class TrxServiceImpl implements TrxService {

	@Autowired
    private TrxMapper trxMapper;
	@Autowired
    private ProductMapper productMapper;

    //鑾峰彇宸茶喘涔扮殑鍟嗗搧鍒楄〃

    public List<Product> getBuyList(Integer userId) {
        List<Product> productList = trxMapper.getBuyList(userId);
        return productList;
    }

    //璐拱璐墿杞﹂噷鐨勫晢鍝�,(寰呰缃簨鍔″洖婊�)

    public boolean buy(User user, List<Product> productList, Long buyTime) {
        boolean isBuy = false;

        for (Product product: productList) {
            int buyNum = product.getBuyNum();
            product = productMapper.getProduct(product.getId());

            String buyPrice = product.getPrice();
            product.setBuyNum(buyNum);
            product.setBuyPrice(buyPrice);
            isBuy = trxMapper.buy(product, buyTime);
            if (!isBuy) {
                throw new RuntimeException();
            }
        }

        return isBuy;
    }


	public List<Product> getSettleList(Integer userId) {
		List<Product> productList = trxMapper.getSettleList(userId);
		return productList;
	}


	public boolean setSettle(User user, Product product) {
		boolean isSet = false;
		if(product !=null) {
		isSet = trxMapper.setSettleAccount(user.getId(), product);
		}
		return isSet;
	}



}
