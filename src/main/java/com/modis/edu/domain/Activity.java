package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.ActivityType;
import com.modis.edu.domain.enumeration.Difficulty;
import com.modis.edu.domain.enumeration.Tool;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Activity.
 */
@Document(collection = "activity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("type")
    private ActivityType type;

    @Field("tool")
    private Tool tool;

    @Field("difficulty")
    private Difficulty difficulty;

    @DBRef
    @Field("concepts")
    @JsonIgnoreProperties(value = { "competences", "activities" }, allowSetters = true)
    private Set<Concept> concepts = new HashSet<>();

    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(
        value = { "condition", "parents", "preconditions", "effects", "goals", "activities", "children", "module", "modules" },
        allowSetters = true
    )
    private Set<Fragment> fragments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Activity id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Activity title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Activity description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityType getType() {
        return this.type;
    }

    public Activity type(ActivityType type) {
        this.setType(type);
        return this;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Tool getTool() {
        return this.tool;
    }

    public Activity tool(Tool tool) {
        this.setTool(tool);
        return this;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Activity difficulty(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<Concept> getConcepts() {
        return this.concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }

    public Activity concepts(Set<Concept> concepts) {
        this.setConcepts(concepts);
        return this;
    }

    public Activity addConcept(Concept concept) {
        this.concepts.add(concept);
        concept.getActivities().add(this);
        return this;
    }

    public Activity removeConcept(Concept concept) {
        this.concepts.remove(concept);
        concept.getActivities().remove(this);
        return this;
    }

    public Set<Fragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<Fragment> fragments) {
        if (this.fragments != null) {
            this.fragments.forEach(i -> i.removeActivity(this));
        }
        if (fragments != null) {
            fragments.forEach(i -> i.addActivity(this));
        }
        this.fragments = fragments;
    }

    public Activity fragments(Set<Fragment> fragments) {
        this.setFragments(fragments);
        return this;
    }

    public Activity addFragment(Fragment fragment) {
        this.fragments.add(fragment);
        fragment.getActivities().add(this);
        return this;
    }

    public Activity removeFragment(Fragment fragment) {
        this.fragments.remove(fragment);
        fragment.getActivities().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", tool='" + getTool() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
