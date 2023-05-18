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
    @Field("fragments")
    @JsonIgnoreProperties(value = { "preconditions", "effects", "outgoingPaths", "activities", "goals" }, allowSetters = true)
    private Set<Fragment> fragments = new HashSet<>();

    @DBRef
    @Field("concepts")
    @JsonIgnoreProperties(value = { "goals", "competences", "activities" }, allowSetters = true)
    private Set<Concept> concepts = new HashSet<>();

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

    public Set<Fragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<Fragment> fragments) {
        this.fragments = fragments;
    }

    public Goal fragments(Set<Fragment> fragments) {
        this.setFragments(fragments);
        return this;
    }

    public Goal addFragment(Fragment fragment) {
        this.fragments.add(fragment);
        fragment.getGoals().add(this);
        return this;
    }

    public Goal removeFragment(Fragment fragment) {
        this.fragments.remove(fragment);
        fragment.getGoals().remove(this);
        return this;
    }

    public Set<Concept> getConcepts() {
        return this.concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        if (this.concepts != null) {
            this.concepts.forEach(i -> i.removeGoal(this));
        }
        if (concepts != null) {
            concepts.forEach(i -> i.addGoal(this));
        }
        this.concepts = concepts;
    }

    public Goal concepts(Set<Concept> concepts) {
        this.setConcepts(concepts);
        return this;
    }

    public Goal addConcept(Concept concept) {
        this.concepts.add(concept);
        concept.getGoals().add(this);
        return this;
    }

    public Goal removeConcept(Concept concept) {
        this.concepts.remove(concept);
        concept.getGoals().remove(this);
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
