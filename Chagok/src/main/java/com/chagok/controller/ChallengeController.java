package com.chagok.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chagok.domain.AbookVO;
import com.chagok.domain.BoardVO;
import com.chagok.domain.BusinessAccountVO;
import com.chagok.domain.CategoryVO;
import com.chagok.domain.ChallengeVO;
import com.chagok.domain.Criteria;
import com.chagok.domain.FeedDTO;
import com.chagok.domain.MessageVO;
import com.chagok.domain.MinusVO;
import com.chagok.domain.PageMaker;
import com.chagok.domain.PlusVO;
import com.chagok.domain.SysLogVO;
import com.chagok.domain.UserVO;
import com.chagok.service.AbookService;
import com.chagok.service.ChallengeService;
import com.chagok.service.FeedService;
import com.chagok.service.UserService;
import com.chagok.utils.UploadFileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Controller
@RequestMapping("/challenge/*")
public class ChallengeController {

	@Inject
	private ChallengeService service;
	
	@Inject
	private UserService uservice;
	
	@Resource(name="uploadPath")
	private String uploadPath;

	@Inject
	private FeedService feedservice;

	private static final Logger mylog = LoggerFactory.getLogger(ChallengeController.class);

	// http://localhost:8080/challenge/plusFeed?cno=2
	@GetMapping(value = "/plusFeed")
	public String plusfeedGET(Model model, @RequestParam("cno") int cno, HttpSession session, UserVO vo) throws Exception {
		mylog.debug("plusfeedGET() ??????");

		List<Map<String, Object>> plusPeoList = service.getPlusPeople(cno);
		mylog.debug("plusFeedGET()?????? id : "+session.getId());
		SysLogVO sysLogVO = new SysLogVO();

		model.addAttribute("sessionId", sysLogVO.getUserId());
		Integer mno = service.getChallengeInfo(cno).getMno();
		
		// ?????? ?????? (plus ??????????????? cno, mno??? ??? ????????? ??????)
		PlusVO plusVO = service.getPlusOne((int)session.getAttribute("mno"), cno);
		model.addAttribute("myPlusVO", plusVO);
		// ?????? ??????
		
		model.addAttribute("vo", service.getChallengeInfo(cno));
		model.addAttribute("plusPeoList", plusPeoList);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("msgList", feedservice.getMsgList(cno));
		model.addAttribute("host",uservice.getUser(mno));
		
		return "/challenge/plusFeed";
	}
	
	// ?????? ???????????? ?????????
	@PostMapping(value = "/getPreChat")
	@ResponseBody 
	public List<MessageVO> preChat(@RequestBody String cno) throws Exception {
		
		return feedservice.getMsgList(Integer.parseInt(cno));
	}
	
	// http://localhost:8080/challenge/plusdetail?cno=2

	@GetMapping(value = "/plusdetail")
	public String plusdetailGET(Model model, @RequestParam("cno") Integer cno, HttpSession session) throws Exception {
		mylog.debug("plusdetailGET ??????");
		mylog.debug(cno + "");
		
	
		ChallengeVO vo = service.getChallengeInfo(cno);
		// mno??? ???????????? user??? nick??? ?????????
		model.addAttribute("user", uservice.getUser(vo.getMno())); 
		
		ChallengeVO vo2 = service.getCt_top(cno);

		model.addAttribute("vo", vo); // plusdetail??? ????????????

		model.addAttribute("vo2", vo2);

		return "/challenge/plusdetail";
	}


