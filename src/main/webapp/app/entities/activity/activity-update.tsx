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
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityType } from 'app/shared/model/enumerations/activity-type.model';
import { Tool } from 'app/shared/model/enumerations/tool.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { getEntity, updateEntity, createEntity, reset } from './activity.reducer';

export const ActivityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const concepts = useAppSelector(state => state.concept.entities);
  const fragments = useAppSelector(state => state.fragment.entities);
  const activityEntity = useAppSelector(state => state.activity.entity);
  const loading = useAppSelector(state => state.activity.loading);
  const updating = useAppSelector(state => state.activity.updating);
  const updateSuccess = useAppSelector(state => state.activity.updateSuccess);
  const activityTypeValues = Object.keys(ActivityType);
  const toolValues = Object.keys(Tool);
  const difficultyValues = Object.keys(Difficulty);

  const handleClose = () => {
    navigate('/activity');
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
      ...activityEntity,
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
          type: 'INDIVIDUAL',
          tool: 'COMPUTER',
          difficulty: 'LOW',
          ...activityEntity,
          concepts: activityEntity?.concepts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.activity.home.createOrEditLabel" data-cy="ActivityCreateUpdateHeading">
            <Translate contentKey="eduApp.activity.home.createOrEditLabel">Create or edit a Activity</Translate>
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
                  id="activity-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.activity.title')} id="activity-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('eduApp.activity.description')}
                id="activity-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('eduApp.activity.type')} id="activity-type" name="type" data-cy="type" type="select">
                {activityTypeValues.map(activityType => (
                  <option value={activityType} key={activityType}>
                    {translate('eduApp.ActivityType.' + activityType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('eduApp.activity.tool')} id="activity-tool" name="tool" data-cy="tool" type="select">
                {toolValues.map(tool => (
                  <option value={tool} key={tool}>
                    {translate('eduApp.Tool.' + tool)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.activity.difficulty')}
                id="activity-difficulty"
                name="difficulty"
                data-cy="difficulty"
                type="select"
              >
                {difficultyValues.map(difficulty => (
                  <option value={difficulty} key={difficulty}>
                    {translate('eduApp.Difficulty.' + difficulty)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.activity.concept')}
                id="activity-concept"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity" replace color="info">
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

export default ActivityUpdate;
