package com.dnd.gooding.domain.onboard.controller.port;

import java.util.List;

public interface OnboardService {
	void create(Long userId, String nickName, List<String> interestCodes);
}
