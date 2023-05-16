import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Module from './module';
import ModuleDetail from './module-detail';
import ModuleUpdate from './module-update';
import ModuleDeleteDialog from './module-delete-dialog';

const ModuleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Module />} />
    <Route path="new" element={<ModuleUpdate />} />
    <Route path=":id">
      <Route index element={<ModuleDetail />} />
      <Route path="edit" element={<ModuleUpdate />} />
      <Route path="delete" element={<ModuleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ModuleRoutes;
