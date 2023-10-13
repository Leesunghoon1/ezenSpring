package com.ezen.myproject.repository;

import java.util.List;

import com.ezen.myproject.domain.CommentVO;

public interface CommentDAO {

	int insert(CommentVO cvo);

	List<CommentVO> getList(int bno);

	int update(CommentVO cvo);

	int del(int cno);

	CommentVO getCommentCount(int bno);

	int getCommentCount();

}
