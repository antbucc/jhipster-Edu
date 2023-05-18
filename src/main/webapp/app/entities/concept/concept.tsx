import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConcept } from 'app/shared/model/concept.model';
import { getEntities } from './concept.reducer';

export const Concept = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const conceptList = useAppSelector(state => state.concept.entities);
  const loading = useAppSelector(state => state.concept.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="concept-heading" data-cy="ConceptHeading">
        <Translate contentKey="eduApp.concept.home.title">Concepts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.concept.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/concept/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.concept.home.createLabel">Create new Concept</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {conceptList && conceptList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.concept.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.concept.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.concept.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.concept.precondition">Precondition</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.concept.effect">Effect</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {conceptList.map((concept, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/concept/${concept.id}`} color="link" size="sm">
                      {concept.id}
                    </Button>
                  </td>
                  <td>{concept.title}</td>
                  <td>{concept.description}</td>
                  <td>
                    {concept.precondition ? (
                      <Link to={`/precondition/${concept.precondition.id}`}>{concept.precondition.metadata}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{concept.effect ? <Link to={`/effect/${concept.effect.id}`}>{concept.effect.metadata}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/concept/${concept.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/concept/${concept.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/concept/${concept.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="eduApp.concept.home.notFound">No Concepts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Concept;
