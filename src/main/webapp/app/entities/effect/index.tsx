import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Effect from './effect';
import EffectDetail from './effect-detail';
import EffectUpdate from './effect-update';
import EffectDeleteDialog from './effect-delete-dialog';

const EffectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Effect />} />
    <Route path="new" element={<EffectUpdate />} />
    <Route path=":id">
      <Route index element={<EffectDetail />} />
      <Route path="edit" element={<EffectUpdate />} />
      <Route path="delete" element={<EffectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EffectRoutes;
