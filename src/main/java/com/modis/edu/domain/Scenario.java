package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.Language;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Scenario.
 */
@Document(collection = "scenario")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Scenario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("language")
    private Language language;

    @DBRef
    @Field("name")
    @JsonIgnoreProperties(value = { "scenario" }, allowSetters = true)
    private Set<Module> names = new HashSet<>();

    @DBRef
    @Field("domains")
    @JsonIgnoreProperties(value = { "scenarios" }, allowSetters = true)
    private Set<Domain> domains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Scenario id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Scenario title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Scenario description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return this.language;
    }

    public Scenario language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Module> getNames() {
        return this.names;
    }

    public void setNames(Set<Module> modules) {
        if (this.names != null) {
            this.names.forEach(i -> i.setScenario(null));
        }
        if (modules != null) {
            modules.forEach(i -> i.setScenario(this));
        }
        this.names = modules;
    }

    public Scenario names(Set<Module> modules) {
        this.setNames(modules);
        return this;
    }

    public Scenario addName(Module module) {
        this.names.add(module);
        module.setScenario(this);
        return this;
    }

    public Scenario removeName(Module module) {
        this.names.remove(module);
        module.setScenario(null);
        return this;
    }

    public Set<Domain> getDomains() {
        return this.domains;
    }

    public void setDomains(Set<Domain> domains) {
        if (this.domains != null) {
            this.domains.forEach(i -> i.removeScenario(this));
        }
        if (domains != null) {
            domains.forEach(i -> i.addScenario(this));
        }
        this.domains = domains;
    }

    public Scenario domains(Set<Domain> domains) {
        this.setDomains(domains);
        return this;
    }

    public Scenario addDomain(Domain domain) {
        this.domains.add(domain);
        domain.getScenarios().add(this);
        return this;
    }

    public Scenario removeDomain(Domain domain) {
        this.domains.remove(domain);
        domain.getScenarios().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scenario)) {
            return false;
        }
        return id != null && id.equals(((Scenario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scenario{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
