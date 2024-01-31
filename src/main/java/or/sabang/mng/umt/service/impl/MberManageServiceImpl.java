package or.sabang.mng.umt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import or.sabang.mng.umt.service.MberManageService;
import or.sabang.mng.umt.service.MberManageVO;
import or.sabang.mng.umt.service.UserDefaultVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 일반회원관리에 관한비지니스클래스를 정의한다.
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *   2017.07.21  장동한 			로그인인증제한 작업
 *
 * </pre>
 */
@Service("mberManageService")
public class MberManageServiceImpl extends EgovAbstractServiceImpl implements MberManageService {

	/** mberManageDAO */
	@Resource(name="mberManageDAO")
	private MberManageDAO mberManageDAO;

	/** egovUsrCnfrmIdGnrService */
	@Resource(name="egovUsrCnfrmIdGnrService")
	private EgovIdGnrService idgenService;

	/**
	 * 사용자의 기본정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param mberManageVO 일반회원 등록정보
	 * @return result 등록결과
	 * @throws Exception
	 */
	@Override
	public int insertMber(MberManageVO mberManageVO) throws Exception  {
		//고유아이디 셋팅
		String uniqId = idgenService.getNextStringId();
		mberManageVO.setUniqId(uniqId);
		//패스워드 암호화
		String pass = EgovFileScrty.encryptPassword(mberManageVO.getPassword(), EgovStringUtil.isNullToString(mberManageVO.getMberId()));//KISA 보안약점 조치 (2018-10-29, 윤창원)
		mberManageVO.setPassword(pass);

		int result = mberManageDAO.insertMber(mberManageVO);
		return result;
	}

	/**
	 * 기 등록된 사용자 중 검색조건에 맞는 일반회원의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param uniqId 상세조회대상 일반회원아이디
	 * @return mberManageVO 일반회원상세정보
	 * @throws Exception
	 */
	@Override
	public MberManageVO selectMber(String uniqId) {
		MberManageVO mberManageVO = mberManageDAO.selectMber(uniqId);
		return mberManageVO;
	}

	/**
	 * 기 등록된 회원 중 검색조건에 맞는 회원들의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param userSearchVO 검색조건
	 * @return List<MberManageVO> 일반회원목록정보
	 */
	@Override
	public List<MberManageVO> selectMberList(UserDefaultVO userSearchVO) {
		return mberManageDAO.selectMberList(userSearchVO);
	}

    /**
     * 일반회원 총 갯수를 조회한다.
     * @param userSearchVO 검색조건
     * @return 일반회원총갯수(int)
     */
    @Override
	public int selectMberListTotCnt(UserDefaultVO userSearchVO) {
    	return mberManageDAO.selectMberListTotCnt(userSearchVO);
    }

	/**
	 * 화면에 조회된 일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param mberManageVO 일반회원수정정보
	 * @throws Exception
	 */
	@Override
	public int updateMber(MberManageVO mberManageVO) throws Exception {
		//패스워드 암호화
		String pass = EgovFileScrty.encryptPassword(mberManageVO.getPassword(), EgovStringUtil.isNullToString(mberManageVO.getMberId()));//KISA 보안약점 조치 (2018-10-29, 윤창원)
		mberManageVO.setPassword(pass);
		int result = mberManageDAO.updateMber(mberManageVO);
		return result;
	}

	/**
	 * 화면에 조회된 사용자의 정보를 데이터베이스에서 삭제
	 * @param checkedIdForDel 삭제대상 일반회원아이디
	 * @throws Exception
	 */
//	@Override
//	public int deleteMber(String checkedIdForDel)  {
//		String [] delId = checkedIdForDel.split(",");
//		for (int i=0; i<delId.length ; i++){
//			String [] id = delId[i].split(":");
////			if (id[0].equals("USR03")){
////		        //업무사용자(직원)삭제
////				userManageDAO.deleteUser(id[1]);
////			}else if(id[0].equals("USR01")){
//				//일반회원삭제
//				mberManageDAO.deleteMber(id[1]);
////			}else if(id[0].equals("USR02")){
////				//기업회원삭제
////				entrprsManageDAO.deleteEntrprsmber(id[1]);
////			}
//		}
//	}
	@Override
	public int deleteMber(String uniqId) throws Exception {
		int result = mberManageDAO.deleteMber(uniqId);
		return result;
	}

	/**
	 * 비밀번호를 초기화.
	 * @param mberId 회원아이디
	 * @return 성공여부(boolean)
	 * @throws Exception
	 */
	@Override
	public int resetMberPassword(MberManageVO mberManageVO) throws Exception {
		//패스워드 암호화
		//String pass = EgovFileScrty.encryptPassword("1234", EgovStringUtil.isNullToString(memberVO.getUserId()));//KISA 보안약점 조치 (2018-10-29, 윤창원)
		String resetPassword = "sabang1234!";
		mberManageVO.setPassword(EgovFileScrty.encryptPassword(resetPassword, mberManageVO.getUniqId()));
		
		int result = mberManageDAO.updatePassword(mberManageVO);
		
		return result;
	}

	/**
	 * 일반회원암호수정
	 * @param mberManageVO 일반회원수정정보(비밀번호)
	 * @throws Exception
	 */
	@Override
	public int updatePassword(MberManageVO mberManageVO) {
		int result = mberManageDAO.updatePassword(mberManageVO);
		return result;
	}

	/**
	 * 일반회원이 비밀번호를 기억하지 못할 때 비밀번호를 찾을 수 있도록 함
	 * @param passVO 일반회원암호 조회조건정보
	 * @return mberManageVO 일반회원암호정보
	 * @throws Exception
	 */
	@Override
	public MberManageVO selectPassword(MberManageVO passVO) {
		MberManageVO mberManageVO = mberManageDAO.selectPassword(passVO);
		return mberManageVO;
	}
	
	/**
     * 가입승인 대기 중인 일반회원의 가입을 승인한다.
     * @param uniqId 일반회원고유아이디
     * @throws Exception
     */
	@Override
	public int updateAccept(String uniqId) {
		int result = mberManageDAO.updateAccept(uniqId);
		return result;
	}
	/**
	 * 로그인인증제한 해제 
	 * @param mberManageVO 일반회원정보
	 * @return void
	 * @throws Exception
	 */
	@Override
	public int updateLockIncorrect(MberManageVO mberManageVO) throws Exception {
		int result = mberManageDAO.updateLockIncorrect(mberManageVO);
		return result;
	}
	
	/**
     * 회원정보 목록을 조회하여 엑셀로 저장한다.
     * @param userSearchVO
     * @return
     * @throws Exception
     */
	@Override
	public Map<String, Object> selectMberListExcel(UserDefaultVO userSearchVO) throws Exception{
		List<?> result = mberManageDAO.selectMberListExcel(userSearchVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", result);
		
		return map;
	}
}