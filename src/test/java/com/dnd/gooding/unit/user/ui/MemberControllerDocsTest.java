package com.dnd.gooding.unit.user.ui;

import static com.dnd.gooding.common.constants.Constant.*;
import static com.dnd.gooding.documenation.DocumentUtils.*;
import static javax.management.openmbean.SimpleType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dnd.gooding.annotations.RestDocsTest;
import com.dnd.gooding.documenation.MockMvcFactory;
import com.dnd.gooding.user.query.application.MemberQueryService;
import com.dnd.gooding.user.ui.MemberController;

@DisplayName("멤버API 문서화")
@RestDocsTest
class MemberControllerDocsTest {

	@Mock
	private MemberQueryService memberQueryService;
	@InjectMocks
	private MemberController memberController;

	@DisplayName("검색 : 멤버정보 조회")
	@Test
	void member(RestDocumentationContextProvider contextProvider) throws Exception {
		String responseSource = """
					{
						"id":"youg1322@naver.com",
						"name":"haeyong"
					}""";

		// Mockito.when(memberQueryService.getMember(Mockito.any()))
		// 	.thenReturn(Optional.of());

		// var responseFieldDescriptors = new FieldDescriptor[]{
		// 	fieldWithPath("code").type(STRING).description("응답코드(정상: 0000)"),
		// 	fieldWithPath("message").type(STRING).description("응답메시지(정상: OK)")
		// };

		String id = "youg1322@naver.com";

		MockMvcFactory.getRestDocsMockMvc(contextProvider, HOST_LOCAL, memberController)
			.perform(RestDocumentationRequestBuilders.get("/api/v1/member/{id}", id))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcRestDocumentation.document("get-v1-get-member",
				getDocumentRequest(),
				getDocumentResponse()
			));
	}
}
