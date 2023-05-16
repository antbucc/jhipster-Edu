import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Educator from './educator';
import EducatorDetail from './educator-detail';
import EducatorUpdate from './educator-update';
import EducatorDeleteDialog from './educator-delete-dialog';

const EducatorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Educator />} />
    <Route path="new" element={<EducatorUpdate />} />
    <Route path=":id">
      <Route index element={<EducatorDetail />} />
      <Route path="edit" element={<EducatorUpdate />} />
      <Route path="delete" element={<EducatorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EducatorRoutes;
