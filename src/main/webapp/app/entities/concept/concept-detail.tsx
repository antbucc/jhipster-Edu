import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './concept.reducer';

export const ConceptDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const conceptEntity = useAppSelector(state => state.concept.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="conceptDetailsHeading">
          <Translate contentKey="eduApp.concept.detail.title">Concept</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{conceptEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.concept.title">Title</Translate>
            </span>
          </dt>
          <dd>{conceptEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.concept.description">Description</Translate>
            </span>
          </dt>
          <dd>{conceptEntity.description}</dd>
          <dt>
            <Translate contentKey="eduApp.concept.goal">Goal</Translate>
          </dt>
          <dd>
            {conceptEntity.goals
              ? conceptEntity.goals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {conceptEntity.goals && i === conceptEntity.goals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/concept" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/concept/${conceptEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConceptDetail;
