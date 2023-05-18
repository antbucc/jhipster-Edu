import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEffect } from 'app/shared/model/effect.model';
import { getEntities } from './effect.reducer';

export const Effect = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const effectList = useAppSelector(state => state.effect.entities);
  const loading = useAppSelector(state => state.effect.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="effect-heading" data-cy="EffectHeading">
        <Translate contentKey="eduApp.effect.home.title">Effects</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.effect.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/effect/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.effect.home.createLabel">Create new Effect</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {effectList && effectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.effect.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.effect.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.effect.fragment">Fragment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {effectList.map((effect, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/effect/${effect.id}`} color="link" size="sm">
                      {effect.id}
                    </Button>
                  </td>
                  <td>{effect.title}</td>
                  <td>{effect.fragment ? <Link to={`/fragment/${effect.fragment.id}`}>{effect.fragment.title}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/effect/${effect.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/effect/${effect.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/effect/${effect.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eduApp.effect.home.notFound">No Effects found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Effect;
