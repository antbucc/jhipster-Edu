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
 * A Effect.
 */
@Document(collection = "effect")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Effect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("metadata")
    private String metadata;

    @DBRef
    @Field("concept")
    @JsonIgnoreProperties(value = { "precondition", "effect", "competences", "activities" }, allowSetters = true)
    private Set<Concept> concepts = new HashSet<>();

    @DBRef
    @Field("activity")
    @JsonIgnoreProperties(value = { "preconditions", "effects", "concepts", "fragments" }, allowSetters = true)
    private Activity activity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Effect id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public Effect metadata(String metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Set<Concept> getConcepts() {
        return this.concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        if (this.concepts != null) {
            this.concepts.forEach(i -> i.setEffect(null));
        }
        if (concepts != null) {
            concepts.forEach(i -> i.setEffect(this));
        }
        this.concepts = concepts;
    }

    public Effect concepts(Set<Concept> concepts) {
        this.setConcepts(concepts);
        return this;
    }

    public Effect addConcept(Concept concept) {
        this.concepts.add(concept);
        concept.setEffect(this);
        return this;
    }

    public Effect removeConcept(Concept concept) {
        this.concepts.remove(concept);
        concept.setEffect(null);
        return this;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Effect activity(Activity activity) {
        this.setActivity(activity);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Effect)) {
            return false;
        }
        return id != null && id.equals(((Effect) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Effect{" +
            "id=" + getId() +
            ", metadata='" + getMetadata() + "'" +
            "}";
    }
}
