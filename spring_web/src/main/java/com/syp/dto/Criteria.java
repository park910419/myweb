package com.syp.dto;

public class Criteria {
	
	private int currentPage;
	private int rowPerPage;
	private String searchField;
	private String searchText;
	
	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Criteria() {
		super();
	}
	
	public Criteria(int currentPage, int rowPerPage) {
		super();
		this.currentPage = currentPage;
		this.rowPerPage = rowPerPage;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

}
