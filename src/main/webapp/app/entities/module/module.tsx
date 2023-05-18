import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IModule } from 'app/shared/model/module.model';
import { getEntities } from './module.reducer';

export const Module = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const moduleList = useAppSelector(state => state.module.entities);
  const loading = useAppSelector(state => state.module.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="module-heading" data-cy="ModuleHeading">
        <Translate contentKey="eduApp.module.home.title">Modules</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.module.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/module/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.module.home.createLabel">Create new Module</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {moduleList && moduleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.module.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module.endData">End Data</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module.level">Level</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module.scenario">Scenario</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module.fragments">Fragments</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {moduleList.map((module, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/module/${module.id}`} color="link" size="sm">
                      {module.id}
                    </Button>
                  </td>
                  <td>{module.title}</td>
                  <td>{module.description}</td>
                  <td>{module.startDate ? <TextFormat type="date" value={module.startDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{module.endData ? <TextFormat type="date" value={module.endData} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    <Translate contentKey={`eduApp.Level.${module.level}`} />
                  </td>
                  <td>{module.scenario ? <Link to={`/scenario/${module.scenario.id}`}>{module.scenario.title}</Link> : ''}</td>
                  <td>
                    {module.fragments
                      ? module.fragments.map((val, j) => (
                          <span key={j}>
                            <Link to={`/fragment/${val.id}`}>{val.id}</Link>
                            {j === module.fragments.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/module/${module.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/module/${module.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/module/${module.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eduApp.module.home.notFound">No Modules found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Module;
