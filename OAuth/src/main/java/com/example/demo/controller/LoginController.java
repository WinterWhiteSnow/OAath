package com.example.demo.controller;

import com.example.demo.model.dto.KakaoToken;
import com.example.demo.model.dto.MemberInfo;
import com.example.demo.model.dto.NaverResponse;
import com.example.demo.model.dto.NaverToken;
import com.example.demo.model.dto.entity.Member;
import com.example.demo.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
	@Autowired
	private WebClient webClient;
	@Autowired
	private MemberService memberService;

	// 네이버 관련 정보
	static final String naverTokenURL = "https://nid.naver.com/oauth2.0/token";
	@Value("${naver.client_id}")
	private String clientId;
	@Value("${naver.client_secret}")
	private String clientSecret;

	// 카카오 관련 정보
	static String kakaoTokenURL = "https://kauth.kakao.com/oauth/token";
	@Value("${kakao.client_id}")
	private String kakaoClientId;
	@Value("${kakao.redirect_uri}")
	private String kakaoRedirectUri;


	// 사용자의 중복검사는 이메일로 검증함
	// 네이버로 로그인, 카카오로 로그인, 로그인 수단이 달라도 반환되는 이메일이 동일하면
	// 같은 테이블을 함유, 데이터가 분할되지 않음


	// 카카오 인가 코드를 받는 함수
	@GetMapping("/kakao")
	public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) throws Exception {

		//인가 코드로 인가 토큰 받음
		KakaoToken response = webClient.get()
				.uri(kakaoTokenURL+"?client_id="+kakaoClientId+"&redirect_uri="+kakaoRedirectUri
						+"&grant_type=authorization_code"+"&code="+code
				).retrieve().bodyToMono(KakaoToken.class).block();

		//이제 인가 토큰으로 진짜 사용자 정보를 가져옴
		String kakaoUserURL = "https://kapi.kakao.com/v2/user/me";
		Map<String, Map<String, String>> kakaoUserInfo = webClient.get()
				.uri(kakaoUserURL)
				.header("Authorization", "Bearer "+response.getAccessToken())
				.retrieve().bodyToMono(Map.class).block();

		// 객체에 사용자 정보를 넣어주고
		Member member = new Member();
		member.setMemberEmail(kakaoUserInfo.get("kakao_account").get("email"));
		member.setMemberName(kakaoUserInfo.get("properties").get("nickname"));

		// 사용자 정보 조회
		MemberInfo result = getMember(member);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 네이버 인가 코드를 받는 함수
	// 인가 코드를 통해 토큰을 발급받을 예정
	@GetMapping("/naver")
	public ResponseEntity<?> naverLogin(@RequestParam String code, @RequestParam String state) throws Exception {

		// 이제 토큰을 발급 받아야 함
		String naverURL = naverTokenURL+"?grant_type=authorization_code&"+"client_id="+clientId+"&client_secret="+clientSecret+"&code="+code+"&state="+state;
		// JSON정보를 가져왔음
		NaverToken temp = webClient.get()
				.uri(naverURL)
				.retrieve()
				.bodyToMono(NaverToken.class)
				.block();

		// 이제 진짜! 유저 정보를 JSON으로 가져오고 객체로 변환도 해줌
		NaverResponse res = getNaverUserInfo(temp.getAccessToken());

		// Entity에 채워넣고
		Member member = new Member();
		member.setMemberEmail(res.getResponse().get("email"));
		member.setMemberName(res.getResponse().get("name"));

		// 사용자 조회
		MemberInfo result = getMember(member);

		return new ResponseEntity<>(result,HttpStatus.OK);
	}

	public static NaverResponse getNaverUserInfo(String token) throws Exception {
		String url = "https://openapi.naver.com/v1/nid/me";
		WebClient lastRequest = WebClient.builder()
				.baseUrl(url)
				.defaultHeader("Authorization", "Bearer "+token)
				.build();

		NaverResponse userInfo = lastRequest.get().retrieve().bodyToMono(NaverResponse.class).block();
		return userInfo;
	}

	public MemberInfo getMember(Member member) throws Exception {
		// DB에 저장하기 전에 존재하는 유저정보인지 확인
		Optional<Member> check = memberService.getMember(member.getMemberEmail());
		if (! check.isPresent()) { // 존재하지 않으므로 저장
			memberService.saveMember(member);
		}
		// 테이블에서 조회 후 그값을 프론트로 반환
		check = memberService.getMember(member.getMemberEmail());
		MemberInfo result = new MemberInfo(check.get());
		return result;
	}
}
