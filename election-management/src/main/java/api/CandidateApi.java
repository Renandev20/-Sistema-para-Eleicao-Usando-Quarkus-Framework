package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import domain.CandidateService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CandidateApi {
    private final CandidateService service;

    public CandidateApi(CandidateService service) {
        this.service = service;
    }

    public void create(CreateCandidate dto) {
        service.save(dto.toDomain());
    }

    public api.dto.out.Candidate update(String id, UpdateCandidate dto) {
        service.save(dto.toDomain(id));
        return api.dto.out.Candidate.fromDomain(service.findById(id));
    }

    public List<api.dto.out.Candidate> list() {
        return service.findAll().stream().map(api.dto.out.Candidate::fromDomain).toList();
    }
}
