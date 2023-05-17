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
 * A Fragment.
 */
@Document(collection = "fragment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fragment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @DBRef
    @Field("activities")
    @JsonIgnoreProperties(value = { "fragments" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

    @DBRef
    @Field("module")
    @JsonIgnoreProperties(value = { "scenario", "fragments" }, allowSetters = true)
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Fragment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Fragment title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Fragment activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Fragment addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getFragments().add(this);
        return this;
    }

    public Fragment removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getFragments().remove(this);
        return this;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Fragment module(Module module) {
        this.setModule(module);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fragment)) {
            return false;
        }
        return id != null && id.equals(((Fragment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fragment{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