	// http://localhost:8080/challenge/minusFeed?cno=1
	// http://localhost:8080/challenge/minusFeed
	@Inject
	private AbookService aService;
	@GetMapping(value="/minusFeed")
	public String minusFeed(Model model,@RequestParam("cno") int cno,HttpSession session,ChallengeVO cvo,MinusVO mvo) throws Exception {
		mylog.debug(" ??? ??? : minusFeed Get ?????? ");
		
		int mno = cvo.getMno();
		
		ChallengeVO vo = service.getChallengeInfo(cno);
		List<Map<String, Object>> minusPeoList = service.getMinusPeople(cno);
		mylog.debug(minusPeoList+"");
		ChallengeVO vo2 = service.getCt_top(cno);
		ChallengeVO vo3 = service.getMoney(mno);
		
		// ????????? -> DAO ????????? ????????? ????????????
		// getAbookList(1) -> getAbookList(mno) ?????? ?????? !!!!!
//		List<AbookVO> abookList = aService.getAbookList(mno);
		List<Map<String, Object>> minusAbook = service.getMinusAbook(mno, cno);
		mylog.debug(minusAbook+"");
//		mylog.debug("abookList : "+abookList);
		
//		List<CategoryVO> cateList = aService.CateList();
//		mylog.debug("cateList : "+cateList);
		mylog.debug("minusFeedGET()?????? id : "+session.getId());
		SysLogVO sysLogVO = new SysLogVO();
//		ObjectMapper mapper = new ObjectMapper();

//		String jsonAbook = mapper.writeValueAsString(abookList);
//		mylog.debug("jsonAbook : "+jsonAbook);
//		String jsonCate = mapper.writeValueAsString(cateList);
//		mylog.debug("jsonCate : "+jsonCate);

	   // ????????? ??????????????? ?????? ??????(model)
		model.addAttribute("sessionId", sysLogVO.getUserId());
	   model.addAttribute("vo", vo);
	   model.addAttribute("minusPeoList", minusPeoList);
	   model.addAttribute("vo2", vo2);
	   model.addAttribute("c_end", service.getChallengeEndDate(cno));
	   model.addAttribute("mvo",mvo);
	   model.addAttribute("vo3", vo3);
	   
//	   model.addAttribute("abookList", abookList);
//	   model.addAttribute("cateList", cateList);
//	   model.addAttribute("jsonAbook",jsonAbook);
//		model.addAttribute("jsonCate",jsonCate);
	   model.addAttribute("minusAbook", minusAbook);
	   
	   return "/challenge/minusFeed";
	}
	
	@PostMapping(value = "/minusFeed")
	public String minusFeedPOST(@RequestParam("cno") int cno,@RequestParam("mno") int mno,Model model,@RequestParam("ab_amount") int ab_amount) throws Exception {
		mylog.debug("minusFeedPOST ?????? ");
		
		
		service.updateMoney(mno,ab_amount,cno);

		model.addAttribute("cno", cno);
		return "redirect:/challenge/minusFeed";
	}

	@PostMapping(value = "/plusdetailPOST")
	@ResponseBody // ajax ?????? ?????? jsp??? ????????? ?????? ??????@RequestParam("ctno") int ctno, 
	public String plusdetailPOST(@RequestBody Map<String, Object> map,HttpSession session) throws Exception {
		mylog.debug("plusdetailPOST ??????");
		mylog.debug(map+"");
		
		String result="";
		
		Integer gctno = service.samechallenge(map);	
		mylog.debug(gctno+"");
		if(gctno != null) {
			result = "Y";
		}else {
			result = "N";

			mylog.debug(map+"");
			service.joinplusInsert(map); // mno??? cno??????
			service.joinplusUpdate(map); // nick?????? cno
		}
	
		mylog.debug(result);
		return result;
	}

	// http://localhost:8080/challenge/minusdetail?cno=1
	@GetMapping(value = "/minusdetail")
	public String minusdetailGET(Model model,@RequestParam("cno") int cno, HttpSession session) throws Exception {
		mylog.debug("minusdetailGET ??????");
		mylog.debug(cno+"");
		
		ChallengeVO vo = service.getChallengeInfo(cno);
		model.addAttribute("user", uservice.getUser(vo.getMno())); 
		ChallengeVO vo2 = service.getCt_top(cno);
		
		model.addAttribute("vo", vo); // minusdetail??? ????????????
		model.addAttribute("vo2", vo2);

		return "/challenge/minusdetail";
	}
	
