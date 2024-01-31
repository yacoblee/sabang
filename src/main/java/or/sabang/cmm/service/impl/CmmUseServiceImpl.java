package or.sabang.cmm.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import egovframework.com.cmm.ComDefaultCodeVO;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.cmm.service.CmmUseService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovCmmUseServiceImpl.java
 * @Description : 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("cmmUseService")
public class CmmUseServiceImpl extends EgovAbstractServiceImpl implements CmmUseService {

    @Resource(name = "cmmUseDAO")
    private CmmUseDAO cmmUseDAO;

    /**
     * 공통코드를 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public List<CmmnDetailCode> selectCmmCodeDetail(ComDefaultCodeVO vo) throws Exception {
    	return cmmUseDAO.selectCmmCodeDetail(vo);
    }

    /**
     * ComDefaultCodeVO의 리스트를 받아서 여러개의 코드 리스트를 맵에 담아서 리턴한다.
     *
     * @param voList
     * @return
     * @throws Exception
     */
    public Map<String, List<CmmnDetailCode>> selectCmmCodeDetails(List<?> voList) throws Exception {
		ComDefaultCodeVO vo;
		Map<String, List<CmmnDetailCode>> map = new HashMap<String, List<CmmnDetailCode>>();

		Iterator<?> iter = voList.iterator();
		while (iter.hasNext()) {
		    vo = (ComDefaultCodeVO)iter.next();
		    map.put(vo.getCodeId(), cmmUseDAO.selectCmmCodeDetail(vo));
		}

		return map;
    }

    /**
     * 조직정보를 코드형태로 리턴한다.
     *
     * @param 조회조건정보 vo
     * @return 조직정보 List
     * @throws Exception
     */
    public List<CmmnDetailCode> selectOgrnztIdDetail(ComDefaultCodeVO vo) throws Exception {
    	return cmmUseDAO.selectOgrnztIdDetail(vo);
    }

    /**
     * 그룹정보를 코드형태로 리턴한다.
     *
     * @param 조회조건정보 vo
     * @return 그룹정보 List
     * @throws Exception
     */
    public List<CmmnDetailCode> selectGroupIdDetail(ComDefaultCodeVO vo) throws Exception {
    	return cmmUseDAO.selectGroupIdDetail(vo);
    }
    
    /**
     * 관할정보를 코드형태로 리턴한다.
     * @param 조회조건정보 vo
     * @return 관할정보 List
     * @throws Exception
     */
    public List<CmmnDetailCode> selectRegionDetail(ComDefaultCodeVO vo) throws Exception {
    	return cmmUseDAO.selectRegionDetail(vo);
    }

    /**
     * 프록시 설정.
     * @param request
     * @return response
     * @throws Exception
     */
	@Override
	public void proxy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpURLConnection http = null;
		ServletOutputStream sos = null;

		try {
			String urlParam = getParam( request, "URL" );
			System.out.println( urlParam );
			if ( urlParam == null || urlParam.trim().length() == 0 ) {
				response.sendError( HttpServletResponse.SC_BAD_REQUEST );
				return;
			}
			boolean doPost = request.getMethod().equalsIgnoreCase( "POST" );
			URL url = new URL( urlParam.replaceAll( " ", "%20" ) );
			http = (HttpURLConnection) url.openConnection();
			Enumeration<String> hNames = request.getHeaderNames();
			while ( hNames.hasMoreElements() ) {
				String key = hNames.nextElement();
				if ( !key.equalsIgnoreCase( "Host" ) ) {
					http.setRequestProperty( key, request.getHeader( key ) );
				}
			}
			http.setDoInput( true );
			http.setDoOutput( doPost );
			byte[] buffer = new byte[8192];
			int read = -1;
			if ( doPost ) {
				OutputStream os = http.getOutputStream();
				ServletInputStream sis = request.getInputStream();
				while ( (read = sis.read( buffer )) != -1 ) {
					os.write( buffer, 0, read );
				}
				os.close();
			}
			InputStream is = http.getInputStream();
			response.setStatus( http.getResponseCode() );
			Map<String, List<String>> headerKeys = http.getHeaderFields();
			Set<String> keySet = headerKeys.keySet();
			Iterator<String> iter = keySet.iterator();
			while ( iter.hasNext() ) {
				String key = iter.next();
				String value = http.getHeaderField( key );
				if ( key != null && value != null ) {
					if ( value.indexOf( "text/xml" ) > -1 && value.indexOf( "ISO-8859-1" ) > -1 ) {
						value = value.replaceAll( "ISO-8859-1", "UTF-8" );
					}
					if ( !(key.equals( "Transfer-Encoding" )) ) {
						response.setHeader( key, value );
					}
				}
			}
			sos = response.getOutputStream();
			response.resetBuffer();
			while ( (read = is.read( buffer )) != -1 ) {
				sos.write( buffer, 0, read );
			}
			sos.println();
			response.flushBuffer();
			sos.close();
			http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * request 파라미터 설정.
     * @param request
     * @return {String}
     * @throws Exception
     */
	public static String getParam(HttpServletRequest request, String name) {
		if ( request.getAttribute( "Map" ) != null ) {
			return "";
		} else {
			String paramsToString = "";
			Enumeration<String> pNames = request.getParameterNames();
			while ( pNames.hasMoreElements() ) {
				String key = pNames.nextElement();
				String value = request.getParameter( key );
				try {
					if ( !key.equalsIgnoreCase( "URL" ) ) {
						value = URLEncoder.encode( value, "UTF-8" );
					}
				} catch ( Exception e ) {
					e.printStackTrace();
				}
				paramsToString += ("&" + key + "=" + value);
			}
			return paramsToString.replaceFirst( "&url=", "" );
		}
	}

	/**
	 * API KEY 목록 조회
	 * @author ipodo
	 * @name selectAPIKeyList
	 * @param map
	 * @return
	 */
	@Override
	public List<EgovMap> selectAPIKeyList(HashMap<String, Object> map) {
		return cmmUseDAO.selectAPIKeyList(map);
	}
	
	 /**
     * 땅밀림 코드정보 조회
     *
     * @param vo
     * @return
     * @throws Exception
     */
	public String selectLcpCodeDetail(ComDefaultCodeVO vo){
		return cmmUseDAO.selectLcpCodeDetail(vo);
	}
	
	/**
	 * 취약지역 실태조사 코드정보 조회
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CmmnDetailCode> selectWkaCodeDetail(ComDefaultCodeVO vo){
		return cmmUseDAO.selectWkaCodeDetail(vo);
	}

	/**
	 * 조사 코드 목록 조회
	 * @author DEVWORK
	 * @return
	 * @since 2023. 8. 18.
	 * @modified
	 * @see or.sabang.cmm.service.CmmUseService#selectSvyCodeLst()
	 */
	@Override
	public List<EgovMap> selectSvyCodeLst() {
		return cmmUseDAO.selectSvyCodeLst();
	}
}
