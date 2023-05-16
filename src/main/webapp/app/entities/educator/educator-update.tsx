import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IScenario } from 'app/shared/model/scenario.model';
import { getEntities as getScenarios } from 'app/entities/scenario/scenario.reducer';
import { IEducator } from 'app/shared/model/educator.model';
import { getEntity, updateEntity, createEntity, reset } from './educator.reducer';

export const EducatorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const scenarios = useAppSelector(state => state.scenario.entities);
  const educatorEntity = useAppSelector(state => state.educator.entity);
  const loading = useAppSelector(state => state.educator.loading);
  const updating = useAppSelector(state => state.educator.updating);
  const updateSuccess = useAppSelector(state => state.educator.updateSuccess);

  const handleClose = () => {
    navigate('/educator');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getScenarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...educatorEntity,
      ...values,
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
          ...educatorEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.educator.home.createOrEditLabel" data-cy="EducatorCreateUpdateHeading">
            <Translate contentKey="eduApp.educator.home.createOrEditLabel">Create or edit a Educator</Translate>
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
                  id="educator-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.educator.firstName')}
                id="educator-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.educator.lastName')}
                id="educator-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField label={translate('eduApp.educator.email')} id="educator-email" name="email" data-cy="email" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/educator" replace color="info">
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

export default EducatorUpdate;