	@PostMapping(value = "/minusdetailPOST")
	@ResponseBody // ajax ?????? ?????? jsp??? ????????? ?????? ??????@RequestParam("ctno") int ctno, 
	public String minusdetailPOST(@RequestBody Map<String, Object> map,HttpSession session) throws Exception {
		mylog.debug("minusdetailPOST ??????");
		mylog.debug(map+"");
		
		String result="";
		
		Integer gctno = service.samechallenge(map);	
		mylog.debug(gctno+"");
		if(gctno != null) {
			result = "Y";
		}else {
			result = "N";

			mylog.debug(map+"");
			service.joinminusInsert(map); // mno??? cno??????
			service.joinplusUpdate(map); // nick?????? cno
		}
	
		mylog.debug(result);
		return result;
}
	
	// http://localhost:8080/challenge/checkfeed?cno=2
	@GetMapping(value = "/checkfeed")
	public String checkfeedGET(HttpSession session,@RequestParam("cno")int cno, Model model) throws Exception {
			
		ChallengeVO vo = service.getChallengeInfo(cno);
		
		List<ChallengeVO> challengeList = service.getChallengeList(cno);
		int CList = service.getCList(cno);
		ChallengeVO vo2 = service.getCt_top(cno);
		List<Map<String, Object>> result = service.getResult(cno);
		List<Map<String, Object>> minusPeoList = service.getMinusPeople(cno);
		List<Map<String, Object>> plusPeoList = service.getPlusPeople(cno);
		
		
		model.addAttribute("vo", vo);
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("user", uservice.getUser(vo.getMno())); 
		model.addAttribute("CList",CList);
		model.addAttribute("vo2", vo2);
		model.addAttribute("result", result);
		model.addAttribute("minusPeoList", minusPeoList);
		model.addAttribute("plusPeoList", plusPeoList);
		
		return "/challenge/checkfeed";
	}

	// http://localhost:8080/challenge/mychallenge
		@GetMapping("/mychallenge")
		public String mychallengeGET(Model model, HttpSession session) throws Exception {
			
			String nick = (String)session.getAttribute("nick");
			
			if(nick != null) {
				List<ChallengeVO> mychallengeList = service.getmyChallenge(nick);
				model.addAttribute("mychallengeList", mychallengeList);
				mylog.debug(mychallengeList+"");
			}
			
			
			return "/challenge/mychallenge";
		}
		
		// mychallenge?????? ???????????? ?????? ???
		@GetMapping("/cancelChallenge")
		public String cancelChallengeGET(@RequestParam("cno") Integer cno, @RequestParam("c_sort") Integer c_sort,HttpSession session) throws Exception {
			
			Integer mno = (Integer)session.getAttribute("mno");
			mylog.debug(cno+" : cno , "+mno+" : mno, "+c_sort+" : c_sort");
			String a = ",";
			String b = uservice.getUser(mno).getNick();
			String nick = a+b;
			service.cancelChallenge(nick,cno);
			
			if(c_sort == 0) {
				service.cancelPlus(mno, cno);
			}else if(c_sort == 1){
				service.cancelMinus(mno, cno);
			}
//			cno, mno???????????? ??????
			
			return "redirect:/challenge/mychallenge";
		}
		
		
	// http://localhost:8080/challenge/webSocket
	// ????????? ?????? !!
	@GetMapping(value="/webSocket")
	public String webSocket(Model model,HttpSession session) throws Exception {
		
	   // ????????? ??????????????? ?????? ??????(model)
	   
	   return "/challenge/webSocket";
	}
	
	
	// ????????? ?????? (?????????) - GET
	// http://localhost:8080/challenge/plusregist
	@GetMapping(value="/plusregist")
	public String plusRegistGET() throws Exception{
		mylog.debug(" /challenge/plusRegistGET ?????? -> ????????? ?????? ");
		
		return "/challenge/plusRegist";
		
	}
		
