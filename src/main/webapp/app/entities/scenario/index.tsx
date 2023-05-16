import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Scenario from './scenario';
import ScenarioDetail from './scenario-detail';
import ScenarioUpdate from './scenario-update';
import ScenarioDeleteDialog from './scenario-delete-dialog';

const ScenarioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Scenario />} />
    <Route path="new" element={<ScenarioUpdate />} />
    <Route path=":id">
      <Route index element={<ScenarioDetail />} />
      <Route path="edit" element={<ScenarioUpdate />} />
      <Route path="delete" element={<ScenarioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ScenarioRoutes;
