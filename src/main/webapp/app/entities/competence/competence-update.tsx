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
import { IScenario } from 'app/shared/model/scenario.model';
import { getEntities as getScenarios } from 'app/entities/scenario/scenario.reducer';
import { ICompetence } from 'app/shared/model/competence.model';
import { CompetenceType } from 'app/shared/model/enumerations/competence-type.model';
import { getEntity, updateEntity, createEntity, reset } from './competence.reducer';

export const CompetenceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const concepts = useAppSelector(state => state.concept.entities);
  const scenarios = useAppSelector(state => state.scenario.entities);
  const competenceEntity = useAppSelector(state => state.competence.entity);
  const loading = useAppSelector(state => state.competence.loading);
  const updating = useAppSelector(state => state.competence.updating);
  const updateSuccess = useAppSelector(state => state.competence.updateSuccess);
  const competenceTypeValues = Object.keys(CompetenceType);

  const handleClose = () => {
    navigate('/competence');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getConcepts({}));
    dispatch(getScenarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...competenceEntity,
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
          type: 'SKILL',
          ...competenceEntity,
          concepts: competenceEntity?.concepts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.competence.home.createOrEditLabel" data-cy="CompetenceCreateUpdateHeading">
            <Translate contentKey="eduApp.competence.home.createOrEditLabel">Create or edit a Competence</Translate>
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
                  id="competence-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.competence.title')} id="competence-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('eduApp.competence.description')}
                id="competence-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('eduApp.competence.type')} id="competence-type" name="type" data-cy="type" type="select">
                {competenceTypeValues.map(competenceType => (
                  <option value={competenceType} key={competenceType}>
                    {translate('eduApp.CompetenceType.' + competenceType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.competence.concept')}
                id="competence-concept"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/competence" replace color="info">
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

export default CompetenceUpdate;
