package domain;

import api.dto.out.Election;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ElectionCacheTest(String id, Map<Candidate, Integer> votes) {

    public static final String ELECTION_ID = "a4d34a72-5bf6-49bb-93bc-dbe0b8813f4f";

    public static ElectionCacheTest create(List<Candidate> candidates) {
        Map<Candidate, Integer> votes = candidates.stream()
                .collect(Collectors.toMap(candidate -> candidate, o -> 0));

        return new ElectionCacheTest(ELECTION_ID, votes);
    }

    public static Election toElectionDtoOut(ElectionCacheTest electionCacheTest) {
        List<String> candidates = electionCacheTest.votes().keySet()
                .stream()
                .map(Candidate::id)
                .toList();

        return new Election(electionCacheTest.id(), candidates);
    }
}