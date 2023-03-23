package com.chagok.persistence;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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

@Repository
public class BoardDAOImpl implements BoardDAO{
	
	private static final Logger mylog = LoggerFactory.getLogger(BoardDAOImpl.class);
	
	// 디비 연결정보 필요함 => 의존관계 // mapper
	@Inject
	private SqlSession sqlSession;
	private static final String NAMESPACE = "com.chagok.mapper.chagokMapper";

	// 후기글 작성
	@Override
	public void createReview(BoardVO vo) {
		
		sqlSession.insert(NAMESPACE + ".create", vo);
	}
	
	// 게시판 글 목록	
	@Override
	public List<BoardVO> getBoard(Integer b_sort) {
		mylog.debug(" getBoard() 호출");
		
		List<BoardVO> boardList = sqlSession.selectList(NAMESPACE+".boardList",b_sort);
		
		mylog.debug("게시판 글 개수 : "+boardList.size()+"");
		
		return boardList;
	}

	// 게시글 내용
	@Override
	public BoardVO getBoardContent(Integer bno) {
		
		BoardVO vo = sqlSession.selectOne(NAMESPACE+".board", bno);
		
		return vo;
		
	}
	
	// 게시글 수정
	@Override
	public Integer updateBoard(BoardVO vo) throws Exception {
		
		mylog.debug("updateBoard() 호출 ");
		
		return sqlSession.update(NAMESPACE+".updateBoard", vo);
	}

	// 게시글 삭제
	@Override
	public void deleteBoard(Integer bno) throws Exception {
		
		mylog.debug(" deleteBoard() 호출 ");
		
		sqlSession.delete(NAMESPACE+".deleteBoard",bno);
		
	}

	// 게시글 등록 (후기 제외)
	@Override
	public void insertBoard(BoardVO vo) throws Exception {
		
		mylog.debug(" inserBoard 호출 ");
		
		sqlSession.insert(NAMESPACE+".insertBoard", vo);
		
	}

	// 게시판 + 챌린지
	@Override
	public Map<String, Object> getBoardChallenge(Integer cno) throws Exception {
		mylog.debug("getBoardChallenge 호출");
		Map<String, Object> boardChallenge = sqlSession.selectOne(NAMESPACE+".boardChallenge", cno);
		return boardChallenge;
	}

	// 공지리스트 조회
	@Override
	public List<BoardVO> getNBoardPage(Criteria cri) throws Exception {
		mylog.debug("  getBoardPage(Criteria cri) 페이징처리 ");
		
		return sqlSession.selectList(NAMESPACE + ".nboardPageNo",cri);
	}

	// 메인 공지리스트 조회
	@Override
	public List<BoardVO> getMNBoardPage(Criteria cri) throws Exception {
		mylog.debug("  getBoardPage(Criteria cri) 페이징처리 ");
	
		return sqlSession.selectList(NAMESPACE + ".MnboardPageNo",cri);
	}

	// 전체 공지게시판 글 개수 조회
	@Override
	public int NBoardCount() throws Exception {
		
		return sqlSession.selectOne(NAMESPACE+".nboardCount");
	
	}

	// 후기리스트 조회
	@Override
	public List<Map<String, Object>> getRBoardPage(Criteria cri) throws Exception {
		mylog.debug("  getBoardPage(Criteria cri) 페이징처리 ");
			
		return sqlSession.selectList(NAMESPACE + ".rboardPageNo",cri);
	}

	// 메인 후기리스트 조회
	@Override
	public List<Map<String, Object>> getMRBoardPage(Criteria cri) throws Exception {
		mylog.debug("  getBoardPage(Criteria cri) 페이징처리 ");
		
		return sqlSession.selectList(NAMESPACE + ".MrboardPageNo",cri);
	}

	// 전체 후기게시판 글 개수 조회
	@Override
	public int RBoardCount() throws Exception {
			
		return sqlSession.selectOne(NAMESPACE+".rboardCount");
		
	}

	// 자유게시판 조회
	@Override
	public List<BoardVO> getFBoardPage(Criteria cri) throws Exception {
		mylog.debug("  getBoardPage(Criteria cri) 페이징처리 ");
		
		return sqlSession.selectList(NAMESPACE + ".fboardPageNo",cri);
	}

