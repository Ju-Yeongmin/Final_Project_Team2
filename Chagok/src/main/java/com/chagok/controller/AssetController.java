package com.chagok.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chagok.apiDomain.AccountHistoryRequestVO;
import com.chagok.apiDomain.AccountHistoryResponseVO;
import com.chagok.apiDomain.AccountHistoryVO;
import com.chagok.apiDomain.AccountVO;
import com.chagok.apiDomain.CardHistoryVO;
import com.chagok.apiDomain.CardInfoRequestVO;
import com.chagok.apiDomain.CardInfoResponseVO;
import com.chagok.apiDomain.CardInfoVO;
import com.chagok.apiDomain.CashVO;
import com.chagok.apiDomain.RequestTokenVO;
import com.chagok.apiDomain.ResponseTokenVO;
import com.chagok.apiDomain.UserInfoResponseVO;
import com.chagok.domain.AbookVO;
import com.chagok.domain.ChallengeVO;
import com.chagok.domain.JsonObj;
import com.chagok.domain.PropCardVO;
import com.chagok.domain.UserVO;
import com.chagok.service.AbookService;
import com.chagok.service.AccountService;
import com.chagok.service.OpenBankingService;
import com.chagok.service.ReportService;
import com.chagok.service.UserService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
@JsonAutoDetect 
@Controller
@RequestMapping("/asset/*")
public class AssetController {
	
	private static final Logger mylog = LoggerFactory.getLogger(AssetController.class);

	
	///////////////////??????////////////////////
	// http://localhost:8080/asset/myAsset
	@Inject
	private OpenBankingService openBankingService;
	
	@Inject
	private AccountService accountService; 
	
	@Inject
	private UserService userService;
	
	@GetMapping("/myAsset")
	public String myAssetGET(HttpSession session, Model model) throws Exception{
		if (session.getAttribute("mno") != null) {
			int mno = (int)session.getAttribute("mno");
			
			UserVO userVO = userService.getUser(mno);
			model.addAttribute("userVO", userVO);
			
			// ?????? ??????
			LocalDate now = LocalDate.now();
			String now_date = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
			model.addAttribute("now_date", now_date);
			
			// ?????? ????????? ??????
			List<AccountVO> accountList = accountService.getAccountInfo(mno);
			model.addAttribute("accountList", accountList);
			
			// ?????? ????????? ??????
			List<CardInfoVO> cardList = accountService.getCardInfo(userVO.getUser_seq_no());
			model.addAttribute("cardList", cardList);
			
			// ?????? ??????/?????? ??????
			List<List<CardHistoryVO>> cardHistoryList = accountService.getCardHistory(cardList);
			model.addAttribute("cardHistoryList", cardHistoryList);
			
			// ?????? ?????? ??????
			CashVO cashVO = accountService.getCashInfo(mno);
			if (cashVO != null) {
				cashVO.setCash_amt(cashVO.getCash_amt().replaceAll(",", ""));
			}
			
			model.addAttribute("cashVO", cashVO);
		}
		
		return "/asset/myAsset";
	}
	
	@GetMapping("/accountHistory")
	public String accountHistoryGET(HttpSession session, Model model, 
			@RequestParam("fintech_use_num") String fintech_use_num) throws Exception{
		
		if (session.getAttribute("mno") != null) {
			int mno = (int)session.getAttribute("mno");
			
			UserVO userVO = userService.getUser(mno);
			model.addAttribute("userVO", userVO);
			
			mylog.debug("@@@@@@@@@@@@@@@@@@@@@@" + fintech_use_num);
			
			List<AccountHistoryVO> accountHistoryList = accountService.getAccountHistory(fintech_use_num);
			model.addAttribute("accountHistoryList", accountHistoryList);
			
			mylog.debug(accountHistoryList+"");
			
		}
		
		return "/asset/accountHistory";
	}
	
	@GetMapping("/cardHistory")
	public String cardHistoryGET(HttpSession session, Model model, 
			@RequestParam("card_id") String card_id, @RequestParam("cardSum") String cardSum) throws Exception{
		
		if (session.getAttribute("mno") != null) {
			int mno = (int)session.getAttribute("mno");
			
			UserVO userVO = userService.getUser(mno);
			model.addAttribute("userVO", userVO);
			
			mylog.debug("@@@@@@@@@@@@@@@@@@@@@@" + card_id);
			
			List<CardHistoryVO> cardHistoryList = accountService.getCardHistory(card_id);
			model.addAttribute("cardHistoryList", cardHistoryList);
			model.addAttribute("cardSum", cardSum);
			
			mylog.debug(cardHistoryList+"");
			
			
		}
		
		return "/asset/cardHistory";
	}
	
	@GetMapping("/insertCash")
	public String insertCashGET() throws Exception{
		
		return "/asset/insertCash";
	}
	
