import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Path from './path';
import PathDetail from './path-detail';
import PathUpdate from './path-update';
import PathDeleteDialog from './path-delete-dialog';

const PathRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Path />} />
    <Route path="new" element={<PathUpdate />} />
    <Route path=":id">
      <Route index element={<PathDetail />} />
      <Route path="edit" element={<PathUpdate />} />
      <Route path="delete" element={<PathDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PathRoutes;
