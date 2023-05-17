import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDomain } from 'app/shared/model/domain.model';
import { getEntities as getDomains } from 'app/entities/domain/domain.reducer';
import { IEducator } from 'app/shared/model/educator.model';
import { getEntities as getEducators } from 'app/entities/educator/educator.reducer';
import { ICompetence } from 'app/shared/model/competence.model';
import { getEntities as getCompetences } from 'app/entities/competence/competence.reducer';
import { ILearner } from 'app/shared/model/learner.model';
import { getEntities as getLearners } from 'app/entities/learner/learner.reducer';
import { IModule } from 'app/shared/model/module.model';
import { getEntities as getModules } from 'app/entities/module/module.reducer';
import { IScenario } from 'app/shared/model/scenario.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { getEntity, updateEntity, createEntity, reset } from './scenario.reducer';

export const ScenarioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const domains = useAppSelector(state => state.domain.entities);
  const educators = useAppSelector(state => state.educator.entities);
  const competences = useAppSelector(state => state.competence.entities);
  const learners = useAppSelector(state => state.learner.entities);
  const modules = useAppSelector(state => state.module.entities);
  const scenarioEntity = useAppSelector(state => state.scenario.entity);
  const loading = useAppSelector(state => state.scenario.loading);
  const updating = useAppSelector(state => state.scenario.updating);
  const updateSuccess = useAppSelector(state => state.scenario.updateSuccess);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/scenario' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDomains({}));
    dispatch(getEducators({}));
    dispatch(getCompetences({}));
    dispatch(getLearners({}));
    dispatch(getModules({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...scenarioEntity,
      ...values,
      educators: mapIdList(values.educators),
      competences: mapIdList(values.competences),
      learners: mapIdList(values.learners),
      domain: domains.find(it => it.id.toString() === values.domain.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          language: 'ENGLISH',
          ...scenarioEntity,
          domain: scenarioEntity?.domain?.id,
          educators: scenarioEntity?.educators?.map(e => e.id.toString()),
          competences: scenarioEntity?.competences?.map(e => e.id.toString()),
          learners: scenarioEntity?.learners?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.scenario.home.createOrEditLabel" data-cy="ScenarioCreateUpdateHeading">
            <Translate contentKey="eduApp.scenario.home.createOrEditLabel">Create or edit a Scenario</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="scenario-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.scenario.title')} id="scenario-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('eduApp.scenario.description')}
                id="scenario-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.scenario.language')}
                id="scenario-language"
                name="language"
                data-cy="language"
                type="select"
              >
                {languageValues.map(language => (
                  <option value={language} key={language}>
                    {translate('eduApp.Language.' + language)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="scenario-domain" name="domain" data-cy="domain" label={translate('eduApp.scenario.domain')} type="select">
                <option value="" key="0" />
                {domains
                  ? domains.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.scenario.educator')}
                id="scenario-educator"
                data-cy="educator"
                type="select"
                multiple
                name="educators"
              >
                <option value="" key="0" />
                {educators
                  ? educators.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.scenario.competence')}
                id="scenario-competence"
                data-cy="competence"
                type="select"
                multiple
                name="competences"
              >
                <option value="" key="0" />
                {competences
                  ? competences.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.scenario.learner')}
                id="scenario-learner"
                data-cy="learner"
                type="select"
                multiple
                name="learners"
              >
                <option value="" key="0" />
                {learners
                  ? learners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/scenario" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ScenarioUpdate;
