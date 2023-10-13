package com.ezen.myproject.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezen.myproject.domain.BoardVO;
import com.ezen.myproject.domain.CommentVO;
import com.ezen.myproject.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> getList();

	BoardVO getDetail(int bno);

	int readcount(@Param("bno")int bno, @Param("cnt") int cnt);

	int update(BoardVO bvo);

	int remove(int bno);

	int getTotalCount(PagingVO pgvo);

	List<BoardVO> getPageList(PagingVO pgvo);

	int selectBno();

}
