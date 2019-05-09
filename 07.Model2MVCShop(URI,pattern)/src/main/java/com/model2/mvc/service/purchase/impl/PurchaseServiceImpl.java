package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;


@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{
	
	// field
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao; 
	}
	
	// Constructor
	public PurchaseServiceImpl() {
		System.out.println(" PurchaseServiceImpl Constructor ");
	}
	
	
	///method
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.addPurchase(purchase);
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.getPurchase(tranNo);
		}
	
	public Purchase getPurchase2(int prodNo) throws Exception{
	return purchaseDao.getPurchase(prodNo);
	}

	
	public Map<String, Object> getPurchaseList(Search search,String userId) throws Exception {
		
		List<Purchase> list= purchaseDao.getPurchaseList(search, userId);
		int totalCount = purchaseDao.getTotalCount(userId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", totalCount);
		
		return map;
	}

	
	
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	
	}



} 
