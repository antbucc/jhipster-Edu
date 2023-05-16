import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Learner from './learner';
import LearnerDetail from './learner-detail';
import LearnerUpdate from './learner-update';
import LearnerDeleteDialog from './learner-delete-dialog';

const LearnerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Learner />} />
    <Route path="new" element={<LearnerUpdate />} />
    <Route path=":id">
      <Route index element={<LearnerDetail />} />
      <Route path="edit" element={<LearnerUpdate />} />
      <Route path="delete" element={<LearnerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LearnerRoutes;
