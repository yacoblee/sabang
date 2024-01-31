package or.sabang.mng.sec.urm.service;

import java.util.List;


/**
 * 사용자권한에 대한 Vo 클래스를 정의한다.
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
 *   2009.03.20  이문준          최초 생성
 *
 * </pre>
 */

public class UserAuthorVO extends UserAuthor {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 사용자권한목록
	 */
	List <UserAuthorVO> userAuthorList;
	/**
	 * 사용자목록
	 */
	List <UserAuthorVO> userList;	

	
	/**
	 * userAuthorList attribute 를 리턴한다.
	 * @return List<UserAuthorVO>
	 */
	public List<UserAuthorVO> getUserAuthorList() {
		return userAuthorList;
	}
	/**
	 * userAuthorList attribute 값을 설정한다.
	 * @param userAuthorList List<UserAuthorVO> 
	 */
	public void setUserAuthorList(List<UserAuthorVO> userAuthorList) {
		this.userAuthorList = userAuthorList;
	}
	/**
	 * userList attribute 를 리턴한다.
	 * @return List<UserAuthorVO>
	 */
	public List<UserAuthorVO> getUserList() {
		return userList;
	}
	/**
	 * userList attribute 값을 설정한다.
	 * @param userList List<UserAuthorVO> 
	 */
	public void setUserList(List<UserAuthorVO> userList) {
		this.userList = userList;
	}
	
}