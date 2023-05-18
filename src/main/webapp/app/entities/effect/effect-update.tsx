import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivity } from 'app/shared/model/activity.model';
import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { IEffect } from 'app/shared/model/effect.model';
import { getEntity, updateEntity, createEntity, reset } from './effect.reducer';

export const EffectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activities = useAppSelector(state => state.activity.entities);
  const effectEntity = useAppSelector(state => state.effect.entity);
  const loading = useAppSelector(state => state.effect.loading);
  const updating = useAppSelector(state => state.effect.updating);
  const updateSuccess = useAppSelector(state => state.effect.updateSuccess);

  const handleClose = () => {
    navigate('/effect');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActivities({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...effectEntity,
      ...values,
      activity: activities.find(it => it.id.toString() === values.activity.toString()),
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
          ...effectEntity,
          activity: effectEntity?.activity?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.effect.home.createOrEditLabel" data-cy="EffectCreateUpdateHeading">
            <Translate contentKey="eduApp.effect.home.createOrEditLabel">Create or edit a Effect</Translate>
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
                  id="effect-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.effect.metadata')}
                id="effect-metadata"
                name="metadata"
                data-cy="metadata"
                type="textarea"
              />
              <ValidatedField
                id="effect-activity"
                name="activity"
                data-cy="activity"
                label={translate('eduApp.effect.activity')}
                type="select"
              >
                <option value="" key="0" />
                {activities
                  ? activities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/effect" replace color="info">
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

export default EffectUpdate;
