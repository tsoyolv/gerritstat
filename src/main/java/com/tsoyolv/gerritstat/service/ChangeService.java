package com.tsoyolv.gerritstat.service;

import com.tsoyolv.gerritstat.port.output.rest.GerritRestClient;
import com.tsoyolv.gerritstat.port.output.rest.dto.ChangesetResponse;
import com.tsoyolv.gerritstat.service.model.ChangeSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChangeService {

    private final GerritRestClient restClient;

    @Value("${gerrit.change.url}")
    private String url;

    public ChangeService(GerritRestClient restClient) {
        this.restClient = restClient;
    }

    public List<ChangeSet> getChangesForUser(String userId, String cookie, LocalDate from, LocalDate to) {
        List<ChangesetResponse> changes = restClient.getChangeSets(userId, cookie, from);
        changes = changes.stream()
                .filter(
                        c -> !c.getSubmitted().toLocalDate().isBefore(from) &&
                                !c.getSubmitted().toLocalDate().isAfter(to))
                .collect(Collectors.toList());
        return mapChanges(changes);
    }

    private List<ChangeSet> mapChanges(List<ChangesetResponse> changes) {
        return changes.stream().map(c -> {
            ChangeSet changeSet = new ChangeSet();
            changeSet.setLink(url + c.getProject() + "/+/" + c.getNumber());
            changeSet.setLinkName(c.getNumber().toString());
            changeSet.setName(c.getSubject());
            changeSet.setSize(getSize(c));
            changeSet.setTotalLinesAdded(c.getInsertions());
            changeSet.setTotalLinesRemoved(c.getDeletions());
            changeSet.setTotalComments(c.getTotalCommentCount());
            changeSet.setCreateDate(c.getCreated());
            changeSet.setCloseDate(c.getSubmitted());
            return changeSet;
        }).sorted(Comparator.comparing(ChangeSet::getCloseDate).reversed()).collect(Collectors.toList());
    }

    private String getSize(ChangesetResponse changesetResponse) {
        int delta = changesetResponse.getDeletions() + changesetResponse.getInsertions();
        if (delta < 10) {
            return "XS";
        } else if (delta < 50) {
            return "S";
        } else if (delta < 250) {
            return "M";
        } else if (delta < 1000) {
            return "L";
        } else {
            return "XL";
        }
    }
}