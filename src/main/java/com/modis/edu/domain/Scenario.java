package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.Language;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Scenario.
 */
@Document(collection = "scenario")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Scenario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("language")
    private Language language;

    @DBRef
    @Field("domain")
    private Domain domain;

    @DBRef
    @Field("educators")
    @JsonIgnoreProperties(value = { "scenarios" }, allowSetters = true)
    private Set<Educator> educators = new HashSet<>();

    @DBRef
    @Field("competences")
    @JsonIgnoreProperties(value = { "concepts", "scenarios" }, allowSetters = true)
    private Set<Competence> competences = new HashSet<>();

    @DBRef
    @Field("learners")
    @JsonIgnoreProperties(value = { "scenarios" }, allowSetters = true)
    private Set<Learner> learners = new HashSet<>();

    @DBRef
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Scenario id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Scenario title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Scenario description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return this.language;
    }

    public Scenario language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Domain getDomain() {
        return this.domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Scenario domain(Domain domain) {
        this.setDomain(domain);
        return this;
    }

    public Set<Educator> getEducators() {
        return this.educators;
    }

    public void setEducators(Set<Educator> educators) {
        this.educators = educators;
    }

    public Scenario educators(Set<Educator> educators) {
        this.setEducators(educators);
        return this;
    }

    public Scenario addEducator(Educator educator) {
        this.educators.add(educator);
        educator.getScenarios().add(this);
        return this;
    }

    public Scenario removeEducator(Educator educator) {
        this.educators.remove(educator);
        educator.getScenarios().remove(this);
        return this;
    }

    public Set<Competence> getCompetences() {
        return this.competences;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }

    public Scenario competences(Set<Competence> competences) {
        this.setCompetences(competences);
        return this;
    }

    public Scenario addCompetence(Competence competence) {
        this.competences.add(competence);
        competence.getScenarios().add(this);
        return this;
    }

    public Scenario removeCompetence(Competence competence) {
        this.competences.remove(competence);
        competence.getScenarios().remove(this);
        return this;
    }

    public Set<Learner> getLearners() {
        return this.learners;
    }

    public void setLearners(Set<Learner> learners) {
        this.learners = learners;
    }

    public Scenario learners(Set<Learner> learners) {
        this.setLearners(learners);
        return this;
    }

    public Scenario addLearner(Learner learner) {
        this.learners.add(learner);
        learner.getScenarios().add(this);
        return this;
    }

    public Scenario removeLearner(Learner learner) {
        this.learners.remove(learner);
        learner.getScenarios().remove(this);
        return this;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        if (this.module != null) {
            this.module.setScenario(null);
        }
        if (module != null) {
            module.setScenario(this);
        }
        this.module = module;
    }

    public Scenario module(Module module) {
        this.setModule(module);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scenario)) {
            return false;
        }
        return id != null && id.equals(((Scenario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scenario{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
