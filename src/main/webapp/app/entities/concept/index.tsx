import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Concept from './concept';
import ConceptDetail from './concept-detail';
import ConceptUpdate from './concept-update';
import ConceptDeleteDialog from './concept-delete-dialog';

const ConceptRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Concept />} />
    <Route path="new" element={<ConceptUpdate />} />
    <Route path=":id">
      <Route index element={<ConceptDetail />} />
      <Route path="edit" element={<ConceptUpdate />} />
      <Route path="delete" element={<ConceptDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConceptRoutes;
