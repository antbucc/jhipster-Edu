package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.ConditionType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Condition.
 */
@Document(collection = "condition")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Condition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("type")
    private ConditionType type;

    @DBRef
    @Field("type")
    @JsonIgnoreProperties(value = { "previous", "activities", "next", "source", "module" }, allowSetters = true)
    private Set<Fragment> types = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Condition id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Condition description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ConditionType getType() {
        return this.type;
    }

    public Condition type(ConditionType type) {
        this.setType(type);
        return this;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }

    public Set<Fragment> getTypes() {
        return this.types;
    }

    public void setTypes(Set<Fragment> fragments) {
        if (this.types != null) {
            this.types.forEach(i -> i.setSource(null));
        }
        if (fragments != null) {
            fragments.forEach(i -> i.setSource(this));
        }
        this.types = fragments;
    }

    public Condition types(Set<Fragment> fragments) {
        this.setTypes(fragments);
        return this;
    }

    public Condition addType(Fragment fragment) {
        this.types.add(fragment);
        fragment.setSource(this);
        return this;
    }

    public Condition removeType(Fragment fragment) {
        this.types.remove(fragment);
        fragment.setSource(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Condition)) {
            return false;
        }
        return id != null && id.equals(((Condition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Condition{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
