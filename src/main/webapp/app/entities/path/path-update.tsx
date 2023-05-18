import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFragment } from 'app/shared/model/fragment.model';
import { getEntities as getFragments } from 'app/entities/fragment/fragment.reducer';
import { IModule } from 'app/shared/model/module.model';
import { getEntities as getModules } from 'app/entities/module/module.reducer';
import { IPath } from 'app/shared/model/path.model';
import { PathType } from 'app/shared/model/enumerations/path-type.model';
import { getEntity, updateEntity, createEntity, reset } from './path.reducer';

export const PathUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fragments = useAppSelector(state => state.fragment.entities);
  const modules = useAppSelector(state => state.module.entities);
  const pathEntity = useAppSelector(state => state.path.entity);
  const loading = useAppSelector(state => state.path.loading);
  const updating = useAppSelector(state => state.path.updating);
  const updateSuccess = useAppSelector(state => state.path.updateSuccess);
  const pathTypeValues = Object.keys(PathType);

  const handleClose = () => {
    navigate('/path');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFragments({}));
    dispatch(getModules({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...pathEntity,
      ...values,
      targetFragment: fragments.find(it => it.id.toString() === values.targetFragment.toString()),
      sourceFragment: fragments.find(it => it.id.toString() === values.sourceFragment.toString()),
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
          type: 'PASS',
          ...pathEntity,
          targetFragment: pathEntity?.targetFragment?.id,
          sourceFragment: pathEntity?.sourceFragment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.path.home.createOrEditLabel" data-cy="PathCreateUpdateHeading">
            <Translate contentKey="eduApp.path.home.createOrEditLabel">Create or edit a Path</Translate>
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
                  id="path-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.path.title')} id="path-title" name="title" data-cy="title" type="text" />
              <ValidatedField label={translate('eduApp.path.type')} id="path-type" name="type" data-cy="type" type="select">
                {pathTypeValues.map(pathType => (
                  <option value={pathType} key={pathType}>
                    {translate('eduApp.PathType.' + pathType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="path-targetFragment"
                name="targetFragment"
                data-cy="targetFragment"
                label={translate('eduApp.path.targetFragment')}
                type="select"
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
              <ValidatedField
                id="path-sourceFragment"
                name="sourceFragment"
                data-cy="sourceFragment"
                label={translate('eduApp.path.sourceFragment')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/path" replace color="info">
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

export default PathUpdate;
