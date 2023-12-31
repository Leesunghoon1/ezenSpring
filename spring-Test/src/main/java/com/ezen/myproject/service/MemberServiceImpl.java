package com.ezen.myproject.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezen.myproject.domain.MemberVO;
import com.ezen.myproject.repository.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{
	
	@Inject
	private MemberDAO mdao;
	
	//password Encode 를 하기 위해 security 디펜던시 추가.
	@Inject
	BCryptPasswordEncoder passwordEncoder;
	
	@Inject
	HttpServletRequest request;
	

	@Override
	public int signup(MemberVO mvo) {
		// 아이디가 중복되면 회원가입 실패
		// 아이디를 주고, DB에서 일치하는 유저를 달라고 요청
		// 일치하는 유저가 없다면 가입(1), / 유저가 있으면 실패(0)
		MemberVO temp = mdao.getUser(mvo.getId());
		//아이디가 일치하면 템프에 넣어주라
		if(temp != null) {
			//여기는 중복되서 0을 리턴
			return 0;
		}
		//아이디가 중복아 되지 않아서 회원가입 을 진행 
		//password가 null이면, 혹은 값이 없다면.... 가입불가
		if(mvo.getId() == null || mvo.getId().length() == 0) {
			return 0;
		}
		if(mvo.getPw() == null || mvo.getPw().length() == 0) {
			return 0;
		}
		
		//회원가입 진행
		//암호화(encode) / matches(원래비번, 암호화된 비번) => true / false
		String pw = mvo.getPw();
		
		String encodePw = passwordEncoder.encode(pw);
		//페스워드 암호화 진행
		mvo.setPw(encodePw);
		//멤버 객체에 암호화된 패스워드로 변경
		
		//그리고 회원가입 진행
		int isOK = mdao.insert(mvo);
		log.info("signup chaeak 2");
		return isOK;
	}


	@Override
	public MemberVO isUser(MemberVO mvo) {
		//로그인 유저 확인용
		MemberVO temp = mdao.getUser(mvo.getId()); //회원가입할 때 사용했던 메서드 호출
		//아이디를 주고, 해당 아이디의 객체 가져오기
		if(temp == null) {
			//객체가 없는경우, 로그인 잘못했을 경우
			return null;
		}
		
		//passwordencoder.matches(원래 비밀번호, 암호화된 비밀번호) : 같이 넣어서 매치가 된다면 체크
		//맞으면 true, 안되면 false
		if(passwordEncoder.matches(mvo.getPw(), temp.getPw())) {
			return temp;
		}else {
			return null;	
		}
	}


	@Override
	public int modify(MemberVO mvo) {
		// mvo 객체에서 pw의 값이 있는지 체크
		// mvo의 pw 객체가 없다면 기존 값으로 세팅
		if(mvo.getPw() == null || mvo.getPw().length() == 0) {
			MemberVO sesMVO = (MemberVO)request.getSession().getAttribute("ses");
			mvo.setPw(sesMVO.getPw());
		}else {
			String setpw = passwordEncoder.encode(mvo.getPw());
			mvo.setPw(setpw);
		}
		log.info("pw 수정 후 >>>" + mvo);
		return mdao.update(mvo);
	}
	
}
