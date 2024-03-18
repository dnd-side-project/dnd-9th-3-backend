package com.dnd.gooding.oauth.infra;

import com.dnd.gooding.oauth.command.application.out.OAuthMemberPort;
import com.dnd.gooding.user.command.application.in.CreateMemberUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuthMemberAdapter implements OAuthMemberPort {

    private final CreateMemberUseCase createMemberUseCase;

    public OAuthMemberAdapter(CreateMemberUseCase createMemberUseCase) {
        this.createMemberUseCase = createMemberUseCase;
    }

    @Transactional
    @Override
    public void create(String id, String oAuthId) {
        createMemberUseCase.create(id, oAuthId);
    }
}