	@GetMapping("/insertCashPro")
	public String insertCashProGET(CashVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		mylog.debug("???????????? ??????!!!!");
		
		int mno = (int)session.getAttribute("mno");
		vo.setMno(mno);
		
		mylog.debug(vo+"");

		accountService.insertCash(vo);

		rttr.addFlashAttribute("insertOK", "OK");
		
		return "redirect:/asset/insertCash";
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public String getToken(RequestTokenVO requestTokenVO, Model model, CardInfoRequestVO cardInfoRequestVO, 
			HttpSession session) throws Exception {
		//////////////// ??????????????? API (3-legged) ////////////////
		
		// ?????? ????????????
		LocalDate now = LocalDate.now();
		String from_date = now.minusMonths(6).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String to_date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		// ?????? ??????
		ResponseTokenVO responseTokenVO = openBankingService.requestToken(requestTokenVO);
		
		// ????????? ?????? jsp ?????? (model ??????)
		model.addAttribute("responseTokenVO", responseTokenVO);
			
		if (responseTokenVO != null) {
			int mno = (int)session.getAttribute("mno");
			
			// isChecked N => Y update
			userService.updateIsCheck(mno);
			
			// user_seq_no ??????
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("mno", mno);
			userMap.put("user_seq_no", responseTokenVO.getUser_seq_no());
			userService.updateSeqNo(userMap);
			
			//////////////// ????????? ??????, ???????????? ?????? => DB(user, account ?????????)??? ?????? ////////////////
			UserInfoResponseVO userInfoResponseVO = openBankingService.getUserInfo(responseTokenVO);
			for (int k = 0; k < userInfoResponseVO.getRes_list().size(); k++) {
				userInfoResponseVO.getRes_list().get(k).setMno(mno);
			}
			// ????????? ??????
			model.addAttribute("userInfoResponseVO", userInfoResponseVO);
			// ?????? ??????
			List<AccountVO> accountList = userInfoResponseVO.getRes_list();
			model.addAttribute("accountList", accountList);
			
			if (userInfoResponseVO.getRes_list() != null) {
				accountService.insertAccountInfo(userInfoResponseVO.getRes_list()); // ?????? ??????
			}
			
			mylog.debug("?????? ????????? : " + accountList);
			
			//////////////// ?????? ???????????? ?????? => DB(account_history ?????????)??? ?????? ////////////////
			List<AccountHistoryRequestVO> accountHistoryRequestList = new ArrayList<AccountHistoryRequestVO>();
			for (int i = 0; i < accountList.size(); i++) {
				AccountHistoryRequestVO accountHistoryRequestVO = new AccountHistoryRequestVO();
				
				accountHistoryRequestVO.setAccess_token(responseTokenVO.getAccess_token());
				accountHistoryRequestVO.setBank_tran_id("M202202513U"+(int)((Math.random()+1)*100000000));
				mylog.debug("@@@@@@@@@@@@@@"+i+accountHistoryRequestVO.getBank_tran_id());
				accountHistoryRequestVO.setFintech_use_num(accountList.get(i).getFintech_use_num());
				accountHistoryRequestVO.setInquiry_type("A");
				accountHistoryRequestVO.setInquiry_base("D");
				accountHistoryRequestVO.setFrom_date(from_date);
				accountHistoryRequestVO.setTo_date(to_date);
				accountHistoryRequestVO.setSort_order("D");
				
				accountHistoryRequestList.add(accountHistoryRequestVO);
			}
			
			List<AccountHistoryResponseVO> accountHistoryResponseList = openBankingService.getAccountHistory(accountHistoryRequestList);
			model.addAttribute("accountHistoryResponseList", accountHistoryResponseList);
			
			if (accountHistoryRequestList != null) {
				accountService.insertAccountHistory(accountHistoryResponseList); // ?????? ??????
			}
			
			//////////////// ???????????? ?????? => DB(card ?????????)??? ?????? ////////////////
			
			cardInfoRequestVO.setAccess_token(responseTokenVO.getAccess_token());
			cardInfoRequestVO.setBank_tran_id("M202202513U"+(int)((Math.random()+1)*100000000));
			cardInfoRequestVO.setUser_seq_no(responseTokenVO.getUser_seq_no());
			cardInfoRequestVO.setBank_code_std("399"); // fix, ??????????????? ????????????
			cardInfoRequestVO.setMember_bank_code("399"); // fix, ??????????????? ????????????
			CardInfoResponseVO cardInfoResponseVO = openBankingService.getCardInfo(cardInfoRequestVO);
			
			model.addAttribute("cardInfoResponseVO", cardInfoResponseVO);
			if (cardInfoResponseVO != null) {
				accountService.insertCardInfo(cardInfoResponseVO);
			}
			
			// ?????? ?????? ??????
			if (userInfoResponseVO.getRes_list() != null) {
				// mno??? ???????????? ???????????? ???????????? ???????????? ??????
				List<AccountHistoryVO> accountHistoryList = accountService.getBalanceAmt(mno);
				model.addAttribute("accountHistoryList", accountHistoryList);
				
				// ?????? ??????????????? ?????? ????????? ????????????
				List<AccountVO> updateBalanceList = new ArrayList<AccountVO>();
				for (int i = 0; i < accountHistoryList.size(); i++) {
					if (accountHistoryList.get(i) != null) {
						AccountVO accountVO = new AccountVO();
						accountVO.setFintech_use_num(accountHistoryList.get(i).getFintech_use_num());
						accountVO.setBalance_amt(accountHistoryList.get(i).getAfter_balance_amt());
						updateBalanceList.add(accountVO);
					}
				}
				accountService.updateBalanceAmt(updateBalanceList);
			}
		}
//		return "/asset/apiTest"; // ?????? ?????????
		return "redirect:/asset/myAsset";
	}
	
	@GetMapping("/callbackCard")
	public String registCard(RequestTokenVO requestTokenVO, Model model) throws Exception{
		
		model.addAttribute("requestTokenVO", requestTokenVO);
		
		return "/asset/cardTest";
	}
	
	
	
	
	
	@RequestMapping(value = "/callbackCenter", method = RequestMethod.GET)
	public String getTokenCenter(RequestTokenVO requestTokenVO, Model model) throws Exception{
		////////////////??????????????? API (2-legged) ////////////////
				
		ResponseTokenVO responseTokenVO = openBankingService.requestTokenCenter(requestTokenVO);
		
		// ????????? ?????? jsp ?????? (model ??????)
		model.addAttribute("responseTokenVO", responseTokenVO); 
		if (responseTokenVO != null) {
			//////////////// ????????? ?????? ?????? => DB(member ?????????)??? ?????? ////////////////
			UserInfoResponseVO userInfoResponseVO = openBankingService.getUserInfo(responseTokenVO);
			// ????????? ??????
			model.addAttribute("userInfoResponseVO", userInfoResponseVO);
			// ?????? ??????
			model.addAttribute("accountList", userInfoResponseVO.getRes_list());
			//////////////// ?????? ?????? ?????? => DB(member ?????????)??? ?????? ////////////////
			
		}
		return "/asset/apiTest";
	}

	
	
	
	
	
	///////////////////??????////////////////////

	
	
	///////////////////??????//////////////////////
	
	//????????? ?????? ??????
	@Inject
	private AbookService abService;
	
	
	// 0. abokkList????????? get?????? ?????? ============================================================
//	http://localhost:8080/asset/abookList
	@GetMapping("/abookList")
//	@ResponseBody
	public String abookList(HttpSession session,Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// 0. mno ???????????? ?????? 
		// ????????? ??????
		if(session.getAttribute("mno")==null) {
			return "/chagok/login";
		}
		int mno = (int)session.getAttribute("mno");
		UserVO userVO = userService.getUser(mno);
		
		mylog.debug("mno : "+mno);

		return "asset/abookList"; 
	}
	
	
	// 1. ?????? Data ????????? ???????????? ????????? ==========================================================
	@ResponseBody
	@RequestMapping(value = "/reqGrid", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonObj test (
			@RequestParam(value = "page", required=false) String page,//page : ????????? ???????????? ???????????????
			@RequestParam(value = "rows", required=false) String rows,//rows : ????????? ??? ????????? ?????? ???????????????
			@RequestParam(value  = "sidx", required=false) String sidx,//sidx : ???????????? ????????? ?????? ?????????
			@RequestParam(value = "sord", required=false) String sord,
			HttpSession session) throws Exception {//sord : ???????????? ?????? ????????????
	    	
		mylog.debug("json controller ?????? ??????");
		
		// ????????? ??????
		int mno = (int)session.getAttribute("mno");
		UserVO userVO = userService.getUser(mno);
		
		mylog.debug("mno@@@@@@@@:"+mno);
		
		// list: ????????? ????????? ??? ????????? ?????????(page,sidx,sord) ????????? 
		// list2: ?????? ?????????(VO) ?????????
		JsonObj obj = new JsonObj();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<?> list2 = abService.getAbookList(mno);
		mylog.debug("vo -> list "+list2);
		
		int int_page = Integer.parseInt(page);// 1 2 3
		int perPageNum = (int)Double.parseDouble(rows);
		
		// db?????? ????????? ???????????? ????????? 10????????? ???????????? ????????? ??????	
//		for(int i= (int_page-1)*perPageNum+1 ; i<(int_page*perPageNum) ; i++){
		for(int i=0; i<list2.size(); i++){
			AbookVO vo = (AbookVO) list2.get(i);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("abno", vo.getAbno());
			map.put("ab_inout", vo.getAb_inout());
			map.put("ab_date", vo.getAb_date());
			map.put("ab_content", vo.getAb_content());
			map.put("ab_amount", vo.getAb_amount());
			map.put("ab_method", vo.getAb_method());
			map.put("ctno", vo.getCtno());
			map.put("ct_top", vo.getCt_top());
			map.put("ct_bottom", vo.getCt_bottom());
			map.put("ab_memo", vo.getAb_memo());
			
			list.add(map);
//			mylog.debug("&&& for??? ?????? "+list);
	}
//		}
	    obj.setRows(list);  // list<map> -> obj
	    
	    //page : ?????? ?????????
	    obj.setPage(int_page);// ?????? ???????????? ??????????????? ????????? page??? ???????????????. 
		
	    //records : ???????????? ???????????? ????????? ??????
	    obj.setRecords(list.size());
		
	    //total : rows??? ?????? ??? ????????????
		// ??? ????????? ????????? ????????? ?????? / ??????????????? ????????? ?????? ?????? ???
		int totalPage = (int)Math.ceil(list.size()/Double.parseDouble(rows));
		obj.setTotal(totalPage); // ??? ????????? ??? (????????? ????????? ??????)
		
		mylog.debug("cont -> ????????????"+obj);
		
	    return obj;
	    
	} // ===========================================================================================================
	
	// ????????? -> DB(?????? ?????????)??? ???????????? ???????????? ??????
	@RequestMapping("/saveGrid")
	 @ResponseBody
	 public Object saveList(HttpServletRequest request,@RequestBody List<Map<String, Object>> list) throws Exception {
		
		mylog.debug("##????????? ??? ?????????"+list);
		 Map <String, String> resultMap =  new HashMap<String, String>();
		
		  String result = "ok";
		  String resultMsg = "";
		  
	  try {
		  
	   for(int i = 0; i < list.size(); i++) {
		AbookVO vo = new AbookVO();
//	    vo.setMno(Integer.parseInt(tList.get("mno").toString( )));
	    vo.setAbno(Integer.parseInt(list.get(i).get("abno").toString()));
	    vo.setAb_inout(Integer.parseInt(list.get(i).get("ab_inout").toString()));
	    vo.setAb_amount(Integer.parseInt(list.get(i).get("ab_amount").toString()));
	    vo.setCtno(Integer.parseInt(list.get(i).get("ctno").toString()));
	    
	    vo.setAb_date(list.get(i).get("ab_date").toString());
	    vo.setAb_content(list.get(i).get("ab_content").toString());
	    vo.setAb_memo(list.get(i).get("ab_memo").toString());
	    vo.setAb_method(list.get(i).get("ab_method").toString());
	    vo.setCt_top(list.get(i).get("ct_top").toString());
	    vo.setCt_bottom(list.get(i).get("ct_bottom").toString());

	    mylog.debug("!!!!!!!!!!!!!!!!!"+vo);
	    abService.setAbookList(vo);
//	    mylog.debug(vo+"%%%%%%%%%%cont");
	   }
	    result = "success";
	    resultMsg = "??????" ;
	     
	   }catch (Exception e) { 
	    result = "failure";
	    resultMsg = "??????" ;
	   }
	  
	  resultMap.put("result", result);
	  resultMap.put("resultMsg", resultMsg);
	  mylog.debug("#######resultMap"+resultMap);
	  
	  return resultMap;
	}
	
	// ????????? -> DB(?????? ?????????)??? ???????????? ???????????? ??????
	@RequestMapping(value = "/saveRows", method = {RequestMethod.GET})
	@ResponseBody
	 public Object saveRows(HttpServletRequest request, @RequestParam Map<String,Object> param, HttpSession session) throws Exception {
		
		int mno = (int)session.getAttribute("mno");
		UserVO userVO = userService.getUser(mno);
		
//		mylog.debug("##????????? ??? ?????????"+inlist);
		mylog.debug("##param"+param);
		mylog.debug(request.getParameter("abno")+"?????? ??????");
		
		 Map <String, String> resultMap =  new HashMap<String, String>();
		
		  String result = "ok";
		  String resultMsg = "";
		  
	  try {
		  
//	   for(int i = 0; i < inlist.size(); i++) {
//		AbookVO vo = new AbookVO();
//	    vo.setMno(mno);
//	    vo.setAbno(Integer.parseInt(inlist.get(i).get("abno").toString()));
//	    vo.setAb_inout(Integer.parseInt(inlist.get(i).get("ab_inout").toString()));
//	    vo.setAb_amount(Integer.parseInt(inlist.get(i).get("ab_amount").toString()));
//	    vo.setCtno(Integer.parseInt(inlist.get(i).get("ctno").toString()));
//	    
//	    vo.setAb_date(inlist.get(i).get("ab_date").toString());
//	    vo.setAb_content(inlist.get(i).get("ab_content").toString());
//	    vo.setAb_memo(inlist.get(i).get("ab_memo").toString());
//	    vo.setAb_method(inlist.get(i).get("ab_method").toString());
//	    vo.setCt_top(inlist.get(i).get("ct_top").toString());
//	    vo.setCt_bottom(inlist.get(i).get("ct_bottom").toString());
//	   
//	    mylog.debug("insert -> vo ??????"+vo);
//	    abService.setAbookList(vo);
//	    mylog.debug(vo+"%%%%%%%%%%cont");
//	   }
	    result = "success";
	    resultMsg = "??????" ;
	     
	   }catch (Exception e) { 
	    result = "failure";
	    resultMsg = "??????" ;
	   }
	  
	  resultMap.put("result", result);
	  resultMap.put("resultMsg", resultMsg);
	  mylog.debug("#######resultMap"+resultMap);
	  
	  return resultMap;
	}
	
	// ===============================================================
	@ResponseBody
	@RequestMapping(value = "/cateSelect", method = {RequestMethod.GET,RequestMethod.POST})
	public JSONArray cateSelect (@RequestParam(value = "cate", required=false) String test,		
			HttpSession session) throws Exception {
		mylog.debug("%%json controller cate"+test);
		
		// ????????? ??????
		int mno = (int)session.getAttribute("mno");
		UserVO userVO = userService.getUser(mno);
		mylog.debug("mno@@@@@@@@:"+mno);
		
//		JsonObj obj = new JsonObj();
//		int int_page = Integer.parseInt(page);// 1 2 3
//		int perPageNum = (int)Double.parseDouble(rows);
		
		
		List<Map<String, Object>> ctList = abService.cateList();
		mylog.debug("****vo -> list "+ctList);
		
//	    obj.setRows(ctList);  // list<map> -> obj
	    	
//	    Map<String, Object> map = new HashMap<String, Object>();
	    
//	    String st = rptService.listMapToJson(ctList);
//	    map.put("st",st);
	    

		JSONArray jArr = new JSONArray();
		for(Map<String, Object> map : ctList) {
			JSONObject jsonobj2 = new JSONObject();
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				jsonobj2.put(key, value);
//				mylog.debug("+++"+jsonobj2);
			    
			}
			jArr.add(jsonobj2);
//			jArr.add(jsonobj);
		}

		return jArr;

	}
	
	// ===========================================================
	@RequestMapping("/catebottom")
	 @ResponseBody
	 public JSONArray ctbottomList(HttpServletRequest request,@RequestParam("ct_top") String ct_top) throws Exception {
	
		mylog.debug("%%value"+ct_top);

		
		List<Map<String, Object>> ctbottomList = abService.ctbottomList();
		mylog.debug("****vo -> list "+ctbottomList);
		
		JSONArray jArrB = new JSONArray();
		for(Map<String, Object> map : ctbottomList) {
			JSONObject jsonobjb = new JSONObject();
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				jsonobjb.put(key, value);
//				if(ct_top == "??????") {
//			}
//				mylog.debug("&&&"+jsonobjb);
			}
			jArrB.add(jsonobjb);
		
			
		}
		return jArrB;

	}
	

	
	// ===============================================================
	
