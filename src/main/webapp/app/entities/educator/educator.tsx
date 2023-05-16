import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEducator } from 'app/shared/model/educator.model';
import { getEntities } from './educator.reducer';

export const Educator = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const educatorList = useAppSelector(state => state.educator.entities);
  const loading = useAppSelector(state => state.educator.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="educator-heading" data-cy="EducatorHeading">
        <Translate contentKey="eduApp.educator.home.title">Educators</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.educator.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/educator/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.educator.home.createLabel">Create new Educator</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {educatorList && educatorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.educator.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educator.firstName">First Name</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educator.lastName">Last Name</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educator.email">Email</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {educatorList.map((educator, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/educator/${educator.id}`} color="link" size="sm">
                      {educator.id}
                    </Button>
                  </td>
                  <td>{educator.firstName}</td>
                  <td>{educator.lastName}</td>
                  <td>{educator.email}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/educator/${educator.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/educator/${educator.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/educator/${educator.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eduApp.educator.home.notFound">No Educators found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Educator;
