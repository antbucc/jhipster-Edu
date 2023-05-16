import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Domain from './domain';
import DomainDetail from './domain-detail';
import DomainUpdate from './domain-update';
import DomainDeleteDialog from './domain-delete-dialog';

const DomainRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Domain />} />
    <Route path="new" element={<DomainUpdate />} />
    <Route path=":id">
      <Route index element={<DomainDetail />} />
      <Route path="edit" element={<DomainUpdate />} />
      <Route path="delete" element={<DomainDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DomainRoutes;
