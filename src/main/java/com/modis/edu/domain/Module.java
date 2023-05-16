package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.Level;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Module.
 */
@Document(collection = "module")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("start_date")
    private Instant startDate;

    @Field("end_data")
    private Instant endData;

    @Field("level")
    private Level level;

    @DBRef
    @Field("scenario")
    private Scenario scenario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Module id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Module title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Module description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Module startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndData() {
        return this.endData;
    }

    public Module endData(Instant endData) {
        this.setEndData(endData);
        return this;
    }

    public void setEndData(Instant endData) {
        this.endData = endData;
    }

    public Level getLevel() {
        return this.level;
    }

    public Module level(Level level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Scenario getScenario() {
        return this.scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Module scenario(Scenario scenario) {
        this.setScenario(scenario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Module)) {
            return false;
        }
        return id != null && id.equals(((Module) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endData='" + getEndData() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
