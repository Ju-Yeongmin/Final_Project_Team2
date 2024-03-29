package com.chagok.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chagok.domain.BusinessAccountVO;
import com.chagok.domain.ChallengeVO;
import com.chagok.domain.Criteria;
import com.chagok.domain.MessageVO;
import com.chagok.domain.MinusVO;
import com.chagok.domain.PageMaker;
import com.chagok.domain.PlusVO;
import com.chagok.domain.SysLogVO;
import com.chagok.domain.UserVO;
import com.chagok.service.AbookService;
import com.chagok.service.AlertService;
import com.chagok.service.ChallengeService;
import com.chagok.service.FeedService;
import com.chagok.service.UserService;
import com.chagok.utils.UploadFileUtils;

@Controller
@RequestMapping("/challenge/*")
public class ChallengeController {

	@Inject
	private ChallengeService service;
	
	@Inject
	private UserService uservice;
	
	@Inject
	private AlertService alertService;
	
	@Resource(name="uploadPath")
	private String uploadPath;

	@Inject
	private FeedService feedservice;
	

	private static final Logger mylog = LoggerFactory.getLogger(ChallengeController.class);

	// http://localhost:8080/challenge/plusFeed?cno=2
	@GetMapping(value = "/plusFeed")
	public String plusfeedGET(Model model, @RequestParam("cno") int cno, HttpSession session, UserVO vo,
			HttpServletRequest req ) throws Exception {
		mylog.debug("plusfeedGET() 호출");
		
		List<Map<String, Object>> plusPeoList = service.getPlusPeople(cno);
		Integer mno = service.getChallengeInfo(cno).getMno();
		
		// 영민 추가 (plus 테이블에서 cno, mno로 내 정보만 호출)
		PlusVO plusVO = service.getPlusOne((int)session.getAttribute("mno"), cno);
		model.addAttribute("myPlusVO", plusVO);
		// 영민 추가
		
		model.addAttribute("vo", service.getChallengeInfo(cno));
		model.addAttribute("plusPeoList", plusPeoList);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("msgList", feedservice.getMsgList(cno));
		model.addAttribute("host",uservice.getUser(mno));
		
		return "/challenge/plusFeed";
	}
	
	// 과거 채팅기록 가져옴
	@PostMapping(value = "/getPreChat")
	@ResponseBody 
	public List<MessageVO> preChat(@RequestBody String cno) throws Exception {
		
		return feedservice.getMsgList(Integer.parseInt(cno));
	}
	
	// http://localhost:8080/challenge/plusdetail?cno=2

	@GetMapping(value = "/plusdetail")
	public String plusdetailGET(Model model, @RequestParam("cno") Integer cno, HttpSession session) throws Exception {
		mylog.debug("plusdetailGET 호출" + cno);
		
		Integer mno = service.getChallengeInfo(cno).getMno();
		ChallengeVO vo = service.getChallengeInfo(cno);
		// mno에 해당하는 user의 nick을 받아옴
		model.addAttribute("user", uservice.getUser(vo.getMno())); 
		
		ChallengeVO vo2 = service.getCt_top(cno);

		model.addAttribute("vo", vo); // plusdetail로 정보전달
		model.addAttribute("vo2", vo2);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("host",uservice.getUser(mno));
		
		return "/challenge/plusdetail";
	}


	// http://localhost:8080/challenge/minusFeed?cno=1
	// http://localhost:8080/challenge/minusFeed
	@Inject
	private AbookService aService;
	@GetMapping(value="/minusFeed")
	public String minusFeed(Model model,@RequestParam("cno") int cno,HttpSession session,ChallengeVO cvo,MinusVO mvo) throws Exception {
		mylog.debug(" 수 지 : minusFeed Get 호출 ");
		
		// 로그인 확인
		if(session.getAttribute("mno")==null) {
			return "/chagok/login";
		}
		
		Integer mmno = service.getChallengeInfo(cno).getMno();
		ChallengeVO vo = service.getChallengeInfo(cno);
		List<Map<String, Object>> minusPeoList = service.getMinusPeople(cno);
		mylog.debug(minusPeoList+"");
		ChallengeVO vo2 = service.getCt_top(cno);
		
		mylog.debug("minusFeedGET()에서 id : "+session.getId());
		SysLogVO sysLogVO = new SysLogVO();

		// 회원정보 저장
		int mno = (Integer)session.getAttribute("mno");
		mylog.debug("mno : " +mno);
					
		ChallengeVO vo3 = service.getMoney(mno);
		
		// 서비스 -> DAO 가계부 리스트 가져오기
		int ctno = service.getCtno(cno);
		List<Map<String, Object>> minusAbook = service.getMinusAbook(mno, cno, ctno);
		mylog.debug(minusAbook+"");
		mylog.debug(mno+"");
		mylog.debug(cno+"");
		mylog.debug(ctno+"");
				
		// 연결된 뷰페이지로 정보 전달(model)
		model.addAttribute("mno", mno);
		model.addAttribute("sessionId", sysLogVO.getUserId());
		model.addAttribute("vo", vo);
		model.addAttribute("minusPeoList", minusPeoList);
		model.addAttribute("vo2", vo2);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("mvo",mvo);
		model.addAttribute("vo3", vo3);
		   
		model.addAttribute("minusAbook", minusAbook);
		model.addAttribute("host",uservice.getUser(mmno));
		   
		return "/challenge/minusFeed";
	}
	
