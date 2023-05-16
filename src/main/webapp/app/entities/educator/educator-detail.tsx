import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './educator.reducer';

export const EducatorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const educatorEntity = useAppSelector(state => state.educator.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="educatorDetailsHeading">
          <Translate contentKey="eduApp.educator.detail.title">Educator</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{educatorEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="eduApp.educator.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{educatorEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="eduApp.educator.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{educatorEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="eduApp.educator.email">Email</Translate>
            </span>
          </dt>
          <dd>{educatorEntity.email}</dd>
        </dl>
        <Button tag={Link} to="/educator" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/educator/${educatorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EducatorDetail;
