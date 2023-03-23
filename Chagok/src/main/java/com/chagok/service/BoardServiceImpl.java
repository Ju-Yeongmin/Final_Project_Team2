package com.chagok.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chagok.domain.AbookVO;
import com.chagok.domain.BoardVO;
import com.chagok.domain.BusinessAccountVO;
import com.chagok.domain.ChallengeVO;
import com.chagok.domain.CommentVO;
import com.chagok.domain.Criteria;
import com.chagok.domain.MinusVO;
import com.chagok.domain.PlusVO;
import com.chagok.domain.SearchCriteria;
import com.chagok.domain.UserVO;
import com.chagok.persistence.BoardDAO;
import com.chagok.persistence.ChallengeDAO;

@Service
public class BoardServiceImpl implements BoardService{
	
	private static final Logger mylog = LoggerFactory.getLogger(BoardServiceImpl.class);

	@Inject
	private BoardDAO dao;
	
	@Override
	public void createReview(BoardVO vo){
		dao.createReview(vo);
		
	}

	@Override
	public List<BoardVO> getBoardList(Integer b_sort) {
		
		return dao.getBoard(b_sort);
	}

	@Override
	public BoardVO getBoardContent(Integer bno) {
		
		return dao.getBoardContent(bno);
	}
	
	// 게시판 글 수정
	@Override
	public Integer updateBoard(BoardVO vo) throws Exception {
		mylog.debug("updateBoard() 호출 ");
		
		return dao.updateBoard(vo);
	}


	// 게시판 글 삭제
	@Override
	public void deleteBoard(Integer bno) throws Exception {
		mylog.debug(" deleteBoard() 호출 ");
		
		dao.deleteBoard(bno);
		
	}

	@Override
	public void insertBoard(BoardVO vo) throws Exception {
		mylog.debug(" insertBoard 호출 ");
		
		dao.insertBoard(vo);
		
	}

	// 챌린지 + 게시판 리스트
	@Override
	public Map<String, Object> getBoardChallenge(Integer cno) throws Exception {
		mylog.debug(" boardChallenge 호출 ");
		return dao.getBoardChallenge(cno);
	}

	// 공지글 정보 조회(페이징처리 cri)
	@Override
	public List<BoardVO> getNBoardPage(Criteria cri) throws Exception {
		mylog.debug(" getNBoardPage(Criteria cri) 호출 ");
		return dao.getNBoardPage(cri);
	}

	// 메인 공지글 정보 조회(페이징처리 cri)
	@Override
	public List<BoardVO> getMNBoardPage(Criteria cri) throws Exception {
		mylog.debug(" getNBoardPage(Criteria cri) 호출 ");
		return dao.getMNBoardPage(cri);
	}

	// 전체 공지게시판 글 개수 조회
	@Override
	public int NboardCount() throws Exception {
		mylog.debug("NboardCount() 호출");
		return dao.NBoardCount();
	}
	
	// 전체 후기게시판 정보 조회(페이징처리 cri)
	@Override
	public List<Map<String, Object>> getRBoardPage(Criteria cri) throws Exception {
		mylog.debug(" getRBoardPage(Criteria cri) 호출 ");
		return dao.getRBoardPage(cri);
	}

	// 메인 후기게시판 정보 조회(페이징처리 cri)
	@Override
	public List<Map<String, Object>> getMRBoardPage(Criteria cri) throws Exception {
		mylog.debug(" getRBoardPage(Criteria cri) 호출 ");
		return dao.getMRBoardPage(cri);
	}

	// 전체 후기게시판 글 개수 조회
	@Override
	public int RboardCount() throws Exception {
		mylog.debug("RboardCount() 호출");
		return dao.RBoardCount();
	}	

	// 자유게시판 정보 조회(페이징처리 cri)
	@Override
	public List<BoardVO> getFBoardPage(Criteria cri) throws Exception {
		mylog.debug(" getFBoardPage(Criteria cri) 호출 ");
		return dao.getFBoardPage(cri);
	}

	// 메인 자유게시판 정보 조회(페이징처리 cri)
	@Override
	public List<BoardVO> getMFBoardPage(Criteria cri) throws Exception {
		mylog.debug(" getFBoardPage(Criteria cri) 호출 ");
		return dao.getMFBoardPage(cri);
	}

	// 전체 자유게시판 글 개수 조회
	@Override
	public int FboardCount() throws Exception {
		mylog.debug("NboardCount() 호출");
		return dao.FBoardCount();
	}

	// 경제게시판 정보 조회(페이징처리 cri)
	@Override
	public List<BoardVO> getEBoardPage(Criteria cri) throws Exception {
		mylog.debug(" getEBoardPage(Criteria cri) 호출 ");
		return dao.getEBoardPage(cri);
	}

	// 메인 경제게시판 정보 조회(페이징처리 cri)
	@Override
	public List<BoardVO> getMEBoardPage(Criteria cri) throws Exception {
		mylog.debug(" getEBoardPage(Criteria cri) 호출 ");
		return dao.getMEBoardPage(cri);
	}
	
	// 경제 자유게시판 글 개수 조회
	@Override
	public int EboardCount() throws Exception {
		mylog.debug("EboardCount() 호출");
		return dao.EBoardCount();
	}
		
	// 내가 쓴 글 조회(페이징처리 cri)	
	@Override
//	public List<BoardVO> getMyBoardWrite(String nick) throws Exception {
	public List<BoardVO> getMyBoardWrite(Criteria cri, String nick) throws Exception {
//	public List<Map<String, Object>> getMyBoardWrite(String nick,Criteria cri) throws Exception {
		
//		return dao.getMyBoardWrite(nick);
		return dao.getMyBoardWrite(cri, nick);
	}	
	
	// 내가 쓴 글 개수 조회
	@Override
//	public int MyBoardWriteCnt() throws Exception {
	public Integer MyBoardWriteCnt(String nick) throws Exception {
		
//		return dao.MyBoardWriteCnt();
		return dao.MyBoardWriteCnt(nick);
	}

	// 전체 게시판 조회
	@Override
	public List<BoardVO> getAllboardList(Criteria cri) throws Exception {
		
		return dao.getAllboardList(cri);
	}

	// 댓글 조회(유저프로필+페이징처리)
	@Override
//	public List<CommentVO> getComment(Integer bno) throws Exception {
	public List<CommentVO> getComment(Criteria cri,Integer bno) throws Exception {
		mylog.debug("BoardServiceImpl 댓글 조회");
		return dao.getComment(cri,bno);
	}

	// 댓글 수정
	@Override
	public Integer updateComment(CommentVO CV) throws Exception {
		
		return dao.updateComment(CV);
	}

	// 댓글 삭제
	@Override
	public void deleteComment(CommentVO CV) throws Exception {
		
		dao.deleteComment(CV);
		
	}

	// 댓글 작성
	@Override
	public void insertComment(CommentVO CV) throws Exception {
		
		dao.insertComment(CV);
		
	}

	// 댓글 총 갯수(해당 글번호)
	@Override
	public Integer countingCM(Integer bno) throws Exception {
		
		return dao.countingCM(bno);
	}
	
	

	
	
}
