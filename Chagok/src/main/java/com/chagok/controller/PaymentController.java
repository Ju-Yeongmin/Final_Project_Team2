package com.chagok.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Controller
public class PaymentController {

	private static final Logger mylog = LoggerFactory.getLogger(PaymentController.class);

	// 결제하기
//	// http://localhost:8080/pay
//	@GetMapping(value="/pay")
//	public String payGET() {
//		
//		return "/challenge/pay";
//	}
	
	
	private IamportClient api;
	// 결제정보 확인(검증)
	
	@ResponseBody
	@RequestMapping(value="/payCallback", method=RequestMethod.POST)
	public IamportResponse<Payment> paymentByImpUid(
			Model model
			, Locale locale
			, HttpSession session
			, @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException
	{	
			return api.paymentByImpUid(imp_uid);
	}

	// 결제페이지 - GET
	// http://localhost:8080/payment
	@GetMapping(value="/payment")
	public String paymentGET() throws Exception{
		mylog.debug(" /payment 호출 -> 페이지 이동 ");
		
		return "/payment/payment";
	}
	
	// 환불페이지 - GET
	// http://localhost:8080/refund
	@GetMapping(value="/refund")
	public String refundGET() throws Exception{
		mylog.debug(" /refund 호출 -> 페이지 이동 ");
		
		return "/payment/refund";
	}

	   
   @PostMapping(value = "/refund")
   public String refundPointPOST(Integer payno) {
	   
	   return null;
   }
	   
}