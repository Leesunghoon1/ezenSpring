package com.ezen.myproject.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.myproject.domain.CommentVO;
import com.ezen.myproject.repository.CommentDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService{

	@Inject
	private CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		// TODO Auto-generated method stub
		return cdao.insert(cvo);
	}

	@Override
	public List<CommentVO> getList(int bno) {
		// TODO Auto-generated method stub
				return cdao.getList(bno);
	}

	@Override
	public int edit(CommentVO cvo) {
		// TODO Auto-generated method stub
		return cdao.update(cvo);
	}

	@Override
	public int remove(int cno) {
		return cdao.del(cno);
	}


}
