import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './goal.reducer';

export const GoalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const goalEntity = useAppSelector(state => state.goal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="goalDetailsHeading">
          <Translate contentKey="eduApp.goal.detail.title">Goal</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{goalEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.goal.title">Title</Translate>
            </span>
          </dt>
          <dd>{goalEntity.title}</dd>
          <dt>
            <Translate contentKey="eduApp.goal.concept">Concept</Translate>
          </dt>
          <dd>
            {goalEntity.concepts
              ? goalEntity.concepts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {goalEntity.concepts && i === goalEntity.concepts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/goal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/goal/${goalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GoalDetail;
