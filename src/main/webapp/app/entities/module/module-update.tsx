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
import { IFragment } from 'app/shared/model/fragment.model';
import { getEntities as getFragments } from 'app/entities/fragment/fragment.reducer';
import { IModule } from 'app/shared/model/module.model';
import { Level } from 'app/shared/model/enumerations/level.model';
import { getEntity, updateEntity, createEntity, reset } from './module.reducer';

export const ModuleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const scenarios = useAppSelector(state => state.scenario.entities);
  const fragments = useAppSelector(state => state.fragment.entities);
  const moduleEntity = useAppSelector(state => state.module.entity);
  const loading = useAppSelector(state => state.module.loading);
  const updating = useAppSelector(state => state.module.updating);
  const updateSuccess = useAppSelector(state => state.module.updateSuccess);
  const levelValues = Object.keys(Level);

  const handleClose = () => {
    navigate('/module');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getScenarios({}));
    dispatch(getFragments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endData = convertDateTimeToServer(values.endData);

    const entity = {
      ...moduleEntity,
      ...values,
      fragments: mapIdList(values.fragments),
      scenario: scenarios.find(it => it.id.toString() === values.scenario.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startDate: displayDefaultDateTime(),
          endData: displayDefaultDateTime(),
        }
      : {
          level: 'BEGINNER',
          ...moduleEntity,
          startDate: convertDateTimeFromServer(moduleEntity.startDate),
          endData: convertDateTimeFromServer(moduleEntity.endData),
          scenario: moduleEntity?.scenario?.id,
          fragments: moduleEntity?.fragments?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.module.home.createOrEditLabel" data-cy="ModuleCreateUpdateHeading">
            <Translate contentKey="eduApp.module.home.createOrEditLabel">Create or edit a Module</Translate>
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
                  id="module-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.module.title')} id="module-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('eduApp.module.description')}
                id="module-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.module.startDate')}
                id="module-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eduApp.module.endData')}
                id="module-endData"
                name="endData"
                data-cy="endData"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('eduApp.module.level')} id="module-level" name="level" data-cy="level" type="select">
                {levelValues.map(level => (
                  <option value={level} key={level}>
                    {translate('eduApp.Level.' + level)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="module-scenario"
                name="scenario"
                data-cy="scenario"
                label={translate('eduApp.module.scenario')}
                type="select"
              >
                <option value="" key="0" />
                {scenarios
                  ? scenarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.module.fragments')}
                id="module-fragments"
                data-cy="fragments"
                type="select"
                multiple
                name="fragments"
              >
                <option value="" key="0" />
                {fragments
                  ? fragments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/module" replace color="info">
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

export default ModuleUpdate;
