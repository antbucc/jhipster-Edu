import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './effect.reducer';

export const EffectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const effectEntity = useAppSelector(state => state.effect.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="effectDetailsHeading">
          <Translate contentKey="eduApp.effect.detail.title">Effect</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{effectEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.effect.title">Title</Translate>
            </span>
          </dt>
          <dd>{effectEntity.title}</dd>
          <dt>
            <Translate contentKey="eduApp.effect.fragment">Fragment</Translate>
          </dt>
          <dd>{effectEntity.fragment ? effectEntity.fragment.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/effect" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/effect/${effectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EffectDetail;
