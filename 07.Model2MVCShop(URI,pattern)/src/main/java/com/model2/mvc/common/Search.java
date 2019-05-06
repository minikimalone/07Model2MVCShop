package com.model2.mvc.common;


public class Search {
	
	///Field
	private int currentPage;
	private String searchCondition;
	private String searchKeyword;
	private String menu;
	private int pageSize;
	
	//==> 리스트화면 currentPage에 해당하는 회원정보를 ROWNUM 사용 SELECT 위해 추가된 Field 
		//==> UserMapper.xml 의 
		//==> <select  id="getUserList"  parameterType="search"	resultMap="userSelectMap">
		//==> 참조
		private int endRowNum;
		private int startRowNum;
	
	
	///Constructor
	public Search() {
	}
	
	///Method
	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int paseSize) {
		this.pageSize = paseSize;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int curruntPage) {
		this.currentPage = curruntPage;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	//==> Select Query 시 ROWNUM 마지막 값 
		public int getEndRowNum() {
			return getCurrentPage()*getPageSize();
		}
		//==> Select Query 시 ROWNUM 시작 값
		public int getStartRowNum() {
			return (getCurrentPage()-1)*getPageSize()+1;
		}

		@Override
		public String toString() {
			return "Search [currentPage=" + currentPage + ", searchCondition="
					+ searchCondition + ", searchKeyword=" + searchKeyword
					+ ", pageSize=" + pageSize + ", menu=" + menu+ ", endRowNum=" + endRowNum
					+ ", startRowNum=" + startRowNum + "]";
		}
	
}