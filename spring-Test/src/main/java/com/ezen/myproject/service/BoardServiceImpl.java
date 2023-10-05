package com.ezen.myproject.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.myproject.domain.BoardVO;
import com.ezen.myproject.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@Service //서비스
@Slf4j //로그
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO bdao;

	@Override
	public int register(BoardVO bvo) {
		log.info("register chaeak 2");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList() {
		log.info("list chaeak 2");
		return bdao.getList();
	}

	@Override
	public BoardVO getDetail(int bno) {
		log.info("list chaeak 2");
		//read_count + 1
		int cnt = 1;
		bdao.readcount(bno, cnt);
		return bdao.getDetail(bno);
	}

	@Override
	public int modify(BoardVO bvo) {
		log.info("register chaeak 2");
		//수정할 때 들어가는 부당 2개 빼주기
		//read_count - 2
		int cnt = -2;
		bdao.readcount(bvo.getBno(), cnt);
		return bdao.update(bvo);
	}

	@Override
	public int remove(int bno) {
		// TODO Auto-generated method stub
		return bdao.remove(bno);
	}


	
	
}