	@PostMapping(value = "/minusFeed")
	public String minusFeedPOST(@RequestParam("cno") int cno,@RequestParam("mno") int mno,Model model,@RequestParam("ab_amount") int ab_amount) throws Exception {
		mylog.debug("minusFeedPOST 호출 ");
	
		//Integer camount = service.getChallengeInfo(cno).getC_amount();
		service.updateMoney(mno,ab_amount,cno);

		model.addAttribute("cno", cno);
		return "redirect:/challenge/minusFeed";
	}

	@PostMapping(value = "/plusdetailPOST")
	@ResponseBody // ajax 값을 바로 jsp에 보내기 위해 사용@RequestParam("ctno") int ctno, 
	public String plusdetailPOST(@RequestBody Map<String, Object> map,HttpSession session) throws Exception {
		mylog.debug("plusdetailPOST 호출" + map);
		
		String result="";
		
		Integer gctno = service.samechallenge(map);	
		mylog.debug(gctno+"");
		if(gctno != null) {
			result = "Y";
		}else {
			result = "N";

//			mylog.debug(map+"");
			service.joinplusInsert(map); // mno랑 cno필요
			service.joinplusUpdate(map); // nick이랑 cno
		}
	
//		mylog.debug(result);
		return result;
	}

	// http://localhost:8080/challenge/minusdetail?cno=1
	@GetMapping(value = "/minusdetail")
	public String minusdetailGET(Model model,@RequestParam("cno") int cno, HttpSession session) throws Exception {
		mylog.debug("minusdetailGET 호출"+cno);
		
		ChallengeVO vo = service.getChallengeInfo(cno);
		model.addAttribute("user", uservice.getUser(vo.getMno())); 
		ChallengeVO vo2 = service.getCt_top(cno);
		Integer mno = service.getChallengeInfo(cno).getMno();
		
		model.addAttribute("vo", vo); // minusdetail로 정보전달
		model.addAttribute("vo2", vo2);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("host",uservice.getUser(mno));
		
		return "/challenge/minusdetail";
	}
	
