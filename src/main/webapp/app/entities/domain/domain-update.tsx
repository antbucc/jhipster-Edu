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
import { IDomain } from 'app/shared/model/domain.model';
import { getEntity, updateEntity, createEntity, reset } from './domain.reducer';

export const DomainUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const scenarios = useAppSelector(state => state.scenario.entities);
  const domainEntity = useAppSelector(state => state.domain.entity);
  const loading = useAppSelector(state => state.domain.loading);
  const updating = useAppSelector(state => state.domain.updating);
  const updateSuccess = useAppSelector(state => state.domain.updateSuccess);

  const handleClose = () => {
    navigate('/domain' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
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
      ...domainEntity,
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
          ...domainEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.domain.home.createOrEditLabel" data-cy="DomainCreateUpdateHeading">
            <Translate contentKey="eduApp.domain.home.createOrEditLabel">Create or edit a Domain</Translate>
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
                  id="domain-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.domain.title')} id="domain-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('eduApp.domain.description')}
                id="domain-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('eduApp.domain.city')} id="domain-city" name="city" data-cy="city" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/domain" replace color="info">
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

export default DomainUpdate;
