package or.sabang.sys.bbs.nyd.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysIntroController {
	
	@RequestMapping("/sys/bbs/nyd/selectSystemIntrcn.do")
	public String selectSystemIntrcn() throws Exception{
		return "sys/bbs/nyd/systemIntrcn";
	}
	
}
