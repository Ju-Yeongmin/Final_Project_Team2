package com.chagok.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chagok.domain.BoardVO;
import com.chagok.domain.ChallengeVO;
import com.chagok.domain.CommentVO;
import com.chagok.domain.Criteria;
import com.chagok.domain.PageMaker;
import com.chagok.service.BoardService;
import com.chagok.service.ChallengeService;
import com.chagok.service.UserService;

@Controller
public class BoardController {
	
	private static final Logger mylog = LoggerFactory.getLogger(ChagokController.class);
	
	@Inject
	private ChallengeService service;
	
	@Inject
	private BoardService Bservice;
	
	@Inject
	private UserService uservice;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	
	// =================================================================================
	// 후기글 리스트 (b_sort=1)
	// http://localhost:8080/reviewboard
	@GetMapping(value = "/reviewboard")
	public String reviewboardGET(HttpSession session,Model model,Criteria cri) throws Exception {
		
		List<Map<String, Object>> boardList2 = Bservice.getRBoardPage(cri);	
		Integer count = Bservice.RboardCount();
		
		// 페이징 처리
		PageMaker pageMaker = new PageMaker();
		cri.setPerPageNum(10);
		pageMaker.setDisplayPageNum(10);
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(Bservice.RboardCount());
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("count",count);			
		model.addAttribute("boardList2", boardList2);
		
		return "/community/reviewboard";
	}

	// 후기글 작성 GET
	// http://localhost:8080/review?cno=1
	@GetMapping(value = "/review")
	public String reviewGET(@RequestParam("cno") int cno, Model model, HttpSession session) throws Exception {
		Integer mno = (Integer) session.getAttribute("mno");
		
		ChallengeVO vo2 = service.getCt_top(cno);
		
		Map<String, Object> result = service.challengeResult(cno, mno);
		
		model.addAttribute("review", service.getChallengeInfo(cno));
		model.addAttribute("vo2", vo2);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("result", result);
		
		return "/community/review";
	}
	
	// 후기글 작성 POST
	@PostMapping(value = "/review")
	public String reviewPOST(BoardVO vo, RedirectAttributes rttr) throws Exception {
		
		Bservice.createReview(vo);

		rttr.addFlashAttribute("result", "createOK");

		return "redirect:/reviewboard?page=1";
	}
	
	
	// 후기 글 수정 GET
	// http://localhost:8080/challenge/reviewupdate?bno=4
	@GetMapping(value= "/reviewupdate")
	public String reviewupdateGET(@RequestParam("bno") int bno, Model model, HttpSession session,Integer cno) throws Exception{
					
			
		Map<String, Object> boardChallenge = Bservice.getBoardChallenge(bno);
		
		Integer Success = service.getSuccess(cno);
		ChallengeVO vo2 = service.getCt_top(cno);
		
		model.addAttribute("review", service.getChallengeInfo(cno));
		model.addAttribute("Success",Success);
		model.addAttribute("vo2", vo2);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		
		model.addAttribute("boardChallenge", boardChallenge);
		
		return "/community/reviewupdate";
					
	}
				
	// 후기 글 수정 POST
	@PostMapping(value = "/reviewupdate")
	public String reviewupdatePOST(BoardVO vo,RedirectAttributes rttr,HttpSession session) throws Exception{
			
		Integer result = Bservice.updateBoard(vo);
					
		if(result > 0) {
				
			rttr.addFlashAttribute("result", "modOK");
						
		}
							
		return "redirect:/reviewboard?page=1";
							
	}
		
	// 후기 글 삭제하기
	// http://localhost:8080/reviewremove?bno=4
	@GetMapping(value = "/reviewremove")
	public String reviewremovePOST(int bno,RedirectAttributes rttr) throws Exception{
					
		Bservice.deleteBoard(bno);
					
		rttr.addFlashAttribute("result", "delOK");
					
		return "redirect:/reviewboard?page=1";
					
	}
	
