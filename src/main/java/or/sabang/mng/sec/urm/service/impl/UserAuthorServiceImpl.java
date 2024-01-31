package or.sabang.mng.sec.urm.service.impl;

import java.util.List;

import or.sabang.mng.sec.urm.service.UserAuthor;
import or.sabang.mng.sec.urm.service.UserAuthorService;
import or.sabang.mng.sec.urm.service.UserAuthorVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 사용자권한에 관한 ServiceImpl 클래스를 정의한다.
 * @author 공통서비스 개발팀 이문준
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.11  이문준          최초 생성
 *
 * </pre>
 */

@Service("userAuthorService")
public class UserAuthorServiceImpl extends EgovAbstractServiceImpl implements UserAuthorService {
	
	@Resource(name="userAuthorDAO")
    private UserAuthorDAO userAuthorDAO;
	
	/**
	 * 사용자별 할당된 권한목록 조회
	 * @param userAuthorVO UserAuthorVO
	 * @return List<UserAuthorVO>
	 * @exception Exception
	 */
	public List<UserAuthorVO> selectUserAuthorList(UserAuthorVO userAuthorVO) throws Exception {
		return userAuthorDAO.selectUserAuthorList(userAuthorVO);
	}
	
	/**
	 * 사용자 권한조회
	 * @param String uniqId
	 * @return UserAuthorVO
	 * @throws Exception
	 */
	public UserAuthor selectUserAuthor(String uniqId) throws Exception {
		return userAuthorDAO.selectUserAuthor(uniqId);
	}
	
	/**
	 * 사용자에 해당하는 사용자에게 시스템 메뉴/접근권한을 일괄 할당
	 * @param userAuthor UserAuthor
	 * @exception Exception
	 */
	public void insertUserAuthor(UserAuthor userAuthor) throws Exception {
		userAuthorDAO.insertUserAuthor(userAuthor);
	}

	/**
	 * 사용자별 시스템 메뉴 접근권한을 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param userAuthor UserAuthor
	 * @exception Exception
	 */
	public void updateUserAuthor(UserAuthor userAuthor) throws Exception {
		userAuthorDAO.updateUserAuthor(userAuthor);
	}

	/**
	 * 불필요한 사용자권한를 조회하여 데이터베이스에서 삭제
	 * @param userAuthor UserAuthor
	 * @exception Exception
	 */
	public void deleteUserAuthor(UserAuthor userAuthor) throws Exception {
		userAuthorDAO.deleteUserAuthor(userAuthor);
	}

    /**
	 * 사용자권한 목록조회 카운트를 반환한다
	 * @param userAuthorVO UserAuthorVO
	 * @return int
	 * @exception Exception
	 */
	public int selectUserAuthorListTotCnt(UserAuthorVO userAuthorVO) throws Exception {
		return userAuthorDAO.selectUserAuthorListTotCnt(userAuthorVO);
	}	
}