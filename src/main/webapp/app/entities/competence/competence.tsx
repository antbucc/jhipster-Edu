import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompetence } from 'app/shared/model/competence.model';
import { getEntities } from './competence.reducer';

export const Competence = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const competenceList = useAppSelector(state => state.competence.entities);
  const loading = useAppSelector(state => state.competence.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="competence-heading" data-cy="CompetenceHeading">
        <Translate contentKey="eduApp.competence.home.title">Competences</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.competence.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/competence/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.competence.home.createLabel">Create new Competence</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {competenceList && competenceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.competence.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.competence.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.competence.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.competence.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.competence.concept">Concept</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {competenceList.map((competence, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/competence/${competence.id}`} color="link" size="sm">
                      {competence.id}
                    </Button>
                  </td>
                  <td>{competence.title}</td>
                  <td>{competence.description}</td>
                  <td>
                    <Translate contentKey={`eduApp.CompetenceType.${competence.type}`} />
                  </td>
                  <td>
                    {competence.concepts
                      ? competence.concepts.map((val, j) => (
                          <span key={j}>
                            <Link to={`/concept/${val.id}`}>{val.title}</Link>
                            {j === competence.concepts.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/competence/${competence.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/competence/${competence.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/competence/${competence.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eduApp.competence.home.notFound">No Competences found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Competence;
