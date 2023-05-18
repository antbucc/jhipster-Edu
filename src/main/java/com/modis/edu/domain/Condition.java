package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.ConditionType;
import java.io.Serializable;
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

    @Field("title")
    private String title;

    @Field("type")
    private ConditionType type;

    @DBRef
    @Field("targetFragment")
    @JsonIgnoreProperties(value = { "preconditions", "effects", "outgoingPaths", "activities" }, allowSetters = true)
    private Fragment targetFragment;

    @DBRef
    @Field("sourceFragment")
    @JsonIgnoreProperties(value = { "preconditions", "effects", "outgoingPaths", "activities" }, allowSetters = true)
    private Fragment sourceFragment;

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

    public String getTitle() {
        return this.title;
    }

    public Condition title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Fragment getTargetFragment() {
        return this.targetFragment;
    }

    public void setTargetFragment(Fragment fragment) {
        this.targetFragment = fragment;
    }

    public Condition targetFragment(Fragment fragment) {
        this.setTargetFragment(fragment);
        return this;
    }

    public Fragment getSourceFragment() {
        return this.sourceFragment;
    }

    public void setSourceFragment(Fragment fragment) {
        this.sourceFragment = fragment;
    }

    public Condition sourceFragment(Fragment fragment) {
        this.setSourceFragment(fragment);
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
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
