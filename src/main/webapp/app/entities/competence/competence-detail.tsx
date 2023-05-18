import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './competence.reducer';

export const CompetenceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const competenceEntity = useAppSelector(state => state.competence.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="competenceDetailsHeading">
          <Translate contentKey="eduApp.competence.detail.title">Competence</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{competenceEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.competence.title">Title</Translate>
            </span>
          </dt>
          <dd>{competenceEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.competence.description">Description</Translate>
            </span>
          </dt>
          <dd>{competenceEntity.description}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="eduApp.competence.type">Type</Translate>
            </span>
          </dt>
          <dd>{competenceEntity.type}</dd>
          <dt>
            <Translate contentKey="eduApp.competence.concept">Concept</Translate>
          </dt>
          <dd>
            {competenceEntity.concepts
              ? competenceEntity.concepts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {competenceEntity.concepts && i === competenceEntity.concepts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/competence" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/competence/${competenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompetenceDetail;
