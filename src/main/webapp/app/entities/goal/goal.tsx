import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGoal } from 'app/shared/model/goal.model';
import { getEntities } from './goal.reducer';

export const Goal = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const goalList = useAppSelector(state => state.goal.entities);
  const loading = useAppSelector(state => state.goal.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="goal-heading" data-cy="GoalHeading">
        <Translate contentKey="eduApp.goal.home.title">Goals</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.goal.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/goal/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.goal.home.createLabel">Create new Goal</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {goalList && goalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.goal.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.goal.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.goal.concept">Concept</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {goalList.map((goal, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/goal/${goal.id}`} color="link" size="sm">
                      {goal.id}
                    </Button>
                  </td>
                  <td>{goal.title}</td>
                  <td>
                    {goal.concepts
                      ? goal.concepts.map((val, j) => (
                          <span key={j}>
                            <Link to={`/concept/${val.id}`}>{val.title}</Link>
                            {j === goal.concepts.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/goal/${goal.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/goal/${goal.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/goal/${goal.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eduApp.goal.home.notFound">No Goals found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Goal;
