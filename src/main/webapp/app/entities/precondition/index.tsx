import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Precondition from './precondition';
import PreconditionDetail from './precondition-detail';
import PreconditionUpdate from './precondition-update';
import PreconditionDeleteDialog from './precondition-delete-dialog';

const PreconditionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Precondition />} />
    <Route path="new" element={<PreconditionUpdate />} />
    <Route path=":id">
      <Route index element={<PreconditionDetail />} />
      <Route path="edit" element={<PreconditionUpdate />} />
      <Route path="delete" element={<PreconditionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreconditionRoutes;