	// 후기글 상세
	// http://localhost:8080/reviewcontent?bno=1
	@GetMapping(value = "/reviewcontent")
	public String reviewcontentGET(HttpSession session,Model model,@RequestParam("cno") int cno,Integer bno,Criteria cri) throws Exception {
		Map<String, Object> boardChallenge = Bservice.getBoardChallenge(bno);
		Integer Success = service.getSuccess(cno);

		ChallengeVO vo = service.getChallengeInfo(cno);
		List<CommentVO> CV = Bservice.getComment(cri,bno);
		Integer CM = Bservice.countingCM(bno);
		
		List<ChallengeVO> challengeList = service.getChallengeList(cno);
		int CList = service.getCList(cno);
		ChallengeVO vo2 = service.getCt_top(cno);
		List<Map<String, Object>> result = service.getResult(cno);
		
		model.addAttribute("CV",CV);
		model.addAttribute("CM",CM);
		model.addAttribute("vo", vo);
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("c_end", service.getChallengeEndDate(cno));
		model.addAttribute("user", uservice.getUser(vo.getMno())); 
		model.addAttribute("CList",CList);
		model.addAttribute("vo2", vo2);
		model.addAttribute("result", result);
		model.addAttribute("boardChallenge", boardChallenge);
		model.addAttribute("Success", Success);
		
		return "/community/reviewcontent";
	}
	// =================================================================================
	
	
	// =================================================================================
	// 공지 글 리스트 (b_sort=2)
	// http://localhost:8080/notice
	@GetMapping(value = "/notice")
	public String noticeGET(Model model,HttpSession session,Criteria cri) throws Exception {
			
		List<BoardVO> boardList2 = Bservice.getNBoardPage(cri);
		Integer count = Bservice.NboardCount();
		
		// 페이징 처리
		PageMaker pageMaker = new PageMaker();
		cri.setPerPageNum(10);
		pageMaker.setDisplayPageNum(10);
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(Bservice.NboardCount());
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("count", count);
		model.addAttribute("boardList2", boardList2);

		return "/community/notice";
	}
	
	// 공지 글 상세
	// http://localhost:8080/noticecontent?bno=4
	@GetMapping(value = "/noticecontent")
	public String noticecontentGET(HttpSession session,Model model,@RequestParam("bno") int bno) throws Exception {
			
		BoardVO vo = Bservice.getBoardContent(bno);
		
		String nick = (String)session.getAttribute("nick");
		
		if(nick == "admin") {
			BoardVO vo2 = Bservice.getBoardContent(bno);
			model.addAttribute("vo2", vo2);
			
		}
		
		model.addAttribute("vo",vo);
			
		return "/community/noticecontent";
	}
	// 공지 글 작성하기
	// http://localhost:8080/noticewrite
	@GetMapping(value = "/noticewrite")
	public String noticewriteGET(Model model, HttpSession session,HttpServletRequest request) throws Exception {
		
		return "/community/noticewrite";
			
	}
		
	// 공지글 작성하기 (post)
	@PostMapping(value = "/noticewrite")
	public String registPOST(BoardVO vo, RedirectAttributes rttr,HttpSession session) throws Exception {
			
		Bservice.insertBoard(vo);
			
		rttr.addFlashAttribute("result", "createOK");
			
		return "redirect:/notice?page=1";
	}	
	
	// 공지 글 수정하기 GET
	// http://localhost:8080/noticeupdate?bno=4
	@GetMapping(value = "/noticeupdate")
	public String noticeupdateGET(@RequestParam("bno") int bno, Model model, HttpSession session) throws Exception{
		model.addAttribute("board", Bservice.getBoardContent(bno));
		
		return "/community/noticeupdate";
			
	}
		
	// 공지 글 수정하기 POST
	@PostMapping(value = "/noticeupdate")
	public String noticeupdatePOST(BoardVO vo,RedirectAttributes rttr,HttpSession session) throws Exception {
				
		Integer result = Bservice.updateBoard(vo);
					
		if(result > 0) {
				
		rttr.addFlashAttribute("result", "modOK");
						
		}
							
		return "redirect:/notice?page=1";
			
	}
	
	// 공지 글 삭제하기
	// http://localhost:8080/noticedelete
	@PostMapping(value = "/noticedelete")
	public String noticedeleteGET(int bno,RedirectAttributes rttr,HttpSession session) throws Exception {
			
		Bservice.deleteBoard(bno);
					
		rttr.addFlashAttribute("result", "delOK");
					
		return "redirect:/notice?page=1";
	}
	
