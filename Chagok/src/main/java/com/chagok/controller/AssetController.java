package com.chagok.controller;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chagok.apiDomain.RequestTokenVO;
import com.chagok.apiDomain.ResponseTokenVO;
import com.chagok.apiDomain.UserInfoResponseVO;
import com.chagok.domain.AbookVO;
import com.chagok.domain.ReportVO;
import com.chagok.service.OpenBankingService;
import com.chagok.service.ReportService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/asset/*")
public class AssetController {
	
	private static final Logger mylog = LoggerFactory.getLogger(AssetController.class);
	
	@Inject
	private ReportService rptService;
	
	// http://localhost:8080/asset/myAsset
	///////////////////영민////////////////////
	@Inject
	private OpenBankingService openBankingService;
	
	@GetMapping("/myAsset")
	public String myAssetGET() {
		
		return "/asset/myAsset";
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public String getToken(RequestTokenVO requestTokenVO, Model model) throws Exception {
		//////////////// 사용자인증 API (3-legged) ////////////////
		
		ResponseTokenVO responseTokenVO = openBankingService.requestToken(requestTokenVO);
		
		// 정보를 들고 jsp 이동 (model 객체)
		model.addAttribute("responseTokenVO", responseTokenVO);
		
///		//////////////// 사용자 정보 조회 => DB(member 테이블)에 저장 ////////////////
		if (responseTokenVO != null) {
			UserInfoResponseVO userInfoResponseVO = openBankingService.getUserInfo(responseTokenVO);
		}
		
		
		
		return "/asset/apiTest";
	}
	
	
	///////////////////영민////////////////////
	
	///////////////////세영//////////////////////
	@GetMapping("/abookList")
	public String abookListGET() {
		return "/asset/abookList"; 
	}
	
	
	
	///////////////////세영////////////////////
	
	
	
	
	///////////////////MJ////////////////////
	// mno 세션으로 받아오기
	
//	http://localhost:8080/asset/rptTest?mno=1
	/*
	@GetMapping("/rptTest")
	public String rptTest(@RequestParam("mno") int mno, Model model) throws Exception {
		mylog.debug("mno : "+mno);
		// service에서 DB 가져오기
		List<AbookVO> abList = rptService.rptTest(mno);
		// model로 전달
		model.addAttribute("abList", abList);
		
		return "/asset/cateReport";
	}*/
	
//	http://localhost:8080/asset/ctRpt
//	http://localhost:8080/asset/ctRpt?mno=1
//	@ResponseBody
	@RequestMapping(value = "/ctRpt", method = RequestMethod.GET)
	public String cateReport(@RequestParam("mno") int mno, Model model) throws Exception {
//	public String cateReport() throws Exception {
		mylog.debug("mno : "+mno);
//	    Map<String, Object> map = new HashMap<String, Object>();
//		map.put("catecnt", abList);

	    // service에서 DB 가져오기
		List<ReportVO> cntcateList = rptService.getCateCnt(mno);
		mylog.debug("cntcateList : "+cntcateList.size());
//		mylog.debug("cntcateList : "+cntcateList.toString());
		Gson gson = new Gson();
		JsonArray jArr = new JsonArray();
//		
		// List -> JSON으로 가공하기
		// VO의 catecnt, catename 추출 -> 변수에 임시 저장 -> JSONArr에 저장
		Iterator<ReportVO> it = cntcateList.iterator();
		while(it.hasNext()) {
			ReportVO cntCate = it.next();
			int catecnt = cntCate.getCateCnt();
			String catename = cntCate.getCateName();
			
			JsonObject obj = new JsonObject();
			obj.addProperty("catecnt", catecnt);
			obj.addProperty("catename", catename);
			jArr.add(obj);
		}
//		
//		// model로 전달
		String cntcatejson = gson.toJson(jArr);
		mylog.debug("json : "+cntcatejson);
		model.addAttribute("cntcatejson", cntcatejson);
//		model.addAttribute("cntcateList", cntcateList);
		return "/asset/cateReport";
	}
	
	///////////////////MJ////////////////////
}
