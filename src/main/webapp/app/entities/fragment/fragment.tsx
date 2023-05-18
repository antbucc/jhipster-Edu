import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFragment } from 'app/shared/model/fragment.model';
import { getEntities } from './fragment.reducer';

export const Fragment = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const fragmentList = useAppSelector(state => state.fragment.entities);
  const loading = useAppSelector(state => state.fragment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="fragment-heading" data-cy="FragmentHeading">
        <Translate contentKey="eduApp.fragment.home.title">Fragments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.fragment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/fragment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.fragment.home.createLabel">Create new Fragment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fragmentList && fragmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.fragment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.fragment.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.fragment.activity">Activity</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fragmentList.map((fragment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/fragment/${fragment.id}`} color="link" size="sm">
                      {fragment.id}
                    </Button>
                  </td>
                  <td>{fragment.title}</td>
                  <td>
                    {fragment.activities
                      ? fragment.activities.map((val, j) => (
                          <span key={j}>
                            <Link to={`/activity/${val.id}`}>{val.title}</Link>
                            {j === fragment.activities.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/fragment/${fragment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/fragment/${fragment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/fragment/${fragment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eduApp.fragment.home.notFound">No Fragments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Fragment;
