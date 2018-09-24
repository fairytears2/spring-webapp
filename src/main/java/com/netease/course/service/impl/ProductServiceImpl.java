package com.netease.course.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.course.bean.Product;
import com.netease.course.bean.User;
import com.netease.course.dao.ProductMapper;
import com.netease.course.service.ProductService;
import com.netease.course.utils.ConvertBlobTypeHandler;
import com.netease.course.utils.ConvertPriceUnitUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductMapper productMapper;

    //鑾峰彇鍟嗗搧鍒楄〃
    public List<Product> getProductList(User user, String type) {

        List<Product> productList = productMapper.getProductList();
        System.out.println("鑾峰彇鐗╁搧娓呭崟瀹炵幇");
        if (user != null) {//濡傛灉鏄敤鎴风櫥褰曠姸鎬�
            Iterator<Product> iterator = productList.iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();
                //鏁版嵁搴撶敤鍒嗕负鍗曚綅瀛樺偍浠锋牸锛岃浆鎴愪互鍏冧负鍗曚綅
                product.setPrice(ConvertPriceUnitUtil
                        .convertF2Y(product.getPrice()));
                if (product.getTrxCount() > 0) {
                    product.setSell(true);
                    product.setBuy(true);
                }

                if ("1".equals(type)) {//濡傛灉瑕佹眰鍙樉绀烘湭璐拱鍐呭
                    if (product.getIsBuy()) {
                        iterator.remove();
                    }
                }
            }
        }
        return productList;
    }

    //鑾峰彇鏌愪釜鍟嗗搧
    public Product getProduct(User user, Integer productId) {

        Product product = productMapper.getProduct(productId);

        if (product != null) {//濡傛灉瀛樺湪瀵瑰簲鐨勫晢鍝�
            //鏁版嵁搴撶敤鍒嗕负鍗曚綅瀛樺偍浠锋牸锛岃浆鎴愪互鍏冧负鍗曚綅
            product.setPrice(ConvertPriceUnitUtil
                    .convertF2Y(product.getPrice()));
            if (user != null) {//濡傛灉鐢ㄦ埛宸茬櫥褰�
                if (product.getTrxCount() > 0) {
                    product.setBuy(true);
                }
            }
        }
        return product;
    }

    //鍙戝竷鍟嗗搧
    public void publicProduct(Product product) {
        //灏嗕环鏍艰浆涓哄垎涓哄崟浣�
        product.setPrice(ConvertPriceUnitUtil.convertY2F(product.getPrice()));
        
        //鍓嶇娌℃湁瀵瑰簲搴撳瓨鐨勫睍绀猴紝涓轰簡鐪佷簨鍚庣鑷浼犺緭,搴撳瓨鍊肩粺涓�涓�100
        product.setNum(100);
        productMapper.publicProduct(product);
        System.out.println("鍙戝竷鏂规硶瀹炵幇");
    }

    //鏌ヨ鍟嗗搧鎬绘暟
    public int getCount() {
        return productMapper.getCount();
    }

    //淇敼鍟嗗搧
    public boolean updateProduct(Product product) {
        //灏嗕环鏍艰浆涓哄垎涓哄崟浣�
    	System.out.println("淇敼鏂规硶瀹炵幇");
        product.setPrice(ConvertPriceUnitUtil
                .convertY2F(product.getPrice()));
        return productMapper.updateProduct(product);
        
    }

    //鍒犻櫎鍟嗗搧
    public boolean deleteProdct(Integer id) {
    	System.out.println("鍒犻櫎鏂规硶瀹炵幇");
        return productMapper.deleteProduct(id);
    }

}
