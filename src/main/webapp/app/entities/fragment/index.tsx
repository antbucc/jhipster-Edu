import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Fragment from './fragment';
import FragmentDetail from './fragment-detail';
import FragmentUpdate from './fragment-update';
import FragmentDeleteDialog from './fragment-delete-dialog';

const FragmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Fragment />} />
    <Route path="new" element={<FragmentUpdate />} />
    <Route path=":id">
      <Route index element={<FragmentDetail />} />
      <Route path="edit" element={<FragmentUpdate />} />
      <Route path="delete" element={<FragmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FragmentRoutes;
