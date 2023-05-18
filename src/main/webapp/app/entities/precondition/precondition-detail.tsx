import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './precondition.reducer';

export const PreconditionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const preconditionEntity = useAppSelector(state => state.precondition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preconditionDetailsHeading">
          <Translate contentKey="eduApp.precondition.detail.title">Precondition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{preconditionEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.precondition.title">Title</Translate>
            </span>
          </dt>
          <dd>{preconditionEntity.title}</dd>
          <dt>
            <Translate contentKey="eduApp.precondition.fragment">Fragment</Translate>
          </dt>
          <dd>{preconditionEntity.fragment ? preconditionEntity.fragment.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/precondition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/precondition/${preconditionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreconditionDetail;
