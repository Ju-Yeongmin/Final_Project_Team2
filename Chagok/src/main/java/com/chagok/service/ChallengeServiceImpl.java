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
import com.chagok.domain.Criteria;
import com.chagok.domain.MinusVO;
import com.chagok.domain.PlusVO;
import com.chagok.domain.SearchCriteria;
import com.chagok.domain.UserVO;
import com.chagok.persistence.ChallengeDAO;

@Service
public class ChallengeServiceImpl implements ChallengeService{
	
	private static final Logger mylog = LoggerFactory.getLogger(ChallengeServiceImpl.class);

	@Inject
	private ChallengeDAO dao;
	
	@Override
	public ChallengeVO getChallengeInfo(Integer cno) {
		mylog.debug("getChallengeInfo(int cno) 호출");
		ChallengeVO vo = dao.getChallengeInfo(cno);
		mylog.debug("getChallengeInfo(cno) 처리 결과 : "+vo);
		return dao.getChallengeInfo(cno);
	}
	
	@Override
	public ChallengeVO getCt_top(Integer cno) {
		mylog.debug("getCt_top(int cno) 호출");
		ChallengeVO vo = dao.getCt_top(cno);
		return dao.getCt_top(cno);
	}

	@Override
	public List<Map<String, Object>> getPlusPeople(Integer cno){
		mylog.debug("getPlusPeople("+cno+") 호출");
		return dao.getPlusPeople(cno);
	}

	@Override
	public List<ChallengeVO> getChallengeList(Integer cno) {
		return dao.getChallengeList(cno);
	}

	@Override
	public List<ChallengeVO> getmyChallenge(String nick) {
		List<ChallengeVO> mychallengeList = dao.getmyChallenge(nick);
		mylog.debug("getmyChallenge(String nick) : "+mychallengeList);
		return dao.getmyChallenge(nick);
	}
	// 챌린지 취소하기 (챌린지 테이블에 닉네임 잘라주기)
	@Override
	public int cancelChallenge(String nick, Integer cno) {
		mylog.debug("service : cancelChallnege 호출");
		return dao.cancelChallenge(nick, cno);
	}
	
	// 저축형 챌린지 신청 취소 (플러스테이블에 mno랑 cno 없애주기)
	@Override
	public int cancelPlus(Integer mno, Integer cno) {
		mylog.debug("service : cancelPlus 호출");
		return dao.cancelPlus(mno, cno);
	}

	// 절약형 챌린지 신청 취소 (마이너스 테이블에 mno랑 cno 없애주기)
	@Override
	public int cancelMinus(Integer mno, Integer cno) {
		mylog.debug("service : cancelMinus 호출");
		return dao.cancelMinus(mno, cno);
	}

	@Override
	public List<Map<String, Object>> getMinusPeople(Integer cno) {
		mylog.debug("getMinusPeople("+cno+") 호출");
		return dao.getMinusPeople(cno);
	}

	@Override
	public Date getChallengeEndDate(Integer cno) {
		return dao.getChallengeEndDate(cno);
	}
	
	// 챌린지 등록
	@Override
   public Integer challengeRegist(ChallengeVO vo) throws Exception {
      Integer cno = dao.challengeRegist(vo);
    
      return cno;
      
   }
	
	// 챌린지 목록
	@Override
	public List<ChallengeVO> getChallengeList() throws Exception {
		
		return dao.getChallengeList();
	}
	
	
	// 중복챌린지 체크
	@Override
	public Integer samechallenge(Map<String, Object> map) {
		mylog.debug("service : samechallenge 호출");
		return dao.samechallenge(map);
	}

	@Override
	public int getCList(Integer cno) throws Exception {
		mylog.debug("getCList("+cno+") 호출");
		
		return  dao.getCList(cno);
		
	}
	// 저축형 챌린지 참여 - plus테이블에 mno랑 cno insert
	@Override
	public void joinplusInsert(Map<String, Object> map) {
		mylog.debug("service: joinplusInsert 호출");
		dao.joinplusInsert(map);
	}
	// 저축형&절약형 챌린지 참여 - challenge테이블 c_person에 ",닉네임" 업데이트하기
	@Override
	public void joinplusUpdate(Map<String, Object> map) {
		mylog.debug("service: joinplusUpdate 호출");
		dao.joinplusUpdate(map);
	}

	// 절약형 챌린지 참여 - minus테이블에 mno랑 cno insert
	@Override
	public void joinminusInsert(Map<String, Object> map) {
		mylog.debug("service: joinminusInsert 호출");
		dao.joinminusInsert(map);
		
	}

	// 챌린지 예치금 합산
	@Override
	public int getChallengeMoney(Integer cno) throws Exception {
		
		return  dao.getChallengeMoney(cno);
	}

	// 챌린지 성공/실패 조건 조회
	@Override
	public List<Map<String, Object>> getResult(Integer cno) throws Exception {
		
		return dao.getResult(cno);
	}

	// 챌린지 성공 인원 조회
	@Override
	public Integer getSuccess(Integer cno) throws Exception {
		
		return dao.getSuccess(cno);
	}

	
	// 명예의 전당 순위
	@Override
	public List<UserVO> ranking() throws Exception {
		mylog.debug(" ranking() 호출 ");
		
		return dao.ranking();
	}

