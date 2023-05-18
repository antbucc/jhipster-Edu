import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fragment.reducer';

export const FragmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fragmentEntity = useAppSelector(state => state.fragment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fragmentDetailsHeading">
          <Translate contentKey="eduApp.fragment.detail.title">Fragment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fragmentEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.fragment.title">Title</Translate>
            </span>
          </dt>
          <dd>{fragmentEntity.title}</dd>
          <dt>
            <Translate contentKey="eduApp.fragment.activity">Activity</Translate>
          </dt>
          <dd>
            {fragmentEntity.activities
              ? fragmentEntity.activities.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {fragmentEntity.activities && i === fragmentEntity.activities.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/fragment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fragment/${fragmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FragmentDetail;