	// =================================================================================
	
	// =================================================================================
	// 자유 게시판 (b_sort = 3)
	//  http://localhost:8080/freeboard
	@GetMapping(value = "/freeboard")
	public String FreeBoardGET(HttpSession session,Model model,Criteria cri) throws Exception {
			
		List<BoardVO> boardList = Bservice.getFBoardPage(cri);	
		
		model.addAttribute("boardList", boardList);
		
		PageMaker pageMaker = new PageMaker();
		cri.setPerPageNum(10);
		pageMaker.setDisplayPageNum(10);
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(Bservice.RboardCount());
		model.addAttribute("pageMaker", pageMaker);
			
		return "/community/freeboard";
	}
	
	// 자유게시판 글 작성 GET
	// http://localhost:8080/freeboardwrite
	@GetMapping(value = "/freeboardwrite")
	public String freeboardwriteGET(HttpSession session,Model model) throws Exception {
		
		return "/community/freeboardwrite";
	}
	
	// 자유게시판 글 작성 POST
	@PostMapping(value = "/freeboardwrite")
	public String freeboardwritePOST(BoardVO vo, RedirectAttributes rttr,HttpSession session) throws Exception {
		
		Bservice.createReview(vo);

		rttr.addFlashAttribute("result", "createOK");

		return "redirect:/freeboard";
	}
	
	// 자유 게시판 글 수정하기 GET
	// http://localhost:8080/freeboardupdate?bno=4
	@GetMapping(value = "/freeboardupdate")
	public String freeboardupdateGET(@RequestParam("bno") int bno, Model model, HttpSession session) throws Exception{
		BoardVO board = Bservice.getBoardContent(bno);
				
		model.addAttribute("board", board);
			
		return "/community/freeboardupdate";
				
	}
			
	// 자유 게시판 글 수정하기 POST
	@PostMapping(value = "/freeboardupdate")
	public String freeboardupdatePOST(BoardVO vo,RedirectAttributes rttr,HttpSession session) throws Exception {
		Integer result = Bservice.updateBoard(vo);
						
		if(result > 0) {
					
		rttr.addFlashAttribute("result", "modOK");
							
		}
								
		return "redirect:/freeboard";
				
	}
	
	// 자유 게시판 삭제
	@GetMapping(value = "/freedelete")
	public String freedeleteGET(int bno,RedirectAttributes rttr,HttpSession session) throws Exception {
		
		Bservice.deleteBoard(bno);
				
		rttr.addFlashAttribute("result", "delOK");
				
		return "redirect:/freeboard";
	}
	// =================================================================================
	
	// =================================================================================
	// http://localhost:8080/economy
	// 경제 게시판 보드
	@GetMapping(value = "/economy")
	public String EconomyGET(HttpSession session,Model model,Criteria cri) throws Exception {
		List<BoardVO> boardList = Bservice.getEBoardPage(cri);
		
		Integer count = Bservice.EboardCount();
		
		PageMaker pageMaker = new PageMaker();
		cri.setPerPageNum(10);
		pageMaker.setDisplayPageNum(10);
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(Bservice.EboardCount());
		
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("boardList", boardList);
		model.addAttribute("count", count);
		
		return "/community/economy";
	}
	
	// 경제 글 작성하기
	// http://localhost:8080/economywrite
	@GetMapping(value = "/economywrite")
	public String economywriteGET(Model model, HttpSession session,HttpServletRequest request) throws Exception {
			
		return "/community/economywrite";
				
	}
			
	// 경제 글 작성하기 (post)
	@PostMapping(value = "/economywrite")
	public String economywritePOST(BoardVO vo, RedirectAttributes rttr,HttpSession session) throws Exception {
				
		Bservice.insertBoard(vo);
			
		rttr.addFlashAttribute("result", "createOK");
		
		return "redirect:/economy?page=1";
	}	
	
