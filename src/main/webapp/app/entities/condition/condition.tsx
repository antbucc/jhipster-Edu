import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICondition } from 'app/shared/model/condition.model';
import { getEntities } from './condition.reducer';

export const Condition = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const conditionList = useAppSelector(state => state.condition.entities);
  const loading = useAppSelector(state => state.condition.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="condition-heading" data-cy="ConditionHeading">
        <Translate contentKey="eduApp.condition.home.title">Conditions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.condition.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/condition/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.condition.home.createLabel">Create new Condition</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {conditionList && conditionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.condition.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.condition.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.condition.type">Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {conditionList.map((condition, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/condition/${condition.id}`} color="link" size="sm">
                      {condition.id}
                    </Button>
                  </td>
                  <td>{condition.description}</td>
                  <td>
                    <Translate contentKey={`eduApp.ConditionType.${condition.type}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/condition/${condition.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/condition/${condition.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/condition/${condition.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eduApp.condition.home.notFound">No Conditions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Condition;
