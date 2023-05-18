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
 * A Concept.
 */
@Document(collection = "concept")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @DBRef
    @Field("precondition")
    @JsonIgnoreProperties(value = { "concepts", "activity" }, allowSetters = true)
    private Precondition precondition;

    @DBRef
    @Field("effect")
    @JsonIgnoreProperties(value = { "concepts", "activity" }, allowSetters = true)
    private Effect effect;

    @DBRef
    @Field("competences")
    @JsonIgnoreProperties(value = { "concepts", "competences", "scenarios", "competences" }, allowSetters = true)
    private Set<Competence> competences = new HashSet<>();

    @DBRef
    @Field("activities")
    @JsonIgnoreProperties(value = { "preconditions", "effects", "concepts", "fragments" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Concept id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Concept title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Concept description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Precondition getPrecondition() {
        return this.precondition;
    }

    public void setPrecondition(Precondition precondition) {
        this.precondition = precondition;
    }

    public Concept precondition(Precondition precondition) {
        this.setPrecondition(precondition);
        return this;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Concept effect(Effect effect) {
        this.setEffect(effect);
        return this;
    }

    public Set<Competence> getCompetences() {
        return this.competences;
    }

    public void setCompetences(Set<Competence> competences) {
        if (this.competences != null) {
            this.competences.forEach(i -> i.removeConcept(this));
        }
        if (competences != null) {
            competences.forEach(i -> i.addConcept(this));
        }
        this.competences = competences;
    }

    public Concept competences(Set<Competence> competences) {
        this.setCompetences(competences);
        return this;
    }

    public Concept addCompetence(Competence competence) {
        this.competences.add(competence);
        competence.getConcepts().add(this);
        return this;
    }

    public Concept removeCompetence(Competence competence) {
        this.competences.remove(competence);
        competence.getConcepts().remove(this);
        return this;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.removeConcept(this));
        }
        if (activities != null) {
            activities.forEach(i -> i.addConcept(this));
        }
        this.activities = activities;
    }

    public Concept activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Concept addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getConcepts().add(this);
        return this;
    }

    public Concept removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getConcepts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concept)) {
            return false;
        }
        return id != null && id.equals(((Concept) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concept{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