	// 경제 글 상세
	// http://localhost:8080/economycontent
	@GetMapping(value = "/economycontent")
	public String economycontentGET(HttpSession session,Model model,@RequestParam("bno") int bno,Criteria cri) throws Exception {
				
		BoardVO vo = Bservice.getBoardContent(bno);
		List<CommentVO> CV = Bservice.getComment(cri,bno);
		Integer CM = Bservice.countingCM(bno);
		
		model.addAttribute("CM",CM);
		model.addAttribute("vo",vo);
		model.addAttribute("CV",CV);
		
		PageMaker pageMaker = new PageMaker();
		cri.setPerPageNum(10);
		pageMaker.setDisplayPageNum(10);
		pageMaker.setCri(cri);
		model.addAttribute("pageMaker", pageMaker);
			
		return "/community/economycontent";
	}	
	
	// 경제 게시판 글 수정하기 GET
	// http://localhost:8080/economyupdate?bno=33
	@GetMapping(value = "/economyupdate")
	public String economyupdateGET(@RequestParam("bno") int bno, Model model, HttpSession session) throws Exception{
		
		BoardVO vo = Bservice.getBoardContent(bno);
					
		model.addAttribute("vo", vo);
		
		return "/community/economyupdate";
					
	}
				
	// 경제 게시판 글 수정하기 POST
	@PostMapping(value = "/economyupdate")
	public String economyupdatePOST(BoardVO vo,RedirectAttributes rttr,HttpSession session) throws Exception {
		
		Integer result = Bservice.updateBoard(vo);
							
		if(result > 0) {
						
		rttr.addFlashAttribute("result", "modOK");
								
		}
									
		return "redirect:/economy?page=1";
					
	}
		
	// 경제 게시판 삭제
	@GetMapping(value = "/economydelete")
	public String economydeleteGET(int bno,RedirectAttributes rttr,HttpSession session) throws Exception {
			
		Bservice.deleteBoard(bno);
					
		rttr.addFlashAttribute("result", "createOK");
					
		return "redirect:/economy?page=1";
	}	
	
//	 @RequestMapping("/selectcmt")
//	 @ResponseBody
//	 public List<Map<String,Object>> selectcmt(@RequestParam Map<String,Object> commandMap){
//	        mylog.debug("request: /selectcmt");
//	        List<Map<String,Object>> resultMap = null;
//	        int totalCmt = 0;
//	        try {
//	            int bbsidx = Integer.parseInt(commandMap.get("bbscmtidx").toString());
//	            
//	            resultMap = service.selectcmt(commandMap);
//	            totalCmt = service.getTotalCmt(bbsidx);//전체 댓글 개수
//	            resultMap.get(0).put("totalCmt", totalCmt);
//	        } catch (Exception e) {
//	            mylog.debug(e.getMessage());
//	        }
//	        return resultMap;
//	    }

	
	// =================================================================================
	
	// =================================================================================
	// 댓글 작성
//	@ResponseBody
	@PostMapping(value = "/insertcomment")
	public String insertcommentGET(CommentVO CV,RedirectAttributes rttr,HttpSession session,HttpServletRequest request) throws Exception {
			
		Bservice.insertComment(CV);
					
		rttr.addFlashAttribute("result", "delOK");
					
		String referer = request.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
		
		return "redirect:"+ referer; // 이전 페이지로 리다이렉트
//		return "";
	}	
	
	// 댓글 수정
	@GetMapping(value = "/updatecomment")
	public String updatecommentGET(CommentVO CV,RedirectAttributes rttr,HttpSession session,HttpServletRequest request) throws Exception {
		
		Bservice.updateComment(CV);
		
		rttr.addFlashAttribute("result", "upOK");
		
		String referer = request.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
		
		return "redirect:"+ referer; // 이전 페이지로 리다이렉트
//		return "";
	}	
	
	// 댓글 삭제
	@GetMapping(value = "/deletecomment")
	public String deletecommentGET(CommentVO CV,RedirectAttributes rttr,HttpSession session,HttpServletRequest request) throws Exception {
		
		Bservice.deleteComment(CV);
		
		rttr.addFlashAttribute("result", "delOK");
		
		String referer = request.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
		
		return "redirect:"+ referer; // 이전 페이지로 리다이렉트
//		return "";
	}	
	
	// 대댓글작성
	@ResponseBody
	@GetMapping(value = "/comment")
	public String getComment(CommentVO CV) throws Exception {
		
		
		return "";
	}
	
}
