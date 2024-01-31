package or.sabang.cmm.web;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

public class CustomPaginationRenderer extends AbstractPaginationRenderer{
	
	public CustomPaginationRenderer() {
		firstPageLabel = "<button id=\"StartPagi\" type=\"button\" onclick=\"{0}({1}); return false;\"><img src='/images/sub/list_arrow_first.png\' alt=\"맨처음\"/></button>";
		previousPageLabel = "<button id=\"prevPagi\" type=\"button\" onclick=\"{0}({1}); return false;\"><img src='/images/sub/list_arrow_forward.png\' alt=\"이전\"></button>";
		currentPageLabel = "<a href=\"#none\" class=\"M_on\">{0}</a>";
		otherPageLabel = "<a href=\"#none\" onclick=\"{0}({1}); return false;\">{2}</a>";
		nextPageLabel = "<button id=\"nextPagi\" type=\"button\" onclick=\"{0}({1}); return false;\"><img src='/images/sub/list_arrow_next.png\' alt=\"다음\"></button>";
		lastPageLabel = "<button id=\"EndPagi\" type=\"button\" onclick=\"{0}({1}); return false;\"><img src='/images/sub/list_arrow_last.png\' alt=\"맨끝\"></button>";
	}

}