	@GetMapping("/delGrid")
	 @ResponseBody
	 public Object delList(HttpServletRequest request,@RequestParam("test") String obj) throws Exception {
		mylog.debug("#??????????????? ????????? abno"+obj);
//		 Map <String, String> resultMap =  new HashMap<String, String>();
		List<AbookVO> delRow = new ArrayList<AbookVO>();
		
//		mylog.debug("0?????? ?????????@@@"+delRow[0]);
		  String result = "ok";
		  String resultMsg = "";
		  
			 Map <String, String> resultMap =  new HashMap<String, String>();

	  try {
		  
//	   for(int i = 0; i < list.size(); i++) {
		AbookVO vo = new AbookVO();
//	    vo.setMno(Integer.parseInt(tList.get("mno").toString( )));
//	    vo.setAbno(Integer.parseInt(list.get(i).get("abno").toString()));
//	    vo.setAb_inout(Integer.parseInt(list.get(i).get("ab_inout").toString()));
//	    vo.setAb_amount(Integer.parseInt(list.get(i).get("ab_amount").toString()));
//	    vo.setCtno(Integer.parseInt(list.get(i).get("ctno").toString()));
//	    
//	    vo.setAb_date(list.get(i).get("ab_date").toString());
//	    vo.setAb_content(list.get(i).get("ab_content").toString());
//	    vo.setAb_memo(list.get(i).get("ab_memo").toString());
//	    vo.setAb_method(list.get(i).get("ab_method").toString());
//	    vo.setCt_top(list.get(i).get("ct_top").toString());
//	    vo.setCt_bottom(list.get(i).get("ct_bottom").toString());
//	   
	    mylog.debug("!!!!!!!!!!!!!!!!!"+vo);
//	    abService.setAbookList(vo);
//	    mylog.debug(vo+"%%%%%%%%%%cont");
//	   }
	    result = "success";
	    resultMsg = "??????" ;
	     
	   }catch (Exception e) { 
	    result = "failure";
	    resultMsg = "??????" ;
	   }
	  
	  resultMap.put("result", result);
	  resultMap.put("resultMsg", resultMsg);
	  mylog.debug("##??????resultMap"+resultMap);
	  
	  return resultMap;
	}
	
	

