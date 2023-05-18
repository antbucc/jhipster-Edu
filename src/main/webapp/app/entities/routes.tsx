import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Domain from './domain';
import Scenario from './scenario';
import Module from './module';
import Learner from './learner';
import Educator from './educator';
import Competence from './competence';
import Concept from './concept';
import Activity from './activity';
import Fragment from './fragment';
import Precondition from './precondition';
import Effect from './effect';
import Condition from './condition';
import Path from './path';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="domain/*" element={<Domain />} />
        <Route path="scenario/*" element={<Scenario />} />
        <Route path="module/*" element={<Module />} />
        <Route path="learner/*" element={<Learner />} />
        <Route path="educator/*" element={<Educator />} />
        <Route path="competence/*" element={<Competence />} />
        <Route path="concept/*" element={<Concept />} />
        <Route path="activity/*" element={<Activity />} />
        <Route path="fragment/*" element={<Fragment />} />
        <Route path="precondition/*" element={<Precondition />} />
        <Route path="effect/*" element={<Effect />} />
        <Route path="condition/*" element={<Condition />} />
        <Route path="path/*" element={<Path />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
