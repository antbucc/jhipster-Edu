import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Goal from './goal';
import GoalDetail from './goal-detail';
import GoalUpdate from './goal-update';
import GoalDeleteDialog from './goal-delete-dialog';

const GoalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Goal />} />
    <Route path="new" element={<GoalUpdate />} />
    <Route path=":id">
      <Route index element={<GoalDetail />} />
      <Route path="edit" element={<GoalUpdate />} />
      <Route path="delete" element={<GoalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GoalRoutes;
