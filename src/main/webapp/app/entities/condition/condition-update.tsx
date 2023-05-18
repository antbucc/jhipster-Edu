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
import { ICondition } from 'app/shared/model/condition.model';
import { ConditionType } from 'app/shared/model/enumerations/condition-type.model';
import { getEntity, updateEntity, createEntity, reset } from './condition.reducer';

export const ConditionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fragments = useAppSelector(state => state.fragment.entities);
  const conditionEntity = useAppSelector(state => state.condition.entity);
  const loading = useAppSelector(state => state.condition.loading);
  const updating = useAppSelector(state => state.condition.updating);
  const updateSuccess = useAppSelector(state => state.condition.updateSuccess);
  const conditionTypeValues = Object.keys(ConditionType);

  const handleClose = () => {
    navigate('/condition');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFragments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...conditionEntity,
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
          ...conditionEntity,
          targetFragment: conditionEntity?.targetFragment?.id,
          sourceFragment: conditionEntity?.sourceFragment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.condition.home.createOrEditLabel" data-cy="ConditionCreateUpdateHeading">
            <Translate contentKey="eduApp.condition.home.createOrEditLabel">Create or edit a Condition</Translate>
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
                  id="condition-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.condition.title')} id="condition-title" name="title" data-cy="title" type="text" />
              <ValidatedField label={translate('eduApp.condition.type')} id="condition-type" name="type" data-cy="type" type="select">
                {conditionTypeValues.map(conditionType => (
                  <option value={conditionType} key={conditionType}>
                    {translate('eduApp.ConditionType.' + conditionType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="condition-targetFragment"
                name="targetFragment"
                data-cy="targetFragment"
                label={translate('eduApp.condition.targetFragment')}
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
                id="condition-sourceFragment"
                name="sourceFragment"
                data-cy="sourceFragment"
                label={translate('eduApp.condition.sourceFragment')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/condition" replace color="info">
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

export default ConditionUpdate;
