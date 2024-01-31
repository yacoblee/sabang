package or.sabang.mng.mnu.mpm.web;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.mng.log.wlg.service.WebLog;
import or.sabang.mng.log.wlg.service.WebLogService;
import or.sabang.mng.mnu.mpm.service.MenuManageService;
import or.sabang.mng.mnu.mpm.service.MenuManageVO;

import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MenuCreatInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "meunManageService")
    private MenuManageService menuManageService;
	
	/**
	 * 메뉴접근권한을 체크한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	/* Authenticated  */
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			String clientIp = request.getRemoteAddr();
			String clientUrl = request.getRequestURI();
			JSONArray accesHeadMenu = null;
			JSONArray accesLeftMenu = null;
			
			if(user.getAccesMenu() == null) {
		        MenuManageVO menuManageVO = new MenuManageVO();
		        menuManageVO.setTmpId(user.getId());
		    	menuManageVO.setTmpPassword(user.getPassword());
		    	menuManageVO.setTmpName(user.getName());
		    	menuManageVO.setTmpUniqId(user.getUniqId());

		    	try {
		    		String accesMenu = menuManageService.selectMainMenuHead(menuManageVO);
		    		if(accesMenu != null) {
		    			accesHeadMenu = new JSONArray(new JSONTokener(accesMenu));
		    		}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
				user.setAccesMenu(accesHeadMenu);
			}else {
				accesHeadMenu = user.getAccesMenu();
				accesLeftMenu = new JSONArray();
				
				int menuId = 0;
		        String[] subPathSplit = clientUrl.split("/");
		        String[] newSubPathSplit = IntStream.range(0, subPathSplit.length)
		                .filter(idx -> idx != (subPathSplit.length-1))
		                .mapToObj(idx -> subPathSplit[idx])
		                .toArray(String[]::new);
		        
		        String subPath = String.join("/",newSubPathSplit);
		        
		        for (int i = 0; i < accesHeadMenu.length(); i++) {
		            JSONObject menu = (JSONObject)accesHeadMenu.get(i);
		            
		            if(menu.getString("chkURL").contains(subPath)) {
		                menuId = menu.getInt("menuNo");
		            }
		        }
		        
				if(menuId != 0) {
					for (int i = 0; i < accesHeadMenu.length(); i++) {
		    			JSONObject menu = (JSONObject)accesHeadMenu.get(i);
		    			if(menu.getInt("upperMenuId") > 0) {
		    				String targetId = String.valueOf(menuId).substring(0,2);
			    			String upperId = String.valueOf(menu.getInt("upperMenuId")).substring(0,2);
			    			if(targetId.equals(upperId)) {
			    				accesLeftMenu.put(menu);
							}
		    			}
					}
					user.setAccesLeftMenu(accesLeftMenu);
				}
			}
    	}
		
		return true;
	}
}