	@PostMapping(value = "/minusdetailPOST")
	@ResponseBody // ajax 값을 바로 jsp에 보내기 위해 사용@RequestParam("ctno") int ctno, 
	public String minusdetailPOST(@RequestBody Map<String, Object> map,HttpSession session) throws Exception {
		mylog.debug("minusdetailPOST 호출" + map);

		String result="";
		Integer gctno = service.samechallenge(map);	
		mylog.debug("gctno : "+gctno);
		
		if(gctno != null) {
			result = "Y";
		}else {
			result = "N";

			service.joinminusInsert(map); // mno랑 cno필요
			service.joinplusUpdate(map); // nick이랑 cno
		}
	
//		mylog.debug(result);
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
		public String mychallengeGET(Model model, HttpSession session, RedirectAttributes rttr,Criteria cri) throws Exception {
			
//			mylog.debug(cri+": 마이챌린지 cri");
			
			String nick = (String)session.getAttribute("nick");
			mylog.debug("mychallenge "+nick);
			Integer mno	= (Integer)session.getAttribute("mno");
			List<Map<String, Object>> challengeResultList = new ArrayList<Map<String,Object>>();
			
			// 페이징 처리
			cri.setPerPageNum(10);
			List<ChallengeVO> mychallengeAll = service.mychallengeAll(cri,nick);
			PageMaker pagevo = new PageMaker();
			pagevo.setDisplayPageNum(10);
			pagevo.setCri(cri);
			pagevo.setTotalCount(service.mychallengecnt(nick));
			
			mylog.debug(pagevo.toString());
			mylog.debug(""+mychallengeAll);
			
			model.addAttribute("pagevo", pagevo);
			model.addAttribute("mychallengeAll", mychallengeAll);
			
			if(nick != null) {
				List<ChallengeVO> mychallengeList = service.getmyChallenge(nick);
				model.addAttribute("mychallengeList", mychallengeList);
				mylog.debug(mychallengeList+"");
				
				for(int i = 0;i< mychallengeList.size();i++) {
					Integer cno = mychallengeList.get(i).getCno();
					Map<String, Object> result = service.challengeResult(cno, mno);
					
					challengeResultList.add(result);
					//model.addAttribute("result", result);
					
				}
				model.addAttribute("challengeResultList", challengeResultList);
			}

			return "/challenge/mychallenge";
		}
		
		// mychallenge에서 신청취소 했을 때
		@ResponseBody
		@GetMapping("/cancelChallenge")
		public int cancelChallengeGET(@RequestParam int cno, @RequestParam int c_sort, HttpSession session) throws Exception {
			
			Integer mno = (Integer)session.getAttribute("mno");
			mylog.debug(cno+" : cno , "+mno+" : mno, "+c_sort+" : c_sort");
			String a = ",";
			String b = uservice.getUser(mno).getNick();
			String nick = a+b;
			int chCancelResult = 0;
			int feedCancelResult = 0;
			int result = 0;
			chCancelResult = service.cancelChallenge(nick,cno);
			
			if(c_sort == 0) {
				feedCancelResult = service.cancelPlus(mno, cno);
			}else if(c_sort == 1){
				feedCancelResult = service.cancelMinus(mno, cno);
			}
//			cno, mno로챌린지 조회
			mylog.debug("chCancelResult : "+chCancelResult);
			mylog.debug("feedCancelResult : "+feedCancelResult);
			if(chCancelResult!=0 && feedCancelResult!=0) {
				result = 1;
			}
			mylog.debug("result"+result);
			return result;
		}
		
		
	// http://localhost:8080/challenge/webSocket
	// 웹소캣 채팅 !!
	@GetMapping(value="/webSocket")
	public String webSocket(Model model,HttpSession session) throws Exception {
		
	   // 연결된 뷰페이지로 정보 전달(model)
	   
	   return "/challenge/webSocket";
	}
	
	
	// 챌린지 등록 (저축형) - GET
	// http://localhost:8080/challenge/plusregist
	@GetMapping(value="/plusregist")
	public String plusRegistGET(HttpSession session) throws Exception{
		// 로그인 확인
		if(session.getAttribute("mno")==null) {
			return "redirect:/login?pageInfo=challenge/plusregist";
		}else {
			return "/challenge/plusRegist";
		}
	}
		
	// 챌린지 등록 (저축형) - POST
	@RequestMapping(value = "/plusregist", method=RequestMethod.POST)
	public String plusRegistPOST(ChallengeVO vo, MultipartFile file, HttpSession session, Model model, Map<String, Object> map, RedirectAttributes rttr) throws Exception{
		
		// 회원정보 저장
		int mno = (Integer)session.getAttribute("mno");
			
		UserVO userVO = uservice.getUser(mno);
		model.addAttribute("userVO", userVO);
		vo.setMno(mno);
		vo.setC_person(userVO.getNick());
		
		// 중복참여 제어
		map.put("mno", mno);
		map.put("ctno", vo.getCtno());
		
		Integer overlap = service.samechallenge(map);
		
		if(overlap != null) {
			rttr.addFlashAttribute("overlap", "overlap");
			
			return "redirect:/commumain";
		}
		rttr.addFlashAttribute("overlap", "Noverlap");
			
		// 사진등록
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
		
		// 챌린지 등록
		service.challengeRegist(vo);
		
		// plus 테이블에 정보 추가
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("mno", mno);
		plus.put("cno", vo.getCno());
		service.joinplusInsert(plus);
		
		return "redirect:/challenge/mychallenge";
	}
		
	
	// 챌린지 등록 (절약형) - GET
	// http://localhost:8080/challenge/minusregist
	@GetMapping(value="/minusregist")
	public String minusRegistGET(HttpSession session) throws Exception{
		// 로그인 확인
		if(session.getAttribute("mno")==null) {
			return "redirect:/login?pageInfo=challenge/minusregist";
		}else {
			return "/challenge/minusRegist";
		}
	}
	
	// 챌린지 등록 (절약형) - POST
	@RequestMapping(value = "/minusregist", method=RequestMethod.POST)
	public String minusRegistPOST(ChallengeVO vo, MultipartFile file, HttpSession session, Model model, Map<String, Object> map, RedirectAttributes rttr) throws Exception{
		
		// 회원정보 저장
		int mno = (Integer)session.getAttribute("mno");
			
		UserVO userVO = uservice.getUser(mno);
		model.addAttribute("userVO", userVO);
		vo.setMno(mno);
		vo.setC_person(userVO.getNick());
		
		// 중복참여 제어
		map.put("mno", mno);
		map.put("ctno", vo.getCtno());
		
		Integer overlap = service.samechallenge(map);
		
		if(overlap != null) {
			rttr.addFlashAttribute("overlap", "overlap");
			
			return "redirect:/commumain";
		}
		rttr.addFlashAttribute("overlap", "Noverlap");
		
		// 사진등록
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
		
		// 챌린지 등록
		service.challengeRegist(vo);
		
		// minus 테이블에 정보 추가
		Map<String, Object> minus = new HashMap<String, Object>();
		minus.put("mno", mno);
		minus.put("cno", vo.getCno());
		service.joinminusInsert(minus);
		
		return "redirect:/challenge/mychallenge";
	}
	
	// 챌린지 결과(성공)
		// http://localhost:8080/challenge/success?cno=1
		@GetMapping(value="/success")
		public String victoryGET(Model model, @RequestParam("cno") int cno, HttpSession session) throws Exception{
			Integer mno = (Integer) session.getAttribute("mno");
		
		ChallengeVO vo = service.getChallengeInfo(cno);
		List<ChallengeVO> challengeList = service.getChallengeList(cno);
		int CList = service.getCList(cno); 
		
		int ChallengeMoney = service.getChallengeMoney(cno); 
		Integer Success = service.getSuccess(cno); 
		Map<String, Object> result = service.challengeResult(cno, mno);
		Date c_end = service.getChallengeEndDate(cno);
		
		model.addAttribute("vo", vo); // 해당 챌린지 정보
		model.addAttribute("CList", CList); // 참여인원
		model.addAttribute("c_end", c_end); // 종료일자
		model.addAttribute("ChallengeMoney", ChallengeMoney); // 총 예치금
		model.addAttribute("Success", Success); // 성공인원
		model.addAttribute("result", result);
		
		// 포인트 처리(biz계좌)
		Map<String, Object> giveInfo = new HashMap<String, Object>();
	    giveInfo.put("mno", mno);
	    giveInfo.put("getpoint", (ChallengeMoney/Success)*0.9);
	    
		uservice.givePoint(giveInfo);
		
		return "/challenge/resultsuccess";
	}

	// 챌린지 결과(실패)
	// http://localhost:8080/challenge/defeat?cno=2
	@GetMapping(value="/defeat")
	public String defeatGET(Model model, @RequestParam("cno") int cno) throws Exception{
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
	
	
	/////////////////////////// 영민 비지니스 계좌 송금 ///////////////////////////////////
	@GetMapping(value = "/sendBiz")
	public String sendBiz(BusinessAccountVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		if (session.getAttribute("mno") != null) {
			int mno = (int)session.getAttribute("mno");
			UserVO userVO = uservice.getUser(mno);
			vo.setBiz_holder_name(userVO.getNick());
			vo.setBiz_inout(2);
			vo.setMno(mno);
		}
		
		service.sendBiz(vo);
		
		service.updatePlusSum(vo);
		
		rttr.addFlashAttribute("sendOK", "OK");
		
		return "redirect:/challenge/plusFeed?cno="+vo.getCno();
	}
	
	/////////////////////////// 영민 비지니스 계좌 송금 ///////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	/////////////////// 영민 (news api 관련) ///////////////////////
	
	@GetMapping("/news/test")
	public String newsGET() throws Exception{
		
		
		return "/news/news";
	}
	
	/////////////////// 영민 (news api 관련) ///////////////////////

}