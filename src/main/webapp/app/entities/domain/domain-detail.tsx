import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './domain.reducer';

export const DomainDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const domainEntity = useAppSelector(state => state.domain.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="domainDetailsHeading">
          <Translate contentKey="eduApp.domain.detail.title">Domain</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{domainEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.domain.title">Title</Translate>
            </span>
          </dt>
          <dd>{domainEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.domain.description">Description</Translate>
            </span>
          </dt>
          <dd>{domainEntity.description}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="eduApp.domain.city">City</Translate>
            </span>
          </dt>
          <dd>{domainEntity.city}</dd>
        </dl>
        <Button tag={Link} to="/domain" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/domain/${domainEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DomainDetail;
