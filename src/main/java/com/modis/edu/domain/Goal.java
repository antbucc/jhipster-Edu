package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Goal.
 */
@Document(collection = "goal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Goal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @DBRef
    @Field("concept")
    @JsonIgnoreProperties(value = { "goal", "competences", "activities" }, allowSetters = true)
    private Set<Concept> concepts = new HashSet<>();

    @DBRef
    @Field("fragment")
    @JsonIgnoreProperties(value = { "preconditions", "effects", "goals", "outgoingPaths", "activities" }, allowSetters = true)
    private Fragment fragment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Goal id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Goal title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Concept> getConcepts() {
        return this.concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        if (this.concepts != null) {
            this.concepts.forEach(i -> i.setGoal(null));
        }
        if (concepts != null) {
            concepts.forEach(i -> i.setGoal(this));
        }
        this.concepts = concepts;
    }

    public Goal concepts(Set<Concept> concepts) {
        this.setConcepts(concepts);
        return this;
    }

    public Goal addConcept(Concept concept) {
        this.concepts.add(concept);
        concept.setGoal(this);
        return this;
    }

    public Goal removeConcept(Concept concept) {
        this.concepts.remove(concept);
        concept.setGoal(null);
        return this;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Goal fragment(Fragment fragment) {
        this.setFragment(fragment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Goal)) {
            return false;
        }
        return id != null && id.equals(((Goal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Goal{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
