package com.dnd.gooding.global.oauth.dto;

import java.util.Map;

public record KakaoUserInfo(
	String id,
	Map<String, String> properties
	){
}
