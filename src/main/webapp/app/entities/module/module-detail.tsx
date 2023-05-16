import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './module.reducer';

export const ModuleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const moduleEntity = useAppSelector(state => state.module.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="moduleDetailsHeading">
          <Translate contentKey="eduApp.module.detail.title">Module</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{moduleEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.module.title">Title</Translate>
            </span>
          </dt>
          <dd>{moduleEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.module.description">Description</Translate>
            </span>
          </dt>
          <dd>{moduleEntity.description}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="eduApp.module.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{moduleEntity.startDate ? <TextFormat value={moduleEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endData">
              <Translate contentKey="eduApp.module.endData">End Data</Translate>
            </span>
          </dt>
          <dd>{moduleEntity.endData ? <TextFormat value={moduleEntity.endData} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="level">
              <Translate contentKey="eduApp.module.level">Level</Translate>
            </span>
          </dt>
          <dd>{moduleEntity.level}</dd>
          <dt>
            <Translate contentKey="eduApp.module.scenario">Scenario</Translate>
          </dt>
          <dd>{moduleEntity.scenario ? moduleEntity.scenario.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/module" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/module/${moduleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ModuleDetail;
