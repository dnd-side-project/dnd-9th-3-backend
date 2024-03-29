package com.dnd.gooding.user.ui.dto.request;

import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.Interest;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;

public class MemberRequest {

    private String id;
    private String name;
    private String password;
    private Set<Email> emails = new HashSet<>();
    private Set<Interest> interests = new HashSet<>();

    public MemberRequest() {}

    @Builder
    public MemberRequest(
            String id, String name, String password, Set<Email> emails, Set<Interest> interests) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.emails = emails;
        this.interests = interests;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public Set<Interest> getInterests() {
        return interests;
    }
}