	// =====================================????????? ??????===============================================================

	
//	@RequestMapping(value="/getGrid", method = RequestMethod.POST, produces="application/json;charset=utf-8")
//	@ResponseBody
//		public void getGrid (@RequestParam Map<String,Object> rows, RedirectAttributes rttr) throws Exception {
//			
//			mylog.debug("getGrid@@@@@"+rows);	        
//		 
//		 ObjectMapper mapper = new ObjectMapper();
////		 AbookVO vo = mapper.readValue(param, AbookVO.class);
//		 AbookVO vo = mapper.convertValue(rows, AbookVO.class);
//		 
//		 // ????????? - DAO : ?????? ?????? ????????? ??????
////		 Integer result = abService.updateAbook(vo);
////		 
////			if(result > 0) {
////				// "????????????" - ?????? ?????? 
////					rttr.addFlashAttribute("result", "modOK");
////				}
////				// ????????? ??????(/board/list) 
//				
//			mylog.debug("?????? ?????? ??????!!");
//		 
//	}
	
	// ===================================================================================
	
	///////////////////MJ////////////////////
	
	@Inject
	private ReportService rptService;
	
//	http://localhost:8080/assetmain
//	http://localhost:8080/asset/dtRpt
	@GetMapping(value = "/dtRpt")
	public String dateReport(HttpSession session, Model model) throws Exception {
		// ????????? ??????
		if(session.getAttribute("mno")==null) {
			return "redirect:/login?pageInfo=asset/dtRpt";
		}
		int mno = (int)session.getAttribute("mno");
		String nick = userService.getUser(mno).getNick();
		
		/////////////// 1. service?????? DB ???????????? ///////////////
		int mm = 0;
		int mm2 = 1;
		// 1. ????????? ??? ??????
		Integer dtSum1 = rptService.dtSum(mno, mm);
		mylog.debug("dtSum1 : "+dtSum1);
		
		// 2. ????????? ??? ??????
		Integer dtSum2 = rptService.dtSum(mno, mm2);
		mylog.debug("dtSum2 : "+dtSum2);
		
		// 3. ????????? ?????? ??????
		Integer dtAvg1 = rptService.dtAvg(mno, mm);
		mylog.debug("dtAvg1 : "+dtAvg1);
		
		// 4. ????????? ?????? ??????
		Integer dtAvg2 = rptService.dtAvg(mno, mm2);
		mylog.debug("dtAvg2 : "+dtAvg2);
		
		// ?????? 3?????? ?????? ??????
		Integer dtAvg3 = rptService.dtAvg3(mno);
		mylog.debug("dtAvg3 : "+dtAvg3);
		
		// 5. ????????? ?????? ??????
		Integer expSum = rptService.expSum(mno, mm);
		mylog.debug("expSum : "+expSum);
		
		// 6. ????????? ??? ??????
		Integer dtSumIn = rptService.dtSumIn(mno, mm);
		mylog.debug("dtSumIn : "+dtSumIn);
		
		// 7. ????????? ????????? ??????
		Integer noOut = rptService.noOut(mno, mm);
		mylog.debug("noOut : "+noOut);
		
		// 8. ????????? ?????? ??????(?????? ??????)
		Integer outCnt = rptService.outCnt(mno, mm);
		mylog.debug("outCnt : "+outCnt);
		
		// 9. ????????? ?????? ??????
		List<Map<String, Object>> outCum = rptService.outCum(mno, mm);
		mylog.debug("outCum : "+outCum.size());
		
		// 10. ?????? ??????
		List<Map<String, Object>> day = rptService.day(mno, mm);
		mylog.debug("day : "+day.size());
		
		// 11. ?????? ??????
		List<Map<String, Object>> week = rptService.week(mno);
		mylog.debug("week : "+week.size());
		
		// 12. ?????? ??????
		List<Map<String, Object>> month = rptService.month(mno);
		mylog.debug("month : "+month.size());
		
		// 13. ????????? TOP 4
		List<Map<String, Object>> amtTop = rptService.amtTop(mno, mm);
		mylog.debug("amtTop : "+amtTop.size());
		
		// 14. ???????????? TOP 4
		List<Map<String, Object>> cntTop = rptService.cntTop(mno, mm);
		mylog.debug("cntTop : "+cntTop.size());		
		
		/////////////// 2. List<Map> -> JsonArray ///////////////
		String outCumjson = rptService.listMapToJson(outCum);
		String dayjson = rptService.listMapToJson(day);
		String weekjson = rptService.listMapToJson(week);
		String monthjson = rptService.listMapToJson(month);
		String amtTopjson = rptService.listMapToJson(amtTop);
		String cntTopjson = rptService.listMapToJson(cntTop);
		
		/////////////// 3. model??? ?????? ///////////////
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dtSum1", dtSum1);
		map.put("dtSum2", dtSum2);
		map.put("dtAvg1", dtAvg1);
		map.put("dtAvg2", dtAvg2);
		map.put("dtAvg3", dtAvg3);
		map.put("expSum", expSum);
		map.put("dtSumIn", dtSumIn);
		map.put("noOut", noOut);
		map.put("outCnt", outCnt);
		map.put("outCumjson", outCumjson);
		map.put("dayjson", dayjson);
		map.put("weekjson", weekjson);
		map.put("monthjson", monthjson);
		map.put("amtTopjson", amtTopjson);
		map.put("cntTopjson", cntTopjson);
		model.addAttribute("map", map);
		model.addAttribute("nick", nick);
		
		return "/asset/dateReport";
	}	
	
	
//	http://localhost:8080/asset/ctRpt
	@GetMapping(value = "/ctRpt")
	public String cateReport(HttpSession session, Model model) throws Exception {
		// ????????? ??????
		int mno = (int)session.getAttribute("mno");
		String nick = userService.getUser(mno).getNick();
		if(mno==0) {
			return "/chagok/login";
		}
		mylog.debug("mno : "+mno);

		/////////////// 1. service?????? DB ???????????? ///////////////
		int mm = 0;
		// 1. ?????? ?????? ????????????
		List<Map<String, Object>> cateCntList = rptService.cateCnt(mno, mm);
		mylog.debug("cateCntList : "+cateCntList.size());
//		
		// 2. ?????? ?????? ????????????
		List<Map<String, Object>> cateSumList = rptService.cateSum(mno, mm);
		mylog.debug("cateSumList : "+cateSumList.size());
		
		// 3. ????????? ??????
		List<ChallengeVO> chRandList = rptService.chRand(mno, mm);
		mylog.debug("chRandList : "+chRandList.size());
		
		// 4. ?????? ??????
		List<PropCardVO> cardRandList = rptService.cardRand(mno, mm);
		mylog.debug("cardRandList : "+cardRandList.size());
		
		/////////////// 2. List<Map> -> JsonArray ///////////////
		String cateCntjson = rptService.listMapToJson(cateCntList);
		String cateSumjson = rptService.listMapToJson(cateSumList);
		
		/////////////// 3. model??? ?????? ///////////////
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cateCntjson", cateCntjson);
		map.put("cateSumjson", cateSumjson);
		map.put("chRandList", chRandList);
		map.put("cardRandList", cardRandList);
		model.addAttribute("map", map);
		model.addAttribute("nick", nick);
		return "/asset/cateReport";
	}
	
	
//	http://localhost:8080/asset/budget
//	http://localhost:8080/asset/budget?mm=0
	@GetMapping(value = "/budget")
	public String budgetGET(@RequestParam("mm") int mm, HttpSession session, Model model, RedirectAttributes rttr) throws Exception {	
		int mno = (int)session.getAttribute("mno");

		List<String> ctTopList = abService.getctTop();
		String pMonth = abService.getPMonth(mm);
		Integer dtAvg3 = rptService.dtAvg3(mno);
		
		model.addAttribute("dtAvg3", dtAvg3);
		model.addAttribute("ctTopList", ctTopList);
		model.addAttribute("pMonth", pMonth);
		model.addAttribute("mm", mm);
		
		// chkBud
		int chkBud = abService.chkBud(mno, pMonth);
		if(chkBud==0) {
			mylog.debug(pMonth+"__?????? ??????");
			return "/asset/budget";
		} else {
			mylog.debug(pMonth+"__?????? ??????");
			return "redirect:/asset/budRpt?mm="+mm+"";
		}
	}
	
