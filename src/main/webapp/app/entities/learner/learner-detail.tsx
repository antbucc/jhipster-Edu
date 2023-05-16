import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './learner.reducer';

export const LearnerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const learnerEntity = useAppSelector(state => state.learner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="learnerDetailsHeading">
          <Translate contentKey="eduApp.learner.detail.title">Learner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{learnerEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="eduApp.learner.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{learnerEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="eduApp.learner.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{learnerEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="eduApp.learner.email">Email</Translate>
            </span>
          </dt>
          <dd>{learnerEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="eduApp.learner.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{learnerEntity.phoneNumber}</dd>
        </dl>
        <Button tag={Link} to="/learner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/learner/${learnerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LearnerDetail;
