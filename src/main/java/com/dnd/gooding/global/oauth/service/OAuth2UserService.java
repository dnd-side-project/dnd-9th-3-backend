package com.dnd.gooding.global.oauth.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.user.service.UserService;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;
import com.dnd.gooding.global.token.dto.Tokens;
import com.dnd.gooding.global.token.service.TokenService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.AllArgsConstructor;

@Service
public class OAuth2UserService {

	private final UserService userService;
	private final TokenService tokenService;

	private final String clientId;
	private final String redirectUrl;
	public OAuth2UserService(
		@Value("${kakao.client-id}") String clientId,
		@Value("${kakao.redirect-url}") String redirectUrl,
		UserService userService,
		TokenService tokenService) {
		this.clientId = clientId;
		this.redirectUrl = redirectUrl;
		this.userService = userService;
		this.tokenService = tokenService;
	}

	public Tokens getKakaoAccessToken (String code) {
		String access_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		Tokens tokens = null;

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			//POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			//POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id="+clientId); 			// TODO REST_API_KEY 입력
			sb.append("&redirect_uri="+redirectUrl); 	// TODO 인가코드 받은 redirect_uri 입력
			sb.append("&code=" + code);
			bw.write(sb.toString());
			bw.flush();

			//결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();

			AuthUserInfo user = saveUser(access_Token);
			tokens = tokenService.createTokens(user);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tokens;
	}

	@Transactional
	public AuthUserInfo saveUser(String accessToken) {
		AuthUserInfo user = null;
		try {
			URL url = new URL("https://kapi.kakao.com/v2/user/me");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.flush();

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			JsonObject jsonObject = element.getAsJsonObject();

			String oauthId = String.valueOf(jsonObject.get("id"));
			JsonObject properties = (JsonObject)jsonObject.get("properties");
			String nickname = String.valueOf(properties.get("nickname"));
			String profileImgUrl = String.valueOf(properties.get("profile_image"));
			String provider = "kakao";

			OAuthUserInfo oAuthUserInfo = OAuthUserInfo.builder()
					.oauthId(oauthId)
					.nickname(nickname)
					.profileImgUrl(profileImgUrl)
					.provider(provider)
					.build();

			user = userService.getOrRegisterUser(oAuthUserInfo);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}
}
