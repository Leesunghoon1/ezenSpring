package com.ezen.myproject.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.myproject.domain.BoardDTO;
import com.ezen.myproject.domain.BoardVO;
import com.ezen.myproject.domain.CommentVO;
import com.ezen.myproject.domain.FileVO;
import com.ezen.myproject.domain.PagingVO;
import com.ezen.myproject.repository.BoardDAO;
import com.ezen.myproject.repository.CommentDAO;
import com.ezen.myproject.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Service //서비스
@Slf4j //로그
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO bdao;
	
	@Inject
	private FileDAO fdao;
	

	@Override
	public int register(BoardDTO bdto) {
		log.info("register chaeak 2");
		//기존 게시글에 대한 내용을 DB에 저장 같은 내용을 저장
		int isOK = bdao.insert(bdto.getBvo());
		// ----- 파일저장 라인
		if(bdto.getFlist()==null) {
			//파일이 null이면 그냥 성공한걸로.....
			isOK *= 1; 
		}else {
			//bvo에 값이 들어갔고, 파일의 개수가 있다면....
			if(isOK > 0 && bdto.getFlist().size() >0) {
				// fvo의 bno는 아직 설정되기 전..
				// 현재 시점에서 bno는 아직 결정되지 않음 .. => db insert ai에의해 자동생성
				int bno = bdao.selectBno(); // 방금 저장된 bno 가져오기
				// 제일 높은 max(bno)를 가져왔다면
				// flist의 모든 fileVO에 방금 가져온 bno를 set해야함
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					//파일 저장
					log.info(">>>>>insert fvo" + fvo);
					isOK *= fdao.insertFile(fvo);
				}
				
			}
		}
		return isOK;
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

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public List<BoardVO> getPageList(PagingVO pgvo) {
		return bdao.getPageList(pgvo);
	}


	
}
