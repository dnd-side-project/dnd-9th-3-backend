package com.dnd.gooding.unit.user.ui;

import static com.dnd.gooding.common.constants.Constant.*;
import static com.dnd.gooding.documenation.DocumentUtil.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dnd.gooding.annotations.RestDocsTest;
import com.dnd.gooding.documenation.MockMvcFactory;
import com.dnd.gooding.token.command.application.in.LogoutTokenUseCase;
import com.dnd.gooding.user.command.application.in.UpdateMemberUseCase;
import com.dnd.gooding.user.query.application.MemberQueryService;
import com.dnd.gooding.user.ui.MyMemberController;

@DisplayName("멤버API 문서화")
@RestDocsTest
class MyMemberControllerDocsTest {

	@Mock private MemberQueryService memberQueryService;
	@Mock private UpdateMemberUseCase updateMemberUseCase;
	@Mock private LogoutTokenUseCase logoutTokenUseCase;
	@InjectMocks
	private MyMemberController myMemberController;

	@BeforeEach
	void beforeEach() {
		memberQueryService = Mockito.mock(MemberQueryService.class);
		updateMemberUseCase = Mockito.mock(UpdateMemberUseCase.class);
		logoutTokenUseCase = Mockito.mock(LogoutTokenUseCase.class);
		myMemberController = new MyMemberController(memberQueryService, updateMemberUseCase, logoutTokenUseCase);
	}

	@DisplayName("수정 : 멤버정보 수정")
	@Test
	void member(RestDocumentationContextProvider contextProvider) throws Exception {

		var commandJson = """
                {
                    "id": "youg1322@naver.com",
                    "name": "haeyong",
                    "password": "123456",
                    "emails": [{ "address" : "yong80211@gmail.com" }],
                    "interests": [{ "interestCode" : "1", "interestName" : "쇼핑"}]
                }
                """;

		var requestFieldDescription = new FieldDescriptor[]{
			fieldWithPath("id").type(STRING).description("멤버아이디"),
			fieldWithPath("name").type(STRING).description("이름"),
			fieldWithPath("password").type(STRING).description("비밀번호").optional(),
			fieldWithPath("emails[].address").type(STRING).description("이메일").optional(),
			fieldWithPath("interests[].interestCode").type(STRING).description("관심사코드").optional(),
			fieldWithPath("interests[].interestName").type(STRING).description("관심사이름").optional()
		};

		MockMvcFactory.getRestDocsMockMvc(contextProvider, HOST_LOCAL, myMemberController)
			.perform(RestDocumentationRequestBuilders.post("/api/v1/my/member")
				.header(HttpHeaders.AUTHORIZATION, "Bearer gqoqmq;13o41mvcvciqermqe")
				.contentType(MediaType.APPLICATION_JSON)
				.content(commandJson)
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isNoContent())
			//REST Docs 용
			.andDo(MockMvcRestDocumentation.document("post-v1-my-update-member",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
					requestFieldDescription
				)
			));
	}
}
