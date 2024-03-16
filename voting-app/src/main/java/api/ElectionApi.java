package api;

import domain.ElectionService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ElectionApi {
    private final ElectionService service;

    public ElectionApi(ElectionService service) {
        this.service = service;
    }

    public List<api.dto.out.Election> findAll() {
        return service.findAll().stream().map(api.dto.out.Election::fromDomain).toList();
    }

    public void vote(String electionId, String candidateId) {
        service.vote(electionId, candidateId);
    }
}