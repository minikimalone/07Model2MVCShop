package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


//==> ��ǰ���� Controller
@Controller
@RequestMapping("/product/*") 
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	
	private ProductService productService;
	@Qualifier("purchaseServiceImpl")
	@Autowired
	private PurchaseService purchaseService;
	
	
	//setter Method ���� ����
		
	
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	
	//@RequestMapping("/addProductView.do")
	@RequestMapping( value="addProduct", method=RequestMethod.GET )
	public String addProductView() throws Exception {

		System.out.println("/addProductView.do");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	@RequestMapping( value="addProduct", method=RequestMethod.POST )
	//@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("product") Product product, HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("/addProduct.do");
		//Business Logic
		

		productService.addProduct(product);
		

		
		return "forward:/product/addProduct.jsp";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping( value="getProduct", method=RequestMethod.GET )
	public String getProduct( @RequestParam("prodNo") int prodNo ,  @RequestParam("menu") String menu , Model model ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
	
		String page=null;
		
		// Model �� View ����
		model.addAttribute("product", product);
		

		if(menu.equals("manage")){
		page = "forward:/product/updateProductView.jsp";
		}else {
		
		
		page = "forward:/product/getProduct.jsp";
		
		
		}
	
		return page;
		}

	
	
	@RequestMapping( value="updateProductView", method=RequestMethod.GET )
	//@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	
	@RequestMapping( value="updateProduct", method=RequestMethod.POST )
	//@RequestMapping("/updateProduct.do")
	public String updateProduct( @ModelAttribute("product") Product product ) throws Exception{

		System.out.println("/updateProduct");
		//Business Logic
		
		productService.updateProduct(product);
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo()+"&menu=update";
	
	
		//return "redirect:/product/getProduct?prodNo="+product.getProdNo()+"&menu=update";
	}
	
	
	
	@RequestMapping( value="listProduct" )

	public String listProduct( @ModelAttribute("search") Search search ,@ModelAttribute("purchase") Purchase purchase, Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		String tranCode= purchase.getTranCode();
		
		System.out.println(resultPage);
		
		
		
		System.out.println(tranCode);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		
		
		return "forward:/product/listProduct.jsp";
	}
	
	
//		@RequestMapping("/product/updateTranCodeByProdAction")
//		//@RequestMapping("/updateTranCodeByProdAction.do")
//		public String updateTranCodeByProdAction( @RequestParam("prodNo") int prodNo, @RequestParam("tranCode") String tranCode, @ModelAttribute("product") Product product ) throws Exception{
//
//			System.out.println("/updateTranCodeByProdAction.do");
//			//Business Logic
//
//			PurchaseService purchaseService = new PurchaseServiceImpl();
//			Purchase purchase = purchaseService.getPurchase2(prodNo);
//			purchase.setTranCode(tranCode);
//			
//			purchaseService.updateTranCode(purchase);
//			
//			
//			
//			return "redirect:/product/listProduct?menu=manage";
//		}
		
		
		
	
		/////////////////////////////////////////////////////////////////////
		 @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
		    public Map fileUpload(HttpServletRequest req, HttpServletResponse rep) { 
		        //파일이 저장될 path 설정 
		        // String path = req.getSession().getServletContext().getRealPath("") + "\\resources";    // 웹프로젝트 경로 위치
		        String path = req.getSession().getServletContext().getRealPath("/resources/");
		        
		        System.out.println("path : " + path);
		        
		        Map returnObject = new HashMap(); 
		        try { 
		            // MultipartHttpServletRequest 생성 
		            MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req; 
		            Iterator iter = mhsr.getFileNames(); 
		            MultipartFile mfile = null; 
		            String fieldName = ""; 
		            List resultList = new ArrayList(); 
		            
		            // 디레토리가 없다면 생성 
		            File dir = new File(path); 
		            if (!dir.isDirectory()) { 
		                dir.mkdirs(); 
		            } 
		            
		            // 값이 나올때까지 
		            while (iter.hasNext()) { 
		                fieldName = (String) iter.next(); // 내용을 가져와서 
		                mfile = mhsr.getFile(fieldName); 
		                String origName; 
		                origName = new String(mfile.getOriginalFilename().getBytes("8859_1"), "UTF-8"); //한글꺠짐 방지 
		                
		                System.out.println("origName: " + origName);
		                // 파일명이 없다면 
		                if ("".equals(origName)) {
		                    continue; 
		                } 
		                
		                // 파일 명 변경(uuid로 암호화) 
//		                String ext = origName.substring(origName.lastIndexOf('.')); // 확장자 
//		                String saveFileName = getUuid() + ext;
		                String saveFileName = origName;
		                
		                System.out.println("saveFileName : " + saveFileName);
		                
		                // 설정한 path에 파일저장 
		                File serverFile = new File(path + File.separator + saveFileName);
		                mfile.transferTo(serverFile);
		                
		                Map file = new HashMap();
		                file.put("origName", origName); file.put("sfile", serverFile);
		                resultList.add(file);
		            }
		            
		            returnObject.put("files", resultList); 
		            returnObject.put("params", mhsr.getParameterMap()); 
		            } catch (UnsupportedEncodingException e) { 
		                // TODO Auto-generated catch block 
		                e.printStackTrace(); 
		            }catch (IllegalStateException e) { // TODO Auto-generated catch block 
		                e.printStackTrace();
		            } catch (IOException e) { // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		        
		            return null;
		    }
		    
		    //uuid생성
		    public static String getUuid() { 
		        return UUID.randomUUID().toString().replaceAll("-", "");
		    }
		


	}