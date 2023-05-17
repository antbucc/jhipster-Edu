import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './scenario.reducer';

export const ScenarioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const scenarioEntity = useAppSelector(state => state.scenario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="scenarioDetailsHeading">
          <Translate contentKey="eduApp.scenario.detail.title">Scenario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{scenarioEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.scenario.title">Title</Translate>
            </span>
          </dt>
          <dd>{scenarioEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.scenario.description">Description</Translate>
            </span>
          </dt>
          <dd>{scenarioEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="eduApp.scenario.language">Language</Translate>
            </span>
          </dt>
          <dd>{scenarioEntity.language}</dd>
          <dt>
            <Translate contentKey="eduApp.scenario.domain">Domain</Translate>
          </dt>
          <dd>{scenarioEntity.domain ? scenarioEntity.domain.title : ''}</dd>
          <dt>
            <Translate contentKey="eduApp.scenario.educator">Educator</Translate>
          </dt>
          <dd>
            {scenarioEntity.educators
              ? scenarioEntity.educators.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.lastName}</a>
                    {scenarioEntity.educators && i === scenarioEntity.educators.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="eduApp.scenario.competence">Competence</Translate>
          </dt>
          <dd>
            {scenarioEntity.competences
              ? scenarioEntity.competences.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {scenarioEntity.competences && i === scenarioEntity.competences.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="eduApp.scenario.learner">Learner</Translate>
          </dt>
          <dd>
            {scenarioEntity.learners
              ? scenarioEntity.learners.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.lastName}</a>
                    {scenarioEntity.learners && i === scenarioEntity.learners.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/scenario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scenario/${scenarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ScenarioDetail;