	// 내 챌린지 (페이징)
	@Override
	public List<ChallengeVO> mychallengeAll(Criteria cri, String nick) throws Exception {
		return dao.mychallengeAll(cri, nick);
	}
	
	// 내 챌린지 총 개수 (페이징)
	@Override
	public Integer mychallengecnt(String nick) throws Exception {
		return dao.mychallengecnt(nick);
	}

	// 챌린지 메인 (페이징)
	@Override
	public List<ChallengeVO> cListM(SearchCriteria scri) throws Exception {
		
		return dao.cListM(scri);
	}
	// 챌린지 메인 총 개수 (페이징)
	@Override
	public Integer cListCountM(SearchCriteria scri) throws Exception {
		
		return dao.cListCountM(scri);
	}
	
	// 챌린지 메인 진행중 (페이징)
	@Override
	public List<ChallengeVO> cListMp(SearchCriteria scri) throws Exception {
		
		return dao.cListMp(scri);
	}
	// 챌린지 메인 진행중 총 개수 (페이징)
	@Override
	public Integer cListCountMp(SearchCriteria scri) throws Exception {
		
		return dao.cListCountMp(scri);
	}
	
	// 챌린지 메인 종료 (페이징)
	@Override
	public List<ChallengeVO> cListMe(SearchCriteria scri) throws Exception {
		
		return dao.cListMe(scri);
	}
	// 챌린지 메인 종료 총 개수 (페이징)
	@Override
	public Integer cListCountMe(SearchCriteria scri) throws Exception {
		
		return dao.cListCountMe(scri);
	}
	
	// 챌린지 목록 (페이징)
	@Override
	public List<ChallengeVO> cList(SearchCriteria scri) throws Exception {
		
		return dao.cList(scri);
	}
	
	// 챌린지 총 개수 (페이징)
	@Override
	public Integer cListCount(SearchCriteria scri) throws Exception {
		
		return dao.cListCount(scri);
	}

	// 챌린지 성공여부 (절약형)
	@Override
	public ChallengeVO getMoney(Integer mno) throws Exception {
		mylog.debug(" getMoney(mno) 호출 ");
		return dao.getMoney(mno);
	}

	// 가계부 가져오기 
	@Override
	public List<Map<String, Object>> getMinusAbook(Integer mno, Integer cno, Integer ctno) {
		mylog.debug(" getMinusAbook(mno,cno) 호출 ");
		return dao.getMinusAbook(mno, cno, ctno);
	}

	// 가계부 값 연동하기 
	@Override
	public void updateMoney(Integer mno, Integer ab_amount, Integer cno) {
		mylog.debug(" updateMoney(mno,ab_amount,cno) 호출 ");
		dao.updateMoney(mno,ab_amount,cno);
	}

	// 관리자 챌린지 승인
	@Override
	public void confirmChallenge(Integer status, Integer cno) throws Exception {
		mylog.debug("status : "+status+", cno : "+cno);
		dao.confirmChallenge(status, cno);
	}

	// 관리자 모달창 회원mno
	@Override
	public List<UserVO> adminmodal(Map<String, Object> map) throws Exception {
		mylog.debug("service : "+map);
		return dao.adminmodal(map);
	}


	// 비지니스 계좌 송금
	@Override
	public void sendBiz(BusinessAccountVO vo) throws Exception {
		dao.sendBiz(vo);
	}
	
	// 비지니스 계좌 송금시 플러스 테이블 업데이트 (pl_sum)
	@Override
	public void updatePlusSum(BusinessAccountVO vo) throws Exception {
		dao.updatePlusSum(vo);
	}

	// 내 plus 테이블 정보 가져오기
	@Override
	public PlusVO getPlusOne(int mno, int cno) throws Exception {
		return dao.getPlusOne(mno, cno);
	}

	
	@Override
	public List<ChallengeVO> chListAll(Criteria cri) throws Exception {
		return dao.chListAll(cri);
	}

	@Override
	public Integer chListCnt() throws Exception {
		return dao.chListCnt();
	}

	@Override
	public int getCtno(Integer cno) {
		mylog.debug(" getCtno() 호출 ");
		return dao.getCtno(cno);
	}

	

	// 챌린지 성공/실패 여부 가져옴(모든 회원)
	@Override
	public List<Map<String, Object>> challengeResultList(Integer cno) throws Exception {
		mylog.debug("challengeResultList() 호출");
		
		return dao.challengeResultList(cno);
	}

	// 챌린지 성공/실패 여부 가져옴(한 명만)
	@Override
	public Map<String, Object> challengeResult(Integer cno, Integer mno) throws Exception {
//		mylog.debug("challengeResult() 호출");
		
		return dao.challengeResult(cno, mno);
	}
	
	// update minus m_sum
	@Override
	public void updateMsum(Map<String, Object> map) throws Exception {
		mylog.debug(" updateMsum() 호출 ");
		dao.updateMsum(map);
	}

	


	

	
	
}
