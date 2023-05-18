import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConcept } from 'app/shared/model/concept.model';
import { getEntities as getConcepts } from 'app/entities/concept/concept.reducer';
import { IFragment } from 'app/shared/model/fragment.model';
import { getEntities as getFragments } from 'app/entities/fragment/fragment.reducer';
import { IGoal } from 'app/shared/model/goal.model';
import { getEntity, updateEntity, createEntity, reset } from './goal.reducer';

export const GoalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const concepts = useAppSelector(state => state.concept.entities);
  const fragments = useAppSelector(state => state.fragment.entities);
  const goalEntity = useAppSelector(state => state.goal.entity);
  const loading = useAppSelector(state => state.goal.loading);
  const updating = useAppSelector(state => state.goal.updating);
  const updateSuccess = useAppSelector(state => state.goal.updateSuccess);

  const handleClose = () => {
    navigate('/goal');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getConcepts({}));
    dispatch(getFragments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...goalEntity,
      ...values,
      concepts: mapIdList(values.concepts),
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
          ...goalEntity,
          concepts: goalEntity?.concepts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.goal.home.createOrEditLabel" data-cy="GoalCreateUpdateHeading">
            <Translate contentKey="eduApp.goal.home.createOrEditLabel">Create or edit a Goal</Translate>
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
                  id="goal-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.goal.title')} id="goal-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('eduApp.goal.concept')}
                id="goal-concept"
                data-cy="concept"
                type="select"
                multiple
                name="concepts"
              >
                <option value="" key="0" />
                {concepts
                  ? concepts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/goal" replace color="info">
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

export default GoalUpdate;
