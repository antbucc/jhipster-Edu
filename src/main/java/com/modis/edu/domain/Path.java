package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.PathType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Path.
 */
@Document(collection = "path")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Path implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("type")
    private PathType type;

    @DBRef
    @Field("targetFragment")
    @JsonIgnoreProperties(value = { "preconditions", "effects", "outgoingPaths", "activities" }, allowSetters = true)
    private Fragment targetFragment;

    @DBRef
    @Field("sourceFragment")
    @JsonIgnoreProperties(value = { "preconditions", "effects", "outgoingPaths", "activities" }, allowSetters = true)
    private Fragment sourceFragment;

    @DBRef
    @Field("modules")
    @JsonIgnoreProperties(value = { "scenario", "paths" }, allowSetters = true)
    private Set<Module> modules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Path id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Path title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PathType getType() {
        return this.type;
    }

    public Path type(PathType type) {
        this.setType(type);
        return this;
    }

    public void setType(PathType type) {
        this.type = type;
    }

    public Fragment getTargetFragment() {
        return this.targetFragment;
    }

    public void setTargetFragment(Fragment fragment) {
        this.targetFragment = fragment;
    }

    public Path targetFragment(Fragment fragment) {
        this.setTargetFragment(fragment);
        return this;
    }

    public Fragment getSourceFragment() {
        return this.sourceFragment;
    }

    public void setSourceFragment(Fragment fragment) {
        this.sourceFragment = fragment;
    }

    public Path sourceFragment(Fragment fragment) {
        this.setSourceFragment(fragment);
        return this;
    }

    public Set<Module> getModules() {
        return this.modules;
    }

    public void setModules(Set<Module> modules) {
        if (this.modules != null) {
            this.modules.forEach(i -> i.removePath(this));
        }
        if (modules != null) {
            modules.forEach(i -> i.addPath(this));
        }
        this.modules = modules;
    }

    public Path modules(Set<Module> modules) {
        this.setModules(modules);
        return this;
    }

    public Path addModules(Module module) {
        this.modules.add(module);
        module.getPaths().add(this);
        return this;
    }

    public Path removeModules(Module module) {
        this.modules.remove(module);
        module.getPaths().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Path)) {
            return false;
        }
        return id != null && id.equals(((Path) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Path{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