	@PostMapping(value = "/budget")
	public String budgetPOST(@RequestParam("mm") int mm, @RequestParam Map map, HttpSession session) throws Exception {
		int mno = (int)session.getAttribute("mno");
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		for(int i=1;i<map.size();i++) {
			Map<String, Object> tmpmap = new HashMap<String, Object>();
			if(map.get("ctno"+i)!=null){
				tmpmap.put("mno", mno);
				tmpmap.put("p_month", map.get("pMonth"));
				tmpmap.put("ctno", map.get("ctno"+i));
				tmpmap.put("p_amount", map.get("p_amount"+i));
				dataList.add(tmpmap);
			}
		}
		Map<String, Object> insertMap = new HashMap<String, Object>();
		insertMap.put("insertList", dataList);	// key???=collection??? value???
		abService.setBud(insertMap);
		
		return "redirect:/asset/budRpt?mm="+mm+"";
	}

	// ?????? ??????
	@ResponseBody
	@GetMapping(value = "/getBud")
	public List<Map<String, Object>> getBud(@RequestParam("mm") int mm, HttpSession session) throws Exception {
		int mno = (int)session.getAttribute("mno");
		String pMonth = abService.getPMonth(mm);
		
		// ?????? ??????
		List<Map<String, Object>> budList = abService.getBud(mno, pMonth);
		mylog.debug("budList"+budList.toString());
		mylog.debug(pMonth+"__?????? ?????? ??????");
		return budList;
	}

//	http://localhost:8080/asset/updBud?mm=0
	@GetMapping(value="/updBud")
	public String updBudGET(@RequestParam("mm") int mm, HttpSession session, Model model) throws Exception {
		mylog.debug("updBudGET");
		int mno = (int)session.getAttribute("mno");

		List<String> ctTopList = abService.getctTop();
		String pMonth = abService.getPMonth(mm);
		
		model.addAttribute("ctTopList", ctTopList);
		model.addAttribute("pMonth", pMonth);
		model.addAttribute("mm", mm);
		return "/asset/budUpdate";
	}
	
