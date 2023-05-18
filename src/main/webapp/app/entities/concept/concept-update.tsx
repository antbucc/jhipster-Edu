import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGoal } from 'app/shared/model/goal.model';
import { getEntities as getGoals } from 'app/entities/goal/goal.reducer';
import { ICompetence } from 'app/shared/model/competence.model';
import { getEntities as getCompetences } from 'app/entities/competence/competence.reducer';
import { IActivity } from 'app/shared/model/activity.model';
import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { IConcept } from 'app/shared/model/concept.model';
import { getEntity, updateEntity, createEntity, reset } from './concept.reducer';

export const ConceptUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const goals = useAppSelector(state => state.goal.entities);
  const competences = useAppSelector(state => state.competence.entities);
  const activities = useAppSelector(state => state.activity.entities);
  const conceptEntity = useAppSelector(state => state.concept.entity);
  const loading = useAppSelector(state => state.concept.loading);
  const updating = useAppSelector(state => state.concept.updating);
  const updateSuccess = useAppSelector(state => state.concept.updateSuccess);

  const handleClose = () => {
    navigate('/concept');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGoals({}));
    dispatch(getCompetences({}));
    dispatch(getActivities({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...conceptEntity,
      ...values,
      goals: mapIdList(values.goals),
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
          ...conceptEntity,
          goals: conceptEntity?.goals?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.concept.home.createOrEditLabel" data-cy="ConceptCreateUpdateHeading">
            <Translate contentKey="eduApp.concept.home.createOrEditLabel">Create or edit a Concept</Translate>
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
                  id="concept-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.concept.title')} id="concept-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('eduApp.concept.description')}
                id="concept-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('eduApp.concept.goal')} id="concept-goal" data-cy="goal" type="select" multiple name="goals">
                <option value="" key="0" />
                {goals
                  ? goals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/concept" replace color="info">
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

export default ConceptUpdate;
