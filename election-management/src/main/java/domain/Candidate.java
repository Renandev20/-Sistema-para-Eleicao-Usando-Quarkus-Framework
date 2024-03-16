package domain;

import java.util.Optional;
import java.util.UUID;

public record Candidate (String id,
                         Optional<String> photo,
                         String givenName,
                         String familyName,
                         String email,
                         Optional<String> phone,
                         Optional<String> jobTitle) {

    public static Candidate create(String photo,
                                   String givenName,
                                   String familyName,
                                   String email,
                                   String phone,
                                   String jobTitle){
        return new Candidate(
                UUID.randomUUID().toString(),
                Optional.ofNullable(photo),
                givenName,
                familyName,
                email,
                Optional.ofNullable(phone),
                Optional.ofNullable(jobTitle)
        );
    }
}