	@PostMapping(value = "/updBud")
	public String updBudPOST(@RequestParam("mm") int mm, @RequestParam Map map, HttpSession session) throws Exception {
		int mno = (int)session.getAttribute("mno");

		// map : form data, tmpmap : ?????????
		List<Map<String, Object>> updateList = new ArrayList<Map<String,Object>>();
		for(int i=1;i<map.size();i++) {
			Map<String, Object> tmpmap = new HashMap<String, Object>();
			if(map.get("ctno"+i)!=null){
				tmpmap.put("pno", map.get("pno"+i));
				tmpmap.put("p_amount", map.get("p_amount"+i));
				updateList.add(tmpmap);
			}
		}
		abService.updBud(updateList);
		
		return "redirect:/asset/budget?mm="+mm+"";
	}

	@GetMapping(value = "/delBud")
	public String delBud(@RequestParam("mm") int mm, HttpSession session) throws Exception {
		int mno = (int)session.getAttribute("mno");
		String pMonth = abService.getPMonth(mm);
		abService.delBud(mno, pMonth);
		
		return "redirect:/asset/budget?mm="+mm+"";
	}
	
//	http://localhost:8080/asset/budRpt?mm=0
	@GetMapping(value="/budRpt")
	public String budRpt(@RequestParam("mm") int mm, HttpSession session, Model model) throws Exception {
		// ????????? ??????
		if(session.getAttribute("mno")==null) {
			return "/chagok/login";
		}
		int mno = (int)session.getAttribute("mno");
		String nick = userService.getUser(mno).getNick();
		String pMonth = abService.getPMonth(mm);
		
		/////////////// 1. service?????? DB ???????????? ///////////////
		// 1. ?????? ??? ??????
		Integer totalBud = abService.totalBud(mno, pMonth);
		
		// 2. ?????? ??? ??????
		Integer dtSum = rptService.dtSum(mno, mm);
		
		// 3. ?????? ??? ?????? ??????
		Integer expSum = rptService.expSum(mno, mm);
		
		// 4. ?????? ??????
		List<Map<String, Object>> day = rptService.day(mno, mm);
		String dayjson = rptService.listMapToJson(day);
		
		/////////////// 2. model??? ?????? ///////////////
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalBud", totalBud);
		map.put("dtSum", dtSum);
		map.put("expSum", expSum);
		map.put("dayjson", dayjson);
		model.addAttribute("nick", nick);		
		model.addAttribute("pMonth", pMonth);
		model.addAttribute("map", map);

		return "/asset/budReport";
	}

//	http://localhost:8080/asset/abookCal
	@GetMapping(value="/abookCal")
	public String abookCal() throws Exception {
		
		return "/asset/calendar";
		
	}
	
	@ResponseBody
	@GetMapping(value="/cal")
	public List<Map<String, Object>> cal(@RequestParam int mm, @RequestParam int inout, HttpSession session) throws Exception {
		int mno = (int)session.getAttribute("mno");
		mylog.debug("mm : "+mm);
		mylog.debug("inout : "+inout);
		
		List<Map<String, Object>> cal = abService.calInout(mno, mm, inout);
		JSONArray jArr = new JSONArray();
		for(Map<String, Object> map : cal) {
			JSONObject jObj = new JSONObject();
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				String key = "";
				String value = String.valueOf(entry.getValue());
				if(entry.getKey().equals("date")) {
					key = "start";
				} else if (entry.getKey().equals("sum")) {
					key = "title";
				} else {
					key = entry.getKey();
				}
				jObj.put(key, value);
			}
			jArr.add(jObj);
		}
//		mylog.debug("jArr"+jArr.toString());
		return jArr;
	}
	
	///////////////////MJ////////////////////
}