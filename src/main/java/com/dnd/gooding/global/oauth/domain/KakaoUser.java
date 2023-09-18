package com.dnd.gooding.global.oauth.domain;

import java.util.Map;

public record KakaoUser(
	String id,
	Map<String, String> properties
	){
}