	// 메인 자유게시판 조회
	@Override
	public List<BoardVO> getMFBoardPage(Criteria cri) throws Exception {
		mylog.debug("  getBoardPage(Criteria cri) 페이징처리 ");
		
		return sqlSession.selectList(NAMESPACE + ".MfboardPageNo",cri);
	}

	// 전체 자유게시판 글 개수 조회
	@Override
	public int FBoardCount() throws Exception {
		
		return sqlSession.selectOne(NAMESPACE+".fboardCount");
	
	}	
		
	// 경제게시판 조회
	@Override
	public List<BoardVO> getEBoardPage(Criteria cri) throws Exception {
		mylog.debug("  getBoardPage(Criteria cri) 페이징처리 ");
			
		return sqlSession.selectList(NAMESPACE + ".eboardPageNo",cri);
	}

	// 메인 경제게시판 조회
	@Override
	public List<BoardVO> getMEBoardPage(Criteria cri) throws Exception {
		mylog.debug("  getBoardPage(Criteria cri) 페이징처리 ");
		
		return sqlSession.selectList(NAMESPACE + ".MeboardPageNo",cri);
	}
		
	// 경제 자유게시판 글 개수 조회
	@Override
	public int EBoardCount() throws Exception {
			
		return sqlSession.selectOne(NAMESPACE+".eboardCount");
			
	}	
	
	// 내가 쓴 글 조회
	@Override
//	public List<BoardVO> getMyBoardWrite(String nick) throws Exception {
	public List<BoardVO> getMyBoardWrite(Criteria cri, String nick) throws Exception {
//	public List<Map<String, Object>> getMyBoardWrite(String nick,Criteria cri) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
//		return sqlSession.selectList(NAMESPACE + ".myBoardWrite",nick);
//		map.put("nick", nick);
//		map.put("pageStart", cri.getPageStart());
//		map.put("perPageNum", cri.getPerPageNum());
		map.put("nick", nick);
		map.put("pageStart", cri.getPageStart());
		map.put("perPageNum", cri.getPerPageNum());
		return sqlSession.selectList(NAMESPACE + ".myBoardWrite",map);
	}

	// 내가 쓴 글 개수 조회
	@Override
//	public int MyBoardWriteCnt() throws Exception {
	public Integer MyBoardWriteCnt(String nick) throws Exception {
					
		return sqlSession.selectOne(NAMESPACE+".myBoardWriteCnt");
				
	}

	// 모든 게시판 글 조회
	@Override
	public List<BoardVO> getAllboardList(Criteria cri) throws Exception {
		
		return sqlSession.selectList(NAMESPACE+".AllboardList");
	}

	// 댓글 조회
	@Override
//	public List<CommentVO> getComment(Integer bno) {
//	mylog.debug("BoardDAOImpl 댓글조회");
//	return sqlSession.selectList(NAMESPACE+".comment",map);
//}
	
//	public Map<String, Object> getComment(Criteria cri,Integer bno) {
	public List<CommentVO> getComment(Criteria cri,Integer bno) {
		mylog.debug("BoardDAOImpl 댓글조회");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("pageStart", cri.getPageStart());
		map.put("perPageNum", cri.getPerPageNum());
		return sqlSession.selectList(NAMESPACE+".comment",map);
		
	}

	// 댓글 수정
	@Override
	public Integer updateComment(CommentVO CV) throws Exception {
		
		return sqlSession.update(NAMESPACE+".updateComment", CV);
	}

	// 댓글 삭제
	@Override
	public void deleteComment(CommentVO CV) throws Exception {
		
		sqlSession.delete(NAMESPACE+".deleteComment",CV);
	}

	// 댓글 작성
	@Override
	public void insertComment(CommentVO CV) throws Exception {
		
		sqlSession.insert(NAMESPACE+".insertComment", CV);
	}

	// 댓글 갯수(해당 글번호)
	@Override
	public Integer countingCM(Integer bno) throws Exception {
		
		return sqlSession.selectOne(NAMESPACE+".countingCM",bno);
	}
	
	
	
}