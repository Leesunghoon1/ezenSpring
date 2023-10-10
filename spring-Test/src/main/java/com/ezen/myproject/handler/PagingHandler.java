package com.ezen.myproject.handler;

import com.ezen.myproject.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingHandler {
	
	private int startPage;
	private int endPage;
	private boolean prev; // 이전 페이지
	private boolean next; // 다음 페이지
	private int totalCount; // 전체 개수
	private PagingVO pgvo;
	
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		// 1 2 3 .... 10 => 10 / pageNo 1~10까지는 endPage 가 10
		// 11 12 .... 20 => 20  / pageNo 11~20까지는 endPage 가 20
		
		this.endPage =  
				(int)Math.ceil(pgvo.getPageNo() / (double)pgvo.getQty())*pgvo.getQty();
		
		this.startPage = endPage - 9;
		//시작 페이지
		
		int realEndPage = (int)Math.ceil(totalCount / (double)pgvo.getQty());
		//endPage는 10, 20, 30 .... 형식으로만 구성,
		//realEnd는 실제 마지막 페이지를 뜻함
		
		if(realEndPage < this.endPage) {
			this.endPage = realEndPage;
		}
		
		//startPage : 1, 11, 21
		this.prev = this.startPage > 1;
		
		this.next = this.endPage < realEndPage; 
		
	}
	

}