	// ????????? ?????? (?????????) - POST
	@RequestMapping(value = "/plusregist", method=RequestMethod.POST)
	public String plusRegistPOST(ChallengeVO vo, MultipartFile file, HttpSession session, Model model) throws Exception{
		mylog.debug(" /challenge/plusRegist(POST) ?????? ");	
		
		// ???????????? ??????
		int mno = (Integer)session.getAttribute("mno");
			
		UserVO userVO = uservice.getUser(mno);
		model.addAttribute("userVO", userVO);
		vo.setMno(mno);
		vo.setC_person(userVO.getNick()+",");
		
		// ????????????
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;

		if(file != null) {
		   fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
		} else {
		   fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}

		vo.setC_file(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		vo.setC_thumbFile(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		
//		// 1. ????????? ?????? ??????
		mylog.debug(vo.toString());
		
		// 2. ????????? -> DAO ?????? (mapper)
		service.challengeRegist(vo);
		mylog.debug(" ????????? ??????(?????????) ??????! ");
		
		// 3. ???????????? ??????(????????? ?????????)
//		rttr.addFlashAttribute("result", "plusRegistOK");
		return "redirect:/commumain";
		
	}
		
	// ????????? ?????? (?????????) - GET
	// http://localhost:8080/challenge/minusregist
	@GetMapping(value="/minusregist")
	public String minusRegistGET() throws Exception{
		mylog.debug(" /challenge/minusRegistGET ?????? -> ????????? ?????? ");
		
		return "/challenge/minusRegist";
	}
	
	// ????????? ?????? (?????????) - POST
	@RequestMapping(value = "/minusregist", method=RequestMethod.POST)
	public String minusRegistPOST(ChallengeVO vo, MultipartFile file, HttpSession session, Model model) throws Exception{
		mylog.debug(" /challenge/minusRegist(POST) ?????? ");	
		
		// ???????????? ??????
		int mno = (Integer)session.getAttribute("mno");
			
		UserVO userVO = uservice.getUser(mno);
		model.addAttribute("userVO", userVO);
		vo.setMno(mno);
		vo.setC_person(userVO.getNick()+",");
		
		// ????????????
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;

		if(file != null) {
		   fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
		} else {
		   fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}

		vo.setC_file(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		vo.setC_thumbFile(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		
		// 1. ????????? ?????? ??????
		mylog.debug(vo.toString());
		
		// 2. ????????? -> DAO ?????? (mapper)
		service.challengeRegist(vo);
		mylog.debug(" ????????? ??????(?????????) ??????! ");
		
		// 3. ???????????? ??????(????????? ?????????)
//		rttr.addFlashAttribute("result", "plusRegistOK");
		return "redirect:/commumain";
		
	}
	
	// ????????? ??????(??????)
	// http://localhost:8080/challenge/success?cno=1
	@GetMapping(value="/success")
	public String victoryGET(Model model, @RequestParam("cno") int cno, HttpSession session) throws Exception{
		ChallengeVO vo = service.getChallengeInfo(cno);
		List<ChallengeVO> challengeList = service.getChallengeList(cno);
		
		ChallengeVO vo2 = service.getCt_top(cno);
		int CList = service.getCList(cno);
		int ChallengeMoney = service.getChallengeMoney(cno);
		Integer Success = service.getSuccess(cno);
		
		model.addAttribute("vo", vo);
		model.addAttribute("CList", CList);
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("vo2", vo2);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("ChallengeMoney", ChallengeMoney);
		model.addAttribute("Success", Success);
		
		return "/challenge/resultsuccess";
	}

	// ????????? ??????(??????)
	// http://localhost:8080/challenge/defeat?cno=1
	@GetMapping(value="/defeat")
	public String defeatGET(Model model, @RequestParam("cno") int cno, HttpSession session) throws Exception{
		ChallengeVO vo = service.getChallengeInfo(cno);
		List<ChallengeVO> challengeList = service.getChallengeList(cno);
				
		ChallengeVO vo2 = service.getCt_top(cno);
		int CList = service.getCList(cno);
		int ChallengeMoney = service.getChallengeMoney(cno);
		Integer Success = service.getSuccess(cno);
		
		model.addAttribute("vo", vo);
		model.addAttribute("CList", CList);
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("vo2", vo2);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("ChallengeMoney", ChallengeMoney);
		model.addAttribute("Success", Success);
				
		return "/challenge/resultdefeat";
	}
	
	////////////////////// ????????? ????????? ///////////////////////////
	
	
	// ????????? ????????? ??????
	// http://localhost:8080/challenge/chListAll
	@GetMapping("/chListAll")
	public String adminconfirmGET(Criteria cri, Model model) throws Exception {
		mylog.debug("/adminconfirmGET ??????");
		
		cri.setPerPageNum(10);
		List<ChallengeVO> challengeList = service.chListAll(cri);
		
		// ????????? ??????
	    PageMaker pagevo = new PageMaker();
	    pagevo.setCri(cri);
	    pagevo.setTotalCount(10000);
		
	    model.addAttribute("pagevo", pagevo);
		model.addAttribute("challengeList", challengeList);
		
		return "/challenge/adminconfirm";
	}
	
	@ResponseBody
	@GetMapping(value="/confirm")
	public int confirm(@RequestParam int status, @RequestParam int cno, RedirectAttributes rttr) throws Exception {
		mylog.debug("status : "+status+", cno : "+cno);
		int result=0;
		
		service.confirmChallenge(status, cno);

		if(status==1) {
			result = 1;
		} else if(status==6) {
			result = 6;
		}
		mylog.debug("??????"+result);
		return result;
	}

	// http://localhost:8080/challenge/memberManagement
	// ????????? ????????????
	@GetMapping("/memberManagement")
	public String memberManagementGET(Model model) throws Exception {
		mylog.debug("/memberManagementGET ??????");
		
		List<UserVO> user = uservice.getUserList();
		
		mylog.debug(user.toString());
		
		model.addAttribute("userlist", user);
		
		return "/challenge/memberManagement";
	}
	// http://localhost:8080/challenge/adminmodal
	@ResponseBody
	@GetMapping("/adminmodal")
	public String adminmodal(Model model,@RequestParam Integer mno) throws Exception {
		mylog.debug("???????????? ?????? mno : " + mno);
		
		List<UserVO> vo = service.adminmodal(mno);
		
		model.addAttribute("UserVO", vo);
		
		return "challenge/memberManagement";
	}
	
	////////////////////// ????????? ????????? ///////////////////////////
	
	
	
	
	
	
	
	/////////////////////////// ?????? ???????????? ?????? ?????? ///////////////////////////////////
	@GetMapping(value = "/sendBiz")
	public String sendBiz(BusinessAccountVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		if (session.getAttribute("mno") != null) {
			int mno = (int)session.getAttribute("mno");
			UserVO userVO = uservice.getUser(mno);
			vo.setBiz_holder_name(userVO.getNick());
			vo.setBiz_inout(1);
			vo.setMno(mno);
		}
		
		service.sendBiz(vo);
		
		service.updatePlusSum(vo);
		
		rttr.addFlashAttribute("sendOK", "OK");
		
		return "redirect:/challenge/plusFeed?cno="+vo.getCno();
	}
	
	/////////////////////////// ?????? ???????????? ?????? ?????? ///////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	/////////////////// ?????? (news api ??????) ///////////////////////
	
	@GetMapping("/news/test")
	public String newsGET() throws Exception{
		
		
		return "/news/news";
	}
	
	/////////////////// ?????? (news api ??????) ///////////////////////

}