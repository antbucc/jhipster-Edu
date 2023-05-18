import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivity } from 'app/shared/model/activity.model';
import { getEntities } from './activity.reducer';

export const Activity = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const activityList = useAppSelector(state => state.activity.entities);
  const loading = useAppSelector(state => state.activity.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="activity-heading" data-cy="ActivityHeading">
        <Translate contentKey="eduApp.activity.home.title">Activities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.activity.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/activity/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.activity.home.createLabel">Create new Activity</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {activityList && activityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.activity.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.activity.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.activity.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.activity.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.activity.tool">Tool</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.activity.difficulty">Difficulty</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.activity.concept">Concept</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {activityList.map((activity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/activity/${activity.id}`} color="link" size="sm">
                      {activity.id}
                    </Button>
                  </td>
                  <td>{activity.title}</td>
                  <td>{activity.description}</td>
                  <td>
                    <Translate contentKey={`eduApp.ActivityType.${activity.type}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.Tool.${activity.tool}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.Difficulty.${activity.difficulty}`} />
                  </td>
                  <td>
                    {activity.concepts
                      ? activity.concepts.map((val, j) => (
                          <span key={j}>
                            <Link to={`/concept/${val.id}`}>{val.title}</Link>
                            {j === activity.concepts.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/activity/${activity.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/activity/${activity.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/activity/${activity.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eduApp.activity.home.notFound">No Activities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Activity;
