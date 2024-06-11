package com.dnd.gooding.unit.user.ui;

import static com.dnd.gooding.common.constants.Constant.*;
import static com.dnd.gooding.documenation.DocumentUtil.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import com.dnd.gooding.annotations.RestDocsTest;
import com.dnd.gooding.documenation.MockMvcFactory;
import com.dnd.gooding.unit.fixture.MemberDataFixture;
import com.dnd.gooding.user.query.application.MemberQueryService;
import com.dnd.gooding.user.query.dto.MemberData;
import com.dnd.gooding.user.ui.MemberController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("멤버API 문서화")
@RestDocsTest
class MemberControllerDocsTest {

    @Mock private MemberQueryService memberQueryService;
    @InjectMocks private MemberController memberController;

    @DisplayName("검색 : 멤버정보 조회")
    @Test
    void member(RestDocumentationContextProvider contextProvider) throws Exception {
        MemberData expectMemberData = MemberDataFixture.getMember();

        Mockito.when(memberQueryService.getMember("youg1322@naver.com")).thenReturn(expectMemberData);

        var responseFieldDescription =
                new FieldDescriptor[] {
                    fieldWithPath("id").type(STRING).description("멤버아이디"),
                    fieldWithPath("name").type(STRING).description("이름"),
                    fieldWithPath("emails[].address").type(STRING).description("이메일").optional(),
                    fieldWithPath("interests[].interestCode").type(STRING).description("관심사코드"),
                    fieldWithPath("interests[].interestName").type(STRING).description("관심사이름"),
                    fieldWithPath("oAuthId").type(STRING).description("소셜로그인일련번호"),
                    fieldWithPath("imageUrl").type(STRING).description("프로필사진"),
                };

        String id = "youg1322@naver.com";

        MockMvcFactory.getRestDocsMockMvc(contextProvider, HOST_LOCAL, memberController)
                .perform(
                        RestDocumentationRequestBuilders.get("/api/v1/member/{id}", id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "get-v1-get-member",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(responseFieldDescription)));
    }
}
