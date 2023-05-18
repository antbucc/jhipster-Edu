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
    @Field("precondition")
    @JsonIgnoreProperties(value = { "fragment" }, allowSetters = true)
    private Set<Precondition> preconditions = new HashSet<>();

    @DBRef
    @Field("effect")
    @JsonIgnoreProperties(value = { "fragment" }, allowSetters = true)
    private Set<Effect> effects = new HashSet<>();

    @DBRef
    @Field("outgoingPaths")
    @JsonIgnoreProperties(value = { "targetFragment", "sourceFragment", "modules" }, allowSetters = true)
    private Set<Path> outgoingPaths = new HashSet<>();

    @DBRef
    @Field("activities")
    @JsonIgnoreProperties(value = { "concepts", "fragments" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

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

    public Set<Precondition> getPreconditions() {
        return this.preconditions;
    }

    public void setPreconditions(Set<Precondition> preconditions) {
        if (this.preconditions != null) {
            this.preconditions.forEach(i -> i.setFragment(null));
        }
        if (preconditions != null) {
            preconditions.forEach(i -> i.setFragment(this));
        }
        this.preconditions = preconditions;
    }

    public Fragment preconditions(Set<Precondition> preconditions) {
        this.setPreconditions(preconditions);
        return this;
    }

    public Fragment addPrecondition(Precondition precondition) {
        this.preconditions.add(precondition);
        precondition.setFragment(this);
        return this;
    }

    public Fragment removePrecondition(Precondition precondition) {
        this.preconditions.remove(precondition);
        precondition.setFragment(null);
        return this;
    }

    public Set<Effect> getEffects() {
        return this.effects;
    }

    public void setEffects(Set<Effect> effects) {
        if (this.effects != null) {
            this.effects.forEach(i -> i.setFragment(null));
        }
        if (effects != null) {
            effects.forEach(i -> i.setFragment(this));
        }
        this.effects = effects;
    }

    public Fragment effects(Set<Effect> effects) {
        this.setEffects(effects);
        return this;
    }

    public Fragment addEffect(Effect effect) {
        this.effects.add(effect);
        effect.setFragment(this);
        return this;
    }

    public Fragment removeEffect(Effect effect) {
        this.effects.remove(effect);
        effect.setFragment(null);
        return this;
    }

    public Set<Path> getOutgoingPaths() {
        return this.outgoingPaths;
    }

    public void setOutgoingPaths(Set<Path> paths) {
        if (this.outgoingPaths != null) {
            this.outgoingPaths.forEach(i -> i.setSourceFragment(null));
        }
        if (paths != null) {
            paths.forEach(i -> i.setSourceFragment(this));
        }
        this.outgoingPaths = paths;
    }

    public Fragment outgoingPaths(Set<Path> paths) {
        this.setOutgoingPaths(paths);
        return this;
    }

    public Fragment addOutgoingPaths(Path path) {
        this.outgoingPaths.add(path);
        path.setSourceFragment(this);
        return this;
    }

    public Fragment removeOutgoingPaths(Path path) {
        this.outgoingPaths.remove(path);
        path.setSourceFragment(null);
        return this;
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
